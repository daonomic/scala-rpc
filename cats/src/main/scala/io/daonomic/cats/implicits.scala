package io.daonomic.cats

object implicits {
  implicit lazy val monoInstance: MonoInstance = new MonoInstance

  implicit lazy val tryInstance: TryInstance = new TryInstance

  implicit lazy val idInstance: IdInstance = new IdInstance
}
