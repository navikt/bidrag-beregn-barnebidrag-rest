package no.nav.bidrag.beregn.barnebidrag.rest.mapper;

import static no.nav.bidrag.beregn.barnebidrag.rest.service.BeregnBarnebidragService.BARNEBIDRAG;

import java.util.ArrayList;
import no.nav.bidrag.beregn.barnebidrag.dto.AndreLopendeBidragPeriodeCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BarnetilleggForsvaretPeriodeCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BarnetilleggPeriodeCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BeregnBarnebidragGrunnlagCore;
import no.nav.bidrag.beregn.barnebidrag.dto.DeltBostedPeriodeCore;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonListe;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.Grunnlag;
import no.nav.bidrag.beregn.bidragsevne.dto.BeregnetBidragsevneResultatCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnetBPsAndelUnderholdskostnadResultatCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnetSamvaersfradragResultatCore;

public class BarnebidragCoreMapper extends CoreMapper {

  public BeregnBarnebidragGrunnlagCore mapBarnebidragGrunnlagTilCore(BeregnGrunnlag beregnBarnebidragGrunnlag,
      SjablonListe sjablonListe, BeregnetBidragsevneResultatCore bidragsevneResultatFraCore,
      BeregnetBPsAndelUnderholdskostnadResultatCore bpAndelUnderholdskostnadResultatFraCore,
      BeregnetSamvaersfradragResultatCore samvaersfradragResultatFraCore) {

    var deltBostedPeriodeCoreListe = new ArrayList<DeltBostedPeriodeCore>();
    var barnetilleggBPPeriodeCoreListe = new ArrayList<BarnetilleggPeriodeCore>();
    var barnetilleggBMPeriodeCoreListe = new ArrayList<BarnetilleggPeriodeCore>();
    var barnetilleggForsvaretPeriodeCoreListe = new ArrayList<BarnetilleggForsvaretPeriodeCore>();
    var andreLopendeBidragPeriodeCoreListe = new ArrayList<AndreLopendeBidragPeriodeCore>();

    // Løper gjennom alle grunnlagene og identifiserer de som skal mappes til barnebidrag core
    for (Grunnlag grunnlag : beregnBarnebidragGrunnlag.getGrunnlagListe()) {
      switch (grunnlag.getType()) {
        case BARNETILLEGG_TYPE -> {
          var rolle = mapText(grunnlag.getInnhold(), "rolle", grunnlag.getType());
          var soknadsbarnId = hentSoknadsbarnId(grunnlag.getInnhold(), grunnlag.getType());
          switch (rolle) {
            case BIDRAGSPLIKTIG -> barnetilleggBPPeriodeCoreListe.add(mapBarnetillegg(grunnlag, Integer.valueOf(soknadsbarnId)));
            case BIDRAGSMOTTAKER -> barnetilleggBMPeriodeCoreListe.add(mapBarnetillegg(grunnlag, Integer.valueOf(soknadsbarnId)));
          }
        }
        case BARNETILLEGG_FORSVARET_TYPE -> barnetilleggForsvaretPeriodeCoreListe.add(mapBarnetilleggForsvaret(grunnlag));
        case DELT_BOSTED_TYPE -> {
          var soknadsbarnId = hentSoknadsbarnId(grunnlag.getInnhold(), grunnlag.getType());
          deltBostedPeriodeCoreListe.add(mapDeltBosted(grunnlag, Integer.valueOf(soknadsbarnId)));
        }
        case ANDRE_LOPENDE_BIDRAG_TYPE -> {
          var soknadsbarnId = hentSoknadsbarnId(grunnlag.getInnhold(), grunnlag.getType());
          andreLopendeBidragPeriodeCoreListe.add(mapAndreLopendeBidrag(grunnlag, Integer.valueOf(soknadsbarnId)));
        }
      }
    }

    // Bygger opp lister basert på resultatet av tidligere delberegninger
    var bidragsevnePeriodeCoreListe = mapDelberegningBidragsevne(bidragsevneResultatFraCore);
    var bpAndelUnderholdskostnadPeriodeCoreListe = mapDelberegningBPAndelUnderholdskostnad(bpAndelUnderholdskostnadResultatFraCore);
    var samvaersfradragPeriodeCoreListe = mapDelberegningSamvaersfradrag(samvaersfradragResultatFraCore);

    // Henter aktuelle sjabloner
    var sjablonPeriodeCoreListe = new ArrayList<>(mapSjablonSjablontall(sjablonListe.getSjablonSjablontallResponse(), BARNEBIDRAG,
        beregnBarnebidragGrunnlag, mapSjablontall()));

    return new BeregnBarnebidragGrunnlagCore(beregnBarnebidragGrunnlag.getBeregnDatoFra(), beregnBarnebidragGrunnlag.getBeregnDatoTil(),
        bidragsevnePeriodeCoreListe, bpAndelUnderholdskostnadPeriodeCoreListe, samvaersfradragPeriodeCoreListe, deltBostedPeriodeCoreListe,
        barnetilleggBPPeriodeCoreListe, barnetilleggBMPeriodeCoreListe, barnetilleggForsvaretPeriodeCoreListe, andreLopendeBidragPeriodeCoreListe,
        sjablonPeriodeCoreListe);
  }
}
