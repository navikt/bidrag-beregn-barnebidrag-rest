package no.nav.bidrag.beregn.barnebidrag.rest.mapper;

import static no.nav.bidrag.beregn.barnebidrag.rest.service.BeregnBarnebidragService.BP_ANDEL_UNDERHOLDSKOSTNAD;

import java.util.ArrayList;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonListe;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.Grunnlag;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnBPsAndelUnderholdskostnadGrunnlagCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.InntektPeriodeCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnetUnderholdskostnadResultatCore;

public class BPAndelUnderholdskostnadCoreMapper extends CoreMapper {

  public BeregnBPsAndelUnderholdskostnadGrunnlagCore mapBPAndelUnderholdskostnadGrunnlagTilCore(
      BeregnTotalBarnebidragGrunnlag beregnBarnebidragGrunnlag, SjablonListe sjablonListe, Integer soknadsbarnIdTilBehandling,
      BeregnetUnderholdskostnadResultatCore underholdskostnadResultatFraCore) {

    var inntektBPPeriodeCoreListe = new ArrayList<InntektPeriodeCore>();
    var inntektBMPeriodeCoreListe = new ArrayList<InntektPeriodeCore>();
    var inntektSBPeriodeCoreListe = new ArrayList<InntektPeriodeCore>();

    // Løper gjennom alle grunnlagene og identifiserer de som skal mappes til BPs andel underholdskostnad core.
    for (Grunnlag grunnlag : beregnBarnebidragGrunnlag.getGrunnlagListe()) {
      if (INNTEKT_TYPE.equals(grunnlag.getType())) {
        var rolle = hentRolle(grunnlag.getInnhold(), grunnlag.getType());
        switch (rolle) {
          case BIDRAGSPLIKTIG -> inntektBPPeriodeCoreListe.add(mapInntektBPAndelUnderholdskostnad(grunnlag));
          case BIDRAGSMOTTAKER -> {
            var soknadsbarnId = grunnlag.getInnhold().has("soknadsbarnId") ? grunnlag.getInnhold().get("soknadsbarnId").asInt() : 0;
            // Søknadsbarn må enten matche eller mangle
            if (soknadsbarnId == soknadsbarnIdTilBehandling || soknadsbarnId == 0) {
              inntektBMPeriodeCoreListe.add(mapInntektBPAndelUnderholdskostnad(grunnlag));
            }
          }
          case SOKNADSBARN -> {
            var soknadsbarnId = hentSoknadsbarnId(grunnlag.getInnhold(), grunnlag.getType());
            // Søknadsbarn må matche
            if (Integer.valueOf(soknadsbarnId).equals(soknadsbarnIdTilBehandling)) {
              inntektSBPeriodeCoreListe.add(mapInntektBPAndelUnderholdskostnad(grunnlag));
            }
          }
        }
      }
    }

    // Filtrerer underholdskostnad på soknadsbarn id
    var filtrertUnderholdskostnadCoreListe = mapDelberegningUnderholdskostnad(underholdskostnadResultatFraCore, soknadsbarnIdTilBehandling);

    // Henter aktuelle sjabloner
    var sjablonPeriodeCoreListe = new ArrayList<>(mapSjablonSjablontall(sjablonListe.getSjablonSjablontallResponse(), BP_ANDEL_UNDERHOLDSKOSTNAD,
            beregnBarnebidragGrunnlag, mapSjablontall()));

    return new BeregnBPsAndelUnderholdskostnadGrunnlagCore(beregnBarnebidragGrunnlag.getBeregnDatoFra(),
        beregnBarnebidragGrunnlag.getBeregnDatoTil(), soknadsbarnIdTilBehandling, filtrertUnderholdskostnadCoreListe, inntektBPPeriodeCoreListe,
        inntektBMPeriodeCoreListe, inntektSBPeriodeCoreListe, sjablonPeriodeCoreListe);
  }
}
