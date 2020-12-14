package no.nav.bidrag.beregn.barnebidrag.rest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import no.nav.bidrag.beregn.barnebidrag.BarnebidragCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BeregnBarnebidragGrunnlagCore;
import no.nav.bidrag.beregn.barnebidrag.rest.TestUtil;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonConsumer;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;
import no.nav.bidrag.beregn.bidragsevne.BidragsevneCore;
import no.nav.bidrag.beregn.bidragsevne.dto.BeregnBidragsevneGrunnlagCore;
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
  void initMocksOgSettOppSjablonMocks() {
    MockitoAnnotations.initMocks(this);
    when(sjablonConsumerMock.hentSjablonSjablontall()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonSjablontallListe()));
    when(sjablonConsumerMock.hentSjablonForbruksutgifter())
        .thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonForbruksutgifterListe()));
    when(sjablonConsumerMock.hentSjablonMaksFradrag()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonMaksFradragListe()));
    when(sjablonConsumerMock.hentSjablonMaksTilsyn()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonMaksTilsynListe()));
    when(sjablonConsumerMock.hentSjablonSamvaersfradrag()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonSamvaersfradragListe()));
    when(sjablonConsumerMock.hentSjablonBidragsevne()).thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonBidragsevneListe()));
    when(sjablonConsumerMock.hentSjablonTrinnvisSkattesats())
        .thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonTrinnvisSkattesatsListe()));
    when(sjablonConsumerMock.hentSjablonBarnetilsyn())
        .thenReturn(HttpResponse.from(HttpStatus.OK, TestUtil.dummySjablonBarnetilsynListe()));
  }

  @Test
  @DisplayName("Skal beregne bidrag når retur fra consumer-moduler og core-moduler er OK")
  void skalBeregneBidragNaarReturFraConsumerOgCoreModulerErOk() {
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
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPBidragsevneResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBMNettoBarnetilsynResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBMUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPAndelUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPSamvaersfradragResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPKostnadsberegnetBidragResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPKostnadsberegnetBidragResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBarnebidragResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPBidragsevneResultat().getResultatPeriodeListe()
            .size()).isEqualTo(1),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe()
            .size()).isEqualTo(1),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe()
            .size()).isEqualTo(1),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPAndelUnderholdskostnadResultat()
            .getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe()
            .size()).isEqualTo(1),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPKostnadsberegnetBidragResultat()
            .getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBarnebidragResultat().getResultatPeriodeListe()
            .size()).isEqualTo(1)
    );
  }

  @Test
  @DisplayName("Skal ha korrekt sjablon-grunnlag når beregningsmodulen kalles")
  void skalHaKorrektSjablonGrunnlagNaarBeregningsmodulenKalles() {
    var bidragsevneGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnBidragsevneGrunnlagCore.class);
    var nettoBarnetilsynGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnNettoBarnetilsynGrunnlagCore.class);
    var underholdskostnadGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnUnderholdskostnadGrunnlagCore.class);
    var bpAndelUnderholdskostnadGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnBPsAndelUnderholdskostnadGrunnlagCore.class);
    var samvaersfradragGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnSamvaersfradragGrunnlagCore.class);
    var barnebidragGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnBarnebidragGrunnlagCore.class);

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

    // For Sjablontall sjekkes at det er riktig type sjablontall. For alle sjabloner sjekkes det at datoen er innenfor beregn-fra-til-dato
    // For å finne riktig tall: Sjekk TestUtil.dummySjablonxxx; tell hvor mange sjabloner som er innenfor dato og (for Sjablontall) av riktig type

    // Bidragsevne: Sjablontall (0004, 0017, 0019, 0023, 0025, 0027, 0028, 0039, 0040) + Bidragsevne + TrinnvisSkattesats
    var forventetAntallSjablonElementerBidragsevne = 25 + 8 + 8;
    // NettoBarnetilsyn: Sjablontall (0015) + MaksFradrag + MaksTilsyn
    var forventetAntallSjablonElementerNettoBarnetilsyn = 3 + 2 + 4;
    // Underholdskostnad: Sjablontall (0001, 0003, 0041) + Forbruksutgifter + Barnetilsyn
    var forventetAntallSjablonElementerUnderholdskostnad = 6 + 8 + 12;
    // BPsAndelUnderholdskostnad: Sjablontall (0004, 0005, 0030, 0031, 0039)
    var forventetAntallSjablonElementerBPsAndelUnderholdskostnad = 14;
    // Samvaersfradrag: Samvaersfradrag
    var forventetAntallSjablonElementerSamvaersfradrag = 8;
    // Barnebidrag: Sjablontall (0021, 0022)
    var forventetAntallSjablonElementerBarnebidrag = 6;

    assertAll(
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody()).isNotNull(),

        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPBidragsevneResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPBidragsevneResultat().getResultatPeriodeListe())
            .isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPBidragsevneResultat().getResultatPeriodeListe()
            .size()).isEqualTo(1),
        () -> assertThat(bidragsevneGrunnlagTilCore.getSjablonPeriodeListe().size()).isEqualTo(forventetAntallSjablonElementerBidragsevne),

        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBMNettoBarnetilsynResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe())
            .isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe()
            .size()).isEqualTo(1),
        () -> assertThat(nettoBarnetilsynGrunnlagTilCore.getSjablonPeriodeListe().size()).isEqualTo(forventetAntallSjablonElementerNettoBarnetilsyn),

        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBMUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBMUnderholdskostnadResultat()
            .getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBMUnderholdskostnadResultat()
            .getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(underholdskostnadGrunnlagTilCore.getSjablonPeriodeListe().size())
            .isEqualTo(forventetAntallSjablonElementerUnderholdskostnad),

        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPAndelUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPAndelUnderholdskostnadResultat()
            .getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPAndelUnderholdskostnadResultat()
            .getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore.getSjablonPeriodeListe().size())
            .isEqualTo(forventetAntallSjablonElementerBPsAndelUnderholdskostnad),

        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPSamvaersfradragResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe())
            .isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe()
            .size()).isEqualTo(1),
        () -> assertThat(samvaersfradragGrunnlagTilCore.getSjablonPeriodeListe().size()).isEqualTo(forventetAntallSjablonElementerSamvaersfradrag),

        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPKostnadsberegnetBidragResultat()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPKostnadsberegnetBidragResultat()
            .getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPKostnadsberegnetBidragResultat()
            .getResultatPeriodeListe().size()).isEqualTo(1),

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
                .filter(sjablontall -> sjablontall.getTypeSjablon().equals("0001"))
                .filter(sjablon -> ((!(sjablon.getDatoFom().isAfter(LocalDate.parse("2020-01-01")))) && (!(sjablon.getDatoTom()
                    .isBefore(LocalDate.parse("2017-01-01")))))).count()),

        // Sjekk at det mappes ut riktig antall sjabloner av type Forbruksutgifter
        () -> assertThat(underholdskostnadGrunnlagTilCore.getSjablonPeriodeListe().stream()
            .filter(sjablonPeriodeCore -> sjablonPeriodeCore.getSjablonNavn().equals(SjablonNavn.FORBRUKSUTGIFTER.getNavn())).count())
            .isEqualTo(TestUtil.dummySjablonForbruksutgifterListe().stream()
                .filter(sjablon -> ((!(sjablon.getDatoFom().isAfter(LocalDate.parse("2020-01-01")))) && (!(sjablon.getDatoTom()
                    .isBefore(LocalDate.parse("2017-01-01")))))).count()),

        // Sjekk at det mappes ut riktig verdi for en gitt sjablon av type Sjablontall
        () -> assertThat(underholdskostnadGrunnlagTilCore.getSjablonPeriodeListe().stream()
            .filter(sjablonPeriodeCore -> (sjablonPeriodeCore.getSjablonNavn().equals(SjablonTallNavn.ORDINAER_BARNETRYGD_BELOP.getNavn())) &&
                (sjablonPeriodeCore.getSjablonPeriodeDatoFraTil().getPeriodeDatoFra().equals(LocalDate.parse("2019-07-01"))))
            .map(SjablonPeriodeCore::getSjablonInnholdListe)
            .flatMap(Collection::stream)
            .findFirst()
            .map(SjablonInnholdCore::getSjablonInnholdVerdi)
            .orElse(BigDecimal.ZERO))
            .isEqualTo(BigDecimal.valueOf(1054)),

        // Sjekk at det mappes ut riktig verdi for en gitt sjablon av type MaksFradrag
        () -> assertThat(nettoBarnetilsynGrunnlagTilCore.getSjablonPeriodeListe().stream()
            .filter(sjablonPeriodeCore -> (sjablonPeriodeCore.getSjablonNavn().equals(SjablonNavn.MAKS_FRADRAG.getNavn())) &&
                (sjablonPeriodeCore.getSjablonPeriodeDatoFraTil().getPeriodeDatoFra().equals(LocalDate.parse("2008-07-01"))))
            .map(SjablonPeriodeCore::getSjablonInnholdListe)
            .flatMap(Collection::stream)
            .findFirst()
            .map(SjablonInnholdCore::getSjablonInnholdVerdi)
            .orElse(BigDecimal.ZERO))
            .isEqualTo(BigDecimal.valueOf(3333)));
  }

  @Test
  @DisplayName("Skal ikke koble BM inntekt og søknadsbarn - knytningsverdi = null")
  void skalIkkeKobleBMInntektOgSoknadsbarn() {
    var bpAndelUnderholdskostnadGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnBPsAndelUnderholdskostnadGrunnlagCore.class);

    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
    when(bpAndelUnderholdskostnadCoreMock.beregnBPsAndelUnderholdskostnad(bpAndelUnderholdskostnadGrunnlagTilCoreCaptor.capture()))
        .thenReturn(TestUtil.dummyBPsAndelUnderholdskostnadResultatCore());
    when(samvaersfradragCoreMock.beregnSamvaersfradrag(any())).thenReturn(TestUtil.dummySamvaersfradragResultatCore());
    when(kostnadsberegnetBidragCoreMock.beregnKostnadsberegnetBidrag(any())).thenReturn(TestUtil.dummyKostnadsberegnetBidragResultatCore());
    when(barnebidragCoreMock.beregnBarnebidrag(any())).thenReturn(TestUtil.dummyBarnebidragResultatCore());

    beregnBarnebidragService.beregn(TestUtil.byggTotalBarnebidragGrunnlag());

    var bpAndelUnderholdskostnadGrunnlagTilCore = bpAndelUnderholdskostnadGrunnlagTilCoreCaptor.getValue();

    assertAll(
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore).isNotNull(),
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore.getInntektBMPeriodeListe()).isNotNull(),
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore.getInntektBMPeriodeListe().size()).isEqualTo(1)
    );
  }

  @Test
  @DisplayName("Skal koble BM inntekt og søknadsbarn - likhet - knytningsverdi = 1")
  void skalKobleBMInntektOgSoknadsbarnLikhet() {
    var bpAndelUnderholdskostnadGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnBPsAndelUnderholdskostnadGrunnlagCore.class);

    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
    when(bpAndelUnderholdskostnadCoreMock.beregnBPsAndelUnderholdskostnad(bpAndelUnderholdskostnadGrunnlagTilCoreCaptor.capture()))
        .thenReturn(TestUtil.dummyBPsAndelUnderholdskostnadResultatCore());
    when(samvaersfradragCoreMock.beregnSamvaersfradrag(any())).thenReturn(TestUtil.dummySamvaersfradragResultatCore());
    when(kostnadsberegnetBidragCoreMock.beregnKostnadsberegnetBidrag(any())).thenReturn(TestUtil.dummyKostnadsberegnetBidragResultatCore());
    when(barnebidragCoreMock.beregnBarnebidrag(any())).thenReturn(TestUtil.dummyBarnebidragResultatCore());

    beregnBarnebidragService.beregn(TestUtil.byggTotalBarnebidragGrunnlagMedKnytningBMInntektSoknadsbarn("1"));

    var bpAndelUnderholdskostnadGrunnlagTilCore = bpAndelUnderholdskostnadGrunnlagTilCoreCaptor.getValue();

    assertAll(
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore).isNotNull(),
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore.getInntektBMPeriodeListe()).isNotNull(),
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore.getInntektBMPeriodeListe().size()).isEqualTo(1)
    );
  }

  @Test
  @DisplayName("Skal koble BM inntekt og søknadsbarn - ikke likhet - knytningsverdi = 2")
  void skalKobleBMInntektOgSoknadsbarnIkkeLikhet() {
    var bpAndelUnderholdskostnadGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnBPsAndelUnderholdskostnadGrunnlagCore.class);

    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
    when(bpAndelUnderholdskostnadCoreMock.beregnBPsAndelUnderholdskostnad(bpAndelUnderholdskostnadGrunnlagTilCoreCaptor.capture()))
        .thenReturn(TestUtil.dummyBPsAndelUnderholdskostnadResultatCore());
    when(samvaersfradragCoreMock.beregnSamvaersfradrag(any())).thenReturn(TestUtil.dummySamvaersfradragResultatCore());
    when(kostnadsberegnetBidragCoreMock.beregnKostnadsberegnetBidrag(any())).thenReturn(TestUtil.dummyKostnadsberegnetBidragResultatCore());
    when(barnebidragCoreMock.beregnBarnebidrag(any())).thenReturn(TestUtil.dummyBarnebidragResultatCore());

    beregnBarnebidragService.beregn(TestUtil.byggTotalBarnebidragGrunnlagMedKnytningBMInntektSoknadsbarn("2"));

    var bpAndelUnderholdskostnadGrunnlagTilCore = bpAndelUnderholdskostnadGrunnlagTilCoreCaptor.getValue();

    assertAll(
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore).isNotNull(),
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore.getInntektBMPeriodeListe()).isNotNull(),
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore.getInntektBMPeriodeListe().size()).isEqualTo(0)
    );
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved feil retur fra BidragsevneCore")
  void skalKasteUgyldigInputExceptionVedFeilReturFraBidragsevneCore() {
    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCoreMedAvvik());

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggTotalBarnebidragGrunnlag()))
        .withMessageContaining("Ugyldig input ved beregning av bidragsevne. Følgende avvik ble funnet:")
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved feil retur fra NettoBarnetilsynCore")
  void skalKasteUgyldigInputExceptionVedFeilReturFraNettoBarnetilsynCore() {
    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCoreMedAvvik());

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggTotalBarnebidragGrunnlag()))
        .withMessageContaining("Ugyldig input ved beregning av netto barnetilsyn. Følgende avvik ble funnet:")
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved feil retur fra UnderholdskostnadCore")
  void skalKasteUgyldigInputExceptionVedFeilReturFraUnderholdskostnadCore() {
    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCoreMedAvvik());

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggTotalBarnebidragGrunnlag()))
        .withMessageContaining("Ugyldig input ved beregning av underholdskostnad. Følgende avvik ble funnet:")
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved feil retur fra BPsAndelUnderholdskostnadCore")
  void skalKasteUgyldigInputExceptionVedFeilReturFraBPsAndelUnderholdskostnadCore() {
    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
    when(bpAndelUnderholdskostnadCoreMock.beregnBPsAndelUnderholdskostnad(any()))
        .thenReturn(TestUtil.dummyBPsAndelUnderholdskostnadResultatCoreMedAvvik());

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggTotalBarnebidragGrunnlag()))
        .withMessageContaining("Ugyldig input ved beregning av BPs andel av underholdskostnad. Følgende avvik ble funnet:")
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved feil retur fra SamvaersfradragCore")
  void skalKasteUgyldigInputExceptionVedFeilReturFraSamvaersfradragCore() {
    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
    when(bpAndelUnderholdskostnadCoreMock.beregnBPsAndelUnderholdskostnad(any())).thenReturn(TestUtil.dummyBPsAndelUnderholdskostnadResultatCore());
    when(samvaersfradragCoreMock.beregnSamvaersfradrag(any())).thenReturn(TestUtil.dummySamvaersfradragResultatCoreMedAvvik());

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggTotalBarnebidragGrunnlag()))
        .withMessageContaining("Ugyldig input ved beregning av samværsfradrag. Følgende avvik ble funnet:")
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved feil retur fra KostnadsberegnetBidragCore")
  void skalKasteUgyldigInputExceptionVedFeilReturFraKostnadsberegnetBidragCore() {
    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
    when(bpAndelUnderholdskostnadCoreMock.beregnBPsAndelUnderholdskostnad(any())).thenReturn(TestUtil.dummyBPsAndelUnderholdskostnadResultatCore());
    when(samvaersfradragCoreMock.beregnSamvaersfradrag(any())).thenReturn(TestUtil.dummySamvaersfradragResultatCore());
    when(kostnadsberegnetBidragCoreMock.beregnKostnadsberegnetBidrag(any())).thenReturn(TestUtil.dummyKostnadsberegnetBidragResultatCoreMedAvvik());

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggTotalBarnebidragGrunnlag()))
        .withMessageContaining("Ugyldig input ved beregning av kostnadsberegnet bidrag. Følgende avvik ble funnet:")
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved feil retur fra BarnebidragCore")
  void skalKasteUgyldigInputExceptionVedFeilReturFraBarnebidragCore() {
    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
    when(bpAndelUnderholdskostnadCoreMock.beregnBPsAndelUnderholdskostnad(any())).thenReturn(TestUtil.dummyBPsAndelUnderholdskostnadResultatCore());
    when(samvaersfradragCoreMock.beregnSamvaersfradrag(any())).thenReturn(TestUtil.dummySamvaersfradragResultatCore());
    when(kostnadsberegnetBidragCoreMock.beregnKostnadsberegnetBidrag(any())).thenReturn(TestUtil.dummyKostnadsberegnetBidragResultatCore());
    when(barnebidragCoreMock.beregnBarnebidrag(any())).thenReturn(TestUtil.dummyBarnebidragResultatCoreMedAvvik());

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggTotalBarnebidragGrunnlag()))
        .withMessageContaining("Ugyldig input ved beregning av barnebidrag. Følgende avvik ble funnet:")
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved validering av inputdata - søknadsbarn")
  void skalKasteUgyldigInputExceptionVedValideringAvInputdataSoknadsbarn() {
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(
            new BeregnTotalBarnebidragGrunnlag(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"),
                TestUtil.byggSoknadsbarnGrunnlagUtenSoknadsbarnFodselsdato(), TestUtil.byggInntektGrunnlag(), TestUtil.byggBidragsevneGrunnlag(),
                TestUtil.byggNettoBarnetilsynGrunnlag(), TestUtil.byggUnderholdskostnadGrunnlag(), TestUtil.byggSamvaersfradragGrunnlag(),
                TestUtil.byggBarnebidragGrunnlag())))
        .withMessageContaining("soknadsbarnFodselsdato kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved validering av inputdata - inntekt")
  void skalKasteUgyldigInputExceptionVedValideringAvInputdataInntekt() {
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(
            new BeregnTotalBarnebidragGrunnlag(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), TestUtil.byggSoknadsbarnGrunnlag(),
                TestUtil.byggInntektGrunnlagUtenInntektBMBelop(), TestUtil.byggBidragsevneGrunnlag(), TestUtil.byggNettoBarnetilsynGrunnlag(),
                TestUtil.byggUnderholdskostnadGrunnlag(), TestUtil.byggSamvaersfradragGrunnlag(), TestUtil.byggBarnebidragGrunnlag())))
        .withMessageContaining("BM inntektBelop kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved validering av inputdata - bidragsevne")
  void skalKasteUgyldigInputExceptionVedValideringAvInputdataBidragsevne() {
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(
            new BeregnTotalBarnebidragGrunnlag(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), TestUtil.byggSoknadsbarnGrunnlag(),
                TestUtil.byggInntektGrunnlag(), TestUtil.byggBidragsevneGrunnlagUtenBostatusKode(), TestUtil.byggNettoBarnetilsynGrunnlag(),
                TestUtil.byggUnderholdskostnadGrunnlag(), TestUtil.byggSamvaersfradragGrunnlag(), TestUtil.byggBarnebidragGrunnlag())))
        .withMessageContaining("bostatusKode kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved validering av inputdata - netto barnetilsyn")
  void skalKasteUgyldigInputExceptionVedValideringAvInputdataNettoBarnetilsyn() {
    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(
            new BeregnTotalBarnebidragGrunnlag(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), TestUtil.byggSoknadsbarnGrunnlag(),
                TestUtil.byggInntektGrunnlag(), TestUtil.byggBidragsevneGrunnlag(), TestUtil.byggNettoBarnetilsynGrunnlagUtenFaktiskUtgiftBelop(),
                TestUtil.byggUnderholdskostnadGrunnlag(), TestUtil.byggSamvaersfradragGrunnlag(), TestUtil.byggBarnebidragGrunnlag())))
        .withMessageContaining("faktiskUtgiftBelop kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved validering av inputdata - underholdskostnad")
  void skalKasteUgyldigInputExceptionVedValideringAvInputdataUnderholdskostnad() {
    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(
            new BeregnTotalBarnebidragGrunnlag(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), TestUtil.byggSoknadsbarnGrunnlag(),
                TestUtil.byggInntektGrunnlag(), TestUtil.byggBidragsevneGrunnlag(), TestUtil.byggNettoBarnetilsynGrunnlag(),
                TestUtil.byggUnderholdskostnadGrunnlagUtenForpleiningUtgiftBelop(), TestUtil.byggSamvaersfradragGrunnlag(),
                TestUtil.byggBarnebidragGrunnlag())))
        .withMessageContaining("forpleiningUtgiftBelop kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved validering av inputdata - samværsfradrag")
  void skalKasteUgyldigInputExceptionVedValideringAvInputdataSamvaersfradrag() {
    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
    when(bpAndelUnderholdskostnadCoreMock.beregnBPsAndelUnderholdskostnad(any())).thenReturn(TestUtil.dummyBPsAndelUnderholdskostnadResultatCore());
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(
            new BeregnTotalBarnebidragGrunnlag(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), TestUtil.byggSoknadsbarnGrunnlag(),
                TestUtil.byggInntektGrunnlag(), TestUtil.byggBidragsevneGrunnlag(), TestUtil.byggNettoBarnetilsynGrunnlag(),
                TestUtil.byggUnderholdskostnadGrunnlag(), TestUtil.byggSamvaersfradragGrunnlagUtenSamvaersklasseId(),
                TestUtil.byggBarnebidragGrunnlag())))
        .withMessageContaining("samvaersklasseId kan ikke være null");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved validering av inputdata - barnebidrag")
  void skalKasteUgyldigInputExceptionVedValideringAvInputdataBarnebidrag() {
    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
    when(bpAndelUnderholdskostnadCoreMock.beregnBPsAndelUnderholdskostnad(any())).thenReturn(TestUtil.dummyBPsAndelUnderholdskostnadResultatCore());
    when(samvaersfradragCoreMock.beregnSamvaersfradrag(any())).thenReturn(TestUtil.dummySamvaersfradragResultatCore());
    when(kostnadsberegnetBidragCoreMock.beregnKostnadsberegnetBidrag(any())).thenReturn(TestUtil.dummyKostnadsberegnetBidragResultatCore());
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(
            new BeregnTotalBarnebidragGrunnlag(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), TestUtil.byggSoknadsbarnGrunnlag(),
                TestUtil.byggInntektGrunnlag(), TestUtil.byggBidragsevneGrunnlag(), TestUtil.byggNettoBarnetilsynGrunnlag(),
                TestUtil.byggUnderholdskostnadGrunnlag(), TestUtil.byggSamvaersfradragGrunnlag(),
                TestUtil.byggBarnebidragGrunnlagUtenBarnetilleggBMBruttoBelop())))
        .withMessageContaining("BM barnetilleggBruttoBelop kan ikke være null");
  }
}
