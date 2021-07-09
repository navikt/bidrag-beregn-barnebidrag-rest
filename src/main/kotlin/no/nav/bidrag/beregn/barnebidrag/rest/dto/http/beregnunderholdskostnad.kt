package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.v3.oas.annotations.media.Schema
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnetUnderholdskostnadResultatCore
import no.nav.bidrag.beregn.underholdskostnad.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.underholdskostnad.dto.ResultatPeriodeCore
import java.math.BigDecimal

// Resultat
@Schema(description = "Resultatet av en underholdskostnadsberegning for bidragsmottaker")
data class BeregnBMUnderholdskostnadResultat(
  @Schema(description = "Periodisert liste over resultat av beregning av underholdskostnad") var resultatPeriodeListe: List<ResultatPeriodeUnderholdskostnad> = emptyList()
) {

  constructor(beregnUnderholdskostnadResultat: BeregnetUnderholdskostnadResultatCore) : this(
    resultatPeriodeListe = beregnUnderholdskostnadResultat.resultatPeriodeListe.map { ResultatPeriodeUnderholdskostnad(it) }
  )
}

@Schema(description = "Resultatet av beregning av underholdskostnad for et søknadsbarn for en gitt periode")
data class ResultatPeriodeUnderholdskostnad(
  @Schema(description = "Søknadsbarnets person-id") var resultatSoknadsbarnPersonId: Int = 0,
  @Schema(description = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode = Periode(),
  @Schema(description = "Beregning resultat innhold") var resultatBeregning: ResultatBeregningUnderholdskostnad = ResultatBeregningUnderholdskostnad(),
  @Schema(description = "Beregning grunnlag innhold") var resultatGrunnlag: List<String> = emptyList()
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
    resultatSoknadsbarnPersonId = resultatPeriode.soknadsbarnPersonId,
    resultatDatoFraTil = Periode(resultatPeriode.periode),
    resultatBeregning = ResultatBeregningUnderholdskostnad(resultatPeriode.resultat),
    resultatGrunnlag = resultatPeriode.grunnlagReferanseListe
  )
}

@Schema(description = "Resultatet av beregning av underholdskostnad")
data class ResultatBeregningUnderholdskostnad(
  @Schema(description = "Beløp underholdskostnad") var resultatBelop: BigDecimal = BigDecimal.ZERO
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
    resultatBelop = resultatBeregning.belop
  )
}
