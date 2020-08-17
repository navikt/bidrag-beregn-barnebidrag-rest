package no.nav.bidrag.beregn.barnebidrag.rest.exception;

public class UgyldigInputException extends RuntimeException {

  public UgyldigInputException(String melding) {
    super(melding);
  }
}
