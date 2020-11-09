package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.BeregnKostnadsberegnetBidragResultatCore
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.ResultatGrunnlagCore
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.ResultatPeriodeCore
import java.math.BigDecimal

// Resultat
@ApiModel(value = "Resultatet av beregning av kostnadsberegnet bidrag for bidragspliktig")
data class BeregnBPKostnadsberegnetBidragResultat(
    @ApiModelProperty(value = "Periodisert liste over resultat av beregning av kostnadsberegnet bidrag")
    var resultatPeriodeListe: List<ResultatPeriodeKostnadsberegnetBidrag> = emptyList()
) {

  constructor(beregnKostnadsberegnetBidragResultat: BeregnKostnadsberegnetBidragResultatCore) : this(
      resultatPeriodeListe = beregnKostnadsberegnetBidragResultat.resultatPeriodeListe.map {ResultatPeriodeKostnadsberegnetBidrag(it) }
  )
}

@ApiModel(value = "Resultatet av beregning av kostnadsberegnet bidrag for et søknadsbarn for en gitt periode")
data class ResultatPeriodeKostnadsberegnetBidrag(
    @ApiModelProperty(value = "Søknadsbarnets person-id") var resultatSoknadsbarnPersonId: Int = 0,
    @ApiModelProperty(value = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode,
    @ApiModelProperty(value = "Beregning resultat innhold") var resultatBeregning: ResultatBeregningKostnadsberegnetBidrag,
    @ApiModelProperty(value = "Beregning grunnlag innhold") var resultatGrunnlag: ResultatGrunnlagKostnadsberegnetBidrag
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
      resultatSoknadsbarnPersonId = resultatPeriode.soknadsbarnPersonId,
      resultatDatoFraTil = Periode(resultatPeriode.resultatDatoFraTil),
      resultatBeregning = ResultatBeregningKostnadsberegnetBidrag(resultatPeriode.resultatBeregning),
      resultatGrunnlag = ResultatGrunnlagKostnadsberegnetBidrag(resultatPeriode.resultatGrunnlag)
  )
}

@ApiModel(value = "Resultatet av beregning av kostnadsberegnet bidrag")
data class ResultatBeregningKostnadsberegnetBidrag(
    @ApiModelProperty(value = "Beløp kostnadsberegnet bidrag") var resultatBelop: BigDecimal = BigDecimal.ZERO
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
      resultatBelop = resultatBeregning.resultatKostnadsberegnetBidragBelop
  )
}

@ApiModel(value = "Grunnlaget for beregning av kostnadsberegnet bidrag")
data class ResultatGrunnlagKostnadsberegnetBidrag(
    @ApiModelProperty(value = "Beløp underholdskostnad") var underholdskostnadBelop: BigDecimal = BigDecimal.ZERO,
    @ApiModelProperty(value = "BPs andel underholdskostnad prosent") var bPAndelUnderholdskostnadProsent: BigDecimal = BigDecimal.ZERO,
    @ApiModelProperty(value = "Beløp samværsfradrag") var samvaersfradragBelop: BigDecimal? = BigDecimal.ZERO
) {

  constructor(resultatGrunnlag: ResultatGrunnlagCore) : this(
      underholdskostnadBelop = resultatGrunnlag.underholdskostnadBelop,
      bPAndelUnderholdskostnadProsent = resultatGrunnlag.bPsAndelUnderholdskostnadProsent,
      samvaersfradragBelop = resultatGrunnlag.samvaersfradragBelop
  )
}
