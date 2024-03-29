package no.nav.bidrag.beregn.barnebidrag.rest;

import no.nav.bidrag.commons.web.test.HttpHeaderTestRestTemplate;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BidragBeregnBarnebidragTestConfig {

  @Bean
  HttpHeaderTestRestTemplate httpHeaderTestRestTemplate() {
    TestRestTemplate testRestTemplate = new TestRestTemplate(new RestTemplateBuilder());
    return new HttpHeaderTestRestTemplate(testRestTemplate);
  }
}
