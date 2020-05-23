package scalether.contract

trait NonAbstractContractObject[T <: scalether.abi.Type[_]] extends ContractObject {
  val constructor: T
}
