(ns helloworld
  (:require
    [nbb.core :refer [*file*]]
    [clojure.tools.cli :as cli]
    ["fs" :as fs]
    ["express$default" :as express]))

(defn fib-seq []
  (map first (iterate (fn [[a b]] [b (+ a b)]) [0 1])))

(defn fib-up-to [n]
  (take-while #(<= % n) (fib-seq)))

(defn print-usage [summary]
  (print "Program options:")
  (print summary))

(defn start-server []
  (let [app (express)
        port 8000]
    (.get app "/" (fn [_req res] (.send res "Hello world"))) ; Simple route
    (.listen app port (fn [] (print (str "Server listening on port " port)))))) ; Start server

(def cli-options
  [["-f" "--fib N" "Calculate Fibonacci sequence up to N."
    :parse-fn #(js/parseInt %)
    :validate [#(< 0 %) "Must be a positive integer"]]
   ["-s" "--filesize FILE" "Show the size of FILE in bytes."
    :parse-fn #(str %)
    :validate [#(string? %) "Path to FILE must be a string."]]
   ["-S" "--serve" "Start a simple web server."] ; Added --serve option
   ["-h" "--help"]])

(defn -main [& args]
  (let [{:keys [options _arguments errors summary]} (cli/parse-opts args cli-options)]
    (cond
      errors
      (doseq [e errors]
        (print e))

      (:help options)
      (print-usage summary)

      (:serve options)
      (start-server)

      (:fib options)
      (print
        (str "Fibonacci sequence up to "
             (:fib options) ": "
             (fib-up-to (:fib options))))

      (:filesize options)
      (print (-> (fs/readFileSync (:filesize options)) .-length))

      :else
      (print-usage summary))))

(when
  (= *file* (nbb.core/invoked-file))
  (apply -main (not-empty (js->clj (.slice js/process.argv 2)))))
