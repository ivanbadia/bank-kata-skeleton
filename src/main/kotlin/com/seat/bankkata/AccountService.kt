package com.seat.bankkata

class AccountService(
    val clock: Clock,
    val transactionRepository: TransactionRepository,
    val statementPrinter: StatementPrinter
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
