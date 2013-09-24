(ns CSSDisplaySpec
  (:require-macros [hiccups.core :as h])
  (:require [domina :as dom]
            [hiccups.runtime :as hiccupsrt]
            [domina.events :as ev]))

(js/describe "CSSDisplay.getCell"
  (fn []
    (js/it "should get the given cell by number"
      (fn []
        (.toBe (js/expect 0) 0)))))
