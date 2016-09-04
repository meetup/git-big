package com.meetup.gitbig.parser

import com.meetup.gitbig.Change
import com.meetup.gitbig.util.Classifier

trait ChangeParser {
  def parse(commit: String, classifier: Option[Classifier]): List[Change]
}

object ChangeParser extends ChangeParser {

  val pattern = """(?m)^(\d+) +(\d+) +(.*)""".r

  def parse(commit: String, classifier: Option[Classifier]): List[Change] = {
    pattern.findAllIn(commit).toList.map { line =>
      line match {
        case pattern(addition, deletion, file) =>
          val pieces = file.split("\\.")
          val ext = if (pieces.size > 1) {
            pieces.lastOption
          } else None

          val category = classifier.flatMap(_.classify(file))
          Change(file, ext, category, addition.toInt, deletion.toInt)
      }
    }
  }
}
