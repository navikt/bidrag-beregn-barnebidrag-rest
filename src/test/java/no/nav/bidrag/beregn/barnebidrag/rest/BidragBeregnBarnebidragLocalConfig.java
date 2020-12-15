package no.nav.bidrag.beregn.barnebidrag.rest;

import static no.nav.bidrag.beregn.barnebidrag.rest.BidragBeregnBarnebidragLocal.LOCAL;

import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import no.nav.bidrag.commons.web.test.HttpHeaderTestRestTemplate;
import org.apache.tomcat.jni.Local;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({LOCAL})
@Configuration
@AutoConfigureWireMock(port = 8096)
public class BidragBeregnBarnebidragLocalConfig {

  @Bean
  public Options wireMockOptions() {

    final WireMockConfiguration options = WireMockSpring.options();
    options.port(8096);

    return options;
  }

}
