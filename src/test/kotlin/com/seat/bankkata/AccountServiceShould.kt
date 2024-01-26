package com.seat.bankkata

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AccountServiceShould {

    private val clock: Clock = mockk<Clock>()

    private val output: Output = mockk<Output>(relaxUnitFun = true)

    private val statementPrinter: StatementPrinter = StatementPrinter(output)

    private val transactionRepository: TransactionRepository = mockk<TransactionRepository>(relaxUnitFun = true)

    private lateinit var account: AccountService


    @BeforeEach
    internal fun setUp() {
        account = AccountService(clock, transactionRepository, statementPrinter)
    }

    @Test
    fun `deposit amount into the account`() {
        val amount = 100
        every { clock.today() } returns TODAY

        account.deposit(amount)

        val transaction = Transaction(amount, TODAY)
        verify { transactionRepository.add(transaction) }
    }

    @Test
    fun `withdraw amount from the account`() {
        val amount = 100
        every { clock.today() } returns TODAY

        account.withdraw(amount)

        val transaction = Transaction(-amount, TODAY)
        verify { transactionRepository.add(transaction) }
    }

    @Test
    fun `print statement`() {
        val transactions: List<Transaction> = listOf(
            Transaction(1000, "01/04/2014"),
            Transaction(-100, "02/04/2014"),
            Transaction(500, "10/04/2014")
        )
        every { transactionRepository.all() } returns transactions

        account.printStatement()

        verifySequence {
            output.printLine("DATE | AMOUNT | BALANCE")
            output.printLine("10/04/2014 | 500.00 | 1400.00")
            output.printLine("02/04/2014 | -100.00 | 900.00")
            output.printLine("01/04/2014 | 1000.00 | 1000.00")
        }
    }

    companion object {
        private const val TODAY: String = "15/09/2020"
    }
}