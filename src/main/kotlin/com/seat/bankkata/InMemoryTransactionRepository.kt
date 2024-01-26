package com.seat.bankkata

class InMemoryTransactionRepository : TransactionRepository {

    private val transactions: MutableList<Transaction> = mutableListOf()

    override fun add(transaction: Transaction) {
        transactions.add(transaction)
    }

    override fun all(): List<Transaction> {
        return transactions.toList()
    }
}