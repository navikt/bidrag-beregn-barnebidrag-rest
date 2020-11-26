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
    @ApiModelProperty(value = "Periodisert liste over bidragsmottakers inntekt") val inntektBMPeriodeListe: List<InntektBMPeriode>? = null
)

@ApiModel(value = "Inntekt periode")
data class InntektPeriode(
    @ApiModelProperty(value = "Inntekt fra-til dato") var inntektDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Inntekt type") var inntektType: String? = null,
    @ApiModelProperty(value = "Inntekt beløp") var inntektBelop: BigDecimal? = null
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

@ApiModel(value = "Inntekt bidragsmottaker periode")
data class InntektBMPeriode(
    @ApiModelProperty(value = "Inntekt fra-til dato") var inntektDatoFraTil: Periode? = null,
    @ApiModelProperty(value = "Inntekt type") var inntektType: String? = null,
    @ApiModelProperty(value = "Inntekt beløp") var inntektBelop: BigDecimal? = null,
    @ApiModelProperty(value = "Søknadsbarnets person-id") var soknadsbarnPersonId: Int? = null,
    @ApiModelProperty(value = "Delt fordel (utvidet barnetrygd)") var deltFordel: Boolean? = null,
    @ApiModelProperty(value = "Skatteklasse 2") var skatteklasse2: Boolean? = null
) {

  fun validerInntekt(dataElement: String) {
    if (inntektDatoFraTil != null) inntektDatoFraTil!!.valider("$dataElement inntekt") else throw UgyldigInputException(
        "$dataElement inntektDatoFraTil kan ikke være null")
    if (inntektType == null) throw UgyldigInputException("$dataElement inntektType kan ikke være null")
    if (inntektBelop == null) throw UgyldigInputException("$dataElement inntektBelop kan ikke være null")
    if (deltFordel == null) throw UgyldigInputException("$dataElement deltFordel kan ikke være null")
    if (skatteklasse2 == null) throw UgyldigInputException("$dataElement skatteklasse2 kan ikke være null")
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

@ApiModel(value = "Inntekt bidragsmottaker")
data class InntektBM(
    @ApiModelProperty(value = "Inntekt type") var inntektType: String,
    @ApiModelProperty(value = "Inntekt beløp") var inntektBelop: BigDecimal = BigDecimal.ZERO,
    @ApiModelProperty(value = "Delt fordel (utvidet barnetrygd)") var deltFordel: Boolean = false,
    @ApiModelProperty(value = "Skatteklasse 2") var skatteklasse2: Boolean = false
) {

  constructor(inntekt: no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.InntektCore) : this(
      inntektType = inntekt.inntektType,
      inntektBelop = inntekt.inntektBelop,
      deltFordel = inntekt.deltFordel,
      skatteklasse2 = inntekt.skatteklasse2
  )
}
