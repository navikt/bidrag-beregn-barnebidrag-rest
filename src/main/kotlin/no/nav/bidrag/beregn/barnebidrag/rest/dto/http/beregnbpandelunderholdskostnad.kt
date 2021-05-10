package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.v3.oas.annotations.media.Schema
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnBPsAndelUnderholdskostnadResultatCore
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatGrunnlagCore
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatPeriodeCore
import java.math.BigDecimal

// Resultat
@Schema(description = "Totalresultatet av en beregning av BPs andel av underholdskostnad")
data class BeregnBPAndelUnderholdskostnadResultat(
    @Schema(description = "Periodisert liste over resultat av beregning av BPs andel av underholdskostnad") var resultatPeriodeListe: List<ResultatPeriodeBPAndelUnderholdskostnad> = emptyList()
) {

    constructor(beregnBPsAndelUnderholdskostnadResultat: BeregnBPsAndelUnderholdskostnadResultatCore) : this(
        resultatPeriodeListe = beregnBPsAndelUnderholdskostnadResultat.resultatPeriodeListe.map { ResultatPeriodeBPAndelUnderholdskostnad(it) }
    )
}

@Schema(description = "Resultatet av beregning av BPs andel av underholdskostnad for et søknadsbarn for en gitt periode")
data class ResultatPeriodeBPAndelUnderholdskostnad(
    @Schema(description = "Søknadsbarnets person-id") var resultatSoknadsbarnPersonId: Int = 0,
    @Schema(description = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode = Periode(),
    @Schema(description = "Beregning resultat innhold") var resultatBeregning: ResultatBeregningBPAndelUnderholdskostnad = ResultatBeregningBPAndelUnderholdskostnad(),
    @Schema(description = "Beregning grunnlag innhold") var resultatGrunnlag: ResultatGrunnlagBPAndelUnderholdskostnad = ResultatGrunnlagBPAndelUnderholdskostnad()
) {

    constructor(resultatPeriode: ResultatPeriodeCore) : this(
        resultatSoknadsbarnPersonId = resultatPeriode.soknadsbarnPersonId,
        resultatDatoFraTil = Periode(resultatPeriode.resultatDatoFraTil),
        resultatBeregning = ResultatBeregningBPAndelUnderholdskostnad(resultatPeriode.resultatBeregning),
        resultatGrunnlag = ResultatGrunnlagBPAndelUnderholdskostnad(resultatPeriode.resultatGrunnlag)
    )
}

@Schema(description = "Resultatet av beregning av BPs andel av underholdskostnad")
data class ResultatBeregningBPAndelUnderholdskostnad(
    @Schema(description = "Resultatandel prosent") var resultatAndelProsent: BigDecimal = BigDecimal.ZERO,
    @Schema(description = "Resultatandel beløp") var resultatAndelBelop: BigDecimal = BigDecimal.ZERO,
    @Schema(description = "Barnet er selvforsørget") var barnetErSelvforsorget: Boolean = false
) {

    constructor(resultatBeregning: ResultatBeregningCore) : this(
        resultatAndelProsent = resultatBeregning.resultatAndelProsent,
        resultatAndelBelop = resultatBeregning.resultatAndelBelop,
        barnetErSelvforsorget = resultatBeregning.barnetErSelvforsorget
    )
}

@Schema(description = "Grunnlaget for beregning av BPs andel av underholdskostnad")
data class ResultatGrunnlagBPAndelUnderholdskostnad(
    @Schema(description = "Underholdskostnad beløp") var underholdskostnadBelop: BigDecimal = BigDecimal.ZERO,
    @Schema(description = "Liste over bidragspliktiges inntekter") var inntektBPListe: List<Inntekt> = emptyList(),
    @Schema(description = "Liste over bidragsmottakers inntekter") var inntektBMListe: List<InntektBM> = emptyList(),
    @Schema(description = "Liste over søknadsbarnets inntekter") var inntektSBListe: List<Inntekt> = emptyList(),
    @Schema(description = "Liste over sjablonperioder") var sjablonListe: List<Sjablon> = emptyList()
) {

    constructor(resultatGrunnlag: ResultatGrunnlagCore) : this(
        underholdskostnadBelop = resultatGrunnlag.underholdskostnadBelop,
        inntektBPListe = resultatGrunnlag.inntektBPListe.map { Inntekt(it) },
        inntektBMListe = resultatGrunnlag.inntektBMListe.map { InntektBM(it) },
        inntektSBListe = resultatGrunnlag.inntektBBListe.map { Inntekt(it) },
        sjablonListe = resultatGrunnlag.sjablonListe.map { Sjablon(it) }
    )
}
