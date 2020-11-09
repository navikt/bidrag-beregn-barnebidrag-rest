package no.nav.bidrag.beregn.barnebidrag.rest.consumer

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class Samvaersfradrag(
    val samvaersklasse: String,
    val alderTom: Int,
    val datoFom: LocalDate,
    val datoTom: LocalDate,
    val antDagerTom: Int,
    val antNetterTom: Int,
    val belopFradrag: BigDecimal
)
