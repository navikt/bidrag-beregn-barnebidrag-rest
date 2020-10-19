package no.nav.bidrag.beregn.barnebidrag.rest.service;

import java.time.LocalDate;
import java.util.Map;

public class SoknadsbarnUtil {

  public static LocalDate hentFodselsdatoForId(Integer soknadsbarnId, Map<Integer, LocalDate> soknadsbarnMap) {
    if (soknadsbarnMap.containsKey(soknadsbarnId)) {
      return soknadsbarnMap.get(soknadsbarnId);
    }
    return null;
  }
}
