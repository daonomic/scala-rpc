package io.daonomic.rpc.mono

import java.net.URI

import reactor.core.publisher.Flux

object WebSocketTransportTest {
  def main(args: Array[String]): Unit = {
/*
    val mapper = JsonConverter.createMapper()
    val transport = new WebSocketTransport("ws://localhost:1080")

    transport.connections().subscribe { _ => println("connected") }

    //transport.send(mapper.writeValueAsString(Request(1, "method", "param")))
    transport.receive().subscribe(s => println(s"got :$s"))
    Thread.sleep(600000)
*/
    val in = WebSocketClient.reconnect(new URI("ws://ether:8546"), Flux.empty())
      .doOnNext(println(_))

    in.subscribe()
    in.subscribe()

    try {
      println("all events: " + in.collectList().block())
    } catch {
      case e: Throwable =>
        println(s"got error: $e")
    }
  }
}