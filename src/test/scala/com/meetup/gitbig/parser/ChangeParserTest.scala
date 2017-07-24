package com.meetup.gitbig.parser

import com.meetup.gitbig.Change
import com.meetup.gitbig.util.Classifier
import org.scalatest.{FunSpec, Matchers}

class ChangeParserTest extends FunSpec with Matchers {

  describe("ChangeParser") {
    it("should parse additions/deletions") {
      val input = "1       2       email/reminder_rollup.email.html"

      val expected = Some(Change("email/reminder_rollup.email.html", Some("html"), None, 1, 2))
      val actual = ChangeParser.parse(input, None)

      expected shouldBe actual
    }

    it("should handle non extension files") {
      val input = "1       2       fileName"

      val expected = Some(Change("fileName", None, None, 1, 2))
      val actual = ChangeParser.parse(input, None)

      expected shouldBe actual
    }

    it("should exercise classifier if provided") {
      val classifier = new Classifier {
        def classify(f: String): Option[String] = Some("ftw")
      }
      val input = "1       2       fileName"

      val expected = Some(Change("fileName", None, Some("ftw"), 1, 2))
      val actual = ChangeParser.parse(input, Some(classifier))

      expected shouldBe actual
    }

    it("should parse changes using tabs") {
      val input = "1	2	fileName"

      val expected = Some(Change("fileName", None, None, 1, 2))
      val actual = ChangeParser.parse(input, None)

      expected shouldBe actual
    }
  }
}
