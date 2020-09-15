package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException

// Grunnlag
@ApiModel(value = "Grunnlaget for en beregning av barnebidrag")
data class BeregnBarnebidragGrunnlag(
    @ApiModelProperty(value = "Periodisert liste over bidragspliktiges barnetillegg")
    val barnetilleggBPPeriodeListe: List<BarnetilleggPeriode>? = null,
    @ApiModelProperty(value = "Periodisert liste over bidragsmottakers barnetillegg")
    val barnetilleggBMPeriodeListe: List<BarnetilleggPeriode>? = null,
    @ApiModelProperty(value = "Periodisert liste over bidragspliktiges barnetillegg fra forsvaret")
    val barnetilleggForsvaretPeriodeListe: List<BarnetilleggForsvaretPeriode>? = null,
    @ApiModelProperty(value = "Periodisert liste over delt bosted for bidragspliktig")
    val deltBostedBPPeriodeListe: List<DeltBostedBPPeriode>? = null
)

@ApiModel(value = "Barnetillegg")
data class BarnetilleggPeriode(
    @ApiModelProperty(value = "Barnetillegg fra-til-dato") var barnetilleggPeriodeDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Søknadsbarnets person-id") var barnetilleggSoknadsbarnPersonId: Int? = null,
    @ApiModelProperty(value = "Barnetillegg beløp") var barnetilleggBelop: Double? = null,
    @ApiModelProperty(value = "Barnetillegg skatt prosent") var barnetilleggSkattProsent: Double? = null
) {

  fun validerBarnetillegg(dataElement: String) {
    if (barnetilleggPeriodeDatoFraTil != null) barnetilleggPeriodeDatoFraTil!!.valider("barnetillegg")
    else throw UgyldigInputException(dataElement + "barnetilleggPeriodeDatoFraTil kan ikke være null")

    if (barnetilleggSoknadsbarnPersonId == null) throw UgyldigInputException(dataElement + "barnetilleggSoknadsbarnPersonId kan ikke være null")
    if (barnetilleggBelop == null) throw UgyldigInputException(dataElement + "barnetilleggBelop kan ikke være null")
    if (barnetilleggSkattProsent == null) throw UgyldigInputException(dataElement + "barnetilleggSkattProsent kan ikke være null")
  }
}

@ApiModel(value = "Bidragspliktiges barnetillegg fra forsvaret")
data class BarnetilleggForsvaretPeriode(
    @ApiModelProperty(value = "Bidragspliktiges barnetillegg forsvaret fra-til-dato") var barnetilleggForsvaretDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Bidragspliktiges barnetillegg forsvaret antall barn") var barnetilleggForsvaretAntallBarn: Int? = null
) {

  fun tilCore() = BarnetilleggForsvaretPeriodeCore(
      barnetilleggForsvaretDatoFraTil = if (barnetilleggForsvaretDatoFraTil != null) barnetilleggForsvaretDatoFraTil!!.tilCore(
          "barnetilleggForsvaret") else throw UgyldigInputException("barnetilleggForsvaretPeriodeDatoFraTil kan ikke være null"),
      barnetilleggForsvaretAntallBarn = if (barnetilleggForsvaretAntallBarn != null) barnetilleggForsvaretAntallBarn!! else throw UgyldigInputException(
          "barnetilleggForsvaretAntallBarn kan ikke være null"),
      true
  )
}

@ApiModel(value = "Delt bosted for bidragspliktig")
data class DeltBostedBPPeriode(
    @ApiModelProperty(value = "Delt bosted bidragspliktig fra-til-dato") var deltBostedPeriodeDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Søknadsbarnets person-id") var deltBostedSoknadsbarnPersonId: Int? = null
) {

  fun validerDeltBosted() {
    if (deltBostedPeriodeDatoFraTil != null) deltBostedPeriodeDatoFraTil!!.valider("deltBosted")
    else throw UgyldigInputException("deltBostedPeriodeDatoFraTil kan ikke være null")

    if (deltBostedSoknadsbarnPersonId == null) throw UgyldigInputException("deltBostedSoknadsbarnPersonId kan ikke være null")
  }
}

// Resultat
@ApiModel(value = "Resultatet av beregning av barnebidrag")
data class BeregnBarnebidragResultat(
    @ApiModelProperty(value = "Periodisert liste over resultat av beregning av barnebidrag")
    var resultatPeriodeListe: List<ResultatPeriodeBarnebidrag> = emptyList()
) {

  constructor(beregnBarnebidragResultat: BeregnBarnebidragResultatCore) : this(
      resultatPeriodeListe = beregnBarnebidragResultat.resultatPeriodeListe.map { ResultatPeriodeBarnebidrag(it) }
  )
}

@ApiModel(value = "Resultatet av beregning av barnebidrag for en gitt periode")
data class ResultatPeriodeBarnebidrag(
    @ApiModelProperty(value = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Beregning resultat innhold") var resultatBeregning: ResultatBeregningBarnebidrag? = null,
    @ApiModelProperty(value = "Beregning grunnlag innhold") var resultatGrunnlag: ResultatGrunnlagBarnebidrag? = null
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
      resultatDatoFraTil = Periode(resultatPeriode.resultatDatoFraTil),
      resultatBeregning = ResultatBeregningBarnebidrag(resultatPeriode.resultatBeregning),
      resultatGrunnlag = ResultatGrunnlagBarnebidrag(resultatPeriode.resultatGrunnlag)
  )
}

@ApiModel(value = "Resultatet av beregning av barnebidrag")
data class ResultatBeregningBarnebidrag(
    @ApiModelProperty(value = "Beløp samværsfradrag") var resultatBarnebidragBelop: Double = 0.0
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
      resultatBarnebidragBelop = resultatBeregning.resultatBarnebidragBelop
  )
}

@ApiModel(value = "Grunnlaget for beregning av barnebidrag")
data class ResultatGrunnlagBarnebidrag(
    @ApiModelProperty(value = "Bidragsevne beløp") var bidragsevneBelop: Double? = null,
    @ApiModelProperty(value = "Kostnadsberegnte bidrag beløp") var kostnadsberegnetBidragBelop: Double? = null,
    @ApiModelProperty(value = "Samværsfradrag") var samvaersfradrag: Double? = null,
    @ApiModelProperty(value = "Barnetillegg bidragspliktig") var barnetilleggBP: BarnetilleggBP? = null,
    @ApiModelProperty(value = "Barnetillegg bidragsmottaker") var barnetilleggBM: BarnetilleggBM? = null,
    @ApiModelProperty(value = "Barnetillegg forsvaret") var barnetilleggForsvaret: BarnetilleggForsvaret? = null
//    @ApiModelProperty(value = "Liste over sjablonperioder") var sjablonListe: List<Sjablon> = emptyList()
) {

  constructor(resultatGrunnlag: ResultatGrunnlagCore) : this(
      bidragsevneBelop = resultatGrunnlag.bidragsevneBelop,
      kostnadsberegnetBidragBelop = resultatGrunnlag.kostnadsberegnetBidragBelop,
      samvaersfradrag = resultatGrunnlag.samvaersfradrag,
      barnetilleggBP = BarnetilleggBP(resultatGrunnlag.barnetilleggBP),
      barnetilleggBM = BarnetilleggBM(resultatGrunnlag.barnetilleggBM),
      barnetilleggForsvaret = BarnetilleggForsvaret(resultatGrunnlag.barnetilleggForsvaret)
//      sjablonListe = resultatGrunnlag.sjablonListe.map { Sjablon(it) }
  )
}

@ApiModel(value = "Grunnlaget for beregning - barnetillegg bidragspliktig")
data class BarnetilleggBP(
    @ApiModelProperty(value = "Barnetillegg bidragspliktig beløp") var barnetilleggBPBelop: Double? = null,
    @ApiModelProperty(value = "Barnetillegg bidragspliktig skatt prosent") var barnetilleggBPSkattProsent: Double? = null
) {

  constructor(barnetilleggBP: BarnetilleggBPCore) : this(
      barnetilleggBPBelop = barnetilleggBP.barnetilleggBPBelop,
      barnetilleggBPSkattProsent = barnetilleggBP.barnetilleggBPSkattProsent
  )
}

@ApiModel(value = "Grunnlaget for beregning - barnetillegg bidragsmottaker")
data class BarnetilleggBM(
    @ApiModelProperty(value = "Barnetillegg bidragsmottaker beløp") var barnetilleggBMBelop: Double? = null,
    @ApiModelProperty(value = "Barnetillegg bidragsmottaker skatt prosent") var barnetilleggBMSkattProsent: Double? = null
) {

  constructor(barnetilleggBM: BarnetilleggBMCore) : this(
      barnetilleggBMBelop = barnetilleggBM.barnetilleggBMBelop,
      barnetilleggBMSkattProsent = barnetilleggBM.barnetilleggBMSkattProsent
  )
}

@ApiModel(value = "Grunnlaget for beregning - barnetillegg forsvaret")
data class BarnetilleggForsvaret(
    @ApiModelProperty(value = "Bidragspliktiges barnetillegg forsvaret antall barn") var antallBarn: Int? = null,
    @ApiModelProperty(value = "Bidragspliktiges barnetillegg forsvaret periode-indikator") var barnetilleggForsvaretJaNei: Boolean? = null
) {

  constructor(barnetilleggForsvaret: BarnetilleggForsvaretCore) : this(
      antallBarn = barnetilleggForsvaret.antallBarn,
      barnetilleggForsvaretJaNei = barnetilleggForsvaret.barnetilleggForsvaretJaNei
  )
}
