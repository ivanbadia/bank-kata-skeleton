package com.seat.bankkata

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate


internal class InMemoryTransactionRepositoryShould {
    private val transactionRepository : TransactionRepository = InMemoryTransactionRepository()

    @Test
    fun `retrieve transactions in order`() {
        val firstTransaction = Transaction(LocalDate.of(2016, 9, 6), 1000)
        transactionRepository.add(firstTransaction)
        val secondTransaction = Transaction(LocalDate.of(2016, 9, 7), -1000)
        transactionRepository.add(secondTransaction)
        val thirdTransaction = Transaction(LocalDate.of(2016, 9, 8), 100)
        transactionRepository.add(thirdTransaction)

        val transactions = transactionRepository.all()

        assertThat(transactions)
            .isEqualTo(listOf(firstTransaction, secondTransaction, thirdTransaction))
    }
}