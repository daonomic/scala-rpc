package io.daonomic.bitcoin.rpc

import io.daonomic.rpc.ManualTag
import org.scalatest.FlatSpec

class BlockSpec extends FlatSpec with IntegrationSpec {
  "Bitcoind" should "do some operations with blocks" in {
    val currentBlock = bitcoind.getBlockCount.block()
    println(currentBlock)
    val hash = bitcoind.getBlockHash(currentBlock).block()
    assert(hash != null)
    val block = restBitcoind.getBlockSimple(hash).block
    println(block)
    assert(currentBlock == block.getBlockNumber)
  }

  it should "draw help" in {
    println(bitcoind.help().block())
  }

  it should "do some basic operations" taggedAs ManualTag in {
    println(bitcoind.getNewAddress.block())

    bitcoind.generate(30).block()
  }

  it should "import address" taggedAs ManualTag in {
    println(bitcoind.importAddress("mjvathXVxkoeHh7KYhNmUN8uMiSWxvtZUa", "test1").block())
  }
}
