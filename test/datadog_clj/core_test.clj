(ns datadog-clj.core-test
  (:require [clojure.test :refer :all]
            [datadog-clj.core :as core])
  (:import java.util.Arrays))

(deftest make-constant-tags-array-test
  (testing "make-constant-tags-array works for nil"
    (is (true? (Arrays/deepEquals (into-array [""]) (#'core/make-constant-tags-array nil)))))
  (testing "make-constant-tags-array works for empty string"
    (is (true? (Arrays/deepEquals (into-array [""]) (#'core/make-constant-tags-array "")))))
  (testing "make-constant-tags-array works for string with one element"
    (is (true? (Arrays/deepEquals (into-array ["foo:bar"]) (#'core/make-constant-tags-array "foo:bar")))))
  (testing "make-constant-tags-array works for string with two elements"
    (is (true? (Arrays/deepEquals (into-array ["foo:bar" "baz:moo"]) (#'core/make-constant-tags-array "foo:bar,baz:moo"))))))

