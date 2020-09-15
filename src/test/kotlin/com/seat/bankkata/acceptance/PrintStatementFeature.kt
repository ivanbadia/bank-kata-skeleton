package com.seat.bankkata.acceptance

import com.seat.bankkata.AccountService
import com.seat.bankkata.Clock
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.time.LocalDate


@ExtendWith(MockKExtension::class)
class PrintStatementsFeature {
    @MockK
    private lateinit var clock: Clock

    private lateinit var account: AccountService

    private val output = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        account = AccountService(clock)
        redirectConsoleOutputTo(output)
    }

    @Test
    fun `print statement contains all transactions in descending order`() {
        every { clock.today() } returns LocalDate.of(2014, 4, 1)
        account.deposit(1000)
        every { clock.today() } returns LocalDate.of(2014, 4, 2)
        account.withdraw(100)
        every { clock.today() } returns LocalDate.of(2014, 4, 10)
        account.deposit(500)

        account.printStatement()

        assertThat(output.toString())
            .isEqualTo(
                "DATE | AMOUNT | BALANCE\n" +
                "10/04/2014 | 500.00 | 1400.00\n" +
                "02/04/2014 | -100.00 | 900.00\n" +
                "01/04/2014 | 1000.00 | 1000.00\n"
            )
    }


    private fun redirectConsoleOutputTo(outputStream: ByteArrayOutputStream) {
        System.setOut(PrintStream(outputStream))
    }
}