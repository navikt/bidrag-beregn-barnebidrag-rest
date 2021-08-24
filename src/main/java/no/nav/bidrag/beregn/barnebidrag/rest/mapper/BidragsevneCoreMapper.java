package no.nav.bidrag.beregn.barnebidrag.rest.mapper;

import static no.nav.bidrag.beregn.barnebidrag.rest.service.BeregnBarnebidragService.BIDRAGSEVNE;

import java.util.ArrayList;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonListe;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.Grunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;
import no.nav.bidrag.beregn.bidragsevne.dto.BarnIHusstandPeriodeCore;
import no.nav.bidrag.beregn.bidragsevne.dto.BeregnBidragsevneGrunnlagCore;
import no.nav.bidrag.beregn.bidragsevne.dto.BostatusPeriodeCore;
import no.nav.bidrag.beregn.bidragsevne.dto.InntektPeriodeCore;
import no.nav.bidrag.beregn.bidragsevne.dto.SaerfradragPeriodeCore;
import no.nav.bidrag.beregn.bidragsevne.dto.SkatteklassePeriodeCore;
import no.nav.bidrag.beregn.felles.dto.SjablonPeriodeCore;

public class BidragsevneCoreMapper extends CoreMapper {

  public BeregnBidragsevneGrunnlagCore mapBidragsevneGrunnlagTilCore(BeregnTotalBarnebidragGrunnlag beregnBarnebidragGrunnlag,
      SjablonListe sjablonListe) {

    var inntektPeriodeCoreListe = new ArrayList<InntektPeriodeCore>();
    var barnIHusstandPeriodeCoreListe = new ArrayList<BarnIHusstandPeriodeCore>();
    var bostatusPeriodeCoreListe = new ArrayList<BostatusPeriodeCore>();
    var saerfradragPeriodeCoreListe = new ArrayList<SaerfradragPeriodeCore>();
    var skatteklassePeriodeCoreListe = new ArrayList<SkatteklassePeriodeCore>();
    var sjablonPeriodeCoreListe = new ArrayList<SjablonPeriodeCore>();

    // Løper gjennom alle grunnlagene og identifiserer de som skal mappes til bidragsevne core
    for (Grunnlag grunnlag : beregnBarnebidragGrunnlag.getGrunnlagListe()) {
      switch (grunnlag.getType()) {
        case INNTEKT_TYPE -> {
          String rolle;
          if (grunnlag.getInnhold().has("rolle")) {
            rolle = grunnlag.getInnhold().get("rolle").asText();
            evaluerStringType(rolle, "rolle", "Inntekt");
          } else {
            throw new UgyldigInputException("rolle i objekt av type Inntekt mangler");
          }
          if (BIDRAGSPLIKTIG.equals(rolle)) {
            inntektPeriodeCoreListe.add(mapInntektBidragsevne(grunnlag));
          }
        }
        case BARN_I_HUSSTAND_TYPE -> barnIHusstandPeriodeCoreListe.add(mapBarnIHusstand(grunnlag));
        case BOSTATUS_TYPE -> bostatusPeriodeCoreListe.add(mapBostatus(grunnlag));
        case SAERFRADRAG_TYPE -> saerfradragPeriodeCoreListe.add(mapSaerfradrag(grunnlag));
        case SKATTEKLASSE_TYPE -> skatteklassePeriodeCoreListe.add(mapSkatteklasse(grunnlag));
      }
    }

    // Henter aktuelle sjabloner
    sjablonPeriodeCoreListe.addAll(mapSjablonSjablontall(sjablonListe.getSjablonSjablontallResponse(), BIDRAGSEVNE, beregnBarnebidragGrunnlag,
            mapSjablontall()));
    sjablonPeriodeCoreListe.addAll(mapSjablonBidragsevne(sjablonListe.getSjablonBidragsevneResponse(), beregnBarnebidragGrunnlag));
    sjablonPeriodeCoreListe.addAll(mapSjablonTrinnvisSkattesats(sjablonListe.getSjablonTrinnvisSkattesatsResponse(), beregnBarnebidragGrunnlag));

    return new BeregnBidragsevneGrunnlagCore(beregnBarnebidragGrunnlag.getBeregnDatoFra(), beregnBarnebidragGrunnlag.getBeregnDatoTil(),
        inntektPeriodeCoreListe, skatteklassePeriodeCoreListe, bostatusPeriodeCoreListe, barnIHusstandPeriodeCoreListe, saerfradragPeriodeCoreListe,
        sjablonPeriodeCoreListe);
  }
}
