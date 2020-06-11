package no.nav.bidrag.beregn.bidrag.rest.controller;

import no.nav.bidrag.beregn.bidrag.rest.dto.http.BeregnBidragGrunnlag;
import no.nav.bidrag.beregn.bidrag.rest.dto.http.BeregnBidragResultat;
import no.nav.bidrag.beregn.bidrag.rest.service.BeregnBidragService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/beregn")
public class BeregnBidragController {

  private final BeregnBidragService beregnBidragService;

  public BeregnBidragController(BeregnBidragService beregnBidragService) {
    this.beregnBidragService = beregnBidragService;
  }

  @PostMapping(path = "/bidrag")
  public ResponseEntity<BeregnBidragResultat> beregnBidrag(@RequestBody BeregnBidragGrunnlag beregnBidragGrunnlag) {
    var beregnBidragResultat = beregnBidragService.beregn(beregnBidragGrunnlag);
    return new ResponseEntity<>(beregnBidragResultat.getBody(), beregnBidragResultat.getHttpStatus());
  }
}
