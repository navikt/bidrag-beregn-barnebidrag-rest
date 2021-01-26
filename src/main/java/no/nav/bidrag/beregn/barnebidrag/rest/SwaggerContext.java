package no.nav.bidrag.beregn.barnebidrag.rest;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerContext {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage(BidragBeregnBarnebidrag.class.getPackage().getName()))
        .paths(or(
            regex("/beregn/barnebidrag" + ".*"),
            regex("/beregn/forholdsmessigfordeling" + ".*")))
        .build();
  }
}
