package com.seat.bankkata

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate


internal class InMemoryTransactionRepositoryShould {
    private val transactionRepository : TransactionRepository = InMemoryTransactionRepository()

    @Test
    fun `retrieve transactions in order`() {
        val firstTransaction = Transaction("01/04/2020", 1000)
        transactionRepository.add(firstTransaction)
        val secondTransaction = Transaction("02/04/2020", -1000)
        transactionRepository.add(secondTransaction)
        val thirdTransaction = Transaction("03/04/2020", 100)
        transactionRepository.add(thirdTransaction)

        val transactions = transactionRepository.all()

        assertThat(transactions)
            .isEqualTo(listOf(firstTransaction, secondTransaction, thirdTransaction))
    }
}