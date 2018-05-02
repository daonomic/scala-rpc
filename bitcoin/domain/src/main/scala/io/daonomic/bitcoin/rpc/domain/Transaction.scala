package io.daonomic.bitcoin.rpc.domain

case class Transaction(txid: String,
                       hash: String,
                       version: Int,
                       size: Long,
                       vsize: Long,
                       locktime: Long,
                       vin: List[TransactionIn],
                       vout: List[TransactionOut],
                       confirmations: Long,
                       hex: String)

case class TransactionIn(txid: String,
                         vout: Int,
                         sequence: Long,
                         scriptSig: ScriptSig,
                         txinwitness: List[String])

case class ScriptSig(asm: String, hex: String)

case class TransactionOut(value: Double,
                          n: Int,
                          scriptPubKey: ScriptPubKey)

case class ScriptPubKey(asm: String,
                        hex: String,
                        reqSigs: Int,
                        `type`: String,
                        addresses: List[String])