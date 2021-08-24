package no.nav.bidrag.beregn.barnebidrag.rest;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import no.nav.bidrag.beregn.barnebidrag.dto.BeregnetBarnebidragResultatCore;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Barnetilsyn;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Bidragsevne;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Forbruksutgifter;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.MaksFradrag;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.MaksTilsyn;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Samvaersfradrag;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Sjablontall;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.TrinnvisSkattesats;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnForholdsmessigFordelingGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnetBidragPerBarn;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnetBidragSakPeriode;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BidragsevnePeriode;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.Grunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.Periode;
import no.nav.bidrag.beregn.bidragsevne.dto.BeregnetBidragsevneResultatCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnetBPsAndelUnderholdskostnadResultatCore;
import no.nav.bidrag.beregn.felles.dto.AvvikCore;
import no.nav.bidrag.beregn.felles.dto.PeriodeCore;
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.BeregnForholdsmessigFordelingResultatCore;
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.BeregnetBidragSakCore;
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.GrunnlagPerBarnCore;
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.ResultatPerBarnCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnetNettoBarnetilsynResultatCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnetSamvaersfradragResultatCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnetUnderholdskostnadResultatCore;

public class TestUtil {

  // Faste dataelementer
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBeregnDatoFra() {
    return byggBarnebidragGrunnlag("beregnDatoFra", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBeregnDatoTil() {
    return byggBarnebidragGrunnlag("beregnDatoTil", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullGrunnlagListe() {
    return byggBarnebidragGrunnlag("grunnlagListe", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullReferanse() {
    return byggBarnebidragGrunnlag("referanse", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullType() {
    return byggBarnebidragGrunnlag("type", false, true, false);
  }


  // SoknadsbarnInfo
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenSoknadsbarnFodselsdato() {
    return byggBarnebidragGrunnlag("SoknadsbarnInfo_fodselsdato", true, false, false, "");
  }

  // Bidragsevne - Inntekt BP
  // rolle
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenInntektBPRolle() {
    return byggBarnebidragGrunnlag("InntektBP_rolle", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullInntektBPRolle() {
    return byggBarnebidragGrunnlag("InntektBP_rolle", false, true, false);
  }

  // datoFom
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenInntektBPDatoFom() {
    return byggBarnebidragGrunnlag("InntektBP_datoFom", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullInntektBPDatoFom() {
    return byggBarnebidragGrunnlag("InntektBP_datoFom", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiInntektBPDatoFom() {
    return byggBarnebidragGrunnlag("InntektBP_datoFom", false, false, true);
  }

  // datoTil
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenInntektBPDatoTil() {
    return byggBarnebidragGrunnlag("InntektBP_datoTil", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullInntektBPDatoTil() {
    return byggBarnebidragGrunnlag("InntektBP_datoTil", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiInntektBPDatoTil() {
    return byggBarnebidragGrunnlag("InntektBP_datoTil", false, false, true);
  }

  // inntektType
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenInntektBPInntektType() {
    return byggBarnebidragGrunnlag("InntektBP_inntektType", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullInntektBPInntektType() {
    return byggBarnebidragGrunnlag("InntektBP_inntektType", false, true, false);
  }

  // belop
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenInntektBPBelop() {
    return byggBarnebidragGrunnlag("InntektBP_belop", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullInntektBPBelop() {
    return byggBarnebidragGrunnlag("InntektBP_belop", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiInntektBPBelop() {
    return byggBarnebidragGrunnlag("InntektBP_belop", false, false, true);
  }

  // Bidragsevne - BarnIHusstand
  // datoFom
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnIHusstandDatoFom() {
    return byggBarnebidragGrunnlag("BarnIHusstand_datoFom", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBarnIHusstandDatoFom() {
    return byggBarnebidragGrunnlag("BarnIHusstand_datoFom", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiBarnIHusstandDatoFom() {
    return byggBarnebidragGrunnlag("BarnIHusstand_datoFom", false, false, true);
  }

  // datoTil
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnIHusstandDatoTil() {
    return byggBarnebidragGrunnlag("BarnIHusstand_datoTil", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBarnIHusstandDatoTil() {
    return byggBarnebidragGrunnlag("BarnIHusstand_datoTil", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiBarnIHusstandDatoTil() {
    return byggBarnebidragGrunnlag("BarnIHusstand_datoTil", false, false, true);
  }

  // antall
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnIHusstandAntall() {
    return byggBarnebidragGrunnlag("BarnIHusstand_antall", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBarnIHusstandAntall() {
    return byggBarnebidragGrunnlag("BarnIHusstand_antall", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiBarnIHusstandAntall() {
    return byggBarnebidragGrunnlag("BarnIHusstand_antall", false, false, true);
  }

  // Bidragsevne - Bostatus
  // datoFom
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBostatusDatoFom() {
    return byggBarnebidragGrunnlag("Bostatus_datoFom", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBostatusDatoFom() {
    return byggBarnebidragGrunnlag("Bostatus_datoFom", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiBostatusDatoFom() {
    return byggBarnebidragGrunnlag("Bostatus_datoFom", false, false, true);
  }

  // datoTil
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBostatusDatoTil() {
    return byggBarnebidragGrunnlag("Bostatus_datoTil", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBostatusDatoTil() {
    return byggBarnebidragGrunnlag("Bostatus_datoTil", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiBostatusDatoTil() {
    return byggBarnebidragGrunnlag("Bostatus_datoTil", false, false, true);
  }

  // bostatusKode
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBostatusBostatusKode() {
    return byggBarnebidragGrunnlag("Bostatus_bostatusKode", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBostatusBostatusKode() {
    return byggBarnebidragGrunnlag("Bostatus_bostatusKode", false, true, false);
  }

  // Bidragsevne - Saerfradrag
  // datoFom
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenSaerfradragDatoFom() {
    return byggBarnebidragGrunnlag("Saerfradrag_datoFom", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullSaerfradragDatoFom() {
    return byggBarnebidragGrunnlag("Saerfradrag_datoFom", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiSaerfradragDatoFom() {
    return byggBarnebidragGrunnlag("Saerfradrag_datoFom", false, false, true);
  }

  // datoTil
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenSaerfradragDatoTil() {
    return byggBarnebidragGrunnlag("Saerfradrag_datoTil", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullSaerfradragDatoTil() {
    return byggBarnebidragGrunnlag("Saerfradrag_datoTil", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiSaerfradragDatoTil() {
    return byggBarnebidragGrunnlag("Saerfradrag_datoTil", false, false, true);
  }

  // saerfradragKode
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenSaerfradragSaerfradragKode() {
    return byggBarnebidragGrunnlag("Saerfradrag_saerfradragKode", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullSaerfradragSaerfradragKode() {
    return byggBarnebidragGrunnlag("Saerfradrag_saerfradragKode", false, true, false);
  }

  // Bidragsevne - Skatteklasse
  // datoFom
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenSkatteklasseDatoFom() {
    return byggBarnebidragGrunnlag("Skatteklasse_datoFom", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullSkatteklasseDatoFom() {
    return byggBarnebidragGrunnlag("Skatteklasse_datoFom", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiSkatteklasseDatoFom() {
    return byggBarnebidragGrunnlag("Skatteklasse_datoFom", false, false, true);
  }

  // datoTil
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenSkatteklasseDatoTil() {
    return byggBarnebidragGrunnlag("Skatteklasse_datoTil", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullSkatteklasseDatoTil() {
    return byggBarnebidragGrunnlag("Skatteklasse_datoTil", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiSkatteklasseDatoTil() {
    return byggBarnebidragGrunnlag("Skatteklasse_datoTil", false, false, true);
  }

  // skatteklasseId
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenSkatteklasseSkatteklasseId() {
    return byggBarnebidragGrunnlag("Skatteklasse_skatteklasseId", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullSkatteklasseSkatteklasseId() {
    return byggBarnebidragGrunnlag("Skatteklasse_skatteklasseId", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiSkatteklasseSkatteklasseId() {
    return byggBarnebidragGrunnlag("Skatteklasse_skatteklasseId", false, false, true);
  }

  // Netto barnetilsyn - FaktiskUtgift
  // soknadsbarnId
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenFaktiskUtgiftSoknadsbarnId() {
    return byggBarnebidragGrunnlag("FaktiskUtgift_soknadsbarnId", true, false, false);
  }

  // datoFom
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenFaktiskUtgiftDatoFom() {
    return byggBarnebidragGrunnlag("FaktiskUtgift_datoFom", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullFaktiskUtgiftDatoFom() {
    return byggBarnebidragGrunnlag("FaktiskUtgift_datoFom", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiFaktiskUtgiftDatoFom() {
    return byggBarnebidragGrunnlag("FaktiskUtgift_datoFom", false, false, true);
  }

  // datoTil
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenFaktiskUtgiftDatoTil() {
    return byggBarnebidragGrunnlag("FaktiskUtgift_datoTil", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullFaktiskUtgiftDatoTil() {
    return byggBarnebidragGrunnlag("FaktiskUtgift_datoTil", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiFaktiskUtgiftDatoTil() {
    return byggBarnebidragGrunnlag("FaktiskUtgift_datoTil", false, false, true);
  }

  // belop
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenFaktiskUtgiftBelop() {
    return byggBarnebidragGrunnlag("FaktiskUtgift_belop", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullFaktiskUtgiftBelop() {
    return byggBarnebidragGrunnlag("FaktiskUtgift_belop", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiFaktiskUtgiftBelop() {
    return byggBarnebidragGrunnlag("FaktiskUtgift_belop", false, false, true);
  }

  // Underholdskostnad - BarnetilsynMedStonad
  // soknadsbarnId
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilsynMedStonadSoknadsbarnId() {
    return byggBarnebidragGrunnlag("BarnetilsynMedStonad_soknadsbarnId", true, false, false);
  }

  // datoFom
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilsynMedStonadDatoFom() {
    return byggBarnebidragGrunnlag("BarnetilsynMedStonad_datoFom", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBarnetilsynMedStonadDatoFom() {
    return byggBarnebidragGrunnlag("BarnetilsynMedStonad_datoFom", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiBarnetilsynMedStonadDatoFom() {
    return byggBarnebidragGrunnlag("BarnetilsynMedStonad_datoFom", false, false, true);
  }

  // datoTil
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilsynMedStonadDatoTil() {
    return byggBarnebidragGrunnlag("BarnetilsynMedStonad_datoTil", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBarnetilsynMedStonadDatoTil() {
    return byggBarnebidragGrunnlag("BarnetilsynMedStonad_datoTil", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiBarnetilsynMedStonadDatoTil() {
    return byggBarnebidragGrunnlag("BarnetilsynMedStonad_datoTil", false, false, true);
  }

  // stonadType
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilsynMedStonadStonadType() {
    return byggBarnebidragGrunnlag("BarnetilsynMedStonad_stonadType", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBarnetilsynMedStonadStonadType() {
    return byggBarnebidragGrunnlag("BarnetilsynMedStonad_stonadType", false, true, false);
  }

  // tilsynType
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilsynMedStonadTilsynType() {
    return byggBarnebidragGrunnlag("BarnetilsynMedStonad_tilsynType", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBarnetilsynMedStonadTilsynType() {
    return byggBarnebidragGrunnlag("BarnetilsynMedStonad_tilsynType", false, true, false);
  }

  // Underholdskostnad - ForpleiningUtgift
  // soknadsbarnId
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenForpleiningUtgiftSoknadsbarnId() {
    return byggBarnebidragGrunnlag("ForpleiningUtgift_soknadsbarnId", true, false, false);
  }

  // datoFom
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenForpleiningUtgiftDatoFom() {
    return byggBarnebidragGrunnlag("ForpleiningUtgift_datoFom", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullForpleiningUtgiftDatoFom() {
    return byggBarnebidragGrunnlag("ForpleiningUtgift_datoFom", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiForpleiningUtgiftDatoFom() {
    return byggBarnebidragGrunnlag("ForpleiningUtgift_datoFom", false, false, true);
  }

  // datoTil
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenForpleiningUtgiftDatoTil() {
    return byggBarnebidragGrunnlag("ForpleiningUtgift_datoTil", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullForpleiningUtgiftDatoTil() {
    return byggBarnebidragGrunnlag("ForpleiningUtgift_datoTil", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiForpleiningUtgiftDatoTil() {
    return byggBarnebidragGrunnlag("ForpleiningUtgift_datoTil", false, false, true);
  }

  // belop
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenForpleiningUtgiftBelop() {
    return byggBarnebidragGrunnlag("ForpleiningUtgift_belop", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullForpleiningUtgiftBelop() {
    return byggBarnebidragGrunnlag("ForpleiningUtgift_belop", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiForpleiningUtgiftBelop() {
    return byggBarnebidragGrunnlag("ForpleiningUtgift_belop", false, false, true);
  }

  // BP andel underholdskostnad - Inntekt SB
  // rolle
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenInntektSBRolle() {
    return byggBarnebidragGrunnlag("InntektSB_rolle", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullInntektSBRolle() {
    return byggBarnebidragGrunnlag("InntektSB_rolle", false, true, false);
  }

  // datoFom
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenInntektSBDatoFom() {
    return byggBarnebidragGrunnlag("InntektSB_datoFom", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullInntektSBDatoFom() {
    return byggBarnebidragGrunnlag("InntektSB_datoFom", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiInntektSBDatoFom() {
    return byggBarnebidragGrunnlag("InntektSB_datoFom", false, false, true);
  }

  // datoTil
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenInntektSBDatoTil() {
    return byggBarnebidragGrunnlag("InntektSB_datoTil", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullInntektSBDatoTil() {
    return byggBarnebidragGrunnlag("InntektSB_datoTil", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiInntektSBDatoTil() {
    return byggBarnebidragGrunnlag("InntektSB_datoTil", false, false, true);
  }

  // soknadsbarnId
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenInntektSBSoknadsbarnId() {
    return byggBarnebidragGrunnlag("InntektSB_soknadsbarnId", true, false, false);
  }

  // inntektType
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenInntektSBInntektType() {
    return byggBarnebidragGrunnlag("InntektSB_inntektType", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullInntektSBInntektType() {
    return byggBarnebidragGrunnlag("InntektSB_inntektType", false, true, false);
  }

  // belop
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenInntektSBBelop() {
    return byggBarnebidragGrunnlag("InntektSB_belop", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullInntektSBBelop() {
    return byggBarnebidragGrunnlag("InntektSB_belop", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiInntektSBBelop() {
    return byggBarnebidragGrunnlag("InntektSB_belop", false, false, true);
  }

  // BP andel underholdskostnad - Inntekt BM
  // rolle
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenInntektBMRolle() {
    return byggBarnebidragGrunnlag("InntektBM_rolle", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullInntektBMRolle() {
    return byggBarnebidragGrunnlag("InntektBM_rolle", false, true, false);
  }

  // datoFom
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenInntektBMDatoFom() {
    return byggBarnebidragGrunnlag("InntektBM_datoFom", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullInntektBMDatoFom() {
    return byggBarnebidragGrunnlag("InntektBM_datoFom", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiInntektBMDatoFom() {
    return byggBarnebidragGrunnlag("InntektBM_datoFom", false, false, true);
  }

  // datoTil
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenInntektBMDatoTil() {
    return byggBarnebidragGrunnlag("InntektBM_datoTil", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullInntektBMDatoTil() {
    return byggBarnebidragGrunnlag("InntektBM_datoTil", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiInntektBMDatoTil() {
    return byggBarnebidragGrunnlag("InntektBM_datoTil", false, false, true);
  }

  // soknadsbarnId
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenInntektBMSoknadsbarnId() {
    return byggBarnebidragGrunnlag("InntektBM_soknadsbarnId", true, false, false);
  }

  // inntektType
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenInntektBMInntektType() {
    return byggBarnebidragGrunnlag("InntektBM_inntektType", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullInntektBMInntektType() {
    return byggBarnebidragGrunnlag("InntektBM_inntektType", false, true, false);
  }

  // belop
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenInntektBMBelop() {
    return byggBarnebidragGrunnlag("InntektBM_belop", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullInntektBMBelop() {
    return byggBarnebidragGrunnlag("InntektBM_belop", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiInntektBMBelop() {
    return byggBarnebidragGrunnlag("InntektBM_belop", false, false, true);
  }

  // BP andel underholdskostnad - Inntekt BP
  // Tester ligger under Bidragsevne


  // Samvaersfradrag - Samvaersklasse
  // datoFom
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenSamvaersklasseDatoFom() {
    return byggBarnebidragGrunnlag("Samvaersklasse_datoFom", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullSamvaersklasseDatoFom() {
    return byggBarnebidragGrunnlag("Samvaersklasse_datoFom", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiSamvaersklasseDatoFom() {
    return byggBarnebidragGrunnlag("Samvaersklasse_datoFom", false, false, true);
  }

  // datoTil
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenSamvaersklasseDatoTil() {
    return byggBarnebidragGrunnlag("Samvaersklasse_datoTil", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullSamvaersklasseDatoTil() {
    return byggBarnebidragGrunnlag("Samvaersklasse_datoTil", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiSamvaersklasseDatoTil() {
    return byggBarnebidragGrunnlag("Samvaersklasse_datoTil", false, false, true);
  }

  // soknadsbarnId
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenSamvaersklasseSoknadsbarnId() {
    return byggBarnebidragGrunnlag("Samvaersklasse_soknadsbarnId", true, false, false);
  }

  // samvaersklasseId
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenSamvaersklasseSamvaersklasseId() {
    return byggBarnebidragGrunnlag("Samvaersklasse_samvaersklasseId", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullSamvaersklasseSamvaersklasseId() {
    return byggBarnebidragGrunnlag("Samvaersklasse_samvaersklasseId", false, true, false);
  }


  // Barnebidrag - BarnetilleggBM
  // rolle
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBMRolle() {
    return byggBarnebidragGrunnlag("BarnetilleggBM_rolle", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBarnetilleggBMRolle() {
    return byggBarnebidragGrunnlag("BarnetilleggBM_rolle", false, true, false);
  }

  // datoFom
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBMDatoFom() {
    return byggBarnebidragGrunnlag("BarnetilleggBM_datoFom", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBarnetilleggBMDatoFom() {
    return byggBarnebidragGrunnlag("BarnetilleggBM_datoFom", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggBMDatoFom() {
    return byggBarnebidragGrunnlag("BarnetilleggBM_datoFom", false, false, true);
  }

  // datoTil
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBMDatoTil() {
    return byggBarnebidragGrunnlag("BarnetilleggBM_datoTil", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBarnetilleggBMDatoTil() {
    return byggBarnebidragGrunnlag("BarnetilleggBM_datoTil", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggBMDatoTil() {
    return byggBarnebidragGrunnlag("BarnetilleggBM_datoTil", false, false, true);
  }

  // soknadsbarnId
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBMSoknadsbarnId() {
    return byggBarnebidragGrunnlag("BarnetilleggBM_soknadsbarnId", true, false, false);
  }

  // bruttoBelop
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBMBruttoBelop() {
    return byggBarnebidragGrunnlag("BarnetilleggBM_bruttoBelop", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBarnetilleggBMBruttoBelop() {
    return byggBarnebidragGrunnlag("BarnetilleggBM_bruttoBelop", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggBMBruttoBelop() {
    return byggBarnebidragGrunnlag("BarnetilleggBM_bruttoBelop", false, false, true);
  }

  // skattProsent
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBMSkattProsent() {
    return byggBarnebidragGrunnlag("BarnetilleggBM_skattProsent", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBarnetilleggBMSkattProsent() {
    return byggBarnebidragGrunnlag("BarnetilleggBM_skattProsent", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggBMSkattProsent() {
    return byggBarnebidragGrunnlag("BarnetilleggBM_skattProsent", false, false, true);
  }

  // Barnebidrag - BarnetilleggBP
  // rolle
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBPRolle() {
    return byggBarnebidragGrunnlag("BarnetilleggBP_rolle", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBarnetilleggBPRolle() {
    return byggBarnebidragGrunnlag("BarnetilleggBP_rolle", false, true, false);
  }

  // datoFom
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBPDatoFom() {
    return byggBarnebidragGrunnlag("BarnetilleggBP_datoFom", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBarnetilleggBPDatoFom() {
    return byggBarnebidragGrunnlag("BarnetilleggBP_datoFom", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggBPDatoFom() {
    return byggBarnebidragGrunnlag("BarnetilleggBP_datoFom", false, false, true);
  }

  // datoTil
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBPDatoTil() {
    return byggBarnebidragGrunnlag("BarnetilleggBP_datoTil", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBarnetilleggBPDatoTil() {
    return byggBarnebidragGrunnlag("BarnetilleggBP_datoTil", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggBPDatoTil() {
    return byggBarnebidragGrunnlag("BarnetilleggBP_datoTil", false, false, true);
  }

  // soknadsbarnId
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBPSoknadsbarnId() {
    return byggBarnebidragGrunnlag("BarnetilleggBP_soknadsbarnId", true, false, false);
  }

  // bruttoBelop
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBPBruttoBelop() {
    return byggBarnebidragGrunnlag("BarnetilleggBP_bruttoBelop", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBarnetilleggBPBruttoBelop() {
    return byggBarnebidragGrunnlag("BarnetilleggBP_bruttoBelop", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggBPBruttoBelop() {
    return byggBarnebidragGrunnlag("BarnetilleggBP_bruttoBelop", false, false, true);
  }

  // skattProsent
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggBPSkattProsent() {
    return byggBarnebidragGrunnlag("BarnetilleggBP_skattProsent", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBarnetilleggBPSkattProsent() {
    return byggBarnebidragGrunnlag("BarnetilleggBP_skattProsent", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggBPSkattProsent() {
    return byggBarnebidragGrunnlag("BarnetilleggBP_skattProsent", false, false, true);
  }

  // Barnebidrag - BarnetilleggForsvaret
  // datoFom
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggForsvaretDatoFom() {
    return byggBarnebidragGrunnlag("BarnetilleggForsvaret_datoFom", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBarnetilleggForsvaretDatoFom() {
    return byggBarnebidragGrunnlag("BarnetilleggForsvaret_datoFom", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggForsvaretDatoFom() {
    return byggBarnebidragGrunnlag("BarnetilleggForsvaret_datoFom", false, false, true);
  }

  // datoTil
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggForsvaretDatoTil() {
    return byggBarnebidragGrunnlag("BarnetilleggForsvaret_datoTil", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBarnetilleggForsvaretDatoTil() {
    return byggBarnebidragGrunnlag("BarnetilleggForsvaret_datoTil", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggForsvaretDatoTil() {
    return byggBarnebidragGrunnlag("BarnetilleggForsvaret_datoTil", false, false, true);
  }

  // soknadsbarnId
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenDeltBostedSoknadsbarnId() {
    return byggBarnebidragGrunnlag("DeltBosted_soknadsbarnId", true, false, false);
  }

  // barnetilleggForsvaret
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenBarnetilleggForsvaretBarnetilleggForsvaret() {
    return byggBarnebidragGrunnlag("BarnetilleggForsvaret_barnetilleggForsvaret", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullBarnetilleggForsvaretBarnetilleggForsvaret() {
    return byggBarnebidragGrunnlag("BarnetilleggForsvaret_barnetilleggForsvaret", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggForsvaretBarnetilleggForsvaret() {
    return byggBarnebidragGrunnlag("BarnetilleggForsvaret_barnetilleggForsvaret", false, false, true);
  }

  // Barnebidrag - DeltBosted
  // datoFom
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenDeltBostedDatoFom() {
    return byggBarnebidragGrunnlag("DeltBosted_datoFom", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullDeltBostedDatoFom() {
    return byggBarnebidragGrunnlag("DeltBosted_datoFom", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiDeltBostedDatoFom() {
    return byggBarnebidragGrunnlag("DeltBosted_datoFom", false, false, true);
  }

  // datoTil
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenDeltBostedDatoTil() {
    return byggBarnebidragGrunnlag("DeltBosted_datoTil", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullDeltBostedDatoTil() {
    return byggBarnebidragGrunnlag("DeltBosted_datoTil", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiDeltBostedDatoTil() {
    return byggBarnebidragGrunnlag("DeltBosted_datoTil", false, false, true);
  }

  // deltBosted
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenDeltBostedDeltBosted() {
    return byggBarnebidragGrunnlag("DeltBosted_deltBosted", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullDeltBostedDeltBosted() {
    return byggBarnebidragGrunnlag("DeltBosted_deltBosted", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiDeltBostedDeltBosted() {
    return byggBarnebidragGrunnlag("DeltBosted_deltBosted", false, false, true);
  }

  // Barnebidrag - AndreLopendeBidrag
  // datoFom
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenAndreLopendeBidragDatoFom() {
    return byggBarnebidragGrunnlag("AndreLopendeBidrag_datoFom", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullAndreLopendeBidragDatoFom() {
    return byggBarnebidragGrunnlag("AndreLopendeBidrag_datoFom", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiAndreLopendeBidragDatoFom() {
    return byggBarnebidragGrunnlag("AndreLopendeBidrag_datoFom", false, false, true);
  }

  // datoTil
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenAndreLopendeBidragDatoTil() {
    return byggBarnebidragGrunnlag("AndreLopendeBidrag_datoTil", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullAndreLopendeBidragDatoTil() {
    return byggBarnebidragGrunnlag("AndreLopendeBidrag_datoTil", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiAndreLopendeBidragDatoTil() {
    return byggBarnebidragGrunnlag("AndreLopendeBidrag_datoTil", false, false, true);
  }

  // soknadsbarnId
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenAndreLopendeBidragSoknadsbarnId() {
    return byggBarnebidragGrunnlag("AndreLopendeBidrag_soknadsbarnId", true, false, false);
  }

  // bidragBelop
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenAndreLopendeBidragBidragBelop() {
    return byggBarnebidragGrunnlag("AndreLopendeBidrag_bidragBelop", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullAndreLopendeBidragBidragBelop() {
    return byggBarnebidragGrunnlag("AndreLopendeBidrag_bidragBelop", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiAndreLopendeBidragBidragBelop() {
    return byggBarnebidragGrunnlag("AndreLopendeBidrag_bidragBelop", false, false, true);
  }

  // samvaersfradragBelop
  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUtenAndreLopendeBidragSamvaersfradragBelop() {
    return byggBarnebidragGrunnlag("AndreLopendeBidrag_samvaersfradragBelop", true, false, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagNullAndreLopendeBidragSamvaersfradragBelop() {
    return byggBarnebidragGrunnlag("AndreLopendeBidrag_samvaersfradragBelop", false, true, false);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlagUgyldigVerdiAndreLopendeBidragSamvaersfradragBelop() {
    return byggBarnebidragGrunnlag("AndreLopendeBidrag_samvaersfradragBelop", false, false, true);
  }

  //==================================================================================================================================================

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlag() {
    return byggBarnebidragGrunnlag("", false, false, false, "");
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlag(String soknadsbarnIdKnytningInntekt) {
    return byggBarnebidragGrunnlag("", false, false, false, soknadsbarnIdKnytningInntekt);
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlag(String feltNavn, boolean manglerVerdi, boolean nullVerdi,
      boolean ugyldigVerdi) {
    return byggBarnebidragGrunnlag(feltNavn, manglerVerdi, nullVerdi, ugyldigVerdi, "");
  }

  public static BeregnTotalBarnebidragGrunnlag byggBarnebidragGrunnlag(String feltNavn, boolean manglerVerdi, boolean nullVerdi, boolean ugyldigVerdi,
      String soknadsbarnIdKnytningInntekt) {
    var beregnDatoFra = ("beregnDatoFra".equals(feltNavn) && nullVerdi ? null : LocalDate.parse("2017-01-01"));
    var beregnDatoTil = ("beregnDatoTil".equals(feltNavn) && nullVerdi ? null : LocalDate.parse("2020-01-01"));
    var soknadsbarnInfoReferanse = ("referanse".equals(feltNavn) && nullVerdi ? null : "Mottatt_SoknadsbarnInfo_SB_1");
    var soknadsbarnInfoType = ("type".equals(feltNavn) && nullVerdi ? null : "SoknadsbarnInfo");
    var grunnlagListe = new ArrayList<Grunnlag>();
    if ("grunnlagListe".equals(feltNavn) && nullVerdi) {
      grunnlagListe = null;
    } else {
      grunnlagListe.add(new Grunnlag(soknadsbarnInfoReferanse, soknadsbarnInfoType,
          byggSoknadsbarnInfoInnhold(feltNavn, manglerVerdi, nullVerdi, ugyldigVerdi, "")));
      if (StringUtils.isNumeric(soknadsbarnIdKnytningInntekt)) {
        grunnlagListe.add(new Grunnlag(soknadsbarnInfoReferanse + soknadsbarnIdKnytningInntekt, soknadsbarnInfoType,
            byggSoknadsbarnInfoInnhold(feltNavn, manglerVerdi, nullVerdi, ugyldigVerdi, soknadsbarnIdKnytningInntekt)));
      }
      grunnlagListe.add(new Grunnlag("Mottatt_Inntekt_AG_20170101_SB_1", "Inntekt",
          byggInntektSBInnhold(feltNavn, manglerVerdi, nullVerdi, ugyldigVerdi)));
      grunnlagListe.add(new Grunnlag("Mottatt_Inntekt_AG_20170101_BM", "Inntekt",
          byggInntektBMInnhold(feltNavn, manglerVerdi, nullVerdi, ugyldigVerdi, soknadsbarnIdKnytningInntekt)));
      grunnlagListe.add(new Grunnlag("Mottatt_Inntekt_AG_20170101_BP", "Inntekt",
          byggInntektBPInnhold(feltNavn, manglerVerdi, nullVerdi, ugyldigVerdi)));
      grunnlagListe.add(new Grunnlag("Mottatt_BarnIHusstand_20170101", "BarnIHusstand",
          byggBarnIHusstandInnhold(feltNavn, manglerVerdi, nullVerdi, ugyldigVerdi)));
      grunnlagListe.add(new Grunnlag("Mottatt_Bostatus_20170101", "Bostatus",
          byggBostatusInnhold(feltNavn, manglerVerdi, nullVerdi, ugyldigVerdi)));
      grunnlagListe.add(new Grunnlag("Mottatt_Saerfradrag_20170101", "Saerfradrag",
          byggSaerfradragInnhold(feltNavn, manglerVerdi, nullVerdi, ugyldigVerdi)));
      grunnlagListe.add(new Grunnlag("Mottatt_Skatteklasse_20170101", "Skatteklasse",
          byggSkatteklasseInnhold(feltNavn, manglerVerdi, nullVerdi, ugyldigVerdi)));
      grunnlagListe.add(new Grunnlag("Mottatt_FaktiskUtgift_20170101_SB_1", "FaktiskUtgift",
          byggFaktiskUtgiftInnhold(feltNavn, manglerVerdi, nullVerdi, ugyldigVerdi)));
      grunnlagListe.add(new Grunnlag("Mottatt_BarnetilsynMedStonad_20170101_SB_1", "BarnetilsynMedStonad",
          byggBarnetilsynMedStonadInnhold(feltNavn, manglerVerdi, nullVerdi, ugyldigVerdi)));
      grunnlagListe.add(new Grunnlag("Mottatt_ForpleiningUtgift_20170101_SB_1", "ForpleiningUtgift",
          byggForpleiningUtgiftInnhold(feltNavn, manglerVerdi, nullVerdi, ugyldigVerdi)));
      grunnlagListe.add(new Grunnlag("Mottatt_Samvaersklasse_20170101_SB_1", "Samvaersklasse",
          byggSamvaersklasseInnhold(feltNavn, manglerVerdi, nullVerdi, ugyldigVerdi)));
      grunnlagListe.add(new Grunnlag("Mottatt_Barnetillegg_20170101_BM_SB_1", "Barnetillegg",
          byggBarnetilleggBMInnhold(feltNavn, manglerVerdi, nullVerdi, ugyldigVerdi)));
      grunnlagListe.add(new Grunnlag("Mottatt_Barnetillegg_20170101_BP_SB_1", "Barnetillegg",
          byggBarnetilleggBPInnhold(feltNavn, manglerVerdi, nullVerdi, ugyldigVerdi)));
      grunnlagListe.add(new Grunnlag("Mottatt_BarnetilleggForsvaret_20170101", "BarnetilleggForsvaret",
          byggBarnetilleggForsvaretInnhold(feltNavn, manglerVerdi, nullVerdi, ugyldigVerdi)));
      grunnlagListe.add(new Grunnlag("Mottatt_DeltBosted_20170101_SB_1", "DeltBosted",
          byggDeltBostedInnhold(feltNavn, manglerVerdi, nullVerdi, ugyldigVerdi)));
      grunnlagListe.add(new Grunnlag("Mottatt_AndreLopendeBidrag_20170101_SB_1", "AndreLopendeBidrag",
          byggAndreLopendeBidragInnhold(feltNavn, manglerVerdi, nullVerdi, ugyldigVerdi)));
    }

    return new BeregnTotalBarnebidragGrunnlag(beregnDatoFra, beregnDatoTil, grunnlagListe);
  }

  // Bygger opp SoknadsbarnInfo innhold
  private static JsonNode byggSoknadsbarnInfoInnhold(String feltNavn, boolean manglerVerdi, boolean nullVerdi, boolean ugyldigVerdi,
      String soknadsbarnIdKnytningInntekt) {
    var jsonMap = new HashMap<>();

    // Spesialvariant for å unngå at inntekt ikke plukkes opp hvis id ikke matcher
    if (StringUtils.isNumeric(soknadsbarnIdKnytningInntekt)) {
      jsonMap.put("soknadsbarnId", Integer.parseInt(soknadsbarnIdKnytningInntekt));
      jsonMap.put("fodselsdato", "2010-01-01");
    } else {

      if ("SoknadsbarnInfo_soknadsbarnId".equals(feltNavn)) {
        if (!manglerVerdi) {
          jsonMap.put("soknadsbarnId", nullVerdi ? null : (ugyldigVerdi ? "xx" : 1));
        }
      } else {
        jsonMap.put("soknadsbarnId", 1);
      }

      if ("SoknadsbarnInfo_fodselsdato".equals(feltNavn)) {
        if (!manglerVerdi) {
          jsonMap.put("fodselsdato", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2010-01-01"));
        }
      } else {
        jsonMap.put("fodselsdato", "2010-01-01");
      }
    }

    return new ObjectMapper().valueToTree(jsonMap);
  }

  // Bygger opp Inntekt SB innhold
  private static JsonNode byggInntektSBInnhold(String feltNavn, boolean manglerVerdi, boolean nullVerdi, boolean ugyldigVerdi) {
    var jsonMap = new HashMap<>();

    if ("InntektSB_rolle".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("rolle", nullVerdi ? null : "SB");
      }
    } else {
      jsonMap.put("rolle", "SB");
    }

    if ("InntektSB_datoFom".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoFom", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2017-01-01"));
      }
    } else {
      jsonMap.put("datoFom", "2017-01-01");
    }

    if ("InntektSB_datoTil".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoTil", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2020-01-01"));
      }
    } else {
      jsonMap.put("datoTil", "2020-01-01");
    }

    if ("InntektSB_soknadsbarnId".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("soknadsbarnId", nullVerdi ? null : (ugyldigVerdi ? "xx" : 1));
      }
    } else {
      jsonMap.put("soknadsbarnId", 1);
    }

    if ("InntektSB_inntektType".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("inntektType", nullVerdi ? null : "INNTEKTSOPPLYSNINGER_ARBEIDSGIVER");
      }
    } else {
      jsonMap.put("inntektType", "INNTEKTSOPPLYSNINGER_ARBEIDSGIVER");
    }

    if ("InntektSB_belop".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("belop", nullVerdi ? null : (ugyldigVerdi ? "xx" : 100));
      }
    } else {
      jsonMap.put("belop", 100);
    }

    return new ObjectMapper().valueToTree(jsonMap);
  }

  // Bygger opp Inntekt BM innhold
  private static JsonNode byggInntektBMInnhold(String feltNavn, boolean manglerVerdi, boolean nullVerdi, boolean ugyldigVerdi,
      String soknadsbarnIdKnytningInntekt) {
    var jsonMap = new HashMap<>();

    if ("InntektBM_rolle".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("rolle", nullVerdi ? null : "BM");
      }
    } else {
      jsonMap.put("rolle", "BM");
    }

    if ("InntektBM_datoFom".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoFom", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2017-01-01"));
      }
    } else {
      jsonMap.put("datoFom", "2017-01-01");
    }

    if ("InntektBM_datoTil".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoTil", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2020-01-01"));
      }
    } else {
      jsonMap.put("datoTil", "2020-01-01");
    }

    if ("InntektBM_soknadsbarnId".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("soknadsbarnId", nullVerdi ? null : (ugyldigVerdi ? "xx" : 1));
      }
    } else if (StringUtils.isNumeric(soknadsbarnIdKnytningInntekt)) {
      jsonMap.put("soknadsbarnId", Integer.parseInt(soknadsbarnIdKnytningInntekt));
    } else {
      jsonMap.put("soknadsbarnId", 1);
    }

    if ("InntektBM_inntektType".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("inntektType", nullVerdi ? null : "INNTEKTSOPPLYSNINGER_ARBEIDSGIVER");
      }
    } else {
      jsonMap.put("inntektType", "INNTEKTSOPPLYSNINGER_ARBEIDSGIVER");
    }

    if ("InntektBM_belop".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("belop", nullVerdi ? null : (ugyldigVerdi ? "xx" : 100));
      }
    } else {
      jsonMap.put("belop", 100);
    }

    return new ObjectMapper().valueToTree(jsonMap);
  }

  // Bygger opp Inntekt BP innhold
  private static JsonNode byggInntektBPInnhold(String feltNavn, boolean manglerVerdi, boolean nullVerdi, boolean ugyldigVerdi) {
    var jsonMap = new HashMap<>();

    if ("InntektBP_rolle".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("rolle", nullVerdi ? null : "BP");
      }
    } else {
      jsonMap.put("rolle", "BP");
    }

    if ("InntektBP_datoFom".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoFom", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2017-01-01"));
      }
    } else {
      jsonMap.put("datoFom", "2017-01-01");
    }

    if ("InntektBP_datoTil".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoTil", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2020-01-01"));
      }
    } else {
      jsonMap.put("datoTil", "2020-01-01");
    }

    if ("InntektBP_inntektType".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("inntektType", nullVerdi ? null : "INNTEKTSOPPLYSNINGER_ARBEIDSGIVER");
      }
    } else {
      jsonMap.put("inntektType", "INNTEKTSOPPLYSNINGER_ARBEIDSGIVER");
    }

    if ("InntektBP_belop".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("belop", nullVerdi ? null : (ugyldigVerdi ? "xx" : 100));
      }
    } else {
      jsonMap.put("belop", 100);
    }

    return new ObjectMapper().valueToTree(jsonMap);
  }

  // Bygger opp BarnIHusstand innhold
  private static JsonNode byggBarnIHusstandInnhold(String feltNavn, boolean manglerVerdi, boolean nullVerdi, boolean ugyldigVerdi) {
    var jsonMap = new HashMap<>();

    if ("BarnIHusstand_datoFom".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoFom", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2017-01-01"));
      }
    } else {
      jsonMap.put("datoFom", "2017-01-01");
    }

    if ("BarnIHusstand_datoTil".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoTil", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2020-01-01"));
      }
    } else {
      jsonMap.put("datoTil", "2020-01-01");
    }

    if ("BarnIHusstand_antall".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("antall", nullVerdi ? null : (ugyldigVerdi ? "xx" : 0));
      }
    } else {
      jsonMap.put("antall", 0);
    }

    return new ObjectMapper().valueToTree(jsonMap);
  }

  // Bygger opp Bostatus innhold
  private static JsonNode byggBostatusInnhold(String feltNavn, boolean manglerVerdi, boolean nullVerdi, boolean ugyldigVerdi) {
    var jsonMap = new HashMap<>();

    if ("Bostatus_datoFom".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoFom", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2017-01-01"));
      }
    } else {
      jsonMap.put("datoFom", "2017-01-01");
    }

    if ("Bostatus_datoTil".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoTil", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2020-01-01"));
      }
    } else {
      jsonMap.put("datoTil", "2020-01-01");
    }

    if ("Bostatus_bostatusKode".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("bostatusKode", nullVerdi ? null : "MED_ANDRE");
      }
    } else {
      jsonMap.put("bostatusKode", "MED_ANDRE");
    }

    return new ObjectMapper().valueToTree(jsonMap);
  }

  // Bygger opp Saerfradrag innhold
  private static JsonNode byggSaerfradragInnhold(String feltNavn, boolean manglerVerdi, boolean nullVerdi, boolean ugyldigVerdi) {
    var jsonMap = new HashMap<>();

    if ("Saerfradrag_datoFom".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoFom", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2017-01-01"));
      }
    } else {
      jsonMap.put("datoFom", "2017-01-01");
    }

    if ("Saerfradrag_datoTil".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoTil", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2020-01-01"));
      }
    } else {
      jsonMap.put("datoTil", "2020-01-01");
    }

    if ("Saerfradrag_saerfradragKode".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("saerfradragKode", nullVerdi ? null : "HELT");
      }
    } else {
      jsonMap.put("saerfradragKode", "HELT");
    }

    return new ObjectMapper().valueToTree(jsonMap);
  }

  // Bygger opp Skatteklasse innhold
  private static JsonNode byggSkatteklasseInnhold(String feltNavn, boolean manglerVerdi, boolean nullVerdi, boolean ugyldigVerdi) {
    var jsonMap = new HashMap<>();

    if ("Skatteklasse_datoFom".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoFom", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2017-01-01"));
      }
    } else {
      jsonMap.put("datoFom", "2017-01-01");
    }

    if ("Skatteklasse_datoTil".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoTil", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2020-01-01"));
      }
    } else {
      jsonMap.put("datoTil", "2020-01-01");
    }

    if ("Skatteklasse_skatteklasseId".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("skatteklasseId", nullVerdi ? null : (ugyldigVerdi ? "xx" : 1));
      }
    } else {
      jsonMap.put("skatteklasseId", 1);
    }

    return new ObjectMapper().valueToTree(jsonMap);
  }

  // Bygger opp FaktiskUtgift innhold
  private static JsonNode byggFaktiskUtgiftInnhold(String feltNavn, boolean manglerVerdi, boolean nullVerdi, boolean ugyldigVerdi) {
    var jsonMap = new HashMap<>();

    if ("FaktiskUtgift_datoFom".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoFom", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2017-01-01"));
      }
    } else {
      jsonMap.put("datoFom", "2017-01-01");
    }

    if ("FaktiskUtgift_datoTil".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoTil", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2020-01-01"));
      }
    } else {
      jsonMap.put("datoTil", "2020-01-01");
    }

    if ("FaktiskUtgift_soknadsbarnId".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("soknadsbarnId", 1);
      }
    } else {
      jsonMap.put("soknadsbarnId", 1);
    }

    if ("FaktiskUtgift_belop".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("belop", nullVerdi ? null : (ugyldigVerdi ? "xx" : 1000));
      }
    } else {
      jsonMap.put("belop", 1000);
    }

    return new ObjectMapper().valueToTree(jsonMap);
  }

  // Bygger opp BarnetilsynMedStonad innhold
  private static JsonNode byggBarnetilsynMedStonadInnhold(String feltNavn, boolean manglerVerdi, boolean nullVerdi, boolean ugyldigVerdi) {
    var jsonMap = new HashMap<>();

    if ("BarnetilsynMedStonad_datoFom".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoFom", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2017-01-01"));
      }
    } else {
      jsonMap.put("datoFom", "2017-01-01");
    }

    if ("BarnetilsynMedStonad_datoTil".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoTil", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2020-01-01"));
      }
    } else {
      jsonMap.put("datoTil", "2020-01-01");
    }

    if ("BarnetilsynMedStonad_soknadsbarnId".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("soknadsbarnId", 1);
      }
    } else {
      jsonMap.put("soknadsbarnId", 1);
    }

    if ("BarnetilsynMedStonad_stonadType".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("stonadType", nullVerdi ? null : "DO");
      }
    } else {
      jsonMap.put("stonadType", "DO");
    }

    if ("BarnetilsynMedStonad_tilsynType".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("tilsynType", nullVerdi ? null : "64");
      }
    } else {
      jsonMap.put("tilsynType", "64");
    }

    return new ObjectMapper().valueToTree(jsonMap);
  }

  // Bygger opp ForpleiningUtgift innhold
  private static JsonNode byggForpleiningUtgiftInnhold(String feltNavn, boolean manglerVerdi, boolean nullVerdi, boolean ugyldigVerdi) {
    var jsonMap = new HashMap<>();

    if ("ForpleiningUtgift_datoFom".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoFom", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2017-01-01"));
      }
    } else {
      jsonMap.put("datoFom", "2017-01-01");
    }

    if ("ForpleiningUtgift_datoTil".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoTil", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2020-01-01"));
      }
    } else {
      jsonMap.put("datoTil", "2020-01-01");
    }

    if ("ForpleiningUtgift_soknadsbarnId".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("soknadsbarnId", 1);
      }
    } else {
      jsonMap.put("soknadsbarnId", 1);
    }

    if ("ForpleiningUtgift_belop".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("belop", nullVerdi ? null : (ugyldigVerdi ? "xx" : 1000));
      }
    } else {
      jsonMap.put("belop", 1000);
    }

    return new ObjectMapper().valueToTree(jsonMap);
  }

  // Bygger opp Samvaersklasse innhold
  private static JsonNode byggSamvaersklasseInnhold(String feltNavn, boolean manglerVerdi, boolean nullVerdi, boolean ugyldigVerdi) {
    var jsonMap = new HashMap<>();

    if ("Samvaersklasse_datoFom".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoFom", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2017-01-01"));
      }
    } else {
      jsonMap.put("datoFom", "2017-01-01");
    }

    if ("Samvaersklasse_datoTil".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoTil", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2020-01-01"));
      }
    } else {
      jsonMap.put("datoTil", "2020-01-01");
    }

    if ("Samvaersklasse_soknadsbarnId".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("soknadsbarnId", 1);
      }
    } else {
      jsonMap.put("soknadsbarnId", 1);
    }

    if ("Samvaersklasse_samvaersklasseId".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("samvaersklasseId", nullVerdi ? null : "64");
      }
    } else {
      jsonMap.put("samvaersklasseId", "64");
    }

    return new ObjectMapper().valueToTree(jsonMap);
  }

  // Bygger opp Barnetillegg BM innhold
  private static JsonNode byggBarnetilleggBMInnhold(String feltNavn, boolean manglerVerdi, boolean nullVerdi, boolean ugyldigVerdi) {
    var jsonMap = new HashMap<>();

    if ("BarnetilleggBM_rolle".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("rolle", nullVerdi ? null : "BM");
      }
    } else {
      jsonMap.put("rolle", "BM");
    }

    if ("BarnetilleggBM_datoFom".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoFom", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2017-01-01"));
      }
    } else {
      jsonMap.put("datoFom", "2017-01-01");
    }

    if ("BarnetilleggBM_datoTil".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoTil", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2020-01-01"));
      }
    } else {
      jsonMap.put("datoTil", "2020-01-01");
    }

    if ("BarnetilleggBM_soknadsbarnId".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("soknadsbarnId", nullVerdi ? null : (ugyldigVerdi ? "xx" : 1));
      }
    } else {
      jsonMap.put("soknadsbarnId", 1);
    }

    if ("BarnetilleggBM_bruttoBelop".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("bruttoBelop", nullVerdi ? null : (ugyldigVerdi ? "xx" : 100));
      }
    } else {
      jsonMap.put("bruttoBelop", 100);
    }

    if ("BarnetilleggBM_skattProsent".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("skattProsent", nullVerdi ? null : (ugyldigVerdi ? "xx" : 25));
      }
    } else {
      jsonMap.put("skattProsent", 25);
    }

    return new ObjectMapper().valueToTree(jsonMap);
  }

  // Bygger opp Barnetillegg BP innhold
  private static JsonNode byggBarnetilleggBPInnhold(String feltNavn, boolean manglerVerdi, boolean nullVerdi, boolean ugyldigVerdi) {
    var jsonMap = new HashMap<>();

    if ("BarnetilleggBP_rolle".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("rolle", nullVerdi ? null : "BP");
      }
    } else {
      jsonMap.put("rolle", "BP");
    }

    if ("BarnetilleggBP_datoFom".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoFom", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2017-01-01"));
      }
    } else {
      jsonMap.put("datoFom", "2017-01-01");
    }

    if ("BarnetilleggBP_datoTil".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoTil", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2020-01-01"));
      }
    } else {
      jsonMap.put("datoTil", "2020-01-01");
    }

    if ("BarnetilleggBP_soknadsbarnId".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("soknadsbarnId", nullVerdi ? null : (ugyldigVerdi ? "xx" : 1));
      }
    } else {
      jsonMap.put("soknadsbarnId", 1);
    }

    if ("BarnetilleggBP_bruttoBelop".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("bruttoBelop", nullVerdi ? null : (ugyldigVerdi ? "xx" : 100));
      }
    } else {
      jsonMap.put("bruttoBelop", 100);
    }

    if ("BarnetilleggBP_skattProsent".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("skattProsent", nullVerdi ? null : (ugyldigVerdi ? "xx" : 25));
      }
    } else {
      jsonMap.put("skattProsent", 25);
    }

    return new ObjectMapper().valueToTree(jsonMap);
  }

  // Bygger opp BarnetilleggForsvaret innhold
  private static JsonNode byggBarnetilleggForsvaretInnhold(String feltNavn, boolean manglerVerdi, boolean nullVerdi, boolean ugyldigVerdi) {
    var jsonMap = new HashMap<>();

    if ("BarnetilleggForsvaret_datoFom".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoFom", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2017-01-01"));
      }
    } else {
      jsonMap.put("datoFom", "2017-01-01");
    }

    if ("BarnetilleggForsvaret_datoTil".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoTil", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2020-01-01"));
      }
    } else {
      jsonMap.put("datoTil", "2020-01-01");
    }

    if ("BarnetilleggForsvaret_barnetilleggForsvaret".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("barnetilleggForsvaret", nullVerdi ? null : (ugyldigVerdi ? "xx" : true));
      }
    } else {
      jsonMap.put("barnetilleggForsvaret", true);
    }

    return new ObjectMapper().valueToTree(jsonMap);
  }

  // Bygger opp DeltBosted innhold
  private static JsonNode byggDeltBostedInnhold(String feltNavn, boolean manglerVerdi, boolean nullVerdi, boolean ugyldigVerdi) {
    var jsonMap = new HashMap<>();

    if ("DeltBosted_datoFom".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoFom", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2017-01-01"));
      }
    } else {
      jsonMap.put("datoFom", "2017-01-01");
    }

    if ("DeltBosted_datoTil".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoTil", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2020-01-01"));
      }
    } else {
      jsonMap.put("datoTil", "2020-01-01");
    }

    if ("DeltBosted_soknadsbarnId".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("soknadsbarnId", nullVerdi ? null : (ugyldigVerdi ? "xx" : 1));
      }
    } else {
      jsonMap.put("soknadsbarnId", 1);
    }

    if ("DeltBosted_deltBosted".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("deltBosted", nullVerdi ? null : (ugyldigVerdi ? "xx" : true));
      }
    } else {
      jsonMap.put("deltBosted", true);
    }

    return new ObjectMapper().valueToTree(jsonMap);
  }

  // Bygger opp AndreLopendeBidrag innhold
  private static JsonNode byggAndreLopendeBidragInnhold(String feltNavn, boolean manglerVerdi, boolean nullVerdi, boolean ugyldigVerdi) {
    var jsonMap = new HashMap<>();

    if ("AndreLopendeBidrag_datoFom".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoFom", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2017-01-01"));
      }
    } else {
      jsonMap.put("datoFom", "2017-01-01");
    }

    if ("AndreLopendeBidrag_datoTil".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("datoTil", nullVerdi ? null : (ugyldigVerdi ? "xx" : "2020-01-01"));
      }
    } else {
      jsonMap.put("datoTil", "2020-01-01");
    }

    if ("AndreLopendeBidrag_soknadsbarnId".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("soknadsbarnId", nullVerdi ? null : (ugyldigVerdi ? "xx" : 1));
      }
    } else {
      jsonMap.put("soknadsbarnId", 1);
    }

    if ("AndreLopendeBidrag_bidragBelop".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("bidragBelop", nullVerdi ? null : (ugyldigVerdi ? "xx" : 100));
      }
    } else {
      jsonMap.put("bidragBelop", 100);
    }

    if ("AndreLopendeBidrag_samvaersfradragBelop".equals(feltNavn)) {
      if (!manglerVerdi) {
        jsonMap.put("samvaersfradragBelop", nullVerdi ? null : (ugyldigVerdi ? "xx" : 100));
      }
    } else {
      jsonMap.put("samvaersfradragBelop", 100);
    }

    return new ObjectMapper().valueToTree(jsonMap);
  }

  //==================================================================================================================================================

  // Bygger opp BeregnetBidragsevneResultatCore
  public static BeregnetBidragsevneResultatCore dummyBidragsevneResultatCore() {
    var bidragPeriodeResultatListe = new ArrayList<no.nav.bidrag.beregn.bidragsevne.dto.ResultatPeriodeCore>();
    bidragPeriodeResultatListe.add(new no.nav.bidrag.beregn.bidragsevne.dto.ResultatPeriodeCore(
        new PeriodeCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        new no.nav.bidrag.beregn.bidragsevne.dto.ResultatBeregningCore(BigDecimal.valueOf(100), BigDecimal.valueOf(100)),
        emptyList()));
    return new BeregnetBidragsevneResultatCore(bidragPeriodeResultatListe, emptyList(), emptyList());
  }

  // Bygger opp BeregnetBidragsevneResultatCore med avvik
  public static BeregnetBidragsevneResultatCore dummyBidragsevneResultatCoreMedAvvik() {
    var avvikListe = new ArrayList<AvvikCore>();
    avvikListe.add(new AvvikCore("beregnDatoFra kan ikke være null", "NULL_VERDI_I_DATO"));
    avvikListe.add(new AvvikCore(
        "periodeDatoTil må være etter periodeDatoFra i inntektPeriodeListe: datoFra=2018-04-01, datoTil=2018-03-01",
        "DATO_FRA_ETTER_DATO_TIL"));
    return new BeregnetBidragsevneResultatCore(emptyList(), emptyList(), avvikListe);
  }


  // Bygger opp BeregnetNettoBarnetilsynResultatCore
  public static BeregnetNettoBarnetilsynResultatCore dummyNettoBarnetilsynResultatCore() {
    var bidragPeriodeResultatListe = new ArrayList<no.nav.bidrag.beregn.nettobarnetilsyn.dto.ResultatPeriodeCore>();
    bidragPeriodeResultatListe.add(new no.nav.bidrag.beregn.nettobarnetilsyn.dto.ResultatPeriodeCore(
        1,
        new PeriodeCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        new no.nav.bidrag.beregn.nettobarnetilsyn.dto.ResultatBeregningCore(BigDecimal.valueOf(100)),
        emptyList()));
    return new BeregnetNettoBarnetilsynResultatCore(bidragPeriodeResultatListe, emptyList(), emptyList());
  }

  // Bygger opp BeregnetNettoBarnetilsynResultatCore med avvik
  public static BeregnetNettoBarnetilsynResultatCore dummyNettoBarnetilsynResultatCoreMedAvvik() {
    var avvikListe = new ArrayList<AvvikCore>();
    avvikListe.add(new AvvikCore("beregnDatoFra kan ikke være null", "NULL_VERDI_I_DATO"));
    avvikListe.add(new AvvikCore(
        "periodeDatoTil må være etter periodeDatoFra i faktiskUtgiftPeriodeListe: datoFra=2018-04-01, datoTil=2018-03-01",
        "DATO_FRA_ETTER_DATO_TIL"));
    return new BeregnetNettoBarnetilsynResultatCore(emptyList(), emptyList(), avvikListe);
  }


  // Bygger opp BeregnetUnderholdskostnadResultatCore
  public static BeregnetUnderholdskostnadResultatCore dummyUnderholdskostnadResultatCore() {
    var bidragPeriodeResultatListe = new ArrayList<no.nav.bidrag.beregn.underholdskostnad.dto.ResultatPeriodeCore>();
    bidragPeriodeResultatListe.add(new no.nav.bidrag.beregn.underholdskostnad.dto.ResultatPeriodeCore(1,
        new PeriodeCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        new no.nav.bidrag.beregn.underholdskostnad.dto.ResultatBeregningCore(BigDecimal.valueOf(100)),
        emptyList()));
    return new BeregnetUnderholdskostnadResultatCore(bidragPeriodeResultatListe, emptyList(), emptyList());
  }

  // Bygger opp BeregnetUnderholdskostnadResultatCore med avvik
  public static BeregnetUnderholdskostnadResultatCore dummyUnderholdskostnadResultatCoreMedAvvik() {
    var avvikListe = new ArrayList<AvvikCore>();
    avvikListe.add(new AvvikCore("beregnDatoFra kan ikke være null", "NULL_VERDI_I_DATO"));
    avvikListe.add(new AvvikCore(
        "periodeDatoTil må være etter periodeDatoFra i forpleiningUtgiftPeriodeListe: datoFra=2018-04-01, datoTil=2018-03-01",
        "DATO_FRA_ETTER_DATO_TIL"));
    return new BeregnetUnderholdskostnadResultatCore(emptyList(), emptyList(), avvikListe);
  }


  // Bygger opp BeregnetBPsAndelUnderholdskostnadResultatCore
  public static BeregnetBPsAndelUnderholdskostnadResultatCore dummyBPsAndelUnderholdskostnadResultatCore() {
    var bidragPeriodeResultatListe = new ArrayList<no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatPeriodeCore>();
    bidragPeriodeResultatListe.add(new no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatPeriodeCore(1,
        new PeriodeCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        new no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatBeregningCore(BigDecimal.valueOf(10), BigDecimal.valueOf(100), false),
        emptyList()));
    return new BeregnetBPsAndelUnderholdskostnadResultatCore(bidragPeriodeResultatListe, emptyList(), emptyList());
  }

  // Bygger opp BeregnetBPsAndelUnderholdskostnadResultatCore med avvik
  public static BeregnetBPsAndelUnderholdskostnadResultatCore dummyBPsAndelUnderholdskostnadResultatCoreMedAvvik() {
    var avvikListe = new ArrayList<AvvikCore>();
    avvikListe.add(new AvvikCore("beregnDatoFra kan ikke være null", "NULL_VERDI_I_DATO"));
    avvikListe.add(new AvvikCore(
        "periodeDatoTil må være etter periodeDatoFra i inntektBPPeriodeListe: datoFra=2018-04-01, datoTil=2018-03-01",
        "DATO_FRA_ETTER_DATO_TIL"));
    return new BeregnetBPsAndelUnderholdskostnadResultatCore(emptyList(), emptyList(), avvikListe);
  }


  // Bygger opp BeregnetSamvaersfradragResultatCore
  public static BeregnetSamvaersfradragResultatCore dummySamvaersfradragResultatCore() {
    var bidragPeriodeResultatListe = new ArrayList<no.nav.bidrag.beregn.samvaersfradrag.dto.ResultatPeriodeCore>();
    bidragPeriodeResultatListe.add(new no.nav.bidrag.beregn.samvaersfradrag.dto.ResultatPeriodeCore(1,
        new PeriodeCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        new no.nav.bidrag.beregn.samvaersfradrag.dto.ResultatBeregningCore(BigDecimal.valueOf(100)),
        emptyList()));
    return new BeregnetSamvaersfradragResultatCore(bidragPeriodeResultatListe, emptyList(), emptyList());
  }

  // Bygger opp BeregnetSamvaersfradragResultatCore med avvik
  public static BeregnetSamvaersfradragResultatCore dummySamvaersfradragResultatCoreMedAvvik() {
    var avvikListe = new ArrayList<AvvikCore>();
    avvikListe.add(new AvvikCore("beregnDatoFra kan ikke være null", "NULL_VERDI_I_DATO"));
    avvikListe.add(new AvvikCore(
        "periodeDatoTil må være etter periodeDatoFra i samvaersklassePeriodeListe: datoFra=2018-04-01, datoTil=2018-03-01",
        "DATO_FRA_ETTER_DATO_TIL"));
    return new BeregnetSamvaersfradragResultatCore(emptyList(), emptyList(), avvikListe);
  }


  // Bygger opp BeregnetBarnebidragResultatCore
  public static BeregnetBarnebidragResultatCore dummyBarnebidragResultatCore() {
    var bidragPeriodeResultatListe = new ArrayList<no.nav.bidrag.beregn.barnebidrag.dto.ResultatPeriodeCore>();
    bidragPeriodeResultatListe.add(new no.nav.bidrag.beregn.barnebidrag.dto.ResultatPeriodeCore(
        1,
        new PeriodeCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        new no.nav.bidrag.beregn.barnebidrag.dto.ResultatBeregningCore(BigDecimal.valueOf(100), "RESULTATKODE"),
        emptyList()));
    return new BeregnetBarnebidragResultatCore(bidragPeriodeResultatListe, emptyList(), emptyList());
  }

  // Bygger opp BeregnetBarnebidragResultatCore med avvik
  public static BeregnetBarnebidragResultatCore dummyBarnebidragResultatCoreMedAvvik() {
    var avvikListe = new ArrayList<AvvikCore>();
    avvikListe.add(new AvvikCore("beregnDatoFra kan ikke være null", "NULL_VERDI_I_DATO"));
    avvikListe.add(new AvvikCore(
        "periodeDatoTil må være etter periodeDatoFra i samvaersfradragPeriodeListe: datoFra=2018-04-01, datoTil=2018-03-01",
        "DATO_FRA_ETTER_DATO_TIL"));
    return new BeregnetBarnebidragResultatCore(emptyList(), emptyList(), avvikListe);
  }

  //==================================================================================================================================================


  // Forholdsmessig fordeling
  public static BeregnForholdsmessigFordelingGrunnlag byggForholdsmessigFordelingGrunnlag() {
    return byggForholdsmessigFordelingGrunnlag("");
  }

  public static BeregnForholdsmessigFordelingGrunnlag byggForholdsmessigFordelingGrunnlagUtenBeregnDatoFra() {
    return byggForholdsmessigFordelingGrunnlag("beregnDatoFra");
  }

  public static BeregnForholdsmessigFordelingGrunnlag byggForholdsmessigFordelingGrunnlagUtenBeregnDatoTil() {
    return byggForholdsmessigFordelingGrunnlag("beregnDatoTil");
  }

  public static BeregnForholdsmessigFordelingGrunnlag byggForholdsmessigFordelingGrunnlagUtenBidragsevnePeriodeListe() {
    return byggForholdsmessigFordelingGrunnlag("bidragsevnePeriodeListe");
  }

  public static BeregnForholdsmessigFordelingGrunnlag byggForholdsmessigFordelingGrunnlagUtenBidragsevneDatoFraTil() {
    return byggForholdsmessigFordelingGrunnlag("bidragsevneDatoFraTil");
  }

  public static BeregnForholdsmessigFordelingGrunnlag byggForholdsmessigFordelingGrunnlagUtenBidragsevneDatoFra() {
    return byggForholdsmessigFordelingGrunnlag("bidragsevneDatoFra");
  }

  public static BeregnForholdsmessigFordelingGrunnlag byggForholdsmessigFordelingGrunnlagUtenBidragsevneDatoTil() {
    return byggForholdsmessigFordelingGrunnlag("bidragsevneDatoTil");
  }

  public static BeregnForholdsmessigFordelingGrunnlag byggForholdsmessigFordelingGrunnlagUtenBidragsevneBelop() {
    return byggForholdsmessigFordelingGrunnlag("bidragsevneBelop");
  }

  public static BeregnForholdsmessigFordelingGrunnlag byggForholdsmessigFordelingGrunnlagUtenBidragsevne25ProsentInntekt() {
    return byggForholdsmessigFordelingGrunnlag("bidragsevne25ProsentInntekt");
  }

  public static BeregnForholdsmessigFordelingGrunnlag byggForholdsmessigFordelingGrunnlagUtenBeregnetBidragSakPeriodeListe() {
    return byggForholdsmessigFordelingGrunnlag("beregnetBidragSakPeriodeListe");
  }

  public static BeregnForholdsmessigFordelingGrunnlag byggForholdsmessigFordelingGrunnlagUtenBeregnetBidragSaksnr() {
    return byggForholdsmessigFordelingGrunnlag("beregnetBidragSaksnr");
  }

  public static BeregnForholdsmessigFordelingGrunnlag byggForholdsmessigFordelingGrunnlagUtenBeregnetBidragDatoFraTil() {
    return byggForholdsmessigFordelingGrunnlag("beregnetBidragDatoFraTil");
  }

  public static BeregnForholdsmessigFordelingGrunnlag byggForholdsmessigFordelingGrunnlagUtenBeregnetBidragDatoFra() {
    return byggForholdsmessigFordelingGrunnlag("beregnetBidragDatoFra");
  }

  public static BeregnForholdsmessigFordelingGrunnlag byggForholdsmessigFordelingGrunnlagUtenBeregnetBidragDatoTil() {
    return byggForholdsmessigFordelingGrunnlag("beregnetBidragDatoTil");
  }

  public static BeregnForholdsmessigFordelingGrunnlag byggForholdsmessigFordelingGrunnlagUtenBeregnetBidragPerBarnListe() {
    return byggForholdsmessigFordelingGrunnlag("beregnetBidragPerBarnListe");
  }

  public static BeregnForholdsmessigFordelingGrunnlag byggForholdsmessigFordelingGrunnlagUtenBarnPersonId() {
    return byggForholdsmessigFordelingGrunnlag("barnPersonId");
  }

  public static BeregnForholdsmessigFordelingGrunnlag byggForholdsmessigFordelingGrunnlagUtenBidragBelop() {
    return byggForholdsmessigFordelingGrunnlag("bidragBelop");
  }

  // Bygger opp BeregnForholdsmessigFordelingGrunnlag
  private static BeregnForholdsmessigFordelingGrunnlag byggForholdsmessigFordelingGrunnlag(String nullVerdi) {
    var beregnDatoFra = (nullVerdi.equals("beregnDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var beregnDatoTil = (nullVerdi.equals("beregnDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var bidragsevneDatoFra = (nullVerdi.equals("bidragsevneDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var bidragsevneDatoTil = (nullVerdi.equals("bidragsevneDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var bidragsevneBelop = (nullVerdi.equals("bidragsevneBelop") ? null : BigDecimal.valueOf(100));
    var bidragsevne25ProsentInntekt = (nullVerdi.equals("bidragsevne25ProsentInntekt") ? null : BigDecimal.valueOf(100));
    var beregnetBidragSaksnr = (nullVerdi.equals("beregnetBidragSaksnr") ? null : 1);
    var beregnetBidragDatoFra = (nullVerdi.equals("beregnetBidragDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var beregnetBidragDatoTil = (nullVerdi.equals("beregnetBidragDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var barnPersonId = (nullVerdi.equals("barnPersonId") ? null : 1);
    var bidragBelop = (nullVerdi.equals("bidragBelop") ? null : BigDecimal.valueOf(100));

    List<BidragsevnePeriode> bidragsevnePeriodeListe;
    if (nullVerdi.equals("bidragsevnePeriodeListe")) {
      bidragsevnePeriodeListe = null;
    } else {
      BidragsevnePeriode bidragsevnePeriode;
      if (nullVerdi.equals("bidragsevneDatoFraTil")) {
        bidragsevnePeriode = new BidragsevnePeriode(null, bidragsevneBelop, bidragsevne25ProsentInntekt);
      } else {
        bidragsevnePeriode = new BidragsevnePeriode(new Periode(bidragsevneDatoFra, bidragsevneDatoTil), bidragsevneBelop,
            bidragsevne25ProsentInntekt);
      }
      bidragsevnePeriodeListe = new ArrayList<>();
      bidragsevnePeriodeListe.add(bidragsevnePeriode);
    }

    List<BeregnetBidragSakPeriode> beregnetBidragSakPeriodeListe;
    if (nullVerdi.equals("beregnetBidragSakPeriodeListe")) {
      beregnetBidragSakPeriodeListe = null;
    } else {
      List<BeregnetBidragPerBarn> beregnetBidragPerBarnListe;
      if (nullVerdi.equals("beregnetBidragPerBarnListe")) {
        beregnetBidragPerBarnListe = null;
      } else {
        var beregnetBidragPerBarn = new BeregnetBidragPerBarn(barnPersonId, bidragBelop);
        beregnetBidragPerBarnListe = new ArrayList<>();
        beregnetBidragPerBarnListe.add(beregnetBidragPerBarn);
      }

      BeregnetBidragSakPeriode beregnetBidragSakPeriode;
      if (nullVerdi.equals("beregnetBidragDatoFraTil")) {
        beregnetBidragSakPeriode = new BeregnetBidragSakPeriode(beregnetBidragSaksnr, null, beregnetBidragPerBarnListe);
      } else {
        beregnetBidragSakPeriode = new BeregnetBidragSakPeriode(beregnetBidragSaksnr, new Periode(beregnetBidragDatoFra, beregnetBidragDatoTil),
            beregnetBidragPerBarnListe);
      }
      beregnetBidragSakPeriodeListe = new ArrayList<>();
      beregnetBidragSakPeriodeListe.add(beregnetBidragSakPeriode);
    }

    return new BeregnForholdsmessigFordelingGrunnlag(beregnDatoFra, beregnDatoTil, bidragsevnePeriodeListe, beregnetBidragSakPeriodeListe);
  }

  // Bygger opp BeregnForholdsmessigFordelingResultatCore
  public static BeregnForholdsmessigFordelingResultatCore dummyForholdsmessigFordelingResultatCore() {
    var forholdsmessigFordelingPeriodeResultatListe = new ArrayList<no.nav.bidrag.beregn.forholdsmessigfordeling.dto.ResultatPeriodeCore>();
    forholdsmessigFordelingPeriodeResultatListe.add(new no.nav.bidrag.beregn.forholdsmessigfordeling.dto.ResultatPeriodeCore(
        new PeriodeCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        singletonList(new no.nav.bidrag.beregn.forholdsmessigfordeling.dto.ResultatBeregningCore(1,
            singletonList(new ResultatPerBarnCore(1, BigDecimal.valueOf(100), "RESULTATKODE")))),
        new no.nav.bidrag.beregn.forholdsmessigfordeling.dto.GrunnlagBeregningPeriodisertCore(
            new no.nav.bidrag.beregn.forholdsmessigfordeling.dto.BidragsevneCore(BigDecimal.valueOf(100), BigDecimal.valueOf(25)),
            singletonList(new BeregnetBidragSakCore(1,
                singletonList(new GrunnlagPerBarnCore(1, BigDecimal.valueOf(100))))))));
    return new BeregnForholdsmessigFordelingResultatCore(forholdsmessigFordelingPeriodeResultatListe, emptyList());
  }

  // Bygger opp BeregnForholdsmessigFordelingResultatCore med avvik
  public static BeregnForholdsmessigFordelingResultatCore dummyForholdsmessigFordelingResultatCoreMedAvvik() {
    var avvikListe = new ArrayList<AvvikCore>();
    avvikListe.add(new AvvikCore("beregnDatoFra kan ikke være null", "NULL_VERDI_I_DATO"));
    avvikListe.add(new AvvikCore(
        "periodeDatoTil må være etter periodeDatoFra i bidragsevnePeriodeListe: datoFra=2018-04-01, datoTil=2018-03-01",
        "DATO_FRA_ETTER_DATO_TIL"));
    return new BeregnForholdsmessigFordelingResultatCore(emptyList(), avvikListe);
  }

  //==================================================================================================================================================


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
