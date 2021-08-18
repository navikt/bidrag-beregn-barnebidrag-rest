package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.v3.oas.annotations.media.Schema
import no.nav.bidrag.beregn.barnebidrag.dto.BeregnetBarnebidragResultatCore
import no.nav.bidrag.beregn.barnebidrag.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.barnebidrag.dto.ResultatPeriodeCore
import java.math.BigDecimal

// Resultat
@Schema(description = "Resultatet av en beregning av barnebidrag")
data class BeregnBarnebidragResultat(
  @Schema(
    description = "Periodisert liste over resultat av beregning av barnebidrag"
  ) var resultatPeriodeListe: List<ResultatPeriodeBarnebidrag> = emptyList()
) {

  constructor(beregnBarnebidragResultat: BeregnetBarnebidragResultatCore) : this(
    resultatPeriodeListe = beregnBarnebidragResultat.resultatPeriodeListe.map { ResultatPeriodeBarnebidrag(it) }
  )
}

@Schema(description = "Resultatet av beregning av barnebidrag for et søknadsbarn for en gitt periode")
data class ResultatPeriodeBarnebidrag(
  @Schema(description = "Søknadsbarn") var barn: Int = 0,
  @Schema(description = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode = Periode(),
  @Schema(description = "Beregning resultat innhold liste") var resultatBeregningListe: ResultatBeregningBarnebidrag = ResultatBeregningBarnebidrag(),
  @Schema(description = "Beregning grunnlag innhold") var resultatGrunnlag: List<String> = emptyList()
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
    resultatDatoFraTil = Periode(resultatPeriode.periode),
    resultatBeregningListe = ResultatBeregningBarnebidrag(resultatPeriode.resultat),
    resultatGrunnlag = resultatPeriode.grunnlagReferanseListe
  )
}

@Schema(description = "Resultatet av beregning av barnebidrag")
data class ResultatBeregningBarnebidrag(
  @Schema(description = "Beløp barnebidrag") var resultatBelop: BigDecimal = BigDecimal.ZERO,
  @Schema(description = "Resultatkode barnebidrag") var resultatKode: String = ""
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
    resultatBelop = resultatBeregning.belop,
    resultatKode = resultatBeregning.kode
  )
}
