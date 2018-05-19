package io.daonomic.blockchain.state

import cats.Id

class IdStateAdapter[T](state: IdState[T]) extends State[T, Id] {
  override def get: Option[T] =
    Option(state.get())

  override def set(value: T): Unit =
    state.set(value)
}
