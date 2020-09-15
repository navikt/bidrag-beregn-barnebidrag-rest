package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import no.nav.bidrag.beregn.bidragsevne.dto.InntektPeriodeCore
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadResultatCore
import no.nav.bidrag.beregn.underholdskostnad.dto.ResultatBeregningCore
import no.nav.bidrag.beregn.underholdskostnad.dto.ResultatGrunnlagCore
import no.nav.bidrag.beregn.underholdskostnad.dto.ResultatPeriodeCore

// Grunnlag
@ApiModel(value = "Grunnlagsdata for bidragspliktiges og bidragsmottakers inntekt")
data class InntektBPBMGrunnlag(
    @ApiModelProperty(value = "Periodisert liste over bidragspliktiges inntekt") val inntektBPPeriodeListe: List<InntektPeriode>? = null,
    @ApiModelProperty(value = "Periodisert liste over bidragsmottakers inntekt") val inntektBMPeriodeListe: List<InntektPeriode>? = null
)

@ApiModel(value = "Inntekt periode")
data class InntektPeriode(
    @ApiModelProperty(value = "Inntekt fra-til dato") var inntektPeriodeDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Inntekt type") var inntektType: String? = null,
    @ApiModelProperty(value = "Inntekt beløp") var inntektBelop: Double? = null
) {

  fun tilCore(dataElement: String) = InntektPeriodeCore(
      inntektPeriodeDatoFraTil = if (inntektPeriodeDatoFraTil != null) inntektPeriodeDatoFraTil!!.tilCore(
          dataElement + "inntekt") else throw UgyldigInputException(dataElement + "inntektPeriodeDatoFraTil kan ikke være null"),
      inntektType = if (inntektType != null) inntektType!! else throw UgyldigInputException(dataElement + "inntektType kan ikke være null"),
      inntektBelop = if (inntektBelop != null) inntektBelop!! else throw UgyldigInputException(dataElement + "inntektBelop kan ikke være null")
  )

  fun valider(dataElement: String) {
    if (inntektPeriodeDatoFraTil != null) inntektPeriodeDatoFraTil!!.valider(dataElement) else throw UgyldigInputException(
        dataElement + "inntektPeriodeDatoFraTil kan ikke være null")
    if (inntektType == null) throw UgyldigInputException(dataElement + "inntektType kan ikke være null")
    if (inntektBelop == null) throw UgyldigInputException(dataElement + "inntektBelop kan ikke være null")
  }
}
