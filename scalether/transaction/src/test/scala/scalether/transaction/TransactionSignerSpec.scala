package scalether.transaction

import org.scalatest.FlatSpec
import org.scalatest.prop.PropertyChecks
import org.web3j.rlp.RlpString
import scalether.domain.implicits._
import scalether.domain.request.Transaction
import scalether.test.Generators

class TransactionSignerSpec extends FlatSpec with PropertyChecks {
  "TransactionSigner" should "encode rlp for transaction" in {
    forAll(Generators.address) { address =>
      val list = TransactionSigner.asRlp(Transaction(
        nonce = 0,
        gasPrice = 1,
        gas = 10,
        to = address,
        value = Long.MaxValue
      ))
      assert(list.length == 6)
      assert(list(3) == RlpString.create(address.bytes))
    }
  }
}
