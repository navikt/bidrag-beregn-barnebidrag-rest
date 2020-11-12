package no.nav.bidrag.beregn.barnebidrag.rest.consumer

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class Barnetilsyn(
  val typeStonad: String? = null,
  val typeTilsyn: String? = null,
  val datoFom: LocalDate? = null,
  val datoTom: LocalDate? = null,
  val belopBarneTilsyn: BigDecimal? = null
)
