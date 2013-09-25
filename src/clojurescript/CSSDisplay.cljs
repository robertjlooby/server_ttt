(ns CSSDisplay
  (:require-macros [hiccups.core :as h])
  (:require [domina :as dom]
            [domina.css :as css]
            [hiccups.runtime :as hiccupsrt]
            [domina.events :as ev]))
(defn resetBoard [fun]
  (->> (map #(h/html [(str "div#row" %) 
                       (for [col (range 0 3)]
                         (let [cell (+ (* % 3) col)]
                           [(str "div#cell" cell) [:button.button (str cell)]]))])
             (range 0 3))
       (apply str)
       (dom/set-html! (dom/by-id "board")))
  (doall
    (for [cell (range 0 9)]
      (-> (dom/by-id (str "cell" cell))
          (ev/listen! :click (fn [evt] (fun cell)))))))

(defn getCell [cellNum]
  (dom/by-id (str "cell" cellNum)))

(defn getButton [cellNum]
  (dom/single-node
    (css/sel (str "#cell" cellNum " .button"))))
