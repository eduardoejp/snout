
snout
=====

`snout` is a library for doing client-side route-matching (like Clout's) in ClojureScript.

Dependency
----------

Simply add this to your leiningen deps: `[snout "0.1.0"]`

Documentation
-------------

The documentation can be found here: http://eduardoejp.github.com/snout/

Structure
---------

Currently the library is divided into 2 namespaces:
`core`: It's where the history object resides, along with the matching functionality and fns for getting and setting the current token (i.e. URL fragment identifier).
`macros`: It's where the defroute macro resides.

Example
-------

	(ns my.ns
	  (:require [snout.core :as snout])
	  (:use-macros [snout.macros :only [defroute]]))
	
	; This route would match /my/route/clojure?arg2=clj&arg3=cljs
	(defroute "/my/route/:arg1" [arg1 arg2 arg3]
	  (js/alert (str "It works!: " (pr-str arg1 arg2 arg3))))
	
	; Let's test it!
	(snout/set-token! "/my/route/clojure?arg2=clj&arg3=cljs")
	
	; And we can also get the current token...
	(def tok (snout/get-token))

