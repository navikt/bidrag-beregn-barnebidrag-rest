package no.nav.bidrag.beregn.barnebidrag.rest;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import no.nav.security.token.support.spring.api.EnableJwtTokenValidation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJwtTokenValidation(ignore = {"org.springframework", "org.springdoc"})
@SecurityScheme(
    bearerFormat = "JWT",
    name = "bearer-key",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP
)
public class BidragBeregnBarnebidrag {

  public static void main(String[] args) {
    SpringApplication.run(BidragBeregnBarnebidrag.class, args);
  }
}
