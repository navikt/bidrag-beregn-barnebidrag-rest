package no.nav.bidrag.beregn.barnebidrag.rest.consumer;

import java.util.Collections;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBidragsevneGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBidragsevneResultat;
import no.nav.bidrag.commons.web.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class BidragsevneConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(BidragsevneConsumer.class);
  private static final ParameterizedTypeReference<BeregnBidragsevneResultat> BIDRAGSEVNE_RESULTAT = new ParameterizedTypeReference<>() {
  };

  private final RestTemplate restTemplate;
  private final String bidragsevneUrl;

  public BidragsevneConsumer(RestTemplate restTemplate, String bidragsevneUrl) {
    this.restTemplate = restTemplate;
    this.bidragsevneUrl = bidragsevneUrl + "/beregn/bidragsevne";
  }

  public HttpResponse<BeregnBidragsevneResultat> hentBidragsevne(BeregnBidragsevneGrunnlag bidragsevneGrunnlag) {

    var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    var request = new HttpEntity<>(bidragsevneGrunnlag, headers);

    var bidragsevneResponse = restTemplate.exchange(bidragsevneUrl, HttpMethod.POST, request, BIDRAGSEVNE_RESULTAT);

    LOGGER.info("hentBidragsevne fikk http status {} fra bidrag-beregn-bidragsevne-rest", bidragsevneResponse.getStatusCode());
    return new HttpResponse<>(bidragsevneResponse);
  }
}
