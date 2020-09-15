package com.seat.bankkata

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


private val DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
private const val STATEMENT_HEADER = "DATE | AMOUNT | BALANCE"

class ConsoleStatementPrinter(private val console: Console) : StatementPrinter {

    override fun print(transactions: List<Transaction>) {
        printHeader()
        printStatementLinesFor(transactions)
    }

    private fun printHeader() {
        console.printLine(STATEMENT_HEADER)
    }

    private fun printStatementLinesFor(transactions: List<Transaction>) {
        val previousBalance = 0
        transactions
            .map(toStatementLine(previousBalance))
            .reversed()
            .forEach(console::printLine)
    }

    private fun toStatementLine(balance: Int): (Transaction) -> String {
        var newBalance = balance
        return { transaction ->
            newBalance += transaction.amount
            "${formatDate(transaction.date)} | ${formatNumber(transaction.amount)} | ${formatNumber(newBalance)}"
        }
    }

    private fun formatDate(date: LocalDate): String? {
        return DATE_TIME_FORMATTER.format(date)
    }

    private fun formatNumber(amount: Int): String {
        val decimalFormat = DecimalFormat("#.00", DecimalFormatSymbols.getInstance(Locale.UK))
        return decimalFormat.format(amount)
    }
}