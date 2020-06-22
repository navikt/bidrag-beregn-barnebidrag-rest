package no.nav.bidrag.beregn.bidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate

// Grunnlag
@ApiModel(value = "Grunnlaget for en bidragsevnesberegning")
data class BeregnBidragsevneGrunnlag(
    @ApiModelProperty(value = "Beregn bidragsevne fra-dato") var beregnDatoFra: LocalDate? = null,
    @ApiModelProperty(value = "Beregn bidragsevne til-dato") var beregnDatoTil: LocalDate? = null,
    @ApiModelProperty(value = "Periodisert liste over bidragspliktiges inntekter") val inntektPeriodeListe: List<InntektPeriode>? = null,
    @ApiModelProperty(value = "Periodisert liste over bidragspliktiges skatteklasse") val skatteklassePeriodeListe: List<SkatteklassePeriode>? = null,
    @ApiModelProperty(value = "Periodisert liste over bidragspliktiges bostatus") val bostatusPeriodeListe: List<BostatusPeriode>? = null,
    @ApiModelProperty(
        value = "Periodisert liste over antall barn i bidragspliktiges hushold") val antallBarnIEgetHusholdPeriodeListe: List<AntallBarnIEgetHusholdPeriode>? = null,
    @ApiModelProperty(value = "Periodisert liste over bidragspliktiges særfradrag") val saerfradragPeriodeListe: List<SaerfradragPeriode>? = null
)

@ApiModel(value = "Bidragsmottakers inntekt")
data class InntektPeriode(
    @ApiModelProperty(value = "Bidragspliktiges inntekt fra-til-dato") var inntektDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Bidragspliktiges inntekt type") var inntektType: String? = null,
    @ApiModelProperty(value = "Bidragspliktiges inntekt beløp") var inntektBelop: Double? = null
)

@ApiModel(value = "Bidragspliktiges skatteklasse")
data class SkatteklassePeriode(
    @ApiModelProperty(value = "Bidragspliktiges skatteklasse fra-til-dato") var skatteklasseDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Bidragspliktiges skatteklasse") var skatteklasse: Int? = null
)

@ApiModel(value = "Bidragspliktiges bostatus")
data class BostatusPeriode(
    @ApiModelProperty(value = "Bidragspliktiges bostatus fra-til-dato") var bostatusDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Bidragspliktiges bostatuskode") var bostatusKode: String? = null
)

@ApiModel(value = "Antall barn i bidragspliktiges hushold")
data class AntallBarnIEgetHusholdPeriode(
    @ApiModelProperty(value = "Antall barn i bidragspliktiges hushold fra-til-dato") var antallBarnIEgetHusholdDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Antall barn i bidragspliktiges husholde") var antallBarn: Int? = null
)

@ApiModel(value = "Bidragspliktiges særfradrag")
data class SaerfradragPeriode(
    @ApiModelProperty(value = "Bidragspliktiges særfradrag fra-til-dato") var saerfradragDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Bidragspliktiges særfradrag kode") var saerfradragKode: String? = null
)


// Resultat
@ApiModel(value = "Resultatet av en bidragsevnesberegning")
data class BeregnBidragsevneResultat(
    @ApiModelProperty(
        value = "Periodisert liste over resultat av bidragsevnesberegning") var resultatPeriodeListe: List<ResultatPeriode> = emptyList()
)

@ApiModel(value = "Resultatet av en beregning for en gitt periode")
data class ResultatPeriode(
    @ApiModelProperty(value = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Beregning resultat innhold") var resultatBeregning: ResultatBeregning? = null,
    @ApiModelProperty(value = "Beregning grunnlag innhold") var resultatGrunnlag: ResultatGrunnlag? = null
)

@ApiModel(value = "Resultatet av en beregning")
data class ResultatBeregning(
    @ApiModelProperty(value = "Resultatevne") var resultatEvne: Double = 0.0
)

@ApiModel(value = "Grunnlaget for en beregning")
data class ResultatGrunnlag(
    @ApiModelProperty(value = "Liste over bidragspliktiges inntekter") var inntektListe: List<Inntekt> = emptyList(),
    @ApiModelProperty(value = "Bidragspliktiges skatteklasse") var skatteklasse: Int? = null,
    @ApiModelProperty(value = "Bidragspliktiges bostatuskode") var bostatusKode: String? = null,
    @ApiModelProperty(value = "Antall egne barn i bidragspliktiges husstand") var antallEgneBarnIHusstand: Int? = null,
    @ApiModelProperty(value = "Bidragspliktiges særfradragkode") var saerfradragKode: String? = null,
    @ApiModelProperty(value = "Liste over sjablonperioder") var sjablonListe: List<Sjablon> = emptyList()
)

@ApiModel(value = "Inntekttype og -beløp")
data class Inntekt(
    @ApiModelProperty(value = "Inntekt type") var inntektType: String? = null,
    @ApiModelProperty(value = "Inntekt beløp") var inntektBelop: Double? = null
)

@ApiModel(value = "Sjablonverdier")
data class Sjablon(
    @ApiModelProperty(value = "Sjablon navn") var sjablonNavn: String? = null,
    @ApiModelProperty(value = "Liste over sjablon nøkler") var sjablonNokkelListe: List<SjablonNokkel> = emptyList(),
    @ApiModelProperty(value = "Liste over sjablon innhold") var sjablonInnholdListe: List<SjablonInnhold> = emptyList()
)

@ApiModel(value = "Sjablonnøkkel")
data class SjablonNokkel(
    @ApiModelProperty(value = "Sjablonnøkkel navn") var sjablonNokkelNavn: String? = null,
    @ApiModelProperty(value = "Sjablonnøkkel verdi") var sjablonNokkelVerdi: String? = null
)

@ApiModel(value = "Sjabloninnhold")
data class SjablonInnhold(
    @ApiModelProperty(value = "Sjabloninnhold navn") var sjablonInnholdNavn: String? = null,
    @ApiModelProperty(value = "Sjabloninnhold verdi") var sjablonInnholdVerdi: Double? = null
)


// Felles
@ApiModel(value = "Periode (fra-til dato)")
data class Periode(
    @ApiModelProperty(value = "Fra-dato") var periodeDatoFra: LocalDate? = null,
    @ApiModelProperty(value = "Til-dato") var periodeDatoTil: LocalDate? = null
)
