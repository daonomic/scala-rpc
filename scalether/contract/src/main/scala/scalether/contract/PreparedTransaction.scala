package scalether.contract

import java.math.BigInteger

import cats.implicits._
import io.daonomic.cats.MonadThrowable
import io.daonomic.rpc.domain.{Binary, Word}
import scalether.abi.Signature
import scalether.abi.tuple.TupleType
import scalether.domain.request.Transaction
import scalether.domain.Address
import scalether.transaction.TransactionSender

import scala.language.higherKinds

class PreparedTransaction[F[_], O](val address: Address,
                                   val out: TupleType[O],
                                   val data: Binary,
                                   sender: TransactionSender[F],
                                   val value: BigInteger,
                                   val gas: BigInteger = null,
                                   val gasPrice: BigInteger = null,
                                   val from: Address = null)
                                  (implicit m: MonadThrowable[F]) {

  def withGas(newGas: BigInteger): PreparedTransaction[F, O] =
    new PreparedTransaction[F, O](address, out, data, sender, value, newGas, gasPrice, from)

  def withGasPrice(newGasPrice: BigInteger): PreparedTransaction[F, O] =
    new PreparedTransaction[F, O](address, out, data, sender, value, gas, newGasPrice, from)

  def withValue(newValue: BigInteger): PreparedTransaction[F, O] =
    new PreparedTransaction[F, O](address, out, data, sender, newValue, gas, gasPrice, from)

  def withFrom(newFrom: Address): PreparedTransaction[F, O] =
    new PreparedTransaction[F, O](address, out, data, sender, value, gas, gasPrice, newFrom)

  def call(): F[O] =
    sender.call(Transaction(to = address, data = data, value = value, gas = gas, gasPrice = gasPrice, from = from))
      .map(binary => out.decode(binary, 0).value)

  def execute(): F[Word] =
    sender.sendTransaction(Transaction(to = address, data = data, value = value, gas = gas, gasPrice = gasPrice, from = from))

  def estimate(): F[BigInteger] =
    sender.estimate(Transaction(to = address, data = data, value = value, gas = gas, gasPrice = gasPrice, from = from))

  def estimateAndExecute(): F[Word] =
    estimate().flatMap(estimated => this.withGas(estimated).execute())
}

object PreparedTransaction {
  def apply[F[_], I, O](address: Address,
                        signature: Signature[I, O],
                        in: I,
                        sender: TransactionSender[F],
                        value: BigInteger = null,
                        gas: BigInteger = null,
                        gasPrice: BigInteger = null)
                       (implicit m: MonadThrowable[F]): PreparedTransaction[F, O] =
    new PreparedTransaction[F, O](address, signature.out, signature.encode(in), sender, value, gas, gasPrice)
}