package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import no.nav.bidrag.beregn.bidragsevne.dto.InntektPeriodeCore
import java.math.BigDecimal

// Grunnlag
@ApiModel(value = "Grunnlagsdata for bidragspliktiges og bidragsmottakers inntekt")
data class InntektBPBMGrunnlag(
    @ApiModelProperty(value = "Periodisert liste over bidragspliktiges inntekt") val inntektBPPeriodeListe: List<InntektPeriode>? = null,
    @ApiModelProperty(value = "Periodisert liste over bidragsmottakers inntekt") val inntektBMPeriodeListe: List<InntektPeriode>? = null
)

@ApiModel(value = "Inntekt periode")
data class InntektPeriode(
    @ApiModelProperty(value = "Inntekt fra-til dato") var inntektDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Inntekt type") var inntektType: String? = null,
    @ApiModelProperty(value = "Inntekt beløp") var inntektBelop: BigDecimal? = null,
    @ApiModelProperty(value = "Søknadsbarnets person-id") var soknadsbarnPersonId: Int? = null
) {

  fun tilCore(dataElement: String) = InntektPeriodeCore(
      inntektPeriodeDatoFraTil = if (inntektDatoFraTil != null) inntektDatoFraTil!!.tilCore(
          "$dataElement inntekt") else throw UgyldigInputException("$dataElement inntektDatoFraTil kan ikke være null"),
      inntektType = if (inntektType != null) inntektType!! else throw UgyldigInputException("$dataElement inntektType kan ikke være null"),
      inntektBelop = if (inntektBelop != null) inntektBelop!! else throw UgyldigInputException("$dataElement inntektBelop kan ikke være null")
  )

  fun validerInntekt(dataElement: String) {
    if (inntektDatoFraTil != null) inntektDatoFraTil!!.valider("$dataElement inntekt") else throw UgyldigInputException(
        "$dataElement inntektDatoFraTil kan ikke være null")
    if (inntektType == null) throw UgyldigInputException("$dataElement inntektType kan ikke være null")
    if (inntektBelop == null) throw UgyldigInputException("$dataElement inntektBelop kan ikke være null")
  }
}


// Resultat
@ApiModel(value = "Inntekttype og -beløp")
data class Inntekt(
    @ApiModelProperty(value = "Inntekt type") var inntektType: String,
    @ApiModelProperty(value = "Inntekt beløp") var inntektBelop: BigDecimal = BigDecimal.ZERO
) {

  constructor(inntekt: no.nav.bidrag.beregn.bidragsevne.dto.InntektCore) : this(
      inntektType = inntekt.inntektType,
      inntektBelop = inntekt.inntektBelop
  )

  constructor(inntekt: no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.InntektCore) : this(
      inntektType = inntekt.inntektType,
      inntektBelop = inntekt.inntektBelop
  )
}
