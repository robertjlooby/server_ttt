(ns tic-tac-toe)

(def board-state (atom "_________"))

(def buttons-enabled (atom false))

(defn enable-buttons []
  (reset! buttons-enabled true))

(defn disable-buttons []
  (reset! buttons-enabled false))

(defn get-new-board-state [marker b-s cell-num]
  (str (subs b-s 0 cell-num) marker (subs b-s (+ cell-num 1))))

(def display (atom (display/css-display.)))

(defn make-move [a b c])

(defn initialize-game [])

(defn reset-board [s]
  (reset! buttons-enabled false)
  (reset! board-state "_________")
  (abstract-display/reset_board
    @display 
    (fn [cell-num] 
      (if @buttons-enabled
        (make-move s @board-state cell-num)))))

(defn display-form []
  (abstract-display/display-form @display #(initialize-game)))
