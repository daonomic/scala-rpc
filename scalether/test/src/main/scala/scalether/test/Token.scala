package scalether.test

import java.math.BigInteger

import cats.{Functor, Monad, MonadError}
import cats.implicits._
import scalether.abi._
import scalether.abi.array._
import scalether.abi.tuple._
import scalether.contract._
import scalether.domain._
import scalether.transaction._
import scalether.util.Hex

import scala.language.higherKinds

class Token[F[_]](address: Address, sender: TransactionSender[F])(implicit m: MonadError[F, Throwable])
  extends Contract[F](address, sender) {

  def approve(spender: Address, value: BigInteger): PreparedTransaction[F, Boolean] =
    PreparedTransaction(address, Signature("approve", Tuple2Type(AddressType, Uint256Type), Tuple1Type(BoolType)), (spender, value), sender)

  def totalSupply: F[BigInteger] =
    PreparedTransaction(address, Signature("totalSupply", UnitType, Tuple1Type(Uint256Type)), (), sender).call()

  def transferFrom(from: Address, to: Address, value: BigInteger): PreparedTransaction[F, Boolean] =
    PreparedTransaction(address, Signature("transferFrom", Tuple3Type(AddressType, AddressType, Uint256Type), Tuple1Type(BoolType)), (from, to, value), sender)

  def balanceOf(who: Address): F[BigInteger] =
    PreparedTransaction(address, Signature("balanceOf", Tuple1Type(AddressType), Tuple1Type(Uint256Type)), who, sender).call()

  def transfer(to: Address, value: BigInteger): PreparedTransaction[F, Boolean] =
    PreparedTransaction(address, Signature("transfer", Tuple2Type(AddressType, Uint256Type), Tuple1Type(BoolType)), (to, value), sender)

  def allowance(owner: Address, spender: Address): F[BigInteger] =
    PreparedTransaction(address, Signature("allowance", Tuple2Type(AddressType, AddressType), Tuple1Type(Uint256Type)), (owner, spender), sender).call()

}

object Token extends ContractObject {
  val name = "Token"
  val abi = "[{\"name\":\"approve\",\"type\":\"function\",\"inputs\":[{\"name\":\"spender\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}],\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"constant\":false},{\"name\":\"totalSupply\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"constant\":true},{\"name\":\"transferFrom\",\"type\":\"function\",\"inputs\":[{\"name\":\"from\",\"type\":\"address\"},{\"name\":\"to\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}],\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"constant\":false},{\"name\":\"balanceOf\",\"type\":\"function\",\"inputs\":[{\"name\":\"who\",\"type\":\"address\"}],\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"constant\":true},{\"name\":\"transfer\",\"type\":\"function\",\"inputs\":[{\"name\":\"to\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}],\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"constant\":false},{\"name\":\"allowance\",\"type\":\"function\",\"inputs\":[{\"name\":\"owner\",\"type\":\"address\"},{\"name\":\"spender\",\"type\":\"address\"}],\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"constant\":true},{\"name\":\"Transfer\",\"inputs\":[{\"name\":\"from\",\"type\":\"address\",\"indexed\":true},{\"name\":\"to\",\"type\":\"address\",\"indexed\":true},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"Approval\",\"inputs\":[{\"name\":\"owner\",\"type\":\"address\",\"indexed\":true},{\"name\":\"spender\",\"type\":\"address\",\"indexed\":true},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"}]"
  val bin = "0x"
}

case class Transfer(from: Address, to: Address, value: BigInteger)

object Transfer {
  val event = Event("Transfer", List(AddressType, AddressType, Uint256Type), Tuple2Type(AddressType, AddressType), Tuple1Type(Uint256Type))

  def apply(log: response.Log): Transfer = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val from = event.indexed.type1.decode(log.topics(1).bytes, 0).value
    val to = event.indexed.type2.decode(log.topics(2).bytes, 0).value
    val value = decodedData
    Transfer(from, to, value)
  }
}

case class Approval(owner: Address, spender: Address, value: BigInteger)

object Approval {
  val event = Event("Approval", List(AddressType, AddressType, Uint256Type), Tuple2Type(AddressType, AddressType), Tuple1Type(Uint256Type))

  def apply(log: response.Log): Approval = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val owner = event.indexed.type1.decode(log.topics(1).bytes, 0).value
    val spender = event.indexed.type2.decode(log.topics(2).bytes, 0).value
    val value = decodedData
    Approval(owner, spender, value)
  }
}

