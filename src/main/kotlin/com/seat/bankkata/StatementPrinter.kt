package com.seat.bankkata

private const val STATEMENT_HEADER = "DATE       | AMOUNT  | BALANCE"
class StatementPrinter(private val output: Output) {
    fun print(transactions: List<Transaction>) {
        printHeader()
        printStatementLinesFor(transactions)
    }


    private fun printStatementLinesFor(transactions: List<Transaction>) {
        var balance = 0
        transactions
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