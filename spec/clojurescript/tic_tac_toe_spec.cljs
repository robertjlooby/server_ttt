(ns tic-tac-toe-spec
  (:require-macros [jasmine-macros :as jas]))

(jas/describe "tic-tac-toe.reset-board"
  (jas/before-each
    (js/affix "#board"))

  (jas/it "should reset tic-tac-toe.board-state to an empty board"
    (reset! tic-tac-toe.board-state "blahblahblah")
    (tic-tac-toe.reset-board "X")
    (jas/expect-to-be @tic-tac-toe.board-state "_________"))

  (jas/it "should reset tic-tac-toe.buttons-enabled to false"
    (reset! tic-tac-toe.buttons-enabled true)
    (tic-tac-toe.reset-board "X")
    (jas/expect-to-be @tic-tac-toe.buttons-enabled false))

  (jas/it "should pass event handler to display.reset-board which calls make-move"
    (let [mm-args (atom nil)
          d (mock-display/mock-css-display.)
          _ (reset! tic-tac-toe.display d)]
      (with-redefs
        [tic-tac-toe/make-move (fn [a b c] (reset! mm-args [a b c]))]
        (tic-tac-toe.reset-board "X")
        (jas/expect-to-equal (first @mock-display.last-args) :reset-board)
        ((last @mock-display.last-args) 0)
        (jas/expect-to-equal @mm-args nil)
        (reset! tic-tac-toe.buttons-enabled true)
        ((last @mock-display.last-args) 0)
        (jas/expect-to-equal @mm-args ["X", "_________", 0]))))
)

(jas/describe "tic-tac-toe.display-form"
  (jas/it "should pass an event handler to call initialize-game to display.display-form"
    (let [ig-args (atom nil)
          d (mock-display/mock-css-display.)
          _ (reset! tic-tac-toe.display d)]
      (with-redefs
        [tic-tac-toe/initialize-game (fn [] (reset! ig-args :called))]
        (tic-tac-toe/display-form)
        (jas/expect-to-equal @ig-args nil)
        ((last @mock-display.last-args))
        (jas/expect-to-equal @ig-args :called)))))

(jas/describe "tic-tac-toe.disable/enable buttons"
  (jas/before-each
    (reset! tic-tac-toe.buttons-enabled false))

  (jas/it "should enable buttons"
    (tic-tac-toe.enable-buttons)
    (jas/expect-to-be @tic-tac-toe.buttons-enabled true))

  (jas/it "should disable buttons"
    (tic-tac-toe.enable-buttons)
    (tic-tac-toe.disable-buttons)
    (jas/expect-to-be @tic-tac-toe.buttons-enabled false))
)

(jas/describe "tic-tac-toe.get-new-board-state"
  (jas/it "should replace first element"
    (jas/expect-to-be (tic-tac-toe/get-new-board-state "X" "__O______" 0) "X_O______"))

  (jas/it "should replace an element in the middle"
    (jas/expect-to-be (tic-tac-toe/get-new-board-state "O" "X_OX_____" 6) "X_OX__O__"))

  (jas/it "should replace an element at the end"
    (jas/expect-to-be (tic-tac-toe/get-new-board-state "X" "X_OX__O__" 8) "X_OX__O_X"))
)
