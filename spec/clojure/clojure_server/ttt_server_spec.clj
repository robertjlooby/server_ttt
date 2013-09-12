(ns clojure_server.ttt-server-spec
  (:require [speclj.core :refer :all]
            [clojure_server.ttt-server :refer :all])
  (import (TicTacToe TicTacToeBoard)))

(describe "get-ai-move"
  (it "should be [0, 0] for an empty board"
    (should= [0 0] (get-ai-move :X "_________")))

  (it "should be [1, 1] for a board with an X at [0, 0]"
    (should= [1 1] (get-ai-move :O "X________")))

  (it "should be [2, 2] for a board with only that spot open"
    (should= [2 2] (get-ai-move :X "XOXXOOOX_")))
)

(describe "make-ai-move"
  (it "should make a move at [0, 0] for an empty board"
    (should= "X________" (make-ai-move :X "_________")))

  (it "should make a move to force a tie"
    (should= "XOX_O____" (make-ai-move :O "X_X_O____")))
)

(describe "start-game"
  (it "should return empty board display for move 0"
    (let [response (start-game {:body '("marker=X&move=0")})
          stream (:content-stream (first response))
          b-a (byte-array 5000)
          _ (.read stream b-a 0 5000)
          body (String. b-a)]
      (should= (.toWebString (TicTacToeBoard. "_________") "X")
               (clojure.string/trim body))
      (should= 200 (second response))))

  (it "should return board display with 1 move made for move 1"
    (let [response (start-game {:body '("marker=X&move=1")})
          stream (:content-stream (first response))
          b-a (byte-array 5000)
          _ (.read stream b-a 0 5000)
          body (String. b-a)]
      (should= (.toWebString (TicTacToeBoard. "O________") "X")
               (clojure.string/trim body))
      (should= 200 (second response))))
)

(describe "take-turn"
  (it "should make the ai move and return the new board state"
    (reset! port 3000)
    (let [req {:body '("marker=O&board_state=O________")}
          response (take-turn req {})
          stream (:content-stream (first response))
          b-a (byte-array 5000)
          _ (.read stream b-a 0 5000)
          body (String. b-a)]
      (should= 200 (second response))
      (should= (.toWebString (TicTacToeBoard. "O___X____") "O")
               (clojure.string/trim body))))

  (it "should return win if player wins and offer to play again"
    (reset! port 3000)
    (let [req {:body '("marker=O&board_state=O_XO_XO__")}
          response (take-turn req {})
          stream (:content-stream (first response))
          b-a (byte-array 5000)
          _ (.read stream b-a 0 5000)
          body (String. b-a)]
      (should= 200 (second response))
      (should= (str "<h1>You Won!</h1>"
                    (.toWebString (TicTacToeBoard. "O_XO_XO__") "O")
                    (new-game-form))
               (clojure.string/trim body))))


  (it "should return tie if player ties and offer to play again"
    (reset! port 3000)
    (let [req {:body '("marker=O&board_state=OXOXXOOOX")}
          response (take-turn req {})
          stream (:content-stream (first response))
          b-a (byte-array 5000)
          _ (.read stream b-a 0 5000)
          body (String. b-a)]
      (should= 200 (second response))
      (should= (str "<h1>It Was a Tie!</h1>"
                    (.toWebString (TicTacToeBoard. "OXOXXOOOX") "O")
                    (new-game-form))
               (clojure.string/trim body))))

  (it "should return lose if player loses and offer to play again"
    (reset! port 3000)
    (let [req {:body '("marker=X&board_state=O_XOOOX_X")}
          response (take-turn req {})
          stream (:content-stream (first response))
          b-a (byte-array 5000)
          _ (.read stream b-a 0 5000)
          body (String. b-a)]
      (should= 200 (second response))
      (should= (str "<h1>You Lost!</h1>"
                    (.toWebString (TicTacToeBoard. "O_XOOOX_X") "X")
                    (new-game-form))
               (clojure.string/trim body))))

  (it "should return lose if player loses after an ai move
       and offer to play again"
    (reset! port 3000)
    (let [req {:body '("marker=X&board_state=O_XOO_X_X")}
          response (take-turn req {})
          stream (:content-stream (first response))
          b-a (byte-array 5000)
          _ (.read stream b-a 0 5000)
          body (String. b-a)]
      (should= 200 (second response))
      (should= (str "<h1>You Lost!</h1>"
                    (.toWebString (TicTacToeBoard. "O_XOOOX_X") "X")
                    (new-game-form))
               (clojure.string/trim body))))

  (it "should return tie if ai ties and offer to play again"
    (reset! port 3000)
    (let [req {:body '("marker=X&board_state=XOXOOX_XO")}
          response (take-turn req {})
          stream (:content-stream (first response))
          b-a (byte-array 5000)
          _ (.read stream b-a 0 5000)
          body (String. b-a)]
      (should= 200 (second response))
      (should= (str "<h1>It Was a Tie!</h1>"
                    (.toWebString (TicTacToeBoard. "XOXOOXOXO") "X")
                    (new-game-form))
               (clojure.string/trim body))))
)

(describe "new-game-form"
  (it "is a form, points to initializeGame"
    (should 
      (re-matches 
        #"<form[\s\S]*onsubmit=\"TicTacToe\.initializeGame[\s\S]*</form>"
        (new-game-form))))
)

(describe "initialize-page"
  (it "should display an HTML page"
    (let [response (initialize-page "hello page")
          stream (:content-stream (first response))
          b-a (byte-array 5000)
          _ (.read stream b-a 0 5000)
          body (String. b-a)]
            (should 
              (re-matches 
                #"<!DOCTYPE html><html><head>[\s\S]*</head><body>[\s\S]*</body></html>[\s\S]*"
                body))
            (should= 200 (second response))))

  (it "should have a #board div"
    (let [response (initialize-page "hello page")
          stream (:content-stream (first response))
          b-a (byte-array 5000)
          _ (.read stream b-a 0 5000)
          body (String. b-a)]
            (should 
              (re-matches 
                #"[\s\S]*<div id=\"board\">[\s\S]*"
                body))
            (should= 200 (second response))))

  (it "should have the arg inside the #board div"
    (let [response (initialize-page "hello page")
          stream (:content-stream (first response))
          b-a (byte-array 5000)
          _ (.read stream b-a 0 5000)
          body (String. b-a)]
            (should 
              (re-matches 
                #"[\s\S]*<div id=\"board\">hello page[\s\S]*"
                body))
            (should= 200 (second response))))
)

(describe "router"
  (it "should call initialize-page for GET / with new-game-form"
    (with-redefs-fn {#'initialize-page (fn [bdy] (str "i-p" bdy))
                     #'new-game-form (fn [] (str "n-g-f"))}
      #(let [req {:headers {:method "GET" :path "/"}}
             response (router req)]
         (should= "i-pn-g-f" response))))

  (it "should call start-game for POST / with request"
    (with-redefs-fn {#'start-game (fn [req] (str "s-g" (:a req)))}
      #(let [req {:headers {:method "POST" :path "/"}
                  :a " hey"}
             response (router req)]
        (should= "s-g hey" response))))

  (it "should make the ai move, then redirect for POST /game"
    (with-redefs-fn {#'take-turn (fn [req params] [req params])}
      #(let [req {:headers {:method "POST"
                            :path "/game"}
                  :body '("marker=X&board_state=X________")}
             response (router req)]
         (should= req (first response)))))

  (it "should serve any file in stylesheets for a GET"
    (reset! directory "/public")
    (with-redefs-fn {#'clojure_server.server/serve-file
                       (fn [path request] [path request])}
      #(let [req {:headers {:method "GET"
                            :path "/stylesheets/any_file"}}
             response (router req)]
        (should= "/public/stylesheets/any_file" (first response))
        (should= req (second response)))))

  (it "should serve any file in javascripts for a GET"
    (reset! directory "/public")
    (with-redefs-fn {#'clojure_server.server/serve-file
                       (fn [path request] [path request])}
      #(let [req {:headers {:method "GET"
                            :path "/javascripts/any_file"}}
             response (router req)]
        (should= "/public/javascripts/any_file" (first response))
        (should= req (second response)))))
)
