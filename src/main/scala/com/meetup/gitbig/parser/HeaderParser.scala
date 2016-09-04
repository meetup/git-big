package com.meetup.gitbig.parser

import com.meetup.gitbig.Header
import com.meetup.logging.Logging

trait HeaderParser {
  def parse(logEntry: String, dateTimeParser: DateTimeParser = DateTimeParser): Option[Header]
}

object HeaderParser extends HeaderParser with Logging {
  val pattern = """\"(.+)\",\"(.+)\",\"(.+)\"""".r

  def parse(
    logEntry: String,
    dateTimeParser: DateTimeParser = DateTimeParser
  ): Option[Header] = {
    val commitHeaders = pattern.findAllIn(logEntry).toList

    if (commitHeaders.isEmpty) {
      log.error(s"Unable to match commit headers for $logEntry")
    }

    commitHeaders.lastOption.flatMap {
      case pattern(id, author, timestamp) =>
        dateTimeParser.parse(timestamp).map { dateTime =>
          Header(id, author, dateTime)
        }
    }
  }

}
