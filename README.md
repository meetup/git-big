# git-big
[![Build Status](https://travis-ci.org/meetup/git-big.svg?branch=master)](https://travis-ci.org/meetup/git-big)
[![](https://images.microbadger.com/badges/version/meetup/git-big.svg)](https://microbadger.com/images/meetup/git-big "Get your own version badge on microbadger.com")
[![](https://images.microbadger.com/badges/image/meetup/git-big.svg)](https://microbadger.com/images/meetup/git-big "Get your own image badge on microbadger.com")

Takes a git log generated via
`git log --pretty=format:'"%H","%ae","%ai"' --numstat --no-merges`
and exports it as newline delimited json objects.

Called it git-bit because big-git sounds
too much like bigot.

## BigQuery Schema

```
repo:STRING,commit:STRING,author:STRING,timestamp:TIMESTAMP,file:STRING,extension:STRING,category:STRING,added:INTEGER,deleted:INTEGER
```

## Usage

```
$ (App) repo_name input_log output_file
```

or with SBT

```
$ sbt "run repo_name input_log output_file"
```
