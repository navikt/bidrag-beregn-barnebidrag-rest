package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.v3.oas.annotations.media.Schema
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnetUnderholdskostnadResultatCore
import no.nav.bidrag.beregn.underholdskostnad.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.underholdskostnad.dto.ResultatPeriodeCore
import java.math.BigDecimal

// Grunnlag
@Schema(description = "Grunnlaget for en underholdskostnadsberegning for bidragsmottaker")
data class BeregnBMUnderholdskostnadGrunnlag(
  @Schema(description = "Periodisert liste over bidragsmottakers barnetilsyn med stønad") val barnetilsynMedStonadPeriodeListe: List<BarnetilsynMedStonadPeriode>? = null,
  @Schema(description = "Periodisert liste over bidragsmottakers utgifter til forpleining") val forpleiningUtgiftPeriodeListe: List<ForpleiningUtgiftPeriode>? = null
)

@Schema(description = "Bidragsmottakers barnetilsyn med stønad")
data class BarnetilsynMedStonadPeriode(
  @Schema(description = "Bidragsmottakers barnetilsyn med stønad fra-til-dato") var barnetilsynMedStonadDatoFraTil: Periode? = null,
  @Schema(description = "Søknadsbarnets person-id") var barnetilsynMedStonadSoknadsbarnPersonId: Int? = null,
  @Schema(description = "Bidragsmottakers barnetilsyn med stønad tilsyn-type") var barnetilsynMedStonadTilsynType: String? = null,
  @Schema(description = "Bidragsmottakers barnetilsyn med stønad stønad-type") var barnetilsynMedStonadStonadType: String? = null
) {

  fun validerBarnetilsynMedStonad() {
    if (barnetilsynMedStonadDatoFraTil != null) barnetilsynMedStonadDatoFraTil!!.valider("barnetilsynMedStonad")
    else throw UgyldigInputException("barnetilsynMedStonadDatoFraTil kan ikke være null")

    if (barnetilsynMedStonadSoknadsbarnPersonId == null) throw UgyldigInputException("barnetilsynMedStonadSoknadsbarnPersonId kan ikke være null")
    if (barnetilsynMedStonadTilsynType == null) throw UgyldigInputException("barnetilsynMedStonadTilsynType kan ikke være null")
    if (barnetilsynMedStonadStonadType == null) throw UgyldigInputException("barnetilsynMedStonadStonadType kan ikke være null")
  }
}

@Schema(description = "Bidragsmottakers utgifter til forpleining")
data class ForpleiningUtgiftPeriode(
  @Schema(description = "Bidragsmottakers utgifter til forpleining fra-til-dato") var forpleiningUtgiftDatoFraTil: Periode? = null,
  @Schema(description = "Søknadsbarnets person-id") var forpleiningUtgiftSoknadsbarnPersonId: Int? = null,
  @Schema(description = "Bidragsmottakers utgifter til forpleining beløp") var forpleiningUtgiftBelop: BigDecimal? = null
) {

  fun validerForpleiningUtgift() {
    if (forpleiningUtgiftDatoFraTil != null) forpleiningUtgiftDatoFraTil!!.valider("forpleiningUtgift")
    else throw UgyldigInputException("forpleiningUtgiftDatoFraTil kan ikke være null")

    if (forpleiningUtgiftSoknadsbarnPersonId == null) throw UgyldigInputException("forpleiningUtgiftSoknadsbarnPersonId kan ikke være null")
    if (forpleiningUtgiftBelop == null) throw UgyldigInputException("forpleiningUtgiftBelop kan ikke være null")
  }
}

// Resultat
@Schema(description = "Resultatet av en underholdskostnadsberegning for bidragsmottaker")
data class BeregnBMUnderholdskostnadResultat(
  @Schema(description = "Periodisert liste over resultat av beregning av underholdskostnad") var resultatPeriodeListe: List<ResultatPeriodeUnderholdskostnad> = emptyList()
) {

  constructor(beregnUnderholdskostnadResultat: BeregnetUnderholdskostnadResultatCore) : this(
    resultatPeriodeListe = beregnUnderholdskostnadResultat.resultatPeriodeListe.map { ResultatPeriodeUnderholdskostnad(it) }
  )
}

@Schema(description = "Resultatet av beregning av underholdskostnad for et søknadsbarn for en gitt periode")
data class ResultatPeriodeUnderholdskostnad(
  @Schema(description = "Søknadsbarnets person-id") var resultatSoknadsbarnPersonId: Int = 0,
  @Schema(description = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode = Periode(),
  @Schema(description = "Beregning resultat innhold") var resultatBeregning: ResultatBeregningUnderholdskostnad = ResultatBeregningUnderholdskostnad(),
  @Schema(description = "Beregning grunnlag innhold") var resultatGrunnlag: List<String> = emptyList()
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
    resultatSoknadsbarnPersonId = resultatPeriode.soknadsbarnPersonId,
    resultatDatoFraTil = Periode(resultatPeriode.periode),
    resultatBeregning = ResultatBeregningUnderholdskostnad(resultatPeriode.resultat),
    resultatGrunnlag = resultatPeriode.grunnlagReferanseListe
  )
}

@Schema(description = "Resultatet av beregning av underholdskostnad")
data class ResultatBeregningUnderholdskostnad(
  @Schema(description = "Beløp underholdskostnad") var resultatBelop: BigDecimal = BigDecimal.ZERO
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
    resultatBelop = resultatBeregning.belop
  )
}
