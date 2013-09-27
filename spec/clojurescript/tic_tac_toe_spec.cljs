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
          rb-args (atom nil)]
      (with-redefs
        [tic-tac-toe/make-move (fn [a b c] (reset! mm-args [a b c]))]
        (tic-tac-toe.reset-board "X")
        
        (js/alert (str @rb-args)))))
)
