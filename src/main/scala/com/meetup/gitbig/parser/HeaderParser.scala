package com.meetup.gitbig.parser

import com.meetup.gitbig.Header
import com.meetup.logging.Logging

object HeaderParser extends Logging {
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
        println("id:" + id)
        dateTimeParser.parse(timestamp).map { dateTime =>
          Header(id, author, dateTime)
        }
    }
  }

}
