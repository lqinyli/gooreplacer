(defproject gooreplacer "3.10.0"
   :description "Modify, block URLs & Headers"
   :url "https://github.com/jiacai2050/gooreplacer"
   :license {:name "MIT"
             :url "http://liujiacai.net/license/MIT.html?year=2015"}
   :dependencies [[org.clojure/clojure "1.10.0"]
                  [org.clojure/clojurescript "1.10.597"]
                  [figwheel-sidecar "0.5.14"]
                  [org.clojure/core.match "0.3.0-alpha5"]
                  [org.clojure/data.json "0.2.7"]
                  [alandipert/storage-atom "2.0.1"]

                  ;; ui
                  [antizer "0.3.0"]
                  [cljs-http "0.1.43"]
                  [org.clojure/core.async "0.3.443"]
                  [reagent "0.8.1"]]
   :plugins [[lein-figwheel "0.5.14"]
             [lein-cljsbuild "1.1.7"]
             [lein-doo "0.1.8"]]
   :profiles {
              ;; https://docs.cider.mx/cider/0.23/basics/clojurescript.html#_piggieback
              ;; cider will jack in piggieback automatically
              ;; :dev {:repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}
              :dev-option {:clean-targets ^{:protect false} [:target-path "resources/dev/option/js"]
                           :cljsbuild {:builds [{:id "dev"
                                                 :figwheel true
                                                 :source-paths ["src"]
                                                 :compiler {:output-to "resources/dev/option/js/main.js"
                                                            :source-map true
                                                            :asset-path "js"
                                                            :output-dir "resources/dev/option/js"
                                                            :optimizations :none
                                                            :main gooreplacer.option.core
                                                            :verbose true}}]}
                           :figwheel {:server-port 8080
                                      :http-server-root "dev/option"
                                      :css-dirs ["resources/dev/option/css"]
                                      :server-logfile ".figwheel_option.log"
                                      :repl true}}
              :dev-popup {:clean-targets ^{:protect false} [:target-path "resources/dev/popup/js"]
                          :cljsbuild {:builds [{:id "dev"
                                                :figwheel true
                                                :compiler {:output-to "resources/dev/popup/js/main.js"
                                                           :source-map true
                                                           :asset-path "js"
                                                           :output-dir "resources/dev/popup/js"
                                                           :optimizations :none
                                                           :main gooreplacer.popup.core
                                                           :verbose true}}]}
                          :figwheel {:server-port 8081
                                     :http-server-root "dev/option"
                                     :css-dirs ["resources/dev/option/css"]
                                     :server-logfile ".figwheel_popup.log"
                                     :repl true}}
              :dev-bg {:clean-targets ^{:protect false} [:target-path "resources/dev/background/js"]
                       :figwheel {:server-port 8089
                                  :http-server-root "dev/background"
                                  :server-logfile ".figwheel_bg.log"
                                  :repl true}
                       :cljsbuild {:builds [{:id "dev"
                                             :source-paths ["src"]
                                             :figwheel true
                                             :compiler {:output-to "resources/dev/background/js/main.js"
                                                        :source-map true
                                                        :output-dir "resources/dev/background/js"
                                                        :asset-path "js"
                                                        :main gooreplacer.background
                                                        :optimizations :none
                                                        :verbose true}}]}}
              :release {:clean-targets ^{:protect false} [:target-path
                                                          "resources/release/option/js"
                                                          "resources/release/background/js"]
                        :cljsbuild {:builds [{:id "option"
                                              :compiler {:output-to "resources/release/option/main.js"
                                                         :output-dir "resources/release/option/js"
                                                         :externs ["externs/chrome_extensions.js" "externs/chrome.js"]
                                                         :optimizations :advanced
                                                         :main gooreplacer.option.core}}
                                             {:id "background"
                                              :compiler {:output-to "resources/release/background/main.js"
                                                         :output-dir "resources/release/background/js"
                                                         :externs ["externs/chrome_extensions.js" "externs/chrome.js"]
                                                         :optimizations :advanced
                                                         :main gooreplacer.background}}]}}
              :test {:cljsbuild {:builds [{:id "test"
                                           :compiler {:output-to "out/main.js"
                                                      :main gooreplacer.runner
                                                      :optimizations :none}}]}
                     :doo {:build "test"}}}

   :aliases {"option"   ["with-profile" "dev-option" "do"
                         ["clean"]
                         ["figwheel" "dev"]]
             "bg"       ["with-profile" "dev-bg" "do"
                         ["clean"]
                         ["figwheel" "dev"]]
             "test-and-watch" ["with-profile" "test" "do"
                               ["clean"]
                               ;; First install phantom
                               ["doo" "phantom"]]})
