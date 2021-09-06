package no.nav.bidrag.beregn.barnebidrag.rest.mapper;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static no.nav.bidrag.beregn.barnebidrag.rest.service.BeregnBarnebidragService.BARNEBIDRAG;
import static no.nav.bidrag.beregn.barnebidrag.rest.service.BeregnBarnebidragService.BIDRAGSEVNE;
import static no.nav.bidrag.beregn.barnebidrag.rest.service.BeregnBarnebidragService.BP_ANDEL_UNDERHOLDSKOSTNAD;
import static no.nav.bidrag.beregn.barnebidrag.rest.service.BeregnBarnebidragService.NETTO_BARNETILSYN;
import static no.nav.bidrag.beregn.barnebidrag.rest.service.BeregnBarnebidragService.UNDERHOLDSKOSTNAD;
import static org.apache.commons.validator.GenericValidator.isDate;

import com.fasterxml.jackson.databind.JsonNode;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import no.nav.bidrag.beregn.barnebidrag.dto.AndreLopendeBidragPeriodeCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BPsAndelUnderholdskostnadPeriodeCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BarnetilleggForsvaretPeriodeCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BarnetilleggPeriodeCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BidragsevnePeriodeCore;
import no.nav.bidrag.beregn.barnebidrag.dto.DeltBostedPeriodeCore;
import no.nav.bidrag.beregn.barnebidrag.dto.SamvaersfradragPeriodeCore;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Barnetilsyn;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Bidragsevne;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Forbruksutgifter;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.MaksFradrag;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.MaksTilsyn;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Samvaersfradrag;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Sjablontall;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.TrinnvisSkattesats;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnetBidragSakPeriode;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.Grunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.GrunnlagFF;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;
import no.nav.bidrag.beregn.barnebidrag.rest.service.SoknadsbarnUtil;
import no.nav.bidrag.beregn.bidragsevne.dto.BarnIHusstandPeriodeCore;
import no.nav.bidrag.beregn.bidragsevne.dto.BeregnetBidragsevneResultatCore;
import no.nav.bidrag.beregn.bidragsevne.dto.BostatusPeriodeCore;
import no.nav.bidrag.beregn.bidragsevne.dto.SaerfradragPeriodeCore;
import no.nav.bidrag.beregn.bidragsevne.dto.SkatteklassePeriodeCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnetBPsAndelUnderholdskostnadResultatCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.UnderholdskostnadPeriodeCore;
import no.nav.bidrag.beregn.felles.dto.PeriodeCore;
import no.nav.bidrag.beregn.felles.dto.SjablonInnholdCore;
import no.nav.bidrag.beregn.felles.dto.SjablonNokkelCore;
import no.nav.bidrag.beregn.felles.dto.SjablonPeriodeCore;
import no.nav.bidrag.beregn.felles.enums.SjablonInnholdNavn;
import no.nav.bidrag.beregn.felles.enums.SjablonNavn;
import no.nav.bidrag.beregn.felles.enums.SjablonNokkelNavn;
import no.nav.bidrag.beregn.felles.enums.SjablonTallNavn;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnetNettoBarnetilsynResultatCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.FaktiskUtgiftPeriodeCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnetSamvaersfradragResultatCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.SamvaersklassePeriodeCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BarnetilsynMedStonadPeriodeCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnetUnderholdskostnadResultatCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.ForpleiningUtgiftPeriodeCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.NettoBarnetilsynPeriodeCore;

public class CoreMapper {

  // Barnebidrag
  protected static final String SOKNADSBARN_INFO = "SoknadsbarnInfo";
  protected static final String INNTEKT_TYPE = "Inntekt";
  protected static final String BARN_I_HUSSTAND_TYPE = "BarnIHusstand";
  protected static final String BOSTATUS_TYPE = "Bostatus";
  protected static final String SAERFRADRAG_TYPE = "Saerfradrag";
  protected static final String SKATTEKLASSE_TYPE = "Skatteklasse";
  protected static final String FAKTISK_UTGIFT_TYPE = "FaktiskUtgift";
  protected static final String BARNETILSYN_MED_STONAD_TYPE = "BarnetilsynMedStonad";
  protected static final String FORPLEINING_UTGIFT_TYPE = "ForpleiningUtgift";
  protected static final String SAMVAERSKLASSE_TYPE = "Samvaersklasse";
  protected static final String BARNETILLEGG_TYPE = "Barnetillegg";
  protected static final String BARNETILLEGG_FORSVARET_TYPE = "BarnetilleggForsvaret";
  protected static final String DELT_BOSTED_TYPE = "DeltBosted";
  protected static final String ANDRE_LOPENDE_BIDRAG_TYPE = "AndreLopendeBidrag";

  // Forholdsmessig fordeling
  protected static final String BIDRAGSEVNE_TYPE = "Bidragsevne";
  protected static final String BARNEBIDRAG_TYPE = "Barnebidrag";

  // Roller
  protected static final String BIDRAGSPLIKTIG = "BP";
  protected static final String BIDRAGSMOTTAKER = "BM";
  protected static final String SOKNADSBARN = "SB";

  // Lager en map for sjablontall (id og navn)
  protected Map<String, SjablonTallNavn> mapSjablontall() {
    var sjablontallMap = new HashMap<String, SjablonTallNavn>();
    for (SjablonTallNavn sjablonTallNavn : SjablonTallNavn.values()) {
      sjablontallMap.put(sjablonTallNavn.getId(), sjablonTallNavn);
    }
    return sjablontallMap;
  }

  protected no.nav.bidrag.beregn.bidragsevne.dto.InntektPeriodeCore mapInntektBidragsevne(Grunnlag grunnlag) {
    var inntektType = mapText(grunnlag.getInnhold(), "inntektType", grunnlag.getType());
    var belop = new BigDecimal(mapNumber(grunnlag.getInnhold(), "belop", grunnlag.getType()));
    return new no.nav.bidrag.beregn.bidragsevne.dto.InntektPeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()),
        inntektType, belop);
  }

  protected no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.InntektPeriodeCore mapInntektBPAndelUnderholdskostnad(Grunnlag grunnlag) {
    var inntektType = mapText(grunnlag.getInnhold(), "inntektType", grunnlag.getType());
    var belop = new BigDecimal(mapNumber(grunnlag.getInnhold(), "belop", grunnlag.getType()));
    var deltFordel = mapBoolean(grunnlag.getInnhold(), "deltFordel", grunnlag.getType());
    var skatteklasse2 = mapBoolean(grunnlag.getInnhold(), "skatteklasse2", grunnlag.getType());
    return new no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.InntektPeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(),
        grunnlag.getType()), inntektType, belop, deltFordel, skatteklasse2);
  }

  protected BostatusPeriodeCore mapBostatus(Grunnlag grunnlag) {
    var bostatusKode = mapText(grunnlag.getInnhold(), "bostatusKode", grunnlag.getType());
    return new BostatusPeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()), bostatusKode);
  }

  protected BarnIHusstandPeriodeCore mapBarnIHusstand(Grunnlag grunnlag) {
    var antallBarn = Double.parseDouble(mapNumber(grunnlag.getInnhold(), "antall", grunnlag.getType()));
    return new BarnIHusstandPeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()), antallBarn);
  }

  protected SaerfradragPeriodeCore mapSaerfradrag(Grunnlag grunnlag) {
    var saerfradragKode = mapText(grunnlag.getInnhold(), "saerfradragKode", grunnlag.getType());
    return new SaerfradragPeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()), saerfradragKode);
  }

  protected SkatteklassePeriodeCore mapSkatteklasse(Grunnlag grunnlag) {
    var skatteklasseId = Integer.parseInt(mapNumber(grunnlag.getInnhold(), "skatteklasseId", grunnlag.getType()));
    return new SkatteklassePeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()), skatteklasseId);
  }

  protected FaktiskUtgiftPeriodeCore mapFaktiskUtgift(Grunnlag grunnlag, Integer soknadsbarnPersonId, Map<Integer, String> soknadsbarnMap) {
    var belop = new BigDecimal(mapNumber(grunnlag.getInnhold(), "belop", grunnlag.getType()));
    var soknadsbarnFodselsdato = LocalDate.parse(hentFodselsdato(soknadsbarnPersonId, soknadsbarnMap));
    return new FaktiskUtgiftPeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()), soknadsbarnFodselsdato,
        soknadsbarnPersonId, belop);
  }

  protected BarnetilsynMedStonadPeriodeCore mapBarnetilsynMedStonad(Grunnlag grunnlag) {
    var stonadType = mapText(grunnlag.getInnhold(), "stonadType", grunnlag.getType());
    var tilsynType = mapText(grunnlag.getInnhold(), "tilsynType", grunnlag.getType());
    return new BarnetilsynMedStonadPeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()), tilsynType,
        stonadType);
  }

  protected ForpleiningUtgiftPeriodeCore mapForpleiningUtgift(Grunnlag grunnlag) {
    var belop = new BigDecimal(mapNumber(grunnlag.getInnhold(), "belop", grunnlag.getType()));
    return new ForpleiningUtgiftPeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()), belop);
  }

  protected SamvaersklassePeriodeCore mapSamvaersklasse(Grunnlag grunnlag) {
    var samvaersklasseId = mapText(grunnlag.getInnhold(), "samvaersklasseId", grunnlag.getType());
    return new SamvaersklassePeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()), samvaersklasseId);
  }

  protected BarnetilleggPeriodeCore mapBarnetillegg(Grunnlag grunnlag, Integer soknadsbarnPersonId) {
    var bruttoBelop = new BigDecimal(mapNumber(grunnlag.getInnhold(), "bruttoBelop", grunnlag.getType()));
    var skattProsent = new BigDecimal(mapNumber(grunnlag.getInnhold(), "skattProsent", grunnlag.getType()));
    return new BarnetilleggPeriodeCore(grunnlag.getReferanse(), soknadsbarnPersonId, mapPeriode(grunnlag.getInnhold(), grunnlag.getType()),
        bruttoBelop, skattProsent);
  }

  protected BarnetilleggForsvaretPeriodeCore mapBarnetilleggForsvaret(Grunnlag grunnlag) {
    var barnetilleggForsvaret = mapBoolean(grunnlag.getInnhold(), "barnetilleggForsvaret", grunnlag.getType());
    return new BarnetilleggForsvaretPeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()),
        barnetilleggForsvaret);
  }

  protected DeltBostedPeriodeCore mapDeltBosted(Grunnlag grunnlag, Integer soknadsbarnPersonId) {
    var deltBosted = mapBoolean(grunnlag.getInnhold(), "deltBosted", grunnlag.getType());
    return new DeltBostedPeriodeCore(grunnlag.getReferanse(), soknadsbarnPersonId, mapPeriode(grunnlag.getInnhold(), grunnlag.getType()), deltBosted);
  }

  protected AndreLopendeBidragPeriodeCore mapAndreLopendeBidrag(Grunnlag grunnlag, Integer barnPersonId) {
    var bidragBelop = new BigDecimal(mapNumber(grunnlag.getInnhold(), "bidragBelop", grunnlag.getType()));
    var samvaersfradragBelop = new BigDecimal(mapNumber(grunnlag.getInnhold(), "samvaersfradragBelop", grunnlag.getType()));
    return new AndreLopendeBidragPeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()), barnPersonId,
        bidragBelop, samvaersfradragBelop);
  }

  protected no.nav.bidrag.beregn.forholdsmessigfordeling.dto.BidragsevnePeriodeCore mapBidragsevne(GrunnlagFF grunnlag) {
    var belop = new BigDecimal(mapNumber(grunnlag.getInnhold(), "belop", grunnlag.getType()));
    var tjuefemProsentInntekt = new BigDecimal(mapNumber(grunnlag.getInnhold(), "25ProsentInntekt", grunnlag.getType()));
    return new no.nav.bidrag.beregn.forholdsmessigfordeling.dto.BidragsevnePeriodeCore(mapPeriode(grunnlag.getInnhold(), grunnlag.getType()), belop,
        tjuefemProsentInntekt);
  }

  protected BeregnetBidragSakPeriode mapBarnebidrag(GrunnlagFF grunnlag) {
    var sakNr = Integer.parseInt(mapNumber(grunnlag.getInnhold(), "sakNr", grunnlag.getType()));
    var datoFom = LocalDate.parse(mapDato(grunnlag.getInnhold(), "datoFom", grunnlag.getType()));
    var datoTil = LocalDate.parse(mapDato(grunnlag.getInnhold(), "datoTil", grunnlag.getType()));
    var soknadsbarnId = Integer.parseInt(mapNumber(grunnlag.getInnhold(), "soknadsbarnId", grunnlag.getType()));
    var belop = new BigDecimal(mapNumber(grunnlag.getInnhold(), "belop", grunnlag.getType()));
    return new BeregnetBidragSakPeriode(sakNr, datoFom, datoTil, soknadsbarnId, belop);
  }

  protected String mapText(JsonNode grunnlagInnhold, String dataElement, String grunnlagType) {
    String verdi;
    if (grunnlagInnhold.has(dataElement)) {
      verdi = grunnlagInnhold.get(dataElement).asText();
      evaluerStringType(verdi, dataElement, grunnlagType);
    } else {
      throw new UgyldigInputException(dataElement + " i objekt av type " + grunnlagType + " mangler");
    }
    return verdi;
  }

  protected String mapNumber(JsonNode grunnlagInnhold, String dataElement, String grunnlagType) {
    String verdi;
    if (grunnlagInnhold.has(dataElement) && grunnlagInnhold.get(dataElement).isNumber()) {
      verdi = grunnlagInnhold.get(dataElement).asText();
    } else {
      throw new UgyldigInputException(dataElement + " i objekt av type " + grunnlagType + " mangler, er null eller har ugyldig verdi");
    }
    return verdi;
  }

  protected Boolean mapBoolean(JsonNode grunnlagInnhold, String dataElement, String grunnlagType) {
    boolean verdi = false;
    if (grunnlagInnhold.has(dataElement)) {
      if (grunnlagInnhold.get(dataElement).isBoolean()) {
        verdi = grunnlagInnhold.get(dataElement).asBoolean();
      } else {
        throw new UgyldigInputException(dataElement + " i objekt av type " + grunnlagType + " er null eller har ugyldig verdi");
      }
    }
    return verdi;
  }

  protected String mapDato(JsonNode grunnlagInnhold, String dataElement, String grunnlagType) {
    var verdi = mapText(grunnlagInnhold, dataElement, grunnlagType);
    if (!gyldigDato(verdi)) {
      throw new UgyldigInputException(dataElement + " i objekt av type " + grunnlagType + " har ugyldig verdi");
    }
    return verdi;
  }

  protected PeriodeCore mapPeriode(JsonNode grunnlagInnhold, String grunnlagType) {
    var datoFom = LocalDate.parse(mapDato(grunnlagInnhold, "datoFom", grunnlagType));
    var datoTil = LocalDate.parse(mapDato(grunnlagInnhold, "datoTil", grunnlagType));
    return new PeriodeCore(datoFom, datoTil);
  }

  protected String hentSoknadsbarnId(JsonNode grunnlagInnhold, String grunnlagType) {
    String soknadsbarnId;
    if (grunnlagInnhold.has("soknadsbarnId")) {
      soknadsbarnId = grunnlagInnhold.get("soknadsbarnId").asText();
    } else {
      throw new UgyldigInputException("soknadsbarnId i objekt av type " + grunnlagType + " mangler");
    }
    return soknadsbarnId;
  }

  protected String hentFodselsdato(Integer soknadsbarnPersonId, Map<Integer, String> soknadsbarnMap) {
    var soknadsbarnFodselsdato = Optional.ofNullable(SoknadsbarnUtil.hentFodselsdatoForId(soknadsbarnPersonId, soknadsbarnMap))
        .orElseThrow(() ->
            new UgyldigInputException("Søknadsbarn med id " + soknadsbarnPersonId + " har ingen korresponderende fødselsdato i soknadsbarnListe"));
    if (!gyldigDato(soknadsbarnFodselsdato)) {
      throw new UgyldigInputException("fodselsdato i objekt av type soknadsbarnInfo er null eller har ugyldig verdi");
    }
    return soknadsbarnFodselsdato;
  }

  // Sjekker om dato har gyldig format
  protected boolean gyldigDato(String dato) {
    if (null == dato) {
      return false;
    }
    return isDate(dato, "yyyy-MM-dd", true);
  }

  // Sjekker om dataelement av typen String er null
  protected void evaluerStringType(String verdi, String dataElement, String grunnlagType) {
    if (null == verdi || ("null".equals(verdi))) {
      throw new UgyldigInputException(dataElement + " i objekt av type " + grunnlagType + " er null");
    }
  }

  // Løper gjennom output fra beregning av netto barnetilsyn og bygger opp ny input-liste til underholdskostnad core
  protected List<NettoBarnetilsynPeriodeCore> mapDelberegningNettoBarnetilsyn(BeregnetNettoBarnetilsynResultatCore nettoBarnetilsynResultatFraCore,
      Integer soknadsbarnPersonId) {
    var nettoBarnetilsynPeriodeCoreListe = new ArrayList<NettoBarnetilsynPeriodeCore>();
    for (var nettoBarnetilsynPeriodeCore : nettoBarnetilsynResultatFraCore.getResultatPeriodeListe()) {
      if (soknadsbarnPersonId == nettoBarnetilsynPeriodeCore.getSoknadsbarnPersonId()) {
        nettoBarnetilsynPeriodeCoreListe.add(new NettoBarnetilsynPeriodeCore(
            byggReferanseForDelberegning("Delberegning_BM_NettoBarnetilsyn", soknadsbarnPersonId.toString(),
                nettoBarnetilsynPeriodeCore.getPeriode().getDatoFom()),
            nettoBarnetilsynPeriodeCore.getPeriode(),
            nettoBarnetilsynPeriodeCore.getResultat().getBelop()));
      }
    }
    return nettoBarnetilsynPeriodeCoreListe;
  }

  // Løper gjennom output fra beregning av underholdskostnad og bygger opp ny input-liste til bp andel underholdskostnad core
  protected List<UnderholdskostnadPeriodeCore> mapDelberegningUnderholdskostnad(
      BeregnetUnderholdskostnadResultatCore underholdskostnadResultatFraCore, Integer soknadsbarnPersonId) {
    return underholdskostnadResultatFraCore.getResultatPeriodeListe().stream()
        .filter(resultat -> resultat.getSoknadsbarnPersonId() == soknadsbarnPersonId)
        .map(resultat -> new UnderholdskostnadPeriodeCore(
            byggReferanseForDelberegning("Delberegning_BM_Underholdskostnad", soknadsbarnPersonId.toString(),
                resultat.getPeriode().getDatoFom()),
            new PeriodeCore(resultat.getPeriode().getDatoFom(), resultat.getPeriode().getDatoTil()),
            resultat.getResultat().getBelop()))
        .collect(toList());
  }

  // Løper gjennom output fra beregning av bidragsevne og bygger opp ny input-liste til barnebidrag core
  protected List<BidragsevnePeriodeCore> mapDelberegningBidragsevne(BeregnetBidragsevneResultatCore bidragsevneResultatFraCore) {
    return bidragsevneResultatFraCore.getResultatPeriodeListe().stream()
        .map(resultat -> new BidragsevnePeriodeCore(
            byggReferanseForDelberegning(resultat.getPeriode().getDatoFom()),
            new PeriodeCore(resultat.getPeriode().getDatoFom(), resultat.getPeriode().getDatoTil()),
            resultat.getResultat().getBelop(),
            resultat.getResultat().getInntekt25Prosent()))
        .collect(toList());
  }

  // Løper gjennom output fra beregning av BPs andel underholdskostnad og bygger opp ny input-liste til barnebidrag core
  protected List<BPsAndelUnderholdskostnadPeriodeCore> mapDelberegningBPAndelUnderholdskostnad(
      BeregnetBPsAndelUnderholdskostnadResultatCore bpAndelUnderholdskostnadResultatFraCore) {
    return bpAndelUnderholdskostnadResultatFraCore.getResultatPeriodeListe().stream()
        .map(resultat -> new no.nav.bidrag.beregn.barnebidrag.dto.BPsAndelUnderholdskostnadPeriodeCore(
            byggReferanseForDelberegning("Delberegning_BP_AndelUnderholdskostnad", String.valueOf(resultat.getSoknadsbarnPersonId()),
                resultat.getPeriode().getDatoFom()),
            resultat.getSoknadsbarnPersonId(),
            new PeriodeCore(resultat.getPeriode().getDatoFom(), resultat.getPeriode().getDatoTil()),
            resultat.getResultat().getAndelProsent(),
            resultat.getResultat().getAndelBelop(),
            resultat.getResultat().getBarnetErSelvforsorget()))
        .collect(toList());
  }

  // Løper gjennom output fra beregning av samværsfradrag og bygger opp ny input-liste til barnebidrag core
  protected List<SamvaersfradragPeriodeCore> mapDelberegningSamvaersfradrag(BeregnetSamvaersfradragResultatCore samvaersfradragResultatFraCore) {
    return samvaersfradragResultatFraCore.getResultatPeriodeListe().stream()
        .map(resultat -> new no.nav.bidrag.beregn.barnebidrag.dto.SamvaersfradragPeriodeCore(
            byggReferanseForDelberegning("Delberegning_BP_Samvaersfradrag", String.valueOf(resultat.getSoknadsbarnPersonId()),
                resultat.getPeriode().getDatoFom()),
            resultat.getSoknadsbarnPersonId(),
            new PeriodeCore(resultat.getPeriode().getDatoFom(), resultat.getPeriode().getDatoTil()),
            resultat.getResultat().getBelop()))
        .collect(toList());
  }

  // Mapper sjabloner av typen sjablontall
  // Filtrerer bort de sjablonene som ikke brukes i den aktuelle delberegningen og de som ikke er innenfor intervallet beregnDatoFra-beregnDatoTil
  protected List<SjablonPeriodeCore> mapSjablonSjablontall(List<Sjablontall> sjablonSjablontallListe, String delberegning,
      BeregnGrunnlag beregnBarnebidragGrunnlag, Map<String, SjablonTallNavn> sjablontallMap) {

    var beregnDatoFra = beregnBarnebidragGrunnlag.getBeregnDatoFra();
    var beregnDatoTil = beregnBarnebidragGrunnlag.getBeregnDatoTil();

    return sjablonSjablontallListe.stream()
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
  protected List<SjablonPeriodeCore> mapSjablonForbruksutgifter(List<Forbruksutgifter> sjablonForbruksutgifterListe,
      BeregnGrunnlag beregnBarnebidragGrunnlag) {

    var beregnDatoFra = beregnBarnebidragGrunnlag.getBeregnDatoFra();
    var beregnDatoTil = beregnBarnebidragGrunnlag.getBeregnDatoTil();

    return sjablonForbruksutgifterListe.stream()
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
  protected List<SjablonPeriodeCore> mapSjablonMaksTilsyn(List<MaksTilsyn> sjablonMaksTilsynListe,
      BeregnGrunnlag beregnBarnebidragGrunnlag) {

    var beregnDatoFra = beregnBarnebidragGrunnlag.getBeregnDatoFra();
    var beregnDatoTil = beregnBarnebidragGrunnlag.getBeregnDatoTil();

    return sjablonMaksTilsynListe.stream()
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
  protected List<SjablonPeriodeCore> mapSjablonMaksFradrag(List<MaksFradrag> sjablonMaksFradragListe,
      BeregnGrunnlag beregnBarnebidragGrunnlag) {

    var beregnDatoFra = beregnBarnebidragGrunnlag.getBeregnDatoFra();
    var beregnDatoTil = beregnBarnebidragGrunnlag.getBeregnDatoTil();

    return sjablonMaksFradragListe.stream()
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
  protected List<SjablonPeriodeCore> mapSjablonSamvaersfradrag(List<Samvaersfradrag> sjablonSamvaersfradragListe,
      BeregnGrunnlag beregnBarnebidragGrunnlag) {

    var beregnDatoFra = beregnBarnebidragGrunnlag.getBeregnDatoFra();
    var beregnDatoTil = beregnBarnebidragGrunnlag.getBeregnDatoTil();

    return sjablonSamvaersfradragListe.stream()
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
  protected List<SjablonPeriodeCore> mapSjablonBidragsevne(List<Bidragsevne> sjablonBidragsevneListe,
      BeregnGrunnlag beregnBarnebidragGrunnlag) {

    var beregnDatoFra = beregnBarnebidragGrunnlag.getBeregnDatoFra();
    var beregnDatoTil = beregnBarnebidragGrunnlag.getBeregnDatoTil();

    return sjablonBidragsevneListe.stream()
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
  protected List<SjablonPeriodeCore> mapSjablonTrinnvisSkattesats(List<TrinnvisSkattesats> sjablonTrinnvisSkattesatsListe,
      BeregnGrunnlag beregnBarnebidragGrunnlag) {

    var beregnDatoFra = beregnBarnebidragGrunnlag.getBeregnDatoFra();
    var beregnDatoTil = beregnBarnebidragGrunnlag.getBeregnDatoTil();

    return sjablonTrinnvisSkattesatsListe.stream()
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
  protected List<SjablonPeriodeCore> mapSjablonBarnetilsyn(List<Barnetilsyn> sjablonBarnetilsynListe,
      BeregnGrunnlag beregnBarnebidragGrunnlag) {

    var beregnDatoFra = beregnBarnebidragGrunnlag.getBeregnDatoFra();
    var beregnDatoTil = beregnBarnebidragGrunnlag.getBeregnDatoTil();

    return sjablonBarnetilsynListe.stream()
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

  // Bygger referanse for delberegning
  private String byggReferanseForDelberegning(String delberegning, String soknadsbarn, LocalDate dato) {
    return delberegning + "_SB" + soknadsbarn + "_" + dato.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
  }

  private String byggReferanseForDelberegning(LocalDate dato) {
    return "Delberegning_BP_Bidragsevne" + "_" + dato.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
  }
}
