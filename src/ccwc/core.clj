(ns ccwc.core
  (:gen-class)
  (:require [clojure.string :as str]))

(defn valid-args?
  [args]
  (= (count args) 2))

(defn valid-file?
  [path]
  (.exists (java.io.File. path)))

(defn get-file-size
  [path]
  (.length (java.io.File. path)))

(defn counter
  [path expr]
  (let [text (slurp path)
        items (str/split text expr)]
    (count items)))

(defn -main [& args]
  (if (valid-args? args)
    (let [op (first args)
          path (second args)]
      (try (valid-file? path)
           (catch Exception e (str "Caught exception" e)))
      (let [res (case op
                  "-c" (get-file-size path)
                  "-l" (counter path #"\n")
                  "-w" (counter path #"\s+"))]
        (str res path)))
    (do
      (println "Expected two args")
      (println "Usage: -flag input-file-path")
      (System/exit 1))))

(-main "-c" "resources/test.txt")
(-main "-l" "resources/test.txt")
(-main "-w" "resources/test.txt")
