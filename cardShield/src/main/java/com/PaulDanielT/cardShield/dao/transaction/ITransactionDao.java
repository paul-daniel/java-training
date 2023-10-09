package com.PaulDanielT.cardShield.dao.transaction;

import com.PaulDanielT.cardShield.model.Transaction;

import java.util.Date;
import java.util.List;

public interface ITransactionDao {
    // Create
    void save(Transaction transaction);

    // Read
    Transaction findById(Integer transactionId);
    List<Transaction> findAll();
    List<Transaction> findByCardId(Integer cardId);
    List<Transaction> findByVendorId(Integer vendorId);
    List<Transaction> findByDateRange(Date startDate, Date endDate);

    // Update
    void update(Transaction transaction);

    // Delete
    void deleteById(Integer transactionId);
}
