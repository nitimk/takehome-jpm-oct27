# start cassandra cluster

```docker run --name cassandra-db -d -p 9042:9042 cassandra```

# login to running cassandra cluster 
```docker exec -it cassandra-db cqlsh```

### run this commands inside Cassandra shell 
    csql>

    show keyspaces;
    
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


### mvn commands 

    mvn clean install

    mvn spring-boot:run 


### kafka steps 



Verify if topic is created or not 

    docker exec -it kafka-learn kafka-topics --list --bootstrap-server localhost:29092

optional: Create a producer 


Create a consumer 
    docker exec -it kafka-learn kafka-console-consumer --topic topic1234 --bootstrap-server localhost:9092 --from-beginning 
