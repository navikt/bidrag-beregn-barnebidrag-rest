package no.nav.bidrag.beregn.barnebidrag.rest.mapper;

import static no.nav.bidrag.beregn.barnebidrag.rest.service.BeregnBarnebidragService.NETTO_BARNETILSYN;

import java.util.ArrayList;
import java.util.Map;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonListe;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.Grunnlag;
import no.nav.bidrag.beregn.felles.dto.SjablonPeriodeCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnNettoBarnetilsynGrunnlagCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.FaktiskUtgiftPeriodeCore;

public class NettoBarnetilsynCoreMapper extends CoreMapper {

  public BeregnNettoBarnetilsynGrunnlagCore mapNettoBarnetilsynGrunnlagTilCore(BeregnTotalBarnebidragGrunnlag beregnBarnebidragGrunnlag,
      SjablonListe sjablonListe, Map<Integer, String> soknadsbarnMap) {

    var faktiskUtgiftPeriodeCoreListe = new ArrayList<FaktiskUtgiftPeriodeCore>();
    var sjablonPeriodeCoreListe = new ArrayList<SjablonPeriodeCore>();

    // Løper gjennom alle grunnlagene og identifiserer de som skal mappes til netto barnetilsyn core
    for (Grunnlag grunnlag : beregnBarnebidragGrunnlag.getGrunnlagListe()) {
      if (FAKTISK_UTGIFT_TYPE.equals(grunnlag.getType())) {
        var soknadsbarnId = hentSoknadsbarnId(grunnlag.getInnhold(), grunnlag.getType());
        faktiskUtgiftPeriodeCoreListe.add(mapFaktiskUtgift(grunnlag, Integer.valueOf(soknadsbarnId), soknadsbarnMap));
      }
    }

    // Henter aktuelle sjabloner
    sjablonPeriodeCoreListe.addAll(mapSjablonSjablontall(sjablonListe.getSjablonSjablontallResponse(), NETTO_BARNETILSYN, beregnBarnebidragGrunnlag,
        mapSjablontall()));
    sjablonPeriodeCoreListe.addAll(mapSjablonMaksTilsyn(sjablonListe.getSjablonMaksTilsynResponse(), beregnBarnebidragGrunnlag));
    sjablonPeriodeCoreListe.addAll(mapSjablonMaksFradrag(sjablonListe.getSjablonMaksFradragResponse(), beregnBarnebidragGrunnlag));

    return new BeregnNettoBarnetilsynGrunnlagCore(beregnBarnebidragGrunnlag.getBeregnDatoFra(), beregnBarnebidragGrunnlag.getBeregnDatoTil(),
        faktiskUtgiftPeriodeCoreListe, sjablonPeriodeCoreListe);
  }
}
