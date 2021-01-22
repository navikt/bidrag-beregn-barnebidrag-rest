package no.nav.bidrag.beregn.barnebidrag.rest;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import no.nav.bidrag.beregn.barnebidrag.dto.AndreLopendeBidragCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BPsAndelUnderholdskostnadCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BarnetilleggCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BeregnBarnebidragResultatCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BidragsevneCore;
import no.nav.bidrag.beregn.barnebidrag.dto.GrunnlagBeregningPerBarnCore;
import no.nav.bidrag.beregn.barnebidrag.dto.GrunnlagBeregningPeriodisertCore;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Barnetilsyn;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Bidragsevne;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Forbruksutgifter;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.MaksFradrag;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.MaksTilsyn;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Samvaersfradrag;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Sjablontall;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.TrinnvisSkattesats;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.AndreLopendeBidrag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.AndreLopendeBidragBPPeriode;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.AntallBarnIEgetHusholdPeriode;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BPAndelUnderholdskostnad;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.Barnetillegg;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BarnetilleggForsvaretBPPeriode;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BarnetilleggPeriode;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BarnetilsynMedStonadPeriode;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBMNettoBarnetilsynGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBMNettoBarnetilsynResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBMUnderholdskostnadGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBMUnderholdskostnadResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBPAndelUnderholdskostnadResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBPBidragsevneGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBPBidragsevneResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBPKostnadsberegnetBidragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBPSamvaersfradragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBPSamvaersfradragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBarnebidragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BidragsevneGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BostatusPeriode;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.DeltBostedBPPeriode;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.FaktiskUtgift;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.FaktiskUtgiftPeriode;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ForpleiningUtgiftPeriode;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.Inntekt;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.InntektBM;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.InntektBMPeriode;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.InntektBPBMGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.InntektPeriode;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.Periode;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatBeregningBPAndelUnderholdskostnad;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatBeregningBarnebidrag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatBeregningBidragsevne;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatBeregningKostnadsberegnetBidrag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatBeregningNettoBarnetilsyn;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatBeregningSamvaersfradrag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatBeregningUnderholdskostnad;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatGrunnlagBPAndelUnderholdskostnad;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatGrunnlagBarnebidrag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatGrunnlagBidragsevne;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatGrunnlagKostnadsberegnetBidrag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatGrunnlagNettoBarnetilsyn;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatGrunnlagPerBarn;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatGrunnlagSamvaersfradrag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatGrunnlagUnderholdskostnad;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatPeriodeBPAndelUnderholdskostnad;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatPeriodeBarnebidrag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatPeriodeBidragsevne;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatPeriodeKostnadsberegnetBidrag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatPeriodeNettoBarnetilsyn;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatPeriodeSamvaersfradrag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatPeriodeUnderholdskostnad;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.SaerfradragPeriode;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.SamvaersklassePeriode;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.SkatteklassePeriode;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.Soknadsbarn;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.SoknadsbarnGrunnlag;
import no.nav.bidrag.beregn.bidragsevne.dto.BeregnBidragsevneResultatCore;
import no.nav.bidrag.beregn.bidragsevne.dto.InntektCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnBPsAndelUnderholdskostnadResultatCore;
import no.nav.bidrag.beregn.felles.dto.AvvikCore;
import no.nav.bidrag.beregn.felles.dto.PeriodeCore;
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.BeregnKostnadsberegnetBidragResultatCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnNettoBarnetilsynResultatCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.FaktiskUtgiftCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnSamvaersfradragResultatCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadResultatCore;

public class TestUtil {

  // Total barnebidrag
  public static BeregnTotalBarnebidragGrunnlag byggTotalBarnebidragGrunnlag() {
    return byggTotalBarnebidragGrunnlag("");
  }

  public static BeregnTotalBarnebidragGrunnlag byggTotalBarnebidragGrunnlagMedKnytningBMInntektSoknadsbarn(String soknadsbarnPersonId) {
    return byggTotalBarnebidragGrunnlag(soknadsbarnPersonId);
  }

  public static BeregnTotalBarnebidragGrunnlag byggTotalBarnebidragGrunnlagUtenBeregnDatoFra() {
    return byggTotalBarnebidragGrunnlag("beregnDatoFra");
  }

  public static BeregnTotalBarnebidragGrunnlag byggTotalBarnebidragGrunnlagUtenBeregnDatoTil() {
    return byggTotalBarnebidragGrunnlag("beregnDatoTil");
  }

  public static BeregnTotalBarnebidragGrunnlag byggTotalBarnebidragGrunnlagUtenSoknadsbarnGrunnlag() {
    return byggTotalBarnebidragGrunnlag("soknadsbarnGrunnlag");
  }

  public static BeregnTotalBarnebidragGrunnlag byggTotalBarnebidragGrunnlagUtenInntektBPBMGrunnlag() {
    return byggTotalBarnebidragGrunnlag("inntektBPBMGrunnlag");
  }

  public static BeregnTotalBarnebidragGrunnlag byggTotalBarnebidragGrunnlagUtenBeregnBPBidragsevneGrunnlag() {
    return byggTotalBarnebidragGrunnlag("beregnBPBidragsevneGrunnlag");
  }

  public static BeregnTotalBarnebidragGrunnlag byggTotalBarnebidragGrunnlagUtenBeregnBMNettoBarnetilsynGrunnlag() {
    return byggTotalBarnebidragGrunnlag("beregnBMNettoBarnetilsynGrunnlag");
  }

  public static BeregnTotalBarnebidragGrunnlag byggTotalBarnebidragGrunnlagUtenBeregnBMUnderholdskostnadGrunnlag() {
    return byggTotalBarnebidragGrunnlag("beregnBMUnderholdskostnadGrunnlag");
  }

  public static BeregnTotalBarnebidragGrunnlag byggTotalBarnebidragGrunnlagUtenBeregnBPSamvaersfradragGrunnlag() {
    return byggTotalBarnebidragGrunnlag("beregnBPSamvaersfradragGrunnlag");
  }

  public static BeregnTotalBarnebidragGrunnlag byggTotalBarnebidragGrunnlagUtenBeregnBarnebidragGrunnlag() {
    return byggTotalBarnebidragGrunnlag("beregnBarnebidragGrunnlag");
  }

  public static BeregnTotalBarnebidragGrunnlag byggSoknadsbarnGrunnlagUtenSoknadsbarnListe() {
    return byggTotalBarnebidragGrunnlag("", "soknadsbarnListe", "", "", "", "", "", "");
  }

  public static BeregnTotalBarnebidragGrunnlag byggInntektBPBMGrunnlagUtenInntektBPPeriodeListe() {
    return byggTotalBarnebidragGrunnlag("", "", "inntektBPPeriodeListe", "", "", "", "", "");
  }

  public static BeregnTotalBarnebidragGrunnlag byggInntektBPBMGrunnlagUtenInntektBMPeriodeListe() {
    return byggTotalBarnebidragGrunnlag("", "", "inntektBMPeriodeListe", "", "", "", "", "");
  }

  public static BeregnTotalBarnebidragGrunnlag byggBidragsevneGrunnlagUtenSkatteklassePeriodeListe() {
    return byggTotalBarnebidragGrunnlag("", "", "", "skatteklassePeriodeListe", "", "", "", "");
  }

  public static BeregnTotalBarnebidragGrunnlag byggBidragsevneGrunnlagUtenBostatusPeriodeListe() {
    return byggTotalBarnebidragGrunnlag("", "", "", "bostatusPeriodeListe", "", "", "", "");
  }

  public static BeregnTotalBarnebidragGrunnlag byggBidragsevneGrunnlagUtenAntallBarnIEgetHusholdPeriodeListe() {
    return byggTotalBarnebidragGrunnlag("", "", "", "antallBarnIEgetHusholdPeriodeListe", "", "", "", "");
  }

  public static BeregnTotalBarnebidragGrunnlag byggBidragsevneGrunnlagUtenSaerfradragPeriodeListe() {
    return byggTotalBarnebidragGrunnlag("", "", "", "saerfradragPeriodeListe", "", "", "", "");
  }

  public static BeregnTotalBarnebidragGrunnlag byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftPeriodeListe() {
    return byggTotalBarnebidragGrunnlag("", "", "", "", "faktiskUtgiftPeriodeListe", "", "", "");
  }

  public static BeregnTotalBarnebidragGrunnlag byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadPeriodeListe() {
    return byggTotalBarnebidragGrunnlag("", "", "", "", "", "barnetilsynMedStonadPeriodeListe", "", "");
  }

  public static BeregnTotalBarnebidragGrunnlag byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftPeriodeListe() {
    return byggTotalBarnebidragGrunnlag("", "", "", "", "", "forpleiningUtgiftPeriodeListe", "", "");
  }

  public static BeregnTotalBarnebidragGrunnlag byggSamvaersfradragGrunnlagUtenSamvaersklassePeriodeListe() {
    return byggTotalBarnebidragGrunnlag("", "", "", "", "", "", "samvaersklassePeriodeListe", "");
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBPPeriodeListe() {
    return byggTotalBarnebidragGrunnlag("", "", "", "", "", "", "", "barnetilleggBPPeriodeListe");
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBMPeriodeListe() {
    return byggTotalBarnebidragGrunnlag("", "", "", "", "", "", "", "barnetilleggBMPeriodeListe");
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggForsvaretBPPeriodeListe() {
    return byggTotalBarnebidragGrunnlag("", "", "", "", "", "", "", "barnetilleggForsvaretBPPeriodeListe");
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenDeltBostedBPPeriodeListe() {
    return byggTotalBarnebidragGrunnlag("", "", "", "", "", "", "", "deltBostedBPPeriodeListe");
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenAndreLopendeBidragBPPeriodeListe() {
    return byggTotalBarnebidragGrunnlag("", "", "", "", "", "", "", "andreLopendeBidragBPPeriodeListe");
  }


  // Søknadsbarn
  public static SoknadsbarnGrunnlag byggSoknadsbarnGrunnlag() {
    return byggSoknadsbarnGrunnlag("");
  }

  public static SoknadsbarnGrunnlag byggSoknadsbarnGrunnlagUtenSoknadsbarnFodselsdato() {
    return byggSoknadsbarnGrunnlag("soknadsbarnFodselsdato");
  }

  public static SoknadsbarnGrunnlag byggSoknadsbarnGrunnlagUtenSoknadsbarnPersonId() {
    return byggSoknadsbarnGrunnlag("soknadsbarnPersonId");
  }

  public static SoknadsbarnGrunnlag byggSoknadsbarnGrunnlagUtenInntektPeriodeListe() {
    return byggSoknadsbarnGrunnlag("inntektPeriodeListe");
  }

  public static SoknadsbarnGrunnlag byggSoknadsbarnGrunnlagUtenInntektDatoFraTil() {
    return byggSoknadsbarnGrunnlag("inntektDatoFraTil");
  }

  public static SoknadsbarnGrunnlag byggSoknadsbarnGrunnlagUtenInntektDatoFra() {
    return byggSoknadsbarnGrunnlag("inntektDatoFra");
  }

  public static SoknadsbarnGrunnlag byggSoknadsbarnGrunnlagUtenInntektDatoTil() {
    return byggSoknadsbarnGrunnlag("inntektDatoTil");
  }

  public static SoknadsbarnGrunnlag byggSoknadsbarnGrunnlagUtenInntektType() {
    return byggSoknadsbarnGrunnlag("inntektType");
  }

  public static SoknadsbarnGrunnlag byggSoknadsbarnGrunnlagUtenInntektBelop() {
    return byggSoknadsbarnGrunnlag("inntektBelop");
  }


  // Inntekt
  public static InntektBPBMGrunnlag byggInntektGrunnlag() {
    return byggInntektGrunnlag("");
  }

  public static InntektBPBMGrunnlag byggInntektGrunnlagUtenInntektBPDatoFraTil() {
    return byggInntektGrunnlag("inntektBPDatoFraTil");
  }

  public static InntektBPBMGrunnlag byggInntektGrunnlagUtenInntektBPDatoFra() {
    return byggInntektGrunnlag("inntektBPDatoFra");
  }

  public static InntektBPBMGrunnlag byggInntektGrunnlagUtenInntektBPDatoTil() {
    return byggInntektGrunnlag("inntektBPDatoTil");
  }

  public static InntektBPBMGrunnlag byggInntektGrunnlagUtenInntektBPType() {
    return byggInntektGrunnlag("inntektBPType");
  }

  public static InntektBPBMGrunnlag byggInntektGrunnlagUtenInntektBPBelop() {
    return byggInntektGrunnlag("inntektBPBelop");
  }

  public static InntektBPBMGrunnlag byggInntektGrunnlagUtenInntektBMDatoFraTil() {
    return byggInntektGrunnlag("inntektBMDatoFraTil");
  }

  public static InntektBPBMGrunnlag byggInntektGrunnlagUtenInntektBMDatoFra() {
    return byggInntektGrunnlag("inntektBMDatoFra");
  }

  public static InntektBPBMGrunnlag byggInntektGrunnlagUtenInntektBMDatoTil() {
    return byggInntektGrunnlag("inntektBMDatoTil");
  }

  public static InntektBPBMGrunnlag byggInntektGrunnlagUtenInntektBMType() {
    return byggInntektGrunnlag("inntektBMType");
  }

  public static InntektBPBMGrunnlag byggInntektGrunnlagUtenInntektBMBelop() {
    return byggInntektGrunnlag("inntektBMBelop");
  }

  public static InntektBPBMGrunnlag byggInntektGrunnlagUtenInntektBMDeltFordel() {
    return byggInntektGrunnlag("inntektBMDeltFordel");
  }

  public static InntektBPBMGrunnlag byggInntektGrunnlagUtenInntektBMSkatteklasse2() {
    return byggInntektGrunnlag("inntektBMSkatteklasse2");
  }


  // Bidragsevne
  public static BeregnBPBidragsevneGrunnlag byggBidragsevneGrunnlag() {
    return byggBidragsevneGrunnlag("");
  }

  public static BeregnBPBidragsevneGrunnlag byggBidragsevneGrunnlagUtenSkatteklasseDatoFraTil() {
    return byggBidragsevneGrunnlag("skatteklasseDatoFraTil");
  }

  public static BeregnBPBidragsevneGrunnlag byggBidragsevneGrunnlagUtenSkatteklasseDatoFra() {
    return byggBidragsevneGrunnlag("skatteklasseDatoFra");
  }

  public static BeregnBPBidragsevneGrunnlag byggBidragsevneGrunnlagUtenSkatteklasseDatoTil() {
    return byggBidragsevneGrunnlag("skatteklasseDatoTil");
  }

  public static BeregnBPBidragsevneGrunnlag byggBidragsevneGrunnlagUtenSkatteklasseId() {
    return byggBidragsevneGrunnlag("skatteklasseId");
  }

  public static BeregnBPBidragsevneGrunnlag byggBidragsevneGrunnlagUtenBostatusDatoFraTil() {
    return byggBidragsevneGrunnlag("bostatusDatoFraTil");
  }

  public static BeregnBPBidragsevneGrunnlag byggBidragsevneGrunnlagUtenBostatusDatoFra() {
    return byggBidragsevneGrunnlag("bostatusDatoFra");
  }

  public static BeregnBPBidragsevneGrunnlag byggBidragsevneGrunnlagUtenBostatusDatoTil() {
    return byggBidragsevneGrunnlag("bostatusDatoTil");
  }

  public static BeregnBPBidragsevneGrunnlag byggBidragsevneGrunnlagUtenBostatusKode() {
    return byggBidragsevneGrunnlag("bostatusKode");
  }

  public static BeregnBPBidragsevneGrunnlag byggBidragsevneGrunnlagUtenAntallBarnIEgetHusholdDatoFraTil() {
    return byggBidragsevneGrunnlag("antallBarnIEgetHusholdDatoFraTil");
  }

  public static BeregnBPBidragsevneGrunnlag byggBidragsevneGrunnlagUtenAntallBarnIEgetHusholdDatoFra() {
    return byggBidragsevneGrunnlag("antallBarnIEgetHusholdDatoFra");
  }

  public static BeregnBPBidragsevneGrunnlag byggBidragsevneGrunnlagUtenAntallBarnIEgetHusholdDatoTil() {
    return byggBidragsevneGrunnlag("antallBarnIEgetHusholdDatoTil");
  }

  public static BeregnBPBidragsevneGrunnlag byggBidragsevneGrunnlagUtenAntallBarn() {
    return byggBidragsevneGrunnlag("antallBarn");
  }

  public static BeregnBPBidragsevneGrunnlag byggBidragsevneGrunnlagUtenSaerfradragDatoFraTil() {
    return byggBidragsevneGrunnlag("saerfradragDatoFraTil");
  }

  public static BeregnBPBidragsevneGrunnlag byggBidragsevneGrunnlagUtenSaerfradragDatoFra() {
    return byggBidragsevneGrunnlag("saerfradragDatoFra");
  }

  public static BeregnBPBidragsevneGrunnlag byggBidragsevneGrunnlagUtenSaerfradragDatoTil() {
    return byggBidragsevneGrunnlag("saerfradragDatoTil");
  }

  public static BeregnBPBidragsevneGrunnlag byggBidragsevneGrunnlagUtenSaerfradragKode() {
    return byggBidragsevneGrunnlag("saerfradragKode");
  }


  // Netto barnetilsyn
  public static BeregnBMNettoBarnetilsynGrunnlag byggNettoBarnetilsynGrunnlag() {
    return byggNettoBarnetilsynGrunnlag("");
  }

  public static BeregnBMNettoBarnetilsynGrunnlag byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftDatoFraTil() {
    return byggNettoBarnetilsynGrunnlag("faktiskUtgiftDatoFraTil");
  }

  public static BeregnBMNettoBarnetilsynGrunnlag byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftDatoFra() {
    return byggNettoBarnetilsynGrunnlag("faktiskUtgiftDatoFra");
  }

  public static BeregnBMNettoBarnetilsynGrunnlag byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftDatoTil() {
    return byggNettoBarnetilsynGrunnlag("faktiskUtgiftDatoTil");
  }

  public static BeregnBMNettoBarnetilsynGrunnlag byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftSoknadsbarnPersonId() {
    return byggNettoBarnetilsynGrunnlag("faktiskUtgiftSoknadsbarnPersonId");
  }

  public static BeregnBMNettoBarnetilsynGrunnlag byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftBelop() {
    return byggNettoBarnetilsynGrunnlag("faktiskUtgiftBelop");
  }


  // Underholdskostnad
  public static BeregnBMUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlag() {
    return byggUnderholdskostnadGrunnlag("");
  }

  public static BeregnBMUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadDatoFraTil() {
    return byggUnderholdskostnadGrunnlag("barnetilsynMedStonadDatoFraTil");
  }

  public static BeregnBMUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadDatoFra() {
    return byggUnderholdskostnadGrunnlag("barnetilsynMedStonadDatoFra");
  }

  public static BeregnBMUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadDatoTil() {
    return byggUnderholdskostnadGrunnlag("barnetilsynMedStonadDatoTil");
  }

  public static BeregnBMUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadSoknadsbarnPersonId() {
    return byggUnderholdskostnadGrunnlag("barnetilsynMedStonadSoknadsbarnPersonId");
  }

  public static BeregnBMUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadTilsynType() {
    return byggUnderholdskostnadGrunnlag("barnetilsynMedStonadTilsynType");
  }

  public static BeregnBMUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadStonadType() {
    return byggUnderholdskostnadGrunnlag("barnetilsynMedStonadStonadType");
  }

  public static BeregnBMUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftDatoFraTil() {
    return byggUnderholdskostnadGrunnlag("forpleiningUtgiftDatoFraTil");
  }

  public static BeregnBMUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftDatoFra() {
    return byggUnderholdskostnadGrunnlag("forpleiningUtgiftDatoFra");
  }

  public static BeregnBMUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftDatoTil() {
    return byggUnderholdskostnadGrunnlag("forpleiningUtgiftDatoTil");
  }

  public static BeregnBMUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftSoknadsbarnPersonId() {
    return byggUnderholdskostnadGrunnlag("forpleiningUtgiftSoknadsbarnPersonId");
  }

  public static BeregnBMUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftBelop() {
    return byggUnderholdskostnadGrunnlag("forpleiningUtgiftBelop");
  }


  // Samværsfradrag
  public static BeregnBPSamvaersfradragGrunnlag byggSamvaersfradragGrunnlag() {
    return byggSamvaersfradragGrunnlag("");
  }

  public static BeregnBPSamvaersfradragGrunnlag byggSamvaersfradragGrunnlagUtenSamvaersklasseDatoFraTil() {
    return byggSamvaersfradragGrunnlag("samvaersklasseDatoFraTil");
  }

  public static BeregnBPSamvaersfradragGrunnlag byggSamvaersfradragGrunnlagUtenSamvaersklasseDatoFra() {
    return byggSamvaersfradragGrunnlag("samvaersklasseDatoFra");
  }

  public static BeregnBPSamvaersfradragGrunnlag byggSamvaersfradragGrunnlagUtenSamvaersklasseDatoTil() {
    return byggSamvaersfradragGrunnlag("samvaersklasseDatoTil");
  }

  public static BeregnBPSamvaersfradragGrunnlag byggSamvaersfradragGrunnlagUtenSamvaersklasseSoknadsbarnPersonId() {
    return byggSamvaersfradragGrunnlag("samvaersklasseSoknadsbarnPersonId");
  }

  public static BeregnBPSamvaersfradragGrunnlag byggSamvaersfradragGrunnlagUtenSamvaersklasseId() {
    return byggSamvaersfradragGrunnlag("samvaersklasseId");
  }


  // Barnebidrag
  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlag() {
    return byggBarnebidragGrunnlag("");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBPDatoFraTil() {
    return byggBarnebidragGrunnlag("barnetilleggBPDatoFraTil");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBPDatoFra() {
    return byggBarnebidragGrunnlag("barnetilleggBPDatoFra");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBPDatoTil() {
    return byggBarnebidragGrunnlag("barnetilleggBPDatoTil");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBPSoknadsbarnPersonId() {
    return byggBarnebidragGrunnlag("barnetilleggBPSoknadsbarnPersonId");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBPBruttoBelop() {
    return byggBarnebidragGrunnlag("barnetilleggBPBruttoBelop");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBPSkattProsent() {
    return byggBarnebidragGrunnlag("barnetilleggBPSkattProsent");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBMDatoFraTil() {
    return byggBarnebidragGrunnlag("barnetilleggBMDatoFraTil");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBMDatoFra() {
    return byggBarnebidragGrunnlag("barnetilleggBMDatoFra");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBMDatoTil() {
    return byggBarnebidragGrunnlag("barnetilleggBMDatoTil");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBMSoknadsbarnPersonId() {
    return byggBarnebidragGrunnlag("barnetilleggBMSoknadsbarnPersonId");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBMBruttoBelop() {
    return byggBarnebidragGrunnlag("barnetilleggBMBruttoBelop");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBMSkattProsent() {
    return byggBarnebidragGrunnlag("barnetilleggBMSkattProsent");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggForsvaretDatoFraTil() {
    return byggBarnebidragGrunnlag("barnetilleggForsvaretDatoFraTil");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggForsvaretDatoFra() {
    return byggBarnebidragGrunnlag("barnetilleggForsvaretDatoFra");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggForsvaretDatoTil() {
    return byggBarnebidragGrunnlag("barnetilleggForsvaretDatoTil");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggForsvaretIPeriode() {
    return byggBarnebidragGrunnlag("barnetilleggForsvaretIPeriode");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenDeltBostedDatoFraTil() {
    return byggBarnebidragGrunnlag("deltBostedDatoFraTil");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenDeltBostedDatoFra() {
    return byggBarnebidragGrunnlag("deltBostedDatoFra");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenDeltBostedDatoTil() {
    return byggBarnebidragGrunnlag("deltBostedDatoTil");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenDeltBostedSoknadsbarnPersonId() {
    return byggBarnebidragGrunnlag("deltBostedSoknadsbarnPersonId");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenDeltBostedIPeriode() {
    return byggBarnebidragGrunnlag("deltBostedIPeriode");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenAndreLopendeBidragDatoFraTil() {
    return byggBarnebidragGrunnlag("andreLopendeBidragDatoFraTil");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenAndreLopendeBidragDatoFra() {
    return byggBarnebidragGrunnlag("andreLopendeBidragDatoFra");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenAndreLopendeBidragDatoTil() {
    return byggBarnebidragGrunnlag("andreLopendeBidragDatoTil");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenAndreLopendeBidragBarnPersonId() {
    return byggBarnebidragGrunnlag("andreLopendeBidragBarnPersonId");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenAndreLopendeBidragBidragBelop() {
    return byggBarnebidragGrunnlag("andreLopendeBidragBidragBelop");
  }

  public static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlagUtenAndreLopendeBidragSamvaersfradragBelop() {
    return byggBarnebidragGrunnlag("andreLopendeBidragSamvaersfradragBelop");
  }


  // Bygger opp BeregnTotalBarnebidragGrunnlag (felles grunnlag for alle delberegninger)
  private static BeregnTotalBarnebidragGrunnlag byggTotalBarnebidragGrunnlag(String nullVerdi) {
    // "Jukser" litt hvis nullVerdi er numerisk. Verdien brukes da til å knytte inntekt BM til nullVerdi (= søknadsbarn person-id)
    if (StringUtils.isNumeric(nullVerdi)) {
      return byggTotalBarnebidragGrunnlag("", "", nullVerdi, "", "", "", "", "");
    } else {
      return byggTotalBarnebidragGrunnlag(nullVerdi, "", "", "", "", "", "", "");
    }
  }

  private static BeregnTotalBarnebidragGrunnlag byggTotalBarnebidragGrunnlag(String nullVerdi, String soknadsbarnVerdi, String inntektVerdi,
      String bidragsevneVerdi, String nettoBarnetilsynVerdi, String underholdskostnadVerdi, String samvaersfradragVerdi, String barnebidragVerdi) {
    var beregnDatoFra = (nullVerdi.equals("beregnDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var beregnDatoTil = (nullVerdi.equals("beregnDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var soknadsbarnGrunnlag = (nullVerdi.equals("soknadsbarnGrunnlag") ? null : byggSoknadsbarnGrunnlag(soknadsbarnVerdi));
    var inntektGrunnlag = (nullVerdi.equals("inntektBPBMGrunnlag") ? null : byggInntektGrunnlag(inntektVerdi));
    var beregnBidragsevneGrunnlag = (nullVerdi.equals("beregnBPBidragsevneGrunnlag") ? null : byggBidragsevneGrunnlag(bidragsevneVerdi));
    var beregnNettoBarnetilsynGrunnlag = (nullVerdi.equals("beregnBMNettoBarnetilsynGrunnlag") ? null
        : byggNettoBarnetilsynGrunnlag(nettoBarnetilsynVerdi));
    var beregnUnderholdskostnadGrunnlag = (nullVerdi.equals("beregnBMUnderholdskostnadGrunnlag") ? null
        : byggUnderholdskostnadGrunnlag(underholdskostnadVerdi));
    var beregnSamvaersfradragGrunnlag = (nullVerdi.equals("beregnBPSamvaersfradragGrunnlag") ? null
        : byggSamvaersfradragGrunnlag(samvaersfradragVerdi));
    var beregnBarnebidragGrunnlag = (nullVerdi.equals("beregnBarnebidragGrunnlag") ? null
        : byggBarnebidragGrunnlag(barnebidragVerdi));

    return new BeregnTotalBarnebidragGrunnlag(beregnDatoFra, beregnDatoTil, soknadsbarnGrunnlag, inntektGrunnlag, beregnBidragsevneGrunnlag,
        beregnNettoBarnetilsynGrunnlag, beregnUnderholdskostnadGrunnlag, beregnSamvaersfradragGrunnlag, beregnBarnebidragGrunnlag);
  }


  // Bygger opp SoknadsbarnGrunnlag
  private static SoknadsbarnGrunnlag byggSoknadsbarnGrunnlag(String nullVerdi) {
    var soknadsbarnFodselsdato = (nullVerdi.equals("soknadsbarnFodselsdato") ? null : LocalDate.parse("2010-01-01"));
    var soknadsbarnPersonId = (nullVerdi.equals("soknadsbarnPersonId") ? null : 1);

    List<Soknadsbarn> soknadsbarnListe;
    if (nullVerdi.equals("soknadsbarnListe")) {
      soknadsbarnListe = null;
    } else {
      Soknadsbarn soknadsbarn;
      if (nullVerdi.equals("inntektPeriodeListe")) {
        soknadsbarn = new Soknadsbarn(soknadsbarnFodselsdato, soknadsbarnPersonId, null);
      } else {
        soknadsbarn = new Soknadsbarn(soknadsbarnFodselsdato, soknadsbarnPersonId, singletonList(byggSoknadsbarnInntektPeriode(nullVerdi)));
      }
      soknadsbarnListe = singletonList(soknadsbarn);
    }

    return new SoknadsbarnGrunnlag(soknadsbarnListe);
  }

  // Bygger opp InntektPeriode for søknadsbarn
  private static InntektPeriode byggSoknadsbarnInntektPeriode(String nullVerdi) {
    var inntektDatoFra = (nullVerdi.equals("inntektDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var inntektDatoTil = (nullVerdi.equals("inntektDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var inntektType = (nullVerdi.equals("inntektType") ? null : "INNTEKTSOPPL_ARBEIDSGIVER");
    var inntektBelop = (nullVerdi.equals("inntektBelop") ? null : BigDecimal.valueOf(100));

    InntektPeriode inntektPeriode;
    if (nullVerdi.equals("inntektDatoFraTil")) {
      inntektPeriode = new InntektPeriode(null, inntektType, inntektBelop);
    } else {
      inntektPeriode = new InntektPeriode(new Periode(inntektDatoFra, inntektDatoTil), inntektType, inntektBelop);
    }

    return inntektPeriode;
  }


  // Bygger opp InntektBPBMGrunnlag
  private static InntektBPBMGrunnlag byggInntektGrunnlag(String nullVerdi) {
    var inntektBPDatoFra = (nullVerdi.equals("inntektBPDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var inntektBPDatoTil = (nullVerdi.equals("inntektBPDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var inntektBPType = (nullVerdi.equals("inntektBPType") ? null : "INNTEKTSOPPL_ARBEIDSGIVER");
    var inntektBPBelop = (nullVerdi.equals("inntektBPBelop") ? null : BigDecimal.valueOf(100));
    var inntektBMDatoFra = (nullVerdi.equals("inntektBMDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var inntektBMDatoTil = (nullVerdi.equals("inntektBMDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var inntektBMType = (nullVerdi.equals("inntektBMType") ? null : "INNTEKTSOPPL_ARBEIDSGIVER");
    var inntektBMBelop = (nullVerdi.equals("inntektBMBelop") ? null : BigDecimal.valueOf(100));
    var inntektBMDeltFordel = (nullVerdi.equals("inntektBMDeltFordel") ? null : false);
    var inntektBMSkatteklasse2 = (nullVerdi.equals("inntektBMSkatteklasse2") ? null : false);

    List<InntektPeriode> inntektBPPeriodeListe;
    if (nullVerdi.equals("inntektBPPeriodeListe")) {
      inntektBPPeriodeListe = null;
    } else {
      InntektPeriode inntektBPPeriode;
      if (nullVerdi.equals("inntektBPDatoFraTil")) {
        inntektBPPeriode = new InntektPeriode(null, inntektBPType, inntektBPBelop);
      } else {
        inntektBPPeriode = new InntektPeriode(new Periode(inntektBPDatoFra, inntektBPDatoTil), inntektBPType, inntektBPBelop);
      }
      inntektBPPeriodeListe = singletonList(inntektBPPeriode);
    }

    List<InntektBMPeriode> inntektBMPeriodeListe;
    if (nullVerdi.equals("inntektBMPeriodeListe")) {
      inntektBMPeriodeListe = null;
    } else {
      InntektBMPeriode inntektBMPeriode;
      if (nullVerdi.equals("inntektBMDatoFraTil")) {
        inntektBMPeriode = new InntektBMPeriode(null, inntektBMType, inntektBMBelop, null, inntektBMDeltFordel, inntektBMSkatteklasse2);
      } else if (StringUtils.isNumeric(nullVerdi)) {
        inntektBMPeriode = new InntektBMPeriode(new Periode(inntektBMDatoFra, inntektBMDatoTil), inntektBMType, inntektBMBelop,
            Integer.parseInt(nullVerdi), inntektBMDeltFordel, inntektBMSkatteklasse2);
      } else {
        inntektBMPeriode = new InntektBMPeriode(new Periode(inntektBMDatoFra, inntektBMDatoTil), inntektBMType, inntektBMBelop, null,
            inntektBMDeltFordel, inntektBMSkatteklasse2);
      }
      inntektBMPeriodeListe = singletonList(inntektBMPeriode);
    }

    return new InntektBPBMGrunnlag(inntektBPPeriodeListe, inntektBMPeriodeListe);
  }


  // Bygger opp BeregnBPBidragsevneGrunnlag
  private static BeregnBPBidragsevneGrunnlag byggBidragsevneGrunnlag(String nullVerdi) {
    var skatteklasseDatoFra = (nullVerdi.equals("skatteklasseDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var skatteklasseDatoTil = (nullVerdi.equals("skatteklasseDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var skatteklasseId = (nullVerdi.equals("skatteklasseId") ? null : 1);
    var bostatusDatoFra = (nullVerdi.equals("bostatusDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var bostatusDatoTil = (nullVerdi.equals("bostatusDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var bostatusKode = (nullVerdi.equals("bostatusKode") ? null : "MED_ANDRE");
    var antallBarnIEgetHusholdDatoFra = (nullVerdi.equals("antallBarnIEgetHusholdDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var antallBarnIEgetHusholdDatoTil = (nullVerdi.equals("antallBarnIEgetHusholdDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var antallBarn = (nullVerdi.equals("antallBarn") ? null : BigDecimal.ONE);
    var saerfradragDatoFra = (nullVerdi.equals("saerfradragDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var saerfradragDatoTil = (nullVerdi.equals("saerfradragDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var saerfradragKode = (nullVerdi.equals("saerfradragKode") ? null : "HELT");

    List<SkatteklassePeriode> skatteklassePeriodeListe;
    if (nullVerdi.equals("skatteklassePeriodeListe")) {
      skatteklassePeriodeListe = null;
    } else {
      SkatteklassePeriode skatteklassePeriode;
      if (nullVerdi.equals("skatteklasseDatoFraTil")) {
        skatteklassePeriode = new SkatteklassePeriode(null, skatteklasseId);
      } else {
        skatteklassePeriode = new SkatteklassePeriode(new Periode(skatteklasseDatoFra, skatteklasseDatoTil), skatteklasseId);
      }
      skatteklassePeriodeListe = singletonList(skatteklassePeriode);
    }

    List<BostatusPeriode> bostatusPeriodeListe;
    if (nullVerdi.equals("bostatusPeriodeListe")) {
      bostatusPeriodeListe = null;
    } else {
      BostatusPeriode bostatusPeriode;
      if (nullVerdi.equals("bostatusDatoFraTil")) {
        bostatusPeriode = new BostatusPeriode(null, bostatusKode);
      } else {
        bostatusPeriode = new BostatusPeriode(new Periode(bostatusDatoFra, bostatusDatoTil), bostatusKode);
      }
      bostatusPeriodeListe = singletonList(bostatusPeriode);
    }

    List<AntallBarnIEgetHusholdPeriode> antallBarnIEgetHusholdPeriodeListe;
    if (nullVerdi.equals("antallBarnIEgetHusholdPeriodeListe")) {
      antallBarnIEgetHusholdPeriodeListe = null;
    } else {
      AntallBarnIEgetHusholdPeriode antallBarnIEgetHusholdPeriode;
      if (nullVerdi.equals("antallBarnIEgetHusholdDatoFraTil")) {
        antallBarnIEgetHusholdPeriode = new AntallBarnIEgetHusholdPeriode(null, antallBarn);
      } else {
        antallBarnIEgetHusholdPeriode =
            new AntallBarnIEgetHusholdPeriode(new Periode(antallBarnIEgetHusholdDatoFra, antallBarnIEgetHusholdDatoTil), antallBarn);
      }
      antallBarnIEgetHusholdPeriodeListe = singletonList(antallBarnIEgetHusholdPeriode);
    }

    List<SaerfradragPeriode> saerfradragPeriodeListe;
    if (nullVerdi.equals("saerfradragPeriodeListe")) {
      saerfradragPeriodeListe = null;
    } else {
      SaerfradragPeriode saerfradragPeriode;
      if (nullVerdi.equals("saerfradragDatoFraTil")) {
        saerfradragPeriode = new SaerfradragPeriode(null, saerfradragKode);
      } else {
        saerfradragPeriode = new SaerfradragPeriode(new Periode(saerfradragDatoFra, saerfradragDatoTil), saerfradragKode);
      }
      saerfradragPeriodeListe = singletonList(saerfradragPeriode);
    }

    return new BeregnBPBidragsevneGrunnlag(skatteklassePeriodeListe, bostatusPeriodeListe, antallBarnIEgetHusholdPeriodeListe,
        saerfradragPeriodeListe);
  }


  // Bygger opp BeregnBMNettoBarnetilsynGrunnlag
  private static BeregnBMNettoBarnetilsynGrunnlag byggNettoBarnetilsynGrunnlag(String nullVerdi) {
    var faktiskUtgiftDatoFra = (nullVerdi.equals("faktiskUtgiftDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var faktiskUtgiftDatoTil = (nullVerdi.equals("faktiskUtgiftDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var faktiskUtgiftSoknadsbarnPersonId = (nullVerdi.equals("faktiskUtgiftSoknadsbarnPersonId") ? null : 1);
    var faktiskUtgiftBelop = (nullVerdi.equals("faktiskUtgiftBelop") ? null : BigDecimal.valueOf(1000));

    List<FaktiskUtgiftPeriode> faktiskUtgiftPeriodeListe;
    if (nullVerdi.equals("faktiskUtgiftPeriodeListe")) {
      faktiskUtgiftPeriodeListe = null;
    } else {
      FaktiskUtgiftPeriode faktiskUtgiftPeriode;
      if (nullVerdi.equals("faktiskUtgiftDatoFraTil")) {
        faktiskUtgiftPeriode = new FaktiskUtgiftPeriode(null, faktiskUtgiftSoknadsbarnPersonId, faktiskUtgiftBelop);
      } else {
        faktiskUtgiftPeriode = new FaktiskUtgiftPeriode(new Periode(faktiskUtgiftDatoFra, faktiskUtgiftDatoTil),
            faktiskUtgiftSoknadsbarnPersonId, faktiskUtgiftBelop);
      }
      faktiskUtgiftPeriodeListe = singletonList(faktiskUtgiftPeriode);
    }

    return new BeregnBMNettoBarnetilsynGrunnlag(faktiskUtgiftPeriodeListe);
  }


  // Bygger opp BeregnBMUnderholdskostnadGrunnlag
  private static BeregnBMUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlag(String nullVerdi) {
    var barnetilsynMedStonadDatoFra = (nullVerdi.equals("barnetilsynMedStonadDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var barnetilsynMedStonadDatoTil = (nullVerdi.equals("barnetilsynMedStonadDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var barnetilsynMedStonadSoknadsbarnPersonId = (nullVerdi.equals("barnetilsynMedStonadSoknadsbarnPersonId") ? null : 1);
    var barnetilsynMedStonadTilsynType = (nullVerdi.equals("barnetilsynMedStonadTilsynType") ? null : "DO");
    var barnetilsynMedStonadStonadType = (nullVerdi.equals("barnetilsynMedStonadStonadType") ? null : "64");
    var forpleiningUtgiftDatoFra = (nullVerdi.equals("forpleiningUtgiftDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var forpleiningUtgiftDatoTil = (nullVerdi.equals("forpleiningUtgiftDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var forpleiningUtgiftSoknadsbarnPersonId = (nullVerdi.equals("forpleiningUtgiftSoknadsbarnPersonId") ? null : 1);
    var forpleiningUtgiftBelop = (nullVerdi.equals("forpleiningUtgiftBelop") ? null : BigDecimal.valueOf(1000));

    List<BarnetilsynMedStonadPeriode> barnetilsynMedStonadPeriodeListe;
    if (nullVerdi.equals("barnetilsynMedStonadPeriodeListe")) {
      barnetilsynMedStonadPeriodeListe = null;
    } else {
      BarnetilsynMedStonadPeriode barnetilsynMedStonadPeriode;
      if (nullVerdi.equals("barnetilsynMedStonadDatoFraTil")) {
        barnetilsynMedStonadPeriode = new BarnetilsynMedStonadPeriode(null, barnetilsynMedStonadSoknadsbarnPersonId, barnetilsynMedStonadTilsynType,
            barnetilsynMedStonadStonadType);
      } else {
        barnetilsynMedStonadPeriode = new BarnetilsynMedStonadPeriode(new Periode(barnetilsynMedStonadDatoFra,
            barnetilsynMedStonadDatoTil), barnetilsynMedStonadSoknadsbarnPersonId, barnetilsynMedStonadTilsynType, barnetilsynMedStonadStonadType);
      }
      barnetilsynMedStonadPeriodeListe = singletonList(barnetilsynMedStonadPeriode);
    }

    List<ForpleiningUtgiftPeriode> forpleiningUtgiftPeriodeListe;
    if (nullVerdi.equals("forpleiningUtgiftPeriodeListe")) {
      forpleiningUtgiftPeriodeListe = null;
    } else {
      ForpleiningUtgiftPeriode forpleiningUtgiftPeriode;
      if (nullVerdi.equals("forpleiningUtgiftDatoFraTil")) {
        forpleiningUtgiftPeriode = new ForpleiningUtgiftPeriode(null, forpleiningUtgiftSoknadsbarnPersonId, forpleiningUtgiftBelop);
      } else {
        forpleiningUtgiftPeriode = new ForpleiningUtgiftPeriode(new Periode(forpleiningUtgiftDatoFra, forpleiningUtgiftDatoTil),
            forpleiningUtgiftSoknadsbarnPersonId, forpleiningUtgiftBelop);
      }
      forpleiningUtgiftPeriodeListe = singletonList(forpleiningUtgiftPeriode);
    }

    return new BeregnBMUnderholdskostnadGrunnlag(barnetilsynMedStonadPeriodeListe, forpleiningUtgiftPeriodeListe);
  }


  // Bygger opp BeregnBPSamvaersfradragGrunnlag
  private static BeregnBPSamvaersfradragGrunnlag byggSamvaersfradragGrunnlag(String nullVerdi) {
    var samvaersklasseDatoFra = (nullVerdi.equals("samvaersklasseDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var samvaersklasseDatoTil = (nullVerdi.equals("samvaersklasseDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var samvaersklasseSoknadsbarnPersonId = (nullVerdi.equals("samvaersklasseSoknadsbarnPersonId") ? null : 1);
    var samvaersklasseId = (nullVerdi.equals("samvaersklasseId") ? null : "00");

    List<SamvaersklassePeriode> samvaersklassePeriodeListe;
    if (nullVerdi.equals("samvaersklassePeriodeListe")) {
      samvaersklassePeriodeListe = null;
    } else {
      SamvaersklassePeriode samvaersklassePeriode;
      if (nullVerdi.equals("samvaersklasseDatoFraTil")) {
        samvaersklassePeriode = new SamvaersklassePeriode(null, samvaersklasseSoknadsbarnPersonId, samvaersklasseId);
      } else {
        samvaersklassePeriode = new SamvaersklassePeriode(new Periode(samvaersklasseDatoFra, samvaersklasseDatoTil),
            samvaersklasseSoknadsbarnPersonId, samvaersklasseId);
      }
      samvaersklassePeriodeListe = singletonList(samvaersklassePeriode);
    }

    return new BeregnBPSamvaersfradragGrunnlag(samvaersklassePeriodeListe);
  }


  // Bygger opp BeregnBarnebidragGrunnlag
  private static BeregnBarnebidragGrunnlag byggBarnebidragGrunnlag(String nullVerdi) {
    var barnetilleggBPDatoFra = (nullVerdi.equals("barnetilleggBPDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var barnetilleggBPDatoTil = (nullVerdi.equals("barnetilleggBPDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var barnetilleggBPSoknadsbarnPersonId = (nullVerdi.equals("barnetilleggBPSoknadsbarnPersonId") ? null : 1);
    var barnetilleggBPBelop = (nullVerdi.equals("barnetilleggBPBruttoBelop") ? null : BigDecimal.valueOf(100));
    var barnetilleggBPSkattProsent = (nullVerdi.equals("barnetilleggBPSkattProsent") ? null : BigDecimal.valueOf(25));
    var barnetilleggBMDatoFra = (nullVerdi.equals("barnetilleggBMDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var barnetilleggBMDatoTil = (nullVerdi.equals("barnetilleggBMDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var barnetilleggBMSoknadsbarnPersonId = (nullVerdi.equals("barnetilleggBMSoknadsbarnPersonId") ? null : 1);
    var barnetilleggBMBelop = (nullVerdi.equals("barnetilleggBMBruttoBelop") ? null : BigDecimal.valueOf(100));
    var barnetilleggBMSkattProsent = (nullVerdi.equals("barnetilleggBMSkattProsent") ? null : BigDecimal.valueOf(25));
    var barnetilleggForsvaretDatoFra = (nullVerdi.equals("barnetilleggForsvaretDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var barnetilleggForsvaretDatoTil = (nullVerdi.equals("barnetilleggForsvaretDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var barnetilleggForsvaretIPeriode = (nullVerdi.equals("barnetilleggForsvaretIPeriode") ? null : true);
    var deltBostedDatoFra = (nullVerdi.equals("deltBostedDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var deltBostedDatoTil = (nullVerdi.equals("deltBostedDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var deltBostedSoknadsbarnPersonId = (nullVerdi.equals("deltBostedSoknadsbarnPersonId") ? null : 1);
    var deltBostedIPeriode = (nullVerdi.equals("deltBostedIPeriode") ? null : true);
    var andreLopendeBidragDatoFra = (nullVerdi.equals("andreLopendeBidragDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var andreLopendeBidragDatoTil = (nullVerdi.equals("andreLopendeBidragDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var andreLopendeBidragBarnPersonId = (nullVerdi.equals("andreLopendeBidragBarnPersonId") ? null : 1);
    var andreLopendeBidragBidragBelop = (nullVerdi.equals("andreLopendeBidragBidragBelop") ? null : BigDecimal.valueOf(100));
    var andreLopendeBidragSamvaersfradragBelop = (nullVerdi.equals("andreLopendeBidragSamvaersfradragBelop") ? null : BigDecimal.valueOf(100));

    List<BarnetilleggPeriode> barnetilleggBPPeriodeListe;
    if (nullVerdi.equals("barnetilleggBPPeriodeListe")) {
      barnetilleggBPPeriodeListe = null;
    } else {
      BarnetilleggPeriode barnetilleggBPPeriode;
      if (nullVerdi.equals("barnetilleggBPDatoFraTil")) {
        barnetilleggBPPeriode = new BarnetilleggPeriode(null, barnetilleggBPSoknadsbarnPersonId, barnetilleggBPBelop, barnetilleggBPSkattProsent);
      } else {
        barnetilleggBPPeriode = new BarnetilleggPeriode(new Periode(barnetilleggBPDatoFra, barnetilleggBPDatoTil),
            barnetilleggBPSoknadsbarnPersonId, barnetilleggBPBelop, barnetilleggBPSkattProsent);
      }
      barnetilleggBPPeriodeListe = singletonList(barnetilleggBPPeriode);
    }

    List<BarnetilleggPeriode> barnetilleggBMPeriodeListe;
    if (nullVerdi.equals("barnetilleggBMPeriodeListe")) {
      barnetilleggBMPeriodeListe = null;
    } else {
      BarnetilleggPeriode barnetilleggBMPeriode;
      if (nullVerdi.equals("barnetilleggBMDatoFraTil")) {
        barnetilleggBMPeriode = new BarnetilleggPeriode(null, barnetilleggBMSoknadsbarnPersonId, barnetilleggBMBelop, barnetilleggBMSkattProsent);
      } else {
        barnetilleggBMPeriode = new BarnetilleggPeriode(new Periode(barnetilleggBMDatoFra, barnetilleggBMDatoTil),
            barnetilleggBMSoknadsbarnPersonId, barnetilleggBMBelop, barnetilleggBMSkattProsent);
      }
      barnetilleggBMPeriodeListe = singletonList(barnetilleggBMPeriode);
    }

    List<BarnetilleggForsvaretBPPeriode> barnetilleggForsvaretBPPeriodeListe;
    if (nullVerdi.equals("barnetilleggForsvaretBPPeriodeListe")) {
      barnetilleggForsvaretBPPeriodeListe = null;
    } else {
      BarnetilleggForsvaretBPPeriode barnetilleggForsvaretBPPeriode;
      if (nullVerdi.equals("barnetilleggForsvaretDatoFraTil")) {
        barnetilleggForsvaretBPPeriode = new BarnetilleggForsvaretBPPeriode(null, barnetilleggForsvaretIPeriode);
      } else {
        barnetilleggForsvaretBPPeriode =
            new BarnetilleggForsvaretBPPeriode(new Periode(barnetilleggForsvaretDatoFra, barnetilleggForsvaretDatoTil),
                barnetilleggForsvaretIPeriode);
      }
      barnetilleggForsvaretBPPeriodeListe = singletonList(barnetilleggForsvaretBPPeriode);
    }

    List<DeltBostedBPPeriode> deltBostedBPPeriodeListe;
    if (nullVerdi.equals("deltBostedBPPeriodeListe")) {
      deltBostedBPPeriodeListe = null;
    } else {
      DeltBostedBPPeriode deltBostedBPPeriode;
      if (nullVerdi.equals("deltBostedDatoFraTil")) {
        deltBostedBPPeriode = new DeltBostedBPPeriode(null, deltBostedSoknadsbarnPersonId, deltBostedIPeriode);
      } else {
        deltBostedBPPeriode = new DeltBostedBPPeriode(new Periode(deltBostedDatoFra, deltBostedDatoTil),
            deltBostedSoknadsbarnPersonId, deltBostedIPeriode);
      }
      deltBostedBPPeriodeListe = singletonList(deltBostedBPPeriode);
    }

    List<AndreLopendeBidragBPPeriode> andreLopendeBidragBPPeriodeListe;
    if (nullVerdi.equals("andreLopendeBidragBPPeriodeListe")) {
      andreLopendeBidragBPPeriodeListe = null;
    } else {
      AndreLopendeBidragBPPeriode andreLopendeBidragBPPeriode;
      if (nullVerdi.equals("andreLopendeBidragDatoFraTil")) {
        andreLopendeBidragBPPeriode = new AndreLopendeBidragBPPeriode(null, andreLopendeBidragBarnPersonId, andreLopendeBidragBidragBelop,
            andreLopendeBidragSamvaersfradragBelop);
      } else {
        andreLopendeBidragBPPeriode = new AndreLopendeBidragBPPeriode(new Periode(andreLopendeBidragDatoFra, andreLopendeBidragDatoTil),
            andreLopendeBidragBarnPersonId, andreLopendeBidragBidragBelop, andreLopendeBidragSamvaersfradragBelop);
      }
      andreLopendeBidragBPPeriodeListe = singletonList(andreLopendeBidragBPPeriode);
    }

    return new BeregnBarnebidragGrunnlag(barnetilleggBPPeriodeListe, barnetilleggBMPeriodeListe, barnetilleggForsvaretBPPeriodeListe,
        deltBostedBPPeriodeListe, andreLopendeBidragBPPeriodeListe);
  }


  // Bygger opp BeregnBidragsevneResultat
  public static BeregnBPBidragsevneResultat dummyBidragsevneResultat() {
    var bidragPeriodeResultatListe = new ArrayList<ResultatPeriodeBidragsevne>();
    bidragPeriodeResultatListe.add(new ResultatPeriodeBidragsevne(new Periode(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        new ResultatBeregningBidragsevne(BigDecimal.valueOf(100), BigDecimal.valueOf(100)),
        new ResultatGrunnlagBidragsevne(singletonList(new Inntekt("INNTEKTSOPPL_ARBEIDSGIVER", BigDecimal.valueOf(500000))), 1, "MED_ANDRE",
            BigDecimal.ONE, "HELT", emptyList())));
    return new BeregnBPBidragsevneResultat(bidragPeriodeResultatListe);
  }

  // Bygger opp BeregnBidragsevneResultatCore
  public static BeregnBidragsevneResultatCore dummyBidragsevneResultatCore() {
    var bidragPeriodeResultatListe = new ArrayList<no.nav.bidrag.beregn.bidragsevne.dto.ResultatPeriodeCore>();
    bidragPeriodeResultatListe.add(new no.nav.bidrag.beregn.bidragsevne.dto.ResultatPeriodeCore(
        new PeriodeCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        new no.nav.bidrag.beregn.bidragsevne.dto.ResultatBeregningCore(BigDecimal.valueOf(100), BigDecimal.valueOf(100)),
        new no.nav.bidrag.beregn.bidragsevne.dto.ResultatGrunnlagCore(
            singletonList(new InntektCore("INNTEKTSOPPL_ARBEIDSGIVER", BigDecimal.valueOf(500000))), 1, "MED_ANDRE", BigDecimal.ONE, "HELT",
            emptyList())));
    return new BeregnBidragsevneResultatCore(bidragPeriodeResultatListe, emptyList());
  }

  // Bygger opp BeregnBidragsevneResultatCore med avvik
  public static BeregnBidragsevneResultatCore dummyBidragsevneResultatCoreMedAvvik() {
    var avvikListe = new ArrayList<AvvikCore>();
    avvikListe.add(new AvvikCore("beregnDatoFra kan ikke være null", "NULL_VERDI_I_DATO"));
    avvikListe.add(new AvvikCore(
        "periodeDatoTil må være etter periodeDatoFra i inntektPeriodeListe: datoFra=2018-04-01, datoTil=2018-03-01",
        "DATO_FRA_ETTER_DATO_TIL"));
    return new BeregnBidragsevneResultatCore(emptyList(), avvikListe);
  }


  // Bygger opp BeregnNettoBarnetilsynResultat
  public static BeregnBMNettoBarnetilsynResultat dummyNettoBarnetilsynResultat() {
    var bidragPeriodeResultatListe = new ArrayList<ResultatPeriodeNettoBarnetilsyn>();
    bidragPeriodeResultatListe.add(new ResultatPeriodeNettoBarnetilsyn(new Periode(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        singletonList(new ResultatBeregningNettoBarnetilsyn(1, BigDecimal.valueOf(100))),
        new ResultatGrunnlagNettoBarnetilsyn(singletonList(new FaktiskUtgift(1, 10, BigDecimal.valueOf(100))), emptyList())));
    return new BeregnBMNettoBarnetilsynResultat(bidragPeriodeResultatListe);
  }

  // Bygger opp BeregnNettoBarnetilsynResultatCore
  public static BeregnNettoBarnetilsynResultatCore dummyNettoBarnetilsynResultatCore() {
    var bidragPeriodeResultatListe = new ArrayList<no.nav.bidrag.beregn.nettobarnetilsyn.dto.ResultatPeriodeCore>();
    bidragPeriodeResultatListe.add(new no.nav.bidrag.beregn.nettobarnetilsyn.dto.ResultatPeriodeCore(
        new PeriodeCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        singletonList(new no.nav.bidrag.beregn.nettobarnetilsyn.dto.ResultatBeregningCore(1, BigDecimal.valueOf(100))),
        new no.nav.bidrag.beregn.nettobarnetilsyn.dto.ResultatGrunnlagCore(
            singletonList(new FaktiskUtgiftCore(1, 10, BigDecimal.valueOf(100))), emptyList())));
    return new BeregnNettoBarnetilsynResultatCore(bidragPeriodeResultatListe, emptyList());
  }

  // Bygger opp BeregnNettoBarnetilsynResultatCore med avvik
  public static BeregnNettoBarnetilsynResultatCore dummyNettoBarnetilsynResultatCoreMedAvvik() {
    var avvikListe = new ArrayList<AvvikCore>();
    avvikListe.add(new AvvikCore("beregnDatoFra kan ikke være null", "NULL_VERDI_I_DATO"));
    avvikListe.add(new AvvikCore(
        "periodeDatoTil må være etter periodeDatoFra i faktiskUtgiftPeriodeListe: datoFra=2018-04-01, datoTil=2018-03-01",
        "DATO_FRA_ETTER_DATO_TIL"));
    return new BeregnNettoBarnetilsynResultatCore(emptyList(), avvikListe);
  }


  // Bygger opp BeregnUnderholdskostnadResultat
  public static BeregnBMUnderholdskostnadResultat dummyUnderholdskostnadResultat() {
    var bidragPeriodeResultatListe = new ArrayList<ResultatPeriodeUnderholdskostnad>();
    bidragPeriodeResultatListe.add(new ResultatPeriodeUnderholdskostnad(1,
        new Periode(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        new ResultatBeregningUnderholdskostnad(BigDecimal.valueOf(100)),
        new ResultatGrunnlagUnderholdskostnad(9, "DO", "64", BigDecimal.valueOf(1000), BigDecimal.valueOf(1000), emptyList())));
    return new BeregnBMUnderholdskostnadResultat(bidragPeriodeResultatListe);
  }

  // Bygger opp BeregnUnderholdskostnadResultatCore
  public static BeregnUnderholdskostnadResultatCore dummyUnderholdskostnadResultatCore() {
    var bidragPeriodeResultatListe = new ArrayList<no.nav.bidrag.beregn.underholdskostnad.dto.ResultatPeriodeCore>();
    bidragPeriodeResultatListe.add(new no.nav.bidrag.beregn.underholdskostnad.dto.ResultatPeriodeCore(1,
        new PeriodeCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        new no.nav.bidrag.beregn.underholdskostnad.dto.ResultatBeregningCore(BigDecimal.valueOf(100)),
        new no.nav.bidrag.beregn.underholdskostnad.dto.ResultatGrunnlagCore(9, "DO", "64", BigDecimal.valueOf(1000), BigDecimal.valueOf(1000),
            emptyList())));
    return new BeregnUnderholdskostnadResultatCore(bidragPeriodeResultatListe, emptyList());
  }

  // Bygger opp BeregnUnderholdskostnadResultatCore med avvik
  public static BeregnUnderholdskostnadResultatCore dummyUnderholdskostnadResultatCoreMedAvvik() {
    var avvikListe = new ArrayList<AvvikCore>();
    avvikListe.add(new AvvikCore("beregnDatoFra kan ikke være null", "NULL_VERDI_I_DATO"));
    avvikListe.add(new AvvikCore(
        "periodeDatoTil må være etter periodeDatoFra i forpleiningUtgiftPeriodeListe: datoFra=2018-04-01, datoTil=2018-03-01",
        "DATO_FRA_ETTER_DATO_TIL"));
    return new BeregnUnderholdskostnadResultatCore(emptyList(), avvikListe);
  }


  // Bygger opp BeregnBPsAndelUnderholdskostnadResultat
  public static BeregnBPAndelUnderholdskostnadResultat dummyBPsAndelUnderholdskostnadResultat() {
    var bidragPeriodeResultatListe = new ArrayList<ResultatPeriodeBPAndelUnderholdskostnad>();
    bidragPeriodeResultatListe
        .add(new ResultatPeriodeBPAndelUnderholdskostnad(1,
            new Periode(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
            new ResultatBeregningBPAndelUnderholdskostnad(BigDecimal.valueOf(10), BigDecimal.valueOf(100), false),
            new ResultatGrunnlagBPAndelUnderholdskostnad(BigDecimal.valueOf(100),
                singletonList(new Inntekt("INNTEKTSOPPL_ARBEIDSGIVER", BigDecimal.valueOf(100000))),
                singletonList(new InntektBM("INNTEKTSOPPL_ARBEIDSGIVER", BigDecimal.valueOf(100000), false, false)),
                singletonList(new Inntekt("INNTEKTSOPPL_ARBEIDSGIVER", BigDecimal.valueOf(100000))),
                emptyList())));
    return new BeregnBPAndelUnderholdskostnadResultat(bidragPeriodeResultatListe);
  }

  // Bygger opp BeregnBPsAndelUnderholdskostnadResultatCore
  public static BeregnBPsAndelUnderholdskostnadResultatCore dummyBPsAndelUnderholdskostnadResultatCore() {
    var bidragPeriodeResultatListe = new ArrayList<no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatPeriodeCore>();
    bidragPeriodeResultatListe.add(new no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatPeriodeCore(1,
        new PeriodeCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        new no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatBeregningCore(BigDecimal.valueOf(10), BigDecimal.valueOf(100), false),
        new no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatGrunnlagCore(BigDecimal.valueOf(100),
            singletonList(new no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.InntektCore("INNTEKTSOPPL_ARBEIDSGIVER",
                BigDecimal.valueOf(100000), false, false)),
            singletonList(new no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.InntektCore("INNTEKTSOPPL_ARBEIDSGIVER",
                BigDecimal.valueOf(100000), false, false)),
            singletonList(new no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.InntektCore("INNTEKTSOPPL_ARBEIDSGIVER",
                BigDecimal.valueOf(100000), false, false)),
            emptyList())));
    return new BeregnBPsAndelUnderholdskostnadResultatCore(bidragPeriodeResultatListe, emptyList());
  }

  // Bygger opp BeregnBPsAndelUnderholdskostnadResultatCore med avvik
  public static BeregnBPsAndelUnderholdskostnadResultatCore dummyBPsAndelUnderholdskostnadResultatCoreMedAvvik() {
    var avvikListe = new ArrayList<AvvikCore>();
    avvikListe.add(new AvvikCore("beregnDatoFra kan ikke være null", "NULL_VERDI_I_DATO"));
    avvikListe.add(new AvvikCore(
        "periodeDatoTil må være etter periodeDatoFra i inntektBPPeriodeListe: datoFra=2018-04-01, datoTil=2018-03-01",
        "DATO_FRA_ETTER_DATO_TIL"));
    return new BeregnBPsAndelUnderholdskostnadResultatCore(emptyList(), avvikListe);
  }


  // Bygger opp BeregnSamvaersfradragResultat
  public static BeregnBPSamvaersfradragResultat dummySamvaersfradragResultat() {
    var bidragPeriodeResultatListe = new ArrayList<ResultatPeriodeSamvaersfradrag>();
    bidragPeriodeResultatListe.add(new ResultatPeriodeSamvaersfradrag(1,
        new Periode(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        new ResultatBeregningSamvaersfradrag(BigDecimal.valueOf(100)),
        new ResultatGrunnlagSamvaersfradrag(9, "00", emptyList())));
    return new BeregnBPSamvaersfradragResultat(bidragPeriodeResultatListe);
  }

  // Bygger opp BeregnSamvaersfradragResultatCore
  public static BeregnSamvaersfradragResultatCore dummySamvaersfradragResultatCore() {
    var bidragPeriodeResultatListe = new ArrayList<no.nav.bidrag.beregn.samvaersfradrag.dto.ResultatPeriodeCore>();
    bidragPeriodeResultatListe.add(new no.nav.bidrag.beregn.samvaersfradrag.dto.ResultatPeriodeCore(1,
        new PeriodeCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        new no.nav.bidrag.beregn.samvaersfradrag.dto.ResultatBeregningCore(BigDecimal.valueOf(100)),
        new no.nav.bidrag.beregn.samvaersfradrag.dto.ResultatGrunnlagCore(9, "00", emptyList())));
    return new BeregnSamvaersfradragResultatCore(bidragPeriodeResultatListe, emptyList());
  }

  // Bygger opp BeregnSamvaersfradragResultatCore med avvik
  public static BeregnSamvaersfradragResultatCore dummySamvaersfradragResultatCoreMedAvvik() {
    var avvikListe = new ArrayList<AvvikCore>();
    avvikListe.add(new AvvikCore("beregnDatoFra kan ikke være null", "NULL_VERDI_I_DATO"));
    avvikListe.add(new AvvikCore(
        "periodeDatoTil må være etter periodeDatoFra i samvaersklassePeriodeListe: datoFra=2018-04-01, datoTil=2018-03-01",
        "DATO_FRA_ETTER_DATO_TIL"));
    return new BeregnSamvaersfradragResultatCore(emptyList(), avvikListe);
  }


  // Bygger opp BeregnKostnadsberegnetBidragResultat
  public static BeregnBPKostnadsberegnetBidragResultat dummyKostnadsberegnetBidragResultat() {
    var bidragPeriodeResultatListe = new ArrayList<ResultatPeriodeKostnadsberegnetBidrag>();
    bidragPeriodeResultatListe
        .add(new ResultatPeriodeKostnadsberegnetBidrag(1,
            new Periode(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
            new ResultatBeregningKostnadsberegnetBidrag(BigDecimal.valueOf(100)),
            new ResultatGrunnlagKostnadsberegnetBidrag(BigDecimal.valueOf(100), BigDecimal.valueOf(10), BigDecimal.valueOf(100))));
    return new BeregnBPKostnadsberegnetBidragResultat(bidragPeriodeResultatListe);
  }

  // Bygger opp BeregnKostnadsberegnetBidragResultatCore
  public static BeregnKostnadsberegnetBidragResultatCore dummyKostnadsberegnetBidragResultatCore() {
    var bidragPeriodeResultatListe = new ArrayList<no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.ResultatPeriodeCore>();
    bidragPeriodeResultatListe.add(new no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.ResultatPeriodeCore(1,
        new PeriodeCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        new no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.ResultatBeregningCore(BigDecimal.valueOf(100)),
        new no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.ResultatGrunnlagCore(BigDecimal.valueOf(100), BigDecimal.valueOf(10),
            BigDecimal.valueOf(100))));
    return new BeregnKostnadsberegnetBidragResultatCore(bidragPeriodeResultatListe, emptyList());
  }

  // Bygger opp BeregnKostnadsberegnetBidragResultatCore med avvik
  public static BeregnKostnadsberegnetBidragResultatCore dummyKostnadsberegnetBidragResultatCoreMedAvvik() {
    var avvikListe = new ArrayList<AvvikCore>();
    avvikListe.add(new AvvikCore("beregnDatoFra kan ikke være null", "NULL_VERDI_I_DATO"));
    avvikListe.add(new AvvikCore(
        "periodeDatoTil må være etter periodeDatoFra i underholdskostnadPeriodeListe: datoFra=2018-04-01, datoTil=2018-03-01",
        "DATO_FRA_ETTER_DATO_TIL"));
    return new BeregnKostnadsberegnetBidragResultatCore(emptyList(), avvikListe);
  }


  // Bygger opp BeregnBarnebidragResultat
  public static BeregnBarnebidragResultat dummyBarnebidragResultat() {
    var bidragPeriodeResultatListe = new ArrayList<ResultatPeriodeBarnebidrag>();
    bidragPeriodeResultatListe.add(new ResultatPeriodeBarnebidrag(
        new Periode(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        singletonList(new ResultatBeregningBarnebidrag(1, BigDecimal.valueOf(100), "RESULTATKODE")),
        new ResultatGrunnlagBarnebidrag(new BidragsevneGrunnlag(BigDecimal.valueOf(100), BigDecimal.valueOf(100)),
            singletonList(new ResultatGrunnlagPerBarn(1, BigDecimal.valueOf(100),
                new BPAndelUnderholdskostnad(BigDecimal.valueOf(10), BigDecimal.valueOf(100), false),
                new Barnetillegg(BigDecimal.valueOf(100), BigDecimal.valueOf(25)),
                new Barnetillegg(BigDecimal.valueOf(100), BigDecimal.valueOf(25)),
                true)),
            true,
            singletonList(new AndreLopendeBidrag(1, BigDecimal.valueOf(100), BigDecimal.valueOf(100))),
            emptyList())));
    return new BeregnBarnebidragResultat(bidragPeriodeResultatListe);
  }

  // Bygger opp BeregnBarnebidragResultatCore
  public static BeregnBarnebidragResultatCore dummyBarnebidragResultatCore() {
    var bidragPeriodeResultatListe = new ArrayList<no.nav.bidrag.beregn.barnebidrag.dto.ResultatPeriodeCore>();
    bidragPeriodeResultatListe.add(new no.nav.bidrag.beregn.barnebidrag.dto.ResultatPeriodeCore(
        new PeriodeCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        singletonList(new no.nav.bidrag.beregn.barnebidrag.dto.ResultatBeregningCore(1, BigDecimal.valueOf(100), "RESULTATKODE")),
        new GrunnlagBeregningPeriodisertCore(new BidragsevneCore(BigDecimal.valueOf(100), BigDecimal.valueOf(100)),
            singletonList(new GrunnlagBeregningPerBarnCore(1,
                new BPsAndelUnderholdskostnadCore(BigDecimal.valueOf(10), BigDecimal.valueOf(100), false),
                BigDecimal.valueOf(100), true,
                new BarnetilleggCore(BigDecimal.valueOf(100), BigDecimal.valueOf(25)),
                new BarnetilleggCore(BigDecimal.valueOf(100), BigDecimal.valueOf(25)))),
            true,
            singletonList(new AndreLopendeBidragCore(1, BigDecimal.valueOf(100), BigDecimal.valueOf(100))),
            emptyList())));
    return new BeregnBarnebidragResultatCore(bidragPeriodeResultatListe, emptyList());
  }

  // Bygger opp BeregnBarnebidragResultatCore med avvik
  public static BeregnBarnebidragResultatCore dummyBarnebidragResultatCoreMedAvvik() {
    var avvikListe = new ArrayList<AvvikCore>();
    avvikListe.add(new AvvikCore("beregnDatoFra kan ikke være null", "NULL_VERDI_I_DATO"));
    avvikListe.add(new AvvikCore(
        "periodeDatoTil må være etter periodeDatoFra i samvaersfradragPeriodeListe: datoFra=2018-04-01, datoTil=2018-03-01",
        "DATO_FRA_ETTER_DATO_TIL"));
    return new BeregnBarnebidragResultatCore(emptyList(), avvikListe);
  }


  // Bygger opp liste av sjabloner av typen Sjablontall
  public static List<Sjablontall> dummySjablonSjablontallListe() {
    var sjablonSjablontallListe = new ArrayList<Sjablontall>();

    sjablonSjablontallListe.add(new Sjablontall("0001", LocalDate.parse("2004-01-01"), LocalDate.parse("2019-06-30"), BigDecimal.valueOf(970)));
    sjablonSjablontallListe.add(new Sjablontall("0001", LocalDate.parse("2019-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(1054)));

    sjablonSjablontallListe.add(new Sjablontall("0003", LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), BigDecimal.valueOf(2504)));
    sjablonSjablontallListe.add(new Sjablontall("0003", LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), BigDecimal.valueOf(2577)));
    sjablonSjablontallListe.add(new Sjablontall("0003", LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), BigDecimal.valueOf(2649)));
    sjablonSjablontallListe.add(new Sjablontall("0003", LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), BigDecimal.valueOf(2692)));
    sjablonSjablontallListe.add(new Sjablontall("0003", LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), BigDecimal.valueOf(2775)));
    sjablonSjablontallListe.add(new Sjablontall("0003", LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(2825)));

    sjablonSjablontallListe.add(new Sjablontall("0004", LocalDate.parse("2012-07-01"), LocalDate.parse("2012-12-31"), BigDecimal.valueOf(12712)));
    sjablonSjablontallListe.add(new Sjablontall("0004", LocalDate.parse("2013-01-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(0)));

    sjablonSjablontallListe.add(new Sjablontall("0005", LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), BigDecimal.valueOf(1490)));
    sjablonSjablontallListe.add(new Sjablontall("0005", LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), BigDecimal.valueOf(1530)));
    sjablonSjablontallListe.add(new Sjablontall("0005", LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), BigDecimal.valueOf(1570)));
    sjablonSjablontallListe.add(new Sjablontall("0005", LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), BigDecimal.valueOf(1600)));
    sjablonSjablontallListe.add(new Sjablontall("0005", LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), BigDecimal.valueOf(1640)));
    sjablonSjablontallListe.add(new Sjablontall("0005", LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(1670)));

    sjablonSjablontallListe.add(new Sjablontall("0015", LocalDate.parse("2016-01-01"), LocalDate.parse("2016-12-31"), BigDecimal.valueOf(26.07)));
    sjablonSjablontallListe.add(new Sjablontall("0015", LocalDate.parse("2017-01-01"), LocalDate.parse("2017-12-31"), BigDecimal.valueOf(25.67)));
    sjablonSjablontallListe.add(new Sjablontall("0015", LocalDate.parse("2018-01-01"), LocalDate.parse("2018-12-31"), BigDecimal.valueOf(25.35)));
    sjablonSjablontallListe.add(new Sjablontall("0015", LocalDate.parse("2019-01-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(25.05)));

    sjablonSjablontallListe.add(new Sjablontall("0017", LocalDate.parse("2003-01-01"), LocalDate.parse("2003-12-31"), BigDecimal.valueOf(7.8)));
    sjablonSjablontallListe.add(new Sjablontall("0017", LocalDate.parse("2014-01-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(8.2)));

    sjablonSjablontallListe.add(new Sjablontall("0019", LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), BigDecimal.valueOf(3150)));
    sjablonSjablontallListe.add(new Sjablontall("0019", LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), BigDecimal.valueOf(3294)));
    sjablonSjablontallListe.add(new Sjablontall("0019", LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), BigDecimal.valueOf(3365)));
    sjablonSjablontallListe.add(new Sjablontall("0019", LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), BigDecimal.valueOf(3417)));
    sjablonSjablontallListe.add(new Sjablontall("0019", LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), BigDecimal.valueOf(3487)));
    sjablonSjablontallListe.add(new Sjablontall("0019", LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(3841)));

    sjablonSjablontallListe.add(new Sjablontall("0021", LocalDate.parse("2012-07-01"), LocalDate.parse("2014-06-30"), BigDecimal.valueOf(4923)));
    sjablonSjablontallListe.add(new Sjablontall("0021", LocalDate.parse("2014-07-01"), LocalDate.parse("2017-06-30"), BigDecimal.valueOf(5100)));
    sjablonSjablontallListe.add(new Sjablontall("0021", LocalDate.parse("2017-07-01"), LocalDate.parse("2017-12-31"), BigDecimal.valueOf(5254)));
    sjablonSjablontallListe.add(new Sjablontall("0021", LocalDate.parse("2018-01-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(5313)));

    sjablonSjablontallListe.add(new Sjablontall("0022", LocalDate.parse("2012-07-01"), LocalDate.parse("2014-06-30"), BigDecimal.valueOf(2028)));
    sjablonSjablontallListe.add(new Sjablontall("0022", LocalDate.parse("2014-07-01"), LocalDate.parse("2017-06-30"), BigDecimal.valueOf(2100)));
    sjablonSjablontallListe.add(new Sjablontall("0022", LocalDate.parse("2017-07-01"), LocalDate.parse("2017-12-31"), BigDecimal.valueOf(2163)));
    sjablonSjablontallListe.add(new Sjablontall("0022", LocalDate.parse("2018-01-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(2187)));

    sjablonSjablontallListe.add(new Sjablontall("0023", LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), BigDecimal.valueOf(72200)));
    sjablonSjablontallListe.add(new Sjablontall("0023", LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), BigDecimal.valueOf(73600)));
    sjablonSjablontallListe.add(new Sjablontall("0023", LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), BigDecimal.valueOf(75000)));
    sjablonSjablontallListe.add(new Sjablontall("0023", LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), BigDecimal.valueOf(83000)));
    sjablonSjablontallListe.add(new Sjablontall("0023", LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), BigDecimal.valueOf(85050)));
    sjablonSjablontallListe.add(new Sjablontall("0023", LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(87450)));

    sjablonSjablontallListe.add(new Sjablontall("0025", LocalDate.parse("2015-01-01"), LocalDate.parse("2017-12-31"), BigDecimal.valueOf(29)));
    sjablonSjablontallListe.add(new Sjablontall("0025", LocalDate.parse("2018-01-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(31)));

    sjablonSjablontallListe.add(new Sjablontall("0027", LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), BigDecimal.valueOf(50400)));
    sjablonSjablontallListe.add(new Sjablontall("0027", LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), BigDecimal.valueOf(51750)));
    sjablonSjablontallListe.add(new Sjablontall("0027", LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), BigDecimal.valueOf(53150)));
    sjablonSjablontallListe.add(new Sjablontall("0027", LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), BigDecimal.valueOf(54750)));
    sjablonSjablontallListe.add(new Sjablontall("0027", LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), BigDecimal.valueOf(56550)));
    sjablonSjablontallListe.add(new Sjablontall("0027", LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(51300)));

    sjablonSjablontallListe.add(new Sjablontall("0028", LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), BigDecimal.valueOf(74250)));
    sjablonSjablontallListe.add(new Sjablontall("0028", LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), BigDecimal.valueOf(76250)));
    sjablonSjablontallListe.add(new Sjablontall("0028", LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), BigDecimal.valueOf(78300)));
    sjablonSjablontallListe.add(new Sjablontall("0028", LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), BigDecimal.valueOf(54750)));
    sjablonSjablontallListe.add(new Sjablontall("0028", LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), BigDecimal.valueOf(56550)));
    sjablonSjablontallListe.add(new Sjablontall("0028", LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(51300)));

    sjablonSjablontallListe.add(new Sjablontall("0030", LocalDate.parse("2016-01-01"), LocalDate.parse("2016-12-31"), BigDecimal.valueOf(90850)));
    sjablonSjablontallListe.add(new Sjablontall("0030", LocalDate.parse("2017-01-01"), LocalDate.parse("2017-12-31"), BigDecimal.valueOf(94850)));
    sjablonSjablontallListe.add(new Sjablontall("0030", LocalDate.parse("2018-01-01"), LocalDate.parse("2018-12-31"), BigDecimal.valueOf(99540)));
    sjablonSjablontallListe.add(new Sjablontall("0030", LocalDate.parse("2019-01-01"), LocalDate.parse("2019-12-31"), BigDecimal.valueOf(102820)));
    sjablonSjablontallListe.add(new Sjablontall("0030", LocalDate.parse("2020-01-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(93273)));

    sjablonSjablontallListe.add(new Sjablontall("0031", LocalDate.parse("2016-01-01"), LocalDate.parse("2016-12-31"), BigDecimal.valueOf(112350)));
    sjablonSjablontallListe.add(new Sjablontall("0031", LocalDate.parse("2017-01-01"), LocalDate.parse("2017-12-31"), BigDecimal.valueOf(117350)));
    sjablonSjablontallListe.add(new Sjablontall("0031", LocalDate.parse("2018-01-01"), LocalDate.parse("2018-12-31"), BigDecimal.valueOf(99540)));
    sjablonSjablontallListe.add(new Sjablontall("0031", LocalDate.parse("2019-01-01"), LocalDate.parse("2019-12-31"), BigDecimal.valueOf(102820)));
    sjablonSjablontallListe.add(new Sjablontall("0031", LocalDate.parse("2020-01-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(93273)));

    sjablonSjablontallListe.add(new Sjablontall("0039", LocalDate.parse("2016-01-01"), LocalDate.parse("2016-12-31"), BigDecimal.valueOf(13505)));
    sjablonSjablontallListe.add(new Sjablontall("0039", LocalDate.parse("2017-01-01"), LocalDate.parse("2017-12-31"), BigDecimal.valueOf(13298)));
    sjablonSjablontallListe.add(new Sjablontall("0039", LocalDate.parse("2018-01-01"), LocalDate.parse("2018-12-31"), BigDecimal.valueOf(13132)));
    sjablonSjablontallListe.add(new Sjablontall("0039", LocalDate.parse("2019-01-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(12977)));

    sjablonSjablontallListe.add(new Sjablontall("0040", LocalDate.parse("2018-01-01"), LocalDate.parse("2018-12-31"), BigDecimal.valueOf(23)));
    sjablonSjablontallListe.add(new Sjablontall("0040", LocalDate.parse("2019-01-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(22)));

    sjablonSjablontallListe.add(new Sjablontall("0041", LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(1354)));

    return sjablonSjablontallListe;
  }

  // Bygger opp liste av sjabloner av typen Forbruksutgifter
  public static List<Forbruksutgifter> dummySjablonForbruksutgifterListe() {
    var sjablonForbruksutgifterListe = new ArrayList<Forbruksutgifter>();

    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(5, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), BigDecimal.valueOf(3352)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(5, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), BigDecimal.valueOf(3489)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(5, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), BigDecimal.valueOf(3539)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(5, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-01"), BigDecimal.valueOf(3562)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(5, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), BigDecimal.valueOf(3661)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(5, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(4228)));

    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(10, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), BigDecimal.valueOf(4675)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(10, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), BigDecimal.valueOf(4869)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(10, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), BigDecimal.valueOf(4934)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(10, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-01"), BigDecimal.valueOf(4992)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(10, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), BigDecimal.valueOf(5113)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(10, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(5710)));

    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(14, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), BigDecimal.valueOf(5432)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(14, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), BigDecimal.valueOf(5794)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(14, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), BigDecimal.valueOf(5889)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(14, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-01"), BigDecimal.valueOf(5935)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(14, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), BigDecimal.valueOf(6099)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(14, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(6913)));

    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(18, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), BigDecimal.valueOf(6332)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(18, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), BigDecimal.valueOf(6644)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(18, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), BigDecimal.valueOf(6744)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(18, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-01"), BigDecimal.valueOf(6790)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(18, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), BigDecimal.valueOf(6985)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(18, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(7953)));

    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(99, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), BigDecimal.valueOf(6332)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(99, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), BigDecimal.valueOf(6644)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(99, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), BigDecimal.valueOf(6744)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(99, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-01"), BigDecimal.valueOf(6790)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(99, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), BigDecimal.valueOf(6985)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(99, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(7953)));

    return sjablonForbruksutgifterListe;
  }

  // Bygger opp liste av sjabloner av typen MaksTilsyn
  public static List<MaksTilsyn> dummySjablonMaksTilsynListe() {
    var sjablonMaksTilsynListe = new ArrayList<MaksTilsyn>();

    sjablonMaksTilsynListe.add(
        new MaksTilsyn(1, LocalDate.parse("2014-07-01"), LocalDate.parse("2016-06-30"), BigDecimal.valueOf(5905)));
    sjablonMaksTilsynListe.add(
        new MaksTilsyn(1, LocalDate.parse("2016-07-01"), LocalDate.parse("2019-06-30"), BigDecimal.valueOf(6075)));
    sjablonMaksTilsynListe.add(
        new MaksTilsyn(1, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), BigDecimal.valueOf(6214)));
    sjablonMaksTilsynListe.add(
        new MaksTilsyn(1, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(6333)));

    sjablonMaksTilsynListe.add(
        new MaksTilsyn(2, LocalDate.parse("2014-07-01"), LocalDate.parse("2016-06-30"), BigDecimal.valueOf(7705)));
    sjablonMaksTilsynListe.add(
        new MaksTilsyn(2, LocalDate.parse("2016-07-01"), LocalDate.parse("2019-06-30"), BigDecimal.valueOf(7928)));
    sjablonMaksTilsynListe.add(
        new MaksTilsyn(2, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), BigDecimal.valueOf(8109)));
    sjablonMaksTilsynListe.add(
        new MaksTilsyn(2, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(8254)));

    sjablonMaksTilsynListe.add(
        new MaksTilsyn(99, LocalDate.parse("2014-07-01"), LocalDate.parse("2016-06-30"), BigDecimal.valueOf(8730)));
    sjablonMaksTilsynListe.add(
        new MaksTilsyn(99, LocalDate.parse("2016-07-01"), LocalDate.parse("2019-06-30"), BigDecimal.valueOf(8983)));
    sjablonMaksTilsynListe.add(
        new MaksTilsyn(99, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), BigDecimal.valueOf(9189)));
    sjablonMaksTilsynListe.add(
        new MaksTilsyn(99, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(9364)));

    return sjablonMaksTilsynListe;
  }

  // Bygger opp liste av sjabloner av typen MaksFradrag
  public static List<MaksFradrag> dummySjablonMaksFradragListe() {
    var sjablonMaksFradragListe = new ArrayList<MaksFradrag>();

    sjablonMaksFradragListe.add(
        new MaksFradrag(1, LocalDate.parse("2001-01-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(2083.33)));
    sjablonMaksFradragListe.add(
        new MaksFradrag(2, LocalDate.parse("2008-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(3333)));
    sjablonMaksFradragListe.add(
        new MaksFradrag(3, LocalDate.parse("2008-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(4583)));
    sjablonMaksFradragListe.add(
        new MaksFradrag(4, LocalDate.parse("2008-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(5833)));
    sjablonMaksFradragListe.add(
        new MaksFradrag(5, LocalDate.parse("2008-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(7083)));
    sjablonMaksFradragListe.add(
        new MaksFradrag(6, LocalDate.parse("2008-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(8333)));
    sjablonMaksFradragListe.add(
        new MaksFradrag(7, LocalDate.parse("2008-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(9583)));
    sjablonMaksFradragListe.add(
        new MaksFradrag(8, LocalDate.parse("2008-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(10833)));
    sjablonMaksFradragListe.add(
        new MaksFradrag(99, LocalDate.parse("2008-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(12083)));

    return sjablonMaksFradragListe;
  }

  // Bygger opp liste av sjabloner av typen Samvaersfradrag
  public static List<Samvaersfradrag> dummySjablonSamvaersfradragListe() {
    var sjablonSamvaersfradragListe = new ArrayList<Samvaersfradrag>();

    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("00", 99, LocalDate.parse("2013-07-01"), LocalDate.parse("9999-12-31"), 1, 1, BigDecimal.valueOf(0)));

    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 5, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), 3, 3, BigDecimal.valueOf(204)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 5, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), 3, 3, BigDecimal.valueOf(208)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 5, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), 3, 3, BigDecimal.valueOf(212)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 5, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), 3, 3, BigDecimal.valueOf(215)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 5, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), 3, 3, BigDecimal.valueOf(219)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 5, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), 3, 3, BigDecimal.valueOf(256)));

    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 10, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), 3, 3, BigDecimal.valueOf(296)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 10, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), 3, 3, BigDecimal.valueOf(301)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 10, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), 3, 3, BigDecimal.valueOf(306)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 10, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), 3, 3, BigDecimal.valueOf(312)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 10, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), 3, 3, BigDecimal.valueOf(318)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 10, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), 3, 3, BigDecimal.valueOf(353)));

    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 14, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), 3, 3, BigDecimal.valueOf(358)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 14, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), 3, 3, BigDecimal.valueOf(378)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 14, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), 3, 3, BigDecimal.valueOf(385)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 14, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), 3, 3, BigDecimal.valueOf(390)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 14, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), 3, 3, BigDecimal.valueOf(400)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 14, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), 3, 3, BigDecimal.valueOf(457)));

    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 18, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), 3, 3, BigDecimal.valueOf(422)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 18, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), 3, 3, BigDecimal.valueOf(436)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 18, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), 3, 3, BigDecimal.valueOf(443)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 18, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), 3, 3, BigDecimal.valueOf(450)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 18, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), 3, 3, BigDecimal.valueOf(460)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 18, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), 3, 3, BigDecimal.valueOf(528)));

    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 99, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), 3, 3, BigDecimal.valueOf(422)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 99, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), 3, 3, BigDecimal.valueOf(436)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 99, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), 3, 3, BigDecimal.valueOf(443)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 99, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), 3, 3, BigDecimal.valueOf(450)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 99, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), 3, 3, BigDecimal.valueOf(460)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("01", 99, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), 3, 3, BigDecimal.valueOf(528)));

    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 5, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), 0, 8, BigDecimal.valueOf(674)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 5, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), 0, 8, BigDecimal.valueOf(689)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 5, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), 0, 8, BigDecimal.valueOf(701)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 5, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), 0, 8, BigDecimal.valueOf(712)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 5, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), 0, 8, BigDecimal.valueOf(727)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 5, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), 0, 8, BigDecimal.valueOf(849)));

    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 10, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), 0, 8, BigDecimal.valueOf(979)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 10, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), 0, 8, BigDecimal.valueOf(998)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 10, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), 0, 8, BigDecimal.valueOf(1012)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 10, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), 0, 8, BigDecimal.valueOf(1034)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 10, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), 0, 8, BigDecimal.valueOf(1052)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 10, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), 0, 8, BigDecimal.valueOf(1167)));

    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 14, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), 0, 8, BigDecimal.valueOf(1184)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 14, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), 0, 8, BigDecimal.valueOf(1252)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 14, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), 0, 8, BigDecimal.valueOf(1275)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 14, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), 0, 8, BigDecimal.valueOf(1293)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 14, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), 0, 8, BigDecimal.valueOf(1323)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 14, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), 0, 8, BigDecimal.valueOf(1513)));

    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 18, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), 0, 8, BigDecimal.valueOf(1397)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 18, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), 0, 8, BigDecimal.valueOf(1444)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 18, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), 0, 8, BigDecimal.valueOf(1468)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 18, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), 0, 8, BigDecimal.valueOf(1490)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 18, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), 0, 8, BigDecimal.valueOf(1525)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 18, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), 0, 8, BigDecimal.valueOf(1749)));

    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 99, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), 0, 8, BigDecimal.valueOf(1397)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 99, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), 0, 8, BigDecimal.valueOf(1444)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 99, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), 0, 8, BigDecimal.valueOf(1468)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 99, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), 0, 8, BigDecimal.valueOf(1490)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 99, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), 0, 8, BigDecimal.valueOf(1525)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("02", 99, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), 0, 8, BigDecimal.valueOf(1749)));

    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 5, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), 0, 13, BigDecimal.valueOf(1904)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 5, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), 0, 13, BigDecimal.valueOf(1953)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 5, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), 0, 13, BigDecimal.valueOf(1998)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 5, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), 0, 13, BigDecimal.valueOf(2029)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 5, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), 0, 13, BigDecimal.valueOf(2082)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 5, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), 0, 13, BigDecimal.valueOf(2272)));

    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 10, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), 0, 13, BigDecimal.valueOf(2330)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 10, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), 0, 13, BigDecimal.valueOf(2385)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 10, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), 0, 13, BigDecimal.valueOf(2432)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 10, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), 0, 13, BigDecimal.valueOf(2478)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 10, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), 0, 13, BigDecimal.valueOf(2536)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 10, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), 0, 13, BigDecimal.valueOf(2716)));

    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 14, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), 0, 13, BigDecimal.valueOf(2616)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 14, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), 0, 13, BigDecimal.valueOf(2739)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 14, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), 0, 13, BigDecimal.valueOf(2798)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 14, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), 0, 13, BigDecimal.valueOf(2839)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 14, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), 0, 13, BigDecimal.valueOf(2914)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 14, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), 0, 13, BigDecimal.valueOf(3199)));

    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 18, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), 0, 13, BigDecimal.valueOf(2912)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 18, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), 0, 13, BigDecimal.valueOf(3007)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 18, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), 0, 13, BigDecimal.valueOf(3067)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 18, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), 0, 13, BigDecimal.valueOf(3115)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 18, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), 0, 13, BigDecimal.valueOf(3196)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 18, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), 0, 13, BigDecimal.valueOf(3528)));

    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 99, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), 0, 13, BigDecimal.valueOf(2912)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 99, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), 0, 13, BigDecimal.valueOf(3007)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 99, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), 0, 13, BigDecimal.valueOf(3067)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 99, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), 0, 13, BigDecimal.valueOf(3115)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 99, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), 0, 13, BigDecimal.valueOf(3196)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("03", 99, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), 0, 13, BigDecimal.valueOf(3528)));

    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 5, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), 0, 15, BigDecimal.valueOf(2391)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 5, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), 0, 15, BigDecimal.valueOf(2452)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 5, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), 0, 15, BigDecimal.valueOf(2509)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 5, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), 0, 15, BigDecimal.valueOf(2548)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 5, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), 0, 15, BigDecimal.valueOf(2614)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 5, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), 0, 15, BigDecimal.valueOf(2852)));

    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 10, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), 0, 15, BigDecimal.valueOf(2925)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 10, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), 0, 15, BigDecimal.valueOf(2994)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 10, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), 0, 15, BigDecimal.valueOf(3053)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 10, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), 0, 15, BigDecimal.valueOf(3111)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 10, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), 0, 15, BigDecimal.valueOf(3184)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 10, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), 0, 15, BigDecimal.valueOf(3410)));

    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 14, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), 0, 15, BigDecimal.valueOf(3284)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 14, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), 0, 15, BigDecimal.valueOf(3428)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 14, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), 0, 15, BigDecimal.valueOf(3512)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 14, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), 0, 15, BigDecimal.valueOf(3565)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 14, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), 0, 15, BigDecimal.valueOf(3658)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 14, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), 0, 15, BigDecimal.valueOf(4016)));

    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 18, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), 0, 15, BigDecimal.valueOf(3656)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 18, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), 0, 15, BigDecimal.valueOf(3774)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 18, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), 0, 15, BigDecimal.valueOf(3851)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 18, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), 0, 15, BigDecimal.valueOf(3910)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 18, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), 0, 15, BigDecimal.valueOf(4012)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 18, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), 0, 15, BigDecimal.valueOf(4429)));

    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 99, LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), 0, 15, BigDecimal.valueOf(3656)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 99, LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), 0, 15, BigDecimal.valueOf(3774)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 99, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), 0, 15, BigDecimal.valueOf(3851)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 99, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), 0, 15, BigDecimal.valueOf(3910)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 99, LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), 0, 15, BigDecimal.valueOf(4012)));
    sjablonSamvaersfradragListe.add(
        new Samvaersfradrag("04", 99, LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), 0, 15, BigDecimal.valueOf(4429)));

    return sjablonSamvaersfradragListe;
  }


  // Bygger opp liste av sjabloner av typen Bidragsevne
  public static List<Bidragsevne> dummySjablonBidragsevneListe() {
    var sjablonBidragsevneListe = new ArrayList<Bidragsevne>();

    sjablonBidragsevneListe.add(
        new Bidragsevne("EN", LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), BigDecimal.valueOf(7711), BigDecimal.valueOf(8048)));
    sjablonBidragsevneListe.add(
        new Bidragsevne("EN", LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), BigDecimal.valueOf(8907), BigDecimal.valueOf(8289)));
    sjablonBidragsevneListe.add(
        new Bidragsevne("EN", LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), BigDecimal.valueOf(9156), BigDecimal.valueOf(8521)));
    sjablonBidragsevneListe.add(
        new Bidragsevne("EN", LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), BigDecimal.valueOf(9303), BigDecimal.valueOf(8657)));
    sjablonBidragsevneListe.add(
        new Bidragsevne("EN", LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), BigDecimal.valueOf(9591), BigDecimal.valueOf(8925)));
    sjablonBidragsevneListe.add(
        new Bidragsevne("EN", LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(9764), BigDecimal.valueOf(9818)));

    sjablonBidragsevneListe.add(
        new Bidragsevne("GS", LocalDate.parse("2015-07-01"), LocalDate.parse("2016-06-30"), BigDecimal.valueOf(5073), BigDecimal.valueOf(6814)));
    sjablonBidragsevneListe.add(
        new Bidragsevne("GS", LocalDate.parse("2016-07-01"), LocalDate.parse("2017-06-30"), BigDecimal.valueOf(5456), BigDecimal.valueOf(7018)));
    sjablonBidragsevneListe.add(
        new Bidragsevne("GS", LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), BigDecimal.valueOf(5609), BigDecimal.valueOf(7215)));
    sjablonBidragsevneListe.add(
        new Bidragsevne("GS", LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), BigDecimal.valueOf(5698), BigDecimal.valueOf(7330)));
    sjablonBidragsevneListe.add(
        new Bidragsevne("GS", LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), BigDecimal.valueOf(5875), BigDecimal.valueOf(7557)));
    sjablonBidragsevneListe.add(
        new Bidragsevne("GS", LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(5981), BigDecimal.valueOf(8313)));

    return sjablonBidragsevneListe;
  }

  // Bygger opp liste av sjabloner av typen TrinnvisSkattesats
  public static List<TrinnvisSkattesats> dummySjablonTrinnvisSkattesatsListe() {
    var sjablonTrinnvisSkattesatsListe = new ArrayList<TrinnvisSkattesats>();

    sjablonTrinnvisSkattesatsListe.add(
        new TrinnvisSkattesats(LocalDate.parse("2018-01-01"), LocalDate.parse("2018-12-31"), BigDecimal.valueOf(169000), BigDecimal.valueOf(1.4)));
    sjablonTrinnvisSkattesatsListe.add(
        new TrinnvisSkattesats(LocalDate.parse("2018-01-01"), LocalDate.parse("2018-12-31"), BigDecimal.valueOf(237900), BigDecimal.valueOf(3.3)));
    sjablonTrinnvisSkattesatsListe.add(
        new TrinnvisSkattesats(LocalDate.parse("2018-01-01"), LocalDate.parse("2018-12-31"), BigDecimal.valueOf(598050), BigDecimal.valueOf(12.4)));
    sjablonTrinnvisSkattesatsListe.add(
        new TrinnvisSkattesats(LocalDate.parse("2018-01-01"), LocalDate.parse("2018-12-31"), BigDecimal.valueOf(962050), BigDecimal.valueOf(15.4)));

    sjablonTrinnvisSkattesatsListe.add(
        new TrinnvisSkattesats(LocalDate.parse("2019-01-01"), LocalDate.parse("2019-12-31"), BigDecimal.valueOf(174500), BigDecimal.valueOf(1.9)));
    sjablonTrinnvisSkattesatsListe.add(
        new TrinnvisSkattesats(LocalDate.parse("2019-01-01"), LocalDate.parse("2019-12-31"), BigDecimal.valueOf(245650), BigDecimal.valueOf(4.2)));
    sjablonTrinnvisSkattesatsListe.add(
        new TrinnvisSkattesats(LocalDate.parse("2019-01-01"), LocalDate.parse("2019-12-31"), BigDecimal.valueOf(617500), BigDecimal.valueOf(13.2)));
    sjablonTrinnvisSkattesatsListe.add(
        new TrinnvisSkattesats(LocalDate.parse("2019-01-01"), LocalDate.parse("2019-12-31"), BigDecimal.valueOf(964800), BigDecimal.valueOf(16.2)));

    sjablonTrinnvisSkattesatsListe.add(
        new TrinnvisSkattesats(LocalDate.parse("2020-01-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(180800), BigDecimal.valueOf(1.9)));
    sjablonTrinnvisSkattesatsListe.add(
        new TrinnvisSkattesats(LocalDate.parse("2020-01-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(254500), BigDecimal.valueOf(4.2)));
    sjablonTrinnvisSkattesatsListe.add(
        new TrinnvisSkattesats(LocalDate.parse("2020-01-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(639750), BigDecimal.valueOf(13.2)));
    sjablonTrinnvisSkattesatsListe.add(
        new TrinnvisSkattesats(LocalDate.parse("2020-01-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(999550), BigDecimal.valueOf(16.2)));

    return sjablonTrinnvisSkattesatsListe;
  }

  // Bygger opp liste av sjabloner av typen Barnetilsyn
  public static List<Barnetilsyn> dummySjablonBarnetilsynListe() {
    var sjablonBarnetilsynListe = new ArrayList<Barnetilsyn>();

    sjablonBarnetilsynListe.add(
        new Barnetilsyn("64", "DO", LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), BigDecimal.valueOf(330)));
    sjablonBarnetilsynListe.add(
        new Barnetilsyn("64", "DU", LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), BigDecimal.valueOf(210)));
    sjablonBarnetilsynListe.add(
        new Barnetilsyn("64", "HO", LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), BigDecimal.valueOf(542)));
    sjablonBarnetilsynListe.add(
        new Barnetilsyn("64", "HU", LocalDate.parse("2017-07-01"), LocalDate.parse("2018-06-30"), BigDecimal.valueOf(526)));

    sjablonBarnetilsynListe.add(
        new Barnetilsyn("64", "DO", LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), BigDecimal.valueOf(335)));
    sjablonBarnetilsynListe.add(
        new Barnetilsyn("64", "DU", LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), BigDecimal.valueOf(249)));
    sjablonBarnetilsynListe.add(
        new Barnetilsyn("64", "HO", LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), BigDecimal.valueOf(546)));
    sjablonBarnetilsynListe.add(
        new Barnetilsyn("64", "HU", LocalDate.parse("2018-07-01"), LocalDate.parse("2019-06-30"), BigDecimal.valueOf(624)));

    sjablonBarnetilsynListe.add(
        new Barnetilsyn("64", "DO", LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), BigDecimal.valueOf(355)));
    sjablonBarnetilsynListe.add(
        new Barnetilsyn("64", "DU", LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), BigDecimal.valueOf(258)));
    sjablonBarnetilsynListe.add(
        new Barnetilsyn("64", "HO", LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), BigDecimal.valueOf(579)));
    sjablonBarnetilsynListe.add(
        new Barnetilsyn("64", "HU", LocalDate.parse("2019-07-01"), LocalDate.parse("2020-06-30"), BigDecimal.valueOf(644)));

    sjablonBarnetilsynListe.add(
        new Barnetilsyn("64", "DO", LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(358)));
    sjablonBarnetilsynListe.add(
        new Barnetilsyn("64", "DU", LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(257)));
    sjablonBarnetilsynListe.add(
        new Barnetilsyn("64", "HO", LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(589)));
    sjablonBarnetilsynListe.add(
        new Barnetilsyn("64", "HU", LocalDate.parse("2020-07-01"), LocalDate.parse("9999-12-31"), BigDecimal.valueOf(643)));

    return sjablonBarnetilsynListe;
  }
}
