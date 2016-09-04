package com.meetup.gitbig.parser

import com.meetup.gitbig.Commit

import scala.io.Source

class LogParser(commitParser: CommitParser = CommitParser) {

  def parse(source: Source, writer: Commit => Unit): Unit = {
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
