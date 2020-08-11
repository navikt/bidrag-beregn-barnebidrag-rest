package no.nav.bidrag.beregn.barnebidrag.rest.consumer;

import java.util.List;
import no.nav.bidrag.commons.web.HttpStatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

public class SjablonConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(SjablonConsumer.class);
  private static final ParameterizedTypeReference<List<Sjablontall>> SJABLON_SJABLONTALL_LISTE = new ParameterizedTypeReference<>() {};
  private static final ParameterizedTypeReference<List<Forbruksutgifter>> SJABLON_FORBRUKSUTGIFTER_LISTE = new ParameterizedTypeReference<>() {};
  private static final ParameterizedTypeReference<List<MaksTilsyn>> SJABLON_MAKS_TILSYN_LISTE = new ParameterizedTypeReference<>() {};
  private static final ParameterizedTypeReference<List<MaksFradrag>> SJABLON_MAKS_FRADRAG_LISTE = new ParameterizedTypeReference<>() {};

  private final RestTemplate restTemplate;
  private final String sjablonSjablontallUrl;
  private final String sjablonForbruksutgifterUrl;
  private final String sjablonMaksTilsynUrl;
  private final String sjablonMaksFradragUrl;

  public SjablonConsumer(RestTemplate restTemplate, String sjablonBaseUrl) {
    this.restTemplate = restTemplate;
    this.sjablonSjablontallUrl = sjablonBaseUrl + "/sjablontall/all";
    this.sjablonForbruksutgifterUrl = sjablonBaseUrl + "/forbruksutgifter/all";
    this.sjablonMaksTilsynUrl = sjablonBaseUrl + "/makstilsyn/all";
    this.sjablonMaksFradragUrl = sjablonBaseUrl + "/maksfradrag/all";
  }

  public HttpStatusResponse<List<Sjablontall>> hentSjablonSjablontall() {
    var sjablonResponse = restTemplate.exchange(sjablonSjablontallUrl, HttpMethod.GET, null, SJABLON_SJABLONTALL_LISTE);

    if (sjablonResponse == null) {
      return new HttpStatusResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ukjent feil ved kall av bidrag-sjablon");
    }

    if (!(sjablonResponse.getStatusCode().is2xxSuccessful())) {
      LOGGER.info("Status ({}) for hent sjablon sjablontall: ", sjablonResponse.getStatusCode());
      return new HttpStatusResponse(sjablonResponse.getStatusCode(), sjablonResponse.getHeaders().toString());
    }

    LOGGER.info("Status ({}) for hent sjablon sjablontall: ", sjablonResponse.getStatusCode());
    return new HttpStatusResponse<>(sjablonResponse.getStatusCode(), sjablonResponse.getBody());
  }

  public HttpStatusResponse<List<Forbruksutgifter>> hentSjablonForbruksutgifter() {
    var sjablonResponse = restTemplate.exchange(sjablonForbruksutgifterUrl, HttpMethod.GET, null, SJABLON_FORBRUKSUTGIFTER_LISTE);

    if (sjablonResponse == null) {
      return new HttpStatusResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ukjent feil ved kall av bidrag-sjablon");
    }

    if (!(sjablonResponse.getStatusCode().is2xxSuccessful())) {
      LOGGER.info("Status ({}) for hent sjablon forbruksutgifter: ", sjablonResponse.getStatusCode());
      return new HttpStatusResponse(sjablonResponse.getStatusCode(), sjablonResponse.getHeaders().toString());
    }

    LOGGER.info("Status ({}) for hent sjablon forbruksutgifter: ", sjablonResponse.getStatusCode());
    return new HttpStatusResponse<>(sjablonResponse.getStatusCode(), sjablonResponse.getBody());
  }

  public HttpStatusResponse<List<MaksTilsyn>> hentSjablonMaksTilsyn() {
    var sjablonResponse = restTemplate.exchange(sjablonMaksTilsynUrl, HttpMethod.GET, null, SJABLON_MAKS_TILSYN_LISTE);

    if (sjablonResponse == null) {
      return new HttpStatusResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ukjent feil ved kall av bidrag-sjablon");
    }

    if (!(sjablonResponse.getStatusCode().is2xxSuccessful())) {
      LOGGER.info("Status ({}) for hent sjablon maks tilsyn: ", sjablonResponse.getStatusCode());
      return new HttpStatusResponse(sjablonResponse.getStatusCode(), sjablonResponse.getHeaders().toString());
    }

    LOGGER.info("Status ({}) for hent sjablon maks tilsyn: ", sjablonResponse.getStatusCode());
    return new HttpStatusResponse<>(sjablonResponse.getStatusCode(), sjablonResponse.getBody());
  }

  public HttpStatusResponse<List<MaksFradrag>> hentSjablonMaksFradrag() {
    var sjablonResponse = restTemplate.exchange(sjablonMaksFradragUrl, HttpMethod.GET, null, SJABLON_MAKS_FRADRAG_LISTE);

    if (sjablonResponse == null) {
      return new HttpStatusResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ukjent feil ved kall av bidrag-sjablon");
    }

    if (!(sjablonResponse.getStatusCode().is2xxSuccessful())) {
      LOGGER.info("Status ({}) for hent sjablon maks fradrag: ", sjablonResponse.getStatusCode());
      return new HttpStatusResponse(sjablonResponse.getStatusCode(), sjablonResponse.getHeaders().toString());
    }

    LOGGER.info("Status ({}) for hent sjablon maks fradrag: ", sjablonResponse.getStatusCode());
    return new HttpStatusResponse<>(sjablonResponse.getStatusCode(), sjablonResponse.getBody());
  }
}
