package no.nav.bidrag.beregn.barnebidrag.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.OK;

import java.nio.file.Files;
import java.nio.file.Paths;
import no.nav.bidrag.beregn.barnebidrag.rest.BidragBeregnBarnebidragLocal;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragResultat;
import no.nav.bidrag.commons.web.test.HttpHeaderTestRestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = BidragBeregnBarnebidragLocal.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integrationtest")
@AutoConfigureWireMock(port = 8096)
public class BeregnBarnebidragControllerIntegrationTest {

  @Autowired
  private HttpHeaderTestRestTemplate httpHeaderTestRestTemplate;

  @Autowired
  private SjablonApiStub sjablonApiStub;

  @LocalServerPort
  private int port;

  @Test
  @DisplayName("skal kalle core og returnere en respons")
  void skalKalleCore() {

    // Wiremock mot sjablon-tjenestene
    sjablonApiStub.hentSjablonSjablontallStub();
    sjablonApiStub.hentSjablonForbruksutgifterStub();
    sjablonApiStub.hentSjablonMaksTilsynStub();
    sjablonApiStub.hentSjablonMaksFradragStub();
    sjablonApiStub.hentSjablonSamvaersfradragStub();
    sjablonApiStub.hentSjablonBidragsevneStub();
    sjablonApiStub.hentSjablonTrinnvisSkattesatsStub();
    sjablonApiStub.hentSjablonBarnetilsynStub();

    // Les inn fil med request-data (json)
    String file = "src/test/java/no/nav/bidrag/beregn/barnebidrag/rest/controller/barnebidrag_eksempel1.json";
    String json = "";
    try {
      json = Files.readString(Paths.get(file));
    } catch (Exception e) {
    }

    var url = "http://localhost:" + port + "/bidrag-beregn-barnebidrag-rest/beregn/barnebidrag";
    var request = initHttpEntity(json);

    // Kall rest-API for barnebidrag
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnTotalBarnebidragResultat.class);
    var totalBarnebidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),

        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatBelop().doubleValue()).isEqualTo(3490d),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatKode()).isEqualTo("KOSTNADSBEREGNET_BIDRAG")
        );
  }

  private <T> HttpEntity<T> initHttpEntity(T body) {
    var httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<>(body, httpHeaders);
  }

}
