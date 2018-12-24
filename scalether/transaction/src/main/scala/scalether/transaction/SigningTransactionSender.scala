package scalether.transaction

import java.math.BigInteger

import cats.implicits._
import io.daonomic.cats.MonadThrowable
import io.daonomic.rpc.domain.{Binary, Word}
import org.web3j.crypto.Keys
import scalether.core.Ethereum
import scalether.domain.Address
import scalether.domain.request.Transaction
import scalether.sync.Synchronizer

import scala.language.higherKinds

class SigningTransactionSender[F[_]](ethereum: Ethereum[F],
                                     nonceProvider: NonceProvider[F],
                                     synchronizer: Synchronizer[F],
                                     privateKey: BigInteger,
                                     gas: BigInteger,
                                     gasPrice: GasPriceProvider[F])
                                    (implicit m: MonadThrowable[F])
  extends AbstractTransactionSender[F](ethereum, Address.apply(Keys.getAddressFromPrivateKey(privateKey)), gas, gasPrice) {

  private val signer = new TransactionSigner(privateKey)

  def sendTransaction(transaction: Transaction): F[Word] = fill(transaction).flatMap {
    transaction =>
      if (transaction.nonce != null) {
        ethereum.ethSendRawTransaction(Binary(signer.sign(transaction)))
      } else {
        synchronizer.synchronize(from) { () =>
          nonceProvider.nonce(address = from).flatMap(
            nonce => ethereum.ethSendRawTransaction(Binary(signer.sign(transaction.copy(nonce = nonce))))
          )
        }
      }
  }
}
