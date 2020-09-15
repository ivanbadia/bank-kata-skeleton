package com.seat.bankkata

class AccountService(
    private val clock: Clock,
    private val transactionRepository: TransactionRepository,
    private val statementPrinter: StatementPrinter
) {
    fun deposit(amount: Int) {
        transactionRepository.add(Transaction(clock.today(), amount))
    }

    fun withdraw(amount: Int) {
        transactionRepository.add(Transaction(clock.today(), -amount))
    }

    fun printStatement() {
        val transactions = transactionRepository.all()
        statementPrinter.print(transactions)
    }
}
