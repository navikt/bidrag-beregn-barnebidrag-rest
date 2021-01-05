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
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragResultat;
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
    forventetBPAndelUnderholdskostnadProsentBarn1 = BigDecimal.valueOf(83.0);
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
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnTotalBarnebidragResultat.class);
    var totalBarnebidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
        () -> assertThat(totalBarnebidragResultat).isNotNull(),

        // Sjekk BeregnBPBidragsevneResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-08-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatEvneBelop()).isEqualTo(BigDecimal.valueOf(16357)),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2021-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(1).getResultatBeregning()
            .getResultatEvneBelop()).isEqualTo(BigDecimal.valueOf(1583)),

        // Sjekk BeregnBMNettoBarnetilsynResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-08-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatBelop()).isEqualTo(BigDecimal.ZERO),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2021-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(1).getResultatBeregningListe()
            .get(0).getResultatBelop()).isEqualTo(BigDecimal.valueOf(2478)),

        // Sjekk BeregnBMUnderholdskostnadResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-08-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatBelop()).isEqualTo(BigDecimal.valueOf(5999)),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2021-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(1).getResultatBeregning()
            .getResultatBelop()).isEqualTo(BigDecimal.valueOf(8477)),

        // Sjekk BeregnBPAndelUnderholdskostnadResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-08-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatAndelBelop()).isEqualTo(BigDecimal.valueOf(3749)),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatAndelProsent()).isEqualTo(BigDecimal.valueOf(62.5)),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2021-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(1).getResultatBeregning()
            .getResultatAndelBelop()).isEqualTo(BigDecimal.valueOf(4239)),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(1).getResultatBeregning()
            .getResultatAndelProsent()).isEqualTo(BigDecimal.valueOf(50.0)),

        // Sjekk BeregnBPSamvaersfradragResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-08-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2021-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatBelop()).isEqualTo(BigDecimal.valueOf(256)),

        // Sjekk BeregnBarnebidragResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-08-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatBelop().compareTo(BigDecimal.valueOf(3490))).isZero(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatKode()).isEqualTo("KOSTNADSBEREGNET_BIDRAG"),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2021-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(1).getResultatBeregningListe()
            .get(0).getResultatBelop().compareTo(BigDecimal.valueOf(1330))).isZero(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(1).getResultatBeregningListe()
            .get(0).getResultatKode()).isEqualTo("BIDRAG_REDUSERT_AV_EVNE")
    );
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 12 og 13 slått sammen")
  void skalKalleCoreOgReturnereEtResultat_Eksempel_12_13_SplittPeriode() {
    // Eksempel 12 og 13 slått sammen (gir 2 perioder i resultatet for enkelte delberegninger)
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel_12_13_splittperiode.json";

    var request = lesFilOgByggRequest(filnavn);

    // Kall rest-API for barnebidrag
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnTotalBarnebidragResultat.class);
    var totalBarnebidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
        () -> assertThat(totalBarnebidragResultat).isNotNull(),

        // Sjekk BeregnBPBidragsevneResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-08-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatEvneBelop()).isEqualTo(BigDecimal.valueOf(136)),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2021-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(1).getResultatBeregning()
            .getResultatEvneBelop()).isEqualTo(BigDecimal.valueOf(16536)),

        // Sjekk BeregnBMNettoBarnetilsynResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-08-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2021-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatBelop()).isEqualTo(BigDecimal.ZERO),

        // Sjekk BeregnBMUnderholdskostnadResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-08-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2021-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatBelop()).isEqualTo(BigDecimal.valueOf(8684)),

        // Sjekk BeregnBPAndelUnderholdskostnadResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-08-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatAndelBelop()).isEqualTo(BigDecimal.valueOf(3725)),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatAndelProsent()).isEqualTo(BigDecimal.valueOf(42.9)),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2021-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(1).getResultatBeregning()
            .getResultatAndelBelop()).isEqualTo(BigDecimal.valueOf(5210)),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(1).getResultatBeregning()
            .getResultatAndelProsent()).isEqualTo(BigDecimal.valueOf(60.0)),

        // Sjekk BeregnBPSamvaersfradragResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-08-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatBelop()).isEqualTo(BigDecimal.valueOf(457)),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2021-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(1).getResultatBeregning()
            .getResultatBelop()).isEqualTo(BigDecimal.ZERO),

        // Sjekk BeregnBarnebidragResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-08-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatBelop().compareTo(BigDecimal.valueOf(1140))).isZero(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatKode()).isEqualTo("BIDRAG_SATT_TIL_BARNETILLEGG_BP"),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-10-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2021-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(1).getResultatBeregningListe()
            .get(0).getResultatBelop().compareTo(BigDecimal.valueOf(5080))).isZero(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(1).getResultatBeregningListe()
            .get(0).getResultatKode()).isEqualTo("BIDRAG_SATT_TIL_UNDERHOLDSKOSTNAD_MINUS_BARNETILLEGG_BM")
    );
  }

  @Test
  @DisplayName("skal kalle core og returnere et resultat - eksempel 1 med splitt sjablon")
  void skalKalleCoreOgReturnereEtResultat_Eksempel_1_SplittSjablon() {
    // Eksempel 1 med splittperiode for sjablon (gir 2 perioder i resultatet for enkelte delberegninger)
    filnavn = "src/test/resources/testfiler/barnebidrag_eksempel_1_splitt_sjablon.json";

    var request = lesFilOgByggRequest(filnavn);

    // Kall rest-API for barnebidrag
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnTotalBarnebidragResultat.class);
    var totalBarnebidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
        () -> assertThat(totalBarnebidragResultat).isNotNull(),

        // Sjekk BeregnBPBidragsevneResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-06-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2020-07-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatEvneBelop()).isEqualTo(BigDecimal.valueOf(17271)),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-07-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2021-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(1).getResultatBeregning()
            .getResultatEvneBelop()).isEqualTo(BigDecimal.valueOf(16357)),

        // Sjekk BeregnBMNettoBarnetilsynResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-06-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2020-07-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatBelop()).isEqualTo(BigDecimal.ZERO),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-07-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2021-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(1).getResultatBeregningListe()
            .get(0).getResultatBelop()).isEqualTo(BigDecimal.ZERO),

        // Sjekk BeregnBMUnderholdskostnadResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-06-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2020-07-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatBelop()).isEqualTo(BigDecimal.valueOf(5382)),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-07-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2021-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(1).getResultatBeregning()
            .getResultatBelop()).isEqualTo(BigDecimal.valueOf(5999)),

        // Sjekk BeregnBPAndelUnderholdskostnadResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-06-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2020-07-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatAndelBelop()).isEqualTo(BigDecimal.valueOf(3364)),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatAndelProsent()).isEqualTo(BigDecimal.valueOf(62.5)),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-07-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2021-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(1).getResultatBeregning()
            .getResultatAndelBelop()).isEqualTo(BigDecimal.valueOf(3749)),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(1).getResultatBeregning()
            .getResultatAndelProsent()).isEqualTo(BigDecimal.valueOf(62.5)),

        // Sjekk BeregnBPSamvaersfradragResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-06-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2020-07-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatBelop()).isEqualTo(BigDecimal.valueOf(219)),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-07-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2021-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(1).getResultatBeregning()
            .getResultatBelop()).isEqualTo(BigDecimal.valueOf(256)),

        // Sjekk BeregnBarnebidragResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-06-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2020-07-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatBelop().compareTo(BigDecimal.valueOf(3150))).isZero(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatKode()).isEqualTo("KOSTNADSBEREGNET_BIDRAG"),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoFra()).isEqualTo(LocalDate.parse("2020-07-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(1).getResultatDatoFraTil()
            .getPeriodeDatoTil()).isEqualTo(LocalDate.parse("2021-01-01")),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(1).getResultatBeregningListe()
            .get(0).getResultatBelop().compareTo(BigDecimal.valueOf(3490))).isZero(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(1).getResultatBeregningListe()
            .get(0).getResultatKode()).isEqualTo("KOSTNADSBEREGNET_BIDRAG")
    );
  }

  private void utfoerBeregningerOgEvaluerResultat_EttSoknadsbarn() {
    var request = lesFilOgByggRequest(filnavn);

    // Kall rest-API for barnebidrag
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnTotalBarnebidragResultat.class);
    var totalBarnebidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
        () -> assertThat(totalBarnebidragResultat).isNotNull(),

        // Sjekk BeregnBPBidragsevneResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatEvneBelop()).isEqualTo(forventetBidragsevneBelop),

        // Sjekk BeregnBMNettoBarnetilsynResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatBelop()).isEqualTo(forventetNettoBarnetilsynBelopBarn1),

        // Sjekk BeregnBMUnderholdskostnadResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatBelop()).isEqualTo(forventetUnderholdskostnadBelopBarn1),

        // Sjekk BeregnBPAndelUnderholdskostnadResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatAndelBelop()).isEqualTo(forventetBPAndelUnderholdskostnadBelopBarn1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatAndelProsent()).isEqualTo(forventetBPAndelUnderholdskostnadProsentBarn1),

        // Sjekk BeregnBPSamvaersfradragResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatBelop()).isEqualTo(forventetSamvaersfradragBelopBarn1),

        // Sjekk BeregnBarnebidragResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatBelop().compareTo(forventetBarnebidragBelopBarn1)).isZero(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatKode()).isEqualTo(forventetBarnebidragResultatkodeBarn1)
    );
  }

  private void utfoerBeregningerOgEvaluerResultat_ToSoknadsbarn() {
    var request = lesFilOgByggRequest(filnavn);

    // Kall rest-API for barnebidrag
    var responseEntity = httpHeaderTestRestTemplate.exchange(url, HttpMethod.POST, request, BeregnTotalBarnebidragResultat.class);
    var totalBarnebidragResultat = responseEntity.getBody();

    assertAll(
        () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
        () -> assertThat(totalBarnebidragResultat).isNotNull(),

        // Sjekk BeregnBPBidragsevneResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPBidragsevneResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatEvneBelop()).isEqualTo(forventetBidragsevneBelop),

        // Sjekk BeregnBMNettoBarnetilsynResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatBelop()).isEqualTo(forventetNettoBarnetilsynBelopBarn1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatSoknadsbarnPersonId()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(1).getResultatBelop()).isEqualTo(forventetNettoBarnetilsynBelopBarn2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMNettoBarnetilsynResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(1).getResultatSoknadsbarnPersonId()).isEqualTo(2),

        // Sjekk BeregnBMUnderholdskostnadResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatBelop()).isEqualTo(forventetUnderholdskostnadBelopBarn1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(0)
            .getResultatSoknadsbarnPersonId()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(1).getResultatBeregning()
            .getResultatBelop()).isEqualTo(forventetUnderholdskostnadBelopBarn2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBMUnderholdskostnadResultat().getResultatPeriodeListe().get(1)
            .getResultatSoknadsbarnPersonId()).isEqualTo(2),

        // Sjekk BeregnBPAndelUnderholdskostnadResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatAndelBelop()).isEqualTo(forventetBPAndelUnderholdskostnadBelopBarn1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatAndelProsent()).isEqualTo(forventetBPAndelUnderholdskostnadProsentBarn1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(0)
            .getResultatSoknadsbarnPersonId()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(1).getResultatBeregning()
            .getResultatAndelBelop()).isEqualTo(forventetBPAndelUnderholdskostnadBelopBarn2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(1).getResultatBeregning()
            .getResultatAndelProsent()).isEqualTo(forventetBPAndelUnderholdskostnadProsentBarn2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPAndelUnderholdskostnadResultat().getResultatPeriodeListe().get(1)
            .getResultatSoknadsbarnPersonId()).isEqualTo(2),

        // Sjekk BeregnBPSamvaersfradragResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().size()).isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(0).getResultatBeregning()
            .getResultatBelop()).isEqualTo(forventetSamvaersfradragBelopBarn1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(0)
            .getResultatSoknadsbarnPersonId()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(1).getResultatBeregning()
            .getResultatBelop()).isEqualTo(forventetSamvaersfradragBelopBarn2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBPSamvaersfradragResultat().getResultatPeriodeListe().get(1)
            .getResultatSoknadsbarnPersonId()).isEqualTo(2),

        // Sjekk BeregnBarnebidragResultat
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe()).isNotNull(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().size()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe().size())
            .isEqualTo(2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatBelop().compareTo(forventetBarnebidragBelopBarn1)).isZero(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatKode()).isEqualTo(forventetBarnebidragResultatkodeBarn1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(0).getResultatSoknadsbarnPersonId()).isEqualTo(1),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(1).getResultatBelop().compareTo(forventetBarnebidragBelopBarn2)).isZero(),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(1).getResultatKode()).isEqualTo(forventetBarnebidragResultatkodeBarn2),
        () -> assertThat(totalBarnebidragResultat.getBeregnBarnebidragResultat().getResultatPeriodeListe().get(0).getResultatBeregningListe()
            .get(1).getResultatSoknadsbarnPersonId()).isEqualTo(2)
    );
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
