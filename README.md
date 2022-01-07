# bidrag-beregn-barnebidrag-rest

[![](https://github.com/navikt/bidrag-beregn-barnebidrag-rest/actions/workflows/ci.yaml/badge.svg)](https://github.com/navikt/bidrag-beregn-barnebidrag-rest/actions/workflows/ci.yaml)
[![](https://github.com/navikt/bidrag-beregn-barnebidrag-rest/actions/workflows/pr.yaml/badge.svg)](https://github.com/navikt/bidrag-beregn-barnebidrag-rest/actions/workflows/pr.yaml)
[![](https://github.com/navikt/bidrag-beregn-barnebidrag-rest/actions/workflows/release.yaml/badge.svg)](https://github.com/navikt/bidrag-beregn-barnebidrag-rest/actions/workflows/release.yaml)

Mikrotjeneste / Rest-API for beregning av barnebidrag som er satt opp til å kjøre på NAIS i GCP.

### Tilgjengelige tjenester (endepunkter)
#### Beregning av barnebidrag
Request-URL: [https://bidrag-beregn-barnebidrag-rest.dev.intern.nav.no/beregn/barnebidrag](https://bidrag-beregn-barnebidrag-rest.dev.intern.nav.no/beregn/barnebidrag)

#### Beregning av forholdsmessig fordeling
Request-URL: [https://bidrag-beregn-barnebidrag-rest.dev.intern.nav.no/beregn/forholdsmessigfordeling](https://bidrag-beregn-barnebidrag-rest.dev.intern.nav.no/beregn/forholdsmessigfordeling)

#### Swagger
Swagger-UI: [https://bidrag-beregn-barnebidrag-rest.dev.intern.nav.no/](https://bidrag-beregn-barnebidrag-rest.dev.intern.nav.no/)

### Input/output
Tjenestene kalles med en POST-request, hvor input-dataene legges i request-bodyen. For nærmere detaljer, se Swagger.

### Avhengigheter
`bidrag-beregn-barnebidrag-rest` kaller maven-modul `bidrag-beregn-barnebidrag-core`, hvor selve beregningen gjøres.
Sjablonverdier hentes ved å kalle rest-tjenesten `bidrag-sjablon` via proxy tjenesten `bidrag-gcp-proxy`.

### Sikkerhet
Tjenesten er sikret med Azure AD JWT tokens. Konsumenter av tjenesten er derfor nødt til å registere seg som konsument av tjenesten, og benytte gyldig token i `Authorization` header ved REST-kall. Dersom en ny applikasjon skal ha tilgang må dette også registreres i henholdsvis `nais.yaml` og `nais-p.yaml` i denne applikasjonen.

### Funksjonalitet
#### Beregning av barnebidrag
Tjenesten tar inn parametre knyttet til bidragsmottaker, bidragspliktig og bidragsbarn. Eksempler på inputdata er inntekter, bostatus og utgifter
knyttet til barna det søkes om bidrag for. Det gjøres input-kontroll på dataene. Hvis noen av disse inneholder null, kastes `UgyldigInputException`, 
som resulterer i statuskode 400 (Bad Request).

Sjablonverdier som er nødvendige for beregningen hentes fra tjenesten `bidrag-sjablon` via proxy tjenesten `bidrag-gcp-proxy`. Ved feil i kall for henting av sjablonverdier kastes
`BidragGcpProxyConsumerException`, som resulterer i statuskode 500 (Internal Server Error).

Det gjøres en mapping fra rest-tjenestens input-grensesnitt til core-tjenestens input-grensesnitt før denne kalles. Output fra enkelte av 
delberegningene brukes som input i andre delberegninger. Beregningsmodulen (core) kan også returnere feil, som vil resultere i at det kastes en
exception (avhengig av type feil).

**Det foretas følgende delberegninger (en eller flere kall til core-modulen for hver delberegning):**
* Bidragsevne (bidragspliktig, utføres en gang)
* Netto barnetilsyn (bidragsmottaker, utføres en gang)
* Underholdskostnad (bidragsmottaker, utføres en gang for hvert bidragsbarn)
* BP's andel av underholdskostnad (bidragspliktig, utføres en gang for hvert bidragsbarn)
* Samværsfradrag (bidragspliktig, utføres en gang for hvert bidragsbarn)
* Barnebidrag (totalberegning, utføres en gang)

For hver delberegning returneres resultatet av beregningen og grunnlaget for beregningen.

#### Beregning av forholdsmessig fordeling
Tjenesten tar inn parametre for bidragsevne og tidligere beregning av alle saker tilhørende bidragspliktig som det skal gjøres forholdsmessig 
fordeling av. Det gjøres input-kontroll på dataene. Hvis noen av disse inneholder null, kastes `UgyldigInputException`, som resulterer i statuskode 
400 (Bad Request).

Det gjøres en mapping fra rest-tjenestens input-grensesnitt til core-tjenestens input-grensesnitt før denne kalles. Beregningsmodulen (core) kan også 
returnere feil, som vil resultere i at det kastes en exception (avhengig av type feil).

Tjenesten er tenkt brukt hvis ordinær beregning avdekker at bidragspliktig har begrenset bidragsevne. Dette resulterer da i at alle saker som
bidragspliktig er involvert i må beregnes på nytt og forholdsmessig fordeles.

### Overordnet arkitektur

![Overordnet arkitektur](./img/beregn-barnebidrag.drawio.png)

### Kjøre applikasjon lokalt
Applikasjonen kan kjøres opp lokalt med fila `BidragBeregnBarnebidragLocal`. Applikasjonen kjøres da opp på [http://localhost:8080/](http://localhost:8080/) og kan testes med Swagger. Også når applikasjonen kjøres lokalt kreves et gyldig JWT-token, men her kreves ikke et gyldig Azure AD token. Lokalt er applikasjonen konfugurert til å bruke en lokalt kjørende MockOAuth-service for å utstede og validere JWT-tokens. For å utstede et gylig token til testing kan man benytte endepunktet `GET /local/cookie?issuerId=aad&audience=aud-localhost`. Viktig at `issuerId=aad` og `audience=aud-locahost`.

## Utstede gyldig token i dev-gcp
For å kunne teste applikasjonen i `dev-gcp` trenger man et gyldig AzureAD JWT-token. For å utstede et slikt token trenger man miljøvariablene `AZURE_APP_CLIENT_ID` og `AZURE_APP_CLIENT_SECRET`. Disse ligger tilgjengelig i de kjørende pod'ene til applikasjonen.

Koble seg til en kjørende pod (feature-branch):
```
kubectl -n bidrag exec -i -t bidrag-beregn-barnebidrag-rest-feature-<sha> -c bidrag-beregn-barnebidrag-rest-feature -- /bin/bash
```

Koble seg til en kjørende pod (main-branch):
```
kubectl -n bidrag exec -i -t bidrag-beregn-barnebidrag-rest-<sha> -c bidrag-beregn-barnebidrag-rest -- /bin/bash
```

Når man er inne i pod'en kan man hente ut miljøvariablene på følgende måte:
```
echo "$( cat /var/run/secrets/nais.io/azure/AZURE_APP_CLIENT_ID )"
echo "$( cat /var/run/secrets/nais.io/azure/AZURE_APP_CLIENT_SECRET )"
```

Deretter kan vi hente ned et gyldig Azure AD JWT-token med følgende kall (feature-branch):
```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'client_id=<AZURE_APP_CLIENT_ID>&scope=api://dev-gcp.bidrag.bidrag-beregn-barnebidrag-rest-feature/.default&client_secret=<AZURE_APP_CLIENT_SECRET>&grant_type=client_credentials' 'https://login.microsoftonline.com/966ac572-f5b7-4bbe-aa88-c76419c0f851/oauth2/v2.0/token'
```

Deretter kan vi hente ned et gyldig Azure AD JWT-token med følgende kall (main-branch):
```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'client_id=<AZURE_APP_CLIENT_ID>&scope=api://dev-gcp.bidrag.bidrag-beregn-barnebidrag-rest/.default&client_secret=<AZURE_APP_CLIENT_SECRET>&grant_type=client_credentials' 'https://login.microsoftonline.com/966ac572-f5b7-4bbe-aa88-c76419c0f851/oauth2/v2.0/token'
```

### Integrasjonstester
Det er lagt inn integrasjonstester (BeregnBarnebidragControllerIntegrationTest.java) vha. Junit- og WireMock-rammeverkene. Disse testene er ment å
dekke et bredt spekter av scenarier med tanke på bidragsberegning. De fleste inneholder bare data for beregning av en enkelt periode og vil dermed 
ikke dekke scenarier for testing av periodisering og splitt-perioder. Det er i tillegg lagt inn noen få tester som danner splitt-perioder.
