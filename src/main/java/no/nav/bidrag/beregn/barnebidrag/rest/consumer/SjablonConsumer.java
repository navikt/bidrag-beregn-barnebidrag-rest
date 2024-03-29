package no.nav.bidrag.beregn.barnebidrag.rest.consumer;

import java.util.List;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.SjablonConsumerException;
import no.nav.bidrag.commons.web.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class SjablonConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(SjablonConsumer.class);
  private static final ParameterizedTypeReference<List<Sjablontall>> SJABLON_SJABLONTALL_LISTE = new ParameterizedTypeReference<>() {
  };
  private static final ParameterizedTypeReference<List<Forbruksutgifter>> SJABLON_FORBRUKSUTGIFTER_LISTE = new ParameterizedTypeReference<>() {
  };
  private static final ParameterizedTypeReference<List<MaksTilsyn>> SJABLON_MAKS_TILSYN_LISTE = new ParameterizedTypeReference<>() {
  };
  private static final ParameterizedTypeReference<List<MaksFradrag>> SJABLON_MAKS_FRADRAG_LISTE = new ParameterizedTypeReference<>() {
  };
  private static final ParameterizedTypeReference<List<Samvaersfradrag>> SJABLON_SAMVAERSFRADRAG_LISTE = new ParameterizedTypeReference<>() {
  };
  private static final ParameterizedTypeReference<List<Bidragsevne>> SJABLON_BIDRAGSEVNE_LISTE = new ParameterizedTypeReference<>() {
  };
  private static final ParameterizedTypeReference<List<TrinnvisSkattesats>> SJABLON_TRINNVIS_SKATTESATS_LISTE = new ParameterizedTypeReference<>() {
  };
  private static final ParameterizedTypeReference<List<Barnetilsyn>> SJABLON_BARNETILSYN_LISTE = new ParameterizedTypeReference<>() {
  };

  private final RestTemplate restTemplate;
  private final String sjablonSjablontallUrl;
  private final String sjablonForbruksutgifterUrl;
  private final String sjablonMaksTilsynUrl;
  private final String sjablonMaksFradragUrl;
  private final String sjablonSamvaersfradragUrl;
  private final String sjablonBidragsevneUrl;
  private final String sjablonTrinnvisSkattesatsUrl;
  private final String sjablonBarnetilsynUrl;

  public SjablonConsumer(RestTemplate restTemplate, String sjablonBaseUrl) {
    this.restTemplate = restTemplate;
    this.sjablonSjablontallUrl = sjablonBaseUrl + "/bidrag-sjablon/sjablontall/all";
    this.sjablonForbruksutgifterUrl = sjablonBaseUrl + "/bidrag-sjablon/forbruksutgifter/all";
    this.sjablonMaksTilsynUrl = sjablonBaseUrl + "/bidrag-sjablon/makstilsyn/all";
    this.sjablonMaksFradragUrl = sjablonBaseUrl + "/bidrag-sjablon/maksfradrag/all";
    this.sjablonSamvaersfradragUrl = sjablonBaseUrl + "/bidrag-sjablon/samvaersfradrag/all";
    this.sjablonBidragsevneUrl = sjablonBaseUrl + "/bidrag-sjablon/bidragsevner/all";
    this.sjablonTrinnvisSkattesatsUrl = sjablonBaseUrl + "/bidrag-sjablon/trinnvisskattesats/all";
    this.sjablonBarnetilsynUrl = sjablonBaseUrl + "/bidrag-sjablon/barnetilsyn/all";
  }

  public HttpResponse<List<Sjablontall>> hentSjablonSjablontall() {
    try {
      var sjablonResponse = restTemplate.exchange(sjablonSjablontallUrl, HttpMethod.GET, null, SJABLON_SJABLONTALL_LISTE);
      LOGGER.info("hentSjablonSjablontall fikk http status {} fra bidrag-sjablon", sjablonResponse.getStatusCode());
      return new HttpResponse<>(sjablonResponse);
    } catch (RestClientResponseException exception) {
      LOGGER.error("hentSjablonSjablontall fikk følgende feilkode fra bidrag-sjablon: {}, med melding {}", exception.getStatusText(),
          exception.getMessage());
      throw new SjablonConsumerException(exception);
    }
  }

  public HttpResponse<List<Forbruksutgifter>> hentSjablonForbruksutgifter() {
    try {
      var sjablonResponse = restTemplate.exchange(sjablonForbruksutgifterUrl, HttpMethod.GET, null, SJABLON_FORBRUKSUTGIFTER_LISTE);
      LOGGER.info("hentSjablonForbruksutgifter fikk http status {} fra bidrag-sjablon", sjablonResponse.getStatusCode());
      return new HttpResponse<>(sjablonResponse);
    } catch (RestClientResponseException exception) {
      LOGGER.error("hentSjablonForbruksutgifter fikk følgende feilkode fra bidrag-sjablon: {}, med melding {}", exception.getStatusText(),
          exception.getMessage());
      throw new SjablonConsumerException(exception);
    }
  }

  public HttpResponse<List<MaksTilsyn>> hentSjablonMaksTilsyn() {
    try {
      var sjablonResponse = restTemplate.exchange(sjablonMaksTilsynUrl, HttpMethod.GET, null, SJABLON_MAKS_TILSYN_LISTE);
      LOGGER.info("hentSjablonMaksTilsyn fikk http status {} fra bidrag-sjablon", sjablonResponse.getStatusCode());
      return new HttpResponse<>(sjablonResponse);
    } catch (RestClientResponseException exception) {
      LOGGER.error("hentSjablonMaksTilsyn fikk følgende feilkode fra bidrag-sjablon: {}, med melding {}", exception.getStatusText(),
          exception.getMessage());
      throw new SjablonConsumerException(exception);
    }
  }

  public HttpResponse<List<MaksFradrag>> hentSjablonMaksFradrag() {
    try {
      var sjablonResponse = restTemplate.exchange(sjablonMaksFradragUrl, HttpMethod.GET, null, SJABLON_MAKS_FRADRAG_LISTE);
      LOGGER.info("hentSjablonMaksFradrag fikk http status {} fra bidrag-sjablon", sjablonResponse.getStatusCode());
      return new HttpResponse<>(sjablonResponse);
    } catch (RestClientResponseException exception) {
      LOGGER.error("hentSjablonMaksFradrag fikk følgende feilkode fra bidrag-sjablon: {}, med melding {}", exception.getStatusText(),
          exception.getMessage());
      throw new SjablonConsumerException(exception);
    }
  }

  public HttpResponse<List<Samvaersfradrag>> hentSjablonSamvaersfradrag() {
    try {
      var sjablonResponse = restTemplate.exchange(sjablonSamvaersfradragUrl, HttpMethod.GET, null, SJABLON_SAMVAERSFRADRAG_LISTE);
      LOGGER.info("hentSjablonSamvaersfradrag fikk http status {} fra bidrag-sjablon", sjablonResponse.getStatusCode());
      return new HttpResponse<>(sjablonResponse);
    } catch (RestClientResponseException exception) {
      LOGGER.error("hentSjablonSamvaersfradrag fikk følgende feilkode fra bidrag-sjablon: {}, med melding {}", exception.getStatusText(),
          exception.getMessage());
      throw new SjablonConsumerException(exception);
    }
  }

  public HttpResponse<List<Bidragsevne>> hentSjablonBidragsevne() {

    try {
      var sjablonResponse = restTemplate.exchange(sjablonBidragsevneUrl, HttpMethod.GET, null, SJABLON_BIDRAGSEVNE_LISTE);
      LOGGER.info("hentSjablonBidragsevne fikk http status {} fra bidrag-sjablon", sjablonResponse.getStatusCode());
      return new HttpResponse<>(sjablonResponse);
    } catch (RestClientResponseException exception) {
      LOGGER.error("hentSjablonBidragsevne fikk følgende feilkode fra bidrag-sjablon: {}, med melding {}", exception.getStatusText(),
          exception.getMessage());
      throw new SjablonConsumerException(exception);
    }
  }

  public HttpResponse<List<TrinnvisSkattesats>> hentSjablonTrinnvisSkattesats() {

    try {
      var sjablonResponse = restTemplate.exchange(sjablonTrinnvisSkattesatsUrl, HttpMethod.GET, null, SJABLON_TRINNVIS_SKATTESATS_LISTE);
      LOGGER.info("hentSjablonTrinnvisSkattesats fikk http status {} fra bidrag-sjablon", sjablonResponse.getStatusCode());
      return new HttpResponse<>(sjablonResponse);
    } catch (RestClientResponseException exception) {
      LOGGER.error("hentSjablonTrinnvisSkattesats fikk følgende feilkode fra bidrag-sjablon: {}, med melding {}", exception.getStatusText(),
          exception.getMessage());
      throw new SjablonConsumerException(exception);
    }
  }

  public HttpResponse<List<Barnetilsyn>> hentSjablonBarnetilsyn() {

    try {
      var sjablonResponse = restTemplate.exchange(sjablonBarnetilsynUrl, HttpMethod.GET, null, SJABLON_BARNETILSYN_LISTE);
      LOGGER.info("hentSjablonBarnetilsyn fikk http status {} fra bidrag-sjablon", sjablonResponse.getStatusCode());
      return new HttpResponse<>(sjablonResponse);
    } catch (RestClientResponseException exception) {
      LOGGER.error("hentSjablonBarnetilsyn fikk følgende feilkode fra bidrag-sjablon: {}, med melding {}", exception.getStatusText(),
          exception.getMessage());
      throw new SjablonConsumerException(exception);
    }
  }
}
