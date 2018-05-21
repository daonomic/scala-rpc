package scalether.transaction

import java.math.BigInteger

import cats.Id
import io.daonomic.rpc.domain.{Binary, Word}
import scalether.domain.request.Transaction

trait IdTransactionSender extends TransactionSender[Id] {
  def call(transaction: Transaction): Binary
  def estimate(transaction: Transaction): BigInteger
  def sendTransaction(transaction: Transaction): Word
}
