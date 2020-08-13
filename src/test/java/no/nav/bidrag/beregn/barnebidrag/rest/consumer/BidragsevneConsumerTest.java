package no.nav.bidrag.beregn.barnebidrag.rest.consumer;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import no.nav.bidrag.beregn.barnebidrag.rest.TestUtil;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBidragsevneResultat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
@DisplayName("BidragsevneConsumerTest")
class BidragsevneConsumerTest {

  @InjectMocks
  private BidragsevneConsumer bidragsevneConsumer;

  @Mock
  private RestTemplate restTemplateMock;

  @Test
  @DisplayName("Skal hente Bidragsevne når respons fra tjenesten er OK")
  void skalHenteBidragsevneNårResponsFraTjenestenErOk() {
    when(restTemplateMock.exchange(anyString(), eq(HttpMethod.POST), any(), (ParameterizedTypeReference<BeregnBidragsevneResultat>) any()))
        .thenReturn(new ResponseEntity<>(TestUtil.dummyBidragsevneResultat(), HttpStatus.OK));
    var bidragsevneResponse = bidragsevneConsumer.hentBidragsevne(TestUtil.byggBidragsevneGrunnlag());

    assertAll(
        () -> assertThat(bidragsevneResponse).isNotNull(),
        () -> assertThat(bidragsevneResponse.getResponseEntity().getStatusCode()).isNotNull(),
        () -> assertThat(bidragsevneResponse.getResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK),
        () -> assertThat(bidragsevneResponse.getResponseEntity().getBody()).isNotNull(),
        () -> assertThat(bidragsevneResponse.getResponseEntity().getBody().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(bidragsevneResponse.getResponseEntity().getBody().getResultatPeriodeListe().size())
            .isEqualTo(TestUtil.dummyBidragsevneResultat().getResultatPeriodeListe().size()),
        () -> assertThat(bidragsevneResponse.getResponseEntity().getBody().getResultatPeriodeListe().get(0).getResultatBeregning().getResultatEvne())
            .isEqualTo(TestUtil.dummyBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatBeregning().getResultatEvne())
    );
  }

  @Test
  @DisplayName("Skal returnere mottatt status for Bidragsevne når respons fra tjenesten ikke er OK")
  void skalReturnereMottattStatusForBidragsevneNårResponsFraTjenestenIkkeErOk() {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.put("error code", singletonList("503"));
    body.put("error msg", singletonList("SERVICE_UNAVAILABLE"));
    body.put("error text", singletonList("Service utilgjengelig"));

    when(restTemplateMock.exchange(anyString(), eq(HttpMethod.POST), any(), (ParameterizedTypeReference<BeregnBidragsevneResultat>) any()))
        .thenReturn(new ResponseEntity(body, HttpStatus.SERVICE_UNAVAILABLE));
    var bidragsevneResponse = bidragsevneConsumer.hentBidragsevne(TestUtil.byggBidragsevneGrunnlag());

    assertAll(
        () -> assertThat(bidragsevneResponse).isNotNull(),
        () -> assertThat(bidragsevneResponse.getResponseEntity().getStatusCode()).isNotNull(),
        () -> assertThat(bidragsevneResponse.getResponseEntity().getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE),
        () -> assertThat(bidragsevneResponse.getResponseEntity().getHeaders()).isNotNull(),
        () -> assertThat(bidragsevneResponse.getResponseEntity().getHeaders().toString()).contains("Service utilgjengelig")
    );
  }
}
