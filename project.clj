(def version "0.1.1")

(defproject org.clojars.kevin-ewing/lambada version
  :description "The messy parts for running Clojure on AWS Lambda."
  :url "https://github.com/kevin-ewing/lambada"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [com.amazonaws/aws-lambda-java-core "1.2.3"]]
  :scm {:name "git"
        :url "https://github.com/kevin-ewing/lambada"}
  :source-paths ["src"]
  :deploy-repositories [["clojars" {:url "https://clojars.org/org.clojars.kevin-ewing/lambada"
                                    :sign-releases false}]])