package no.nav.bidrag.beregn.bidrag.rest.service;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import no.nav.bidrag.beregn.bidrag.rest.consumer.BidragsevneConsumer;
import no.nav.bidrag.beregn.bidrag.rest.consumer.Forbruksutgifter;
import no.nav.bidrag.beregn.bidrag.rest.consumer.SjablonConsumer;
import no.nav.bidrag.beregn.bidrag.rest.consumer.Sjablontall;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BeregnBidragGrunnlag;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BeregnBidragResultat;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BeregnBidragsevneGrunnlag;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BeregnBidragsevneResultat;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BeregnUnderholdskostnadResultat;
import no.nav.bidrag.beregn.bidrag.rest.exception.BidragsevneConsumerException;
import no.nav.bidrag.beregn.bidrag.rest.exception.SjablonConsumerException;
import no.nav.bidrag.beregn.bidrag.rest.exception.UgyldigInputException;
import no.nav.bidrag.beregn.felles.dto.AvvikCore;
import no.nav.bidrag.beregn.felles.dto.PeriodeCore;
import no.nav.bidrag.beregn.felles.dto.SjablonInnholdCore;
import no.nav.bidrag.beregn.felles.dto.SjablonNokkelCore;
import no.nav.bidrag.beregn.felles.dto.SjablonPeriodeCore;
import no.nav.bidrag.beregn.felles.enums.SjablonInnholdNavn;
import no.nav.bidrag.beregn.felles.enums.SjablonNavn;
import no.nav.bidrag.beregn.felles.enums.SjablonNokkelNavn;
import no.nav.bidrag.beregn.felles.enums.SjablonTallNavn;
import no.nav.bidrag.beregn.underholdskostnad.UnderholdskostnadCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadGrunnlagCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadResultatCore;
import no.nav.bidrag.commons.web.HttpStatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BeregnBidragService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BeregnBidragService.class);

  private final BidragsevneConsumer bidragsevneConsumer;
  private final SjablonConsumer sjablonConsumer;
  private final UnderholdskostnadCore underholdskostnadCore;

  private HttpStatusResponse<BeregnBidragsevneResultat> bidragsevneResultat;
  private HttpStatusResponse<List<Sjablontall>> sjablonSjablontallResponse;
  private HttpStatusResponse<List<Forbruksutgifter>> sjablonForbruksutgifterResponse;
  private BeregnUnderholdskostnadResultatCore underholdskostnadResultat;

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

  public BeregnBidragService(BidragsevneConsumer bidragsevneConsumer, SjablonConsumer sjablonConsumer, UnderholdskostnadCore underholdskostnadCore) {
    this.bidragsevneConsumer = bidragsevneConsumer;
    this.sjablonConsumer = sjablonConsumer;
    this.underholdskostnadCore = underholdskostnadCore;
  }

  public HttpStatusResponse<BeregnBidragResultat> beregn(BeregnBidragGrunnlag beregnBidragGrunnlag) {

    // Ekstraher grunnlag for beregning av underholdskostnad. Her gjøres også kontroll av inputdata
    var underholdskostnadGrunnlagTilCore = beregnBidragGrunnlag.getBeregnUnderholdskostnadGrunnlag().tilCore();

    // Hent sjabloner
    hentSjabloner();

    // Kall beregning av bidragsevne
    beregnBidragsevne(beregnBidragGrunnlag.getBeregnBidragsevneGrunnlag());

    // Kall beregning av netto barnetilsyn

    // Kall beregning av underholdskostnad
    beregnUnderholdskostnad(underholdskostnadGrunnlagTilCore);

    // Kall totalberegning

    return new HttpStatusResponse(HttpStatus.OK, new BeregnBidragResultat(bidragsevneResultat.getBody(),
        new BeregnUnderholdskostnadResultat(underholdskostnadResultat), "Resten av resultatet"));
  }

  // Henter sjabloner
  private void hentSjabloner() {
    // Henter sjabloner for sjablontall
    sjablonSjablontallResponse = sjablonConsumer.hentSjablonSjablontall();

    if (!(sjablonSjablontallResponse.getHttpStatus().is2xxSuccessful())) {
      LOGGER.error("Feil ved kall av bidrag-sjablon (sjablontall). Status: {}", sjablonSjablontallResponse.getHttpStatus().toString());
      throw new SjablonConsumerException("Feil ved kall av bidrag-sjablon (sjablontall). Status: "
          + sjablonSjablontallResponse.getHttpStatus().toString() + " Melding: " + sjablonSjablontallResponse.getBody());
    } else {
      LOGGER.debug("Antall sjabloner hentet av type Sjablontall: {}", sjablonSjablontallResponse.getBody().size());
    }

    // Henter sjabloner for forbruksutgifter
    sjablonForbruksutgifterResponse = sjablonConsumer.hentSjablonForbruksutgifter();

    if (!(sjablonForbruksutgifterResponse.getHttpStatus().is2xxSuccessful())) {
      LOGGER.error("Feil ved kall av bidrag-sjablon (forbruksutgifter). Status: {}", sjablonForbruksutgifterResponse.getHttpStatus().toString());
      throw new SjablonConsumerException("Feil ved kall av bidrag-sjablon (forbruksutgifter). Status: "
          + sjablonForbruksutgifterResponse.getHttpStatus().toString() + " Melding: " + sjablonForbruksutgifterResponse.getBody());
    } else {
      LOGGER.debug("Antall sjabloner hentet av type Bidragsevne: {}", sjablonForbruksutgifterResponse.getBody().size());
    }
  }

  // Kaller rest-modul for beregning av bidragsevne
  private void beregnBidragsevne(BeregnBidragsevneGrunnlag bidragsevneGrunnlag) {
    bidragsevneResultat = bidragsevneConsumer.hentBidragsevne(bidragsevneGrunnlag);

    if (!(bidragsevneResultat.getHttpStatus().is2xxSuccessful())) {
      LOGGER.error("Feil ved kall av bidrag-beregn-bidragsevne-rest. Status: {}", bidragsevneResultat.getHttpStatus().toString());
      throw new BidragsevneConsumerException("Feil ved kall av bidrag-beregn-bidragsevne-rest. Status: "
          + bidragsevneResultat.getHttpStatus().toString() + " Melding: " + bidragsevneResultat.getBody());
    }
  }

  // Kaller rest-modul for beregning av bidragsevne
  private void beregnUnderholdskostnad(BeregnUnderholdskostnadGrunnlagCore underholdskostnadGrunnlag) {
    // Populerer liste over aktuelle sjabloner
    var sjablonPeriodeListe = new ArrayList<SjablonPeriodeCore>();
    sjablonPeriodeListe.addAll(mapSjablonSjablontall(sjablonSjablontallResponse.getBody()));
    sjablonPeriodeListe.addAll(mapSjablonForbruksutgifter(sjablonForbruksutgifterResponse.getBody()));
    underholdskostnadGrunnlag.setSjablonPeriodeListe(sjablonPeriodeListe);

    //TODO Legg til NettoBarnetilsynPeriodeCore fra netto barnetilsyn core-tjenesten

    // Kaller core-modulen for beregning av underholdskostnad
    LOGGER.debug("Underholdskostnad - grunnlag for beregning: {}", underholdskostnadGrunnlag);
    underholdskostnadResultat = underholdskostnadCore.beregnUnderholdskostnad(underholdskostnadGrunnlag);

    if (!underholdskostnadResultat.getAvvikListe().isEmpty()) {
      LOGGER.error("Ugyldig input ved beregning av underholdskostnad" + System.lineSeparator()
          + "Underholdskostnad - grunnlag for beregning: " + underholdskostnadGrunnlag + System.lineSeparator()
          + "Underholdskostnad - avvik: " + underholdskostnadResultat.getAvvikListe().stream().map(AvvikCore::getAvvikTekst)
          .collect(Collectors.joining("; ")));
      throw new UgyldigInputException(
          underholdskostnadResultat.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining("; ")));
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
}
