package no.nav.bidrag.beregn.barnebidrag.rest.mapper;

import static java.util.Comparator.comparing;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnGrunnlagFF;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnetBidragSakPeriode;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnetForholdsmessigFordelingResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.GrunnlagFF;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.Periode;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatBeregning;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.ResultatPeriodeFF;
import no.nav.bidrag.beregn.felles.dto.PeriodeCore;
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.BeregnForholdsmessigFordelingGrunnlagCore;
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.BeregnForholdsmessigFordelingResultatCore;
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.BeregnetBidragSakPeriodeCore;
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.BidragsevnePeriodeCore;
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.GrunnlagPerBarnCore;

public class ForholdsmessigFordelingCoreMapper extends CoreMapper {

  public BeregnForholdsmessigFordelingGrunnlagCore mapForholdsmessigFordelingGrunnlagTilCore(BeregnGrunnlagFF beregnGrunnlag) {

    var bidragsevnePeriodeCoreListe = new ArrayList<BidragsevnePeriodeCore>();
    var beregnetBidragSakPeriodeListe = new ArrayList<BeregnetBidragSakPeriode>();

    // Løper gjennom alle grunnlagene og identifiserer de som skal mappes til barnebidrag core
    for (GrunnlagFF grunnlag : beregnGrunnlag.getGrunnlagListe()) {
      switch (grunnlag.getType()) {
        case BIDRAGSEVNE_TYPE -> bidragsevnePeriodeCoreListe.add(mapBidragsevne(grunnlag));
        case BARNEBIDRAG_TYPE -> beregnetBidragSakPeriodeListe.add(mapBarnebidrag(grunnlag));
      }
    }

    // Omstrukturerer beregnetBidragSakPeriodeListe for at den skal passe inn i core
    var beregnetBidragSakPeriodeCoreListe = omstrukturerBidragSakPeriodeListe(beregnetBidragSakPeriodeListe);

    return new BeregnForholdsmessigFordelingGrunnlagCore(beregnGrunnlag.getBeregnDatoFra(), beregnGrunnlag.getBeregnDatoTil(),
        bidragsevnePeriodeCoreListe, beregnetBidragSakPeriodeCoreListe);
  }

  // Omstrukturerer beregnetBidragSakPeriodeListe for at den skal passe inn i core
  private List<BeregnetBidragSakPeriodeCore> omstrukturerBidragSakPeriodeListe(List<BeregnetBidragSakPeriode> beregnetBidragSakPeriodeListe) {

    // Sorterer beregnetBidragSakPeriodeListe
    var beregnetBidragSakPeriodeListeSortert = beregnetBidragSakPeriodeListe.stream()
        .sorted(comparing(BeregnetBidragSakPeriode::getSakNr)
            .thenComparing(BeregnetBidragSakPeriode::getDatoFom)
            .thenComparing(BeregnetBidragSakPeriode::getDatoTil)
            .thenComparing(BeregnetBidragSakPeriode::getSoknadsbarnId))
        .toList();

    var beregnetBidragSakPeriodeCoreListe = new ArrayList<BeregnetBidragSakPeriodeCore>();
    var grunnlagPerBarnListe = new ArrayList<GrunnlagPerBarnCore>();
    var sakNr = 0;
    var datoFom = LocalDate.MIN;
    var datoTil = LocalDate.MAX;
    var forsteGang = true;

    // Løper gjennom mottatt liste og bygger opp liste til core
    for (var bidragSak : beregnetBidragSakPeriodeListeSortert) {
      if (forsteGang) {
        forsteGang = false;
        sakNr = bidragSak.getSakNr();
        datoFom = bidragSak.getDatoFom();
        datoTil = bidragSak.getDatoTil();
      }
      if (brudd(sakNr, datoFom, datoTil, bidragSak)) {
        beregnetBidragSakPeriodeCoreListe.add(
            new BeregnetBidragSakPeriodeCore(sakNr, new PeriodeCore(datoFom, datoTil), new ArrayList<>(grunnlagPerBarnListe)));
        sakNr = bidragSak.getSakNr();
        datoFom = bidragSak.getDatoFom();
        datoTil = bidragSak.getDatoTil();
        grunnlagPerBarnListe.clear();
      }
      grunnlagPerBarnListe.add(new GrunnlagPerBarnCore(bidragSak.getSoknadsbarnId(), bidragSak.getBelop()));
    }
    beregnetBidragSakPeriodeCoreListe.add(new BeregnetBidragSakPeriodeCore(sakNr, new PeriodeCore(datoFom, datoTil), grunnlagPerBarnListe));

    return beregnetBidragSakPeriodeCoreListe;
  }

  // Sjekker om det er brudd i kriteriene
  private Boolean brudd(Integer sakNr, LocalDate datoFom, LocalDate datoTil, BeregnetBidragSakPeriode bidragSak) {
    return ((!sakNr.equals(bidragSak.getSakNr())) || (!datoFom.equals(bidragSak.getDatoFom())) || (!datoTil.equals(bidragSak.getDatoTil())));
  }

  // Løper gjennom resultatliste fra core og bygger opp liste som skal returneres fra tjenesten
  public BeregnetForholdsmessigFordelingResultat mapForholdsmessigFordelingResultatFraCore(
      BeregnForholdsmessigFordelingResultatCore resultatFraCore) {

    var resultatPeriode = new ArrayList<ResultatPeriodeFF>();

    // Løper gjennom alle resultatperiodene og mapper til resultatPeriode
    resultatFraCore.getResultatPeriodeListe().forEach(resultatPeriodeCore -> {
      resultatPeriodeCore.getResultatBeregningListe().forEach(resultatBeregningCore -> {
        resultatBeregningCore.getResultatPerBarnListe().stream()
            .map(resultatPerBarnCore -> new ResultatPeriodeFF(resultatBeregningCore.getSaksnr(), resultatPerBarnCore.getBarnPersonId(),
                new Periode(resultatPeriodeCore.getPeriode().getDatoFom(), resultatPeriodeCore.getPeriode().getDatoTil()),
                new ResultatBeregning(resultatPerBarnCore.getBelop(), resultatPerBarnCore.getKode())))
            .forEach(resultatPeriode::add);
      });
    });

    return new BeregnetForholdsmessigFordelingResultat(resultatPeriode);
  }
}
