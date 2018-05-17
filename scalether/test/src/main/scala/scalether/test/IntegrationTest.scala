package scalether.test

import java.math.BigInteger

import cats.{Functor, MonadError}
import cats.implicits._
import scalether.abi._
import scalether.abi.array._
import scalether.abi.tuple._
import scalether.contract._
import scalether.domain._
import scalether.domain.request._
import scalether.transaction._
import scalether.util.Hex

import scala.language.higherKinds


class IntegrationTest[F[_]](address: Address, sender: TransactionSender[F])(implicit f: MonadError[F, Throwable])
  extends Contract[F](address, sender) {

  val stateSignature = Signature("state", UnitType, Tuple1Type(Uint256Type))
  def state: F[BigInteger] =
    PreparedTransaction(address, stateSignature, (), sender).call()

  val setStateSignature = Signature("setState", Tuple1Type(Uint256Type), UnitType)
  def setState(_state: BigInteger): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, setStateSignature, _state, sender)

  val checkStructsWithStringSignature = Signature("checkStructsWithString", Tuple1Type(VarArrayType(Tuple2Type(StringType, Uint256Type))), UnitType)
  def checkStructsWithString(structs: Array[(String, BigInteger)]): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, checkStructsWithStringSignature, structs, sender)

  val getStructWithStringSignature = Signature("getStructWithString", UnitType, Tuple1Type(Tuple2Type(StringType, Uint256Type)))
  def getStructWithString: F[(String, BigInteger)] =
    PreparedTransaction(address, getStructWithStringSignature, (), sender).call()

  val getStructsWithStringSignature = Signature("getStructsWithString", UnitType, Tuple1Type(FixArrayType(1, Tuple2Type(StringType, Uint256Type))))
  def getStructsWithString: F[Array[(String, BigInteger)]] =
    PreparedTransaction(address, getStructsWithStringSignature, (), sender).call()

  val setRatesSignature = Signature("setRates", Tuple1Type(VarArrayType(Tuple2Type(AddressType, Uint256Type))), UnitType)
  def setRates(rates: Array[(Address, BigInteger)]): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, setRatesSignature, rates, sender)

  val setRateSignature = Signature("setRate", Tuple1Type(Tuple2Type(AddressType, Uint256Type)), UnitType)
  def setRate(_rate: (Address, BigInteger)): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, setRateSignature, _rate, sender)

  val getRateSignature = Signature("getRate", Tuple1Type(Uint256Type), Tuple1Type(Tuple2Type(AddressType, Uint256Type)))
  def getRate(test: BigInteger): PreparedTransaction[F, (Address, BigInteger)] =
    PreparedTransaction(address, getRateSignature, test, sender)

  val getRate1Signature = Signature("getRate", UnitType, Tuple1Type(Tuple2Type(AddressType, Uint256Type)))
  def getRate: PreparedTransaction[F, (Address, BigInteger)] =
    PreparedTransaction(address, getRate1Signature, (), sender)

  val emitSimpleEventSignature = Signature("emitSimpleEvent", Tuple2Type(StringType, StringType), UnitType)
  def emitSimpleEvent(topic: String, value: String): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, emitSimpleEventSignature, (topic, value), sender)

  val emitAddressEventSignature = Signature("emitAddressEvent", Tuple2Type(AddressType, StringType), UnitType)
  def emitAddressEvent(topic: Address, value: String): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, emitAddressEventSignature, (topic, value), sender)

  val emitMixedEventSignature = Signature("emitMixedEvent", Tuple3Type(AddressType, StringType, AddressType), UnitType)
  def emitMixedEvent(topic: Address, value: String, test: Address): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, emitMixedEventSignature, (topic, value, test), sender)

}

object IntegrationTest extends ContractObject {
  val name = "IntegrationTest"
  val abi = "[{\"name\":\"state\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"constant\":true},{\"name\":\"SimpleEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"AddressEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"MixedEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false},{\"name\":\"test\",\"type\":\"address\",\"indexed\":true}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"RateEvent\",\"inputs\":[{\"name\":\"token\",\"type\":\"address\",\"indexed\":false},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"StringEvent\",\"inputs\":[{\"name\":\"str\",\"type\":\"string\",\"indexed\":false},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"TestEvent\",\"inputs\":[{\"name\":\"value1\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"TestEvent\",\"inputs\":[{\"name\":\"value1\",\"type\":\"uint256\",\"indexed\":false},{\"name\":\"value2\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"TestEvent\",\"inputs\":[{\"name\":\"value1\",\"type\":\"uint256\",\"indexed\":false},{\"name\":\"value2\",\"type\":\"uint256\",\"indexed\":false},{\"name\":\"value3\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"setState\",\"type\":\"function\",\"inputs\":[{\"name\":\"_state\",\"type\":\"uint256\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"checkStructsWithString\",\"type\":\"function\",\"inputs\":[{\"name\":\"structs\",\"type\":\"tuple[]\",\"components\":[{\"name\":\"str\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"getStructWithString\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"tuple\",\"components\":[{\"name\":\"str\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"payable\":false,\"constant\":true},{\"name\":\"getStructsWithString\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"tuple[1]\",\"components\":[{\"name\":\"str\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"payable\":false,\"constant\":true},{\"name\":\"setRates\",\"type\":\"function\",\"inputs\":[{\"name\":\"rates\",\"type\":\"tuple[]\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"setRate\",\"type\":\"function\",\"inputs\":[{\"name\":\"_rate\",\"type\":\"tuple\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"getRate\",\"type\":\"function\",\"inputs\":[{\"name\":\"test\",\"type\":\"uint256\"}],\"outputs\":[{\"name\":\"\",\"type\":\"tuple\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"payable\":false,\"constant\":false},{\"name\":\"getRate\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"tuple\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"payable\":false,\"constant\":false},{\"name\":\"emitSimpleEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"string\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"emitAddressEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"string\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"emitMixedEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"string\"},{\"name\":\"test\",\"type\":\"address\"}],\"outputs\":[],\"payable\":false,\"constant\":false}]"
  val bin = "0x608060405234801561001057600080fd5b50611145806100206000396000f3006080604052600436106100ba576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806329dc7efe146100bf5780633cb7bce8146100e857806345e73e6514610111578063577640941461013c578063679aefce1461017957806375e8660d146101a45780637ad8748f146101cd578063a9e966b7146101f8578063b710cc7c14610221578063c19d93fb1461024a578063c4be9b3014610275578063d1c58fbd1461029e575b600080fd5b3480156100cb57600080fd5b506100e660048036036100e19190810190610c0c565b6102c7565b005b3480156100f457600080fd5b5061010f600480360361010a9190810190610bcb565b610365565b005b34801561011d57600080fd5b506101266103fa565b6040516101339190610e8b565b60405180910390f35b34801561014857600080fd5b50610163600480360361015e9190810190610ca1565b61044e565b6040516101709190610eff565b60405180910390f35b34801561018557600080fd5b5061018e6104ca565b60405161019b9190610eff565b60405180910390f35b3480156101b057600080fd5b506101cb60048036036101c69190810190610b23565b610544565b005b3480156101d957600080fd5b506101e26105ae565b6040516101ef9190610f1a565b60405180910390f35b34801561020457600080fd5b5061021f600480360361021a9190810190610ca1565b6105e1565b005b34801561022d57600080fd5b5061024860048036036102439190810190610b8a565b6105eb565b005b34801561025657600080fd5b5061025f610680565b60405161026c9190610f3c565b60405180910390f35b34801561028157600080fd5b5061029c60048036036102979190810190610c78565b610686565b005b3480156102aa57600080fd5b506102c560048036036102c09190810190610acf565b610721565b005b816040518082805190602001908083835b6020831015156102fd57805182526020820191506020810190506020830392506102d8565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390207fdf07c14ba7c542332df3cebcac73245d796909b2be2b2aed13f40d5b23136cd0826040516103599190610ead565b60405180910390a25050565b60008082519150600090505b818110156103f5577f6fc10800b7deda6aea9a2a89968fd690847169903420807806184ae95e93693383828151811015156103a857fe5b906020019060200201516000015184838151811015156103c457fe5b90602001906020020151602001516040516103e0929190610ecf565b60405180910390a18080600101915050610371565b505050565b610402610773565b61040a610773565b604080519081016040528060206040519081016040528060008152508152602001600081525081600060018110151561043f57fe5b60200201819052508091505090565b6104566107a0565b60016040805190810160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820154815250509050919050565b6104d26107a0565b60016040805190810160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001600182015481525050905090565b8073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff167f0f5e2a2282931e0c4b5f2319a04deb79518c3127921c3f70d2fe9306563dd167846040516105a19190610ead565b60405180910390a3505050565b6105b66107d0565b6040805190810160405280602060405190810160405280600081525081526020016000815250905090565b8060008190555050565b60008082519150600090505b8181101561067b577ff6cc106d4ff63d31aa62a665afe317068b92fc2bda4688afe33635cfff444749838281518110151561062e57fe5b9060200190602002015160000151848381518110151561064a57fe5b9060200190602002015160200151604051610666929190610e62565b60405180910390a180806001019150506105f7565b505050565b60005481565b80600160008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550602082015181600101559050507ff6cc106d4ff63d31aa62a665afe317068b92fc2bda4688afe33635cfff44474981600001518260200151604051610716929190610e62565b60405180910390a150565b8173ffffffffffffffffffffffffffffffffffffffff167f2d1926fdeec1e1c3ef245ded9ff04522b3e5e14f0a57a12efe35949d72bf661e826040516107679190610ead565b60405180910390a25050565b60408051908101604052806001905b61078a6107ea565b8152602001906001900390816107825790505090565b6040805190810160405280600073ffffffffffffffffffffffffffffffffffffffff168152602001600081525090565b604080519081016040528060608152602001600081525090565b604080519081016040528060608152602001600081525090565b6000610810823561108e565b905092915050565b600082601f830112151561082b57600080fd5b813561083e61083982610f84565b610f57565b9150818183526020840193506020810190508385604084028201111561086357600080fd5b60005b83811015610893578161087988826109bf565b845260208401935060408301925050600181019050610866565b5050505092915050565b600082601f83011215156108b057600080fd5b81356108c36108be82610fac565b610f57565b9150818183526020840193506020810190508360005b8381101561090957813586016108ef8882610a57565b8452602084019350602083019250506001810190506108d9565b5050505092915050565b600082601f830112151561092657600080fd5b813561093961093482610fd4565b610f57565b9150808252602083016020830185838301111561095557600080fd5b6109608382846110b8565b50505092915050565b600082601f830112151561097c57600080fd5b813561098f61098a82611000565b610f57565b915080825260208301602083018583830111156109ab57600080fd5b6109b68382846110b8565b50505092915050565b6000604082840312156109d157600080fd5b6109db6040610f57565b905060006109eb84828501610804565b60008301525060206109ff84828501610abb565b60208301525092915050565b600060408284031215610a1d57600080fd5b610a276040610f57565b90506000610a3784828501610804565b6000830152506020610a4b84828501610abb565b60208301525092915050565b600060408284031215610a6957600080fd5b610a736040610f57565b9050600082013567ffffffffffffffff811115610a8f57600080fd5b610a9b84828501610913565b6000830152506020610aaf84828501610abb565b60208301525092915050565b6000610ac782356110ae565b905092915050565b60008060408385031215610ae257600080fd5b6000610af085828601610804565b925050602083013567ffffffffffffffff811115610b0d57600080fd5b610b1985828601610969565b9150509250929050565b600080600060608486031215610b3857600080fd5b6000610b4686828701610804565b935050602084013567ffffffffffffffff811115610b6357600080fd5b610b6f86828701610969565b9250506040610b8086828701610804565b9150509250925092565b600060208284031215610b9c57600080fd5b600082013567ffffffffffffffff811115610bb657600080fd5b610bc284828501610818565b91505092915050565b600060208284031215610bdd57600080fd5b600082013567ffffffffffffffff811115610bf757600080fd5b610c038482850161089d565b91505092915050565b60008060408385031215610c1f57600080fd5b600083013567ffffffffffffffff811115610c3957600080fd5b610c4585828601610969565b925050602083013567ffffffffffffffff811115610c6257600080fd5b610c6e85828601610969565b9150509250929050565b600060408284031215610c8a57600080fd5b6000610c9884828501610a0b565b91505092915050565b600060208284031215610cb357600080fd5b6000610cc184828501610abb565b91505092915050565b610cd381611064565b82525050565b6000610ce482611036565b83602082028501610cf48561102c565b60005b84811015610d2d578383038852610d0f838351610e16565b9250610d1a82611057565b9150602088019750600181019050610cf7565b508196508694505050505092915050565b6000610d498261104c565b808452610d5d8160208601602086016110c7565b610d66816110fa565b602085010191505092915050565b6000610d7f82611041565b808452610d938160208601602086016110c7565b610d9c816110fa565b602085010191505092915050565b604082016000820151610dc06000850182610cca565b506020820151610dd36020850182610e53565b50505050565b60006040830160008301518482036000860152610df68282610d74565b9150506020830151610e0b6020860182610e53565b508091505092915050565b60006040830160008301518482036000860152610e338282610d74565b9150506020830151610e486020860182610e53565b508091505092915050565b610e5c81611084565b82525050565b6000604082019050610e776000830185610cca565b610e846020830184610e53565b9392505050565b60006020820190508181036000830152610ea58184610cd9565b905092915050565b60006020820190508181036000830152610ec78184610d3e565b905092915050565b60006040820190508181036000830152610ee98185610d74565b9050610ef86020830184610e53565b9392505050565b6000604082019050610f146000830184610daa565b92915050565b60006020820190508181036000830152610f348184610dd9565b905092915050565b6000602082019050610f516000830184610e53565b92915050565b6000604051905081810181811067ffffffffffffffff82111715610f7a57600080fd5b8060405250919050565b600067ffffffffffffffff821115610f9b57600080fd5b602082029050602081019050919050565b600067ffffffffffffffff821115610fc357600080fd5b602082029050602081019050919050565b600067ffffffffffffffff821115610feb57600080fd5b601f19601f8301169050602081019050919050565b600067ffffffffffffffff82111561101757600080fd5b601f19601f8301169050602081019050919050565b6000819050919050565b600060019050919050565b600081519050919050565b600081519050919050565b6000602082019050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b82818337600083830152505050565b60005b838110156110e55780820151818401526020810190506110ca565b838111156110f4576000848401525b50505050565b6000601f19601f83011690509190505600a265627a7a72305820e5152ece3ed6c2786d787d146838f2c12d7ba4e904d610797de0d9f7f32ddc266c6578706572696d656e74616cf50037"

  val constructor = UnitType

  def encodeArgs: Array[Byte] =
    constructor.encode()

  def deployTransactionData: Binary =
    Binary(Hex.toBytes(bin) ++ encodeArgs)

  def deploy[F[_]](sender: TransactionSender[F])(implicit f: Functor[F]): F[Word] =
    sender.sendTransaction(request.Transaction(data = deployTransactionData))

  def deployAndWait[F[_]](sender: TransactionSender[F], poller: TransactionPoller[F])(implicit m: MonadError[F, Throwable]): F[IntegrationTest[F]] =
      poller.waitForTransaction(deploy(sender))
      .map(receipt => new IntegrationTest[F](receipt.contractAddress, sender))
}

case class SimpleEvent(topic: Word, value: String)

object SimpleEvent {
  val event = Event("SimpleEvent", List(StringType, StringType), Tuple1Type(StringType), Tuple1Type(StringType))

  @annotation.varargs def filter(fromBlock: String, toBlock: String, addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList, fromBlock = fromBlock, toBlock = toBlock)

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList)

  def apply(log: response.Log): SimpleEvent = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val topic = log.topics(1)
    val value = decodedData
    SimpleEvent(topic, value)
  }
}

case class AddressEvent(topic: Address, value: String)

object AddressEvent {
  val event = Event("AddressEvent", List(AddressType, StringType), Tuple1Type(AddressType), Tuple1Type(StringType))

  @annotation.varargs def filter(fromBlock: String, toBlock: String, addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList, fromBlock = fromBlock, toBlock = toBlock)

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList)

  def apply(log: response.Log): AddressEvent = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val topic = event.indexed.type1.decode(log.topics(1).bytes, 0).value
    val value = decodedData
    AddressEvent(topic, value)
  }
}

case class MixedEvent(topic: Address, test: Address, value: String)

object MixedEvent {
  val event = Event("MixedEvent", List(AddressType, StringType, AddressType), Tuple2Type(AddressType, AddressType), Tuple1Type(StringType))

  @annotation.varargs def filter(fromBlock: String, toBlock: String, addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList, fromBlock = fromBlock, toBlock = toBlock)

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList)

  def apply(log: response.Log): MixedEvent = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val topic = event.indexed.type1.decode(log.topics(1).bytes, 0).value
    val test = event.indexed.type2.decode(log.topics(2).bytes, 0).value
    val value = decodedData
    MixedEvent(topic, test, value)
  }
}

case class RateEvent(token: Address, value: BigInteger)

object RateEvent {
  val event = Event("RateEvent", List(AddressType, Uint256Type), UnitType, Tuple2Type(AddressType, Uint256Type))

  @annotation.varargs def filter(fromBlock: String, toBlock: String, addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList, fromBlock = fromBlock, toBlock = toBlock)

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList)

  def apply(log: response.Log): RateEvent = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val token = decodedData._1
    val value = decodedData._2
    RateEvent(token, value)
  }
}

case class StringEvent(str: String, value: BigInteger)

object StringEvent {
  val event = Event("StringEvent", List(StringType, Uint256Type), UnitType, Tuple2Type(StringType, Uint256Type))

  @annotation.varargs def filter(fromBlock: String, toBlock: String, addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList, fromBlock = fromBlock, toBlock = toBlock)

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList)

  def apply(log: response.Log): StringEvent = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val str = decodedData._1
    val value = decodedData._2
    StringEvent(str, value)
  }
}

case class TestEvent(value1: BigInteger)

object TestEvent {
  val event = Event("TestEvent", List(Uint256Type), UnitType, Tuple1Type(Uint256Type))

  @annotation.varargs def filter(fromBlock: String, toBlock: String, addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList, fromBlock = fromBlock, toBlock = toBlock)

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList)

  def apply(log: response.Log): TestEvent = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val value1 = decodedData
    TestEvent(value1)
  }
}

case class Test1Event(value1: BigInteger, value2: BigInteger)

object Test1Event {
  val event = Event("TestEvent", List(Uint256Type, Uint256Type), UnitType, Tuple2Type(Uint256Type, Uint256Type))

  @annotation.varargs def filter(fromBlock: String, toBlock: String, addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList, fromBlock = fromBlock, toBlock = toBlock)

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList)

  def apply(log: response.Log): Test1Event = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val value1 = decodedData._1
    val value2 = decodedData._2
    Test1Event(value1, value2)
  }
}

case class Test2Event(value1: BigInteger, value2: BigInteger, value3: BigInteger)

object Test2Event {
  val event = Event("TestEvent", List(Uint256Type, Uint256Type, Uint256Type), UnitType, Tuple3Type(Uint256Type, Uint256Type, Uint256Type))

  @annotation.varargs def filter(fromBlock: String, toBlock: String, addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList, fromBlock = fromBlock, toBlock = toBlock)

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList)

  def apply(log: response.Log): Test2Event = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val value1 = decodedData._1
    val value2 = decodedData._2
    val value3 = decodedData._3
    Test2Event(value1, value2, value3)
  }
}

