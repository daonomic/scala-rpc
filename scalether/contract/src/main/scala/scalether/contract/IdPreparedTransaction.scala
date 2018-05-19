package scalether.contract

import java.math.BigInteger

import cats.Id
import io.daonomic.cats.implicits._
import scalether.abi.Signature
import scalether.domain.{Address, Binary, Word}
import scalether.transaction.IdTransactionSender

class IdPreparedTransaction[O](address: Address,
                                 signature: Signature[_, O],
                                 data: Binary,
                                 sender: IdTransactionSender,
                                 value: BigInteger,
                                 gas: BigInteger,
                                 gasPrice: BigInteger)
  extends PreparedTransaction[Id, O](address, signature, data, sender, value, gas, gasPrice) {

  override def withGas(newGas: BigInteger): IdPreparedTransaction[O] =
    new IdPreparedTransaction[O](address, signature, data, sender, value, newGas, gasPrice)

  override def withGasPrice(newGasPrice: BigInteger): IdPreparedTransaction[O] =
    new IdPreparedTransaction[O](address, signature, data, sender, value, gas, newGasPrice)

  override def withValue(newValue: BigInteger): IdPreparedTransaction[O] =
    new IdPreparedTransaction[O](address, signature, data, sender, newValue, gas, gasPrice)

  override def call(): O = super.call()

  override def execute(): Word = super.execute()

  override def estimate(): BigInteger = super.estimate()

  override def estimateAndExecute(): Word = super.estimateAndExecute()
}

object IdPreparedTransaction {
  def apply[I, O](address: Address,
                  signature: Signature[I, O],
                  in: I,
                  sender: IdTransactionSender,
                  value: BigInteger = null,
                  gas: BigInteger = null,
                  gasPrice: BigInteger = null): IdPreparedTransaction[O] =
    new IdPreparedTransaction[O](address, signature, Binary(signature.encode(in)), sender, value, gas, gasPrice)
}