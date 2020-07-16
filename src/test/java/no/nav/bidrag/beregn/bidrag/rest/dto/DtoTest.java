package no.nav.bidrag.beregn.bidrag.rest.dto;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import no.nav.bidrag.beregn.bidrag.rest.TestUtil;
import no.nav.bidrag.beregn.bidrag.rest.exception.UgyldigInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DtoTest")
class DtoTest {

  // Underholdskostnad
  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarBeregnDatoFraErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBeregnDatoFra();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("beregnDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarBeregnDatoTilErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBeregnDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("beregnDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når soknadBarnFodselsdato er null")
  void skalKasteIllegalArgumentExceptionNaarSoknadBarnFodselsdatoErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenSoknadBarnFodselsdato();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("soknadBarnFodselsdato kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarInntektDatoFraErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("barnetilsynMedStonadPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når forpleiningUtgiftPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarForpleiningUtgiftPeriodeListeErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("forpleiningUtgiftPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadPeriodeDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarBarnetilsynMedStonadPeriodeDatoFraTilErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadPeriodeDatoFraTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("barnetilsynMedStonadPeriodeDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadPeriodeDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarBarnetilsynMedStonadPeriodeDatoFraErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadPeriodeDatoFra();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("periodeDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadPeriodeDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarBarnetilsynMedStonadPeriodeDatoTilErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadPeriodeDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("periodeDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadTilsynType er null")
  void skalKasteIllegalArgumentExceptionNaarBarnetilsynMedStonadTilsynTypeErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadTilsynType();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("barnetilsynMedStonadTilsynType kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadStonadType er null")
  void skalKasteIllegalArgumentExceptionNaarBarnetilsynMedStonadStonadTypeErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadStonadType();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("barnetilsynMedStonadStonadType kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når forpleiningUtgiftPeriodeDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarForpleiningUtgiftPeriodeDatoFraTilErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftPeriodeDatoFraTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("forpleiningUtgiftPeriodeDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når forpleiningUtgiftPeriodeDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarForpleiningUtgiftPeriodeDatoFraErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftPeriodeDatoFra();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("periodeDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når forpleiningUtgiftPeriodeDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarForpleiningUtgiftPeriodeDatoTilErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftPeriodeDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("periodeDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når forpleiningUtgiftBelop er null")
  void skalKasteIllegalArgumentExceptionNaarForpleiningUtgiftBelopErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftBelop();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("forpleiningUtgiftBelop kan ikke være null");
  }
}
