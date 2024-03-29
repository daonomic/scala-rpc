package io.daonomic.rpc.mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.WebsocketClientSpec;
import reactor.netty.resources.ConnectionProvider;
import reactor.util.retry.Retry;

import java.net.URI;
import java.time.Duration;

public class SimpleWebSocketClient {
    private static final Logger logger = LoggerFactory.getLogger(SimpleWebSocketClient.class);

    private static final Retry retry = Retry.backoff(Long.MAX_VALUE, Duration.ofMillis(500)).maxBackoff(Duration.ofMillis(5000));

    public static Flux<String> reconnect(URI uri, int maxFramePayloadLength, Flux<String> send) {
        return Flux.create(sink -> Mono.defer(() -> {
            HttpClient httpClient = HttpClient.create(ConnectionProvider.create("webSocket", 1));
            ReactorNettyWebSocketClient client = new ReactorNettyWebSocketClient(httpClient);
            client.setMaxFramePayloadLength(maxFramePayloadLength);
            return connect(client, uri, send)
                .doOnNext(sink::next)
                .<Void>then(Mono.error(new IllegalStateException("disconnected")));
        }).retryWhen(retry)
            .subscribe()
        );
    }

    public static Flux<String> connect(URI uri, int maxFramePayloadLength, Flux<String> send) {
        HttpClient httpClient = HttpClient.create(ConnectionProvider.create("webSocket", 1));
        ReactorNettyWebSocketClient client = new ReactorNettyWebSocketClient(httpClient);
        client.setMaxFramePayloadLength(maxFramePayloadLength);
        return connect(client, uri, send);
    }

    private static Flux<String> connect(ReactorNettyWebSocketClient client, URI uri, Flux<String> send) {
        return Flux.create(sink -> {
            Mono<Void> mono = client.execute(uri, session -> {
                logger.info("connected to {}", uri);
                return Mono.first(
                    session.receive().doOnNext(m -> sink.next(m.getPayloadAsText())).then(),
                    session.send(send.map(session::textMessage))
                );
            });
            final Disposable d = mono
                .doOnTerminate(() -> logger.info("disconnected from {}", uri))
                .subscribe(
                    __ -> {
                    },
                    th -> {
                        logger.error("got error for " + uri, th);
                        sink.error(th);
                    },
                    sink::complete,
                    s -> logger.info("connecting to {}", uri)
                );
            sink.onCancel(() -> {
                logger.info("cancelled");
                d.dispose();
            });
        });
    }
}
