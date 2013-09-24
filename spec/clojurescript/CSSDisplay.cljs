(ns CSSDisplaySpec
  (:require-macros [hiccups.core :as h]
                   [JasmineMacros :as jas])
  (:require [domina :as dom]
            [hiccups.runtime :as hiccupsrt]
            [domina.events :as ev]))

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
