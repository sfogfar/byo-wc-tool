(ns wc.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [wc.core :refer [default with-option]]
            [clojure.string :as str]))

(def test-file-path "resources/test.txt")
(def test-content (delay (slurp test-file-path)))

(defn extract-number [output]
  (-> output
      str/trim
      (str/split #"\s+") 
      first 
      Integer/parseInt))

(defn extract-numbers [output]
  (mapv #(Integer/parseInt %)
        (take 3 (str/split (str/trim output) #"\s+"))))

(deftest wc-example-based-tests
  (let [content @test-content]

    (testing "byte count (-c)"
      (let [output (with-out-str (with-option content "-c" test-file-path))]
        (is (= 342190 (extract-number output)))))

    (testing "line count (-l)"
      (let [output (with-out-str (with-option content "-l" test-file-path))]
        (is (= 7145 (extract-number output)))))

    (testing "word count (-w)"
      (let [output (with-out-str (with-option content "-w" test-file-path))]
        (is (= 58164 (extract-number output)))))

    (testing "character count (-m)"
      (let [output (with-out-str (with-option content "-m" test-file-path))]
        (is (= 339292 (extract-number output)))))

    (testing "default output (bytes, lines, words)"
      (let [output (with-out-str (default content test-file-path))]
        (is (= [342190 7145 58164] (extract-numbers output)))))

    (testing "stdin with line count (-l)"
      (let [output (with-out-str (with-option content "-l"))]
        (is (= 7145 (extract-number output)))))))
