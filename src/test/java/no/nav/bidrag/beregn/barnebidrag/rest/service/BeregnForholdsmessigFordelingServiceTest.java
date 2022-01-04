package no.nav.bidrag.beregn.barnebidrag.rest.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import no.nav.bidrag.beregn.barnebidrag.rest.BidragBeregnBarnebidragLocal;
import no.nav.bidrag.beregn.barnebidrag.rest.BidragBeregnBarnebidragTest;
import no.nav.bidrag.beregn.barnebidrag.rest.TestUtil;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;
import no.nav.bidrag.beregn.forholdsmessigfordeling.ForholdsmessigFordelingCore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = BidragBeregnBarnebidragTest.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayName("BeregnForholdsmessigFordelingServiceTest")
class BeregnForholdsmessigFordelingServiceTest {

  @Autowired
  private BeregnForholdsmessigFordelingService beregnForholdsmessigFordelingService;

  @MockBean
  private ForholdsmessigFordelingCore forholdsmessigFordelingCoreMock;

  @Test
  @DisplayName("Skal kaste UgyldigInputException ved feil retur fra Core")
  void skalKasteUgyldigInputExceptionVedFeilReturFraCore() {
    when(forholdsmessigFordelingCoreMock.beregnForholdsmessigFordeling(any())).thenReturn(TestUtil.dummyForholdsmessigFordelingResultatCoreMedAvvik());

    assertThatExceptionOfType(UgyldigInputException.class)
        .isThrownBy(() -> beregnForholdsmessigFordelingService.beregn(TestUtil.byggForholdsmessigFordelingGrunnlag()))
        .withMessageContaining("Ugyldig input ved beregning av forholdsmessig fordeling. Følgende avvik ble funnet:")
        .withMessageContaining("beregnDatoFra kan ikke være null")
        .withMessageContaining("periodeDatoTil må være etter periodeDatoFra");
  }
}
