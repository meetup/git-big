package com.meetup.gitbig

import com.meetup.gitbig.parser.CommitParser

import scala.io.Source

class GitBig(commitParser: CommitParser) {

  def out(source: Source, writer: Commit => Unit): Unit = {
    val lastCommit = source.getLines().fold("") { (buffer, l) =>
      l.trim match {
        case "" =>
          if (buffer.trim.nonEmpty) {
            commitParser.parse(buffer.trim).foreach(writer)
          }
          ""
        case x => s"$buffer\n$x"
      }
    }

    if (lastCommit.trim.nonEmpty) {
      commitParser.parse(lastCommit.trim).foreach(writer)
    }
  }
}
