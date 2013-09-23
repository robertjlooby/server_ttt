(ns CSSDisplay
  (:require-macros [hiccups.core :as h])
  (:require [domina :as dom]
            [hiccups.runtime :as hiccupsrt]
            [domina.events :as ev]))

(defn getCell [cellNum]
  (dom/by-id (str "cell" cellNum)))

(defn getButton [cellNum]
  (dom/sel (str "#cell" cellNum " .button")))
