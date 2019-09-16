package org.zalando.logbook.webflux;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Origin;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
public class LocalResponse implements HttpResponse {

    private final ServerHttpResponse delegate;

    @Override
    public int getStatus() {
        return ofNullable(delegate.getStatusCode())
            .map(HttpStatus::value)
            .orElse(200);
    }

    @Override
    public String getProtocolVersion() {
        return Utils.PROTOCOL_VERSION;
    }

    @Override
    public Origin getOrigin() {
        return Origin.LOCAL;
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return delegate.getHeaders();
    }

    @Nullable
    @Override
    public String getContentType() {
        return Utils.getContentType(delegate.getHeaders());
    }

    @Override
    public Charset getCharset() {
        return Utils.getCharset(delegate.getHeaders());
    }

    @Override
    public HttpResponse withBody() throws IOException {
        return null;
    }

    @Override
    public HttpResponse withoutBody() {
        return null;
    }

    @Override
    public byte[] getBody() throws IOException {
        return new byte[0];
    }
}
