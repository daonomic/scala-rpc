package io.daonomic.rpc

import io.daonomic.rpc.domain.Error

class RpcException(message: String, cause: Throwable) extends Exception(message, cause)

class RpcIoException(cause: Throwable) extends RpcException(null, cause)

class RpcCodeException(val error: Error) extends RpcException(s"${error.code}: ${error.fullMessage}", null)