(ns clojure_server.ttt-server-spec
  (:require [speclj.core :refer :all]
            [clojure_server.ttt-server :refer :all]))

(describe "start-game"
  (it "should redirect to GET /game with marker/move set
       and board state empty"
    (reset! port 3000)
    (let [req {:body '("marker=X&move=0")}
          response (start-game req)]
      (should= 301 (second response))
      (should= "http://localhost:3000/game?marker=X&move=0&board_state=_________"
               (:Location (:headers (first response))))))
)

(describe "display-game"
  (it "should display an HTML page"
    (let [response (display-game {} {:marker "X"
                                     :move   "0"
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
    (with-redefs-fn {#'display-game (fn [request params]
                                      [request params])}
      #(let [req {:headers {:method "GET"
                            :path "/game?marker=X&move=0&board_state=_________"}}
             response (router req)]
         (should= req (first response))
         (should= {:marker "X"
                   :move   "0"
                   :board_state "_________"} (second response)))))
)
