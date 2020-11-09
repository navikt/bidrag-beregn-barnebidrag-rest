package no.nav.bidrag.beregn.barnebidrag.rest.consumer

data class SjablonListe (
    var sjablonSjablontallResponse: List<Sjablontall>,
    var sjablonForbruksutgifterResponse: List<Forbruksutgifter>,
    var sjablonMaksTilsynResponse: List<MaksTilsyn>,
    var sjablonMaksFradragResponse: List<MaksFradrag>,
    var sjablonSamvaersfradragResponse: List<Samvaersfradrag>,
    var sjablonBidragsevneResponse: List<Bidragsevne>,
    var sjablonTrinnvisSkattesatsResponse: List<TrinnvisSkattesats>
)