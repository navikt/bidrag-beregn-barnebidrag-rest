package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

// Resultat
@Schema(description = "Inntekttype og -beløp")
data class Inntekt(
  @Schema(description = "Inntekt type") var inntektType: String = "",
  @Schema(description = "Inntekt beløp") var inntektBelop: BigDecimal = BigDecimal.ZERO
)
