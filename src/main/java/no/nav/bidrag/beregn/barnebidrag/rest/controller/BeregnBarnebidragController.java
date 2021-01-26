package no.nav.bidrag.beregn.barnebidrag.rest.controller;

import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnForholdsmessigFordelingGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnForholdsmessigFordelingResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnTotalBarnebidragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.service.BeregnBarnebidragService;
import no.nav.bidrag.beregn.barnebidrag.rest.service.BeregnForholdsmessigFordelingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/beregn")
public class BeregnBarnebidragController {

  private final BeregnBarnebidragService beregnBarnebidragService;
  private final BeregnForholdsmessigFordelingService beregnForholdsmessigFordelingService;

  public BeregnBarnebidragController(BeregnBarnebidragService beregnBarnebidragService,
      BeregnForholdsmessigFordelingService beregnForholdsmessigFordelingService) {
    this.beregnBarnebidragService = beregnBarnebidragService;
    this.beregnForholdsmessigFordelingService = beregnForholdsmessigFordelingService;
  }

  @PostMapping(path = "/barnebidrag")
  public ResponseEntity<BeregnTotalBarnebidragResultat> beregnTotalBarnebidrag(
      @RequestBody BeregnTotalBarnebidragGrunnlag beregnTotalBarnebidragGrunnlag) {
    var beregnTotalBarnebidragResultat = beregnBarnebidragService.beregn(beregnTotalBarnebidragGrunnlag);
    return new ResponseEntity<>(beregnTotalBarnebidragResultat.getResponseEntity().getBody(),
        beregnTotalBarnebidragResultat.getResponseEntity().getStatusCode());
  }

  @PostMapping(path = "/forholdsmessigfordeling")
  public ResponseEntity<BeregnForholdsmessigFordelingResultat> beregnForholdsmessigFordeling(
      @RequestBody BeregnForholdsmessigFordelingGrunnlag beregnForholdsmessigFordelingGrunnlag) {
    var beregnForholdsmessigFordelingResultat = beregnForholdsmessigFordelingService.beregn(beregnForholdsmessigFordelingGrunnlag.tilCore());
    return new ResponseEntity<>(beregnForholdsmessigFordelingResultat.getResponseEntity().getBody(),
        beregnForholdsmessigFordelingResultat.getResponseEntity().getStatusCode());
  }
}
