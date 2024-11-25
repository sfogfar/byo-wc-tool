(ns wc.core
  (:gen-class)
  (:require [clojure.string :as str]))

(defn get-size
  [text]
  (alength (.getBytes text "UTF8")))

(defn split-and-count
  [text split-by]
  (let [split-expressions {:by-line #"\n"
                           :by-word #"\s+"
                           :by-char #""}
        split-expression (get split-expressions split-by)
        items (str/split text split-expression)]
    (count items)))

(defn default
  ([text] (default text ""))
  ([text file-path]
   (let [file-size (get-size text)
         line-count (split-and-count text :by-line)
         word-count (split-and-count text :by-word)]
     (println (str/join " " [file-size line-count word-count file-path])))))

(defn with-option
  ([text option] (with-option text option ""))
  ([text option file-path]
   (let [res (case option
               "-c" (get-size text)
               "-l" (split-and-count text :by-line)
               "-w" (split-and-count text :by-word)
               "-m" (split-and-count text :by-char))]
     (println (str/join " " [res file-path])))))

(defn -main
  [& args]
  (let [[arg1 arg2] args
        args-count (count args)]
    (try
      (cond
        (= 0 args-count)
        (default (slurp *in*))

        (and (= args-count 1) (str/starts-with? arg1 "-"))
        (with-option (slurp *in*) arg1)

        (and (= args-count 1) (not (str/starts-with? arg1 "-")))
        (default (slurp arg1) arg1)

        (and (= args-count 2) (str/starts-with? arg1 "-"))
        (with-option (slurp arg2) arg1 arg2)

        :else
        (throw (Exception. "Invalid arguments")))
      (catch Exception e
        (println "Error:" (.getMessage e))
        (System/exit 1)))))

(comment
  (def test-file-path "resources/test.txt")
  (-main "-c" test-file-path)
  (-main "-l" test-file-path)
  (-main "-w" test-file-path)
  (-main "-m" test-file-path)
  (-main test-file-path))
