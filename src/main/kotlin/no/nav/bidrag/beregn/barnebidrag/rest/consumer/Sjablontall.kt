package no.nav.bidrag.beregn.barnebidrag.rest.consumer

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class Sjablontall (
    val typeSjablon: String,
    val datoFom: LocalDate,
    val datoTom: LocalDate,
    val verdi: BigDecimal
)
