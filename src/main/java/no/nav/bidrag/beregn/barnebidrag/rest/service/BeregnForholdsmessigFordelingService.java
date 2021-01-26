package no.nav.bidrag.beregn.barnebidrag.rest.service;

import java.util.stream.Collectors;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnForholdsmessigFordelingResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.exception.UgyldigInputException;
import no.nav.bidrag.beregn.felles.dto.AvvikCore;
import no.nav.bidrag.beregn.forholdsmessigfordeling.ForholdsmessigFordelingCore;
import no.nav.bidrag.beregn.forholdsmessigfordeling.dto.BeregnForholdsmessigFordelingGrunnlagCore;
import no.nav.bidrag.commons.web.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BeregnForholdsmessigFordelingService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BeregnForholdsmessigFordelingService.class);

  private final ForholdsmessigFordelingCore forholdsmessigFordelingCore;

  public BeregnForholdsmessigFordelingService(ForholdsmessigFordelingCore forholdsmessigFordelingCore) {
    this.forholdsmessigFordelingCore = forholdsmessigFordelingCore;
  }

  public HttpResponse<BeregnForholdsmessigFordelingResultat> beregn(BeregnForholdsmessigFordelingGrunnlagCore grunnlagTilCore) {

    // Kaller core-modulen for beregning av forholdsmessig fordeling
    LOGGER.debug("Forholdsmessig fordeling - grunnlag for beregning: {}", grunnlagTilCore);
    var resultatFraCore = forholdsmessigFordelingCore.beregnForholdsmessigFordeling(grunnlagTilCore);

    if (!resultatFraCore.getAvvikListe().isEmpty()) {
      LOGGER.error("Ugyldig input ved beregning av forholdsmessig fordeling. Følgende avvik ble funnet: " + System.lineSeparator()
          + resultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining(System.lineSeparator())));
      LOGGER.info("Forholdsmessig fordeling - grunnlag for beregning: " + System.lineSeparator()
          + "beregnDatoFra= " + grunnlagTilCore.getBeregnDatoFra() + System.lineSeparator()
          + "beregnDatoTil= " + grunnlagTilCore.getBeregnDatoTil() + System.lineSeparator()
          + "bidragsevnePeriodeListe= " + grunnlagTilCore.getBidragsevnePeriodeListe() + System.lineSeparator()
          + "beregnetBidragPeriodeListe= " + grunnlagTilCore.getBeregnetBidragPeriodeListe() + System.lineSeparator());
      throw new UgyldigInputException("Ugyldig input ved beregning av forholdsmessig fordeling. Følgende avvik ble funnet: "
          + resultatFraCore.getAvvikListe().stream().map(AvvikCore::getAvvikTekst).collect(Collectors.joining("; ")));

    }

    LOGGER.debug("Forskudd - resultat av beregning: {}", resultatFraCore.getResultatPeriodeListe());
    return HttpResponse.from(HttpStatus.OK, new BeregnForholdsmessigFordelingResultat(resultatFraCore));
  }
}
