# Build Your Own WC Tool

A Clojure implementation of the Unix [wc
utility](https://en.wikipedia.org/wiki/Wc_(Unix)), as described in [Coding
Challenges](https://codingchallenges.fyi/challenges/challenge-wc).

## Usage

Run with the [Clojure CLI](https://clojure.org/reference/clojure_cli).

| Feature | Flag | Example Command | Example Output |
|---------|------|----------------|----------------|
| Byte count | `-c` | `clj -M -m wc.core -c file.txt` | `342190 resources/test.txt` |
| Line count | `-l` | `clj -M -m wc.core -l file.txt` | `7145 resources/test.txt` |
| Word count | `-w` | `clj -M -m wc.core -w file.txt` | `58164 resources/test.txt` |
| Character count | `-m` | `clj -M -m wc.core -m file.txt` | `339292 resources/test.txt` |
| Default (all) | *none* | `clj -M -m wc.core file.txt` | `342190 7145 58164 resources/test.txt` | 
| Piped input | any | `cat file.txt \| clj -M -m wc.core -l` | `7145` |
