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
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.service.BeregnBarnebidragService;
import no.nav.bidrag.commons.web.HttpResponse;
import no.nav.bidrag.commons.web.test.HttpHeaderTestRestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@DisplayName("BeregnBarnebidragControllerTest")
@SpringBootTest(classes = BidragBeregnBarnebidragLocal.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class BeregnBarnebidragControllerTest {

  @Autowired
  private HttpHeaderTestRestTemplate httpHeaderTestRestTemplate;
  @LocalServerPort
  private int port;
  @MockBean
  private BeregnBarnebidragService beregnBarnebidragServiceMock;

  @BeforeEach
  void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("Skal returnere total barnebidrag resultat ved gyldig input")
  void skalReturnereTotalBarnebidragResultatVedGyldigInput() {

    when(beregnBarnebidragServiceMock.beregn(any(BeregnTotalBarnebidragGrunnlag.class))).thenReturn(HttpResponse.from(OK,
        new BeregnTotalBarnebidragResultat(TestUtil.dummyBidragsevneResultat(), TestUtil.dummyNettoBarnetilsynResultat(),
            TestUtil.dummyUnderholdskostnadResultat(), TestUtil.dummyBPsAndelUnderholdskostnadResultat(), TestUtil.dummySamvaersfradragResultat(),
            TestUtil.dummyKostnadsberegnetBidragResultat(), TestUtil.dummyBarnebidragResultat())));

    var url = "http://localhost:" + port + "/bidrag-beregn-barnebidrag-rest/beregn/barnebidrag";
    var request = initHttpEntity(TestUtil.byggTotalBarnebidragGrunnlag());
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
            totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatBeregning().getResultat25ProsentInntekt())
            .isEqualTo(BigDecimal.valueOf(100)),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatBeregning().getResultatEvneBelop())
            .isEqualTo(BigDecimal.valueOf(100)),

        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe().size())
            .isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0)
            .getResultatSoknadsbarnPersonId()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0)
            .getResultatBelop()).isEqualTo(BigDecimal.valueOf(100)),

        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatSoknadsbarnPersonId())
            .isEqualTo(1),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoTil())
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
            totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatBelop()).isEqualTo(BigDecimal.valueOf(100)),

        () -> assertThat(totalBarnebidragResultat.getBeregnBPKostnadsberegnetBidragResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPKostnadsberegnetBidragResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPKostnadsberegnetBidragResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPKostnadsberegnetBidragResultat().getResultatPeriodeListe().get(0)
            .getResultatSoknadsbarnPersonId()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPKostnadsberegnetBidragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPKostnadsberegnetBidragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPKostnadsberegnetBidragResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
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
  @DisplayName("Skal returnere 400 Bad Request når input data mangler")
  void skalReturnere400BadRequestNaarInputDataMangler() {

    when(beregnBarnebidragServiceMock.beregn(any(BeregnTotalBarnebidragGrunnlag.class))).thenReturn(HttpResponse.from(BAD_REQUEST));

    var url = "http://localhost:" + port + "/bidrag-beregn-barnebidrag-rest/beregn/barnebidrag";
    var request = initHttpEntity(TestUtil.byggTotalBarnebidragGrunnlag());
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnTotalBarnebidragResultat.class);
    var totalBarnebidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST),
        () -> assertThat(totalBarnebidragResultat).isNull()
    );
  }

  @Test
  @DisplayName("Skal returnere 500 Internal Server Error når kall til servicen feiler")
  void skalReturnere500InternalServerErrorNaarKallTilServicenFeiler() {

    when(beregnBarnebidragServiceMock.beregn(any(BeregnTotalBarnebidragGrunnlag.class))).thenReturn(HttpResponse.from(INTERNAL_SERVER_ERROR));

    var url = "http://localhost:" + port + "/bidrag-beregn-barnebidrag-rest/beregn/barnebidrag";
    var request = initHttpEntity(TestUtil.byggTotalBarnebidragGrunnlag());
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnTotalBarnebidragResultat.class);
    var totalBarnebidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR),
        () -> assertThat(totalBarnebidragResultat).isNull()
    );
  }

  private <T> HttpEntity<T> initHttpEntity(T body) {
    var httpHeaders = new HttpHeaders();
    return new HttpEntity<>(body, httpHeaders);
  }
}
