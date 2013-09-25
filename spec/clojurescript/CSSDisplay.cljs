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
