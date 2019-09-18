package scalether.test

import java.math.BigInteger

import cats.Functor
import io.daonomic.cats.MonadThrowable
import cats.implicits._
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


class IntegrationTest[F[_]](address: Address, sender: TransactionSender[F])(implicit f: MonadThrowable[F])
  extends Contract[F](address, sender) {

  import IntegrationTest._

  def state: F[BigInteger] =
    PreparedTransaction(address, stateSignature, (), sender, description = "state").call()
  def fallback: PreparedTransaction[F, Unit] =
    new PreparedTransaction(address, UnitType, Binary(), sender, BigInteger.ZERO, description = "fallback")
  def setState(_state: BigInteger): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, setStateSignature, _state, sender, description = "setState")
  def checkUintArrayAbi(array: Array[BigInteger]): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, checkUintArrayAbiSignature, array, sender, description = "checkUintArrayAbi")
  def checkStructsWithString(structs: Array[(String, BigInteger)]): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, checkStructsWithStringSignature, structs, sender, description = "checkStructsWithString")
  def getStructWithString: F[(String, BigInteger)] =
    PreparedTransaction(address, getStructWithStringSignature, (), sender, description = "getStructWithString").call()
  def getStructsWithString: F[Array[(String, BigInteger)]] =
    PreparedTransaction(address, getStructsWithStringSignature, (), sender, description = "getStructsWithString").call()
  def setRates(rates: Array[(Address, BigInteger)]): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, setRatesSignature, rates, sender, description = "setRates")
  def setRate(_rate: (Address, BigInteger)): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, setRateSignature, _rate, sender, description = "setRate")
  def getRate(test: BigInteger): PreparedTransaction[F, (Address, BigInteger)] =
    PreparedTransaction(address, getRateSignature, test, sender, description = "getRate")
  def getRate: PreparedTransaction[F, (Address, BigInteger)] =
    PreparedTransaction(address, getRate1Signature, (), sender, description = "getRate")
  def emitSimpleEvent(topic: String, value: String): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, emitSimpleEventSignature, (topic, value), sender, description = "emitSimpleEvent")
  def emitAddressEvent(topic: Address, value: String): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, emitAddressEventSignature, (topic, value), sender, description = "emitAddressEvent")
  def emitMixedEvent(topic: Address, value: String, test: Address): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, emitMixedEventSignature, (topic, value, test), sender, description = "emitMixedEvent")
}

object IntegrationTest extends ContractObject {
  val name = "IntegrationTest"
  val abi = "[{\"name\":\"state\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"constant\":true,\"id\":\"0xc19d93fb\"},{\"type\":\"fallback\",\"inputs\":[],\"outputs\":[],\"payable\":true,\"constant\":false,\"id\":\"0xfa963d8d\"},{\"name\":\"SimpleEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false}],\"anonymous\":false,\"id\":\"0xdf07c14ba7c542332df3cebcac73245d796909b2be2b2aed13f40d5b23136cd0\",\"type\":\"event\"},{\"name\":\"AddressEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false}],\"anonymous\":false,\"id\":\"0x2d1926fdeec1e1c3ef245ded9ff04522b3e5e14f0a57a12efe35949d72bf661e\",\"type\":\"event\"},{\"name\":\"MixedEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false},{\"name\":\"test\",\"type\":\"address\",\"indexed\":true}],\"anonymous\":false,\"id\":\"0x0f5e2a2282931e0c4b5f2319a04deb79518c3127921c3f70d2fe9306563dd167\",\"type\":\"event\"},{\"name\":\"RateEvent\",\"inputs\":[{\"name\":\"token\",\"type\":\"address\",\"indexed\":false},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"id\":\"0xf6cc106d4ff63d31aa62a665afe317068b92fc2bda4688afe33635cfff444749\",\"type\":\"event\"},{\"name\":\"StringEvent\",\"inputs\":[{\"name\":\"str\",\"type\":\"string\",\"indexed\":false},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"id\":\"0x6fc10800b7deda6aea9a2a89968fd690847169903420807806184ae95e936933\",\"type\":\"event\"},{\"name\":\"TestEvent\",\"inputs\":[{\"name\":\"value1\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"id\":\"0x1440c4dd67b4344ea1905ec0318995133b550f168b4ee959a0da6b503d7d2414\",\"type\":\"event\"},{\"name\":\"TestEvent\",\"inputs\":[{\"name\":\"value1\",\"type\":\"uint256\",\"indexed\":false},{\"name\":\"value2\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"id\":\"0xf3ca124a697ba07e8c5e80bebcfcc48991fc16a63170e8a9206e30508960d003\",\"type\":\"event\"},{\"name\":\"TestEvent\",\"inputs\":[{\"name\":\"value1\",\"type\":\"uint256\",\"indexed\":false},{\"name\":\"value2\",\"type\":\"uint256\",\"indexed\":false},{\"name\":\"value3\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"id\":\"0x7144a1ad56c12b8e789974c9048bb0e6fe77810226fc73e65630d7f96af1de8a\",\"type\":\"event\"},{\"name\":\"setState\",\"type\":\"function\",\"inputs\":[{\"name\":\"_state\",\"type\":\"uint256\"}],\"outputs\":[],\"payable\":false,\"constant\":false,\"id\":\"0xa9e966b7\"},{\"name\":\"checkUintArrayAbi\",\"type\":\"function\",\"inputs\":[{\"name\":\"array\",\"type\":\"uint256[]\"}],\"outputs\":[],\"payable\":false,\"constant\":false,\"id\":\"0x8a8bbc65\"},{\"name\":\"checkStructsWithString\",\"type\":\"function\",\"inputs\":[{\"name\":\"structs\",\"type\":\"tuple[]\",\"components\":[{\"name\":\"str\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"outputs\":[],\"payable\":false,\"constant\":false,\"id\":\"0x65ef6a92\"},{\"name\":\"getStructWithString\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"tuple\",\"components\":[{\"name\":\"str\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"payable\":false,\"constant\":true,\"id\":\"0x7ad8748f\"},{\"name\":\"getStructsWithString\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"tuple[1]\",\"components\":[{\"name\":\"str\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"payable\":false,\"constant\":true,\"id\":\"0x45e73e65\"},{\"name\":\"setRates\",\"type\":\"function\",\"inputs\":[{\"name\":\"rates\",\"type\":\"tuple[]\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"outputs\":[],\"payable\":false,\"constant\":false,\"id\":\"0xe8a44970\"},{\"name\":\"setRate\",\"type\":\"function\",\"inputs\":[{\"name\":\"_rate\",\"type\":\"tuple\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"outputs\":[],\"payable\":false,\"constant\":false,\"id\":\"0xd109877f\"},{\"name\":\"getRate\",\"type\":\"function\",\"inputs\":[{\"name\":\"test\",\"type\":\"uint256\"}],\"outputs\":[{\"name\":\"\",\"type\":\"tuple\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"payable\":false,\"constant\":false,\"id\":\"0x57764094\"},{\"name\":\"getRate\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"tuple\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"payable\":false,\"constant\":false,\"id\":\"0x679aefce\"},{\"name\":\"emitSimpleEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"string\"}],\"outputs\":[],\"payable\":false,\"constant\":false,\"id\":\"0x29dc7efe\"},{\"name\":\"emitAddressEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"string\"}],\"outputs\":[],\"payable\":false,\"constant\":false,\"id\":\"0xd1c58fbd\"},{\"name\":\"emitMixedEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"string\"},{\"name\":\"test\",\"type\":\"address\"}],\"outputs\":[],\"payable\":false,\"constant\":false,\"id\":\"0x75e8660d\"}]"
  val bin = "0x608060405234801561001057600080fd5b50611270806100206000396000f3006080604052600436106100c5576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806329dc7efe146100d05780633cb7bce8146100f957806345e73e6514610122578063577640941461014d578063679aefce1461018a57806375e8660d146101b55780637ad8748f146101de5780638a8bbc6514610209578063a9e966b714610232578063b710cc7c1461025b578063c19d93fb14610284578063c4be9b30146102af578063d1c58fbd146102d8575b612710600081905550005b3480156100dc57600080fd5b506100f760048036036100f29190810190610d0f565b610301565b005b34801561010557600080fd5b50610120600480360361011b9190810190610c8d565b61039f565b005b34801561012e57600080fd5b50610137610434565b6040516101449190610f8e565b60405180910390f35b34801561015957600080fd5b50610174600480360361016f9190810190610da4565b610488565b6040516101819190611002565b60405180910390f35b34801561019657600080fd5b5061019f610504565b6040516101ac9190611002565b60405180910390f35b3480156101c157600080fd5b506101dc60048036036101d79190810190610be5565b61057e565b005b3480156101ea57600080fd5b506101f36105e8565b604051610200919061101d565b60405180910390f35b34801561021557600080fd5b50610230600480360361022b9190810190610cce565b61061b565b005b34801561023e57600080fd5b5061025960048036036102549190810190610da4565b61061e565b005b34801561026757600080fd5b50610282600480360361027d9190810190610c4c565b610628565b005b34801561029057600080fd5b506102996106bd565b6040516102a6919061103f565b60405180910390f35b3480156102bb57600080fd5b506102d660048036036102d19190810190610d7b565b6106c3565b005b3480156102e457600080fd5b506102ff60048036036102fa9190810190610b91565b61075e565b005b816040518082805190602001908083835b6020831015156103375780518252602082019150602081019050602083039250610312565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390207fdf07c14ba7c542332df3cebcac73245d796909b2be2b2aed13f40d5b23136cd0826040516103939190610fb0565b60405180910390a25050565b60008082519150600090505b8181101561042f577f6fc10800b7deda6aea9a2a89968fd690847169903420807806184ae95e93693383828151811015156103e257fe5b906020019060200201516000015184838151811015156103fe57fe5b906020019060200201516020015160405161041a929190610fd2565b60405180910390a180806001019150506103ab565b505050565b61043c6107b0565b6104446107b0565b604080519081016040528060206040519081016040528060008152508152602001600081525081600060018110151561047957fe5b60200201819052508091505090565b6104906107dd565b60016040805190810160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820154815250509050919050565b61050c6107dd565b60016040805190810160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001600182015481525050905090565b8073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff167f0f5e2a2282931e0c4b5f2319a04deb79518c3127921c3f70d2fe9306563dd167846040516105db9190610fb0565b60405180910390a3505050565b6105f061080d565b6040805190810160405280602060405190810160405280600081525081526020016000815250905090565b50565b8060008190555050565b60008082519150600090505b818110156106b8577ff6cc106d4ff63d31aa62a665afe317068b92fc2bda4688afe33635cfff444749838281518110151561066b57fe5b9060200190602002015160000151848381518110151561068757fe5b90602001906020020151602001516040516106a3929190610f65565b60405180910390a18080600101915050610634565b505050565b60005481565b80600160008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550602082015181600101559050507ff6cc106d4ff63d31aa62a665afe317068b92fc2bda4688afe33635cfff44474981600001518260200151604051610753929190610f65565b60405180910390a150565b8173ffffffffffffffffffffffffffffffffffffffff167f2d1926fdeec1e1c3ef245ded9ff04522b3e5e14f0a57a12efe35949d72bf661e826040516107a49190610fb0565b60405180910390a25050565b60408051908101604052806001905b6107c7610827565b8152602001906001900390816107bf5790505090565b6040805190810160405280600073ffffffffffffffffffffffffffffffffffffffff168152602001600081525090565b604080519081016040528060608152602001600081525090565b604080519081016040528060608152602001600081525090565b600061084d82356111b9565b905092915050565b600082601f830112151561086857600080fd5b813561087b61087682611087565b61105a565b915081818352602084019350602081019050838560408402820111156108a057600080fd5b60005b838110156108d057816108b68882610a81565b8452602084019350604083019250506001810190506108a3565b5050505092915050565b600082601f83011215156108ed57600080fd5b81356109006108fb826110af565b61105a565b9150818183526020840193506020810190508360005b83811015610946578135860161092c8882610b19565b845260208401935060208301925050600181019050610916565b5050505092915050565b600082601f830112151561096357600080fd5b8135610976610971826110d7565b61105a565b9150818183526020840193506020810190508385602084028201111561099b57600080fd5b60005b838110156109cb57816109b18882610b7d565b84526020840193506020830192505060018101905061099e565b5050505092915050565b600082601f83011215156109e857600080fd5b81356109fb6109f6826110ff565b61105a565b91508082526020830160208301858383011115610a1757600080fd5b610a228382846111e3565b50505092915050565b600082601f8301121515610a3e57600080fd5b8135610a51610a4c8261112b565b61105a565b91508082526020830160208301858383011115610a6d57600080fd5b610a788382846111e3565b50505092915050565b600060408284031215610a9357600080fd5b610a9d604061105a565b90506000610aad84828501610841565b6000830152506020610ac184828501610b7d565b60208301525092915050565b600060408284031215610adf57600080fd5b610ae9604061105a565b90506000610af984828501610841565b6000830152506020610b0d84828501610b7d565b60208301525092915050565b600060408284031215610b2b57600080fd5b610b35604061105a565b9050600082013567ffffffffffffffff811115610b5157600080fd5b610b5d848285016109d5565b6000830152506020610b7184828501610b7d565b60208301525092915050565b6000610b8982356111d9565b905092915050565b60008060408385031215610ba457600080fd5b6000610bb285828601610841565b925050602083013567ffffffffffffffff811115610bcf57600080fd5b610bdb85828601610a2b565b9150509250929050565b600080600060608486031215610bfa57600080fd5b6000610c0886828701610841565b935050602084013567ffffffffffffffff811115610c2557600080fd5b610c3186828701610a2b565b9250506040610c4286828701610841565b9150509250925092565b600060208284031215610c5e57600080fd5b600082013567ffffffffffffffff811115610c7857600080fd5b610c8484828501610855565b91505092915050565b600060208284031215610c9f57600080fd5b600082013567ffffffffffffffff811115610cb957600080fd5b610cc5848285016108da565b91505092915050565b600060208284031215610ce057600080fd5b600082013567ffffffffffffffff811115610cfa57600080fd5b610d0684828501610950565b91505092915050565b60008060408385031215610d2257600080fd5b600083013567ffffffffffffffff811115610d3c57600080fd5b610d4885828601610a2b565b925050602083013567ffffffffffffffff811115610d6557600080fd5b610d7185828601610a2b565b9150509250929050565b600060408284031215610d8d57600080fd5b6000610d9b84828501610acd565b91505092915050565b600060208284031215610db657600080fd5b6000610dc484828501610b7d565b91505092915050565b610dd68161118f565b82525050565b6000610de782611161565b83602082028501610df785611157565b60005b84811015610e30578383038852610e12838351610f19565b9250610e1d82611182565b9150602088019750600181019050610dfa565b508196508694505050505092915050565b6000610e4c82611177565b808452610e608160208601602086016111f2565b610e6981611225565b602085010191505092915050565b6000610e828261116c565b808452610e968160208601602086016111f2565b610e9f81611225565b602085010191505092915050565b604082016000820151610ec36000850182610dcd565b506020820151610ed66020850182610f56565b50505050565b60006040830160008301518482036000860152610ef98282610e77565b9150506020830151610f0e6020860182610f56565b508091505092915050565b60006040830160008301518482036000860152610f368282610e77565b9150506020830151610f4b6020860182610f56565b508091505092915050565b610f5f816111af565b82525050565b6000604082019050610f7a6000830185610dcd565b610f876020830184610f56565b9392505050565b60006020820190508181036000830152610fa88184610ddc565b905092915050565b60006020820190508181036000830152610fca8184610e41565b905092915050565b60006040820190508181036000830152610fec8185610e77565b9050610ffb6020830184610f56565b9392505050565b60006040820190506110176000830184610ead565b92915050565b600060208201905081810360008301526110378184610edc565b905092915050565b60006020820190506110546000830184610f56565b92915050565b6000604051905081810181811067ffffffffffffffff8211171561107d57600080fd5b8060405250919050565b600067ffffffffffffffff82111561109e57600080fd5b602082029050602081019050919050565b600067ffffffffffffffff8211156110c657600080fd5b602082029050602081019050919050565b600067ffffffffffffffff8211156110ee57600080fd5b602082029050602081019050919050565b600067ffffffffffffffff82111561111657600080fd5b601f19601f8301169050602081019050919050565b600067ffffffffffffffff82111561114257600080fd5b601f19601f8301169050602081019050919050565b6000819050919050565b600060019050919050565b600081519050919050565b600081519050919050565b6000602082019050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b82818337600083830152505050565b60005b838110156112105780820151818401526020810190506111f5565b8381111561121f576000848401525b50505050565b6000601f19601f83011690509190505600a265627a7a72305820f92979122eb1960115a2489dfa3b66965778c5ccf5622ed39c5ac7e5e60aa1066c6578706572696d656e74616cf50037"

  val constructor = UnitType

  def encodeArgs: Binary =
    constructor.encode()

  def deployTransactionData: Binary =
    Binary(Hex.toBytes(bin)) ++ encodeArgs

  def deploy[F[_]](sender: TransactionSender[F])(implicit f: Functor[F]): F[Word] =
    sender.sendTransaction(request.Transaction(data = deployTransactionData))

  def deployAndWait[F[_]](sender: TransactionSender[F], poller: TransactionPoller[F])(implicit m: MonadThrowable[F]): F[IntegrationTest[F]] =
    poller.waitForTransaction(deploy(sender))
      .map(receipt => new IntegrationTest[F](receipt.contractAddress, sender))

  val stateSignature = Signature("state", UnitType, Tuple1Type(Uint256Type))/*0xc19d93fb*/
  val setStateSignature = Signature("setState", Tuple1Type(Uint256Type), UnitType)/*0xa9e966b7*/
  val checkUintArrayAbiSignature = Signature("checkUintArrayAbi", Tuple1Type(VarArrayType(Uint256Type)), UnitType)/*0x8a8bbc65*/
  val checkStructsWithStringSignature = Signature("checkStructsWithString", Tuple1Type(VarArrayType(Tuple2Type(StringType, Uint256Type))), UnitType)/*0x65ef6a92*/
  val getStructWithStringSignature = Signature("getStructWithString", UnitType, Tuple1Type(Tuple2Type(StringType, Uint256Type)))/*0x7ad8748f*/
  val getStructsWithStringSignature = Signature("getStructsWithString", UnitType, Tuple1Type(FixArrayType(1, Tuple2Type(StringType, Uint256Type))))/*0x45e73e65*/
  val setRatesSignature = Signature("setRates", Tuple1Type(VarArrayType(Tuple2Type(AddressType, Uint256Type))), UnitType)/*0xe8a44970*/
  val setRateSignature = Signature("setRate", Tuple1Type(Tuple2Type(AddressType, Uint256Type)), UnitType)/*0xd109877f*/
  val getRateSignature = Signature("getRate", Tuple1Type(Uint256Type), Tuple1Type(Tuple2Type(AddressType, Uint256Type)))/*0x57764094*/
  val getRate1Signature = Signature("getRate", UnitType, Tuple1Type(Tuple2Type(AddressType, Uint256Type)))/*0x679aefce*/
  val emitSimpleEventSignature = Signature("emitSimpleEvent", Tuple2Type(StringType, StringType), UnitType)/*0x29dc7efe*/
  val emitAddressEventSignature = Signature("emitAddressEvent", Tuple2Type(AddressType, StringType), UnitType)/*0xd1c58fbd*/
  val emitMixedEventSignature = Signature("emitMixedEvent", Tuple3Type(AddressType, StringType, AddressType), UnitType)/*0x75e8660d*/
}

case class SimpleEvent(log: response.Log, topic: Word, value: String)

object SimpleEvent {
  import TopicFilter.simple

  val event = Event("SimpleEvent", List(StringType, StringType), Tuple1Type(StringType), Tuple1Type(StringType))
  val id: Word = Word.apply("0xdf07c14ba7c542332df3cebcac73245d796909b2be2b2aed13f40d5b23136cd0")

  def filter(topic: String): LogFilter =
    LogFilter(topics = List(simple(id), StringType.encodeForTopic(topic)))

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(id)), address = addresses.toList)

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[SimpleEvent] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(SimpleEvent(_))

  def apply(log: response.Log): SimpleEvent = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val topic = log.topics(1)
    val value = decodedData
    SimpleEvent(log, topic, value)
  }
}

case class AddressEvent(log: response.Log, topic: Address, value: String)

object AddressEvent {
  import TopicFilter.simple

  val event = Event("AddressEvent", List(AddressType, StringType), Tuple1Type(AddressType), Tuple1Type(StringType))
  val id: Word = Word.apply("0x2d1926fdeec1e1c3ef245ded9ff04522b3e5e14f0a57a12efe35949d72bf661e")

  def filter(topic: Address): LogFilter =
    LogFilter(topics = List(simple(id), AddressType.encodeForTopic(topic)))

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(id)), address = addresses.toList)

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[AddressEvent] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(AddressEvent(_))

  def apply(log: response.Log): AddressEvent = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val topic = event.indexed.type1.decode(log.topics(1), 0).value
    val value = decodedData
    AddressEvent(log, topic, value)
  }
}

case class MixedEvent(log: response.Log, topic: Address, test: Address, value: String)

object MixedEvent {
  import TopicFilter.simple

  val event = Event("MixedEvent", List(AddressType, StringType, AddressType), Tuple2Type(AddressType, AddressType), Tuple1Type(StringType))
  val id: Word = Word.apply("0x0f5e2a2282931e0c4b5f2319a04deb79518c3127921c3f70d2fe9306563dd167")

  def filter(topic: Address, test: Address): LogFilter =
    LogFilter(topics = List(simple(id), AddressType.encodeForTopic(topic), AddressType.encodeForTopic(test)))

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(id)), address = addresses.toList)

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[MixedEvent] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(MixedEvent(_))

  def apply(log: response.Log): MixedEvent = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val topic = event.indexed.type1.decode(log.topics(1), 0).value
    val test = event.indexed.type2.decode(log.topics(2), 0).value
    val value = decodedData
    MixedEvent(log, topic, test, value)
  }
}

case class RateEvent(log: response.Log, token: Address, value: BigInteger)

object RateEvent {
  import TopicFilter.simple

  val event = Event("RateEvent", List(AddressType, Uint256Type), UnitType, Tuple2Type(AddressType, Uint256Type))
  val id: Word = Word.apply("0xf6cc106d4ff63d31aa62a665afe317068b92fc2bda4688afe33635cfff444749")

  def filter(): LogFilter =
    LogFilter(topics = List(simple(id)))

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(id)), address = addresses.toList)

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[RateEvent] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(RateEvent(_))

  def apply(log: response.Log): RateEvent = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val token = decodedData._1
    val value = decodedData._2
    RateEvent(log, token, value)
  }
}

case class StringEvent(log: response.Log, str: String, value: BigInteger)

object StringEvent {
  import TopicFilter.simple

  val event = Event("StringEvent", List(StringType, Uint256Type), UnitType, Tuple2Type(StringType, Uint256Type))
  val id: Word = Word.apply("0x6fc10800b7deda6aea9a2a89968fd690847169903420807806184ae95e936933")

  def filter(): LogFilter =
    LogFilter(topics = List(simple(id)))

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(id)), address = addresses.toList)

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[StringEvent] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(StringEvent(_))

  def apply(log: response.Log): StringEvent = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val str = decodedData._1
    val value = decodedData._2
    StringEvent(log, str, value)
  }
}

case class TestEvent(log: response.Log, value1: BigInteger)

object TestEvent {
  import TopicFilter.simple

  val event = Event("TestEvent", List(Uint256Type), UnitType, Tuple1Type(Uint256Type))
  val id: Word = Word.apply("0x1440c4dd67b4344ea1905ec0318995133b550f168b4ee959a0da6b503d7d2414")

  def filter(): LogFilter =
    LogFilter(topics = List(simple(id)))

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(id)), address = addresses.toList)

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[TestEvent] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(TestEvent(_))

  def apply(log: response.Log): TestEvent = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val value1 = decodedData
    TestEvent(log, value1)
  }
}

case class Test1Event(log: response.Log, value1: BigInteger, value2: BigInteger)

object Test1Event {
  import TopicFilter.simple

  val event = Event("TestEvent", List(Uint256Type, Uint256Type), UnitType, Tuple2Type(Uint256Type, Uint256Type))
  val id: Word = Word.apply("0xf3ca124a697ba07e8c5e80bebcfcc48991fc16a63170e8a9206e30508960d003")

  def filter(): LogFilter =
    LogFilter(topics = List(simple(id)))

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(id)), address = addresses.toList)

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[Test1Event] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(Test1Event(_))

  def apply(log: response.Log): Test1Event = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val value1 = decodedData._1
    val value2 = decodedData._2
    Test1Event(log, value1, value2)
  }
}

case class Test2Event(log: response.Log, value1: BigInteger, value2: BigInteger, value3: BigInteger)

object Test2Event {
  import TopicFilter.simple

  val event = Event("TestEvent", List(Uint256Type, Uint256Type, Uint256Type), UnitType, Tuple3Type(Uint256Type, Uint256Type, Uint256Type))
  val id: Word = Word.apply("0x7144a1ad56c12b8e789974c9048bb0e6fe77810226fc73e65630d7f96af1de8a")

  def filter(): LogFilter =
    LogFilter(topics = List(simple(id)))

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(id)), address = addresses.toList)

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[Test2Event] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(Test2Event(_))

  def apply(log: response.Log): Test2Event = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val value1 = decodedData._1
    val value2 = decodedData._2
    val value3 = decodedData._3
    Test2Event(log, value1, value2, value3)
  }
}



