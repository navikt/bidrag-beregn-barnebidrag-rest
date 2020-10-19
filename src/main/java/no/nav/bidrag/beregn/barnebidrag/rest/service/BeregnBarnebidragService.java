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
import no.nav.bidrag.beregn.barnebidrag.BarnebidragCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BeregnBarnebidragGrunnlagCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BeregnBarnebidragResultatCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BidragsevnePeriodeCore;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Bidragsevne;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Forbruksutgifter;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.MaksFradrag;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.MaksTilsyn;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Samvaersfradrag;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonConsumer;
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
import no.nav.bidrag.beregn.bidragsevne.dto.BeregnBidragsevneResultatCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.BPsAndelUnderholdskostnadCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnBPsAndelUnderholdskostnadGrunnlagCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnBPsAndelUnderholdskostnadResultatCore;
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
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.BeregnKostnadsberegnetBidragResultatCore;
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.SamvaersfradragPeriodeCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.NettoBarnetilsynCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnNettoBarnetilsynGrunnlagCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnNettoBarnetilsynResultatCore;
import no.nav.bidrag.beregn.samvaersfradrag.SamvaersfradragCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnSamvaersfradragGrunnlagCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnSamvaersfradragResultatCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.SamvaersklassePeriodeCore;
import no.nav.bidrag.beregn.underholdskostnad.UnderholdskostnadCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BarnetilsynMedStonadPeriodeCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadGrunnlagCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadResultatCore;
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
  private static final String UNDERHOLDSKOSTAND = "Underholdskostnad";
  private static final String BP_ANDEL_UNDERHOLDSKOSTAND = "BPsAndelUnderholdskostnad";
  private static final String BARNEBIDRAG = "Barnebidrag";

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

  private BeregnBidragsevneGrunnlagCore bidragsevneGrunnlagTilCore;
  private BeregnNettoBarnetilsynGrunnlagCore nettoBarnetilsynGrunnlagTilCore;
  private List<BeregnUnderholdskostnadGrunnlagCore> underholdskostnadGrunnlagTilCoreListe;
  private List<BeregnBPsAndelUnderholdskostnadGrunnlagCore> bPAndelUnderholdskostnadGrunnlagTilCoreListe;
  private List<BeregnSamvaersfradragGrunnlagCore> samvaersfradragGrunnlagTilCoreListe;
  private List<BeregnKostnadsberegnetBidragGrunnlagCore> kostnadsberegnetBidragGrunnlagTilCoreListe;
  private BeregnBarnebidragGrunnlagCore barnebidragGrunnlagTilCore;

  private BeregnBidragsevneResultatCore bidragsevneResultatFraCore;
  private BeregnNettoBarnetilsynResultatCore nettoBarnetilsynResultatFraCore;
  private BeregnUnderholdskostnadResultatCore underholdskostnadResultatFraCore;
  private BeregnBPsAndelUnderholdskostnadResultatCore bpAndelUnderholdskostnadResultatFraCore;
  private BeregnSamvaersfradragResultatCore samvaersfradragResultatFraCore;
  private BeregnKostnadsberegnetBidragResultatCore kostnadsberegnetBidragResultatFraCore;
  private BeregnBarnebidragResultatCore barnebidragResultatFraCore;

  private LocalDate beregnDatoFra;
  private LocalDate beregnDatoTil;

  private Map<Integer, LocalDate> soknadsbarnMap;
  private final Map<String, SjablonTallNavn> sjablontallMap = new HashMap<>();

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

    // Initierer variable
    initier(beregnTotalBarnebidragGrunnlag);

    // Henter sjabloner
    hentSjabloner();

    // Bygger grunnlag til core og utfører delberegninger
    utfoerDelberegninger(beregnTotalBarnebidragGrunnlag);

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

  //==================================================================================================================================================

  // Initierer variable
  private void initier(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag) {
    // Initier beregnDatoFra og beregnDatoTil
    beregnDatoFra = beregnTotalBarnebidragGrunnlag.getBeregnDatoFra();
    beregnDatoTil = beregnTotalBarnebidragGrunnlag.getBeregnDatoTil();

    // Lager en map for sjablontall (id og navn)
    for (SjablonTallNavn sjablonTallNavn : SjablonTallNavn.values()) {
      sjablontallMap.put(sjablonTallNavn.getId(), sjablonTallNavn);
    }

    // Lager en map for søknadsbarn id og fødselsdato
    soknadsbarnMap = beregnTotalBarnebidragGrunnlag.getSoknadsbarnGrunnlag().getSoknadsbarnListe().stream()
        .collect(Collectors.toMap(Soknadsbarn::getSoknadsbarnPersonId, Soknadsbarn::getSoknadsbarnFodselsdato));
  }

  //==================================================================================================================================================

  // Bygger grunnlag til core og kaller delberegninger
  private void utfoerDelberegninger(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag) {

    // ++ Bidragsevne
    byggBidragsevneGrunnlagTilCore(beregnTotalBarnebidragGrunnlag);
    beregnBidragsevne();

    // ++ Netto barnetilsyn
    byggNettoBarnetilsynGrunnlagTilCore(beregnTotalBarnebidragGrunnlag);
    beregnNettoBarnetilsyn();

    // ++ Underholdskostnad
    utfoerDelberegningUnderholdskostnad(beregnTotalBarnebidragGrunnlag);

    // ++ BPs andel av underholdskostnad
    utfoerDelberegningBPAndelUnderholdskostnad(beregnTotalBarnebidragGrunnlag);

    // ++ Samværsfradrag
    utfoerDelberegningSamvaersfradrag(beregnTotalBarnebidragGrunnlag);

    // ++ Kostnadsberegnet bidrag
    utfoerDelberegningKostnadsberegnetBidrag(beregnTotalBarnebidragGrunnlag);

    // ++ Barnebidrag (totalberegning)
    byggBarnebidragGrunnlagTilCore(beregnTotalBarnebidragGrunnlag);
    beregnBarnebidrag();
  }

  // Delberegning underholdskostnad
  private void utfoerDelberegningUnderholdskostnad(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag) {

    // Validerer input
    beregnTotalBarnebidragGrunnlag.validerUnderholdskostnad();

    // Bygger opp liste med grunnlag for hvert søknadsbarn
    underholdskostnadGrunnlagTilCoreListe = new ArrayList<>();
    beregnTotalBarnebidragGrunnlag.getSoknadsbarnGrunnlag().getSoknadsbarnListe()
        .forEach(soknadsbarn -> underholdskostnadGrunnlagTilCoreListe
            .add(byggUnderholdskostnadGrunnlagTilCore(beregnTotalBarnebidragGrunnlag, soknadsbarn.getSoknadsbarnPersonId())));

    // Kaller beregning for hvert søknadsbarn
    underholdskostnadGrunnlagTilCoreListe
        .forEach(this::beregnUnderholdskostnad);
  }

  // Delberegning BPs andel av underholdskostnad
  private void utfoerDelberegningBPAndelUnderholdskostnad(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag) {

    // Bygger opp liste med grunnlag for hvert søknadsbarn
    bPAndelUnderholdskostnadGrunnlagTilCoreListe = new ArrayList<>();
    beregnTotalBarnebidragGrunnlag.getSoknadsbarnGrunnlag().getSoknadsbarnListe()
        .forEach(soknadsbarn -> bPAndelUnderholdskostnadGrunnlagTilCoreListe
            .add(byggBPAndelUnderholdskostnadGrunnlagTilCore(beregnTotalBarnebidragGrunnlag, soknadsbarn.getSoknadsbarnPersonId())));

    // Kaller beregning for hvert søknadsbarn
    bPAndelUnderholdskostnadGrunnlagTilCoreListe
        .forEach(this::beregnBPsAndelUnderholdskostnad);
  }

  // Delberegning samværsfradrag
  private void utfoerDelberegningSamvaersfradrag(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag) {

    // Validerer input
    beregnTotalBarnebidragGrunnlag.validerSamvaersfradrag();

    // Bygger opp liste med grunnlag for hvert søknadsbarn
    samvaersfradragGrunnlagTilCoreListe = new ArrayList<>();
    beregnTotalBarnebidragGrunnlag.getSoknadsbarnGrunnlag().getSoknadsbarnListe()
        .forEach(soknadsbarn -> samvaersfradragGrunnlagTilCoreListe
            .add(byggSamvaersfradragGrunnlagTilCore(beregnTotalBarnebidragGrunnlag, soknadsbarn.getSoknadsbarnPersonId())));

    // Kaller beregning for hvert søknadsbarn
    samvaersfradragGrunnlagTilCoreListe
        .forEach(this::beregnSamvaersfradrag);
  }

  // Delberegning kostnadsberegnet bidrag
  private void utfoerDelberegningKostnadsberegnetBidrag(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag) {

    // Bygger opp liste med grunnlag for hvert søknadsbarn
    kostnadsberegnetBidragGrunnlagTilCoreListe = new ArrayList<>();
    beregnTotalBarnebidragGrunnlag.getSoknadsbarnGrunnlag().getSoknadsbarnListe()
        .forEach(soknadsbarn -> kostnadsberegnetBidragGrunnlagTilCoreListe
            .add(byggKostnadsberegnetBidragGrunnlagTilCore(soknadsbarn.getSoknadsbarnPersonId())));

    // Kaller beregning for hvert søknadsbarn
    kostnadsberegnetBidragGrunnlagTilCoreListe
        .forEach(this::beregnKostnadsberegnetBidrag);
  }

  //==================================================================================================================================================

  // Bygger grunnlag til core for beregning av bidragsevne
  private void byggBidragsevneGrunnlagTilCore(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag) {

    // Hent aktuelle sjabloner
    var sjablonPeriodeCoreListe = new ArrayList<SjablonPeriodeCore>();
    sjablonPeriodeCoreListe.addAll(mapSjablonSjablontall(sjablonSjablontallResponse.getResponseEntity().getBody(), BIDRAGSEVNE));
    sjablonPeriodeCoreListe.addAll(mapSjablonBidragsevne(sjablonBidragsevneResponse.getResponseEntity().getBody()));
    sjablonPeriodeCoreListe.addAll(mapSjablonTrinnvisSkattesats(sjablonTrinnvisSkattesatsResponse.getResponseEntity().getBody()));

    // Bygg grunnlag for beregning av bidragsevne. Her gjøres også kontroll av inputdata
    bidragsevneGrunnlagTilCore = beregnTotalBarnebidragGrunnlag.bidragsevneTilCore(sjablonPeriodeCoreListe);
  }

  // Bygger grunnlag til core for beregning av netto barnetilsyn
  private void byggNettoBarnetilsynGrunnlagTilCore(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag) {

    // Hent aktuelle sjabloner
    var sjablonPeriodeCoreListe = new ArrayList<SjablonPeriodeCore>();
    sjablonPeriodeCoreListe.addAll(mapSjablonSjablontall(sjablonSjablontallResponse.getResponseEntity().getBody(), NETTO_BARNETILSYN));
    sjablonPeriodeCoreListe.addAll(mapSjablonMaksTilsyn(sjablonMaksTilsynResponse.getResponseEntity().getBody()));
    sjablonPeriodeCoreListe.addAll(mapSjablonMaksFradrag(sjablonMaksFradragResponse.getResponseEntity().getBody()));

    // Bygg grunnlag for beregning av netto barnetilsyn. Her gjøres også kontroll av inputdata
    nettoBarnetilsynGrunnlagTilCore = beregnTotalBarnebidragGrunnlag.nettoBarnetilsynTilCore(soknadsbarnMap, sjablonPeriodeCoreListe);
  }

  // Bygger grunnlag til core for beregning av underholdskostnad
  private BeregnUnderholdskostnadGrunnlagCore byggUnderholdskostnadGrunnlagTilCore(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag,
      Integer soknadsbarnPersonId) {

    // Løp gjennom BarnetilsynMedStonadPeriodeListe og bygg opp ny liste til core med riktig PersonId
    var barnetilsynMedStonadPeriodeCoreListe =
        beregnTotalBarnebidragGrunnlag.getBeregnBMUnderholdskostnadGrunnlag().getBarnetilsynMedStonadPeriodeListe()
            .stream()
            .filter(bMSPeriode -> bMSPeriode.getBarnetilsynMedStonadSoknadsbarnPersonId().equals(soknadsbarnPersonId))
            .map(bMSPeriode -> new BarnetilsynMedStonadPeriodeCore(
                new PeriodeCore(bMSPeriode.getBarnetilsynMedStonadDatoFraTil().getPeriodeDatoFra(),
                    bMSPeriode.getBarnetilsynMedStonadDatoFraTil().getPeriodeDatoTil()),
                bMSPeriode.getBarnetilsynMedStonadTilsynType(), bMSPeriode.getBarnetilsynMedStonadStonadType()))
            .collect(toList());

    // Løp gjennom output fra beregning av netto barnetilsyn og byggg opp ny input-liste til core
    var nettoBarnetilsynPeriodeCoreListe = new ArrayList<NettoBarnetilsynPeriodeCore>();
    for (var nettoBarnetilsynPeriodeCore : nettoBarnetilsynResultatFraCore.getResultatPeriodeListe()) {
      for (var resultatBeregning : nettoBarnetilsynPeriodeCore.getResultatBeregningListe()) {
        if (soknadsbarnPersonId == resultatBeregning.getResultatSoknadsbarnPersonId()) {
          nettoBarnetilsynPeriodeCoreListe.add(new NettoBarnetilsynPeriodeCore(
              nettoBarnetilsynPeriodeCore.getResultatDatoFraTil(),
              resultatBeregning.getResultatBelop()));
        }
      }
    }

    // Løp gjennom ForpleiningUtgiftPeriodeListe og bygg opp ny liste til core med riktig PersonId
    var forpleiningUtgiftPeriodeCoreListe =
        beregnTotalBarnebidragGrunnlag.getBeregnBMUnderholdskostnadGrunnlag().getForpleiningUtgiftPeriodeListe()
            .stream()
            .filter(fUPeriode -> fUPeriode.getForpleiningUtgiftSoknadsbarnPersonId().equals(soknadsbarnPersonId))
            .map(fUPeriode -> new ForpleiningUtgiftPeriodeCore(
                new PeriodeCore(fUPeriode.getForpleiningUtgiftDatoFraTil().getPeriodeDatoFra(),
                    fUPeriode.getForpleiningUtgiftDatoFraTil().getPeriodeDatoTil()),
                fUPeriode.getForpleiningUtgiftBelop()))
            .collect(toList());

    // Hent aktuelle sjabloner
    var sjablonPeriodeCoreListe = new ArrayList<SjablonPeriodeCore>();
    sjablonPeriodeCoreListe.addAll(mapSjablonSjablontall(sjablonSjablontallResponse.getResponseEntity().getBody(), UNDERHOLDSKOSTAND));
    sjablonPeriodeCoreListe.addAll(mapSjablonForbruksutgifter(sjablonForbruksutgifterResponse.getResponseEntity().getBody()));

    // Bygg core-objekt
    return new BeregnUnderholdskostnadGrunnlagCore(
        soknadsbarnPersonId,
        beregnDatoFra,
        beregnDatoTil,
        SoknadsbarnUtil.hentFodselsdatoForId(soknadsbarnPersonId, soknadsbarnMap),
        barnetilsynMedStonadPeriodeCoreListe,
        nettoBarnetilsynPeriodeCoreListe,
        forpleiningUtgiftPeriodeCoreListe,
        sjablonPeriodeCoreListe
    );
  }

  // Bygger grunnlag til core for beregning av BPs andel av underholdskostnad
  private BeregnBPsAndelUnderholdskostnadGrunnlagCore byggBPAndelUnderholdskostnadGrunnlagTilCore(
      BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag, Integer soknadsbarnPersonId) {

    // Løp gjennom output fra beregning av underholdskostnad og bygg opp ny input-liste til core
    var underholdskostnadPeriodeCoreListe =
        underholdskostnadResultatFraCore.getResultatPeriodeListe()
            .stream()
            .filter(resultat -> resultat.getSoknadsbarnPersonId() == soknadsbarnPersonId)
            .map(resultat -> new UnderholdskostnadPeriodeCore(
                new PeriodeCore(resultat.getResultatDatoFraTil().getPeriodeDatoFra(),
                    resultat.getResultatDatoFraTil().getPeriodeDatoTil()),
                resultat.getResultatBeregning().getResultatBelopUnderholdskostnad()))
            .collect(toList());

    // Bygg liste over BPs inntekter til core
    var inntektBPPeriodeCoreListe =
        beregnTotalBarnebidragGrunnlag.getInntektBPBMGrunnlag().getInntektBPPeriodeListe()
            .stream()
            .map(inntektBPPeriode -> new InntektPeriodeCore(
                new PeriodeCore(inntektBPPeriode.getInntektDatoFraTil().getPeriodeDatoFra(),
                    inntektBPPeriode.getInntektDatoFraTil().getPeriodeDatoTil()),
                inntektBPPeriode.getInntektType(), inntektBPPeriode.getInntektBelop()))
            .collect(toList());

    // Bygg liste over BMs inntekter til core
    var inntektBMPeriodeCoreListe =
        beregnTotalBarnebidragGrunnlag.getInntektBPBMGrunnlag().getInntektBMPeriodeListe()
            .stream()
            .map(inntektBMPeriode -> new InntektPeriodeCore(
                new PeriodeCore(inntektBMPeriode.getInntektDatoFraTil().getPeriodeDatoFra(),
                    inntektBMPeriode.getInntektDatoFraTil().getPeriodeDatoTil()),
                inntektBMPeriode.getInntektType(), inntektBMPeriode.getInntektBelop()))
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
          .map(inntektSBPeriode -> new InntektPeriodeCore(
              new PeriodeCore(inntektSBPeriode.getInntektDatoFraTil().getPeriodeDatoFra(),
                  inntektSBPeriode.getInntektDatoFraTil().getPeriodeDatoTil()),
              inntektSBPeriode.getInntektType(), inntektSBPeriode.getInntektBelop()))
          .collect(toList());
    } else {
      throw new UgyldigInputException("Fant ikke søknadsbarn med id " + soknadsbarnPersonId + " i soknadsbarnListe");
    }

    // Hent aktuelle sjabloner
    var sjablonPeriodeCoreListe = new ArrayList<>(
        mapSjablonSjablontall(sjablonSjablontallResponse.getResponseEntity().getBody(), BP_ANDEL_UNDERHOLDSKOSTAND));

    // Bygg core-objekt
    return new BeregnBPsAndelUnderholdskostnadGrunnlagCore(
        beregnDatoFra,
        beregnDatoTil,
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
      Integer soknadsbarnPersonId) {

    // Løp gjennom SamværsklassePeriodeListe og bygg opp ny liste til core med riktig PersonId
    var samvaersklassePeriodeCoreListe =
        beregnTotalBarnebidragGrunnlag.getBeregnBPSamvaersfradragGrunnlag().getSamvaersklassePeriodeListe()
            .stream()
            .filter(samvaersklassePeriode -> samvaersklassePeriode.getSamvaersklasseSoknadsbarnPersonId().equals(soknadsbarnPersonId))
            .map(samvaersklassePeriode -> new SamvaersklassePeriodeCore(
                new PeriodeCore(samvaersklassePeriode.getSamvaersklasseDatoFraTil().getPeriodeDatoFra(),
                    samvaersklassePeriode.getSamvaersklasseDatoFraTil().getPeriodeDatoTil()),
                samvaersklassePeriode.getSamvaersklasseId()))
            .collect(toList());

    // Hent aktuelle sjabloner
    var sjablonPeriodeCoreListe = new ArrayList<>(mapSjablonSamvaersfradrag(sjablonSamvaersfradragResponse.getResponseEntity().getBody()));

    // Bygg core-objekt
    return new BeregnSamvaersfradragGrunnlagCore(
        beregnDatoFra,
        beregnDatoTil,
        soknadsbarnPersonId,
        SoknadsbarnUtil.hentFodselsdatoForId(soknadsbarnPersonId, soknadsbarnMap),
        samvaersklassePeriodeCoreListe,
        sjablonPeriodeCoreListe
    );
  }

  // Bygger grunnlag til core for beregning av kostnadsberegnet bidrag
  private BeregnKostnadsberegnetBidragGrunnlagCore byggKostnadsberegnetBidragGrunnlagTilCore(Integer soknadsbarnPersonId) {

    // Løp gjennom output fra beregning av underholdskostnad og bygg opp ny input-liste til core
    var underholdskostnadPeriodeCoreListe =
        underholdskostnadResultatFraCore.getResultatPeriodeListe()
            .stream()
            .filter(resultat -> resultat.getSoknadsbarnPersonId() == soknadsbarnPersonId)
            .map(resultat -> new no.nav.bidrag.beregn.kostnadsberegnetbidrag.dto.UnderholdskostnadPeriodeCore(
                new PeriodeCore(resultat.getResultatDatoFraTil().getPeriodeDatoFra(),
                    resultat.getResultatDatoFraTil().getPeriodeDatoTil()),
                resultat.getResultatBeregning().getResultatBelopUnderholdskostnad()))
            .collect(toList());

    // Løp gjennom output fra beregning av BPs andel av underholdskostnad og bygg opp ny input-liste til core
    var bPAndelUnderholdskostnadPeriodeCoreListe =
        bpAndelUnderholdskostnadResultatFraCore.getResultatPeriodeListe()
            .stream()
            .filter(resultat -> resultat.getSoknadsbarnPersonId() == soknadsbarnPersonId)
            .map(resultat -> new BPsAndelUnderholdskostnadPeriodeCore(
                new PeriodeCore(resultat.getResultatDatoFraTil().getPeriodeDatoFra(),
                    resultat.getResultatDatoFraTil().getPeriodeDatoTil()),
                resultat.getResultatBeregning().getResultatAndelProsent()))
            .collect(toList());

    // Løp gjennom output fra beregning av samværsfradrag og bygg opp ny input-liste til core
    var samvaersfradragPeriodeCoreListe =
        samvaersfradragResultatFraCore.getResultatPeriodeListe()
            .stream()
            .filter(resultat -> resultat.getSoknadsbarnPersonId() == soknadsbarnPersonId)
            .map(resultat -> new SamvaersfradragPeriodeCore(
                new PeriodeCore(resultat.getResultatDatoFraTil().getPeriodeDatoFra(),
                    resultat.getResultatDatoFraTil().getPeriodeDatoTil()),
                resultat.getResultatBeregning().getResultatSamvaersfradragBelop()))
            .collect(toList());

    // Bygg core-objekt
    return new BeregnKostnadsberegnetBidragGrunnlagCore(
        beregnDatoFra,
        beregnDatoTil,
        soknadsbarnPersonId,
        underholdskostnadPeriodeCoreListe,
        bPAndelUnderholdskostnadPeriodeCoreListe,
        samvaersfradragPeriodeCoreListe
    );
  }

  // Bygger grunnlag til core for beregning av barnebidrag
  private void byggBarnebidragGrunnlagTilCore(BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag) {

    // Løp gjennom output fra beregning av bidragsevne og bygg opp ny input-liste til core
    var bidragsevnePeriodeCoreListe =
        bidragsevneResultatFraCore.getResultatPeriodeListe()
            .stream()
            .map(resultat -> new BidragsevnePeriodeCore(
                new PeriodeCore(resultat.getResultatDatoFraTil().getPeriodeDatoFra(),
                    resultat.getResultatDatoFraTil().getPeriodeDatoTil()),
                resultat.getResultatBeregning().getResultatEvneBelop(), resultat.getResultatBeregning().getResultat25ProsentInntekt()))
            .collect(toList());

    // Løp gjennom output fra beregning av BPs andel underholdskostnad og bygg opp ny input-liste til core
    var bPAndelUnderholdskostnadPeriodeCoreListe =
        bpAndelUnderholdskostnadResultatFraCore.getResultatPeriodeListe()
            .stream()
            .map(resultat -> new no.nav.bidrag.beregn.barnebidrag.dto.BPsAndelUnderholdskostnadPeriodeCore(
                resultat.getSoknadsbarnPersonId(),
                new PeriodeCore(resultat.getResultatDatoFraTil().getPeriodeDatoFra(),
                    resultat.getResultatDatoFraTil().getPeriodeDatoTil()),
                resultat.getResultatBeregning().getResultatAndelProsent(), resultat.getResultatBeregning().getResultatAndelBelop()))
            .collect(toList());

    // Løp gjennom output fra beregning av samværsfradrag og bygg opp ny input-liste til core
    var samvaersfradragPeriodeCoreListe =
        samvaersfradragResultatFraCore.getResultatPeriodeListe()
            .stream()
            .map(resultat -> new no.nav.bidrag.beregn.barnebidrag.dto.SamvaersfradragPeriodeCore(
                resultat.getSoknadsbarnPersonId(),
                new PeriodeCore(resultat.getResultatDatoFraTil().getPeriodeDatoFra(),
                    resultat.getResultatDatoFraTil().getPeriodeDatoTil()),
                resultat.getResultatBeregning().getResultatSamvaersfradragBelop()))
            .collect(toList());

    // Hent aktuelle sjabloner
    var sjablonPeriodeCoreListe = mapSjablonSjablontall(sjablonSjablontallResponse.getResponseEntity().getBody(), BARNEBIDRAG);

    // Bygg grunnlag for beregning av barnebidrag. Her gjøres også kontroll av inputdata
    barnebidragGrunnlagTilCore = beregnTotalBarnebidragGrunnlag.barnebidragTilCore(bidragsevnePeriodeCoreListe,
        bPAndelUnderholdskostnadPeriodeCoreListe, samvaersfradragPeriodeCoreListe, sjablonPeriodeCoreListe);
  }

  //==================================================================================================================================================

  // Kaller core for beregning av bidragsevne
  private void beregnBidragsevne() {

    // Kaller core-modulen for beregning av bidragsevne
    LOGGER.debug("Bidragsevne - grunnlag for beregning: {}", bidragsevneGrunnlagTilCore);
    bidragsevneResultatFraCore = bidragsevneCore.beregnBidragsevne(bidragsevneGrunnlagTilCore);

    if (!bidragsevneResultatFraCore.getAvvikListe().isEmpty()) {
      LOGGER.error("Ugyldig input ved beregning av bidragsevne. Følgende avvik ble funnet: " + System.lineSeparator()
          + bidragsevneResultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining(System.lineSeparator())));
      LOGGER.info("Bidragsevne - grunnlag for beregning:" + System.lineSeparator()
          + "beregnDatoFra= " + bidragsevneGrunnlagTilCore.getBeregnDatoFra() + System.lineSeparator()
          + "beregnDatoTil= " + bidragsevneGrunnlagTilCore.getBeregnDatoTil() + System.lineSeparator()
          + "antallBarnIEgetHusholdPeriodeListe= " + bidragsevneGrunnlagTilCore.getAntallBarnIEgetHusholdPeriodeListe() + System.lineSeparator()
          + "bostatusPeriodeListe= " + bidragsevneGrunnlagTilCore.getBostatusPeriodeListe() + System.lineSeparator()
          + "inntektPeriodeListe= " + bidragsevneGrunnlagTilCore.getInntektPeriodeListe() + System.lineSeparator()
          + "særfradragPeriodeListe= " + bidragsevneGrunnlagTilCore.getSaerfradragPeriodeListe() + System.lineSeparator()
          + "skatteklassePeriodeListe= " + bidragsevneGrunnlagTilCore.getSkatteklassePeriodeListe());
      throw new UgyldigInputException("Ugyldig input ved beregning av bidragsevne. Følgende avvik ble funnet: "
          + bidragsevneResultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining("; ")));
    }

    LOGGER.debug("Bidragsevne - resultat av beregning: {}", bidragsevneResultatFraCore.getResultatPeriodeListe());
  }

  // Kaller core for beregning av netto barnetilsyn
  private void beregnNettoBarnetilsyn() {

    // Kaller core-modulen for beregning av netto barnetilsyn
    LOGGER.debug("Netto barnetilsyn - grunnlag for beregning: {}", nettoBarnetilsynGrunnlagTilCore);
    nettoBarnetilsynResultatFraCore = nettoBarnetilsynCore.beregnNettoBarnetilsyn(nettoBarnetilsynGrunnlagTilCore);

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
  }

  // Kaller core for beregning av underholdskostnad
  private void beregnUnderholdskostnad(BeregnUnderholdskostnadGrunnlagCore underholdskostnadGrunnlagTilCore) {

    // Kaller core-modulen for beregning av underholdskostnad
    LOGGER.debug("Underholdskostnad - grunnlag for beregning: {}", underholdskostnadGrunnlagTilCore);
    underholdskostnadResultatFraCore = underholdskostnadCore.beregnUnderholdskostnad(underholdskostnadGrunnlagTilCore);

    if (!underholdskostnadResultatFraCore.getAvvikListe().isEmpty()) {
      LOGGER.error("Ugyldig input ved beregning av underholdskostnad. Følgende avvik ble funnet: " + System.lineSeparator()
          + underholdskostnadResultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst)
          .collect(Collectors.joining(System.lineSeparator())));
      LOGGER.info("Underholdskostnad - grunnlag for beregning: " + System.lineSeparator()
          + "beregnDatoFra= " + underholdskostnadGrunnlagTilCore.getBeregnDatoFra() + System.lineSeparator()
          + "beregnDatoTil= " + underholdskostnadGrunnlagTilCore.getBeregnDatoTil() + System.lineSeparator()
          + "soknadsbarnPersonId= " + underholdskostnadGrunnlagTilCore.getSoknadsbarnPersonId() + System.lineSeparator()
          + "soknadBarnFodselsdato= " + underholdskostnadGrunnlagTilCore.getSoknadBarnFodselsdato() + System.lineSeparator()
          + "barneTilsynMedStonadPeriodeListe= " + underholdskostnadGrunnlagTilCore.getBarnetilsynMedStonadPeriodeListe() + System.lineSeparator()
          + "forpleiningUtgiftPeriodeListe= " + underholdskostnadGrunnlagTilCore.getForpleiningUtgiftPeriodeListe() + System.lineSeparator()
          + "nettoBarnetilsynPeriodeListe= " + underholdskostnadGrunnlagTilCore.getNettoBarnetilsynPeriodeListe() + System.lineSeparator());
      throw new UgyldigInputException("Ugyldig input ved beregning av underholdskostnad. Følgende avvik ble funnet: "
          + underholdskostnadResultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining("; ")));
    }

    LOGGER.debug("Underholdskostnad - resultat av beregning: {}", underholdskostnadResultatFraCore.getResultatPeriodeListe());
  }

  // Kaller core for beregning av BPs andel av underholdskostnad
  private void beregnBPsAndelUnderholdskostnad(BeregnBPsAndelUnderholdskostnadGrunnlagCore bpAndelUnderholdskostnadGrunnlagTilCore) {

    // Kaller core-modulen for beregning av BPs andel av underholdskostnad
    LOGGER.debug("BPs andel av underholdskostnad - grunnlag for beregning: {}", bpAndelUnderholdskostnadGrunnlagTilCore);
    bpAndelUnderholdskostnadResultatFraCore = bpAndelUnderholdskostnadCore.beregnBPsAndelUnderholdskostnad(bpAndelUnderholdskostnadGrunnlagTilCore);

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
  }

  // Kaller core for beregning av samværsfradrag
  private void beregnSamvaersfradrag(BeregnSamvaersfradragGrunnlagCore samvaersfradragGrunnlagTilCore) {

    // Kaller core-modulen for beregning av samværsfradrag
    LOGGER.debug("Samværsfradrag - grunnlag for beregning: {}", samvaersfradragGrunnlagTilCore);
    samvaersfradragResultatFraCore = samvaersfradragCore.beregnSamvaersfradrag(samvaersfradragGrunnlagTilCore);

    if (!samvaersfradragResultatFraCore.getAvvikListe().isEmpty()) {
      LOGGER.error("Ugyldig input ved beregning av samværsfradrag. Følgende avvik ble funnet: " + System.lineSeparator()
          + samvaersfradragResultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst)
          .collect(Collectors.joining(System.lineSeparator())));
      LOGGER.info("Samværsfradrag - grunnlag for beregning: " + System.lineSeparator()
          + "beregnDatoFra= " + samvaersfradragGrunnlagTilCore.getBeregnDatoFra() + System.lineSeparator()
          + "beregnDatoTil= " + samvaersfradragGrunnlagTilCore.getBeregnDatoTil() + System.lineSeparator()
          + "soknadsbarnPersonId= " + samvaersfradragGrunnlagTilCore.getSoknadsbarnPersonId() + System.lineSeparator()
          + "soknadsbarnFodselsdato= " + samvaersfradragGrunnlagTilCore.getSoknadsbarnFodselsdato() + System.lineSeparator()
          + "samvaersklassePeriodeListe= " + samvaersfradragGrunnlagTilCore.getSamvaersklassePeriodeListe() + System.lineSeparator());
      throw new UgyldigInputException("Ugyldig input ved beregning av samværsfradrag. Følgende avvik ble funnet: "
          + samvaersfradragResultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining("; ")));
    }

    LOGGER.debug("Samværsfradrag - resultat av beregning: {}", samvaersfradragResultatFraCore.getResultatPeriodeListe());
  }

  // Kaller core for beregning av kostnadsberegnet bidrag
  private void beregnKostnadsberegnetBidrag(BeregnKostnadsberegnetBidragGrunnlagCore kostnadsberegnetBidragGrunnlagTilCore) {

    // Kaller core-modulen for beregning av kostnadsberegnet bidrag
    LOGGER.debug("Kostnadsberegnet bidrag - grunnlag for beregning: {}", kostnadsberegnetBidragGrunnlagTilCore);
    kostnadsberegnetBidragResultatFraCore = kostnadsberegnetBidragCore.beregnKostnadsberegnetBidrag(kostnadsberegnetBidragGrunnlagTilCore);

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
  }

  // Kaller core for beregning av barnebidrag
  private void beregnBarnebidrag() {

    // Kaller core-modulen for beregning av barnebidrag
    LOGGER.debug("Barnebidrag - grunnlag for beregning: {}", barnebidragGrunnlagTilCore);
    barnebidragResultatFraCore = barnebidragCore.beregnBarnebidrag(barnebidragGrunnlagTilCore);

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
          + "barnetilleggForsvaretPeriodeListe= " + barnebidragGrunnlagTilCore.getBarnetilleggForsvaretPeriodeListe() + System.lineSeparator());
      throw new UgyldigInputException("Ugyldig input ved beregning av barnebidrag. Følgende avvik ble funnet: "
          + barnebidragResultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining("; ")));
    }

    LOGGER.debug("Barnebidrag - resultat av beregning: {}", barnebidragResultatFraCore.getResultatPeriodeListe());
  }

  //==================================================================================================================================================

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

  // Mapper sjabloner av typen sjablontall
  // Filtrerer bort de sjablonene som ikke brukes i den aktuelle delberegningen og de som ikke er innenfor intervallet beregnDatoFra-beregnDatoTil
  private List<SjablonPeriodeCore> mapSjablonSjablontall(List<Sjablontall> sjablonSjablontallListe, String delberegning) {
    return sjablonSjablontallListe
        .stream()
        .filter(sjablon -> (!(sjablon.getDatoFom().isAfter(beregnDatoTil)) && (!(sjablon.getDatoTom().isBefore(beregnDatoFra)))))
        .filter(sjablon -> filtrerSjablonTall(sjablontallMap.getOrDefault(sjablon.getTypeSjablon(), SjablonTallNavn.DUMMY), delberegning))
        .map(sjablon -> new SjablonPeriodeCore(
            new PeriodeCore(sjablon.getDatoFom(), sjablon.getDatoTom()),
            sjablontallMap.getOrDefault(sjablon.getTypeSjablon(), SjablonTallNavn.DUMMY).getNavn(),
            emptyList(),
            singletonList(new SjablonInnholdCore(SjablonInnholdNavn.SJABLON_VERDI.getNavn(), sjablon.getVerdi().doubleValue()))))
        .collect(toList());
  }

  // Mapper sjabloner av typen forbruksutgifter
  // Filtrerer bort de sjablonene som ikke er innenfor intervallet beregnDatoFra-beregnDatoTil
  private List<SjablonPeriodeCore> mapSjablonForbruksutgifter(List<Forbruksutgifter> sjablonForbruksutgifterListe) {
    return sjablonForbruksutgifterListe
        .stream()
        .filter(sjablon -> (!(sjablon.getDatoFom().isAfter(beregnDatoTil)) && (!(sjablon.getDatoTom().isBefore(beregnDatoFra)))))
        .map(sjablon -> new SjablonPeriodeCore(
            new PeriodeCore(sjablon.getDatoFom(), sjablon.getDatoTom()),
            SjablonNavn.FORBRUKSUTGIFTER.getNavn(),
            singletonList(new SjablonNokkelCore(SjablonNokkelNavn.ALDER_TOM.getNavn(), sjablon.getAlderTom().toString())),
            singletonList(new SjablonInnholdCore(SjablonInnholdNavn.FORBRUK_TOTAL_BELOP.getNavn(), sjablon.getBelopForbrukTot().doubleValue()))))
        .collect(toList());
  }

  // Mapper sjabloner av typen maks tilsyn
  // Filtrerer bort de sjablonene som ikke er innenfor intervallet beregnDatoFra-beregnDatoTil
  private List<SjablonPeriodeCore> mapSjablonMaksTilsyn(List<MaksTilsyn> sjablonMaksTilsynListe) {
    return sjablonMaksTilsynListe
        .stream()
        .filter(sjablon -> (!(sjablon.getDatoFom().isAfter(beregnDatoTil)) && (!(sjablon.getDatoTom().isBefore(beregnDatoFra)))))
        .map(sjablon -> new SjablonPeriodeCore(
            new PeriodeCore(sjablon.getDatoFom(), sjablon.getDatoTom()),
            SjablonNavn.MAKS_TILSYN.getNavn(),
            singletonList(new SjablonNokkelCore(SjablonNokkelNavn.ANTALL_BARN_TOM.getNavn(), sjablon.getAntBarnTom().toString())),
            singletonList(new SjablonInnholdCore(SjablonInnholdNavn.MAKS_TILSYN_BELOP.getNavn(), sjablon.getMaksBelopTilsyn().doubleValue()))))
        .collect(toList());
  }

  // Mapper sjabloner av typen maks fradrag
  // Filtrerer bort de sjablonene som ikke er innenfor intervallet beregnDatoFra-beregnDatoTil
  private List<SjablonPeriodeCore> mapSjablonMaksFradrag(List<MaksFradrag> sjablonMaksFradragListe) {
    return sjablonMaksFradragListe
        .stream()
        .filter(sjablon -> (!(sjablon.getDatoFom().isAfter(beregnDatoTil)) && (!(sjablon.getDatoTom().isBefore(beregnDatoFra)))))
        .map(sjablon -> new SjablonPeriodeCore(
            new PeriodeCore(sjablon.getDatoFom(), sjablon.getDatoTom()),
            SjablonNavn.MAKS_FRADRAG.getNavn(),
            singletonList(new SjablonNokkelCore(SjablonNokkelNavn.ANTALL_BARN_TOM.getNavn(), sjablon.getAntBarnTom().toString())),
            singletonList(new SjablonInnholdCore(SjablonInnholdNavn.MAKS_FRADRAG_BELOP.getNavn(), sjablon.getMaksBelopFradrag().doubleValue()))))
        .collect(toList());
  }

  // Mapper sjabloner av typen samværsfradrag
  // Filtrerer bort de sjablonene som ikke er innenfor intervallet beregnDatoFra-beregnDatoTil
  private List<SjablonPeriodeCore> mapSjablonSamvaersfradrag(List<Samvaersfradrag> sjablonSamvaersfradragListe) {
    return sjablonSamvaersfradragListe
        .stream()
        .filter(sjablon -> (!(sjablon.getDatoFom().isAfter(beregnDatoTil)) && (!(sjablon.getDatoTom().isBefore(beregnDatoFra)))))
        .map(sjablon -> new SjablonPeriodeCore(
            new PeriodeCore(sjablon.getDatoFom(), sjablon.getDatoTom()),
            SjablonNavn.SAMVAERSFRADRAG.getNavn(),
            asList(new SjablonNokkelCore(SjablonNokkelNavn.SAMVAERSKLASSE.getNavn(), sjablon.getSamvaersklasse()),
                new SjablonNokkelCore(SjablonNokkelNavn.ALDER_TOM.getNavn(), sjablon.getAlderTom().toString())),
            asList(new SjablonInnholdCore(SjablonInnholdNavn.ANTALL_DAGER_TOM.getNavn(), sjablon.getAntDagerTom().doubleValue()),
                new SjablonInnholdCore(SjablonInnholdNavn.ANTALL_NETTER_TOM.getNavn(), sjablon.getAntNetterTom().doubleValue()),
                new SjablonInnholdCore(SjablonInnholdNavn.FRADRAG_BELOP.getNavn(), sjablon.getBelopFradrag().doubleValue()))))
        .collect(toList());
  }

  // Mapper sjabloner av typen bidragsevne
  // Filtrerer bort de sjablonene som ikke er innenfor intervallet beregnDatoFra-beregnDatoTil
  private List<SjablonPeriodeCore> mapSjablonBidragsevne(List<Bidragsevne> sjablonBidragsevneListe) {
    return sjablonBidragsevneListe
        .stream()
        .filter(sjablon -> (!(sjablon.getDatoFom().isAfter(beregnDatoTil)) && (!(sjablon.getDatoTom().isBefore(beregnDatoFra)))))
        .map(sjablon -> new SjablonPeriodeCore(
            new PeriodeCore(sjablon.getDatoFom(), sjablon.getDatoTom()),
            SjablonNavn.BIDRAGSEVNE.getNavn(),
            singletonList(new SjablonNokkelCore(SjablonNokkelNavn.BOSTATUS.getNavn(), sjablon.getBostatus())),
            Arrays.asList(new SjablonInnholdCore(SjablonInnholdNavn.BOUTGIFT_BELOP.getNavn(), sjablon.getBelopBoutgift().doubleValue()),
                new SjablonInnholdCore(SjablonInnholdNavn.UNDERHOLD_BELOP.getNavn(), sjablon.getBelopUnderhold().doubleValue()))))
        .collect(toList());
  }

  // Mapper sjabloner av typen trinnvis skattesats
  // Filtrerer bort de sjablonene som ikke er innenfor intervallet beregnDatoFra-beregnDatoTil
  private List<SjablonPeriodeCore> mapSjablonTrinnvisSkattesats(List<TrinnvisSkattesats> sjablonTrinnvisSkattesatsListe) {
    return sjablonTrinnvisSkattesatsListe
        .stream()
        .filter(sjablon -> (!(sjablon.getDatoFom().isAfter(beregnDatoTil)) && (!(sjablon.getDatoTom().isBefore(beregnDatoFra)))))
        .map(sjablon -> new SjablonPeriodeCore(
            new PeriodeCore(sjablon.getDatoFom(), sjablon.getDatoTom()),
            SjablonNavn.TRINNVIS_SKATTESATS.getNavn(),
            emptyList(),
            Arrays.asList(new SjablonInnholdCore(SjablonInnholdNavn.INNTEKTSGRENSE_BELOP.getNavn(), sjablon.getInntektgrense().doubleValue()),
                new SjablonInnholdCore(SjablonInnholdNavn.SKATTESATS_PROSENT.getNavn(), sjablon.getSats().doubleValue()))))
        .collect(toList());
  }

  // Sjekker om en type SjablonTall er i bruk for en delberegning
  private boolean filtrerSjablonTall(SjablonTallNavn sjablonTallNavn, String delberegning) {

    return switch (delberegning) {
      case BIDRAGSEVNE -> sjablonTallNavn.getBidragsevne();
      case NETTO_BARNETILSYN -> sjablonTallNavn.getNettoBarnetilsyn();
      case UNDERHOLDSKOSTAND -> sjablonTallNavn.getUnderholdskostnad();
      case BP_ANDEL_UNDERHOLDSKOSTAND -> sjablonTallNavn.getBpAndelUnderholdskostnad();
      case BARNEBIDRAG -> sjablonTallNavn.getBarnebidrag();
      default -> false;
    };
  }
}
