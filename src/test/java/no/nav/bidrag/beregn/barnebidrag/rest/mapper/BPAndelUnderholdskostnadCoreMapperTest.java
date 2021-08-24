package no.nav.bidrag.beregn.barnebidrag.rest.mapper;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import no.nav.bidrag.beregn.barnebidrag.rest.BidragBeregnBarnebidragLocal;
import no.nav.bidrag.beregn.barnebidrag.rest.TestUtil;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonListe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BidragBeregnBarnebidragLocal.class)
@DisplayName("CoreMapperTest")
class BPAndelUnderholdskostnadCoreMapperTest {

  @Autowired
  private BPAndelUnderholdskostnadCoreMapper bpAndelUnderholdskostnadCoreMapper;

  private final SjablonListe sjablonListe = new SjablonListe(TestUtil.dummySjablonSjablontallListe(), emptyList(), emptyList(), emptyList(), emptyList(),
      emptyList(), emptyList(), emptyList());

  @Test
  @DisplayName("Skal ikke koble BM inntekt og søknadsbarn - knytningsverdi = null")
  void skalIkkeKobleBMInntektOgSoknadsbarn() {
    var bpAndelUnderholdskostnadGrunnlagTilCore = bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(
        TestUtil.byggBarnebidragGrunnlag(), sjablonListe, 1, TestUtil.dummyUnderholdskostnadResultatCore());

    assertAll(
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore).isNotNull(),
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore.getInntektBMPeriodeListe()).isNotNull(),
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore.getInntektBMPeriodeListe().size()).isEqualTo(1)
    );
  }

  @Test
  @DisplayName("Skal koble BM inntekt og søknadsbarn 1")
  void skalKobleBMInntektOgSoknadsbarn1() {
    var bpAndelUnderholdskostnadGrunnlagTilCore = bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(
        TestUtil.byggBarnebidragGrunnlag("1"), sjablonListe, 1, TestUtil.dummyUnderholdskostnadResultatCore());

    assertAll(
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore).isNotNull(),
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore.getInntektBMPeriodeListe()).isNotNull(),
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore.getInntektBMPeriodeListe().size()).isEqualTo(1)
    );
  }

  @Test
  @DisplayName("Skal ikke koble BM inntekt og søknadsbarn 1")
  void skalIkkeKobleBMInntektOgSoknadsbarn1() {
    var bpAndelUnderholdskostnadGrunnlagTilCore = bpAndelUnderholdskostnadCoreMapper.mapBPAndelUnderholdskostnadGrunnlagTilCore(
        TestUtil.byggBarnebidragGrunnlag("2"), sjablonListe, 1, TestUtil.dummyUnderholdskostnadResultatCore());

    assertAll(
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore).isNotNull(),
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore.getInntektBMPeriodeListe()).isNotNull(),
        () -> assertThat(bpAndelUnderholdskostnadGrunnlagTilCore.getInntektBMPeriodeListe().size()).isEqualTo(0)
    );
  }
}
