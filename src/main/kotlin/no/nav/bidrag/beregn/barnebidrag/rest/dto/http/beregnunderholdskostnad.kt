package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadResultatCore
import no.nav.bidrag.beregn.underholdskostnad.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.underholdskostnad.dto.ResultatGrunnlagCore
import no.nav.bidrag.beregn.underholdskostnad.dto.ResultatPeriodeCore
import java.math.BigDecimal

// Grunnlag
@ApiModel(value = "Grunnlaget for en underholdskostnadsberegning for bidragsmottaker")
data class BeregnBMUnderholdskostnadGrunnlag(
    @ApiModelProperty(
        value = "Periodisert liste over bidragsmottakers barnetilsyn med stønad") val barnetilsynMedStonadPeriodeListe: List<BarnetilsynMedStonadPeriode>? = null,
    @ApiModelProperty(
        value = "Periodisert liste over bidragsmottakers utgifter til forpleining") val forpleiningUtgiftPeriodeListe: List<ForpleiningUtgiftPeriode>? = null
)

@ApiModel(value = "Bidragsmottakers barnetilsyn med stønad")
data class BarnetilsynMedStonadPeriode(
    @ApiModelProperty(value = "Bidragsmottakers barnetilsyn med stønad fra-til-dato") var barnetilsynMedStonadDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Søknadsbarnets person-id") var barnetilsynMedStonadSoknadsbarnPersonId: Int? = null,
    @ApiModelProperty(value = "Bidragsmottakers barnetilsyn med stønad tilsyn-type") var barnetilsynMedStonadTilsynType: String? = null,
    @ApiModelProperty(value = "Bidragsmottakers barnetilsyn med stønad stønad-type") var barnetilsynMedStonadStonadType: String? = null
) {

  fun validerBarnetilsynMedStonad() {
    if (barnetilsynMedStonadDatoFraTil != null) barnetilsynMedStonadDatoFraTil!!.valider("barnetilsynMedStonad")
    else throw UgyldigInputException("barnetilsynMedStonadDatoFraTil kan ikke være null")

    if (barnetilsynMedStonadSoknadsbarnPersonId == null) throw UgyldigInputException("barnetilsynMedStonadSoknadsbarnPersonId kan ikke være null")
    if (barnetilsynMedStonadTilsynType == null) throw UgyldigInputException("barnetilsynMedStonadTilsynType kan ikke være null")
    if (barnetilsynMedStonadStonadType == null) throw UgyldigInputException("barnetilsynMedStonadStonadType kan ikke være null")
  }
}

@ApiModel(value = "Bidragsmottakers utgifter til forpleining")
data class ForpleiningUtgiftPeriode(
    @ApiModelProperty(value = "Bidragsmottakers utgifter til forpleining fra-til-dato") var forpleiningUtgiftDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Søknadsbarnets person-id") var forpleiningUtgiftSoknadsbarnPersonId: Int? = null,
    @ApiModelProperty(value = "Bidragsmottakers utgifter til forpleining beløp") var forpleiningUtgiftBelop: BigDecimal? = null
) {

  fun validerForpleiningUtgift() {
    if (forpleiningUtgiftDatoFraTil != null) forpleiningUtgiftDatoFraTil!!.valider("forpleiningUtgift")
    else throw UgyldigInputException("forpleiningUtgiftDatoFraTil kan ikke være null")

    if (forpleiningUtgiftSoknadsbarnPersonId == null) throw UgyldigInputException("forpleiningUtgiftSoknadsbarnPersonId kan ikke være null")
    if (forpleiningUtgiftBelop == null) throw UgyldigInputException("forpleiningUtgiftBelop kan ikke være null")
  }
}

// Resultat
@ApiModel(value = "Resultatet av en underholdskostnadsberegning for bidragsmottaker")
data class BeregnBMUnderholdskostnadResultat(
    @ApiModelProperty(
        value = "Periodisert liste over resultat av beregning av underholdskostnad") var resultatPeriodeListe: List<ResultatPeriodeUnderholdskostnad> = emptyList()
) {

  constructor(beregnUnderholdskostnadResultat: BeregnUnderholdskostnadResultatCore) : this(
      resultatPeriodeListe = beregnUnderholdskostnadResultat.resultatPeriodeListe.map { ResultatPeriodeUnderholdskostnad(it) }
  )
}

@ApiModel(value = "Resultatet av beregning av underholdskostnad for et søknadsbarn for en gitt periode")
data class ResultatPeriodeUnderholdskostnad(
    @ApiModelProperty(value = "Søknadsbarnets person-id") var resultatSoknadsbarnPersonId: Int = 0,
    @ApiModelProperty(value = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode,
    @ApiModelProperty(value = "Beregning resultat innhold") var resultatBeregning: ResultatBeregningUnderholdskostnad,
    @ApiModelProperty(value = "Beregning grunnlag innhold") var resultatGrunnlag: ResultatGrunnlagUnderholdskostnad
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
      resultatSoknadsbarnPersonId = resultatPeriode.soknadsbarnPersonId,
      resultatDatoFraTil = Periode(resultatPeriode.resultatDatoFraTil),
      resultatBeregning = ResultatBeregningUnderholdskostnad(resultatPeriode.resultatBeregning),
      resultatGrunnlag = ResultatGrunnlagUnderholdskostnad(resultatPeriode.resultatGrunnlag)
  )
}

@ApiModel(value = "Resultatet av beregning av underholdskostnad")
data class ResultatBeregningUnderholdskostnad(
    @ApiModelProperty(value = "Beløp underholdskostnad") var resultatBelop: BigDecimal = BigDecimal.ZERO
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
      resultatBelop = resultatBeregning.resultatBelopUnderholdskostnad
  )
}

@ApiModel(value = "Grunnlaget for beregning av underholdskostnad")
data class ResultatGrunnlagUnderholdskostnad(
    @ApiModelProperty(value = "Søknadsbarnets alder") var soknadsbarnAlder: Int = 0,
    @ApiModelProperty(value = "Barnetilsyn med stønad - tilsyn-type") var barnetilsynMedStonadTilsynType: String,
    @ApiModelProperty(value = "Barnetilsyn med stønad - stønad-type") var barnetilsynMedStonadStonadType: String,
    @ApiModelProperty(value = "Faktisk utgift barnetilsyn - netto-beløp") var nettoBarnetilsynBelop: BigDecimal = BigDecimal.ZERO,
    @ApiModelProperty(value = "Utgift forpleining - beløp") var forpleiningUtgiftBelop: BigDecimal = BigDecimal.ZERO
//    @ApiModelProperty(value = "Liste over sjablonperioder") var sjablonListe: List<Sjablon>
) {

  constructor(resultatGrunnlag: ResultatGrunnlagCore) : this(
      soknadsbarnAlder = resultatGrunnlag.soknadBarnAlder,
      barnetilsynMedStonadTilsynType = resultatGrunnlag.barnetilsynMedStonadTilsynType,
      barnetilsynMedStonadStonadType = resultatGrunnlag.barnetilsynMedStonadStonadType,
      nettoBarnetilsynBelop = resultatGrunnlag.nettoBarnetilsynBelop,
      forpleiningUtgiftBelop = resultatGrunnlag.forpleiningUtgiftBelop
//      sjablonListe = resultatGrunnlag.sjablonListe.map { Sjablon(it) }
  )
}
