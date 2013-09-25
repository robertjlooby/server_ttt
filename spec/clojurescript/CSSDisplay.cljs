(ns CSSDisplaySpec
  (:require-macros [hiccups.core :as h]
                   [JasmineMacros :as jas])
  (:require [domina :as dom]
            [hiccups.runtime :as hiccupsrt]
            [domina.events :as ev]))

(jas/describe "CSSDisplay.resetBoard"
  (jas/beforeEach
    (let [board (js/affix "#board")]
      (jas/affixTo board "#newGameForm" dom/set-text! "a form")))

  (jas/it "should reset the contents of #board"
    (CSSDisplay.resetBoard #(str %))
    (jas/expectToMatch (dom/html (dom/by-id "board"))
                       "class=\"button\">0<")
    (jas/expectToMatch (dom/html (dom/by-id "board"))
                       "<div id=\"row2\">")
    (jas/expectToMatch (dom/html (dom/by-id "board"))
                       "<div id=\"cell8\">"))

  (jas/it "should remove form"
    (jas/expectToBe (dom/text (dom/by-id "newGameForm")) "a form")
    (CSSDisplay.resetBoard #(str %))
    (jas/expectToBe (dom/by-id "newGameForm") nil))

  (jas/it "should attach the given event handler to each button"
    (let [fun (.createSpy js/jasmine "fun")]
      (CSSDisplay.resetBoard fun)
      (doall
        (for [c (range 0 9)]
          (ev/dispatch! (dom/by-id (str "cell" c)) :click {})))
      (jas/expectToBe (.-length (.-calls fun)) 9)
      (doall
        (for [c (range 0 9)]
          (jas/expectToHaveBeenCalledWith fun c)))))
)

(jas/describe "CSSDisplay.getCell"
  (jas/beforeEach
    (let [board (js/affix "#board")]
      (jas/affixTo board "#cell0" dom/set-text! "cell 0")
      (jas/affixTo board "#cell3" dom/set-text! "cell 3")))

  (jas/it "should get the given cell by number"
    (jas/expectToBe (dom/text (CSSDisplay.getCell 0)) "cell 0")
    (jas/expectToBe (dom/text (CSSDisplay.getCell 3)) "cell 3"))

  (jas/it "should return null for invalid cell"
    (jas/expectToBe (CSSDisplay.getCell -1) nil))
)

(jas/describe "CSSDisplay.getButton"
  (jas/beforeEach
    (let [board (js/affix "#board")]
      (jas/affixTo board "#cell0" ".button" dom/set-text! "btn 0")
      (jas/affixTo board "#cell3" ".button" dom/set-text! "btn 3")))

  (jas/it "should get the given button by number"
    (jas/expectToBe (dom/text (CSSDisplay.getButton 0)) "btn 0")
    (jas/expectToBe (dom/text (CSSDisplay.getButton 3)) "btn 3"))

  (jas/it "should return null for invalid cell"
    (jas/expectToBe (CSSDisplay.getButton -1) nil))
)

(jas/describe "CSSDisplay.makeMove"
  (jas/beforeEach
    (let [board (js/affix "#board")]
      (jas/affixTo board "#cell0" dom/set-text! "empty cell")
      (jas/affixTo board "#cell5" dom/set-text! "empty cell")))

  (jas/it "should replace cell contents with marker"
    (CSSDisplay.makeMove "X" 0)
    (jas/expectToBe (dom/html (dom/by-id "cell0")) "X")
    (CSSDisplay.makeMove "O" 5)
    (jas/expectToBe (dom/html (dom/by-id "cell5")) "O"))
)

(jas/describe "CSSDisplay.getMarker"
  (jas/beforeEach
    (let [f (js/affix "#newGameForm")]
      (jas/affixTo
        f "input[id=\"x\"][name=\"marker\"][type=\"radio\"][value=\"X\"]")
      (jas/affixTo
        f "input[id=\"o\"][name=\"marker\"][type=\"radio\"][value=\"O\"]")))

  (jas/it "should return the marker value from the form"
    (dom/set-attr! (dom/by-id "x") :checked true)
    (jas/expectToBe (CSSDisplay.getMarker) "X")
    (dom/set-attr! (dom/by-id "o") :checked true)
    (jas/expectToBe (CSSDisplay.getMarker) "O"))
)

(jas/describe "CSSDisplay.getMove"
  (jas/beforeEach
    (let [f (js/affix "#newGameForm")]
      (jas/affixTo
        f "input[id=\"zero\"][name=\"move\"][type=\"radio\"][value=\"0\"]")
      (jas/affixTo
        f "input[id=\"one\"][name=\"move\"][type=\"radio\"][value=\"1\"]")))

  (jas/it "should return the move value from the form"
    (dom/set-attr! (dom/by-id "zero") :checked true)
    (jas/expectToBe (CSSDisplay.getMove) "0")
    (dom/set-attr! (dom/by-id "one") :checked true)
    (jas/expectToBe (CSSDisplay.getMove) "1"))
)
