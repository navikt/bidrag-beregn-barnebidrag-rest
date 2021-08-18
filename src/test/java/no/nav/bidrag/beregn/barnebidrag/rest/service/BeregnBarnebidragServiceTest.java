package no.nav.bidrag.beregn.barnebidrag.rest.service;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import no.nav.bidrag.beregn.barnebidrag.BarnebidragCore;
import no.nav.bidrag.beregn.barnebidrag.dto.BeregnBarnebidragGrunnlagCore;
import no.nav.bidrag.beregn.barnebidrag.rest.TestUtil;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonConsumer;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;
import no.nav.bidrag.beregn.barnebidrag.rest.mapper.BPAndelUnderholdskostnadCoreMapper;
import no.nav.bidrag.beregn.barnebidrag.rest.mapper.BarnebidragCoreMapper;
import no.nav.bidrag.beregn.barnebidrag.rest.mapper.BidragsevneCoreMapper;
import no.nav.bidrag.beregn.barnebidrag.rest.mapper.NettoBarnetilsynCoreMapper;
import no.nav.bidrag.beregn.barnebidrag.rest.mapper.SamvaersfradragCoreMapper;
import no.nav.bidrag.beregn.barnebidrag.rest.mapper.UnderholdskostnadCoreMapper;
import no.nav.bidrag.beregn.bidragsevne.BidragsevneCore;
import no.nav.bidrag.beregn.bidragsevne.dto.BeregnBidragsevneGrunnlagCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.BPsAndelUnderholdskostnadCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnBPsAndelUnderholdskostnadGrunnlagCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.NettoBarnetilsynCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnNettoBarnetilsynGrunnlagCore;
import no.nav.bidrag.beregn.samvaersfradrag.SamvaersfradragCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnSamvaersfradragGrunnlagCore;
import no.nav.bidrag.beregn.underholdskostnad.UnderholdskostnadCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadGrunnlagCore;
import no.nav.bidrag.commons.web.HttpResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
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
  private BarnebidragCore barnebidragCoreMock;
  @Mock
  private BidragsevneCoreMapper bidragsevneCoreMapperMock;
  @Mock
  private NettoBarnetilsynCoreMapper nettoBarnetilsynCoreMapperMock;
  @Mock
  private UnderholdskostnadCoreMapper underholdskostnadCoreMapperMock;
  @Mock
  private BPAndelUnderholdskostnadCoreMapper bpAndelUnderholdskostnadCoreMapperMock;
  @Mock
  private SamvaersfradragCoreMapper samvaersfradragCoreMapperMock;
  @Mock
  private BarnebidragCoreMapper barnebidragCoreMapperMock;


  void settOppSjablonMocks() {
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

  void settOppCoreMapperMocks() {
    when(bidragsevneCoreMapperMock.mapBidragsevneGrunnlagTilCore(any(), any())).thenReturn(new BeregnBidragsevneGrunnlagCore(
        LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList()));
    when(nettoBarnetilsynCoreMapperMock.mapNettoBarnetilsynGrunnlagTilCore(any(), any(), any())).thenReturn(new BeregnNettoBarnetilsynGrunnlagCore(
        LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), emptyList(), emptyList()));
    when(underholdskostnadCoreMapperMock.mapUnderholdskostnadGrunnlagTilCore(any(), any(), any(), any(), any())).thenReturn(
        new BeregnUnderholdskostnadGrunnlagCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"),
            new no.nav.bidrag.beregn.underholdskostnad.dto.SoknadsbarnCore("", 0, LocalDate.now()), emptyList(), emptyList(), emptyList(),
            emptyList()));
    when(bpAndelUnderholdskostnadCoreMapperMock.mapBPAndelUnderholdskostnadGrunnlagTilCore(any(), any(), any(), any())).thenReturn(
        new BeregnBPsAndelUnderholdskostnadGrunnlagCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), 0, emptyList(), emptyList(),
            emptyList(), emptyList(), emptyList()));
    when(samvaersfradragCoreMapperMock.mapSamvaersfradragGrunnlagTilCore(any(), any(), any(), any())).thenReturn(
        new BeregnSamvaersfradragGrunnlagCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"),
            new no.nav.bidrag.beregn.samvaersfradrag.dto.SoknadsbarnCore("", 0, LocalDate.now()), emptyList(), emptyList()));
    when(barnebidragCoreMapperMock.mapBarnebidragGrunnlagTilCore(any(), any(), any(), any(), any())).thenReturn(new BeregnBarnebidragGrunnlagCore(
        LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(),
        emptyList(), emptyList(), emptyList()));
  }

//  @Test
//  @DisplayName("Skal beregne bidrag når retur fra consumer-moduler og core-moduler er OK")
//  void skalBeregneBidragNaarReturFraConsumerOgCoreModulerErOk() {
//    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
//    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
//    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
//    when(bpAndelUnderholdskostnadCoreMock.beregnBPsAndelUnderholdskostnad(any())).thenReturn(TestUtil.dummyBPsAndelUnderholdskostnadResultatCore());
//    when(samvaersfradragCoreMock.beregnSamvaersfradrag(any())).thenReturn(TestUtil.dummySamvaersfradragResultatCore());
//    when(barnebidragCoreMock.beregnBarnebidrag(any())).thenReturn(TestUtil.dummyBarnebidragResultatCore());
//
//    settOppSjablonMocks();
//    settOppCoreMapperMocks();
//
//    var beregnTotalBarnebidragResultat = beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlag());
//
//    assertAll(
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody()).isNotNull(),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPBidragsevneResultat()).isNotNull(),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBMNettoBarnetilsynResultat()).isNotNull(),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBMUnderholdskostnadResultat()).isNotNull(),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPAndelUnderholdskostnadResultat()).isNotNull(),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPSamvaersfradragResultat()).isNotNull(),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBarnebidragResultat()).isNotNull(),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPBidragsevneResultat().getResultatPeriodeListe()
//            .size()).isEqualTo(1),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe()
//            .size()).isEqualTo(1),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe()
//            .size()).isEqualTo(1),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPAndelUnderholdskostnadResultat()
//            .getResultatPeriodeListe().size()).isEqualTo(1),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe()
//            .size()).isEqualTo(1),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBarnebidragResultat().getResultatPeriodeListe()
//            .size()).isEqualTo(1)
//    );
//  }

//  @Test
//  @Disabled
//  @DisplayName("Skal ha korrekt sjablon-grunnlag når beregningsmodulen kalles")
//  void skalHaKorrektSjablonGrunnlagNaarBeregningsmodulenKalles() {
//    var bidragsevneGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnBidragsevneGrunnlagCore.class);
//    var nettoBarnetilsynGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnNettoBarnetilsynGrunnlagCore.class);
//    var underholdskostnadGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnUnderholdskostnadGrunnlagCore.class);
//    var bpAndelUnderholdskostnadGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnBPsAndelUnderholdskostnadGrunnlagCore.class);
//    var samvaersfradragGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnSamvaersfradragGrunnlagCore.class);
//    var barnebidragGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnBarnebidragGrunnlagCore.class);
//
//    when(bidragsevneCoreMock.beregnBidragsevne(bidragsevneGrunnlagTilCoreCaptor.capture()))
//        .thenReturn(TestUtil.dummyBidragsevneResultatCore());
//    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(nettoBarnetilsynGrunnlagTilCoreCaptor.capture()))
//        .thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
//    when(underholdskostnadCoreMock.beregnUnderholdskostnad(underholdskostnadGrunnlagTilCoreCaptor.capture()))
//        .thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
//    when(bpAndelUnderholdskostnadCoreMock.beregnBPsAndelUnderholdskostnad(bpAndelUnderholdskostnadGrunnlagTilCoreCaptor.capture()))
//        .thenReturn(TestUtil.dummyBPsAndelUnderholdskostnadResultatCore());
//    when(samvaersfradragCoreMock.beregnSamvaersfradrag(samvaersfradragGrunnlagTilCoreCaptor.capture()))
//        .thenReturn(TestUtil.dummySamvaersfradragResultatCore());
//    when(barnebidragCoreMock.beregnBarnebidrag(barnebidragGrunnlagTilCoreCaptor.capture())).thenReturn(TestUtil.dummyBarnebidragResultatCore());
//
//    settOppSjablonMocks();
//    settOppCoreMapperMocks();
//
//    var beregnTotalBarnebidragResultat = beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlag());
//
//    var bidragsevneGrunnlagTilCore = bidragsevneGrunnlagTilCoreCaptor.getValue();
//    var nettoBarnetilsynGrunnlagTilCore = nettoBarnetilsynGrunnlagTilCoreCaptor.getValue();
//    var underholdskostnadGrunnlagTilCore = underholdskostnadGrunnlagTilCoreCaptor.getValue();
//    var bpAndelUnderholdskostnadGrunnlagTilCore = bpAndelUnderholdskostnadGrunnlagTilCoreCaptor.getValue();
//    var samvaersfradragGrunnlagTilCore = samvaersfradragGrunnlagTilCoreCaptor.getValue();
//    var barnebidragGrunnlagTilCore = barnebidragGrunnlagTilCoreCaptor.getValue();
//
//    // For Sjablontall sjekkes at det er riktig type sjablontall. For alle sjabloner sjekkes det at datoen er innenfor beregn-fra-til-dato
//    // For å finne riktig tall: Sjekk TestUtil.dummySjablonxxx; tell hvor mange sjabloner som er innenfor dato og (for Sjablontall) av riktig type
//
//    // Bidragsevne: Sjablontall (0004, 0017, 0019, 0023, 0025, 0027, 0028, 0039, 0040) + Bidragsevne + TrinnvisSkattesats
//    var forventetAntallSjablonElementerBidragsevne = 25 + 8 + 8;
//    // NettoBarnetilsyn: Sjablontall (0015) + MaksFradrag + MaksTilsyn
//    var forventetAntallSjablonElementerNettoBarnetilsyn = 3 + 9 + 6;
//    // Underholdskostnad: Sjablontall (0001, 0003, 0041) + Forbruksutgifter + Barnetilsyn
//    var forventetAntallSjablonElementerUnderholdskostnad = 6 + 20 + 12;
//    // BPsAndelUnderholdskostnad: Sjablontall (0004, 0005, 0030, 0031, 0039)
//    var forventetAntallSjablonElementerBPsAndelUnderholdskostnad = 14;
//    // Samvaersfradrag: Samvaersfradrag
//    var forventetAntallSjablonElementerSamvaersfradrag = 81;
//    // Barnebidrag: Sjablontall (0021, 0022)
//    var forventetAntallSjablonElementerBarnebidrag = 6;
//
//    assertAll(
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody()).isNotNull(),
//
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPBidragsevneResultat()).isNotNull(),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPBidragsevneResultat().getResultatPeriodeListe())
//            .isNotNull(),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPBidragsevneResultat().getResultatPeriodeListe()
//            .size()).isEqualTo(1),
//        () -> assertThat(bidragsevneGrunnlagTilCore.getSjablonPeriodeListe().size()).isEqualTo(forventetAntallSjablonElementerBidragsevne),
//
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBMNettoBarnetilsynResultat()).isNotNull(),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe())
//            .isNotNull(),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe()
//            .size()).isEqualTo(1),
//        () -> assertThat(nettoBarnetilsynGrunnlagTilCore.getSjablonPeriodeListe().size()).isEqualTo(forventetAntallSjablonElementerNettoBarnetilsyn),
//
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBMUnderholdskostnadResultat()).isNotNull(),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBMUnderholdskostnadResultat()
//            .getResultatPeriodeListe()).isNotNull(),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBMUnderholdskostnadResultat()
//            .getResultatPeriodeListe().size()).isEqualTo(1),
//        () -> assertThat(underholdskostnadGrunnlagTilCore.getSjablonPeriodeListe().size())
//            .isEqualTo(forventetAntallSjablonElementerUnderholdskostnad),
//
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPAndelUnderholdskostnadResultat()).isNotNull(),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPAndelUnderholdskostnadResultat()
//            .getResultatPeriodeListe()).isNotNull(),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPAndelUnderholdskostnadResultat()
//            .getResultatPeriodeListe().size()).isEqualTo(1),
//        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore.getSjablonPeriodeListe().size())
//            .isEqualTo(forventetAntallSjablonElementerBPsAndelUnderholdskostnad),
//
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPSamvaersfradragResultat()).isNotNull(),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe())
//            .isNotNull(),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe()
//            .size()).isEqualTo(1),
//        () -> assertThat(samvaersfradragGrunnlagTilCore.getSjablonPeriodeListe().size()).isEqualTo(forventetAntallSjablonElementerSamvaersfradrag),
//
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBarnebidragResultat()).isNotNull(),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBarnebidragResultat().getResultatPeriodeListe())
//            .isNotNull(),
//        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody().getBeregnBarnebidragResultat().getResultatPeriodeListe()
//            .size()).isEqualTo(1),
//        () -> assertThat(barnebidragGrunnlagTilCore.getSjablonPeriodeListe().size()).isEqualTo(forventetAntallSjablonElementerBarnebidrag),
//
//        // Sjekk at det mappes ut riktig antall for en gitt sjablon av type Sjablontall
//        () -> assertThat(underholdskostnadGrunnlagTilCore.getSjablonPeriodeListe().stream()
//            .filter(sjablonPeriodeCore -> sjablonPeriodeCore.getNavn().equals(SjablonTallNavn.ORDINAER_BARNETRYGD_BELOP.getNavn())).count())
//            .isEqualTo(TestUtil.dummySjablonSjablontallListe().stream()
//                .filter(sjablontall -> sjablontall.getTypeSjablon().equals("0001"))
//                .filter(sjablon -> ((!(sjablon.getDatoFom().isAfter(LocalDate.parse("2020-01-01")))) && (!(sjablon.getDatoTom()
//                    .isBefore(LocalDate.parse("2017-01-01")))))).count()),
//
//        // Sjekk at det mappes ut riktig antall sjabloner av type Forbruksutgifter
//        () -> assertThat(underholdskostnadGrunnlagTilCore.getSjablonPeriodeListe().stream()
//            .filter(sjablonPeriodeCore -> sjablonPeriodeCore.getNavn().equals(SjablonNavn.FORBRUKSUTGIFTER.getNavn())).count())
//            .isEqualTo(TestUtil.dummySjablonForbruksutgifterListe().stream()
//                .filter(sjablon -> ((!(sjablon.getDatoFom().isAfter(LocalDate.parse("2020-01-01")))) && (!(sjablon.getDatoTom()
//                    .isBefore(LocalDate.parse("2017-01-01")))))).count()),
//
//        // Sjekk at det mappes ut riktig verdi for en gitt sjablon av type Sjablontall
//        () -> assertThat(underholdskostnadGrunnlagTilCore.getSjablonPeriodeListe().stream()
//            .filter(sjablonPeriodeCore -> (sjablonPeriodeCore.getNavn().equals(SjablonTallNavn.ORDINAER_BARNETRYGD_BELOP.getNavn())) &&
//                (sjablonPeriodeCore.getPeriode().getDatoFom().equals(LocalDate.parse("2019-07-01"))))
//            .map(SjablonPeriodeCore::getInnholdListe)
//            .flatMap(Collection::stream)
//            .findFirst()
//            .map(SjablonInnholdCore::getVerdi)
//            .orElse(BigDecimal.ZERO))
//            .isEqualTo(BigDecimal.valueOf(1054)),
//
//        // Sjekk at det mappes ut riktig verdi for en gitt sjablon av type MaksFradrag
//        () -> assertThat(nettoBarnetilsynGrunnlagTilCore.getSjablonPeriodeListe().stream()
//            .filter(sjablonPeriodeCore -> (sjablonPeriodeCore.getNavn().equals(SjablonNavn.MAKS_FRADRAG.getNavn())) &&
//                (sjablonPeriodeCore.getPeriode().getDatoFom().equals(LocalDate.parse("2008-07-01"))))
//            .map(SjablonPeriodeCore::getInnholdListe)
//            .flatMap(Collection::stream)
//            .findFirst()
//            .map(SjablonInnholdCore::getVerdi)
//            .orElse(BigDecimal.ZERO))
//            .isEqualTo(BigDecimal.valueOf(3333)));
//  }

  @Test
  @Disabled
  @DisplayName("Skal ikke koble BM inntekt og søknadsbarn - knytningsverdi = null")
  void skalIkkeKobleBMInntektOgSoknadsbarn() {
    var bpAndelUnderholdskostnadGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnBPsAndelUnderholdskostnadGrunnlagCore.class);

    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
    when(bpAndelUnderholdskostnadCoreMock.beregnBPsAndelUnderholdskostnad(bpAndelUnderholdskostnadGrunnlagTilCoreCaptor.capture()))
        .thenReturn(TestUtil.dummyBPsAndelUnderholdskostnadResultatCore());
    when(samvaersfradragCoreMock.beregnSamvaersfradrag(any())).thenReturn(TestUtil.dummySamvaersfradragResultatCore());
    when(barnebidragCoreMock.beregnBarnebidrag(any())).thenReturn(TestUtil.dummyBarnebidragResultatCore());

    settOppSjablonMocks();
    settOppCoreMapperMocks();

    beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlag());

    var bpAndelUnderholdskostnadGrunnlagTilCore = bpAndelUnderholdskostnadGrunnlagTilCoreCaptor.getValue();

    assertAll(
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore).isNotNull(),
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore.getInntektBMPeriodeListe()).isNotNull(),
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore.getInntektBMPeriodeListe().size()).isEqualTo(1)
    );
  }

  @Test
  @Disabled
  @DisplayName("Skal koble BM inntekt og søknadsbarn - likhet - knytningsverdi = 1")
  void skalKobleBMInntektOgSoknadsbarnLikhet() {
    var bpAndelUnderholdskostnadGrunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnBPsAndelUnderholdskostnadGrunnlagCore.class);

    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCore());
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());
    when(bpAndelUnderholdskostnadCoreMock.beregnBPsAndelUnderholdskostnad(bpAndelUnderholdskostnadGrunnlagTilCoreCaptor.capture()))
        .thenReturn(TestUtil.dummyBPsAndelUnderholdskostnadResultatCore());
    when(samvaersfradragCoreMock.beregnSamvaersfradrag(any())).thenReturn(TestUtil.dummySamvaersfradragResultatCore());
    when(barnebidragCoreMock.beregnBarnebidrag(any())).thenReturn(TestUtil.dummyBarnebidragResultatCore());

    settOppSjablonMocks();
    settOppCoreMapperMocks();

    beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlag("1"));

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
    when(barnebidragCoreMock.beregnBarnebidrag(any())).thenReturn(TestUtil.dummyBarnebidragResultatCore());

    settOppSjablonMocks();
    settOppCoreMapperMocks();

    beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlag("2"));

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

    when(bidragsevneCoreMapperMock.mapBidragsevneGrunnlagTilCore(any(), any())).thenReturn(new BeregnBidragsevneGrunnlagCore(
        LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList()));

    settOppSjablonMocks();

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlag()))
        .withMessageContaining("Ugyldig input ved beregning av bidragsevne. Følgende avvik ble funnet:")
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved feil retur fra NettoBarnetilsynCore")
  void skalKasteUgyldigInputExceptionVedFeilReturFraNettoBarnetilsynCore() {
    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCore());
    when(nettoBarnetilsynCoreMock.beregnNettoBarnetilsyn(any())).thenReturn(TestUtil.dummyNettoBarnetilsynResultatCoreMedAvvik());

    when(bidragsevneCoreMapperMock.mapBidragsevneGrunnlagTilCore(any(), any())).thenReturn(new BeregnBidragsevneGrunnlagCore(
        LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList()));
    when(nettoBarnetilsynCoreMapperMock.mapNettoBarnetilsynGrunnlagTilCore(any(), any(), any())).thenReturn(new BeregnNettoBarnetilsynGrunnlagCore(
        LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), emptyList(), emptyList()));

    settOppSjablonMocks();

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlag()))
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

    when(bidragsevneCoreMapperMock.mapBidragsevneGrunnlagTilCore(any(), any())).thenReturn(new BeregnBidragsevneGrunnlagCore(
        LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList()));
    when(nettoBarnetilsynCoreMapperMock.mapNettoBarnetilsynGrunnlagTilCore(any(), any(), any())).thenReturn(new BeregnNettoBarnetilsynGrunnlagCore(
        LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), emptyList(), emptyList()));
    when(underholdskostnadCoreMapperMock.mapUnderholdskostnadGrunnlagTilCore(any(), any(), any(), any(), any())).thenReturn(
        new BeregnUnderholdskostnadGrunnlagCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"),
            new no.nav.bidrag.beregn.underholdskostnad.dto.SoknadsbarnCore("", 0, LocalDate.now()), emptyList(), emptyList(), emptyList(),
            emptyList()));

    settOppSjablonMocks();

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlag()))
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

    when(bidragsevneCoreMapperMock.mapBidragsevneGrunnlagTilCore(any(), any())).thenReturn(new BeregnBidragsevneGrunnlagCore(
        LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList()));
    when(nettoBarnetilsynCoreMapperMock.mapNettoBarnetilsynGrunnlagTilCore(any(), any(), any())).thenReturn(new BeregnNettoBarnetilsynGrunnlagCore(
        LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), emptyList(), emptyList()));
    when(underholdskostnadCoreMapperMock.mapUnderholdskostnadGrunnlagTilCore(any(), any(), any(), any(), any())).thenReturn(
        new BeregnUnderholdskostnadGrunnlagCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"),
            new no.nav.bidrag.beregn.underholdskostnad.dto.SoknadsbarnCore("", 0, LocalDate.now()), emptyList(), emptyList(), emptyList(),
            emptyList()));
    when(bpAndelUnderholdskostnadCoreMapperMock.mapBPAndelUnderholdskostnadGrunnlagTilCore(any(), any(), any(), any())).thenReturn(
        new BeregnBPsAndelUnderholdskostnadGrunnlagCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), 0, emptyList(), emptyList(),
            emptyList(), emptyList(), emptyList()));

    settOppSjablonMocks();

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlag()))
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

    when(bidragsevneCoreMapperMock.mapBidragsevneGrunnlagTilCore(any(), any())).thenReturn(new BeregnBidragsevneGrunnlagCore(
        LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList()));
    when(nettoBarnetilsynCoreMapperMock.mapNettoBarnetilsynGrunnlagTilCore(any(), any(), any())).thenReturn(new BeregnNettoBarnetilsynGrunnlagCore(
        LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), emptyList(), emptyList()));
    when(underholdskostnadCoreMapperMock.mapUnderholdskostnadGrunnlagTilCore(any(), any(), any(), any(), any())).thenReturn(
        new BeregnUnderholdskostnadGrunnlagCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"),
            new no.nav.bidrag.beregn.underholdskostnad.dto.SoknadsbarnCore("", 0, LocalDate.now()), emptyList(), emptyList(), emptyList(),
            emptyList()));
    when(bpAndelUnderholdskostnadCoreMapperMock.mapBPAndelUnderholdskostnadGrunnlagTilCore(any(), any(), any(), any())).thenReturn(
        new BeregnBPsAndelUnderholdskostnadGrunnlagCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"), 0, emptyList(), emptyList(),
            emptyList(), emptyList(), emptyList()));
    when(samvaersfradragCoreMapperMock.mapSamvaersfradragGrunnlagTilCore(any(), any(), any(), any())).thenReturn(
        new BeregnSamvaersfradragGrunnlagCore(LocalDate.parse("2017-01-01"), LocalDate.parse("2020-01-01"),
            new no.nav.bidrag.beregn.samvaersfradrag.dto.SoknadsbarnCore("", 0, LocalDate.now()), emptyList(), emptyList()));

    settOppSjablonMocks();

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlag()))
        .withMessageContaining("Ugyldig input ved beregning av samværsfradrag. Følgende avvik ble funnet:")
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
    when(barnebidragCoreMock.beregnBarnebidrag(any())).thenReturn(TestUtil.dummyBarnebidragResultatCoreMedAvvik());

    settOppSjablonMocks();
    settOppCoreMapperMocks();

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlag()))
        .withMessageContaining("Ugyldig input ved beregning av barnebidrag. Følgende avvik ble funnet:")
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }

  @Test
  @Disabled
  @DisplayName("Skal kaste UgyldigInputException ved validering av inputdata - søknadsbarn")
  void skalKasteUgyldigInputExceptionVedValideringAvInputdataSoknadsbarn() {
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlagUtenSoknadsbarnFodselsdato()))
        .withMessageContaining("soknadsbarnFodselsdato kan ikke være null");
  }
}
