package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import com.fasterxml.jackson.databind.JsonNode
import io.swagger.v3.oas.annotations.media.Schema
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import java.math.BigDecimal
import java.time.LocalDate

@Schema(description = "Grunnlaget for en beregning av barnebidrag")
data class BeregnGrunnlag(
  @Schema(description = "Beregn fra-dato") val beregnDatoFra: LocalDate? = null,
  @Schema(description = "Beregn til-dato") val beregnDatoTil: LocalDate? = null,
  @Schema(description = "Periodisert liste over grunnlagselementer") val grunnlagListe: List<Grunnlag>? = null
) {

  fun valider() {
    if (beregnDatoFra == null) throw UgyldigInputException("beregnDatoFra kan ikke være null")
    if (beregnDatoTil == null) throw UgyldigInputException("beregnDatoTil kan ikke være null")
    if (grunnlagListe != null) grunnlagListe.map { it.valider() } else throw UgyldigInputException("grunnlagListe kan ikke være null")
  }
}

@Schema(description = "Grunnlag")
data class Grunnlag(
  @Schema(description = "Referanse") val referanse: String? = null,
  @Schema(description = "Type") val type: String? = null,
  @Schema(description = "Innhold") val innhold: JsonNode? = null
) {

  fun valider() {
    if (referanse == null) throw UgyldigInputException("referanse kan ikke være null")
    if (type == null) throw UgyldigInputException("type kan ikke være null")
    if (innhold == null) throw UgyldigInputException("innhold kan ikke være null")
  }
}

@Schema(description = "Grunnlaget for en beregning av forholdsmessig fordeling")
data class BeregnGrunnlagFF(
  @Schema(description = "Beregn fra-dato") val beregnDatoFra: LocalDate? = null,
  @Schema(description = "Beregn til-dato") val beregnDatoTil: LocalDate? = null,
  @Schema(description = "Periodisert liste over grunnlagselementer") val grunnlagListe: List<GrunnlagFF>? = null
) {

  fun valider() {
    if (beregnDatoFra == null) throw UgyldigInputException("beregnDatoFra kan ikke være null")
    if (beregnDatoTil == null) throw UgyldigInputException("beregnDatoTil kan ikke være null")
    if (grunnlagListe != null) grunnlagListe.map { it.valider() } else throw UgyldigInputException("grunnlagListe kan ikke være null")
  }
}

@Schema(description = "Grunnlag")
data class GrunnlagFF(
  @Schema(description = "Type") val type: String? = null,
  @Schema(description = "Innhold") val innhold: JsonNode? = null
) {

  fun valider() {
    if (type == null) throw UgyldigInputException("type kan ikke være null")
    if (innhold == null) throw UgyldigInputException("innhold kan ikke være null")
  }
}

// Hjelpeklasse
data class BeregnetBidragSakPeriode(
  val sakNr: Int,
  val datoFom: LocalDate,
  val datoTil: LocalDate,
  val soknadsbarnId: Int,
  val belop: BigDecimal
)
