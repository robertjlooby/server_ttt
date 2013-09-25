(ns css-display
  (:require-macros [hiccups.core :as h])
  (:require [domina :as dom]
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

(defn reset-board [fun]
  (dom/set-html! (dom/by-id "board") board-html)
  (doall
    (for [cell (range 0 9)]
      (-> (dom/by-id (str "cell" cell))
          (ev/listen! :click (fn [evt] (fun cell)))))))

(defn display-form [fun]
  (dom/append! (dom/by-id "board") form-html)
  (ev/listen! (dom/by-id "newGameButton") :click fun))

(defn get-cell [cell-num]
  (dom/by-id (str "cell" cell-num)))

(defn get-button [cell-num]
  (dom/single-node
    (css/sel (str "#cell" cell-num " .button"))))

(defn make-move [marker cell-num]
  (-> (get-cell cell-num)
      (dom/set-html! marker)))

(defn get-marker []
  (-> (css/sel "input[name=\"marker\"]:checked")
      dom/value))

(defn get-move []
  (-> (css/sel "input[name=\"move\"]:checked")
      dom/value))

(defn display-message [msg]
  (dom/prepend! (dom/by-id "board") (h/html [:h1 msg])))

(defn display-win-message []
  (display-message "You Win!"))

(defn display-lose-message []
  (display-message "You Lose!"))

(defn display-tie-message []
  (display-message "It was a Tie!"))
