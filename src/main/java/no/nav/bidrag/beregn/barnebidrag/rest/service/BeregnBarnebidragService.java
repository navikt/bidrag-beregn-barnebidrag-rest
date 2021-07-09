package no.nav.bidrag.beregn.barnebidrag.rest.service;

import static java.util.Collections.emptyList;
import static no.nav.bidrag.beregn.barnebidrag.rest.service.SoknadsbarnUtil.validerSoknadsbarnId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import no.nav.bidrag.beregn.barnebidrag.BarnebidragCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BeregnBarnebidragGrunnlagCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BeregnetBarnebidragResultatCore;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonConsumer;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonListe;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBMNettoBarnetilsynResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBMUnderholdskostnadResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBPAndelUnderholdskostnadResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBPBidragsevneResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBPSamvaersfradragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBarnebidragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragResultat;
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
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.BPsAndelUnderholdskostnadCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnBPsAndelUnderholdskostnadGrunnlagCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnetBPsAndelUnderholdskostnadResultatCore;
import no.nav.bidrag.beregn.felles.dto.AvvikCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.NettoBarnetilsynCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnNettoBarnetilsynGrunnlagCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnetNettoBarnetilsynResultatCore;
import no.nav.bidrag.beregn.samvaersfradrag.SamvaersfradragCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnSamvaersfradragGrunnlagCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnetSamvaersfradragResultatCore;
import no.nav.bidrag.beregn.underholdskostnad.UnderholdskostnadCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadGrunnlagCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnetUnderholdskostnadResultatCore;
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

  public HttpResponse<BeregnTotalBarnebidragResultat> beregn(BeregnTotalBarnebidragGrunnlag grunnlag) {

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
  private HttpResponse<BeregnTotalBarnebidragResultat> utfoerDelberegninger(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag,
      Map<Integer, String> soknadsbarnMap, SjablonListe sjablonListe) {

    // ++ Bidragsevne
    var bidragsevneGrunnlagTilCore = bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(beregnTotalBarnebidragGrunnlag, sjablonListe);
    var bidragsevneResultatFraCore = beregnBidragsevne(bidragsevneGrunnlagTilCore);

    // ++ Netto barnetilsyn
    var nettoBarnetilsynGrunnlagTilCore = nettoBarnetilsynCoreMapper.mapNettoBarnetilsynGrunnlagTilCore(beregnTotalBarnebidragGrunnlag, sjablonListe,
        soknadsbarnMap);
    var nettoBarnetilsynResultatFraCore = beregnNettoBarnetilsyn(nettoBarnetilsynGrunnlagTilCore);

    // ++ Underholdskostnad
    var underholdskostnadResultatFraCore = utfoerDelberegningUnderholdskostnad(beregnTotalBarnebidragGrunnlag, soknadsbarnMap,
        nettoBarnetilsynResultatFraCore, sjablonListe);

    // ++ BPs andel av underholdskostnad
    var bpAndelUnderholdskostnadResultatFraCore = utfoerDelberegningBPAndelUnderholdskostnad(beregnTotalBarnebidragGrunnlag, soknadsbarnMap,
        underholdskostnadResultatFraCore, sjablonListe);

    // ++ Samværsfradrag
    var samvaersfradragResultatFraCore = utfoerDelberegningSamvaersfradrag(beregnTotalBarnebidragGrunnlag, soknadsbarnMap, sjablonListe);

    // ++ Barnebidrag (totalberegning)
    var barnebidragGrunnlagTilCore = barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(beregnTotalBarnebidragGrunnlag, sjablonListe,
        bidragsevneResultatFraCore, bpAndelUnderholdskostnadResultatFraCore, samvaersfradragResultatFraCore);
    var barnebidragResultatFraCore = beregnBarnebidrag(barnebidragGrunnlagTilCore);

    // Bygger responsobjekt
    return HttpResponse.from(HttpStatus.OK, new BeregnTotalBarnebidragResultat(
        new BeregnBPBidragsevneResultat(bidragsevneResultatFraCore),
        new BeregnBMNettoBarnetilsynResultat(nettoBarnetilsynResultatFraCore),
        new BeregnBMUnderholdskostnadResultat(underholdskostnadResultatFraCore),
        new BeregnBPAndelUnderholdskostnadResultat(bpAndelUnderholdskostnadResultatFraCore),
        new BeregnBPSamvaersfradragResultat(samvaersfradragResultatFraCore),
        new BeregnBarnebidragResultat(barnebidragResultatFraCore)));
  }

  //==================================================================================================================================================

  // Delberegning underholdskostnad
  private BeregnetUnderholdskostnadResultatCore utfoerDelberegningUnderholdskostnad(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag,
      Map<Integer, String> soknadsbarnMap, BeregnetNettoBarnetilsynResultatCore nettoBarnetilsynResultatFraCore, SjablonListe sjablonListe) {

    // Bygger opp liste med grunnlag for hvert søknadsbarn
    var underholdskostnadGrunnlagTilCoreListe = new ArrayList<BeregnUnderholdskostnadGrunnlagCore>();
    soknadsbarnMap.forEach((id, fodselsdato) ->
        underholdskostnadGrunnlagTilCoreListe.add(underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(beregnTotalBarnebidragGrunnlag,
            sjablonListe, id, nettoBarnetilsynResultatFraCore, soknadsbarnMap)));

    // Kaller beregning for hvert søknadsbarn
    var resultatUnderholdskostnadListe = new ArrayList<no.nav.bidrag.beregn.underholdskostnad.dto.ResultatPeriodeCore>();
    underholdskostnadGrunnlagTilCoreListe.forEach(underholdskostnadGrunnlagTilCore ->
        resultatUnderholdskostnadListe.addAll(beregnUnderholdskostnad(underholdskostnadGrunnlagTilCore)));

    return new BeregnetUnderholdskostnadResultatCore(resultatUnderholdskostnadListe, emptyList(), emptyList());
  }

  // Delberegning BPs andel av underholdskostnad
  private BeregnetBPsAndelUnderholdskostnadResultatCore utfoerDelberegningBPAndelUnderholdskostnad(
      BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag, Map<Integer, String> soknadsbarnMap,
      BeregnetUnderholdskostnadResultatCore underholdskostnadResultatFraCore, SjablonListe sjablonListe) {

    // Bygger opp liste med grunnlag for hvert søknadsbarn
    var bpAndelUnderholdskostnadGrunnlagTilCoreListe = new ArrayList<BeregnBPsAndelUnderholdskostnadGrunnlagCore>();
    soknadsbarnMap.forEach((id, fodselsdato) ->
        bpAndelUnderholdskostnadGrunnlagTilCoreListe.add(
            bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(beregnTotalBarnebidragGrunnlag, sjablonListe, id,
                underholdskostnadResultatFraCore)));

    // Kaller beregning for hvert søknadsbarn
    var resultatBPAndelUnderholdskostnadListe = new ArrayList<no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatPeriodeCore>();
    bpAndelUnderholdskostnadGrunnlagTilCoreListe.forEach(bpAndelUnderholdskostnadGrunnlagTilCore ->
        resultatBPAndelUnderholdskostnadListe.addAll(beregnBPsAndelUnderholdskostnad(bpAndelUnderholdskostnadGrunnlagTilCore)));

    return new BeregnetBPsAndelUnderholdskostnadResultatCore(resultatBPAndelUnderholdskostnadListe, emptyList(), emptyList());
  }

  // Delberegning samværsfradrag
  private BeregnetSamvaersfradragResultatCore utfoerDelberegningSamvaersfradrag(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag,
      Map<Integer, String> soknadsbarnMap, SjablonListe sjablonListe) {

    // Bygger opp liste med grunnlag for hvert søknadsbarn
    var samvaersfradragGrunnlagTilCoreListe = new ArrayList<BeregnSamvaersfradragGrunnlagCore>();
    soknadsbarnMap.forEach((id, fodselsdato) ->
        samvaersfradragGrunnlagTilCoreListe.add(samvaersfradragCoreMapper.mapSamvaersfradragGrunnlagTilCore(beregnTotalBarnebidragGrunnlag,
            sjablonListe, id, soknadsbarnMap)));

    // Kaller beregning for hvert søknadsbarn
    var resultatSamvaersfradragListe = new ArrayList<no.nav.bidrag.beregn.samvaersfradrag.dto.ResultatPeriodeCore>();
    samvaersfradragGrunnlagTilCoreListe.forEach(samvaersfradragGrunnlagTilCore ->
        resultatSamvaersfradragListe.addAll(beregnSamvaersfradrag(samvaersfradragGrunnlagTilCore)));
    return new BeregnetSamvaersfradragResultatCore(resultatSamvaersfradragListe, emptyList(), emptyList());
  }

  //==================================================================================================================================================

  // Kaller core for beregning av bidragsevne
  private BeregnetBidragsevneResultatCore beregnBidragsevne(BeregnBidragsevneGrunnlagCore bidragsevneGrunnlagTilCore) {

    // Kaller core-modulen for beregning av bidragsevne
    LOGGER.debug("Bidragsevne - grunnlag for beregning: {}", bidragsevneGrunnlagTilCore);
    var bidragsevneResultatFraCore = bidragsevneCore.beregnBidragsevne(bidragsevneGrunnlagTilCore);

    if (!bidragsevneResultatFraCore.getAvvikListe().isEmpty()) {
      LOGGER.error("Ugyldig input ved beregning av bidragsevne. Følgende avvik ble funnet: " + System.lineSeparator()
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
      LOGGER.error("Ugyldig input ved beregning av netto barnetilsyn. Følgende avvik ble funnet: " + System.lineSeparator()
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
  private List<no.nav.bidrag.beregn.underholdskostnad.dto.ResultatPeriodeCore> beregnUnderholdskostnad(
      BeregnUnderholdskostnadGrunnlagCore underholdskostnadGrunnlagTilCore) {

    // Kaller core-modulen for beregning av underholdskostnad
    LOGGER.debug("Underholdskostnad - grunnlag for beregning: {}", underholdskostnadGrunnlagTilCore);
    var underholdskostnadResultatFraCore = underholdskostnadCore.beregnUnderholdskostnad(underholdskostnadGrunnlagTilCore);

    if (!underholdskostnadResultatFraCore.getAvvikListe().isEmpty()) {
      LOGGER.error("Ugyldig input ved beregning av underholdskostnad. Følgende avvik ble funnet: " + System.lineSeparator()
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
    return underholdskostnadResultatFraCore.getResultatPeriodeListe();
  }

  // Kaller core for beregning av BPs andel av underholdskostnad
  private List<no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatPeriodeCore> beregnBPsAndelUnderholdskostnad(
      BeregnBPsAndelUnderholdskostnadGrunnlagCore bpAndelUnderholdskostnadGrunnlagTilCore) {

    // Kaller core-modulen for beregning av BPs andel av underholdskostnad
    LOGGER.debug("BPs andel av underholdskostnad - grunnlag for beregning: {}", bpAndelUnderholdskostnadGrunnlagTilCore);
    var bpAndelUnderholdskostnadResultatFraCore = bpAndelUnderholdskostnadCore
        .beregnBPsAndelUnderholdskostnad(bpAndelUnderholdskostnadGrunnlagTilCore);

    if (!bpAndelUnderholdskostnadResultatFraCore.getAvvikListe().isEmpty()) {
      LOGGER.error("Ugyldig input ved beregning av BPs andel av underholdskostnad. Følgende avvik ble funnet: " + System.lineSeparator()
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
    return bpAndelUnderholdskostnadResultatFraCore.getResultatPeriodeListe();
  }

  // Kaller core for beregning av samværsfradrag
  private List<no.nav.bidrag.beregn.samvaersfradrag.dto.ResultatPeriodeCore> beregnSamvaersfradrag(
      BeregnSamvaersfradragGrunnlagCore samvaersfradragGrunnlagTilCore) {

    // Kaller core-modulen for beregning av samværsfradrag
    LOGGER.debug("Samværsfradrag - grunnlag for beregning: {}", samvaersfradragGrunnlagTilCore);
    var samvaersfradragResultatFraCore = samvaersfradragCore.beregnSamvaersfradrag(samvaersfradragGrunnlagTilCore);

    if (!samvaersfradragResultatFraCore.getAvvikListe().isEmpty()) {
      LOGGER.error("Ugyldig input ved beregning av samværsfradrag. Følgende avvik ble funnet: " + System.lineSeparator()
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
    return samvaersfradragResultatFraCore.getResultatPeriodeListe();
  }

  // Kaller core for beregning av barnebidrag
  private BeregnetBarnebidragResultatCore beregnBarnebidrag(BeregnBarnebidragGrunnlagCore barnebidragGrunnlagTilCore) {

    // Kaller core-modulen for beregning av barnebidrag
    LOGGER.debug("Barnebidrag - grunnlag for beregning: {}", barnebidragGrunnlagTilCore);
    var barnebidragResultatFraCore = barnebidragCore.beregnBarnebidrag(barnebidragGrunnlagTilCore);

    if (!barnebidragResultatFraCore.getAvvikListe().isEmpty()) {
      LOGGER.error("Ugyldig input ved beregning av barnebidrag. Følgende avvik ble funnet: " + System.lineSeparator()
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
}
