(ns JasmineMacros)

(defmacro describe [description & body]
  `(~'js/describe ~description (fn [] ~@body)))

(defmacro beforeEach [& body]
  `(~'js/beforeEach (fn [] ~@body)))

(defmacro it [description & body]
  `(~'js/it ~description (fn [] ~@body)))

(defmacro expectToBe [actual expected]
  `(-> ~actual
       (~'js/expect)
       (.toBe ~expected)))

(defmacro expectToMatch [actual expected]
  `(-> ~actual
       (~'js/expect)
       (.toMatch ~expected)))

(defmacro expectToHaveBeenCalledWith [fun arg]
  `(-> ~fun
       (~'js/expect)
       (.toHaveBeenCalledWith ~arg)))

(defmacro affixTo
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
