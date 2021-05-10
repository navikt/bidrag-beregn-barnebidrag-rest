package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.v3.oas.annotations.media.Schema
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import java.time.LocalDate

// Grunnlag
@Schema(description = "Grunnlagsdata for søknadsbarn")
data class SoknadsbarnGrunnlag(
    @Schema(description = "Periodisert liste over søknadsbarn") val soknadsbarnListe: List<Soknadsbarn>? = null
)

@Schema(description = "Søknadsbarn")
data class Soknadsbarn(
    @Schema(description = "øknadsbarnets fødselsdato") var soknadsbarnFodselsdato: LocalDate? = null,
    @Schema(description = "øknadsbarnets person-id") var soknadsbarnPersonId: Int? = null,
    @Schema(description = "Periodisert liste over søknadsbarnets inntekter") var inntektPeriodeListe: List<InntektPeriode>? = null
) {

    fun validerSoknadsbarn() {
        if (soknadsbarnFodselsdato == null) throw UgyldigInputException("soknadsbarnFodselsdato kan ikke være null")
        if (soknadsbarnPersonId == null) throw UgyldigInputException("soknadsbarnPersonId kan ikke være null")
        if (inntektPeriodeListe != null) inntektPeriodeListe!!.map { it.validerInntekt("SB") } else throw UgyldigInputException(
            "SB inntektPeriodeListe kan ikke være null"
        )
    }
}