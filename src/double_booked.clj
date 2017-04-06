(ns double-booked)

(defn overlaps?
  "determines whether 2 event maps overlap or not"
  [{s1 :start e1 :end} {s2 :start e2 :end}]
  (let [st-overlap (compare s1 e2)
        ed-overlap (compare e1 s2)]
    ;; without using clj-time or another library, we have to use
    ;; a raw comparison instead of > < =.
    ;; clojure.core/compare returns:
    ;; -1 for less than
    ;; 0 for equal
    ;; 1 for greater than
    ;;
    ;; This is the same as expressing:
    ;; (start event 1 <= end event 2) and (end event 1 >= start event 2)
    (and (or (= st-overlap 0) (= st-overlap -1))
         (or (= ed-overlap 0) (= ed-overlap 1)))))

(defn overlapping-seq
  "return a seq of all event pairs in coll that overlap.
  coll is a sequence of maps containing the keys :start and :ends
  in which the value of each is a java.util.Date"
  [coll]
  (loop [x   (first coll)
         xs  (rest coll)
         acc []]
    (if (seq xs)
      (let [overlapping (filter (partial overlaps? x) xs)
            pairs (map (partial vector x) overlapping)]
        (recur (first xs)
               (rest xs)
               (concat acc pairs)))
      (into [] acc))))
