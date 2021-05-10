package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.v3.oas.annotations.media.Schema
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnSamvaersfradragResultatCore
import no.nav.bidrag.beregn.samvaersfradrag.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.samvaersfradrag.dto.ResultatGrunnlagCore
import no.nav.bidrag.beregn.samvaersfradrag.dto.ResultatPeriodeCore
import java.math.BigDecimal

// Grunnlag
@Schema(description = "Grunnlaget for en samværsfradragberegning for bidragspliktig")
data class BeregnBPSamvaersfradragGrunnlag(
    @Schema(description = "Periodisert liste over bidragspliktiges samværsklasser") val samvaersklassePeriodeListe: List<SamvaersklassePeriode>? = null
)

@Schema(description = "Bidragspliktiges samværsklasse")
data class SamvaersklassePeriode(
    @Schema(description = "Bidragspliktiges samværsklasse fra-til-dato") var samvaersklasseDatoFraTil: Periode? = null,
    @Schema(description = "Søknadsbarnets person-id") var samvaersklasseSoknadsbarnPersonId: Int? = null,
    @Schema(description = "Bidragspliktiges samværsklasse id") var samvaersklasseId: String? = null
) {

    fun validerSamvaersklasse() {
        if (samvaersklasseDatoFraTil != null) samvaersklasseDatoFraTil!!.valider("samvaersklasse") else throw UgyldigInputException(
            "samvaersklasseDatoFraTil kan ikke være null"
        )

        if (samvaersklasseSoknadsbarnPersonId == null) throw UgyldigInputException("samvaersklasseSoknadsbarnPersonId kan ikke være null")
        if (samvaersklasseId == null) throw UgyldigInputException("samvaersklasseId kan ikke være null")
    }
}


// Resultat
@Schema(description = "Resultatet av en samværsfradragberegning for bidragspliktig")
data class BeregnBPSamvaersfradragResultat(
    @Schema(description = "Periodisert liste over resultat av beregning av samværsfradrag") var resultatPeriodeListe: List<ResultatPeriodeSamvaersfradrag> = emptyList()
) {

    constructor(beregnSamvaersfradragResultat: BeregnSamvaersfradragResultatCore) : this(
        resultatPeriodeListe = beregnSamvaersfradragResultat.resultatPeriodeListe.map { ResultatPeriodeSamvaersfradrag(it) }
    )
}

@Schema(description = "Resultatet av beregning av samværsfradrag for et søknadsbarn for en gitt periode")
data class ResultatPeriodeSamvaersfradrag(
    @Schema(description = "Søknadsbarnets person-id") var resultatSoknadsbarnPersonId: Int = 0,
    @Schema(description = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode = Periode(),
    @Schema(description = "Beregning resultat innhold") var resultatBeregning: ResultatBeregningSamvaersfradrag = ResultatBeregningSamvaersfradrag(),
    @Schema(description = "Beregning grunnlag innhold") var resultatGrunnlag: ResultatGrunnlagSamvaersfradrag = ResultatGrunnlagSamvaersfradrag()
) {

    constructor(resultatPeriode: ResultatPeriodeCore) : this(
        resultatSoknadsbarnPersonId = resultatPeriode.soknadsbarnPersonId,
        resultatDatoFraTil = Periode(resultatPeriode.resultatDatoFraTil),
        resultatBeregning = ResultatBeregningSamvaersfradrag(resultatPeriode.resultatBeregning),
        resultatGrunnlag = ResultatGrunnlagSamvaersfradrag(resultatPeriode.resultatGrunnlag)
    )
}

@Schema(description = "Resultatet av beregning av samværsfradrag")
data class ResultatBeregningSamvaersfradrag(
    @Schema(description = "Beløp samværsfradrag") var resultatBelop: BigDecimal = BigDecimal.ZERO
) {

    constructor(resultatBeregning: ResultatBeregningCore) : this(
        resultatBelop = resultatBeregning.resultatSamvaersfradragBelop
    )
}

@Schema(description = "Grunnlaget for beregning av samværsfradrag")
data class ResultatGrunnlagSamvaersfradrag(
    @Schema(description = "Søknadsbarnets alder") var soknadsbarnAlder: Int = 0,
    @Schema(description = "Samværsklasse Id") var samvaersklasseId: String = "",
    @Schema(description = "Liste over sjablonperioder") var sjablonListe: List<Sjablon> = emptyList()
) {

    constructor(resultatGrunnlag: ResultatGrunnlagCore) : this(
        soknadsbarnAlder = resultatGrunnlag.soknadBarnAlder,
        samvaersklasseId = resultatGrunnlag.samvaersklasse,
        sjablonListe = resultatGrunnlag.sjablonListe.map { Sjablon(it) }
    )
}
