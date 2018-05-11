<#macro monad><#compress>
    <#if F?has_content>
        ${F}
    <#else>
        F
    </#if>
</#compress></#macro>
<#macro monad_param><#compress>
    <#if !(F?has_content)>
        [F[_]]
    </#if>
</#compress></#macro>
<#macro sender><#compress>
    <#if transactionSender?has_content>
        ${transactionSender}
    <#else>
        TransactionSender[<@monad/>]
    </#if>
</#compress></#macro>
<#macro poller><#compress>
    <#if transactionPoller?has_content>
        ${transactionPoller}
    <#else>
        TransactionPoller[<@monad/>]
    </#if>
</#compress></#macro>
<#macro implicit><#compress>
    <#if !(monadImport?has_content)>
        <#nested/>
    </#if>
</#compress></#macro>
<#macro single_scala_type abiType components=[]><#compress>
    <#if abiType == 'address'>
        Address
    <#elseif abiType == 'string'>
        String
    <#elseif abiType?starts_with("uint")>
        BigInteger
    <#elseif abiType == "bool">
        Boolean
    <#elseif abiType?starts_with("bytes")>
        Array[Byte]
    <#elseif abiType?ends_with("[]")>
        Array[<@single_scala_type abiType?substring(0, abiType?length - 2) components/>]
    <#elseif abiType == "tuple">
        (<#list components as component><@single_scala_type component.type component.components/><#if component?has_next>, </#if></#list>)
    <#else>
        generic
    </#if>
</#compress></#macro>
<#macro scala_type types><#compress>
    <#if types?size == 0>
        Unit
    <#else>
        <#list types as type><@single_scala_type type.type type.components/><#if type?has_next>, </#if></#list>
    </#if>
</#compress></#macro>
<#macro single_type abiType components=[]><#compress>
    <#if abiType == 'address'>
        AddressType
    <#elseif abiType == 'string'>
        StringType
    <#elseif abiType == 'uint'>
        Uint${abiType?substring(4)}Type
    <#elseif abiType?starts_with("uint")>
        Uint${abiType?substring(4)}Type
    <#elseif abiType == "bool">
        BoolType
    <#elseif abiType == "bytes">
        BytesType
    <#elseif abiType?starts_with("bytes")>
        Bytes${abiType?substring(5)}Type
    <#elseif abiType?ends_with("[]")>
        VarArrayType(<@single_type abiType?substring(0, abiType?length - 2) components/>)
    <#elseif abiType == "tuple">
        Tuple${components?size}Type(<#list components as component><@single_type component.type component.components/><#if component?has_next>, </#if></#list>)
    <#else>
        Type
    </#if>
</#compress></#macro>
<#macro type_list types=[]><#compress>
    <#list types as type><@single_type type.type type.components/><#if type?has_next>, </#if></#list>
</#compress></#macro>
<#macro type types=[]><#compress>
    <#if types?size == 0>
        UnitType
    <#else>
        Tuple${types?size}Type(<@type_list types/>)
    </#if>
</#compress></#macro>
<#macro signature item>Signature("${item.name}", <@type item.inputs/>, <@type item.outputs/>)</#macro>
<#macro tuple_type types=[]><#compress>
    <#if types?size == 1 || types?size == 0>
        <@scala_type types/>
    <#else>
        (<@scala_type types/>)
    </#if>
</#compress></#macro>
<#function isHashTopic arg>
    <#return arg.type == 'string'>
</#function>
<#macro event_arg_type arg><#compress>
    <#if arg.indexed && isHashTopic(arg)>
        Word
    <#else>
        <@single_scala_type arg.type arg.components/>
    </#if>
</#compress></#macro>
<#macro event_indexed_arg arg index><#compress>
    <#if isHashTopic(arg)>
        log.topics(${index + 1})
    <#else>
        event.indexed.type${index + 1}.decode(log.topics(${index + 1}).bytes, 0).value
    </#if>
</#compress></#macro>
<#macro event_non_indexed_arg arg index><#compress>
    decodedData._${index + 1}
</#compress></#macro>
<#macro if_empty name default_name><#if name?has_content>${name}<#else>${default_name}</#if></#macro>
<#macro args inputs><#if inputs?has_content>(<#list inputs as inp><#local c=inp?counter/><@if_empty inp.name "arg${c}"/>: <@single_scala_type inp.type inp.components/><#if inp?has_next>, </#if></#list>)</#if></#macro>
<#macro args_values inputs><#list inputs as inp><#local c=inp?counter/><@if_empty inp.name "arg${c}"/><#if inp?has_next>, </#if></#list></#macro>
<#macro args_params inputs><#if inputs?size != 0>(</#if><@args_values inputs/><#if inputs?size != 0>)</#if></#macro>
<#macro args_tuple inputs><#if inputs?size != 1>(</#if><@args_values inputs/><#if inputs?size != 1>)</#if></#macro>
<#function find_constructor_args>
    <#list truffle.abi as item>
        <#if item.type != "event" && item.type?? && item.type.id == 'constructor'>
            <#return item.inputs/>
        </#if>
    </#list>
    <#return []/>
</#function>
<#assign constructor_args = find_constructor_args()/>
package ${package}

import java.math.BigInteger

<#if monadType?has_content>
import ${monadType}
</#if>
<#if monadImport?has_content>
import ${monadImport}
</#if>
<#list imports![] as import>
import ${import}
</#list>
import scalether.abi._
import scalether.abi.array._
import scalether.abi.tuple._
import scalether.contract._
import scalether.domain._
import scalether.domain.request._
import scalether.transaction._
import scalether.util.Hex

import scala.language.higherKinds

class ${truffle.name}<@monad_param/>(address: Address, sender: <@sender/>)<@implicit>(implicit f: MonadError[<@monad/>, Throwable])</@>
  extends Contract[<@monad/>](address, sender) {

  <#list truffle.abi as item>
        <#if item.type != 'event' && item.name??>
            <#if item.constant>
  def ${item.name}<@args item.inputs/>: <@monad/>[<@tuple_type item.outputs/>] =
    <#if preparedTransaction?has_content>${preparedTransaction}<#else>PreparedTransaction</#if>(address, <@signature item/>, <@args_tuple item.inputs/>, sender).call()
            <#else>
  def ${item.name}<@args item.inputs/>: <#if preparedTransaction?has_content>${preparedTransaction}<#else>PreparedTransaction</#if>[<#if !(preparedTransaction?has_content)><@monad/>, </#if><@tuple_type item.outputs/>] =
    <#if preparedTransaction?has_content>${preparedTransaction}<#else>PreparedTransaction</#if>(address, <@signature item/>, <@args_tuple item.inputs/>, sender)
            </#if>

        </#if>
    </#list>
}

object ${truffle.name} extends ContractObject {
  val name = "${truffle.name}"
  val abi = ${abi}
  val bin = "${truffle.bin}"
  <#if !truffle.abstract>

  val constructor = <@type constructor_args/>

  def encodeArgs<@args constructor_args/>: Array[Byte] =
    constructor.encode(<@args_values constructor_args/>)

  def deployTransactionData<@args constructor_args/>: Binary =
    Binary(Hex.toBytes(bin) ++ encodeArgs<@args_params constructor_args/>)

  def deploy<@monad_param/>(sender: <@sender/>)<@args constructor_args/><@implicit>(implicit f: Functor[<@monad/>])</@>: <@monad/>[Word] =
    sender.sendTransaction(request.Transaction(data = deployTransactionData<@args_params constructor_args/>))

  def deployAndWait<@monad_param/>(sender: <@sender/>, poller: <@poller/>)<@args constructor_args/><@implicit>(implicit m: MonadError[<@monad/>, Throwable])</@>: <@monad/>[${truffle.name}<#if !(F?has_content)>[F]</#if>] =
      poller.waitForTransaction(deploy(sender)<@args_params constructor_args/>)
      .map(receipt => new ${truffle.name}<#if !(F?has_content)>[F]</#if>(receipt.contractAddress, sender))
  </#if>
}

<#assign map={}/>
<#list truffle.abi as item>
  <#if item.type == "event">
    <#assign simpleName=item.name/>
    <#if simpleName?ends_with('Event')>
        <#assign simpleName=simpleName[0..*(simpleName?length-5)]/>
    </#if>
    <#if (map[simpleName]??)>
      <#assign eventName="${simpleName}${map[simpleName]}"/>
      <#assign map=map + {simpleName: 1 + map[simpleName]}/>
    <#else>
      <#assign eventName=simpleName/>
      <#assign map=map + {simpleName: 1}/>
    </#if>
    <#assign eventName="${eventName}Event"/>
case class ${eventName}(<#list item.all as arg>${arg.name}: <@event_arg_type arg/><#if arg?has_next>, </#if></#list>)

object ${eventName} {
  val event = Event("${item.name}", List(<@type_list item.inputs/>), <@type item.indexed/>, <@type item.nonIndexed/>)

  @annotation.varargs def filter(fromBlock: String, toBlock: String, addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList, fromBlock = fromBlock, toBlock = toBlock)

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList)

  def apply(log: response.Log): ${eventName} = {
    assert(log.topics.head == event.id)

    <#if item.nonIndexed?has_content>val decodedData = event.decode(log.data)</#if>
    <#list item.indexed as arg>
    val ${arg.name} = <@event_indexed_arg arg arg?index/>
    </#list>
    <#if item.nonIndexed?size == 1>
      <#list item.nonIndexed as arg>
    val ${arg.name} = decodedData
      </#list>
    <#else>
      <#list item.nonIndexed as arg>
    val ${arg.name} = <@event_non_indexed_arg arg arg?index/>
      </#list>
    </#if>
    ${eventName}(<#list item.all as arg>${arg.name}<#if arg?has_next>, </#if></#list>)
  }
}

  </#if>
</#list>
