package com.meetup.gitbig.parser

import java.time.LocalDateTime

import org.scalatest.{FunSpec, Matchers}

class DateTimeParserTest extends FunSpec with Matchers {

  describe("DateTimeParser") {
    it("should parse to an accurate LocalDateTime") {
      val input = "2016-08-24 14:43:49 -0400"
      val expected = Some(LocalDateTime.of(2016, 8, 24, 14, 43, 49))

      val actual = DateTimeParser.parse(input)

      actual shouldBe expected
    }
  }

}
