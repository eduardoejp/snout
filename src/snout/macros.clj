
(ns snout.macros)

; <Constants>
(def ^:private +splat+ #"\*")
(def ^:private +literal+ #"/\w*")
(def ^:private +keyword+  #"/:[\w\-]+")
(def ^:private +params+ #"\?.*")

; <Utils>
(defn- lex1 [route]
  (if-let [x (and (.startsWith route "/:") (re-find +keyword+ route))]
    [(keyword (.substring x 2)) (.substring route (count x))]
    (if-let [x (re-find +splat+ route)]
      ["*" nil]
      (if-let [x (re-find +literal+ route)]
        [x (.substring route (count x))]
        (if-let [x (re-find +params+ route)]
          ["?" (.substring x 1)])))))

(defn- make-matcher [route]
  (loop [[type route] (lex1 route)
         matcher []]
    (if (nil? type)
      matcher
      (recur (lex1 route)
             (conj matcher type)))))

; <API>
(defmacro defroute
  "Given a route (the same style as a Clout route), an args-vector and a body,
defines a function that will be called when the fragment identifier of the current URL changes and is matched by the route.
The route will be passed :route :params as well as parameters like those of a GET request.
e.g. (defroute \"/my/route/:arg1\" [arg1 arg2 arg3]
       (js/alert (pr-str arg1 arg2 arg3)))"
  [route args & body]
  `(swap! snout.core/routes conj [~(make-matcher route) (fn [{:keys ~args}] ~@body)]))
