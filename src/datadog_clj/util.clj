(ns datadog-clj.util
  (:require [clojure.string :refer [lower-case]]))

(def truthy #{"true"})

(defn ->int
  "Converts a string to an integer"
  [s]
  (read-string (re-find #"[+|-]?[0-9]*" s)))

(defn ->float
  "Converts a string to a float"
  [s]
  (read-string (re-find #"[+|-]?[0-9]*\.?[0-9]*" s)))

(defn ->bool
  "Converts a string to a boolean"
  [true?]
  (and (string? true?)
       (truthy (lower-case true?))))
