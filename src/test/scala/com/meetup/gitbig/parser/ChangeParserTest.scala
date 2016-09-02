package com.meetup.gitbig.parser

import com.meetup.gitbig.Change
import org.scalatest.{FunSpec, Matchers}

class ChangeParserTest extends FunSpec with Matchers {

  describe("ChangeParser") {
    it("should parse additions/deletions") {
      val input =
        """
          |"abc","someone@meetup.com","Thu Aug 25 18:31:21 2016 -0400"
          |"090976b","someone@meetup.com","Thu Aug 25 18:31:21 2016 -0400"
          |1       2       email/reminder_rollup.email.html
        """.stripMargin

      val expected = List(Change("email/reminder_rollup.email.html", Some("html"), None, 1, 2))
      val actual = ChangeParser.parse(input, None)

      expected shouldBe actual
    }
  }
}
