(ns jasmine-macros)

(defmacro describe [description & body]
  `(~'js/describe ~description (fn [] ~@body)))

(defmacro before-each [& body]
  `(~'js/beforeEach (fn [] ~@body)))

(defmacro it [description & body]
  `(~'js/it ~description (fn [] ~@body)))

(defmacro expect-to-be [actual expected]
  `(-> ~actual
       (~'js/expect)
       (.toBe ~expected)))

(defmacro expect-to-match [actual expected]
  `(-> ~actual
       (~'js/expect)
       (.toMatch ~expected)))

(defmacro expect-to-have-been-called-with [fun & args]
  `(-> ~fun
       (~'js/expect)
       (.toHaveBeenCalledWith ~@args)))

(defmacro affix-to
  ([node id]
   `(.affix ~node ~id))
  ([node id fun text]
   `(-> ~node
        (.affix ~id)
        (~fun ~text)))
  ([node id1 id2 fun text]
   `(-> ~node
        (.affix ~id1)
        (.affix ~id2)
        (~fun ~text))))
