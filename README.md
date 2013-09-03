# JavaTicTacToe in browser with clojure_server

To run clojure tests: `lein spec`

To run java tests:    `lein junit`

To run the server:

1. Create the .jar file:  `lein uberjar`
2. Start the server:      `java -jar target/server_ttt-0.1.0-SNAPSHOT-standalone.jar -p 3000 -d "/path/to/server_ttt/ttt_public"`
