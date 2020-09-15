package com.seat.bankkata

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class AccountServiceShould {


    @MockK(relaxUnitFun = true)
    private lateinit var transactionRepository: TransactionRepository
    @MockK
    private lateinit var clock: Clock
    @MockK(relaxUnitFun = true)
    private lateinit var statementPrinter: StatementPrinter

    private lateinit var accountService: AccountService

    @BeforeEach
    internal fun setUp() {
        accountService = AccountService(clock, transactionRepository, statementPrinter)
    }

    @Test
    fun `deposit amount into the account`() {
        every { clock.today() } returns TODAY
        val amount = 100

        accountService.deposit(amount)

        val transaction = Transaction(TODAY, amount)
        verify { transactionRepository.add(transaction) }
    }


    @Test
    fun `withdraw amount from the account`() {
        every { clock.today() } returns TODAY
        val amount = 100

        accountService.withdraw(amount)

        val transaction = Transaction(TODAY, -amount)
        verify { transactionRepository.add(transaction) }
    }

    @Test
    fun `print statement`() {
        every { transactionRepository.all() } returns TRANSACTIONS

        accountService.printStatement()

        verify { statementPrinter.print(TRANSACTIONS) }
    }

    companion object {
        private const val TODAY : String = "15/09/2020"
        private val TRANSACTIONS: List<Transaction> = listOf(Transaction(TODAY, 300))
    }

}