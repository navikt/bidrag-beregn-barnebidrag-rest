package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.v3.oas.annotations.media.Schema
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnetSamvaersfradragResultatCore
import no.nav.bidrag.beregn.samvaersfradrag.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.samvaersfradrag.dto.ResultatPeriodeCore
import java.math.BigDecimal

// Resultat
@Schema(description = "Resultatet av en samværsfradragberegning for bidragspliktig")
data class BeregnBPSamvaersfradragResultat(
  @Schema(description = "Periodisert liste over resultat av beregning av samværsfradrag") var resultatPeriodeListe: List<ResultatPeriodeSamvaersfradrag> = emptyList()
) {

  constructor(beregnSamvaersfradragResultat: BeregnetSamvaersfradragResultatCore) : this(
    resultatPeriodeListe = beregnSamvaersfradragResultat.resultatPeriodeListe.map { ResultatPeriodeSamvaersfradrag(it) }
  )
}

@Schema(description = "Resultatet av beregning av samværsfradrag for et søknadsbarn for en gitt periode")
data class ResultatPeriodeSamvaersfradrag(
  @Schema(description = "Søknadsbarnets person-id") var resultatSoknadsbarnPersonId: Int = 0,
  @Schema(description = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode = Periode(),
  @Schema(description = "Beregning resultat innhold") var resultatBeregning: ResultatBeregningSamvaersfradrag = ResultatBeregningSamvaersfradrag(),
  @Schema(description = "Beregning grunnlag innhold") var resultatGrunnlag: List<String> = emptyList()
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
    resultatSoknadsbarnPersonId = resultatPeriode.soknadsbarnPersonId,
    resultatDatoFraTil = Periode(resultatPeriode.periode),
    resultatBeregning = ResultatBeregningSamvaersfradrag(resultatPeriode.resultat),
    resultatGrunnlag = resultatPeriode.grunnlagReferanseListe
  )
}

@Schema(description = "Resultatet av beregning av samværsfradrag")
data class ResultatBeregningSamvaersfradrag(
  @Schema(description = "Beløp samværsfradrag") var resultatBelop: BigDecimal = BigDecimal.ZERO
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
    resultatBelop = resultatBeregning.belop
  )
}
