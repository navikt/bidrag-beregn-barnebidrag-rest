package no.nav.bidrag.beregn.barnebidrag.rest.controller;

import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnBarnebidragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.service.BeregnBarnebidragService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/beregn")
public class BeregnBarnebidragController {

  private final BeregnBarnebidragService beregnBarnebidragService;

  public BeregnBarnebidragController(BeregnBarnebidragService beregnBarnebidragService) {
    this.beregnBarnebidragService = beregnBarnebidragService;
  }

  @PostMapping(path = "/barnebidrag")
  public ResponseEntity<BeregnBarnebidragResultat> beregnBarnebidrag(@RequestBody BeregnBarnebidragGrunnlag beregnBarnebidragGrunnlag) {
    var beregnBarnebidragResultat = beregnBarnebidragService.beregn(beregnBarnebidragGrunnlag);
    return new ResponseEntity<>(beregnBarnebidragResultat.getResponseEntity().getBody(),
        beregnBarnebidragResultat.getResponseEntity().getStatusCode());
  }
}
