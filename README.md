# E-commerce Cart Abandonment Producer App

## Overview

The Java application is designed to simulate cart abandonment events within an e-commerce system. Cart abandonment refers to the situation where a user adds products to their online shopping cart but leaves the website or application without completing the purchase. This application generates simulated instances of cart abandonment, mimicking user behavior, and streams these events to an Amazon Kinesis Data Stream.

## Project Structure

The project includes the following classes and files:

* **ProducerApp.java** - The main application class responsible for generating and streaming cart abandonment events to
   Kinesis.
* **CartAbandonmentEventGenerator.java** - A utility class for generating simulated cart abandonment events with random
   data.
* **CartAbandonmentEvent.java** - A POJO class representing a cart abandonment event, including cart items, customer
   ID, order ID, seller ID, and timestamp.
* **CartItem.java** - A POJO class representing an individual item within a shopping cart, including product name,
   product code, quantity, and price.

* **Product.java** - A POJO class representing product information, including code, name, and price.

* **pom.xml** - The Maven project configuration file that includes project details, dependencies, and build settings.

## Getting Started

To run the application, follow these steps:

1. Clone this repository to your local machine.

2. Ensure you have Java 8 or later installed.

3. Build the project using Maven:

   ```bash
   mvn clean package
   ```
4. Run the application

   ```bash
   java -jar target/producer-app-1.0.0.jar <streamName> <region>
   ```
   Replace `stream_name` and `region` with your specific Kinesis stream name and AWS region.

## Configuration
### Record aggregation
Record aggregation allows for combining multiple records into a single Kinesis Data Streams record. This allows for improving their per shard throughput. Aggregation is enabled by default but can be disabled by providing command-line arguments.
```bash
java -jar target/producer-app-1.0.0.jar <streamName> <region> --no-agg
```