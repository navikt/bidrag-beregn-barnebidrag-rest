package no.nav.bidrag.beregn.barnebidrag.rest;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

import no.nav.bidrag.beregn.barnebidrag.rest.consumer.wiremock_stub.SjablonApiStub;
import no.nav.security.token.support.spring.api.EnableJwtTokenValidation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
@EnableJwtTokenValidation(ignore = {"org.springdoc", "org.springframework"})
@ComponentScan(excludeFilters = {@Filter(type = ASSIGNABLE_TYPE, value = BidragBeregnBarnebidrag.class)})
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
