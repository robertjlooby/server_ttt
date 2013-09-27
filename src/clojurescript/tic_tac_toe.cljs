(ns tic-tac-toe)

(def board-state (atom "_________"))

(def buttons-enabled (atom false))

(def display (atom (display/css-display.)))

(defn make-move [a b c])

(defn reset-board [fun]
  (reset! buttons-enabled false)
  (reset! board-state "_________")
  (abstract-display/reset_board @display #())
  (make-move "X" "_________" 0))
