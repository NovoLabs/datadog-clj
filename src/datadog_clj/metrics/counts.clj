(ns datadog-clj.metrics.counts
  (:require [datadog-clj.core :refer [statsd]]))

(defn incre
  "Takes `metric`, `tags`, increments that metric."
  [metric tags]
  (.incrementCounter @statsd metric (if (empty? tags)
                                      (into-array [""])
                                      (into-array tags))))

(defn decre
  "Takes `metric`, `tags`, decrements that metric."
  [metric tags]
  (.decrementCounter @statsd metric (if (empty? tags)
                                      (into-array [""])
                                      (into-array tags))))
