package no.nav.bidrag.beregn.barnebidrag.rest.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonListe;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.Grunnlag;
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnSamvaersfradragGrunnlagCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.SamvaersklassePeriodeCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.SoknadsbarnCore;

public class SamvaersfradragCoreMapper extends CoreMapper {

  public BeregnSamvaersfradragGrunnlagCore mapSamvaersfradragGrunnlagTilCore(BeregnTotalBarnebidragGrunnlag beregnBarnebidragGrunnlag,
      SjablonListe sjablonListe, Integer soknadsbarnIdTilBehandling, Map<Integer, String> soknadsbarnMap) {

    var samvaersklassePeriodeCoreListe = new ArrayList<SamvaersklassePeriodeCore>();
    var soknadsbarnCore = new SoknadsbarnCore("", soknadsbarnIdTilBehandling,
        LocalDate.parse(hentFodselsdato(soknadsbarnIdTilBehandling, soknadsbarnMap)));

    // Løper gjennom alle grunnlagene og identifiserer de som skal mappes til samværsfradrag core
    for (Grunnlag grunnlag : beregnBarnebidragGrunnlag.getGrunnlagListe()) {
      if (SAMVAERSKLASSE_TYPE.equals(grunnlag.getType())) {
        var soknadsbarnId = hentSoknadsbarnId(grunnlag.getInnhold(), grunnlag.getType());
        // Søknadsbarn må matche
        if (Integer.valueOf(soknadsbarnId).equals(soknadsbarnIdTilBehandling)) {
          samvaersklassePeriodeCoreListe.add(mapSamvaersklasse(grunnlag));
        }
      }
    }

    // Henter aktuelle sjabloner
    var sjablonPeriodeCoreListe = new ArrayList<>(mapSjablonSamvaersfradrag(sjablonListe.getSjablonSamvaersfradragResponse(),
        beregnBarnebidragGrunnlag));

    return new BeregnSamvaersfradragGrunnlagCore(beregnBarnebidragGrunnlag.getBeregnDatoFra(), beregnBarnebidragGrunnlag.getBeregnDatoTil(),
        soknadsbarnCore, samvaersklassePeriodeCoreListe, sjablonPeriodeCoreListe);
  }
}
