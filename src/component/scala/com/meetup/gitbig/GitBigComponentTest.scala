package com.meetup.gitbig

import java.io.{BufferedWriter, StringWriter}

import org.scalatest.{FunSpec, Matchers}

import scala.io.Source

class GitBigComponentTest extends FunSpec with Matchers {

  describe("GitBig") {

    it("should generate json with no newlines") {
      val input = """
        |"090976b","someone@meetup.com","2016-08-25 18:31:21 -0400"
        |1       1       email/reminder_rollup.email.html
        |
        |"530d8f3","someone2@meetup.com","2016-08-25 14:52:36 -0400"
        |1       1       ivy.xml
        |
        |"530d8f3","someone2@meetup.com","2016-08-25 14:52:36 -0400"
        |-       -       ivy.xml
        |
        |"530d8f3","someone2@meetup.com","2016-08-25 14:52:36 -0400"
        |1       1       ivy.xml
        |""".stripMargin

      val expected =
        """{"repo":"meetup/meetup","commit":"090976b","author":"someone@meetup.com","timestamp":"2016-08-25 18:31:21","file":"email/reminder_rollup.email.html","extension":"html","added":1,"deleted":1}
           |{"repo":"meetup/meetup","commit":"530d8f3","author":"someone2@meetup.com","timestamp":"2016-08-25 14:52:36","file":"ivy.xml","extension":"xml","added":1,"deleted":1}
           |{"repo":"meetup/meetup","commit":"530d8f3","author":"someone2@meetup.com","timestamp":"2016-08-25 14:52:36","file":"ivy.xml","extension":"xml","added":1,"deleted":1}
        |""".stripMargin

      val sw = new StringWriter()
      val bw = new BufferedWriter(sw)

      GitBig(Source.fromString(input), bw, "meetup/meetup")
      bw.flush()

      sw.toString shouldBe expected
    }
  }

}
