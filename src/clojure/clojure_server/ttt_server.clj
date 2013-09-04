(ns clojure_server.ttt-server
  (:require [clojure_server.server :refer :all]
            [clojure_server.router :refer :all])
  (:use clojure.contrib.command-line)
  (:gen-class))
(def directory (atom nil))
(def port (atom nil))

(defn get-ai-move [ai-key board-str]
  (let [ai-sym (if (= ai-key :X) TicTacToe.BoardMarker/X
                                  TicTacToe.BoardMarker/O)
        board (TicTacToe.TicTacToeBoard. board-str)
        player (TicTacToe.AIPlayer. 
                 ai-sym (TicTacToe.TicTacToeBoard.))]
    (seq (.getMove player (.getState board)))))

(defn make-ai-move [ai-key board-str]
 (let [ai-move (get-ai-move ai-key board-str)
       board (TicTacToe.TicTacToeBoard. board-str)
       _ (.makeMove board (first ai-move) (second ai-move)
                    (if (= ai-key :X) TicTacToe.BoardMarker/X
                                      TicTacToe.BoardMarker/O))]
   (.getBoardStateString board)))

(defn start-game [request]
  (let [settings (parse-query-to-params (first (:body request)))
        marker (:marker settings)
        move   (:move   settings)
        bs-str "_________"
        final-bs (if (= "0" move) bs-str
                  (make-ai-move (if (= marker "X") :O :X) bs-str))]
    [{:headers {:Location (str "http://localhost:" @port
                               "/game?marker=" marker
                               "&board_state=" final-bs)}} 301]))

(defn display-game [request params]
  (let [board (TicTacToe.TicTacToeBoard. (:board_state params))
        response
        (str "<!DOCTYPE html>"
             "<html><head>"
             "<link rel=\"stylesheet\" type=\"text/css\" href=\"ttt_style.css\">"
             "</head><body>"
             (.toWebString board (:marker params))
             "</body></html>")]
    [{:content-stream (java.io.StringBufferInputStream. response)}
     200]))

(defn take-turn [request params]
  (let [settings (parse-query-to-params (first (:body request)))
        marker (:marker settings)
        board_state (:board_state settings)
        ai-key (if (= marker "X") :O :X)
        new-bs (make-ai-move ai-key board_state)]
    [{:headers {:Location (str "http://localhost:" @port
                               "/game?marker=" marker
                               "&board_state=" new-bs)}} 301]))

(defrouter router [request params]
  (GET "/" [{:headers {:Location (str "http://localhost:" @port "/index.html")}} 301])
  (GET "/index.html" (serve-file (str @directory "/index.html") request))
  (POST "/index.html" (start-game request))
  (GET "/game" (display-game request params))
  (POST "/game" (take-turn request params))
  (GET "/ttt_style.css" (serve-file (str @directory "/ttt_style.css") request)))

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
