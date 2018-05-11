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

  def state: F[BigInteger] =
    PreparedTransaction(address, Signature("state", UnitType, Tuple1Type(Uint256Type)), (), sender).call()

  def setState(_state: BigInteger): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, Signature("setState", Tuple1Type(Uint256Type), UnitType), _state, sender)

  def checkStructsWithString(structs: Array[(String, BigInteger)]): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, Signature("checkStructsWithString", Tuple1Type(VarArrayType(Tuple2Type(StringType, Uint256Type))), UnitType), structs, sender)

  def getStructWithString: F[(String, BigInteger)] =
    PreparedTransaction(address, Signature("getStructWithString", UnitType, Tuple1Type(Tuple2Type(StringType, Uint256Type))), (), sender).call()

  def getStructsWithString: F[Array[(String, BigInteger)]] =
    PreparedTransaction(address, Signature("getStructsWithString", UnitType, Tuple1Type(FixArrayType(1, Tuple2Type(StringType, Uint256Type)))), (), sender).call()

  def setRates(rates: Array[(Address, BigInteger)]): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, Signature("setRates", Tuple1Type(VarArrayType(Tuple2Type(AddressType, Uint256Type))), UnitType), rates, sender)

  def setRate(_rate: (Address, BigInteger)): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, Signature("setRate", Tuple1Type(Tuple2Type(AddressType, Uint256Type)), UnitType), _rate, sender)

  def getRate: PreparedTransaction[F, (Address, BigInteger)] =
    PreparedTransaction(address, Signature("getRate", UnitType, Tuple1Type(Tuple2Type(AddressType, Uint256Type))), (), sender)

  def emitSimpleEvent(topic: String, value: String): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, Signature("emitSimpleEvent", Tuple2Type(StringType, StringType), UnitType), (topic, value), sender)

  def emitAddressEvent(topic: Address, value: String): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, Signature("emitAddressEvent", Tuple2Type(AddressType, StringType), UnitType), (topic, value), sender)

  def emitMixedEvent(topic: Address, value: String, test: Address): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, Signature("emitMixedEvent", Tuple3Type(AddressType, StringType, AddressType), UnitType), (topic, value, test), sender)

}

object IntegrationTest extends ContractObject {
  val name = "IntegrationTest"
  val abi = "[{\"name\":\"state\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"constant\":true},{\"name\":\"SimpleEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"AddressEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"MixedEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false},{\"name\":\"test\",\"type\":\"address\",\"indexed\":true}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"RateEvent\",\"inputs\":[{\"name\":\"token\",\"type\":\"address\",\"indexed\":false},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"StringEvent\",\"inputs\":[{\"name\":\"str\",\"type\":\"string\",\"indexed\":false},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"TestEvent\",\"inputs\":[{\"name\":\"value1\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"TestEvent\",\"inputs\":[{\"name\":\"value1\",\"type\":\"uint256\",\"indexed\":false},{\"name\":\"value2\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"TestEvent\",\"inputs\":[{\"name\":\"value1\",\"type\":\"uint256\",\"indexed\":false},{\"name\":\"value2\",\"type\":\"uint256\",\"indexed\":false},{\"name\":\"value3\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"setState\",\"type\":\"function\",\"inputs\":[{\"name\":\"_state\",\"type\":\"uint256\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"checkStructsWithString\",\"type\":\"function\",\"inputs\":[{\"name\":\"structs\",\"type\":\"tuple[]\",\"components\":[{\"name\":\"str\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"getStructWithString\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"tuple\",\"components\":[{\"name\":\"str\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"payable\":false,\"constant\":true},{\"name\":\"getStructsWithString\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"tuple[1]\",\"components\":[{\"name\":\"str\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"payable\":false,\"constant\":true},{\"name\":\"setRates\",\"type\":\"function\",\"inputs\":[{\"name\":\"rates\",\"type\":\"tuple[]\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"setRate\",\"type\":\"function\",\"inputs\":[{\"name\":\"_rate\",\"type\":\"tuple\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"getRate\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"tuple\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"payable\":false,\"constant\":false},{\"name\":\"emitSimpleEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"string\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"emitAddressEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"string\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"emitMixedEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"string\"},{\"name\":\"test\",\"type\":\"address\"}],\"outputs\":[],\"payable\":false,\"constant\":false}]"
  val bin = "0x608060405234801561001057600080fd5b50611081806100206000396000f3006080604052600436106100af576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806329dc7efe146100b45780633cb7bce8146100dd57806345e73e6514610106578063679aefce1461013157806375e8660d1461015c5780637ad8748f14610185578063a9e966b7146101b0578063b710cc7c146101d9578063c19d93fb14610202578063c4be9b301461022d578063d1c58fbd14610256575b600080fd5b3480156100c057600080fd5b506100db60048036036100d69190810190610b48565b61027f565b005b3480156100e957600080fd5b5061010460048036036100ff9190810190610b07565b61031d565b005b34801561011257600080fd5b5061011b6103b2565b6040516101289190610dc7565b60405180910390f35b34801561013d57600080fd5b50610146610406565b6040516101539190610e3b565b60405180910390f35b34801561016857600080fd5b50610183600480360361017e9190810190610a5f565b610480565b005b34801561019157600080fd5b5061019a6104ea565b6040516101a79190610e56565b60405180910390f35b3480156101bc57600080fd5b506101d760048036036101d29190810190610bdd565b61051d565b005b3480156101e557600080fd5b5061020060048036036101fb9190810190610ac6565b610527565b005b34801561020e57600080fd5b506102176105bc565b6040516102249190610e78565b60405180910390f35b34801561023957600080fd5b50610254600480360361024f9190810190610bb4565b6105c2565b005b34801561026257600080fd5b5061027d60048036036102789190810190610a0b565b61065d565b005b816040518082805190602001908083835b6020831015156102b55780518252602082019150602081019050602083039250610290565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390207fdf07c14ba7c542332df3cebcac73245d796909b2be2b2aed13f40d5b23136cd0826040516103119190610de9565b60405180910390a25050565b60008082519150600090505b818110156103ad577f6fc10800b7deda6aea9a2a89968fd690847169903420807806184ae95e936933838281518110151561036057fe5b9060200190602002015160000151848381518110151561037c57fe5b9060200190602002015160200151604051610398929190610e0b565b60405180910390a18080600101915050610329565b505050565b6103ba6106af565b6103c26106af565b60408051908101604052806020604051908101604052806000815250815260200160008152508160006001811015156103f757fe5b60200201819052508091505090565b61040e6106dc565b60016040805190810160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001600182015481525050905090565b8073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff167f0f5e2a2282931e0c4b5f2319a04deb79518c3127921c3f70d2fe9306563dd167846040516104dd9190610de9565b60405180910390a3505050565b6104f261070c565b6040805190810160405280602060405190810160405280600081525081526020016000815250905090565b8060008190555050565b60008082519150600090505b818110156105b7577ff6cc106d4ff63d31aa62a665afe317068b92fc2bda4688afe33635cfff444749838281518110151561056a57fe5b9060200190602002015160000151848381518110151561058657fe5b90602001906020020151602001516040516105a2929190610d9e565b60405180910390a18080600101915050610533565b505050565b60005481565b80600160008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550602082015181600101559050507ff6cc106d4ff63d31aa62a665afe317068b92fc2bda4688afe33635cfff44474981600001518260200151604051610652929190610d9e565b60405180910390a150565b8173ffffffffffffffffffffffffffffffffffffffff167f2d1926fdeec1e1c3ef245ded9ff04522b3e5e14f0a57a12efe35949d72bf661e826040516106a39190610de9565b60405180910390a25050565b60408051908101604052806001905b6106c6610726565b8152602001906001900390816106be5790505090565b6040805190810160405280600073ffffffffffffffffffffffffffffffffffffffff168152602001600081525090565b604080519081016040528060608152602001600081525090565b604080519081016040528060608152602001600081525090565b600061074c8235610fca565b905092915050565b600082601f830112151561076757600080fd5b813561077a61077582610ec0565b610e93565b9150818183526020840193506020810190508385604084028201111561079f57600080fd5b60005b838110156107cf57816107b588826108fb565b8452602084019350604083019250506001810190506107a2565b5050505092915050565b600082601f83011215156107ec57600080fd5b81356107ff6107fa82610ee8565b610e93565b9150818183526020840193506020810190508360005b83811015610845578135860161082b8882610993565b845260208401935060208301925050600181019050610815565b5050505092915050565b600082601f830112151561086257600080fd5b813561087561087082610f10565b610e93565b9150808252602083016020830185838301111561089157600080fd5b61089c838284610ff4565b50505092915050565b600082601f83011215156108b857600080fd5b81356108cb6108c682610f3c565b610e93565b915080825260208301602083018583830111156108e757600080fd5b6108f2838284610ff4565b50505092915050565b60006040828403121561090d57600080fd5b6109176040610e93565b9050600061092784828501610740565b600083015250602061093b848285016109f7565b60208301525092915050565b60006040828403121561095957600080fd5b6109636040610e93565b9050600061097384828501610740565b6000830152506020610987848285016109f7565b60208301525092915050565b6000604082840312156109a557600080fd5b6109af6040610e93565b9050600082013567ffffffffffffffff8111156109cb57600080fd5b6109d78482850161084f565b60008301525060206109eb848285016109f7565b60208301525092915050565b6000610a038235610fea565b905092915050565b60008060408385031215610a1e57600080fd5b6000610a2c85828601610740565b925050602083013567ffffffffffffffff811115610a4957600080fd5b610a55858286016108a5565b9150509250929050565b600080600060608486031215610a7457600080fd5b6000610a8286828701610740565b935050602084013567ffffffffffffffff811115610a9f57600080fd5b610aab868287016108a5565b9250506040610abc86828701610740565b9150509250925092565b600060208284031215610ad857600080fd5b600082013567ffffffffffffffff811115610af257600080fd5b610afe84828501610754565b91505092915050565b600060208284031215610b1957600080fd5b600082013567ffffffffffffffff811115610b3357600080fd5b610b3f848285016107d9565b91505092915050565b60008060408385031215610b5b57600080fd5b600083013567ffffffffffffffff811115610b7557600080fd5b610b81858286016108a5565b925050602083013567ffffffffffffffff811115610b9e57600080fd5b610baa858286016108a5565b9150509250929050565b600060408284031215610bc657600080fd5b6000610bd484828501610947565b91505092915050565b600060208284031215610bef57600080fd5b6000610bfd848285016109f7565b91505092915050565b610c0f81610fa0565b82525050565b6000610c2082610f72565b83602082028501610c3085610f68565b60005b84811015610c69578383038852610c4b838351610d52565b9250610c5682610f93565b9150602088019750600181019050610c33565b508196508694505050505092915050565b6000610c8582610f88565b808452610c99816020860160208601611003565b610ca281611036565b602085010191505092915050565b6000610cbb82610f7d565b808452610ccf816020860160208601611003565b610cd881611036565b602085010191505092915050565b604082016000820151610cfc6000850182610c06565b506020820151610d0f6020850182610d8f565b50505050565b60006040830160008301518482036000860152610d328282610cb0565b9150506020830151610d476020860182610d8f565b508091505092915050565b60006040830160008301518482036000860152610d6f8282610cb0565b9150506020830151610d846020860182610d8f565b508091505092915050565b610d9881610fc0565b82525050565b6000604082019050610db36000830185610c06565b610dc06020830184610d8f565b9392505050565b60006020820190508181036000830152610de18184610c15565b905092915050565b60006020820190508181036000830152610e038184610c7a565b905092915050565b60006040820190508181036000830152610e258185610cb0565b9050610e346020830184610d8f565b9392505050565b6000604082019050610e506000830184610ce6565b92915050565b60006020820190508181036000830152610e708184610d15565b905092915050565b6000602082019050610e8d6000830184610d8f565b92915050565b6000604051905081810181811067ffffffffffffffff82111715610eb657600080fd5b8060405250919050565b600067ffffffffffffffff821115610ed757600080fd5b602082029050602081019050919050565b600067ffffffffffffffff821115610eff57600080fd5b602082029050602081019050919050565b600067ffffffffffffffff821115610f2757600080fd5b601f19601f8301169050602081019050919050565b600067ffffffffffffffff821115610f5357600080fd5b601f19601f8301169050602081019050919050565b6000819050919050565b600060019050919050565b600081519050919050565b600081519050919050565b6000602082019050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b82818337600083830152505050565b60005b83811015611021578082015181840152602081019050611006565b83811115611030576000848401525b50505050565b6000601f19601f83011690509190505600a265627a7a72305820159ec02e6a4e96f6c769638e2dffd9d9f60aac98279afa8927d1bc4bff3178006c6578706572696d656e74616cf50037"

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

