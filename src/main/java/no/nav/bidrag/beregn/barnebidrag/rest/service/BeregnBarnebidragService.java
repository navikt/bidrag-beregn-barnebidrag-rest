package no.nav.bidrag.beregn.barnebidrag.rest.service;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.BidragsevneConsumer;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Forbruksutgifter;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.MaksFradrag;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.MaksTilsyn;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Samvaersfradrag;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonConsumer;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Sjablontall;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBPsAndelUnderholdskostnadResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBarnebidragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBidragsevneGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBidragsevneResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnKostnadsberegnetBidragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnNettoBarnetilsynResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnSamvaersfradragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnUnderholdskostnadResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.BPsAndelUnderholdskostnadCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnBPsAndelUnderholdskostnadGrunnlagCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnBPsAndelUnderholdskostnadResultatCore;
import no.nav.bidrag.beregn.felles.dto.AvvikCore;
import no.nav.bidrag.beregn.felles.dto.PeriodeCore;
import no.nav.bidrag.beregn.felles.dto.SjablonInnholdCore;
import no.nav.bidrag.beregn.felles.dto.SjablonNokkelCore;
import no.nav.bidrag.beregn.felles.dto.SjablonPeriodeCore;
import no.nav.bidrag.beregn.felles.enums.SjablonInnholdNavn;
import no.nav.bidrag.beregn.felles.enums.SjablonNavn;
import no.nav.bidrag.beregn.felles.enums.SjablonNokkelNavn;
import no.nav.bidrag.beregn.felles.enums.SjablonTallNavn;
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.KostnadsberegnetBidragCore;
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.BPsAndelUnderholdskostnadPeriodeCore;
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.BeregnKostnadsberegnetBidragGrunnlagCore;
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.BeregnKostnadsberegnetBidragResultatCore;
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.SamvaersfradragPeriodeCore;
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.UnderholdskostnadPeriodeCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.NettoBarnetilsynCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnNettoBarnetilsynGrunnlagCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnNettoBarnetilsynResultatCore;
import no.nav.bidrag.beregn.samvaersfradrag.SamvaersfradragCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnSamvaersfradragGrunnlagCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnSamvaersfradragResultatCore;
import no.nav.bidrag.beregn.underholdskostnad.UnderholdskostnadCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadGrunnlagCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadResultatCore;
import no.nav.bidrag.commons.web.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BeregnBarnebidragService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BeregnBarnebidragService.class);

  private final BidragsevneConsumer bidragsevneConsumer;
  private final SjablonConsumer sjablonConsumer;
  private final NettoBarnetilsynCore nettoBarnetilsynCore;
  private final UnderholdskostnadCore underholdskostnadCore;
  private final BPsAndelUnderholdskostnadCore bpAndelUnderholdskostnadCore;
  private final SamvaersfradragCore samvaersfradragCore;
  private final KostnadsberegnetBidragCore kostnadsberegnetBidragCore;

  private HttpResponse<BeregnBidragsevneResultat> bidragsevneResultat;
  private HttpResponse<List<Sjablontall>> sjablonSjablontallResponse;
  private HttpResponse<List<Forbruksutgifter>> sjablonForbruksutgifterResponse;
  private HttpResponse<List<MaksTilsyn>> sjablonMaksTilsynResponse;
  private HttpResponse<List<MaksFradrag>> sjablonMaksFradragResponse;
  private HttpResponse<List<Samvaersfradrag>> sjablonSamvaersfradragResponse;
  private BeregnNettoBarnetilsynResultatCore nettoBarnetilsynResultat;
  private BeregnUnderholdskostnadResultatCore underholdskostnadResultat;
  private BeregnBPsAndelUnderholdskostnadResultatCore bpAndelUnderholdskostnadResultat;
  private BeregnSamvaersfradragResultatCore samvaersfradragResultat;
  private BeregnKostnadsberegnetBidragResultatCore kostnadsberegnetBidragResultat;

  private final Map<String, String> sjablontallMap = new HashMap<>() {{
    put("0001", SjablonTallNavn.ORDINAER_BARNETRYGD_BELOP.getNavn());
    put("0002", SjablonTallNavn.ORDINAER_SMAABARNSTILLEGG_BELOP.getNavn());
    put("0003", SjablonTallNavn.BOUTGIFTER_BIDRAGSBARN_BELOP.getNavn());
    put("0004", SjablonTallNavn.FORDEL_SKATTEKLASSE2_BELOP.getNavn());
    put("0005", SjablonTallNavn.FORSKUDDSSATS_BELOP.getNavn());
    put("0006", SjablonTallNavn.INNSLAG_KAPITALINNTEKT_BELOP.getNavn());
    put("0007", SjablonTallNavn.INNTEKTSINTERVALL_TILLEGGSBIDRAG_BELOP.getNavn());
    put("0008", SjablonTallNavn.MAKS_INNTEKT_BP_PROSENT.getNavn());
    put("0009", SjablonTallNavn.HOY_INNTEKT_BP_MULTIPLIKATOR.getNavn());
    put("0010", SjablonTallNavn.INNTEKT_BB_MULTIPLIKATOR.getNavn());
    put("0011", SjablonTallNavn.MAKS_BIDRAG_MULTIPLIKATOR.getNavn());
    put("0012", SjablonTallNavn.MAKS_INNTEKT_BB_MULTIPLIKATOR.getNavn());
    put("0013", SjablonTallNavn.MAKS_INNTEKT_FORSKUDD_MOTTAKER_MULTIPLIKATOR.getNavn());
    put("0014", SjablonTallNavn.NEDRE_INNTEKTSGRENSE_GEBYR_BELOP.getNavn());
    put("0015", SjablonTallNavn.SKATT_ALMINNELIG_INNTEKT_PROSENT.getNavn());
    put("0016", SjablonTallNavn.TILLEGGSBIDRAG_PROSENT.getNavn());
    put("0017", SjablonTallNavn.TRYGDEAVGIFT_PROSENT.getNavn());
    put("0018", SjablonTallNavn.BARNETILLEGG_SKATT_PROSENT.getNavn());
    put("0019", SjablonTallNavn.UNDERHOLD_EGNE_BARN_I_HUSSTAND_BELOP.getNavn());
    put("0020", SjablonTallNavn.ENDRING_BIDRAG_GRENSE_PROSENT.getNavn());
    put("0021", SjablonTallNavn.BARNETILLEGG_FORSVARET_FORSTE_BARN_BELOP.getNavn());
    put("0022", SjablonTallNavn.BARNETILLEGG_FORSVARET_OVRIGE_BARN_BELOP.getNavn());
    put("0023", SjablonTallNavn.MINSTEFRADRAG_INNTEKT_BELOP.getNavn());
    put("0024", SjablonTallNavn.GJENNOMSNITT_VIRKEDAGER_PR_MAANED_ANTALL.getNavn());
    put("0025", SjablonTallNavn.MINSTEFRADRAG_INNTEKT_PROSENT.getNavn());
    put("0026", SjablonTallNavn.DAGLIG_SATS_BARNETILLEGG_BELOP.getNavn());
    put("0027", SjablonTallNavn.PERSONFRADRAG_KLASSE1_BELOP.getNavn());
    put("0028", SjablonTallNavn.PERSONFRADRAG_KLASSE2_BELOP.getNavn());
    put("0029", SjablonTallNavn.KONTANTSTOTTE_BELOP.getNavn());
    put("0030", SjablonTallNavn.OVRE_INNTEKTSGRENSE_IKKE_I_SKATTEPOSISJON_BELOP.getNavn());
    put("0031", SjablonTallNavn.NEDRE_INNTEKTSGRENSE_FULL_SKATTEPOSISJON_BELOP.getNavn());
    put("0032", SjablonTallNavn.EKSTRA_SMAABARNSTILLEGG_BELOP.getNavn());
    put("0033", SjablonTallNavn.OVRE_INNTEKTSGRENSE_FULLT_FORSKUDD_BELOP.getNavn());
    put("0034", SjablonTallNavn.OVRE_INNTEKTSGRENSE_75PROSENT_FORSKUDD_EN_BELOP.getNavn());
    put("0035", SjablonTallNavn.OVRE_INNTEKTSGRENSE_75PROSENT_FORSKUDD_GS_BELOP.getNavn());
    put("0036", SjablonTallNavn.INNTEKTSINTERVALL_FORSKUDD_BELOP.getNavn());
    put("0037", SjablonTallNavn.OVRE_GRENSE_SAERTILSKUDD_BELOP.getNavn());
    put("0038", SjablonTallNavn.FORSKUDDSSATS_75PROSENT_BELOP.getNavn());
    put("0039", SjablonTallNavn.FORDEL_SAERFRADRAG_BELOP.getNavn());
    put("0040", SjablonTallNavn.SKATTESATS_ALMINNELIG_INNTEKT_PROSENT.getNavn());
    put("0041", SjablonTallNavn.FORHOYET_BARNETRYGD_BELOP.getNavn());
    put("0100", SjablonTallNavn.FASTSETTELSESGEBYR_BELOP.getNavn());
  }};

  public BeregnBarnebidragService(BidragsevneConsumer bidragsevneConsumer, SjablonConsumer sjablonConsumer,
      NettoBarnetilsynCore nettoBarnetilsynCore, UnderholdskostnadCore underholdskostnadCore,
      BPsAndelUnderholdskostnadCore bpAndelUnderholdskostnadCore, SamvaersfradragCore samvaersfradragCore,
      KostnadsberegnetBidragCore kostnadsberegnetBidragCore) {
    this.bidragsevneConsumer = bidragsevneConsumer;
    this.sjablonConsumer = sjablonConsumer;
    this.nettoBarnetilsynCore = nettoBarnetilsynCore;
    this.underholdskostnadCore = underholdskostnadCore;
    this.bpAndelUnderholdskostnadCore = bpAndelUnderholdskostnadCore;
    this.samvaersfradragCore = samvaersfradragCore;
    this.kostnadsberegnetBidragCore = kostnadsberegnetBidragCore;
  }

  public HttpResponse<BeregnBarnebidragResultat> beregn(BeregnBarnebidragGrunnlag beregnBarnebidragGrunnlag) {

    // Ekstraher grunnlag for beregning av netto barnetilsyn. Her gjøres også kontroll av inputdata
    var nettoBarnetilsynGrunnlagTilCore = beregnBarnebidragGrunnlag.getBeregnNettoBarnetilsynGrunnlag().tilCore();

    // Ekstraher grunnlag for beregning av underholdskostnad. Her gjøres også kontroll av inputdata
    var underholdskostnadGrunnlagTilCore = beregnBarnebidragGrunnlag.getBeregnUnderholdskostnadGrunnlag().tilCore();

    // Ekstraher grunnlag for beregning av BPs andel av underholdskostnad. Her gjøres også kontroll av inputdata
    var bpAndelUnderholdskostnadGrunnlagTilCore = beregnBarnebidragGrunnlag.getBeregnBPAndelUnderholdskostnadGrunnlag().tilCore();

    // Ekstraher grunnlag for beregning av samværsfradrag. Her gjøres også kontroll av inputdata
    var samvaersfradragGrunnlagTilCore = beregnBarnebidragGrunnlag.getBeregnSamvaersfradragGrunnlag().tilCore();

    // Hent sjabloner
    hentSjabloner();

    // Kall beregning av bidragsevne
    beregnBidragsevne(beregnBarnebidragGrunnlag.getBeregnBidragsevneGrunnlag());

    // Kall beregning av netto barnetilsyn
    beregnNettoBarnetilsyn(nettoBarnetilsynGrunnlagTilCore);

    // Kall beregning av underholdskostnad
    beregnUnderholdskostnad(underholdskostnadGrunnlagTilCore);

    // Kall beregning av BPs andel av underholdskostnad
    beregnBPsAndelUnderholdskostnad(bpAndelUnderholdskostnadGrunnlagTilCore);

    // Kall beregning av samværsfradrag
    beregnSamvaersfradrag(samvaersfradragGrunnlagTilCore);

    // Kall beregning av kostnadsberegnet bidrag
    beregnKostnadsberegnetBidrag(underholdskostnadGrunnlagTilCore);

    // Kall totalberegning

    return HttpResponse.from(HttpStatus.OK, new BeregnBarnebidragResultat(bidragsevneResultat.getResponseEntity().getBody(),
        new BeregnNettoBarnetilsynResultat(nettoBarnetilsynResultat), new BeregnUnderholdskostnadResultat(underholdskostnadResultat),
        new BeregnBPsAndelUnderholdskostnadResultat(bpAndelUnderholdskostnadResultat), new BeregnSamvaersfradragResultat(samvaersfradragResultat),
        new BeregnKostnadsberegnetBidragResultat(kostnadsberegnetBidragResultat)));
  }

  // Henter sjabloner
  private void hentSjabloner() {
    // Henter sjabloner for sjablontall
    sjablonSjablontallResponse = sjablonConsumer.hentSjablonSjablontall();
    LOGGER.debug("Antall sjabloner hentet av type Sjablontall: {}", sjablonSjablontallResponse.getResponseEntity().getBody().size());

    // Henter sjabloner for forbruksutgifter
    sjablonForbruksutgifterResponse = sjablonConsumer.hentSjablonForbruksutgifter();
    LOGGER.debug("Antall sjabloner hentet av type Bidragsevne: {}", sjablonForbruksutgifterResponse.getResponseEntity().getBody().size());

    // Henter sjabloner for maks tilsyn
    sjablonMaksTilsynResponse = sjablonConsumer.hentSjablonMaksTilsyn();
    LOGGER.debug("Antall sjabloner hentet av type Maks tilsyn: {}", sjablonMaksTilsynResponse.getResponseEntity().getBody().size());

    // Henter sjabloner for maks bidrag
    sjablonMaksFradragResponse = sjablonConsumer.hentSjablonMaksFradrag();
    LOGGER.debug("Antall sjabloner hentet av type Maks fradrag: {}", sjablonMaksFradragResponse.getResponseEntity().getBody().size());

    // Henter sjabloner for samværsfradrag
    sjablonSamvaersfradragResponse = sjablonConsumer.hentSjablonSamvaersfradrag();
    LOGGER.debug("Antall sjabloner hentet av type Samværsfradrag: {}", sjablonSamvaersfradragResponse.getResponseEntity().getBody().size());
  }

  // Kaller rest-modul for beregning av bidragsevne
  private void beregnBidragsevne(BeregnBidragsevneGrunnlag bidragsevneGrunnlag) {
    bidragsevneResultat = bidragsevneConsumer.hentBidragsevne(bidragsevneGrunnlag);
  }

  // Beregning av netto barnetilsyn
  private void beregnNettoBarnetilsyn(BeregnNettoBarnetilsynGrunnlagCore nettoBarnetilsynGrunnlag) {
    // Populerer liste over aktuelle sjabloner
    var sjablonPeriodeListe = new ArrayList<SjablonPeriodeCore>();
    sjablonPeriodeListe.addAll(mapSjablonSjablontall(sjablonSjablontallResponse.getResponseEntity().getBody()));
    sjablonPeriodeListe.addAll(mapSjablonMaksTilsyn(sjablonMaksTilsynResponse.getResponseEntity().getBody()));
    sjablonPeriodeListe.addAll(mapSjablonMaksFradrag(sjablonMaksFradragResponse.getResponseEntity().getBody()));
    nettoBarnetilsynGrunnlag.setSjablonPeriodeListe(sjablonPeriodeListe);

    // Kaller core-modulen for beregning av netto barnetilsyn
    LOGGER.debug("Netto barnetilsyn - grunnlag for beregning: {}", nettoBarnetilsynGrunnlag);
    nettoBarnetilsynResultat = nettoBarnetilsynCore.beregnNettoBarnetilsyn(nettoBarnetilsynGrunnlag);

    if (!nettoBarnetilsynResultat.getAvvikListe().isEmpty()) {
      LOGGER.error("Ugyldig input ved beregning av netto barnetilsyn. Følgende avvik ble funnet: " + System.lineSeparator()
          + nettoBarnetilsynResultat.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining(System.lineSeparator())));
      LOGGER.info("Netto barnetilsyn - grunnlag for beregning:" + System.lineSeparator()
          + "beregnDatoFra= " + nettoBarnetilsynGrunnlag.getBeregnDatoFra() + System.lineSeparator()
          + "beregnDatoTil= " + nettoBarnetilsynGrunnlag.getBeregnDatoTil() + System.lineSeparator()
          + "faktiskUtgiftPeriodeListe= " + nettoBarnetilsynGrunnlag.getFaktiskUtgiftPeriodeListe());
      throw new UgyldigInputException("Ugyldig input ved beregning av netto barnetilsyn. Følgende avvik ble funnet: "
          + nettoBarnetilsynResultat.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining("; ")));
    }

    LOGGER.debug("Netto barnetilsyn - resultat av beregning: {}", nettoBarnetilsynResultat.getResultatPeriodeListe());
  }

  // Beregning av underholdskostnad
  private void beregnUnderholdskostnad(BeregnUnderholdskostnadGrunnlagCore underholdskostnadGrunnlag) {
    // Populerer liste over aktuelle sjabloner
    var sjablonPeriodeListe = new ArrayList<SjablonPeriodeCore>();
    sjablonPeriodeListe.addAll(mapSjablonSjablontall(sjablonSjablontallResponse.getResponseEntity().getBody()));
    sjablonPeriodeListe.addAll(mapSjablonForbruksutgifter(sjablonForbruksutgifterResponse.getResponseEntity().getBody()));
    underholdskostnadGrunnlag.setSjablonPeriodeListe(sjablonPeriodeListe);

    //TODO Legg til NettoBarnetilsynPeriodeCore fra netto barnetilsyn core-tjenesten
    //underholdskostnadGrunnlag.setNettoBarnetilsynPeriodeListe(nettoBarnetilsynResultat.getResultatPeriodeListe().???);

    // Kaller core-modulen for beregning av underholdskostnad
    LOGGER.debug("Underholdskostnad - grunnlag for beregning: {}", underholdskostnadGrunnlag);
    underholdskostnadResultat = underholdskostnadCore.beregnUnderholdskostnad(underholdskostnadGrunnlag);

    if (!underholdskostnadResultat.getAvvikListe().isEmpty()) {
      LOGGER.error("Ugyldig input ved beregning av underholdskostnad. Følgende avvik ble funnet: " + System.lineSeparator()
          + underholdskostnadResultat.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining(System.lineSeparator())));
      LOGGER.info("Underholdskostnad - grunnlag for beregning: " + System.lineSeparator()
          + "beregnDatoFra= " + underholdskostnadGrunnlag.getBeregnDatoFra() + System.lineSeparator()
          + "beregnDatoTil= " + underholdskostnadGrunnlag.getBeregnDatoTil() + System.lineSeparator()
          + "soknadBarnFodselsdato= " + underholdskostnadGrunnlag.getSoknadBarnFodselsdato() + System.lineSeparator()
          + "barneTilsynMedStonadPeriodeListe= " + underholdskostnadGrunnlag.getBarnetilsynMedStonadPeriodeListe() + System.lineSeparator()
          + "forpleiningUtgiftPeriodeListe= " + underholdskostnadGrunnlag.getForpleiningUtgiftPeriodeListe() + System.lineSeparator()
          + "nettoBarnetilsynPeriodeListe= " + underholdskostnadGrunnlag.getNettoBarnetilsynPeriodeListe() + System.lineSeparator());
      throw new UgyldigInputException("Ugyldig input ved beregning av underholdskostnad. Følgende avvik ble funnet: "
          + underholdskostnadResultat.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining("; ")));
    }

    LOGGER.debug("Underholdskostnad - resultat av beregning: {}", underholdskostnadResultat.getResultatPeriodeListe());
  }

  // Beregning av BPs andel av underholdskostnad
  private void beregnBPsAndelUnderholdskostnad(BeregnBPsAndelUnderholdskostnadGrunnlagCore bpAndelUnderholdskostnadGrunnlag) {
    // Populerer liste over aktuelle sjabloner
    var sjablonPeriodeListe = new ArrayList<>(mapSjablonSjablontall(sjablonSjablontallResponse.getResponseEntity().getBody()));
    bpAndelUnderholdskostnadGrunnlag.setSjablonPeriodeListe(sjablonPeriodeListe);

    // Kaller core-modulen for beregning av BPs andel av underholdskostnad
    LOGGER.debug("BPs andel av underholdskostnad - grunnlag for beregning: {}", bpAndelUnderholdskostnadGrunnlag);
    bpAndelUnderholdskostnadResultat = bpAndelUnderholdskostnadCore.beregnBPsAndelUnderholdskostnad(bpAndelUnderholdskostnadGrunnlag);

    if (!bpAndelUnderholdskostnadResultat.getAvvikListe().isEmpty()) {
      LOGGER.error("Ugyldig input ved beregning av BPs andel av underholdskostnad. Følgende avvik ble funnet: " + System.lineSeparator()
          + bpAndelUnderholdskostnadResultat.getAvvikListe().stream().map(AvvikCore::getAvvikTekst)
          .collect(Collectors.joining(System.lineSeparator())));
      LOGGER.info("BPs andel av underholdskostnad - grunnlag for beregning: " + System.lineSeparator()
          + "beregnDatoFra= " + bpAndelUnderholdskostnadGrunnlag.getBeregnDatoFra() + System.lineSeparator()
          + "beregnDatoTil= " + bpAndelUnderholdskostnadGrunnlag.getBeregnDatoTil() + System.lineSeparator()
          + "inntekterPeriodeListe= " + bpAndelUnderholdskostnadGrunnlag.getInntekterPeriodeListe() + System.lineSeparator());
      throw new UgyldigInputException("Ugyldig input ved beregning av BPs andel av underholdskostnad. Følgende avvik ble funnet: "
          + bpAndelUnderholdskostnadResultat.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining("; ")));
    }

    LOGGER.debug("BPs andel av underholdskostnad - resultat av beregning: {}", bpAndelUnderholdskostnadResultat.getResultatPeriodeListe());
  }

  // Beregning av samværsfradrag
  private void beregnSamvaersfradrag(BeregnSamvaersfradragGrunnlagCore samvaersfradragGrunnlag) {
    // Populerer liste over aktuelle sjabloner
    var sjablonPeriodeListe = new ArrayList<>(mapSjablonSamvaersfradrag(sjablonSamvaersfradragResponse.getResponseEntity().getBody()));
    samvaersfradragGrunnlag.setSjablonPeriodeListe(sjablonPeriodeListe);

    // Kaller core-modulen for beregning av samværsfradrag
    LOGGER.debug("Samværsfradrag - grunnlag for beregning: {}", samvaersfradragGrunnlag);
    samvaersfradragResultat = samvaersfradragCore.beregnSamvaersfradrag(samvaersfradragGrunnlag);

    if (!samvaersfradragResultat.getAvvikListe().isEmpty()) {
      LOGGER.error("Ugyldig input ved beregning av samværsfradrag. Følgende avvik ble funnet: " + System.lineSeparator()
          + samvaersfradragResultat.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining(System.lineSeparator())));
      LOGGER.info("Samværsfradrag - grunnlag for beregning: " + System.lineSeparator()
          + "beregnDatoFra= " + samvaersfradragGrunnlag.getBeregnDatoFra() + System.lineSeparator()
          + "beregnDatoTil= " + samvaersfradragGrunnlag.getBeregnDatoTil() + System.lineSeparator()
          + "soknadsbarnFodselsdato= " + samvaersfradragGrunnlag.getSoknadsbarnFodselsdato() + System.lineSeparator()
          + "samvaersklassePeriodeListe= " + samvaersfradragGrunnlag.getSamvaersklassePeriodeListe() + System.lineSeparator());
      throw new UgyldigInputException("Ugyldig input ved beregning av samværsfradrag. Følgende avvik ble funnet: "
          + samvaersfradragResultat.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining("; ")));
    }

    LOGGER.debug("Samværsfradrag - resultat av beregning: {}", samvaersfradragResultat.getResultatPeriodeListe());
  }

  // Beregning av kostnadsberegnet bidrag
  private void beregnKostnadsberegnetBidrag(BeregnUnderholdskostnadGrunnlagCore underholdskostnadGrunnlagTilCore) {
    // Bygger kostnadsberegnet bidrag grunnlag
    var kostnadsberegnetBidragGrunnlag = byggKostnadsberegnetBidragGrunnlag(underholdskostnadGrunnlagTilCore);

    // Kaller core-modulen for beregning av kostnadsberegnet bidrag
    LOGGER.debug("Kostnadsberegnet bidrag - grunnlag for beregning: {}", kostnadsberegnetBidragGrunnlag);
    kostnadsberegnetBidragResultat = kostnadsberegnetBidragCore.beregnKostnadsberegnetBidrag(kostnadsberegnetBidragGrunnlag);

    if (!kostnadsberegnetBidragResultat.getAvvikListe().isEmpty()) {
      LOGGER.error("Ugyldig input ved beregning av kostnadsberegnet bidrag. Følgende avvik ble funnet: " + System.lineSeparator()
          + kostnadsberegnetBidragResultat.getAvvikListe().stream().map(AvvikCore::getAvvikTekst)
          .collect(Collectors.joining(System.lineSeparator())));
      LOGGER.info("Kostnadsberegnet bidrag - grunnlag for beregning: " + System.lineSeparator()
          + "beregnDatoFra= " + kostnadsberegnetBidragGrunnlag.getBeregnDatoFra() + System.lineSeparator()
          + "beregnDatoTil= " + kostnadsberegnetBidragGrunnlag.getBeregnDatoTil() + System.lineSeparator()
          + "underholdskostnadPeriodeListe= " + kostnadsberegnetBidragGrunnlag.getUnderholdskostnadPeriodeListe() + System.lineSeparator()
          + "bPsAndelUnderholdskostnadPeriodeListe= " + kostnadsberegnetBidragGrunnlag.getBPsAndelUnderholdskostnadPeriodeListe() +
          System.lineSeparator()
          + "samvaersfradragPeriodeListe= " + kostnadsberegnetBidragGrunnlag.getSamvaersfradragPeriodeListe() + System.lineSeparator());
      throw new UgyldigInputException("Ugyldig input ved beregning av kostnadsberegnet bidrag. Følgende avvik ble funnet: "
          + kostnadsberegnetBidragResultat.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining("; ")));
    }

    LOGGER.debug("Kostnadsberegnet bidrag - resultat av beregning: {}", kostnadsberegnetBidragResultat.getResultatPeriodeListe());
  }

  // Beregning av kostnadsberegnet bidrag
  private BeregnKostnadsberegnetBidragGrunnlagCore byggKostnadsberegnetBidragGrunnlag(
      BeregnUnderholdskostnadGrunnlagCore underholdskostnadGrunnlagTilCore) {
    var underholdskostnadPeriodeCoreListe = new ArrayList<UnderholdskostnadPeriodeCore>();
    for (var underholdskostnadPeriodeCore : underholdskostnadResultat.getResultatPeriodeListe()) {
      underholdskostnadPeriodeCoreListe.add(new UnderholdskostnadPeriodeCore(
          underholdskostnadPeriodeCore.getResultatDatoFraTil(),
          underholdskostnadPeriodeCore.getResultatBeregning().getResultatBelopUnderholdskostnad()));
    }

    var bpAndelUnderholdskostnadPeriodeCoreListe = new ArrayList<BPsAndelUnderholdskostnadPeriodeCore>();
    for (var bpAndelUnderholdskostnadPeriodeCore : bpAndelUnderholdskostnadResultat.getResultatPeriodeListe()) {
      bpAndelUnderholdskostnadPeriodeCoreListe.add(new BPsAndelUnderholdskostnadPeriodeCore(
          bpAndelUnderholdskostnadPeriodeCore.getResultatDatoFraTil(),
          bpAndelUnderholdskostnadPeriodeCore.getResultatBeregning().getResultatAndelProsent()));
    }

    var samvaersfradragPeriodeCoreListe = new ArrayList<SamvaersfradragPeriodeCore>();
    for (var samvaersfradragPeriodeCore : samvaersfradragResultat.getResultatPeriodeListe()) {
      samvaersfradragPeriodeCoreListe.add(new SamvaersfradragPeriodeCore(
          samvaersfradragPeriodeCore.getResultatDatoFraTil(), samvaersfradragPeriodeCore.getResultatBeregning().getResultatSamvaersfradragBelop()));
    }

    //TODO beregnDatoFra og bergnDatoTil må hentes fra et annet sted
    return new BeregnKostnadsberegnetBidragGrunnlagCore(underholdskostnadGrunnlagTilCore.getBeregnDatoFra(),
        underholdskostnadGrunnlagTilCore.getBeregnDatoTil(), underholdskostnadPeriodeCoreListe, bpAndelUnderholdskostnadPeriodeCoreListe,
        samvaersfradragPeriodeCoreListe);
  }

  // Mapper sjabloner av typen sjablontall
  private List<SjablonPeriodeCore> mapSjablonSjablontall(List<Sjablontall> sjablonSjablontallListe) {
    return sjablonSjablontallListe
        .stream()
        .map(sSL -> new SjablonPeriodeCore(
            new PeriodeCore(sSL.getDatoFom(), sSL.getDatoTom()),
            sjablontallMap.getOrDefault(sSL.getTypeSjablon(), sSL.getTypeSjablon()),
            emptyList(),
            singletonList(new SjablonInnholdCore(SjablonInnholdNavn.SJABLON_VERDI.getNavn(), sSL.getVerdi().doubleValue()))))
        .collect(toList());
  }

  // Mapper sjabloner av typen forbruksutgifter
  private List<SjablonPeriodeCore> mapSjablonForbruksutgifter(List<Forbruksutgifter> sjablonForbruksutgifterListe) {

    return sjablonForbruksutgifterListe
        .stream()
        .map(sFL -> new SjablonPeriodeCore(
            new PeriodeCore(sFL.getDatoFom(), sFL.getDatoTom()),
            SjablonNavn.FORBRUKSUTGIFTER.getNavn(),
            singletonList(new SjablonNokkelCore(SjablonNokkelNavn.ALDER_TOM.getNavn(), sFL.getAlderTom().toString())),
            singletonList(new SjablonInnholdCore(SjablonInnholdNavn.FORBRUK_TOTAL_BELOP.getNavn(), sFL.getBelopForbrukTot().doubleValue()))))
        .collect(toList());
  }

  // Mapper sjabloner av typen maks tilsyn
  private List<SjablonPeriodeCore> mapSjablonMaksTilsyn(List<MaksTilsyn> sjablonMaksTilsynListe) {

    return sjablonMaksTilsynListe
        .stream()
        .map(sMTL -> new SjablonPeriodeCore(
            new PeriodeCore(sMTL.getDatoFom(), sMTL.getDatoTom()),
            SjablonNavn.MAKS_TILSYN.getNavn(),
            singletonList(new SjablonNokkelCore(SjablonNokkelNavn.ANTALL_BARN_TOM.getNavn(), sMTL.getAntBarnTom().toString())),
            singletonList(new SjablonInnholdCore(SjablonInnholdNavn.MAKS_TILSYN_BELOP.getNavn(), sMTL.getMaksBelopTilsyn().doubleValue()))))
        .collect(toList());
  }

  // Mapper sjabloner av typen maks fradrag
  private List<SjablonPeriodeCore> mapSjablonMaksFradrag(List<MaksFradrag> sjablonMaksFradragListe) {

    return sjablonMaksFradragListe
        .stream()
        .map(sMFL -> new SjablonPeriodeCore(
            new PeriodeCore(sMFL.getDatoFom(), sMFL.getDatoTom()),
            SjablonNavn.MAKS_FRADRAG.getNavn(),
            singletonList(new SjablonNokkelCore(SjablonNokkelNavn.ANTALL_BARN_TOM.getNavn(), sMFL.getAntBarnTom().toString())),
            singletonList(new SjablonInnholdCore(SjablonInnholdNavn.MAKS_FRADRAG_BELOP.getNavn(), sMFL.getMaksBelopFradrag().doubleValue()))))
        .collect(toList());
  }

  // Mapper sjabloner av typen samværsfradrag
  private List<SjablonPeriodeCore> mapSjablonSamvaersfradrag(List<Samvaersfradrag> sjablonSamvaersfradragListe) {

    return sjablonSamvaersfradragListe
        .stream()
        .map(sSL -> new SjablonPeriodeCore(
            new PeriodeCore(sSL.getDatoFom(), sSL.getDatoTom()),
            SjablonNavn.SAMVAERSFRADRAG.getNavn(),
            asList(new SjablonNokkelCore(SjablonNokkelNavn.SAMVAERSKLASSE.getNavn(), sSL.getSamvaersklasse()),
                new SjablonNokkelCore(SjablonNokkelNavn.ALDER_TOM.getNavn(), sSL.getAlderTom().toString())),
            asList(new SjablonInnholdCore(SjablonInnholdNavn.ANTALL_DAGER_TOM.getNavn(), sSL.getAntDagerTom().doubleValue()),
                new SjablonInnholdCore(SjablonInnholdNavn.ANTALL_NETTER_TOM.getNavn(), sSL.getAntNetterTom().doubleValue()),
                new SjablonInnholdCore(SjablonInnholdNavn.FRADRAG_BELOP.getNavn(), sSL.getBelopFradrag().doubleValue()))))
        .collect(toList());
  }
}