package com.seat.bankkata.acceptance

import com.seat.bankkata.AccountService
import com.seat.bankkata.Clock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.time.LocalDate


@ExtendWith(MockitoExtension::class)
class PrintStatementsFeature {
    @Mock
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
        given(clock.today()).willReturn("01/04/2014")
        account.deposit(1000)
        given(clock.today()).willReturn("02/04/2014")
        account.withdraw(100)
        given(clock.today()).willReturn("10/04/2014")
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