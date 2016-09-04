package com.meetup.gitbig.parser

import java.time.LocalDateTime

import com.meetup.gitbig.Header
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FunSpec, Matchers}
import org.mockito.Matchers.any
import org.mockito.Mockito.when

class HeaderParserTest extends FunSpec with Matchers with MockitoSugar {

  describe("HeaderParser") {
    it("should parse correctly") {
      val input =
        """
          |"090976b","someone@meetup.com","Thu Aug 25 18:31:21 2016 -0400"
        """.stripMargin

      HeaderParser.pattern.findAllIn(input).size shouldBe 1
    }

    it("should parse single commit headers") {
      val input =
        """
          |"090976b","someone@meetup.com","Thu Aug 25 18:31:21 2016 -0400"
          |1       1       email/reminder_rollup.email.html
        """.stripMargin

      val dateTime = LocalDateTime.of(2016, 8, 25, 18, 31, 21)
      val expected = Some(Header(
        "090976b", "someone@meetup.com",
        dateTime
      ))

      val dateTimeParser = mock[DateTimeParser]
      when(dateTimeParser.parse(any())).thenReturn(Some(dateTime))

      val actual = HeaderParser.parse(input, dateTimeParser)

      actual shouldBe expected
    }

    it("should parse double commit headers") {
      // This happens when there's a commit with no changes
      // like a merge commit.  Typically excluding merges
      // from log output but just in case.
      val input =
        """
          |"abc","someone@meetup.com","Thu Aug 25 18:31:21 2016 -0400"
          |"090976b","someone@meetup.com","Thu Aug 25 18:31:21 2016 -0400"
          |1       1       email/reminder_rollup.email.html
        """.stripMargin

      val dateTime = LocalDateTime.of(2016, 8, 25, 18, 31, 21)
      val expected = Some(Header(
        "090976b", "someone@meetup.com",
        dateTime
      ))

      val dateTimeParser = mock[DateTimeParser]
      when(dateTimeParser.parse(any())).thenReturn(Some(dateTime))

      val actual = HeaderParser.parse(input, dateTimeParser)

      actual shouldBe expected
    }

    it("should handle parse failures gracefully") {
      val expected = None
      val actual = HeaderParser.parse("blah")

      actual shouldBe expected
    }
  }

}
