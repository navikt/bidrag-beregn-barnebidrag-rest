package no.nav.bidrag.beregn.bidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

// Grunnlag
@ApiModel(value = "Grunnlaget for en bidragsberegning")
data class BeregnBidragGrunnlag(
    @ApiModelProperty(value = "Beregn bidragsevne grunnlag") var beregnBidragsevneGrunnlag: BeregnBidragsevneGrunnlag? = null,
    @ApiModelProperty(value = "Resten av grunnlaget") var restenAvGrunnlaget: String? = "Resten av grunnlaget"
)

// Resultat
@ApiModel(value = "Resultatet av en bidragsberegning")
data class BeregnBidragResultat(
    @ApiModelProperty(value = "Beregn bidragsevne resultat") var beregnBidragsevneResultat: BeregnBidragsevneResultat,
    @ApiModelProperty(value = "Resten av resultatet") var restenAvResultatet: String
)
