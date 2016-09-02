package com.meetup.gitbig

import java.time.LocalDateTime

import com.meetup.gitbig.parser.CommitParser
import org.scalatest.{FunSpec, Matchers}

import scala.collection.mutable
import scala.io.Source

class GitBigComponentTest extends FunSpec with Matchers {

  val full = """
"090976b","someone@meetup.com","2016-08-25 18:31:21 -0400"
1       1       email/reminder_rollup.email.html

"530d8f3","someone2@meetup.com","2016-08-25 14:52:36 -0400"
1       1       ivy.xml
-       -       lib/base/jira_2.11-0.1.19.jar
-       -       lib/base/jira_2.11-11.0.0.jar

"a3cb7a1","someone3@meetup.com","2016-08-25 11:35:48 -0400"
1       1       static/script/mobile/comments/ThreadController.js

"544b094","someone4@meetup.com","2016-08-24 16:27:32 -0400"
10      9       modules/chapstick/src/test/scala/com/meetup/business/ttdl/LatencyEstimatorTest.scala

"161f4c6","someone@meetup.com","2016-08-24 15:18:36 -0400"
-       -       lib/base/jira_2.11-0.1.19.jar
-       -       lib/base/jira_2.11-11.0.0.jar

"93c240d","someone5@meetup.com","2016-08-24 14:47:03 -0400"
3       3       .gitignore
5       6       util/db/MUP-3626_schema_update.sql"""

  describe("GitBig") {
    it("should work") {
      val expected = List(
        Commit(
          Header("090976b", "someone@meetup.com", LocalDateTime.of(2016, 8, 25, 18, 31, 21)),
          List(Change("email/reminder_rollup.email.html", Some("html"), None, 1, 1))
        ),
        Commit(
          Header("530d8f3", "someone2@meetup.com", LocalDateTime.of(2016, 8, 25, 14, 52, 36)),
          List(Change("ivy.xml", Some("xml"), None, 1, 1))
        ),
        Commit(
          Header("a3cb7a1", "someone3@meetup.com", LocalDateTime.of(2016, 8, 25, 11, 35, 48)),
          List(Change("static/script/mobile/comments/ThreadController.js", Some("js"), None, 1, 1))
        ),
        Commit(
          Header("544b094", "someone4@meetup.com", LocalDateTime.of(2016, 8, 24, 16, 27, 32)),
          List(Change("modules/chapstick/src/test/scala/com/meetup/business/ttdl/LatencyEstimatorTest.scala", Some("scala"), Some("test"), 10, 9))
        ),
        Commit(
          Header("93c240d", "someone5@meetup.com", LocalDateTime.of(2016, 8, 24, 14, 47, 3)),
          List(
            Change(".gitignore", Some("gitignore"), None, 3, 3),
            Change("util/db/MUP-3626_schema_update.sql", Some("sql"), None, 5, 6)
          )
        )
      )

      val outBuffer = mutable.ListBuffer[Commit]()

        def out(commit: Commit): Unit = outBuffer += commit

      new GitBig(CommitParser).out(Source.fromString(full), out)

      val actual = outBuffer.toList

      if (actual.size != expected.size) {
        val bad = expected.intersect(actual)
        fail(s"Lists of different sizes: $bad")
      }

      for ((exp, act) <- expected.zip(actual)) {
        act shouldBe exp
      }
    }
  }

}
