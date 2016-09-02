package com.meetup.gitbig.parser

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import com.meetup.logging.Logging

import scala.util.Try

trait DateTimeParser {
  def parse(input: String): Option[LocalDateTime]
}

object DateTimeParser extends DateTimeParser with Logging {

  private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");

  def parse(input: String): Option[LocalDateTime] = {
    Try(LocalDateTime.parse(input, formatter)).toOption.orElse {
      log.error(s"Failed to parse: $input")
      None
    }
  }

}
