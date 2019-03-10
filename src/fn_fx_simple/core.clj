(ns fn-fx-simple.core
  (:require [clojure.java.io :as io]
            [fn-fx.fx-dom :as dom]
            [fn-fx.diff :refer [component defui render should-update?]]
            [fn-fx.controls :as ui]
            [fn-fx.render-core :as render-core])
  (:gen-class :extends
              javafx.application.Application))

(defn force-exit
  []
  (reify javafx.event.EventHandler
    (handle [this event]
      (shutdown-agents)
      (javafx.application.Platform/exit))))

(defn button-press
  [data-state]
  (send data-state
        assoc
        :text "Said Hello"
        :button-disabled? true)
  (send data-state
        (fn [the-data]
          (Thread/sleep 5000)
          (assoc the-data
                 :text "Say 'Hello World'"
                 :button-disabled? false))))

(defui TheButton
  (render
   [this args]
   (ui/stack-pane :children [(ui/button :text (:text args)
                                        :disable (:button-disabled? args)
                                        :on-action {:event :button-press})])))

(defui TheStage
  (render
   [this args]
   (let  [image-value-tp (ui/image :is (io/input-stream
                                        (io/resource "icon.png")))
          image (render-core/convert-value image-value-tp
                                           javafx.scene.image.Image)]
     (ui/stage :title "Hello World !"
               :on-close-request (force-exit)
               :shown true
               :icons [image]
               :scene (ui/scene :root (the-button args)
                                :width 300
                                :height 250)))))

(defn -start
  [& args]
  (let [data-state (agent {:text "Say 'Hello World'"
                           :button-disabled? false })
        handler-fn (fn [{:keys [event]}]
                     (condp = event
                       :button-press (button-press data-state)))
        ui-state (agent (dom/app
                         (the-stage @data-state)
                         handler-fn))
        update-ui-state (fn [_ _ _ _]
                          (send ui-state (fn [old-ui]
                                           (dom/update-app old-ui
                                                           (the-stage @data-state)))))]
    (add-watch data-state
               :ui
               update-ui-state)))

(defn start-javafx
  [& args]
  (javafx.application.Application/launch fn_fx_simple.core
                                         (into-array String args)))

(comment
  (require 'fn-fx.util.reflect-utils)
  (fn-fx.util.reflect-utils/get-value-ctors javafx.scene.image.Image)

  )
(when *compile-files*
  (javafx.application.Platform/exit))
