package scalether.transaction

import java.math.BigInteger

import org.web3j.crypto.Sign
import org.web3j.crypto.Sign.SignatureData
import org.web3j.rlp.{RlpEncoder, RlpList, RlpString, RlpType}
import scalether.domain.request.Transaction
import scalether.transaction.TransactionSigner._
import scalether.util.{Bytes, Hex}

class TransactionSigner(privateKey: BigInteger) {
  private val publicKey = Sign.publicKeyFromPrivate(privateKey)

  def sign(transaction: Transaction): Array[Byte] = {
    val encodedTransaction = RlpEncoder.encode(new RlpList(asRlp(transaction):_*))
    val data = Sign.signMessage(encodedTransaction, publicKey, privateKey)
    RlpEncoder.encode(new RlpList(asRlp(transaction) ++ asRlp(data):_*))
  }
}

object TransactionSigner {
  def asRlp(transaction: Transaction): Array[RlpType] = Array(
    RlpString.create(transaction.nonce),
    RlpString.create(transaction.gasPrice),
    RlpString.create(transaction.gas),
    Option(transaction.to).map(a => RlpString.create(a.bytes)).getOrElse(RlpString.create("")),
    RlpString.create(Option(transaction.value).getOrElse(BigInteger.ZERO)),
    RlpString.create(transaction.data.bytes)
  )

  protected def asRlp(ecSignature: SignatureData): Array[RlpType] = Array(
    RlpString.create(ecSignature.getV),
    RlpString.create(Bytes.trimLeadingZeroes(ecSignature.getR)),
    RlpString.create(Bytes.trimLeadingZeroes(ecSignature.getS)),
  )
}
