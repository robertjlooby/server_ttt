(defproject server_ttt "0.1.0-SNAPSHOT"
  :description "JavaTicTacToe in browser with clojure_server"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/data.codec "0.1.0"]
                 [org.clojure/data.json  "0.2.3"]
                 [org.clojure/clojure-contrib "1.2.0"]]
  :profiles {:dev {:dependencies [[speclj "2.5.0"]
                                  [junit/junit "4.10"]]}}
  :plugins [[speclj "2.5.0"]
            [lein-junit "1.1.2"]]
  :jvm-opts ["-Dline.separator=\r\n"]
  :source-paths ["src/clojure"]
  :java-source-paths ["src/java" "spec/java"]
  :junit ["spec/java"]
  :test-paths ["spec/clojure"]
  :main clojure_server.ttt-server)
