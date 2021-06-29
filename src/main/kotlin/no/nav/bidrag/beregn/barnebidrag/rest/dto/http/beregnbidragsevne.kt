package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.v3.oas.annotations.media.Schema
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import no.nav.bidrag.beregn.bidragsevne.dto.BarnIHusstandPeriodeCore
import no.nav.bidrag.beregn.bidragsevne.dto.BeregnetBidragsevneResultatCore
import no.nav.bidrag.beregn.bidragsevne.dto.BostatusPeriodeCore
import no.nav.bidrag.beregn.bidragsevne.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.bidragsevne.dto.ResultatPeriodeCore
import no.nav.bidrag.beregn.bidragsevne.dto.SaerfradragPeriodeCore
import no.nav.bidrag.beregn.bidragsevne.dto.SkatteklassePeriodeCore
import java.math.BigDecimal

// Grunnlag
@Schema(description = "Grunnlaget for en bidragsevnesberegning for bidragspliktig")
data class BeregnBPBidragsevneGrunnlag(
  @Schema(description = "Periodisert liste over bidragspliktiges skatteklasse") val skatteklassePeriodeListe: List<SkatteklassePeriode>? = null,
  @Schema(description = "Periodisert liste over bidragspliktiges bostatus") val bostatusPeriodeListe: List<BostatusPeriode>? = null,
  @Schema(description = "Periodisert liste over antall barn i bidragspliktiges hushold") val antallBarnIEgetHusholdPeriodeListe: List<AntallBarnIEgetHusholdPeriode>? = null,
  @Schema(description = "Periodisert liste over bidragspliktiges særfradrag") val saerfradragPeriodeListe: List<SaerfradragPeriode>? = null
)

@Schema(description = "Bidragspliktiges skatteklasse")
data class SkatteklassePeriode(
  @Schema(description = "Bidragspliktiges skatteklasse fra-til-dato") var skatteklasseDatoFraTil: Periode? = null,
  @Schema(description = "Bidragspliktiges skatteklasse") var skatteklasseId: Int? = null
) {

  fun tilCore() = SkatteklassePeriodeCore(
    referanse = "",
    periode = if (skatteklasseDatoFraTil != null) skatteklasseDatoFraTil!!.tilCore(
      "skatteklasse"
    ) else throw UgyldigInputException("skatteklasseDatoFraTil kan ikke være null"),
    skatteklasse = if (skatteklasseId != null) skatteklasseId!! else throw UgyldigInputException("skatteklasseId kan ikke være null")
  )
}

@Schema(description = "Bidragspliktiges bostatus")
data class BostatusPeriode(
  @Schema(description = "Bidragspliktiges bostatus fra-til-dato") var bostatusDatoFraTil: Periode? = null,
  @Schema(description = "Bidragspliktiges bostatuskode") var bostatusKode: String? = null
) {

  fun tilCore() = BostatusPeriodeCore(
    referanse = "",
    periode = if (bostatusDatoFraTil != null) bostatusDatoFraTil!!.tilCore(
      "bostatus"
    ) else throw UgyldigInputException("bostatusDatoFraTil kan ikke være null"),
    kode = if (bostatusKode != null) bostatusKode!! else throw UgyldigInputException("bostatusKode kan ikke være null")
  )
}

@Schema(description = "Antall barn i bidragspliktiges hushold")
data class AntallBarnIEgetHusholdPeriode(
  @Schema(description = "Antall barn i bidragspliktiges hushold fra-til-dato") var antallBarnIEgetHusholdDatoFraTil: Periode? = null,
  @Schema(description = "Antall barn i bidragspliktiges hushold") var antallBarn: Double? = null
) {

  fun tilCore() = BarnIHusstandPeriodeCore(
    referanse = "",
    periode = if (antallBarnIEgetHusholdDatoFraTil != null) antallBarnIEgetHusholdDatoFraTil!!.tilCore(
      "antallBarnIEgetHushold"
    ) else throw UgyldigInputException("antallBarnIEgetHusholdDatoFraTil kan ikke være null"),
    antallBarn = if (antallBarn != null) antallBarn!! else throw UgyldigInputException("antallBarn kan ikke være null")
  )
}

@Schema(description = "Bidragspliktiges særfradrag")
data class SaerfradragPeriode(
  @Schema(description = "Bidragspliktiges særfradrag fra-til-dato") var saerfradragDatoFraTil: Periode? = null,
  @Schema(description = "Bidragspliktiges særfradrag kode") var saerfradragKode: String? = null
) {

  fun tilCore() = SaerfradragPeriodeCore(
    referanse = "",
    periode = if (saerfradragDatoFraTil != null) saerfradragDatoFraTil!!.tilCore(
      "saerfradrag"
    ) else throw UgyldigInputException("saerfradragDatoFraTil kan ikke være null"),
    kode = if (saerfradragKode != null) saerfradragKode!! else throw UgyldigInputException("saerfradragKode kan ikke være null")
  )
}

// Resultat
@Schema(description = "Resultatet av en bidragsevnesberegning for bidragspliktig")
data class BeregnBPBidragsevneResultat(
  @Schema(description = "Periodisert liste over resultat av bidragsevnesberegning") var resultatPeriodeListe: List<ResultatPeriodeBidragsevne> = emptyList()
) {

  constructor(beregnBidragsevneResultat: BeregnetBidragsevneResultatCore) : this(
    resultatPeriodeListe = beregnBidragsevneResultat.resultatPeriodeListe.map { ResultatPeriodeBidragsevne(it) }
  )
}

@Schema(description = "Resultatet av en beregning for en gitt periode")
data class ResultatPeriodeBidragsevne(
  @Schema(description = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode = Periode(),
  @Schema(description = "Beregning resultat innhold") var resultatBeregning: ResultatBeregningBidragsevne = ResultatBeregningBidragsevne(),
  @Schema(description = "Beregning grunnlag innhold") var resultatGrunnlag: List<String> = emptyList()
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
    resultatDatoFraTil = Periode(resultatPeriode.periode),
    resultatBeregning = ResultatBeregningBidragsevne(resultatPeriode.resultat),
    resultatGrunnlag = resultatPeriode.grunnlagReferanseListe
  )
}

@Schema(description = "Resultatet av en beregning")
data class ResultatBeregningBidragsevne(
  @Schema(description = "Resultat 25 prosent inntekt") var resultat25ProsentInntekt: BigDecimal = BigDecimal.ZERO,
  @Schema(description = "Resultatevne beløp") var resultatEvneBelop: BigDecimal = BigDecimal.ZERO
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
    resultatEvneBelop = resultatBeregning.belop,
    resultat25ProsentInntekt = resultatBeregning.inntekt25Prosent
  )
}
