package no.nav.bidrag.beregn.barnebidrag.rest.consumer

data class SjablonListe (
    var sjablonSjablontallResponse: List<Sjablontall>? = null,
    var sjablonForbruksutgifterResponse: List<Forbruksutgifter>? = null,
    var sjablonMaksTilsynResponse: List<MaksTilsyn>? = null,
    var sjablonMaksFradragResponse: List<MaksFradrag>? = null,
    var sjablonSamvaersfradragResponse: List<Samvaersfradrag>? = null,
    var sjablonBidragsevneResponse: List<Bidragsevne>? = null,
    var sjablonTrinnvisSkattesatsResponse: List<TrinnvisSkattesats>? = null
)