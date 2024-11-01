package com.example.demo.repository;

import com.example.demo.model.Message;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface MessageRepository extends CassandraRepository<Message, UUID> {
}
