package io.daonomic.rpc.mono;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

public class WebSocketClient {

    public static Flux<String> reconnect(URI uri, Flux<String> send) {
        return Flux.create(sink -> connect(uri, send)
            .doOnNext(sink::next)
            .<Void>then(Mono.error(new IllegalStateException("disconnected")))
            .retryBackoff(Long.MAX_VALUE, Duration.ofMillis(500), Duration.ofMillis(5000))
            .subscribe()
        );
    }

    public static Flux<String> connect(URI uri, Flux<String> send) {
        return Flux.create(sink -> new ReactorNettyWebSocketClient().execute(uri, session -> {
            Flux<WebSocketMessage> receive = session.receive()
                .doOnNext(m -> sink.next(m.getPayloadAsText()));
            return session.send(send.map(session::textMessage))
                .and(receive);
        }).subscribe(
            __ -> {
            },
            sink::error,
            sink::complete,
            s -> System.out.println("connecting")
        ));
    }
}
