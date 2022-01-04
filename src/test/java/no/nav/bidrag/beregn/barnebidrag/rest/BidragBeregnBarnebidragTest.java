package no.nav.bidrag.beregn.barnebidrag.rest;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;

@SpringBootApplication
@ComponentScan(excludeFilters = {@Filter(type = ASSIGNABLE_TYPE, value = {BidragBeregnBarnebidrag.class, BidragBeregnBarnebidragLocal.class})})
public class BidragBeregnBarnebidragTest {

  public static void main(String... args) {
    SpringApplication app = new SpringApplication(BidragBeregnBarnebidragTest.class);
    app.run(args);
  }
}
