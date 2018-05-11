package io.daonomic.blockchain.block

import java.math.BigInteger

import cats.implicits._
import io.daonomic.blockchain.Blockchain
import io.daonomic.blockchain.state.State
import org.mockito.Matchers
import org.mockito.Mockito._
import org.scalatest.FlatSpec
import org.scalatest.mockito.MockitoSugar

import scala.util.{Success, Try}

class BlockListenServiceSpec extends FlatSpec with MockitoSugar {
  "BlockListenService" should "notify if run first time" in {
    val (state, blockchain, listener) = prepare(None, BigInteger.TEN)
    when(listener.onBlock(BigInteger.TEN)).thenReturn(Success())

    val testing = new BlockListenService[Try](blockchain, listener, state)
    testing.check().get

    verify(state).set(BigInteger.TEN)
    verify(state, atLeastOnce()).get
    verify(listener).onBlock(BigInteger.TEN)
    verifyAfter(blockchain, listener, state)
  }

  it should "notify if block is changed" in {
    val (state, blockchain, listener) = prepare(Some(BigInteger.ONE), BigInteger.TEN)
    when(listener.onBlock(BigInteger.TEN)).thenReturn(Success())

    val testing = new BlockListenService[Try](blockchain, listener, state)
    testing.check().get

    verify(state).set(BigInteger.TEN)
    verify(state, atLeastOnce()).get
    verify(listener).onBlock(BigInteger.TEN)
    verifyAfter(blockchain, listener, state)
  }

  it should "not notify if block is the same" in {
    val (state, blockchain, listener) = prepare(Some(BigInteger.TEN), BigInteger.TEN)

    val testing = new BlockListenService[Try](blockchain, listener, state)
    testing.check().get

    verify(state, atLeastOnce()).get
    verifyAfter(blockchain, listener, state)
  }

  private def prepare(stateValue: Option[BigInteger], blockNumber: BigInteger) = {
    val blockchain = mock[Blockchain[Try]]
    when(blockchain.blockNumber).thenReturn(Success(blockNumber))
    val listener = mock[BlockListener[Try]]
    val state = mock[State[BigInteger, Try]]
    when(state.set(Matchers.any[BigInteger]())).thenReturn(Success())
    when(state.get).thenReturn(Success(stateValue))
    (state, blockchain, listener)
  }

  private def verifyAfter(blockchain: Blockchain[Try], listener: BlockListener[Try], state: State[BigInteger, Try]): Unit = {
    verify(blockchain).blockNumber
    verifyNoMoreInteractions(blockchain, listener, state)
  }
}
