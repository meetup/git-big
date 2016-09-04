package com.meetup.gitbig

import java.io.File

import com.meetup.gitbig.GitBigApp.Exit
import org.scalatest.{FunSpec, Matchers}

import scala.io.Source

class GitBigAppComponentTest extends FunSpec with Matchers {

  describe("GitBigApp") {
    it("should load a file and provide expected output") {
      val log = new File(getClass.getResource("/test.log").toURI)
      val expected = Source.fromFile(getClass.getResource("/test-expected").toURI).mkString

      val repoName = "meetup"
      val output = File.createTempFile("git-big", "output-json-expected")
      output.delete() // So GitBig won't refuse to overwrite.
      output.deleteOnExit()

      val exit = GitBigApp(
        Array[String](
          repoName,
          log.getAbsolutePath,
          output.getAbsolutePath
        )
      )

      val actual = Source.fromFile(output).mkString

      exit shouldBe Exit(0)
      actual shouldBe expected
    }

    it("should fail if output file exists") {
      val log = new File(getClass.getResource("/test.log").toURI)

      val repoName = "meetup"
      val output = File.createTempFile("git-big", "output-json-expected")
      output.deleteOnExit()

      val exit = GitBigApp(
        Array[String](
          repoName,
          log.getAbsolutePath,
          output.getAbsolutePath
        )
      )

      val actual = Source.fromFile(output).mkString

      exit shouldBe Exit(1)
    }

    it("should fail if intput file exists") {
      val repoName = "meetup"
      val output = File.createTempFile("git-big", "output-json-expected")
      output.delete() // So GitBig won't refuse to overwrite.
      output.deleteOnExit()

      val exit = GitBigApp(
        Array[String](
          repoName,
          "/fake-file-123",
          output.getAbsolutePath
        )
      )

      exit shouldBe Exit(1)
    }
  }
}
