package scalether.core

import io.daonomic.cats.MonadThrowable
import io.daonomic.rpc.{JsonConverter, RpcHttpClient, RpcTransport}
import scalether.core.json.EthereumJacksonModule

import scala.language.higherKinds

class EthereumRpcClient[F[_]](transport: RpcTransport[F])
                          (implicit me: MonadThrowable[F])
  extends RpcHttpClient[F](EthereumRpcClient.jsonConverter, transport) {

}

object EthereumRpcClient {
  def jsonConverter: JsonConverter = new JsonConverter(new EthereumJacksonModule)
}
