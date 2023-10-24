(defproject my-lambda-project "0.1.0-SNAPSHOT"
  :description "Example lambada project."
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/data.json "2.4.0"]
                 [org.clojars.kevin-ewing/lambada "0.1.1-SNAPSHOT"]]
  :profiles {:uberjar {:aot :all}}
  :uberjar-name "my-lambda-project.jar")
