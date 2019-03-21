package io.daonomic.rpc.mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.net.URI;
import java.time.Duration;

public class SimpleWebSocketClient {
    private static final Logger logger = LoggerFactory.getLogger(SimpleWebSocketClient.class);

    public static Flux<String> reconnect(URI uri, int maxFramePayloadLength, Flux<String> send) {
        return Flux.create(sink -> {
                HttpClient httpClient = HttpClient.create(ConnectionProvider.fixed("webSocket", 1));
                ReactorNettyWebSocketClient client = new ReactorNettyWebSocketClient(httpClient, maxFramePayloadLength);
                connect(client, uri, send)
                    .doOnNext(sink::next)
                    .<Void>then(Mono.error(new IllegalStateException("disconnected")))
                    .doOnError(th -> logger.error("disconnected from " + uri, th))
                    .retryBackoff(Long.MAX_VALUE, Duration.ofMillis(500), Duration.ofMillis(5000))
                    .subscribe();
            }
        );
    }

    private static Flux<String> connect(ReactorNettyWebSocketClient client, URI uri, Flux<String> send) {
        return Flux.create(sink -> client.execute(uri, session -> {
            logger.info("connected to {}", uri);
            return Mono.first(
                session.receive().doOnNext(m -> sink.next(m.getPayloadAsText())).then(),
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
