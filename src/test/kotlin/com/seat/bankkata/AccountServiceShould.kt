package com.seat.bankkata

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AccountServiceShould {
    private  val TODAY : String = "15/09/2020"

    private val clock : Clock = mockk<Clock>()

    private val transactionRepository : TransactionRepository = mockk<TransactionRepository>(relaxUnitFun = true)

    private lateinit var account: AccountService


    @BeforeEach
    internal fun setUp() {
        every { clock.today() } returns TODAY
        account = AccountService(clock, transactionRepository)
    }

    @Test
    fun `deposit amount into the account`() {
        val amount = 100

        account.deposit(amount)

        val transaction = Transaction(amount, TODAY)
        verify { transactionRepository.add(transaction) }
    }
}