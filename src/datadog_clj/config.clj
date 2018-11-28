(ns datadog-clj.config
  (:require [environ.core :refer [env]]
            [trptcolin.versioneer.core :as version]
            [datadog-clj.util :refer [->int]]))

(def prefix (env :datadog-prefix))
(def host (env :datadog-dogstatsd-host))
(def port (->int (or (env :datadog-dogstatsd-port) "8125")))
(def constant-tags (or (env :datadog-constant-tags) ""))
(def include-version? (->bool (or (env :datadog-include-version?) "false")))
(def sample-rate? (not (nil? (env :datadog-sample-rate))))
(def sample-rate (->int (or (env :datadog-sample-rate) 1)))

