package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.v3.oas.annotations.media.Schema
import no.nav.bidrag.beregn.bidragsevne.dto.BeregnetBidragsevneResultatCore
import no.nav.bidrag.beregn.bidragsevne.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.bidragsevne.dto.ResultatPeriodeCore
import java.math.BigDecimal

// Resultat
@Schema(description = "Resultatet av en bidragsevnesberegning for bidragspliktig")
data class BeregnBPBidragsevneResultat(
  @Schema(description = "Periodisert liste over resultat av bidragsevnesberegning") var resultatPeriodeListe: List<ResultatPeriodeBidragsevne> = emptyList()
) {

  constructor(beregnBidragsevneResultat: BeregnetBidragsevneResultatCore) : this(
    resultatPeriodeListe = beregnBidragsevneResultat.resultatPeriodeListe.map { ResultatPeriodeBidragsevne(it) }
  )
}

@Schema(description = "Resultatet av en beregning for en gitt periode")
data class ResultatPeriodeBidragsevne(
  @Schema(description = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode = Periode(),
  @Schema(description = "Beregning resultat innhold") var resultatBeregning: ResultatBeregningBidragsevne = ResultatBeregningBidragsevne(),
  @Schema(description = "Beregning grunnlag innhold") var resultatGrunnlag: List<String> = emptyList()
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
    resultatDatoFraTil = Periode(resultatPeriode.periode),
    resultatBeregning = ResultatBeregningBidragsevne(resultatPeriode.resultat),
    resultatGrunnlag = resultatPeriode.grunnlagReferanseListe
  )
}

@Schema(description = "Resultatet av en beregning")
data class ResultatBeregningBidragsevne(
  @Schema(description = "Resultat 25 prosent inntekt") var resultat25ProsentInntekt: BigDecimal = BigDecimal.ZERO,
  @Schema(description = "Resultatevne beløp") var resultatEvneBelop: BigDecimal = BigDecimal.ZERO
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
    resultatEvneBelop = resultatBeregning.belop,
    resultat25ProsentInntekt = resultatBeregning.inntekt25Prosent
  )
}
