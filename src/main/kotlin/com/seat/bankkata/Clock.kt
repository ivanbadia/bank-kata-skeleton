package com.seat.bankkata

import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

class Clock {
    fun today(): String = DATE_TIME_FORMATTER.format(LocalDate.now())
}