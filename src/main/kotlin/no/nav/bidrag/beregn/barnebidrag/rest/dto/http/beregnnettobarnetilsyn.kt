package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.v3.oas.annotations.media.Schema
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import no.nav.bidrag.beregn.barnebidrag.rest.service.SoknadsbarnUtil
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnNettoBarnetilsynResultatCore
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.FaktiskUtgiftCore
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.FaktiskUtgiftPeriodeCore
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.ResultatGrunnlagCore
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.ResultatPeriodeCore
import java.math.BigDecimal
import java.time.LocalDate

// Grunnlag
@Schema(description = "Grunnlaget for en netto barnetilsyn beregning for bidragsmottaker")
data class BeregnBMNettoBarnetilsynGrunnlag(
    @Schema(description = "Periodisert liste over bidragsmottakers faktiske bruttoutgifter til tilsyn") val faktiskUtgiftPeriodeListe: List<FaktiskUtgiftPeriode>? = null
)

@Schema(description = "Bidragsmottakers faktiske bruttoutgifter til tilsyn")
data class FaktiskUtgiftPeriode(
    @Schema(description = "Bidragsmottakers faktiske bruttoutgifter til tilsyn fra-til-dato") var faktiskUtgiftDatoFraTil: Periode? = null,
    @Schema(description = "Søknadsbarnets person-id") var faktiskUtgiftSoknadsbarnPersonId: Int? = null,
    @Schema(description = "Bidragsmottakers faktiske bruttoutgifter til tilsyn beløp") var faktiskUtgiftBelop: BigDecimal? = null
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
@Schema(description = "Resultatet av en netto barnetilsyn beregning for bidragsmottaker")
data class BeregnBMNettoBarnetilsynResultat(
    @Schema(description = "Periodisert liste over resultat av beregning av netto barnetilsyn") var resultatPeriodeListe: List<ResultatPeriodeNettoBarnetilsyn> = emptyList()
) {

  constructor(beregnNettoBarnetilsynResultat: BeregnNettoBarnetilsynResultatCore) : this(
      resultatPeriodeListe = beregnNettoBarnetilsynResultat.resultatPeriodeListe.map { ResultatPeriodeNettoBarnetilsyn(it) }
  )
}

@Schema(description = "Resultatet av beregning av netto barnetilsyn for et søknadsbarn for en gitt periode")
data class ResultatPeriodeNettoBarnetilsyn(
    @Schema(description = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode = Periode(),
    @Schema(description = "Beregning resultat innhold liste") var resultatBeregningListe: List<ResultatBeregningNettoBarnetilsyn> = emptyList(),
    @Schema(description = "Beregning grunnlag innhold") var resultatGrunnlag: ResultatGrunnlagNettoBarnetilsyn = ResultatGrunnlagNettoBarnetilsyn()
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
      resultatDatoFraTil = Periode(resultatPeriode.resultatDatoFraTil),
      resultatBeregningListe = resultatPeriode.resultatBeregningListe.map { ResultatBeregningNettoBarnetilsyn(it) },
      resultatGrunnlag = ResultatGrunnlagNettoBarnetilsyn(resultatPeriode.resultatGrunnlag)
  )
}

@Schema(description = "Resultatet av beregning av netto barnetilsyn")
data class ResultatBeregningNettoBarnetilsyn(
    @Schema(description = "Søknadsbarnets person-id") var resultatSoknadsbarnPersonId: Int = 0,
    @Schema(description = "Beløp netto barnetilsyn") var resultatBelop: BigDecimal = BigDecimal.ZERO
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
      resultatSoknadsbarnPersonId = resultatBeregning.resultatSoknadsbarnPersonId,
      resultatBelop = resultatBeregning.resultatBelop
  )
}

@Schema(description = "Grunnlaget for beregning av netto barnetilsyn")
data class ResultatGrunnlagNettoBarnetilsyn(
    @Schema(description = "Liste over faktiske utgifter") var faktiskUtgiftListe: List<FaktiskUtgift> = emptyList(),
    @Schema(description = "Liste over sjablonperioder") var sjablonListe: List<Sjablon> = emptyList()
) {

  constructor(resultatGrunnlag: ResultatGrunnlagCore) : this(
      faktiskUtgiftListe = resultatGrunnlag.faktiskUtgiftListe.map { FaktiskUtgift(it) },
      sjablonListe = resultatGrunnlag.sjablonListe.map { Sjablon(it) }
  )
}

@Schema(description = "Grunnlaget for beregning av netto barnetilsyn - faktisk utgift")
data class FaktiskUtgift(
    @Schema(description = "Søknadsbarnets person-id") var soknadsbarnPersonId: Int = 0,
    @Schema(description = "Søknadsbarnets alder") var soknadsbarnAlder: Int = 0,
    @Schema(description = "Bidragsmottakers faktiske bruttoutgifter til tilsyn beløp") var faktiskUtgiftBelop: BigDecimal = BigDecimal.ZERO
) {

  constructor(faktiskUtgift: FaktiskUtgiftCore) : this(
      soknadsbarnPersonId = faktiskUtgift.faktiskUtgiftSoknadsbarnPersonId,
      soknadsbarnAlder = faktiskUtgift.soknadsbarnAlder,
      faktiskUtgiftBelop = faktiskUtgift.faktiskUtgiftBelop
  )
}
