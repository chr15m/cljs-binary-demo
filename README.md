# Single Binary ClojureScript Demo

This repository demonstrates how to compile an [nbb](https://github.com/babashka/nbb) ClojureScript script into a single executable binary.

It uses the techniques described in [nbb/doc/bundle](https://github.com/babashka/nbb/tree/main/doc/bundle).

It compiles `helloworld.cljs` into a single binary called `helloworld`.
This binary can be run on any system with Node 18 or greater installed.
Invoke it with `./helloworld`.

The script is compiled with `nbb bundle` and `@vercel/ncc` which rolls up all dependencies into a single file.
A hashbang for invoking node is added to the top of the script.
You can find the commands for building the single binary in [`package.json`](./package.json).

The `helloworld.cljs` script includes examples of:
- Parsing command-line arguments using `clojure.tools.cli`.
- Calculating Fibonacci sequences.
- Reading file sizes using Node.js `fs` module.
- Running a simple Express web server.
