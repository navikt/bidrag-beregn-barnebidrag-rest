package no.nav.bidrag.beregn.barnebidrag.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.http.HttpStatus.OK;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import no.nav.bidrag.beregn.barnebidrag.rest.BidragBeregnBarnebidragTest;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnetForholdsmessigFordelingResultat;
import no.nav.bidrag.commons.web.test.HttpHeaderTestRestTemplate;
import no.nav.security.token.support.spring.test.EnableMockOAuth2Server;
import org.junit.jupiter.api.BeforeEach;
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

@SpringBootTest(classes = BidragBeregnBarnebidragTest.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8096)
@EnableMockOAuth2Server
class BeregnForholdsmessigFordelingControllerIntegrationTest {

  @Autowired
  private HttpHeaderTestRestTemplate httpHeaderTestRestTemplate;

  @LocalServerPort
  private int port;

  private String url;
  private String filnavn;

  @BeforeEach
  void init() {
    // Bygg opp url
    url = "http://localhost:" + port + "/beregn/forholdsmessigfordeling";
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 1")
  void skalKalleCoreOgReturnereEtResultat_Eksempel01() {
    // 2 saker med 1 barn i hver sak
    filnavn = "src/test/resources/testfiler/forholdsmessig_fordeling_eksempel1.json";

    var request = lesFilOgByggRequest(filnavn);

    // Kall rest-API for barnebidrag
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnetForholdsmessigFordelingResultat.class);
    var forholdsmessigFordelingResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
        () -> assertThat(forholdsmessigFordelingResultat).isNotNull(),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe()).isNotNull(),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe()).hasSize(2),

        // Sjekk resultatet av beregningen
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(0).getSakNr()).isEqualTo(1),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(0).getBarn()).isEqualTo(1),
        () -> assertThat(
            forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(0).getResultat().getBelop()).isEqualByComparingTo(
            BigDecimal.valueOf(2060)),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(0).getResultat().getKode())
            .isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET"),

        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(1).getSakNr()).isEqualTo(2),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(1).getBarn()).isEqualTo(2),
        () -> assertThat(
            forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(1).getResultat().getBelop()).isEqualByComparingTo(
            BigDecimal.valueOf(3600)),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(1).getResultat().getKode())
            .isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET")
    );
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 2")
  void skalKalleCoreOgReturnereEtResultat_Eksempel02() {
    // 3 saker med 1 barn i hver sak
    filnavn = "src/test/resources/testfiler/forholdsmessig_fordeling_eksempel2.json";

    var request = lesFilOgByggRequest(filnavn);

    // Kall rest-API for barnebidrag
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnetForholdsmessigFordelingResultat.class);
    var forholdsmessigFordelingResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
        () -> assertThat(forholdsmessigFordelingResultat).isNotNull(),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe()).isNotNull(),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe()).hasSize(3),

        // Sjekk resultatet av beregningen
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(0).getSakNr()).isEqualTo(1),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(0).getBarn()).isEqualTo(1),
        () -> assertThat(
            forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(0).getResultat().getBelop()).isEqualByComparingTo(
            BigDecimal.valueOf(1460)),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(0).getResultat().getKode())
            .isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET"),

        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(1).getSakNr()).isEqualTo(2),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(1).getBarn()).isEqualTo(2),
        () -> assertThat(
            forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(1).getResultat().getBelop()).isEqualByComparingTo(
            BigDecimal.valueOf(2550)),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(1).getResultat().getKode())
            .isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET"),

        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(2).getSakNr()).isEqualTo(3),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(2).getBarn()).isEqualTo(3),
        () -> assertThat(
            forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(2).getResultat().getBelop()).isEqualByComparingTo(
            BigDecimal.valueOf(1640)),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(2).getResultat().getKode())
            .isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET")
    );
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 3")
  void skalKalleCoreOgReturnereEtResultat_Eksempel03() {
    // 6 saker med 1 barn i hver sak
    filnavn = "src/test/resources/testfiler/forholdsmessig_fordeling_eksempel3.json";

    var request = lesFilOgByggRequest(filnavn);

    // Kall rest-API for barnebidrag
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnetForholdsmessigFordelingResultat.class);
    var forholdsmessigFordelingResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
        () -> assertThat(forholdsmessigFordelingResultat).isNotNull(),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe()).isNotNull(),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe()).hasSize(6),

        // Sjekk resultatet av beregningen
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(0).getSakNr()).isEqualTo(1),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(0).getBarn()).isEqualTo(1),
        () -> assertThat(
            forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(0).getResultat().getBelop()).isEqualByComparingTo(
            BigDecimal.valueOf(1340)),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(0).getResultat().getKode())
            .isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET"),

        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(1).getSakNr()).isEqualTo(2),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(1).getBarn()).isEqualTo(2),
        () -> assertThat(
            forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(1).getResultat().getBelop()).isEqualByComparingTo(
            BigDecimal.valueOf(2180)),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(1).getResultat().getKode())
            .isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET"),

        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(2).getSakNr()).isEqualTo(3),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(2).getBarn()).isEqualTo(3),
        () -> assertThat(
            forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(2).getResultat().getBelop()).isEqualByComparingTo(
            BigDecimal.valueOf(1940)),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(2).getResultat().getKode())
            .isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET"),

        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(3).getSakNr()).isEqualTo(4),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(3).getBarn()).isEqualTo(4),
        () -> assertThat(
            forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(3).getResultat().getBelop()).isEqualByComparingTo(
            BigDecimal.valueOf(1940)),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(3).getResultat().getKode())
            .isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET"),

        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(4).getSakNr()).isEqualTo(5),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(4).getBarn()).isEqualTo(5),
        () -> assertThat(
            forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(4).getResultat().getBelop()).isEqualByComparingTo(
            BigDecimal.valueOf(1670)),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(4).getResultat().getKode())
            .isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET"),

        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(5).getSakNr()).isEqualTo(6),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(5).getBarn()).isEqualTo(6),
        () -> assertThat(
            forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(5).getResultat().getBelop()).isEqualByComparingTo(
            BigDecimal.valueOf(1340)),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(5).getResultat().getKode())
            .isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET")
    );
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 4")
  void skalKalleCoreOgReturnereEtResultat_Eksempel04() {
    // 3 saker med hhv. 1, 2 og 3 barn i hver sak
    filnavn = "src/test/resources/testfiler/forholdsmessig_fordeling_eksempel4.json";

    var request = lesFilOgByggRequest(filnavn);

    // Kall rest-API for barnebidrag
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnetForholdsmessigFordelingResultat.class);
    var forholdsmessigFordelingResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
        () -> assertThat(forholdsmessigFordelingResultat).isNotNull(),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe()).isNotNull(),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe()).hasSize(6),

        // Sjekk resultatet av beregningen
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(0).getSakNr()).isEqualTo(1),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(0).getBarn()).isEqualTo(1),
        () -> assertThat(
            forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(0).getResultat().getBelop()).isEqualByComparingTo(
            BigDecimal.valueOf(1630)),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(0).getResultat().getKode())
            .isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET"),

        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(1).getSakNr()).isEqualTo(2),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(1).getBarn()).isEqualTo(2),
        () -> assertThat(
            forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(1).getResultat().getBelop()).isEqualByComparingTo(
            BigDecimal.valueOf(2220)),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(1).getResultat().getKode())
            .isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET"),

        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(2).getSakNr()).isEqualTo(2),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(2).getBarn()).isEqualTo(3),
        () -> assertThat(
            forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(2).getResultat().getBelop()).isEqualByComparingTo(
            BigDecimal.valueOf(1980)),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(2).getResultat().getKode())
            .isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET"),

        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(3).getSakNr()).isEqualTo(3),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(3).getBarn()).isEqualTo(4),
        () -> assertThat(
            forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(3).getResultat().getBelop()).isEqualByComparingTo(
            BigDecimal.valueOf(1800)),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(3).getResultat().getKode())
            .isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET"),

        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(4).getSakNr()).isEqualTo(3),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(4).getBarn()).isEqualTo(5),
        () -> assertThat(
            forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(4).getResultat().getBelop()).isEqualByComparingTo(
            BigDecimal.valueOf(1550)),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(4).getResultat().getKode())
            .isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET"),

        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(5).getSakNr()).isEqualTo(3),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(5).getBarn()).isEqualTo(6),
        () -> assertThat(
            forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(5).getResultat().getBelop()).isEqualByComparingTo(
            BigDecimal.valueOf(1240)),
        () -> assertThat(forholdsmessigFordelingResultat.getBeregnetForholdsmessigFordelingPeriodeListe().get(5).getResultat().getKode())
            .isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET")
    );
  }

  private HttpEntity<String> lesFilOgByggRequest(String filnavn) {
    var json = "";

    // Les inn fil med request-data (json)
    try {
      json = Files.readString(Paths.get(filnavn));
    } catch (Exception e) {
      fail("Klarte ikke å lese fil: " + filnavn);
    }

    // Lag request
    return initHttpEntity(json);
  }

  private <T> HttpEntity<T> initHttpEntity(T body) {
    var httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<>(body, httpHeaders);
  }

}
