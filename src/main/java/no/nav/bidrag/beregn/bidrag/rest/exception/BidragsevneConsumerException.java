package no.nav.bidrag.beregn.bidrag.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BidragsevneConsumerException extends RuntimeException {

  public BidragsevneConsumerException(String melding) {
    super(melding);
  }
}