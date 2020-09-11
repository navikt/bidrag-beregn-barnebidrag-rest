package no.nav.bidrag.beregn.barnebidrag.rest;

import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBarnebidragGrunnlagCore;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBarnebidragResultatCore;

public interface BarnebidragCore {

  BeregnBarnebidragResultatCore beregnBarnebidrag(
      BeregnBarnebidragGrunnlagCore beregnBarnebidragGrunnlagCore);

  static BarnebidragCore getInstance() {
    return new BarnebidragCoreImpl();
  }
}
