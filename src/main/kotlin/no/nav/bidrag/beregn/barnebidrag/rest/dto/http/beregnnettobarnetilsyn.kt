package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.v3.oas.annotations.media.Schema
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnetNettoBarnetilsynResultatCore
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.ResultatPeriodeCore
import java.math.BigDecimal

// Resultat
@Schema(description = "Resultatet av en netto barnetilsyn beregning for bidragsmottaker")
data class BeregnBMNettoBarnetilsynResultat(
  @Schema(description = "Periodisert liste over resultat av beregning av netto barnetilsyn") var resultatPeriodeListe: List<ResultatPeriodeNettoBarnetilsyn> = emptyList()
) {

  constructor(beregnNettoBarnetilsynResultat: BeregnetNettoBarnetilsynResultatCore) : this(
    resultatPeriodeListe = beregnNettoBarnetilsynResultat.resultatPeriodeListe.map { ResultatPeriodeNettoBarnetilsyn(it) }
  )
}

@Schema(description = "Resultatet av beregning av netto barnetilsyn for et søknadsbarn for en gitt periode")
data class ResultatPeriodeNettoBarnetilsyn(
  @Schema(description = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode = Periode(),
  @Schema(description = "Beregning resultat innhold liste") var resultatBeregningListe: List<ResultatBeregningNettoBarnetilsyn> = emptyList(),
  @Schema(description = "Beregning grunnlag innhold") var resultatGrunnlag: List<String> = emptyList()
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
    resultatDatoFraTil = Periode(resultatPeriode.periode),
    resultatBeregningListe = resultatPeriode.resultatListe.map { ResultatBeregningNettoBarnetilsyn(it) },
    resultatGrunnlag = resultatPeriode.grunnlagReferanseListe
  )
}

@Schema(description = "Resultatet av beregning av netto barnetilsyn")
data class ResultatBeregningNettoBarnetilsyn(
  @Schema(description = "Søknadsbarnets person-id") var resultatSoknadsbarnPersonId: Int = 0,
  @Schema(description = "Beløp netto barnetilsyn") var resultatBelop: BigDecimal = BigDecimal.ZERO
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
    resultatSoknadsbarnPersonId = resultatBeregning.soknadsbarnPersonId,
    resultatBelop = resultatBeregning.belop
  )
}
