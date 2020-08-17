package no.nav.bidrag.beregn.barnebidrag.rest.exception;

public class BidragsevneConsumerException extends RuntimeException {

  public BidragsevneConsumerException(String melding) {
    super(melding);
  }
}
