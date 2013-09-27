(ns tic-tac-toe)

(def board-state (atom "_________"))

(def buttons-enabled (atom false))

(defn reset-board [fun]
  (reset! buttons-enabled false)
  (reset! board-state "_________")
  (make-move "X" "_________" 0))

(defn make-move [a b c])
