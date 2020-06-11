package no.nav.bidrag.beregn.bidrag.rest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import no.nav.bidrag.beregn.bidrag.rest.TestUtil;
import no.nav.bidrag.beregn.bidrag.rest.consumer.BidragsevneConsumer;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BeregnBidragsevneGrunnlag;
import no.nav.bidrag.beregn.bidrag.rest.exception.BidragsevneConsumerException;
import no.nav.bidrag.commons.web.HttpStatusResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

@DisplayName("BeregnBidragServiceTest")
class BeregnBidragServiceTest {

  @InjectMocks
  private BeregnBidragService beregnBidragService;

  @Mock
  private BidragsevneConsumer bidragsevneConsumerMock;

  @BeforeEach
  void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("Skal beregne bidragsevne når retur fra BidragsevneConsumer er OK")
  void skalBeregneBidragsevneNårReturFraBidragsevneConsumerErOk() {
    when(bidragsevneConsumerMock.hentBidragsevne(any(BeregnBidragsevneGrunnlag.class))).thenReturn(new HttpStatusResponse<>(HttpStatus.OK,
        TestUtil.dummyBidragsevneResultat()));

    var beregnBidragResultat = beregnBidragService.beregn(TestUtil.byggBidragGrunnlag());

    assertAll(
        () -> assertThat(beregnBidragResultat.getHttpStatus()).isEqualTo(HttpStatus.OK),
        () -> assertThat(beregnBidragResultat.getBody()).isNotNull(),
        () -> assertThat(beregnBidragResultat.getBody().getBeregnBidragsevneResultat()).isNotNull(),
        () -> assertThat(beregnBidragResultat.getBody().getRestenAvResultatet()).isNotNull(),
        () -> assertThat(beregnBidragResultat.getBody().getBeregnBidragsevneResultat().getResultatPeriodeListe().size()).isEqualTo(1)
    );
  }

  @Test
  @DisplayName("Skal kaste BidragsevneConsumerException ved feil retur fra BidragsevneConsumer")
  void skalKasteBidragsevneConsumerExceptionVedFeilReturFraBidragsevneConsumer() {
    Map<String, String> body = new HashMap<>();
    body.put("error code", "503");
    body.put("error msg", "SERVICE_UNAVAILABLE");
    body.put("error text", "Service utilgjengelig");
    when(bidragsevneConsumerMock.hentBidragsevne(any(BeregnBidragsevneGrunnlag.class)))
        .thenReturn(new HttpStatusResponse(HttpStatus.SERVICE_UNAVAILABLE, body.toString()));

    assertThatExceptionOfType(BidragsevneConsumerException.class)
        .isThrownBy(() -> beregnBidragService.beregn(TestUtil.byggBidragGrunnlag()))
        .withMessageContaining("Feil ved kall av bidrag-beregn-bidragsevne-rest. Status: " + HttpStatus.SERVICE_UNAVAILABLE + " Melding: ");
  }
}
