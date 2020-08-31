package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnBPsAndelUnderholdskostnadGrunnlagCore
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnBPsAndelUnderholdskostnadResultatCore
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.InntekterPeriodeCore
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatGrunnlagCore
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatPeriodeCore
import java.time.LocalDate

// Grunnlag
@ApiModel(value = "Grunnlaget for beregning av BPs andel av underholdskostnad")
data class BeregnBPsAndelUnderholdskostnadGrunnlag(
    @ApiModelProperty(value = "Beregn BPs andel underholdskostnad fra-dato") var beregnDatoFra: LocalDate? = null,
    @ApiModelProperty(value = "Beregn BPs andel underholdskostnad til-dato") var beregnDatoTil: LocalDate? = null,
    @ApiModelProperty(value = "Periodisert liste over inntekter") val inntekterPeriodeListe: List<InntekterPeriode>? = null
) {

  fun tilCore() = BeregnBPsAndelUnderholdskostnadGrunnlagCore(
      beregnDatoFra = if (beregnDatoFra != null) beregnDatoFra!! else throw UgyldigInputException("beregnDatoFra kan ikke være null"),
      beregnDatoTil = if (beregnDatoTil != null) beregnDatoTil!! else throw UgyldigInputException("beregnDatoTil kan ikke være null"),

      inntekterPeriodeListe = if (inntekterPeriodeListe != null) inntekterPeriodeListe.map { it.tilCore() }
      else throw UgyldigInputException("inntekterPeriodeListe kan ikke være null"),

      sjablonPeriodeListe = emptyList()
  )
}

@ApiModel(value = "Inntekter")
data class InntekterPeriode(
    @ApiModelProperty(value = "Inntekter fra-til-dato") var inntekterPeriodeDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Inntekt bidragspliktig") var inntektBP: Double? = null,
    @ApiModelProperty(value = "Inntekt bidragsmottaker") var inntektBM: Double? = null,
    @ApiModelProperty(value = "Inntekt bidragsbarn") var inntektBB: Double? = null
) {

  fun tilCore() = InntekterPeriodeCore(
      inntekterPeriodeDatoFraTil = if (inntekterPeriodeDatoFraTil != null) inntekterPeriodeDatoFraTil!!.tilCore()
      else throw UgyldigInputException("inntekterPeriodeDatoFraTil kan ikke være null"),

      inntektBP = if (inntektBP != null) inntektBP!! else throw UgyldigInputException("inntektBP kan ikke være null"),
      inntektBM = if (inntektBM != null) inntektBM!! else throw UgyldigInputException("inntektBM kan ikke være null"),
      inntektBB = if (inntektBB != null) inntektBB!! else throw UgyldigInputException("inntektBB kan ikke være null")
  )
}


// Resultat
@ApiModel(value = "Totalresultatet av beregning av BPs andel av underholdskostnad")
data class BeregnBPsAndelUnderholdskostnadResultat(
    @ApiModelProperty(value = "Periodisert liste over resultat av beregning av BPs andel av underholdskostnad")
    var resultatPeriodeListe: List<ResultatPeriodeBPsAndelUnderholdskostnad> = emptyList()
) {

  constructor(beregnBPsAndelUnderholdskostnadResultat: BeregnBPsAndelUnderholdskostnadResultatCore) : this(
      resultatPeriodeListe = beregnBPsAndelUnderholdskostnadResultat.resultatPeriodeListe.map {ResultatPeriodeBPsAndelUnderholdskostnad(it) }
  )
}

@ApiModel(value = "Resultatet av beregning av BPs andel av underholdskostnad for en gitt periode")
data class ResultatPeriodeBPsAndelUnderholdskostnad(
    @ApiModelProperty(value = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Beregning resultat innhold") var resultatBeregning: ResultatBeregningBPsAndelUnderholdskostnad? = null,
    @ApiModelProperty(value = "Beregning grunnlag innhold") var resultatGrunnlag: ResultatGrunnlagBPsAndelUnderholdskostnad? = null
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
      resultatDatoFraTil = Periode(resultatPeriode.resultatDatoFraTil),
      resultatBeregning = ResultatBeregningBPsAndelUnderholdskostnad(resultatPeriode.resultatBeregning),
      resultatGrunnlag = ResultatGrunnlagBPsAndelUnderholdskostnad(resultatPeriode.resultatGrunnlag)
  )
}

@ApiModel(value = "Resultatet av beregning av BPs andel av underholdskostnad")
data class ResultatBeregningBPsAndelUnderholdskostnad(
    @ApiModelProperty(value = "Resultatandel prosent") var resultatAndelProsent: Double = 0.0
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
      resultatAndelProsent = resultatBeregning.resultatAndelProsent
  )
}

@ApiModel(value = "Grunnlaget for beregning av BPs andel av underholdskostnad")
data class ResultatGrunnlagBPsAndelUnderholdskostnad(
    @ApiModelProperty(value = "Inntekt bidragspliktig") var inntektBP: Double? = null,
    @ApiModelProperty(value = "Inntekt bidragsmottaker") var inntektBM: Double? = null,
    @ApiModelProperty(value = "Inntekt bidragsbarn") var inntektBB: Double? = null,
    @ApiModelProperty(value = "Liste over sjablonperioder") var sjablonListe: List<Sjablon> = emptyList()
) {

  constructor(resultatGrunnlag: ResultatGrunnlagCore) : this(
      inntektBP = resultatGrunnlag.inntektBP,
      inntektBM = resultatGrunnlag.inntektBM,
      inntektBB = resultatGrunnlag.inntektBB,
      sjablonListe = resultatGrunnlag.sjablonListe.map { Sjablon(it) }
  )
}
