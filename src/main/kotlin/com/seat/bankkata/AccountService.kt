package com.seat.bankkata

private const val STATEMENT_HEADER = "DATE | AMOUNT | BALANCE"

class AccountService(
    private val clock: Clock,
    private val transactionRepository: TransactionRepository,
    private val output: Output
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
        printHeader()
        printStatementTransactions()
    }

    private fun printStatementTransactions() {
        var balance = 0
        transactionRepository.all()
            .map { transaction ->
                balance += transaction.amount
                Pair(transaction, balance)
            }
            .map(toLineStatement())
            .reversed()
            .forEach(output::printLine)
    }

    private fun toLineStatement(): (Pair<Transaction, Int>) -> String {
        return { (transaction, balance) ->
            "${transaction.day} | ${transaction.amount.toBigDecimal().setScale(2)} | ${
                balance.toBigDecimal().setScale(2)
            }"
        }
    }

    private fun printHeader() {
        output.printLine(STATEMENT_HEADER)
    }

}
