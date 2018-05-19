package scalether.transaction

import java.math.BigInteger

import cats.Id
import scalether.core.IdEthereum
import scalether.domain.Address

class IdSimpleNonceProvider(ethereum: IdEthereum)
  extends SimpleNonceProvider[Id](ethereum) with IdNonceProvider {

  override def nonce(address: Address): BigInteger = super.nonce(address)
}
