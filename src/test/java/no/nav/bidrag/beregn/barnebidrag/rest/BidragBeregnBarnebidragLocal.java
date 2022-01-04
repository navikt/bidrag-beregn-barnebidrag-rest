package no.nav.bidrag.beregn.barnebidrag.rest;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

import no.nav.bidrag.beregn.barnebidrag.rest.consumer.wiremock_stub.SjablonApiStub;
import no.nav.security.token.support.spring.api.EnableJwtTokenValidation;
import no.nav.security.token.support.spring.test.EnableMockOAuth2Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;

@SpringBootApplication
@EnableMockOAuth2Server
@EnableJwtTokenValidation(ignore = {"org.springdoc", "org.springframework"})
@ComponentScan(
    excludeFilters = {@Filter(type = ASSIGNABLE_TYPE, value = BidragBeregnBarnebidrag.class)})
public class BidragBeregnBarnebidragLocal {

  public static final String LOCAL =
      "local"; // Enable endpoint testing with Swagger locally, see application.yaml

  public static void main(String... args) {
    SpringApplication app = new SpringApplication(BidragBeregnBarnebidragLocal.class);
    app.setAdditionalProfiles(LOCAL);
    ConfigurableApplicationContext context = app.run(args);

    context.getBean(SjablonApiStub.class).settOppSjablonStub();
  }
}
