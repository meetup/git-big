package com.meetup.gitbig

import java.io.BufferedWriter

import com.meetup.gitbig.parser.LogParser
import com.meetup.gitbig.util.CommitRenderer

import scala.io.Source

object GitBig {

  def apply(source: Source, writer: BufferedWriter, repo: String): Unit = {
    new LogParser().parse(source, { commit =>
      writer.write(CommitRenderer.json(repo, commit))
      writer.newLine()
    })
  }

}
