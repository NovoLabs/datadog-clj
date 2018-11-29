(defproject novolabs/datadog-clj "0.1.3"
  :description "A Clojure wrapper around the official Datadog Java client."

  :url "https://github.com/novolabs/datadog-clj"

  :license {:name "BSD-3-Clause"
            :url "https://opensource.org/licenses/BSD-3-Clause"}

  :min-lein-version "2.0.0"

  :plugins [[cider/cider-nrepl "0.18.0"]
            [lein-environ "1.1.0"]
            [lein-codox "0.10.4"]]

  :dependencies [[com.datadoghq/java-dogstatsd-client "2.6.1"]
                 [trptcolin/versioneer "0.2.0"]
                 [environ "1.1.0"]
                 [org.clojure/clojure "1.9.0"]]

  :profiles {:dev  {:env
                     {;; This value is prepended to all metric names
                      :datadog-prefix "datadogclj"

                      ;; The host that the DataDog agent is running on.
                      ;; This should almost always be `localhost`
                      :datadog-agent-host "localhost"
                      
                      ;; The port that the DataDog agent is running on
                      :datadog-agent-port "8125"
                      
                      ;; comma-seprated list of default tags that will be
                      ;; added to all metrics. `environment` is the most common,
                      ;; allowing you to partition your metrics by environment
                      :datadog-constant-tags "environment:dev"

                      ;; Changes this to a value between 0.0 and 1.0 to adjust
                      ;; the sampling rate of DataDog
                      :datadog-sample-rate "1.0"
                      
                      ;; `true` if you want version `version` tag included as
                      ;; part of the contstant tags
                      :datadog-include-version "true"

                      ;; This should be the group id of your project
                      :datadog-version-group-id "novolabs"
                      
                      ;; This should be the artifact id of your project
                      :datadog-version-artifact-id "datadog-clj"}}})
