package com.meetup.gitbig.parser

import com.meetup.gitbig.Change
import com.meetup.gitbig.util.Classifier
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

    it("should handle non extension files") {
      val input =
        """
          |"abc","someone@meetup.com","Thu Aug 25 18:31:21 2016 -0400"
          |1       2       fileName
        """.stripMargin

      val expected = List(Change("fileName", None, None, 1, 2))
      val actual = ChangeParser.parse(input, None)

      expected shouldBe actual
    }

    it("should exercise classifier if provided") {
      val classifier = new Classifier {
        def classify(f: String): Option[String] = Some("ftw")
      }
      val input =
        """
          |"abc","someone@meetup.com","Thu Aug 25 18:31:21 2016 -0400"
          |1       2       fileName
        """.stripMargin

      val expected = List(Change("fileName", None, Some("ftw"), 1, 2))
      val actual = ChangeParser.parse(input, Some(classifier))

      expected shouldBe actual
    }

    it("should parse changes using tabs") {
      val input =
        """
          |"abc","someone@meetup.com","Thu Aug 25 18:31:21 2016 -0400"
          |1	2	fileName
        """.stripMargin

      val expected = List(Change("fileName", None, None, 1, 2))
      val actual = ChangeParser.parse(input, None)

      expected shouldBe actual
    }
  }
}
