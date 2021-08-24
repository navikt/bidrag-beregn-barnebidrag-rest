package no.nav.bidrag.beregn.barnebidrag.rest.mapper;

import static no.nav.bidrag.beregn.barnebidrag.rest.service.BeregnBarnebidragService.UNDERHOLDSKOSTNAD;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonListe;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.Grunnlag;
import no.nav.bidrag.beregn.felles.dto.SjablonPeriodeCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnetNettoBarnetilsynResultatCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BarnetilsynMedStonadPeriodeCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadGrunnlagCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.ForpleiningUtgiftPeriodeCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.SoknadsbarnCore;

public class UnderholdskostnadCoreMapper extends CoreMapper {

  public BeregnUnderholdskostnadGrunnlagCore mapUnderholdskostnadGrunnlagTilCore(BeregnTotalBarnebidragGrunnlag beregnBarnebidragGrunnlag,
      SjablonListe sjablonListe, Integer soknadsbarnIdTilBehandling, BeregnetNettoBarnetilsynResultatCore nettoBarnetilsynResultatFraCore,
      Map<Integer, String> soknadsbarnMap) {

    var barnetilsynMedStonadPeriodeCoreListe = new ArrayList<BarnetilsynMedStonadPeriodeCore>();
    var forpleiningUtgiftPeriodeCoreListe = new ArrayList<ForpleiningUtgiftPeriodeCore>();
    var sjablonPeriodeCoreListe = new ArrayList<SjablonPeriodeCore>();
    var soknadsbarnInfoReferanse = "";

    // Løper gjennom alle grunnlagene og identifiserer de som skal mappes til underholdskostnad core
    for (Grunnlag grunnlag : beregnBarnebidragGrunnlag.getGrunnlagListe()) {
      switch (grunnlag.getType()) {
        case SOKNADSBARN_INFO -> {
          var soknadsbarnId = hentSoknadsbarnId(grunnlag.getInnhold(), grunnlag.getType());
          // Søknadsbarn må matche
          if (Integer.valueOf(soknadsbarnId).equals(soknadsbarnIdTilBehandling)) {
            soknadsbarnInfoReferanse = grunnlag.getReferanse();
          }
        }
        case BARNETILSYN_MED_STONAD_TYPE -> {
          var soknadsbarnId = hentSoknadsbarnId(grunnlag.getInnhold(), grunnlag.getType());
          // Søknadsbarn må matche
          if (Integer.valueOf(soknadsbarnId).equals(soknadsbarnIdTilBehandling)) {
            barnetilsynMedStonadPeriodeCoreListe.add(mapBarnetilsynMedStonad(grunnlag));
          }
        }
        case FORPLEINING_UTGIFT_TYPE -> {
          var soknadsbarnId = hentSoknadsbarnId(grunnlag.getInnhold(), grunnlag.getType());
          // Søknadsbarn må matche
          if (Integer.valueOf(soknadsbarnId).equals(soknadsbarnIdTilBehandling)) {
            forpleiningUtgiftPeriodeCoreListe.add(mapForpleiningUtgift(grunnlag));
          }
        }
      }
    }

    var soknadsbarnCore = new SoknadsbarnCore(soknadsbarnInfoReferanse, soknadsbarnIdTilBehandling,
        LocalDate.parse(hentFodselsdato(soknadsbarnIdTilBehandling, soknadsbarnMap)));

    // Filtrerer netto barnetilsyn på soknadsbarn id
    var filtrertNettoBarnetilsynCoreListe = mapDelberegningNettoBarnetilsyn(nettoBarnetilsynResultatFraCore, soknadsbarnIdTilBehandling);

    // Henter aktuelle sjabloner
    sjablonPeriodeCoreListe.addAll(mapSjablonSjablontall(sjablonListe.getSjablonSjablontallResponse(), UNDERHOLDSKOSTNAD, beregnBarnebidragGrunnlag,
        mapSjablontall()));
    sjablonPeriodeCoreListe.addAll(mapSjablonForbruksutgifter(sjablonListe.getSjablonForbruksutgifterResponse(), beregnBarnebidragGrunnlag));
    sjablonPeriodeCoreListe.addAll(mapSjablonBarnetilsyn(sjablonListe.getSjablonBarnetilsynResponse(), beregnBarnebidragGrunnlag));

    return new BeregnUnderholdskostnadGrunnlagCore(beregnBarnebidragGrunnlag.getBeregnDatoFra(), beregnBarnebidragGrunnlag.getBeregnDatoTil(),
        soknadsbarnCore, barnetilsynMedStonadPeriodeCoreListe, filtrertNettoBarnetilsynCoreListe, forpleiningUtgiftPeriodeCoreListe,
        sjablonPeriodeCoreListe);
  }
}
