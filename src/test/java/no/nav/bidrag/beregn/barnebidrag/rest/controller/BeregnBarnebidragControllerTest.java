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
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBarnebidragResultat;
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
  @DisplayName("Skal returnere barnebidrag resultat ved gyldig input")
  void skalReturnereBarnebidragResultatVedGyldigInput() {

    when(beregnBarnebidragServiceMock.beregn(any(BeregnBarnebidragGrunnlag.class))).thenReturn(HttpResponse.from(OK,
        new BeregnBarnebidragResultat(TestUtil.dummyBidragsevneResultat(), TestUtil.dummyUnderholdskostnadResultat(),
            TestUtil.dummyNettoBarnetilsynResultat(), "Resten av resultatet")));

    var url = "http://localhost:" + port + "/bidrag-beregn-barnebidrag-rest/beregn/barnebidrag";
    var request = initHttpEntity(TestUtil.byggBarnebidragGrunnlag());
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnBarnebidragResultat.class);
    var barnebidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
        () -> assertThat(barnebidragResultat).isNotNull(),

        () -> assertThat(barnebidragResultat.getBeregnBidragsevneResultat()).isNotNull(),
        () -> assertThat(barnebidragResultat.getBeregnBidragsevneResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(barnebidragResultat.getBeregnBidragsevneResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(
            barnebidragResultat.getBeregnBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(
            barnebidragResultat.getBeregnBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(barnebidragResultat.getBeregnBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatBeregning().getResultatEvne())
            .isEqualTo(100d),

        () -> assertThat(barnebidragResultat.getBeregnNettoBarnetilsynResultat()).isNotNull(),
        () -> assertThat(barnebidragResultat.getBeregnNettoBarnetilsynResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(barnebidragResultat.getBeregnNettoBarnetilsynResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(
            barnebidragResultat.getBeregnNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(
            barnebidragResultat.getBeregnNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(barnebidragResultat.getBeregnNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe().size())
            .isEqualTo(1),
        () -> assertThat(barnebidragResultat.getBeregnNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0)
            .getResultatBelopNettoBarnetilsyn()).isEqualTo(100d),

        () -> assertThat(barnebidragResultat.getBeregnUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(barnebidragResultat.getBeregnUnderholdskostnadResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(barnebidragResultat.getBeregnUnderholdskostnadResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(
            barnebidragResultat.getBeregnUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(
            barnebidragResultat.getBeregnUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(barnebidragResultat.getBeregnUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatBelopUnderholdskostnad()).isEqualTo(100d),

        () -> assertThat(barnebidragResultat.getRestenAvResultatet()).isNotNull(),
        () -> assertThat(barnebidragResultat.getRestenAvResultatet()).isEqualTo("Resten av resultatet")
    );
  }

  @Test
  @DisplayName("Skal returnere 400 Bad Request når input data mangler")
  void skalReturnere400BadRequestNårInputDataMangler() {

    when(beregnBarnebidragServiceMock.beregn(any(BeregnBarnebidragGrunnlag.class))).thenReturn(HttpResponse.from(BAD_REQUEST, null));

    var url = "http://localhost:" + port + "/bidrag-beregn-barnebidrag-rest/beregn/barnebidrag";
    var request = initHttpEntity(TestUtil.byggBarnebidragGrunnlag());
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnBarnebidragResultat.class);
    var barnebidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST),
        () -> assertThat(barnebidragResultat).isNull()
    );
  }

  @Test
  @DisplayName("Skal returnere 500 Internal Server Error når kall til servicen feiler")
  void skalReturnere500InternalServerErrorNårKallTilServicenFeiler() {

    when(beregnBarnebidragServiceMock.beregn(any(BeregnBarnebidragGrunnlag.class))).thenReturn(HttpResponse.from(INTERNAL_SERVER_ERROR, null));

    var url = "http://localhost:" + port + "/bidrag-beregn-barnebidrag-rest/beregn/barnebidrag";
    var request = initHttpEntity(TestUtil.byggBarnebidragGrunnlag());
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnBarnebidragResultat.class);
    var barnebidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR),
        () -> assertThat(barnebidragResultat).isNull()
    );
  }

  private <T> HttpEntity<T> initHttpEntity(T body) {
    var httpHeaders = new HttpHeaders();
    return new HttpEntity<>(body, httpHeaders);
  }
}
