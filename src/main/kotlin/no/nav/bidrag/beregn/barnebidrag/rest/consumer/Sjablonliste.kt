package no.nav.bidrag.beregn.barnebidrag.rest.consumer

data class SjablonListe (
    var sjablonSjablontallResponse: List<Sjablontall> = emptyList(),
    var sjablonForbruksutgifterResponse: List<Forbruksutgifter> = emptyList(),
    var sjablonMaksTilsynResponse: List<MaksTilsyn> = emptyList(),
    var sjablonMaksFradragResponse: List<MaksFradrag> = emptyList(),
    var sjablonSamvaersfradragResponse: List<Samvaersfradrag> = emptyList(),
    var sjablonBidragsevneResponse: List<Bidragsevne> = emptyList(),
    var sjablonTrinnvisSkattesatsResponse: List<TrinnvisSkattesats> = emptyList()
)