package no.nav.bidrag.beregn.barnebidrag.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

import java.math.BigDecimal;
import java.time.LocalDate;
import no.nav.bidrag.beregn.barnebidrag.rest.BidragBeregnBarnebidragLocal;
import no.nav.bidrag.beregn.barnebidrag.rest.TestUtil;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnForholdsmessigFordelingResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.service.BeregnBarnebidragService;
import no.nav.bidrag.beregn.barnebidrag.rest.service.BeregnForholdsmessigFordelingService;
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.BeregnForholdsmessigFordelingGrunnlagCore;
import no.nav.bidrag.commons.web.HttpResponse;
import no.nav.bidrag.commons.web.test.HttpHeaderTestRestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@DisplayName("BeregnBarnebidragControllerTest")
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = BidragBeregnBarnebidragLocal.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class BeregnBarnebidragControllerMockTest {

  @Autowired
  private HttpHeaderTestRestTemplate httpHeaderTestRestTemplate;
  @LocalServerPort
  private int port;
  @MockBean
  private BeregnBarnebidragService beregnBarnebidragServiceMock;
  @MockBean
  private BeregnForholdsmessigFordelingService beregnForholdsmessigFordelingServiceMock;

  @Test
  @DisplayName("Skal returnere total barnebidrag resultat ved gyldig input")
  void skalReturnereTotalBarnebidragResultatVedGyldigInput() {

    when(beregnBarnebidragServiceMock.beregn(any(BeregnTotalBarnebidragGrunnlag.class))).thenReturn(HttpResponse.from(OK,
        new BeregnTotalBarnebidragResultat(TestUtil.dummyBidragsevneResultat(), TestUtil.dummyNettoBarnetilsynResultat(),
            TestUtil.dummyUnderholdskostnadResultat(), TestUtil.dummyBPsAndelUnderholdskostnadResultat(), TestUtil.dummySamvaersfradragResultat(),
            TestUtil.dummyBarnebidragResultat())));

    var url = "http://localhost:" + port + "/bidrag-beregn-barnebidrag-rest/beregn/barnebidrag";
    var request = initHttpEntity(TestUtil.byggBarnebidragGrunnlag());
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnTotalBarnebidragResultat.class);
    var totalBarnebidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
        () -> assertThat(totalBarnebidragResultat).isNotNull(),

        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
                .getResultat25ProsentInntekt())
            .isEqualTo(BigDecimal.valueOf(100)),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatBeregning().getResultatEvneBelop())
            .isEqualTo(BigDecimal.valueOf(100)),

        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
                .getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
                .getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe().size())
            .isEqualTo(1),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0)
                .getResultatSoknadsbarnPersonId()).isEqualTo(1),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0)
                .getResultatBelop()).isEqualTo(BigDecimal.valueOf(100)),

        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatSoknadsbarnPersonId())
            .isEqualTo(1),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
                .getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
                .getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatBelop()).isEqualTo(BigDecimal.valueOf(100)),

        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatSoknadsbarnPersonId())
            .isEqualTo(1),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
                .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
                .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatAndelProsent()).isEqualTo(BigDecimal.valueOf(10)),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatAndelBelop()).isEqualTo(BigDecimal.valueOf(100)),

        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(0).getResultatSoknadsbarnPersonId())
            .isEqualTo(1),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
                .getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
                .getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatBelop()).isEqualTo(BigDecimal.valueOf(100)),

        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatSoknadsbarnPersonId()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatBelop()).isEqualTo(BigDecimal.valueOf(100)),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatKode()).isEqualTo("RESULTATKODE")
    );
  }

  @Test
  @DisplayName("Skal returnere 400 Bad Request når input data mangler - barnebidrag")
  void skalReturnere400BadRequestNaarInputDataManglerBarnebidrag() {

    when(beregnBarnebidragServiceMock.beregn(any(BeregnTotalBarnebidragGrunnlag.class))).thenReturn(HttpResponse.from(BAD_REQUEST));

    var url = "http://localhost:" + port + "/bidrag-beregn-barnebidrag-rest/beregn/barnebidrag";
    var request = initHttpEntity(TestUtil.byggBarnebidragGrunnlag());
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnTotalBarnebidragResultat.class);
    var totalBarnebidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST),
        () -> assertThat(totalBarnebidragResultat).isNull()
    );
  }

  @Test
  @DisplayName("Skal returnere 500 Internal Server Error når kall til servicen feiler - barnebidrag")
  void skalReturnere500InternalServerErrorNaarKallTilServicenFeilerBarnebidrag() {

    when(beregnBarnebidragServiceMock.beregn(any(BeregnTotalBarnebidragGrunnlag.class))).thenReturn(HttpResponse.from(INTERNAL_SERVER_ERROR));

    var url = "http://localhost:" + port + "/bidrag-beregn-barnebidrag-rest/beregn/barnebidrag";
    var request = initHttpEntity(TestUtil.byggBarnebidragGrunnlag());
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnTotalBarnebidragResultat.class);
    var totalBarnebidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR),
        () -> assertThat(totalBarnebidragResultat).isNull()
    );
  }

  @Test
  @DisplayName("Skal returnere forholdsmessig fordeling resultat ved gyldig input")
  void skalReturnereForholdsmessigFordelingResultatVedGyldigInput() {

    when(beregnForholdsmessigFordelingServiceMock.beregn(any())).thenReturn(HttpResponse.from(OK,
        new BeregnForholdsmessigFordelingResultat(TestUtil.dummyForholdsmessigFordelingResultatCore())));

    var url = "http://localhost:" + port + "/bidrag-beregn-barnebidrag-rest/beregn/forholdsmessigfordeling";
    var request = initHttpEntity(TestUtil.byggForholdsmessigFordelingGrunnlag());
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnForholdsmessigFordelingResultat.class);
    var forholdsmessigFordelingResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
        () -> assertThat(forholdsmessigFordelingResultat).isNotNull(),

        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe()).isNotNull(),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().size()).isEqualTo(1),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0)
            .getResultatPerBarnListe()).isNotNull(),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0)
            .getResultatPerBarnListe().size()).isEqualTo(1),

        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0).getSaksnr())
            .isEqualTo(1),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0)
            .getResultatPerBarnListe().get(0).getResultatBarnebidragBelop()).isEqualTo(BigDecimal.valueOf(100))
    );
  }

  @Test
  @DisplayName("Skal returnere 400 Bad Request når input data mangler - forholdsmessig fordeling")
  void skalReturnere400BadRequestNaarInputDataManglerForholdsmessigFordeling() {

    var url = "http://localhost:" + port + "/bidrag-beregn-barnebidrag-rest/beregn/forholdsmessigfordeling";
    var request = initHttpEntity(TestUtil.byggForholdsmessigFordelingGrunnlagUtenBeregnDatoFra());
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnForholdsmessigFordelingResultat.class);
    var forholdsmessigFordelingResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST),
        () -> assertThat(forholdsmessigFordelingResultat).isNull()
    );
  }

  @Test
  @DisplayName("Skal returnere 500 Internal Server Error når kall til servicen feiler - forholdsmessig fordeling")
  void skalReturnere500InternalServerErrorNaarKallTilServicenFeilerForholdsmessigFordeling() {

    when(beregnForholdsmessigFordelingServiceMock.beregn(any(BeregnForholdsmessigFordelingGrunnlagCore.class)))
        .thenReturn(HttpResponse.from(INTERNAL_SERVER_ERROR));

    var url = "http://localhost:" + port + "/bidrag-beregn-barnebidrag-rest/beregn/forholdsmessigfordeling";
    var request = initHttpEntity(TestUtil.byggForholdsmessigFordelingGrunnlag());
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnForholdsmessigFordelingResultat.class);
    var forholdsmessigFordelingResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR),
        () -> assertThat(forholdsmessigFordelingResultat).isNull()
    );
  }

  private <T> HttpEntity<T> initHttpEntity(T body) {
    var httpHeaders = new HttpHeaders();
    return new HttpEntity<>(body, httpHeaders);
  }
}
