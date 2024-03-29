package no.nav.bidrag.beregn.barnebidrag.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnGrunnlag;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnGrunnlagFF;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnetForholdsmessigFordelingResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.dto.http.BeregnetTotalBarnebidragResultat;
import no.nav.bidrag.beregn.barnebidrag.rest.service.BeregnBarnebidragService;
import no.nav.bidrag.beregn.barnebidrag.rest.service.BeregnForholdsmessigFordelingService;
import no.nav.security.token.support.core.api.Protected;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/beregn")
@Protected
public class BeregnBarnebidragController {

  private final BeregnBarnebidragService beregnBarnebidragService;
  private final BeregnForholdsmessigFordelingService beregnForholdsmessigFordelingService;

  public BeregnBarnebidragController(BeregnBarnebidragService beregnBarnebidragService,
      BeregnForholdsmessigFordelingService beregnForholdsmessigFordelingService) {
    this.beregnBarnebidragService = beregnBarnebidragService;
    this.beregnForholdsmessigFordelingService = beregnForholdsmessigFordelingService;
  }

  @PostMapping(path = "/barnebidrag")
  @Operation(summary = "Beregner totalt barnebidrag")
  @SecurityRequirement(name = "bearer-key")
  public ResponseEntity<BeregnetTotalBarnebidragResultat> beregnTotalBarnebidrag(@RequestBody BeregnGrunnlag beregnGrunnlag) {
    var beregnTotalBarnebidragResultat = beregnBarnebidragService.beregn(beregnGrunnlag);
    return new ResponseEntity<>(beregnTotalBarnebidragResultat.getResponseEntity().getBody(),
        beregnTotalBarnebidragResultat.getResponseEntity().getStatusCode());
  }

  @PostMapping(path = "/forholdsmessigfordeling")
  @Operation(summary = "Beregner forholdsmessig fordeling")
  @SecurityRequirement(name = "bearer-key")
  public ResponseEntity<BeregnetForholdsmessigFordelingResultat> beregnForholdsmessigFordeling(@RequestBody BeregnGrunnlagFF beregnGrunnlag) {
    var beregnForholdsmessigFordelingResultat = beregnForholdsmessigFordelingService.beregn(beregnGrunnlag);
    return new ResponseEntity<>(beregnForholdsmessigFordelingResultat.getResponseEntity().getBody(),
        beregnForholdsmessigFordelingResultat.getResponseEntity().getStatusCode());
  }
}
