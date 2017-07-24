package com.meetup.gitbig.util

import java.time.format.DateTimeFormatter

import com.meetup.gitbig.CommitChange
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._

object CommitRenderer {
  private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  def json(repo: String, commit: CommitChange): String = {
    compact(render(
      ("repo" -> repo) ~
        ("commit" -> commit.header.commit) ~
        ("author" -> commit.header.author) ~
        ("timestamp" -> commit.header.dateTime.format(dateFormatter)) ~
        ("file" -> commit.change.fileName) ~
        ("extension" -> commit.change.extension) ~
        ("category" -> commit.change.category) ~
        ("added" -> commit.change.added) ~
        ("deleted" -> commit.change.deleted)))
  }

}
