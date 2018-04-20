package io.daonomic.rpc.domain

case class Error(code: Int, message: String, data: Option[String] = None) {
  def fullMessage: String = message + data.map(": " + _).getOrElse("")
}

object Error {
  val default: Error = new Error(0, "No result received")
}
