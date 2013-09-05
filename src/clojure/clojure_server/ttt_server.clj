(ns clojure_server.ttt-server
  (:require [clojure_server.server :refer :all]
            [clojure_server.router :refer :all])
  (:use clojure.contrib.command-line)
  (:gen-class))
(def directory (atom nil))
(def port (atom nil))

(defn redirect-to [rel-path]
  [{:headers {:Location (str "http://localhost:" @port rel-path)}}
   301])

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
    (redirect-to (str "/game?marker=" marker
                      "&board_state=" final-bs))))

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

(defn result-page [marker board_state]
  (let [player (if (= marker "X") TicTacToe.BoardMarker/X
                                  TicTacToe.BoardMarker/O)
        winner (.winner (TicTacToe.TicTacToeBoard. board_state))]
    (condp = winner
      player
        (redirect-to "/win.html")
      TicTacToe.BoardMarker/T
        (redirect-to "/tie.html")
      TicTacToe.BoardMarker/_
        nil
      (redirect-to "/lose.html"))))


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
              (redirect-to (str "/game?marker=" marker
                                "&board_state=" new-bs))
              new-result))
        result)))

(defrouter router [request params]
  (GET "/" (redirect-to "/index.html"))
  (POST "/index.html" (start-game request))
  (GET "/game" (display-game request params))
  (POST "/game" (take-turn request params))
  (GET "/:file" (serve-file (str @directory (:path 
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
