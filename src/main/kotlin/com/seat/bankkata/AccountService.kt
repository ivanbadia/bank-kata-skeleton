package com.seat.bankkata

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
        output.printLine("DATE | AMOUNT | BALANCE")
        val previousBalance = 0
        var newBalance = previousBalance
        transactionRepository.all()
            .map { transaction ->
                newBalance += transaction.amount
                "${transaction.day} | ${transaction.amount.toBigDecimal().setScale(2)} | ${
                    newBalance.toBigDecimal().setScale(2)
                }"
            }
            .reversed()
            .forEach(output::printLine)

    }

}
