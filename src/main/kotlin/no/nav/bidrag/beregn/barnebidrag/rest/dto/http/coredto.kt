package no.nav.bidrag.beregn.barnebidrag.rest.dto.http

import no.nav.bidrag.beregn.felles.dto.AvvikCore
import no.nav.bidrag.beregn.felles.dto.PeriodeCore
import no.nav.bidrag.beregn.felles.dto.SjablonPeriodeCore
import java.time.LocalDate

// Grunnlag periode
data class BeregnBarnebidragGrunnlagCore(
    val beregnDatoFra: LocalDate,
    val beregnDatoTil: LocalDate,
    var bidragsevnePeriodeListe: List<BidragsevnePeriodeCore>,
    var kostnadsberegnetBidragPeriodeListe: List<KostnadsberegnetBidragPeriodeCore>,
    var samvaerfradragPeriodeListe: List<SamvaersfradragPeriodeCore>,
    val barnetilleggBPPeriodeListe: List<BarnetilleggBPPeriodeCore>,
    val barnetilleggBMPeriodeListe: List<BarnetilleggBMPeriodeCore>,
    val barnetilleggForsvaretPeriodeListe: List<BarnetilleggForsvaretPeriodeCore>,
    var sjablonPeriodeListe: List<SjablonPeriodeCore>
)

data class BidragsevnePeriodeCore(
    val bidragsevneDatoFraTil: PeriodeCore,
    val bidragsevneBelop: Double,
    val tjuefemProsentInntekt: Double
)

data class KostnadsberegnetBidragPeriodeCore(
    val kostnadsberegnetBidragDatoFraTil: PeriodeCore,
    val kostnadsberegnetBidragBelop: Double
)

data class SamvaersfradragPeriodeCore(
    val samvaersfradragDatoFraTil: PeriodeCore,
    val samvaersfradrag: Double?
)

data class BarnetilleggBPPeriodeCore(
    val barnetilleggBPDatoFraTil: PeriodeCore,
    val barnetilleggBPBelop: Double,
    val barnetilleggBPSkattProsent: Double
)

data class BarnetilleggBMPeriodeCore(
    val barnetilleggBMDatoFraTil: PeriodeCore,
    val barnetilleggBMBelop: Double,
    val barnetilleggBMSkattProsent: Double
)

data class BarnetilleggForsvaretPeriodeCore(
    val barnetilleggForsvaretDatoFraTil: PeriodeCore,
    val barnetilleggForsvaretAntallBarn: Int,
    val barnetilleggForsvaretIPeriode: Boolean
)


// Resultatperiode
data class BeregnBarnebidragResultatCore(
    val resultatPeriodeListe: List<ResultatPeriodeCore>,
    val avvikListe: List<AvvikCore>
)

data class ResultatPeriodeCore(
    val resultatDatoFraTil: PeriodeCore,
    val resultatBeregning: ResultatBeregningCore,
    val resultatGrunnlag: ResultatGrunnlagCore
)

data class ResultatBeregningCore(
    val resultatBarnebidragBelop: Double
)

// Grunnlag beregning
data class ResultatGrunnlagCore(
    val bidragsevneBelop: Double,
    val kostnadsberegnetBidragBelop: Double,
    val samvaersfradrag: Double,
    val barnetilleggBP: BarnetilleggBPCore,
    val barnetilleggBM: BarnetilleggBMCore,
    val barnetilleggForsvaret: BarnetilleggForsvaretCore,
    val sjablonListe: List<Sjablon>)

data class BarnetilleggBPCore(
    val barnetilleggBPBelop: Double,
    val barnetilleggBPSkattProsent: Double
)

data class BarnetilleggBMCore(
    val barnetilleggBMBelop: Double,
    val barnetilleggBMSkattProsent: Double
)

data class BarnetilleggForsvaretCore(
    val barnetilleggForsvaretJaNei: Boolean,
    val antallBarn: Int
)