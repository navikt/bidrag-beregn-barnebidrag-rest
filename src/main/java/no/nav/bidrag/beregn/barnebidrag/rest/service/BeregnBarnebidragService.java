package no.nav.bidrag.beregn.barnebidrag.rest.service;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import no.nav.bidrag.beregn.barnebidrag.BarnebidragCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BeregnBarnebidragGrunnlagCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BeregnetBarnebidragResultatCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BidragsevnePeriodeCore;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Barnetilsyn;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Bidragsevne;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Forbruksutgifter;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.MaksFradrag;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.MaksTilsyn;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Samvaersfradrag;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonConsumer;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonListe;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Sjablontall;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.TrinnvisSkattesats;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBMNettoBarnetilsynResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBMUnderholdskostnadResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBPAndelUnderholdskostnadResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBPBidragsevneResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBPKostnadsberegnetBidragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBPSamvaersfradragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBarnebidragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.Soknadsbarn;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;
import no.nav.bidrag.beregn.bidragsevne.BidragsevneCore;
import no.nav.bidrag.beregn.bidragsevne.dto.BeregnBidragsevneGrunnlagCore;
import no.nav.bidrag.beregn.bidragsevne.dto.BeregnetBidragsevneResultatCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.BPsAndelUnderholdskostnadCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnBPsAndelUnderholdskostnadGrunnlagCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnetBPsAndelUnderholdskostnadResultatCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.InntektPeriodeCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.UnderholdskostnadPeriodeCore;
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
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.BeregnetKostnadsberegnetBidragResultatCore;
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.SamvaersfradragPeriodeCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.NettoBarnetilsynCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnNettoBarnetilsynGrunnlagCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnetNettoBarnetilsynResultatCore;
import no.nav.bidrag.beregn.samvaersfradrag.SamvaersfradragCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnSamvaersfradragGrunnlagCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnetSamvaersfradragResultatCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.SamvaersklassePeriodeCore;
import no.nav.bidrag.beregn.underholdskostnad.UnderholdskostnadCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BarnetilsynMedStonadPeriodeCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadGrunnlagCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnetUnderholdskostnadResultatCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.ForpleiningUtgiftPeriodeCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.NettoBarnetilsynPeriodeCore;
import no.nav.bidrag.commons.web.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BeregnBarnebidragService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BeregnBarnebidragService.class);

  private static final String BIDRAGSEVNE = "Bidragsevne";
  private static final String NETTO_BARNETILSYN = "NettoBarnetilsyn";
  private static final String UNDERHOLDSKOSTNAD = "Underholdskostnad";
  private static final String BP_ANDEL_UNDERHOLDSKOSTNAD = "BPsAndelUnderholdskostnad";
  private static final String BARNEBIDRAG = "Barnebidrag";

  private final SjablonConsumer sjablonConsumer;
  private final BidragsevneCore bidragsevneCore;
  private final NettoBarnetilsynCore nettoBarnetilsynCore;
  private final UnderholdskostnadCore underholdskostnadCore;
  private final BPsAndelUnderholdskostnadCore bpAndelUnderholdskostnadCore;
  private final SamvaersfradragCore samvaersfradragCore;
  private final KostnadsberegnetBidragCore kostnadsberegnetBidragCore;
  private final BarnebidragCore barnebidragCore;

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
    beregnTotalBarnebidragGrunnlag.validerSoknadsbarn();
    beregnTotalBarnebidragGrunnlag.validerInntekt();

    // Lager en map for sjablontall (id og navn)
    var sjablontallMap = new HashMap<String, SjablonTallNavn>();
    for (SjablonTallNavn sjablonTallNavn : SjablonTallNavn.values()) {
      sjablontallMap.put(sjablonTallNavn.getId(), sjablonTallNavn);
    }

    // Lager en map for søknadsbarn id og fødselsdato
    var soknadsbarnMap = beregnTotalBarnebidragGrunnlag.getSoknadsbarnGrunnlag().getSoknadsbarnListe().stream()
        .collect(Collectors.toMap(Soknadsbarn::getSoknadsbarnPersonId, Soknadsbarn::getSoknadsbarnFodselsdato));

    // Henter sjabloner
    var sjablonListe = hentSjabloner();

    // Bygger grunnlag til core og utfører delberegninger
    return utfoerDelberegninger(beregnTotalBarnebidragGrunnlag, soknadsbarnMap, sjablontallMap, sjablonListe);
  }

  //==================================================================================================================================================

  // Bygger grunnlag til core og kaller delberegninger
  private HttpResponse<BeregnTotalBarnebidragResultat> utfoerDelberegninger(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag,
      Map<Integer, LocalDate> soknadsbarnMap, Map<String, SjablonTallNavn> sjablontallMap, SjablonListe sjablonListe) {

    // ++ Bidragsevne
    var bidragsevneGrunnlagTilCore = byggBidragsevneGrunnlagTilCore(beregnTotalBarnebidragGrunnlag, sjablontallMap, sjablonListe);
    var bidragsevneResultatFraCore = beregnBidragsevne(bidragsevneGrunnlagTilCore);

    // ++ Netto barnetilsyn
    var nettoBarnetilsynGrunnlagTilCore = byggNettoBarnetilsynGrunnlagTilCore(beregnTotalBarnebidragGrunnlag, soknadsbarnMap, sjablontallMap,
        sjablonListe);
    var nettoBarnetilsynResultatFraCore = beregnNettoBarnetilsyn(nettoBarnetilsynGrunnlagTilCore);

    // ++ Underholdskostnad
    var underholdskostnadResultatFraCore = utfoerDelberegningUnderholdskostnad(beregnTotalBarnebidragGrunnlag, soknadsbarnMap, sjablontallMap,
        nettoBarnetilsynResultatFraCore, sjablonListe);

    // ++ BPs andel av underholdskostnad
    var bpAndelUnderholdskostnadResultatFraCore = utfoerDelberegningBPAndelUnderholdskostnad(beregnTotalBarnebidragGrunnlag, sjablontallMap,
        underholdskostnadResultatFraCore, sjablonListe);

    // ++ Samværsfradrag
    var samvaersfradragResultatFraCore = utfoerDelberegningSamvaersfradrag(beregnTotalBarnebidragGrunnlag, soknadsbarnMap, sjablonListe);

    // ++ Kostnadsberegnet bidrag
    var kostnadsberegnetBidragResultatFraCore = utfoerDelberegningKostnadsberegnetBidrag(beregnTotalBarnebidragGrunnlag,
        underholdskostnadResultatFraCore, bpAndelUnderholdskostnadResultatFraCore, samvaersfradragResultatFraCore);

    // ++ Barnebidrag (totalberegning)
    var barnebidragGrunnlagTilCore = byggBarnebidragGrunnlagTilCore(beregnTotalBarnebidragGrunnlag, sjablontallMap, bidragsevneResultatFraCore,
        bpAndelUnderholdskostnadResultatFraCore, samvaersfradragResultatFraCore, sjablonListe);
    var barnebidragResultatFraCore = beregnBarnebidrag(barnebidragGrunnlagTilCore);

    // Bygger responsobjekt
    return HttpResponse.from(HttpStatus.OK, new BeregnTotalBarnebidragResultat(
        new BeregnBPBidragsevneResultat(bidragsevneResultatFraCore),
        new BeregnBMNettoBarnetilsynResultat(nettoBarnetilsynResultatFraCore),
        new BeregnBMUnderholdskostnadResultat(underholdskostnadResultatFraCore),
        new BeregnBPAndelUnderholdskostnadResultat(bpAndelUnderholdskostnadResultatFraCore),
        new BeregnBPSamvaersfradragResultat(samvaersfradragResultatFraCore),
        new BeregnBPKostnadsberegnetBidragResultat(kostnadsberegnetBidragResultatFraCore),
        new BeregnBarnebidragResultat(barnebidragResultatFraCore)));
  }

  // Delberegning underholdskostnad
  private BeregnetUnderholdskostnadResultatCore utfoerDelberegningUnderholdskostnad(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag,
      Map<Integer, LocalDate> soknadsbarnMap, Map<String, SjablonTallNavn> sjablontallMap,
      BeregnetNettoBarnetilsynResultatCore nettoBarnetilsynResultatFraCore, SjablonListe sjablonListe) {

    // Validerer input
    beregnTotalBarnebidragGrunnlag.validerUnderholdskostnad();

    // Bygger opp liste med grunnlag for hvert søknadsbarn
    var underholdskostnadGrunnlagTilCoreListe = new ArrayList<BeregnUnderholdskostnadGrunnlagCore>();
    beregnTotalBarnebidragGrunnlag.getSoknadsbarnGrunnlag().getSoknadsbarnListe()
        .forEach(soknadsbarn -> underholdskostnadGrunnlagTilCoreListe
            .add(byggUnderholdskostnadGrunnlagTilCore(beregnTotalBarnebidragGrunnlag, soknadsbarn.getSoknadsbarnPersonId(), soknadsbarnMap,
                sjablontallMap, nettoBarnetilsynResultatFraCore, sjablonListe)));

    // Kaller beregning for hvert søknadsbarn
    var resultatUnderholdskostnadListe = new ArrayList<no.nav.bidrag.beregn.underholdskostnad.dto.ResultatPeriodeCore>();
    underholdskostnadGrunnlagTilCoreListe
        .forEach(underholdskostnadGrunnlagTilCore -> resultatUnderholdskostnadListe
            .addAll(beregnUnderholdskostnad(underholdskostnadGrunnlagTilCore)));

    return new BeregnetUnderholdskostnadResultatCore(resultatUnderholdskostnadListe, emptyList(), emptyList());
  }

  // Delberegning BPs andel av underholdskostnad
  private BeregnetBPsAndelUnderholdskostnadResultatCore utfoerDelberegningBPAndelUnderholdskostnad(
      BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag, Map<String, SjablonTallNavn> sjablontallMap,
      BeregnetUnderholdskostnadResultatCore underholdskostnadResultatFraCore, SjablonListe sjablonListe) {

    // Bygger opp liste med grunnlag for hvert søknadsbarn
    var bpAndelUnderholdskostnadGrunnlagTilCoreListe = new ArrayList<BeregnBPsAndelUnderholdskostnadGrunnlagCore>();
    beregnTotalBarnebidragGrunnlag.getSoknadsbarnGrunnlag().getSoknadsbarnListe()
        .forEach(soknadsbarn -> bpAndelUnderholdskostnadGrunnlagTilCoreListe
            .add(byggBPAndelUnderholdskostnadGrunnlagTilCore(beregnTotalBarnebidragGrunnlag, soknadsbarn.getSoknadsbarnPersonId(), sjablontallMap,
                underholdskostnadResultatFraCore, sjablonListe)));

    // Kaller beregning for hvert søknadsbarn
    var resultatBPAndelUnderholdskostnadListe = new ArrayList<no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.ResultatPeriodeCore>();
    bpAndelUnderholdskostnadGrunnlagTilCoreListe
        .forEach(bpAndelUnderholdskostnadGrunnlagTilCore -> resultatBPAndelUnderholdskostnadListe
            .addAll(beregnBPsAndelUnderholdskostnad(bpAndelUnderholdskostnadGrunnlagTilCore)));

    return new BeregnetBPsAndelUnderholdskostnadResultatCore(resultatBPAndelUnderholdskostnadListe, emptyList(), emptyList());
  }

  // Delberegning samværsfradrag
  private BeregnetSamvaersfradragResultatCore utfoerDelberegningSamvaersfradrag(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag,
      Map<Integer, LocalDate> soknadsbarnMap, SjablonListe sjablonListe) {

    // Validerer input
    beregnTotalBarnebidragGrunnlag.validerSamvaersfradrag();

    // Bygger opp liste med grunnlag for hvert søknadsbarn
    var samvaersfradragGrunnlagTilCoreListe = new ArrayList<BeregnSamvaersfradragGrunnlagCore>();
    beregnTotalBarnebidragGrunnlag.getSoknadsbarnGrunnlag().getSoknadsbarnListe()
        .forEach(soknadsbarn -> samvaersfradragGrunnlagTilCoreListe
            .add(byggSamvaersfradragGrunnlagTilCore(beregnTotalBarnebidragGrunnlag, soknadsbarn.getSoknadsbarnPersonId(), soknadsbarnMap,
                sjablonListe)));

    // Kaller beregning for hvert søknadsbarn
    var resultatSamvaersfradragListe = new ArrayList<no.nav.bidrag.beregn.samvaersfradrag.dto.ResultatPeriodeCore>();
    samvaersfradragGrunnlagTilCoreListe
        .forEach(samvaersfradragGrunnlagTilCore -> resultatSamvaersfradragListe
            .addAll(beregnSamvaersfradrag(samvaersfradragGrunnlagTilCore)));
    return new BeregnetSamvaersfradragResultatCore(resultatSamvaersfradragListe, emptyList(), emptyList());
  }

  // Delberegning kostnadsberegnet bidrag
  private BeregnetKostnadsberegnetBidragResultatCore utfoerDelberegningKostnadsberegnetBidrag(
      BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag,
      BeregnetUnderholdskostnadResultatCore underholdskostnadResultatFraCore,
      BeregnetBPsAndelUnderholdskostnadResultatCore bpAndelUnderholdskostnadResultatFraCore,
      BeregnetSamvaersfradragResultatCore samvaersfradragResultatFraCore) {

    // Bygger opp liste med grunnlag for hvert søknadsbarn
    var kostnadsberegnetBidragGrunnlagTilCoreListe = new ArrayList<BeregnKostnadsberegnetBidragGrunnlagCore>();
    beregnTotalBarnebidragGrunnlag.getSoknadsbarnGrunnlag().getSoknadsbarnListe()
        .forEach(soknadsbarn -> kostnadsberegnetBidragGrunnlagTilCoreListe
            .add(byggKostnadsberegnetBidragGrunnlagTilCore(beregnTotalBarnebidragGrunnlag, soknadsbarn.getSoknadsbarnPersonId(),
                underholdskostnadResultatFraCore, bpAndelUnderholdskostnadResultatFraCore, samvaersfradragResultatFraCore)));

    // Kaller beregning for hvert søknadsbarn
    var resultatKostnadsberegnetBidragListe = new ArrayList<no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.ResultatPeriodeCore>();
    kostnadsberegnetBidragGrunnlagTilCoreListe
        .forEach(kostnadsberegnetBidragGrunnlagTilCore -> resultatKostnadsberegnetBidragListe
            .addAll(beregnKostnadsberegnetBidrag(kostnadsberegnetBidragGrunnlagTilCore)));
    return new BeregnetKostnadsberegnetBidragResultatCore(resultatKostnadsberegnetBidragListe, emptyList());
  }

  //==================================================================================================================================================

  // Bygger grunnlag til core for beregning av bidragsevne
  private BeregnBidragsevneGrunnlagCore byggBidragsevneGrunnlagTilCore(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag,
      Map<String, SjablonTallNavn> sjablontallMap, SjablonListe sjablonListe) {

    // Hent aktuelle sjabloner
    var sjablonPeriodeCoreListe = new ArrayList<SjablonPeriodeCore>();
    sjablonPeriodeCoreListe.addAll(
        mapSjablonSjablontall(sjablonListe.getSjablonSjablontallResponse(), BIDRAGSEVNE, beregnTotalBarnebidragGrunnlag, sjablontallMap));
    sjablonPeriodeCoreListe.addAll(mapSjablonBidragsevne(sjablonListe.getSjablonBidragsevneResponse(), beregnTotalBarnebidragGrunnlag));
    sjablonPeriodeCoreListe
        .addAll(mapSjablonTrinnvisSkattesats(sjablonListe.getSjablonTrinnvisSkattesatsResponse(), beregnTotalBarnebidragGrunnlag));

    // Bygg grunnlag for beregning av bidragsevne. Her gjøres også kontroll av inputdata
    return beregnTotalBarnebidragGrunnlag.bidragsevneTilCore(sjablonPeriodeCoreListe);
  }

  // Bygger grunnlag til core for beregning av netto barnetilsyn
  private BeregnNettoBarnetilsynGrunnlagCore byggNettoBarnetilsynGrunnlagTilCore(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag,
      Map<Integer, LocalDate> soknadsbarnMap, Map<String, SjablonTallNavn> sjablontallMap, SjablonListe sjablonListe) {

    // Hent aktuelle sjabloner
    var sjablonPeriodeCoreListe = new ArrayList<SjablonPeriodeCore>();
    sjablonPeriodeCoreListe.addAll(mapSjablonSjablontall(sjablonListe.getSjablonSjablontallResponse(), NETTO_BARNETILSYN,
        beregnTotalBarnebidragGrunnlag, sjablontallMap));
    sjablonPeriodeCoreListe.addAll(mapSjablonMaksTilsyn(sjablonListe.getSjablonMaksTilsynResponse(), beregnTotalBarnebidragGrunnlag));
    sjablonPeriodeCoreListe.addAll(mapSjablonMaksFradrag(sjablonListe.getSjablonMaksFradragResponse(), beregnTotalBarnebidragGrunnlag));

    // Bygg grunnlag for beregning av netto barnetilsyn. Her gjøres også kontroll av inputdata
    return beregnTotalBarnebidragGrunnlag.nettoBarnetilsynTilCore(soknadsbarnMap, sjablonPeriodeCoreListe);
  }

  // Bygger grunnlag til core for beregning av underholdskostnad
  private BeregnUnderholdskostnadGrunnlagCore byggUnderholdskostnadGrunnlagTilCore(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag,
      Integer soknadsbarnPersonId, Map<Integer, LocalDate> soknadsbarnMap, Map<String, SjablonTallNavn> sjablontallMap,
      BeregnetNettoBarnetilsynResultatCore nettoBarnetilsynResultatFraCore, SjablonListe sjablonListe) {

    // Løp gjennom BarnetilsynMedStonadPeriodeListe og bygg opp ny liste til core med riktig PersonId
    var barnetilsynMedStonadPeriodeCoreListe =
        beregnTotalBarnebidragGrunnlag.getBeregnBMUnderholdskostnadGrunnlag().getBarnetilsynMedStonadPeriodeListe()
            .stream()
            .filter(bMSPeriode -> bMSPeriode.getBarnetilsynMedStonadSoknadsbarnPersonId().equals(soknadsbarnPersonId))
            .map(bMSPeriode -> new BarnetilsynMedStonadPeriodeCore("",
                new PeriodeCore(bMSPeriode.getBarnetilsynMedStonadDatoFraTil().getPeriodeDatoFra(),
                    bMSPeriode.getBarnetilsynMedStonadDatoFraTil().getPeriodeDatoTil()),
                bMSPeriode.getBarnetilsynMedStonadTilsynType(), bMSPeriode.getBarnetilsynMedStonadStonadType()))
            .collect(toList());

    // Løp gjennom output fra beregning av netto barnetilsyn og byggg opp ny input-liste til core
    var nettoBarnetilsynPeriodeCoreListe = new ArrayList<NettoBarnetilsynPeriodeCore>();
    for (var nettoBarnetilsynPeriodeCore : nettoBarnetilsynResultatFraCore.getResultatPeriodeListe()) {
      for (var resultatBeregning : nettoBarnetilsynPeriodeCore.getResultatListe()) {
        if (soknadsbarnPersonId == resultatBeregning.getSoknadsbarnPersonId()) {
          nettoBarnetilsynPeriodeCoreListe.add(new NettoBarnetilsynPeriodeCore("",
              nettoBarnetilsynPeriodeCore.getPeriode(),
              resultatBeregning.getBelop()));
        }
      }
    }

    // Løp gjennom ForpleiningUtgiftPeriodeListe og bygg opp ny liste til core med riktig PersonId
    var forpleiningUtgiftPeriodeCoreListe =
        beregnTotalBarnebidragGrunnlag.getBeregnBMUnderholdskostnadGrunnlag().getForpleiningUtgiftPeriodeListe()
            .stream()
            .filter(fUPeriode -> fUPeriode.getForpleiningUtgiftSoknadsbarnPersonId().equals(soknadsbarnPersonId))
            .map(fUPeriode -> new ForpleiningUtgiftPeriodeCore("",
                new PeriodeCore(fUPeriode.getForpleiningUtgiftDatoFraTil().getPeriodeDatoFra(),
                    fUPeriode.getForpleiningUtgiftDatoFraTil().getPeriodeDatoTil()),
                fUPeriode.getForpleiningUtgiftBelop()))
            .collect(toList());

    // Hent aktuelle sjabloner
    var sjablonPeriodeCoreListe = new ArrayList<SjablonPeriodeCore>();
    sjablonPeriodeCoreListe.addAll(mapSjablonSjablontall(sjablonListe.getSjablonSjablontallResponse(), UNDERHOLDSKOSTNAD,
        beregnTotalBarnebidragGrunnlag, sjablontallMap));
    sjablonPeriodeCoreListe
        .addAll(mapSjablonForbruksutgifter(sjablonListe.getSjablonForbruksutgifterResponse(), beregnTotalBarnebidragGrunnlag));
    sjablonPeriodeCoreListe
        .addAll(mapSjablonBarnetilsyn(sjablonListe.getSjablonBarnetilsynResponse(), beregnTotalBarnebidragGrunnlag));

    // Bygg core-objekt
    return new BeregnUnderholdskostnadGrunnlagCore(
        beregnTotalBarnebidragGrunnlag.getBeregnDatoFra(),
        beregnTotalBarnebidragGrunnlag.getBeregnDatoTil(),
        new no.nav.bidrag.beregn.underholdskostnad.dto.SoknadsbarnCore(
            "",
            soknadsbarnPersonId,
            Optional.ofNullable(SoknadsbarnUtil.hentFodselsdatoForId(soknadsbarnPersonId, soknadsbarnMap))
                .orElseThrow(() -> new UgyldigInputException(
                    "Søknadsbarn med id " + soknadsbarnPersonId + "har ingen korresponderende fødselsdato i soknadsbarnListe"))),
        barnetilsynMedStonadPeriodeCoreListe,
        nettoBarnetilsynPeriodeCoreListe,
        forpleiningUtgiftPeriodeCoreListe,
        sjablonPeriodeCoreListe
    );
  }

  // Bygger grunnlag til core for beregning av BPs andel av underholdskostnad
  private BeregnBPsAndelUnderholdskostnadGrunnlagCore byggBPAndelUnderholdskostnadGrunnlagTilCore(
      BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag, Integer soknadsbarnPersonId, Map<String, SjablonTallNavn> sjablontallMap,
      BeregnetUnderholdskostnadResultatCore underholdskostnadResultatFraCore, SjablonListe sjablonListe) {

    // Løp gjennom output fra beregning av underholdskostnad og bygg opp ny input-liste til core
    var underholdskostnadPeriodeCoreListe =
        underholdskostnadResultatFraCore.getResultatPeriodeListe()
            .stream()
            .filter(resultat -> resultat.getSoknadsbarnPersonId() == soknadsbarnPersonId)
            .map(resultat -> new UnderholdskostnadPeriodeCore("",
                new PeriodeCore(resultat.getPeriode().getDatoFom(),
                    resultat.getPeriode().getDatoTil()),
                resultat.getResultat().getBelop()))
            .collect(toList());

    // Bygg liste over BPs inntekter til core
    var inntektBPPeriodeCoreListe =
        beregnTotalBarnebidragGrunnlag.getInntektBPBMGrunnlag().getInntektBPPeriodeListe()
            .stream()
            .map(inntektBPPeriode -> new InntektPeriodeCore("",
                new PeriodeCore(inntektBPPeriode.getInntektDatoFraTil().getPeriodeDatoFra(),
                    inntektBPPeriode.getInntektDatoFraTil().getPeriodeDatoTil()),
                inntektBPPeriode.getInntektType(), inntektBPPeriode.getInntektBelop(), false, false))
            .collect(toList());

    // Bygg liste over BMs inntekter til core. En inntekt kan være knyttet til et bestemt søknadsbarn eller gjelde for alle søknadsbarn (null-verdi).
    // Filtrerer derfor vekk inntekter som er knyttet til andre søknadsbarn.
    var inntektBMPeriodeCoreListe =
        beregnTotalBarnebidragGrunnlag.getInntektBPBMGrunnlag().getInntektBMPeriodeListe()
            .stream()
            .filter(inntektBMPeriode ->
                (inntektBMPeriode.getSoknadsbarnPersonId() == null || inntektBMPeriode.getSoknadsbarnPersonId().equals(soknadsbarnPersonId)))
            .map(inntektBMPeriode -> new InntektPeriodeCore("",
                new PeriodeCore(inntektBMPeriode.getInntektDatoFraTil().getPeriodeDatoFra(),
                    inntektBMPeriode.getInntektDatoFraTil().getPeriodeDatoTil()),
                inntektBMPeriode.getInntektType(), inntektBMPeriode.getInntektBelop(), inntektBMPeriode.getDeltFordel(),
                inntektBMPeriode.getSkatteklasse2()))
            .collect(toList());

    // Bygg liste over SBs inntekter til core
    var soknadsbarn =
        beregnTotalBarnebidragGrunnlag.getSoknadsbarnGrunnlag().getSoknadsbarnListe()
            .stream()
            .filter(sb -> sb.getSoknadsbarnPersonId().equals(soknadsbarnPersonId))
            .findFirst();

    List<InntektPeriodeCore> inntektSBPeriodeCoreListe;
    if (soknadsbarn.isPresent()) {
      inntektSBPeriodeCoreListe = soknadsbarn.get().getInntektPeriodeListe()
          .stream()
          .map(inntektSBPeriode -> new InntektPeriodeCore("",
              new PeriodeCore(inntektSBPeriode.getInntektDatoFraTil().getPeriodeDatoFra(),
                  inntektSBPeriode.getInntektDatoFraTil().getPeriodeDatoTil()),
              inntektSBPeriode.getInntektType(), inntektSBPeriode.getInntektBelop(), false, false))
          .collect(toList());
    } else {
      throw new UgyldigInputException("Fant ikke søknadsbarn med id " + soknadsbarnPersonId + " i soknadsbarnListe");
    }

    // Hent aktuelle sjabloner
    var sjablonPeriodeCoreListe = new ArrayList<>(
        mapSjablonSjablontall(sjablonListe.getSjablonSjablontallResponse(), BP_ANDEL_UNDERHOLDSKOSTNAD, beregnTotalBarnebidragGrunnlag,
            sjablontallMap));

    // Bygg core-objekt
    return new BeregnBPsAndelUnderholdskostnadGrunnlagCore(
        beregnTotalBarnebidragGrunnlag.getBeregnDatoFra(),
        beregnTotalBarnebidragGrunnlag.getBeregnDatoTil(),
        soknadsbarnPersonId,
        underholdskostnadPeriodeCoreListe,
        inntektBPPeriodeCoreListe,
        inntektBMPeriodeCoreListe,
        inntektSBPeriodeCoreListe,
        sjablonPeriodeCoreListe
    );
  }

  // Bygger grunnlag til core for beregning av samværsfradrag
  private BeregnSamvaersfradragGrunnlagCore byggSamvaersfradragGrunnlagTilCore(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag,
      Integer soknadsbarnPersonId, Map<Integer, LocalDate> soknadsbarnMap, SjablonListe sjablonListe) {

    // Løp gjennom SamværsklassePeriodeListe og bygg opp ny liste til core med riktig PersonId
    var samvaersklassePeriodeCoreListe =
        beregnTotalBarnebidragGrunnlag.getBeregnBPSamvaersfradragGrunnlag().getSamvaersklassePeriodeListe()
            .stream()
            .filter(samvaersklassePeriode -> samvaersklassePeriode.getSamvaersklasseSoknadsbarnPersonId().equals(soknadsbarnPersonId))
            .map(samvaersklassePeriode -> new SamvaersklassePeriodeCore("",
                new PeriodeCore(samvaersklassePeriode.getSamvaersklasseDatoFraTil().getPeriodeDatoFra(),
                    samvaersklassePeriode.getSamvaersklasseDatoFraTil().getPeriodeDatoTil()),
                samvaersklassePeriode.getSamvaersklasseId()))
            .collect(toList());

    // Hent aktuelle sjabloner
    var sjablonPeriodeCoreListe = new ArrayList<>(
        mapSjablonSamvaersfradrag(sjablonListe.getSjablonSamvaersfradragResponse(), beregnTotalBarnebidragGrunnlag));

    // Bygg core-objekt
    return new BeregnSamvaersfradragGrunnlagCore(
        beregnTotalBarnebidragGrunnlag.getBeregnDatoFra(),
        beregnTotalBarnebidragGrunnlag.getBeregnDatoTil(),
        new no.nav.bidrag.beregn.samvaersfradrag.dto.SoknadsbarnCore(
            "",
            soknadsbarnPersonId,
            Optional.ofNullable(SoknadsbarnUtil.hentFodselsdatoForId(soknadsbarnPersonId, soknadsbarnMap))
                .orElseThrow(() -> new UgyldigInputException(
                    "Søknadsbarn med id " + soknadsbarnPersonId + "har ingen korresponderende fødselsdato i soknadsbarnListe"))),
        samvaersklassePeriodeCoreListe,
        sjablonPeriodeCoreListe
    );
  }

  // Bygger grunnlag til core for beregning av kostnadsberegnet bidrag
  private BeregnKostnadsberegnetBidragGrunnlagCore byggKostnadsberegnetBidragGrunnlagTilCore(
      BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag, Integer soknadsbarnPersonId,
      BeregnetUnderholdskostnadResultatCore underholdskostnadResultatFraCore,
      BeregnetBPsAndelUnderholdskostnadResultatCore bpAndelUnderholdskostnadResultatFraCore,
      BeregnetSamvaersfradragResultatCore samvaersfradragResultatFraCore) {

    // Løp gjennom output fra beregning av underholdskostnad og bygg opp ny input-liste til core
    var underholdskostnadPeriodeCoreListe =
        underholdskostnadResultatFraCore.getResultatPeriodeListe()
            .stream()
            .filter(resultat -> resultat.getSoknadsbarnPersonId() == soknadsbarnPersonId)
            .map(resultat -> new no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.UnderholdskostnadPeriodeCore("",
                new PeriodeCore(resultat.getPeriode().getDatoFom(),
                    resultat.getPeriode().getDatoTil()),
                resultat.getResultat().getBelop()))
            .collect(toList());

    // Løp gjennom output fra beregning av BPs andel av underholdskostnad og bygg opp ny input-liste til core
    var bpAndelUnderholdskostnadPeriodeCoreListe =
        bpAndelUnderholdskostnadResultatFraCore.getResultatPeriodeListe()
            .stream()
            .filter(resultat -> resultat.getSoknadsbarnPersonId() == soknadsbarnPersonId)
            .map(resultat -> new BPsAndelUnderholdskostnadPeriodeCore("",
                new PeriodeCore(resultat.getPeriode().getDatoFom(),
                    resultat.getPeriode().getDatoTil()),
                resultat.getResultat().getAndelProsent()))
            .collect(toList());

    // Løp gjennom output fra beregning av samværsfradrag og bygg opp ny input-liste til core
    var samvaersfradragPeriodeCoreListe =
        samvaersfradragResultatFraCore.getResultatPeriodeListe()
            .stream()
            .filter(resultat -> resultat.getSoknadsbarnPersonId() == soknadsbarnPersonId)
            .map(resultat -> new SamvaersfradragPeriodeCore("",
                new PeriodeCore(resultat.getPeriode().getDatoFom(),
                    resultat.getPeriode().getDatoTil()),
                resultat.getResultat().getBelop()))
            .collect(toList());

    // Bygg core-objekt
    return new BeregnKostnadsberegnetBidragGrunnlagCore(
        beregnTotalBarnebidragGrunnlag.getBeregnDatoFra(),
        beregnTotalBarnebidragGrunnlag.getBeregnDatoTil(),
        soknadsbarnPersonId,
        underholdskostnadPeriodeCoreListe,
        bpAndelUnderholdskostnadPeriodeCoreListe,
        samvaersfradragPeriodeCoreListe
    );
  }

  // Bygger grunnlag til core for beregning av barnebidrag
  private BeregnBarnebidragGrunnlagCore byggBarnebidragGrunnlagTilCore(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag,
      Map<String, SjablonTallNavn> sjablontallMap, BeregnetBidragsevneResultatCore bidragsevneResultatFraCore,
      BeregnetBPsAndelUnderholdskostnadResultatCore bpAndelUnderholdskostnadResultatFraCore,
      BeregnetSamvaersfradragResultatCore samvaersfradragResultatFraCore, SjablonListe sjablonListe) {

    // Løp gjennom output fra beregning av bidragsevne og bygg opp ny input-liste til core
    var bidragsevnePeriodeCoreListe =
        bidragsevneResultatFraCore.getResultatPeriodeListe()
            .stream()
            .map(resultat -> new BidragsevnePeriodeCore("",
                new PeriodeCore(resultat.getPeriode().getDatoFom(),
                    resultat.getPeriode().getDatoTil()),
                resultat.getResultat().getBelop(), resultat.getResultat().getInntekt25Prosent()))
            .collect(toList());

    // Løp gjennom output fra beregning av BPs andel underholdskostnad og bygg opp ny input-liste til core
    var bpAndelUnderholdskostnadPeriodeCoreListe =
        bpAndelUnderholdskostnadResultatFraCore.getResultatPeriodeListe()
            .stream()
            .map(resultat -> new no.nav.bidrag.beregn.barnebidrag.dto.BPsAndelUnderholdskostnadPeriodeCore("",
                resultat.getSoknadsbarnPersonId(),
                new PeriodeCore(resultat.getPeriode().getDatoFom(),
                    resultat.getPeriode().getDatoTil()),
                resultat.getResultat().getAndelProsent(), resultat.getResultat().getAndelBelop(),
                resultat.getResultat().getBarnetErSelvforsorget()))
            .collect(toList());

    // Løp gjennom output fra beregning av samværsfradrag og bygg opp ny input-liste til core
    var samvaersfradragPeriodeCoreListe =
        samvaersfradragResultatFraCore.getResultatPeriodeListe()
            .stream()
            .map(resultat -> new no.nav.bidrag.beregn.barnebidrag.dto.SamvaersfradragPeriodeCore("",
                resultat.getSoknadsbarnPersonId(),
                new PeriodeCore(resultat.getPeriode().getDatoFom(),
                    resultat.getPeriode().getDatoTil()),
                resultat.getResultat().getBelop()))
            .collect(toList());

    // Hent aktuelle sjabloner
    var sjablonPeriodeCoreListe = mapSjablonSjablontall(sjablonListe.getSjablonSjablontallResponse(), BARNEBIDRAG,
        beregnTotalBarnebidragGrunnlag, sjablontallMap);

    // Bygg grunnlag for beregning av barnebidrag. Her gjøres også kontroll av inputdata
    return beregnTotalBarnebidragGrunnlag.barnebidragTilCore(bidragsevnePeriodeCoreListe, bpAndelUnderholdskostnadPeriodeCoreListe,
        samvaersfradragPeriodeCoreListe, sjablonPeriodeCoreListe);
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

  // Kaller core for beregning av kostnadsberegnet bidrag
  private List<no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.ResultatPeriodeCore> beregnKostnadsberegnetBidrag(
      BeregnKostnadsberegnetBidragGrunnlagCore kostnadsberegnetBidragGrunnlagTilCore) {

    // Kaller core-modulen for beregning av kostnadsberegnet bidrag
    LOGGER.debug("Kostnadsberegnet bidrag - grunnlag for beregning: {}", kostnadsberegnetBidragGrunnlagTilCore);
    var kostnadsberegnetBidragResultatFraCore = kostnadsberegnetBidragCore.beregnKostnadsberegnetBidrag(kostnadsberegnetBidragGrunnlagTilCore);

    if (!kostnadsberegnetBidragResultatFraCore.getAvvikListe().isEmpty()) {
      LOGGER.error("Ugyldig input ved beregning av kostnadsberegnet bidrag. Følgende avvik ble funnet: " + System.lineSeparator()
          + kostnadsberegnetBidragResultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst)
          .collect(Collectors.joining(System.lineSeparator())));
      LOGGER.info("Kostnadsberegnet bidrag - grunnlag for beregning: " + System.lineSeparator()
          + "beregnDatoFra= " + kostnadsberegnetBidragGrunnlagTilCore.getBeregnDatoFra() + System.lineSeparator()
          + "beregnDatoTil= " + kostnadsberegnetBidragGrunnlagTilCore.getBeregnDatoTil() + System.lineSeparator()
          + "soknadsbarnPersonId= " + kostnadsberegnetBidragGrunnlagTilCore.getSoknadsbarnPersonId() + System.lineSeparator()
          + "underholdskostnadPeriodeListe= " + kostnadsberegnetBidragGrunnlagTilCore.getUnderholdskostnadPeriodeListe() + System.lineSeparator()
          + "bPsAndelUnderholdskostnadPeriodeListe= " + kostnadsberegnetBidragGrunnlagTilCore.getBPsAndelUnderholdskostnadPeriodeListe() +
          System.lineSeparator()
          + "samvaersfradragPeriodeListe= " + kostnadsberegnetBidragGrunnlagTilCore.getSamvaersfradragPeriodeListe() + System.lineSeparator());
      throw new UgyldigInputException("Ugyldig input ved beregning av kostnadsberegnet bidrag. Følgende avvik ble funnet: "
          + kostnadsberegnetBidragResultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining("; ")));
    }

    LOGGER.debug("Kostnadsberegnet bidrag - resultat av beregning: {}", kostnadsberegnetBidragResultatFraCore.getResultatPeriodeListe());
    return kostnadsberegnetBidragResultatFraCore.getResultatPeriodeListe();
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
    var sjablonSjablontallListe = Optional.ofNullable(sjablonConsumer.hentSjablonSjablontall().getResponseEntity().getBody()).orElse(emptyList());
    LOGGER.debug("Antall sjabloner hentet av type Sjablontall: {}", sjablonSjablontallListe.size());

    // Henter sjabloner for forbruksutgifter
    var sjablonForbruksutgifterListe = Optional.ofNullable(sjablonConsumer.hentSjablonForbruksutgifter().getResponseEntity().getBody())
        .orElse(emptyList());
    LOGGER.debug("Antall sjabloner hentet av type Bidragsevne: {}", sjablonForbruksutgifterListe.size());

    // Henter sjabloner for maks tilsyn
    var sjablonMaksTilsynListe = Optional.ofNullable(sjablonConsumer.hentSjablonMaksTilsyn().getResponseEntity().getBody()).orElse(emptyList());
    LOGGER.debug("Antall sjabloner hentet av type Maks tilsyn: {}", sjablonMaksTilsynListe.size());

    // Henter sjabloner for maks bidrag
    var sjablonMaksFradragListe = Optional.ofNullable(sjablonConsumer.hentSjablonMaksFradrag().getResponseEntity().getBody()).orElse(emptyList());
    LOGGER.debug("Antall sjabloner hentet av type Maks fradrag: {}", sjablonMaksFradragListe.size());

    // Henter sjabloner for samværsfradrag
    var sjablonSamvaersfradragListe = Optional.ofNullable(sjablonConsumer.hentSjablonSamvaersfradrag().getResponseEntity().getBody())
        .orElse(emptyList());
    LOGGER.debug("Antall sjabloner hentet av type Samværsfradrag: {}", sjablonSamvaersfradragListe.size());

    // Henter sjabloner for bidragsevne
    var sjablonBidragsevneListe = Optional.ofNullable(sjablonConsumer.hentSjablonBidragsevne().getResponseEntity().getBody()).orElse(emptyList());
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

  // Mapper sjabloner av typen sjablontall
  // Filtrerer bort de sjablonene som ikke brukes i den aktuelle delberegningen og de som ikke er innenfor intervallet beregnDatoFra-beregnDatoTil
  private List<SjablonPeriodeCore> mapSjablonSjablontall(List<Sjablontall> sjablonSjablontallListe, String delberegning,
      BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag, Map<String, SjablonTallNavn> sjablontallMap) {

    var beregnDatoFra = beregnTotalBarnebidragGrunnlag.getBeregnDatoFra();
    var beregnDatoTil = beregnTotalBarnebidragGrunnlag.getBeregnDatoTil();

    return sjablonSjablontallListe
        .stream()
        .filter(sjablon -> (sjablon.getDatoFom().isBefore(beregnDatoTil) && (!(sjablon.getDatoTom().isBefore(beregnDatoFra)))))
        .filter(sjablon -> filtrerSjablonTall(sjablontallMap.getOrDefault(sjablon.getTypeSjablon(), SjablonTallNavn.DUMMY), delberegning))
        .map(sjablon -> new SjablonPeriodeCore(
            new PeriodeCore(sjablon.getDatoFom(), sjablon.getDatoTom()),
            sjablontallMap.getOrDefault(sjablon.getTypeSjablon(), SjablonTallNavn.DUMMY).getNavn(),
            emptyList(),
            singletonList(new SjablonInnholdCore(SjablonInnholdNavn.SJABLON_VERDI.getNavn(), sjablon.getVerdi()))))
        .collect(toList());
  }

  // Mapper sjabloner av typen forbruksutgifter
  // Filtrerer bort de sjablonene som ikke er innenfor intervallet beregnDatoFra-beregnDatoTil
  private List<SjablonPeriodeCore> mapSjablonForbruksutgifter(List<Forbruksutgifter> sjablonForbruksutgifterListe,
      BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag) {

    var beregnDatoFra = beregnTotalBarnebidragGrunnlag.getBeregnDatoFra();
    var beregnDatoTil = beregnTotalBarnebidragGrunnlag.getBeregnDatoTil();

    return sjablonForbruksutgifterListe
        .stream()
        .filter(sjablon -> (sjablon.getDatoFom().isBefore(beregnDatoTil) && (!(sjablon.getDatoTom().isBefore(beregnDatoFra)))))
        .map(sjablon -> new SjablonPeriodeCore(
            new PeriodeCore(sjablon.getDatoFom(), sjablon.getDatoTom()),
            SjablonNavn.FORBRUKSUTGIFTER.getNavn(),
            singletonList(new SjablonNokkelCore(SjablonNokkelNavn.ALDER_TOM.getNavn(), String.valueOf(sjablon.getAlderTom()))),
            singletonList(new SjablonInnholdCore(SjablonInnholdNavn.FORBRUK_TOTAL_BELOP.getNavn(), sjablon.getBelopForbrukTot()))))
        .collect(toList());
  }

  // Mapper sjabloner av typen maks tilsyn
  // Filtrerer bort de sjablonene som ikke er innenfor intervallet beregnDatoFra-beregnDatoTil
  private List<SjablonPeriodeCore> mapSjablonMaksTilsyn(List<MaksTilsyn> sjablonMaksTilsynListe,
      BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag) {

    var beregnDatoFra = beregnTotalBarnebidragGrunnlag.getBeregnDatoFra();
    var beregnDatoTil = beregnTotalBarnebidragGrunnlag.getBeregnDatoTil();

    return sjablonMaksTilsynListe
        .stream()
        .filter(sjablon -> (sjablon.getDatoFom().isBefore(beregnDatoTil) && (!(sjablon.getDatoTom().isBefore(beregnDatoFra)))))
        .map(sjablon -> new SjablonPeriodeCore(
            new PeriodeCore(sjablon.getDatoFom(), sjablon.getDatoTom()),
            SjablonNavn.MAKS_TILSYN.getNavn(),
            singletonList(new SjablonNokkelCore(SjablonNokkelNavn.ANTALL_BARN_TOM.getNavn(), String.valueOf(sjablon.getAntBarnTom()))),
            singletonList(new SjablonInnholdCore(SjablonInnholdNavn.MAKS_TILSYN_BELOP.getNavn(), sjablon.getMaksBelopTilsyn()))))
        .collect(toList());
  }

  // Mapper sjabloner av typen maks fradrag
  // Filtrerer bort de sjablonene som ikke er innenfor intervallet beregnDatoFra-beregnDatoTil
  private List<SjablonPeriodeCore> mapSjablonMaksFradrag(List<MaksFradrag> sjablonMaksFradragListe,
      BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag) {

    var beregnDatoFra = beregnTotalBarnebidragGrunnlag.getBeregnDatoFra();
    var beregnDatoTil = beregnTotalBarnebidragGrunnlag.getBeregnDatoTil();

    return sjablonMaksFradragListe
        .stream()
        .filter(sjablon -> (sjablon.getDatoFom().isBefore(beregnDatoTil) && (!(sjablon.getDatoTom().isBefore(beregnDatoFra)))))
        .map(sjablon -> new SjablonPeriodeCore(
            new PeriodeCore(sjablon.getDatoFom(), sjablon.getDatoTom()),
            SjablonNavn.MAKS_FRADRAG.getNavn(),
            singletonList(new SjablonNokkelCore(SjablonNokkelNavn.ANTALL_BARN_TOM.getNavn(), String.valueOf(sjablon.getAntBarnTom()))),
            singletonList(new SjablonInnholdCore(SjablonInnholdNavn.MAKS_FRADRAG_BELOP.getNavn(), sjablon.getMaksBelopFradrag()))))
        .collect(toList());
  }

  // Mapper sjabloner av typen samværsfradrag
  // Filtrerer bort de sjablonene som ikke er innenfor intervallet beregnDatoFra-beregnDatoTil
  private List<SjablonPeriodeCore> mapSjablonSamvaersfradrag(List<Samvaersfradrag> sjablonSamvaersfradragListe,
      BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag) {

    var beregnDatoFra = beregnTotalBarnebidragGrunnlag.getBeregnDatoFra();
    var beregnDatoTil = beregnTotalBarnebidragGrunnlag.getBeregnDatoTil();

    return sjablonSamvaersfradragListe
        .stream()
        .filter(sjablon -> (sjablon.getDatoFom().isBefore(beregnDatoTil) && (!(sjablon.getDatoTom().isBefore(beregnDatoFra)))))
        .map(sjablon -> new SjablonPeriodeCore(
            new PeriodeCore(sjablon.getDatoFom(), sjablon.getDatoTom()),
            SjablonNavn.SAMVAERSFRADRAG.getNavn(),
            asList(new SjablonNokkelCore(SjablonNokkelNavn.SAMVAERSKLASSE.getNavn(), sjablon.getSamvaersklasse()),
                new SjablonNokkelCore(SjablonNokkelNavn.ALDER_TOM.getNavn(), String.valueOf(sjablon.getAlderTom()))),
            asList(new SjablonInnholdCore(SjablonInnholdNavn.ANTALL_DAGER_TOM.getNavn(), BigDecimal.valueOf(sjablon.getAntDagerTom())),
                new SjablonInnholdCore(SjablonInnholdNavn.ANTALL_NETTER_TOM.getNavn(), BigDecimal.valueOf(sjablon.getAntNetterTom())),
                new SjablonInnholdCore(SjablonInnholdNavn.FRADRAG_BELOP.getNavn(), sjablon.getBelopFradrag()))))
        .collect(toList());
  }

  // Mapper sjabloner av typen bidragsevne
  // Filtrerer bort de sjablonene som ikke er innenfor intervallet beregnDatoFra-beregnDatoTil
  private List<SjablonPeriodeCore> mapSjablonBidragsevne(List<Bidragsevne> sjablonBidragsevneListe,
      BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag) {

    var beregnDatoFra = beregnTotalBarnebidragGrunnlag.getBeregnDatoFra();
    var beregnDatoTil = beregnTotalBarnebidragGrunnlag.getBeregnDatoTil();

    return sjablonBidragsevneListe
        .stream()
        .filter(sjablon -> (sjablon.getDatoFom().isBefore(beregnDatoTil) && (!(sjablon.getDatoTom().isBefore(beregnDatoFra)))))
        .map(sjablon -> new SjablonPeriodeCore(
            new PeriodeCore(sjablon.getDatoFom(), sjablon.getDatoTom()),
            SjablonNavn.BIDRAGSEVNE.getNavn(),
            singletonList(new SjablonNokkelCore(SjablonNokkelNavn.BOSTATUS.getNavn(), sjablon.getBostatus())),
            asList(new SjablonInnholdCore(SjablonInnholdNavn.BOUTGIFT_BELOP.getNavn(), sjablon.getBelopBoutgift()),
                new SjablonInnholdCore(SjablonInnholdNavn.UNDERHOLD_BELOP.getNavn(), sjablon.getBelopUnderhold()))))
        .collect(toList());
  }

  // Mapper sjabloner av typen trinnvis skattesats
  // Filtrerer bort de sjablonene som ikke er innenfor intervallet beregnDatoFra-beregnDatoTil
  private List<SjablonPeriodeCore> mapSjablonTrinnvisSkattesats(List<TrinnvisSkattesats> sjablonTrinnvisSkattesatsListe,
      BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag) {

    var beregnDatoFra = beregnTotalBarnebidragGrunnlag.getBeregnDatoFra();
    var beregnDatoTil = beregnTotalBarnebidragGrunnlag.getBeregnDatoTil();

    return sjablonTrinnvisSkattesatsListe
        .stream()
        .filter(sjablon -> (sjablon.getDatoFom().isBefore(beregnDatoTil) && (!(sjablon.getDatoTom().isBefore(beregnDatoFra)))))
        .map(sjablon -> new SjablonPeriodeCore(
            new PeriodeCore(sjablon.getDatoFom(), sjablon.getDatoTom()),
            SjablonNavn.TRINNVIS_SKATTESATS.getNavn(),
            emptyList(),
            asList(new SjablonInnholdCore(SjablonInnholdNavn.INNTEKTSGRENSE_BELOP.getNavn(), sjablon.getInntektgrense()),
                new SjablonInnholdCore(SjablonInnholdNavn.SKATTESATS_PROSENT.getNavn(), sjablon.getSats()))))
        .collect(toList());
  }

  // Mapper sjabloner av typen barnetilsyn
  // Filtrerer bort de sjablonene som ikke er innenfor intervallet beregnDatoFra-beregnDatoTil
  private List<SjablonPeriodeCore> mapSjablonBarnetilsyn(List<Barnetilsyn> sjablonBarnetilsynListe,
      BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag) {

    var beregnDatoFra = beregnTotalBarnebidragGrunnlag.getBeregnDatoFra();
    var beregnDatoTil = beregnTotalBarnebidragGrunnlag.getBeregnDatoTil();

    return sjablonBarnetilsynListe
        .stream()
        .filter(sjablon -> (sjablon.getDatoFom().isBefore(beregnDatoTil) && (!(sjablon.getDatoTom().isBefore(beregnDatoFra)))))
        .map(sjablon -> new SjablonPeriodeCore(
            new PeriodeCore(sjablon.getDatoFom(), sjablon.getDatoTom()),
            SjablonNavn.BARNETILSYN.getNavn(),
            asList(new SjablonNokkelCore(SjablonNokkelNavn.STONAD_TYPE.getNavn(), sjablon.getTypeStonad()),
                new SjablonNokkelCore(SjablonNokkelNavn.TILSYN_TYPE.getNavn(), sjablon.getTypeTilsyn())),
            singletonList(new SjablonInnholdCore(SjablonInnholdNavn.BARNETILSYN_BELOP.getNavn(), sjablon.getBelopBarneTilsyn()))))
        .collect(toList());
  }

  // Sjekker om en type SjablonTall er i bruk for en delberegning
  private boolean filtrerSjablonTall(SjablonTallNavn sjablonTallNavn, String delberegning) {

    return switch (delberegning) {
      case BIDRAGSEVNE -> sjablonTallNavn.getBidragsevne();
      case NETTO_BARNETILSYN -> sjablonTallNavn.getNettoBarnetilsyn();
      case UNDERHOLDSKOSTNAD -> sjablonTallNavn.getUnderholdskostnad();
      case BP_ANDEL_UNDERHOLDSKOSTNAD -> sjablonTallNavn.getBpAndelUnderholdskostnad();
      case BARNEBIDRAG -> sjablonTallNavn.getBarnebidrag();
      default -> false;
    };
  }
}
