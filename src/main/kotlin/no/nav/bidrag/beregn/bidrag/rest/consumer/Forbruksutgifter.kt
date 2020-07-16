package no.nav.bidrag.beregn.bidrag.rest.consumer

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class Forbruksutgifter(
    var alderTom: Int? = null,
    var datoFom: LocalDate? = null,
    var datoTom: LocalDate? = null,
    var belopForbrukTot: BigDecimal? = null
)
