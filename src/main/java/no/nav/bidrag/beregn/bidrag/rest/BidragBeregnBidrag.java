package no.nav.bidrag.beregn.bidrag.rest;

import no.nav.bidrag.beregn.bidrag.rest.consumer.BidragsevneConsumer;
import no.nav.bidrag.commons.ExceptionLogger;
import no.nav.bidrag.commons.web.CorrelationIdFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BidragBeregnBidrag {

  @Bean
  public BidragsevneConsumer bidragsevneConsumer(@Value("${BIDRAGSEVNE_URL}") String bidragsevneBaseUrl, RestTemplate restTemplate) {
    return new BidragsevneConsumer(restTemplate, bidragsevneBaseUrl);
  }

  @Bean
  public ExceptionLogger exceptionLogger() {
    return new ExceptionLogger(BidragBeregnBidrag.class.getSimpleName());
  }

  @Bean
  public CorrelationIdFilter correlationIdFilter() {
    return new CorrelationIdFilter();
  }

  public static void main(String[] args) {
    SpringApplication.run(BidragBeregnBidrag.class, args);
  }

}
