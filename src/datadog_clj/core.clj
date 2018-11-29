(ns datadog-clj.core
  (:require [datadog-clj.datadog :as dog]
            [datadog-clj.config :as config]))

(defn increment
  "Increment the specified `metric`, include the specified `tags`"
  ([metric] (increment metric {} {}))
  ([metric tags] (increment metric tags {}))
  ([metric tags options] (dog/increment metric tags options)))

(defn decrement
  "Decrement the specified `metric`, include the specified `tags`"
  ([metric] (decrement metric {} {}))
  ([metric tags] (decrement metric tags {}))
  ([metric tags options] (dog/decrement metric tags options)))

(defn gauge
  "Records the `number` for the specified `metric`, include the specified `tags`.  `number` must be of type double or long"
  ([metric number] (gauge metric number {} {}))
  ([metric number tags] (gauge metric number tags {}))
  ([metric number tags options] (dog/gauge metric number tags options)))

(defn execution-time
  "Records the execution `epoch-time` for the specified `metric`, include the specified `tags`"
  ([metric epoch-time] (execution-time metric epoch-time {} {}))
  ([metric epoch-time tags] (execution-time metric epoch-time tags {}))
  ([metric epoch-time tags options] (dog/execution-time metric epoch-time tags options)))

(defn histogram
  "Records the `number` for the specified `metric`, include the specified `tags`. `number` must be of type double or long"
  ([metric number] (histogram metric number {} {}))
  ([metric number tags] (histogram metric number tags {}))
  ([metric number tags options] (dog/histogram metric number tags options)))

(defn unique
  "Counts unique occurances of `value` for the specified `metric`, include the specified `tags`"
  ([metric value] (unique metric value {}))
  ([metric value tags] (dog/unique metric value tags)))

(defn event
  "Records the specified `event`"
  ([name description type] (event name description type {}))
  ([name description type tags] (dog/event name description type tags)))
