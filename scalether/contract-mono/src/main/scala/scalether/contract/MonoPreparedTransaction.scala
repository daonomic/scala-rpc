package scalether.contract

import java.math.BigInteger

import io.daonomic.cats.mono.implicits._
import io.daonomic.rpc.domain.{Binary, Word}
import reactor.core.publisher.Mono
import scalether.abi.Signature
import scalether.abi.tuple.TupleType
import scalether.domain.Address
import scalether.transaction.MonoTransactionSender

class MonoPreparedTransaction[O](address: Address,
                                 out: TupleType[O],
                                 data: Binary,
                                 sender: MonoTransactionSender,
                                 value: BigInteger,
                                 gas: BigInteger = null,
                                 gasPrice: BigInteger = null,
                                 from: Address = null,
                                 description: String = null)
  extends PreparedTransaction[Mono, O](address, out, data, sender, value, gas, gasPrice, from, description) {

  override def withGas(newGas: BigInteger): MonoPreparedTransaction[O] =
    new MonoPreparedTransaction[O](address, out, data, sender, value, newGas, gasPrice, from, description)

  override def withGasPrice(newGasPrice: BigInteger): MonoPreparedTransaction[O] =
    new MonoPreparedTransaction[O](address, out, data, sender, value, gas, newGasPrice, from, description)

  override def withValue(newValue: BigInteger): MonoPreparedTransaction[O] =
    new MonoPreparedTransaction[O](address, out, data, sender, newValue, gas, gasPrice, from, description)

  override def withFrom(newFrom: Address): MonoPreparedTransaction[O] =
    new MonoPreparedTransaction[O](address, out, data, sender, value, gas, gasPrice, newFrom, description)

  override def call(): Mono[O] = super.call()

  override def execute(): Mono[Word] = super.execute()

  override def estimate(): Mono[BigInteger] = super.estimate()

  override def estimateAndExecute(): Mono[Word] = super.estimateAndExecute()
}

object MonoPreparedTransaction {
  def apply[I, O](address: Address,
                  signature: Signature[I, O],
                  in: I,
                  sender: MonoTransactionSender,
                  value: BigInteger = null,
                  gas: BigInteger = null,
                  gasPrice: BigInteger = null,
                  from: Address = null,
                  description: String = null): MonoPreparedTransaction[O] =
    new MonoPreparedTransaction[O](address, signature.out, signature.encode(in), sender, value, gas, gasPrice, from, description)
}