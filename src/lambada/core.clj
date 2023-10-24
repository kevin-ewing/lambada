(ns lambada.core
  (:import [com.amazonaws.services.lambda.runtime RequestStreamHandler])
  (:require [clojure.tools.macro :refer [declare]]))

(defmacro deflambdafn
  "Create a named class that can be invoked as a AWS Lambda function."
  [name args & body]
  (assert (= (count args) 3) "lambda function must have exactly three args")
  (let [prefix (gensym)
        handleRequestMethod (symbol (str prefix "handleRequest"))]
    `(do
       (declare ~@args)
       (gen-class
        :name ~name
        :prefix ~prefix
        :implements [com.amazonaws.services.lambda.runtime.RequestStreamHandler])
       (defn ~handleRequestMethod
         ~(into ['this] args)
         ~@body))))