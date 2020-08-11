package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException
import no.nav.bidrag.beregn.felles.dto.PeriodeCore
import no.nav.bidrag.beregn.felles.dto.SjablonCore
import no.nav.bidrag.beregn.felles.dto.SjablonInnholdCore
import no.nav.bidrag.beregn.felles.dto.SjablonNokkelCore
import java.time.LocalDate

@ApiModel(value = "Sjablonverdier")
data class Sjablon(
    @ApiModelProperty(value = "Sjablon navn") var sjablonNavn: String? = null,
    @ApiModelProperty(value = "Liste over sjablon nøkler") var sjablonNokkelListe: List<SjablonNokkel> = emptyList(),
    @ApiModelProperty(value = "Liste over sjablon innhold") var sjablonInnholdListe: List<SjablonInnhold> = emptyList()
) {

  constructor(sjablon: SjablonCore) : this(
      sjablonNavn = sjablon.sjablonNavn,
      sjablonNokkelListe = if (sjablon.sjablonNokkelListe != null) sjablon.sjablonNokkelListe!!.map {
        SjablonNokkel(it)
      } else emptyList(),
      sjablonInnholdListe = sjablon.sjablonInnholdListe.map { SjablonInnhold(it) }
  )
}

@ApiModel(value = "Sjablonnøkkel")
data class SjablonNokkel(
    @ApiModelProperty(value = "Sjablonnøkkel navn") var sjablonNokkelNavn: String? = null,
    @ApiModelProperty(value = "Sjablonnøkkel verdi") var sjablonNokkelVerdi: String? = null
) {

  constructor(sjablonNokkel: SjablonNokkelCore) : this(
      sjablonNokkelNavn = sjablonNokkel.sjablonNokkelNavn,
      sjablonNokkelVerdi = sjablonNokkel.sjablonNokkelVerdi
  )
}

@ApiModel(value = "Sjabloninnhold")
data class SjablonInnhold(
    @ApiModelProperty(value = "Sjabloninnhold navn") var sjablonInnholdNavn: String? = null,
    @ApiModelProperty(value = "Sjabloninnhold verdi") var sjablonInnholdVerdi: Double? = null
) {

  constructor(sjablonInnhold: SjablonInnholdCore) : this(
      sjablonInnholdNavn = sjablonInnhold.sjablonInnholdNavn,
      sjablonInnholdVerdi = sjablonInnhold.sjablonInnholdVerdi
  )
}

// Felles
@ApiModel(value = "Periode (fra-til dato)")
data class Periode(
    @ApiModelProperty(value = "Fra-dato") var periodeDatoFra: LocalDate? = null,
    @ApiModelProperty(value = "Til-dato") var periodeDatoTil: LocalDate? = null
) {

  constructor(periode: PeriodeCore) : this(
      periodeDatoFra = periode.periodeDatoFra,
      periodeDatoTil = periode.periodeDatoTil
  )

  fun tilCore() = PeriodeCore(
      periodeDatoFra = if (periodeDatoFra != null) periodeDatoFra!! else throw UgyldigInputException("periodeDatoFra kan ikke være null"),
      periodeDatoTil = if (periodeDatoTil != null) periodeDatoTil!! else throw UgyldigInputException("periodeDatoTil kan ikke være null")
  )
}
