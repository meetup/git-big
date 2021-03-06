package com.meetup.gitbig.util

import java.time.LocalDateTime

import com.meetup.gitbig.{Change, CommitChange, Header}
import org.scalatest.{FunSpec, Matchers}

class CommitChangeRendererTest extends FunSpec with Matchers {

  describe("CommitRenderer") {
    it("should render commits") {
      val input = CommitChange(
        Header("abc", "no@meetup.com", LocalDateTime.of(2016, 8, 25, 14, 52, 36)),
        Change("ivy.xml", Some("xml"), None, 1, 2)
      )

      val expected = """{"repo":"m","commit":"abc","author":"no@meetup.com","timestamp":"2016-08-25 14:52:36","file":"ivy.xml","extension":"xml","added":1,"deleted":2}"""
      val actual = CommitRenderer.json("m", input)

      actual shouldBe expected
    }
  }

}
