package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.v3.oas.annotations.media.Schema
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadResultatCore
import no.nav.bidrag.beregn.underholdskostnad.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.underholdskostnad.dto.ResultatGrunnlagCore
import no.nav.bidrag.beregn.underholdskostnad.dto.ResultatPeriodeCore
import java.math.BigDecimal

// Grunnlag
@Schema(description = "Grunnlaget for en underholdskostnadsberegning for bidragsmottaker")
data class BeregnBMUnderholdskostnadGrunnlag(
    @Schema(description = "Periodisert liste over bidragsmottakers barnetilsyn med stønad") val barnetilsynMedStonadPeriodeListe: List<BarnetilsynMedStonadPeriode>? = null,
    @Schema(description = "Periodisert liste over bidragsmottakers utgifter til forpleining") val forpleiningUtgiftPeriodeListe: List<ForpleiningUtgiftPeriode>? = null
)

@Schema(description = "Bidragsmottakers barnetilsyn med stønad")
data class BarnetilsynMedStonadPeriode(
    @Schema(description = "Bidragsmottakers barnetilsyn med stønad fra-til-dato") var barnetilsynMedStonadDatoFraTil: Periode? = null,
    @Schema(description = "Søknadsbarnets person-id") var barnetilsynMedStonadSoknadsbarnPersonId: Int? = null,
    @Schema(description = "Bidragsmottakers barnetilsyn med stønad tilsyn-type") var barnetilsynMedStonadTilsynType: String? = null,
    @Schema(description = "Bidragsmottakers barnetilsyn med stønad stønad-type") var barnetilsynMedStonadStonadType: String? = null
) {

    fun validerBarnetilsynMedStonad() {
        if (barnetilsynMedStonadDatoFraTil != null) barnetilsynMedStonadDatoFraTil!!.valider("barnetilsynMedStonad")
        else throw UgyldigInputException("barnetilsynMedStonadDatoFraTil kan ikke være null")

        if (barnetilsynMedStonadSoknadsbarnPersonId == null) throw UgyldigInputException("barnetilsynMedStonadSoknadsbarnPersonId kan ikke være null")
        if (barnetilsynMedStonadTilsynType == null) throw UgyldigInputException("barnetilsynMedStonadTilsynType kan ikke være null")
        if (barnetilsynMedStonadStonadType == null) throw UgyldigInputException("barnetilsynMedStonadStonadType kan ikke være null")
    }
}

@Schema(description = "Bidragsmottakers utgifter til forpleining")
data class ForpleiningUtgiftPeriode(
    @Schema(description = "Bidragsmottakers utgifter til forpleining fra-til-dato") var forpleiningUtgiftDatoFraTil: Periode? = null,
    @Schema(description = "Søknadsbarnets person-id") var forpleiningUtgiftSoknadsbarnPersonId: Int? = null,
    @Schema(description = "Bidragsmottakers utgifter til forpleining beløp") var forpleiningUtgiftBelop: BigDecimal? = null
) {

    fun validerForpleiningUtgift() {
        if (forpleiningUtgiftDatoFraTil != null) forpleiningUtgiftDatoFraTil!!.valider("forpleiningUtgift")
        else throw UgyldigInputException("forpleiningUtgiftDatoFraTil kan ikke være null")

        if (forpleiningUtgiftSoknadsbarnPersonId == null) throw UgyldigInputException("forpleiningUtgiftSoknadsbarnPersonId kan ikke være null")
        if (forpleiningUtgiftBelop == null) throw UgyldigInputException("forpleiningUtgiftBelop kan ikke være null")
    }
}

// Resultat
@Schema(description = "Resultatet av en underholdskostnadsberegning for bidragsmottaker")
data class BeregnBMUnderholdskostnadResultat(
    @Schema(description = "Periodisert liste over resultat av beregning av underholdskostnad") var resultatPeriodeListe: List<ResultatPeriodeUnderholdskostnad> = emptyList()
) {

    constructor(beregnUnderholdskostnadResultat: BeregnUnderholdskostnadResultatCore) : this(
        resultatPeriodeListe = beregnUnderholdskostnadResultat.resultatPeriodeListe.map { ResultatPeriodeUnderholdskostnad(it) }
    )
}

@Schema(description = "Resultatet av beregning av underholdskostnad for et søknadsbarn for en gitt periode")
data class ResultatPeriodeUnderholdskostnad(
    @Schema(description = "Søknadsbarnets person-id") var resultatSoknadsbarnPersonId: Int = 0,
    @Schema(description = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode = Periode(),
    @Schema(description = "Beregning resultat innhold") var resultatBeregning: ResultatBeregningUnderholdskostnad = ResultatBeregningUnderholdskostnad(),
    @Schema(description = "Beregning grunnlag innhold") var resultatGrunnlag: ResultatGrunnlagUnderholdskostnad = ResultatGrunnlagUnderholdskostnad()
) {

    constructor(resultatPeriode: ResultatPeriodeCore) : this(
        resultatSoknadsbarnPersonId = resultatPeriode.soknadsbarnPersonId,
        resultatDatoFraTil = Periode(resultatPeriode.resultatDatoFraTil),
        resultatBeregning = ResultatBeregningUnderholdskostnad(resultatPeriode.resultatBeregning),
        resultatGrunnlag = ResultatGrunnlagUnderholdskostnad(resultatPeriode.resultatGrunnlag)
    )
}

@Schema(description = "Resultatet av beregning av underholdskostnad")
data class ResultatBeregningUnderholdskostnad(
    @Schema(description = "Beløp underholdskostnad") var resultatBelop: BigDecimal = BigDecimal.ZERO
) {

    constructor(resultatBeregning: ResultatBeregningCore) : this(
        resultatBelop = resultatBeregning.resultatBelopUnderholdskostnad
    )
}

@Schema(description = "Grunnlaget for beregning av underholdskostnad")
data class ResultatGrunnlagUnderholdskostnad(
    @Schema(description = "Søknadsbarnets alder") var soknadsbarnAlder: Int = 0,
    @Schema(description = "Barnetilsyn med stønad - tilsyn-type") var barnetilsynMedStonadTilsynType: String = "",
    @Schema(description = "Barnetilsyn med stønad - stønad-type") var barnetilsynMedStonadStonadType: String = "",
    @Schema(description = "Faktisk utgift barnetilsyn - netto-beløp") var nettoBarnetilsynBelop: BigDecimal = BigDecimal.ZERO,
    @Schema(description = "Utgift forpleining - beløp") var forpleiningUtgiftBelop: BigDecimal = BigDecimal.ZERO,
    @Schema(description = "Liste over sjablonperioder") var sjablonListe: List<Sjablon> = emptyList()
) {

    constructor(resultatGrunnlag: ResultatGrunnlagCore) : this(
        soknadsbarnAlder = resultatGrunnlag.soknadBarnAlder,
        barnetilsynMedStonadTilsynType = resultatGrunnlag.barnetilsynMedStonadTilsynType,
        barnetilsynMedStonadStonadType = resultatGrunnlag.barnetilsynMedStonadStonadType,
        nettoBarnetilsynBelop = resultatGrunnlag.nettoBarnetilsynBelop,
        forpleiningUtgiftBelop = resultatGrunnlag.forpleiningUtgiftBelop,
        sjablonListe = resultatGrunnlag.sjablonListe.map { Sjablon(it) }
    )
}
