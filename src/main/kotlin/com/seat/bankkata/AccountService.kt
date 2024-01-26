package com.seat.bankkata



class AccountService(
    private val clock: Clock,
    private val transactionRepository: TransactionRepository,
    private val statementPrinter: StatementPrinter
) {
    fun deposit(amount: Int) {
        val transaction = Transaction(amount, clock.today())
        transactionRepository.add(transaction)
    }

    fun withdraw(amount: Int) {
        val transaction = Transaction(-amount, clock.today())
        transactionRepository.add(transaction)
    }

    fun printStatement() {
        val transactions = transactionRepository.all()
        statementPrinter.print(transactions)
    }


}
