package io.daonomic.blockchain.poller.mono

import org.reactivestreams.{Subscriber, Subscription}
import reactor.core.publisher.{Mono, MonoSink}
import reactor.core.scheduler.Schedulers

object MonoReduce {
  private val scheduler = Schedulers.newSingle("mono-notifier")

  def reduce[T, R](r: R, list: Seq[T])(reduce: (R, T) => Mono[R]): Mono[R] = Mono.create { sink =>
    subscribe(r, list, reduce, sink)
  }

  def subscribe[T, R](r: R, list: Seq[T], reduce: (R, T) => Mono[R], sink: MonoSink[R]): Unit = list match {
    case _ if list.isEmpty => sink.success(r)
    case _ => reduce(r, list.head)
      .publishOn(scheduler)
      .subscribe(new Subscriber[R] {
        override def onSubscribe(s: Subscription): Unit = {
          s.request(1)
        }

        override def onNext(r: R): Unit =
          subscribe(r, list.tail, reduce, sink)

        override def onError(t: Throwable): Unit =
          sink.error(t)

        override def onComplete(): Unit = {}
      })
  }
}
