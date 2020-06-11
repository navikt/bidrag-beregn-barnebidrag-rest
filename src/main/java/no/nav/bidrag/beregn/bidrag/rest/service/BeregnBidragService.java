package no.nav.bidrag.beregn.bidrag.rest.service;

import no.nav.bidrag.beregn.bidrag.rest.consumer.BidragsevneConsumer;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BeregnBidragGrunnlag;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BeregnBidragResultat;
import no.nav.bidrag.beregn.bidrag.rest.exception.BidragsevneConsumerException;
import no.nav.bidrag.commons.web.HttpStatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BeregnBidragService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BeregnBidragService.class);

  private final BidragsevneConsumer bidragsevneConsumer;

  public BeregnBidragService(BidragsevneConsumer bidragsevneConsumer) {
    this.bidragsevneConsumer = bidragsevneConsumer;
  }

  public HttpStatusResponse<BeregnBidragResultat> beregn(BeregnBidragGrunnlag beregnBidragGrunnlag) {

    // Hent sjabloner

    // Kall bidragsevneberegning
    var bidragsevneResultat = bidragsevneConsumer.hentBidragsevne(beregnBidragGrunnlag.getBeregnBidragsevneGrunnlag());

    if (!(bidragsevneResultat.getHttpStatus().is2xxSuccessful())) {
      LOGGER.error("Feil ved kall av bidrag-beregn-bidragsevne-rest. Status: {}", bidragsevneResultat.getHttpStatus().toString());
      throw new BidragsevneConsumerException("Feil ved kall av bidrag-beregn-bidragsevne-rest. Status: "
          + bidragsevneResultat.getHttpStatus().toString() + " Melding: " + bidragsevneResultat.getBody());
    }

    // Kall underholdskostnadberegning

    // Kall totalberegning

    return new HttpStatusResponse(HttpStatus.OK, new BeregnBidragResultat(bidragsevneResultat.getBody(), "Resten av resultatet"));
  }
}
