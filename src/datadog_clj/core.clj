(ns datadog-clj.core
  (:require [environ.core :refer [env]])
  (:import com.timgroup.statsd.NonBlockingStatsDClient))

(def prefix
  "The prefix to all metrics created by the current application.
   See https://docs.datadoghq.com/developers/metrics/#naming-metrics for more on
   datadog naming conventions."
  (env :datadog-prefix))
(def host (env :datadog-host))
(def port (env :datadog-port))
(def constant-tags (env :datadog-constant-tags))

(defn- make-constant-tags-array
  [x]
  (if (clojure.string/blank? x)
    (into-array [""])
    (into-array (clojure.string/split x #","))))

(def statsd (delay (NonBlockingStatsDClient. prefix
                                             host
                                             port
                                             (make-constant-tags-array constant-tags))))
