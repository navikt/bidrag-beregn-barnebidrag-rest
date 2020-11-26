package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnBPsAndelUnderholdskostnadResultatCore
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatGrunnlagCore
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatPeriodeCore
import java.math.BigDecimal

// Resultat
@ApiModel(value = "Totalresultatet av en beregning av BPs andel av underholdskostnad")
data class BeregnBPAndelUnderholdskostnadResultat(
    @ApiModelProperty(
        value = "Periodisert liste over resultat av beregning av BPs andel av underholdskostnad") var resultatPeriodeListe: List<ResultatPeriodeBPAndelUnderholdskostnad> = emptyList()
) {

  constructor(beregnBPsAndelUnderholdskostnadResultat: BeregnBPsAndelUnderholdskostnadResultatCore) : this(
      resultatPeriodeListe = beregnBPsAndelUnderholdskostnadResultat.resultatPeriodeListe.map { ResultatPeriodeBPAndelUnderholdskostnad(it) }
  )
}

@ApiModel(value = "Resultatet av beregning av BPs andel av underholdskostnad for et søknadsbarn for en gitt periode")
data class ResultatPeriodeBPAndelUnderholdskostnad(
    @ApiModelProperty(value = "Søknadsbarnets person-id") var resultatSoknadsbarnPersonId: Int = 0,
    @ApiModelProperty(value = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode,
    @ApiModelProperty(value = "Beregning resultat innhold") var resultatBeregning: ResultatBeregningBPAndelUnderholdskostnad,
    @ApiModelProperty(value = "Beregning grunnlag innhold") var resultatGrunnlag: ResultatGrunnlagBPAndelUnderholdskostnad
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
      resultatSoknadsbarnPersonId = resultatPeriode.soknadsbarnPersonId,
      resultatDatoFraTil = Periode(resultatPeriode.resultatDatoFraTil),
      resultatBeregning = ResultatBeregningBPAndelUnderholdskostnad(resultatPeriode.resultatBeregning),
      resultatGrunnlag = ResultatGrunnlagBPAndelUnderholdskostnad(resultatPeriode.resultatGrunnlag)
  )
}

@ApiModel(value = "Resultatet av beregning av BPs andel av underholdskostnad")
data class ResultatBeregningBPAndelUnderholdskostnad(
    @ApiModelProperty(value = "Resultatandel prosent") var resultatAndelProsent: BigDecimal = BigDecimal.ZERO,
    @ApiModelProperty(value = "Resultatandel beløp") var resultatAndelBelop: BigDecimal = BigDecimal.ZERO,
    @ApiModelProperty(value = "Barnet er selvforsørget") var barnetErSelvforsorget: Boolean = false
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
      resultatAndelProsent = resultatBeregning.resultatAndelProsent,
      resultatAndelBelop = resultatBeregning.resultatAndelBelop,
      barnetErSelvforsorget = resultatBeregning.barnetErSelvforsorget
  )
}

@ApiModel(value = "Grunnlaget for beregning av BPs andel av underholdskostnad")
data class ResultatGrunnlagBPAndelUnderholdskostnad(
    @ApiModelProperty(value = "Underholdskostnad beløp") var underholdskostnadBelop: BigDecimal = BigDecimal.ZERO,
    @ApiModelProperty(value = "Liste over bidragspliktiges inntekter") var inntektBPListe: List<Inntekt> = emptyList(),
    @ApiModelProperty(value = "Liste over bidragsmottakers inntekter") var inntektBMListe: List<InntektBM> = emptyList(),
    @ApiModelProperty(value = "Liste over søknadsbarnets inntekter") var inntektSBListe: List<Inntekt> = emptyList(),
    @ApiModelProperty(value = "Liste over sjablonperioder") var sjablonListe: List<Sjablon>
) {

  constructor(resultatGrunnlag: ResultatGrunnlagCore) : this(
      underholdskostnadBelop = resultatGrunnlag.underholdskostnadBelop,
      inntektBPListe = resultatGrunnlag.inntektBPListe.map { Inntekt(it) },
      inntektBMListe = resultatGrunnlag.inntektBMListe.map { InntektBM(it) },
      inntektSBListe = resultatGrunnlag.inntektBBListe.map { Inntekt(it) },
      sjablonListe = resultatGrunnlag.sjablonListe.map { Sjablon(it) }
  )
}
