(ns clojure_server.ttt-server
  (:require [clojure_server.server :refer :all]
            [clojure_server.router :refer :all])
  (:import (TicTacToe BoardMarker TicTacToeBoard AIPlayer)
           (java.io StringBufferInputStream))
  (:use clojure.contrib.command-line)
  (:gen-class))
(def directory (atom nil))
(def port (atom nil))

(defn get-ai-move [ai-key board-str]
  (let [ai-sym (if (= ai-key :X) BoardMarker/X
                                  BoardMarker/O)
        board (TicTacToeBoard. board-str)
        player (AIPlayer. 
                 ai-sym (TicTacToeBoard.))]
    (seq (.getMove player (.getState board)))))

(defn make-ai-move [ai-key board-str]
 (let [ai-move (get-ai-move ai-key board-str)
       board (TicTacToeBoard. board-str)
       _ (.makeMove board (first ai-move) (second ai-move)
                    (if (= ai-key :X) BoardMarker/X
                                      BoardMarker/O))]
   (.getBoardStateString board)))

(defn start-game [request]
  (let [settings (parse-query-to-params (first (:body request)))
        marker (:marker settings)
        move   (:move   settings)
        bs-str "_________"
        final-bs (if (= "0" move) bs-str
                  (make-ai-move (if (= marker "X") :O :X) bs-str))]
    [{:content-stream (StringBufferInputStream.
                        (.toWebString (TicTacToeBoard. final-bs)
                                      marker))} 200]))

(defn new-game-form []
  (str "<form action=\"\" onsubmit=\"TicTacToe.initializeGame(); return false;\">"
    "<div>"
    "Marker:"
      "<input type=\"radio\" name=\"marker\" value=\"X\" checked> X"
      "<input type=\"radio\" name=\"marker\"   value=\"O\"> O"
    "</div>"
    "<div>"
    "Move:"
      "<input type=\"radio\" name=\"move\" value=\"0\" checked> First"
      "<input type=\"radio\" name=\"move\" value=\"1\"> Second"
    "</div>"
    "<input type=\"submit\" value=\"Play!\">"
  "</form>"))

(defn result-page [marker board_state]
  (let [player (if (= marker "X") BoardMarker/X
                                  BoardMarker/O)
        board (TicTacToeBoard. board_state)
        winner (.winner board)]
    (condp = winner
      player
        [{:content-stream (StringBufferInputStream.
                            (str "<h1>You Won!</h1>"
                                 (.toWebString board marker)
                                 (new-game-form)))} 200]
      BoardMarker/T
        [{:content-stream (StringBufferInputStream.
                            (str "<h1>It Was a Tie!</h1>"
                                 (.toWebString board marker)
                                 (new-game-form)))} 200]
      BoardMarker/_
        nil
      [{:content-stream (StringBufferInputStream.
                            (str "<h1>You Lost!</h1>"
                                 (.toWebString board marker)
                                 (new-game-form)))} 200])))

(defn take-turn [request params]
  (let [settings (parse-query-to-params (first (:body request)))
        marker (:marker settings)
        board_state (:board_state settings)
        result (result-page marker board_state)]
    (if (nil? result)
        (let [ai-key (if (= marker "X") :O :X)
              new-bs (make-ai-move ai-key board_state)
              new-result (result-page marker new-bs)]
          (if (nil? new-result)
            [{:content-stream (StringBufferInputStream.
                                (.toWebString
                                 (TicTacToeBoard. new-bs) marker))}
             200]
              new-result))
        result)))

(defn initialize-page [body]
  (let [response
    (str "<!DOCTYPE html>"
         "<html><head>"
         "<link rel=\"stylesheet\" type=\"text/css\" href=\"/stylesheets/ttt_style.css\">"
         "<script src=\"/javascripts/jquery-1.10.2.min.js\"></script>"
         "<script src=\"/javascripts/TicTacToe.js\"></script>"
         "</head><body><div id=\"board\">"
         body
         "</div></body></html>")]
    [{:content-stream (StringBufferInputStream. response)} 200]))

(defrouter router [request params]
  (GET "/" (initialize-page (new-game-form)))
  (POST "/" (start-game request))
  (POST "/game" (take-turn request params))
  (GET "/stylesheets/:file" (serve-file (str @directory 
                                             (:path 
                                              (:headers request)))
                            request))
  (GET "/javascripts/:file" (serve-file (str @directory 
                                             (:path 
                                              (:headers request)))
                            request)))

(defn -main [& args]
  (with-command-line args
    "Usage...."
    [[p "The port to run the server on"]
     [d "The directory to serve files from"]]
    (reset! directory d)
    (reset! port (Integer/parseInt p))
    (with-open [server-socket (create-server-socket @port
                              (java.net.InetAddress/getByName "localhost"))]
      (server server-socket @directory router))))
