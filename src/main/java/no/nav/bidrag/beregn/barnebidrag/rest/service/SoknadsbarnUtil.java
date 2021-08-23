package no.nav.bidrag.beregn.barnebidrag.rest.service;

import java.util.HashMap;
import java.util.Map;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.Grunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;

public class SoknadsbarnUtil {

  protected static final String SOKNADSBARN_INFO_TYPE = "SoknadsbarnInfo";

  public static String hentFodselsdatoForId(Integer soknadsbarnId, Map<Integer, String> soknadsbarnMap) {
    if (soknadsbarnMap.containsKey(soknadsbarnId)) {
      return soknadsbarnMap.get(soknadsbarnId);
    }
    return null;
  }

  // Lager en map for søknadsbarn (id og fødselsdato)
  protected static Map<Integer, String> mapSoknadsbarn(BeregnTotalBarnebidragGrunnlag beregnBarnebidragGrunnlag) {
    var soknadsbarnMap = new HashMap<Integer, String>();
    beregnBarnebidragGrunnlag.getGrunnlagListe().stream()
        .filter(grunnlag -> grunnlag.getType().equals(SOKNADSBARN_INFO_TYPE))
        .forEach(grunnlag -> soknadsbarnMap.putAll(mapSoknadsbarnInfo(grunnlag)));
    return soknadsbarnMap;
  }

  private static Map<Integer, String> mapSoknadsbarnInfo(Grunnlag grunnlag) {
    var soknadsbarnMap = new HashMap<Integer, String>();
    if (!grunnlag.getInnhold().has("soknadsbarnId")) {
      throw new UgyldigInputException("soknadsbarnId mangler i objekt av type SoknadsbarnInfo");
    } else if (!grunnlag.getInnhold().has("fodselsdato")) {
      throw new UgyldigInputException("fodselsdato mangler i objekt av type SoknadsbarnInfo");
    } else {
      var id = grunnlag.getInnhold().get("soknadsbarnId").asInt();
      var fodselsdato = grunnlag.getInnhold().get("fodselsdato").asText();
      soknadsbarnMap.put(id, fodselsdato);
    }
    return soknadsbarnMap;
  }

  // Validerer at alle forekomster av soknadsbarnId er numeriske og har tilknyttet fødselsdato
  protected static void validerSoknadsbarnId(BeregnTotalBarnebidragGrunnlag beregnBarnebidragGrunnlag) {
    var soknadsbarnMap = mapSoknadsbarn(beregnBarnebidragGrunnlag);
    beregnBarnebidragGrunnlag.getGrunnlagListe().forEach(grunnlag -> {
      if (grunnlag.getInnhold().has("soknadsbarnId")) {
        var soknadsbarnId = 0;
        if (grunnlag.getInnhold().get("soknadsbarnId").isNumber()) {
          soknadsbarnId = grunnlag.getInnhold().get("soknadsbarnId").asInt();
        } else {
          throw new UgyldigInputException("soknadsbarnId i objekt av type " + grunnlag.getType() + " er null eller har ugyldig verdi");
        }
        if (null == hentFodselsdatoForId(soknadsbarnId, soknadsbarnMap)) {
          throw new UgyldigInputException("fodselsdato for soknadsbarnId " + soknadsbarnId + " i objekt av type " + grunnlag.getType() + " mangler");
        }
      }
    });
  }
}
