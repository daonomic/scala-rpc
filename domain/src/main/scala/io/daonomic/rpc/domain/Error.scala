package io.daonomic.rpc.domain

case class Error(code: Int, message: String, data: Option[String] = None)

object Error {
  val default: Error = new Error(0, "No result received")
}
