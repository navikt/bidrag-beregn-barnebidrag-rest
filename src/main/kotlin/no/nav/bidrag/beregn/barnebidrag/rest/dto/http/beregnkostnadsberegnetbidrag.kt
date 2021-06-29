package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.v3.oas.annotations.media.Schema
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.BeregnetKostnadsberegnetBidragResultatCore
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.ResultatPeriodeCore
import java.math.BigDecimal

// Resultat
@Schema(description = "Resultatet av beregning av kostnadsberegnet bidrag for bidragspliktig")
data class BeregnBPKostnadsberegnetBidragResultat(
  @Schema(description = "Periodisert liste over resultat av beregning av kostnadsberegnet bidrag")
  var resultatPeriodeListe: List<ResultatPeriodeKostnadsberegnetBidrag> = emptyList()
) {

  constructor(beregnKostnadsberegnetBidragResultat: BeregnetKostnadsberegnetBidragResultatCore) : this(
    resultatPeriodeListe = beregnKostnadsberegnetBidragResultat.resultatPeriodeListe.map { ResultatPeriodeKostnadsberegnetBidrag(it) }
  )
}

@Schema(description = "Resultatet av beregning av kostnadsberegnet bidrag for et søknadsbarn for en gitt periode")
data class ResultatPeriodeKostnadsberegnetBidrag(
  @Schema(description = "Søknadsbarnets person-id") var resultatSoknadsbarnPersonId: Int = 0,
  @Schema(description = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode = Periode(),
  @Schema(description = "Beregning resultat innhold") var resultatBeregning: ResultatBeregningKostnadsberegnetBidrag = ResultatBeregningKostnadsberegnetBidrag(),
  @Schema(description = "Beregning grunnlag innhold") var resultatGrunnlag: List<String> = emptyList()
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
    resultatSoknadsbarnPersonId = resultatPeriode.soknadsbarnPersonId,
    resultatDatoFraTil = Periode(resultatPeriode.periode),
    resultatBeregning = ResultatBeregningKostnadsberegnetBidrag(resultatPeriode.resultat),
    resultatGrunnlag = resultatPeriode.grunnlagReferanseListe
  )
}

@Schema(description = "Resultatet av beregning av kostnadsberegnet bidrag")
data class ResultatBeregningKostnadsberegnetBidrag(
  @Schema(description = "Beløp kostnadsberegnet bidrag") var resultatBelop: BigDecimal = BigDecimal.ZERO
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
    resultatBelop = resultatBeregning.belop
  )
}
