package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnSamvaersfradragGrunnlagCore
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnSamvaersfradragResultatCore
import no.nav.bidrag.beregn.samvaersfradrag.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.samvaersfradrag.dto.ResultatGrunnlagCore
import no.nav.bidrag.beregn.samvaersfradrag.dto.ResultatPeriodeCore
import no.nav.bidrag.beregn.samvaersfradrag.dto.SamvaersklassePeriodeCore
import java.time.LocalDate

// Grunnlag
@ApiModel(value = "Grunnlaget for en samværsfradragberegning")
data class BeregnSamvaersfradragGrunnlag(
    @ApiModelProperty(value = "Beregn samværsfradrag fra-dato") var beregnDatoFra: LocalDate? = null,
    @ApiModelProperty(value = "Beregn samværsfradrag til-dato") var beregnDatoTil: LocalDate? = null,
    @ApiModelProperty(value = "Søknadsbarnets fødselsdato") var soknadsbarnFodselsdato: LocalDate? = null,
    @ApiModelProperty(
        value = "Periodisert liste over bidragspliktiges samværsklasser") val samvaersklassePeriodeListe: List<SamvaersklassePeriode>? = null
) {

  fun tilCore() = BeregnSamvaersfradragGrunnlagCore(
      beregnDatoFra = if (beregnDatoFra != null) beregnDatoFra!! else throw UgyldigInputException("beregnDatoFra kan ikke være null"),
      beregnDatoTil = if (beregnDatoTil != null) beregnDatoTil!! else throw UgyldigInputException("beregnDatoTil kan ikke være null"),
      soknadsbarnFodselsdato = if (soknadsbarnFodselsdato != null) soknadsbarnFodselsdato!! else throw UgyldigInputException(
          "soknadsbarnFodselsdato kan ikke være null"),

      samvaersklassePeriodeListe = if (samvaersklassePeriodeListe != null) samvaersklassePeriodeListe.map { it.tilCore() }
      else throw UgyldigInputException("samvaersklassePeriodeListe kan ikke være null"),

      sjablonPeriodeListe = emptyList()
  )
}

@ApiModel(value = "Bidragspliktiges samværsklasse")
data class SamvaersklassePeriode(
    @ApiModelProperty(value = "Bidragspliktiges samværsklasse fra-til-dato") var samvaersklassePeriodeDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Bidragspliktiges samværsklasse navn") var samvaersklasse: String? = null
) {

  fun tilCore() = SamvaersklassePeriodeCore(
      samvaersklassePeriodeDatoFraTil = if (samvaersklassePeriodeDatoFraTil != null) samvaersklassePeriodeDatoFraTil!!.tilCore()
      else throw UgyldigInputException("samvaersklassePeriodeDatoFraTil kan ikke være null"),
      samvaersklasse = if (samvaersklasse != null) samvaersklasse!!
      else throw UgyldigInputException("samvaersklasse kan ikke være null"),
  )
}


// Resultat
@ApiModel(value = "Totalresultatet av beregning av samværsfradrag")
data class BeregnSamvaersfradragResultat(
    @ApiModelProperty(value = "Periodisert liste over resultat av beregning av samværsfradrag")
    var resultatPeriodeListe: List<ResultatPeriodeSamvaersfradrag> = emptyList()
) {

  constructor(beregnSamvaersfradragResultat: BeregnSamvaersfradragResultatCore) : this(
      resultatPeriodeListe = beregnSamvaersfradragResultat.resultatPeriodeListe.map {ResultatPeriodeSamvaersfradrag(it) }
  )
}

@ApiModel(value = "Resultatet av beregning av samværsfradrag for en gitt periode")
data class ResultatPeriodeSamvaersfradrag(
    @ApiModelProperty(value = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Beregning resultat innhold") var resultatBeregning: ResultatBeregningSamvaersfradrag? = null,
    @ApiModelProperty(value = "Beregning grunnlag innhold") var resultatGrunnlag: ResultatGrunnlagSamvaersfradrag? = null
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
      resultatDatoFraTil = Periode(resultatPeriode.resultatDatoFraTil),
      resultatBeregning = ResultatBeregningSamvaersfradrag(resultatPeriode.resultatBeregning),
      resultatGrunnlag = ResultatGrunnlagSamvaersfradrag(resultatPeriode.resultatGrunnlag)
  )
}

@ApiModel(value = "Resultatet av beregning av samværsfradrag")
data class ResultatBeregningSamvaersfradrag(
    @ApiModelProperty(value = "Beløp samværsfradrag") var resultatSamvaersfradragBelop: Double = 0.0
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
      resultatSamvaersfradragBelop = resultatBeregning.resultatSamvaersfradragBelop
  )
}

@ApiModel(value = "Grunnlaget for beregning av samværsfradrag")
data class ResultatGrunnlagSamvaersfradrag(
    @ApiModelProperty(value = "Søknadsbarnets alder") var soknadBarnAlder: Int? = null,
    @ApiModelProperty(value = "Samværsklasse") var samvaersklasse: String? = null,
    @ApiModelProperty(value = "Liste over sjablonperioder") var sjablonListe: List<Sjablon> = emptyList()
) {

  constructor(resultatGrunnlag: ResultatGrunnlagCore) : this(
      soknadBarnAlder = resultatGrunnlag.soknadBarnAlder,
      samvaersklasse = resultatGrunnlag.samvaersklasse,
      sjablonListe = resultatGrunnlag.sjablonListe.map { Sjablon(it) }
  )
}
