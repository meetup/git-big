# git-big

Takes a git log generated via
`git log --pretty=format:'"%H","%ae","%ai"' --numstat --no-merges`
and exports it as newline delimited json objects.

Called it git-bit because big-git sounds
too much like bigot.

## Usage

```
$ (App) repo_name input_log output_file
```

or with SBT

```
$ sbt "run repo_name input_log output_file"
```
