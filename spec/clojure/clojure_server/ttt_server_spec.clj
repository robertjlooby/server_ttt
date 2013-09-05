(ns clojure_server.ttt-server-spec
  (:require [speclj.core :refer :all]
            [clojure_server.ttt-server :refer :all]))

(describe "redirect-to"
  (it "should redirect to the given path"
    (reset! port 3000)
    (let [r (redirect-to "/hello")]
      (should= "http://localhost:3000/hello"
               (:Location (:headers (first r))))
      (should= 301 (second r))))
)

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
  (it "should redirect to GET /game with marker/move set
       and board state empty"
    (reset! port 3000)
    (let [req {:body '("marker=X&move=0")}
          response (start-game req)]
      (should= 301 (second response))
      (should= "http://localhost:3000/game?marker=X&board_state=_________"
               (:Location (:headers (first response))))))

  (it "should redirect to GET /game with marker/move set
       and first move made"
    (reset! port 3000)
    (let [req {:body '("marker=O&move=1")}
          response (start-game req)]
      (should= 301 (second response))
      (should= "http://localhost:3000/game?marker=O&board_state=X________"
               (:Location (:headers (first response))))))
)

(describe "display-game"
  (it "should display an HTML page"
    (let [response (display-game {} {:marker "X"
                                     :board_state "_________"})
          stream (:content-stream (first response))
          b-a (byte-array 5000)
          _ (.read stream b-a 0 5000)
          body (String. b-a)]
            (should 
              (re-matches 
                #"<!DOCTYPE html><html><head>[\s\S]*</head><body>[\s\S]*</body></html>[\s\S]*"
                body))
            (should= 200 (second response))))
)

(describe "take-turn"
  (it "should make the ai move and redirect to the new board state"
    (reset! port 3000)
    (let [req {:body '("marker=O&board_state=O________")}
          response (take-turn req {})]
      (should= 301 (second response))
      (should= "http://localhost:3000/game?marker=O&board_state=O___X____"
               (:Location (:headers (first response))))))
)

(describe "router"
  (it "should redirect GET / to GET /index.html"
    (reset! port 3000)
    (let [response (router {:headers {:method "GET"
                                      :path "/"}})]
      (should= 301 (second response))
      (should= "http://localhost:3000/index.html"
               (:Location (:headers (first response))))))

  (it "should serve /index.html for GET /index.html"
    (reset! directory "/public")
    (with-redefs-fn {#'clojure_server.server/serve-file
                       (fn [path request] [path request])}
      #(let [req {:headers {:method "GET" :path "/index.html"}}
             response (router req)]
        (should= "/public/index.html" (first response))
        (should= req (second response)))))

  (it "should call start-game for POST /index.html"
    (with-redefs-fn {#'start-game (fn [request] [request])}
      #(let [req {:headers {:method "POST" :path "/index.html?p=1"}}
             response (router req)]
        (should= req (first response)))))

  (it "should call display-game for GET /game"
    (with-redefs-fn {#'display-game (fn [req params] [req params])}
      #(let [req {:headers {:method "GET"
                            :path "/game?marker=X&board_state=_________"}}
             response (router req)]
         (should= req (first response))
         (should= {:marker "X"
                   :board_state "_________"} (second response)))))

  (it "should make the ai move, then redirect for POST /game"
    (with-redefs-fn {#'take-turn (fn [req params] [req params])}
      #(let [req {:headers {:method "POST"
                            :path "/game"}
                  :body '("marker=X&board_state=X________")}
             response (router req)]
         (should= req (first response)))))
)
