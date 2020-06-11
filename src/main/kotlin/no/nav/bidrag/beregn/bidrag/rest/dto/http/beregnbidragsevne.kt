package no.nav.bidrag.beregn.bidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate

// Grunnlag
@ApiModel(value = "Grunnlaget for en bidragsevnesberegning")
data class BeregnBidragsevneGrunnlag(
    @ApiModelProperty(value = "Beregn bidragsevne fra-dato") var beregnDatoFra: LocalDate? = null,
    @ApiModelProperty(value = "Beregn bidragsevne til-dato") var beregnDatoTil: LocalDate? = null,
    @ApiModelProperty(value = "Periodisert liste over bidragmottakers inntekter") val inntektPeriodeListe: List<InntektPeriode>? = null,
    @ApiModelProperty(value = "Periodisert liste over søknadsbarnets bostatus") val bostatusPeriodeListe: List<BostatusPeriode>? = null,
    @ApiModelProperty(
        value = "Periodisert liste over antall barn i eget hushold") val antallBarnIEgetHusholdPeriodeListe: List<AntallBarnIEgetHusholdPeriode>? = null,
    @ApiModelProperty(value = "Periodisert liste over særfradrag") val saerfradragPeriodeListe: List<SaerfradragPeriode>? = null
)

@ApiModel(value = "Bidragsmottakers inntekt")
data class InntektPeriode(
    @ApiModelProperty(value = "Bidragsmottakers inntekt fra-til-dato") var inntektDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Bidragsmottakers inntekt type") var inntektType: String? = null,
    @ApiModelProperty(value = "Bidragsmottakers skatteklasse") var skatteklasse: Int? = null,
    @ApiModelProperty(value = "Bidragsmottakers inntekt beløp") var inntektBelop: Double? = null
)

@ApiModel(value = "Søknadsbarnets bostatus")
data class BostatusPeriode(
    @ApiModelProperty(value = "Søknadsbarnets bostatus fra-til-dato") var bostatusDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Søknadsbarnets bostatuskode") var bostatusKode: String? = null
)

@ApiModel(value = "Antall barn i eget hushold")
data class AntallBarnIEgetHusholdPeriode(
    @ApiModelProperty(value = "Antall barn i eget hushold fra-til-dato") var antallBarnIEgetHusholdDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Antall barn i eget husholde") var antallBarn: Int? = null
)

@ApiModel(value = "Særfradrag")
data class SaerfradragPeriode(
    @ApiModelProperty(value = "Særfradrag fra-til-dato") var saerfradragDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Særfradrag kode") var saerfradragKode: String? = null
)


// Resultat
@ApiModel(value = "Resultatet av en bidragsevnesberegning")
data class BeregnBidragsevneResultat(
    @ApiModelProperty(
        value = "Periodisert liste over resultat av bidragsevnesberegning") var resultatPeriodeListe: List<ResultatPeriode> = emptyList()
)

@ApiModel(value = "Resultatet av en beregning for en gitt periode")
data class ResultatPeriode(
    @ApiModelProperty(value = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode,
    @ApiModelProperty(value = "Beregning resultat innhold") var resultatBeregning: ResultatBeregning,
    @ApiModelProperty(value = "Beregning grunnlag innhold") var resultatGrunnlag: ResultatGrunnlag
)

@ApiModel(value = "Resultatet av en beregning")
data class ResultatBeregning(
    @ApiModelProperty(value = "Resultatevne") var resultatEvne: Double = 0.0
)

@ApiModel(value = "Grunnlaget for en beregning")
data class ResultatGrunnlag(
    @ApiModelProperty(value = "Liste over bidragsmottakers inntekter") var inntektListe: List<Inntekt> = emptyList(),
    @ApiModelProperty(value = "Skatteklasse") var skatteklasse: Int,
    @ApiModelProperty(value = "Bostatuskode") var bostatusKode: String,
    @ApiModelProperty(value = "Antall egne barn i husstand") var antallEgneBarnIHusstand: Int,
    @ApiModelProperty(value = "Særfradragkode") var saerfradragKode: String,
    @ApiModelProperty(value = "Liste over sjablonperioder") var sjablonListe: List<Sjablon> = emptyList()
)

@ApiModel(value = "Inntekttype og -beløp")
data class Inntekt(
    @ApiModelProperty(value = "Inntekt type") var inntektType: String,
    @ApiModelProperty(value = "Inntekt beløp") var inntektBelop: Double
)

@ApiModel(value = "Sjablonverdier")
data class Sjablon(
    @ApiModelProperty(value = "Sjablon navn") var sjablonNavn: String,
    @ApiModelProperty(value = "Sjablon verdi 1") var sjablonVerdi1: Double,
    @ApiModelProperty(value = "Sjablon verdi 2") var sjablonVerdi2: Double? = null
)


// Felles
@ApiModel(value = "Periode (fra-til dato)")
data class Periode(
    @ApiModelProperty(value = "Fra-dato") var periodeDatoFra: LocalDate? = null,
    @ApiModelProperty(value = "Til-dato") var periodeDatoTil: LocalDate? = null
)

