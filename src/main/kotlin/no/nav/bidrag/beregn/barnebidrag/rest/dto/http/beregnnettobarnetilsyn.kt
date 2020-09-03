package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnNettoBarnetilsynResultatCore
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.FaktiskUtgiftCore
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.FaktiskUtgiftPeriodeCore
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.ResultatGrunnlagCore
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.ResultatPeriodeCore
import java.time.LocalDate

// Grunnlag
@ApiModel(value = "Grunnlaget for en netto barnetilsyn beregning")
data class BeregnNettoBarnetilsynGrunnlag(
    @ApiModelProperty(
        value = "Periodisert liste over bidragsmottakers faktiske bruttoutgifter til tilsyn") val faktiskUtgiftPeriodeListe: List<FaktiskUtgiftPeriode>? = null
)

@ApiModel(value = "Bidragsmottakers faktiske bruttoutgifter til tilsyn")
data class FaktiskUtgiftPeriode(
    @ApiModelProperty(value = "Bidragsmottakers faktiske bruttoutgifter til tilsyn fra-til-dato") var faktiskUtgiftPeriodeDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Søknadsbarnets fødselsdato") var faktiskUtgiftSoknadsbarnFodselsdato: LocalDate? = null,
    @ApiModelProperty(value = "Søknadsbarnets person-id") var faktiskUtgiftSoknadsbarnPersonId: Int? = null,
    @ApiModelProperty(value = "Bidragsmottakers faktiske bruttoutgifter til tilsyn beløp") var faktiskUtgiftBelop: Double? = null
) {

  fun tilCore() = FaktiskUtgiftPeriodeCore(
      faktiskUtgiftPeriodeDatoFraTil = if (faktiskUtgiftPeriodeDatoFraTil != null) faktiskUtgiftPeriodeDatoFraTil!!.tilCore() else throw UgyldigInputException(
          "faktiskUtgiftPeriodeDatoFraTil kan ikke være null"),
      faktiskUtgiftSoknadsbarnFodselsdato = if (faktiskUtgiftSoknadsbarnFodselsdato != null) faktiskUtgiftSoknadsbarnFodselsdato!! else throw UgyldigInputException(
          "faktiskUtgiftSoknadsbarnFodselsdato kan ikke være null"),
      faktiskUtgiftSoknadsbarnPersonId = if (faktiskUtgiftSoknadsbarnPersonId != null) faktiskUtgiftSoknadsbarnPersonId!! else throw UgyldigInputException(
          "faktiskUtgiftSoknadsbarnPersonId kan ikke være null"),
      faktiskUtgiftBelop = if (faktiskUtgiftBelop != null) faktiskUtgiftBelop!! else throw UgyldigInputException(
          "faktiskUtgiftBelop kan ikke være null")
  )
}

// Resultat
@ApiModel(value = "Totalresultatet av beregning av netto barnetilsyn")
data class BeregnNettoBarnetilsynResultat(
    @ApiModelProperty(
        value = "Periodisert liste over resultat av beregning av netto barnetilsyn") var resultatPeriodeListe: List<ResultatPeriodeNettoBarnetilsyn> = emptyList()
) {

  constructor(beregnNettoBarnetilsynResultat: BeregnNettoBarnetilsynResultatCore) : this(
      resultatPeriodeListe = beregnNettoBarnetilsynResultat.resultatPeriodeListe.map {
        ResultatPeriodeNettoBarnetilsyn(it)
      }
  )
}

@ApiModel(value = "Resultatet av beregning av netto barnetilsyn for en gitt periode")
data class ResultatPeriodeNettoBarnetilsyn(
    @ApiModelProperty(value = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Beregning resultat innhold") var resultatBeregningListe: List<ResultatBeregningNettoBarnetilsyn> = emptyList(),
    @ApiModelProperty(value = "Beregning grunnlag innhold") var resultatGrunnlag: ResultatGrunnlagNettoBarnetilsyn? = null
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
      resultatDatoFraTil = Periode(resultatPeriode.resultatDatoFraTil),
      resultatBeregningListe = resultatPeriode.resultatBeregningListe.map { ResultatBeregningNettoBarnetilsyn(it) },
      resultatGrunnlag = ResultatGrunnlagNettoBarnetilsyn(resultatPeriode.resultatGrunnlag)
  )
}

@ApiModel(value = "Resultatet av beregning av netto barnetilsyn")
data class ResultatBeregningNettoBarnetilsyn(
    @ApiModelProperty(value = "Søknadsbarnets person-id") var resultatSoknadsbarnPersonId: Int = 0,
    @ApiModelProperty(value = "Beløp netto barnetilsyn") var resultatBelopNettoBarnetilsyn: Double = 0.0
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
      resultatSoknadsbarnPersonId = resultatBeregning.resultatSoknadsbarnPersonId,
      resultatBelopNettoBarnetilsyn = resultatBeregning.resultatBelop
  )
}

@ApiModel(value = "Grunnlaget for beregning av netto barnetilsyn")
data class ResultatGrunnlagNettoBarnetilsyn(
    @ApiModelProperty(
        value = "Liste over faktiske utgifter") var resultatGrunnlagFaktiskUtgiftListe: List<ResultatGrunnlagFaktiskUtgift> = emptyList(),
//    @ApiModelProperty(value = "Liste over sjablonperioder") var sjablonListe: List<Sjablon> = emptyList()
) {

  constructor(resultatGrunnlag: ResultatGrunnlagCore) : this(
      resultatGrunnlagFaktiskUtgiftListe = resultatGrunnlag.faktiskUtgiftListe.map { ResultatGrunnlagFaktiskUtgift(it) },
//      sjablonListe = resultatGrunnlag.sjablonListe.map { Sjablon(it) }
  )
}

@ApiModel(value = "Grunnlaget for beregning av netto barnetilsyn - faktisk utgift")
data class ResultatGrunnlagFaktiskUtgift(
    @ApiModelProperty(value = "Søknadsbarnets fødselsdato") var soknadsbarnFodselsdato: LocalDate? = null,
    @ApiModelProperty(value = "Søknadsbarnets person-id") var soknadsbarnPersonId: Int? = null,
    @ApiModelProperty(value = "Bidragsmottakers faktiske bruttoutgifter til tilsyn beløp") var faktiskUtgiftBelop: Double? = null
) {

  constructor(faktiskUtgift: FaktiskUtgiftCore) : this(
      soknadsbarnFodselsdato = faktiskUtgift.soknadsbarnFodselsdato,
      soknadsbarnPersonId = faktiskUtgift.soknadsbarnPersonId,
      faktiskUtgiftBelop = faktiskUtgift.faktiskUtgiftBelop
  )
}
