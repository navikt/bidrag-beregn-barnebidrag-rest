package no.nav.bidrag.beregn.barnebidrag.rest.service;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.BidragsevneConsumer;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Forbruksutgifter;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.MaksFradrag;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.MaksTilsyn;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonConsumer;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Sjablontall;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBarnebidragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBidragsevneGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBidragsevneResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnNettoBarnetilsynResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnUnderholdskostnadResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.BidragsevneConsumerException;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.SjablonConsumerException;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;
import no.nav.bidrag.beregn.felles.dto.AvvikCore;
import no.nav.bidrag.beregn.felles.dto.PeriodeCore;
import no.nav.bidrag.beregn.felles.dto.SjablonInnholdCore;
import no.nav.bidrag.beregn.felles.dto.SjablonNokkelCore;
import no.nav.bidrag.beregn.felles.dto.SjablonPeriodeCore;
import no.nav.bidrag.beregn.felles.enums.SjablonInnholdNavn;
import no.nav.bidrag.beregn.felles.enums.SjablonNavn;
import no.nav.bidrag.beregn.felles.enums.SjablonNokkelNavn;
import no.nav.bidrag.beregn.felles.enums.SjablonTallNavn;
import no.nav.bidrag.beregn.nettobarnetilsyn.NettoBarnetilsynCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnNettoBarnetilsynGrunnlagCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnNettoBarnetilsynResultatCore;
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
  private final UnderholdskostnadCore underholdskostnadCore;
  private final NettoBarnetilsynCore nettoBarnetilsynCore;

  private HttpResponse<BeregnBidragsevneResultat> bidragsevneResultat;
  private HttpResponse<List<Sjablontall>> sjablonSjablontallResponse;
  private HttpResponse<List<Forbruksutgifter>> sjablonForbruksutgifterResponse;
  private HttpResponse<List<MaksTilsyn>> sjablonMaksTilsynResponse;
  private HttpResponse<List<MaksFradrag>> sjablonMaksFradragResponse;
  private BeregnUnderholdskostnadResultatCore underholdskostnadResultat;
  private BeregnNettoBarnetilsynResultatCore nettoBarnetilsynResultat;

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
    put("0100", SjablonTallNavn.FASTSETTELSESGEBYR_BELOP.getNavn());
  }};

  public BeregnBarnebidragService(BidragsevneConsumer bidragsevneConsumer, SjablonConsumer sjablonConsumer,
      UnderholdskostnadCore underholdskostnadCore, NettoBarnetilsynCore nettoBarnetilsynCore) {
    this.bidragsevneConsumer = bidragsevneConsumer;
    this.sjablonConsumer = sjablonConsumer;
    this.underholdskostnadCore = underholdskostnadCore;
    this.nettoBarnetilsynCore = nettoBarnetilsynCore;
  }

  public HttpResponse<BeregnBarnebidragResultat> beregn(BeregnBarnebidragGrunnlag beregnBarnebidragGrunnlag) {

    // Ekstraher grunnlag for beregning av underholdskostnad. Her gjøres også kontroll av inputdata
    var underholdskostnadGrunnlagTilCore = beregnBarnebidragGrunnlag.getBeregnUnderholdskostnadGrunnlag().tilCore();

    // Ekstraher grunnlag for beregning av netto barnetilsyn. Her gjøres også kontroll av inputdata
    var nettoBarnetilsynGrunnlagTilCore = beregnBarnebidragGrunnlag.getBeregnNettoBarnetilsynGrunnlag().tilCore();

    // Hent sjabloner
    hentSjabloner();

    // Kall beregning av bidragsevne
    beregnBidragsevne(beregnBarnebidragGrunnlag.getBeregnBidragsevneGrunnlag());

    // Kall beregning av netto barnetilsyn
    beregnNettoBarnetilsyn(nettoBarnetilsynGrunnlagTilCore);

    // Kall beregning av underholdskostnad
    beregnUnderholdskostnad(underholdskostnadGrunnlagTilCore);

    // Kall totalberegning

    return HttpResponse.from(HttpStatus.OK, new BeregnBarnebidragResultat(bidragsevneResultat.getResponseEntity().getBody(),
        new BeregnUnderholdskostnadResultat(underholdskostnadResultat), new BeregnNettoBarnetilsynResultat(nettoBarnetilsynResultat),
        "Resten av resultatet"));
  }

  // Henter sjabloner
  private void hentSjabloner() {
    // Henter sjabloner for sjablontall
    sjablonSjablontallResponse = sjablonConsumer.hentSjablonSjablontall();

    if (!(sjablonSjablontallResponse.is2xxSuccessful())) {
      LOGGER.error("Feil ved kall av bidrag-sjablon (sjablontall). Status: {}",
          sjablonSjablontallResponse.getResponseEntity().getStatusCode().toString());
      throw new SjablonConsumerException("Feil ved kall av bidrag-sjablon (sjablontall). Status: "
          + sjablonSjablontallResponse.getResponseEntity().getStatusCode().toString() + " Melding: " + sjablonSjablontallResponse.getResponseEntity()
          .getBody());
    } else {
      LOGGER.debug("Antall sjabloner hentet av type Sjablontall: {}", sjablonSjablontallResponse.getResponseEntity().getBody().size());
    }

    // Henter sjabloner for forbruksutgifter
    sjablonForbruksutgifterResponse = sjablonConsumer.hentSjablonForbruksutgifter();

    if (!(sjablonForbruksutgifterResponse.is2xxSuccessful())) {
      LOGGER.error("Feil ved kall av bidrag-sjablon (forbruksutgifter). Status: {}",
          sjablonForbruksutgifterResponse.getResponseEntity().getStatusCode().toString());
      throw new SjablonConsumerException("Feil ved kall av bidrag-sjablon (forbruksutgifter). Status: "
          + sjablonForbruksutgifterResponse.getResponseEntity().getStatusCode().toString() + " Melding: " + sjablonForbruksutgifterResponse
          .getResponseEntity().getBody());
    } else {
      LOGGER.debug("Antall sjabloner hentet av type Bidragsevne: {}", sjablonForbruksutgifterResponse.getResponseEntity().getBody().size());
    }

    // Henter sjabloner for maks tilsyn
    sjablonMaksTilsynResponse = sjablonConsumer.hentSjablonMaksTilsyn();

    if (!(sjablonMaksTilsynResponse.is2xxSuccessful())) {
      LOGGER.error("Feil ved kall av bidrag-sjablon (maks tilsyn). Status: {}",
          sjablonMaksTilsynResponse.getResponseEntity().getStatusCode().toString());
      throw new SjablonConsumerException("Feil ved kall av bidrag-sjablon (maks tilsyn). Status: "
          + sjablonMaksTilsynResponse.getResponseEntity().getStatusCode().toString() + " Melding: " + sjablonMaksTilsynResponse.getResponseEntity()
          .getBody());
    } else {
      LOGGER.debug("Antall sjabloner hentet av type Maks tilsyn: {}", sjablonMaksTilsynResponse.getResponseEntity().getBody().size());
    }

    // Henter sjabloner for maks bidrag
    sjablonMaksFradragResponse = sjablonConsumer.hentSjablonMaksFradrag();

    if (!(sjablonMaksFradragResponse.is2xxSuccessful())) {
      LOGGER.error("Feil ved kall av bidrag-sjablon (maks fradrag). Status: {}",
          sjablonMaksFradragResponse.getResponseEntity().getStatusCode().toString());
      throw new SjablonConsumerException("Feil ved kall av bidrag-sjablon (maks fradrag). Status: "
          + sjablonMaksFradragResponse.getResponseEntity().getStatusCode().toString() + " Melding: " + sjablonMaksFradragResponse.getResponseEntity()
          .getBody());
    } else {
      LOGGER.debug("Antall sjabloner hentet av type Maks fradrag: {}", sjablonMaksFradragResponse.getResponseEntity().getBody().size());
    }
  }

  // Kaller rest-modul for beregning av bidragsevne
  private void beregnBidragsevne(BeregnBidragsevneGrunnlag bidragsevneGrunnlag) {
    bidragsevneResultat = bidragsevneConsumer.hentBidragsevne(bidragsevneGrunnlag);

    if (!(bidragsevneResultat.is2xxSuccessful())) {
      LOGGER.error("Feil ved kall av bidrag-beregn-bidragsevne-rest. Status: {}", bidragsevneResultat.getResponseEntity().getStatusCode().toString());
      throw new BidragsevneConsumerException("Feil ved kall av bidrag-beregn-bidragsevne-rest. Status: "
          + bidragsevneResultat.getResponseEntity().getStatusCode().toString() + " Melding: " + bidragsevneResultat.getResponseEntity().getBody());
    }
  }

  // Kaller rest-modul for beregning av netto barnetilsyn
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

  // Kaller rest-modul for beregning av underholdskostnad
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
          + "soknadBarnFodselsdatoTil= " + underholdskostnadGrunnlag.getSoknadBarnFodselsdato() + System.lineSeparator()
          + "barneTilsynMedStonadPeriodeListe= " + underholdskostnadGrunnlag.getBarnetilsynMedStonadPeriodeListe() + System.lineSeparator()
          + "forpleiningUtgiftPeriodeListe= " + underholdskostnadGrunnlag.getForpleiningUtgiftPeriodeListe() + System.lineSeparator()
          + "nettoBarnetilsynPeriodeListe= " + underholdskostnadGrunnlag.getNettoBarnetilsynPeriodeListe() + System.lineSeparator());
      throw new UgyldigInputException("Ugyldig input ved beregning av underholdskostnad. Følgende avvik ble funnet: "
          + underholdskostnadResultat.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining("; ")));
    }

    LOGGER.debug("Underholdskostnad - resultat av beregning: {}", underholdskostnadResultat.getResultatPeriodeListe());
  }

  // Mapper sjabloner av typen sjablontall
  private List<SjablonPeriodeCore> mapSjablonSjablontall(List<Sjablontall> sjablonSjablontallListe) {
    return sjablonSjablontallListe
        .stream()
        .map(sSL -> new SjablonPeriodeCore(
            new PeriodeCore(sSL.getDatoFom(), sSL.getDatoTom()),
            sjablontallMap.get(sSL.getTypeSjablon()),
            emptyList(),
            Arrays.asList(new SjablonInnholdCore(SjablonInnholdNavn.SJABLON_VERDI.getNavn(), sSL.getVerdi().doubleValue()))))
        .collect(toList());
  }

  // Mapper sjabloner av typen forbruksutgifter
  private List<SjablonPeriodeCore> mapSjablonForbruksutgifter(List<Forbruksutgifter> sjablonForbruksutgifterListe) {

    return sjablonForbruksutgifterListe
        .stream()
        .map(sFL -> new SjablonPeriodeCore(
            new PeriodeCore(sFL.getDatoFom(), sFL.getDatoTom()),
            SjablonNavn.FORBRUKSUTGIFTER.getNavn(),
            Arrays.asList(new SjablonNokkelCore(SjablonNokkelNavn.ALDER_TOM.getNavn(), sFL.getAlderTom().toString())),
            Arrays.asList(new SjablonInnholdCore(SjablonInnholdNavn.FORBRUK_TOTAL_BELOP.getNavn(), sFL.getBelopForbrukTot().doubleValue()))))
        .collect(toList());
  }

  // Mapper sjabloner av typen maks tilsyn
  private List<SjablonPeriodeCore> mapSjablonMaksTilsyn(List<MaksTilsyn> sjablonMaksTilsynListe) {

    return sjablonMaksTilsynListe
        .stream()
        .map(sMTL -> new SjablonPeriodeCore(
            new PeriodeCore(sMTL.getDatoFom(), sMTL.getDatoTom()),
            SjablonNavn.MAKS_TILSYN.getNavn(),
            Arrays.asList(new SjablonNokkelCore(SjablonNokkelNavn.ANTALL_BARN_TOM.getNavn(), sMTL.getAntBarnTom().toString())),
            Arrays.asList(new SjablonInnholdCore(SjablonInnholdNavn.MAKS_TILSYN_BELOP.getNavn(), sMTL.getMaksBelopTilsyn().doubleValue()))))
        .collect(toList());
  }

  // Mapper sjabloner av typen maks fradrag
  private List<SjablonPeriodeCore> mapSjablonMaksFradrag(List<MaksFradrag> sjablonMaksFradragListe) {

    return sjablonMaksFradragListe
        .stream()
        .map(sMFL -> new SjablonPeriodeCore(
            new PeriodeCore(sMFL.getDatoFom(), sMFL.getDatoTom()),
            SjablonNavn.MAKS_FRADRAG.getNavn(),
            Arrays.asList(new SjablonNokkelCore(SjablonNokkelNavn.ANTALL_BARN_TOM.getNavn(), sMFL.getAntBarnTom().toString())),
            Arrays.asList(new SjablonInnholdCore(SjablonInnholdNavn.MAKS_FRADRAG_BELOP.getNavn(), sMFL.getMaksBelopFradrag().doubleValue()))))
        .collect(toList());
  }
}