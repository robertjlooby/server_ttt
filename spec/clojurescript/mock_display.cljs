(ns mock-display)

(def last-args (atom nil))

(deftype mock-css-display []
  abstract-display/display-protocol
  (reset-board [this fun]
    (reset! last-args [:reset-board fun]))
  (display-form [this fun]
    (reset! last-args [:display-form fun]))
  (get-cell [this cell-num]
    (reset! last-args [:get-cell cell-num]))
  (get-button [this cell-num]
    (reset! last-args [:get-button cell-num]))
  (make-move [this marker cell-num]
    (reset! last-args [:make-move cell-num]))
  (get-marker [this]
    (reset! last-args [:get-marker]))
  (get-move [this]
    (reset! last-args [:get-move]))
  (display-win-message [this]
    (reset! last-args [:display-win-message]))
  (display-lose-message [this]
    (reset! last-args [:display-lose-message]))
  (display-tie-message [this]
    (reset! last-args [:display-tie-message])))
