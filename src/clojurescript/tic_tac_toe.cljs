(ns tic-tac-toe
  (:require [css-display :as display]))

(def board-state (atom "_________"))

(def buttons-enabled (atom false))

(defn reset-board [fun]
  (reset! buttons-enabled false)
  (reset! board-state "_________")
  (display/display-win-message)
  (make-move "X" "_________" 0)
  (js/alert "pause"))

(defn make-move [a b c])
