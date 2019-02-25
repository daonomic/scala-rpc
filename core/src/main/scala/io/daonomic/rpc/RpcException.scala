package io.daonomic.rpc

import io.daonomic.rpc.domain.Error

class RpcException(message: String, cause: Throwable) extends Exception(message, cause)

class RpcIoException(cause: Throwable) extends RpcException(null, cause)

class RpcCodeException(val message: String, val error: Error) extends RpcException(s"$message... ${error.code}: ${error.fullMessage}", null)