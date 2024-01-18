package com.seat.bankkata.acceptance

import com.seat.bankkata.AccountService
import com.seat.bankkata.Clock
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream


class PrintStatementsFeature {
    private lateinit var clock: Clock

    private lateinit var account: AccountService

    private val output = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        clock = mockk<Clock>()
        account = AccountService(clock)
        redirectConsoleOutputTo(output)
    }

    @Test
    fun `print statement contains all transactions in descending order`() {
        every {  clock.today() } returns "01/04/2014"
        account.deposit(1000)
        every {  clock.today() } returns "02/04/2014"
        account.withdraw(100)
        every {  clock.today() } returns "10/04/2014"
        account.deposit(500)

        account.printStatement()

        assertThat(output.toString())
            .isEqualTo(
                "DATE       | AMOUNT  | BALANCE\n" +
                "10/04/2014 | 500.00  | 1400.00\n" +
                "02/04/2014 | -100.00 | 900.00\n" +
                "01/04/2014 | 1000.00 | 1000.00\n"
            )
    }


    private fun redirectConsoleOutputTo(outputStream: ByteArrayOutputStream) {
        System.setOut(PrintStream(outputStream))
    }
}