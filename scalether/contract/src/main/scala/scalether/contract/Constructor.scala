package scalether.contract

import io.daonomic.rpc.domain.Binary

object Constructor {
  def checkConstructorTx[T](txInput: Binary, contract: NonAbstractContractObject[scalether.abi.Type[T]]): Option[T] = {
    if (txInput.prefixed.startsWith(contract.bin)) {
      val data = txInput.slice((contract.bin.length - 2) / 2, txInput.length)
      Some(contract.constructor.decode(data, 0).value)
    } else {
      None
    }
  }
}
