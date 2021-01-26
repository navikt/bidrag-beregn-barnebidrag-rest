package no.nav.bidrag.beregn.barnebidrag.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.http.HttpStatus.OK;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import no.nav.bidrag.beregn.barnebidrag.rest.BidragBeregnBarnebidragLocal;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.wiremock_stub.SjablonApiStub;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnForholdsmessigFordelingResultat;
import no.nav.bidrag.commons.web.test.HttpHeaderTestRestTemplate;
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
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = BidragBeregnBarnebidragLocal.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integrationtest")
@AutoConfigureWireMock(port = 8096)
public class BeregnForholdsmessigFordelingControllerIntegrationTest {

  @Autowired
  private HttpHeaderTestRestTemplate httpHeaderTestRestTemplate;

  @Autowired
  private SjablonApiStub sjablonApiStub;

  @LocalServerPort
  private int port;

  private String url;
  private String filnavn;

  @BeforeEach
  void init() {
    // Bygg opp url
    url = "http://localhost:" + port + "/bidrag-beregn-barnebidrag-rest/beregn/forholdsmessigfordeling";
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 1")
  void skalKalleCoreOgReturnereEtResultat_Eksempel01() {
    // 2 saker med 1 barn i hver sak
    filnavn = "src/test/resources/testfiler/forholdsmessig_fordeling_eksempel1.json";

    var request = lesFilOgByggRequest(filnavn);

    // Kall rest-API for barnebidrag
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnForholdsmessigFordelingResultat.class);
    var forholdsmessigFordelingResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
        () -> assertThat(forholdsmessigFordelingResultat).isNotNull(),

        // Sjekk resultatet av beregningen
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe()).isNotNull(),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().size()).isEqualTo(2),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0)
            .getResultatPerBarnListe()).isNotNull(),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0)
            .getResultatPerBarnListe().size()).isEqualTo(1),

        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0).getSaksnr())
            .isEqualTo(1),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0)
            .getResultatPerBarnListe().get(0).getBarnPersonId()).isEqualTo(1),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0)
            .getResultatPerBarnListe().get(0).getResultatBarnebidragBelop().compareTo(BigDecimal.valueOf(2060))).isZero(),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0)
            .getResultatPerBarnListe().get(0).getResultatkode()).isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET"),

        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(1).getSaksnr())
            .isEqualTo(2),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(1)
            .getResultatPerBarnListe().get(0).getBarnPersonId()).isEqualTo(2),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(1)
            .getResultatPerBarnListe().get(0).getResultatBarnebidragBelop().compareTo(BigDecimal.valueOf(3600))).isZero(),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(1)
            .getResultatPerBarnListe().get(0).getResultatkode()).isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET")
    );
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 2")
  void skalKalleCoreOgReturnereEtResultat_Eksempel02() {
    // 3 saker med 1 barn i hver sak
    filnavn = "src/test/resources/testfiler/forholdsmessig_fordeling_eksempel2.json";

    var request = lesFilOgByggRequest(filnavn);

    // Kall rest-API for barnebidrag
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnForholdsmessigFordelingResultat.class);
    var forholdsmessigFordelingResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
        () -> assertThat(forholdsmessigFordelingResultat).isNotNull(),

        // Sjekk resultatet av beregningen
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe()).isNotNull(),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().size()).isEqualTo(3),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0)
            .getResultatPerBarnListe()).isNotNull(),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0)
            .getResultatPerBarnListe().size()).isEqualTo(1),

        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0).getSaksnr())
            .isEqualTo(1),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0)
            .getResultatPerBarnListe().get(0).getBarnPersonId()).isEqualTo(1),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0)
            .getResultatPerBarnListe().get(0).getResultatBarnebidragBelop().compareTo(BigDecimal.valueOf(1460))).isZero(),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(0)
            .getResultatPerBarnListe().get(0).getResultatkode()).isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET"),

        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(1).getSaksnr())
            .isEqualTo(2),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(1)
            .getResultatPerBarnListe().get(0).getBarnPersonId()).isEqualTo(2),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(1)
            .getResultatPerBarnListe().get(0).getResultatBarnebidragBelop().compareTo(BigDecimal.valueOf(2550))).isZero(),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(1)
            .getResultatPerBarnListe().get(0).getResultatkode()).isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET"),

        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(2).getSaksnr())
            .isEqualTo(3),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(2)
            .getResultatPerBarnListe().get(0).getBarnPersonId()).isEqualTo(3),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(2)
            .getResultatPerBarnListe().get(0).getResultatBarnebidragBelop().compareTo(BigDecimal.valueOf(1640))).isZero(),
        () -> assertThat(forholdsmessigFordelingResultat.getResultatPeriodeListe().get(0).getResultatBeregningListe().get(2)
            .getResultatPerBarnListe().get(0).getResultatkode()).isEqualTo("FORHOLDSMESSIG_FORDELING_BIDRAGSBELOP_ENDRET")
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
