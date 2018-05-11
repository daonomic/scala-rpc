package scalether.transaction

import java.math.BigInteger

import cats.{Monad, MonadError}
import cats.implicits._
import org.web3j.crypto.Keys
import scalether.core.Ethereum
import scalether.domain.{Address, Binary, Word}
import scalether.domain.request.Transaction
import scalether.util.Hex

import scala.language.higherKinds

class SigningTransactionSender[F[_]](ethereum: Ethereum[F],
                                     nonceProvider: NonceProvider[F],
                                     privateKey: BigInteger,
                                     gas: BigInteger,
                                     gasPrice: GasPriceProvider[F])
                                    (implicit m: MonadError[F, Throwable])
  extends AbstractTransactionSender[F](ethereum, Address.apply(Keys.getAddressFromPrivateKey(privateKey)), gas, gasPrice) {

  private val signer = new TransactionSigner(privateKey)

  def sendTransaction(transaction: Transaction): F[Word] = fill(transaction).flatMap {
    transaction =>
      ethereum.ethCall(transaction, "latest").flatMap { _ =>
        if (transaction.nonce != null) {
          ethereum.ethSendRawTransaction(Binary(signer.sign(transaction)))
        } else {
          nonceProvider.nonce(address = from).flatMap(
            nonce => ethereum.ethSendRawTransaction(Binary(signer.sign(transaction.copy(nonce = nonce))))
          )
        }
      }
  }
}
