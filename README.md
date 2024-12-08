# start cassandra cluster

`docker run --name cassandra-db -d -p 9042:9042 cassandra`

# login to running cassandra cluster

`docker exec -it cassandra-db cqlsh`

### run this commands inside Cassandra shell

    csql>

    show keyspaces;

    DESCRIBE KEYSPACES;

    CREATE KEYSPACE my_keyspace_1234
    WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

    USE my_keyspace_1234;

    DESCRIBE TABLES;

    CREATE TABLE my_cassandra_table_1234 (
    id UUID PRIMARY KEY,
    message text
    );

    INSERT INTO my_cassandra_table_1234 (id, message)
    VALUES (uuid(), 'Hello, World 1234!');

    SELECT * FROM my_cassandra_table_1234;

    exit



### kafka steps

    docker-compose up -d


    -- to verify if containers are up and running
    docker ps -a

    -- create a topic
    docker exec -it kafka-learn kafka-topics --create --topic topic1234 --bootstrap-server localhost:29092 --partitions 1 --replication-factor 1


    -- Verify if topic is created or not
    docker exec -it kafka-learn kafka-topics --list --bootstrap-server localhost:29092

    -- optional: start a producer and produce some messages to test
    docker exec -it kafka-learn kafka-console-producer --topic topic1234 --bootstrap-server localhost:29092

    -- Create a consumer
    docker exec -it kafka-learn kafka-console-consumer --topic topic1234 --bootstrap-server localhost:9092 --from-beginning

## Steps to run Spring Application

    mvn clean install

    mvn spring-boot:run

## APIs

#### API to get Messages from Cassandra 
GET: `localhost:8081/api/messages`

#### API to post Messages to Cassandra (which also produces messages to kafka topic)

POST: `localhost:8081/api/messages`

Body:
```
{
    "message": "Hello, this is a test message for Kafka!"
}
```


#### API that can publish messages to Kafka:
POST: `localhost:8080/api/kafka/publish`

Body:
```
{
    "message": "Hello, this is a test message for Kafka!"
}
```
