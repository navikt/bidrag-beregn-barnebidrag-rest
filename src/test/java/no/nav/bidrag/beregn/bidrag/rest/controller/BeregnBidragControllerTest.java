package no.nav.bidrag.beregn.bidrag.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

import java.time.LocalDate;
import no.nav.bidrag.beregn.bidrag.rest.BidragBeregnBidragLocal;
import no.nav.bidrag.beregn.bidrag.rest.TestUtil;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BeregnBidragGrunnlag;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BeregnBidragResultat;
import no.nav.bidrag.beregn.bidrag.rest.service.BeregnBidragService;
import no.nav.bidrag.commons.web.HttpStatusResponse;
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

@DisplayName("BeregnBidragControllerTest")
@SpringBootTest(classes = BidragBeregnBidragLocal.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class BeregnBidragControllerTest {

  @Autowired
  private HttpHeaderTestRestTemplate httpHeaderTestRestTemplate;
  @LocalServerPort
  private int port;
  @MockBean
  private BeregnBidragService beregnBidragServiceMock;

  @BeforeEach
  void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("Skal returnere bidrag resultat ved gyldig input")
  void skalReturnereBidragResultatVedGyldigInput() {

    when(beregnBidragServiceMock.beregn(any(BeregnBidragGrunnlag.class))).thenReturn(new HttpStatusResponse(OK,
        new BeregnBidragResultat(TestUtil.dummyBidragsevneResultat(), TestUtil.dummyUnderholdskostnadResultat(), "Resten av resultatet")));

    var url = "http://localhost:" + port + "/bidrag-beregn-bidrag-rest/beregn/bidrag";
    var request = initHttpEntity(TestUtil.byggBidragGrunnlag());
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnBidragResultat.class);
    var bidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
        () -> assertThat(bidragResultat).isNotNull(),

        () -> assertThat(bidragResultat.getBeregnBidragsevneResultat()).isNotNull(),
        () -> assertThat(bidragResultat.getBeregnBidragsevneResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(bidragResultat.getBeregnBidragsevneResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(bidragResultat.getBeregnBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(bidragResultat.getBeregnBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(bidragResultat.getBeregnBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatBeregning().getResultatEvne())
            .isEqualTo(100d),

        () -> assertThat(bidragResultat.getBeregnUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(bidragResultat.getBeregnUnderholdskostnadResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(bidragResultat.getBeregnUnderholdskostnadResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(
            bidragResultat.getBeregnUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2017-01-01")),
        () -> assertThat(
            bidragResultat.getBeregnUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil().getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2019-01-01")),
        () -> assertThat(bidragResultat.getBeregnUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatBelopUnderholdskostnad()).isEqualTo(100d),

        () -> assertThat(bidragResultat.getRestenAvResultatet()).isNotNull(),
        () -> assertThat(bidragResultat.getRestenAvResultatet()).isEqualTo("Resten av resultatet")
    );
  }

  @Test
  @DisplayName("Skal returnere 400 Bad Request når input data mangler")
  void skalReturnere400BadRequestNårInputDataMangler() {

    when(beregnBidragServiceMock.beregn(any(BeregnBidragGrunnlag.class))).thenReturn(new HttpStatusResponse(BAD_REQUEST, null));

    var url = "http://localhost:" + port + "/bidrag-beregn-bidrag-rest/beregn/bidrag";
    var request = initHttpEntity(TestUtil.byggBidragGrunnlag());
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnBidragResultat.class);
    var bidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST),
        () -> assertThat(bidragResultat).isNull()
    );
  }

  @Test
  @DisplayName("Skal returnere 500 Internal Server Error når kall til servicen feiler")
  void skalReturnere500InternalServerErrorNårKallTilServicenFeiler() {

    when(beregnBidragServiceMock.beregn(any(BeregnBidragGrunnlag.class))).thenReturn(new HttpStatusResponse(INTERNAL_SERVER_ERROR, null));

    var url = "http://localhost:" + port + "/bidrag-beregn-bidrag-rest/beregn/bidrag";
    var request = initHttpEntity(TestUtil.byggBidragGrunnlag());
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnBidragResultat.class);
    var bidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR),
        () -> assertThat(bidragResultat).isNull()
    );
  }

  private <T> HttpEntity<T> initHttpEntity(T body) {
    var httpHeaders = new HttpHeaders();
    return new HttpEntity<>(body, httpHeaders);
  }
}
