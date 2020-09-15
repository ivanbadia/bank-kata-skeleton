package com.seat.bankkata

import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifyOrder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
internal class ConsoleStatementPrinterShould {
    @MockK(relaxUnitFun = true)
    private lateinit var console : Console

    private lateinit var statementPrinter : StatementPrinter

    @BeforeEach
    internal fun setUp() {
        statementPrinter = ConsoleStatementPrinter(console)
    }

    @Test
    fun `print statement`() {
        val transactions: List<Transaction> = listOf(
            Transaction("01/04/2014", 1000),
            Transaction("02/04/2014", -100),
            Transaction("10/04/2014", 500)
        )

        statementPrinter.print(transactions)

        verifyOrder {
            console.printLine("DATE | AMOUNT | BALANCE")
            console.printLine("10/04/2014 | 500.00 | 1400.00")
            console.printLine("02/04/2014 | -100.00 | 900.00")
            console.printLine("01/04/2014 | 1000.00 | 1000.00")
        }
    }
}