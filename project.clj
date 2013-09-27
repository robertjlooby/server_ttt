(defproject server_ttt "0.1.0-SNAPSHOT"
  :description "JavaTicTacToe in browser with clojure_server"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/data.codec "0.1.0"]
                 [org.clojure/data.json  "0.2.3"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.clojure/clojurescript"0.0-1889"]
                 [org.clojure/google-closure-library-third-party "0.0-2029"]
                 [domina "1.0.2-SNAPSHOT"]
                 [hiccups "0.2.0"]]
  :profiles {:dev {:dependencies [[speclj "2.5.0"]
                                  [junit/junit "4.10"]]}}
  :plugins [[speclj "2.5.0"]
            [lein-junit "1.1.2"]
            [lein-cljsbuild "0.3.3"]]
  :jvm-opts ["-Dline.separator=\r\n"]
  :source-paths ["src/clojure"]
  :java-source-paths ["src/java" "spec/java"]
  :junit ["spec/java"]
  :test-paths ["spec/clojure"]
  :cljsbuild {
              :builds [{
                        :source-paths ["src/clojurescript" "spec/clojurescript"]
                        :compiler {
                                   :output-to "spec/javascripts/mainSpec.js"
                                   :optimizations :whitespace
                                   :pretty-print true}}]}
  :main clojure_server.ttt-server)
