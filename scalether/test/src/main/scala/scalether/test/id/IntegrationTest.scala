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
  def checkUintArrayAbi(array: Array[BigInteger]): IdPreparedTransaction[Unit] =
    IdPreparedTransaction(address, checkUintArrayAbiSignature, array, sender)
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
  val abi = "[{\"name\":\"state\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"constant\":true},{\"type\":\"fallback\",\"inputs\":[],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"SimpleEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"AddressEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"MixedEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false},{\"name\":\"test\",\"type\":\"address\",\"indexed\":true}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"RateEvent\",\"inputs\":[{\"name\":\"token\",\"type\":\"address\",\"indexed\":false},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"StringEvent\",\"inputs\":[{\"name\":\"str\",\"type\":\"string\",\"indexed\":false},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"TestEvent\",\"inputs\":[{\"name\":\"value1\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"TestEvent\",\"inputs\":[{\"name\":\"value1\",\"type\":\"uint256\",\"indexed\":false},{\"name\":\"value2\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"TestEvent\",\"inputs\":[{\"name\":\"value1\",\"type\":\"uint256\",\"indexed\":false},{\"name\":\"value2\",\"type\":\"uint256\",\"indexed\":false},{\"name\":\"value3\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"setState\",\"type\":\"function\",\"inputs\":[{\"name\":\"_state\",\"type\":\"uint256\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"checkUintArrayAbi\",\"type\":\"function\",\"inputs\":[{\"name\":\"array\",\"type\":\"uint256[]\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"checkStructsWithString\",\"type\":\"function\",\"inputs\":[{\"name\":\"structs\",\"type\":\"tuple[]\",\"components\":[{\"name\":\"str\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"getStructWithString\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"tuple\",\"components\":[{\"name\":\"str\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"payable\":false,\"constant\":true},{\"name\":\"getStructsWithString\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"tuple[1]\",\"components\":[{\"name\":\"str\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"payable\":false,\"constant\":true},{\"name\":\"setRates\",\"type\":\"function\",\"inputs\":[{\"name\":\"rates\",\"type\":\"tuple[]\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"setRate\",\"type\":\"function\",\"inputs\":[{\"name\":\"_rate\",\"type\":\"tuple\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"getRate\",\"type\":\"function\",\"inputs\":[{\"name\":\"test\",\"type\":\"uint256\"}],\"outputs\":[{\"name\":\"\",\"type\":\"tuple\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"payable\":false,\"constant\":false},{\"name\":\"getRate\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"tuple\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"payable\":false,\"constant\":false},{\"name\":\"emitSimpleEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"string\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"emitAddressEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"string\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"emitMixedEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"string\"},{\"name\":\"test\",\"type\":\"address\"}],\"outputs\":[],\"payable\":false,\"constant\":false}]"
  val bin = "0x608060405234801561001057600080fd5b5061127d806100206000396000f3006080604052600436106100c5576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806329dc7efe146100dd5780633cb7bce81461010657806345e73e651461012f578063577640941461015a578063679aefce1461019757806375e8660d146101c25780637ad8748f146101eb5780638a8bbc6514610216578063a9e966b71461023f578063b710cc7c14610268578063c19d93fb14610291578063c4be9b30146102bc578063d1c58fbd146102e5575b3480156100d157600080fd5b50612710600081905550005b3480156100e957600080fd5b5061010460048036036100ff9190810190610d1c565b61030e565b005b34801561011257600080fd5b5061012d60048036036101289190810190610c9a565b6103ac565b005b34801561013b57600080fd5b50610144610441565b6040516101519190610f9b565b60405180910390f35b34801561016657600080fd5b50610181600480360361017c9190810190610db1565b610495565b60405161018e919061100f565b60405180910390f35b3480156101a357600080fd5b506101ac610511565b6040516101b9919061100f565b60405180910390f35b3480156101ce57600080fd5b506101e960048036036101e49190810190610bf2565b61058b565b005b3480156101f757600080fd5b506102006105f5565b60405161020d919061102a565b60405180910390f35b34801561022257600080fd5b5061023d60048036036102389190810190610cdb565b610628565b005b34801561024b57600080fd5b5061026660048036036102619190810190610db1565b61062b565b005b34801561027457600080fd5b5061028f600480360361028a9190810190610c59565b610635565b005b34801561029d57600080fd5b506102a66106ca565b6040516102b3919061104c565b60405180910390f35b3480156102c857600080fd5b506102e360048036036102de9190810190610d88565b6106d0565b005b3480156102f157600080fd5b5061030c60048036036103079190810190610b9e565b61076b565b005b816040518082805190602001908083835b602083101515610344578051825260208201915060208101905060208303925061031f565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390207fdf07c14ba7c542332df3cebcac73245d796909b2be2b2aed13f40d5b23136cd0826040516103a09190610fbd565b60405180910390a25050565b60008082519150600090505b8181101561043c577f6fc10800b7deda6aea9a2a89968fd690847169903420807806184ae95e93693383828151811015156103ef57fe5b9060200190602002015160000151848381518110151561040b57fe5b9060200190602002015160200151604051610427929190610fdf565b60405180910390a180806001019150506103b8565b505050565b6104496107bd565b6104516107bd565b604080519081016040528060206040519081016040528060008152508152602001600081525081600060018110151561048657fe5b60200201819052508091505090565b61049d6107ea565b60016040805190810160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820154815250509050919050565b6105196107ea565b60016040805190810160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001600182015481525050905090565b8073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff167f0f5e2a2282931e0c4b5f2319a04deb79518c3127921c3f70d2fe9306563dd167846040516105e89190610fbd565b60405180910390a3505050565b6105fd61081a565b6040805190810160405280602060405190810160405280600081525081526020016000815250905090565b50565b8060008190555050565b60008082519150600090505b818110156106c5577ff6cc106d4ff63d31aa62a665afe317068b92fc2bda4688afe33635cfff444749838281518110151561067857fe5b9060200190602002015160000151848381518110151561069457fe5b90602001906020020151602001516040516106b0929190610f72565b60405180910390a18080600101915050610641565b505050565b60005481565b80600160008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550602082015181600101559050507ff6cc106d4ff63d31aa62a665afe317068b92fc2bda4688afe33635cfff44474981600001518260200151604051610760929190610f72565b60405180910390a150565b8173ffffffffffffffffffffffffffffffffffffffff167f2d1926fdeec1e1c3ef245ded9ff04522b3e5e14f0a57a12efe35949d72bf661e826040516107b19190610fbd565b60405180910390a25050565b60408051908101604052806001905b6107d4610834565b8152602001906001900390816107cc5790505090565b6040805190810160405280600073ffffffffffffffffffffffffffffffffffffffff168152602001600081525090565b604080519081016040528060608152602001600081525090565b604080519081016040528060608152602001600081525090565b600061085a82356111c6565b905092915050565b600082601f830112151561087557600080fd5b813561088861088382611094565b611067565b915081818352602084019350602081019050838560408402820111156108ad57600080fd5b60005b838110156108dd57816108c38882610a8e565b8452602084019350604083019250506001810190506108b0565b5050505092915050565b600082601f83011215156108fa57600080fd5b813561090d610908826110bc565b611067565b9150818183526020840193506020810190508360005b8381101561095357813586016109398882610b26565b845260208401935060208301925050600181019050610923565b5050505092915050565b600082601f830112151561097057600080fd5b813561098361097e826110e4565b611067565b915081818352602084019350602081019050838560208402820111156109a857600080fd5b60005b838110156109d857816109be8882610b8a565b8452602084019350602083019250506001810190506109ab565b5050505092915050565b600082601f83011215156109f557600080fd5b8135610a08610a038261110c565b611067565b91508082526020830160208301858383011115610a2457600080fd5b610a2f8382846111f0565b50505092915050565b600082601f8301121515610a4b57600080fd5b8135610a5e610a5982611138565b611067565b91508082526020830160208301858383011115610a7a57600080fd5b610a858382846111f0565b50505092915050565b600060408284031215610aa057600080fd5b610aaa6040611067565b90506000610aba8482850161084e565b6000830152506020610ace84828501610b8a565b60208301525092915050565b600060408284031215610aec57600080fd5b610af66040611067565b90506000610b068482850161084e565b6000830152506020610b1a84828501610b8a565b60208301525092915050565b600060408284031215610b3857600080fd5b610b426040611067565b9050600082013567ffffffffffffffff811115610b5e57600080fd5b610b6a848285016109e2565b6000830152506020610b7e84828501610b8a565b60208301525092915050565b6000610b9682356111e6565b905092915050565b60008060408385031215610bb157600080fd5b6000610bbf8582860161084e565b925050602083013567ffffffffffffffff811115610bdc57600080fd5b610be885828601610a38565b9150509250929050565b600080600060608486031215610c0757600080fd5b6000610c158682870161084e565b935050602084013567ffffffffffffffff811115610c3257600080fd5b610c3e86828701610a38565b9250506040610c4f8682870161084e565b9150509250925092565b600060208284031215610c6b57600080fd5b600082013567ffffffffffffffff811115610c8557600080fd5b610c9184828501610862565b91505092915050565b600060208284031215610cac57600080fd5b600082013567ffffffffffffffff811115610cc657600080fd5b610cd2848285016108e7565b91505092915050565b600060208284031215610ced57600080fd5b600082013567ffffffffffffffff811115610d0757600080fd5b610d138482850161095d565b91505092915050565b60008060408385031215610d2f57600080fd5b600083013567ffffffffffffffff811115610d4957600080fd5b610d5585828601610a38565b925050602083013567ffffffffffffffff811115610d7257600080fd5b610d7e85828601610a38565b9150509250929050565b600060408284031215610d9a57600080fd5b6000610da884828501610ada565b91505092915050565b600060208284031215610dc357600080fd5b6000610dd184828501610b8a565b91505092915050565b610de38161119c565b82525050565b6000610df48261116e565b83602082028501610e0485611164565b60005b84811015610e3d578383038852610e1f838351610f26565b9250610e2a8261118f565b9150602088019750600181019050610e07565b508196508694505050505092915050565b6000610e5982611184565b808452610e6d8160208601602086016111ff565b610e7681611232565b602085010191505092915050565b6000610e8f82611179565b808452610ea38160208601602086016111ff565b610eac81611232565b602085010191505092915050565b604082016000820151610ed06000850182610dda565b506020820151610ee36020850182610f63565b50505050565b60006040830160008301518482036000860152610f068282610e84565b9150506020830151610f1b6020860182610f63565b508091505092915050565b60006040830160008301518482036000860152610f438282610e84565b9150506020830151610f586020860182610f63565b508091505092915050565b610f6c816111bc565b82525050565b6000604082019050610f876000830185610dda565b610f946020830184610f63565b9392505050565b60006020820190508181036000830152610fb58184610de9565b905092915050565b60006020820190508181036000830152610fd78184610e4e565b905092915050565b60006040820190508181036000830152610ff98185610e84565b90506110086020830184610f63565b9392505050565b60006040820190506110246000830184610eba565b92915050565b600060208201905081810360008301526110448184610ee9565b905092915050565b60006020820190506110616000830184610f63565b92915050565b6000604051905081810181811067ffffffffffffffff8211171561108a57600080fd5b8060405250919050565b600067ffffffffffffffff8211156110ab57600080fd5b602082029050602081019050919050565b600067ffffffffffffffff8211156110d357600080fd5b602082029050602081019050919050565b600067ffffffffffffffff8211156110fb57600080fd5b602082029050602081019050919050565b600067ffffffffffffffff82111561112357600080fd5b601f19601f8301169050602081019050919050565b600067ffffffffffffffff82111561114f57600080fd5b601f19601f8301169050602081019050919050565b6000819050919050565b600060019050919050565b600081519050919050565b600081519050919050565b6000602082019050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b82818337600083830152505050565b60005b8381101561121d578082015181840152602081019050611202565b8381111561122c576000848401525b50505050565b6000601f19601f83011690509190505600a265627a7a723058201e7b31cc3ea4dbb2cd019276fb0226672ad9a03b9770ed72c8b87923b09473e26c6578706572696d656e74616cf50037"

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
  val checkUintArrayAbiSignature = Signature("checkUintArrayAbi", Tuple1Type(VarArrayType(Uint256Type)), UnitType)
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

