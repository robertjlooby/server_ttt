(ns clojure_server.server
  (:require [clojure_server.request-parser :refer :all]
            [clojure_server.response-builder :refer :all]
            [clojure_server.router :refer :all])
  (:import (java.io PrintWriter FileInputStream InputStream)
           (java.net ServerSocket)))

(defn create-server-socket 
  ([port] (ServerSocket. port))
  ([port address] (ServerSocket. port 0 address)))

(defn listen [server-socket]
  (try
    (.accept server-socket)
    (catch Exception e (prn (str "exception caught " e)))))

(defn socket-writer [socket]
  (PrintWriter.
    (.getOutputStream socket) true))

(defn file-to-seq [file-path]
  (line-seq (clojure.java.io/reader file-path)))

(defn seq-to-file [file string-seq]
  (let [p (PrintWriter. file)]
    (doseq [line string-seq]
      (.println p line))
    (.flush p)))

(defn serve-directory [dir]
  (lazy-cat
    ["<!DOCTYPE html>"
     "<html>"
     "<head>" 
     "</head>"
     "<body>"
     (.getAbsolutePath dir)]
    (map #(str "<div><a href=\"/" (.getName %) "\">" (.getName %) "</a></div>")
      (.listFiles dir))
    ["</body>"
     "</html>"]))

(defn extension [path]
  (let [start (.lastIndexOf path ".")]
    (if (> start 0)
      (subs path start))))

(defn serve-file [path request]
  (let [file (clojure.java.io/file path)]
    (if (.exists file)
      (cond
        (.isDirectory file)
          [{:body (serve-directory file)} 200]
        (contains? #{".gif" ".png" ".jpeg"} (extension path))
          [{:headers {:media-type (str "image/"
                                       (subs (extension path) 1))
                     :Content-Length (.length file)}
            :body (FileInputStream. file)} 200]
        (:Range (:headers request))
          (let [[_ f l] (first (re-seq #"bytes=(\d+)-(\d+)"
                                (:Range (:headers request))))
                begin (Integer/parseInt f)
                end   (Integer/parseInt l)
                reader (FileInputStream. file)
                _ (.read reader (byte-array begin) 0 begin)]
            [{:headers {:Content-Length (- end begin)}
              :body reader} 206])
        :else
          [{:headers {:Content-Length (.length file)}
            :body (file-to-seq file)} 200])
      [{:headers {:Content-Length 9}
        :body '("Not Found")} 404])))

(defmulti serve (fn [response, o-stream]
                  (cond
                    (sequential? (:body (first response)))
                      :string-seq
                    (isa? (class (:body (first response)))
                          InputStream)
                      :stream)))

(defmethod serve :string-seq [response, o-stream]
  (let [p-writer (PrintWriter. o-stream)]
    (doseq [line (:body (first response))]
      (.println p-writer line))
    (.flush p-writer)))
(defmethod serve :stream [response, o-stream]
  (let [i-stream (:body (first response))
        length (or (:Content-Length (:headers (first response)))
                   Integer/MAX_VALUE)
        chunk-size 1024
        b-a (byte-array chunk-size)]
    (loop [num-read (.read i-stream b-a 0 (min length chunk-size))
           tot-read 0]
      (cond
        (< num-read chunk-size)
          (do
            (if (> num-read 0) (.write o-stream b-a 0 num-read))
            (.flush o-stream))
        :else
          (do
            (.write o-stream b-a 0 num-read)
            (recur
              (.read i-stream b-a 0 
                     (min (- length num-read tot-read) chunk-size))
              (+ num-read tot-read)))))))

(defn server [server-socket directory router]
  (let [socket-to-client (listen server-socket)]
    (future
      (with-open [socket socket-to-client]
        (let [p-o-stream (socket-writer socket)
              request  (parse-request socket)
              router-response (router request)
              headers (build-response router-response)]
          (doseq [line headers]
            (.println p-o-stream line))
          (serve router-response (.getOutputStream socket))))))
  (if (.isClosed server-socket) 
      (prn "server exiting, socket closed") 
      (recur server-socket directory router)))
