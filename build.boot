#!/usr/bin/env boot

(deftask uberjar
  []
  (set-env! :source-paths #{"src"}
            :resource-paths #{"resources"}
            :dependencies '[[org.clojure/clojure "1.9.0"]
                            [fn-fx/fn-fx-openjfx11 "0.5.0-SNAPSHOT"]])
  (task-options!
   pom {:project 'fn_fx_simple :version "0.1.0"}
   aot {:all true}
   jar {:main 'fn_fx_simple.main :file "fn_fx_simple.jar"})
  (comp (aot)
        (pom)
        (uber)
        (jar)
        (target)))

