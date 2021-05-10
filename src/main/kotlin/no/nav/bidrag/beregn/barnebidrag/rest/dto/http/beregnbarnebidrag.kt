package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.v3.oas.annotations.media.Schema
import no.nav.bidrag.beregn.barnebidrag.dto.AndreLopendeBidragCore
import no.nav.bidrag.beregn.barnebidrag.dto.AndreLopendeBidragPeriodeCore
import no.nav.bidrag.beregn.barnebidrag.dto.BPsAndelUnderholdskostnadCore
import no.nav.bidrag.beregn.barnebidrag.dto.BarnetilleggCore
import no.nav.bidrag.beregn.barnebidrag.dto.BarnetilleggForsvaretPeriodeCore
import no.nav.bidrag.beregn.barnebidrag.dto.BarnetilleggPeriodeCore
import no.nav.bidrag.beregn.barnebidrag.dto.BeregnBarnebidragResultatCore
import no.nav.bidrag.beregn.barnebidrag.dto.BidragsevneCore
import no.nav.bidrag.beregn.barnebidrag.dto.DeltBostedPeriodeCore
import no.nav.bidrag.beregn.barnebidrag.dto.GrunnlagBeregningPerBarnCore
import no.nav.bidrag.beregn.barnebidrag.dto.GrunnlagBeregningPeriodisertCore
import no.nav.bidrag.beregn.barnebidrag.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.barnebidrag.dto.ResultatPeriodeCore
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import java.math.BigDecimal

// Grunnlag
@Schema(description = "Grunnlaget for en beregning av barnebidrag")
data class BeregnBarnebidragGrunnlag(
    @Schema(description = "Periodisert liste over bidragspliktiges barnetillegg") val barnetilleggBPPeriodeListe: List<BarnetilleggPeriode>? = null,
    @Schema(description = "Periodisert liste over bidragsmottakers barnetillegg") val barnetilleggBMPeriodeListe: List<BarnetilleggPeriode>? = null,
    @Schema(description = "Periodisert liste over bidragspliktiges barnetillegg fra forsvaret") val barnetilleggForsvaretBPPeriodeListe: List<BarnetilleggForsvaretBPPeriode>? = null,
    @Schema(description = "Periodisert liste over delt bosted for bidragspliktig") val deltBostedBPPeriodeListe: List<DeltBostedBPPeriode>? = null,
    @Schema(description = "Periodisert liste over andre løpende bidrag for bidragspliktig") val andreLopendeBidragBPPeriodeListe: List<AndreLopendeBidragBPPeriode>? = null
)

@Schema(description = "Barnetillegg BM og BP")
data class BarnetilleggPeriode(
    @Schema(description = "Barnetillegg fra-til-dato") var barnetilleggDatoFraTil: Periode? = null,
    @Schema(description = "Søknadsbarnets person-id") var barnetilleggSoknadsbarnPersonId: Int? = null,
    @Schema(description = "Barnetillegg bruttobeløp") var barnetilleggBruttoBelop: BigDecimal? = null,
    @Schema(description = "Barnetillegg skatt prosent") var barnetilleggSkattProsent: BigDecimal? = null
) {

    fun tilCore(dataElement: String) = BarnetilleggPeriodeCore(
        soknadsbarnPersonId = if (barnetilleggSoknadsbarnPersonId != null) barnetilleggSoknadsbarnPersonId!! else throw UgyldigInputException(
            "$dataElement barnetilleggSoknadsbarnPersonId kan ikke være null"
        ),
        barnetilleggDatoFraTil = if (barnetilleggDatoFraTil != null) barnetilleggDatoFraTil!!.tilCore(
            "$dataElement barnetillegg"
        ) else throw UgyldigInputException("$dataElement barnetilleggDatoFraTil kan ikke være null"),
        barnetilleggBelop = if (barnetilleggBruttoBelop != null) barnetilleggBruttoBelop!! else throw UgyldigInputException(
            "$dataElement barnetilleggBruttoBelop kan ikke være null"
        ),
        barnetilleggSkattProsent = if (barnetilleggSkattProsent != null) barnetilleggSkattProsent!! else throw UgyldigInputException(
            "$dataElement barnetilleggSkattProsent kan ikke være null"
        )
    )
}

@Schema(description = "Bidragspliktiges barnetillegg fra forsvaret")
data class BarnetilleggForsvaretBPPeriode(
    @Schema(description = "Barnetillegg forsvaret fra-til-dato") var barnetilleggForsvaretDatoFraTil: Periode? = null,
    @Schema(description = "Barnetillegg forsvaret i periode true/false") var barnetilleggForsvaretIPeriode: Boolean? = null
) {

    fun tilCore() = BarnetilleggForsvaretPeriodeCore(
        barnetilleggForsvaretDatoFraTil = if (barnetilleggForsvaretDatoFraTil != null) barnetilleggForsvaretDatoFraTil!!.tilCore(
            "barnetilleggForsvaret"
        ) else throw UgyldigInputException("barnetilleggForsvaretDatoFraTil kan ikke være null"),
        barnetilleggForsvaretIPeriode = if (barnetilleggForsvaretIPeriode != null) barnetilleggForsvaretIPeriode!! else throw UgyldigInputException(
            "barnetilleggForsvaretIPeriode kan ikke være null"
        )
    )
}

@Schema(description = "Delt bosted for bidragspliktig")
data class DeltBostedBPPeriode(
    @Schema(description = "Delt bosted fra-til-dato") var deltBostedDatoFraTil: Periode? = null,
    @Schema(description = "Søknadsbarnets person-id") var deltBostedSoknadsbarnPersonId: Int? = null,
    @Schema(description = "Delt bosted i periode true/false") var deltBostedIPeriode: Boolean? = null
) {

    fun tilCore() = DeltBostedPeriodeCore(
        soknadsbarnPersonId = if (deltBostedSoknadsbarnPersonId != null) deltBostedSoknadsbarnPersonId!! else throw UgyldigInputException(
            "deltBostedSoknadsbarnPersonId kan ikke være null"
        ),
        deltBostedDatoFraTil = if (deltBostedDatoFraTil != null) deltBostedDatoFraTil!!.tilCore(
            "deltBosted"
        ) else throw UgyldigInputException("deltBostedDatoFraTil kan ikke være null"),
        deltBostedIPeriode = if (deltBostedIPeriode != null) deltBostedIPeriode!! else throw UgyldigInputException(
            "deltBostedIPeriode kan ikke være null"
        )
    )
}

@Schema(description = "Andre løpende bidrag for bidragspliktig")
data class AndreLopendeBidragBPPeriode(
    @Schema(description = "Andre løpende bidrag - fra-til-dato") var andreLopendeBidragDatoFraTil: Periode? = null,
    @Schema(description = "Andre løpende bidrag - barn person-id") var andreLopendeBidragBarnPersonId: Int? = null,
    @Schema(description = "Andre løpende bidrag - bidrag beløp") var andreLopendeBidragBidragBelop: BigDecimal? = null,
    @Schema(description = "Andre løpende bidrag - samværsfradrag beløp") var andreLopendeBidragSamvaersfradragBelop: BigDecimal? = null
) {

    fun tilCore() = AndreLopendeBidragPeriodeCore(
        periodeDatoFraTil = if (andreLopendeBidragDatoFraTil != null) andreLopendeBidragDatoFraTil!!.tilCore("andreLopendeBidrag")
        else throw UgyldigInputException("andreLopendeBidragDatoFraTil kan ikke være null"),
        barnPersonId = if (andreLopendeBidragBarnPersonId != null) andreLopendeBidragBarnPersonId!! else throw UgyldigInputException(
            "andreLopendeBidragBarnPersonId kan ikke være null"
        ),
        bidragBelop = if (andreLopendeBidragBidragBelop != null) andreLopendeBidragBidragBelop!! else throw UgyldigInputException(
            "andreLopendeBidragBidragBelop kan ikke være null"
        ),
        samvaersfradragBelop = if (andreLopendeBidragSamvaersfradragBelop != null) andreLopendeBidragSamvaersfradragBelop!!
        else throw UgyldigInputException("andreLopendeBidragSamvaersfradragBelop kan ikke være null")
    )
}

// Resultat
@Schema(description = "Resultatet av en beregning av barnebidrag")
data class BeregnBarnebidragResultat(
    @Schema(
        description = "Periodisert liste over resultat av beregning av barnebidrag"
    ) var resultatPeriodeListe: List<ResultatPeriodeBarnebidrag> = emptyList()
) {

    constructor(beregnBarnebidragResultat: BeregnBarnebidragResultatCore) : this(
        resultatPeriodeListe = beregnBarnebidragResultat.resultatPeriodeListe.map { ResultatPeriodeBarnebidrag(it) }
    )
}

@Schema(description = "Resultatet av beregning av barnebidrag for et søknadsbarn for en gitt periode")
data class ResultatPeriodeBarnebidrag(
    @Schema(description = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode = Periode(),
    @Schema(description = "Beregning resultat innhold liste") var resultatBeregningListe: List<ResultatBeregningBarnebidrag> = emptyList(),
    @Schema(description = "Beregning grunnlag innhold") var resultatGrunnlag: ResultatGrunnlagBarnebidrag = ResultatGrunnlagBarnebidrag()
) {

    constructor(resultatPeriode: ResultatPeriodeCore) : this(
        resultatDatoFraTil = Periode(resultatPeriode.resultatDatoFraTil),
        resultatBeregningListe = resultatPeriode.resultatBeregningListe.map { ResultatBeregningBarnebidrag(it) },
        resultatGrunnlag = ResultatGrunnlagBarnebidrag(resultatPeriode.resultatGrunnlag)
    )
}

@Schema(description = "Resultatet av beregning av barnebidrag")
data class ResultatBeregningBarnebidrag(
    @Schema(description = "Søknadsbarnets person-id") var resultatSoknadsbarnPersonId: Int = 0,
    @Schema(description = "Beløp barnebidrag") var resultatBelop: BigDecimal = BigDecimal.ZERO,
    @Schema(description = "Resultatkode barnebidrag") var resultatKode: String = ""
) {

    constructor(resultatBeregning: ResultatBeregningCore) : this(
        resultatSoknadsbarnPersonId = resultatBeregning.soknadsbarnPersonId,
        resultatBelop = resultatBeregning.resultatBarnebidragBelop,
        resultatKode = resultatBeregning.resultatkode
    )
}

@Schema(description = "Grunnlaget for beregning av barnebidrag")
data class ResultatGrunnlagBarnebidrag(
    @Schema(description = "Bidragsevne") var bidragsevne: BidragsevneGrunnlag = BidragsevneGrunnlag(),
    @Schema(description = "Grunnlag per barn liste") var resultatGrunnlagPerBarnListe: List<ResultatGrunnlagPerBarn> = emptyList(),
    @Schema(description = "Barnetillegg forsvaret") var barnetilleggForsvaret: Boolean = false,
    @Schema(description = "Andre løpende bidrag liste") var andreLopendeBidragListe: List<AndreLopendeBidrag> = emptyList(),
    @Schema(description = "Liste over sjablonperioder") var sjablonListe: List<Sjablon> = emptyList()
) {

    constructor(resultatGrunnlag: GrunnlagBeregningPeriodisertCore) : this(
        bidragsevne = BidragsevneGrunnlag(resultatGrunnlag.bidragsevne),
        resultatGrunnlagPerBarnListe = resultatGrunnlag.grunnlagPerBarnListe.map { ResultatGrunnlagPerBarn(it) },
        barnetilleggForsvaret = resultatGrunnlag.barnetilleggForsvaret,
        andreLopendeBidragListe = resultatGrunnlag.andreLopendeBidragListe.map { AndreLopendeBidrag(it) },
        sjablonListe = resultatGrunnlag.sjablonListe.map { Sjablon(it) }
    )
}

@Schema(description = "Grunnlaget for beregning - bidragsevne")
data class BidragsevneGrunnlag(
    @Schema(description = "Bidragsevne beløp") var bidragsevneBelop: BigDecimal = BigDecimal.ZERO,
    @Schema(description = "Bidragsevne 25 prosent inntekt") var bidragsevne25ProsentInntekt: BigDecimal = BigDecimal.ZERO
) {

    constructor(bidragsevne: BidragsevneCore) : this(
        bidragsevneBelop = bidragsevne.bidragsevneBelop,
        bidragsevne25ProsentInntekt = bidragsevne.tjuefemProsentInntekt
    )
}

@Schema(description = "Grunnlaget for beregning av barnebidrag per barn")
data class ResultatGrunnlagPerBarn(
    @Schema(description = "Søknadsbarnets person-id") var resultatSoknadsbarnPersonId: Int = 0,
    @Schema(description = "Samværsfradrag beløp") var samvaersfradragBelop: BigDecimal = BigDecimal.ZERO,
    @Schema(description = "BPs andel underholdskostnad") var bpAndelUnderholdskostnad: BPAndelUnderholdskostnad = BPAndelUnderholdskostnad(),
    @Schema(description = "Barnetillegg bidragspliktig") var barnetilleggBP: Barnetillegg = Barnetillegg(),
    @Schema(description = "Barnetillegg bidragsmottaker") var barnetilleggBM: Barnetillegg = Barnetillegg(),
    @Schema(description = "Delt bosted") var deltBosted: Boolean = false
) {

    constructor(resultatGrunnlagPerBarn: GrunnlagBeregningPerBarnCore) : this(
        resultatSoknadsbarnPersonId = resultatGrunnlagPerBarn.soknadsbarnPersonId,
        samvaersfradragBelop = resultatGrunnlagPerBarn.samvaersfradrag,
        bpAndelUnderholdskostnad = BPAndelUnderholdskostnad(resultatGrunnlagPerBarn.bPsAndelUnderholdskostnad),
        barnetilleggBP = Barnetillegg(resultatGrunnlagPerBarn.barnetilleggBP),
        barnetilleggBM = Barnetillegg(resultatGrunnlagPerBarn.barnetilleggBM),
        deltBosted = resultatGrunnlagPerBarn.deltBosted
    )
}

@Schema(description = "Grunnlaget for beregning - BPs andel underholdskostnad")
data class BPAndelUnderholdskostnad(
    @Schema(description = "Resultatandel prosent") var resultatAndelProsent: BigDecimal = BigDecimal.ZERO,
    @Schema(description = "Resultatandel beløp") var resultatAndelBelop: BigDecimal = BigDecimal.ZERO,
    @Schema(description = "Barnet er selvforsørget") var barnetErSelvforsorget: Boolean = false
) {

    constructor(bpAndelUnderholdskostnad: BPsAndelUnderholdskostnadCore) : this(
        resultatAndelProsent = bpAndelUnderholdskostnad.bPsAndelUnderholdskostnadProsent,
        resultatAndelBelop = bpAndelUnderholdskostnad.bPsAndelUnderholdskostnadBelop,
        barnetErSelvforsorget = bpAndelUnderholdskostnad.barnetErSelvforsorget
    )
}

@Schema(description = "Grunnlaget for beregning - barnetillegg")
data class Barnetillegg(
    @Schema(description = "Barnetillegg beløp") var barnetilleggBelop: BigDecimal = BigDecimal.ZERO,
    @Schema(description = "Barnetillegg skatt prosent") var barnetilleggSkattProsent: BigDecimal = BigDecimal.ZERO
) {

    constructor(barnetillegg: BarnetilleggCore) : this(
        barnetilleggBelop = barnetillegg.barnetilleggBelop,
        barnetilleggSkattProsent = barnetillegg.barnetilleggSkattProsent
    )
}

@Schema(description = "Grunnlaget for beregning - andre løpende bidrag")
data class AndreLopendeBidrag(
    @Schema(description = "Andre løpende bidrag - barn person-id") var andreLopendeBidragBarnPersonId: Int = 0,
    @Schema(description = "Andre løpende bidrag - bidrag beløp") var andreLopendeBidragBidragBelop: BigDecimal = BigDecimal.ZERO,
    @Schema(description = "Andre løpende bidrag - samværsfradrag beløp") var andreLopendeBidragSamvaersfradragBelop: BigDecimal = BigDecimal.ZERO
) {

    constructor(andreLopendBidrag: AndreLopendeBidragCore) : this(
        andreLopendeBidragBarnPersonId = andreLopendBidrag.barnPersonId,
        andreLopendeBidragBidragBelop = andreLopendBidrag.bidragBelop,
        andreLopendeBidragSamvaersfradragBelop = andreLopendBidrag.samvaersfradragBelop
    )
}
