(ns example.lambda
  (:require [lambada.core :refer [deflambdafn]]
            [clojure.data.json :as json]
            [clojure.java.io :as io]))

(ns example.lambda
  (:require [lambada.core :refer [deflambdafn]]))

(defn do-something
  [event-map]
  (println "This will show up in the lambda log")
  {:status 200
   :body "Hello, World!"})

(deflambdafn example.lambda.MyLambdaFn
  [in out ctx]
  (let [event (json/read (io/reader in))
        response (do-something event)]
    (with-open [w (io/writer out)]
      (json/write response w))))