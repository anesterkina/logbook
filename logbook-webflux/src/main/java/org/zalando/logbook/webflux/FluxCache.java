package org.zalando.logbook.webflux;

import lombok.SneakyThrows;
import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;

import java.io.ByteArrayOutputStream;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

public class FluxCache {
    private ByteArrayOutputStream cache = new ByteArrayOutputStream();
    private WritableByteChannel channel = Channels.newChannel(cache);

    public byte[] getCachedBytes() {
        return cache.toByteArray();
    }

    @SneakyThrows
    private void cacheDataBuffer(DataBuffer dataBuffer) {
        channel.write(dataBuffer.asByteBuffer());
    }

    public Flux<DataBuffer> cache(Flux<DataBuffer> flux) {
        return flux.doOnNext(this::cacheDataBuffer);
    }
}
