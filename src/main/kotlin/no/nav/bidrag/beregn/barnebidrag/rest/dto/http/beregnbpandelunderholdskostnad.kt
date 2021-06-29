package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.v3.oas.annotations.media.Schema
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnetBPsAndelUnderholdskostnadResultatCore
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatPeriodeCore
import java.math.BigDecimal

// Resultat
@Schema(description = "Totalresultatet av en beregning av BPs andel av underholdskostnad")
data class BeregnBPAndelUnderholdskostnadResultat(
  @Schema(description = "Periodisert liste over resultat av beregning av BPs andel av underholdskostnad") var resultatPeriodeListe: List<ResultatPeriodeBPAndelUnderholdskostnad> = emptyList()
) {

  constructor(beregnBPsAndelUnderholdskostnadResultat: BeregnetBPsAndelUnderholdskostnadResultatCore) : this(
    resultatPeriodeListe = beregnBPsAndelUnderholdskostnadResultat.resultatPeriodeListe.map { ResultatPeriodeBPAndelUnderholdskostnad(it) }
  )
}

@Schema(description = "Resultatet av beregning av BPs andel av underholdskostnad for et søknadsbarn for en gitt periode")
data class ResultatPeriodeBPAndelUnderholdskostnad(
  @Schema(description = "Søknadsbarnets person-id") var resultatSoknadsbarnPersonId: Int = 0,
  @Schema(description = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode = Periode(),
  @Schema(description = "Beregning resultat innhold") var resultatBeregning: ResultatBeregningBPAndelUnderholdskostnad = ResultatBeregningBPAndelUnderholdskostnad(),
  @Schema(description = "Beregning grunnlag innhold") var resultatGrunnlag: List<String> = emptyList()
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
    resultatSoknadsbarnPersonId = resultatPeriode.soknadsbarnPersonId,
    resultatDatoFraTil = Periode(resultatPeriode.periode),
    resultatBeregning = ResultatBeregningBPAndelUnderholdskostnad(resultatPeriode.resultat),
    resultatGrunnlag = resultatPeriode.grunnlagReferanseListe
  )
}

@Schema(description = "Resultatet av beregning av BPs andel av underholdskostnad")
data class ResultatBeregningBPAndelUnderholdskostnad(
  @Schema(description = "Resultatandel prosent") var resultatAndelProsent: BigDecimal = BigDecimal.ZERO,
  @Schema(description = "Resultatandel beløp") var resultatAndelBelop: BigDecimal = BigDecimal.ZERO,
  @Schema(description = "Barnet er selvforsørget") var barnetErSelvforsorget: Boolean = false
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
    resultatAndelProsent = resultatBeregning.andelProsent,
    resultatAndelBelop = resultatBeregning.andelBelop,
    barnetErSelvforsorget = resultatBeregning.barnetErSelvforsorget
  )
}
