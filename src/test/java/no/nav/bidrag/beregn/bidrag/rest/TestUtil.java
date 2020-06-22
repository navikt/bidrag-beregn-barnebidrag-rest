package no.nav.bidrag.beregn.bidrag.rest;

import static java.util.Collections.singletonList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.AntallBarnIEgetHusholdPeriode;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BeregnBidragGrunnlag;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BeregnBidragsevneGrunnlag;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BeregnBidragsevneResultat;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BostatusPeriode;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.Inntekt;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.InntektPeriode;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.Periode;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.ResultatBeregning;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.ResultatGrunnlag;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.ResultatPeriode;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.SaerfradragPeriode;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.Sjablon;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.SjablonInnhold;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.SjablonNokkel;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.SkatteklassePeriode;

public class TestUtil {


  public static BeregnBidragGrunnlag byggBidragGrunnlag() {
    return new BeregnBidragGrunnlag(byggBidragsevneGrunnlag(""), "Resten av grunnlaget");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlag() {
    return byggBidragsevneGrunnlag("");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenBeregnDatoFra() {
    return byggBidragsevneGrunnlag("beregnDatoFra");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenBeregnDatoTil() {
    return byggBidragsevneGrunnlag("beregnDatoTil");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenInntektPeriodeListe() {
    return byggBidragsevneGrunnlag("inntektPeriodeListe");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenSkatteklassePeriodeListe() {
    return byggBidragsevneGrunnlag("skatteklassePeriodeListe");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenBostatusPeriodeListe() {
    return byggBidragsevneGrunnlag("bostatusPeriodeListe");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenAntallBarnIEgetHusholdPeriodeListe() {
    return byggBidragsevneGrunnlag("antallBarnIEgetHusholdPeriodeListe");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenSaerfradragPeriodeListe() {
    return byggBidragsevneGrunnlag("saerfradragPeriodeListe");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenInntektDatoFraTil() {
    return byggBidragsevneGrunnlag("inntektDatoFraTil");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenInntektDatoFra() {
    return byggBidragsevneGrunnlag("inntektDatoFra");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenInntektDatoTil() {
    return byggBidragsevneGrunnlag("inntektDatoTil");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenInntektType() {
    return byggBidragsevneGrunnlag("inntektType");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenInntektBelop() {
    return byggBidragsevneGrunnlag("inntektBelop");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenSkatteklasseDatoFraTil() {
    return byggBidragsevneGrunnlag("skatteklasseDatoFraTil");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenSkatteklasseDatoFra() {
    return byggBidragsevneGrunnlag("skatteklasseDatoFra");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenSkatteklasseDatoTil() {
    return byggBidragsevneGrunnlag("skatteklasseDatoTil");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenSkatteklasse() {
    return byggBidragsevneGrunnlag("skatteklasse");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenBostatusDatoFraTil() {
    return byggBidragsevneGrunnlag("bostatusDatoFraTil");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenBostatusDatoFra() {
    return byggBidragsevneGrunnlag("bostatusDatoFra");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenBostatusDatoTil() {
    return byggBidragsevneGrunnlag("bostatusDatoTil");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenBostatusKode() {
    return byggBidragsevneGrunnlag("bostatusKode");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenAntallBarnIEgetHusholdDatoFraTil() {
    return byggBidragsevneGrunnlag("antallBarnIEgetHusholdDatoFraTil");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenAntallBarnIEgetHusholdDatoFra() {
    return byggBidragsevneGrunnlag("antallBarnIEgetHusholdDatoFra");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenAntallBarnIEgetHusholdDatoTil() {
    return byggBidragsevneGrunnlag("antallBarnIEgetHusholdDatoTil");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenAntallBarn() {
    return byggBidragsevneGrunnlag("antallBarn");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenSaerfradragDatoFraTil() {
    return byggBidragsevneGrunnlag("saerfradragDatoFraTil");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenSaerfradragDatoFra() {
    return byggBidragsevneGrunnlag("saerfradragDatoFra");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenSaerfradragDatoTil() {
    return byggBidragsevneGrunnlag("saerfradragDatoTil");
  }

  public static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlagUtenSaerfradragKode() {
    return byggBidragsevneGrunnlag("saerfradragKode");
  }

  // Bygger opp BeregnBidragsevneGrunnlag
  private static BeregnBidragsevneGrunnlag byggBidragsevneGrunnlag(String nullVerdi) {
    var beregnDatoFra = (nullVerdi.equals("beregnDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var beregnDatoTil = (nullVerdi.equals("beregnDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var inntektDatoFra = (nullVerdi.equals("inntektDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var inntektDatoTil = (nullVerdi.equals("inntektDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var inntektType = (nullVerdi.equals("inntektType") ? null : "LØNNSINNTEKT");
    var inntektBelop = (nullVerdi.equals("inntektBelop") ? null : 100000d);
    var skatteklasseDatoFra = (nullVerdi.equals("skatteklasseDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var skatteklasseDatoTil = (nullVerdi.equals("skatteklasseDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var skatteklasse = (nullVerdi.equals("skatteklasse") ? null : 1);
    var bostatusDatoFra = (nullVerdi.equals("bostatusDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var bostatusDatoTil = (nullVerdi.equals("bostatusDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var bostatusKode = (nullVerdi.equals("bostatusKode") ? null : "MED_ANDRE");
    var antallBarnIEgetHusholdDatoFra = (nullVerdi.equals("antallBarnIEgetHusholdDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var antallBarnIEgetHusholdDatoTil = (nullVerdi.equals("antallBarnIEgetHusholdDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var antallBarn = (nullVerdi.equals("antallBarn") ? null : 1);
    var saerfradragDatoFra = (nullVerdi.equals("saerfradragDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var saerfradragDatoTil = (nullVerdi.equals("saerfradragDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var saerfradragKode = (nullVerdi.equals("saerfradragKode") ? null : "HELT");

    List<InntektPeriode> inntektPeriodeListe;
    if (nullVerdi.equals("inntektPeriodeListe")) {
      inntektPeriodeListe = null;
    } else {
      InntektPeriode inntektPeriode;
      if (nullVerdi.equals("inntektDatoFraTil")) {
        inntektPeriode = new InntektPeriode(null, inntektType, inntektBelop);
      } else {
        inntektPeriode = new InntektPeriode(new Periode(inntektDatoFra, inntektDatoTil), inntektType, inntektBelop);
      }
      inntektPeriodeListe = new ArrayList<>();
      inntektPeriodeListe.add(inntektPeriode);
    }

    List<SkatteklassePeriode> skatteklassePeriodeListe;
    if (nullVerdi.equals("skatteklassePeriodeListe")) {
      skatteklassePeriodeListe = null;
    } else {
      SkatteklassePeriode skatteklassePeriode;
      if (nullVerdi.equals("skatteklasseDatoFraTil")) {
        skatteklassePeriode = new SkatteklassePeriode(null, skatteklasse);
      } else {
        skatteklassePeriode = new SkatteklassePeriode(new Periode(skatteklasseDatoFra, skatteklasseDatoTil), skatteklasse);
      }
      skatteklassePeriodeListe = new ArrayList<>();
      skatteklassePeriodeListe.add(skatteklassePeriode);
    }

    List<BostatusPeriode> bostatusPeriodeListe;
    if (nullVerdi.equals("bostatusPeriodeListe")) {
      bostatusPeriodeListe = null;
    } else {
      BostatusPeriode bostatusPeriode;
      if (nullVerdi.equals("bostatusDatoFraTil")) {
        bostatusPeriode = new BostatusPeriode(null, bostatusKode);
      } else {
        bostatusPeriode = new BostatusPeriode(new Periode(bostatusDatoFra, bostatusDatoTil), bostatusKode);
      }
      bostatusPeriodeListe = new ArrayList<>();
      bostatusPeriodeListe.add(bostatusPeriode);
    }

    List<AntallBarnIEgetHusholdPeriode> antallBarnIEgetHusholdPeriodeListe;
    if (nullVerdi.equals("antallBarnIEgetHusholdPeriodeListe")) {
      antallBarnIEgetHusholdPeriodeListe = null;
    } else {
      AntallBarnIEgetHusholdPeriode antallBarnIEgetHusholdPeriode;
      if (nullVerdi.equals("antallBarnIEgetHusholdDatoFraTil")) {
        antallBarnIEgetHusholdPeriode = new AntallBarnIEgetHusholdPeriode(null, antallBarn);
      } else {
        antallBarnIEgetHusholdPeriode = new AntallBarnIEgetHusholdPeriode(new Periode(antallBarnIEgetHusholdDatoFra, antallBarnIEgetHusholdDatoTil),
            antallBarn);
      }
      antallBarnIEgetHusholdPeriodeListe = new ArrayList<>();
      antallBarnIEgetHusholdPeriodeListe.add(antallBarnIEgetHusholdPeriode);
    }

    List<SaerfradragPeriode> saerfradragPeriodeListe;
    if (nullVerdi.equals("saerfradragPeriodeListe")) {
      saerfradragPeriodeListe = null;
    } else {
      SaerfradragPeriode saerfradragPeriode;
      if (nullVerdi.equals("saerfradragDatoFraTil")) {
        saerfradragPeriode = new SaerfradragPeriode(null, saerfradragKode);
      } else {
        saerfradragPeriode = new SaerfradragPeriode(new Periode(saerfradragDatoFra, saerfradragDatoTil), saerfradragKode);
      }
      saerfradragPeriodeListe = new ArrayList<>();
      saerfradragPeriodeListe.add(saerfradragPeriode);
    }

    return new BeregnBidragsevneGrunnlag(beregnDatoFra, beregnDatoTil, inntektPeriodeListe, skatteklassePeriodeListe, bostatusPeriodeListe,
        antallBarnIEgetHusholdPeriodeListe, saerfradragPeriodeListe);
  }

  // Bygger opp BeregnBidragsevneResultat
  public static BeregnBidragsevneResultat dummyBidragsevneResultat() {
    var bidragPeriodeResultatListe = new ArrayList<ResultatPeriode>();
    bidragPeriodeResultatListe.add(new ResultatPeriode(new Periode(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        new ResultatBeregning(100d),
        new ResultatGrunnlag(singletonList(new Inntekt("LØNNSINNTEKT", 500000d)), 1, "MED_ANDRE", 1,
            "HELT", singletonList(new Sjablon("SatsTrygdeavgift", singletonList(new SjablonNokkel("Nokkelnavn", "Nokkelverdi")),
            singletonList(new SjablonInnhold("InnholdNavn", 0d)))))));
    return new BeregnBidragsevneResultat(bidragPeriodeResultatListe);
  }
}
