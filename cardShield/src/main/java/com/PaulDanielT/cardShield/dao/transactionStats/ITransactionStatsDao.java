package com.PaulDanielT.cardShield.dao.transactionStats;

import com.PaulDanielT.cardShield.model.TransactionStats;

import java.util.List;

public interface ITransactionStatsDao {
    // Create
    void save(TransactionStats stats);

    // Read
    TransactionStats findByCustomerId(Integer customerId);
    List<TransactionStats> findTopSpendingCustomers(int limit);

    // Update
    void update(TransactionStats stats);

    // Delete
    void deleteByCustomerId(Integer customerId);
}
