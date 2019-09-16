package org.zalando.logbook.webflux;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

public class ServerHttpRequestLoggingDecorator extends ServerHttpRequestDecorator {

    private FluxCache fluxCache = new FluxCache();

    public ServerHttpRequestLoggingDecorator(ServerHttpRequest delegate) {
        super(delegate);
    }

    @Override
    public Flux<DataBuffer> getBody() {
        return fluxCache.cache(super.getBody());
    }
}
