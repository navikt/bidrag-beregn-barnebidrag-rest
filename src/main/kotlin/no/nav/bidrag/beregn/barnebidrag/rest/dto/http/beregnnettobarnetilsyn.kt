package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import no.nav.bidrag.beregn.barnebidrag.rest.service.SoknadsbarnUtil
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnNettoBarnetilsynResultatCore
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.FaktiskUtgiftCore
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.FaktiskUtgiftPeriodeCore
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.ResultatGrunnlagCore
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.ResultatPeriodeCore
import java.time.LocalDate

// Grunnlag
@ApiModel(value = "Grunnlaget for en netto barnetilsyn beregning for bidragsmottaker")
data class BeregnBMNettoBarnetilsynGrunnlag(
    @ApiModelProperty(
        value = "Periodisert liste over bidragsmottakers faktiske bruttoutgifter til tilsyn") val faktiskUtgiftPeriodeListe: List<FaktiskUtgiftPeriode>? = null
)

@ApiModel(value = "Bidragsmottakers faktiske bruttoutgifter til tilsyn")
data class FaktiskUtgiftPeriode(
    @ApiModelProperty(value = "Bidragsmottakers faktiske bruttoutgifter til tilsyn fra-til-dato") var faktiskUtgiftDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Søknadsbarnets person-id") var faktiskUtgiftSoknadsbarnPersonId: Int? = null,
    @ApiModelProperty(value = "Bidragsmottakers faktiske bruttoutgifter til tilsyn beløp") var faktiskUtgiftBelop: Double? = null
) {

  fun tilCore(soknadsbarnMap: Map<Int, LocalDate>) = FaktiskUtgiftPeriodeCore(
      faktiskUtgiftPeriodeDatoFraTil = if (faktiskUtgiftDatoFraTil != null) faktiskUtgiftDatoFraTil!!.tilCore(
          "faktiskUtgift") else throw UgyldigInputException("faktiskUtgiftDatoFraTil kan ikke være null"),
      faktiskUtgiftSoknadsbarnPersonId = if (faktiskUtgiftSoknadsbarnPersonId != null) faktiskUtgiftSoknadsbarnPersonId!! else throw UgyldigInputException(
          "faktiskUtgiftSoknadsbarnPersonId kan ikke være null"),
      faktiskUtgiftBelop = if (faktiskUtgiftBelop != null) faktiskUtgiftBelop!! else throw UgyldigInputException(
          "faktiskUtgiftBelop kan ikke være null"),
      faktiskUtgiftSoknadsbarnFodselsdato = SoknadsbarnUtil.hentFodselsdatoForId(faktiskUtgiftSoknadsbarnPersonId, soknadsbarnMap) ?: throw UgyldigInputException(
          "Søknadsbarn med id $faktiskUtgiftSoknadsbarnPersonId har ingen korresponderende fødselsdato i soknadsbarnListe")
  )
}

// Resultat
@ApiModel(value = "Resultatet av en netto barnetilsyn beregning for bidragsmottaker")
data class BeregnBMNettoBarnetilsynResultat(
    @ApiModelProperty(
        value = "Periodisert liste over resultat av beregning av netto barnetilsyn") var resultatPeriodeListe: List<ResultatPeriodeNettoBarnetilsyn> = emptyList()
) {

  constructor(beregnNettoBarnetilsynResultat: BeregnNettoBarnetilsynResultatCore) : this(
      resultatPeriodeListe = beregnNettoBarnetilsynResultat.resultatPeriodeListe.map { ResultatPeriodeNettoBarnetilsyn(it) }
  )
}

@ApiModel(value = "Resultatet av beregning av netto barnetilsyn for et søknadsbarn for en gitt periode")
data class ResultatPeriodeNettoBarnetilsyn(
    @ApiModelProperty(value = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Beregning resultat innhold liste") var resultatBeregningListe: List<ResultatBeregningNettoBarnetilsyn> = emptyList(),
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
    @ApiModelProperty(value = "Beløp netto barnetilsyn") var resultatBelop: Double = 0.0
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
      resultatSoknadsbarnPersonId = resultatBeregning.resultatSoknadsbarnPersonId,
      resultatBelop = resultatBeregning.resultatBelop
  )
}

@ApiModel(value = "Grunnlaget for beregning av netto barnetilsyn")
data class ResultatGrunnlagNettoBarnetilsyn(
    @ApiModelProperty(
        value = "Liste over faktiske utgifter") var faktiskUtgiftListe: List<FaktiskUtgift> = emptyList()
//    @ApiModelProperty(value = "Liste over sjablonperioder") var sjablonListe: List<Sjablon> = emptyList()
) {

  constructor(resultatGrunnlag: ResultatGrunnlagCore) : this(
      faktiskUtgiftListe = resultatGrunnlag.faktiskUtgiftListe.map { FaktiskUtgift(it) }
//      sjablonListe = resultatGrunnlag.sjablonListe.map { Sjablon(it) }
  )
}

@ApiModel(value = "Grunnlaget for beregning av netto barnetilsyn - faktisk utgift")
data class FaktiskUtgift(
    @ApiModelProperty(value = "Søknadsbarnets person-id") var soknadsbarnPersonId: Int = 0,
    @ApiModelProperty(value = "Søknadsbarnets alder") var soknadsbarnAlder: Int = 0,
    @ApiModelProperty(value = "Bidragsmottakers faktiske bruttoutgifter til tilsyn beløp") var faktiskUtgiftBelop: Double? = null
) {

  constructor(faktiskUtgift: FaktiskUtgiftCore) : this(
      soknadsbarnPersonId = faktiskUtgift.faktiskUtgiftSoknadsbarnPersonId,
      soknadsbarnAlder = faktiskUtgift.soknadsbarnAlder,
      faktiskUtgiftBelop = faktiskUtgift.faktiskUtgiftBelop
  )
}
