(ns fn-fx-simple.main
  (:require [fn-fx-simple.core :as core])
  (:gen-class))

(defn -main
  [& args]
  (core/start-javafx))
