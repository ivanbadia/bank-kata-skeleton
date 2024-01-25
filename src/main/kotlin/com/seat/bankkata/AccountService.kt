package com.seat.bankkata

class AccountService(private val clock: Clock, private val transactionRepository: TransactionRepository) {
    fun deposit(amount: Int) {
        val transaction = Transaction(amount, clock.today())
        transactionRepository.add(transaction)
    }

    fun withdraw(amount: Int) {
        TODO("Not implemented")
    }

    fun printStatement() {
        TODO("Not implemented")
    }
}
