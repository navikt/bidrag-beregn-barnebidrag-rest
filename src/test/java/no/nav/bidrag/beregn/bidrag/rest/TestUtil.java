package no.nav.bidrag.beregn.bidrag.rest;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import no.nav.bidrag.beregn.bidrag.rest.consumer.Forbruksutgifter;
import no.nav.bidrag.beregn.bidrag.rest.consumer.Sjablontall;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.AntallBarnIEgetHusholdPeriode;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BarnetilsynMedStonadPeriode;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BeregnBidragGrunnlag;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BeregnBidragsevneGrunnlag;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BeregnBidragsevneResultat;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BeregnUnderholdskostnadGrunnlag;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BeregnUnderholdskostnadResultat;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BostatusPeriode;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.ForpleiningUtgiftPeriode;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.Inntekt;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.InntektPeriode;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.Periode;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.ResultatBeregningBidragsevne;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.ResultatBeregningUnderholdskostnad;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.ResultatGrunnlagBidragsevne;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.ResultatGrunnlagUnderholdskostnad;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.ResultatPeriodeBidragsevne;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.ResultatPeriodeUnderholdskostnad;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.SaerfradragPeriode;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.Sjablon;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.SjablonInnhold;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.SkatteklassePeriode;
import no.nav.bidrag.beregn.felles.dto.AvvikCore;
import no.nav.bidrag.beregn.felles.dto.PeriodeCore;
import no.nav.bidrag.beregn.felles.dto.SjablonCore;
import no.nav.bidrag.beregn.felles.dto.SjablonInnholdCore;
import no.nav.bidrag.beregn.felles.enums.SjablonInnholdNavn;
import no.nav.bidrag.beregn.felles.enums.SjablonTallNavn;
import no.nav.bidrag.beregn.underholdskostnad.dto.BarnetilsynMedStonadPeriodeCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadGrunnlagCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadResultatCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.ForpleiningUtgiftPeriodeCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.NettoBarnetilsynPeriodeCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.ResultatBeregningCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.ResultatGrunnlagCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.ResultatPeriodeCore;

public class TestUtil {


  public static BeregnBidragGrunnlag byggBidragGrunnlag() {
    return new BeregnBidragGrunnlag(byggBidragsevneGrunnlag(""), byggUnderholdskostnadGrunnlag(""), "Resten av grunnlaget");
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

  public static BeregnUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlag() {
    return byggUnderholdskostnadGrunnlag("");
  }

  public static BeregnUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenBeregnDatoFra() {
    return byggUnderholdskostnadGrunnlag("beregnDatoFra");
  }

  public static BeregnUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenBeregnDatoTil() {
    return byggUnderholdskostnadGrunnlag("beregnDatoTil");
  }

  public static BeregnUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenSoknadBarnFodselsdato() {
    return byggUnderholdskostnadGrunnlag("soknadBarnFodselsdato");
  }

  public static BeregnUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadPeriodeListe() {
    return byggUnderholdskostnadGrunnlag("barnetilsynMedStonadPeriodeListe");
  }

  public static BeregnUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftPeriodeListe() {
    return byggUnderholdskostnadGrunnlag("forpleiningUtgiftPeriodeListe");
  }

  public static BeregnUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadPeriodeDatoFraTil() {
    return byggUnderholdskostnadGrunnlag("barnetilsynMedStonadPeriodeDatoFraTil");
  }

  public static BeregnUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadPeriodeDatoFra() {
    return byggUnderholdskostnadGrunnlag("barnetilsynMedStonadPeriodeDatoFra");
  }

  public static BeregnUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadPeriodeDatoTil() {
    return byggUnderholdskostnadGrunnlag("barnetilsynMedStonadPeriodeDatoTil");
  }

  public static BeregnUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadTilsynType() {
    return byggUnderholdskostnadGrunnlag("barnetilsynMedStonadTilsynType");
  }

  public static BeregnUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadStonadType() {
    return byggUnderholdskostnadGrunnlag("barnetilsynMedStonadStonadType");
  }

  public static BeregnUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftPeriodeDatoFraTil() {
    return byggUnderholdskostnadGrunnlag("forpleiningUtgiftPeriodeDatoFraTil");
  }

  public static BeregnUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftPeriodeDatoFra() {
    return byggUnderholdskostnadGrunnlag("forpleiningUtgiftPeriodeDatoFra");
  }

  public static BeregnUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftPeriodeDatoTil() {
    return byggUnderholdskostnadGrunnlag("forpleiningUtgiftPeriodeDatoTil");
  }

  public static BeregnUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftBelop() {
    return byggUnderholdskostnadGrunnlag("forpleiningUtgiftBelop");
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
        antallBarnIEgetHusholdPeriode =
            new AntallBarnIEgetHusholdPeriode(new Periode(antallBarnIEgetHusholdDatoFra, antallBarnIEgetHusholdDatoTil), antallBarn);
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

  // Bygger opp BeregnUnderholdskostnadGrunnlag
  private static BeregnUnderholdskostnadGrunnlag byggUnderholdskostnadGrunnlag(String nullVerdi) {
    var beregnDatoFra = (nullVerdi.equals("beregnDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var beregnDatoTil = (nullVerdi.equals("beregnDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var soknadBarnFodselsdato = (nullVerdi.equals("soknadBarnFodselsdato") ? null : LocalDate.parse("2010-01-01"));
    var barnetilsynMedStonadPeriodeDatoFra = (nullVerdi.equals("barnetilsynMedStonadPeriodeDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var barnetilsynMedStonadPeriodeDatoTil = (nullVerdi.equals("barnetilsynMedStonadPeriodeDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var barnetilsynMedStonadTilsynType = (nullVerdi.equals("barnetilsynMedStonadTilsynType") ? null : "DO");
    var barnetilsynMedStonadStonadType = (nullVerdi.equals("barnetilsynMedStonadStonadType") ? null : "64");
    var forpleiningUtgiftPeriodeDatoFra = (nullVerdi.equals("forpleiningUtgiftPeriodeDatoFra") ? null : LocalDate.parse("2017-01-01"));
    var forpleiningUtgiftPeriodeDatoTil = (nullVerdi.equals("forpleiningUtgiftPeriodeDatoTil") ? null : LocalDate.parse("2020-01-01"));
    var forpleiningUtgiftBelop = (nullVerdi.equals("forpleiningUtgiftBelop") ? null : 1000d);

    List<BarnetilsynMedStonadPeriode> barnetilsynMedStonadPeriodeListe;
    if (nullVerdi.equals("barnetilsynMedStonadPeriodeListe")) {
      barnetilsynMedStonadPeriodeListe = null;
    } else {
      BarnetilsynMedStonadPeriode barnetilsynMedStonadPeriode;
      if (nullVerdi.equals("barnetilsynMedStonadPeriodeDatoFraTil")) {
        barnetilsynMedStonadPeriode = new BarnetilsynMedStonadPeriode(null, barnetilsynMedStonadTilsynType, barnetilsynMedStonadStonadType);
      } else {
        barnetilsynMedStonadPeriode = new BarnetilsynMedStonadPeriode(new Periode(barnetilsynMedStonadPeriodeDatoFra,
            barnetilsynMedStonadPeriodeDatoTil), barnetilsynMedStonadTilsynType, barnetilsynMedStonadStonadType);
      }
      barnetilsynMedStonadPeriodeListe = new ArrayList<>();
      barnetilsynMedStonadPeriodeListe.add(barnetilsynMedStonadPeriode);
    }

    List<ForpleiningUtgiftPeriode> forpleiningUtgiftPeriodeListe;
    if (nullVerdi.equals("forpleiningUtgiftPeriodeListe")) {
      forpleiningUtgiftPeriodeListe = null;
    } else {
      ForpleiningUtgiftPeriode forpleiningUtgiftPeriode;
      if (nullVerdi.equals("forpleiningUtgiftPeriodeDatoFraTil")) {
        forpleiningUtgiftPeriode = new ForpleiningUtgiftPeriode(null, forpleiningUtgiftBelop);
      } else {
        forpleiningUtgiftPeriode = new ForpleiningUtgiftPeriode(new Periode(forpleiningUtgiftPeriodeDatoFra, forpleiningUtgiftPeriodeDatoTil),
            forpleiningUtgiftBelop);
      }
      forpleiningUtgiftPeriodeListe = new ArrayList<>();
      forpleiningUtgiftPeriodeListe.add(forpleiningUtgiftPeriode);
    }

    return new BeregnUnderholdskostnadGrunnlag(beregnDatoFra, beregnDatoTil, soknadBarnFodselsdato, barnetilsynMedStonadPeriodeListe,
        forpleiningUtgiftPeriodeListe);
  }

  // Bygger opp BeregnBidragsevneResultat
  public static BeregnBidragsevneResultat dummyBidragsevneResultat() {
    var bidragPeriodeResultatListe = new ArrayList<ResultatPeriodeBidragsevne>();
    bidragPeriodeResultatListe.add(new ResultatPeriodeBidragsevne(new Periode(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        new ResultatBeregningBidragsevne(100d),
        new ResultatGrunnlagBidragsevne(singletonList(new Inntekt("LØNNSINNTEKT", 500000d)), 1, "MED_ANDRE", 1,
            "HELT", singletonList(new Sjablon(SjablonTallNavn.TRYGDEAVGIFT_PROSENT.getNavn(), emptyList(),
            singletonList(new SjablonInnhold(SjablonInnholdNavn.SJABLON_VERDI.getNavn(), 8d)))))));
    return new BeregnBidragsevneResultat(bidragPeriodeResultatListe);
  }

  // Bygger opp BeregnUnderholdskostnadGrunnlagCore
  public static BeregnUnderholdskostnadGrunnlagCore dummyUnderholdskostnadGrunnlagCore() {

    var barnetilsynMedStonadPeriode = new BarnetilsynMedStonadPeriodeCore(
        new PeriodeCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01")), "DO", "64");
    var barnetilsynMedStonadPeriodeListe = new ArrayList<BarnetilsynMedStonadPeriodeCore>();
    barnetilsynMedStonadPeriodeListe.add(barnetilsynMedStonadPeriode);

    var nettoBarnetilsynPeriode = new NettoBarnetilsynPeriodeCore(new PeriodeCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01")),
        1000d);
    var nettoBarnetilsynPeriodeListe = new ArrayList<NettoBarnetilsynPeriodeCore>();
    nettoBarnetilsynPeriodeListe.add(nettoBarnetilsynPeriode);

    var forpleiningUtgiftPeriode = new ForpleiningUtgiftPeriodeCore(new PeriodeCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01")),
        500d);
    var forpleiningUtgiftPeriodeListe = new ArrayList<ForpleiningUtgiftPeriodeCore>();
    forpleiningUtgiftPeriodeListe.add(forpleiningUtgiftPeriode);

    return new BeregnUnderholdskostnadGrunnlagCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), LocalDate.parse("2010-01-01"),
        barnetilsynMedStonadPeriodeListe, nettoBarnetilsynPeriodeListe, forpleiningUtgiftPeriodeListe, emptyList());
  }

  // Bygger opp BeregnUnderholdskostnadResultat
  public static BeregnUnderholdskostnadResultat dummyUnderholdskostnadResultat() {
    var bidragPeriodeResultatListe = new ArrayList<ResultatPeriodeUnderholdskostnad>();
    bidragPeriodeResultatListe.add(new ResultatPeriodeUnderholdskostnad(new Periode(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        new ResultatBeregningUnderholdskostnad(100d),
        new ResultatGrunnlagUnderholdskostnad(9, "DO", "64", 1000d, 1000d,
            singletonList(new Sjablon(SjablonTallNavn.ORDINAER_BARNETRYGD_BELOP.getNavn(), emptyList(),
                singletonList(new SjablonInnhold(SjablonInnholdNavn.SJABLON_VERDI.getNavn(), 1054d)))))));
    return new BeregnUnderholdskostnadResultat(bidragPeriodeResultatListe);
  }

  // Bygger opp BeregnUnderholdskostnadResultatCore
  public static BeregnUnderholdskostnadResultatCore dummyUnderholdskostnadResultatCore() {
    var bidragPeriodeResultatListe = new ArrayList<ResultatPeriodeCore>();
    bidragPeriodeResultatListe.add(new ResultatPeriodeCore(new PeriodeCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2019-01-01")),
        new ResultatBeregningCore(100d),
        new ResultatGrunnlagCore(9, "DO", "64", 1000d, 1000d,
            singletonList(new SjablonCore(SjablonTallNavn.ORDINAER_BARNETRYGD_BELOP.getNavn(), emptyList(),
                singletonList(new SjablonInnholdCore(SjablonInnholdNavn.SJABLON_VERDI.getNavn(), 1054d)))))));
    return new BeregnUnderholdskostnadResultatCore(bidragPeriodeResultatListe, emptyList());
  }

  // Bygger opp BeregnUnderholdskostnadResultatCore med avvik
  public static BeregnUnderholdskostnadResultatCore dummyUnderholdskostnadResultatCoreMedAvvik() {
    var avvikListe = new ArrayList<AvvikCore>();
    avvikListe.add(new AvvikCore("beregnDatoFra kan ikke være null", "NULL_VERDI_I_DATO"));
    avvikListe.add(new AvvikCore(
        "periodeDatoTil må være etter periodeDatoFra i inntektPeriodeListe: periodeDatoFra=2018-04-01, periodeDatoTil=2018-03-01",
        "DATO_FRA_ETTER_DATO_TIL"));
    return new BeregnUnderholdskostnadResultatCore(emptyList(), avvikListe);
  }

  // Bygger opp liste av sjabloner av typen Sjablontall
  public static List<Sjablontall> dummySjablonSjablontallListe() {
    var sjablonSjablontallListe = new ArrayList<Sjablontall>();
    sjablonSjablontallListe.add(new Sjablontall("0001", LocalDate.parse("2017-07-01"), LocalDate.parse("2018-07-01"), BigDecimal.valueOf(1054)));
    sjablonSjablontallListe.add(new Sjablontall("0003", LocalDate.parse("2017-07-01"), LocalDate.parse("2018-07-01"), BigDecimal.valueOf(2775)));

    sjablonSjablontallListe.add(new Sjablontall("0001", LocalDate.parse("2018-07-01"), LocalDate.parse("2019-07-01"), BigDecimal.valueOf(1074)));
    sjablonSjablontallListe.add(new Sjablontall("0003", LocalDate.parse("2018-07-01"), LocalDate.parse("2019-07-01"), BigDecimal.valueOf(2795)));
    return sjablonSjablontallListe;
  }

  // Bygger opp liste av sjabloner av typen Forbruksutgifter
  public static List<Forbruksutgifter> dummySjablonForbruksutgifterListe() {
    var sjablonForbruksutgifterListe = new ArrayList<Forbruksutgifter>();
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(5, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-07-01"), BigDecimal.valueOf(3661)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(5, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-07-01"), BigDecimal.valueOf(3861)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(10, LocalDate.parse("2017-07-01"), LocalDate.parse("2018-07-01"), BigDecimal.valueOf(5113)));
    sjablonForbruksutgifterListe.add(
        new Forbruksutgifter(10, LocalDate.parse("2018-07-01"), LocalDate.parse("2019-07-01"), BigDecimal.valueOf(5313)));
    return sjablonForbruksutgifterListe;
  }
}
