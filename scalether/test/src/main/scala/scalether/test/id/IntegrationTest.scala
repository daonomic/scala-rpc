package scalether.test.id

import java.math.BigInteger

import cats.Id
import io.daonomic.cats.implicits._
import io.daonomic.rpc.domain._
import scalether.abi._
import scalether.abi.array._
import scalether.abi.tuple._
import scalether.contract._
import scalether.domain._
import scalether.domain.request._
import scalether.transaction._
import scalether.util.Hex

import scala.language.higherKinds


class IntegrationTest(address: Address, sender: IdTransactionSender)
  extends Contract[Id](address, sender) {

  import IntegrationTest._

  def state: BigInteger =
    IdPreparedTransaction(address, stateSignature, (), sender).call()
  def fallback: IdPreparedTransaction[Unit] =
    new IdPreparedTransaction(address, UnitType, Binary(), sender, BigInteger.ZERO)
  def setState(_state: BigInteger): IdPreparedTransaction[Unit] =
    IdPreparedTransaction(address, setStateSignature, _state, sender)
  def checkStructsWithString(structs: Array[(String, BigInteger)]): IdPreparedTransaction[Unit] =
    IdPreparedTransaction(address, checkStructsWithStringSignature, structs, sender)
  def getStructWithString: (String, BigInteger) =
    IdPreparedTransaction(address, getStructWithStringSignature, (), sender).call()
  def getStructsWithString: Array[(String, BigInteger)] =
    IdPreparedTransaction(address, getStructsWithStringSignature, (), sender).call()
  def setRates(rates: Array[(Address, BigInteger)]): IdPreparedTransaction[Unit] =
    IdPreparedTransaction(address, setRatesSignature, rates, sender)
  def setRate(_rate: (Address, BigInteger)): IdPreparedTransaction[Unit] =
    IdPreparedTransaction(address, setRateSignature, _rate, sender)
  def getRate(test: BigInteger): IdPreparedTransaction[(Address, BigInteger)] =
    IdPreparedTransaction(address, getRateSignature, test, sender)
  def getRate: IdPreparedTransaction[(Address, BigInteger)] =
    IdPreparedTransaction(address, getRate1Signature, (), sender)
  def emitSimpleEvent(topic: String, value: String): IdPreparedTransaction[Unit] =
    IdPreparedTransaction(address, emitSimpleEventSignature, (topic, value), sender)
  def emitAddressEvent(topic: Address, value: String): IdPreparedTransaction[Unit] =
    IdPreparedTransaction(address, emitAddressEventSignature, (topic, value), sender)
  def emitMixedEvent(topic: Address, value: String, test: Address): IdPreparedTransaction[Unit] =
    IdPreparedTransaction(address, emitMixedEventSignature, (topic, value, test), sender)
}

object IntegrationTest extends ContractObject {
  val name = "IntegrationTest"
  val abi = "[{\"name\":\"state\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"constant\":true},{\"type\":\"fallback\",\"inputs\":[],\"outputs\":[],\"payable\":true,\"constant\":false},{\"name\":\"SimpleEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"AddressEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"MixedEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false},{\"name\":\"test\",\"type\":\"address\",\"indexed\":true}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"RateEvent\",\"inputs\":[{\"name\":\"token\",\"type\":\"address\",\"indexed\":false},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"StringEvent\",\"inputs\":[{\"name\":\"str\",\"type\":\"string\",\"indexed\":false},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"TestEvent\",\"inputs\":[{\"name\":\"value1\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"TestEvent\",\"inputs\":[{\"name\":\"value1\",\"type\":\"uint256\",\"indexed\":false},{\"name\":\"value2\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"TestEvent\",\"inputs\":[{\"name\":\"value1\",\"type\":\"uint256\",\"indexed\":false},{\"name\":\"value2\",\"type\":\"uint256\",\"indexed\":false},{\"name\":\"value3\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"setState\",\"type\":\"function\",\"inputs\":[{\"name\":\"_state\",\"type\":\"uint256\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"checkStructsWithString\",\"type\":\"function\",\"inputs\":[{\"name\":\"structs\",\"type\":\"tuple[]\",\"components\":[{\"name\":\"str\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"getStructWithString\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"tuple\",\"components\":[{\"name\":\"str\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"payable\":false,\"constant\":true},{\"name\":\"getStructsWithString\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"tuple[1]\",\"components\":[{\"name\":\"str\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"payable\":false,\"constant\":true},{\"name\":\"setRates\",\"type\":\"function\",\"inputs\":[{\"name\":\"rates\",\"type\":\"tuple[]\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"setRate\",\"type\":\"function\",\"inputs\":[{\"name\":\"_rate\",\"type\":\"tuple\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"getRate\",\"type\":\"function\",\"inputs\":[{\"name\":\"test\",\"type\":\"uint256\"}],\"outputs\":[{\"name\":\"\",\"type\":\"tuple\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"payable\":false,\"constant\":false},{\"name\":\"getRate\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"tuple\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"payable\":false,\"constant\":false},{\"name\":\"emitSimpleEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"string\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"emitAddressEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"string\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"emitMixedEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"string\"},{\"name\":\"test\",\"type\":\"address\"}],\"outputs\":[],\"payable\":false,\"constant\":false}]"
  val bin = "0x608060405234801561001057600080fd5b5061114b806100206000396000f3006080604052600436106100ba576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806329dc7efe146100c55780633cb7bce8146100ee57806345e73e65146101175780635776409414610142578063679aefce1461017f57806375e8660d146101aa5780637ad8748f146101d3578063a9e966b7146101fe578063b710cc7c14610227578063c19d93fb14610250578063c4be9b301461027b578063d1c58fbd146102a4575b612710600081905550005b3480156100d157600080fd5b506100ec60048036036100e79190810190610c12565b6102cd565b005b3480156100fa57600080fd5b5061011560048036036101109190810190610bd1565b61036b565b005b34801561012357600080fd5b5061012c610400565b6040516101399190610e91565b60405180910390f35b34801561014e57600080fd5b5061016960048036036101649190810190610ca7565b610454565b6040516101769190610f05565b60405180910390f35b34801561018b57600080fd5b506101946104d0565b6040516101a19190610f05565b60405180910390f35b3480156101b657600080fd5b506101d160048036036101cc9190810190610b29565b61054a565b005b3480156101df57600080fd5b506101e86105b4565b6040516101f59190610f20565b60405180910390f35b34801561020a57600080fd5b5061022560048036036102209190810190610ca7565b6105e7565b005b34801561023357600080fd5b5061024e60048036036102499190810190610b90565b6105f1565b005b34801561025c57600080fd5b50610265610686565b6040516102729190610f42565b60405180910390f35b34801561028757600080fd5b506102a2600480360361029d9190810190610c7e565b61068c565b005b3480156102b057600080fd5b506102cb60048036036102c69190810190610ad5565b610727565b005b816040518082805190602001908083835b60208310151561030357805182526020820191506020810190506020830392506102de565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390207fdf07c14ba7c542332df3cebcac73245d796909b2be2b2aed13f40d5b23136cd08260405161035f9190610eb3565b60405180910390a25050565b60008082519150600090505b818110156103fb577f6fc10800b7deda6aea9a2a89968fd690847169903420807806184ae95e93693383828151811015156103ae57fe5b906020019060200201516000015184838151811015156103ca57fe5b90602001906020020151602001516040516103e6929190610ed5565b60405180910390a18080600101915050610377565b505050565b610408610779565b610410610779565b604080519081016040528060206040519081016040528060008152508152602001600081525081600060018110151561044557fe5b60200201819052508091505090565b61045c6107a6565b60016040805190810160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820154815250509050919050565b6104d86107a6565b60016040805190810160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001600182015481525050905090565b8073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff167f0f5e2a2282931e0c4b5f2319a04deb79518c3127921c3f70d2fe9306563dd167846040516105a79190610eb3565b60405180910390a3505050565b6105bc6107d6565b6040805190810160405280602060405190810160405280600081525081526020016000815250905090565b8060008190555050565b60008082519150600090505b81811015610681577ff6cc106d4ff63d31aa62a665afe317068b92fc2bda4688afe33635cfff444749838281518110151561063457fe5b9060200190602002015160000151848381518110151561065057fe5b906020019060200201516020015160405161066c929190610e68565b60405180910390a180806001019150506105fd565b505050565b60005481565b80600160008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550602082015181600101559050507ff6cc106d4ff63d31aa62a665afe317068b92fc2bda4688afe33635cfff4447498160000151826020015160405161071c929190610e68565b60405180910390a150565b8173ffffffffffffffffffffffffffffffffffffffff167f2d1926fdeec1e1c3ef245ded9ff04522b3e5e14f0a57a12efe35949d72bf661e8260405161076d9190610eb3565b60405180910390a25050565b60408051908101604052806001905b6107906107f0565b8152602001906001900390816107885790505090565b6040805190810160405280600073ffffffffffffffffffffffffffffffffffffffff168152602001600081525090565b604080519081016040528060608152602001600081525090565b604080519081016040528060608152602001600081525090565b60006108168235611094565b905092915050565b600082601f830112151561083157600080fd5b813561084461083f82610f8a565b610f5d565b9150818183526020840193506020810190508385604084028201111561086957600080fd5b60005b83811015610899578161087f88826109c5565b84526020840193506040830192505060018101905061086c565b5050505092915050565b600082601f83011215156108b657600080fd5b81356108c96108c482610fb2565b610f5d565b9150818183526020840193506020810190508360005b8381101561090f57813586016108f58882610a5d565b8452602084019350602083019250506001810190506108df565b5050505092915050565b600082601f830112151561092c57600080fd5b813561093f61093a82610fda565b610f5d565b9150808252602083016020830185838301111561095b57600080fd5b6109668382846110be565b50505092915050565b600082601f830112151561098257600080fd5b813561099561099082611006565b610f5d565b915080825260208301602083018583830111156109b157600080fd5b6109bc8382846110be565b50505092915050565b6000604082840312156109d757600080fd5b6109e16040610f5d565b905060006109f18482850161080a565b6000830152506020610a0584828501610ac1565b60208301525092915050565b600060408284031215610a2357600080fd5b610a2d6040610f5d565b90506000610a3d8482850161080a565b6000830152506020610a5184828501610ac1565b60208301525092915050565b600060408284031215610a6f57600080fd5b610a796040610f5d565b9050600082013567ffffffffffffffff811115610a9557600080fd5b610aa184828501610919565b6000830152506020610ab584828501610ac1565b60208301525092915050565b6000610acd82356110b4565b905092915050565b60008060408385031215610ae857600080fd5b6000610af68582860161080a565b925050602083013567ffffffffffffffff811115610b1357600080fd5b610b1f8582860161096f565b9150509250929050565b600080600060608486031215610b3e57600080fd5b6000610b4c8682870161080a565b935050602084013567ffffffffffffffff811115610b6957600080fd5b610b758682870161096f565b9250506040610b868682870161080a565b9150509250925092565b600060208284031215610ba257600080fd5b600082013567ffffffffffffffff811115610bbc57600080fd5b610bc88482850161081e565b91505092915050565b600060208284031215610be357600080fd5b600082013567ffffffffffffffff811115610bfd57600080fd5b610c09848285016108a3565b91505092915050565b60008060408385031215610c2557600080fd5b600083013567ffffffffffffffff811115610c3f57600080fd5b610c4b8582860161096f565b925050602083013567ffffffffffffffff811115610c6857600080fd5b610c748582860161096f565b9150509250929050565b600060408284031215610c9057600080fd5b6000610c9e84828501610a11565b91505092915050565b600060208284031215610cb957600080fd5b6000610cc784828501610ac1565b91505092915050565b610cd98161106a565b82525050565b6000610cea8261103c565b83602082028501610cfa85611032565b60005b84811015610d33578383038852610d15838351610e1c565b9250610d208261105d565b9150602088019750600181019050610cfd565b508196508694505050505092915050565b6000610d4f82611052565b808452610d638160208601602086016110cd565b610d6c81611100565b602085010191505092915050565b6000610d8582611047565b808452610d998160208601602086016110cd565b610da281611100565b602085010191505092915050565b604082016000820151610dc66000850182610cd0565b506020820151610dd96020850182610e59565b50505050565b60006040830160008301518482036000860152610dfc8282610d7a565b9150506020830151610e116020860182610e59565b508091505092915050565b60006040830160008301518482036000860152610e398282610d7a565b9150506020830151610e4e6020860182610e59565b508091505092915050565b610e628161108a565b82525050565b6000604082019050610e7d6000830185610cd0565b610e8a6020830184610e59565b9392505050565b60006020820190508181036000830152610eab8184610cdf565b905092915050565b60006020820190508181036000830152610ecd8184610d44565b905092915050565b60006040820190508181036000830152610eef8185610d7a565b9050610efe6020830184610e59565b9392505050565b6000604082019050610f1a6000830184610db0565b92915050565b60006020820190508181036000830152610f3a8184610ddf565b905092915050565b6000602082019050610f576000830184610e59565b92915050565b6000604051905081810181811067ffffffffffffffff82111715610f8057600080fd5b8060405250919050565b600067ffffffffffffffff821115610fa157600080fd5b602082029050602081019050919050565b600067ffffffffffffffff821115610fc957600080fd5b602082029050602081019050919050565b600067ffffffffffffffff821115610ff157600080fd5b601f19601f8301169050602081019050919050565b600067ffffffffffffffff82111561101d57600080fd5b601f19601f8301169050602081019050919050565b6000819050919050565b600060019050919050565b600081519050919050565b600081519050919050565b6000602082019050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b82818337600083830152505050565b60005b838110156110eb5780820151818401526020810190506110d0565b838111156110fa576000848401525b50505050565b6000601f19601f83011690509190505600a265627a7a72305820e162c3f21e51d5f8e9b1202e8a03f25c49d7139892740df9fc7fbfa6240b53516c6578706572696d656e74616cf50037"

  val constructor = UnitType

  def encodeArgs: Binary =
    constructor.encode()

  def deployTransactionData: Binary =
    Binary(Hex.toBytes(bin)) ++ encodeArgs

  def deploy(sender: IdTransactionSender): Word =
    sender.sendTransaction(request.Transaction(data = deployTransactionData))

  def deployAndWait(sender: IdTransactionSender, poller: IdTransactionPoller): IntegrationTest = {
    val receipt = poller.waitForTransaction(deploy(sender))
    new IntegrationTest(receipt.contractAddress, sender)
  }

  val stateSignature = Signature("state", UnitType, Tuple1Type(Uint256Type))
  val setStateSignature = Signature("setState", Tuple1Type(Uint256Type), UnitType)
  val checkStructsWithStringSignature = Signature("checkStructsWithString", Tuple1Type(VarArrayType(Tuple2Type(StringType, Uint256Type))), UnitType)
  val getStructWithStringSignature = Signature("getStructWithString", UnitType, Tuple1Type(Tuple2Type(StringType, Uint256Type)))
  val getStructsWithStringSignature = Signature("getStructsWithString", UnitType, Tuple1Type(FixArrayType(1, Tuple2Type(StringType, Uint256Type))))
  val setRatesSignature = Signature("setRates", Tuple1Type(VarArrayType(Tuple2Type(AddressType, Uint256Type))), UnitType)
  val setRateSignature = Signature("setRate", Tuple1Type(Tuple2Type(AddressType, Uint256Type)), UnitType)
  val getRateSignature = Signature("getRate", Tuple1Type(Uint256Type), Tuple1Type(Tuple2Type(AddressType, Uint256Type)))
  val getRate1Signature = Signature("getRate", UnitType, Tuple1Type(Tuple2Type(AddressType, Uint256Type)))
  val emitSimpleEventSignature = Signature("emitSimpleEvent", Tuple2Type(StringType, StringType), UnitType)
  val emitAddressEventSignature = Signature("emitAddressEvent", Tuple2Type(AddressType, StringType), UnitType)
  val emitMixedEventSignature = Signature("emitMixedEvent", Tuple3Type(AddressType, StringType, AddressType), UnitType)
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
    val topic = event.indexed.type1.decode(log.topics(1), 0).value
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
    val topic = event.indexed.type1.decode(log.topics(1), 0).value
    val test = event.indexed.type2.decode(log.topics(2), 0).value
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

