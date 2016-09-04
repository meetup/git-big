package com.meetup.gitbig

import java.io.{BufferedWriter, File, FileWriter}

import com.meetup.logging.Logging

import scala.io.Source

/**
 * Application entry point.
 */
object GitBigApp extends Logging {

  private def usageAndExit(): Exit = {
    log.error("Usage:")
    log.error(" GitBig repo_name input_log output_file")
    Exit(1)
  }

  private def badInputFileAndExit(input: String): Exit = {
    log.error(s"Couldn't read input file: $input")
    Exit(1)
  }

  private def outputExistsAndExit(output: String): Exit = {
    log.error(s"Output file already exists, refusing to overwrite")
    Exit(1)
  }

  private def outputNotWritableAndExit(output: String): Exit = {
    log.error(s"Oputput file isn't writable")
    Exit(1)
  }

  case class Exit(code: Int)

  def apply(args: Array[String]): Exit = {
    args.toList match {
      case List(repoName, input, output) =>
        val inputFile = new File(input)
        val outputFile = new File(output)

        if (!inputFile.exists() || !inputFile.canRead()) {
          badInputFileAndExit(input)

        } else if (outputFile.exists()) {
          outputExistsAndExit(output)

        } else if (!outputFile.getAbsoluteFile.getParentFile.canWrite()) {
          outputNotWritableAndExit(output)

        } else {
          log.info(s"Transforming $input for repo $repoName to $output...")

          val source = Source.fromFile(inputFile)
          val writer = new FileWriter(outputFile)
          val bufferedWriter = new BufferedWriter(writer)

          GitBig(source, bufferedWriter, repoName)
          bufferedWriter.flush()

          log.info(s"Finished processing $input")
          Exit(0)
        }

      case _ => usageAndExit()
    }
  }

  def main(args: Array[String]): Unit = {
    sys.exit(apply(args).code)
  }
}
