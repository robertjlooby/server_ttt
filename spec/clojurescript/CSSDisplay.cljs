(ns CSSDisplaySpec
  (:require-macros [hiccups.core :as h])
  (:require [domina :as dom]
            [hiccups.runtime :as hiccupsrt]
            [domina.events :as ev]))

(js/describe "CSSDisplay.getCell"
  (fn []
    (js/beforeEach
      (fn []
        (let [board (js/affix "#board")]
          (-> board
              (.affix "#cell0")
              (dom/set-text! "cell 0"))
          (-> board
              (.affix "#cell3")
              (dom/set-text! "cell 3")))))

    (js/it "should get the given cell by number"
      (fn []
        (-> (CSSDisplay.getCell 0)
            (dom/text)
            (js/expect)
            (.toBe "cell 0"))
        (-> (CSSDisplay.getCell 3)
            (dom/text)
            (js/expect)
            (.toBe "cell 3"))))

    (js/it "should return null for invalid cell"
      (fn []
        (-> (CSSDisplay.getCell -1)
            (js/expect)
            (.toBe nil))))
))
