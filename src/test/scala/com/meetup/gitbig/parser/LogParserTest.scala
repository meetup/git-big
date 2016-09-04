package com.meetup.gitbig.parser

import java.time.LocalDateTime

import com.meetup.gitbig.{Change, Commit, Header}
import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FunSpec, Matchers}

import scala.collection.mutable
import scala.io.Source

class LogParserTest extends FunSpec with Matchers with MockitoSugar {

  describe("LogParser") {
    it("should parse a single commit log") {
      val input = """
        |"530d8f3","someone@meetup.com","Thu Aug 25 14:52:36 2016 -0400"
        |1       1       ivy.xml
        |-       -       lib/base/jira_2.11-0.1.19.jar
        |-       -       lib/base/jira_2.11-11.0.0.jar""".stripMargin

      val expected = Commit(
        Header("530d8f3", "someone@meetup.com", LocalDateTime.of(2016, 8, 25, 14, 52, 36)),
        List(Change("ivy.xml", Some("xml"), None, 1, 1))
      )

      val outBuffer = mutable.ListBuffer[Commit]()
        def out(commit: Commit): Unit = outBuffer += commit

      val commitParser = mock[CommitParser]
      when(commitParser.parse(input.trim)).thenReturn(Some(expected))

      new LogParser(commitParser).parse(Source.fromString(input), out)

      val actual = outBuffer.toList

      actual shouldBe List(expected)
    }

    it("should parse two commits") {
      val input =
        """
          |"530d8f3","someone@meetup.com","Thu Aug 25 14:52:36 2016 -0400"
          |1       1       ivy.xml
          |-       -       lib/base/jira_2.11-0.1.19.jar
          |-       -       lib/base/jira_2.11-11.0.0.jar
        """.stripMargin

      val input2 =
        """
          |"abc","someone@meetup.com","Thu Aug 25 18:31:21 2016 -0400"
          |"090976b","someone@meetup.com","Thu Aug 25 18:31:21 2016 -0400"
          |1       2       email/reminder_rollup.email.html
        """.stripMargin

      // The following values don't really matter.
      // We're just ensure different inputs were read.
      val expected = Commit(
        Header("530d8f3", "doug@meetup.com", LocalDateTime.of(2016, 8, 25, 14, 52, 36)),
        List(Change("ivy.xml", Some("xml"), None, 1, 1))
      )

      val expected2 = Commit(
        Header("090976b", "someone@meetup.com", LocalDateTime.of(2016, 8, 25, 14, 52, 36)),
        List(Change("email/reminder_rollup.email.html", Some("html"), None, 1, 2))
      )

      val outBuffer = mutable.ListBuffer[Commit]()
        def out(commit: Commit): Unit = outBuffer += commit

      val commitParser = mock[CommitParser]
      when(commitParser.parse(input.trim)).thenReturn(Some(expected))
      when(commitParser.parse(input2.trim)).thenReturn(Some(expected2))

      new LogParser(commitParser).parse(Source.fromString(input + input2), out)

      val actual = outBuffer.toList

      actual shouldBe List(expected, expected2)
    }
  }

}
