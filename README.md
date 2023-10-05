# E-commerce Cart Abandonment Producer App

## Overview

The Java application is designed to simulate cart abandonment events within an e-commerce system. Cart abandonment refers to the situation where a user adds products to their online shopping cart but leaves the website or application without completing the purchase. This application generates simulated instances of cart abandonment, mimicking user behavior, and streams these events to an Amazon Kinesis Data Stream, leveraging the power of the Kinesis Producer Library (KPL).

### Advantages of KPL Usage:

- **Efficient Streaming:** KPL ensures high-speed, low-latency data delivery for real-time cart abandonment analysis.

- **Reliability:** Built-in retry mechanisms handle network issues, guaranteeing data delivery.

- **Aggregation:** KPL supports record aggregation for enhanced performance in high-velocity scenarios.

- **Simplicity:** It simplifies Kinesis data streaming implementation, reducing development time.

- **Robust Error Handling:** Detailed error logs aid in issue tracking and data integrity maintenance.

Integrating KPL elevates application performance and reliability, empowering businesses with valuable user behavior insights for improved conversion rates.

## Features

- **Cart Abandonment Event Simulation:** The producer application simulates cart abandonment events within an e-commerce system. It generates realistic data, including products, customers, sellers, and timestamps, mimicking user behavior.
- **Amazon Kinesis Integration:** The application streams generated cart abandonment events to an Amazon Kinesis Data Stream. This integration allows you to capture and analyze the data in real-time.
- **Error Handling:** The producer application includes robust error handling mechanisms. It logs any encountered errors, making it easier to troubleshoot and diagnose issues during operation.
- **Logging:** The application utilizes log4j for robust logging, allowing you to monitor its performance and troubleshoot any issues effectively.
- **Configurability:** You can configure the application with various parameters, such as the Kinesis stream name, AWS region, and more, to adapt it to your specific environment and requirements.

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
* **log4j.properties** - Sets up logging for the application, including both console output and log file (`kinesis.log`).

## Prerequisites
Before running this demo project, make sure you have the following prerequisites:

* **Java Development Kit (JDK)**: Ensure you have Java 8 or later installed.
* **Amazon Web Services (AWS) Account**: You need an AWS account to create and manage Kinesis streams, DynamoDB tables, and other AWS resources.
* **AWS CLI**: Install and configure the AWS Command Line Interface (CLI) to set up your AWS credentials.

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
   java -jar target/aws-kpl-producer-jar-with-dependencies.jar <streamName> <region>
   ```
   Replace `stream_name` and `region` with your specific Kinesis stream name and AWS region.
5. The application will start generating and producing events to the specified Kinesis stream.

## Configuration
### Record aggregation
Record aggregation allows for combining multiple records into a single Kinesis Data Streams record. This allows for improving their per shard throughput. Aggregation is enabled by default but can be disabled by providing command-line arguments.
```bash
java -jar target/aws-kpl-producer-jar-with-dependencies.jar <streamName> <region> --no-agg
```
## Shutdown and Error Handling
The project includes graceful shutdown and error handling mechanisms. It ensures that when the application is terminated, it will checkpoint its progress and log any encountered errors.
