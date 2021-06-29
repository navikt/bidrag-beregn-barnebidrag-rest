package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.v3.oas.annotations.media.Schema
import no.nav.bidrag.beregn.barnebidrag.dto.AndreLopendeBidragPeriodeCore
import no.nav.bidrag.beregn.barnebidrag.dto.BarnetilleggForsvaretPeriodeCore
import no.nav.bidrag.beregn.barnebidrag.dto.BarnetilleggPeriodeCore
import no.nav.bidrag.beregn.barnebidrag.dto.BeregnetBarnebidragResultatCore
import no.nav.bidrag.beregn.barnebidrag.dto.DeltBostedPeriodeCore
import no.nav.bidrag.beregn.barnebidrag.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.barnebidrag.dto.ResultatPeriodeCore
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import java.math.BigDecimal

// Grunnlag
@Schema(description = "Grunnlaget for en beregning av barnebidrag")
data class BeregnBarnebidragGrunnlag(
  @Schema(description = "Periodisert liste over bidragspliktiges barnetillegg") val barnetilleggBPPeriodeListe: List<BarnetilleggPeriode>? = null,
  @Schema(description = "Periodisert liste over bidragsmottakers barnetillegg") val barnetilleggBMPeriodeListe: List<BarnetilleggPeriode>? = null,
  @Schema(description = "Periodisert liste over bidragspliktiges barnetillegg fra forsvaret") val barnetilleggForsvaretBPPeriodeListe: List<BarnetilleggForsvaretBPPeriode>? = null,
  @Schema(description = "Periodisert liste over delt bosted for bidragspliktig") val deltBostedBPPeriodeListe: List<DeltBostedBPPeriode>? = null,
  @Schema(description = "Periodisert liste over andre løpende bidrag for bidragspliktig") val andreLopendeBidragBPPeriodeListe: List<AndreLopendeBidragBPPeriode>? = null
)

@Schema(description = "Barnetillegg BM og BP")
data class BarnetilleggPeriode(
  @Schema(description = "Barnetillegg fra-til-dato") var barnetilleggDatoFraTil: Periode? = null,
  @Schema(description = "Søknadsbarnets person-id") var barnetilleggSoknadsbarnPersonId: Int? = null,
  @Schema(description = "Barnetillegg bruttobeløp") var barnetilleggBruttoBelop: BigDecimal? = null,
  @Schema(description = "Barnetillegg skatt prosent") var barnetilleggSkattProsent: BigDecimal? = null
) {

  fun tilCore(dataElement: String) = BarnetilleggPeriodeCore(
    referanse = "",
    soknadsbarnPersonId = if (barnetilleggSoknadsbarnPersonId != null) barnetilleggSoknadsbarnPersonId!! else throw UgyldigInputException(
      "$dataElement barnetilleggSoknadsbarnPersonId kan ikke være null"
    ),
    periode = if (barnetilleggDatoFraTil != null) barnetilleggDatoFraTil!!.tilCore(
      "$dataElement barnetillegg"
    ) else throw UgyldigInputException("$dataElement barnetilleggDatoFraTil kan ikke være null"),
    belop = if (barnetilleggBruttoBelop != null) barnetilleggBruttoBelop!! else throw UgyldigInputException(
      "$dataElement barnetilleggBruttoBelop kan ikke være null"
    ),
    skattProsent = if (barnetilleggSkattProsent != null) barnetilleggSkattProsent!! else throw UgyldigInputException(
      "$dataElement barnetilleggSkattProsent kan ikke være null"
    )
  )
}

@Schema(description = "Bidragspliktiges barnetillegg fra forsvaret")
data class BarnetilleggForsvaretBPPeriode(
  @Schema(description = "Barnetillegg forsvaret fra-til-dato") var barnetilleggForsvaretDatoFraTil: Periode? = null,
  @Schema(description = "Barnetillegg forsvaret i periode true/false") var barnetilleggForsvaretIPeriode: Boolean? = null
) {

  fun tilCore() = BarnetilleggForsvaretPeriodeCore(
    referanse = "",
    periode = if (barnetilleggForsvaretDatoFraTil != null) barnetilleggForsvaretDatoFraTil!!.tilCore(
      "barnetilleggForsvaret"
    ) else throw UgyldigInputException("barnetilleggForsvaretDatoFraTil kan ikke være null"),
    barnetilleggForsvaretIPeriode = if (barnetilleggForsvaretIPeriode != null) barnetilleggForsvaretIPeriode!! else throw UgyldigInputException(
      "barnetilleggForsvaretIPeriode kan ikke være null"
    )
  )
}

@Schema(description = "Delt bosted for bidragspliktig")
data class DeltBostedBPPeriode(
  @Schema(description = "Delt bosted fra-til-dato") var deltBostedDatoFraTil: Periode? = null,
  @Schema(description = "Søknadsbarnets person-id") var deltBostedSoknadsbarnPersonId: Int? = null,
  @Schema(description = "Delt bosted i periode true/false") var deltBostedIPeriode: Boolean? = null
) {

  fun tilCore() = DeltBostedPeriodeCore(
    referanse = "",
    soknadsbarnPersonId = if (deltBostedSoknadsbarnPersonId != null) deltBostedSoknadsbarnPersonId!! else throw UgyldigInputException(
      "deltBostedSoknadsbarnPersonId kan ikke være null"
    ),
    periode = if (deltBostedDatoFraTil != null) deltBostedDatoFraTil!!.tilCore(
      "deltBosted"
    ) else throw UgyldigInputException("deltBostedDatoFraTil kan ikke være null"),
    deltBostedIPeriode = if (deltBostedIPeriode != null) deltBostedIPeriode!! else throw UgyldigInputException(
      "deltBostedIPeriode kan ikke være null"
    )
  )
}

@Schema(description = "Andre løpende bidrag for bidragspliktig")
data class AndreLopendeBidragBPPeriode(
  @Schema(description = "Andre løpende bidrag - fra-til-dato") var andreLopendeBidragDatoFraTil: Periode? = null,
  @Schema(description = "Andre løpende bidrag - barn person-id") var andreLopendeBidragBarnPersonId: Int? = null,
  @Schema(description = "Andre løpende bidrag - bidrag beløp") var andreLopendeBidragBidragBelop: BigDecimal? = null,
  @Schema(description = "Andre løpende bidrag - samværsfradrag beløp") var andreLopendeBidragSamvaersfradragBelop: BigDecimal? = null
) {

  fun tilCore() = AndreLopendeBidragPeriodeCore(
    referanse = "",
    periode = if (andreLopendeBidragDatoFraTil != null) andreLopendeBidragDatoFraTil!!.tilCore("andreLopendeBidrag")
    else throw UgyldigInputException("andreLopendeBidragDatoFraTil kan ikke være null"),
    barnPersonId = if (andreLopendeBidragBarnPersonId != null) andreLopendeBidragBarnPersonId!! else throw UgyldigInputException(
      "andreLopendeBidragBarnPersonId kan ikke være null"
    ),
    bidragBelop = if (andreLopendeBidragBidragBelop != null) andreLopendeBidragBidragBelop!! else throw UgyldigInputException(
      "andreLopendeBidragBidragBelop kan ikke være null"
    ),
    samvaersfradragBelop = if (andreLopendeBidragSamvaersfradragBelop != null) andreLopendeBidragSamvaersfradragBelop!!
    else throw UgyldigInputException("andreLopendeBidragSamvaersfradragBelop kan ikke være null")
  )
}

// Resultat
@Schema(description = "Resultatet av en beregning av barnebidrag")
data class BeregnBarnebidragResultat(
  @Schema(
    description = "Periodisert liste over resultat av beregning av barnebidrag"
  ) var resultatPeriodeListe: List<ResultatPeriodeBarnebidrag> = emptyList()
) {

  constructor(beregnBarnebidragResultat: BeregnetBarnebidragResultatCore) : this(
    resultatPeriodeListe = beregnBarnebidragResultat.resultatPeriodeListe.map { ResultatPeriodeBarnebidrag(it) }
  )
}

@Schema(description = "Resultatet av beregning av barnebidrag for et søknadsbarn for en gitt periode")
data class ResultatPeriodeBarnebidrag(
  @Schema(description = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode = Periode(),
  @Schema(description = "Beregning resultat innhold liste") var resultatBeregningListe: List<ResultatBeregningBarnebidrag> = emptyList(),
  @Schema(description = "Beregning grunnlag innhold") var resultatGrunnlag: List<String> = emptyList()
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
    resultatDatoFraTil = Periode(resultatPeriode.periode),
    resultatBeregningListe = resultatPeriode.resultatListe.map { ResultatBeregningBarnebidrag(it) },
    resultatGrunnlag = resultatPeriode.grunnlagReferanseListe
  )
}

@Schema(description = "Resultatet av beregning av barnebidrag")
data class ResultatBeregningBarnebidrag(
  @Schema(description = "Søknadsbarnets person-id") var resultatSoknadsbarnPersonId: Int = 0,
  @Schema(description = "Beløp barnebidrag") var resultatBelop: BigDecimal = BigDecimal.ZERO,
  @Schema(description = "Resultatkode barnebidrag") var resultatKode: String = ""
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
    resultatSoknadsbarnPersonId = resultatBeregning.soknadsbarnPersonId,
    resultatBelop = resultatBeregning.belop,
    resultatKode = resultatBeregning.kode
  )
}
