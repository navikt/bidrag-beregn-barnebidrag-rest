package no.nav.bidrag.beregn.barnebidrag.rest.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.util.HashMap;
import java.util.Map;
import no.nav.bidrag.beregn.barnebidrag.rest.BidragBeregnBarnebidragLocal;
import no.nav.bidrag.beregn.barnebidrag.rest.TestUtil;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonListe;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BidragBeregnBarnebidragLocal.class)
@DisplayName("CoreMapperTest")
class CoreMapperTest {

  @Autowired
  private BidragsevneCoreMapper bidragsevneCoreMapper;

  @Autowired
  private NettoBarnetilsynCoreMapper nettoBarnetilsynCoreMapper;

  @Autowired
  private UnderholdskostnadCoreMapper underholdskostnadCoreMapper;

  @Autowired
  private BPAndelUnderholdskostnadCoreMapper bpAndelUnderholdskostnadCoreMapper;

  @Autowired
  private SamvaersfradragCoreMapper samvaersfradragCoreMapper;

  @Autowired
  private BarnebidragCoreMapper barnebidragCoreMapper;

  @Autowired
  private ForholdsmessigFordelingCoreMapper forholdsmessigFordelingCoreMapper;

  //==================================================================================================================================================

  // Test av validering i BidragsevneCoreMapper

  // InntektBP - rolle
  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP rolle mangler")
  void skalKasteUgyldigInputExceptionNaarInntektBPRolleMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektBPRolle();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("rolle i objekt av type Inntekt mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP rolle er null")
  void skalKasteUgyldigInputExceptionNaarInntektBPRolleErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullInntektBPRolle();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("rolle i objekt av type Inntekt er null");
  }

  // InntektBP - datoFom
  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP datoFom mangler")
  void skalKasteUgyldigInputExceptionNaarInntektBPDatoFomMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektBPDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoFom i objekt av type Inntekt mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP datoFom er null")
  void skalKasteUgyldigInputExceptionNaarInntektBPDatoFomErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullInntektBPDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoFom i objekt av type Inntekt er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP datoFom har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarInntektBPDatoFomHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiInntektBPDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoFom i objekt av type Inntekt har ugyldig verdi");
  }

  // InntektBP - datoTil
  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP datoTil mangler")
  void skalKasteUgyldigInputExceptionNaarInntektBPDatoTilMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektBPDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoTil i objekt av type Inntekt mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP datoTil er null")
  void skalKasteUgyldigInputExceptionNaarInntektBPDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullInntektBPDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoTil i objekt av type Inntekt er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP datoTil har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarInntektBPDatoTilHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiInntektBPDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoTil i objekt av type Inntekt har ugyldig verdi");
  }

  // InntektBP - inntektType
  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP inntektType mangler")
  void skalKasteUgyldigInputExceptionNaarInntektBPInntektTypeMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektBPInntektType();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("inntektType i objekt av type Inntekt mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP inntektType er null")
  void skalKasteUgyldigInputExceptionNaarInntektBPInntektTypeErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullInntektBPInntektType();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("inntektType i objekt av type Inntekt er null");
  }

  // InntektBP - belop
  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP belop mangler")
  void skalKasteUgyldigInputExceptionNaarInntektBPBelopMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektBPBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("belop i objekt av type Inntekt mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP belop er null")
  void skalKasteUgyldigInputExceptionNaarInntektBPBelopErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullInntektBPBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("belop i objekt av type Inntekt mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP belop har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarInntektBPBelopHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiInntektBPBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("belop i objekt av type Inntekt mangler, er null eller har ugyldig verdi");
  }

  // BarnIHusstand - datoFom
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnIHusstand datoFom mangler")
  void skalKasteUgyldigInputExceptionNaarBarnIHusstandDatoFomMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnIHusstandDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoFom i objekt av type BarnIHusstand mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnIHusstand datoFom er null")
  void skalKasteUgyldigInputExceptionNaarBarnIHusstandDatoFomErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBarnIHusstandDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoFom i objekt av type BarnIHusstand er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnIHusstand datoFom har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBarnIHusstandDatoFomHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiBarnIHusstandDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoFom i objekt av type BarnIHusstand har ugyldig verdi");
  }

  // BarnIHusstand - datoTil
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnIHusstand datoTil mangler")
  void skalKasteUgyldigInputExceptionNaarBarnIHusstandDatoTilMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnIHusstandDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoTil i objekt av type BarnIHusstand mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnIHusstand datoTil er null")
  void skalKasteUgyldigInputExceptionNaarBarnIHusstandDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBarnIHusstandDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoTil i objekt av type BarnIHusstand er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnIHusstand datoTil har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBarnIHusstandDatoTilHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiBarnIHusstandDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoTil i objekt av type BarnIHusstand har ugyldig verdi");
  }

  // BarnIHusstand - antall
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnIHusstand antall mangler")
  void skalKasteUgyldigInputExceptionNaarBarnIHusstandAntallMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnIHusstandAntall();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("antall i objekt av type BarnIHusstand mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnIHusstand antall er null")
  void skalKasteUgyldigInputExceptionNaarBarnIHusstandAntallErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBarnIHusstandAntall();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("antall i objekt av type BarnIHusstand mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnIHusstand antall har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBarnIHusstandAntallHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiBarnIHusstandAntall();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("antall i objekt av type BarnIHusstand mangler, er null eller har ugyldig verdi");
  }

  // Bostatus - datoFom
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Bostatus datoFom mangler")
  void skalKasteUgyldigInputExceptionNaarBostatusDatoFomMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBostatusDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoFom i objekt av type Bostatus mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Bostatus datoFom er null")
  void skalKasteUgyldigInputExceptionNaarBostatusDatoFomErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBostatusDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoFom i objekt av type Bostatus er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Bostatus datoFom har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBostatusDatoFomHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiBostatusDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoFom i objekt av type Bostatus har ugyldig verdi");
  }

  // Bostatus - datoTil
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Bostatus datoTil mangler")
  void skalKasteUgyldigInputExceptionNaarBostatusDatoTilMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBostatusDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoTil i objekt av type Bostatus mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Bostatus datoTil er null")
  void skalKasteUgyldigInputExceptionNaarBostatusDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBostatusDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoTil i objekt av type Bostatus er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Bostatus datoTil har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBostatusDatoTilHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiBostatusDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoTil i objekt av type Bostatus har ugyldig verdi");
  }

  // Bostatus - bostatusKode
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Bostatus bostatusKode mangler")
  void skalKasteUgyldigInputExceptionNaarBostatusBostatusKodeMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBostatusBostatusKode();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("bostatusKode i objekt av type Bostatus mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Bostatus bostatusKode er null")
  void skalKasteUgyldigInputExceptionNaarBostatusBostatusKodeErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBostatusBostatusKode();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("bostatusKode i objekt av type Bostatus er null");
  }

  // Saerfradrag - datoFom
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Saerfradrag datoFom mangler")
  void skalKasteUgyldigInputExceptionNaarSaerfradragDatoFomMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenSaerfradragDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoFom i objekt av type Saerfradrag mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Saerfradrag datoFom er null")
  void skalKasteUgyldigInputExceptionNaarSaerfradragDatoFomErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullSaerfradragDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoFom i objekt av type Saerfradrag er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Saerfradrag datoFom har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarSaerfradragDatoFomHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiSaerfradragDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoFom i objekt av type Saerfradrag har ugyldig verdi");
  }

  // Saerfradrag - datoTil
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Saerfradrag datoTil mangler")
  void skalKasteUgyldigInputExceptionNaarSaerfradragDatoTilMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenSaerfradragDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoTil i objekt av type Saerfradrag mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Saerfradrag datoTil er null")
  void skalKasteUgyldigInputExceptionNaarSaerfradragDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullSaerfradragDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoTil i objekt av type Saerfradrag er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Saerfradrag datoTil har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarSaerfradragDatoTilHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiSaerfradragDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoTil i objekt av type Saerfradrag har ugyldig verdi");
  }

  // Saerfradrag - saerfradragKode
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Saerfradrag saerfradragKode mangler")
  void skalKasteUgyldigInputExceptionNaarSaerfradragSaerfradragKodeMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenSaerfradragSaerfradragKode();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("saerfradragKode i objekt av type Saerfradrag mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Saerfradrag saerfradragKode er null")
  void skalKasteUgyldigInputExceptionNaarSaerfradragSaerfradragKodeErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullSaerfradragSaerfradragKode();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("saerfradragKode i objekt av type Saerfradrag er null");
  }

  // Skatteklasse - datoFom
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Skatteklasse datoFom mangler")
  void skalKasteUgyldigInputExceptionNaarSkatteklasseDatoFomMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenSkatteklasseDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoFom i objekt av type Skatteklasse mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Skatteklasse datoFom er null")
  void skalKasteUgyldigInputExceptionNaarSkatteklasseDatoFomErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullSkatteklasseDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoFom i objekt av type Skatteklasse er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Skatteklasse datoFom har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarSkatteklasseDatoFomHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiSkatteklasseDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoFom i objekt av type Skatteklasse har ugyldig verdi");
  }

  // Skatteklasse - datoTil
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Skatteklasse datoTil mangler")
  void skalKasteUgyldigInputExceptionNaarSkatteklasseDatoTilMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenSkatteklasseDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoTil i objekt av type Skatteklasse mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Skatteklasse datoTil er null")
  void skalKasteUgyldigInputExceptionNaarSkatteklasseDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullSkatteklasseDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoTil i objekt av type Skatteklasse er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Skatteklasse datoTil har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarSkatteklasseDatoTilHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiSkatteklasseDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("datoTil i objekt av type Skatteklasse har ugyldig verdi");
  }

  // Skatteklasse - skatteklasseId
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Skatteklasse skatteklasseId mangler")
  void skalKasteUgyldigInputExceptionNaarSkatteklasseSkatteklasseIdMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenSkatteklasseSkatteklasseId();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("skatteklasseId i objekt av type Skatteklasse mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Skatteklasse skatteklasseId er null")
  void skalKasteUgyldigInputExceptionNaarSkatteklasseSkatteklasseIdErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullSkatteklasseSkatteklasseId();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("skatteklasseId i objekt av type Skatteklasse mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Skatteklasse skatteklasseId har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarSkatteklasseSkatteklasseIdHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiSkatteklasseSkatteklasseId();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bidragsevneCoreMapper.mapBidragsevneGrunnlagTilCore(grunnlag, new SjablonListe()))
        .withMessage("skatteklasseId i objekt av type Skatteklasse mangler, er null eller har ugyldig verdi");
  }

  //==================================================================================================================================================

  // Test av validering i NettoBarnetilsynCoreMapper

  // FaktiskUtgift - soknadsbarnId
  @Test
  @DisplayName("Skal kaste UgyldigInputException når FaktiskUtgift soknadsbarnId mangler")
  void skalKasteUgyldigInputExceptionNaarFaktiskUtgiftSoknadsbarnIdMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenFaktiskUtgiftSoknadsbarnId();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> nettoBarnetilsynCoreMapper.mapNettoBarnetilsynGrunnlagTilCore(grunnlag, new SjablonListe(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("soknadsbarnId i objekt av type FaktiskUtgift mangler");
  }

  // FaktiskUtgift - datoFom
  @Test
  @DisplayName("Skal kaste UgyldigInputException når FaktiskUtgift datoFom mangler")
  void skalKasteUgyldigInputExceptionNaarFaktiskUtgiftDatoFomMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenFaktiskUtgiftDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> nettoBarnetilsynCoreMapper.mapNettoBarnetilsynGrunnlagTilCore(grunnlag, new SjablonListe(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoFom i objekt av type FaktiskUtgift mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når FaktiskUtgift datoFom er null")
  void skalKasteUgyldigInputExceptionNaarFaktiskUtgiftDatoFomErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullFaktiskUtgiftDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> nettoBarnetilsynCoreMapper.mapNettoBarnetilsynGrunnlagTilCore(grunnlag, new SjablonListe(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoFom i objekt av type FaktiskUtgift er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når FaktiskUtgift datoFom har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarFaktiskUtgiftDatoFomHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiFaktiskUtgiftDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> nettoBarnetilsynCoreMapper.mapNettoBarnetilsynGrunnlagTilCore(grunnlag, new SjablonListe(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoFom i objekt av type FaktiskUtgift har ugyldig verdi");
  }

  // FaktiskUtgift - datoTil
  @Test
  @DisplayName("Skal kaste UgyldigInputException når FaktiskUtgift datoTil mangler")
  void skalKasteUgyldigInputExceptionNaarFaktiskUtgiftDatoTilMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenFaktiskUtgiftDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> nettoBarnetilsynCoreMapper.mapNettoBarnetilsynGrunnlagTilCore(grunnlag, new SjablonListe(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoTil i objekt av type FaktiskUtgift mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når FaktiskUtgift datoTil er null")
  void skalKasteUgyldigInputExceptionNaarFaktiskUtgiftDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullFaktiskUtgiftDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> nettoBarnetilsynCoreMapper.mapNettoBarnetilsynGrunnlagTilCore(grunnlag, new SjablonListe(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoTil i objekt av type FaktiskUtgift er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når FaktiskUtgift datoTil har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarFaktiskUtgiftDatoTilHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiFaktiskUtgiftDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> nettoBarnetilsynCoreMapper.mapNettoBarnetilsynGrunnlagTilCore(grunnlag, new SjablonListe(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoTil i objekt av type FaktiskUtgift har ugyldig verdi");
  }

  // FaktiskUtgift - belop
  @Test
  @DisplayName("Skal kaste UgyldigInputException når FaktiskUtgift belop mangler")
  void skalKasteUgyldigInputExceptionNaarFaktiskUtgiftBelopMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenFaktiskUtgiftBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> nettoBarnetilsynCoreMapper.mapNettoBarnetilsynGrunnlagTilCore(grunnlag, new SjablonListe(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("belop i objekt av type FaktiskUtgift mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når FaktiskUtgift belop er null")
  void skalKasteUgyldigInputExceptionNaarFaktiskUtgiftBelopErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullFaktiskUtgiftBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> nettoBarnetilsynCoreMapper.mapNettoBarnetilsynGrunnlagTilCore(grunnlag, new SjablonListe(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("belop i objekt av type FaktiskUtgift mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når FaktiskUtgift belop har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarFaktiskUtgiftBelopHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiFaktiskUtgiftBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> nettoBarnetilsynCoreMapper.mapNettoBarnetilsynGrunnlagTilCore(grunnlag, new SjablonListe(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("belop i objekt av type FaktiskUtgift mangler, er null eller har ugyldig verdi");
  }

  //==================================================================================================================================================

  // Test av validering i UnderholdskostnadCoreMapper

  // BarnetilsynMedStonad - soknadsbarnId
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilsynMedStonad soknadsbarnId mangler")
  void skalKasteUgyldigInputExceptionNaarBarnetilsynMedStonadSoknadsbarnIdMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilsynMedStonadSoknadsbarnId();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyNettoBarnetilsynResultatCore(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("soknadsbarnId i objekt av type BarnetilsynMedStonad mangler");
  }

  // BarnetilsynMedStonad - datoFom
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilsynMedStonad datoFom mangler")
  void skalKasteUgyldigInputExceptionNaarBarnetilsynMedStonadDatoFomMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilsynMedStonadDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyNettoBarnetilsynResultatCore(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoFom i objekt av type BarnetilsynMedStonad mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilsynMedStonad datoFom er null")
  void skalKasteUgyldigInputExceptionNaarBarnetilsynMedStonadDatoFomErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBarnetilsynMedStonadDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyNettoBarnetilsynResultatCore(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoFom i objekt av type BarnetilsynMedStonad er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilsynMedStonad datoFom har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBarnetilsynMedStonadDatoFomHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiBarnetilsynMedStonadDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyNettoBarnetilsynResultatCore(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoFom i objekt av type BarnetilsynMedStonad har ugyldig verdi");
  }

  // BarnetilsynMedStonad - datoTil
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilsynMedStonad datoTil mangler")
  void skalKasteUgyldigInputExceptionNaarBarnetilsynMedStonadDatoTilMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilsynMedStonadDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyNettoBarnetilsynResultatCore(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoTil i objekt av type BarnetilsynMedStonad mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilsynMedStonad datoTil er null")
  void skalKasteUgyldigInputExceptionNaarBarnetilsynMedStonadDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBarnetilsynMedStonadDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyNettoBarnetilsynResultatCore(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoTil i objekt av type BarnetilsynMedStonad er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilsynMedStonad datoTil har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBarnetilsynMedStonadDatoTilHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiBarnetilsynMedStonadDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyNettoBarnetilsynResultatCore(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoTil i objekt av type BarnetilsynMedStonad har ugyldig verdi");
  }

  // BarnetilsynMedStonad - stonadType
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilsynMedStonad stonadType mangler")
  void skalKasteUgyldigInputExceptionNaarBarnetilsynMedStonadStonadTypeMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilsynMedStonadStonadType();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyNettoBarnetilsynResultatCore(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("stonadType i objekt av type BarnetilsynMedStonad mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilsynMedStonad stonadType er null")
  void skalKasteUgyldigInputExceptionNaarBarnetilsynMedStonadStonadTypeErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBarnetilsynMedStonadStonadType();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyNettoBarnetilsynResultatCore(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("stonadType i objekt av type BarnetilsynMedStonad er null");
  }

  // BarnetilsynMedStonad - tilsynType
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilsynMedStonad tilsynType mangler")
  void skalKasteUgyldigInputExceptionNaarBarnetilsynMedStonadTilsynTypeMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilsynMedStonadTilsynType();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyNettoBarnetilsynResultatCore(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("tilsynType i objekt av type BarnetilsynMedStonad mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilsynMedStonad tilsynType er null")
  void skalKasteUgyldigInputExceptionNaarBarnetilsynMedStonadTilsynTypeErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBarnetilsynMedStonadTilsynType();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyNettoBarnetilsynResultatCore(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("tilsynType i objekt av type BarnetilsynMedStonad er null");
  }

  // ForpleiningUtgift - soknadsbarnId
  @Test
  @DisplayName("Skal kaste UgyldigInputException når ForpleiningUtgift soknadsbarnId mangler")
  void skalKasteUgyldigInputExceptionNaarForpleiningUtgiftSoknadsbarnIdMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenForpleiningUtgiftSoknadsbarnId();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyNettoBarnetilsynResultatCore(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("soknadsbarnId i objekt av type ForpleiningUtgift mangler");
  }

  // ForpleiningUtgift - datoFom
  @Test
  @DisplayName("Skal kaste UgyldigInputException når ForpleiningUtgift datoFom mangler")
  void skalKasteUgyldigInputExceptionNaarForpleiningUtgiftDatoFomMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenForpleiningUtgiftDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyNettoBarnetilsynResultatCore(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoFom i objekt av type ForpleiningUtgift mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når ForpleiningUtgift datoFom er null")
  void skalKasteUgyldigInputExceptionNaarForpleiningUtgiftDatoFomErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullForpleiningUtgiftDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyNettoBarnetilsynResultatCore(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoFom i objekt av type ForpleiningUtgift er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når ForpleiningUtgift datoFom har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarForpleiningUtgiftDatoFomHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiForpleiningUtgiftDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyNettoBarnetilsynResultatCore(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoFom i objekt av type ForpleiningUtgift har ugyldig verdi");
  }

  // ForpleiningUtgift - datoTil
  @Test
  @DisplayName("Skal kaste UgyldigInputException når ForpleiningUtgift datoTil mangler")
  void skalKasteUgyldigInputExceptionNaarForpleiningUtgiftDatoTilMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenForpleiningUtgiftDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyNettoBarnetilsynResultatCore(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoTil i objekt av type ForpleiningUtgift mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når ForpleiningUtgift datoTil er null")
  void skalKasteUgyldigInputExceptionNaarForpleiningUtgiftDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullForpleiningUtgiftDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyNettoBarnetilsynResultatCore(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoTil i objekt av type ForpleiningUtgift er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når ForpleiningUtgift datoTil har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarForpleiningUtgiftDatoTilHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiForpleiningUtgiftDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyNettoBarnetilsynResultatCore(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoTil i objekt av type ForpleiningUtgift har ugyldig verdi");
  }

  // ForpleiningUtgift - belop
  @Test
  @DisplayName("Skal kaste UgyldigInputException når ForpleiningUtgift belop mangler")
  void skalKasteUgyldigInputExceptionNaarForpleiningUtgiftBelopMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenForpleiningUtgiftBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyNettoBarnetilsynResultatCore(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("belop i objekt av type ForpleiningUtgift mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når ForpleiningUtgift belop er null")
  void skalKasteUgyldigInputExceptionNaarForpleiningUtgiftBelopErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullForpleiningUtgiftBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyNettoBarnetilsynResultatCore(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("belop i objekt av type ForpleiningUtgift mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når ForpleiningUtgift belop har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarForpleiningUtgiftBelopHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiForpleiningUtgiftBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> underholdskostnadCoreMapper.mapUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyNettoBarnetilsynResultatCore(), new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("belop i objekt av type ForpleiningUtgift mangler, er null eller har ugyldig verdi");
  }

  //==================================================================================================================================================

  // Test av validering i BPAndelUnderholdskostnadCoreMapper

  // InntektSB - rolle
  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektSB rolle mangler")
  void skalKasteUgyldigInputExceptionNaarInntektSBRolleMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektSBRolle();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("rolle i objekt av type Inntekt mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektSB rolle er null")
  void skalKasteUgyldigInputExceptionNaarInntektSBRolleErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullInntektSBRolle();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("rolle i objekt av type Inntekt er null");
  }

  // InntektSB - datoFom
  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektSB datoFom mangler")
  void skalKasteUgyldigInputExceptionNaarInntektSBDatoFomMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektSBDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("datoFom i objekt av type Inntekt mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektSB datoFom er null")
  void skalKasteUgyldigInputExceptionNaarInntektSBDatoFomErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullInntektSBDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("datoFom i objekt av type Inntekt er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektSB datoFom har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarInntektSBDatoFomHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiInntektSBDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("datoFom i objekt av type Inntekt har ugyldig verdi");
  }

  // InntektSB - datoTil
  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektSB datoTil mangler")
  void skalKasteUgyldigInputExceptionNaarInntektSBDatoTilMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektSBDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("datoTil i objekt av type Inntekt mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektSB datoTil er null")
  void skalKasteUgyldigInputExceptionNaarInntektSBDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullInntektSBDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("datoTil i objekt av type Inntekt er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektSB datoTil har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarInntektSBDatoTilHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiInntektSBDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("datoTil i objekt av type Inntekt har ugyldig verdi");
  }

  // InntektSB - soknadsbarnId
  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektSB soknadsbarnId mangler")
  void skalKasteUgyldigInputExceptionNaarInntektSBSoknadsbarnIdMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektSBSoknadsbarnId();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("soknadsbarnId i objekt av type Inntekt mangler");
  }

  // InntektSB - inntektType
  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektSB inntektType mangler")
  void skalKasteUgyldigInputExceptionNaarInntektSBInntektTypeMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektSBInntektType();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("inntektType i objekt av type Inntekt mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektSB inntektType er null")
  void skalKasteUgyldigInputExceptionNaarInntektSBInntektTypeErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullInntektSBInntektType();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("inntektType i objekt av type Inntekt er null");
  }

  // InntektSB - belop
  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektSB belop mangler")
  void skalKasteUgyldigInputExceptionNaarInntektSBBelopMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektSBBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("belop i objekt av type Inntekt mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektSB belop er null")
  void skalKasteUgyldigInputExceptionNaarInntektSBBelopErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullInntektSBBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("belop i objekt av type Inntekt mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektSB belop har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarInntektSBBelopHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiInntektSBBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("belop i objekt av type Inntekt mangler, er null eller har ugyldig verdi");
  }

  // InntektBM - rolle
  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBM rolle mangler")
  void skalKasteUgyldigInputExceptionNaarInntektBMRolleMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektBMRolle();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("rolle i objekt av type Inntekt mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBM rolle er null")
  void skalKasteUgyldigInputExceptionNaarInntektBMRolleErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullInntektBMRolle();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("rolle i objekt av type Inntekt er null");
  }

  // InntektBM - datoFom
  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBM datoFom mangler")
  void skalKasteUgyldigInputExceptionNaarInntektBMDatoFomMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektBMDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("datoFom i objekt av type Inntekt mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBM datoFom er null")
  void skalKasteUgyldigInputExceptionNaarInntektBMDatoFomErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullInntektBMDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("datoFom i objekt av type Inntekt er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBM datoFom har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarInntektBMDatoFomHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiInntektBMDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("datoFom i objekt av type Inntekt har ugyldig verdi");
  }

  // InntektBM - datoTil
  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBM datoTil mangler")
  void skalKasteUgyldigInputExceptionNaarInntektBMDatoTilMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektBMDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("datoTil i objekt av type Inntekt mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBM datoTil er null")
  void skalKasteUgyldigInputExceptionNaarInntektBMDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullInntektBMDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("datoTil i objekt av type Inntekt er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBM datoTil har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarInntektBMDatoTilHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiInntektBMDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("datoTil i objekt av type Inntekt har ugyldig verdi");
  }

  // InntektBM - soknadsbarnId
  @Test
  @DisplayName("Skal ikke kaste exception når InntektBM soknadsbarnId mangler")
  void skalIkkeKasteExceptionNaarInntektBMSoknadsbarnIdMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektBMSoknadsbarnId();
    assertThatNoException().isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(
        grunnlag, new SjablonListe(), 1, TestUtil.dummyUnderholdskostnadResultatCore()));
  }

  // InntektBM - inntektType
  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBM inntektType mangler")
  void skalKasteUgyldigInputExceptionNaarInntektBMInntektTypeMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektBMInntektType();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("inntektType i objekt av type Inntekt mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBM inntektType er null")
  void skalKasteUgyldigInputExceptionNaarInntektBMInntektTypeErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullInntektBMInntektType();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("inntektType i objekt av type Inntekt er null");
  }

  // InntektBM - belop
  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBM belop mangler")
  void skalKasteUgyldigInputExceptionNaarInntektBMBelopMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektBMBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("belop i objekt av type Inntekt mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBM belop er null")
  void skalKasteUgyldigInputExceptionNaarInntektBMBelopErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullInntektBMBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("belop i objekt av type Inntekt mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBM belop har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarInntektBMBelopHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiInntektBMBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("belop i objekt av type Inntekt mangler, er null eller har ugyldig verdi");
  }

  // InntektBP - rolle
  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP rolle mangler")
  void skalKasteUgyldigInputExceptionNaarInntektBPRolleMangler_2() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektBPRolle();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("rolle i objekt av type Inntekt mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP rolle er null")
  void skalKasteUgyldigInputExceptionNaarInntektBPRolleErNull_2() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullInntektBPRolle();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("rolle i objekt av type Inntekt er null");
  }

  // InntektBP - datoFom
  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP datoFom mangler")
  void skalKasteUgyldigInputExceptionNaarInntektBPDatoFomMangler_2() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektBPDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("datoFom i objekt av type Inntekt mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP datoFom er null")
  void skalKasteUgyldigInputExceptionNaarInntektBPDatoFomErNull_2() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullInntektBPDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("datoFom i objekt av type Inntekt er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP datoFom har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarInntektBPDatoFomHarUgyldigVerdi_2() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiInntektBPDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("datoFom i objekt av type Inntekt har ugyldig verdi");
  }

  // InntektBP - datoTil
  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP datoTil mangler")
  void skalKasteUgyldigInputExceptionNaarInntektBPDatoTilMangler_2() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektBPDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("datoTil i objekt av type Inntekt mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP datoTil er null")
  void skalKasteUgyldigInputExceptionNaarInntektBPDatoTilErNull_2() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullInntektBPDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("datoTil i objekt av type Inntekt er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP datoTil har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarInntektBPDatoTilHarUgyldigVerdi_2() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiInntektBPDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("datoTil i objekt av type Inntekt har ugyldig verdi");
  }

  // InntektBP - inntektType
  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP inntektType mangler")
  void skalKasteUgyldigInputExceptionNaarInntektBPInntektTypeMangler_2() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektBPInntektType();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("inntektType i objekt av type Inntekt mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP inntektType er null")
  void skalKasteUgyldigInputExceptionNaarInntektBPInntektTypeErNull_2() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullInntektBPInntektType();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("inntektType i objekt av type Inntekt er null");
  }

  // InntektBP - belop
  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP belop mangler")
  void skalKasteUgyldigInputExceptionNaarInntektBPBelopMangler_2() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenInntektBPBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("belop i objekt av type Inntekt mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP belop er null")
  void skalKasteUgyldigInputExceptionNaarInntektBPBelopErNull_2() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullInntektBPBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("belop i objekt av type Inntekt mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når InntektBP belop har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarInntektBPBelopHarUgyldigVerdi_2() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiInntektBPBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            TestUtil.dummyUnderholdskostnadResultatCore()))
        .withMessage("belop i objekt av type Inntekt mangler, er null eller har ugyldig verdi");
  }

  //==================================================================================================================================================

  // Test av validering i SamvaersfradragCoreMapper

  // Samvaersklasse - datoFom
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Samvaersklasse datoFom mangler")
  void skalKasteUgyldigInputExceptionNaarSamvaersklasseDatoFomMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenSamvaersklasseDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> samvaersfradragCoreMapper.mapSamvaersfradragGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoFom i objekt av type Samvaersklasse mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Samvaersklasse datoFom er null")
  void skalKasteUgyldigInputExceptionNaarSamvaersklasseDatoFomErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullSamvaersklasseDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> samvaersfradragCoreMapper.mapSamvaersfradragGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoFom i objekt av type Samvaersklasse er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Samvaersklasse datoFom har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarSamvaersklasseDatoFomHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiSamvaersklasseDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> samvaersfradragCoreMapper.mapSamvaersfradragGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoFom i objekt av type Samvaersklasse har ugyldig verdi");
  }

  // Samvaersklasse - datoTil
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Samvaersklasse datoTil mangler")
  void skalKasteUgyldigInputExceptionNaarSamvaersklasseDatoTilMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenSamvaersklasseDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> samvaersfradragCoreMapper.mapSamvaersfradragGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoTil i objekt av type Samvaersklasse mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Samvaersklasse datoTil er null")
  void skalKasteUgyldigInputExceptionNaarSamvaersklasseDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullSamvaersklasseDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> samvaersfradragCoreMapper.mapSamvaersfradragGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoTil i objekt av type Samvaersklasse er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Samvaersklasse datoTil har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarSamvaersklasseDatoTilHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiSamvaersklasseDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> samvaersfradragCoreMapper.mapSamvaersfradragGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("datoTil i objekt av type Samvaersklasse har ugyldig verdi");
  }

  // Samvaersklasse - soknadsbarnId
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Samvaersklasse soknadsbarnId mangler")
  void skalKasteUgyldigInputExceptionNaarSamvaersklasseSoknadsbarnIdMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenSamvaersklasseSoknadsbarnId();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> samvaersfradragCoreMapper.mapSamvaersfradragGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("soknadsbarnId i objekt av type Samvaersklasse mangler");
  }

  // Samvaersklasse - samvaersklasseId
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Samvaersklasse samvaersklasseId mangler")
  void skalKasteUgyldigInputExceptionNaarSamvaersklasseSamvaersklasseIdMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenSamvaersklasseSamvaersklasseId();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> samvaersfradragCoreMapper.mapSamvaersfradragGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("samvaersklasseId i objekt av type Samvaersklasse mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Samvaersklasse samvaersklasseId er null")
  void skalKasteUgyldigInputExceptionNaarSamvaersklasseSamvaersklasseIdErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullSamvaersklasseSamvaersklasseId();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> samvaersfradragCoreMapper.mapSamvaersfradragGrunnlagTilCore(grunnlag, new SjablonListe(), 1,
            new HashMap(Map.of(1, "2010-01-01"))))
        .withMessage("samvaersklasseId i objekt av type Samvaersklasse er null");
  }

  //==================================================================================================================================================

  // Test av validering i BarnebidragCoreMapper

  // BarnetilleggBM - rolle
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBM rolle mangler")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBMRolleMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBMRolle();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("rolle i objekt av type Barnetillegg mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBM rolle er null")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBMRolleErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBarnetilleggBMRolle();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("rolle i objekt av type Barnetillegg er null");
  }

  // BarnetilleggBM - datoFom
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBM datoFom mangler")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBMDatoFomMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBMDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoFom i objekt av type Barnetillegg mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBM datoFom er null")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBMDatoFomErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBarnetilleggBMDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoFom i objekt av type Barnetillegg er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBM datoFom har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBMDatoFomHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggBMDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoFom i objekt av type Barnetillegg har ugyldig verdi");
  }

  // BarnetilleggBM - datoTil
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBM datoTil mangler")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBMDatoTilMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBMDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoTil i objekt av type Barnetillegg mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBM datoTil er null")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBMDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBarnetilleggBMDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoTil i objekt av type Barnetillegg er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBM datoTil har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBMDatoTilHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggBMDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoTil i objekt av type Barnetillegg har ugyldig verdi");
  }

  // BarnetilleggBM - soknadsbarnId
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBM soknadsbarnId mangler")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBMSoknadsbarnIdMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBMSoknadsbarnId();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("soknadsbarnId i objekt av type Barnetillegg mangler");
  }

  // BarnetilleggBM - bruttoBelop
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBM bruttoBelop mangler")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBMBruttoBelopMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBMBruttoBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("bruttoBelop i objekt av type Barnetillegg mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBM bruttoBelop er null")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBMBruttoBelopErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBarnetilleggBMBruttoBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("bruttoBelop i objekt av type Barnetillegg mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBM bruttoBelop har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBMBruttoBelopHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggBMBruttoBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("bruttoBelop i objekt av type Barnetillegg mangler, er null eller har ugyldig verdi");
  }

  // BarnetilleggBM - skattProsent
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBM skattProsent mangler")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBMSkattProsentMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBMSkattProsent();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("skattProsent i objekt av type Barnetillegg mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBM skattProsent er null")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBMSkattProsentErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBarnetilleggBMSkattProsent();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("skattProsent i objekt av type Barnetillegg mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBM skattProsent har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBMSkattProsentHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggBMSkattProsent();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("skattProsent i objekt av type Barnetillegg mangler, er null eller har ugyldig verdi");
  }

  // BarnetilleggBP - rolle
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBP rolle mangler")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBPRolleMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBPRolle();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("rolle i objekt av type Barnetillegg mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBP rolle er null")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBPRolleErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBarnetilleggBPRolle();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("rolle i objekt av type Barnetillegg er null");
  }

  // BarnetilleggBP - datoFom
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBP datoFom mangler")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBPDatoFomMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBPDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoFom i objekt av type Barnetillegg mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBP datoFom er null")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBPDatoFomErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBarnetilleggBPDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoFom i objekt av type Barnetillegg er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBP datoFom har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBPDatoFomHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggBPDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoFom i objekt av type Barnetillegg har ugyldig verdi");
  }

  // BarnetilleggBP - datoTil
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBP datoTil mangler")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBPDatoTilMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBPDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoTil i objekt av type Barnetillegg mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBP datoTil er null")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBPDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBarnetilleggBPDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoTil i objekt av type Barnetillegg er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBP datoTil har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBPDatoTilHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggBPDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoTil i objekt av type Barnetillegg har ugyldig verdi");
  }

  // BarnetilleggBP - soknadsbarnId
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBP soknadsbarnId mangler")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBPSoknadsbarnIdMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBPSoknadsbarnId();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("soknadsbarnId i objekt av type Barnetillegg mangler");
  }

  // BarnetilleggBP - bruttoBelop
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBP bruttoBelop mangler")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBPBruttoBelopMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBPBruttoBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("bruttoBelop i objekt av type Barnetillegg mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBP bruttoBelop er null")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBPBruttoBelopErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBarnetilleggBPBruttoBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("bruttoBelop i objekt av type Barnetillegg mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBP bruttoBelop har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBPBruttoBelopHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggBPBruttoBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("bruttoBelop i objekt av type Barnetillegg mangler, er null eller har ugyldig verdi");
  }

  // BarnetilleggBP - skattProsent
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBP skattProsent mangler")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBPSkattProsentMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBPSkattProsent();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("skattProsent i objekt av type Barnetillegg mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBP skattProsent er null")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBPSkattProsentErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBarnetilleggBPSkattProsent();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("skattProsent i objekt av type Barnetillegg mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggBP skattProsent har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggBPSkattProsentHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggBPSkattProsent();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("skattProsent i objekt av type Barnetillegg mangler, er null eller har ugyldig verdi");
  }

  // BarnetilleggForsvaret - datoFom
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggForsvaret datoFom mangler")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggForsvaretDatoFomMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggForsvaretDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoFom i objekt av type BarnetilleggForsvaret mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggForsvaret datoFom er null")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggForsvaretDatoFomErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBarnetilleggForsvaretDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoFom i objekt av type BarnetilleggForsvaret er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggForsvaret datoFom har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggForsvaretDatoFomHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggForsvaretDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoFom i objekt av type BarnetilleggForsvaret har ugyldig verdi");
  }

  // BarnetilleggForsvaret - datoTil
  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggForsvaret datoTil mangler")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggForsvaretDatoTilMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggForsvaretDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoTil i objekt av type BarnetilleggForsvaret mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggForsvaret datoTil er null")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggForsvaretDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBarnetilleggForsvaretDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoTil i objekt av type BarnetilleggForsvaret er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggForsvaret datoTil har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggForsvaretDatoTilHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggForsvaretDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoTil i objekt av type BarnetilleggForsvaret har ugyldig verdi");
  }

  // BarnetilleggForsvaret - barnetilleggForsvaret
  @Test
  @DisplayName("Skal ikke kaste UgyldigInputException når BarnetilleggForsvaret barnetilleggForsvaret mangler")
  void skalIkkeKasteUgyldigInputExceptionNaarBarnetilleggForsvaretBarnetilleggForsvaretMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggForsvaretBarnetilleggForsvaret();
    assertThatNoException()
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()));
    var grunnlagTilCore = barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
        TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore());
    assertThat(grunnlagTilCore.getBarnetilleggForsvaretPeriodeListe().get(0).getBarnetilleggForsvaretIPeriode()).isFalse();
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggForsvaret barnetilleggForsvaret er null")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggForsvaretBarnetilleggForsvaretErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullBarnetilleggForsvaretBarnetilleggForsvaret();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("barnetilleggForsvaret i objekt av type BarnetilleggForsvaret er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når BarnetilleggForsvaret barnetilleggForsvaret har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBarnetilleggForsvaretBarnetilleggForsvaretHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiBarnetilleggForsvaretBarnetilleggForsvaret();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("barnetilleggForsvaret i objekt av type BarnetilleggForsvaret er null eller har ugyldig verdi");
  }

  // DeltBosted - datoFom
  @Test
  @DisplayName("Skal kaste UgyldigInputException når DeltBosted datoFom mangler")
  void skalKasteUgyldigInputExceptionNaarDeltBostedDatoFomMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenDeltBostedDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoFom i objekt av type DeltBosted mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når DeltBosted datoFom er null")
  void skalKasteUgyldigInputExceptionNaarDeltBostedDatoFomErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullDeltBostedDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoFom i objekt av type DeltBosted er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når DeltBosted datoFom har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarDeltBostedDatoFomHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiDeltBostedDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoFom i objekt av type DeltBosted har ugyldig verdi");
  }

  // DeltBosted - datoTil
  @Test
  @DisplayName("Skal kaste UgyldigInputException når DeltBosted datoTil mangler")
  void skalKasteUgyldigInputExceptionNaarDeltBostedDatoTilMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenDeltBostedDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoTil i objekt av type DeltBosted mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når DeltBosted datoTil er null")
  void skalKasteUgyldigInputExceptionNaarDeltBostedDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullDeltBostedDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoTil i objekt av type DeltBosted er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når DeltBosted datoTil har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarDeltBostedDatoTilHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiDeltBostedDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoTil i objekt av type DeltBosted har ugyldig verdi");
  }

  // DeltBosted - soknadsbarnId
  @Test
  @DisplayName("Skal kaste UgyldigInputException når DeltBosted soknadsbarnId mangler")
  void skalKasteUgyldigInputExceptionNaarDeltBostedSoknadsbarnIdMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenDeltBostedSoknadsbarnId();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("soknadsbarnId i objekt av type DeltBosted mangler");
  }

  // DeltBosted - deltBosted
  @Test
  @DisplayName("Skal ikke kaste UgyldigInputException når DeltBosted deltBosted mangler")
  void skalIkkeKasteUgyldigInputExceptionNaarDeltBostedDeltBostedMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenDeltBostedDeltBosted();
    assertThatNoException()
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()));
    var grunnlagTilCore = barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
        TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore());
    assertThat(grunnlagTilCore.getDeltBostedPeriodeListe().get(0).getDeltBostedIPeriode()).isFalse();
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når DeltBosted deltBosted er null")
  void skalKasteUgyldigInputExceptionNaarDeltBostedDeltBostedErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullDeltBostedDeltBosted();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("deltBosted i objekt av type DeltBosted er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når DeltBosted deltBosted har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarDeltBostedDeltBostedHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiDeltBostedDeltBosted();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("deltBosted i objekt av type DeltBosted er null eller har ugyldig verdi");
  }

  // AndreLopendeBidrag - datoFom
  @Test
  @DisplayName("Skal kaste UgyldigInputException når AndreLopendeBidrag datoFom mangler")
  void skalKasteUgyldigInputExceptionNaarAndreLopendeBidragDatoFomMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenAndreLopendeBidragDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoFom i objekt av type AndreLopendeBidrag mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når AndreLopendeBidrag datoFom er null")
  void skalKasteUgyldigInputExceptionNaarAndreLopendeBidragDatoFomErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullAndreLopendeBidragDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoFom i objekt av type AndreLopendeBidrag er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når AndreLopendeBidrag datoFom har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarAndreLopendeBidragDatoFomHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiAndreLopendeBidragDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoFom i objekt av type AndreLopendeBidrag har ugyldig verdi");
  }

  // AndreLopendeBidrag - datoTil
  @Test
  @DisplayName("Skal kaste UgyldigInputException når AndreLopendeBidrag datoTil mangler")
  void skalKasteUgyldigInputExceptionNaarAndreLopendeBidragDatoTilMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenAndreLopendeBidragDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoTil i objekt av type AndreLopendeBidrag mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når AndreLopendeBidrag datoTil er null")
  void skalKasteUgyldigInputExceptionNaarAndreLopendeBidragDatoTilErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullAndreLopendeBidragDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoTil i objekt av type AndreLopendeBidrag er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når AndreLopendeBidrag datoTil har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarAndreLopendeBidragDatoTilHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiAndreLopendeBidragDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("datoTil i objekt av type AndreLopendeBidrag har ugyldig verdi");
  }

  // AndreLopendeBidrag - soknadsbarnId
  @Test
  @DisplayName("Skal kaste UgyldigInputException når AndreLopendeBidrag soknadsbarnId mangler")
  void skalKasteUgyldigInputExceptionNaarAndreLopendeBidragSoknadsbarnIdMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenAndreLopendeBidragSoknadsbarnId();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("soknadsbarnId i objekt av type AndreLopendeBidrag mangler");
  }

  // AndreLopendeBidrag - bidragBelop
  @Test
  @DisplayName("Skal kaste UgyldigInputException når AndreLopendeBidrag bidragBelop mangler")
  void skalKasteUgyldigInputExceptionNaarAndreLopendeBidragBidragBelopMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenAndreLopendeBidragBidragBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("bidragBelop i objekt av type AndreLopendeBidrag mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når AndreLopendeBidrag bidragBelop er null")
  void skalKasteUgyldigInputExceptionNaarAndreLopendeBidragBidragBelopErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullAndreLopendeBidragBidragBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("bidragBelop i objekt av type AndreLopendeBidrag mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når AndreLopendeBidrag bidragBelop har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarAndreLopendeBidragBidragBelopHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiAndreLopendeBidragBidragBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("bidragBelop i objekt av type AndreLopendeBidrag mangler, er null eller har ugyldig verdi");
  }

  // AndreLopendeBidrag - samvaersfradragBelop
  @Test
  @DisplayName("Skal kaste UgyldigInputException når AndreLopendeBidrag samvaersfradragBelop mangler")
  void skalKasteUgyldigInputExceptionNaarAndreLopendeBidragSamvaersfradragBelopMangler() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUtenAndreLopendeBidragSamvaersfradragBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("samvaersfradragBelop i objekt av type AndreLopendeBidrag mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når AndreLopendeBidrag samvaersfradragBelop er null")
  void skalKasteUgyldigInputExceptionNaarAndreLopendeBidragSamvaersfradragBelopErNull() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagNullAndreLopendeBidragSamvaersfradragBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("samvaersfradragBelop i objekt av type AndreLopendeBidrag mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når AndreLopendeBidrag samvaersfradragBelop har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarAndreLopendeBidragSamvaersfradragBelopHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggBarnebidragGrunnlagUgyldigVerdiAndreLopendeBidragSamvaersfradragBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> barnebidragCoreMapper.mapBarnebidragGrunnlagTilCore(grunnlag, new SjablonListe(), TestUtil.dummyBidragsevneResultatCore(),
            TestUtil.dummyBPsAndelUnderholdskostnadResultatCore(), TestUtil.dummySamvaersfradragResultatCore()))
        .withMessage("samvaersfradragBelop i objekt av type AndreLopendeBidrag mangler, er null eller har ugyldig verdi");
  }

  //==================================================================================================================================================

  // Test av validering i ForholdsmessigFordelingCoreMapper

  // Bidragsevne - datoFom
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Bidragsevne datoFom mangler")
  void skalKasteUgyldigInputExceptionNaarBidragsevneDatoFomMangler() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBidragsevneDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("datoFom i objekt av type Bidragsevne mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Bidragsevne datoFom er null")
  void skalKasteUgyldigInputExceptionNaarBidragsevneDatoFomErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagNullBidragsevneDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("datoFom i objekt av type Bidragsevne er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Bidragsevne datoFom har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBidragsevneDatoFomHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUgyldigVerdiBidragsevneDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("datoFom i objekt av type Bidragsevne har ugyldig verdi");
  }

  // Bidragsevne - datoTil
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Bidragsevne datoTil mangler")
  void skalKasteUgyldigInputExceptionNaarBidragsevneDatoTilMangler() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBidragsevneDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("datoTil i objekt av type Bidragsevne mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Bidragsevne datoTil er null")
  void skalKasteUgyldigInputExceptionNaarBidragsevneDatoTilErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagNullBidragsevneDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("datoTil i objekt av type Bidragsevne er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Bidragsevne datoTil har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBidragsevneDatoTilHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUgyldigVerdiBidragsevneDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("datoTil i objekt av type Bidragsevne har ugyldig verdi");
  }

  // Bidragsevne - belop
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Bidragsevne belop mangler")
  void skalKasteUgyldigInputExceptionNaarBidragsevneBelopMangler() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBidragsevneBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("belop i objekt av type Bidragsevne mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Bidragsevne belop er null")
  void skalKasteUgyldigInputExceptionNaarBidragsevneBelopErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagNullBidragsevneBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("belop i objekt av type Bidragsevne mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Bidragsevne belop har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBidragsevneBelopHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUgyldigVerdiBidragsevneBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("belop i objekt av type Bidragsevne mangler, er null eller har ugyldig verdi");
  }

  // Bidragsevne - 25ProsentInntekt
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Bidragsevne 25ProsentInntekt mangler")
  void skalKasteUgyldigInputExceptionNaarBidragsevne25ProsentInntektMangler() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBidragsevne25ProsentInntekt();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("25ProsentInntekt i objekt av type Bidragsevne mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Bidragsevne 25ProsentInntekt er null")
  void skalKasteUgyldigInputExceptionNaarBidragsevne25ProsentInntektErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagNullBidragsevne25ProsentInntekt();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("25ProsentInntekt i objekt av type Bidragsevne mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Bidragsevne 25ProsentInntekt har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBidragsevne25ProsentInntektHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUgyldigVerdiBidragsevne25ProsentInntekt();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("25ProsentInntekt i objekt av type Bidragsevne mangler, er null eller har ugyldig verdi");
  }

  // Barnebidrag - datoFom
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Barnebidrag datoFom mangler")
  void skalKasteUgyldigInputExceptionNaarBarnebidragDatoFomMangler() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBarnebidragDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("datoFom i objekt av type Barnebidrag mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Barnebidrag datoFom er null")
  void skalKasteUgyldigInputExceptionNaarBarnebidragDatoFomErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagNullBarnebidragDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("datoFom i objekt av type Barnebidrag er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Barnebidrag datoFom har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBarnebidragDatoFomHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUgyldigVerdiBarnebidragDatoFom();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("datoFom i objekt av type Barnebidrag har ugyldig verdi");
  }

  // Barnebidrag - datoTil
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Barnebidrag datoTil mangler")
  void skalKasteUgyldigInputExceptionNaarBarnebidragDatoTilMangler() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBarnebidragDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("datoTil i objekt av type Barnebidrag mangler");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Barnebidrag datoTil er null")
  void skalKasteUgyldigInputExceptionNaarBarnebidragDatoTilErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagNullBarnebidragDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("datoTil i objekt av type Barnebidrag er null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Barnebidrag datoTil har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBarnebidragDatoTilHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUgyldigVerdiBarnebidragDatoTil();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("datoTil i objekt av type Barnebidrag har ugyldig verdi");
  }

  // Barnebidrag - sakNr
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Barnebidrag sakNr mangler")
  void skalKasteUgyldigInputExceptionNaarBarnebidragSakNrMangler() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBarnebidragSakNr();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("sakNr i objekt av type Barnebidrag mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Barnebidrag sakNr er null")
  void skalKasteUgyldigInputExceptionNaarBarnebidragSakNrErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagNullBarnebidragSakNr();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("sakNr i objekt av type Barnebidrag mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Barnebidrag sakNr har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBarnebidragSakNrHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUgyldigVerdiBarnebidragSakNr();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("sakNr i objekt av type Barnebidrag mangler, er null eller har ugyldig verdi");
  }

  // Barnebidrag - soknadsbarnId
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Barnebidrag soknadsbarnId mangler")
  void skalKasteUgyldigInputExceptionNaarBarnebidragSoknadsbarnIdMangler() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBarnebidragSoknadsbarnId();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("soknadsbarnId i objekt av type Barnebidrag mangler, er null eller har ugyldig verdi");
  }

  // Barnebidrag - belop
  @Test
  @DisplayName("Skal kaste UgyldigInputException når Barnebidrag belop mangler")
  void skalKasteUgyldigInputExceptionNaarBarnebidragBelopMangler() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUtenBarnebidragBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("belop i objekt av type Barnebidrag mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Barnebidrag belop er null")
  void skalKasteUgyldigInputExceptionNaarBarnebidragBelopErNull() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagNullBarnebidragBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("belop i objekt av type Barnebidrag mangler, er null eller har ugyldig verdi");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException når Barnebidrag belop har ugyldig verdi")
  void skalKasteUgyldigInputExceptionNaarBarnebidragBelopHarUgyldigVerdi() {
    var grunnlag = TestUtil.byggForholdsmessigFordelingGrunnlagUgyldigVerdiBarnebidragBelop();
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(
            () -> forholdsmessigFordelingCoreMapper.mapForholdsmessigFordelingGrunnlagTilCore(grunnlag))
        .withMessage("belop i objekt av type Barnebidrag mangler, er null eller har ugyldig verdi");
  }
}
