package com.demo.kinesis;

import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration;
import com.amazonaws.services.kinesis.producer.UserRecordResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * The {@code ProducerApp} class is responsible for continuous producing and sending cart abandonment
 * events to a Kinesis Data Stream within an e-commerce application. It simulates the behavior of users
 * adding products to their carts and then potentially abandoning them.
 *
 * <p>The key responsibilities of this class include generating simulated cart abandonment events,
 * serializing and sending these events to a Kinesis Data Stream.
 */
public class ProducerApp {
    static final Logger logger = LogManager.getLogger(ProducerApp.class);
    static final ObjectMapper objMapper = new ObjectMapper();

    public static void main(String[] args) {
        // Parse command-line arguments
        String streamName = args[0];
        String region = args[1];

        logger.info(String.format("Starting Kinesis Producer Library Application for Stream %s in %s", streamName, region));

        var producerConfig = new KinesisProducerConfiguration().setRegion(region);

        // Check if aggregation should be disabled based on command-line argument
        if (args.length == 3 && args[2].equalsIgnoreCase("--no-agg")) {
            logger.info("Disabling aggregation");
            producerConfig.setAggregationEnabled(false);
        }

        // Instantiate KPL client
        var producer = new KinesisProducer(producerConfig);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down program");
            producer.flush();
        }, "producer-shutdown"));

        List<Future<UserRecordResult>> putFutures = new LinkedList<>();

        // Continuously produce records
        int i = 0;
        while (true) {
            // Generate fake cart abandonment event data
            var abandonmentEvent = CartAbandonmentEventGenerator.makeEvent();
            logger.info(String.format("Generated %s", abandonmentEvent));

            // Serialize and add to producer to be batched and sent to Kinesis
            ByteBuffer data;
            try {
                data = ByteBuffer.wrap(objMapper.writeValueAsBytes(abandonmentEvent));
            } catch (JsonProcessingException e) {
                logger.error(String.format("Failed to serialize %s", abandonmentEvent), e);
                continue; // Continue to the next iteration if serialization fails
            }
            ListenableFuture<UserRecordResult> future = producer.addUserRecord(
                    streamName,
                    abandonmentEvent.getCustomerID(), // The customerId is used as the partition key
                    data
            );
            putFutures.add(future);

            // Register a callback handler on the async Future to handle success and failure cases
            Futures.addCallback(future, new FutureCallback<>() {
                @Override
                public void onFailure(Throwable t) {
                    logger.error("Failed to produce batch", t);
                }

                @Override
                public void onSuccess(UserRecordResult result) {
                    logger.info(String.format("Produced User Record to shard %s at position %s",
                            result.getShardId(),
                            result.getSequenceNumber()));
                }
            }, MoreExecutors.directExecutor());

            // Introduce artificial delay for demonstration purpose / visual tracking of logging
            if (++i % 100 == 0) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    logger.warn("Sleep interrupted", e);
                }
            }
        }
    }
}
