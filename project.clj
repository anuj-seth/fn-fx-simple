(defproject fn-fx-simple "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [fn-fx/fn-fx-openjfx11 "0.5.0-SNAPSHOT"]]
  :main fn-fx-simple.main
  :aot [fn-fx-simple.core]
  :profiles {:uberjar {:aot :all}}
  :java-cmd "/usr/lib/jvm/java-11-openjdk-amd64/bin/java")
