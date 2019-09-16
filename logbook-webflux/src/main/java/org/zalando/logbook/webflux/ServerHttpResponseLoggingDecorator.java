package org.zalando.logbook.webflux;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ServerHttpResponseLoggingDecorator extends ServerHttpResponseDecorator {

    private FluxCache fluxCache = new FluxCache();

    public ServerHttpResponseLoggingDecorator(ServerHttpResponse delegate) {
        super(delegate);
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        return super.writeWith(
            fluxCache.cache(Flux.from(body))
        );
    }
}
