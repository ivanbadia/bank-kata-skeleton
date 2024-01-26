package com.seat.bankkata.acceptance

import com.seat.bankkata.*
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import javax.swing.plaf.nimbus.State


class PrintStatementsFeature {
    private val clock: Clock = mockk<Clock>()
    private val transactionRepository: TransactionRepository = InMemoryTransactionRepository()
    private val consoleOutput : ConsoleOutput = ConsoleOutput()

    private lateinit var account: AccountService


    private val output = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        account = AccountService(clock, transactionRepository, StatementPrinter(consoleOutput))
        redirectConsoleOutputTo(output)
    }

    @Test
    fun `print statement contains all transactions in descending order`() {
        every { clock.today() } returns "01/04/2014"
        account.deposit(1000)
        every { clock.today() } returns "02/04/2014"
        account.withdraw(100)
        every { clock.today() } returns "10/04/2014"
        account.deposit(500)

        account.printStatement()

        assertThat(consoleOutput.toString())
            .isEqualTo(
                """
                DATE       | AMOUNT  | BALANCE
                10/04/2014 | 500.00  | 1400.00
                02/04/2014 | -100.00 | 900.00
                01/04/2014 | 1000.00 | 1000.00
                """.trimIndent()
            )
    }


    private fun redirectConsoleOutputTo(outputStream: ByteArrayOutputStream) {
        System.setOut(PrintStream(outputStream))
    }
}