(defproject novolabs/datadog-clj "0.1.2"
  :description "A Clojure wrapper around the official Datadog Java client."
  :url "https://github.com/novolabs/datadog-clj"
  :license {:name "BSD-3-Clause"
            :url "https://opensource.org/licenses/BSD-3-Clause"}
  :min-lein-version "2.0.0"
  :plugins [[cider/cider-nrepl "0.17.0"]
            [lein-environ "1.1.0"]
            [lein-codox "0.10.4"]]
  :dependencies [[com.datadoghq/java-dogstatsd-client "2.6.1"]
                 [trptcolin/versioneer "0.2.0"]
                 [environ "1.1.0"]
                 [org.clojure/clojure "1.8.0"]])
