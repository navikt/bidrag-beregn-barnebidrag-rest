package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.oas.annotations.media.Schema
import no.nav.bidrag.beregn.barnebidrag.dto.BeregnetBarnebidragResultatCore
import no.nav.bidrag.beregn.barnebidrag.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.barnebidrag.dto.ResultatPeriodeCore
import java.math.BigDecimal

// Barnebidrag
@Schema(description = "Resultatet av en barnebidragsberegning")
data class BeregnetTotalBarnebidragResultat(
  @Schema(description = "Periodisert liste over resultat av barnebidragsberegning") var beregnetBarnebidragPeriodeListe: List<ResultatPeriode> = emptyList(),
  @Schema(description = "Liste over grunnlag brukt i beregning") var grunnlagListe: List<ResultatGrunnlag> = emptyList()
) {

  constructor(beregnetBarnebidragResultat: BeregnetBarnebidragResultatCore, grunnlagListe: List<ResultatGrunnlag>) : this(
    beregnetBarnebidragPeriodeListe = beregnetBarnebidragResultat.resultatPeriodeListe.map { ResultatPeriode(it) },
    grunnlagListe = grunnlagListe
  )
}

@Schema(description = "Resultatet av en beregning for en gitt periode - barnebidrag")
data class ResultatPeriode(
  @Schema(description = "Søknadsbarn") var barn: Int = 0,
  @Schema(description = "Beregnet resultat periode") var periode: Periode = Periode(),
  @Schema(description = "Beregnet resultat innhold") var resultat: ResultatBeregning = ResultatBeregning(),
  @Schema(description = "Beregnet grunnlag innhold") var grunnlagReferanseListe: List<String> = emptyList()
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
    barn = resultatPeriode.soknadsbarnPersonId,
    periode = Periode(resultatPeriode.periode),
    resultat = ResultatBeregning(resultatPeriode.resultat),
    grunnlagReferanseListe = resultatPeriode.grunnlagReferanseListe
  )
}

@Schema(description = "Resultatet av en beregning")
data class ResultatBeregning(
  @Schema(description = "Resultat beløp") var belop: BigDecimal = BigDecimal.ZERO,
  @Schema(description = "Resultat kode") var kode: String = ""
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
    belop = resultatBeregning.belop,
    kode = resultatBeregning.kode
  )
}

@Schema(description = "Grunnlaget for en beregning")
data class ResultatGrunnlag(
  @Schema(description = "Referanse") val referanse: String = "",
  @Schema(description = "Type") val type: String = "",
  @Schema(description = "Innhold") val innhold: JsonNode = ObjectMapper().createObjectNode()
)

// Forholdsmessig fordeling
@Schema(description = "Resultatet av en beregning av forholdsmessig fordeling")
data class BeregnetForholdsmessigFordelingResultat(
  @Schema(description = "Periodisert liste over resultat av beregning av forholdsmessig fordeling") var beregnetForholdsmessigFordelingPeriodeListe: List<ResultatPeriodeFF> = emptyList(),
)

@Schema(description = "Resultatet av en beregning for en gitt periode - forholdsmessig fordeling")
data class ResultatPeriodeFF(
  @Schema(description = "Sak") var sakNr: Int = 0,
  @Schema(description = "Søknadsbarn") var barn: Int = 0,
  @Schema(description = "Beregnet resultat periode") var periode: Periode = Periode(),
  @Schema(description = "Beregnet resultat innhold") var resultat: ResultatBeregning = ResultatBeregning()
)
