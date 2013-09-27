(ns display
  (:require-macros [hiccups.core :as h])
  (:require [abstract-display :as a-d]
            [domina :as dom]
            [domina.css :as css]
            [hiccups.runtime :as hiccupsrt]
            [domina.events :as ev]))

(def board-html
  (->> (map #(h/html [(str "div#row" %) 
                       (for [col (range 0 3)]
                         (let [cell (+ (* % 3) col)]
                           [(str "div#cell" cell) [:button.button (str cell)]]))])
             (range 0 3))
       (apply str)))

(def form-html
  (h/html [:div#newGameForm [:div "Marker:"
                                  [:input {:name "marker" :type "radio" :value "X" :checked true} "X"]
                                  [:input {:name "marker" :type "radio" :value "O"} "O"]]
                            [:div "Move:"
                                  [:input {:name "move" :type "radio" :value "0" :checked true} "First"]
                                  [:input {:name "move" :type "radio" :value "1"} "Second"]]
                            [:button#newGameButton {:type "button"} "Play!"]]))

(defn display-message [msg]
  (dom/prepend! (dom/by-id "board") (h/html [:h1 msg])))


(deftype css-display []
  a-d/display-protocol
  (reset-board [this fun]
    (dom/set-html! (dom/by-id "board") board-html)
    (doseq [cell (range 0 9)]
      (-> (dom/by-id (str "cell" cell))
          (ev/listen! :click (fn [evt] (fun cell))))))

  (display-form [this fun]
    (dom/append! (dom/by-id "board") form-html)
    (ev/listen! (dom/by-id "newGameButton") :click fun))

  (get-cell [this cell-num]
    (dom/by-id (str "cell" cell-num)))

  (get-button [this cell-num]
    (dom/single-node
      (css/sel (str "#cell" cell-num " .button"))))

  (make-move [this marker cell-num]
    (-> (a-d/get-cell this cell-num)
        (dom/set-html! marker)))

  (get-marker [this]
    (-> (css/sel "input[name=\"marker\"]:checked")
        dom/value))

  (get-move [this]
    (-> (css/sel "input[name=\"move\"]:checked")
        dom/value))

  (display-win-message [this]
    (display-message "You Win!"))

  (display-lose-message [this]
    (display-message "You Lose!"))

  (display-tie-message [this]
    (display-message "It was a Tie!"))
)
