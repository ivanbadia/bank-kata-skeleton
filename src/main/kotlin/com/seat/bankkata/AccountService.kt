package com.seat.bankkata

class AccountService(val clock: Clock, val transactionRepository: TransactionRepository) {
    fun deposit(amount: Int) {
        transactionRepository.add(Transaction(clock.today(), amount))
    }

    fun withdraw(amount: Int) {
        TODO("Not implemented")
    }

    fun printStatement() {
        TODO("Not implemented")
    }
}
