package no.nav.bidrag.beregn.barnebidrag.rest.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import org.springframework.cloud.contract.spec.internal.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class SjablonApiStub {

  public void hentSjablonBarnetilsynStub() {
    var url = "http://localhost:8096/bidrag-sjablon/barnetilsyn/all";

    stubFor(
        get(urlEqualTo(url))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withStatus(HttpStatus.OK)
                    .withBody(
                        String.join(
                            "\n",
                            " [  "
                                + "{\"typeStonad\": \"64\","
                                + "\"typeTilsyn\": \"DO\","
                                + "\"datoFom\": \"2020-07-01\","
                                + "\"datoTom\": \"9999-12-31\","
                                + "\"belopBarneTilsyn\": 358,"
                                + "\"brukerid\": \"A100364 \","
                                + "\"tidspktEndret\": \"2020-05-17T14:13:16.999\"},"

                                + "{\"typeStonad\": \"64\","
                                + "\"typeTilsyn\": \"DU\","
                                + "\"datoFom\": \"2020-07-01\","
                                + "\"datoTom\": \"9999-12-31\","
                                + "\"belopBarneTilsyn\": 257,"
                                + "\"brukerid\": \"A100364 \","
                                + "\"tidspktEndret\": \"2020-05-17T14:14:13.801\"},"

                                + "{\"typeStonad\": \"64\","
                                + "\"typeTilsyn\": \"HO\","
                                + "\"datoFom\": \"2020-07-01\","
                                + "\"datoTom\": \"9999-12-31\","
                                + "\"belopBarneTilsyn\": 589,"
                                + "\"brukerid\": \"A100364 \","
                                + "\"tidspktEndret\": \"2020-05-17T14:14:53.803\"},"

                                + "{\"typeStonad\": \"64\","
                                + "\"typeTilsyn\": \"HU\","
                                + "\"datoFom\": \"2020-07-01\","
                                + "\"datoTom\": \"9999-12-31\","
                                + "\"belopBarneTilsyn\": 643,"
                                + "\"brukerid\": \"A100364 \","
                                + "\"tidspktEndret\": \"2020-05-17T14:15:49.233\"}]"))));
  }
}
