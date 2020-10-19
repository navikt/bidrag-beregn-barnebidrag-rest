package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import no.nav.bidrag.beregn.barnebidrag.dto.BPsAndelUnderholdskostnadPeriodeCore
import no.nav.bidrag.beregn.barnebidrag.dto.BeregnBarnebidragGrunnlagCore
import no.nav.bidrag.beregn.barnebidrag.dto.BidragsevnePeriodeCore
import no.nav.bidrag.beregn.barnebidrag.dto.SamvaersfradragPeriodeCore
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import no.nav.bidrag.beregn.bidragsevne.dto.BeregnBidragsevneGrunnlagCore
import no.nav.bidrag.beregn.felles.dto.SjablonPeriodeCore
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnNettoBarnetilsynGrunnlagCore
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

  fun validerSoknadsbarn() {
    if (soknadsbarnGrunnlag!!.soknadsbarnListe != null)
      soknadsbarnGrunnlag!!.soknadsbarnListe!!.map { it.validerSoknadsbarn() }
    else throw UgyldigInputException("soknadsbarnListe kan ikke være null")
  }

  fun validerInntekt() {
    if (inntektBPBMGrunnlag!!.inntektBPPeriodeListe != null)
      inntektBPBMGrunnlag!!.inntektBPPeriodeListe!!.map { it.validerInntekt("BP") }
    else throw UgyldigInputException("inntektBPPeriodeListe kan ikke være null")

    if (inntektBPBMGrunnlag!!.inntektBMPeriodeListe != null)
      inntektBPBMGrunnlag!!.inntektBMPeriodeListe!!.map { it.validerInntekt("BM") }
    else throw UgyldigInputException("inntektBMPeriodeListe kan ikke være null")
  }

  fun bidragsevneTilCore(sjablonPeriodeListe: List<SjablonPeriodeCore>) = BeregnBidragsevneGrunnlagCore(
      beregnDatoFra = beregnDatoFra!!,
      beregnDatoTil = beregnDatoTil!!,

      inntektPeriodeListe =
      if (inntektBPBMGrunnlag!!.inntektBPPeriodeListe != null)
        inntektBPBMGrunnlag!!.inntektBPPeriodeListe!!.map { it.tilCore("BP") }
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

      sjablonPeriodeListe = sjablonPeriodeListe
  )

  fun nettoBarnetilsynTilCore(soknadsbarnMap: Map<Int, LocalDate>, sjablonPeriodeListe: List<SjablonPeriodeCore>) = BeregnNettoBarnetilsynGrunnlagCore(
      beregnDatoFra = beregnDatoFra!!,
      beregnDatoTil = beregnDatoTil!!,

      faktiskUtgiftPeriodeListe =
      if (beregnBMNettoBarnetilsynGrunnlag!!.faktiskUtgiftPeriodeListe != null)
        beregnBMNettoBarnetilsynGrunnlag!!.faktiskUtgiftPeriodeListe!!.map { it.tilCore(soknadsbarnMap) }
      else throw UgyldigInputException("faktiskUtgiftPeriodeListe kan ikke være null"),

      sjablonPeriodeListe = sjablonPeriodeListe
  )

  fun validerUnderholdskostnad() {
    if (beregnBMUnderholdskostnadGrunnlag!!.barnetilsynMedStonadPeriodeListe != null)
      beregnBMUnderholdskostnadGrunnlag!!.barnetilsynMedStonadPeriodeListe!!.map { it.validerBarnetilsynMedStonad() }
    else throw UgyldigInputException("barnetilsynMedStonadPeriodeListe kan ikke være null")

    if (beregnBMUnderholdskostnadGrunnlag!!.forpleiningUtgiftPeriodeListe != null)
      beregnBMUnderholdskostnadGrunnlag!!.forpleiningUtgiftPeriodeListe!!.map { it.validerForpleiningUtgift() }
    else throw UgyldigInputException("forpleiningUtgiftPeriodeListe kan ikke være null")
  }

  fun validerSamvaersfradrag() {
    if (beregnBPSamvaersfradragGrunnlag!!.samvaersklassePeriodeListe != null)
      beregnBPSamvaersfradragGrunnlag!!.samvaersklassePeriodeListe!!.map { it.validerSamvaersklasse() }
    else throw UgyldigInputException("samvaersklassePeriodeListe kan ikke være null")
  }


  fun barnebidragTilCore(
      bidragsevnePeriodeListe: List<BidragsevnePeriodeCore>, bPAndelUnderholdskostnadPeriodeListe: List<BPsAndelUnderholdskostnadPeriodeCore>,
      samvaersfradragPeriodeListe: List<SamvaersfradragPeriodeCore>, sjablonPeriodeListe: List<SjablonPeriodeCore>) = BeregnBarnebidragGrunnlagCore(

      beregnDatoFra = beregnDatoFra!!,
      beregnDatoTil = beregnDatoTil!!,

      bidragsevnePeriodeListe = bidragsevnePeriodeListe,
      bPsAndelUnderholdskostnadPeriodeListe = bPAndelUnderholdskostnadPeriodeListe,
      samvaersfradragPeriodeListe = samvaersfradragPeriodeListe,

      deltBostedPeriodeListe =
      if (beregnBarnebidragGrunnlag!!.deltBostedBPPeriodeListe != null)
        beregnBarnebidragGrunnlag!!.deltBostedBPPeriodeListe!!.map { it.tilCore() }
      else throw UgyldigInputException("deltBostedBPPeriodeListe kan ikke være null"),

      barnetilleggBPPeriodeListe =
      if (beregnBarnebidragGrunnlag!!.barnetilleggBPPeriodeListe != null)
        beregnBarnebidragGrunnlag!!.barnetilleggBPPeriodeListe!!.map { it.tilCore("BP") }
      else throw UgyldigInputException("barnetilleggBPPeriodeListe kan ikke være null"),

      barnetilleggBMPeriodeListe =
      if (beregnBarnebidragGrunnlag!!.barnetilleggBMPeriodeListe != null)
        beregnBarnebidragGrunnlag!!.barnetilleggBMPeriodeListe!!.map { it.tilCore("BM") }
      else throw UgyldigInputException("barnetilleggBMPeriodeListe kan ikke være null"),

      barnetilleggForsvaretPeriodeListe =
      if (beregnBarnebidragGrunnlag!!.barnetilleggForsvaretBPPeriodeListe != null)
        beregnBarnebidragGrunnlag!!.barnetilleggForsvaretBPPeriodeListe!!.map { it.tilCore() }
      else throw UgyldigInputException("barnetilleggForsvaretBPPeriodeListe kan ikke være null"),

      sjablonPeriodeListe = sjablonPeriodeListe
  )
}

// Resultat
@ApiModel(value = "Resultatet av en total barnebidragsberegning")
data class BeregnTotalBarnebidragResultat(
    @ApiModelProperty(value = "Beregn BP bidragsevne resultat") var beregnBPBidragsevneResultat: BeregnBPBidragsevneResultat,
    @ApiModelProperty(value = "Beregn BM netto barnetilsyn resultat") var beregnBMNettoBarnetilsynResultat: BeregnBMNettoBarnetilsynResultat,
    @ApiModelProperty(value = "Beregn BM underholdskostnad resultat") var beregnBMUnderholdskostnadResultat: BeregnBMUnderholdskostnadResultat,
    @ApiModelProperty(
        value = "Beregn BP andel av underholdskostnad resultat") var beregnBPAndelUnderholdskostnadResultat: BeregnBPAndelUnderholdskostnadResultat,
    @ApiModelProperty(value = "Beregn BP samværsfradrag resultat") var beregnBPSamvaersfradragResultat: BeregnBPSamvaersfradragResultat,
    @ApiModelProperty(
        value = "Beregn BP kostnadsberegnet bidrag resultat") var beregnBPKostnadsberegnetBidragResultat: BeregnBPKostnadsberegnetBidragResultat,
    @ApiModelProperty(value = "Beregn barnebidrag resultat") var beregnBarnebidragResultat: BeregnBarnebidragResultat
)
