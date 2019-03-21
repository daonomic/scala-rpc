package scalether.transaction

import io.daonomic.cats.mono.implicits._
import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.Address

import scala.language.higherKinds
class ReadOnlyMonoTransactionSender(ethereum: MonoEthereum, from: Address)
  extends ReadOnlyTransactionSender[Mono](ethereum, from) with MonoTransactionSender {

}
