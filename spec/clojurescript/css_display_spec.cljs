(ns css-display-spec
  (:require-macros [hiccups.core :as h]
                   [jasmine-macros :as jas])
  (:require [abstract-display :as dis]
            [domina :as dom]
            [domina.css :as css]
            [hiccups.runtime :as hiccupsrt]
            [domina.events :as ev]))

(jas/describe "css-display.reset-board"
  (jas/before-each
    (let [board (js/affix "#board")]
      (jas/affix-to board "#newGameForm" dom/set-text! "a form")))

  (jas/it "should reset the contents of #board"
    (let [d (display/css-display.)]
      (dis/reset-board d #(str %)))
    (jas/expect-to-match (dom/html (dom/by-id "board"))
                       "class=\"button\">0<")
    (jas/expect-to-match (dom/html (dom/by-id "board"))
                       "<div id=\"row2\">")
    (jas/expect-to-match (dom/html (dom/by-id "board"))
                       "<div id=\"cell8\">"))

  (jas/it "should remove form"
    (jas/expect-to-be (dom/text (dom/by-id "newGameForm")) "a form")
    (let [d (display/css-display.)]
      (dis/reset-board d #(str %)))
    (jas/expect-to-be (dom/by-id "newGameForm") nil))

  (jas/it "should attach the given event handler to each button"
    (let [fun (.createSpy js/jasmine "fun")]
      (let [d (display/css-display.)]
        (dis/reset-board d fun))
      (doseq [c (range 0 9)]
        (ev/dispatch! (dom/by-id (str "cell" c)) :click {}))
      (jas/expect-to-be (.-length (.-calls fun)) 9)
      (doseq [c (range 0 9)]
        (jas/expect-to-have-been-called-with fun c))))
)

(jas/describe "css-display.display-form"
  (jas/before-each
    (let [board (js/affix "#board")]
      (jas/affix-to board "#fakeBoard" dom/set-text! "a board")))

  (jas/it "should display the form"
    (let [d (display/css-display.)]
      (dis/display-form d #()))
    (jas/expect-to-match (dom/html (dom/by-id "newGameForm"))
     "<input checked=\"checked\" name=\"marker\" value=\"X\" type=\"radio\">X")
    (jas/expect-to-match (dom/html (dom/by-id "newGameForm"))
     "<input checked=\"checked\" name=\"move\" value=\"0\" type=\"radio\">First")
    (jas/expect-to-match (dom/html (dom/by-id "newGameForm"))
     "<button id=\"newGameButton\" type=\"button\">Play!</button>"))

  (jas/it "should not remove other board elements"
    (let [d (display/css-display.)]
      (dis/display-form d #()))
    (jas/expect-to-be (dom/text (dom/by-id "fakeBoard")) "a board"))

  (jas/it "should attach the given function to the button"
    (let [fun (.createSpy js/jasmine "fun")]
      (let [d (display/css-display.)]
        (dis/display-form d fun))
      (ev/dispatch! (dom/by-id "newGameButton") :click {})
      (jas/expect-to-be (.-length (.-calls fun)) 1)))
)

(jas/describe "css-display.get-cell"
  (jas/before-each
    (let [board (js/affix "#board")]
      (jas/affix-to board "#cell0" dom/set-text! "cell 0")
      (jas/affix-to board "#cell3" dom/set-text! "cell 3")))

  (jas/it "should get the given cell by number"
    (let [d (display/css-display.)]
      (jas/expect-to-be (dom/text (dis/get-cell d 0)) "cell 0")
      (jas/expect-to-be (dom/text (dis/get-cell d 3)) "cell 3")))

  (jas/it "should return null for invalid cell"
    (let [d (display/css-display.)]
      (jas/expect-to-be (dis/get-cell d -1) nil)))
)

(jas/describe "css-display.get-button"
  (jas/before-each
    (let [board (js/affix "#board")]
      (jas/affix-to board "#cell0" ".button" dom/set-text! "btn 0")
      (jas/affix-to board "#cell3" ".button" dom/set-text! "btn 3")))

  (jas/it "should get the given button by number"
    (let [d (display/css-display.)]
      (jas/expect-to-be (dom/text (dis/get-button d 0)) "btn 0")
      (jas/expect-to-be (dom/text (dis/get-button d 3)) "btn 3")))

  (jas/it "should return null for invalid cell"
    (let [d (display/css-display.)]
      (jas/expect-to-be (dis/get-button d -1) nil)))
)

(jas/describe "css-display.make-move"
  (jas/before-each
    (let [board (js/affix "#board")]
      (jas/affix-to board "#cell0" dom/set-text! "empty cell")
      (jas/affix-to board "#cell5" dom/set-text! "empty cell")))

  (jas/it "should replace cell contents with marker"
    (let [d (display/css-display.)]
      (dis/make-move d "X" 0)
      (jas/expect-to-be (dom/html (dom/by-id "cell0")) "X")
      (dis/make-move d "O" 5)
      (jas/expect-to-be (dom/html (dom/by-id "cell5")) "O")))
)

(jas/describe "css-display.get-marker"
  (jas/before-each
    (let [f (js/affix "#newGameForm")]
      (jas/affix-to
        f "input[id=\"x\"][name=\"marker\"][type=\"radio\"][value=\"X\"]")
      (jas/affix-to
        f "input[id=\"o\"][name=\"marker\"][type=\"radio\"][value=\"O\"]")))

  (jas/it "should return the marker value from the form"
    (let [d (display/css-display.)]
      (dom/set-attr! (dom/by-id "x") :checked true)
      (jas/expect-to-be (dis/get-marker d) "X")
      (dom/set-attr! (dom/by-id "o") :checked true)
      (jas/expect-to-be (dis/get-marker d) "O")))
)

(jas/describe "css-display.get-move"
  (jas/before-each
    (let [f (js/affix "#newGameForm")]
      (jas/affix-to
        f "input[id=\"zero\"][name=\"move\"][type=\"radio\"][value=\"0\"]")
      (jas/affix-to
        f "input[id=\"one\"][name=\"move\"][type=\"radio\"][value=\"1\"]")))

  (jas/it "should return the move value from the form"
    (let [d (display/css-display.)]
      (dom/set-attr! (dom/by-id "zero") :checked true)
      (jas/expect-to-be (dis/get-move d) "0")
      (dom/set-attr! (dom/by-id "one") :checked true)
      (jas/expect-to-be (dis/get-move d) "1")))
)

(jas/describe "css-display end of game messages"
  (jas/before-each
    (js/affix "#board"))

  (jas/it "should display a win message"
    (let [d (display/css-display.)]
      (dis/display-win-message d))
    (jas/expect-to-match (dom/text (css/sel "#board h1")) "Win"))

  (jas/it "should display a lose message"
    (let [d (display/css-display.)]
      (dis/display-lose-message d))
    (jas/expect-to-match (dom/text (css/sel "#board h1")) "Lose"))

  (jas/it "should display a tie message"
    (let [d (display/css-display.)]
      (dis/display-tie-message d))
    (jas/expect-to-match (dom/text (css/sel "#board h1")) "Tie"))
)
