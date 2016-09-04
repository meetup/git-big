package com.meetup.gitbig.util

import java.time.format.DateTimeFormatter

import com.meetup.gitbig.Commit
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._

object CommitRenderer {
  private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  def json(repo: String, commit: Commit): String = {
    commit.changes.map { change =>
      compact(render(
        ("repo" -> repo) ~
          ("commit" -> commit.header.commit) ~
          ("author" -> commit.header.author) ~
          ("timestamp" -> commit.header.dateTime.format(dateFormatter)) ~
          ("file" -> change.fileName) ~
          ("extension" -> change.extension) ~
          ("category" -> change.category) ~
          ("added" -> change.added) ~
          ("deleted" -> change.deleted)))
    }.mkString("\n")
  }

}
