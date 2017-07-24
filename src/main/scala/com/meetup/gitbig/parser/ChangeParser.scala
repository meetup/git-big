package com.meetup.gitbig.parser

import com.meetup.gitbig.Change
import com.meetup.gitbig.util.Classifier

trait ChangeParser {
  def parse(commit: String, classifier: Option[Classifier]): Option[Change]
}

object ChangeParser extends ChangeParser {

  val pattern = """(?m)^(\d+)\s+(\d+)\s+(.*)""".r

  def parse(change: String, classifier: Option[Classifier] = Some(Classifier)): Option[Change] = {

    change match {
      case pattern(addition, deletion, file) =>
        val pieces = file.split("\\.")
        val ext = if (pieces.size > 1) {
          pieces.lastOption
        } else None

        val category = classifier.flatMap(_.classify(file))
        Some(Change(file, ext, category, addition.toInt, deletion.toInt))
      case _ =>
        None
    }
  }
}
