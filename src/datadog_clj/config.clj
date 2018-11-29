(ns datadog-clj.config
  (:require [environ.core :refer [env]]
            [trptcolin.versioneer.core :as version]
            [datadog-clj.util :refer [->int ->bool ->float]]
            [clojure.pprint :refer [pprint]]))

(def prefix (env :datadog-prefix))
(def host (or (env :datadog-agent-host (env :datadog-host))))
(def port (->int (or (env :datadog-agent-port) (env :datadog-port) "8125")))
(def constant-tags (or (env :datadog-constant-tags) ""))
(def include-version? (->bool (or (env :datadog-include-version) "false")))
(def sample-rate? (not (nil? (env :datadog-sample-rate))))
(def sample-rate (->float (or (env :datadog-sample-rate) "0.5")))
(def include-version? (->bool (or (env :datadog-include-version) "false")))
(def group-id (env :datadog-version-group-id))
(def artifact-id (env :datadog-version-artifact-id))

(defn pprint-config
  "Prints configuration options using `pprint`"
  []
  (pprint {:datadog-prefix prefix
           :datadog-agent-host host
           :datadog-agent-port port
           :datadog-constant-tags constant-tags
           :datadog-sample-rate sample-rate
           :datadog-include-version include-version?
           :datadog-version-group-id group-id
           :datadog-version-artifact-id artifact-id}))
