package org.zalando.logbook.webflux;

import org.springframework.http.HttpHeaders;
import org.springframework.util.MimeType;

import javax.annotation.Nullable;
import java.nio.charset.Charset;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class Utils {

    public static final String PROTOCOL_VERSION = "HTTP/1.1";
    public static final String LOCALHOST = "127.0.0.1";

    public static Charset getCharset(HttpHeaders httpHeaders) {
        return Optional.ofNullable(httpHeaders.getContentType())
            .map(MimeType::getCharset)
            .orElse(Charset.defaultCharset());
    }

    @Nullable
    public static String getContentType(HttpHeaders httpHeaders) {
        return ofNullable(httpHeaders.getContentType())
            .map(MimeType::getType)
            .orElse(null);
    }
}
