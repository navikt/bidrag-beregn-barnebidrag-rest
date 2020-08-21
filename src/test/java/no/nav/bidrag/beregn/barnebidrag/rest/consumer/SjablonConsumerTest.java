package no.nav.bidrag.beregn.barnebidrag.rest.consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import no.nav.bidrag.beregn.barnebidrag.rest.TestUtil;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.SjablonConsumerException;
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
import org.springframework.web.client.HttpClientErrorException;
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
  void skalHenteListeAvSjablontallNaarResponsFraTjenestenErOk() {
    when(restTemplateMock.exchange(anyString(), eq(HttpMethod.GET), eq(null), (ParameterizedTypeReference<List<Sjablontall>>) any()))
        .thenReturn(new ResponseEntity<>(TestUtil.dummySjablonSjablontallListe(), HttpStatus.OK));
    var sjablonResponse = sjablonConsumer.hentSjablonSjablontall();

    assertAll(
        () -> assertThat(sjablonResponse).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getStatusCode()).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK),
        () -> assertThat(sjablonResponse.getResponseEntity().getBody()).isNotNull(),
        () -> assertThat(sjablonResponse.getResponseEntity().getBody().size()).isEqualTo(TestUtil.dummySjablonSjablontallListe().size()),
        () -> assertThat(sjablonResponse.getResponseEntity().getBody().get(0).getTypeSjablon())
            .isEqualTo(TestUtil.dummySjablonSjablontallListe().get(0).getTypeSjablon())
    );
  }

  @Test
  @DisplayName("Skal kaste SjablonConsumerException når respons fra tjenesten ikke er OK for Sjablontall")
  void skalKasteRestClientExceptionNaarResponsFraTjenestenIkkeErOkForSjablontall() {
    when(restTemplateMock.exchange(anyString(), eq(HttpMethod.GET), eq(null), (ParameterizedTypeReference<List<Sjablontall>>) any()))
        .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

    assertThatExceptionOfType(SjablonConsumerException.class).isThrownBy(() -> sjablonConsumer.hentSjablonSjablontall());
  }

  @Test
  @DisplayName("Skal hente liste av Forbruksutgifter-sjabloner når respons fra tjenesten er OK")
  void skalHenteListeAvForbruksutgifterSjablonerNaarResponsFraTjenestenErOk() {
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
  @DisplayName("Skal kaste SjablonConsumerException når respons fra tjenesten ikke er OK for Forbruksutgifter")
  void skalKasteRestClientExceptionNaarResponsFraTjenestenIkkeErOkForForbruksutgifter() {
    when(restTemplateMock.exchange(anyString(), eq(HttpMethod.GET), eq(null), (ParameterizedTypeReference<List<Forbruksutgifter>>) any()))
        .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

    assertThatExceptionOfType(SjablonConsumerException.class).isThrownBy(() -> sjablonConsumer.hentSjablonForbruksutgifter());
  }

  @Test
  @DisplayName("Skal hente liste av MaksTilsyn-sjabloner når respons fra tjenesten er OK")
  void skalHenteListeAvMaksTilsynSjablonerNaarResponsFraTjenestenErOk() {
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
  @DisplayName("Skal kaste SjablonConsumerException når respons fra tjenesten ikke er OK for MaksTilsyn")
  void skalKasteRestClientExceptionNaarResponsFraTjenestenIkkeErOkForMaksTilsyn() {
    when(restTemplateMock.exchange(anyString(), eq(HttpMethod.GET), eq(null), (ParameterizedTypeReference<List<MaksTilsyn>>) any()))
        .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

    assertThatExceptionOfType(SjablonConsumerException.class).isThrownBy(() -> sjablonConsumer.hentSjablonMaksTilsyn());
  }

  @Test
  @DisplayName("Skal hente liste av MaksFradrag-sjabloner når respons fra tjenesten er OK")
  void skalHenteListeAvMaksFradragSjablonerNaarResponsFraTjenestenErOk() {
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
  @DisplayName("Skal kaste SjablonConsumerException når respons fra tjenesten ikke er OK for MaksFradrag")
  void skalKasteRestClientExceptionNaarResponsFraTjenestenIkkeErOkForMaksFradrag() {
    when(restTemplateMock.exchange(anyString(), eq(HttpMethod.GET), eq(null), (ParameterizedTypeReference<List<MaksFradrag>>) any()))
        .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

    assertThatExceptionOfType(SjablonConsumerException.class).isThrownBy(() -> sjablonConsumer.hentSjablonMaksFradrag());
  }
}
