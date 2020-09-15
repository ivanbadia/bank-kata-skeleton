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

    private lateinit var accountService: AccountService

    @BeforeEach
    internal fun setUp() {
        accountService = AccountService(clock, transactionRepository)
    }

    @Test
    fun `deposit amount into the account`() {
        every { clock.today() } returns TODAY
        val amount = 100

        accountService.deposit(amount)

        val transaction = Transaction(TODAY, amount)
        verify { transactionRepository.add(transaction) }
    }


    companion object {
        private val TODAY : LocalDate = LocalDate.now()
    }

}