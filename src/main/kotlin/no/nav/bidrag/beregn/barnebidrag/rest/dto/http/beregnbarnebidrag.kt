package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException

// Grunnlag
@ApiModel(value = "Grunnlaget for en beregning av barnebidrag")
data class BeregnBarnebidragGrunnlag(
    @ApiModelProperty(value = "Periodisert liste over bidragspliktiges barnetillegg")
    val barnetilleggBPPeriodeListe: List<BarnetilleggBPPeriode>? = null,
    @ApiModelProperty(value = "Periodisert liste over bidragsmottakers barnetillegg")
    val barnetilleggBMPeriodeListe: List<BarnetilleggBMPeriode>? = null,
    @ApiModelProperty(value = "Periodisert liste over bidragspliktiges barnetillegg fra forsvaret")
    val barnetilleggForsvaretPeriodeListe: List<BarnetilleggForsvaretPeriode>? = null
)

@ApiModel(value = "Bidragspliktiges barnetillegg")
data class BarnetilleggBPPeriode(
    @ApiModelProperty(value = "Bidragspliktiges barnetillegg fra-til-dato") var barnetilleggBPPeriodeDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Bidragspliktiges barnetillegg beløp") var barnetilleggBPBelop: Double? = null,
    @ApiModelProperty(value = "Bidragspliktiges barnetillegg skatt prosent") var barnetilleggBPSkattProsent: Double? = null
) {

  fun tilCore() = BarnetilleggBPPeriodeCore(
      barnetilleggBPDatoFraTil = if (barnetilleggBPPeriodeDatoFraTil != null) barnetilleggBPPeriodeDatoFraTil!!.tilCore()
      else throw UgyldigInputException("barnetilleggBPPeriodeDatoFraTil kan ikke være null"),
      barnetilleggBPBelop = if (barnetilleggBPBelop != null) barnetilleggBPBelop!!
      else throw UgyldigInputException("barnetilleggBPBelop kan ikke være null"),
      barnetilleggBPSkattProsent = if (barnetilleggBPSkattProsent != null) barnetilleggBPSkattProsent!!
      else throw UgyldigInputException("barnetilleggBPSkattProsent kan ikke være null")
  )
}

@ApiModel(value = "Bidragsmottakers barnetillegg")
data class BarnetilleggBMPeriode(
    @ApiModelProperty(value = "Bidragspliktiges barnetillegg fra-til-dato") var barnetilleggBMPeriodeDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Bidragspliktiges barnetillegg beløp") var barnetilleggBMBelop: Double? = null,
    @ApiModelProperty(value = "Bidragspliktiges barnetillegg skatt prosent") var barnetilleggBMSkattProsent: Double? = null
) {

  fun tilCore() = BarnetilleggBMPeriodeCore(
      barnetilleggBMDatoFraTil = if (barnetilleggBMPeriodeDatoFraTil != null) barnetilleggBMPeriodeDatoFraTil!!.tilCore()
      else throw UgyldigInputException("barnetilleggBMPeriodeDatoFraTil kan ikke være null"),
      barnetilleggBMBelop = if (barnetilleggBMBelop != null) barnetilleggBMBelop!!
      else throw UgyldigInputException("barnetilleggBMBelop kan ikke være null"),
      barnetilleggBMSkattProsent = if (barnetilleggBMSkattProsent != null) barnetilleggBMSkattProsent!!
      else throw UgyldigInputException("barnetilleggBMSkattProsent kan ikke være null")
  )
}

@ApiModel(value = "Bidragspliktiges barnetillegg fra forsvaret")
data class BarnetilleggForsvaretPeriode(
    @ApiModelProperty(value = "Bidragspliktiges barnetillegg forsvaret fra-til-dato") var barnetilleggForsvaretDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Bidragspliktiges barnetillegg forsvaret antall barn") var barnetilleggForsvaretAntallBarn: Int? = null,
    @ApiModelProperty(value = "Bidragspliktiges barnetillegg forsvaret periode-indikator") var barnetilleggForsvaretIPeriode: Boolean? = null
) {

  fun tilCore() = BarnetilleggForsvaretPeriodeCore(
      barnetilleggForsvaretDatoFraTil = if (barnetilleggForsvaretDatoFraTil != null) barnetilleggForsvaretDatoFraTil!!.tilCore()
      else throw UgyldigInputException("barnetilleggForsvaretPeriodeDatoFraTil kan ikke være null"),
      barnetilleggForsvaretAntallBarn = if (barnetilleggForsvaretAntallBarn != null) barnetilleggForsvaretAntallBarn!!
      else throw UgyldigInputException("barnetilleggForsvaretAntallBarn kan ikke være null"),
      barnetilleggForsvaretIPeriode = if (barnetilleggForsvaretIPeriode != null) barnetilleggForsvaretIPeriode!!
      else throw UgyldigInputException("barnetilleggForsvaretIPeriode kan ikke være null")
  )
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
