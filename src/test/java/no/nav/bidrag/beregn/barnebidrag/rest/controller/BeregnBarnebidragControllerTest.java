package no.nav.bidrag.beregn.barnebidrag.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

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

        () -> assertThat(totalBarnebidragResultat.getBeregnBidragsevneResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBidragsevneResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBidragsevneResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatBeregning().getResultat25ProsentInntekt())
            .isEqualTo(100d),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatBeregning().getResultatEvneBelop())
            .isEqualTo(100d),

        () -> assertThat(totalBarnebidragResultat.getBeregnNettoBarnetilsynResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnNettoBarnetilsynResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnNettoBarnetilsynResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe().size())
            .isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0)
            .getResultatBelopNettoBarnetilsyn()).isEqualTo(100d),

        () -> assertThat(totalBarnebidragResultat.getBeregnUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnUnderholdskostnadResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnUnderholdskostnadResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatBelopUnderholdskostnad()).isEqualTo(100d),

        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
                .getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
                .getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatAndelProsent()).isEqualTo(10),

        () -> assertThat(totalBarnebidragResultat.getBeregnSamvaersfradragResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnSamvaersfradragResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnSamvaersfradragResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnSamvaersfradragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(
            totalBarnebidragResultat.getBeregnSamvaersfradragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnSamvaersfradragResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatSamvaersfradragBelop()).isEqualTo(100d),

        () -> assertThat(totalBarnebidragResultat.getBeregnKostnadsberegnetBidragResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnKostnadsberegnetBidragResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnKostnadsberegnetBidragResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnKostnadsberegnetBidragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnKostnadsberegnetBidragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnKostnadsberegnetBidragResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatKostnadsberegnetBidragBelop()).isEqualTo(100d),

        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatBarnebidragBelop()).isEqualTo(100d)
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
