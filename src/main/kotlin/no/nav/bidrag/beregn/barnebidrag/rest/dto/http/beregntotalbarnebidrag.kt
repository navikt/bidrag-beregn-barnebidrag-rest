package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import no.nav.bidrag.beregn.bidragsevne.dto.BeregnBidragsevneGrunnlagAltCore
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnBPsAndelUnderholdskostnadGrunnlagCore
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnNettoBarnetilsynGrunnlagCore
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnSamvaersfradragGrunnlagCore
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadGrunnlagCore
import java.time.LocalDate

// Grunnlag
@ApiModel(value = "Grunnlaget for en total barnebidragsberegning")
data class BeregnTotalBarnebidragGrunnlag(
    @ApiModelProperty(value = "Beregn fra-dato") var beregnDatoFra: LocalDate? = null,
    @ApiModelProperty(value = "Beregn til-dato") var beregnDatoTil: LocalDate? = null,
    @ApiModelProperty(value = "Søknadsbarn grunnlag") var soknadsbarnGrunnlag: SoknadsbarnGrunnlag? = null,
    @ApiModelProperty(value = "Inntekt BP/BM grunnlag") var inntektBPBMGrunnlag: InntektBPBMGrunnlag? = null,
    @ApiModelProperty(value = "Beregn BP bidragsevne grunnlag") var beregnBPBidragsevneGrunnlag: BeregnBPBidragsevneGrunnlag? = null,
    @ApiModelProperty(value = "Beregn BM netto barnetilsyn grunnlag") var beregnBMNettoBarnetilsynGrunnlag: BeregnBMNettoBarnetilsynGrunnlag? = null,
    @ApiModelProperty(
        value = "Beregn BM underholdskostnad grunnlag") var beregnBMUnderholdskostnadGrunnlag: BeregnBMUnderholdskostnadGrunnlag? = null,
    @ApiModelProperty(value = "Beregn BP samværsfradrag grunnlag") var beregnBPSamvaersfradragGrunnlag: BeregnBPSamvaersfradragGrunnlag? = null,
    @ApiModelProperty(value = "Beregn barnebidrag grunnlag") var beregnBarnebidragGrunnlag: BeregnBarnebidragGrunnlag? = null
) {

  fun validerTotalBarnebidragGrunnlag() {
    if (beregnDatoFra != null) beregnDatoFra!! else throw UgyldigInputException("beregnDatoFra kan ikke være null")
    if (beregnDatoTil != null) beregnDatoTil!! else throw UgyldigInputException("beregnDatoTil kan ikke være null")
    if (soknadsbarnGrunnlag != null) soknadsbarnGrunnlag!! else throw UgyldigInputException("soknadsbarnGrunnlag kan ikke være null")
    if (inntektBPBMGrunnlag != null) inntektBPBMGrunnlag!! else throw UgyldigInputException("inntektBPBMGrunnlag kan ikke være null")
    if (beregnBPBidragsevneGrunnlag != null) beregnBPBidragsevneGrunnlag!! else throw UgyldigInputException(
        "beregnBPBidragsevneGrunnlag kan ikke være null")
    if (beregnBMNettoBarnetilsynGrunnlag != null) beregnBMNettoBarnetilsynGrunnlag!! else throw UgyldigInputException(
        "beregnBMNettoBarnetilsynGrunnlag kan ikke være null")
    if (beregnBMUnderholdskostnadGrunnlag != null) beregnBMUnderholdskostnadGrunnlag!! else throw UgyldigInputException(
        "beregnBMUnderholdskostnadGrunnlag kan ikke være null")
    if (beregnBPSamvaersfradragGrunnlag != null) beregnBPSamvaersfradragGrunnlag!! else throw UgyldigInputException(
        "beregnBPSamvaersfradragGrunnlag kan ikke være null")
    if (beregnBarnebidragGrunnlag != null) beregnBarnebidragGrunnlag!! else throw UgyldigInputException(
        "beregnBarnebidragGrunnlag kan ikke være null")
  }

  fun bidragsevneTilCore() = BeregnBidragsevneGrunnlagAltCore(
      beregnDatoFra = beregnDatoFra!!,
      beregnDatoTil = beregnDatoTil!!,

      inntektPeriodeListe =
      if (inntektBPBMGrunnlag!!.inntektBPPeriodeListe != null)
        inntektBPBMGrunnlag!!.inntektBPPeriodeListe!!.map { it.tilCore("BP ") }
      else throw UgyldigInputException("inntektBPPeriodeListe kan ikke være null"),

      skatteklassePeriodeListe =
      if (beregnBPBidragsevneGrunnlag!!.skatteklassePeriodeListe != null)
        beregnBPBidragsevneGrunnlag!!.skatteklassePeriodeListe!!.map { it.tilCore() }
      else throw UgyldigInputException("skatteklassePeriodeListe kan ikke være null"),

      bostatusPeriodeListe =
      if (beregnBPBidragsevneGrunnlag!!.bostatusPeriodeListe != null)
        beregnBPBidragsevneGrunnlag!!.bostatusPeriodeListe!!.map { it.tilCore() }
      else throw UgyldigInputException("bostatusPeriodeListe kan ikke være null"),

      antallBarnIEgetHusholdPeriodeListe =
      if (beregnBPBidragsevneGrunnlag!!.antallBarnIEgetHusholdPeriodeListe != null)
        beregnBPBidragsevneGrunnlag!!.antallBarnIEgetHusholdPeriodeListe!!.map { it.tilCore() }
      else throw UgyldigInputException("antallBarnIEgetHusholdPeriodeListe kan ikke være null"),

      saerfradragPeriodeListe =
      if (beregnBPBidragsevneGrunnlag!!.saerfradragPeriodeListe != null)
        beregnBPBidragsevneGrunnlag!!.saerfradragPeriodeListe!!.map { it.tilCore() }
      else throw UgyldigInputException("saerfradragPeriodeListe kan ikke være null"),

      sjablonPeriodeListe = emptyList()
  )

  fun nettoBarnetilsynTilCore() = BeregnNettoBarnetilsynGrunnlagCore(
      beregnDatoFra = beregnDatoFra!!,
      beregnDatoTil = beregnDatoTil!!,

      faktiskUtgiftPeriodeListe =
      if (beregnBMNettoBarnetilsynGrunnlag!!.faktiskUtgiftPeriodeListe != null)
        beregnBMNettoBarnetilsynGrunnlag!!.faktiskUtgiftPeriodeListe!!.map { it.tilCore() }
      else throw UgyldigInputException("faktiskUtgiftPeriodeListe kan ikke være null"),

      sjablonPeriodeListe = emptyList()
  )

//  fun underholdskostnadTilCore() = BeregnUnderholdskostnadGrunnlagCore(
//      beregnDatoFra = beregnDatoFra!!,
//      beregnDatoTil = beregnDatoTil!!,
//      soknadBarnFodselsdato = soknadBarnFodselsdato!!,
//
//      barnetilsynMedStonadPeriodeListe =
//      if (beregnUnderholdskostnadGrunnlag!!.barnetilsynMedStonadPeriodeListe != null)
//        beregnUnderholdskostnadGrunnlag!!.barnetilsynMedStonadPeriodeListe!!.map { it.tilCore() }
//      else throw UgyldigInputException("barnetilsynMedStonadPeriodeListe kan ikke være null"),
//
//      nettoBarnetilsynPeriodeListe = emptyList(),
//
//      forpleiningUtgiftPeriodeListe =
//      if (beregnUnderholdskostnadGrunnlag!!.forpleiningUtgiftPeriodeListe != null)
//        beregnUnderholdskostnadGrunnlag!!.forpleiningUtgiftPeriodeListe!!.map { it.tilCore() }
//      else throw UgyldigInputException("forpleiningUtgiftPeriodeListe kan ikke være null"),
//
//      sjablonPeriodeListe = emptyList()
//  )
//
//  fun bpAndelUnderholdskostnadTilCore() = BeregnBPsAndelUnderholdskostnadGrunnlagCore(
//      beregnDatoFra = beregnDatoFra!!,
//      beregnDatoTil = beregnDatoTil!!,
//
//      inntekterPeriodeListe =
//      if (beregnBPAndelUnderholdskostnadGrunnlag!!.inntekterPeriodeListe != null)
//        beregnBPAndelUnderholdskostnadGrunnlag!!.inntekterPeriodeListe!!.map { it.tilCore() }
//      else throw UgyldigInputException("inntekterPeriodeListe kan ikke være null"),
//
//      sjablonPeriodeListe = emptyList()
//  )
//
//  fun samvaersfradragTilCore() = BeregnSamvaersfradragGrunnlagCore(
//      beregnDatoFra = beregnDatoFra!!,
//      beregnDatoTil = beregnDatoTil!!,
//      soknadsbarnFodselsdato = soknadBarnFodselsdato!!,
//
//      samvaersklassePeriodeListe =
//      if (beregnSamvaersfradragGrunnlag!!.samvaersklassePeriodeListe != null)
//        beregnSamvaersfradragGrunnlag!!.samvaersklassePeriodeListe!!.map { it.tilCore() }
//      else throw UgyldigInputException("samvaersklassePeriodeListe kan ikke være null"),
//
//      sjablonPeriodeListe = emptyList()
//  )
//
//  fun barnebidragTilCore() = BeregnBarnebidragGrunnlagCore(
//      beregnDatoFra = beregnDatoFra!!,
//      beregnDatoTil = beregnDatoTil!!,
//
//      bidragsevnePeriodeListe = emptyList(),
//      kostnadsberegnetBidragPeriodeListe = emptyList(),
//      samvaerfradragPeriodeListe = emptyList(),
//
//      barnetilleggBPPeriodeListe =
//      if (beregnBarnebidragGrunnlag!!.barnetilleggBPPeriodeListe != null)
//        beregnBarnebidragGrunnlag!!.barnetilleggBPPeriodeListe!!.map { it.tilCore() }
//      else throw UgyldigInputException("barnetilleggBPPeriodeListe kan ikke være null"),
//
//      barnetilleggBMPeriodeListe =
//      if (beregnBarnebidragGrunnlag!!.barnetilleggBMPeriodeListe != null)
//        beregnBarnebidragGrunnlag!!.barnetilleggBMPeriodeListe!!.map { it.tilCore() }
//      else throw UgyldigInputException("barnetilleggBMPeriodeListe kan ikke være null"),
//
//      barnetilleggForsvaretPeriodeListe =
//      if (beregnBarnebidragGrunnlag!!.barnetilleggForsvaretPeriodeListe != null)
//        beregnBarnebidragGrunnlag!!.barnetilleggForsvaretPeriodeListe!!.map { it.tilCore() }
//      else throw UgyldigInputException("barnetilleggForsvaretPeriodeListe kan ikke være null"),
//
//      sjablonPeriodeListe = emptyList()
//  )
}

// Resultat
@ApiModel(value = "Resultatet av en total barnebidragsberegning")
data class BeregnTotalBarnebidragResultat(
    @ApiModelProperty(value = "Beregn bidragsevne resultat") var beregnBidragsevneResultat: BeregnBidragsevneResultat,
    @ApiModelProperty(value = "Beregn netto barnetilsyn resultat") var beregnNettoBarnetilsynResultat: BeregnNettoBarnetilsynResultat,
    @ApiModelProperty(value = "Beregn underholdskostnad resultat") var beregnUnderholdskostnadResultat: BeregnUnderholdskostnadResultat,
    @ApiModelProperty(
        value = "Beregn BPs andel av underholdskostnad resultat") var beregnBPAndelUnderholdskostnadResultat: BeregnBPsAndelUnderholdskostnadResultat,
    @ApiModelProperty(value = "Beregn samværsfradrag resultat") var beregnSamvaersfradragResultat: BeregnSamvaersfradragResultat,
    @ApiModelProperty(
        value = "Beregn kostnadsberegnet bidrag resultat") var beregnKostnadsberegnetBidragResultat: BeregnKostnadsberegnetBidragResultat,
    @ApiModelProperty(value = "Beregn barnebidrag resultat") var beregnBarnebidragResultat: BeregnBarnebidragResultat
)
