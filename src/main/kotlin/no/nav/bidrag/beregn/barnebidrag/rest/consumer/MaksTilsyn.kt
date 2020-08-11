package no.nav.bidrag.beregn.barnebidrag.rest.consumer

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class MaksTilsyn(
    var antBarnTom: Int? = null,
    var datoFom: LocalDate? = null,
    var datoTom: LocalDate? = null,
    var maksBelopTilsyn: BigDecimal? = null
)
