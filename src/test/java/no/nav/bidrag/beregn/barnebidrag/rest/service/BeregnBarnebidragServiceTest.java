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
import no.nav.bidrag.beregn.barnebidrag.rest.BidragBeregnBarnebidragLocal;
import no.nav.bidrag.beregn.barnebidrag.rest.TestUtil;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonConsumer;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;
import no.nav.bidrag.beregn.bidragsevne.BidragsevneCore;
import no.nav.bidrag.beregn.bidragsevne.dto.BeregnBidragsevneGrunnlagCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.BPsAndelUnderholdskostnadCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.dto.BeregnBPsAndelUnderholdskostnadGrunnlagCore;
import no.nav.bidrag.beregn.felles.dto.SjablonInnholdCore;
import no.nav.bidrag.beregn.felles.dto.SjablonPeriodeCore;
import no.nav.bidrag.beregn.felles.enums.SjablonNavn;
import no.nav.bidrag.beregn.felles.enums.SjablonTallNavn;
import no.nav.bidrag.beregn.nettobarnetilsyn.NettoBarnetilsynCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.dto.BeregnNettoBarnetilsynGrunnlagCore;
import no.nav.bidrag.beregn.samvaersfradrag.SamvaersfradragCore;
import no.nav.bidrag.beregn.samvaersfradrag.dto.BeregnSamvaersfradragGrunnlagCore;
import no.nav.bidrag.beregn.underholdskostnad.UnderholdskostnadCore;
import no.nav.bidrag.beregn.underholdskostnad.dto.BeregnUnderholdskostnadGrunnlagCore;
import no.nav.bidrag.commons.web.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

@SpringBootTest(classes = BidragBeregnBarnebidragLocal.class)
@DisplayName("BeregnBarnebidragServiceTest")
class BeregnBarnebidragServiceTest {

  @Autowired
  private BeregnBarnebidragService beregnBarnebidragService;

  @MockBean
  private SjablonConsumer sjablonConsumerMock;
  @MockBean
  private BidragsevneCore bidragsevneCoreMock;
  @MockBean
  private NettoBarnetilsynCore nettoBarnetilsynCoreMock;
  @MockBean
  private UnderholdskostnadCore underholdskostnadCoreMock;
  @MockBean
  private BPsAndelUnderholdskostnadCore bpAndelUnderholdskostnadCoreMock;
  @MockBean
  private SamvaersfradragCore samvaersfradragCoreMock;
  @MockBean
  private BarnebidragCore barnebidragCoreMock;


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
    when(barnebidragCoreMock.beregnBarnebidrag(barnebidragGrunnlagTilCoreCaptor.capture())).thenReturn(TestUtil.dummyBarnebidragResultatCore());

    settOppSjablonMocks();

    var beregnTotalBarnebidragResultat = beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlag());

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
    var forventetAntallSjablonElementerNettoBarnetilsyn = 3 + 9 + 6;
    // Underholdskostnad: Sjablontall (0001, 0003, 0041) + Forbruksutgifter + Barnetilsyn
    var forventetAntallSjablonElementerUnderholdskostnad = 6 + 20 + 12;
    // BPsAndelUnderholdskostnad: Sjablontall (0004, 0005, 0030, 0031, 0039)
    var forventetAntallSjablonElementerBPsAndelUnderholdskostnad = 14;
    // Samvaersfradrag: Samvaersfradrag
    var forventetAntallSjablonElementerSamvaersfradrag = 81;
    // Barnebidrag: Sjablontall (0021, 0022)
    var forventetAntallSjablonElementerBarnebidrag = 6;

    assertAll(
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK),
        () -> assertThat(beregnTotalBarnebidragResultat.getResponseEntity().getBody()).isNotNull(),
        () -> assertThat(bidragsevneGrunnlagTilCore.getSjablonPeriodeListe().size()).isEqualTo(forventetAntallSjablonElementerBidragsevne),
        () -> assertThat(nettoBarnetilsynGrunnlagTilCore.getSjablonPeriodeListe().size()).isEqualTo(forventetAntallSjablonElementerNettoBarnetilsyn),
        () -> assertThat(underholdskostnadGrunnlagTilCore.getSjablonPeriodeListe().size())
            .isEqualTo(forventetAntallSjablonElementerUnderholdskostnad),
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore.getSjablonPeriodeListe().size())
            .isEqualTo(forventetAntallSjablonElementerBPsAndelUnderholdskostnad),
        () -> assertThat(samvaersfradragGrunnlagTilCore.getSjablonPeriodeListe().size()).isEqualTo(forventetAntallSjablonElementerSamvaersfradrag),
        () -> assertThat(barnebidragGrunnlagTilCore.getSjablonPeriodeListe().size()).isEqualTo(forventetAntallSjablonElementerBarnebidrag),

        // Sjekk at det mappes ut riktig antall for en gitt sjablon av type Sjablontall
        () -> assertThat(underholdskostnadGrunnlagTilCore.getSjablonPeriodeListe().stream()
            .filter(sjablonPeriodeCore -> sjablonPeriodeCore.getNavn().equals(SjablonTallNavn.ORDINAER_BARNETRYGD_BELOP.getNavn())).count())
            .isEqualTo(TestUtil.dummySjablonSjablontallListe().stream()
                .filter(sjablontall -> sjablontall.getTypeSjablon().equals("0001"))
                .filter(sjablon -> ((!(sjablon.getDatoFom().isAfter(LocalDate.parse("2020-01-01")))) && (!(sjablon.getDatoTom()
                    .isBefore(LocalDate.parse("2017-01-01")))))).count()),

        // Sjekk at det mappes ut riktig antall sjabloner av type Forbruksutgifter
        () -> assertThat(underholdskostnadGrunnlagTilCore.getSjablonPeriodeListe().stream()
            .filter(sjablonPeriodeCore -> sjablonPeriodeCore.getNavn().equals(SjablonNavn.FORBRUKSUTGIFTER.getNavn())).count())
            .isEqualTo(TestUtil.dummySjablonForbruksutgifterListe().stream()
                .filter(sjablon -> ((!(sjablon.getDatoFom().isAfter(LocalDate.parse("2020-01-01")))) && (!(sjablon.getDatoTom()
                    .isBefore(LocalDate.parse("2017-01-01")))))).count()),

        // Sjekk at det mappes ut riktig verdi for en gitt sjablon av type Sjablontall
        () -> assertThat(underholdskostnadGrunnlagTilCore.getSjablonPeriodeListe().stream()
            .filter(sjablonPeriodeCore -> (sjablonPeriodeCore.getNavn().equals(SjablonTallNavn.ORDINAER_BARNETRYGD_BELOP.getNavn())) &&
                (sjablonPeriodeCore.getPeriode().getDatoFom().equals(LocalDate.parse("2019-07-01"))))
            .map(SjablonPeriodeCore::getInnholdListe)
            .flatMap(Collection::stream)
            .findFirst()
            .map(SjablonInnholdCore::getVerdi)
            .orElse(BigDecimal.ZERO))
            .isEqualTo(BigDecimal.valueOf(1054)),

        // Sjekk at det mappes ut riktig verdi for en gitt sjablon av type MaksFradrag
        () -> assertThat(nettoBarnetilsynGrunnlagTilCore.getSjablonPeriodeListe().stream()
            .filter(sjablonPeriodeCore -> (sjablonPeriodeCore.getNavn().equals(SjablonNavn.MAKS_FRADRAG.getNavn())) &&
                (sjablonPeriodeCore.getPeriode().getDatoFom().equals(LocalDate.parse("2008-07-01"))))
            .map(SjablonPeriodeCore::getInnholdListe)
            .flatMap(Collection::stream)
            .findFirst()
            .map(SjablonInnholdCore::getVerdi)
            .orElse(BigDecimal.ZERO))
            .isEqualTo(BigDecimal.valueOf(3333)));
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved feil retur fra BidragsevneCore")
  void skalKasteUgyldigInputExceptionVedFeilReturFraBidragsevneCore() {
    when(bidragsevneCoreMock.beregnBidragsevne(any())).thenReturn(TestUtil.dummyBidragsevneResultatCoreMedAvvik());

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

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlag()))
        .withMessageContaining("Ugyldig input ved beregning av barnebidrag. Følgende avvik ble funnet:")
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved validering av inputdata - søknadsbarn")
  void skalKasteUgyldigInputExceptionVedValideringAvInputdataSoknadsbarn() {
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlagUtenSoknadsbarnFodselsdato()))
        .withMessageContaining("fodselsdato mangler i objekt av type SoknadsbarnInfo");
  }
}
