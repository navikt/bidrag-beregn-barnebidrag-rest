package no.nav.bidrag.beregn.barnebidrag.rest;

import no.nav.bidrag.beregn.barnebidrag.rest.consumer.BidragsevneConsumer;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonConsumer;
import no.nav.bidrag.beregn.nettobarnetilsyn.NettoBarnetilsynCore;
import no.nav.bidrag.beregn.samvaersfradrag.SamvaersfradragCore;
import no.nav.bidrag.beregn.underholdskostnad.UnderholdskostnadCore;
import no.nav.bidrag.commons.ExceptionLogger;
import no.nav.bidrag.commons.web.CorrelationIdFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BidragBeregnBarnebidrag {

  @Bean
  public NettoBarnetilsynCore nettoBarnetilsynCore() {
    return NettoBarnetilsynCore.getInstance();
  }

  @Bean
  public UnderholdskostnadCore underholdskostnadCore() {
    return UnderholdskostnadCore.getInstance();
  }

  @Bean
  public SamvaersfradragCore samvaersfradragCore() {
    return SamvaersfradragCore.getInstance();
  }

  @Bean
  public BidragsevneConsumer bidragsevneConsumer(@Value("${BIDRAGSEVNE_URL}") String bidragsevneBaseUrl, RestTemplate restTemplate) {
    return new BidragsevneConsumer(restTemplate, bidragsevneBaseUrl);
  }

  @Bean
  public SjablonConsumer sjablonConsumer(@Value("${SJABLON_URL}") String sjablonBaseUrl, RestTemplate restTemplate) {
    return new SjablonConsumer(restTemplate, sjablonBaseUrl);
  }

  @Bean
  public ExceptionLogger exceptionLogger() {
    return new ExceptionLogger(BidragBeregnBarnebidrag.class.getSimpleName());
  }

  @Bean
  public CorrelationIdFilter correlationIdFilter() {
    return new CorrelationIdFilter();
  }

  public static void main(String[] args) {
    SpringApplication.run(BidragBeregnBarnebidrag.class, args);
  }
}
