package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import java.time.LocalDate

// Grunnlag
@ApiModel(value = "Grunnlagsdata for søknadsbarn")
data class SoknadsbarnGrunnlag(
    @ApiModelProperty(
        value = "Periodeisert liste over søknadsbarn") val soknadsbarnListe: List<Soknadsbarn>? = null
)

@ApiModel(value = "Søknadsbarn")
data class Soknadsbarn(
    @ApiModelProperty(value = "Søknadsbarnets fødselsdato") var soknadsbarnFodselsdato: LocalDate? = null,
    @ApiModelProperty(value = "Søknadsbarnets person-id") var soknadsbarnPersonId: Int? = null,
    @ApiModelProperty(value = "Periodisert liste over søknadsbarnets inntekter") var inntektPeriodeListe: List<InntektPeriode>? = null
) {

  fun validerSoknadsbarn() {
    if (soknadsbarnFodselsdato == null) throw UgyldigInputException("soknadsbarnFodselsdato kan ikke være null")
    if (soknadsbarnPersonId == null) throw UgyldigInputException("soknadsbarnPersonId kan ikke være null")
    if (inntektPeriodeListe != null) inntektPeriodeListe!!.map { it.validerInntekt("SB") } else throw UgyldigInputException(
        "SB inntektPeriodeListe kan ikke være null")
  }
}