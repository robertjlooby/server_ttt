# JavaTicTacToe in browser with clojure_server

To run Clojure tests:    `lein spec`

To run Java tests:       `lein junit`

To run JavaScript tests: `rake jasmine`

                         visit http://localhost:8888/ in a browser

To compile ClojureScripts: `rake spec_trim &`
                           `lein cljsbuild auto`

To run the server:

1. Create the .jar file:  `lein uberjar`
2. Start the server:      `java -jar target/server_ttt-0.1.0-SNAPSHOT-standalone.jar -p 3000 -d "/path/to/server_ttt/ttt_public"`
