package scalether.contract

import scalether.domain.Address
import scalether.transaction.TransactionSender

import scala.language.higherKinds

abstract class Contract[F[_]](val address: Address, sender: TransactionSender[F]) {
}
