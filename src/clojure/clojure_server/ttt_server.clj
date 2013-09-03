(ns clojure_server.ttt-server
  (:require [clojure_server.server :refer :all]
            [clojure_server.router :refer :all])
  (:use clojure.contrib.command-line)
  (:gen-class))

(def directory (atom nil))
(def port (atom nil))
(defrouter router [request params]
  (GET "/" [{:headers {:Location (str "http://localhost:" @port "/index.html")}} 301])
  (GET "index.html" (serve-file (str @directory "/index.html") request)))

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
