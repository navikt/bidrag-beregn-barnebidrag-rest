package no.nav.bidrag.beregn.barnebidrag.rest.dto;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import no.nav.bidrag.beregn.barnebidrag.rest.TestUtil;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DtoTest")
class DtoTest {

  // Netto barnetilsyn
  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarNBBeregnDatoFraErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenBeregnDatoFra();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore).withMessage("beregnDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarNBBeregnDatoTilErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenBeregnDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore).withMessage("beregnDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når faktiskUtgiftPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarNBFaktiskUtgiftPeriodeListeErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore).withMessage("faktiskUtgiftPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når faktiskUtgiftPeriodeDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarNBFaktiskUtgiftPeriodeDatoFraTilErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftPeriodeDatoFraTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("faktiskUtgiftPeriodeDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når faktiskUtgiftPeriodeDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarNBFaktiskUtgiftPeriodeDatoFraErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftPeriodeDatoFra();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("periodeDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når faktiskUtgiftPeriodeDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarNBFaktiskUtgiftPeriodeDatoTilErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftPeriodeDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("periodeDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når faktiskUtgiftSoknadsbarnFodselsdato er null")
  void skalKasteIllegalArgumentExceptionNaarNBFaktiskUtgiftSoknadsbarnFodselsdatoErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftSoknadsbarnFodselsdato();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("faktiskUtgiftSoknadsbarnFodselsdato kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når faktiskUtgiftSoknadsbarnPersonId er null")
  void skalKasteIllegalArgumentExceptionNaarNBFaktiskUtgiftSoknadsbarnPersonIdErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftSoknadsbarnPersonId();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("faktiskUtgiftSoknadsbarnPersonId kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når faktiskUtgiftBelop er null")
  void skalKasteIllegalArgumentExceptionNaarNBFaktiskUtgiftBelopErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftBelop();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore).withMessage("faktiskUtgiftBelop kan ikke være null");
  }

  // Underholdskostnad
  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarUKBeregnDatoFraErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBeregnDatoFra();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore).withMessage("beregnDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarUKBeregnDatoTilErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBeregnDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore).withMessage("beregnDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når soknadBarnFodselsdato er null")
  void skalKasteIllegalArgumentExceptionNaarUKSoknadBarnFodselsdatoErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenSoknadBarnFodselsdato();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore).withMessage("soknadBarnFodselsdato kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarUKBarnetilsynMedStonadPeriodeListeErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("barnetilsynMedStonadPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når forpleiningUtgiftPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarUKForpleiningUtgiftPeriodeListeErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("forpleiningUtgiftPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadPeriodeDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarUKBarnetilsynMedStonadPeriodeDatoFraTilErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadPeriodeDatoFraTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("barnetilsynMedStonadPeriodeDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadPeriodeDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarUKBarnetilsynMedStonadPeriodeDatoFraErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadPeriodeDatoFra();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore).withMessage("periodeDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadPeriodeDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarUKBarnetilsynMedStonadPeriodeDatoTilErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadPeriodeDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore).withMessage("periodeDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadTilsynType er null")
  void skalKasteIllegalArgumentExceptionNaarUKBarnetilsynMedStonadTilsynTypeErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadTilsynType();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("barnetilsynMedStonadTilsynType kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadStonadType er null")
  void skalKasteIllegalArgumentExceptionNaarUKBarnetilsynMedStonadStonadTypeErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadStonadType();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("barnetilsynMedStonadStonadType kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når forpleiningUtgiftPeriodeDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarUKForpleiningUtgiftPeriodeDatoFraTilErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftPeriodeDatoFraTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("forpleiningUtgiftPeriodeDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når forpleiningUtgiftPeriodeDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarUKForpleiningUtgiftPeriodeDatoFraErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftPeriodeDatoFra();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore).withMessage("periodeDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når forpleiningUtgiftPeriodeDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarUKForpleiningUtgiftPeriodeDatoTilErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftPeriodeDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore).withMessage("periodeDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når forpleiningUtgiftBelop er null")
  void skalKasteIllegalArgumentExceptionNaarUKForpleiningUtgiftBelopErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftBelop();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore).withMessage("forpleiningUtgiftBelop kan ikke være null");
  }

  // BPs andel av underholdskostnad
  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarBPUKBeregnDatoFraErNull() {
    var grunnlag = TestUtil.byggBPsAndelUnderholdskostnadGrunnlagUtenBeregnDatoFra();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore).withMessage("beregnDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarBPUKBeregnDatoTilErNull() {
    var grunnlag = TestUtil.byggBPsAndelUnderholdskostnadGrunnlagUtenBeregnDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore).withMessage("beregnDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntekterPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarBPUKInntekterPeriodeListeErNull() {
    var grunnlag = TestUtil.byggBPsAndelUnderholdskostnadGrunnlagUtenInntekterPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("inntekterPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntekterPeriodeDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarBPUKInntekterPeriodeDatoFraTilErNull() {
    var grunnlag = TestUtil.byggBPsAndelUnderholdskostnadGrunnlagUtenInntekterPeriodeDatoFraTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("inntekterPeriodeDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntekterPeriodeDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarBPUKInntekterPeriodeDatoFraErNull() {
    var grunnlag = TestUtil.byggBPsAndelUnderholdskostnadGrunnlagUtenInntekterPeriodeDatoFra();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("periodeDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntekterPeriodeDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarBPUKInntekterPeriodeDatoTilErNull() {
    var grunnlag = TestUtil.byggBPsAndelUnderholdskostnadGrunnlagUtenInntekterPeriodeDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("periodeDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBP er null")
  void skalKasteIllegalArgumentExceptionNaarBPUKInntektBPErNull() {
    var grunnlag = TestUtil.byggBPsAndelUnderholdskostnadGrunnlagUtenInntektBP();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("inntektBP kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBM er null")
  void skalKasteIllegalArgumentExceptionNaarBPUKInntektBMErNull() {
    var grunnlag = TestUtil.byggBPsAndelUnderholdskostnadGrunnlagUtenInntektBM();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("inntektBM kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBB er null")
  void skalKasteIllegalArgumentExceptionNaarBPUKInntektBBErNull() {
    var grunnlag = TestUtil.byggBPsAndelUnderholdskostnadGrunnlagUtenInntektBB();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("inntektBB kan ikke være null");
  }

  // Samværsfradrag
  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarSFBeregnDatoFraErNull() {
    var grunnlag = TestUtil.byggSamvaersfradragGrunnlagUtenBeregnDatoFra();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore).withMessage("beregnDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarSFBeregnDatoTilErNull() {
    var grunnlag = TestUtil.byggSamvaersfradragGrunnlagUtenBeregnDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore).withMessage("beregnDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når soknadsbarnFodselsdato er null")
  void skalKasteIllegalArgumentExceptionNaarSFSoknadsbarnFodselsdatoErNull() {
    var grunnlag = TestUtil.byggSamvaersfradragGrunnlagUtenSoknadsbarnFodselsdato();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore).withMessage("soknadsbarnFodselsdato kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når samvaersklassePeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarSFSamvaersklassePeriodeListeErNull() {
    var grunnlag = TestUtil.byggSamvaersfradragGrunnlagUtenSamvaersklassePeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore).withMessage("samvaersklassePeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når samvaersklassePeriodeDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarSFSamvaersklassePeriodeDatoFraTilErNull() {
    var grunnlag = TestUtil.byggSamvaersfradragGrunnlagUtenSamvaersklassePeriodeDatoFraTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("samvaersklassePeriodeDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når samvaersklassePeriodeDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarSFSamvaersklassePeriodeDatoFraErNull() {
    var grunnlag = TestUtil.byggSamvaersfradragGrunnlagUtenSamvaersklassePeriodeDatoFra();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore).withMessage("periodeDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når samvaersklassePeriodeDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarSFSamvaersklassePeriodeDatoTilErNull() {
    var grunnlag = TestUtil.byggSamvaersfradragGrunnlagUtenSamvaersklassePeriodeDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore).withMessage("periodeDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når samvaersklasse er null")
  void skalKasteIllegalArgumentExceptionNaarSFSamvaersklasseErNull() {
    var grunnlag = TestUtil.byggSamvaersfradragGrunnlagUtenSamvaersklasse();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore).withMessage("samvaersklasse kan ikke være null");
  }
}
