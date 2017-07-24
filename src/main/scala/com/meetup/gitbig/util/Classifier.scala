package com.meetup.gitbig.util

trait Classifier {
  def classify(f: String): Option[String]
}

object Classifier extends Classifier {

  def classify(fileName: String): Option[String] = {
    if (fileName.toLowerCase.contains("test") ||
      fileName.contains("/it/") ||
      fileName.contains("/component/")) {
      Some("test")
    } else {
      None
    }
  }

}
