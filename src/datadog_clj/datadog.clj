(ns datadog-clj.datadog
  (:require [environ.core :refer [env]]
            [datadog-clj.config :as config]
            [clojure.string :as string]
            [trptcolin.versioneer.core :as v])
  (:import (com.timgroup.statsd NonBlockingStatsDClient
                                Event$AlertType
                                Event)))

(defn string->tag-array
  "Creates a tag array from a comma seperated string"
  [tags-str]
  (if (and (string? tags-str) (not (string/blank? tags-str)))
    (into-array (string/split tags-str #","))
    (into-array [""])))

(defn ->string
  "Converts value to a string"
  [v]
  (cond
    (keyword? v) (-> (name v) str)
    (string? v) v
    :else (str v)))

(defn make-tag
  "Given a `k` and `v`, produces a corresponding tag string"
  [k v]
  (str k ":" v))

(defn map->tag-array
  "Creates a tag array from a Clojure map"
  [tag-map]
  (let [ks (->> (keys tag-map) (map ->string))
        vs (->> (vals tag-map) (map ->string))]
    (into-array String (map make-tag ks vs))))

(defn make-version
  "Builds the version string for the calling code, provided `datadog-include-version` is set to `true`, empty string otherwise"
  []
  (if config/include-version?
    (let [version (v/get-version config/group-id config/artifact-id)]
      (if (string/blank? version)
        "GROUP_ID_OR_ARTIFACT_ID_WAS_NOT_FOUND"
        version))))

(defn add-version
  "Adds version tag to `tags-str`"
  [tags-str]
  (if config/include-version?
    (let [version (make-version)
          version-tag (if (string/blank? tags-str) (str "version:" version) (str ",version:" version))] 
      (str tags-str version-tag))))

(def statsd- (delay
              (let [constant-tags (add-version config/constant-tags)]
                (NonBlockingStatsDClient. config/prefix config/host config/port (string->tag-array constant-tags)))))

(defn get-sample-rate
  "Gets the sample rate that should be used for parent call"
  [{:keys [sample-rate]}]
  (cond
    sample-rate sample-rate
    config/sample-rate config/sample-rate
    :else nil))

(defn increment
  "Increments the specified `metric`, include the specified `tags`"
  [metric tags options]
  (let [sample-rate (get-sample-rate options)]
    (if sample-rate
      (.increment @statsd- metric sample-rate (map->tag-array tags))
      (.increment @statsd- metric (map->tag-array tags)))))

(defn decrement
  "Decrements the specified `metric`, include the specified `tags`"
  [metric tags options]
  (let [sample-rate (get-sample-rate options)]
    (if sample-rate
      (.decrement @statsd- metric sample-rate (map->tag-array tags))
      (.decrement @statsd- metric (map->tag-array)))))

(defn gauge
  "Records the `number` for the specified `metric`, include the specified `tags`.  `number` must be of type double or long"
  [metric number tags options]
  (let [sample-rate (get-sample-rate options)]
    (if sample-rate
      (.gauge @statsd- metric number sample-rate (map->tag-array tags))
      (.gauge @statsd- metric number (map->tag-array tags)))))

(defn execution-time
  "Records the execution `epoch-time` for the specified `metric`, include the specified `tags`"
  [metric epoch-time tags options]
  (let [sample-rate (get-sample-rate options)]
    (if sample-rate
      (.time @statsd- metric epoch-time sample-rate (map->tag-array tags))
      (.time @statsd- metric epoch-time (map->tag-array tags)))))

(defn histogram
  "Records the `number` for the specified `metric`, include the specified `tags`.  `number` must be of type double or long"
  [metric number tags options]
  (let [sample-rate (get-sample-rate options)]
    (let [sample-rate (get-sample-rate options)]
      (if sample-rate
        (.histogram @statsd- metric number sample-rate (map->tag-array tags))
        (.histogram @statsd- metric number (map->tag-array tags))))))

(defn unique
  "Counts unique occurances of `value` for the specified `metric`, include the specified `tags`."
  [metric value tags]
  (.recordSetValue @statsd- metric value (map->tag-array tags)))

(defn get-event-type
  "Returns `Event.*` enum value from specified `type` keyword"
  [type]
  (case type
    :success Event$AlertType/SUCCESS
    :info Event$AlertType/INFO
    :warning Event$AlertType/WARNING
    :error Event$AlertType/ERROR
    Event$AlertType/INFO))

(defn build-event
  "Constructs `Event` object from specified parameters"
  [name description type tags]
  (.. Event
      builder
      (withTitle name)
      (withText description)
      (withAlertType (get-event-type type))
      build))

(defn event
  "Records the specified `event`"
  [name description type tags]
  (.recordEvent @statsd- (build-event name description type) (map->tag-array tags)))
