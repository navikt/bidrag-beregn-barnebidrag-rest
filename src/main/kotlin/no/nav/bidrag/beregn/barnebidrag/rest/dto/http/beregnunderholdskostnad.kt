package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import no.nav.bidrag.beregn.underholdskostnad.dto.BarnetilsynMedStonadPeriodeCore
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadResultatCore
import no.nav.bidrag.beregn.underholdskostnad.dto.ForpleiningUtgiftPeriodeCore
import no.nav.bidrag.beregn.underholdskostnad.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.underholdskostnad.dto.ResultatGrunnlagCore
import no.nav.bidrag.beregn.underholdskostnad.dto.ResultatPeriodeCore

// Grunnlag
@ApiModel(value = "Grunnlaget for en underholdskostnadberegning")
data class BeregnUnderholdskostnadGrunnlag(
    @ApiModelProperty(
        value = "Periodisert liste over bidragsmottakers barnetilsyn med stønad") val barnetilsynMedStonadPeriodeListe: List<BarnetilsynMedStonadPeriode>? = null,
    @ApiModelProperty(
        value = "Periodisert liste over bidragsmottakers utgifter til forpleining") val forpleiningUtgiftPeriodeListe: List<ForpleiningUtgiftPeriode>? = null
)

@ApiModel(value = "Bidragsmottakers barnetilsyn med stønad")
data class BarnetilsynMedStonadPeriode(
    @ApiModelProperty(value = "Bidragsmottakers barnetilsyn med stønad fra-til-dato") var barnetilsynMedStonadPeriodeDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Bidragsmottakers barnetilsyn med stønad tilsyn-type") var barnetilsynMedStonadTilsynType: String? = null,
    @ApiModelProperty(value = "Bidragsmottakers barnetilsyn med stønad stønad-type") var barnetilsynMedStonadStonadType: String? = null
) {

  fun tilCore() = BarnetilsynMedStonadPeriodeCore(
      barnetilsynMedStonadPeriodeDatoFraTil = if (barnetilsynMedStonadPeriodeDatoFraTil != null) barnetilsynMedStonadPeriodeDatoFraTil!!.tilCore()
      else throw UgyldigInputException("barnetilsynMedStonadPeriodeDatoFraTil kan ikke være null"),
      barnetilsynMedStonadTilsynType = if (barnetilsynMedStonadTilsynType != null) barnetilsynMedStonadTilsynType!!
      else throw UgyldigInputException("barnetilsynMedStonadTilsynType kan ikke være null"),
      barnetilsynStonadStonadType = if (barnetilsynMedStonadStonadType != null) barnetilsynMedStonadStonadType!!
      else throw UgyldigInputException("barnetilsynMedStonadStonadType kan ikke være null")
  )
}

@ApiModel(value = "Bidragsmottakers utgifter til forpleining")
data class ForpleiningUtgiftPeriode(
    @ApiModelProperty(value = "Bidragsmottakers utgifter til forpleining fra-til-dato") var forpleiningUtgiftPeriodeDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Bidragsmottakers utgifter til forpleining beløp") var forpleiningUtgiftBelop: Double? = null
) {

  fun tilCore() = ForpleiningUtgiftPeriodeCore(
      forpleiningUtgiftPeriodeDatoFraTil = if (forpleiningUtgiftPeriodeDatoFraTil != null) forpleiningUtgiftPeriodeDatoFraTil!!.tilCore()
      else throw UgyldigInputException("forpleiningUtgiftPeriodeDatoFraTil kan ikke være null"),
      forpleiningUtgiftBelop = if (forpleiningUtgiftBelop != null) forpleiningUtgiftBelop!!
      else throw UgyldigInputException("forpleiningUtgiftBelop kan ikke være null")
  )
}

// Resultat
@ApiModel(value = "Totalresultatet av beregning av underholdskostnad")
data class BeregnUnderholdskostnadResultat(
    @ApiModelProperty(value = "Periodisert liste over resultat av beregning av underholdskostnad")
    var resultatPeriodeListe: List<ResultatPeriodeUnderholdskostnad> = emptyList()
) {

  constructor(beregnUnderholdskostnadResultat: BeregnUnderholdskostnadResultatCore) : this(
      resultatPeriodeListe = beregnUnderholdskostnadResultat.resultatPeriodeListe.map {ResultatPeriodeUnderholdskostnad(it) }
  )
}

@ApiModel(value = "Resultatet av beregning av underholdskostnad for en gitt periode")
data class ResultatPeriodeUnderholdskostnad(
    @ApiModelProperty(value = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Beregning resultat innhold") var resultatBeregning: ResultatBeregningUnderholdskostnad? = null,
    @ApiModelProperty(value = "Beregning grunnlag innhold") var resultatGrunnlag: ResultatGrunnlagUnderholdskostnad? = null
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
      resultatDatoFraTil = Periode(resultatPeriode.resultatDatoFraTil),
      resultatBeregning = ResultatBeregningUnderholdskostnad(resultatPeriode.resultatBeregning),
      resultatGrunnlag = ResultatGrunnlagUnderholdskostnad(resultatPeriode.resultatGrunnlag)
  )
}

@ApiModel(value = "Resultatet av beregning av underholdskostnad")
data class ResultatBeregningUnderholdskostnad(
    @ApiModelProperty(value = "Beløp underholdskostnad") var resultatBelopUnderholdskostnad: Double = 0.0
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
      resultatBelopUnderholdskostnad = resultatBeregning.resultatBelopUnderholdskostnad
  )
}

@ApiModel(value = "Grunnlaget for beregning av underholdskostnad")
data class ResultatGrunnlagUnderholdskostnad(
    @ApiModelProperty(value = "Søknadsbarnets alder") var soknadBarnAlder: Int? = null,
    @ApiModelProperty(value = "Barnetilsyn med stønad - tilsyn-type") var barnetilsynMedStonadTilsynType: String? = null,
    @ApiModelProperty(value = "Barnetilsyn med stønad - stønad-type") var barnetilsynMedStonadStonadType: String? = null,
    @ApiModelProperty(value = "Faktisk utgift barnetilsyn - netto-beløp") var nettoBarnetilsynBelop: Double? = null,
    @ApiModelProperty(value = "Utgift forpleining - beløp") var forpleiningUtgiftBelop: Double? = null,
//    @ApiModelProperty(value = "Liste over sjablonperioder") var sjablonListe: List<Sjablon> = emptyList()
) {

  constructor(resultatGrunnlag: ResultatGrunnlagCore) : this(
      soknadBarnAlder = resultatGrunnlag.soknadBarnAlder,
      barnetilsynMedStonadTilsynType = resultatGrunnlag.barnetilsynMedStonadTilsynType,
      barnetilsynMedStonadStonadType = resultatGrunnlag.barnetilsynMedStonadStonadType,
      nettoBarnetilsynBelop = resultatGrunnlag.nettoBarnetilsynBelop,
      forpleiningUtgiftBelop = resultatGrunnlag.forpleiningUtgiftBelop,
//      sjablonListe = resultatGrunnlag.sjablonListe.map { Sjablon(it) }
  )
}
