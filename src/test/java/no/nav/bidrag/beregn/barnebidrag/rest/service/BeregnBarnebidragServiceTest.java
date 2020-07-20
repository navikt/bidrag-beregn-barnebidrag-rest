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
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBidragsevneGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.BidragsevneConsumerException;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.SjablonConsumerException;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.BidragsevneConsumer;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonConsumer;
import no.nav.bidrag.beregn.felles.dto.SjablonInnholdCore;
import no.nav.bidrag.beregn.felles.dto.SjablonPeriodeCore;
import no.nav.bidrag.beregn.felles.enums.SjablonNavn;
import no.nav.bidrag.beregn.felles.enums.SjablonTallNavn;
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

  @BeforeEach
  void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("Skal beregne bidragsevne når retur fra SjablonConsumer, BidragsevneConsumer og UnderholdskostnadCore er OK")
  void skalBeregneBidragsevneNårReturFraSjablonConsumerOgBidragsevneConsumerOgUnderholdskostnadCoreErOk() {
    when(sjablonConsumerMock.hentSjablonSjablontall()).thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonSjablontallListe()));
    when(sjablonConsumerMock.hentSjablonForbruksutgifter())
        .thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonForbruksutgifterListe()));
    when(bidragsevneConsumerMock.hentBidragsevne(any(BeregnBidragsevneGrunnlag.class)))
        .thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummyBidragsevneResultat()));
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());

    var beregnBarnebidragResultat = beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlag());

    assertAll(
        () -> assertThat(beregnBarnebidragResultat.getHttpStatus()).isEqualTo(HttpStatus.OK),
        () -> assertThat(beregnBarnebidragResultat.getBody()).isNotNull(),
        () -> assertThat(beregnBarnebidragResultat.getBody().getBeregnBidragsevneResultat()).isNotNull(),
        () -> assertThat(beregnBarnebidragResultat.getBody().getBeregnUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(beregnBarnebidragResultat.getBody().getRestenAvResultatet()).isNotNull(),
        () -> assertThat(beregnBarnebidragResultat.getBody().getBeregnBidragsevneResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(beregnBarnebidragResultat.getBody().getBeregnUnderholdskostnadResultat().getResultatPeriodeListe().size()).isEqualTo(1)
    );
  }

  @Test
  @DisplayName("Skal ha korrekt sjablon-grunnlag når beregningsmodulen kalles")
  void skalHaKorrektSjablonGrunnlagNårBeregningsmodulenKalles() {
    var grunnlagTilCoreCaptor = ArgumentCaptor.forClass(BeregnUnderholdskostnadGrunnlagCore.class);
    when(sjablonConsumerMock.hentSjablonSjablontall()).thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonSjablontallListe()));
    when(sjablonConsumerMock.hentSjablonForbruksutgifter())
        .thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummySjablonForbruksutgifterListe()));
    when(bidragsevneConsumerMock.hentBidragsevne(any(BeregnBidragsevneGrunnlag.class)))
        .thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummyBidragsevneResultat()));
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(grunnlagTilCoreCaptor.capture()))
        .thenReturn(TestUtil.dummyUnderholdskostnadResultatCore());

    var beregnUnderholdskostnadResultat = beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlag());
    var grunnlagTilCore = grunnlagTilCoreCaptor.getValue();

    var forventetAntallSjablonElementer = TestUtil.dummySjablonSjablontallListe().size() + TestUtil.dummySjablonForbruksutgifterListe().size();

    assertAll(
        () -> assertThat(beregnUnderholdskostnadResultat.getHttpStatus()).isEqualTo(HttpStatus.OK),
        () -> assertThat(beregnUnderholdskostnadResultat.getBody()).isNotNull(),
        () -> assertThat(beregnUnderholdskostnadResultat.getBody().getBeregnUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(beregnUnderholdskostnadResultat.getBody().getBeregnUnderholdskostnadResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(beregnUnderholdskostnadResultat.getBody().getBeregnUnderholdskostnadResultat().getResultatPeriodeListe().size())
            .isEqualTo(1),
        () -> assertThat(grunnlagTilCore.getSjablonPeriodeListe().size()).isEqualTo(forventetAntallSjablonElementer),

        // Sjekk at det mappes ut riktig antall for en gitt sjablon av type Sjablontall
        () -> assertThat(grunnlagTilCore.getSjablonPeriodeListe().stream()
            .filter(sjablonPeriodeCore -> sjablonPeriodeCore.getSjablonNavn().equals(SjablonTallNavn.ORDINAER_BARNETRYGD_BELOP.getNavn())).count())
            .isEqualTo(TestUtil.dummySjablonSjablontallListe().stream()
                .filter(sjablontall -> sjablontall.getTypeSjablon().equals("0001")).count()),

        // Sjekk at det mappes ut riktig antall sjabloner av type Forbruksutgifter
        () -> assertThat(grunnlagTilCore.getSjablonPeriodeListe().stream()
            .filter(sjablonPeriodeCore -> sjablonPeriodeCore.getSjablonNavn().equals(SjablonNavn.FORBRUKSUTGIFTER.getNavn())).count())
            .isEqualTo(TestUtil.dummySjablonForbruksutgifterListe().size()),

        // Sjekk at det mappes ut riktig verdi for en gitt sjablon av type Sjablontall
        () -> assertThat(grunnlagTilCore.getSjablonPeriodeListe().stream()
            .filter(sjablonPeriodeCore -> (sjablonPeriodeCore.getSjablonNavn().equals(SjablonTallNavn.ORDINAER_BARNETRYGD_BELOP.getNavn())) &&
                (sjablonPeriodeCore.getSjablonPeriodeDatoFraTil().getPeriodeDatoFra().equals(LocalDate.parse("2017-07-01"))))
            .map(SjablonPeriodeCore::getSjablonInnholdListe)
            .flatMap(Collection::stream)
            .findFirst()
            .map(SjablonInnholdCore::getSjablonInnholdVerdi)
            .orElse(0d))
            .isEqualTo(1054d),

        // Sjekk at det mappes ut riktig verdi for en gitt sjablon av type Forbruksutgifter
        () -> assertThat(grunnlagTilCore.getSjablonPeriodeListe().stream()
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
            .beregn(new BeregnBarnebidragGrunnlag(TestUtil.byggBidragsevneGrunnlag(), TestUtil.byggUnderholdskostnadGrunnlag(), "")))
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
    when(bidragsevneConsumerMock.hentBidragsevne(any(BeregnBidragsevneGrunnlag.class)))
        .thenReturn(new HttpStatusResponse<>(HttpStatus.OK, TestUtil.dummyBidragsevneResultat()));
    when(underholdskostnadCoreMock.beregnUnderholdskostnad(any())).thenReturn(TestUtil.dummyUnderholdskostnadResultatCoreMedAvvik());

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(TestUtil.byggBarnebidragGrunnlag()))
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved validering av inputdata")
  void skalKasteUgyldigInputExceptionVedValideringAvInputdata() {
    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnBarnebidragService.beregn(
            new BeregnBarnebidragGrunnlag(TestUtil.byggBidragsevneGrunnlag(), TestUtil.byggUnderholdskostnadGrunnlagUtenSoknadBarnFodselsdato(), "")))
        .withMessageContaining("soknadBarnFodselsdato kan ikke være null");
  }
}
