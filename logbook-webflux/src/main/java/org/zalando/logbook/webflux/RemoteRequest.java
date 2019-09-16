package org.zalando.logbook.webflux;

import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.Origin;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

@RequiredArgsConstructor
public class RemoteRequest implements HttpRequest {

    private final ServerHttpRequest delegate;

    @Override
    public String getRemote() {
        return ofNullable(delegate.getRemoteAddress())
            .map(it -> ofNullable(it.getAddress())
                .map(InetAddress::getHostAddress)
                .orElse(it.getHostName())
            ).orElse(Utils.LOCALHOST);
    }

    @Override
    public String getMethod() {
        return delegate.getMethodValue();
    }

    @Override
    public String getScheme() {
        return delegate.getURI().getScheme();
    }

    @Override
    public String getHost() {
        return delegate.getURI().getHost();
    }

    @Override
    public Optional<Integer> getPort() {
        return of(delegate.getURI().getPort())
            .filter(port -> port != -1);
    }

    @Override
    public String getPath() {
        return delegate.getURI().getPath();
    }

    @Override
    public String getQuery() {
        return delegate.getQueryParams()
            .entrySet().stream()
            .map(this::queryEntryToString)
            .collect(joining("&"));
    }

    private String queryEntryToString(Map.Entry<String, List<String>> queryEntry) {
        String vals = queryEntry.getValue().stream()
            .collect(joining(","));
        return String.format("%s=%s", queryEntry.getKey(), vals);
    }

    @Override
    public String getProtocolVersion() {
        return Utils.PROTOCOL_VERSION;
    }

    @Override
    public Origin getOrigin() {
        return Origin.REMOTE;
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
    public HttpRequest withBody() throws IOException {
        return null;
    }

    @Override
    public HttpRequest withoutBody() {
        return null;
    }

    @Override
    public byte[] getBody() throws IOException {
        return new byte[0];
    }
}
