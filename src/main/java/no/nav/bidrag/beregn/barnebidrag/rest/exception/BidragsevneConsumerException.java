package no.nav.bidrag.beregn.barnebidrag.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientResponseException;

public class BidragsevneConsumerException extends RuntimeException {

  private HttpStatus statusCode;
  private String meldingstekst;

  public HttpStatus getStatusCode() {
    return statusCode;
  }

  public String getMeldingstekst() {
    return meldingstekst;
  }

  public BidragsevneConsumerException(RestClientResponseException exception, String meldingstekst) {
    super(exception);
    this.statusCode = HttpStatus.valueOf(exception.getRawStatusCode());
    this.meldingstekst = meldingstekst;
  }

}
