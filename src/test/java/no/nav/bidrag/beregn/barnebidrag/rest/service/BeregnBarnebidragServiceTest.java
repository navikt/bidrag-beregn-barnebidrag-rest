package no.nav.bidrag.beregn.barnebidrag.rest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collection;
import no.nav.bidrag.beregn.barnebidrag.rest.BarnebidragCore;
import no.nav.bidrag.beregn.barnebidrag.rest.TestUtil;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonConsumer;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBarnebidragGrunnlagCore;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;
import no.nav.bidrag.beregn.bidragsevne.BidragsevneCore;
import no.nav.bidrag.beregn.bidragsevne.dto.BeregnBidragsevneGrunnlagAltCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.BPsAndelUnderholdskostnadCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnBPsAndelUnderholdskostnadGrunnlagCore;
import no.nav.bidrag.beregn.felles.dto.SjablonInnholdCore;
import no.nav.bidrag.beregn.felles.dto.SjablonPeriodeCore;
import no.nav.bidrag.beregn.felles.enums.SjablonNavn;
import no.nav.bidrag.beregn.felles.enums.SjablonTallNavn;
import no.nav.bidrag.beregn.kostnadsberegnetbidrag.KostnadsberegnetBidragCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.NettoBarnetilsynCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnNettoBarnetilsynGrunnlagCore;
import no.nav.bidrag.beregn.samvaersfradrag.SamvaersfradragCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnSamvaersfradragGrunnlagCore;
import no.nav.bidrag.beregn.underholdskostnad.UnderholdskostnadCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadGrunnlagCore;
import no.nav.bidrag.commons.web.HttpResponse;
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
  private BidragsevneCore bidragsevneCoreMock;
  @Mock
  private NettoBarnetilsynCore nettoBarnetilsynCoreMock;
  @Mock
  private UnderholdskostnadCore underholdskostnadCoreMock;
  @Mock
  private BPsAndelUnderholdskostnadCore bpAndelUnderholdskostnadCoreMock;
  @Mock
  private SamvaersfradragCore samvaersfradragCoreMock;
  @Mock
  private KostnadsberegnetBidragCore kostnadsberegnetBidragCoreMock;
  @Mock
  private BarnebidragCore barnebidragCoreMock;

  @BeforeEach
  void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("Skal beregne bidrag når retur fra consumer-moduler og core-moduler er OK")
  void skalBeregneBidragsevneNaarReturFraConsumerOgCoreModulerErOk() {
    when(sjablonConsumerMock.hentSjablonSjablontall()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonSjablontallListe()));
    when(sjablonConsumerMock.hentSjablonForbruksutgifter())
        .thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonForbruksutgifterListe()));
    when(sjablonConsumerMock.hentSjablonMaksFradrag()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonMaksFradragListe()));
    when(sjablonConsumerMock.hentSjablonMaksTilsyn()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonMaksTilsynListe()));
    when(sjablonConsumerMock.hentSjablonSamvaersfradrag()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonSamvaersfradragListe()));
    when(sjablonConsumerMock.hentSjablonBidragsevne()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonBidragsevneListe()));
    when(sjablonConsumerMock.hentSjablonTrinnvisSkattesats())
        .thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonTrinnvisSkattesatsListe()));

    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
    when(bpAndelUnderholdskostnadCoreMock.beregnBPsAndelUnderholdskostnad(any())).thenReturn(TestUtil.dummyBPsAndelUnderholdskostnadResultatCore());
    when(samvaersfradragCoreMock.beregnSamvaersfradrag(any())).thenReturn(TestUtil.dummySamvaersfradragResultatCore());
    when(kostnadsberegnetBidragCoreMock.beregnKostnadsberegnetBidrag(any())).thenReturn(TestUtil.dummyKostnadsberegnetBidragResultatCore());
    when(barnebidragCoreMock.beregnBarnebidrag(any())).thenReturn(TestUtil.dummyBarnebidragResultatCore());

    var beregnTotalBarnebidragResultat = beregnBarnebidragService.beregn(TestUtil.byggTotalBarnebidragGrunnlag());

    assertAll(
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBidragsevneResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnNettoBarnetilsynResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPAndelUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnSamvaersfradragResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnKostnadsberegnetBidragResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnKostnadsberegnetBidragResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBarnebidragResultat().getResultatPeriodeListe().size())
            .isEqualTo(1),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnNettoBarnetilsynResultat().getResultatPeriodeListe().size())
            .isEqualTo(1),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnUnderholdskostnadResultat().getResultatPeriodeListe()
            .size()).isEqualTo(1),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe()
            .size()).isEqualTo(1),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnSamvaersfradragResultat().getResultatPeriodeListe().size())
            .isEqualTo(1),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnKostnadsberegnetBidragResultat().getResultatPeriodeListe()
            .size()).isEqualTo(1),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBarnebidragResultat().getResultatPeriodeListe()
            .size()).isEqualTo(1)
    );
  }

  @Test
  @DisplayName("Skal ha korrekt sjablon-grunnlag når beregningsmodulen kalles")
  void skalHaKorrektSjablonGrunnlagNaarBeregningsmodulenKalles() {
    var bidragsevneGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnBidragsevneGrunnlagAltCore.class);
    var nettoBarnetilsynGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnNettoBarnetilsynGrunnlagCore.class);
    var underholdskostnadGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnUnderholdskostnadGrunnlagCore.class);
    var bpAndelUnderholdskostnadGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnBPsAndelUnderholdskostnadGrunnlagCore.class);
    var samvaersfradragGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnSamvaersfradragGrunnlagCore.class);
    var barnebidragGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnBarnebidragGrunnlagCore.class);

    when(sjablonConsumerMock.hentSjablonSjablontall()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonSjablontallListe()));
    when(sjablonConsumerMock.hentSjablonForbruksutgifter())
        .thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonForbruksutgifterListe()));
    when(sjablonConsumerMock.hentSjablonMaksFradrag()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonMaksFradragListe()));
    when(sjablonConsumerMock.hentSjablonMaksTilsyn()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonMaksTilsynListe()));
    when(sjablonConsumerMock.hentSjablonSamvaersfradrag()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonSamvaersfradragListe()));
    when(sjablonConsumerMock.hentSjablonBidragsevne()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonBidragsevneListe()));
    when(sjablonConsumerMock.hentSjablonTrinnvisSkattesats())
        .thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonTrinnvisSkattesatsListe()));

    when(bidragsevneCoreMock.beregnBidragsevne(bidragsevneGrunnlagTilCoreCaptor.capture()))
        .thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(nettoBarnetilsynGrunnlagTilCoreCaptor.capture()))
        .thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(underholdskostnadGrunnlagTilCoreCaptor.capture()))
        .thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
    when(bpAndelUnderholdskostnadCoreMock.beregnBPsAndelUnderholdskostnad(bpAndelUnderholdskostnadGrunnlagTilCoreCaptor.capture()))
        .thenReturn(TestUtil.dummyBPsAndelUnderholdskostnadResultatCore());
    when(samvaersfradragCoreMock.beregnSamvaersfradrag(samvaersfradragGrunnlagTilCoreCaptor.capture()))
        .thenReturn(TestUtil.dummySamvaersfradragResultatCore());
    when(kostnadsberegnetBidragCoreMock.beregnKostnadsberegnetBidrag(any())).thenReturn(TestUtil.dummyKostnadsberegnetBidragResultatCore());
    when(barnebidragCoreMock.beregnBarnebidrag(barnebidragGrunnlagTilCoreCaptor.capture())).thenReturn(TestUtil.dummyBarnebidragResultatCore());

    var beregnTotalBarnebidragResultat = beregnBarnebidragService.beregn(TestUtil.byggTotalBarnebidragGrunnlag());

    var bidragsevneGrunnlagTilCore = bidragsevneGrunnlagTilCoreCaptor.getValue();
    var nettoBarnetilsynGrunnlagTilCore = nettoBarnetilsynGrunnlagTilCoreCaptor.getValue();
    var underholdskostnadGrunnlagTilCore = underholdskostnadGrunnlagTilCoreCaptor.getValue();
    var bpAndelUnderholdskostnadGrunnlagTilCore = bpAndelUnderholdskostnadGrunnlagTilCoreCaptor.getValue();
    var samvaersfradragGrunnlagTilCore = samvaersfradragGrunnlagTilCoreCaptor.getValue();
    var barnebidragGrunnlagTilCore = barnebidragGrunnlagTilCoreCaptor.getValue();

    var forventetAntallSjablonElementerBidragsevne = TestUtil.dummySjablonSjablontallListe().size() +
        TestUtil.dummySjablonBidragsevneListe().size() + TestUtil.dummySjablonTrinnvisSkattesatsListe().size();
    var forventetAntallSjablonElementerNettoBarnetilsyn = TestUtil.dummySjablonSjablontallListe().size() +
        TestUtil.dummySjablonMaksFradragListe().size() + TestUtil.dummySjablonMaksTilsynListe().size();
    var forventetAntallSjablonElementerUnderholdskostnad =
        TestUtil.dummySjablonSjablontallListe().size() + TestUtil.dummySjablonForbruksutgifterListe().size();
    var forventetAntallSjablonElementerBPsAndelUnderholdskostnad = TestUtil.dummySjablonSjablontallListe().size();
    var forventetAntallSjablonElementerSamvaersfradrag = TestUtil.dummySjablonSamvaersfradragListe().size();
    var forventetAntallSjablonElementerBarnebidrag = TestUtil.dummySjablonSjablontallListe().size();

    assertAll(
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody()).isNotNull(),

        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBidragsevneResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBidragsevneResultat().getResultatPeriodeListe())
            .isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBidragsevneResultat().getResultatPeriodeListe().size())
            .isEqualTo(1),
        () -> assertThat(bidragsevneGrunnlagTilCore.getSjablonPeriodeListe().size())
            .isEqualTo(forventetAntallSjablonElementerBidragsevne),

        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnNettoBarnetilsynResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnNettoBarnetilsynResultat().getResultatPeriodeListe())
            .isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnNettoBarnetilsynResultat().getResultatPeriodeListe().size())
            .isEqualTo(1),
        () -> assertThat(nettoBarnetilsynGrunnlagTilCore.getSjablonPeriodeListe().size())
            .isEqualTo(forventetAntallSjablonElementerNettoBarnetilsyn),

        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnUnderholdskostnadResultat().getResultatPeriodeListe())
            .isNotNull(),
        () -> assertThat(
            beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnUnderholdskostnadResultat().getResultatPeriodeListe().size())
            .isEqualTo(1),
        () -> assertThat(underholdskostnadGrunnlagTilCore.getSjablonPeriodeListe().size())
            .isEqualTo(forventetAntallSjablonElementerUnderholdskostnad),

        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPAndelUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPAndelUnderholdskostnadResultat()
            .getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPAndelUnderholdskostnadResultat()
            .getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore.getSjablonPeriodeListe().size())
            .isEqualTo(forventetAntallSjablonElementerBPsAndelUnderholdskostnad),

        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnSamvaersfradragResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnSamvaersfradragResultat().getResultatPeriodeListe())
            .isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnSamvaersfradragResultat().getResultatPeriodeListe().size())
            .isEqualTo(1),
        () -> assertThat(samvaersfradragGrunnlagTilCore.getSjablonPeriodeListe().size()).isEqualTo(forventetAntallSjablonElementerSamvaersfradrag),

        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnKostnadsberegnetBidragResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnKostnadsberegnetBidragResultat().getResultatPeriodeListe())
            .isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnKostnadsberegnetBidragResultat().getResultatPeriodeListe()
            .size()).isEqualTo(1),

        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBarnebidragResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBarnebidragResultat().getResultatPeriodeListe())
            .isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBarnebidragResultat().getResultatPeriodeListe()
            .size()).isEqualTo(1),
        () -> assertThat(barnebidragGrunnlagTilCore.getSjablonPeriodeListe().size()).isEqualTo(forventetAntallSjablonElementerBarnebidrag),

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
  @DisplayName("Skal kaste UgyldigInputException ved feil retur fra BidragsevneCore")
  void skalKasteUgyldigInputExceptionVedFeilReturFraBidragsevneCore() {
    when(sjablonConsumerMock.hentSjablonSjablontall()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonSjablontallListe()));
    when(sjablonConsumerMock.hentSjablonForbruksutgifter())
        .thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonForbruksutgifterListe()));
    when(sjablonConsumerMock.hentSjablonMaksFradrag()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonMaksFradragListe()));
    when(sjablonConsumerMock.hentSjablonMaksTilsyn()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonMaksTilsynListe()));
    when(sjablonConsumerMock.hentSjablonSamvaersfradrag()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonSamvaersfradragListe()));
    when(sjablonConsumerMock.hentSjablonBidragsevne()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonBidragsevneListe()));
    when(sjablonConsumerMock.hentSjablonTrinnvisSkattesats())
        .thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonTrinnvisSkattesatsListe()));
    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCoreMedAvvik());

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggTotalBarnebidragGrunnlag()))
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved feil retur fra NettoBarnetilsynCore")
  void skalKasteUgyldigInputExceptionVedFeilReturFraNettoBarnetilsynCore() {
    when(sjablonConsumerMock.hentSjablonSjablontall()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonSjablontallListe()));
    when(sjablonConsumerMock.hentSjablonForbruksutgifter())
        .thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonForbruksutgifterListe()));
    when(sjablonConsumerMock.hentSjablonMaksFradrag()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonMaksFradragListe()));
    when(sjablonConsumerMock.hentSjablonMaksTilsyn()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonMaksTilsynListe()));
    when(sjablonConsumerMock.hentSjablonSamvaersfradrag()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonSamvaersfradragListe()));
    when(sjablonConsumerMock.hentSjablonBidragsevne()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonBidragsevneListe()));
    when(sjablonConsumerMock.hentSjablonTrinnvisSkattesats())
        .thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonTrinnvisSkattesatsListe()));
    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCoreMedAvvik());

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggTotalBarnebidragGrunnlag()))
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved feil retur fra UnderholdskostnadCore")
  void skalKasteUgyldigInputExceptionVedFeilReturFraUnderholdskostnadCore() {
    when(sjablonConsumerMock.hentSjablonSjablontall()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonSjablontallListe()));
    when(sjablonConsumerMock.hentSjablonForbruksutgifter())
        .thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonForbruksutgifterListe()));
    when(sjablonConsumerMock.hentSjablonMaksFradrag()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonMaksFradragListe()));
    when(sjablonConsumerMock.hentSjablonMaksTilsyn()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonMaksTilsynListe()));
    when(sjablonConsumerMock.hentSjablonSamvaersfradrag()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonSamvaersfradragListe()));
    when(sjablonConsumerMock.hentSjablonBidragsevne()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonBidragsevneListe()));
    when(sjablonConsumerMock.hentSjablonTrinnvisSkattesats())
        .thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonTrinnvisSkattesatsListe()));
    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCoreMedAvvik());

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggTotalBarnebidragGrunnlag()))
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved feil retur fra BPsAndelUnderholdskostnadCore")
  void skalKasteUgyldigInputExceptionVedFeilReturFraBPsAndelUnderholdskostnadCore() {
    when(sjablonConsumerMock.hentSjablonSjablontall()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonSjablontallListe()));
    when(sjablonConsumerMock.hentSjablonForbruksutgifter())
        .thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonForbruksutgifterListe()));
    when(sjablonConsumerMock.hentSjablonMaksFradrag()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonMaksFradragListe()));
    when(sjablonConsumerMock.hentSjablonMaksTilsyn()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonMaksTilsynListe()));
    when(sjablonConsumerMock.hentSjablonSamvaersfradrag()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonSamvaersfradragListe()));
    when(sjablonConsumerMock.hentSjablonBidragsevne()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonBidragsevneListe()));
    when(sjablonConsumerMock.hentSjablonTrinnvisSkattesats())
        .thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonTrinnvisSkattesatsListe()));
    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
    when(bpAndelUnderholdskostnadCoreMock.beregnBPsAndelUnderholdskostnad(any()))
        .thenReturn(TestUtil.dummyBPsAndelUnderholdskostnadResultatCoreMedAvvik());

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggTotalBarnebidragGrunnlag()))
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved feil retur fra SamvaersfradragCore")
  void skalKasteUgyldigInputExceptionVedFeilReturFraSamvaersfradragCore() {
    when(sjablonConsumerMock.hentSjablonSjablontall()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonSjablontallListe()));
    when(sjablonConsumerMock.hentSjablonForbruksutgifter())
        .thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonForbruksutgifterListe()));
    when(sjablonConsumerMock.hentSjablonMaksFradrag()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonMaksFradragListe()));
    when(sjablonConsumerMock.hentSjablonMaksTilsyn()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonMaksTilsynListe()));
    when(sjablonConsumerMock.hentSjablonSamvaersfradrag()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonSamvaersfradragListe()));
    when(sjablonConsumerMock.hentSjablonBidragsevne()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonBidragsevneListe()));
    when(sjablonConsumerMock.hentSjablonTrinnvisSkattesats())
        .thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonTrinnvisSkattesatsListe()));
    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
    when(bpAndelUnderholdskostnadCoreMock.beregnBPsAndelUnderholdskostnad(any())).thenReturn(TestUtil.dummyBPsAndelUnderholdskostnadResultatCore());
    when(samvaersfradragCoreMock.beregnSamvaersfradrag(any())).thenReturn(TestUtil.dummySamvaersfradragResultatCoreMedAvvik());

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggTotalBarnebidragGrunnlag()))
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved feil retur fra KostnadsberegnetBidragCore")
  void skalKasteUgyldigInputExceptionVedFeilReturFraKostnadsberegnetBidragCore() {
    when(sjablonConsumerMock.hentSjablonSjablontall()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonSjablontallListe()));
    when(sjablonConsumerMock.hentSjablonForbruksutgifter())
        .thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonForbruksutgifterListe()));
    when(sjablonConsumerMock.hentSjablonMaksFradrag()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonMaksFradragListe()));
    when(sjablonConsumerMock.hentSjablonMaksTilsyn()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonMaksTilsynListe()));
    when(sjablonConsumerMock.hentSjablonSamvaersfradrag()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonSamvaersfradragListe()));
    when(sjablonConsumerMock.hentSjablonBidragsevne()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonBidragsevneListe()));
    when(sjablonConsumerMock.hentSjablonTrinnvisSkattesats())
        .thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonTrinnvisSkattesatsListe()));
    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
    when(bpAndelUnderholdskostnadCoreMock.beregnBPsAndelUnderholdskostnad(any())).thenReturn(TestUtil.dummyBPsAndelUnderholdskostnadResultatCore());
    when(samvaersfradragCoreMock.beregnSamvaersfradrag(any())).thenReturn(TestUtil.dummySamvaersfradragResultatCore());
    when(kostnadsberegnetBidragCoreMock.beregnKostnadsberegnetBidrag(any())).thenReturn(TestUtil.dummyKostnadsberegnetBidragResultatCoreMedAvvik());

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggTotalBarnebidragGrunnlag()))
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved feil retur fra BarnebidragCore")
  void skalKasteUgyldigInputExceptionVedFeilReturFraBarnebidragCore() {
    when(sjablonConsumerMock.hentSjablonSjablontall()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonSjablontallListe()));
    when(sjablonConsumerMock.hentSjablonForbruksutgifter())
        .thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonForbruksutgifterListe()));
    when(sjablonConsumerMock.hentSjablonMaksFradrag()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonMaksFradragListe()));
    when(sjablonConsumerMock.hentSjablonMaksTilsyn()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonMaksTilsynListe()));
    when(sjablonConsumerMock.hentSjablonSamvaersfradrag()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonSamvaersfradragListe()));
    when(sjablonConsumerMock.hentSjablonBidragsevne()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonBidragsevneListe()));
    when(sjablonConsumerMock.hentSjablonTrinnvisSkattesats())
        .thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonTrinnvisSkattesatsListe()));
    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
    when(bpAndelUnderholdskostnadCoreMock.beregnBPsAndelUnderholdskostnad(any())).thenReturn(TestUtil.dummyBPsAndelUnderholdskostnadResultatCore());
    when(samvaersfradragCoreMock.beregnSamvaersfradrag(any())).thenReturn(TestUtil.dummySamvaersfradragResultatCore());
    when(kostnadsberegnetBidragCoreMock.beregnKostnadsberegnetBidrag(any())).thenReturn(TestUtil.dummyKostnadsberegnetBidragResultatCore());
    when(barnebidragCoreMock.beregnBarnebidrag(any())).thenReturn(TestUtil.dummyBarnebidragResultatCoreMedAvvik());

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggTotalBarnebidragGrunnlag()))
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved validering av inputdata - bidragsevne")
  void skalKasteUgyldigInputExceptionVedValideringAvInputdataBidragsevne() {
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(
            new BeregnTotalBarnebidragGrunnlag(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), LocalDate.parse("2010-01-01"), 1,
                TestUtil.byggBidragsevneGrunnlagUtenBostatusKode(), TestUtil.byggNettoBarnetilsynGrunnlag(),
                TestUtil.byggUnderholdskostnadGrunnlag(), TestUtil.byggBPsAndelUnderholdskostnadGrunnlag(), TestUtil.byggSamvaersfradragGrunnlag(),
                TestUtil.byggBarnebidragGrunnlag())))
        .withMessageContaining("bostatusKode kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved validering av inputdata - netto barnetilsyn")
  void skalKasteUgyldigInputExceptionVedValideringAvInputdataNettoBarnetilsyn() {
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(
            new BeregnTotalBarnebidragGrunnlag(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), LocalDate.parse("2010-01-01"), 1,
                TestUtil.byggBidragsevneGrunnlag(), TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftBelop(),
                TestUtil.byggUnderholdskostnadGrunnlag(), TestUtil.byggBPsAndelUnderholdskostnadGrunnlag(), TestUtil.byggSamvaersfradragGrunnlag(),
                TestUtil.byggBarnebidragGrunnlag())))
        .withMessageContaining("faktiskUtgiftBelop kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved validering av inputdata - underholdskostnad")
  void skalKasteUgyldigInputExceptionVedValideringAvInputdataUnderholdskostnad() {
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(
            new BeregnTotalBarnebidragGrunnlag(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), LocalDate.parse("2010-01-01"), 1,
                TestUtil.byggBidragsevneGrunnlag(), TestUtil.byggNettoBarnetilsynGrunnlag(),
                TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftBelop(), TestUtil.byggBPsAndelUnderholdskostnadGrunnlag(),
                TestUtil.byggSamvaersfradragGrunnlag(), TestUtil.byggBarnebidragGrunnlag())))
        .withMessageContaining("forpleiningUtgiftBelop kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved validering av inputdata - BPs andel av underholdskostnad")
  void skalKasteUgyldigInputExceptionVedValideringAvInputdataBPsAndelUnderholdskostnad() {
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(
            new BeregnTotalBarnebidragGrunnlag(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), LocalDate.parse("2010-01-01"), 1,
                TestUtil.byggBidragsevneGrunnlag(), TestUtil.byggNettoBarnetilsynGrunnlag(),
                TestUtil.byggUnderholdskostnadGrunnlag(), TestUtil.byggBPsAndelUnderholdskostnadGrunnlagUtenInntektBP(),
                TestUtil.byggSamvaersfradragGrunnlag(), TestUtil.byggBarnebidragGrunnlag())))
        .withMessageContaining("inntektBP kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved validering av inputdata - samværsfradrag")
  void skalKasteUgyldigInputExceptionVedValideringAvInputdataSamvaersfradrag() {
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(
            new BeregnTotalBarnebidragGrunnlag(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), LocalDate.parse("2010-01-01"), 1,
                TestUtil.byggBidragsevneGrunnlag(), TestUtil.byggNettoBarnetilsynGrunnlag(),
                TestUtil.byggUnderholdskostnadGrunnlag(), TestUtil.byggBPsAndelUnderholdskostnadGrunnlag(),
                TestUtil.byggSamvaersfradragGrunnlagUtenSamvaersklasse(), TestUtil.byggBarnebidragGrunnlag())))
        .withMessageContaining("samvaersklasse kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved validering av inputdata - barnebidrag")
  void skalKasteUgyldigInputExceptionVedValideringAvInputdataBarnebidrag() {
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(
            new BeregnTotalBarnebidragGrunnlag(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), LocalDate.parse("2010-01-01"), 1,
                TestUtil.byggBidragsevneGrunnlag(), TestUtil.byggNettoBarnetilsynGrunnlag(),
                TestUtil.byggUnderholdskostnadGrunnlag(), TestUtil.byggBPsAndelUnderholdskostnadGrunnlag(),
                TestUtil.byggSamvaersfradragGrunnlag(), TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBMBelop())))
        .withMessageContaining("barnetilleggBMBelop kan ikke være null");
  }
}
