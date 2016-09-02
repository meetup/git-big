package com.meetup.gitbig.parser

import com.meetup.gitbig.Commit
import com.meetup.gitbig.util.Classifier
import com.meetup.logging.Logging

trait CommitParser {
  def parse(c: String): Option[Commit]
}

object CommitParser extends CommitParser with Logging {

  def parse(commit: String): Option[Commit] = {
    val parsedCommit = HeaderParser.parse(commit).map { header =>
      val changes = ChangeParser.parse(commit, Some(Classifier))

      if (changes.nonEmpty) Some(Commit(header, changes))
      else None
    }.orElse {
      log.error("Failed to parse header.")
      None
    }

    parsedCommit.flatten
  }
}
