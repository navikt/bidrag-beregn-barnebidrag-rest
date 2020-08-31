package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

// Grunnlag
@ApiModel(value = "Grunnlaget for en barnebidragsberegning")
data class BeregnBarnebidragGrunnlag(
    @ApiModelProperty(value = "Beregn bidragsevne grunnlag") var beregnBidragsevneGrunnlag: BeregnBidragsevneGrunnlag? = null,
    @ApiModelProperty(value = "Beregn netto barnetilsyn grunnlag") var beregnNettoBarnetilsynGrunnlag: BeregnNettoBarnetilsynGrunnlag? = null,
    @ApiModelProperty(value = "Beregn underholdskostnad grunnlag") var beregnUnderholdskostnadGrunnlag: BeregnUnderholdskostnadGrunnlag? = null,
    @ApiModelProperty(
        value = "Beregn BPs andel av underholdskostnad grunnlag") var beregnBPAndelUnderholdskostnadGrunnlag: BeregnBPsAndelUnderholdskostnadGrunnlag? = null,
    @ApiModelProperty(value = "Beregn samværsfradrag grunnlag") var beregnSamvaersfradragGrunnlag: BeregnSamvaersfradragGrunnlag? = null
)

// Resultat
@ApiModel(value = "Resultatet av en barnebidragsberegning")
data class BeregnBarnebidragResultat(
    @ApiModelProperty(value = "Beregn bidragsevne resultat") var beregnBidragsevneResultat: BeregnBidragsevneResultat,
    @ApiModelProperty(value = "Beregn netto barnetilsyn resultat") var beregnNettoBarnetilsynResultat: BeregnNettoBarnetilsynResultat,
    @ApiModelProperty(value = "Beregn underholdskostnad resultat") var beregnUnderholdskostnadResultat: BeregnUnderholdskostnadResultat,
    @ApiModelProperty(
        value = "Beregn BPs andel av underholdskostnad resultat") var beregnBPAndelUnderholdskostnadResultat: BeregnBPsAndelUnderholdskostnadResultat,
    @ApiModelProperty(value = "Beregn samværsfradrag resultat") var beregnSamvaersfradragResultat: BeregnSamvaersfradragResultat
)