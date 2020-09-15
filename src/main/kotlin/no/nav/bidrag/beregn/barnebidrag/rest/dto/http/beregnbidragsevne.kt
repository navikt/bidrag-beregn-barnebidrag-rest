package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import no.nav.bidrag.beregn.bidragsevne.dto.AntallBarnIEgetHusholdPeriodeCore
import no.nav.bidrag.beregn.bidragsevne.dto.BeregnBidragsevneResultatCore
import no.nav.bidrag.beregn.bidragsevne.dto.BostatusPeriodeCore
import no.nav.bidrag.beregn.bidragsevne.dto.InntektCore
import no.nav.bidrag.beregn.bidragsevne.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.bidragsevne.dto.ResultatGrunnlagCore
import no.nav.bidrag.beregn.bidragsevne.dto.ResultatPeriodeCore
import no.nav.bidrag.beregn.bidragsevne.dto.SaerfradragPeriodeCore
import no.nav.bidrag.beregn.bidragsevne.dto.SkatteklassePeriodeCore

// Grunnlag
@ApiModel(value = "Grunnlaget for en bidragsevnesberegning for bidragspliktig")
data class BeregnBPBidragsevneGrunnlag(
    @ApiModelProperty(value = "Periodisert liste over bidragspliktiges skatteklasse") val skatteklassePeriodeListe: List<SkatteklassePeriode>? = null,
    @ApiModelProperty(value = "Periodisert liste over bidragspliktiges bostatus") val bostatusPeriodeListe: List<BostatusPeriode>? = null,
    @ApiModelProperty(
        value = "Periodisert liste over antall barn i bidragspliktiges hushold") val antallBarnIEgetHusholdPeriodeListe: List<AntallBarnIEgetHusholdPeriode>? = null,
    @ApiModelProperty(value = "Periodisert liste over bidragspliktiges særfradrag") val saerfradragPeriodeListe: List<SaerfradragPeriode>? = null
)

@ApiModel(value = "Bidragspliktiges skatteklasse")
data class SkatteklassePeriode(
    @ApiModelProperty(value = "Bidragspliktiges skatteklasse fra-til-dato") var skatteklassePeriodeDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Bidragspliktiges skatteklasse") var skatteklasseId: Int? = null
) {

  fun tilCore() = SkatteklassePeriodeCore(
      skatteklassePeriodeDatoFraTil = if (skatteklassePeriodeDatoFraTil != null) skatteklassePeriodeDatoFraTil!!.tilCore(
          "skatteklasse") else throw UgyldigInputException("skatteklassePeriodeDatoFraTil kan ikke være null"),
      skatteklasse = if (skatteklasseId != null) skatteklasseId!! else throw UgyldigInputException("skatteklasseId kan ikke være null")
  )
}

@ApiModel(value = "Bidragspliktiges bostatus")
data class BostatusPeriode(
    @ApiModelProperty(value = "Bidragspliktiges bostatus fra-til-dato") var bostatusPeriodeDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Bidragspliktiges bostatuskode") var bostatusKode: String? = null
) {

  fun tilCore() = BostatusPeriodeCore(
      bostatusPeriodeDatoFraTil = if (bostatusPeriodeDatoFraTil != null) bostatusPeriodeDatoFraTil!!.tilCore(
          "bostatus") else throw UgyldigInputException("bostatusPeriodeDatoFraTil kan ikke være null"),
      bostatusKode = if (bostatusKode != null) bostatusKode!! else throw UgyldigInputException("bostatusKode kan ikke være null")
  )
}

@ApiModel(value = "Antall barn i bidragspliktiges hushold")
data class AntallBarnIEgetHusholdPeriode(
    @ApiModelProperty(value = "Antall barn i bidragspliktiges hushold fra-til-dato") var antallBarnIEgetHusholdPeriodeDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Antall barn i bidragspliktiges husholde") var antallBarn: Int? = null
) {

  fun tilCore() = AntallBarnIEgetHusholdPeriodeCore(
      antallBarnIEgetHusholdPeriodeDatoFraTil = if (antallBarnIEgetHusholdPeriodeDatoFraTil != null) antallBarnIEgetHusholdPeriodeDatoFraTil!!.tilCore(
          "antallBarnIEgetHushold") else throw UgyldigInputException("antallBarnIEgetHusholdPeriodeDatoFraTil kan ikke være null"),
      antallBarn = if (antallBarn != null) antallBarn!! else throw UgyldigInputException("antallBarn kan ikke være null")
  )
}

@ApiModel(value = "Bidragspliktiges særfradrag")
data class SaerfradragPeriode(
    @ApiModelProperty(value = "Bidragspliktiges særfradrag fra-til-dato") var saerfradragPeriodeDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Bidragspliktiges særfradrag kode") var saerfradragKode: String? = null
) {

  fun tilCore() = SaerfradragPeriodeCore(
      saerfradragPeriodeDatoFraTil = if (saerfradragPeriodeDatoFraTil != null) saerfradragPeriodeDatoFraTil!!.tilCore(
          "saerfradrag") else throw UgyldigInputException("saerfradragPeriodeDatoFraTil kan ikke være null"),
      saerfradragKode = if (saerfradragKode != null) saerfradragKode!! else throw UgyldigInputException("saerfradragKode kan ikke være null")
  )
}

// Resultat
@ApiModel(value = "Resultatet av en bidragsevnesberegning")
data class BeregnBidragsevneResultat(
    @ApiModelProperty(
        value = "Periodisert liste over resultat av bidragsevnesberegning") var resultatPeriodeListe: List<ResultatPeriodeBidragsevne> = emptyList()
) {

  constructor(beregnBidragsevneResultat: BeregnBidragsevneResultatCore) : this(
      resultatPeriodeListe = beregnBidragsevneResultat.resultatPeriodeListe.map { ResultatPeriodeBidragsevne(it) }
  )
}

@ApiModel(value = "Resultatet av en beregning for en gitt periode")
data class ResultatPeriodeBidragsevne(
    @ApiModelProperty(value = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode,
    @ApiModelProperty(value = "Beregning resultat innhold") var resultatBeregning: ResultatBeregningBidragsevne,
    @ApiModelProperty(value = "Beregning grunnlag innhold") var resultatGrunnlag: ResultatGrunnlagBidragsevne
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
      resultatDatoFraTil = Periode(resultatPeriode.resultatDatoFraTil),
      resultatBeregning = ResultatBeregningBidragsevne(resultatPeriode.resultatBeregning),
      resultatGrunnlag = ResultatGrunnlagBidragsevne(resultatPeriode.resultatGrunnlag)
  )
}

@ApiModel(value = "Resultatet av en beregning")
data class ResultatBeregningBidragsevne(
    @ApiModelProperty(value = "Resultat 25 prosent inntekt") var resultat25ProsentInntekt: Double = 0.0,
    @ApiModelProperty(value = "Resultatevne beløp") var resultatEvneBelop: Double = 0.0
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
      resultat25ProsentInntekt = resultatBeregning.resultat25ProsentInntekt,
      resultatEvneBelop = resultatBeregning.resultatEvneBelop
  )
}

@ApiModel(value = "Grunnlaget for en beregning")
data class ResultatGrunnlagBidragsevne(
    @ApiModelProperty(value = "Liste over bidragspliktiges inntekter") var inntektListe: List<Inntekt> = emptyList(),
    @ApiModelProperty(value = "Bidragspliktiges skatteklasse") var skatteklasse: Int,
    @ApiModelProperty(value = "Bidragspliktiges bostatuskode") var bostatusKode: String,
    @ApiModelProperty(value = "Antall egne barn i bidragspliktiges husstand") var antallEgneBarnIHusstand: Int,
    @ApiModelProperty(value = "Bidragspliktiges særfradragkode") var saerfradragKode: String
//    @ApiModelProperty(value = "Liste over sjablonperioder") var sjablonListe: List<Sjablon> = emptyList()
) {

  constructor(resultatGrunnlag: ResultatGrunnlagCore) : this(
      inntektListe = resultatGrunnlag.inntektListe.map { Inntekt(it) },
      skatteklasse = resultatGrunnlag.skatteklasse,
      bostatusKode = resultatGrunnlag.bostatusKode,
      antallEgneBarnIHusstand = resultatGrunnlag.antallEgneBarnIHusstand,
      saerfradragKode = resultatGrunnlag.saerfradragkode
//      sjablonListe = resultatGrunnlag.sjablonListe.map { Sjablon(it) }
  )
}

@ApiModel(value = "Inntekttype og -beløp")
data class Inntekt(
    @ApiModelProperty(value = "Inntekt type") var inntektType: String,
    @ApiModelProperty(value = "Inntekt beløp") var inntektBelop: Double
) {

  constructor(inntekt: InntektCore) : this(
      inntektType = inntekt.inntektType,
      inntektBelop = inntekt.inntektBelop
  )
}