package com.meetup.gitbig.util

import org.scalatest.{FunSpec, Matchers}

class ClassifierTest extends FunSpec with Matchers {

  describe("Classifier") {
    it("should classify test dir files as test") {
      val expected = Some("test")
      val actual = Classifier.classify("src/test/scala/something.scala")

      actual shouldBe expected
    }

    it("should classify some obscured test file") {
      val expected = Some("test")
      val actual = Classifier.classify("util/somethingTest.sh")

      actual shouldBe expected
    }

    it("should return none on unrecognized files") {
      val expected = None
      val actual = Classifier.classify("acsdf")

      actual shouldBe None
    }

    it("should classify integration test dir as test") {
      val expected = Some("test")
      val actual = Classifier.classify("src/it/scala/something.scala")

      actual shouldBe expected
    }

    it("should classify component test dir as test") {
      val expected = Some("test")
      val actual = Classifier.classify("src/component/scala/something.scala")

      actual shouldBe expected
    }
  }

}
