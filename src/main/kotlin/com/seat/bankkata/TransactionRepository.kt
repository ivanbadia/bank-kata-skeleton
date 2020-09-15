package com.seat.bankkata

interface TransactionRepository {
    fun add(transaction: Transaction)
}
