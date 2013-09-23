(ns clojure_server.ttt-server-spec
  (:require [speclj.core :refer :all]
            [clojure.data.json :as json]
            [clojure_server.ttt-server :refer :all])
  (import (TicTacToe TicTacToeBoard)))

(defn stream-to-string [stream]
  (let [b-a (byte-array 5000)
        _ (.read stream b-a 0 5000)
        body (String. b-a)]
    (clojure.string/trim body)))

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
    (should= [0, "X________"] (make-ai-move :X "_________")))

  (it "should make a move to force a tie"
    (should= [1, "XOX_O____"] (make-ai-move :O "X_X_O____")))
)

(describe "start-game"
  (it "should return empty board for move 0"
    (let [response (start-game {:body '("marker=X&move=0")})
          body (:body (first response))]
      (should= {:boardState "_________" :aiMove -1 :result nil}
               (json/read-str (first body) :key-fn keyword))
      (should= 200 (second response))))

  (it "should return board with 1 move made for move 1"
    (let [response (start-game {:body '("marker=X&move=1")})
          body (:body (first response))]
      (should= {:boardState "O________" :aiMove 0 :result nil}
               (json/read-str (first body) :key-fn keyword))
      (should= 200 (second response))))
)

(describe "take-turn"
  (it "should make the ai move and return the new board state"
    (reset! port 3000)
    (let [req {:body '("marker=O&board_state=O________")}
          response (take-turn req {})
          body (:body (first response))]
      (should= 200 (second response))
      (should= {:boardState "O___X____" :aiMove 4 :result nil}
               (json/read-str (first body) :key-fn keyword))))

  (it "should return W if player wins"
    (reset! port 3000)
    (let [req {:body '("marker=O&board_state=O_XO_XO__")}
          response (take-turn req {})
          body (:body (first response))]
      (should= 200 (second response))
      (should= {:boardState "O_XO_XO__" :aiMove -1 :result "W"}
               (json/read-str (first body) :key-fn keyword))))


  (it "should return T if player ties"
    (reset! port 3000)
    (let [req {:body '("marker=O&board_state=OXOXXOOOX")}
          response (take-turn req {})
          body (:body (first response))]
      (should= 200 (second response))
      (should= {:boardState "OXOXXOOOX" :aiMove -1 :result "T"}
               (json/read-str (first body) :key-fn keyword))))

  (it "should return L if player loses"
    (reset! port 3000)
    (let [req {:body '("marker=X&board_state=O_XOOOX_X")}
          response (take-turn req {})
          body (:body (first response))]
      (should= 200 (second response))
      (should= {:boardState "O_XOOOX_X" :aiMove -1 :result "L"}
               (json/read-str (first body) :key-fn keyword))))

  (it "should return L if player loses after an ai move"
    (reset! port 3000)
    (let [req {:body '("marker=X&board_state=O_XOO_X_X")}
          response (take-turn req {})
          body (:body (first response))]
      (should= 200 (second response))
      (should= {:boardState "O_XOOOX_X" :aiMove 5 :result "L"}
               (json/read-str (first body) :key-fn keyword))))

  (it "should return tie if ai ties"
    (reset! port 3000)
    (let [req {:body '("marker=X&board_state=XOXOOX_XO")}
          response (take-turn req {})
          body (:body (first response))]
      (should= 200 (second response))
      (should= {:boardState "XOXOOXOXO" :aiMove 6 :result "T"}
               (json/read-str (first body) :key-fn keyword))))
)

(describe "result"
  (it "should return nil for unfinished game"
    (should= nil (result "X" "O___X____")))

  (it "should return W for a player win"
    (should= "W" (result "X" "X_OOO_XXX")))

  (it "should return L for a player loss"
    (should= "L" (result "O" "X_OOO_XXX")))

  (it "should return T for a tie"
    (should= "T" (result "O" "XOOOXXXXO")))
)

(describe "initialize-page"
  (it "should display an HTML page"
    (let [response (initialize-page)
          body (:body (first response))]
      (should 
        (re-matches 
          #"<!DOCTYPE html><html><head>[\s\S]*</head><body>[\s\S]*</body></html>[\s\S]*"
          (first body)))
      (should= 200 (second response))))

  (it "should have a #board div"
    (let [response (initialize-page)
          body (:body (first response))]
      (should 
        (re-matches 
          #"[\s\S]*<div id=\"board\"></div>[\s\S]*"
          (first body)))
      (should= 200 (second response))))
)

(describe "router"
  (it "should call initialize-page for GET / with new-game-form"
    (with-redefs-fn {#'initialize-page (fn [] "i-p")}
      #(let [req {:headers {:method "GET" :path "/"}}
             response (router req)]
         (should= "i-p" response))))

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
