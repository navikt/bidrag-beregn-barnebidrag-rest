package no.nav.bidrag.beregn.barnebidrag.rest.service;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import no.nav.bidrag.beregn.barnebidrag.rest.BarnebidragCore;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Bidragsevne;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Forbruksutgifter;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.MaksFradrag;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.MaksTilsyn;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Samvaersfradrag;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonConsumer;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Sjablontall;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.TrinnvisSkattesats;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBPsAndelUnderholdskostnadResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBarnebidragGrunnlagCore;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBarnebidragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBarnebidragResultatCore;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBidragsevneResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnKostnadsberegnetBidragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnNettoBarnetilsynResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnSamvaersfradragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnUnderholdskostnadResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BidragsevnePeriodeCore;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.KostnadsberegnetBidragPeriodeCore;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;
import no.nav.bidrag.beregn.bidragsevne.BidragsevneCore;
import no.nav.bidrag.beregn.bidragsevne.dto.BeregnBidragsevneGrunnlagAltCore;
import no.nav.bidrag.beregn.bidragsevne.dto.BeregnBidragsevneResultatCore;
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
import no.nav.bidrag.beregn.underholdskostnad.dto.NettoBarnetilsynPeriodeCore;
import no.nav.bidrag.commons.web.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BeregnBarnebidragService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BeregnBarnebidragService.class);

  private final SjablonConsumer sjablonConsumer;
  private final BidragsevneCore bidragsevneCore;
  private final NettoBarnetilsynCore nettoBarnetilsynCore;
  private final UnderholdskostnadCore underholdskostnadCore;
  private final BPsAndelUnderholdskostnadCore bpAndelUnderholdskostnadCore;
  private final SamvaersfradragCore samvaersfradragCore;
  private final KostnadsberegnetBidragCore kostnadsberegnetBidragCore;
  private final BarnebidragCore barnebidragCore;

  private HttpResponse<List<Sjablontall>> sjablonSjablontallResponse;
  private HttpResponse<List<Forbruksutgifter>> sjablonForbruksutgifterResponse;
  private HttpResponse<List<MaksTilsyn>> sjablonMaksTilsynResponse;
  private HttpResponse<List<MaksFradrag>> sjablonMaksFradragResponse;
  private HttpResponse<List<Samvaersfradrag>> sjablonSamvaersfradragResponse;
  private HttpResponse<List<Bidragsevne>> sjablonBidragsevneResponse;
  private HttpResponse<List<TrinnvisSkattesats>> sjablonTrinnvisSkattesatsResponse;
  private BeregnBidragsevneResultatCore bidragsevneResultat;
  private BeregnNettoBarnetilsynResultatCore nettoBarnetilsynResultat;
  private BeregnUnderholdskostnadResultatCore underholdskostnadResultat;
  private BeregnBPsAndelUnderholdskostnadResultatCore bpAndelUnderholdskostnadResultat;
  private BeregnSamvaersfradragResultatCore samvaersfradragResultat;
  private BeregnKostnadsberegnetBidragResultatCore kostnadsberegnetBidragResultat;
  private BeregnBarnebidragResultatCore barnebidragResultat;

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

  public BeregnBarnebidragService(SjablonConsumer sjablonConsumer, BidragsevneCore bidragsevneCore,
      NettoBarnetilsynCore nettoBarnetilsynCore, UnderholdskostnadCore underholdskostnadCore,
      BPsAndelUnderholdskostnadCore bpAndelUnderholdskostnadCore, SamvaersfradragCore samvaersfradragCore,
      KostnadsberegnetBidragCore kostnadsberegnetBidragCore, BarnebidragCore barnebidragCore) {
    this.sjablonConsumer = sjablonConsumer;
    this.bidragsevneCore = bidragsevneCore;
    this.nettoBarnetilsynCore = nettoBarnetilsynCore;
    this.underholdskostnadCore = underholdskostnadCore;
    this.bpAndelUnderholdskostnadCore = bpAndelUnderholdskostnadCore;
    this.samvaersfradragCore = samvaersfradragCore;
    this.kostnadsberegnetBidragCore = kostnadsberegnetBidragCore;
    this.barnebidragCore = barnebidragCore;
  }

  public HttpResponse<BeregnTotalBarnebidragResultat> beregn(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag) {

    // Kontroll av felles inputdata
    beregnTotalBarnebidragGrunnlag.validerTotalBarnebidragGrunnlag();

    // Ekstraher grunnlag for beregning av bidragsevne. Her gjøres også kontroll av inputdata
    var bidragsevneGrunnlagTilCore = beregnTotalBarnebidragGrunnlag.bidragsevneTilCore();

    // Ekstraher grunnlag for beregning av netto barnetilsyn. Her gjøres også kontroll av inputdata
    var nettoBarnetilsynGrunnlagTilCore = beregnTotalBarnebidragGrunnlag.nettoBarnetilsynTilCore();

    // Ekstraher grunnlag for beregning av underholdskostnad. Her gjøres også kontroll av inputdata
    var underholdskostnadGrunnlagTilCore = beregnTotalBarnebidragGrunnlag.underholdskostnadTilCore();

    // Ekstraher grunnlag for beregning av BPs andel av underholdskostnad. Her gjøres også kontroll av inputdata
    var bpAndelUnderholdskostnadGrunnlagTilCore = beregnTotalBarnebidragGrunnlag.bpAndelUnderholdskostnadTilCore();

    // Ekstraher grunnlag for beregning av samværsfradrag. Her gjøres også kontroll av inputdata
    var samvaersfradragGrunnlagTilCore = beregnTotalBarnebidragGrunnlag.samvaersfradragTilCore();

    // Ekstraher grunnlag for beregning av barnebidrag. Her gjøres også kontroll av inputdata
    var barnebidragGrunnlagTilCore = beregnTotalBarnebidragGrunnlag.barnebidragTilCore();

    // Hent sjabloner
    hentSjabloner();

    // Kall beregning av bidragsevne
    beregnBidragsevne(bidragsevneGrunnlagTilCore);

    // Kall beregning av netto barnetilsyn
    beregnNettoBarnetilsyn(nettoBarnetilsynGrunnlagTilCore);

    // Kall beregning av underholdskostnad
    beregnUnderholdskostnad(beregnTotalBarnebidragGrunnlag.getSoknadBarnPersonId(), underholdskostnadGrunnlagTilCore);

    // Kall beregning av BPs andel av underholdskostnad
    beregnBPsAndelUnderholdskostnad(bpAndelUnderholdskostnadGrunnlagTilCore);

    // Kall beregning av samværsfradrag
    beregnSamvaersfradrag(samvaersfradragGrunnlagTilCore);

    // Kall beregning av kostnadsberegnet bidrag
    beregnKostnadsberegnetBidrag(beregnTotalBarnebidragGrunnlag.getBeregnDatoFra(), beregnTotalBarnebidragGrunnlag.getBeregnDatoTil(),
        underholdskostnadGrunnlagTilCore);

    // Kall beregning av barnebidrag
    beregnBarnebidrag(barnebidragGrunnlagTilCore);

    return HttpResponse.from(HttpStatus.OK, new BeregnTotalBarnebidragResultat(new BeregnBidragsevneResultat(bidragsevneResultat),
        new BeregnNettoBarnetilsynResultat(nettoBarnetilsynResultat), new BeregnUnderholdskostnadResultat(underholdskostnadResultat),
        new BeregnBPsAndelUnderholdskostnadResultat(bpAndelUnderholdskostnadResultat), new BeregnSamvaersfradragResultat(samvaersfradragResultat),
        new BeregnKostnadsberegnetBidragResultat(kostnadsberegnetBidragResultat), new BeregnBarnebidragResultat(barnebidragResultat)));
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

    // Henter sjabloner for bidragsevne
    sjablonBidragsevneResponse = sjablonConsumer.hentSjablonBidragsevne();
    LOGGER.debug("Antall sjabloner hentet av type Bidragsevne: {}", sjablonBidragsevneResponse.getResponseEntity().getBody().size());

    // Henter sjabloner for trinnvis skattesats
    sjablonTrinnvisSkattesatsResponse = sjablonConsumer.hentSjablonTrinnvisSkattesats();
    LOGGER.debug("Antall sjabloner hentet av type Trinnvis skattesats: {}", sjablonTrinnvisSkattesatsResponse.getResponseEntity().getBody().size());
  }

  // Beregning av bidragsevne
  private void beregnBidragsevne(BeregnBidragsevneGrunnlagAltCore bidragsevneGrunnlag) {
    // Populerer liste over aktuelle sjabloner
    var sjablonPeriodeListe = new ArrayList<SjablonPeriodeCore>();
    sjablonPeriodeListe.addAll(mapSjablonSjablontall(sjablonSjablontallResponse.getResponseEntity().getBody()));
    sjablonPeriodeListe.addAll(mapSjablonBidragsevne(sjablonBidragsevneResponse.getResponseEntity().getBody()));
    sjablonPeriodeListe.addAll(mapSjablonTrinnvisSkattesats(sjablonTrinnvisSkattesatsResponse.getResponseEntity().getBody()));
    bidragsevneGrunnlag.setSjablonPeriodeListe(sjablonPeriodeListe);

    // Kaller core-modulen for beregning av bidragsevne
    LOGGER.debug("Bidragsevne - grunnlag for beregning: {}", bidragsevneGrunnlag);
    bidragsevneResultat = bidragsevneCore.beregnBidragsevne(bidragsevneGrunnlag);

    if (!bidragsevneResultat.getAvvikListe().isEmpty()) {
      LOGGER.error("Ugyldig input ved beregning av bidragsevne. Følgende avvik ble funnet: " + System.lineSeparator()
          + bidragsevneResultat.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining(System.lineSeparator())));
      LOGGER.info("Bidragsevne - grunnlag for beregning:" + System.lineSeparator()
          + "beregnDatoFra= " + bidragsevneGrunnlag.getBeregnDatoFra() + System.lineSeparator()
          + "beregnDatoTil= " + bidragsevneGrunnlag.getBeregnDatoTil() + System.lineSeparator()
          + "antallBarnIEgetHusholdPeriodeListe= " + bidragsevneGrunnlag.getAntallBarnIEgetHusholdPeriodeListe() + System.lineSeparator()
          + "bostatusPeriodeListe= " + bidragsevneGrunnlag.getBostatusPeriodeListe() + System.lineSeparator()
          + "inntektPeriodeListe= " + bidragsevneGrunnlag.getInntektPeriodeListe() + System.lineSeparator()
          + "særfradragPeriodeListe= " + bidragsevneGrunnlag.getSaerfradragPeriodeListe() + System.lineSeparator()
          + "skatteklassePeriodeListe= " + bidragsevneGrunnlag.getSkatteklassePeriodeListe());
      throw new UgyldigInputException("Ugyldig input ved beregning av bidragsevne. Følgende avvik ble funnet: "
          + bidragsevneResultat.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining("; ")));
    }

    LOGGER.debug("Bidragsevne - resultat av beregning: {}", bidragsevneResultat.getResultatPeriodeListe());
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
  private void beregnUnderholdskostnad(int soknadBarnPersonId, BeregnUnderholdskostnadGrunnlagCore underholdskostnadGrunnlag) {
    // Populerer liste over aktuelle sjabloner
    var sjablonPeriodeListe = new ArrayList<SjablonPeriodeCore>();
    sjablonPeriodeListe.addAll(mapSjablonSjablontall(sjablonSjablontallResponse.getResponseEntity().getBody()));
    sjablonPeriodeListe.addAll(mapSjablonForbruksutgifter(sjablonForbruksutgifterResponse.getResponseEntity().getBody()));
    underholdskostnadGrunnlag.setSjablonPeriodeListe(sjablonPeriodeListe);

    underholdskostnadGrunnlag.setNettoBarnetilsynPeriodeListe(hentNettoBarnetilsynPeriodeResultat(soknadBarnPersonId));

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
  private void beregnKostnadsberegnetBidrag(LocalDate beregnDatoFra, LocalDate beregnDatoTil,
      BeregnUnderholdskostnadGrunnlagCore underholdskostnadGrunnlagTilCore) {
    // Bygger kostnadsberegnet bidrag grunnlag
    var kostnadsberegnetBidragGrunnlag = byggKostnadsberegnetBidragGrunnlag(beregnDatoFra, beregnDatoTil);

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

  // Beregning av barnebidrag
  private void beregnBarnebidrag(BeregnBarnebidragGrunnlagCore barnebidragGrunnlag) {
    // Populerer liste over aktuelle sjabloner
    var sjablonPeriodeListe = new ArrayList<>(mapSjablonSjablontall(sjablonSjablontallResponse.getResponseEntity().getBody()));
    barnebidragGrunnlag.setSjablonPeriodeListe(sjablonPeriodeListe);

    // Populerer lister hentet fra resultatet av andre delberegninger
    barnebidragGrunnlag.setBidragsevnePeriodeListe(byggBidragsevnePeriodeListe());
    barnebidragGrunnlag.setKostnadsberegnetBidragPeriodeListe(byggKostnadsberegnetBidragPeriodeListe());
    barnebidragGrunnlag.setSamvaerfradragPeriodeListe(byggSamvaersfradragPeriodeListe());

    // Kaller core-modulen for beregning av barnebidrag
    LOGGER.debug("Barnebidrag - grunnlag for beregning: {}", barnebidragGrunnlag);
    barnebidragResultat = barnebidragCore.beregnBarnebidrag(barnebidragGrunnlag);

    if (!barnebidragResultat.getAvvikListe().isEmpty()) {
      LOGGER.error("Ugyldig input ved beregning av barnebidrag. Følgende avvik ble funnet: " + System.lineSeparator()
          + barnebidragResultat.getAvvikListe().stream().map(AvvikCore::getAvvikTekst)
          .collect(Collectors.joining(System.lineSeparator())));
      LOGGER.info("Barnebidrag - grunnlag for beregning: " + System.lineSeparator()
          + "beregnDatoFra= " + barnebidragGrunnlag.getBeregnDatoFra() + System.lineSeparator()
          + "beregnDatoTil= " + barnebidragGrunnlag.getBeregnDatoTil() + System.lineSeparator()
          + "bidragsevnePeriodeListe= " + barnebidragGrunnlag.getBidragsevnePeriodeListe() + System.lineSeparator()
          + "kostnadsberegnetBidragPeriodeListe= " + barnebidragGrunnlag.getKostnadsberegnetBidragPeriodeListe() + System.lineSeparator()
          + "samvaerfradragPeriodeListe= " + barnebidragGrunnlag.getSamvaerfradragPeriodeListe() + System.lineSeparator()
          + "barnetilleggBPPeriodeListe= " + barnebidragGrunnlag.getBarnetilleggBPPeriodeListe() + System.lineSeparator()
          + "barnetilleggBMPeriodeListe= " + barnebidragGrunnlag.getBarnetilleggBMPeriodeListe() + System.lineSeparator()
          + "barnetilleggForsvaretPeriodeListe= " + barnebidragGrunnlag.getBarnetilleggForsvaretPeriodeListe() + System.lineSeparator());
      throw new UgyldigInputException("Ugyldig input ved beregning av barnebidrag. Følgende avvik ble funnet: "
          + barnebidragResultat.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining("; ")));
    }

    LOGGER.debug("Barnebidrag - resultat av beregning: {}", barnebidragResultat.getResultatPeriodeListe());
  }

  // Hent data fra netto barnetilsyn resultat som skal brukes som input til beregning av underholdskostnad
  private List<NettoBarnetilsynPeriodeCore> hentNettoBarnetilsynPeriodeResultat(int soknadBarnPersonId) {
    var nettoBarnetilsynPeriodeCoreListe = new ArrayList<NettoBarnetilsynPeriodeCore>();
    for (var nettoBarnetilsynPeriodeCore : nettoBarnetilsynResultat.getResultatPeriodeListe()) {
      for (var resultatBeregning : nettoBarnetilsynPeriodeCore.getResultatBeregningListe()) {
        if (soknadBarnPersonId == resultatBeregning.getResultatSoknadsbarnPersonId()) {
          nettoBarnetilsynPeriodeCoreListe.add(new NettoBarnetilsynPeriodeCore(
              nettoBarnetilsynPeriodeCore.getResultatDatoFraTil(),
              resultatBeregning.getResultatBelop()));
        }
      }
    }
    return nettoBarnetilsynPeriodeCoreListe;
  }

  // Beregning av kostnadsberegnet bidrag
  private BeregnKostnadsberegnetBidragGrunnlagCore byggKostnadsberegnetBidragGrunnlag(LocalDate beregnDatoFra, LocalDate beregnDatoTil) {
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

    return new BeregnKostnadsberegnetBidragGrunnlagCore(beregnDatoFra, beregnDatoTil, underholdskostnadPeriodeCoreListe,
        bpAndelUnderholdskostnadPeriodeCoreListe, samvaersfradragPeriodeCoreListe);
  }

  // Bygger bidragsevne periodeliste som input til total beregning av bidrag
  private List<BidragsevnePeriodeCore> byggBidragsevnePeriodeListe() {
    var bidragsevnePeriodeCoreListe = new ArrayList<BidragsevnePeriodeCore>();
    for (var bidragsevnePeriodeCore : bidragsevneResultat.getResultatPeriodeListe()) {
      bidragsevnePeriodeCoreListe.add(new BidragsevnePeriodeCore(
          bidragsevnePeriodeCore.getResultatDatoFraTil(),
          bidragsevnePeriodeCore.getResultatBeregning().getResultatEvneBelop(),
          bidragsevnePeriodeCore.getResultatBeregning().getResultat25ProsentInntekt()));
    }
    return bidragsevnePeriodeCoreListe;
  }

  // Bygger kostnadsberegnet bidrag periodeliste som input til total beregning av bidrag
  private List<KostnadsberegnetBidragPeriodeCore> byggKostnadsberegnetBidragPeriodeListe() {
    var kostnadsberegnetBidragPeriodeCoreListe = new ArrayList<KostnadsberegnetBidragPeriodeCore>();
    for (var kostnadsberegnetBidragPeriodeCore : kostnadsberegnetBidragResultat.getResultatPeriodeListe()) {
      kostnadsberegnetBidragPeriodeCoreListe.add(new KostnadsberegnetBidragPeriodeCore(
          kostnadsberegnetBidragPeriodeCore.getResultatDatoFraTil(),
          kostnadsberegnetBidragPeriodeCore.getResultatBeregning().getResultatKostnadsberegnetBidragBelop()));
    }
    return kostnadsberegnetBidragPeriodeCoreListe;
  }

  // Bygger samværsfradrag periodeliste som input til total beregning av bidrag
  private List<no.nav.bidrag.beregn.barnebidrag.rest.dto.http.SamvaersfradragPeriodeCore> byggSamvaersfradragPeriodeListe() {
    var samvaersfradragPeriodeCoreListe = new ArrayList<no.nav.bidrag.beregn.barnebidrag.rest.dto.http.SamvaersfradragPeriodeCore>();
    for (var samvaersfradragPeriodeCore : samvaersfradragResultat.getResultatPeriodeListe()) {
      samvaersfradragPeriodeCoreListe.add(new no.nav.bidrag.beregn.barnebidrag.rest.dto.http.SamvaersfradragPeriodeCore(
          samvaersfradragPeriodeCore.getResultatDatoFraTil(),
          samvaersfradragPeriodeCore.getResultatBeregning().getResultatSamvaersfradragBelop()));
    }
    return samvaersfradragPeriodeCoreListe;
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

  // Mapper sjabloner av typen bidragsevne
  private List<SjablonPeriodeCore> mapSjablonBidragsevne(List<Bidragsevne> sjablonBidragsevneListe) {

    return sjablonBidragsevneListe
        .stream()
        .map(sBL -> new SjablonPeriodeCore(
            new PeriodeCore(sBL.getDatoFom(), sBL.getDatoTom()),
            SjablonNavn.BIDRAGSEVNE.getNavn(),
            singletonList(new SjablonNokkelCore(SjablonNokkelNavn.BOSTATUS.getNavn(), sBL.getBostatus())),
            Arrays.asList(new SjablonInnholdCore(SjablonInnholdNavn.BOUTGIFT_BELOP.getNavn(), sBL.getBelopBoutgift().doubleValue()),
                new SjablonInnholdCore(SjablonInnholdNavn.UNDERHOLD_BELOP.getNavn(), sBL.getBelopUnderhold().doubleValue()))))
        .collect(toList());
  }

  // Mapper sjabloner av typen trinnvis skattesats
  private List<SjablonPeriodeCore> mapSjablonTrinnvisSkattesats(List<TrinnvisSkattesats> sjablonTrinnvisSkattesatsListe) {

    return sjablonTrinnvisSkattesatsListe
        .stream()
        .map(sTSL -> new SjablonPeriodeCore(
            new PeriodeCore(sTSL.getDatoFom(), sTSL.getDatoTom()),
            SjablonNavn.TRINNVIS_SKATTESATS.getNavn(),
            emptyList(),
            Arrays.asList(new SjablonInnholdCore(SjablonInnholdNavn.INNTEKTSGRENSE_BELOP.getNavn(), sTSL.getInntektgrense().doubleValue()),
                new SjablonInnholdCore(SjablonInnholdNavn.SKATTESATS_PROSENT.getNavn(), sTSL.getSats().doubleValue()))))
        .collect(toList());
  }
}
