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
@ApiModel(value = "Grunnlaget for en barnebidragsberegning")
data class BeregnBarnebidragGrunnlag(
    @ApiModelProperty(value = "Beregn fra-dato") var beregnDatoFra: LocalDate? = null,
    @ApiModelProperty(value = "Beregn til-dato") var beregnDatoTil: LocalDate? = null,
    @ApiModelProperty(value = "Søknadsbarnets fødselsdato") var soknadBarnFodselsdato: LocalDate? = null,
    @ApiModelProperty(value = "Søknadsbarnets person-id") var soknadBarnPersonId: Int? = null,
    @ApiModelProperty(value = "Beregn bidragsevne grunnlag") var beregnBidragsevneGrunnlag: BeregnBidragsevneGrunnlag? = null,
    @ApiModelProperty(value = "Beregn netto barnetilsyn grunnlag") var beregnNettoBarnetilsynGrunnlag: BeregnNettoBarnetilsynGrunnlag? = null,
    @ApiModelProperty(value = "Beregn underholdskostnad grunnlag") var beregnUnderholdskostnadGrunnlag: BeregnUnderholdskostnadGrunnlag? = null,
    @ApiModelProperty(
        value = "Beregn BPs andel av underholdskostnad grunnlag") var beregnBPAndelUnderholdskostnadGrunnlag: BeregnBPsAndelUnderholdskostnadGrunnlag? = null,
    @ApiModelProperty(value = "Beregn samværsfradrag grunnlag") var beregnSamvaersfradragGrunnlag: BeregnSamvaersfradragGrunnlag? = null
) {

  fun validerBarnebidragGrunnlag() {
    if (beregnDatoFra != null) beregnDatoFra!! else throw UgyldigInputException("beregnDatoFra kan ikke være null")
    if (beregnDatoTil != null) beregnDatoTil!! else throw UgyldigInputException("beregnDatoTil kan ikke være null")
    if (soknadBarnFodselsdato != null) soknadBarnFodselsdato!! else throw UgyldigInputException("soknadBarnFodselsdato kan ikke være null")
    if (soknadBarnPersonId != null) soknadBarnPersonId!! else throw UgyldigInputException("soknadBarnPersonId kan ikke være null")

    if (beregnBidragsevneGrunnlag != null) beregnBidragsevneGrunnlag!!
    else throw UgyldigInputException("beregnBidragsevneGrunnlag kan ikke være null")

    if (beregnNettoBarnetilsynGrunnlag != null) beregnNettoBarnetilsynGrunnlag!!
    else throw UgyldigInputException("beregnNettoBarnetilsynGrunnlag kan ikke være null")

    if (beregnUnderholdskostnadGrunnlag != null) beregnUnderholdskostnadGrunnlag!!
    else throw UgyldigInputException("beregnUnderholdskostnadGrunnlag kan ikke være null")

    if (beregnBPAndelUnderholdskostnadGrunnlag != null) beregnBPAndelUnderholdskostnadGrunnlag!!
    else throw UgyldigInputException("beregnBPAndelUnderholdskostnadGrunnlag kan ikke være null")

    if (beregnSamvaersfradragGrunnlag != null) beregnSamvaersfradragGrunnlag!!
    else throw UgyldigInputException("beregnSamvaersfradragGrunnlag kan ikke være null")
  }

  fun bidragsevneTilCore() = BeregnBidragsevneGrunnlagAltCore(
      beregnDatoFra = beregnDatoFra!!,
      beregnDatoTil = beregnDatoTil!!,

      inntektPeriodeListe =
      if (beregnBidragsevneGrunnlag!!.inntektPeriodeListe != null)
        beregnBidragsevneGrunnlag!!.inntektPeriodeListe!!.map { it.tilCore() }
      else throw UgyldigInputException("inntektPeriodeListe kan ikke være null"),

      skatteklassePeriodeListe =
      if (beregnBidragsevneGrunnlag!!.skatteklassePeriodeListe != null)
        beregnBidragsevneGrunnlag!!.skatteklassePeriodeListe!!.map { it.tilCore() }
      else throw UgyldigInputException("skatteklassePeriodeListe kan ikke være null"),

      bostatusPeriodeListe =
      if (beregnBidragsevneGrunnlag!!.bostatusPeriodeListe != null)
        beregnBidragsevneGrunnlag!!.bostatusPeriodeListe!!.map { it.tilCore() }
      else throw UgyldigInputException("bostatusPeriodeListe kan ikke være null"),

      antallBarnIEgetHusholdPeriodeListe =
      if (beregnBidragsevneGrunnlag!!.antallBarnIEgetHusholdPeriodeListe != null)
        beregnBidragsevneGrunnlag!!.antallBarnIEgetHusholdPeriodeListe!!.map { it.tilCore() }
      else throw UgyldigInputException("antallBarnIEgetHusholdPeriodeListe kan ikke være null"),

      saerfradragPeriodeListe =
      if (beregnBidragsevneGrunnlag!!.saerfradragPeriodeListe != null)
        beregnBidragsevneGrunnlag!!.saerfradragPeriodeListe!!.map { it.tilCore() }
      else throw UgyldigInputException("saerfradragPeriodeListe kan ikke være null"),

      sjablonPeriodeListe = emptyList()
  )

  fun nettoBarnetilsynTilCore() = BeregnNettoBarnetilsynGrunnlagCore(
      beregnDatoFra = beregnDatoFra!!,
      beregnDatoTil = beregnDatoTil!!,

      faktiskUtgiftPeriodeListe =
      if (beregnNettoBarnetilsynGrunnlag!!.faktiskUtgiftPeriodeListe != null)
        beregnNettoBarnetilsynGrunnlag!!.faktiskUtgiftPeriodeListe!!.map { it.tilCore() }
      else throw UgyldigInputException("faktiskUtgiftPeriodeListe kan ikke være null"),

      sjablonPeriodeListe = emptyList()
  )

  fun underholdskostnadTilCore() = BeregnUnderholdskostnadGrunnlagCore(
      beregnDatoFra = beregnDatoFra!!,
      beregnDatoTil = beregnDatoTil!!,
      soknadBarnFodselsdato = soknadBarnFodselsdato!!,

      barnetilsynMedStonadPeriodeListe =
      if (beregnUnderholdskostnadGrunnlag!!.barnetilsynMedStonadPeriodeListe != null)
        beregnUnderholdskostnadGrunnlag!!.barnetilsynMedStonadPeriodeListe!!.map { it.tilCore() }
      else throw UgyldigInputException("barnetilsynMedStonadPeriodeListe kan ikke være null"),

      nettoBarnetilsynPeriodeListe = emptyList(),

      forpleiningUtgiftPeriodeListe =
      if (beregnUnderholdskostnadGrunnlag!!.forpleiningUtgiftPeriodeListe != null)
        beregnUnderholdskostnadGrunnlag!!.forpleiningUtgiftPeriodeListe!!.map { it.tilCore() }
      else throw UgyldigInputException("forpleiningUtgiftPeriodeListe kan ikke være null"),

      sjablonPeriodeListe = emptyList()
  )

  fun bpAndelUnderholdskostnadTilCore() = BeregnBPsAndelUnderholdskostnadGrunnlagCore(
      beregnDatoFra = beregnDatoFra!!,
      beregnDatoTil = beregnDatoTil!!,

      inntekterPeriodeListe =
      if (beregnBPAndelUnderholdskostnadGrunnlag!!.inntekterPeriodeListe != null)
        beregnBPAndelUnderholdskostnadGrunnlag!!.inntekterPeriodeListe!!.map { it.tilCore() }
      else throw UgyldigInputException("inntekterPeriodeListe kan ikke være null"),

      sjablonPeriodeListe = emptyList()
  )

  fun samvaersfradragTilCore() = BeregnSamvaersfradragGrunnlagCore(
      beregnDatoFra = beregnDatoFra!!,
      beregnDatoTil = beregnDatoTil!!,
      soknadsbarnFodselsdato = soknadBarnFodselsdato!!,

      samvaersklassePeriodeListe =
      if (beregnSamvaersfradragGrunnlag!!.samvaersklassePeriodeListe != null)
        beregnSamvaersfradragGrunnlag!!.samvaersklassePeriodeListe!!.map { it.tilCore() }
      else throw UgyldigInputException("samvaersklassePeriodeListe kan ikke være null"),

      sjablonPeriodeListe = emptyList()
  )
}

// Resultat
@ApiModel(value = "Resultatet av en barnebidragsberegning")
data class BeregnBarnebidragResultat(
    @ApiModelProperty(value = "Beregn bidragsevne resultat") var beregnBidragsevneResultat: BeregnBidragsevneResultat,
    @ApiModelProperty(value = "Beregn netto barnetilsyn resultat") var beregnNettoBarnetilsynResultat: BeregnNettoBarnetilsynResultat,
    @ApiModelProperty(value = "Beregn underholdskostnad resultat") var beregnUnderholdskostnadResultat: BeregnUnderholdskostnadResultat,
    @ApiModelProperty(
        value = "Beregn BPs andel av underholdskostnad resultat") var beregnBPAndelUnderholdskostnadResultat: BeregnBPsAndelUnderholdskostnadResultat,
    @ApiModelProperty(value = "Beregn samværsfradrag resultat") var beregnSamvaersfradragResultat: BeregnSamvaersfradragResultat,
    @ApiModelProperty(
        value = "Beregn kostnadsberegnet bidrag resultat") var beregnKostnadsberegnetBidragResultat: BeregnKostnadsberegnetBidragResultat
)
