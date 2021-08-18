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
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.Grunnlag;
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
    String inntektType;
    if (grunnlag.getInnhold().has("inntektType")) {
      inntektType = grunnlag.getInnhold().get("inntektType").asText();
      evaluerStringType(inntektType, "inntektType", "Inntekt");
    } else {
      throw new UgyldigInputException("inntektType i objekt av type Inntekt mangler");
    }

    String belop;
    if (grunnlag.getInnhold().has("belop") && grunnlag.getInnhold().get("belop").isNumber()) {
      belop = grunnlag.getInnhold().get("belop").asText();
    } else {
      throw new UgyldigInputException("belop i objekt av type Inntekt mangler, er null eller har ugyldig verdi");
    }

    return new no.nav.bidrag.beregn.bidragsevne.dto.InntektPeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()),
        inntektType, new BigDecimal(belop));
  }

  protected no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.InntektPeriodeCore mapInntektBPAndelUnderholdskostnad(Grunnlag grunnlag) {
    String inntektType;
    if (grunnlag.getInnhold().has("inntektType")) {
      inntektType = grunnlag.getInnhold().get("inntektType").asText();
      evaluerStringType(inntektType, "inntektType", "Inntekt");
    } else {
      throw new UgyldigInputException("inntektType i objekt av type Inntekt mangler");
    }

    String belop;
    if (grunnlag.getInnhold().has("belop") && grunnlag.getInnhold().get("belop").isNumber()) {
      belop = grunnlag.getInnhold().get("belop").asText();
    } else {
      throw new UgyldigInputException("belop i objekt av type Inntekt mangler, er null eller har ugyldig verdi");
    }
    var deltFordel = grunnlag.getInnhold().has("deltFordel") && grunnlag.getInnhold().get("deltFordel").asBoolean();
    var skatteklasse2 = grunnlag.getInnhold().has("skatteklasse2") && grunnlag.getInnhold().get("skatteklasse2").asBoolean();

    return new no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.InntektPeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(),
        grunnlag.getType()), inntektType, new BigDecimal(belop), deltFordel, skatteklasse2);
  }

  protected BostatusPeriodeCore mapBostatus(Grunnlag grunnlag) {
    String bostatusKode;
    if (grunnlag.getInnhold().has("bostatusKode")) {
      bostatusKode = grunnlag.getInnhold().get("bostatusKode").asText();
      evaluerStringType(bostatusKode, "bostatusKode", "Bostatus");
    } else {
      throw new UgyldigInputException("bostatusKode i objekt av type Bostatus mangler");
    }

    return new BostatusPeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()), bostatusKode);
  }

  protected BarnIHusstandPeriodeCore mapBarnIHusstand(Grunnlag grunnlag) {
    String antallBarn;
    if (grunnlag.getInnhold().has("antall") && grunnlag.getInnhold().get("antall").isNumber()) {
      antallBarn = grunnlag.getInnhold().get("antall").asText();
    } else {
      throw new UgyldigInputException("antall i objekt av type BarnIHusstand mangler, er null eller har ugyldig verdi");
    }

    return new BarnIHusstandPeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()),
        Double.parseDouble(antallBarn));
  }

  protected SaerfradragPeriodeCore mapSaerfradrag(Grunnlag grunnlag) {
    String saerfradragKode;
    if (grunnlag.getInnhold().has("saerfradragKode")) {
      saerfradragKode = grunnlag.getInnhold().get("saerfradragKode").asText();
      evaluerStringType(saerfradragKode, "saerfradragKode", "Saerfradrag");
    } else {
      throw new UgyldigInputException("saerfradragKode i objekt av type Saerfradrag mangler");
    }

    return new SaerfradragPeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()), saerfradragKode);
  }

  protected SkatteklassePeriodeCore mapSkatteklasse(Grunnlag grunnlag) {
    String skatteklasseId;
    if (grunnlag.getInnhold().has("skatteklasseId") && grunnlag.getInnhold().get("skatteklasseId").isNumber()) {
      skatteklasseId = grunnlag.getInnhold().get("skatteklasseId").asText();
    } else {
      throw new UgyldigInputException("skatteklasseId i objekt av type Skatteklasse mangler, er null eller har ugyldig verdi");
    }

    return new SkatteklassePeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()),
        Integer.parseInt(skatteklasseId));
  }

  protected FaktiskUtgiftPeriodeCore mapFaktiskUtgift(Grunnlag grunnlag, Integer soknadsbarnPersonId, Map<Integer, String> soknadsbarnMap) {
    String belop;
    if (grunnlag.getInnhold().has("belop") && grunnlag.getInnhold().get("belop").isNumber()) {
      belop = grunnlag.getInnhold().get("belop").asText();
    } else {
      throw new UgyldigInputException("belop i objekt av type FaktiskUtgift mangler, er null eller har ugyldig verdi");
    }

    var soknadsbarnFodselsdato = hentFodselsdato(soknadsbarnPersonId, soknadsbarnMap);

    return new FaktiskUtgiftPeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()),
        LocalDate.parse(soknadsbarnFodselsdato), soknadsbarnPersonId, new BigDecimal(belop));
  }

  protected BarnetilsynMedStonadPeriodeCore mapBarnetilsynMedStonad(Grunnlag grunnlag) {
    String stonadType;
    if (grunnlag.getInnhold().has("stonadType")) {
      stonadType = grunnlag.getInnhold().get("stonadType").asText();
      evaluerStringType(stonadType, "stonadType", "BarnetilsynMedStonad");
    } else {
      throw new UgyldigInputException("stonadType i objekt av type BarnetilsynMedStonad mangler");
    }

    String tilsynType;
    if (grunnlag.getInnhold().has("tilsynType")) {
      tilsynType = grunnlag.getInnhold().get("tilsynType").asText();
      evaluerStringType(tilsynType, "tilsynType", "BarnetilsynMedStonad");
    } else {
      throw new UgyldigInputException("tilsynType i objekt av type BarnetilsynMedStonad mangler");
    }

    return new BarnetilsynMedStonadPeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()), tilsynType,
        stonadType);
  }

  protected ForpleiningUtgiftPeriodeCore mapForpleiningUtgift(Grunnlag grunnlag) {
    String belop;
    if (grunnlag.getInnhold().has("belop") && grunnlag.getInnhold().get("belop").isNumber()) {
      belop = grunnlag.getInnhold().get("belop").asText();
    } else {
      throw new UgyldigInputException("belop i objekt av type ForpleiningUtgift mangler, er null eller har ugyldig verdi");
    }

    return new ForpleiningUtgiftPeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()),
        new BigDecimal(belop));
  }

  protected SamvaersklassePeriodeCore mapSamvaersklasse(Grunnlag grunnlag) {
    String samvaersklasseId;
    if (grunnlag.getInnhold().has("samvaersklasseId")) {
      samvaersklasseId = grunnlag.getInnhold().get("samvaersklasseId").asText();
      evaluerStringType(samvaersklasseId, "samvaersklasseId", "Samvaersklasse");
    } else {
      throw new UgyldigInputException("samvaersklasseId i objekt av type Samvaersklasse mangler");
    }

    return new SamvaersklassePeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()), samvaersklasseId);
  }

  protected BarnetilleggPeriodeCore mapBarnetillegg(Grunnlag grunnlag, Integer soknadsbarnPersonId) {
    String bruttoBelop;
    if (grunnlag.getInnhold().has("bruttoBelop") && grunnlag.getInnhold().get("bruttoBelop").isNumber()) {
      bruttoBelop = grunnlag.getInnhold().get("bruttoBelop").asText();
    } else {
      throw new UgyldigInputException("bruttoBelop i objekt av type Barnetillegg mangler, er null eller har ugyldig verdi");
    }

    String skattProsent;
    if (grunnlag.getInnhold().has("skattProsent") && grunnlag.getInnhold().get("skattProsent").isNumber()) {
      skattProsent = grunnlag.getInnhold().get("skattProsent").asText();
    } else {
      throw new UgyldigInputException("skattProsent i objekt av type Barnetillegg mangler, er null eller har ugyldig verdi");
    }

    return new BarnetilleggPeriodeCore(grunnlag.getReferanse(), soknadsbarnPersonId, mapPeriode(grunnlag.getInnhold(), grunnlag.getType()),
        new BigDecimal(bruttoBelop), new BigDecimal(skattProsent));
  }

  protected BarnetilleggForsvaretPeriodeCore mapBarnetilleggForsvaret(Grunnlag grunnlag) {
    var barnetilleggForsvaret = grunnlag.getInnhold().has("barnetilleggForsvaret") && grunnlag.getInnhold().get("barnetilleggForsvaret").asBoolean();

    return new BarnetilleggForsvaretPeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()),
        barnetilleggForsvaret);
  }

  protected DeltBostedPeriodeCore mapDeltBosted(Grunnlag grunnlag, Integer soknadsbarnPersonId) {
    var deltBosted = grunnlag.getInnhold().has("deltBosted") && grunnlag.getInnhold().get("deltBosted").asBoolean();

    return new DeltBostedPeriodeCore(grunnlag.getReferanse(), soknadsbarnPersonId, mapPeriode(grunnlag.getInnhold(), grunnlag.getType()),
        deltBosted);
  }

  protected AndreLopendeBidragPeriodeCore mapAndreLopendeBidrag(Grunnlag grunnlag, Integer barnPersonId) {
    String bidragBelop;
    if (grunnlag.getInnhold().has("bidragBelop") && grunnlag.getInnhold().get("bidragBelop").isNumber()) {
      bidragBelop = grunnlag.getInnhold().get("bidragBelop").asText();
    } else {
      throw new UgyldigInputException("bidragBelop i objekt av type AndreLopendeBidrag mangler, er null eller har ugyldig verdi");
    }

    String samvaersfradragBelop;
    if (grunnlag.getInnhold().has("samvaersfradragBelop") && grunnlag.getInnhold().get("samvaersfradragBelop").isNumber()) {
      samvaersfradragBelop = grunnlag.getInnhold().get("samvaersfradragBelop").asText();
    } else {
      throw new UgyldigInputException("samvaersfradragBelop i objekt av type AndreLopendeBidrag mangler, er null eller har ugyldig verdi");
    }

    return new AndreLopendeBidragPeriodeCore(grunnlag.getReferanse(), mapPeriode(grunnlag.getInnhold(), grunnlag.getType()), barnPersonId,
        new BigDecimal(bidragBelop), new BigDecimal(samvaersfradragBelop));
  }

  protected PeriodeCore mapPeriode(JsonNode grunnlagInnhold, String grunnlagType) {
    String datoFom;
    if (grunnlagInnhold.has("datoFom")) {
      datoFom = grunnlagInnhold.get("datoFom").asText();
      if (!gyldigDato(datoFom)) {
        throw new UgyldigInputException("datoFom i objekt av type " + grunnlagType + " mangler, er null eller har ugyldig verdi");
      }
    } else {
      throw new UgyldigInputException("datoFom i objekt av type " + grunnlagType + " mangler, er null eller har ugyldig verdi");
    }

    String datoTil;
    if (grunnlagInnhold.has("datoTil")) {
      datoTil = grunnlagInnhold.get("datoTil").asText();
      if (!gyldigDato(datoTil)) {
        throw new UgyldigInputException("datoTil i objekt av type " + grunnlagType + " mangler, er null eller har ugyldig verdi");
      }
    } else {
      throw new UgyldigInputException("datoTil i objekt av type " + grunnlagType + " mangler, er null eller har ugyldig verdi");
    }

    return new PeriodeCore(LocalDate.parse(datoFom), LocalDate.parse(datoTil));
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

  protected String hentRolle(JsonNode grunnlagInnhold, String grunnlagType) {
    String rolle;
    if (grunnlagInnhold.has("rolle")) {
      rolle = grunnlagInnhold.get("rolle").asText();
      evaluerStringType(rolle, "rolle", grunnlagType);
    } else {
      throw new UgyldigInputException("rolle i objekt av type " + grunnlagType + " mangler");
    }

    return rolle;
  }

  protected String hentFodselsdato(Integer soknadsbarnPersonId, Map<Integer, String> soknadsbarnMap) {
    var soknadsbarnFodselsdato = Optional.ofNullable(SoknadsbarnUtil.hentFodselsdatoForId(soknadsbarnPersonId, soknadsbarnMap))
        .orElseThrow(() ->
            new UgyldigInputException("Søknadsbarn med id " + soknadsbarnPersonId + " har ingen korresponderende fødselsdato i soknadsbarnListe"));
    if (!gyldigDato(soknadsbarnFodselsdato)) {
      throw new UgyldigInputException("fodselsdato i objekt av type soknadsbarnInfo mangler, er null eller har ugyldig verdi");
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
            byggReferanseForDelberegning("Delberegning_BP_Bidragsevne", resultat.getPeriode().getDatoFom()),
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
      BeregnTotalBarnebidragGrunnlag beregnBarnebidragGrunnlag, Map<String, SjablonTallNavn> sjablontallMap) {

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
      BeregnTotalBarnebidragGrunnlag beregnBarnebidragGrunnlag) {

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
      BeregnTotalBarnebidragGrunnlag beregnBarnebidragGrunnlag) {

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
      BeregnTotalBarnebidragGrunnlag beregnBarnebidragGrunnlag) {

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
      BeregnTotalBarnebidragGrunnlag beregnBarnebidragGrunnlag) {

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
      BeregnTotalBarnebidragGrunnlag beregnBarnebidragGrunnlag) {

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
      BeregnTotalBarnebidragGrunnlag beregnBarnebidragGrunnlag) {

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
      BeregnTotalBarnebidragGrunnlag beregnBarnebidragGrunnlag) {

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

  private String byggReferanseForDelberegning(String delberegning, LocalDate dato) {
    return delberegning + "_" + dato.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
  }
}
