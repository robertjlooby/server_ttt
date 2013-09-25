(ns CSSDisplay
  (:require-macros [hiccups.core :as h])
  (:require [domina :as dom]
            [domina.css :as css]
            [hiccups.runtime :as hiccupsrt]
            [domina.events :as ev]))

(def boardHtml
  (->> (map #(h/html [(str "div#row" %) 
                       (for [col (range 0 3)]
                         (let [cell (+ (* % 3) col)]
                           [(str "div#cell" cell) [:button.button (str cell)]]))])
             (range 0 3))
       (apply str)))

(def formHtml
  (h/html [:div#newGameForm [:div "Marker:"
                                  [:input {:name "marker" :type "radio" :value "X" :checked true} "X"]
                                  [:input {:name "marker" :type "radio" :value "O"} "O"]]
                            [:div "Move:"
                                  [:input {:name "move" :type "radio" :value "0" :checked true} "First"]
                                  [:input {:name "move" :type "radio" :value "1"} "Second"]]
                            [:button#newGameButton {:type "button"} "Play!"]]))

(defn resetBoard [fun]
  (dom/set-html! (dom/by-id "board") boardHtml)
  (doall
    (for [cell (range 0 9)]
      (-> (dom/by-id (str "cell" cell))
          (ev/listen! :click (fn [evt] (fun cell)))))))

(defn displayForm [fun]
  (dom/append! (dom/by-id "board") formHtml)
  (ev/listen! (dom/by-id "newGameButton") :click fun))

(defn getCell [cellNum]
  (dom/by-id (str "cell" cellNum)))

(defn getButton [cellNum]
  (dom/single-node
    (css/sel (str "#cell" cellNum " .button"))))

(defn makeMove [marker cellNum]
  (-> (getCell cellNum)
      (dom/set-html! marker)))

(defn getMarker []
  (-> (css/sel "input[name=\"marker\"]:checked")
      dom/value))

(defn getMove []
  (-> (css/sel "input[name=\"move\"]:checked")
      dom/value))

(defn displayMessage [msg]
  (dom/prepend! (dom/by-id "board") (h/html [:h1 msg])))

(defn displayWinMessage []
  (displayMessage "You Win!"))

(defn displayLoseMessage []
  (displayMessage "You Lose!"))

(defn displayTieMessage []
  (displayMessage "It was a Tie!"))
