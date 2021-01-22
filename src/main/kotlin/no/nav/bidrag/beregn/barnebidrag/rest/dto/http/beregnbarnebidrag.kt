package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import no.nav.bidrag.beregn.barnebidrag.dto.AndreLopendeBidragCore
import no.nav.bidrag.beregn.barnebidrag.dto.AndreLopendeBidragPeriodeCore
import no.nav.bidrag.beregn.barnebidrag.dto.BPsAndelUnderholdskostnadCore
import no.nav.bidrag.beregn.barnebidrag.dto.BarnetilleggCore
import no.nav.bidrag.beregn.barnebidrag.dto.BarnetilleggForsvaretPeriodeCore
import no.nav.bidrag.beregn.barnebidrag.dto.BarnetilleggPeriodeCore
import no.nav.bidrag.beregn.barnebidrag.dto.BeregnBarnebidragResultatCore
import no.nav.bidrag.beregn.barnebidrag.dto.BidragsevneCore
import no.nav.bidrag.beregn.barnebidrag.dto.DeltBostedPeriodeCore
import no.nav.bidrag.beregn.barnebidrag.dto.GrunnlagBeregningPerBarnCore
import no.nav.bidrag.beregn.barnebidrag.dto.GrunnlagBeregningPeriodisertCore
import no.nav.bidrag.beregn.barnebidrag.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.barnebidrag.dto.ResultatPeriodeCore
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import java.math.BigDecimal

// Grunnlag
@ApiModel(value = "Grunnlaget for en beregning av barnebidrag")
data class BeregnBarnebidragGrunnlag(
    @ApiModelProperty(value = "Periodisert liste over bidragspliktiges barnetillegg")
    val barnetilleggBPPeriodeListe: List<BarnetilleggPeriode>? = null,
    @ApiModelProperty(value = "Periodisert liste over bidragsmottakers barnetillegg")
    val barnetilleggBMPeriodeListe: List<BarnetilleggPeriode>? = null,
    @ApiModelProperty(value = "Periodisert liste over bidragspliktiges barnetillegg fra forsvaret")
    val barnetilleggForsvaretBPPeriodeListe: List<BarnetilleggForsvaretBPPeriode>? = null,
    @ApiModelProperty(value = "Periodisert liste over delt bosted for bidragspliktig")
    val deltBostedBPPeriodeListe: List<DeltBostedBPPeriode>? = null,
    @ApiModelProperty(value = "Periodisert liste over andre løpende bidrag for bidragspliktig")
    val andreLopendeBidragBPPeriodeListe: List<AndreLopendeBidragBPPeriode>? = null
)

@ApiModel(value = "Barnetillegg BM og BP")
data class BarnetilleggPeriode(
    @ApiModelProperty(value = "Barnetillegg fra-til-dato") var barnetilleggDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Søknadsbarnets person-id") var barnetilleggSoknadsbarnPersonId: Int? = null,
    @ApiModelProperty(value = "Barnetillegg bruttobeløp") var barnetilleggBruttoBelop: BigDecimal? = null,
    @ApiModelProperty(value = "Barnetillegg skatt prosent") var barnetilleggSkattProsent: BigDecimal? = null
) {

  fun tilCore(dataElement: String) = BarnetilleggPeriodeCore(
      soknadsbarnPersonId = if (barnetilleggSoknadsbarnPersonId != null) barnetilleggSoknadsbarnPersonId!! else throw UgyldigInputException(
          "$dataElement barnetilleggSoknadsbarnPersonId kan ikke være null"),
      barnetilleggDatoFraTil = if (barnetilleggDatoFraTil != null) barnetilleggDatoFraTil!!.tilCore(
          "$dataElement barnetillegg") else throw UgyldigInputException("$dataElement barnetilleggDatoFraTil kan ikke være null"),
      barnetilleggBelop = if (barnetilleggBruttoBelop != null) barnetilleggBruttoBelop!! else throw UgyldigInputException(
          "$dataElement barnetilleggBruttoBelop kan ikke være null"),
      barnetilleggSkattProsent = if (barnetilleggSkattProsent != null) barnetilleggSkattProsent!! else throw UgyldigInputException(
          "$dataElement barnetilleggSkattProsent kan ikke være null")
  )
}

@ApiModel(value = "Bidragspliktiges barnetillegg fra forsvaret")
data class BarnetilleggForsvaretBPPeriode(
    @ApiModelProperty(value = "Barnetillegg forsvaret fra-til-dato") var barnetilleggForsvaretDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Barnetillegg forsvaret i periode true/false") var barnetilleggForsvaretIPeriode: Boolean? = null
) {

  fun tilCore() = BarnetilleggForsvaretPeriodeCore(
      barnetilleggForsvaretDatoFraTil = if (barnetilleggForsvaretDatoFraTil != null) barnetilleggForsvaretDatoFraTil!!.tilCore(
          "barnetilleggForsvaret") else throw UgyldigInputException("barnetilleggForsvaretDatoFraTil kan ikke være null"),
      barnetilleggForsvaretIPeriode = if (barnetilleggForsvaretIPeriode != null) barnetilleggForsvaretIPeriode!! else throw UgyldigInputException(
          "barnetilleggForsvaretIPeriode kan ikke være null")
  )
}

@ApiModel(value = "Delt bosted for bidragspliktig")
data class DeltBostedBPPeriode(
    @ApiModelProperty(value = "Delt bosted fra-til-dato") var deltBostedDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Søknadsbarnets person-id") var deltBostedSoknadsbarnPersonId: Int? = null,
    @ApiModelProperty(value = "Delt bosted i periode true/false") var deltBostedIPeriode: Boolean? = null
) {

  fun tilCore() = DeltBostedPeriodeCore(
      soknadsbarnPersonId = if (deltBostedSoknadsbarnPersonId != null) deltBostedSoknadsbarnPersonId!! else throw UgyldigInputException(
          "deltBostedSoknadsbarnPersonId kan ikke være null"),
      deltBostedDatoFraTil = if (deltBostedDatoFraTil != null) deltBostedDatoFraTil!!.tilCore(
          "deltBosted") else throw UgyldigInputException("deltBostedDatoFraTil kan ikke være null"),
      deltBostedIPeriode = if (deltBostedIPeriode != null) deltBostedIPeriode!! else throw UgyldigInputException(
          "deltBostedIPeriode kan ikke være null")
  )
}

@ApiModel(value = "Andre løpende bidrag for bidragspliktig")
data class AndreLopendeBidragBPPeriode(
    @ApiModelProperty(value = "Andre løpende bidrag - fra-til-dato") var andreLopendeBidragDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Andre løpende bidrag - barn person-id") var andreLopendeBidragBarnPersonId: Int? = null,
    @ApiModelProperty(value = "Andre løpende bidrag - bidrag beløp") var andreLopendeBidragBidragBelop: BigDecimal? = null,
    @ApiModelProperty(value = "Andre løpende bidrag - samværsfradrag beløp") var andreLopendeBidragSamvaersfradragBelop: BigDecimal? = null
) {

  fun tilCore() = AndreLopendeBidragPeriodeCore(
      periodeDatoFraTil = if (andreLopendeBidragDatoFraTil != null) andreLopendeBidragDatoFraTil!!.tilCore("andreLopendeBidrag")
      else throw UgyldigInputException("andreLopendeBidragDatoFraTil kan ikke være null"),
      barnPersonId = if (andreLopendeBidragBarnPersonId != null) andreLopendeBidragBarnPersonId!! else throw UgyldigInputException(
          "andreLopendeBidragBarnPersonId kan ikke være null"),
      bidragBelop = if (andreLopendeBidragBidragBelop != null) andreLopendeBidragBidragBelop!! else throw UgyldigInputException(
          "andreLopendeBidragBidragBelop kan ikke være null"),
      samvaersfradragBelop = if (andreLopendeBidragSamvaersfradragBelop != null) andreLopendeBidragSamvaersfradragBelop!!
      else throw UgyldigInputException("andreLopendeBidragSamvaersfradragBelop kan ikke være null")
  )
}

// Resultat
@ApiModel(value = "Resultatet av en beregning av barnebidrag")
data class BeregnBarnebidragResultat(
    @ApiModelProperty(
        value = "Periodisert liste over resultat av beregning av barnebidrag") var resultatPeriodeListe: List<ResultatPeriodeBarnebidrag> = emptyList()
) {

  constructor(beregnBarnebidragResultat: BeregnBarnebidragResultatCore) : this(
      resultatPeriodeListe = beregnBarnebidragResultat.resultatPeriodeListe.map { ResultatPeriodeBarnebidrag(it) }
  )
}

@ApiModel(value = "Resultatet av beregning av barnebidrag for et søknadsbarn for en gitt periode")
data class ResultatPeriodeBarnebidrag(
    @ApiModelProperty(value = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode = Periode(),
    @ApiModelProperty(value = "Beregning resultat innhold liste") var resultatBeregningListe: List<ResultatBeregningBarnebidrag> = emptyList(),
    @ApiModelProperty(value = "Beregning grunnlag innhold") var resultatGrunnlag: ResultatGrunnlagBarnebidrag = ResultatGrunnlagBarnebidrag()
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
      resultatDatoFraTil = Periode(resultatPeriode.resultatDatoFraTil),
      resultatBeregningListe = resultatPeriode.resultatBeregningListe.map { ResultatBeregningBarnebidrag(it) },
      resultatGrunnlag = ResultatGrunnlagBarnebidrag(resultatPeriode.resultatGrunnlag)
  )
}

@ApiModel(value = "Resultatet av beregning av barnebidrag")
data class ResultatBeregningBarnebidrag(
    @ApiModelProperty(value = "Søknadsbarnets person-id") var resultatSoknadsbarnPersonId: Int = 0,
    @ApiModelProperty(value = "Beløp barnebidrag") var resultatBelop: BigDecimal = BigDecimal.ZERO,
    @ApiModelProperty(value = "Resultatkode barnebidrag") var resultatKode: String = ""
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
      resultatSoknadsbarnPersonId = resultatBeregning.soknadsbarnPersonId,
      resultatBelop = resultatBeregning.resultatBarnebidragBelop,
      resultatKode = resultatBeregning.resultatkode
  )
}

@ApiModel(value = "Grunnlaget for beregning av barnebidrag")
data class ResultatGrunnlagBarnebidrag(
    @ApiModelProperty(value = "Bidragsevne") var bidragsevne: BidragsevneGrunnlag = BidragsevneGrunnlag(),
    @ApiModelProperty(value = "Grunnlag per barn liste") var resultatGrunnlagPerBarnListe: List<ResultatGrunnlagPerBarn> = emptyList(),
    @ApiModelProperty(value = "Barnetillegg forsvaret") var barnetilleggForsvaret: Boolean = false,
    @ApiModelProperty(value = "Liste over sjablonperioder") var sjablonListe: List<Sjablon> = emptyList()
) {

  constructor(resultatGrunnlag: GrunnlagBeregningPeriodisertCore) : this(
      bidragsevne = BidragsevneGrunnlag(resultatGrunnlag.bidragsevne),
      resultatGrunnlagPerBarnListe = resultatGrunnlag.grunnlagPerBarnListe.map { ResultatGrunnlagPerBarn(it) },
      barnetilleggForsvaret = resultatGrunnlag.barnetilleggForsvaret,
      sjablonListe = resultatGrunnlag.sjablonListe.map { Sjablon(it) }
  )
}

@ApiModel(value = "Grunnlaget for beregning - bidragsevne")
data class BidragsevneGrunnlag(
    @ApiModelProperty(value = "Bidragsevne beløp") var bidragsevneBelop: BigDecimal = BigDecimal.ZERO,
    @ApiModelProperty(value = "Bidragsevne 25 prosent inntekt") var bidragsevne25ProsentInntekt: BigDecimal = BigDecimal.ZERO
) {

  constructor(bidragsevne: BidragsevneCore) : this(
      bidragsevneBelop = bidragsevne.bidragsevneBelop,
      bidragsevne25ProsentInntekt = bidragsevne.tjuefemProsentInntekt
  )
}

@ApiModel(value = "Grunnlaget for beregning av barnebidrag per barn")
data class ResultatGrunnlagPerBarn(
    @ApiModelProperty(value = "Søknadsbarnets person-id") var resultatSoknadsbarnPersonId: Int = 0,
    @ApiModelProperty(value = "Samværsfradrag beløp") var samvaersfradragBelop: BigDecimal = BigDecimal.ZERO,
    @ApiModelProperty(value = "BPs andel underholdskostnad") var bpAndelUnderholdskostnad: BPAndelUnderholdskostnad = BPAndelUnderholdskostnad(),
    @ApiModelProperty(value = "Barnetillegg bidragspliktig") var barnetilleggBP: Barnetillegg = Barnetillegg(),
    @ApiModelProperty(value = "Barnetillegg bidragsmottaker") var barnetilleggBM: Barnetillegg = Barnetillegg(),
    @ApiModelProperty(value = "Delt bosted") var deltBosted: Boolean = false,
    @ApiModelProperty(value = "Andre løpende bidrag") var andreLopendeBidrag: AndreLopendeBidrag = AndreLopendeBidrag()
) {

  constructor(resultatGrunnlagPerBarn: GrunnlagBeregningPerBarnCore) : this(
      resultatSoknadsbarnPersonId = resultatGrunnlagPerBarn.soknadsbarnPersonId,
      samvaersfradragBelop = resultatGrunnlagPerBarn.samvaersfradrag,
      bpAndelUnderholdskostnad = BPAndelUnderholdskostnad(resultatGrunnlagPerBarn.bPsAndelUnderholdskostnad),
      barnetilleggBP = Barnetillegg(resultatGrunnlagPerBarn.barnetilleggBP),
      barnetilleggBM = Barnetillegg(resultatGrunnlagPerBarn.barnetilleggBM),
      deltBosted = resultatGrunnlagPerBarn.deltBosted
//      andreLopendeBidrag = AndreLopendeBidrag(resultatGrunnlagPerBarn.andreLopendeBidrag)
  )
}

@ApiModel(value = "Grunnlaget for beregning - BPs andel underholdskostnad")
data class BPAndelUnderholdskostnad(
    @ApiModelProperty(value = "Resultatandel prosent") var resultatAndelProsent: BigDecimal = BigDecimal.ZERO,
    @ApiModelProperty(value = "Resultatandel beløp") var resultatAndelBelop: BigDecimal = BigDecimal.ZERO,
    @ApiModelProperty(value = "Barnet er selvforsørget") var barnetErSelvforsorget: Boolean = false
) {

  constructor(bpAndelUnderholdskostnad: BPsAndelUnderholdskostnadCore) : this(
      resultatAndelProsent = bpAndelUnderholdskostnad.bPsAndelUnderholdskostnadProsent,
      resultatAndelBelop = bpAndelUnderholdskostnad.bPsAndelUnderholdskostnadBelop,
      barnetErSelvforsorget = bpAndelUnderholdskostnad.barnetErSelvforsorget
  )
}

@ApiModel(value = "Grunnlaget for beregning - barnetillegg")
data class Barnetillegg(
    @ApiModelProperty(value = "Barnetillegg beløp") var barnetilleggBelop: BigDecimal = BigDecimal.ZERO,
    @ApiModelProperty(value = "Barnetillegg skatt prosent") var barnetilleggSkattProsent: BigDecimal = BigDecimal.ZERO
) {

  constructor(barnetillegg: BarnetilleggCore) : this(
      barnetilleggBelop = barnetillegg.barnetilleggBelop,
      barnetilleggSkattProsent = barnetillegg.barnetilleggSkattProsent
  )
}

@ApiModel(value = "Grunnlaget for beregning - andre løpende bidrag")
data class AndreLopendeBidrag(
  @ApiModelProperty(value = "Andre løpende bidrag - barn person-id") var andreLopendeBidragBarnPersonId: Int = 0,
  @ApiModelProperty(value = "Andre løpende bidrag - bidrag beløp") var andreLopendeBidragBidragBelop: BigDecimal = BigDecimal.ZERO,
  @ApiModelProperty(value = "Andre løpende bidrag - samværsfradrag beløp") var andreLopendeBidragSamvaersfradragBelop: BigDecimal = BigDecimal.ZERO
) {

  constructor(andreLopendBidrag: AndreLopendeBidragCore) : this(
      andreLopendeBidragBarnPersonId = andreLopendBidrag.barnPersonId,
      andreLopendeBidragBidragBelop = andreLopendBidrag.bidragBelop,
      andreLopendeBidragSamvaersfradragBelop = andreLopendBidrag.samvaersfradragBelop
  )
}
