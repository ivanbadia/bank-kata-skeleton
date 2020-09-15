package com.seat.bankkata

interface TransactionRepository {
    fun add(transaction: Transaction)
    fun all() : List<Transaction>
}
