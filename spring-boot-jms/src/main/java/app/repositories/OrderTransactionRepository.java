package app.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import app.documents.OrderTransaction;

public interface OrderTransactionRepository extends MongoRepository<OrderTransaction, String> {}
