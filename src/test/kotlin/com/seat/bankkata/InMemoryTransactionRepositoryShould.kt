package com.seat.bankkata

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class InMemoryTransactionRepositoryShould {

    private val transactionRepository: TransactionRepository = InMemoryTransactionRepository()

    @Test
    fun `retrieve transactions in order`() {
        val oneOfJanuaryDeposit = Transaction(1000, "01/01/2024")
        transactionRepository.add(oneOfJanuaryDeposit)
        val secondOfJanuaryWithdrawal = Transaction(-1000, "02/01/2024")
        transactionRepository.add(secondOfJanuaryWithdrawal)
        val thirdOfJanuaryDeposit = Transaction(100, "03/01/2024")
        transactionRepository.add(thirdOfJanuaryDeposit)

        val transactions = transactionRepository.all()


        assertThat(transactions).isEqualTo(
                listOf(
                    oneOfJanuaryDeposit,
                    secondOfJanuaryWithdrawal,
                    thirdOfJanuaryDeposit
                )
            )


    }
}