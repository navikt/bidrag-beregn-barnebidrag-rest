package no.nav.bidrag.beregn.barnebidrag.rest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import no.nav.bidrag.beregn.barnebidrag.rest.TestUtil;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.BidragsevneConsumer;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonConsumer;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBidragsevneGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.BidragsevneConsumerException;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.SjablonConsumerException;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;
import no.nav.bidrag.beregn.felles.dto.SjablonInnholdCore;
import no.nav.bidrag.beregn.felles.dto.SjablonPeriodeCore;
import no.nav.bidrag.beregn.felles.enums.SjablonNavn;
import no.nav.bidrag.beregn.felles.enums.SjablonTallNavn;
import no.nav.bidrag.beregn.nettobarnetilsyn.NettoBarnetilsynCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnNettoBarnetilsynGrunnlagCore;
import no.nav.bidrag.beregn.underholdskostnad.UnderholdskostnadCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadGrunnlagCore;
import no.nav.bidrag.commons.web.HttpStatusResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

@DisplayName("BeregnBarnebidragServiceTest")
class BeregnBarnebidragServiceTest {

  @InjectMocks
  private BeregnBarnebidragService beregnBarnebidragService;

  @Mock
  private SjablonConsumer sjablonConsumerMock;
  @Mock
  private BidragsevneConsumer bidragsevneConsumerMock;
  @Mock
  private UnderholdskostnadCore underholdskostnadCoreMock;
  @Mock
  private NettoBarnetilsynCore nettoBarnetilsynCoreMock;

  @BeforeEach
  void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("Skal beregne bidrag når retur fra SjablonConsumer, BidragsevneConsumer, UnderholdskostnadCore og NettoBarnetilsynCore er OK")
  void skalBeregneBidragsevneNårReturFraSjablonConsumerOgBidragsevneConsumerOgUnderholdskostnadCoreErOk() {
    when(sjablonConsumerMock.hentSjablonSjablontall()).thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonSjablontallListe()));
    when(sjablonConsumerMock.hentSjablonForbruksutgifter())
        .thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonForbruksutgifterListe()));
    when(sjablonConsumerMock.hentSjablonMaksFradrag()).thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonMaksFradragListe()));
    when(sjablonConsumerMock.hentSjablonMaksTilsyn()).thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonMaksTilsynListe()));

    when(bidragsevneConsumerMock.hentBidragsevne(any(BeregnBidragsevneGrunnlag.class)))
        .thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummyBidragsevneResultat()));
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());

    var beregnBarnebidragResultat = beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlag());

    assertAll(
        () -> assertThat(beregnBarnebidragResultat.getHttpStatus()).isEqualTo(HttpStatus.OK),
        () -> assertThat(beregnBarnebidragResultat.getBody()).isNotNull(),
        () -> assertThat(beregnBarnebidragResultat.getBody().getBeregnBidragsevneResultat()).isNotNull(),
        () -> assertThat(beregnBarnebidragResultat.getBody().getBeregnUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(beregnBarnebidragResultat.getBody().getBeregnNettoBarnetilsynResultat()).isNotNull(),
        () -> assertThat(beregnBarnebidragResultat.getBody().getRestenAvResultatet()).isNotNull(),
        () -> assertThat(beregnBarnebidragResultat.getBody().getBeregnBidragsevneResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(beregnBarnebidragResultat.getBody().getBeregnUnderholdskostnadResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(beregnBarnebidragResultat.getBody().getBeregnNettoBarnetilsynResultat().getResultatPeriodeListe().size()).isEqualTo(1)
    );
  }

  @Test
  @DisplayName("Skal ha korrekt sjablon-grunnlag når beregningsmodulen kalles")
  void skalHaKorrektSjablonGrunnlagNårBeregningsmodulenKalles() {
    var underholdskostnadGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnUnderholdskostnadGrunnlagCore.class);
    var nettoBarnetilsynGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnNettoBarnetilsynGrunnlagCore.class);

    when(sjablonConsumerMock.hentSjablonSjablontall()).thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonSjablontallListe()));
    when(sjablonConsumerMock.hentSjablonForbruksutgifter())
        .thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonForbruksutgifterListe()));
    when(sjablonConsumerMock.hentSjablonMaksFradrag()).thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonMaksFradragListe()));
    when(sjablonConsumerMock.hentSjablonMaksTilsyn()).thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonMaksTilsynListe()));

    when(bidragsevneConsumerMock.hentBidragsevne(any(BeregnBidragsevneGrunnlag.class)))
        .thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummyBidragsevneResultat()));
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(underholdskostnadGrunnlagTilCoreCaptor.capture()))
        .thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(nettoBarnetilsynGrunnlagTilCoreCaptor.capture()))
        .thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());

    var beregnBarnebidragResultat = beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlag());
    var underholdskostnadGrunnlagTilCore = underholdskostnadGrunnlagTilCoreCaptor.getValue();
    var nettoBarnetilsynGrunnlagTilCore = nettoBarnetilsynGrunnlagTilCoreCaptor.getValue();

    var forventetAntallSjablonElementerUnderholdskostnad =
        TestUtil.dummySjablonSjablontallListe().size() + TestUtil.dummySjablonForbruksutgifterListe().size();
    var forventetAntallSjablonElementerNettoBarnetilsyn = TestUtil.dummySjablonSjablontallListe().size() +
        TestUtil.dummySjablonMaksFradragListe().size() + TestUtil.dummySjablonMaksTilsynListe().size();

    assertAll(
        () -> assertThat(beregnBarnebidragResultat.getHttpStatus()).isEqualTo(HttpStatus.OK),
        () -> assertThat(beregnBarnebidragResultat.getBody()).isNotNull(),

        () -> assertThat(beregnBarnebidragResultat.getBody().getBeregnUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(beregnBarnebidragResultat.getBody().getBeregnUnderholdskostnadResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(beregnBarnebidragResultat.getBody().getBeregnUnderholdskostnadResultat().getResultatPeriodeListe().size())
            .isEqualTo(1),
        () -> assertThat(underholdskostnadGrunnlagTilCore.getSjablonPeriodeListe().size())
            .isEqualTo(forventetAntallSjablonElementerUnderholdskostnad),

        () -> assertThat(beregnBarnebidragResultat.getBody().getBeregnNettoBarnetilsynResultat()).isNotNull(),
        () -> assertThat(beregnBarnebidragResultat.getBody().getBeregnNettoBarnetilsynResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(beregnBarnebidragResultat.getBody().getBeregnNettoBarnetilsynResultat().getResultatPeriodeListe().size())
            .isEqualTo(1),
        () -> assertThat(nettoBarnetilsynGrunnlagTilCore.getSjablonPeriodeListe().size())
            .isEqualTo(forventetAntallSjablonElementerNettoBarnetilsyn),

        // Sjekk at det mappes ut riktig antall for en gitt sjablon av type Sjablontall
        () -> assertThat(underholdskostnadGrunnlagTilCore.getSjablonPeriodeListe().stream()
            .filter(sjablonPeriodeCore -> sjablonPeriodeCore.getSjablonNavn().equals(SjablonTallNavn.ORDINAER_BARNETRYGD_BELOP.getNavn())).count())
            .isEqualTo(TestUtil.dummySjablonSjablontallListe().stream()
                .filter(sjablontall -> sjablontall.getTypeSjablon().equals("0001")).count()),

        // Sjekk at det mappes ut riktig antall sjabloner av type Forbruksutgifter
        () -> assertThat(underholdskostnadGrunnlagTilCore.getSjablonPeriodeListe().stream()
            .filter(sjablonPeriodeCore -> sjablonPeriodeCore.getSjablonNavn().equals(SjablonNavn.FORBRUKSUTGIFTER.getNavn())).count())
            .isEqualTo(TestUtil.dummySjablonForbruksutgifterListe().size()),

        // Sjekk at det mappes ut riktig verdi for en gitt sjablon av type Sjablontall
        () -> assertThat(underholdskostnadGrunnlagTilCore.getSjablonPeriodeListe().stream()
            .filter(sjablonPeriodeCore -> (sjablonPeriodeCore.getSjablonNavn().equals(SjablonTallNavn.ORDINAER_BARNETRYGD_BELOP.getNavn())) &&
                (sjablonPeriodeCore.getSjablonPeriodeDatoFraTil().getPeriodeDatoFra().equals(LocalDate.parse("2017-07-01"))))
            .map(SjablonPeriodeCore::getSjablonInnholdListe)
            .flatMap(Collection::stream)
            .findFirst()
            .map(SjablonInnholdCore::getSjablonInnholdVerdi)
            .orElse(0d))
            .isEqualTo(1054d),

        // Sjekk at det mappes ut riktig verdi for en gitt sjablon av type Forbruksutgifter
        () -> assertThat(underholdskostnadGrunnlagTilCore.getSjablonPeriodeListe().stream()
            .filter(sjablonPeriodeCore -> (sjablonPeriodeCore.getSjablonNavn().equals(SjablonNavn.FORBRUKSUTGIFTER.getNavn())) &&
                (sjablonPeriodeCore.getSjablonPeriodeDatoFraTil().getPeriodeDatoFra().equals(LocalDate.parse("2017-07-01"))))
            .map(SjablonPeriodeCore::getSjablonInnholdListe)
            .flatMap(Collection::stream)
            .findFirst()
            .map(SjablonInnholdCore::getSjablonInnholdVerdi)
            .orElse(0d))
            .isEqualTo(3661d));
  }

  @Test
  @DisplayName("Skal kaste SjablonConsumerException ved feil retur fra SjablonConsumer")
  void skalKasteSjablonConsumerExceptionVedFeilReturFraSjablonConsumer() {
    Map<String, String> body = new HashMap<>();
    body.put("error code", "503");
    body.put("error msg", "SERVICE_UNAVAILABLE");
    body.put("error text", "Service utilgjengelig");
    when(sjablonConsumerMock.hentSjablonSjablontall()).thenReturn(new HttpStatusResponse(HttpStatus.SERVICE_UNAVAILABLE, body.toString()));

    assertThatExceptionOfType(SjablonConsumerException.class)
        .isThrownBy(() -> beregnBarnebidragService
            .beregn(new BeregnBarnebidragGrunnlag(TestUtil.byggBidragsevneGrunnlag(), TestUtil.byggUnderholdskostnadGrunnlag(),
                TestUtil.byggNettoBarnetilsynGrunnlag(), "")))
        .withMessageContaining("Feil ved kall av bidrag-sjablon (sjablontall). Status: " + HttpStatus.SERVICE_UNAVAILABLE + " Melding: ");
  }

  @Test
  @DisplayName("Skal kaste BidragsevneConsumerException ved feil retur fra BidragsevneConsumer")
  void skalKasteBidragsevneConsumerExceptionVedFeilReturFraBidragsevneConsumer() {
    Map<String, String> body = new HashMap<>();
    body.put("error code", "503");
    body.put("error msg", "SERVICE_UNAVAILABLE");
    body.put("error text", "Service utilgjengelig");
    when(sjablonConsumerMock.hentSjablonSjablontall()).thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonSjablontallListe()));
    when(sjablonConsumerMock.hentSjablonForbruksutgifter())
        .thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonForbruksutgifterListe()));
    when(sjablonConsumerMock.hentSjablonMaksFradrag()).thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonMaksFradragListe()));
    when(sjablonConsumerMock.hentSjablonMaksTilsyn()).thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonMaksTilsynListe()));
    when(bidragsevneConsumerMock.hentBidragsevne(any(BeregnBidragsevneGrunnlag.class)))
        .thenReturn(new HttpStatusResponse(HttpStatus.SERVICE_UNAVAILABLE, body.toString()));

    assertThatExceptionOfType(BidragsevneConsumerException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlag()))
        .withMessageContaining("Feil ved kall av bidrag-beregn-bidragsevne-rest. Status: " + HttpStatus.SERVICE_UNAVAILABLE + " Melding: ");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved feil retur fra UnderholdskostnadCore")
  void skalKasteUgyldigInputExceptionVedFeilReturFraUnderholdskostnadCore() {
    when(sjablonConsumerMock.hentSjablonSjablontall()).thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonSjablontallListe()));
    when(sjablonConsumerMock.hentSjablonForbruksutgifter())
        .thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonForbruksutgifterListe()));
    when(sjablonConsumerMock.hentSjablonMaksFradrag()).thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonMaksFradragListe()));
    when(sjablonConsumerMock.hentSjablonMaksTilsyn()).thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonMaksTilsynListe()));
    when(bidragsevneConsumerMock.hentBidragsevne(any(BeregnBidragsevneGrunnlag.class)))
        .thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummyBidragsevneResultat()));
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCoreMedAvvik());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlag()))
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved feil retur fra NettoBarnetilsynCore")
  void skalKasteUgyldigInputExceptionVedFeilReturFraNettoBarnetilsynCore() {
    when(sjablonConsumerMock.hentSjablonSjablontall()).thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonSjablontallListe()));
    when(sjablonConsumerMock.hentSjablonForbruksutgifter())
        .thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonForbruksutgifterListe()));
    when(sjablonConsumerMock.hentSjablonMaksFradrag()).thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonMaksFradragListe()));
    when(sjablonConsumerMock.hentSjablonMaksTilsyn()).thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonMaksTilsynListe()));
    when(bidragsevneConsumerMock.hentBidragsevne(any(BeregnBidragsevneGrunnlag.class)))
        .thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummyBidragsevneResultat()));
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCoreMedAvvik());

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlag()))
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved validering av inputdata - underholdskostnad")
  void skalKasteUgyldigInputExceptionVedValideringAvInputdataUnderholdskostnad() {
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(
            new BeregnBarnebidragGrunnlag(TestUtil.byggBidragsevneGrunnlag(), TestUtil.byggUnderholdskostnadGrunnlagUtenSoknadBarnFodselsdato(),
                TestUtil.byggNettoBarnetilsynGrunnlag(), "")))
        .withMessageContaining("soknadBarnFodselsdato kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved validering av inputdata - netto barnetilsyn")
  void skalKasteUgyldigInputExceptionVedValideringAvInputdataNettoBarnetilsyn() {
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(
            new BeregnBarnebidragGrunnlag(TestUtil.byggBidragsevneGrunnlag(), TestUtil.byggUnderholdskostnadGrunnlag(),
                TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftBelop(), "")))
        .withMessageContaining("faktiskUtgiftBelop kan ikke være null");
  }
}
