package no.nav.bidrag.beregn.barnebidrag.rest.consumer;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import no.nav.bidrag.beregn.barnebidrag.rest.TestUtil;
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
@SuppressWarnings("unchecked")
@DisplayName("SjablonConsumerTest")
class SjablonConsumerTest {

  @InjectMocks
  private SjablonConsumer sjablonConsumer;

  @Mock
  private RestTemplate restTemplateMock;

  @Test
  @DisplayName("Skal hente liste av Sjablontall når respons fra tjenesten er OK")
  void skalHenteListeAvSjablontallNårResponsFraTjenestenErOk() {
    when(restTemplateMock.exchange(anyString(), eq(HttpMethod.GET), eq(null), (ParameterizedTypeReference<List<Sjablontall>>) any()))
        .thenReturn(new ResponseEntity<>(TestUtil.dummySjablonSjablontallListe(), HttpStatus.OK));
    var sjablonResponse = sjablonConsumer.hentSjablonSjablontall();

    assertAll(
        () -> assertThat(sjablonResponse).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getStatusCode()).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK),
        () -> assertThat(sjablonResponse.getResponseEntity().getBody()).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getBody().size()).isEqualTo(TestUtil.dummySjablonSjablontallListe().size()),
        () -> assertThat(sjablonResponse.getResponseEntity().getBody().get(0).getTypeSjablon()).isEqualTo(TestUtil.dummySjablonSjablontallListe().get(0).getTypeSjablon())
    );
  }

  @Test
  @DisplayName("Skal returnere mottatt status for Sjablontall når respons fra tjenesten ikke er OK")
  void skalReturnereMottattStatusForSjablontallNårResponsFraTjenestenIkkeErOk() {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.put("error code", singletonList("503"));
    body.put("error msg", singletonList("SERVICE_UNAVAILABLE"));
    body.put("error text", singletonList("Service utilgjengelig"));

    when(restTemplateMock.exchange(anyString(), eq(HttpMethod.GET), eq(null), (ParameterizedTypeReference<List<Sjablontall>>) any()))
        .thenReturn(new ResponseEntity(body, HttpStatus.SERVICE_UNAVAILABLE));
    var sjablonResponse = sjablonConsumer.hentSjablonSjablontall();

    assertAll(
        () -> assertThat(sjablonResponse).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getStatusCode()).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE),
        () -> assertThat(sjablonResponse.getResponseEntity().getHeaders()).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getHeaders().toString()).contains("Service utilgjengelig")
    );
  }

  @Test
  @DisplayName("Skal hente liste av Forbruksutgifter-sjabloner når respons fra tjenesten er OK")
  void skalHenteListeAvForbruksutgifterSjablonerNårResponsFraTjenestenErOk() {
    when(restTemplateMock.exchange(anyString(), eq(HttpMethod.GET), eq(null), (ParameterizedTypeReference<List<Forbruksutgifter>>) any()))
        .thenReturn(new ResponseEntity<>(TestUtil.dummySjablonForbruksutgifterListe(), HttpStatus.OK));
    var sjablonResponse = sjablonConsumer.hentSjablonForbruksutgifter();

    assertAll(
        () -> assertThat(sjablonResponse).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getStatusCode()).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK),
        () -> assertThat(sjablonResponse.getResponseEntity().getBody()).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getBody().size()).isEqualTo(TestUtil.dummySjablonForbruksutgifterListe().size()),
        () -> assertThat(sjablonResponse.getResponseEntity().getBody().get(0).getBelopForbrukTot())
            .isEqualTo(TestUtil.dummySjablonForbruksutgifterListe().get(0).getBelopForbrukTot())
    );
  }

  @Test
  @DisplayName("Skal returnere mottatt status for Forbruksutgifter-sjabloner når respons fra tjenesten ikke er OK")
  void skalReturnereMottattStatusForForbruksutgifterSjablonerNårResponsFraTjenestenIkkeErOk() {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.put("error code", singletonList("503"));
    body.put("error msg", singletonList("SERVICE_UNAVAILABLE"));
    body.put("error text", singletonList("Service utilgjengelig"));

    when(restTemplateMock.exchange(anyString(), eq(HttpMethod.GET), eq(null), (ParameterizedTypeReference<List<Forbruksutgifter>>) any()))
        .thenReturn(new ResponseEntity(body, HttpStatus.SERVICE_UNAVAILABLE));
    var sjablonResponse = sjablonConsumer.hentSjablonForbruksutgifter();

    assertAll(
        () -> assertThat(sjablonResponse).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getStatusCode()).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE),
        () -> assertThat(sjablonResponse.getResponseEntity().getHeaders()).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getHeaders().toString()).contains("Service utilgjengelig")
    );
  }

  @Test
  @DisplayName("Skal hente liste av MaksTilsyn-sjabloner når respons fra tjenesten er OK")
  void skalHenteListeAvMaksTilsynSjablonerNårResponsFraTjenestenErOk() {
    when(restTemplateMock.exchange(anyString(), eq(HttpMethod.GET), eq(null), (ParameterizedTypeReference<List<MaksTilsyn>>) any()))
        .thenReturn(new ResponseEntity<>(TestUtil.dummySjablonMaksTilsynListe(), HttpStatus.OK));
    var sjablonResponse = sjablonConsumer.hentSjablonMaksTilsyn();

    assertAll(
        () -> assertThat(sjablonResponse).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getStatusCode()).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK),
        () -> assertThat(sjablonResponse.getResponseEntity().getBody()).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getBody().size()).isEqualTo(TestUtil.dummySjablonMaksTilsynListe().size()),
        () -> assertThat(sjablonResponse.getResponseEntity().getBody().get(0).getMaksBelopTilsyn())
            .isEqualTo(TestUtil.dummySjablonMaksTilsynListe().get(0).getMaksBelopTilsyn())
    );
  }

  @Test
  @DisplayName("Skal returnere mottatt status for MaksTilsyn-sjabloner når respons fra tjenesten ikke er OK")
  void skalReturnereMottattStatusForMaksTilsynSjablonerNårResponsFraTjenestenIkkeErOk() {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.put("error code", singletonList("503"));
    body.put("error msg", singletonList("SERVICE_UNAVAILABLE"));
    body.put("error text", singletonList("Service utilgjengelig"));

    when(restTemplateMock.exchange(anyString(), eq(HttpMethod.GET), eq(null), (ParameterizedTypeReference<List<MaksTilsyn>>) any()))
        .thenReturn(new ResponseEntity(body, HttpStatus.SERVICE_UNAVAILABLE));
    var sjablonResponse = sjablonConsumer.hentSjablonMaksTilsyn();

    assertAll(
        () -> assertThat(sjablonResponse).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getStatusCode()).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE),
        () -> assertThat(sjablonResponse.getResponseEntity().getHeaders()).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getHeaders().toString()).contains("Service utilgjengelig")
    );
  }

  @Test
  @DisplayName("Skal hente liste av MaksFradrag-sjabloner når respons fra tjenesten er OK")
  void skalHenteListeAvMaksFradragSjablonerNårResponsFraTjenestenErOk() {
    when(restTemplateMock.exchange(anyString(), eq(HttpMethod.GET), eq(null), (ParameterizedTypeReference<List<MaksFradrag>>) any()))
        .thenReturn(new ResponseEntity<>(TestUtil.dummySjablonMaksFradragListe(), HttpStatus.OK));
    var sjablonResponse = sjablonConsumer.hentSjablonMaksFradrag();

    assertAll(
        () -> assertThat(sjablonResponse).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getStatusCode()).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK),
        () -> assertThat(sjablonResponse.getResponseEntity().getBody()).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getBody().size()).isEqualTo(TestUtil.dummySjablonMaksFradragListe().size()),
        () -> assertThat(sjablonResponse.getResponseEntity().getBody().get(0).getMaksBelopFradrag())
            .isEqualTo(TestUtil.dummySjablonMaksFradragListe().get(0).getMaksBelopFradrag())
    );
  }

  @Test
  @DisplayName("Skal returnere mottatt status for MaksFradrag-sjabloner når respons fra tjenesten ikke er OK")
  void skalReturnereMottattStatusForMaksFradragSjablonerNårResponsFraTjenestenIkkeErOk() {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.put("error code", singletonList("503"));
    body.put("error msg", singletonList("SERVICE_UNAVAILABLE"));
    body.put("error text", singletonList("Service utilgjengelig"));

    when(restTemplateMock.exchange(anyString(), eq(HttpMethod.GET), eq(null), (ParameterizedTypeReference<List<MaksFradrag>>) any()))
        .thenReturn(new ResponseEntity(body, HttpStatus.SERVICE_UNAVAILABLE));
    var sjablonResponse = sjablonConsumer.hentSjablonMaksFradrag();

    assertAll(
        () -> assertThat(sjablonResponse).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getStatusCode()).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE),
        () -> assertThat(sjablonResponse.getResponseEntity().getHeaders()).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getHeaders().toString()).contains("Service utilgjengelig")
    );
  }
}
