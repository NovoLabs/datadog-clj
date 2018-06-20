(defproject datadog-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://github.com/novolabs/datadog-clj"
  :license {:name "BSD-3-Clause"
            :url "https://opensource.org/licenses/BSD-3-Clause"}
  :min-lein-version "2.0.0"
  :plugins [[cider/cider-nrepl "0.17.0"]
            [lein-codox "0.10.4"]]
  :dependencies [[com.datadoghq/java-dogstatsd-client "2.5"]
                 [environ "1.1.0"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.nrepl "0.2.13"]])
