package no.nav.bidrag.beregn.barnebidrag.rest.dto;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
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
  @DisplayName("Skal kaste IllegalArgumentException når soknadsbarnGrunnlag er null")
  void skalKasteIllegalArgumentExceptionNaarBBSoknadsbarnGrunnlagErNull() {
    var grunnlag = TestUtil.byggTotalBarnebidragGrunnlagUtenSoknadsbarnGrunnlag();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerTotalBarnebidragGrunnlag)
        .withMessage("soknadsbarnGrunnlag kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBPBMGrunnlag er null")
  void skalKasteIllegalArgumentExceptionNaarBBInntektGrunnlagErNull() {
    var grunnlag = TestUtil.byggTotalBarnebidragGrunnlagUtenInntektBPBMGrunnlag();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerTotalBarnebidragGrunnlag)
        .withMessage("inntektBPBMGrunnlag kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnBPBidragsevneGrunnlag er null")
  void skalKasteIllegalArgumentExceptionNaarBBBeregnBidragsevneGrunnlagErNull() {
    var grunnlag = TestUtil.byggTotalBarnebidragGrunnlagUtenBeregnBPBidragsevneGrunnlag();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerTotalBarnebidragGrunnlag)
        .withMessage("beregnBPBidragsevneGrunnlag kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnBMNettoBarnetilsynGrunnlag er null")
  void skalKasteIllegalArgumentExceptionNaarBBBeregnNettoBarnetilsynGrunnlagErNull() {
    var grunnlag = TestUtil.byggTotalBarnebidragGrunnlagUtenBeregnBMNettoBarnetilsynGrunnlag();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerTotalBarnebidragGrunnlag)
        .withMessage("beregnBMNettoBarnetilsynGrunnlag kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnBMUnderholdskostnadGrunnlag er null")
  void skalKasteIllegalArgumentExceptionNaarBBBeregnUnderholdskostnadGrunnlagErNull() {
    var grunnlag = TestUtil.byggTotalBarnebidragGrunnlagUtenBeregnBMUnderholdskostnadGrunnlag();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerTotalBarnebidragGrunnlag)
        .withMessage("beregnBMUnderholdskostnadGrunnlag kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnBPSamvaersfradragGrunnlag er null")
  void skalKasteIllegalArgumentExceptionNaarBBBeregnBPSamvaersfradragGrunnlagErNull() {
    var grunnlag = TestUtil.byggTotalBarnebidragGrunnlagUtenBeregnBPSamvaersfradragGrunnlag();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerTotalBarnebidragGrunnlag)
        .withMessage("beregnBPSamvaersfradragGrunnlag kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnBarnebidragGrunnlag er null")
  void skalKasteIllegalArgumentExceptionNaarBBBeregnBarnebidragGrunnlagErNull() {
    var grunnlag = TestUtil.byggTotalBarnebidragGrunnlagUtenBeregnBarnebidragGrunnlag();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerTotalBarnebidragGrunnlag)
        .withMessage("beregnBarnebidragGrunnlag kan ikke være null");
  }


  @Test
  @DisplayName("Skal kaste IllegalArgumentException når soknadsbarnListe er null")
  void skalKasteIllegalArgumentExceptionNaarSBSoknadsbarnListeErNull() {
    var grunnlag = TestUtil.byggSoknadsbarnGrunnlagUtenSoknadsbarnListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerSoknadsbarn)
        .withMessage("soknadsbarnListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBPPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarInntektBPPeriodeListeErNull() {
    var grunnlag = TestUtil.byggInntektBPBMGrunnlagUtenInntektBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerInntekt)
        .withMessage("inntektBPPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBMPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarInntektBMPeriodeListeErNull() {
    var grunnlag = TestUtil.byggInntektBPBMGrunnlagUtenInntektBMPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerInntekt)
        .withMessage("inntektBMPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når skatteklassePeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarBESkatteklassePeriodeListeErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenSkatteklassePeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.bidragsevneTilCore(emptyList()))
        .withMessage("skatteklassePeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når bostatusPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarBEBostatusPeriodeListeErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenBostatusPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.bidragsevneTilCore(emptyList()))
        .withMessage("bostatusPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når antallBarnIEgetHusholdPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarBEAntallBarnIEgetHusholdPeriodeListeErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenAntallBarnIEgetHusholdPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.bidragsevneTilCore(emptyList()))
        .withMessage("antallBarnIEgetHusholdPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når saerfradragPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarBESaerfradragPeriodeListeErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenSaerfradragPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.bidragsevneTilCore(emptyList()))
        .withMessage("saerfradragPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når faktiskUtgiftPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarNBFaktiskUtgiftPeriodeListeErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.nettoBarnetilsynTilCore(emptyMap(), emptyList()))
        .withMessage("faktiskUtgiftPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarUKBarnetilsynMedStonadPeriodeListeErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerUnderholdskostnad)
        .withMessage("barnetilsynMedStonadPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når forpleiningUtgiftPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarUKForpleiningUtgiftPeriodeListeErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerUnderholdskostnad)
        .withMessage("forpleiningUtgiftPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når samvaersklassePeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarSFSamvaersklassePeriodeListeErNull() {
    var grunnlag = TestUtil.byggSamvaersfradragGrunnlagUtenSamvaersklassePeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::validerSamvaersfradrag)
        .withMessage("samvaersklassePeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBPPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarBBBarnetilleggBPPeriodeListeErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> grunnlag.barnebidragTilCore(emptyList(), emptyList(), emptyList(), emptyList()))
        .withMessage("barnetilleggBPPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBMPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarBBBarnetilleggBMPeriodeListeErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBMPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> grunnlag.barnebidragTilCore(emptyList(), emptyList(), emptyList(), emptyList()))
        .withMessage("barnetilleggBMPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggForsvaretBPPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarBBBarnetilleggForsvaretBPPeriodeListeErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggForsvaretBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> grunnlag.barnebidragTilCore(emptyList(), emptyList(), emptyList(), emptyList()))
        .withMessage("barnetilleggForsvaretBPPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når deltBostedBPPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarBBDeltBostedBPPeriodeListeErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenDeltBostedBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> grunnlag.barnebidragTilCore(emptyList(), emptyList(), emptyList(), emptyList()))
        .withMessage("deltBostedBPPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når andreLopendeBidragBPPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarBBAndreLopendeBidragBPPeriodeListeErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenAndreLopendeBidragBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> grunnlag.barnebidragTilCore(emptyList(), emptyList(), emptyList(), emptyList()))
        .withMessage("andreLopendeBidragBPPeriodeListe kan ikke være null");
  }


  // Søknadsbarn
  @Test
  @DisplayName("Skal kaste IllegalArgumentException når soknadsbarnFodselsdato er null")
  void skalKasteIllegalArgumentExceptionNaarSBSoknadsbarnFodselsdatoErNull() {
    var grunnlag = TestUtil.byggSoknadsbarnGrunnlagUtenSoknadsbarnFodselsdato().getSoknadsbarnListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerSoknadsbarn)
        .withMessage("soknadsbarnFodselsdato kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når soknadsbarnPersonId er null")
  void skalKasteIllegalArgumentExceptionNaarSBSoknadsbarnPersonIdErNull() {
    var grunnlag = TestUtil.byggSoknadsbarnGrunnlagUtenSoknadsbarnPersonId().getSoknadsbarnListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerSoknadsbarn)
        .withMessage("soknadsbarnPersonId kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarSBInntektPeriodeListeErNull() {
    var grunnlag = TestUtil.byggSoknadsbarnGrunnlagUtenInntektPeriodeListe().getSoknadsbarnListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerSoknadsbarn)
        .withMessage("SB inntektPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarSBInntektDatoFraTilErNull() {
    var grunnlag = TestUtil.byggSoknadsbarnGrunnlagUtenInntektDatoFraTil().getSoknadsbarnListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerSoknadsbarn)
        .withMessage("SB inntektDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarSBInntektDatoFraErNull() {
    var grunnlag = TestUtil.byggSoknadsbarnGrunnlagUtenInntektDatoFra().getSoknadsbarnListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerSoknadsbarn)
        .withMessage("SB inntektDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarSBInntektDatoTilErNull() {
    var grunnlag = TestUtil.byggSoknadsbarnGrunnlagUtenInntektDatoTil().getSoknadsbarnListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerSoknadsbarn)
        .withMessage("SB inntektDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektType er null")
  void skalKasteIllegalArgumentExceptionNaarSBInntektTypeErNull() {
    var grunnlag = TestUtil.byggSoknadsbarnGrunnlagUtenInntektType().getSoknadsbarnListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerSoknadsbarn)
        .withMessage("SB inntektType kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBelop er null")
  void skalKasteIllegalArgumentExceptionNaarSBInntektBelopErNull() {
    var grunnlag = TestUtil.byggSoknadsbarnGrunnlagUtenInntektBelop().getSoknadsbarnListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerSoknadsbarn)
        .withMessage("SB inntektBelop kan ikke være null");
  }


  // Inntekt
  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBPDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarInntektBPDatoFraTilErNull() {
    var grunnlag = TestUtil.byggInntektGrunnlagUtenInntektBPDatoFraTil().getInntektBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).validerInntekt("BP"))
        .withMessage("BP inntektDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBPDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarInntektBPDatoFraErNull() {
    var grunnlag = TestUtil.byggInntektGrunnlagUtenInntektBPDatoFra().getInntektBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).validerInntekt("BP"))
        .withMessage("BP inntektDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBPDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarInntektBPDatoTilErNull() {
    var grunnlag = TestUtil.byggInntektGrunnlagUtenInntektBPDatoTil().getInntektBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).validerInntekt("BP"))
        .withMessage("BP inntektDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBPType er null")
  void skalKasteIllegalArgumentExceptionNaarInntektBPTypeErNull() {
    var grunnlag = TestUtil.byggInntektGrunnlagUtenInntektBPType().getInntektBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).validerInntekt("BP"))
        .withMessage("BP inntektType kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBPBelop er null")
  void skalKasteIllegalArgumentExceptionNaarInntektBPBelopErNull() {
    var grunnlag = TestUtil.byggInntektGrunnlagUtenInntektBPBelop().getInntektBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).validerInntekt("BP"))
        .withMessage("BP inntektBelop kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBMDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarInntektBMDatoFraTilErNull() {
    var grunnlag = TestUtil.byggInntektGrunnlagUtenInntektBMDatoFraTil().getInntektBMPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).validerInntekt("BM"))
        .withMessage("BM inntektDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBMDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarInntektBMDatoFraErNull() {
    var grunnlag = TestUtil.byggInntektGrunnlagUtenInntektBMDatoFra().getInntektBMPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).validerInntekt("BM"))
        .withMessage("BM inntektDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBMDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarInntektBMDatoTilErNull() {
    var grunnlag = TestUtil.byggInntektGrunnlagUtenInntektBMDatoTil().getInntektBMPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).validerInntekt("BM"))
        .withMessage("BM inntektDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBMType er null")
  void skalKasteIllegalArgumentExceptionNaarInntektBMTypeErNull() {
    var grunnlag = TestUtil.byggInntektGrunnlagUtenInntektBMType().getInntektBMPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).validerInntekt("BM"))
        .withMessage("BM inntektType kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBMBelop er null")
  void skalKasteIllegalArgumentExceptionNaarInntektBMBelopErNull() {
    var grunnlag = TestUtil.byggInntektGrunnlagUtenInntektBMBelop().getInntektBMPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).validerInntekt("BM"))
        .withMessage("BM inntektBelop kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBMDeltFordel er null")
  void skalKasteIllegalArgumentExceptionNaarInntektBMDeltFordelErNull() {
    var grunnlag = TestUtil.byggInntektGrunnlagUtenInntektBMDeltFordel().getInntektBMPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).validerInntekt("BM"))
        .withMessage("BM deltFordel kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når inntektBMSkatteklasse2 er null")
  void skalKasteIllegalArgumentExceptionNaarInntektBMSkatteklasse2ErNull() {
    var grunnlag = TestUtil.byggInntektGrunnlagUtenInntektBMSkatteklasse2().getInntektBMPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).validerInntekt("BM"))
        .withMessage("BM skatteklasse2 kan ikke være null");
  }


  // Bidragsevne
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
        .withMessage("skatteklasseDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når skatteklasseDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarBESkatteklasseDatoTilErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenSkatteklasseDatoTil().getSkatteklassePeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("skatteklasseDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når skatteklasseId er null")
  void skalKasteIllegalArgumentExceptionNaarBESkatteklasseErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenSkatteklasseId().getSkatteklassePeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("skatteklasseId kan ikke være null");
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
        .withMessage("bostatusDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når bostatusDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarBEBostatusDatoTilErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenBostatusDatoTil().getBostatusPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("bostatusDatoTil kan ikke være null");
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
        .withMessage("antallBarnIEgetHusholdDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når antallBarnIEgetHusholdDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarBEAntallBarnIEgetHusholdDatoTilErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenAntallBarnIEgetHusholdDatoTil().getAntallBarnIEgetHusholdPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("antallBarnIEgetHusholdDatoTil kan ikke være null");
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
        .withMessage("saerfradragDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når saerfradragDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarBESaerfradragDatoTilErNull() {
    var grunnlag = TestUtil.byggBidragsevneGrunnlagUtenSaerfradragDatoTil().getSaerfradragPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("saerfradragDatoTil kan ikke være null");
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
  @DisplayName("Skal kaste IllegalArgumentException når faktiskUtgiftDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarNBFaktiskUtgiftDatoFraTilErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftDatoFraTil().getFaktiskUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).tilCore(emptyMap()))
        .withMessage("faktiskUtgiftDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når faktiskUtgiftDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarNBFaktiskUtgiftDatoFraErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftDatoFra().getFaktiskUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).tilCore(emptyMap()))
        .withMessage("faktiskUtgiftDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når faktiskUtgiftDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarNBFaktiskUtgiftDatoTilErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftDatoTil().getFaktiskUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).tilCore(emptyMap()))
        .withMessage("faktiskUtgiftDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når faktiskUtgiftSoknadsbarnPersonId er null")
  void skalKasteIllegalArgumentExceptionNaarNBFaktiskUtgiftSoknadsbarnPersonIdErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftSoknadsbarnPersonId().getFaktiskUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).tilCore(emptyMap()))
        .withMessage("faktiskUtgiftSoknadsbarnPersonId kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når faktiskUtgiftBelop er null")
  void skalKasteIllegalArgumentExceptionNaarNBFaktiskUtgiftBelopErNull() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftBelop().getFaktiskUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).tilCore(emptyMap()))
        .withMessage("faktiskUtgiftBelop kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når faktiskUtgiftSoknadsbarnPersonId ikke har matchende fødselsdato")
  void skalKasteIllegalArgumentExceptionNaarNBFaktiskUtgftSoknadsbarnPersonIdIkkeHarMatchendeFodselsdato() {
    var grunnlag = TestUtil.byggNettoBarnetilsynGrunnlag().getFaktiskUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).tilCore(emptyMap()))
        .withMessage("Søknadsbarn med id 1 har ingen korresponderende fødselsdato i soknadsbarnListe");
  }


  // Underholdskostnad
  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarUKBarnetilsynMedStonadDatoFraTilErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadDatoFraTil().getBarnetilsynMedStonadPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerBarnetilsynMedStonad)
        .withMessage("barnetilsynMedStonadDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarUKBarnetilsynMedStonadDatoFraErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadDatoFra().getBarnetilsynMedStonadPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerBarnetilsynMedStonad)
        .withMessage("barnetilsynMedStonadDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarUKBarnetilsynMedStonadDatoTilErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadDatoTil().getBarnetilsynMedStonadPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerBarnetilsynMedStonad)
        .withMessage("barnetilsynMedStonadDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadSoknadsbarnPersonId er null")
  void skalKasteIllegalArgumentExceptionNaarUKBarnetilsynMedStonadSoknadsbarnPersonIdErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadSoknadsbarnPersonId().getBarnetilsynMedStonadPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerBarnetilsynMedStonad)
        .withMessage("barnetilsynMedStonadSoknadsbarnPersonId kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadTilsynType er null")
  void skalKasteIllegalArgumentExceptionNaarUKBarnetilsynMedStonadTilsynTypeErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadTilsynType().getBarnetilsynMedStonadPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerBarnetilsynMedStonad)
        .withMessage("barnetilsynMedStonadTilsynType kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilsynMedStonadStonadType er null")
  void skalKasteIllegalArgumentExceptionNaarUKBarnetilsynMedStonadStonadTypeErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenBarnetilsynMedStonadStonadType().getBarnetilsynMedStonadPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerBarnetilsynMedStonad)
        .withMessage("barnetilsynMedStonadStonadType kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når forpleiningUtgiftDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarUKForpleiningUtgiftDatoFraTilErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftDatoFraTil().getForpleiningUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerForpleiningUtgift)
        .withMessage("forpleiningUtgiftDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når forpleiningUtgiftDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarUKForpleiningUtgiftDatoFraErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftDatoFra().getForpleiningUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerForpleiningUtgift)
        .withMessage("forpleiningUtgiftDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når forpleiningUtgiftDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarUKForpleiningUtgiftDatoTilErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftDatoTil().getForpleiningUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerForpleiningUtgift)
        .withMessage("forpleiningUtgiftDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når forpleiningUtgiftSoknadsbarnPersonId er null")
  void skalKasteIllegalArgumentExceptionNaarUKForpleiningUtgiftSoknadsbarnPersonIdErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftSoknadsbarnPersonId().getForpleiningUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerForpleiningUtgift)
        .withMessage("forpleiningUtgiftSoknadsbarnPersonId kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når forpleiningUtgiftBelop er null")
  void skalKasteIllegalArgumentExceptionNaarUKForpleiningUtgiftBelopErNull() {
    var grunnlag = TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftBelop().getForpleiningUtgiftPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerForpleiningUtgift)
        .withMessage("forpleiningUtgiftBelop kan ikke være null");
  }


  // Samværsfradrag
  @Test
  @DisplayName("Skal kaste IllegalArgumentException når samvaersklasseDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarSFSamvaersklasseDatoFraTilErNull() {
    var grunnlag = TestUtil.byggSamvaersfradragGrunnlagUtenSamvaersklasseDatoFraTil().getSamvaersklassePeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerSamvaersklasse)
        .withMessage("samvaersklasseDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når samvaersklasseDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarSFSamvaersklasseDatoFraErNull() {
    var grunnlag = TestUtil.byggSamvaersfradragGrunnlagUtenSamvaersklasseDatoFra().getSamvaersklassePeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerSamvaersklasse)
        .withMessage("samvaersklasseDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når samvaersklasseDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarSFSamvaersklasseDatoTilErNull() {
    var grunnlag = TestUtil.byggSamvaersfradragGrunnlagUtenSamvaersklasseDatoTil().getSamvaersklassePeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerSamvaersklasse)
        .withMessage("samvaersklasseDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når samvaersklasseSoknadsbarnPersonId er null")
  void skalKasteIllegalArgumentExceptionNaarSFSamvaersklasseSoknadsbarnPersonIdErNull() {
    var grunnlag = TestUtil.byggSamvaersfradragGrunnlagUtenSamvaersklasseSoknadsbarnPersonId().getSamvaersklassePeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerSamvaersklasse)
        .withMessage("samvaersklasseSoknadsbarnPersonId kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når samvaersklasseId er null")
  void skalKasteIllegalArgumentExceptionNaarSFSamvaersklasseIdErNull() {
    var grunnlag = TestUtil.byggSamvaersfradragGrunnlagUtenSamvaersklasseId().getSamvaersklassePeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::validerSamvaersklasse)
        .withMessage("samvaersklasseId kan ikke være null");
  }


  // Barnebidrag
  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBPDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarBBBarnetilleggBPDatoFraTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBPDatoFraTil().getBarnetilleggBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).tilCore("BP"))
        .withMessage("BP barnetilleggDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBPDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarBBBarnetilleggBPDatoFraErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBPDatoFra().getBarnetilleggBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).tilCore("BP"))
        .withMessage("BP barnetilleggDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBPDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarBBBarnetilleggBPDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBPDatoTil().getBarnetilleggBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).tilCore("BP"))
        .withMessage("BP barnetilleggDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBPSoknadsbarnPersonId er null")
  void skalKasteIllegalArgumentExceptionNaarBBBarnetilleggBPSoknadsbarnPersonIdErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBPSoknadsbarnPersonId().getBarnetilleggBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).tilCore("BP"))
        .withMessage("BP barnetilleggSoknadsbarnPersonId kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBPBruttoBelop er null")
  void skalKasteIllegalArgumentExceptionNaarBBBarnetilleggBPBruttoBelopErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBPBruttoBelop().getBarnetilleggBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).tilCore("BP"))
        .withMessage("BP barnetilleggBruttoBelop kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBPSkattProsent er null")
  void skalKasteIllegalArgumentExceptionNaarBBBarnetilleggBPSkattProsentErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBPSkattProsent().getBarnetilleggBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).tilCore("BP"))
        .withMessage("BP barnetilleggSkattProsent kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBMDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarBBBarnetilleggBMDatoFraTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBMDatoFraTil().getBarnetilleggBMPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).tilCore("BM"))
        .withMessage("BM barnetilleggDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBMDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarBBBarnetilleggBMDatoFraErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBMDatoFra().getBarnetilleggBMPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).tilCore("BM"))
        .withMessage("BM barnetilleggDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBMDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarBBBarnetilleggBMDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBMDatoTil().getBarnetilleggBMPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).tilCore("BM"))
        .withMessage("BM barnetilleggDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBMSoknadsbarnPersonId er null")
  void skalKasteIllegalArgumentExceptionNaarBBBarnetilleggBMSoknadsbarnPersonIdErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBMSoknadsbarnPersonId().getBarnetilleggBMPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).tilCore("BM"))
        .withMessage("BM barnetilleggSoknadsbarnPersonId kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBMBruttoBelop er null")
  void skalKasteIllegalArgumentExceptionNaarBBBarnetilleggBMBruttoBelopErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBMBruttoBelop().getBarnetilleggBMPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).tilCore("BM"))
        .withMessage("BM barnetilleggBruttoBelop kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggBMSkattProsent er null")
  void skalKasteIllegalArgumentExceptionNaarBBBarnetilleggBMSkattProsentErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBMSkattProsent().getBarnetilleggBMPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(() -> grunnlag.get(0).tilCore("BM"))
        .withMessage("BM barnetilleggSkattProsent kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggForsvaretDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarBBBarnetilleggForsvaretDatoFraTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggForsvaretDatoFraTil().getBarnetilleggForsvaretBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("barnetilleggForsvaretDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggForsvaretDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarBBBarnetilleggForsvaretDatoFraErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggForsvaretDatoFra().getBarnetilleggForsvaretBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("barnetilleggForsvaretDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggForsvaretDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarBBBarnetilleggForsvaretDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggForsvaretDatoTil().getBarnetilleggForsvaretBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("barnetilleggForsvaretDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnetilleggForsvaretIPeriode er null")
  void skalKasteIllegalArgumentExceptionNaarBBBarnetilleggForsvaretIPeriodeErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggForsvaretIPeriode().getBarnetilleggForsvaretBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("barnetilleggForsvaretIPeriode kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når deltBostedDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarBBDeltBostedDatoFraTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenDeltBostedDatoFraTil().getDeltBostedBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("deltBostedDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når deltBostedDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarBBDeltBostedDatoFraErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenDeltBostedDatoFra().getDeltBostedBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("deltBostedDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når deltBostedDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarBBDeltBostedDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenDeltBostedDatoTil().getDeltBostedBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("deltBostedDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når deltBostedSoknadsbarnPersonId er null")
  void skalKasteIllegalArgumentExceptionNaarBBDeltBostedSoknadsbarnPersonIdErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenDeltBostedSoknadsbarnPersonId().getDeltBostedBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("deltBostedSoknadsbarnPersonId kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når deltBostedIPeriode er null")
  void skalKasteIllegalArgumentExceptionNaarBBDeltBostedIPeriodeErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenDeltBostedIPeriode().getDeltBostedBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("deltBostedIPeriode kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når andreLopendeBidragDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarBBAndreLopendeBidragDatoFraTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenAndreLopendeBidragDatoFraTil().getAndreLopendeBidragBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("andreLopendeBidragDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når andreLopendeBidragDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarBBAndreLopendeBidragDatoFraErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenAndreLopendeBidragDatoFra().getAndreLopendeBidragBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("andreLopendeBidragDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når andreLopendeBidragDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarBBAndreLopendeBidragDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenAndreLopendeBidragDatoTil().getAndreLopendeBidragBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("andreLopendeBidragDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når andreLopendeBidragBarnPersonId er null")
  void skalKasteIllegalArgumentExceptionNaarBBAndreLopendeBidragBarnPersonIdErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenAndreLopendeBidragBarnPersonId().getAndreLopendeBidragBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("andreLopendeBidragBarnPersonId kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når andreLopendeBidragBidragBelop er null")
  void skalKasteIllegalArgumentExceptionNaarBBAndreLopendeBidragBidragBelopErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenAndreLopendeBidragBidragBelop().getAndreLopendeBidragBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("andreLopendeBidragBidragBelop kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når andreLopendeBidragSamvaersfradragBelop er null")
  void skalKasteIllegalArgumentExceptionNaarBBAndreLopendeBidragSamvaersfradragBelopErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenAndreLopendeBidragSamvaersfradragBelop().getAndreLopendeBidragBPPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag.get(0)::tilCore)
        .withMessage("andreLopendeBidragSamvaersfradragBelop kan ikke være null");
  }


  // Forholdsmessig fordeling
  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarFFBeregnDatoFraErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBeregnDatoFra();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("beregnDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarFFBeregnDatoTilErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBeregnDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("beregnDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når bidragsevnePeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarFFBidragsevnePeriodeListeErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBidragsevnePeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("bidragsevnePeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når bidragsevneDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarFFBidragsevneDatoFraTilErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBidragsevneDatoFraTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("bidragsevneDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når bidragsevneDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarFFBidragsevneDatoFraErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBidragsevneDatoFra();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("bidragsevneDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når bidragsevneDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarFFBidragsevneDatoTilErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBidragsevneDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("bidragsevneDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når bidragsevneBelop er null")
  void skalKasteIllegalArgumentExceptionNaarFFBidragsevneBelopErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBidragsevneBelop();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("bidragsevneBelop kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når bidragsevne25ProsentInntekt er null")
  void skalKasteIllegalArgumentExceptionNaarFFBidragsevne25ProsentInntektErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBidragsevne25ProsentInntekt();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("bidragsevne25ProsentInntekt kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når bidragSakPeriodeListe er null")
  void skalKasteIllegalArgumentExceptionNaarFFBidragSakPeriodeListeErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBeregnetBidragSakPeriodeListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("beregnetBidragSakPeriodeListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnetBidragSaksnr er null")
  void skalKasteIllegalArgumentExceptionNaarFFBeregnetBidragSaksnrErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBeregnetBidragSaksnr();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("beregnetBidragSaksnr kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnetBidragDatoFraTil er null")
  void skalKasteIllegalArgumentExceptionNaarFFBeregnetBidragDatoFraTilErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBeregnetBidragDatoFraTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("beregnetBidragDatoFraTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnetBidragDatoFra er null")
  void skalKasteIllegalArgumentExceptionNaarFFBeregnetBidragDatoFraErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBeregnetBidragDatoFra();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("beregnetBidragDatoFra kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnetBidragDatoTil er null")
  void skalKasteIllegalArgumentExceptionNaarFFBeregnetBidragDatoTilErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBeregnetBidragDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("beregnetBidragDatoTil kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når beregnetBidragPerBarnListe er null")
  void skalKasteIllegalArgumentExceptionNaarFFBeregnetBidragPerBarnListeErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBeregnetBidragPerBarnListe();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("beregnetBidragPerBarnListe kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når barnPersonId er null")
  void skalKasteIllegalArgumentExceptionNaarFFBarnPersonIdErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBarnPersonId();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("barnPersonId kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste IllegalArgumentException når bidragBelop er null")
  void skalKasteIllegalArgumentExceptionNaarFFBidragBelopErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBidragBelop();
    assertThatExceptionOfType(UgyldigInputException.class).isThrownBy(grunnlag::tilCore)
        .withMessage("bidragBelop kan ikke være null");
  }
}
