(ns datadog-clj.metrics-test
  (:require [clojure.test :refer :all]
            [datadog-clj.metrics :as metrics])
  (:import java.util.Arrays))

(deftest make-constant-tags-array-test
  (testing "make-constant-tags-array works for nil"
    (is (true? (Arrays/deepEquals (into-array [""]) (#'metrics/make-constant-tags-array nil)))))
  (testing "make-constant-tags-array works for empty string"
    (is (true? (Arrays/deepEquals (into-array [""]) (#'metrics/make-constant-tags-array "")))))
  (testing "make-constant-tags-array works for string with one element"
    (is (true? (Arrays/deepEquals (into-array ["foo:bar"]) (#'metrics/make-constant-tags-array "foo:bar")))))
  (testing "make-constant-tags-array works for string with two elements"
    (is (true? (Arrays/deepEquals (into-array ["foo:bar" "baz:moo"]) (#'metrics/make-constant-tags-array "foo:bar,baz:moo"))))))
