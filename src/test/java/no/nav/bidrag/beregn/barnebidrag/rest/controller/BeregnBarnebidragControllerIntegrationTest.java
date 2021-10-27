package no.nav.bidrag.beregn.barnebidrag.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.http.HttpStatus.OK;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import no.nav.bidrag.beregn.barnebidrag.rest.BidragBeregnBarnebidragLocal;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.wiremock_stub.SjablonApiStub;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnetTotalBarnebidragResultat;
import no.nav.bidrag.commons.web.test.HttpHeaderTestRestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = BidragBeregnBarnebidragLocal.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integrationtest")
@AutoConfigureWireMock(port = 8096)
public class BeregnBarnebidragControllerIntegrationTest {

  @Autowired
  private HttpHeaderTestRestTemplate httpHeaderTestRestTemplate;

  @Autowired
  private SjablonApiStub sjablonApiStub;

  @LocalServerPort
  private int port;

  private String url;
  private String filnavn;

  private BigDecimal forventetBidragsevneBelop;
  private BigDecimal forventetNettoBarnetilsynBelopBarn1;
  private BigDecimal forventetUnderholdskostnadBelopBarn1;
  private BigDecimal forventetBPAndelUnderholdskostnadBelopBarn1;
  private BigDecimal forventetBPAndelUnderholdskostnadProsentBarn1;
  private BigDecimal forventetSamvaersfradragBelopBarn1;
  private BigDecimal forventetBarnebidragBelopBarn1;
  private String forventetBarnebidragResultatkodeBarn1;
  private BigDecimal forventetNettoBarnetilsynBelopBarn2;
  private BigDecimal forventetUnderholdskostnadBelopBarn2;
  private BigDecimal forventetBPAndelUnderholdskostnadBelopBarn2;
  private BigDecimal forventetBPAndelUnderholdskostnadProsentBarn2;
  private BigDecimal forventetSamvaersfradragBelopBarn2;
  private BigDecimal forventetBarnebidragBelopBarn2;
  private String forventetBarnebidragResultatkodeBarn2;
  private final Integer forventetBarnebidragAntallGrunnlagReferanser = 7;

  @BeforeEach
  void init() {
    // Sett opp wiremock mot sjablon-tjenestene
    sjablonApiStub.settOppSjablonStub();

    // Bygg opp url
    url = "http://localhost:" + port + "/bidrag-beregn-barnebidrag-rest/beregn/barnebidrag";
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 1")
  void skalKalleCoreOgReturnereEtResultat_Eksempel01() {
    // Ordinært kostnadsberegnet bidrag
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel1.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(16357);
    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.ZERO;
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(5999);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(3749);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(62.5);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.valueOf(256);
    forventetBarnebidragBelopBarn1 = BigDecimal.valueOf(3490);
    forventetBarnebidragResultatkodeBarn1 = "KOSTNADSBEREGNET_BIDRAG";

    utfoerBeregningerOgEvaluerResultat_EttSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 2")
  void skalKalleCoreOgReturnereEtResultat_Eksempel02() {
    // Ordinært kostnadsberegnet bidrag inkludert netto barnetilsyn
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel2.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(16357);
    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.valueOf(2478);
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8477);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(5298);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(62.5);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.valueOf(256);
    forventetBarnebidragBelopBarn1 = BigDecimal.valueOf(5040);
    forventetBarnebidragResultatkodeBarn1 = "KOSTNADSBEREGNET_BIDRAG";

    utfoerBeregningerOgEvaluerResultat_EttSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 3")
  void skalKalleCoreOgReturnereEtResultat_Eksempel03() {
    // Ordinært kostnadsberegnet bidrag inkludert netto barnetilsyn
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel3.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(5424);
    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.valueOf(2478);
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8477);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(4239);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(50.0);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.valueOf(256);
    forventetBarnebidragBelopBarn1 = BigDecimal.valueOf(3980);
    forventetBarnebidragResultatkodeBarn1 = "KOSTNADSBEREGNET_BIDRAG";

    utfoerBeregningerOgEvaluerResultat_EttSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 4")
  void skalKalleCoreOgReturnereEtResultat_Eksempel04() {
    // Bidrag beregnes til BPs evne
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel4.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(1583);
    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.valueOf(2478);
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8477);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(4239);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(50.0);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.valueOf(256);
    forventetBarnebidragBelopBarn1 = BigDecimal.valueOf(1330);
    forventetBarnebidragResultatkodeBarn1 = "BIDRAG_REDUSERT_AV_EVNE";

    utfoerBeregningerOgEvaluerResultat_EttSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 5")
  void skalKalleCoreOgReturnereEtResultat_Eksempel05() {
    // Bidrag beregnes til BPs evne (som er 0 kr)
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel5.json";

    forventetBidragsevneBelop = BigDecimal.ZERO;
    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.valueOf(2478);
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8477);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(4239);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(50.0);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.valueOf(256);
    forventetBarnebidragBelopBarn1 = BigDecimal.ZERO;
    forventetBarnebidragResultatkodeBarn1 = "INGEN_EVNE";

    utfoerBeregningerOgEvaluerResultat_EttSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 6")
  void skalKalleCoreOgReturnereEtResultat_Eksempel06() {
    // Kostnadsberegnet bidrag og BPs andel reduseres til 83,33333333 (5/6)
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel6.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(16536);
    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.ZERO;
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8684);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(7237);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(83.3333333333);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.ZERO;
    forventetBarnebidragBelopBarn1 = BigDecimal.valueOf(7240);
    forventetBarnebidragResultatkodeBarn1 = "KOSTNADSBEREGNET_BIDRAG";

    utfoerBeregningerOgEvaluerResultat_EttSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 7")
  void skalKalleCoreOgReturnereEtResultat_Eksempel07() {
    // 2 barn, kostnadsberegnet bidrag, inkludert at netto tilsyn er rett når barn 2 er over 12 år
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel7.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(16536);

    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.ZERO;
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8684);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(5210);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(60.0);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.valueOf(1513);
    forventetBarnebidragBelopBarn1 = BigDecimal.valueOf(3700);
    forventetBarnebidragResultatkodeBarn1 = "KOSTNADSBEREGNET_BIDRAG";

    forventetNettoBarnetilsynBelopBarn2 = BigDecimal.valueOf(1978);
    forventetUnderholdskostnadBelopBarn2 = BigDecimal.valueOf(9459);
    forventetBPAndelUnderholdskostnadBelopBarn2 = BigDecimal.valueOf(5675);
    forventetBPAndelUnderholdskostnadProsentBarn2 = BigDecimal.valueOf(60.0);
    forventetSamvaersfradragBelopBarn2 = BigDecimal.valueOf(1167);
    forventetBarnebidragBelopBarn2 = BigDecimal.valueOf(4510);
    forventetBarnebidragResultatkodeBarn2 = "KOSTNADSBEREGNET_BIDRAG";

    utfoerBeregningerOgEvaluerResultat_ToSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 8")
  void skalKalleCoreOgReturnereEtResultat_Eksempel08() {
    // 2 barn, bidrag redusert til evne og delt forholdsmessig inkludert netto tilsyn (2 barn 12 år eller under)
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel8.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(8854);

    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.ZERO;
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8684);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(5210);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(60.0);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.valueOf(1513);
    forventetBarnebidragBelopBarn1 = BigDecimal.valueOf(2750);
    forventetBarnebidragResultatkodeBarn1 = "BIDRAG_REDUSERT_AV_EVNE";

    forventetNettoBarnetilsynBelopBarn2 = BigDecimal.valueOf(1874);
    forventetUnderholdskostnadBelopBarn2 = BigDecimal.valueOf(9355);
    forventetBPAndelUnderholdskostnadBelopBarn2 = BigDecimal.valueOf(5613);
    forventetBPAndelUnderholdskostnadProsentBarn2 = BigDecimal.valueOf(60.0);
    forventetSamvaersfradragBelopBarn2 = BigDecimal.valueOf(1167);
    forventetBarnebidragBelopBarn2 = BigDecimal.valueOf(3420);
    forventetBarnebidragResultatkodeBarn2 = "BIDRAG_REDUSERT_AV_EVNE";

    utfoerBeregningerOgEvaluerResultat_ToSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 9")
  void skalKalleCoreOgReturnereEtResultat_Eksempel09() {
    // 2 barn, bidrag settes til maks 25 % av BPs inntekt
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel9.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(16536);

    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.ZERO;
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8684);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(7237);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(83.3333333333);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.valueOf(1513);
    forventetBarnebidragBelopBarn1 = BigDecimal.valueOf(4500);
    forventetBarnebidragResultatkodeBarn1 = "BIDRAG_REDUSERT_TIL_25_PROSENT_AV_INNTEKT";

    forventetNettoBarnetilsynBelopBarn2 = BigDecimal.valueOf(1874);
    forventetUnderholdskostnadBelopBarn2 = BigDecimal.valueOf(9355);
    forventetBPAndelUnderholdskostnadBelopBarn2 = BigDecimal.valueOf(7796);
    forventetBPAndelUnderholdskostnadProsentBarn2 = BigDecimal.valueOf(83.3333333333);
    forventetSamvaersfradragBelopBarn2 = BigDecimal.valueOf(1167);
    forventetBarnebidragBelopBarn2 = BigDecimal.valueOf(5320);
    forventetBarnebidragResultatkodeBarn2 = "BIDRAG_REDUSERT_TIL_25_PROSENT_AV_INNTEKT";

    utfoerBeregningerOgEvaluerResultat_ToSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 10")
  void skalKalleCoreOgReturnereEtResultat_Eksempel10() {
    // Bidrag beregnes til BPs evne
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel10.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(136);
    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.ZERO;
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8684);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(3725);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(42.9);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.ZERO;
    forventetBarnebidragBelopBarn1 = BigDecimal.valueOf(140);
    forventetBarnebidragResultatkodeBarn1 = "BIDRAG_REDUSERT_AV_EVNE";

    utfoerBeregningerOgEvaluerResultat_EttSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 11")
  void skalKalleCoreOgReturnereEtResultat_Eksempel11() {
    // Bidrag beregnes til BPs evne (BP har særfradrag)
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel11.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(1217);
    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.ZERO;
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8684);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(3725);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(42.9);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.ZERO;
    forventetBarnebidragBelopBarn1 = BigDecimal.valueOf(1220);
    forventetBarnebidragResultatkodeBarn1 = "BIDRAG_REDUSERT_AV_EVNE";

    utfoerBeregningerOgEvaluerResultat_EttSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 12")
  void skalKalleCoreOgReturnereEtResultat_Eksempel12() {
    // Bidrag beregnes til BPs evne (BPs evne er liten, og BP barnetillegg kommer til anvendelse)
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel12.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(136);
    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.ZERO;
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8684);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(3725);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(42.9);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.valueOf(457);
    forventetBarnebidragBelopBarn1 = BigDecimal.valueOf(1140);
    forventetBarnebidragResultatkodeBarn1 = "BIDRAG_SATT_TIL_BARNETILLEGG_BP";

    utfoerBeregningerOgEvaluerResultat_EttSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 13")
  void skalKalleCoreOgReturnereEtResultat_Eksempel13() {
    // Bidrag beregnes etter regel for BM baretillegg
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel13.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(16536);
    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.ZERO;
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8684);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(5210);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(60.0);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.ZERO;
    forventetBarnebidragBelopBarn1 = BigDecimal.valueOf(5080);
    forventetBarnebidragResultatkodeBarn1 = "BIDRAG_SATT_TIL_UNDERHOLDSKOSTNAD_MINUS_BARNETILLEGG_BM";

    utfoerBeregningerOgEvaluerResultat_EttSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 14")
  void skalKalleCoreOgReturnereEtResultat_Eksempel14() {
    // Bidrag beregnes etter regel for BM baretillegg, men samværet gjør at regel likevel ikke benyttes
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel14.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(16536);
    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.ZERO;
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8684);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(5210);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(60.0);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.valueOf(457);
    forventetBarnebidragBelopBarn1 = BigDecimal.valueOf(4750);
    forventetBarnebidragResultatkodeBarn1 = "KOSTNADSBEREGNET_BIDRAG";

    utfoerBeregningerOgEvaluerResultat_EttSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 15")
  void skalKalleCoreOgReturnereEtResultat_Eksempel15() {
    // BP barnetillegg er høyere enn beregnet bidrag
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel15.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(16536);
    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.ZERO;
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8684);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(5210);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(60.0);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.ZERO;
    forventetBarnebidragBelopBarn1 = BigDecimal.valueOf(5400);
    forventetBarnebidragResultatkodeBarn1 = "BIDRAG_SATT_TIL_BARNETILLEGG_BP";

    utfoerBeregningerOgEvaluerResultat_EttSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 16")
  void skalKalleCoreOgReturnereEtResultat_Eksempel16() {
    // BP barnetillegg er høyere enn beregnet bidrag og det er hensyntatt samvær
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel16.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(16536);
    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.ZERO;
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8684);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(5210);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(60.0);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.valueOf(457);
    forventetBarnebidragBelopBarn1 = BigDecimal.valueOf(4940);
    forventetBarnebidragResultatkodeBarn1 = "BIDRAG_SATT_TIL_BARNETILLEGG_BP";

    utfoerBeregningerOgEvaluerResultat_EttSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 17")
  void skalKalleCoreOgReturnereEtResultat_Eksempel17() {
    // Ordinær beregning av delt bosted
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel17.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(16536);
    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.ZERO;
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8684);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(5080);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(58.5);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.ZERO;
    forventetBarnebidragBelopBarn1 = BigDecimal.valueOf(740);
    forventetBarnebidragResultatkodeBarn1 = "DELT_BOSTED";

    utfoerBeregningerOgEvaluerResultat_EttSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 18")
  void skalKalleCoreOgReturnereEtResultat_Eksempel18() {
    // BPs andel er mindre enn 50%, bidrag skal ikke betales
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel18.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(5603);
    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.ZERO;
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8684);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(4203);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(48.4);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.ZERO;
    forventetBarnebidragBelopBarn1 = BigDecimal.ZERO;
    forventetBarnebidragResultatkodeBarn1 = "BARNEBIDRAG_IKKE_BEREGNET_DELT_BOSTED";

    utfoerBeregningerOgEvaluerResultat_EttSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 19")
  void skalKalleCoreOgReturnereEtResultat_Eksempel19() {
    // Selv om det er delt bosted skal bidraget reduseres til evne
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel19.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(359);
    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.ZERO;
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8684);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(4898);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(56.4);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.ZERO;
    forventetBarnebidragBelopBarn1 = BigDecimal.valueOf(360);
    forventetBarnebidragResultatkodeBarn1 = "BIDRAG_REDUSERT_AV_EVNE";

    utfoerBeregningerOgEvaluerResultat_EttSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 20")
  void skalKalleCoreOgReturnereEtResultat_Eksempel20() {
    // Kostnadsberegnet bidrag (BMs inntekt er så lav at særfradrag ikke blir beregnet)
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel20.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(11069);
    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.ZERO;
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8684);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(7208);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(83);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.ZERO;
    forventetBarnebidragBelopBarn1 = BigDecimal.valueOf(7210);
    forventetBarnebidragResultatkodeBarn1 = "KOSTNADSBEREGNET_BIDRAG";

    utfoerBeregningerOgEvaluerResultat_EttSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 21")
  void skalKalleCoreOgReturnereEtResultat_Eksempel21() {
    // 2 barn, fordel særfradrag er delt mellom partene, gir delt bosted i samvær
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel21.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(11069);

    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.ZERO;
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8684);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(4724);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(54.4);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.ZERO;
    forventetBarnebidragBelopBarn1 = BigDecimal.valueOf(380);
    forventetBarnebidragResultatkodeBarn1 = "DELT_BOSTED";

    forventetNettoBarnetilsynBelopBarn2 = BigDecimal.ZERO;
    forventetUnderholdskostnadBelopBarn2 = BigDecimal.valueOf(8684);
    forventetBPAndelUnderholdskostnadBelopBarn2 = BigDecimal.valueOf(4724);
    forventetBPAndelUnderholdskostnadProsentBarn2 = BigDecimal.valueOf(54.4);
    forventetSamvaersfradragBelopBarn2 = BigDecimal.ZERO;
    forventetBarnebidragBelopBarn2 = BigDecimal.valueOf(380);
    forventetBarnebidragResultatkodeBarn2 = "DELT_BOSTED";

    utfoerBeregningerOgEvaluerResultat_ToSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 22")
  void skalKalleCoreOgReturnereEtResultat_Eksempel22() {
    // Bidrag skal ikke beregnes fordi barnet har inntekt over 100 x forhøyet forskudd (tilsvarende barn under 11 år)
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel22.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(11069);
    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.ZERO;
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8684);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.ZERO;
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.ZERO;
    forventetSamvaersfradragBelopBarn1 = BigDecimal.ZERO;
    forventetBarnebidragBelopBarn1 = BigDecimal.ZERO;
    forventetBarnebidragResultatkodeBarn1 = "BARNET_ER_SELVFORSORGET";

    utfoerBeregningerOgEvaluerResultat_EttSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 23")
  void skalKalleCoreOgReturnereEtResultat_Eksempel23() {
    // Kostnadsberegnet bidrag (barnets inntekt reduseres med 30 x forskudd)
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel23.json";

    forventetBidragsevneBelop = BigDecimal.valueOf(11069);
    forventetNettoBarnetilsynBelopBarn1 = BigDecimal.ZERO;
    forventetUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(8684);
    forventetBPAndelUnderholdskostnadBelopBarn1 = BigDecimal.valueOf(4194);
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(48.3);
    forventetSamvaersfradragBelopBarn1 = BigDecimal.ZERO;
    forventetBarnebidragBelopBarn1 = BigDecimal.valueOf(4190);
    forventetBarnebidragResultatkodeBarn1 = "KOSTNADSBEREGNET_BIDRAG";

    utfoerBeregningerOgEvaluerResultat_EttSoknadsbarn();
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 1 og 4 slått sammen")
  void skalKalleCoreOgReturnereEtResultat_Eksempel_1_4_SplittPeriode() {
    // Eksempel 1 og 4 slått sammen (gir 2 perioder i resultatet for enkelte delberegninger)
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel_1_4_splittperiode.json";

    var request = lesFilOgByggRequest(filnavn);

    // Kall rest-API for barnebidrag
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnetTotalBarnebidragResultat.class);
    var totalBarnebidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
        () -> assertThat(totalBarnebidragResultat).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getGrunnlagListe()).isNotNull(),

        // Sjekk BeregnBarnebidragResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getBarn()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getPeriode().getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2020-08-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getPeriode().getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getResultat().getBelop()
            .compareTo(BigDecimal.valueOf(3490))).isZero(),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getResultat().getKode())
            .isEqualTo("KOSTNADSBEREGNET_BIDRAG"),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getGrunnlagReferanseListe().size()).isEqualTo(7),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getBarn()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getPeriode().getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getPeriode().getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2021-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getResultat().getBelop()
            .compareTo(BigDecimal.valueOf(1330))).isZero(),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getResultat().getKode())
            .isEqualTo("BIDRAG_REDUSERT_AV_EVNE"),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getGrunnlagReferanseListe().size()).isEqualTo(7)
    );

    // Sjekk delberegninger
    totalBarnebidragResultat.getGrunnlagListe().stream()
        .filter(resultatGrunnlag -> resultatGrunnlag.getType().equals("Delberegning"))
        .forEach(resultatGrunnlag -> {
          if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_Bidragsevne_20200801")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("16357");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_Bidragsevne_20201001")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("1583");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BM_NettoBarnetilsyn_SB1_20200801")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("0");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BM_NettoBarnetilsyn_SB1_20201001")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("2478");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BM_Underholdskostnad_SB1_20200801")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("5999");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BM_Underholdskostnad_SB1_20201001")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("8477");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_AndelUnderholdskostnad_20200801")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("3749");
            assertThat(resultatGrunnlag.getInnhold().get("prosent").asText()).isEqualTo("62.5");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_AndelUnderholdskostnad_20201001")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("4239");
            assertThat(resultatGrunnlag.getInnhold().get("prosent").asText()).isEqualTo("50");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_Samvaersfradrag_20200801")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("256");
          }
        });
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 12 og 13 slått sammen")
  void skalKalleCoreOgReturnereEtResultat_Eksempel_12_13_SplittPeriode() {
    // Eksempel 12 og 13 slått sammen (gir 2 perioder i resultatet for enkelte delberegninger)
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel_12_13_splittperiode.json";

    var request = lesFilOgByggRequest(filnavn);

    // Kall rest-API for barnebidrag
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnetTotalBarnebidragResultat.class);
    var totalBarnebidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
        () -> assertThat(totalBarnebidragResultat).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getGrunnlagListe()).isNotNull(),

        // Sjekk BeregnBarnebidragResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getBarn()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getPeriode().getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2020-08-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getPeriode().getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getResultat().getBelop()
            .compareTo(BigDecimal.valueOf(1140))).isZero(),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getResultat().getKode())
            .isEqualTo("BIDRAG_SATT_TIL_BARNETILLEGG_BP"),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getGrunnlagReferanseListe().size()).isEqualTo(7),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getBarn()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getPeriode().getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getPeriode().getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2021-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getResultat().getBelop()
            .compareTo(BigDecimal.valueOf(5080))).isZero(),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getResultat().getKode())
            .isEqualTo("BIDRAG_SATT_TIL_UNDERHOLDSKOSTNAD_MINUS_BARNETILLEGG_BM"),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getGrunnlagReferanseListe().size()).isEqualTo(7)
    );

    // Sjekk delberegninger
    totalBarnebidragResultat.getGrunnlagListe().stream()
        .filter(resultatGrunnlag -> resultatGrunnlag.getType().equals("Delberegning"))
        .forEach(resultatGrunnlag -> {
          if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_Bidragsevne_20200801")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("136");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_Bidragsevne_20201001")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("16536");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BM_NettoBarnetilsyn_SB1_20200801")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("0");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BM_Underholdskostnad_SB1_20200801")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("8684");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_AndelUnderholdskostnad_20200801")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("3725");
            assertThat(resultatGrunnlag.getInnhold().get("prosent").asText()).isEqualTo("42.9");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_AndelUnderholdskostnad_20201001")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("5210");
            assertThat(resultatGrunnlag.getInnhold().get("prosent").asText()).isEqualTo("60");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_Samvaersfradrag_20200801")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("457");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_Samvaersfradrag_20201001")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("0");
          }
        });
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 1 med splitt sjablon")
  void skalKalleCoreOgReturnereEtResultat_Eksempel_1_SplittSjablon() {
    // Eksempel 1 med splittperiode for sjablon (gir 2 perioder i resultatet for enkelte delberegninger)
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel_1_splitt_sjablon.json";

    var request = lesFilOgByggRequest(filnavn);

    // Kall rest-API for barnebidrag
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnetTotalBarnebidragResultat.class);
    var totalBarnebidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
        () -> assertThat(totalBarnebidragResultat).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getGrunnlagListe()).isNotNull(),

        // Sjekk BeregnBarnebidragResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getBarn()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getPeriode().getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2020-06-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getPeriode().getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2020-07-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getResultat().getBelop()
            .compareTo(BigDecimal.valueOf(3150))).isZero(),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getResultat().getKode())
            .isEqualTo("KOSTNADSBEREGNET_BIDRAG"),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getGrunnlagReferanseListe().size()).isEqualTo(7),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getBarn()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getPeriode().getPeriodeDatoFra())
            .isEqualTo(LocalDate.parse("2020-07-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getPeriode().getPeriodeDatoTil())
            .isEqualTo(LocalDate.parse("2021-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getResultat().getBelop()
            .compareTo(BigDecimal.valueOf(3490))).isZero(),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getResultat().getKode())
            .isEqualTo("KOSTNADSBEREGNET_BIDRAG"),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getGrunnlagReferanseListe().size()).isEqualTo(7)
    );

    // Sjekk delberegninger
    totalBarnebidragResultat.getGrunnlagListe().stream()
        .filter(resultatGrunnlag -> resultatGrunnlag.getType().equals("Delberegning"))
        .forEach(resultatGrunnlag -> {
          if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_Bidragsevne_20200601")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("17271");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_Bidragsevne_20200701")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("16357");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BM_NettoBarnetilsyn_SB1_20200601")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("0");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BM_NettoBarnetilsyn_SB1_20200701")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("0");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BM_Underholdskostnad_SB1_20200601")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("5382");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BM_Underholdskostnad_SB1_20200701")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("5999");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_AndelUnderholdskostnad_20200601")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("3364");
            assertThat(resultatGrunnlag.getInnhold().get("prosent").asText()).isEqualTo("62.5");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_AndelUnderholdskostnad_20200701")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("3749");
            assertThat(resultatGrunnlag.getInnhold().get("prosent").asText()).isEqualTo("62.5");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_Samvaersfradrag_20200601")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("219");
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_Samvaersfradrag_20200701")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo("256");
          }
        });
  }

  private void utfoerBeregningerOgEvaluerResultat_EttSoknadsbarn() {
    var request = lesFilOgByggRequest(filnavn);

    // Kall rest-API for barnebidrag
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnetTotalBarnebidragResultat.class);
    var totalBarnebidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
        () -> assertThat(totalBarnebidragResultat).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getGrunnlagListe()).isNotNull(),

        // Sjekk Barnebidrag
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getBarn()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getResultat().getBelop()
            .compareTo(forventetBarnebidragBelopBarn1)).isZero(),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getResultat().getKode())
            .isEqualTo(forventetBarnebidragResultatkodeBarn1),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getGrunnlagReferanseListe().size())
            .isEqualTo(forventetBarnebidragAntallGrunnlagReferanser)
    );

    // Sjekk delberegninger
    totalBarnebidragResultat.getGrunnlagListe().stream()
        .filter(resultatGrunnlag -> resultatGrunnlag.getType().equals("Delberegning"))
        .forEach(resultatGrunnlag -> {
          if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_Bidragsevne")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo(forventetBidragsevneBelop.toString());
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BM_NettoBarnetilsyn")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo(forventetNettoBarnetilsynBelopBarn1.toString());
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BM_Underholdskostnad")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo(forventetUnderholdskostnadBelopBarn1.toString());
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_AndelUnderholdskostnad")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo(forventetBPAndelUnderholdskostnadBelopBarn1.toString());
            assertThat(resultatGrunnlag.getInnhold().get("prosent").asText()).isEqualTo(forventetBPAndelUnderholdskostnadProsentBarn1.toString());
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_Samvaersfradrag")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo(forventetSamvaersfradragBelopBarn1.toString());
          }
        });
  }

  private void utfoerBeregningerOgEvaluerResultat_ToSoknadsbarn() {
    var request = lesFilOgByggRequest(filnavn);

    // Kall rest-API for barnebidrag
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnetTotalBarnebidragResultat.class);
    var totalBarnebidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
        () -> assertThat(totalBarnebidragResultat).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getGrunnlagListe()).isNotNull(),

        // Sjekk Barnebidrag
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getBarn()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getResultat().getBelop()
            .compareTo(forventetBarnebidragBelopBarn1)).isZero(),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getResultat().getKode())
            .isEqualTo(forventetBarnebidragResultatkodeBarn1),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(0).getGrunnlagReferanseListe().size())
            .isEqualTo(forventetBarnebidragAntallGrunnlagReferanser),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getBarn()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getResultat().getBelop()
            .compareTo(forventetBarnebidragBelopBarn2)).isZero(),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getResultat().getKode())
            .isEqualTo(forventetBarnebidragResultatkodeBarn2),
        () -> assertThat(totalBarnebidragResultat.getBeregnetBarnebidragPeriodeListe().get(1).getGrunnlagReferanseListe().size())
            .isEqualTo(forventetBarnebidragAntallGrunnlagReferanser)
    );

    // Sjekk delberegninger
    totalBarnebidragResultat.getGrunnlagListe().stream()
        .filter(resultatGrunnlag -> resultatGrunnlag.getType().equals("Delberegning"))
        .forEach(resultatGrunnlag -> {
          if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_Bidragsevne")) {
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo(forventetBidragsevneBelop.toString());
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BM_NettoBarnetilsyn_SB1")) {
            assertThat(resultatGrunnlag.getInnhold().get("barn").asText()).isEqualTo("1");
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo(forventetNettoBarnetilsynBelopBarn1.toString());
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BM_Underholdskostnad_SB1")) {
            assertThat(resultatGrunnlag.getInnhold().get("barn").asText()).isEqualTo("1");
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo(forventetUnderholdskostnadBelopBarn1.toString());
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_AndelUnderholdskostnad_SB1")) {
            assertThat(resultatGrunnlag.getInnhold().get("barn").asText()).isEqualTo("1");
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo(forventetBPAndelUnderholdskostnadBelopBarn1.toString());
            assertThat(resultatGrunnlag.getInnhold().get("prosent").asText()).isEqualTo(forventetBPAndelUnderholdskostnadProsentBarn1.toString());
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_Samvaersfradrag_SB1")) {
            assertThat(resultatGrunnlag.getInnhold().get("barn").asText()).isEqualTo("1");
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo(forventetSamvaersfradragBelopBarn1.toString());
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BM_NettoBarnetilsyn_SB2")) {
            assertThat(resultatGrunnlag.getInnhold().get("barn").asText()).isEqualTo("2");
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo(forventetNettoBarnetilsynBelopBarn2.toString());
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BM_Underholdskostnad_SB2")) {
            assertThat(resultatGrunnlag.getInnhold().get("barn").asText()).isEqualTo("2");
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo(forventetUnderholdskostnadBelopBarn2.toString());
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_AndelUnderholdskostnad_SB2")) {
            assertThat(resultatGrunnlag.getInnhold().get("barn").asText()).isEqualTo("2");
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo(forventetBPAndelUnderholdskostnadBelopBarn2.toString());
            assertThat(resultatGrunnlag.getInnhold().get("prosent").asText()).isEqualTo(forventetBPAndelUnderholdskostnadProsentBarn2.toString());
          } else if (resultatGrunnlag.getReferanse().startsWith("Delberegning_BP_Samvaersfradrag_SB2")) {
            assertThat(resultatGrunnlag.getInnhold().get("barn").asText()).isEqualTo("2");
            assertThat(resultatGrunnlag.getInnhold().get("belop").asText()).isEqualTo(forventetSamvaersfradragBelopBarn2.toString());
          }
        });
  }

  private HttpEntity<String> lesFilOgByggRequest(String filnavn) {
    var json = "";

    // Les inn fil med request-data (json)
    try {
      json = Files.readString(Paths.get(filnavn));
    } catch (Exception e) {
      fail("Klarte ikke å lese fil: " + filnavn);
    }

    // Lag request
    return initHttpEntity(json);
  }

  private <T> HttpEntity<T> initHttpEntity(T body) {
    var httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<>(body, httpHeaders);
  }

}
