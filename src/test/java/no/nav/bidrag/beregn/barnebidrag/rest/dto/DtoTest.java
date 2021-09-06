package no.nav.bidrag.beregn.barnebidrag.rest.dto;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import no.nav.bidrag.beregn.barnebidrag.rest.TestUtil;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DtoTest")
class DtoTest {

  // Test av validering av faste dataelementer i dto - barnebidrag
  @Test
  @DisplayName("Skal kaste UgyldigInputException når beregnDatoFra er null")
  void skalKasteUgyldigInputExceptionNaarBeregnDatoFraErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBeregnDatoFra();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::valider)
        .withMessage("beregnDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når beregnDatoTil er null")
  void skalKasteUgyldigInputExceptionNaarBeregnDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBeregnDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::valider)
        .withMessage("beregnDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når grunnlagListe er null")
  void skalKasteUgyldigInputExceptionNaarGrunnlagListeErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullGrunnlagListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::valider)
        .withMessage("grunnlagListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når referanse er null")
  void skalKasteUgyldigInputExceptionNaarReferanseErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullReferanse();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::valider)
        .withMessage("referanse kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når type er null")
  void skalKasteUgyldigInputExceptionNaarTypeErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullType();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::valider)
        .withMessage("type kan ikke være null");
  }

  // Test av validering av faste dataelementer i dto - forholdsmessig fordeling
  @Test
  @DisplayName("Skal kaste UgyldigInputException når beregnDatoFra er null - FF")
  void skalKasteUgyldigInputExceptionNaarBeregnDatoFraErNullFF() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagNullBeregnDatoFra();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::valider)
        .withMessage("beregnDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når beregnDatoTil er null - FF")
  void skalKasteUgyldigInputExceptionNaarBeregnDatoTilErNullFF() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagNullBeregnDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::valider)
        .withMessage("beregnDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når grunnlagListe er null - FF")
  void skalKasteUgyldigInputExceptionNaarGrunnlagListeErNullFF() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagNullGrunnlagListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::valider)
        .withMessage("grunnlagListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når type er null - FF")
  void skalKasteUgyldigInputExceptionNaarTypeErNullFF() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagNullType();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::valider)
        .withMessage("type kan ikke være null");
  }
}
