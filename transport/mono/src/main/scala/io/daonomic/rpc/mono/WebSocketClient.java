package io.daonomic.rpc.mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

public class WebSocketClient {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketClient.class);

    public static Flux<String> reconnect(URI uri, Flux<String> send) {
        return Flux.create(sink -> connect(uri, send)
            .doOnNext(sink::next)
            .<Void>then(Mono.error(new IllegalStateException("disconnected")))
            .doOnError(th -> logger.error("disconnected from " + uri, th))
            .retryBackoff(Long.MAX_VALUE, Duration.ofMillis(500), Duration.ofMillis(5000))
            .subscribe()
        );
    }

    public static Flux<String> connect(URI uri, Flux<String> send) {
        return Flux.create(sink -> new ReactorNettyWebSocketClient().execute(uri, session -> {
            logger.info("connected to {}", uri);
            return Mono.when(
                session.receive().doOnNext(m -> sink.next(m.getPayloadAsText())),
                session.send(send.map(session::textMessage))
            );
        }).subscribe(
            __ -> {
            },
            sink::error,
            sink::complete,
            s -> logger.info("connecting to {}", uri)
        ));
    }
}
