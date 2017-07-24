package com.meetup.gitbig

import java.io.BufferedWriter

import com.meetup.gitbig.parser.{ChangeParser, HeaderParser}
import com.meetup.gitbig.util.CommitRenderer

import scala.io.Source

object GitBig {

  def apply(source: Source, output: BufferedWriter, repo: String): Unit = {
    val writer = { commit: CommitChange =>
      output.write(CommitRenderer.json(repo, commit))
      output.newLine()
    }

    val lastCommit = source.getLines().foldLeft[Option[Header]](None) { (headerOpt, l) =>
      l.trim match {
        case "" =>
          None

        case line @ HeaderParser.pattern(_, _, _) =>
          HeaderParser.parse(line)

        case line @ ChangeParser.pattern(_, _, _) =>
          for {
            header <- headerOpt
            change <- ChangeParser.parse(line)
          } {
            writer(CommitChange(header, change))
          }

          headerOpt

        case _ => headerOpt
      }
    }
  }

}
