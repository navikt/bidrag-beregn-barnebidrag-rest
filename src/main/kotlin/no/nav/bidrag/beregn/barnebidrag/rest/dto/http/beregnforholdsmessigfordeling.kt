package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.BeregnForholdsmessigFordelingGrunnlagCore
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.BeregnForholdsmessigFordelingResultatCore
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.BeregnetBidragSakCore
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.BeregnetBidragSakPeriodeCore
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.BidragsevneCore
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.BidragsevnePeriodeCore
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.GrunnlagBeregningPeriodisertCore
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.GrunnlagPerBarnCore
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.ResultatPerBarnCore
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.ResultatPeriodeCore
import java.math.BigDecimal
import java.time.LocalDate

// Grunnlag
@ApiModel(value = "Grunnlaget for en beregning av forholdsmessig fordeling")
data class BeregnForholdsmessigFordelingGrunnlag(
  @ApiModelProperty(value = "Beregn fra-dato") val beregnDatoFra: LocalDate? = null,
  @ApiModelProperty(value = "Beregn til-dato") val beregnDatoTil: LocalDate? = null,
  @ApiModelProperty(value = "Periodisert liste over bidragspliktiges bidragsevne") val bidragsevnePeriodeListe: List<BidragsevnePeriode>? = null,
  @ApiModelProperty(value = "Periodisert liste over bidragspliktiges beregnede bidrag") val beregnetBidragSakPeriodeListe: List<BeregnetBidragSakPeriode>? = null
) {

  fun tilCore() = BeregnForholdsmessigFordelingGrunnlagCore(
    beregnDatoFra = beregnDatoFra ?: throw UgyldigInputException("beregnDatoFra kan ikke være null"),
    beregnDatoTil = beregnDatoTil ?: throw UgyldigInputException("beregnDatoTil kan ikke være null"),
    bidragsevnePeriodeListe = bidragsevnePeriodeListe?.map { it.tilCore() }
      ?: throw UgyldigInputException("bidragsevnePeriodeListe kan ikke være null"),
    beregnetBidragPeriodeListe = beregnetBidragSakPeriodeListe?.map { it.tilCore() }
      ?: throw UgyldigInputException("beregnetBidragSakPeriodeListe kan ikke være null")
  )
}

@ApiModel(value = "Bidragspliktiges bidragsevne")
data class BidragsevnePeriode(
  @ApiModelProperty(value = "Bidragspliktiges bidragsevne fra-til-dato") val bidragsevneDatoFraTil: Periode? = null,
  @ApiModelProperty(value = "Bidragspliktiges bidragsevne beløp") val bidragsevneBelop: BigDecimal? = null,
  @ApiModelProperty(value = "Bidragspliktiges bidragsevne 25 prosent inntekt") val bidragsevne25ProsentInntekt: BigDecimal? = null
) {

  fun tilCore() = BidragsevnePeriodeCore(
    bidragsevneDatoFraTil = bidragsevneDatoFraTil?.tilCore("bidragsevne") ?: throw UgyldigInputException("bidragsevneDatoFraTil kan ikke være null"),
    bidragsevneBelop = bidragsevneBelop ?: throw UgyldigInputException("bidragsevneBelop kan ikke være null"),
    tjuefemProsentInntekt = bidragsevne25ProsentInntekt ?: throw UgyldigInputException("bidragsevne25ProsentInntekt kan ikke være null")
  )
}

@ApiModel(value = "Bidragspliktiges beregnede bidrag")
data class BeregnetBidragSakPeriode(
  @ApiModelProperty(value = "Bidragspliktiges beregnede bidrag saksnr") val beregnetBidragSaksnr: Int? = null,
  @ApiModelProperty(value = "Bidragspliktiges beregnede bidrag fra-til-dato") val beregnetBidragDatoFraTil: Periode? = null,
  @ApiModelProperty(value = "Liste over bidragspliktiges beregnede bidrag grunnlag per barn") val beregnetBidragPerBarnListe: List<BeregnetBidragPerBarn>? = null
) {

  fun tilCore() = BeregnetBidragSakPeriodeCore(
    saksnr = beregnetBidragSaksnr ?: throw UgyldigInputException("beregnetBidragSaksnr kan ikke være null"),
    periodeDatoFraTil = beregnetBidragDatoFraTil?.tilCore("beregnetBidrag")
      ?: throw UgyldigInputException("beregnetBidragDatoFraTil kan ikke være null"),
    grunnlagPerBarnListe = beregnetBidragPerBarnListe?.map { it.tilCore() }
      ?: throw UgyldigInputException("beregnetBidragPerBarnListe kan ikke være null")
  )
}

@ApiModel(value = "Beregnet bidrag per barn")
data class BeregnetBidragPerBarn(
  @ApiModelProperty(value = "Barnets person-id") val barnPersonId: Int? = null,
  @ApiModelProperty(value = "Bidrag beløp") val bidragBelop: BigDecimal? = null
) {

  fun tilCore() = GrunnlagPerBarnCore(
    barnPersonId = barnPersonId ?: throw UgyldigInputException("barnPersonId kan ikke være null"),
    bidragBelop = bidragBelop ?: throw UgyldigInputException("bidragBelop kan ikke være null")
  )

  constructor(grunnlagPerBarn: GrunnlagPerBarnCore) : this(
    barnPersonId = grunnlagPerBarn.barnPersonId,
    bidragBelop = grunnlagPerBarn.bidragBelop
  )
}

// Resultat
@ApiModel(value = "Resultatet av en beregning av forholdsmessig fordeling")
data class BeregnForholdsmessigFordelingResultat(
  @ApiModelProperty(value = "Periodisert liste over resultatet av av en beregning av forholdsmessig fordeling")
  var resultatPeriodeListe: List<ResultatPeriodeForholdsmessigFordeling> = emptyList()
) {

  constructor(beregnForholdsmessigFordelingResultat: BeregnForholdsmessigFordelingResultatCore) : this(
    resultatPeriodeListe = beregnForholdsmessigFordelingResultat.resultatPeriodeListe.map { ResultatPeriodeForholdsmessigFordeling(it) }
  )
}

@ApiModel(value = "Resultatet av en beregning for en gitt periode")
data class ResultatPeriodeForholdsmessigFordeling(
  @ApiModelProperty(value = "Beregning resultat fra-til-dato") var resultatDatoFraTil: Periode = Periode(),
  @ApiModelProperty(value = "Liste over beregning resultat innhold") var resultatBeregningListe: List<ResultatBeregningForholdsmessigFordeling> = emptyList(),
  @ApiModelProperty(value = "Beregning grunnlag innhold") var resultatGrunnlag: ResultatGrunnlagForholdsmessigFordeling = ResultatGrunnlagForholdsmessigFordeling()
) {

  constructor(resultatPeriode: ResultatPeriodeCore) : this(
    resultatDatoFraTil = Periode(resultatPeriode.resultatDatoFraTil),
    resultatBeregningListe = resultatPeriode.resultatBeregningListe.map { ResultatBeregningForholdsmessigFordeling(it) },
    resultatGrunnlag = ResultatGrunnlagForholdsmessigFordeling(resultatPeriode.resultatGrunnlag)
  )
}

@ApiModel(value = "Resultatet av en beregning")
data class ResultatBeregningForholdsmessigFordeling(
  @ApiModelProperty(value = "Saksnummer") var saksnr: Int = 0,
  @ApiModelProperty(value = "Liste over resultat per barn") var resultatPerBarnListe: List<ResultatPerBarn> = emptyList()
) {

  constructor(resultatBeregning: ResultatBeregningCore) : this(
    saksnr = resultatBeregning.saksnr,
    resultatPerBarnListe = resultatBeregning.resultatPerBarnListe.map { ResultatPerBarn(it) }
  )
}

@ApiModel(value = "Resultatet av en beregning per barn")
data class ResultatPerBarn(
  @ApiModelProperty(value = "Barnets person-id") var barnPersonId: Int = 0,
  @ApiModelProperty(value = "Barnebidrag beløp") var resultatBarnebidragBelop: BigDecimal = BigDecimal.ZERO,
  @ApiModelProperty(value = "Resultatkode") var resultatKode: String = "",
) {

  constructor(resultatPerBarn: ResultatPerBarnCore) : this(
    barnPersonId = resultatPerBarn.barnPersonId,
    resultatBarnebidragBelop = resultatPerBarn.resultatBarnebidragBelop,
    resultatKode = resultatPerBarn.resultatkode
  )
}

@ApiModel(value = "Grunnlaget for en beregning")
data class ResultatGrunnlagForholdsmessigFordeling(
  @ApiModelProperty(value = "Bidragsevne") var bidragsevneResultatGrunnlag: BidragsevneResultatGrunnlag = BidragsevneResultatGrunnlag(),
  @ApiModelProperty(value = "Liste over beregnet bidrag per sak") var beregnetBidragSakListe: List<BeregnetBidragSakResultatGrunnlag> = emptyList()
) {

  constructor(resultatGrunnlag: GrunnlagBeregningPeriodisertCore) : this(
    bidragsevneResultatGrunnlag = BidragsevneResultatGrunnlag(resultatGrunnlag.bidragsevne),
    beregnetBidragSakListe = resultatGrunnlag.beregnetBidragSakListe.map { BeregnetBidragSakResultatGrunnlag(it) }
  )
}

@ApiModel(value = "Bidragsevne grunnlag")
data class BidragsevneResultatGrunnlag(
  @ApiModelProperty(value = "Bidragsevne beløp") var bidragsevneBelop: BigDecimal = BigDecimal.ZERO,
  @ApiModelProperty(value = "bidragsevne 25 prosent inntekt") var bidragsevne25ProsentInntekt: BigDecimal = BigDecimal.ZERO
) {

  constructor(bidragsevne: BidragsevneCore) : this(
    bidragsevneBelop = bidragsevne.bidragsevneBelop,
    bidragsevne25ProsentInntekt = bidragsevne.tjuefemProsentInntekt
  )
}

@ApiModel(value = "Beregnet bidrag sak grunnlag")
data class BeregnetBidragSakResultatGrunnlag(
  @ApiModelProperty(value = "Saksnummer") var saksnr: Int = 0,
  @ApiModelProperty(value = "Liste over grunnlag per barn") var grunnlagPerBarnListe: List<BeregnetBidragPerBarn> = emptyList()
) {

  constructor(beregnetBidragSak: BeregnetBidragSakCore) : this(
    saksnr = beregnetBidragSak.saksnr,
    grunnlagPerBarnListe = beregnetBidragSak.grunnlagPerBarnListe.map { BeregnetBidragPerBarn(it) }
  )
}
