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
