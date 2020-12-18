package no.nav.bidrag.beregn.barnebidrag.rest.controller;

import no.nav.bidrag.beregn.barnebidrag.rest.BidragBeregnBarnebidragLocal;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragResultat;
import no.nav.bidrag.commons.web.test.HttpHeaderTestRestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

@SpringBootTest(classes = BidragBeregnBarnebidragLocal.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class BeregnBarnebidragControllerIntegrationTest {

  @Autowired
  private HttpHeaderTestRestTemplate httpHeaderTestRestTemplate;
  @LocalServerPort
  private int port;

  @Test
  @DisplayName("skal ")
  void skal() {
    var url = "http://localhost:" + port + "/bidrag-beregn-barnebidrag-rest/beregn/barnebidrag";
    var json = "todo: json";
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(json), BeregnTotalBarnebidragResultat.class);
  }
}
