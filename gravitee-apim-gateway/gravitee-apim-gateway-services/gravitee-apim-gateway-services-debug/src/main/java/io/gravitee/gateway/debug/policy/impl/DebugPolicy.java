package io.gravitee.gateway.debug.policy.impl;

import io.gravitee.common.http.HttpHeaders;
import io.gravitee.gateway.api.ExecutionContext;
import io.gravitee.gateway.api.buffer.Buffer;
import io.gravitee.gateway.api.stream.ReadWriteStream;
import io.gravitee.gateway.debug.reactor.reactor.handler.context.DebugExecutionContext;
import io.gravitee.gateway.policy.Policy;
import io.gravitee.gateway.policy.PolicyException;
import io.gravitee.gateway.policy.StreamType;
import io.gravitee.policy.api.PolicyChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * @author Yann TAVERNIER (yann.tavernier at graviteesource.com)
 * @author GraviteeSource Team
 */
public class DebugPolicy implements Policy {

    public static final Logger LOGGER = LoggerFactory.getLogger(DebugPolicy.class);

    private final StreamType streamType;
    private final Policy policy;

    private Instant begin;
    private Instant end;
    private final Instant creationDate;

    public DebugPolicy(StreamType streamType, Policy policy) {
        this.streamType = streamType;
        this.policy = policy;
        creationDate = Instant.now();
    }

    @Override
    public String id() {
        return policy.id();
    }

    @Override
    public void execute(PolicyChain chain, ExecutionContext context) throws PolicyException {
        DebugExecutionContext debugContext = (DebugExecutionContext) context;

        debugContext.beginExecutingPolicy(this, streamType);
//        LOGGER.info("------------- execute --------------------");

//        before(context);
        policy.execute(chain, context);
//        after(context);

        debugContext.exitExecutingPolicy(this, streamType);
    }

    @Override
    public ReadWriteStream<Buffer> stream(PolicyChain chain, ExecutionContext context) throws PolicyException {
        DebugExecutionContext debugContext = (DebugExecutionContext) context;
//        LOGGER.info("------------- stream --------------------");

//        before(context);
        debugContext.beginStreamingPolicy(this, streamType);
        final ReadWriteStream<Buffer> stream = policy.stream(chain, context);

        if (stream == null) {
            return null;
        }

        //Discuss with archi
//        context.push(this) (cas du execute)
//        new DebugReadWriteStream((content) -> {  }) // se chargera de pusher au moment du bodyHandler
//        push(Policy) {
//            listOrder.add(policy)
//            streamMap.put(policy, content)
//            executionMap.
//        }

        DebugReadWriteStream otherStream = new DebugReadWriteStream(debugContext, stream, this, streamType);
//        otherStream.endHandler(result -> {
//            final String content = otherStream.getContent();
//            LOGGER.info("Body content: {}", content);
//        });

//        after(context);

        return otherStream;
    }

    @Override
    public boolean isStreamable() {
        return policy.isStreamable();
    }

    @Override
    public boolean isRunnable() {
        return policy.isRunnable();
    }

    private void before(ExecutionContext context) {
        LOGGER.info("---------------------------------");
        LOGGER.info("PHASE: {}", streamType);
        LOGGER.info("Before policy: {}", id());
        begin = Instant.now();
        LOGGER.info("Starting at: {}", begin);
        printHeaders(streamType.equals(StreamType.ON_REQUEST) ? context.request().headers() : context.response().headers());

//        if (streamType.equals(StreamType.ON_REQUEST)) {
//            context.request().bodyHandler(b -> LOGGER.info("Body: {}", b.toString()));
//        } else {
//
//
//            TransformableRequestStreamBuilder.on(context.request()).transform(b -> {
//                LOGGER.info("Body:");
//                LOGGER.info(b.toString());
//                return b;
//            }).build().bodyHandler(b -> {
//                LOGGER.info("Body:");
//                LOGGER.info(b.toString());
//            });
//        }
    }

    private void after(ExecutionContext context) {
        LOGGER.info("---------------------------------");
        LOGGER.info("After policy: " + id());
        end = Instant.now();
        LOGGER.info("Ending at: " + end);
        printHeaders(streamType.equals(StreamType.ON_REQUEST) ? context.request().headers() : context.response().headers());
        LOGGER.info("Policy took {} ms to execute", Duration.between(begin, end).toMillis());
        LOGGER.info("---------------------------------");
    }

    private void printHeaders(HttpHeaders httpHeaders) {
        LOGGER.info("Headers:");
        httpHeaders.forEach((key, values) ->
        {
            LOGGER.info("   {} - {}", key, String.join(",", values));
        });
    }

//    @Override
//    public int compareTo(DebugPolicy o) {
//        return this.creationDate.compareTo(o.creationDate);
//    }
}
