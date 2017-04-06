(ns double-booked-test
  (:require [clojure.test :refer [deftest is]]
            double-booked))

(defn date-formatter
  "Creates date from string. Resolution to the second"
  [s]
  (.parse (java.text.SimpleDateFormat. "yyyy-MM-dd'T'HH:mm:ssZ") s))

(def e7to8    {:start (date-formatter "2017-04-06T07:00:00+0000")
               :name "7 to 8"
              :end (date-formatter "2017-04-06T08:00:00+0000")})

(def e8to9    {:start (date-formatter "2017-04-06T08:00:00+0000")
               :name "8 to 9"
               :end (date-formatter "2017-04-06T09:00:00+0000")})

(def happyhour {:start (date-formatter "2017-04-06T17:00:00+0000")
                :name "happy hour"
                 :end (date-formatter "2017-04-06T18:00:00+0000")})

(def e801to11 {:start (date-formatter "2017-04-06T08:01:00+0000")
               :name "8:01 to 11"
              :end (date-formatter "2017-04-06T011:00:00+0000")})

(def e7to5    {:start (date-formatter "2017-04-06T07:00:00+0000")
               :name "7 to 5"
               :end (date-formatter "2017-04-06T17:00:00+0000")})

(def allday {:start (date-formatter "2017-04-06T00:00:00+0000")
             :name "all day"
             :end (date-formatter "2017-04-07T00:00:00+0000")})

(deftest not-overlapping
  (is (= false (double-booked/overlaps? e7to8 e801to11))))

(deftest overlapping
  (is (= true (double-booked/overlaps? e801to11 e7to5))))

(deftest boundary-overlaps
  (is (= true (double-booked/overlaps? e8to9 e7to8))))

(deftest one-event-is-empty
  (is (= [] (double-booked/overlapping-seq [allday]))))

(deftest no-overlapping-events
  (is (= [] (double-booked/overlapping-seq [e7to8 happyhour e801to11]))))

(deftest simple-overlap
  (is (= [[e7to8 allday]] (double-booked/overlapping-seq [e7to8 allday]))))

(deftest many-overlap
  (is (= [[e7to5 e7to8] [e7to5 e8to9] [e7to5 e801to11] [e7to5 happyhour]
          [e7to5 allday] [e7to8 e8to9] [e7to8 allday] [e8to9 e801to11]
          [e8to9 allday] [e801to11 allday] [happyhour allday]]
         (double-booked/overlapping-seq
          [e7to5 e7to8 e8to9 e801to11 happyhour allday]))))
