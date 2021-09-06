package no.nav.bidrag.beregn.barnebidrag.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import no.nav.bidrag.beregn.barnebidrag.rest.BidragBeregnBarnebidragLocal;
import no.nav.bidrag.beregn.barnebidrag.rest.TestUtil;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnGrunnlagFF;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnetForholdsmessigFordelingResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnetTotalBarnebidragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.service.BeregnBarnebidragService;
import no.nav.bidrag.beregn.barnebidrag.rest.service.BeregnForholdsmessigFordelingService;
import no.nav.bidrag.commons.web.HttpResponse;
import no.nav.bidrag.commons.web.test.HttpHeaderTestRestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
  @DisplayName("Skal returnere 400 Bad Request når input data mangler - barnebidrag")
  void skalReturnere400BadRequestNaarInputDataManglerBarnebidrag() {

    when(beregnBarnebidragServiceMock.beregn(any(BeregnGrunnlag.class))).thenReturn(HttpResponse.from(BAD_REQUEST));

    var url = "http://localhost:" + port + "/bidrag-beregn-barnebidrag-rest/beregn/barnebidrag";
    var request = initHttpEntity(TestUtil.byggBarnebidragGrunnlag());
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnetTotalBarnebidragResultat.class);
    var totalBarnebidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST),
        () -> assertThat(totalBarnebidragResultat).isNull()
    );
  }

  @Test
  @DisplayName("Skal returnere 500 Internal Server Error når kall til servicen feiler - barnebidrag")
  void skalReturnere500InternalServerErrorNaarKallTilServicenFeilerBarnebidrag() {

    when(beregnBarnebidragServiceMock.beregn(any(BeregnGrunnlag.class))).thenReturn(HttpResponse.from(INTERNAL_SERVER_ERROR));

    var url = "http://localhost:" + port + "/bidrag-beregn-barnebidrag-rest/beregn/barnebidrag";
    var request = initHttpEntity(TestUtil.byggBarnebidragGrunnlag());
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnetTotalBarnebidragResultat.class);
    var totalBarnebidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR),
        () -> assertThat(totalBarnebidragResultat).isNull()
    );
  }

  @Test
  @DisplayName("Skal returnere 400 Bad Request når input data mangler - forholdsmessig fordeling")
  void skalReturnere400BadRequestNaarInputDataManglerForholdsmessigFordeling() {

    when(beregnForholdsmessigFordelingServiceMock.beregn(any(BeregnGrunnlagFF.class))).thenReturn(HttpResponse.from(BAD_REQUEST));

    var url = "http://localhost:" + port + "/bidrag-beregn-barnebidrag-rest/beregn/forholdsmessigfordeling";
    var request = initHttpEntity(TestUtil.byggForholdsmessigFordelingGrunnlag());
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnetForholdsmessigFordelingResultat.class);
    var forholdsmessigFordelingResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST),
        () -> assertThat(forholdsmessigFordelingResultat).isNull()
    );
  }

  @Test
  @DisplayName("Skal returnere 500 Internal Server Error når kall til servicen feiler - forholdsmessig fordeling")
  void skalReturnere500InternalServerErrorNaarKallTilServicenFeilerForholdsmessigFordeling() {

    when(beregnForholdsmessigFordelingServiceMock.beregn(any(BeregnGrunnlagFF.class))).thenReturn(HttpResponse.from(INTERNAL_SERVER_ERROR));

    var url = "http://localhost:" + port + "/bidrag-beregn-barnebidrag-rest/beregn/forholdsmessigfordeling";
    var request = initHttpEntity(TestUtil.byggForholdsmessigFordelingGrunnlag());
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnetForholdsmessigFordelingResultat.class);
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
