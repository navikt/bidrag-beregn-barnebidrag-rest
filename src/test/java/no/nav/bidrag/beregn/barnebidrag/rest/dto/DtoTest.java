package no.nav.bidrag.beregn.barnebidrag.rest.dto;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import no.nav.bidrag.beregn.barnebidrag.rest.TestUtil;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DtoTest")
class DtoTest {

  // Total barnebidrag
  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarBBBeregnDatoFraErNull() {
    var grunnlag = TestUtil.byggTotalBarnebidragGrunnlagUtenBeregnDatoFra();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerTotalBarnebidragGrunnlag)
        .withMessage("beregnDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarBBBeregnDatoTilErNull() {
    var grunnlag = TestUtil.byggTotalBarnebidragGrunnlagUtenBeregnDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerTotalBarnebidragGrunnlag)
        .withMessage("beregnDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når soknadBarnFodselsdato er null")
  void skalKasteIllegalArgumentExceptionNaarBBSoknadBarnFodselsdatoErNull() {
    var grunnlag = TestUtil.byggTotalBarnebidragGrunnlagUtenSoknadBarnFodselsdato();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerTotalBarnebidragGrunnlag)
        .withMessage("soknadBarnFodselsdato kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når soknadBarnPersonId er null")
  void skalKasteIllegalArgumentExceptionNaarBBSoknadBarnPersonIdErNull() {
    var grunnlag = TestUtil.byggTotalBarnebidragGrunnlagUtenSoknadBarnPersonId();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerTotalBarnebidragGrunnlag)
        .withMessage("soknadBarnPersonId kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnBidragsevneGrunnlag er null")
  void skalKasteIllegalArgumentExceptionNaarBBBeregnBidragsevneGrunnlagErNull() {
    var grunnlag = TestUtil.byggTotalBarnebidragGrunnlagUtenBeregnBidragsevneGrunnlag();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerTotalBarnebidragGrunnlag)
        .withMessage("beregnBidragsevneGrunnlag kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnNettoBarnetilsynGrunnlag er null")
  void skalKasteIllegalArgumentExceptionNaarBBBeregnNettoBarnetilsynGrunnlagErNull() {
    var grunnlag = TestUtil.byggTotalBarnebidragGrunnlagUtenBeregnNettoBarnetilsynGrunnlag();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerTotalBarnebidragGrunnlag)
        .withMessage("beregnNettoBarnetilsynGrunnlag kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnUnderholdskostnadGrunnlag er null")
  void skalKasteIllegalArgumentExceptionNaarBBBeregnUnderholdskostnadGrunnlagErNull() {
    var grunnlag = TestUtil.byggTotalBarnebidragGrunnlagUtenBeregnUnderholdskostnadGrunnlag();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerTotalBarnebidragGrunnlag)
        .withMessage("beregnUnderholdskostnadGrunnlag kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnBPAndelUnderholdskostnadGrunnlag er null")
  void skalKasteIllegalArgumentExceptionNaarBBBeregnBPAndelUnderholdskostnadGrunnlagErNull() {
    var grunnlag = TestUtil.byggTotalBarnebidragGrunnlagUtenBeregnBPAndelUnderholdskostnadGrunnlag();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerTotalBarnebidragGrunnlag)
        .withMessage("beregnBPAndelUnderholdskostnadGrunnlag kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnSamvaersfradragGrunnlag er null")
  void skalKasteIllegalArgumentExceptionNaarBBBeregnSamvaersfradragGrunnlagErNull() {
    var grunnlag = TestUtil.byggTotalBarnebidragGrunnlagUtenBeregnSamvaersfradragGrunnlag();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerTotalBarnebidragGrunnlag)
        .withMessage("beregnSamvaersfradragGrunnlag kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarBEInntektPeriodeListeErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenInntektPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::bidragsevneTilCore)
        .withMessage("inntektPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når skatteklassePeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarBESkatteklassePeriodeListeErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenSkatteklassePeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::bidragsevneTilCore)
        .withMessage("skatteklassePeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når bostatusPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarBEBostatusPeriodeListeErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenBostatusPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::bidragsevneTilCore)
        .withMessage("bostatusPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når antallBarnIEgetHusholdPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarBEAntallBarnIEgetHusholdPeriodeListeErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenAntallBarnIEgetHusholdPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::bidragsevneTilCore)
        .withMessage("antallBarnIEgetHusholdPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når saerfradragPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarBESaerfradragPeriodeListeErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenSaerfradragPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::bidragsevneTilCore)
        .withMessage("saerfradragPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når faktiskUtgiftPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarNBFaktiskUtgiftPeriodeListeErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::nettoBarnetilsynTilCore)
        .withMessage("faktiskUtgiftPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarUKBarnetilsynMedStonadPeriodeListeErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::underholdskostnadTilCore)
        .withMessage("barnetilsynMedStonadPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når forpleiningUtgiftPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarUKForpleiningUtgiftPeriodeListeErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::underholdskostnadTilCore)
        .withMessage("forpleiningUtgiftPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntekterPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarBPUKInntekterPeriodeListeErNull() {
    var grunnlag = TestUtil.byggBPsAndelUnderholdskostnadGrunnlagUtenInntekterPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::bpAndelUnderholdskostnadTilCore)
        .withMessage("inntekterPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når samvaersklassePeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarSFSamvaersklassePeriodeListeErNull() {
    var grunnlag = TestUtil.byggSamvaersfradragGrunnlagUtenSamvaersklassePeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::samvaersfradragTilCore)
        .withMessage("samvaersklassePeriodeListe kan ikke være null");
  }


  // Bidragsevne
  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarBEInntektDatoFraTilErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenInntektDatoFraTil().getInntektPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("inntektDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarBEInntektDatoFraErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenInntektDatoFra().getInntektPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("periodeDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal ikke kaste exception når inntektDatoTil er null")
  void skalIkkeKasteExceptionNaarBEInntektDatoTilErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenInntektDatoTil().getInntektPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("periodeDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektType er null")
  void skalKasteIllegalArgumentExceptionNaarBEInntektTypeErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenInntektType().getInntektPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("inntektType kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBelop er null")
  void skalKasteIllegalArgumentExceptionNaarBEInntektBelopErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenInntektBelop().getInntektPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("inntektBelop kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når skatteklasseDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarBESkatteklasseDatoFraTilErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenSkatteklasseDatoFraTil().getSkatteklassePeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("skatteklasseDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når skatteklasseDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarBESkatteklasseDatoFraErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenSkatteklasseDatoFra().getSkatteklassePeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("periodeDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal ikke kaste exception når skatteklasseDatoTil er null")
  void skalIkkeKasteExceptionNaarBESkatteklasseDatoTilErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenSkatteklasseDatoTil().getSkatteklassePeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("periodeDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når skatteklasse er null")
  void skalKasteIllegalArgumentExceptionNaarBESkatteklasseErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenSkatteklasse().getSkatteklassePeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("skatteklasse kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når bostatusDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarBEBostatusDatoFraTilErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenBostatusDatoFraTil().getBostatusPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("bostatusDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når bostatusDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarBEBostatusDatoFraErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenBostatusDatoFra().getBostatusPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("periodeDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal ikke kaste exception når bostatusDatoTil er null")
  void skalIkkeKasteExceptionNaarBEBostatusDatoTilErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenBostatusDatoTil().getBostatusPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("periodeDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når bostatusKode er null")
  void skalKasteIllegalArgumentExceptionNaarBEBostatusKodeErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenBostatusKode().getBostatusPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("bostatusKode kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når antallBarnIEgetHusholdDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarBEAntallBarnIEgetHusholdDatoFraTilErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenAntallBarnIEgetHusholdDatoFraTil().getAntallBarnIEgetHusholdPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("antallBarnIEgetHusholdDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når antallBarnIEgetHusholdDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarBEAntallBarnIEgetHusholdDatoFraErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenAntallBarnIEgetHusholdDatoFra().getAntallBarnIEgetHusholdPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("periodeDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal ikke kaste exception når antallBarnIEgetHusholdDatoTil er null")
  void skalIkkeKasteExceptionNaarBEAntallBarnIEgetHusholdDatoTilErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenAntallBarnIEgetHusholdDatoTil().getAntallBarnIEgetHusholdPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("periodeDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når antallBarn er null")
  void skalKasteIllegalArgumentExceptionNaarBEAntallBarnErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenAntallBarn().getAntallBarnIEgetHusholdPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("antallBarn kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når saerfradragDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarBESaerfradragDatoFraTilErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenSaerfradragDatoFraTil().getSaerfradragPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("saerfradragDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når saerfradragDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarBESaerfradragDatoFraErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenSaerfradragDatoFra().getSaerfradragPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("periodeDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal ikke kaste exception når saerfradragDatoTil er null")
  void skalIkkeKasteExceptionNaarBESaerfradragDatoTilErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenSaerfradragDatoTil().getSaerfradragPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("periodeDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når saerfradragKode er null")
  void skalKasteIllegalArgumentExceptionNaarBESaerfradragKodeErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenSaerfradragKode().getSaerfradragPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("saerfradragKode kan ikke være null");
  }


  // Netto barnetilsyn
  @Test
  @DisplayName("Skal kaste IllegalArgumentException når faktiskUtgiftPeriodeDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarNBFaktiskUtgiftPeriodeDatoFraTilErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftPeriodeDatoFraTil().getFaktiskUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("faktiskUtgiftPeriodeDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når faktiskUtgiftPeriodeDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarNBFaktiskUtgiftPeriodeDatoFraErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftPeriodeDatoFra().getFaktiskUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("periodeDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når faktiskUtgiftPeriodeDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarNBFaktiskUtgiftPeriodeDatoTilErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftPeriodeDatoTil().getFaktiskUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("periodeDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når faktiskUtgiftSoknadsbarnFodselsdato er null")
  void skalKasteIllegalArgumentExceptionNaarNBFaktiskUtgiftSoknadsbarnFodselsdatoErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftSoknadsbarnFodselsdato().getFaktiskUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("faktiskUtgiftSoknadsbarnFodselsdato kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når faktiskUtgiftSoknadsbarnPersonId er null")
  void skalKasteIllegalArgumentExceptionNaarNBFaktiskUtgiftSoknadsbarnPersonIdErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftSoknadsbarnPersonId().getFaktiskUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("faktiskUtgiftSoknadsbarnPersonId kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når faktiskUtgiftBelop er null")
  void skalKasteIllegalArgumentExceptionNaarNBFaktiskUtgiftBelopErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftBelop().getFaktiskUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore).withMessage("faktiskUtgiftBelop kan ikke være null");
  }


  // Underholdskostnad
  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadPeriodeDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarUKBarnetilsynMedStonadPeriodeDatoFraTilErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadPeriodeDatoFraTil().getBarnetilsynMedStonadPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("barnetilsynMedStonadPeriodeDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadPeriodeDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarUKBarnetilsynMedStonadPeriodeDatoFraErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadPeriodeDatoFra().getBarnetilsynMedStonadPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore).withMessage("periodeDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadPeriodeDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarUKBarnetilsynMedStonadPeriodeDatoTilErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadPeriodeDatoTil().getBarnetilsynMedStonadPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore).withMessage("periodeDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadTilsynType er null")
  void skalKasteIllegalArgumentExceptionNaarUKBarnetilsynMedStonadTilsynTypeErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadTilsynType().getBarnetilsynMedStonadPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("barnetilsynMedStonadTilsynType kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadStonadType er null")
  void skalKasteIllegalArgumentExceptionNaarUKBarnetilsynMedStonadStonadTypeErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadStonadType().getBarnetilsynMedStonadPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("barnetilsynMedStonadStonadType kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når forpleiningUtgiftPeriodeDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarUKForpleiningUtgiftPeriodeDatoFraTilErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftPeriodeDatoFraTil().getForpleiningUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("forpleiningUtgiftPeriodeDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når forpleiningUtgiftPeriodeDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarUKForpleiningUtgiftPeriodeDatoFraErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftPeriodeDatoFra().getForpleiningUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore).withMessage("periodeDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når forpleiningUtgiftPeriodeDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarUKForpleiningUtgiftPeriodeDatoTilErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftPeriodeDatoTil().getForpleiningUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore).withMessage("periodeDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når forpleiningUtgiftBelop er null")
  void skalKasteIllegalArgumentExceptionNaarUKForpleiningUtgiftBelopErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftBelop().getForpleiningUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("forpleiningUtgiftBelop kan ikke være null");
  }


  // BPs andel av underholdskostnad
  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntekterPeriodeDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarBPUKInntekterPeriodeDatoFraTilErNull() {
    var grunnlag = TestUtil.byggBPsAndelUnderholdskostnadGrunnlagUtenInntekterPeriodeDatoFraTil().getInntekterPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("inntekterPeriodeDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntekterPeriodeDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarBPUKInntekterPeriodeDatoFraErNull() {
    var grunnlag = TestUtil.byggBPsAndelUnderholdskostnadGrunnlagUtenInntekterPeriodeDatoFra().getInntekterPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("periodeDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntekterPeriodeDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarBPUKInntekterPeriodeDatoTilErNull() {
    var grunnlag = TestUtil.byggBPsAndelUnderholdskostnadGrunnlagUtenInntekterPeriodeDatoTil().getInntekterPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("periodeDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBP er null")
  void skalKasteIllegalArgumentExceptionNaarBPUKInntektBPErNull() {
    var grunnlag = TestUtil.byggBPsAndelUnderholdskostnadGrunnlagUtenInntektBP().getInntekterPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("inntektBP kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBM er null")
  void skalKasteIllegalArgumentExceptionNaarBPUKInntektBMErNull() {
    var grunnlag = TestUtil.byggBPsAndelUnderholdskostnadGrunnlagUtenInntektBM().getInntekterPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("inntektBM kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBB er null")
  void skalKasteIllegalArgumentExceptionNaarBPUKInntektBBErNull() {
    var grunnlag = TestUtil.byggBPsAndelUnderholdskostnadGrunnlagUtenInntektBB().getInntekterPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("inntektBB kan ikke være null");
  }


  // Samværsfradrag
  @Test
  @DisplayName("Skal kaste IllegalArgumentException når samvaersklassePeriodeDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarSFSamvaersklassePeriodeDatoFraTilErNull() {
    var grunnlag = TestUtil.byggSamvaersfradragGrunnlagUtenSamvaersklassePeriodeDatoFraTil().getSamvaersklassePeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("samvaersklassePeriodeDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når samvaersklassePeriodeDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarSFSamvaersklassePeriodeDatoFraErNull() {
    var grunnlag = TestUtil.byggSamvaersfradragGrunnlagUtenSamvaersklassePeriodeDatoFra().getSamvaersklassePeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore).withMessage("periodeDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når samvaersklassePeriodeDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarSFSamvaersklassePeriodeDatoTilErNull() {
    var grunnlag = TestUtil.byggSamvaersfradragGrunnlagUtenSamvaersklassePeriodeDatoTil().getSamvaersklassePeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore).withMessage("periodeDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når samvaersklasse er null")
  void skalKasteIllegalArgumentExceptionNaarSFSamvaersklasseErNull() {
    var grunnlag = TestUtil.byggSamvaersfradragGrunnlagUtenSamvaersklasse().getSamvaersklassePeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore).withMessage("samvaersklasse kan ikke være null");
  }


  // Barnebidrag
  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBPPeriodeDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarTBBarnetilleggBPPeriodeDatoFraTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBPPeriodeDatoFraTil().getBarnetilleggBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("barnetilleggBPPeriodeDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBPPeriodeDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarTBBarnetilleggBPPeriodeDatoFraErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBPPeriodeDatoFra().getBarnetilleggBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore).withMessage("periodeDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBPPeriodeDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarTBBarnetilleggBPPeriodeDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBPPeriodeDatoTil().getBarnetilleggBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore).withMessage("periodeDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBPBelop er null")
  void skalKasteIllegalArgumentExceptionNaarTBBarnetilleggBPBelopErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBPBelop().getBarnetilleggBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("barnetilleggBPBelop kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBPSkattProsent er null")
  void skalKasteIllegalArgumentExceptionNaarTBBarnetilleggBPSkattProsentErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBPSkattProsent().getBarnetilleggBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("barnetilleggBPSkattProsent kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBMPeriodeDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarTBBarnetilleggBMPeriodeDatoFraTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBMPeriodeDatoFraTil().getBarnetilleggBMPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("barnetilleggBMPeriodeDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBMPeriodeDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarTBBarnetilleggBMPeriodeDatoFraErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBMPeriodeDatoFra().getBarnetilleggBMPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore).withMessage("periodeDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBMPeriodeDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarTBBarnetilleggBMPeriodeDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBMPeriodeDatoTil().getBarnetilleggBMPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore).withMessage("periodeDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBMBelop er null")
  void skalKasteIllegalArgumentExceptionNaarTBBarnetilleggBMBelopErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBMBelop().getBarnetilleggBMPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("barnetilleggBMBelop kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBMSkattProsent er null")
  void skalKasteIllegalArgumentExceptionNaarTBBarnetilleggBMSkattProsentErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBMSkattProsent().getBarnetilleggBMPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("barnetilleggBMSkattProsent kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggForsvaretPeriodeDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarTBBarnetilleggForsvaretPeriodeDatoFraTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggForsvaretPeriodeDatoFraTil().getBarnetilleggForsvaretPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("barnetilleggForsvaretPeriodeDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggForsvaretPeriodeDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarTBBarnetilleggForsvaretPeriodeDatoFraErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggForsvaretPeriodeDatoFra().getBarnetilleggForsvaretPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore).withMessage("periodeDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggForsvaretPeriodeDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarTBBarnetilleggForsvaretPeriodeDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggForsvaretPeriodeDatoTil().getBarnetilleggForsvaretPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore).withMessage("periodeDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggForsvaretAntallBarn er null")
  void skalKasteIllegalArgumentExceptionNaarTBBarnetilleggForsvaretAntallBarnErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggForsvaretAntallBarn().getBarnetilleggForsvaretPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("barnetilleggForsvaretAntallBarn kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggForsvaretIPeriode er null")
  void skalKasteIllegalArgumentExceptionNaarTBBarnetilleggForsvaretIPeriodeErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggForsvaretIPeriode().getBarnetilleggForsvaretPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("barnetilleggForsvaretIPeriode kan ikke være null");
  }
}
