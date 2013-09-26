(ns abstract-display)

(defprotocol display-protocol
  (reset-board [this fun])
  (display-form [this fun])
  (get-cell [this cell-num])
  (get-button [this cell-num])
  (make-move [this marker cell-num])
  (get-marker [this])
  (get-move [this])
  (display-win-message [this])
  (display-lose-message [this])
  (display-tie-message [this]))

