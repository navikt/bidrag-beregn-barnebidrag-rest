# bidrag-beregn-barnebidrag-rest

![](https://github.com/navikt/bidrag-beregn-barnebidrag-rest/workflows/continuous%20integration/badge.svg)

Mikrotjeneste / Rest-API for beregning av barnebidrag som er satt opp til å kjøre på NAIS.

### Tilgjengelige tjenester (endepunkter)
Request-URL: https://bidrag-beregn-barnebidrag-rest.dev-fss.nais.io/bidrag-beregn-barnebidrag-rest/beregn/barnebidrag<br/>
Swagger-UI: https://bidrag-beregn-barnebidrag-rest.dev-fss.nais.io/bidrag-beregn-barnebidrag-rest/swagger-ui.html#/beregn-barnebidrag-controller

### Input/output
Tjenesten kalles med en POST-request, hvor input-dataene legges i request-bodyen. For nærmere detaljer, se Swagger.

### Avhengigheter
bidrag-beregn-barnebidrag-rest kaller maven-modul bidrag-beregn-barnebidrag-core, hvor selve beregningen gjøres.<br/>
Sjablonverdier hentes ved å kalle rest-tjeneste bidrag-sjablon.

### Sikkerhet
Det er ingen sikkerhet, da tjenesten ikke behandler sensitive data.

### Funksjonalitet
Tjenesten tar inn parametre knyttet til bidragsmottaker, bidragspliktig og bidragsbarn. Eksempler på inputdata er inntekter, bostatus og utgifter
knyttet til barna det søkes om bidrag for. Det gjøres input-kontroll på dataene. Hvis noen av disse inneholder null, kastes UgyldigInputException, 
som resulterer i statuskode 400 (Bad Request).

Sjablonverdier som er nødvendige for beregningen hentes fra tjeneste bidrag-sjablon. Ved feil i kall til bidrag-sjablon kastes 
SjablonConsumerException, som resulterer i statuskode 500 (Internal Server Error).

Det gjøres en mapping fra rest-tjenestens input-grensesnitt til core-tjenestens input-grensesnitt før denne kalles. Output fra enkelte av 
delberegningene brukes som input i andre delberegninger. Beregningsmodulen (core) kan også returnere feil, som vil resultere i at det kastes en
exception (avhengig av type feil).

**Det foretas følgende delberegninger (en eller flere kall til core-modulen for hver delberegning):**
* Bidragsevne (bidragspliktig, utføres en gang)
* Netto barnetilsyn (bidragsmottaker, utføres en gang)
* Underholdskostnad (bidragsmottaker, utføres en gang for hvert bidragsbarn)
* BP's andel av underholdskostnad (bidragspliktig, utføres en gang for hvert bidragsbarn)
* Samværsfradrag (bidragspliktig, utføres en gang for hvert bidragsbarn)
* Kostnadsberegnet bidrag (utføres en gang for hvert søknadsbarn)
* Barnebidrag (totalberegning, utføres en gang)

For hver delberegning returneres resultatet av beregningen og grunnlaget for beregningen

### Integrasjonstester
Det er lagt inn integrasjonstester (BeregnBarnebidragControllerIntegrationTest.java) vha. Junit- og WireMock-rammeverkene. Disse testene er ment å
dekke et bredt spekter av scenarier med tanke på bidragsberegning. De fleste inneholder bare data for beregning av en enkelt periode og vil dermed 
ikke dekke scenarier for testing av periodisering og splitt-perioder. Det er i tillegg lagt inn noen få tester som danner splitt-perioder.