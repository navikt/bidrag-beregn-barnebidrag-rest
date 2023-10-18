package no.nav.bidrag.beregn.barnebidrag.rest.service;

import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;
import static no.nav.bidrag.beregn.barnebidrag.rest.service.SoknadsbarnUtil.validerSoknadsbarnId;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import no.nav.bidrag.beregn.barnebidrag.BarnebidragCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BPsAndelUnderholdskostnadPeriodeCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BeregnBarnebidragGrunnlagCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BeregnetBarnebidragResultatCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BidragsevnePeriodeCore;
import no.nav.bidrag.beregn.barnebidrag.dto.SamvaersfradragPeriodeCore;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonConsumer;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonListe;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnetTotalBarnebidragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;
import no.nav.bidrag.beregn.barnebidrag.rest.mapper.BPAndelUnderholdskostnadCoreMapper;
import no.nav.bidrag.beregn.barnebidrag.rest.mapper.BarnebidragCoreMapper;
import no.nav.bidrag.beregn.barnebidrag.rest.mapper.BidragsevneCoreMapper;
import no.nav.bidrag.beregn.barnebidrag.rest.mapper.NettoBarnetilsynCoreMapper;
import no.nav.bidrag.beregn.barnebidrag.rest.mapper.SamvaersfradragCoreMapper;
import no.nav.bidrag.beregn.barnebidrag.rest.mapper.UnderholdskostnadCoreMapper;
import no.nav.bidrag.beregn.bidragsevne.BidragsevneCore;
import no.nav.bidrag.beregn.bidragsevne.dto.BeregnBidragsevneGrunnlagCore;
import no.nav.bidrag.beregn.bidragsevne.dto.BeregnetBidragsevneResultatCore;
import no.nav.bidrag.beregn.bidragsevne.dto.ResultatPeriodeCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.BPsAndelUnderholdskostnadCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnBPsAndelUnderholdskostnadGrunnlagCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnetBPsAndelUnderholdskostnadResultatCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.UnderholdskostnadPeriodeCore;
import no.nav.bidrag.beregn.felles.dto.AvvikCore;
import no.nav.bidrag.beregn.felles.dto.SjablonResultatGrunnlagCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.NettoBarnetilsynCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnNettoBarnetilsynGrunnlagCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnetNettoBarnetilsynResultatCore;
import no.nav.bidrag.beregn.samvaersfradrag.SamvaersfradragCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnSamvaersfradragGrunnlagCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnetSamvaersfradragResultatCore;
import no.nav.bidrag.beregn.underholdskostnad.UnderholdskostnadCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadGrunnlagCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnetUnderholdskostnadResultatCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.NettoBarnetilsynPeriodeCore;
import no.nav.bidrag.commons.web.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BeregnBarnebidragService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BeregnBarnebidragService.class);

  public static final String BIDRAGSEVNE = "Bidragsevne";
  public static final String NETTO_BARNETILSYN = "NettoBarnetilsyn";
  public static final String UNDERHOLDSKOSTNAD = "Underholdskostnad";
  public static final String BP_ANDEL_UNDERHOLDSKOSTNAD = "BPsAndelUnderholdskostnad";
  public static final String BARNEBIDRAG = "Barnebidrag";

  private final BidragsevneCoreMapper bidragsevneCoreMapper;
  private final NettoBarnetilsynCoreMapper nettoBarnetilsynCoreMapper;
  private final UnderholdskostnadCoreMapper underholdskostnadCoreMapper;
  private final BPAndelUnderholdskostnadCoreMapper bpAndelUnderholdskostnadCoreMapper;
  private final SamvaersfradragCoreMapper samvaersfradragCoreMapper;
  private final BarnebidragCoreMapper barnebidragCoreMapper;
  private final SjablonConsumer sjablonConsumer;
  private final BidragsevneCore bidragsevneCore;
  private final NettoBarnetilsynCore nettoBarnetilsynCore;
  private final UnderholdskostnadCore underholdskostnadCore;
  private final BPsAndelUnderholdskostnadCore bpAndelUnderholdskostnadCore;
  private final SamvaersfradragCore samvaersfradragCore;
  private final BarnebidragCore barnebidragCore;

  public BeregnBarnebidragService(BidragsevneCoreMapper bidragsevneCoreMapper, NettoBarnetilsynCoreMapper nettoBarnetilsynCoreMapper,
      UnderholdskostnadCoreMapper underholdskostnadCoreMapper, BPAndelUnderholdskostnadCoreMapper bpAndelUnderholdskostnadCoreMapper,
      SamvaersfradragCoreMapper samvaersfradragCoreMapper, BarnebidragCoreMapper barnebidragCoreMapper, SjablonConsumer sjablonConsumer,
      BidragsevneCore bidragsevneCore, NettoBarnetilsynCore nettoBarnetilsynCore, UnderholdskostnadCore underholdskostnadCore,
      BPsAndelUnderholdskostnadCore bpAndelUnderholdskostnadCore, SamvaersfradragCore samvaersfradragCore,
      BarnebidragCore barnebidragCore) {
    this.bidragsevneCoreMapper = bidragsevneCoreMapper;
    this.nettoBarnetilsynCoreMapper = nettoBarnetilsynCoreMapper;
    this.underholdskostnadCoreMapper = underholdskostnadCoreMapper;
    this.bpAndelUnderholdskostnadCoreMapper = bpAndelUnderholdskostnadCoreMapper;
    this.samvaersfradragCoreMapper = samvaersfradragCoreMapper;
    this.barnebidragCoreMapper = barnebidragCoreMapper;
    this.sjablonConsumer = sjablonConsumer;
    this.bidragsevneCore = bidragsevneCore;
    this.nettoBarnetilsynCore = nettoBarnetilsynCore;
    this.underholdskostnadCore = underholdskostnadCore;
    this.bpAndelUnderholdskostnadCore = bpAndelUnderholdskostnadCore;
    this.samvaersfradragCore = samvaersfradragCore;
    this.barnebidragCore = barnebidragCore;
  }

  public HttpResponse<BeregnetTotalBarnebidragResultat> beregn(BeregnGrunnlag grunnlag) {

    // Kontroll av inputdata
    grunnlag.valider();
    validerSoknadsbarnId(grunnlag);

    // Lager en map for søknadsbarn id og fødselsdato
    var soknadsbarnMap = SoknadsbarnUtil.mapSoknadsbarn(grunnlag);

    // Henter sjabloner
    var sjablonListe = hentSjabloner();

    // Bygger grunnlag til core og utfører delberegninger
    return utfoerDelberegninger(grunnlag, soknadsbarnMap, sjablonListe);
  }

  //==================================================================================================================================================

  // Bygger grunnlag til core og kaller delberegninger
  private HttpResponse<BeregnetTotalBarnebidragResultat> utfoerDelberegninger(BeregnGrunnlag beregnGrunnlag,
      Map<Integer, String> soknadsbarnMap, SjablonListe sjablonListe) {

    var grunnlagReferanseListe = new ArrayList<ResultatGrunnlag>();

    // ++ Bidragsevne
    var bidragsevneGrunnlagTilCore = bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(beregnGrunnlag, sjablonListe);
    var bidragsevneResultatFraCore = beregnBidragsevne(bidragsevneGrunnlagTilCore);
    grunnlagReferanseListe.addAll(lagGrunnlagListeBidragsevne(beregnGrunnlag, bidragsevneResultatFraCore));

    // ++ Netto barnetilsyn
    var nettoBarnetilsynGrunnlagTilCore = nettoBarnetilsynCoreMapper.mapNettoBarnetilsynGrunnlagTilCore(beregnGrunnlag, sjablonListe,
        soknadsbarnMap);
    var nettoBarnetilsynResultatFraCore = beregnNettoBarnetilsyn(nettoBarnetilsynGrunnlagTilCore);
    grunnlagReferanseListe.addAll(lagGrunnlagListeNettoBarnetilsyn(beregnGrunnlag, nettoBarnetilsynResultatFraCore));

    // ++ Underholdskostnad
    var underholdskostnadResultatFraCore = utfoerDelberegningUnderholdskostnad(beregnGrunnlag, soknadsbarnMap,
        nettoBarnetilsynResultatFraCore, sjablonListe, grunnlagReferanseListe);

    // ++ BPs andel av underholdskostnad
    var bpAndelUnderholdskostnadResultatFraCore = utfoerDelberegningBPAndelUnderholdskostnad(beregnGrunnlag, soknadsbarnMap,
        underholdskostnadResultatFraCore, sjablonListe, grunnlagReferanseListe);

    // ++ Samværsfradrag
    var samvaersfradragResultatFraCore = utfoerDelberegningSamvaersfradrag(beregnGrunnlag, soknadsbarnMap, sjablonListe,
        grunnlagReferanseListe);

    // ++ Barnebidrag (totalberegning)
    var barnebidragGrunnlagTilCore = barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(beregnGrunnlag, sjablonListe,
        bidragsevneResultatFraCore, bpAndelUnderholdskostnadResultatFraCore, samvaersfradragResultatFraCore);
    var barnebidragResultatFraCore = beregnBarnebidrag(barnebidragGrunnlagTilCore);
    grunnlagReferanseListe.addAll(lagGrunnlagListeBarnebidrag(beregnGrunnlag, barnebidragResultatFraCore,
        barnebidragGrunnlagTilCore, bidragsevneResultatFraCore, bpAndelUnderholdskostnadResultatFraCore, samvaersfradragResultatFraCore));

    // Bygger responsobjekt
    return HttpResponse.Companion.from(HttpStatus.OK, new BeregnetTotalBarnebidragResultat(barnebidragResultatFraCore,
        grunnlagReferanseListe.stream().sorted(comparing(ResultatGrunnlag::getReferanse)).distinct().toList()));
  }

  //==================================================================================================================================================

  // Delberegning underholdskostnad
  private BeregnetUnderholdskostnadResultatCore utfoerDelberegningUnderholdskostnad(BeregnGrunnlag beregnGrunnlag,
      Map<Integer, String> soknadsbarnMap, BeregnetNettoBarnetilsynResultatCore nettoBarnetilsynResultatFraCore, SjablonListe sjablonListe,
      List<ResultatGrunnlag> grunnlagReferanseListe) {

    // Bygger opp liste med grunnlag for hvert søknadsbarn
    var underholdskostnadGrunnlagTilCoreListe = new ArrayList<BeregnUnderholdskostnadGrunnlagCore>();
    soknadsbarnMap.forEach((id, fodselsdato) ->
        underholdskostnadGrunnlagTilCoreListe.add(underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(beregnGrunnlag,
            sjablonListe, id, nettoBarnetilsynResultatFraCore, soknadsbarnMap)));

    // Kaller beregning for hvert søknadsbarn
    var resultatUnderholdskostnadListe = new ArrayList<no.nav.bidrag.beregn.underholdskostnad.dto.ResultatPeriodeCore>();
    var sjablonUnderholdskostnadListe = new ArrayList<SjablonResultatGrunnlagCore>();
    underholdskostnadGrunnlagTilCoreListe.forEach(underholdskostnadGrunnlagTilCore -> {
      var beregnetUnderholdskostnad = beregnUnderholdskostnad(underholdskostnadGrunnlagTilCore);
      grunnlagReferanseListe.addAll(lagGrunnlagListeUnderholdskostnad(beregnGrunnlag, beregnetUnderholdskostnad,
          underholdskostnadGrunnlagTilCore, nettoBarnetilsynResultatFraCore));
      resultatUnderholdskostnadListe.addAll(beregnetUnderholdskostnad.getResultatPeriodeListe());
      sjablonUnderholdskostnadListe.addAll(beregnetUnderholdskostnad.getSjablonListe());
    });
    return new BeregnetUnderholdskostnadResultatCore(resultatUnderholdskostnadListe, sjablonUnderholdskostnadListe, emptyList());
  }

  // Delberegning BPs andel av underholdskostnad
  private BeregnetBPsAndelUnderholdskostnadResultatCore utfoerDelberegningBPAndelUnderholdskostnad(
      BeregnGrunnlag beregnGrunnlag, Map<Integer, String> soknadsbarnMap,
      BeregnetUnderholdskostnadResultatCore underholdskostnadResultatFraCore, SjablonListe sjablonListe,
      List<ResultatGrunnlag> grunnlagReferanseListe) {

    // Bygger opp liste med grunnlag for hvert søknadsbarn
    var bpAndelUnderholdskostnadGrunnlagTilCoreListe = new ArrayList<BeregnBPsAndelUnderholdskostnadGrunnlagCore>();
    soknadsbarnMap.forEach((id, fodselsdato) ->
        bpAndelUnderholdskostnadGrunnlagTilCoreListe.add(
            bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(beregnGrunnlag, sjablonListe, id,
                underholdskostnadResultatFraCore)));

    // Kaller beregning for hvert søknadsbarn
    var resultatBPAndelUnderholdskostnadListe = new ArrayList<no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatPeriodeCore>();
    var sjablonBPAndelUnderholdskostnadListe = new ArrayList<SjablonResultatGrunnlagCore>();
    bpAndelUnderholdskostnadGrunnlagTilCoreListe.forEach(bpAndelUnderholdskostnadGrunnlagTilCore -> {
      var beregnetBPAndelUnderholdskostnad = beregnBPsAndelUnderholdskostnad(bpAndelUnderholdskostnadGrunnlagTilCore);
      grunnlagReferanseListe.addAll(lagGrunnlagListeBPAndelUnderholdskostnad(beregnGrunnlag,
          beregnetBPAndelUnderholdskostnad, bpAndelUnderholdskostnadGrunnlagTilCore, underholdskostnadResultatFraCore));
      resultatBPAndelUnderholdskostnadListe.addAll(beregnetBPAndelUnderholdskostnad.getResultatPeriodeListe());
      sjablonBPAndelUnderholdskostnadListe.addAll(beregnetBPAndelUnderholdskostnad.getSjablonListe());
    });
    return new BeregnetBPsAndelUnderholdskostnadResultatCore(resultatBPAndelUnderholdskostnadListe, sjablonBPAndelUnderholdskostnadListe,
        emptyList());
  }

  // Delberegning samværsfradrag
  private BeregnetSamvaersfradragResultatCore utfoerDelberegningSamvaersfradrag(BeregnGrunnlag beregnGrunnlag,
      Map<Integer, String> soknadsbarnMap, SjablonListe sjablonListe, List<ResultatGrunnlag> grunnlagReferanseListe) {

    // Bygger opp liste med grunnlag for hvert søknadsbarn
    var samvaersfradragGrunnlagTilCoreListe = new ArrayList<BeregnSamvaersfradragGrunnlagCore>();
    soknadsbarnMap.forEach((id, fodselsdato) ->
        samvaersfradragGrunnlagTilCoreListe.add(samvaersfradragCoreMapper.mapSamvaersfradragGrunnlagTilCore(beregnGrunnlag,
            sjablonListe, id, soknadsbarnMap)));

    // Kaller beregning for hvert søknadsbarn
    var resultatSamvaersfradragListe = new ArrayList<no.nav.bidrag.beregn.samvaersfradrag.dto.ResultatPeriodeCore>();
    var sjablonSamvaersfradragListe = new ArrayList<SjablonResultatGrunnlagCore>();
    samvaersfradragGrunnlagTilCoreListe.forEach(samvaersfradragGrunnlagTilCore -> {
      var beregnetSamvaersfradrag = beregnSamvaersfradrag(samvaersfradragGrunnlagTilCore);
      grunnlagReferanseListe.addAll(lagGrunnlagListeSamvaersfradrag(beregnGrunnlag, beregnetSamvaersfradrag));
      resultatSamvaersfradragListe.addAll(beregnetSamvaersfradrag.getResultatPeriodeListe());
      sjablonSamvaersfradragListe.addAll(beregnetSamvaersfradrag.getSjablonListe());
    });
    return new BeregnetSamvaersfradragResultatCore(resultatSamvaersfradragListe, sjablonSamvaersfradragListe, emptyList());
  }

  //==================================================================================================================================================

  // Kaller core for beregning av bidragsevne
  private BeregnetBidragsevneResultatCore beregnBidragsevne(BeregnBidragsevneGrunnlagCore bidragsevneGrunnlagTilCore) {

    // Kaller core-modulen for beregning av bidragsevne
    LOGGER.debug("Bidragsevne - grunnlag for beregning: {}", bidragsevneGrunnlagTilCore);
    var bidragsevneResultatFraCore = bidragsevneCore.beregnBidragsevne(bidragsevneGrunnlagTilCore);

    if (!bidragsevneResultatFraCore.getAvvikListe().isEmpty()) {
      LOGGER.warn("Ugyldig input ved beregning av bidragsevne. Følgende avvik ble funnet: " + System.lineSeparator()
          + bidragsevneResultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining(System.lineSeparator())));
      LOGGER.info("Bidragsevne - grunnlag for beregning:" + System.lineSeparator()
          + "beregnDatoFra= " + bidragsevneGrunnlagTilCore.getBeregnDatoFra() + System.lineSeparator()
          + "beregnDatoTil= " + bidragsevneGrunnlagTilCore.getBeregnDatoTil() + System.lineSeparator()
          + "antallBarnIEgetHusholdPeriodeListe= " + bidragsevneGrunnlagTilCore.getBarnIHusstandPeriodeListe() + System.lineSeparator()
          + "bostatusPeriodeListe= " + bidragsevneGrunnlagTilCore.getBostatusPeriodeListe() + System.lineSeparator()
          + "inntektPeriodeListe= " + bidragsevneGrunnlagTilCore.getInntektPeriodeListe() + System.lineSeparator()
          + "særfradragPeriodeListe= " + bidragsevneGrunnlagTilCore.getSaerfradragPeriodeListe() + System.lineSeparator()
          + "skatteklassePeriodeListe= " + bidragsevneGrunnlagTilCore.getSkatteklassePeriodeListe());
      throw new UgyldigInputException("Ugyldig input ved beregning av bidragsevne. Følgende avvik ble funnet: "
          + bidragsevneResultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining("; ")));
    }

    LOGGER.debug("Bidragsevne - resultat av beregning: {}", bidragsevneResultatFraCore.getResultatPeriodeListe());
    return bidragsevneResultatFraCore;
  }

  // Kaller core for beregning av netto barnetilsyn
  private BeregnetNettoBarnetilsynResultatCore beregnNettoBarnetilsyn(BeregnNettoBarnetilsynGrunnlagCore nettoBarnetilsynGrunnlagTilCore) {

    // Kaller core-modulen for beregning av netto barnetilsyn
    LOGGER.debug("Netto barnetilsyn - grunnlag for beregning: {}", nettoBarnetilsynGrunnlagTilCore);
    var nettoBarnetilsynResultatFraCore = nettoBarnetilsynCore.beregnNettoBarnetilsyn(nettoBarnetilsynGrunnlagTilCore);

    if (!nettoBarnetilsynResultatFraCore.getAvvikListe().isEmpty()) {
      LOGGER.warn("Ugyldig input ved beregning av netto barnetilsyn. Følgende avvik ble funnet: " + System.lineSeparator()
          + nettoBarnetilsynResultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst)
          .collect(Collectors.joining(System.lineSeparator())));
      LOGGER.info("Netto barnetilsyn - grunnlag for beregning:" + System.lineSeparator()
          + "beregnDatoFra= " + nettoBarnetilsynGrunnlagTilCore.getBeregnDatoFra() + System.lineSeparator()
          + "beregnDatoTil= " + nettoBarnetilsynGrunnlagTilCore.getBeregnDatoTil() + System.lineSeparator()
          + "faktiskUtgiftPeriodeListe= " + nettoBarnetilsynGrunnlagTilCore.getFaktiskUtgiftPeriodeListe());
      throw new UgyldigInputException("Ugyldig input ved beregning av netto barnetilsyn. Følgende avvik ble funnet: "
          + nettoBarnetilsynResultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining("; ")));
    }

    LOGGER.debug("Netto barnetilsyn - resultat av beregning: {}", nettoBarnetilsynResultatFraCore.getResultatPeriodeListe());
    return nettoBarnetilsynResultatFraCore;
  }

  // Kaller core for beregning av underholdskostnad
  private BeregnetUnderholdskostnadResultatCore beregnUnderholdskostnad(
      BeregnUnderholdskostnadGrunnlagCore underholdskostnadGrunnlagTilCore) {

    // Kaller core-modulen for beregning av underholdskostnad
    LOGGER.debug("Underholdskostnad - grunnlag for beregning: {}", underholdskostnadGrunnlagTilCore);
    var underholdskostnadResultatFraCore = underholdskostnadCore.beregnUnderholdskostnad(underholdskostnadGrunnlagTilCore);

    if (!underholdskostnadResultatFraCore.getAvvikListe().isEmpty()) {
      LOGGER.warn("Ugyldig input ved beregning av underholdskostnad. Følgende avvik ble funnet: " + System.lineSeparator()
          + underholdskostnadResultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst)
          .collect(Collectors.joining(System.lineSeparator())));
      LOGGER.info("Underholdskostnad - grunnlag for beregning: " + System.lineSeparator()
          + "beregnDatoFra= " + underholdskostnadGrunnlagTilCore.getBeregnDatoFra() + System.lineSeparator()
          + "beregnDatoTil= " + underholdskostnadGrunnlagTilCore.getBeregnDatoTil() + System.lineSeparator()
          + "soknadsbarnPersonId= " + underholdskostnadGrunnlagTilCore.getSoknadsbarn().getPersonId() + System.lineSeparator()
          + "soknadsbarnFodselsdato= " + underholdskostnadGrunnlagTilCore.getSoknadsbarn().getFodselsdato() + System.lineSeparator()
          + "barneTilsynMedStonadPeriodeListe= " + underholdskostnadGrunnlagTilCore.getBarnetilsynMedStonadPeriodeListe() + System.lineSeparator()
          + "forpleiningUtgiftPeriodeListe= " + underholdskostnadGrunnlagTilCore.getForpleiningUtgiftPeriodeListe() + System.lineSeparator()
          + "nettoBarnetilsynPeriodeListe= " + underholdskostnadGrunnlagTilCore.getNettoBarnetilsynPeriodeListe() + System.lineSeparator());
      throw new UgyldigInputException("Ugyldig input ved beregning av underholdskostnad. Følgende avvik ble funnet: "
          + underholdskostnadResultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining("; ")));
    }

    LOGGER.debug("Underholdskostnad - resultat av beregning: {}", underholdskostnadResultatFraCore.getResultatPeriodeListe());
    return underholdskostnadResultatFraCore;
  }

  // Kaller core for beregning av BPs andel av underholdskostnad
  private BeregnetBPsAndelUnderholdskostnadResultatCore beregnBPsAndelUnderholdskostnad(
      BeregnBPsAndelUnderholdskostnadGrunnlagCore bpAndelUnderholdskostnadGrunnlagTilCore) {

    // Kaller core-modulen for beregning av BPs andel av underholdskostnad
    LOGGER.debug("BPs andel av underholdskostnad - grunnlag for beregning: {}", bpAndelUnderholdskostnadGrunnlagTilCore);
    var bpAndelUnderholdskostnadResultatFraCore = bpAndelUnderholdskostnadCore
        .beregnBPsAndelUnderholdskostnad(bpAndelUnderholdskostnadGrunnlagTilCore);

    if (!bpAndelUnderholdskostnadResultatFraCore.getAvvikListe().isEmpty()) {
      LOGGER.warn("Ugyldig input ved beregning av BPs andel av underholdskostnad. Følgende avvik ble funnet: " + System.lineSeparator()
          + bpAndelUnderholdskostnadResultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst)
          .collect(Collectors.joining(System.lineSeparator())));
      LOGGER.info("BPs andel av underholdskostnad - grunnlag for beregning: " + System.lineSeparator()
          + "beregnDatoFra= " + bpAndelUnderholdskostnadGrunnlagTilCore.getBeregnDatoFra() + System.lineSeparator()
          + "beregnDatoTil= " + bpAndelUnderholdskostnadGrunnlagTilCore.getBeregnDatoTil() + System.lineSeparator()
          + "soknadsbarnPersonId= " + bpAndelUnderholdskostnadGrunnlagTilCore.getSoknadsbarnPersonId() + System.lineSeparator()
          + "underholdskostnadPeriodeListe= " + bpAndelUnderholdskostnadGrunnlagTilCore.getUnderholdskostnadPeriodeListe() + System.lineSeparator()
          + "inntektBPPeriodeListe= " + bpAndelUnderholdskostnadGrunnlagTilCore.getInntektBPPeriodeListe() + System.lineSeparator()
          + "inntektBMPeriodeListe= " + bpAndelUnderholdskostnadGrunnlagTilCore.getInntektBMPeriodeListe() + System.lineSeparator()
          + "inntektBBPeriodeListe= " + bpAndelUnderholdskostnadGrunnlagTilCore.getInntektBBPeriodeListe() + System.lineSeparator());
      throw new UgyldigInputException("Ugyldig input ved beregning av BPs andel av underholdskostnad. Følgende avvik ble funnet: "
          + bpAndelUnderholdskostnadResultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining("; ")));
    }

    LOGGER.debug("BPs andel av underholdskostnad - resultat av beregning: {}", bpAndelUnderholdskostnadResultatFraCore.getResultatPeriodeListe());
    return bpAndelUnderholdskostnadResultatFraCore;
  }

  // Kaller core for beregning av samværsfradrag
  private BeregnetSamvaersfradragResultatCore beregnSamvaersfradrag(
      BeregnSamvaersfradragGrunnlagCore samvaersfradragGrunnlagTilCore) {

    // Kaller core-modulen for beregning av samværsfradrag
    LOGGER.debug("Samværsfradrag - grunnlag for beregning: {}", samvaersfradragGrunnlagTilCore);
    var samvaersfradragResultatFraCore = samvaersfradragCore.beregnSamvaersfradrag(samvaersfradragGrunnlagTilCore);

    if (!samvaersfradragResultatFraCore.getAvvikListe().isEmpty()) {
      LOGGER.warn("Ugyldig input ved beregning av samværsfradrag. Følgende avvik ble funnet: " + System.lineSeparator()
          + samvaersfradragResultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst)
          .collect(Collectors.joining(System.lineSeparator())));
      LOGGER.info("Samværsfradrag - grunnlag for beregning: " + System.lineSeparator()
          + "beregnDatoFra= " + samvaersfradragGrunnlagTilCore.getBeregnDatoFra() + System.lineSeparator()
          + "beregnDatoTil= " + samvaersfradragGrunnlagTilCore.getBeregnDatoTil() + System.lineSeparator()
          + "soknadsbarnPersonId= " + samvaersfradragGrunnlagTilCore.getSoknadsbarn().getPersonId() + System.lineSeparator()
          + "soknadsbarnFodselsdato= " + samvaersfradragGrunnlagTilCore.getSoknadsbarn().getFodselsdato() + System.lineSeparator()
          + "samvaersklassePeriodeListe= " + samvaersfradragGrunnlagTilCore.getSamvaersklassePeriodeListe() + System.lineSeparator());
      throw new UgyldigInputException("Ugyldig input ved beregning av samværsfradrag. Følgende avvik ble funnet: "
          + samvaersfradragResultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining("; ")));
    }

    LOGGER.debug("Samværsfradrag - resultat av beregning: {}", samvaersfradragResultatFraCore.getResultatPeriodeListe());
    return samvaersfradragResultatFraCore;
  }

  // Kaller core for beregning av barnebidrag
  private BeregnetBarnebidragResultatCore beregnBarnebidrag(BeregnBarnebidragGrunnlagCore barnebidragGrunnlagTilCore) {

    // Kaller core-modulen for beregning av barnebidrag
    LOGGER.debug("Barnebidrag - grunnlag for beregning: {}", barnebidragGrunnlagTilCore);
    var barnebidragResultatFraCore = barnebidragCore.beregnBarnebidrag(barnebidragGrunnlagTilCore);

    if (!barnebidragResultatFraCore.getAvvikListe().isEmpty()) {
      LOGGER.warn("Ugyldig input ved beregning av barnebidrag. Følgende avvik ble funnet: " + System.lineSeparator()
          + barnebidragResultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst)
          .collect(Collectors.joining(System.lineSeparator())));
      LOGGER.info("Barnebidrag - grunnlag for beregning: " + System.lineSeparator()
          + "beregnDatoFra= " + barnebidragGrunnlagTilCore.getBeregnDatoFra() + System.lineSeparator()
          + "beregnDatoTil= " + barnebidragGrunnlagTilCore.getBeregnDatoTil() + System.lineSeparator()
          + "bidragsevnePeriodeListe= " + barnebidragGrunnlagTilCore.getBidragsevnePeriodeListe() + System.lineSeparator()
          + "bPsAndelUnderholdskostnadPeriodeListe= " + barnebidragGrunnlagTilCore.getBPsAndelUnderholdskostnadPeriodeListe() + System.lineSeparator()
          + "samvaersfradragPeriodeListe= " + barnebidragGrunnlagTilCore.getSamvaersfradragPeriodeListe() + System.lineSeparator()
          + "deltBostedPeriodeListe= " + barnebidragGrunnlagTilCore.getDeltBostedPeriodeListe() + System.lineSeparator()
          + "barnetilleggBPPeriodeListe= " + barnebidragGrunnlagTilCore.getBarnetilleggBPPeriodeListe() + System.lineSeparator()
          + "barnetilleggBMPeriodeListe= " + barnebidragGrunnlagTilCore.getBarnetilleggBMPeriodeListe() + System.lineSeparator()
          + "barnetilleggForsvaretPeriodeListe= " + barnebidragGrunnlagTilCore.getBarnetilleggForsvaretPeriodeListe() + System.lineSeparator()
          + "andreLopendeBidragPeriodeListe= " + barnebidragGrunnlagTilCore.getAndreLopendeBidragPeriodeListe() + System.lineSeparator());
      throw new UgyldigInputException("Ugyldig input ved beregning av barnebidrag. Følgende avvik ble funnet: "
          + barnebidragResultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining("; ")));
    }

    LOGGER.debug("Barnebidrag - resultat av beregning: {}", barnebidragResultatFraCore.getResultatPeriodeListe());
    return barnebidragResultatFraCore;
  }

  //==================================================================================================================================================

  // Henter sjabloner
  private SjablonListe hentSjabloner() {

    // Henter sjabloner for sjablontall
    var sjablonSjablontallListe = Optional.ofNullable(sjablonConsumer.hentSjablonSjablontall().getResponseEntity().getBody())
        .orElse(emptyList());
    LOGGER.debug("Antall sjabloner hentet av type Sjablontall: {}", sjablonSjablontallListe.size());

    // Henter sjabloner for forbruksutgifter
    var sjablonForbruksutgifterListe = Optional.ofNullable(sjablonConsumer.hentSjablonForbruksutgifter().getResponseEntity().getBody())
        .orElse(emptyList());
    LOGGER.debug("Antall sjabloner hentet av type Bidragsevne: {}", sjablonForbruksutgifterListe.size());

    // Henter sjabloner for maks tilsyn
    var sjablonMaksTilsynListe = Optional.ofNullable(sjablonConsumer.hentSjablonMaksTilsyn().getResponseEntity().getBody())
        .orElse(emptyList());
    LOGGER.debug("Antall sjabloner hentet av type Maks tilsyn: {}", sjablonMaksTilsynListe.size());

    // Henter sjabloner for maks bidrag
    var sjablonMaksFradragListe = Optional.ofNullable(sjablonConsumer.hentSjablonMaksFradrag().getResponseEntity().getBody())
        .orElse(emptyList());
    LOGGER.debug("Antall sjabloner hentet av type Maks fradrag: {}", sjablonMaksFradragListe.size());

    // Henter sjabloner for samværsfradrag
    var sjablonSamvaersfradragListe = Optional.ofNullable(sjablonConsumer.hentSjablonSamvaersfradrag().getResponseEntity().getBody())
        .orElse(emptyList());
    LOGGER.debug("Antall sjabloner hentet av type Samværsfradrag: {}", sjablonSamvaersfradragListe.size());

    // Henter sjabloner for bidragsevne
    var sjablonBidragsevneListe = Optional.ofNullable(sjablonConsumer.hentSjablonBidragsevne().getResponseEntity().getBody())
        .orElse(emptyList());
    LOGGER.debug("Antall sjabloner hentet av type Bidragsevne: {}", sjablonBidragsevneListe.size());

    // Henter sjabloner for trinnvis skattesats
    var sjablonTrinnvisSkattesatsListe = Optional.ofNullable(sjablonConsumer.hentSjablonTrinnvisSkattesats().getResponseEntity().getBody())
        .orElse(emptyList());
    LOGGER.debug("Antall sjabloner hentet av type Trinnvis skattesats: {}", sjablonTrinnvisSkattesatsListe.size());

    // Henter sjabloner for barnetilsyn
    var sjablonBarnetilsynListe = Optional.ofNullable(sjablonConsumer.hentSjablonBarnetilsyn().getResponseEntity().getBody())
        .orElse(emptyList());
    LOGGER.debug("Antall sjabloner hentet av type Barnetilsyn: {}", sjablonBarnetilsynListe.size());

    return new SjablonListe(sjablonSjablontallListe, sjablonForbruksutgifterListe, sjablonMaksTilsynListe, sjablonMaksFradragListe,
        sjablonSamvaersfradragListe, sjablonBidragsevneListe, sjablonTrinnvisSkattesatsListe, sjablonBarnetilsynListe);
  }

  //==================================================================================================================================================

  // Lager en liste over resultatgrunnlag som inneholder:
  //   - mottatte grunnlag som er brukt i beregningen
  //   - tidligere delberegninger som er brukt i beregningen
  //   - sjabloner som er brukt i beregningen

  // Bidragsevne
  private List<ResultatGrunnlag> lagGrunnlagListeBidragsevne(BeregnGrunnlag totalBarnebidragGrunnlag,
      BeregnetBidragsevneResultatCore bidragsevneResultatFraCore) {
    var resultatGrunnlagListe = new ArrayList<ResultatGrunnlag>();

    // Bygger opp oversikt over alle grunnlag som er brukt i beregningen
    var grunnlagReferanseListe = bidragsevneResultatFraCore.getResultatPeriodeListe().stream()
        .flatMap(resultatPeriodeCore -> resultatPeriodeCore.getGrunnlagReferanseListe().stream()
            .map(String::new))
        .distinct()
        .toList();

    // Matcher mottatte grunnlag med grunnlag som er brukt i beregningen
    resultatGrunnlagListe.addAll(totalBarnebidragGrunnlag.getGrunnlagListe().stream()
        .filter(grunnlag -> grunnlagReferanseListe.contains(grunnlag.getReferanse()))
        .map(grunnlag -> new ResultatGrunnlag(grunnlag.getReferanse(), grunnlag.getType(), grunnlag.getInnhold()))
        .toList());

    // Danner grunnlag basert på liste over sjabloner som er brukt i beregningen
    resultatGrunnlagListe.addAll(mapSjabloner(bidragsevneResultatFraCore.getSjablonListe()));

    return resultatGrunnlagListe;
  }

  // Netto barnetilsyn
  private List<ResultatGrunnlag> lagGrunnlagListeNettoBarnetilsyn(BeregnGrunnlag totalBarnebidragGrunnlag,
      BeregnetNettoBarnetilsynResultatCore nettoBarnetilsynResultatFraCore) {
    var resultatGrunnlagListe = new ArrayList<ResultatGrunnlag>();

    // Bygger opp oversikt over alle grunnlag som er brukt i beregningen
    var grunnlagReferanseListe = nettoBarnetilsynResultatFraCore.getResultatPeriodeListe().stream()
        .flatMap(resultatPeriodeCore -> resultatPeriodeCore.getGrunnlagReferanseListe().stream()
            .map(String::new))
        .distinct()
        .toList();

    // Matcher mottatte grunnlag med grunnlag som er brukt i beregningen
    resultatGrunnlagListe.addAll(totalBarnebidragGrunnlag.getGrunnlagListe().stream()
        .filter(grunnlag -> grunnlagReferanseListe.contains(grunnlag.getReferanse()))
        .map(grunnlag -> new ResultatGrunnlag(grunnlag.getReferanse(), grunnlag.getType(), grunnlag.getInnhold()))
        .toList());

    // Danner grunnlag basert på liste over sjabloner som er brukt i beregningen
    resultatGrunnlagListe.addAll(mapSjabloner(nettoBarnetilsynResultatFraCore.getSjablonListe()));

    return resultatGrunnlagListe;
  }

  // Underholdskostnad
  private List<ResultatGrunnlag> lagGrunnlagListeUnderholdskostnad(BeregnGrunnlag totalBarnebidragGrunnlag,
      BeregnetUnderholdskostnadResultatCore underholdskostnadResultatFraCore, BeregnUnderholdskostnadGrunnlagCore underholdskostnadGrunnlagTilCore,
      BeregnetNettoBarnetilsynResultatCore nettoBarnetilsynResultatFraCore) {
    var resultatGrunnlagListe = new ArrayList<ResultatGrunnlag>();

    // Bygger opp oversikt over alle grunnlag som er brukt i beregningen
    var grunnlagReferanseListe = underholdskostnadResultatFraCore.getResultatPeriodeListe().stream()
        .flatMap(resultatPeriodeCore -> resultatPeriodeCore.getGrunnlagReferanseListe().stream()
            .map(String::new))
        .distinct()
        .toList();

    // Matcher mottatte grunnlag med grunnlag som er brukt i beregningen
    resultatGrunnlagListe.addAll(totalBarnebidragGrunnlag.getGrunnlagListe().stream()
        .filter(grunnlag -> grunnlagReferanseListe.contains(grunnlag.getReferanse()))
        .map(grunnlag -> new ResultatGrunnlag(grunnlag.getReferanse(), grunnlag.getType(), grunnlag.getInnhold()))
        .toList());

    // Mapper ut delberegninger som er brukt som grunnlag
    resultatGrunnlagListe.addAll(underholdskostnadGrunnlagTilCore.getNettoBarnetilsynPeriodeListe().stream()
        .filter(grunnlag -> grunnlagReferanseListe.contains(grunnlag.getReferanse()))
        .map(grunnlag -> new ResultatGrunnlag(grunnlag.getReferanse(), "Delberegning",
            lagInnholdNettoBarnetilsyn(grunnlag, underholdskostnadGrunnlagTilCore.getSoknadsbarn().getPersonId(), nettoBarnetilsynResultatFraCore)))
        .toList());

    // Danner grunnlag basert på liste over sjabloner som er brukt i beregningen
    resultatGrunnlagListe.addAll(mapSjabloner(underholdskostnadResultatFraCore.getSjablonListe()));

    return resultatGrunnlagListe;
  }

  // BP's andel underholdskostnad
  private List<ResultatGrunnlag> lagGrunnlagListeBPAndelUnderholdskostnad(BeregnGrunnlag totalBarnebidragGrunnlag,
      BeregnetBPsAndelUnderholdskostnadResultatCore bpAndelUnderholdskostnadResultatFraCore,
      BeregnBPsAndelUnderholdskostnadGrunnlagCore bpAndelUnderholdskostnadGrunnlagTilCore,
      BeregnetUnderholdskostnadResultatCore underholdskostnadResultatFraCore) {
    var resultatGrunnlagListe = new ArrayList<ResultatGrunnlag>();

    // Bygger opp oversikt over alle grunnlag som er brukt i beregningen
    var grunnlagReferanseListe = bpAndelUnderholdskostnadResultatFraCore.getResultatPeriodeListe().stream()
        .flatMap(resultatPeriodeCore -> resultatPeriodeCore.getGrunnlagReferanseListe().stream()
            .map(String::new))
        .distinct()
        .toList();

    // Matcher mottatte grunnlag med grunnlag som er brukt i beregningen
    resultatGrunnlagListe.addAll(totalBarnebidragGrunnlag.getGrunnlagListe().stream()
        .filter(grunnlag -> grunnlagReferanseListe.contains(grunnlag.getReferanse()))
        .map(grunnlag -> new ResultatGrunnlag(grunnlag.getReferanse(), grunnlag.getType(), grunnlag.getInnhold()))
        .toList());

    // Mapper ut delberegninger som er brukt som grunnlag
    resultatGrunnlagListe.addAll(bpAndelUnderholdskostnadGrunnlagTilCore.getUnderholdskostnadPeriodeListe().stream()
        .filter(grunnlag -> grunnlagReferanseListe.contains(grunnlag.getReferanse()))
        .map(grunnlag -> new ResultatGrunnlag(grunnlag.getReferanse(), "Delberegning", lagInnholdUnderholdskostnad(grunnlag,
            bpAndelUnderholdskostnadGrunnlagTilCore.getSoknadsbarnPersonId(), underholdskostnadResultatFraCore)))
        .toList());

    // Danner grunnlag basert på liste over sjabloner som er brukt i beregningen
    resultatGrunnlagListe.addAll(mapSjabloner(bpAndelUnderholdskostnadResultatFraCore.getSjablonListe()));

    return resultatGrunnlagListe;
  }

  // Samværsfradrag
  private List<ResultatGrunnlag> lagGrunnlagListeSamvaersfradrag(BeregnGrunnlag totalBarnebidragGrunnlag,
      BeregnetSamvaersfradragResultatCore samvaersfradragResultatFraCore) {
    var resultatGrunnlagListe = new ArrayList<ResultatGrunnlag>();

    // Bygger opp oversikt over alle grunnlag som er brukt i beregningen
    var grunnlagReferanseListe = samvaersfradragResultatFraCore.getResultatPeriodeListe().stream()
        .flatMap(resultatPeriodeCore -> resultatPeriodeCore.getGrunnlagReferanseListe().stream()
            .map(String::new))
        .distinct()
        .toList();

    // Matcher mottatte grunnlag med grunnlag som er brukt i beregningen
    resultatGrunnlagListe.addAll(totalBarnebidragGrunnlag.getGrunnlagListe().stream()
        .filter(grunnlag -> grunnlagReferanseListe.contains(grunnlag.getReferanse()))
        .map(grunnlag -> new ResultatGrunnlag(grunnlag.getReferanse(), grunnlag.getType(), grunnlag.getInnhold()))
        .toList());

    // Danner grunnlag basert på liste over sjabloner som er brukt i beregningen
    resultatGrunnlagListe.addAll(mapSjabloner(samvaersfradragResultatFraCore.getSjablonListe()));

    return resultatGrunnlagListe;
  }

  // Barnebidrag
  private List<ResultatGrunnlag> lagGrunnlagListeBarnebidrag(BeregnGrunnlag totalBarnebidragGrunnlag,
      BeregnetBarnebidragResultatCore barnebidragResultatFraCore, BeregnBarnebidragGrunnlagCore barnebidragGrunnlagTilCore,
      BeregnetBidragsevneResultatCore bidragsevneResultatFraCore,
      BeregnetBPsAndelUnderholdskostnadResultatCore bpAndelUnderholdskostnadResultatFraCore,
      BeregnetSamvaersfradragResultatCore samvaersfradragResultatFraCore) {
    var resultatGrunnlagListe = new ArrayList<ResultatGrunnlag>();

    // Bygger opp oversikt over alle grunnlag som er brukt i beregningen
    var grunnlagReferanseListe = barnebidragResultatFraCore.getResultatPeriodeListe().stream()
        .flatMap(resultatPeriodeCore -> resultatPeriodeCore.getGrunnlagReferanseListe().stream()
            .map(String::new))
        .distinct()
        .toList();

    // Matcher mottatte grunnlag med grunnlag som er brukt i beregningen
    resultatGrunnlagListe.addAll(totalBarnebidragGrunnlag.getGrunnlagListe().stream()
        .filter(grunnlag -> grunnlagReferanseListe.contains(grunnlag.getReferanse()))
        .map(grunnlag -> new ResultatGrunnlag(grunnlag.getReferanse(), grunnlag.getType(), grunnlag.getInnhold()))
        .toList());

    // Mapper ut delberegninger som er brukt som grunnlag
    resultatGrunnlagListe.addAll(barnebidragGrunnlagTilCore.getBidragsevnePeriodeListe().stream()
        .filter(grunnlag -> grunnlagReferanseListe.contains(grunnlag.getReferanse()))
        .map(grunnlag -> new ResultatGrunnlag(grunnlag.getReferanse(), "Delberegning", lagInnholdBidragsevne(grunnlag, bidragsevneResultatFraCore)))
        .toList());

    resultatGrunnlagListe.addAll(barnebidragGrunnlagTilCore.getBPsAndelUnderholdskostnadPeriodeListe().stream()
        .filter(grunnlag -> grunnlagReferanseListe.contains(grunnlag.getReferanse()))
        .map(grunnlag -> new ResultatGrunnlag(grunnlag.getReferanse(), "Delberegning",
            lagInnholdBPAndelUnderholdskostnad(grunnlag, bpAndelUnderholdskostnadResultatFraCore)))
        .toList());

    resultatGrunnlagListe.addAll(barnebidragGrunnlagTilCore.getSamvaersfradragPeriodeListe().stream()
        .filter(grunnlag -> grunnlagReferanseListe.contains(grunnlag.getReferanse()))
        .map(grunnlag -> new ResultatGrunnlag(grunnlag.getReferanse(), "Delberegning",
            lagInnholdSamvaersfradrag(grunnlag, samvaersfradragResultatFraCore)))
        .toList());

    // Danner grunnlag basert på liste over sjabloner som er brukt i beregningen
    resultatGrunnlagListe.addAll(mapSjabloner(barnebidragResultatFraCore.getSjablonListe()));

    return resultatGrunnlagListe;
  }

  // Mapper ut innhold fra delberegning Bidragsevne
  private JsonNode lagInnholdBidragsevne(BidragsevnePeriodeCore bidragsevnePeriodeCore, BeregnetBidragsevneResultatCore bidragsevneResultatFraCore) {
    var mapper = new ObjectMapper();
    var map = new LinkedHashMap<String, Object>();
    map.put("datoFom", mapDato(bidragsevnePeriodeCore.getPeriode().getDatoFom()));
    map.put("datoTil", mapDato(bidragsevnePeriodeCore.getPeriode().getDatoTil()));
    map.put("belop", bidragsevnePeriodeCore.getBelop());
    map.put("25ProsentInntekt", bidragsevnePeriodeCore.getTjuefemProsentInntekt());

    var grunnlagReferanseListe = bidragsevneResultatFraCore.getResultatPeriodeListe().stream()
        .filter(resultatPeriodeCore -> bidragsevnePeriodeCore.getPeriode().getDatoFom().equals(resultatPeriodeCore.getPeriode().getDatoFom()))
        .findFirst()
        .map(ResultatPeriodeCore::getGrunnlagReferanseListe)
        .orElse(emptyList());

    map.put("grunnlagReferanseListe", grunnlagReferanseListe);
    return mapper.valueToTree(map);
  }

  // Mapper ut innhold fra delberegning Netto Barnetilsyn
  private JsonNode lagInnholdNettoBarnetilsyn(NettoBarnetilsynPeriodeCore nettoBarnetilsynPeriodeCore, Integer soknadsbarnPersonId,
      BeregnetNettoBarnetilsynResultatCore nettoBarnetilsynResultatFraCore) {
    var mapper = new ObjectMapper();
    var map = new LinkedHashMap<String, Object>();
    map.put("barn", soknadsbarnPersonId);
    map.put("datoFom", mapDato(nettoBarnetilsynPeriodeCore.getPeriode().getDatoFom()));
    map.put("datoTil", mapDato(nettoBarnetilsynPeriodeCore.getPeriode().getDatoTil()));
    map.put("belop", nettoBarnetilsynPeriodeCore.getBelop());

    var grunnlagReferanseListe = nettoBarnetilsynResultatFraCore.getResultatPeriodeListe().stream()
        .filter(resultatPeriodeCore -> nettoBarnetilsynPeriodeCore.getPeriode().getDatoFom().equals(resultatPeriodeCore.getPeriode().getDatoFom()))
        .findFirst()
        .map(no.nav.bidrag.beregn.nettobarnetilsyn.dto.ResultatPeriodeCore::getGrunnlagReferanseListe)
        .orElse(emptyList());

    map.put("grunnlagReferanseListe", grunnlagReferanseListe);
    return mapper.valueToTree(map);
  }

  // Mapper ut innhold fra delberegning Underholdskostnad
  private JsonNode lagInnholdUnderholdskostnad(UnderholdskostnadPeriodeCore underholdskostnadPeriodeCore, Integer soknadsbarnPersonId,
      BeregnetUnderholdskostnadResultatCore underholdskostnadResultatFraCore) {
    var mapper = new ObjectMapper();
    var map = new LinkedHashMap<String, Object>();
    map.put("barn", soknadsbarnPersonId);
    map.put("datoFom", mapDato(underholdskostnadPeriodeCore.getPeriode().getDatoFom()));
    map.put("datoTil", mapDato(underholdskostnadPeriodeCore.getPeriode().getDatoTil()));
    map.put("belop", underholdskostnadPeriodeCore.getBelop());

    var grunnlagReferanseListe = underholdskostnadResultatFraCore.getResultatPeriodeListe().stream()
        .filter(resultatPeriodeCore -> underholdskostnadPeriodeCore.getPeriode().getDatoFom().equals(resultatPeriodeCore.getPeriode().getDatoFom()))
        .filter(resultatPeriodeCore -> soknadsbarnPersonId.equals(resultatPeriodeCore.getSoknadsbarnPersonId()))
        .findFirst()
        .map(no.nav.bidrag.beregn.underholdskostnad.dto.ResultatPeriodeCore::getGrunnlagReferanseListe)
        .orElse(emptyList());

    map.put("grunnlagReferanseListe", grunnlagReferanseListe);
    return mapper.valueToTree(map);
  }

  // Mapper ut innhold fra delberegning BPsAndelUnderholdskostnad
  private JsonNode lagInnholdBPAndelUnderholdskostnad(BPsAndelUnderholdskostnadPeriodeCore bpAndelUnderholdskostnadPeriodeCore,
      BeregnetBPsAndelUnderholdskostnadResultatCore bpAndelUnderholdskostnadResultatFraCore) {
    var mapper = new ObjectMapper();
    var map = new LinkedHashMap<String, Object>();
    map.put("barn", bpAndelUnderholdskostnadPeriodeCore.getSoknadsbarnPersonId());
    map.put("datoFom", mapDato(bpAndelUnderholdskostnadPeriodeCore.getPeriode().getDatoFom()));
    map.put("datoTil", mapDato(bpAndelUnderholdskostnadPeriodeCore.getPeriode().getDatoTil()));
    map.put("belop", bpAndelUnderholdskostnadPeriodeCore.getAndelBelop().toString());
    map.put("prosent", bpAndelUnderholdskostnadPeriodeCore.getAndelProsent());
    map.put("selvforsorget", bpAndelUnderholdskostnadPeriodeCore.getBarnetErSelvforsorget());

    var grunnlagReferanseListe = bpAndelUnderholdskostnadResultatFraCore.getResultatPeriodeListe().stream()
        .filter(resultatPeriodeCore -> bpAndelUnderholdskostnadPeriodeCore.getPeriode().getDatoFom()
            .equals(resultatPeriodeCore.getPeriode().getDatoFom()))
        .filter(resultatPeriodeCore -> bpAndelUnderholdskostnadPeriodeCore.getSoknadsbarnPersonId() == resultatPeriodeCore.getSoknadsbarnPersonId())
        .findFirst()
        .map(no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatPeriodeCore::getGrunnlagReferanseListe)
        .orElse(emptyList());

    map.put("grunnlagReferanseListe", grunnlagReferanseListe);
    return mapper.valueToTree(map);
  }

  // Mapper ut innhold fra delberegning Samvaersfradrag
  private JsonNode lagInnholdSamvaersfradrag(SamvaersfradragPeriodeCore samvaersfradragPeriodeCore,
      BeregnetSamvaersfradragResultatCore samvaersfradragResultatFraCore) {
    var mapper = new ObjectMapper();
    var map = new LinkedHashMap<String, Object>();
    map.put("barn", samvaersfradragPeriodeCore.getSoknadsbarnPersonId());
    map.put("datoFom", mapDato(samvaersfradragPeriodeCore.getPeriode().getDatoFom()));
    map.put("datoTil", mapDato(samvaersfradragPeriodeCore.getPeriode().getDatoTil()));
    map.put("belop", samvaersfradragPeriodeCore.getBelop());

    var grunnlagReferanseListe = samvaersfradragResultatFraCore.getResultatPeriodeListe().stream()
        .filter(resultatPeriodeCore -> samvaersfradragPeriodeCore.getPeriode().getDatoFom().equals(resultatPeriodeCore.getPeriode().getDatoFom()))
        .filter(resultatPeriodeCore -> samvaersfradragPeriodeCore.getSoknadsbarnPersonId() == resultatPeriodeCore.getSoknadsbarnPersonId())
        .findFirst()
        .map(no.nav.bidrag.beregn.samvaersfradrag.dto.ResultatPeriodeCore::getGrunnlagReferanseListe)
        .orElse(emptyList());

    map.put("grunnlagReferanseListe", grunnlagReferanseListe);
    return mapper.valueToTree(map);
  }

  // Unngå å legge ut datoer høyere enn 9999-12-31
  private static String mapDato(LocalDate dato) {
    return dato.isAfter(LocalDate.parse("9999-12-31")) ? "9999-12-31" : dato.toString();
  }

  private List<ResultatGrunnlag> mapSjabloner(List<SjablonResultatGrunnlagCore> sjablonResultatGrunnlagCoreListe) {
    var mapper = new ObjectMapper();
    return sjablonResultatGrunnlagCoreListe.stream()
        .map(sjablon -> {
              var map = new LinkedHashMap<String, Object>();
              map.put("datoFom", mapDato(sjablon.getPeriode().getDatoFom()));
              map.put("datoTil", mapDato(sjablon.getPeriode().getDatoTil()));
              map.put("sjablonNavn", sjablon.getNavn());
              map.put("sjablonVerdi", sjablon.getVerdi().toString());
              return new ResultatGrunnlag(sjablon.getReferanse(), "Sjablon", mapper.valueToTree(map));
            }
        )
        .toList();
  }
}
