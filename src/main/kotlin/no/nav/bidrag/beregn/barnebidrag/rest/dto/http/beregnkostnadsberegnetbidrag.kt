package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.BeregnKostnadsberegnetBidragResultatCore
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.ResultatGrunnlagCore
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.ResultatPeriodeCore

// Resultat
@ApiModel(value = "Totalresultatet av beregning av kostnadsberegnet bidrag")
data class BeregnKostnadsberegnetBidragResultat(
    @ApiModelProperty(value = "Periodisert liste over resultat av beregning av kostnadsberegnet bidrag")
    var resultatPeriodeListe: List<ResultatPeriodeKostnadsberegnetBidrag> = emptyList()
) {

  constructor(beregnKostnadsberegnetBidragResultat: BeregnKostnadsberegnetBidragResultatCore) : this(
      resultatPeriodeListe = beregnKostnadsberegnetBidragResultat.resultatPeriodeListe.map {ResultatPeriodeKostnadsberegnetBidrag(it) }
  )
}

@ApiModel(value = "Resultatet av beregning av kostnadsberegnet bidrag for en gitt periode")
data class ResultatPeriodeKostnadsberegnetBidrag(
    @ApiModelProperty(value = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Beregning resultat innhold") var resultatBeregning: ResultatBeregningKostnadsberegnetBidrag? = null,
    @ApiModelProperty(value = "Beregning grunnlag innhold") var resultatGrunnlag: ResultatGrunnlagKostnadsberegnetBidrag? = null
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
      resultatDatoFraTil = Periode(resultatPeriode.resultatDatoFraTil),
      resultatBeregning = ResultatBeregningKostnadsberegnetBidrag(resultatPeriode.resultatBeregning),
      resultatGrunnlag = ResultatGrunnlagKostnadsberegnetBidrag(resultatPeriode.resultatGrunnlag)
  )
}

@ApiModel(value = "Resultatet av beregning av kostnadsberegnet bidrag")
data class ResultatBeregningKostnadsberegnetBidrag(
    @ApiModelProperty(value = "Beløp kostnadsberegnet bidrag") var resultatKostnadsberegnetBidragBelop: Double = 0.0
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
      resultatKostnadsberegnetBidragBelop = resultatBeregning.resultatKostnadsberegnetBidragBelop
  )
}

@ApiModel(value = "Grunnlaget for beregning av kostnadsberegnet bidrag")
data class ResultatGrunnlagKostnadsberegnetBidrag(
    @ApiModelProperty(value = "Beløp underholdskostnad") var underholdskostnadBelop: Double = 0.0,
    @ApiModelProperty(value = "BPs andel underholdskostnad prosent") var bPsAndelUnderholdskostnadProsent: Double = 0.0,
    @ApiModelProperty(value = "Beløp samværsfradrag") var samvaersfradragBelop: Double? = null
) {

  constructor(resultatGrunnlag: ResultatGrunnlagCore) : this(
      underholdskostnadBelop = resultatGrunnlag.underholdskostnadBelop,
      bPsAndelUnderholdskostnadProsent = resultatGrunnlag.bPsAndelUnderholdskostnadProsent,
      samvaersfradragBelop = resultatGrunnlag.samvaersfradrag
  )
}
