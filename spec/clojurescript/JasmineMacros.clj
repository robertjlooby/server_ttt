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
