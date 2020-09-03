package no.nav.bidrag.beregn.barnebidrag.rest.dto;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import no.nav.bidrag.beregn.barnebidrag.rest.TestUtil;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DtoTest")
class DtoTest {

  // Barnebidrag
  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarBBBeregnDatoFraErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBeregnDatoFra();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerBarnebidragGrunnlag)
        .withMessage("beregnDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarBBBeregnDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBeregnDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerBarnebidragGrunnlag)
        .withMessage("beregnDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når soknadBarnFodselsdato er null")
  void skalKasteIllegalArgumentExceptionNaarBBSoknadBarnFodselsdatoErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenSoknadBarnFodselsdato();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerBarnebidragGrunnlag)
        .withMessage("soknadBarnFodselsdato kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når soknadBarnPersonId er null")
  void skalKasteIllegalArgumentExceptionNaarBBSoknadBarnPersonIdErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenSoknadBarnPersonId();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerBarnebidragGrunnlag)
        .withMessage("soknadBarnPersonId kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnBidragsevneGrunnlag er null")
  void skalKasteIllegalArgumentExceptionNaarBBBeregnBidragsevneGrunnlagErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBeregnBidragsevneGrunnlag();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerBarnebidragGrunnlag)
        .withMessage("beregnBidragsevneGrunnlag kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnNettoBarnetilsynGrunnlag er null")
  void skalKasteIllegalArgumentExceptionNaarBBBeregnNettoBarnetilsynGrunnlagErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBeregnNettoBarnetilsynGrunnlag();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerBarnebidragGrunnlag)
        .withMessage("beregnNettoBarnetilsynGrunnlag kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnUnderholdskostnadGrunnlag er null")
  void skalKasteIllegalArgumentExceptionNaarBBBeregnUnderholdskostnadGrunnlagErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBeregnUnderholdskostnadGrunnlag();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerBarnebidragGrunnlag)
        .withMessage("beregnUnderholdskostnadGrunnlag kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnBPAndelUnderholdskostnadGrunnlag er null")
  void skalKasteIllegalArgumentExceptionNaarBBBeregnBPAndelUnderholdskostnadGrunnlagErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBeregnBPAndelUnderholdskostnadGrunnlag();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerBarnebidragGrunnlag)
        .withMessage("beregnBPAndelUnderholdskostnadGrunnlag kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnSamvaersfradragGrunnlag er null")
  void skalKasteIllegalArgumentExceptionNaarBBBeregnSamvaersfradragGrunnlagErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBeregnSamvaersfradragGrunnlag();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerBarnebidragGrunnlag)
        .withMessage("beregnSamvaersfradragGrunnlag kan ikke være null");
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
}
