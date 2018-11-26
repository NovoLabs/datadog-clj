(ns datadog-clj.metrics
  (:require [environ.core :refer [env]]
            [trptcolin.versioneer.core :as version])
  (:import com.timgroup.statsd.NonBlockingStatsDClient))

(def prefix
  "The prefix to all metrics created by the current application.
   See https://docs.datadoghq.com/developers/metrics/#naming-metrics for more on
   datadog naming conventions."
  (env :datadog-prefix))
(def host (env :datadog-host))
(def port
  (when-not (nil? (env :datadog-port))
    (Integer/parseInt (env :datadog-port))))
(def constant-tags (env :datadog-constant-tags))

(defn- make-constant-tags-array
  [x]
  (if (clojure.string/blank? x)
    (into-array [""])
    (into-array (clojure.string/split x #","))))

(def statsd (delay (do
                     (prn "datadog-prefix[" prefix"], datadog-host[" host "], datadog-port[" port "], datadog-constant-tags[" constant-tags "].")
                     (NonBlockingStatsDClient. prefix
                                             host
                                             port
                                             (make-constant-tags-array constant-tags)))))

(defn make-version
  "Builds the version string the calling code.
   If datadog-foo isn't set, DATADOG_FOO"
  []
  (let [ver (version/get-version (env :datadog-version-group-id)
                                 (env :datadog-version-artifact-id))]
    (if (clojure.string/blank? ver)
      "DATADOG_CLJ_VERSION_REQUESTED_BUT_NO_GROUP_ID_OR_ARTIFACT_ID_WAS_FOUND"
      ver)))

(defn- make-tags
  "Takes `tags`, `opts`, builds datadog tags"
  [tags opts]
  (into-array (merge tags (when (:include-version? opts)
                            (format "version:%s" (make-version))))))

(defn incre
  "Takes `metric`, `tags`, increments that metric."
  [metric tags opts]
  (.incrementCounter @statsd metric (make-tags tags opts)))

(defn decre
  "Takes `metric`, `tags`, decrements that metric."
  [metric tags opts]
  (.decrementCounter @statsd metric (make-tags tags opts)))
