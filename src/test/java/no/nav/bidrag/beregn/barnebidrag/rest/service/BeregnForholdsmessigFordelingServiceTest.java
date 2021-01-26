package no.nav.bidrag.beregn.barnebidrag.rest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import no.nav.bidrag.beregn.barnebidrag.rest.TestUtil;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;
import no.nav.bidrag.beregn.forholdsmessigfordeling.ForholdsmessigFordelingCore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@DisplayName("BeregnForholdsmessigFordelingServiceTest")
@ExtendWith(MockitoExtension.class)
class BeregnForholdsmessigFordelingServiceTest {

  @InjectMocks
  private BeregnForholdsmessigFordelingService beregnForholdsmessigFordelingService;

  @Mock
  private ForholdsmessigFordelingCore forholdsmessigFordelingCoreMock;

  @Test
  @DisplayName("Skal beregne forholdsmessig fordeling")
  void skalBeregneForholdsmessigFordeling() {
    when(forholdsmessigFordelingCoreMock.beregnForholdsmessigFordeling(any())).thenReturn(TestUtil.dummyForholdsmessigFordelingResultatCore());

    var beregnForholdsmessigFordelingResultat = beregnForholdsmessigFordelingService.beregn(TestUtil.byggForholdsmessigFordelingGrunnlag().tilCore());

    assertAll(
        () -> assertThat(beregnForholdsmessigFordelingResultat.getResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK),
        () -> assertThat(beregnForholdsmessigFordelingResultat.getResponseEntity().getBody()).isNotNull(),
        () -> assertThat(beregnForholdsmessigFordelingResultat.getResponseEntity().getBody().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(beregnForholdsmessigFordelingResultat.getResponseEntity().getBody().getResultatPeriodeListe().size()).isEqualTo(1)
    );
  }

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved feil retur fra Core")
  void skalKasteUgyldigInputExceptionVedFeilReturFraCore() {
    when(forholdsmessigFordelingCoreMock.beregnForholdsmessigFordeling(any())).thenReturn(TestUtil.dummyForholdsmessigFordelingResultatCoreMedAvvik());

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnForholdsmessigFordelingService.beregn(TestUtil.byggForholdsmessigFordelingGrunnlag().tilCore()))
        .withMessageContaining("Ugyldig input ved beregning av forholdsmessig fordeling. Følgende avvik ble funnet:")
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }
}
