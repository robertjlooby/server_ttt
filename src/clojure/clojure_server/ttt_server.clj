(ns clojure_server.ttt-server
  (:require [clojure.data.json :as json]
            [clojure_server.server :refer :all]
            [clojure_server.router :refer :all])
  (:import (TicTacToe BoardMarker TicTacToeBoard AIPlayer)
           (java.io StringBufferInputStream))
  (:use clojure.contrib.command-line)
  (:gen-class))
(def directory (atom nil))
(def port (atom nil))

(defn response-from-string [response]
  [{:headers {:Content-Length (count response)}
    :content-stream (StringBufferInputStream. response)} 200])

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
       cell-num (+ (* 3 (first ai-move)) (second ai-move))
       _ (.makeMove board (first ai-move) (second ai-move)
                    (if (= ai-key :X) BoardMarker/X
                                      BoardMarker/O))]
   [cell-num, (.getBoardStateString board)]))

(defn start-game [request]
  (let [settings (parse-query-to-params (first (:body request)))
        marker (:marker settings)
        move   (:move   settings)
        bs-str "_________"
        [ai-move final-bs] (if (= "0" move) [-1 bs-str]
                  (make-ai-move (if (= marker "X") :O :X) bs-str))]
    (response-from-string (json/write-str {:boardState final-bs
                                           :aiMove ai-move
                                           :result nil}))))

(defn result [marker board_state]
  (let [player (if (= marker "X") BoardMarker/X
                                  BoardMarker/O)
        board (TicTacToeBoard. board_state)
        winner (.winner board)]
    (condp = winner
      player
        "W"
      BoardMarker/T
        "T"
      BoardMarker/_
        nil
      "L")))

(defn take-turn [request params]
  (let [settings (parse-query-to-params (first (:body request)))
        marker (:marker settings)
        board_state (:board_state settings)
        res (result marker board_state)]
    (if (nil? res)
        (let [ai-key (if (= marker "X") :O :X)
              [ai-move new-bs] (make-ai-move ai-key board_state)
              new-res (result marker new-bs)]
          (response-from-string
            (json/write-str
              {:boardState new-bs :aiMove ai-move :result new-res})))
        (response-from-string
          (json/write-str
            {:boardState board_state :aiMove -1 :result res})))))

(defn initialize-page []
  (let [response
    (str "<!DOCTYPE html>"
         "<html><head>"
         "<link rel=\"stylesheet\" type=\"text/css\" href=\"/stylesheets/ttt_style.css\">"
         "<script src=\"/javascripts/jquery-1.10.2.min.js\"></script>"
         "<script src=\"/javascripts/CSSDisplay.js\"></script>"
         "<script src=\"/javascripts/TicTacToe.js\"></script>"
         "</head><body><div id=\"board\"></div>"
         "</body></html>")]
    (response-from-string response)))

(defrouter router [request params]
  (GET "/" (initialize-page))
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
