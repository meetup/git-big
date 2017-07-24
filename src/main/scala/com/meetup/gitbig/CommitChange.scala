package com.meetup.gitbig

import java.time.LocalDateTime

case class CommitChange(
  header: Header,
  change: Change
)

case class Change(
  fileName: String,
  extension: Option[String],
  category: Option[String],
  added: Int,
  deleted: Int
)

case class Header(
  commit: String,
  author: String,
  dateTime: LocalDateTime
)
