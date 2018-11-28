(ns datadog-clj.util
  (:require [clojure.string :refer [lower-case]]))

(def truthy #{"true"})

(defn ->int
  "Converts a string to an integer"
  [s]
  (java.lang.Integer/valueOf (re-find #"\d+" s) 10))

(defn ->bool
  "Converts a string to a boolean"
  [true?]
  (and (string? true?)
       (truthy (lower-case true?))))
