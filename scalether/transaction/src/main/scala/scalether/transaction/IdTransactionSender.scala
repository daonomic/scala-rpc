package scalether.transaction

import java.math.BigInteger

import cats.Id
import scalether.domain.request.Transaction
import scalether.domain.{Binary, Word}

trait IdTransactionSender extends TransactionSender[Id] {
  def call(transaction: Transaction): Binary
  def estimate(transaction: Transaction): BigInteger
  def sendTransaction(transaction: Transaction): Word
}
