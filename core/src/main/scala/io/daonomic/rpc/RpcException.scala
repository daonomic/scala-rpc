package io.daonomic.rpc

import io.daonomic.rpc.domain.Error

class RpcException(val error: Error) extends Exception(s"${error.code}: ${error.message}")