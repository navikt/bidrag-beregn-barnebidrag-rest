package no.nav.bidrag.beregn.barnebidrag.rest.consumer

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class Samvaersfradrag(
    var samvaersklasse: String? = null,
    var alderTom: Int? = null,
    var datoFom: LocalDate? = null,
    var datoTom: LocalDate? = null,
    var antDagerTom: Int? = null,
    var antNetterTom: Int? = null,
    var belopFradrag: BigDecimal? = null
)
