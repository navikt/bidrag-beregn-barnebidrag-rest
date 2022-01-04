package no.nav.bidrag.beregn.barnebidrag.rest.service;

import java.util.List;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Barnetilsyn;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.BidragGcpProxyConsumer;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Bidragsevne;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Forbruksutgifter;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.MaksFradrag;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.MaksTilsyn;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Samvaersfradrag;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.Sjablontall;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.TrinnvisSkattesats;
import no.nav.bidrag.commons.web.HttpResponse;
import org.springframework.core.ParameterizedTypeReference;

public class SjablonService {
  private static final ParameterizedTypeReference<List<Sjablontall>> SJABLON_SJABLONTALL_LISTE = new ParameterizedTypeReference<>() {};
  private static final ParameterizedTypeReference<List<Forbruksutgifter>> SJABLON_FORBRUKSUTGIFTER_LISTE = new ParameterizedTypeReference<>() {};
  private static final ParameterizedTypeReference<List<MaksTilsyn>> SJABLON_MAKSTILSYN_LISTE = new ParameterizedTypeReference<>() {};
  private static final ParameterizedTypeReference<List<MaksFradrag>> SJABLON_MAKSFRADRAG_LISTE = new ParameterizedTypeReference<>() {};
  private static final ParameterizedTypeReference<List<Samvaersfradrag>> SJABLON_SAMVAERSFRADRAG_LISTE = new ParameterizedTypeReference<>() {};
  private static final ParameterizedTypeReference<List<Bidragsevne>> SJABLON_BIDRAGSEVNE_LISTE = new ParameterizedTypeReference<>() {};
  private static final ParameterizedTypeReference<List<TrinnvisSkattesats>> SJABLON_TRINNVIS_SKATTESATS_LISTE = new ParameterizedTypeReference<>() {};
  private static final ParameterizedTypeReference<List<Barnetilsyn>> SJABLON_BARNETILSYN_LISTE = new ParameterizedTypeReference<>() {};


  private final BidragGcpProxyConsumer bidragGcpProxyConsumer;


  public SjablonService(BidragGcpProxyConsumer bidragGcpProxyConsumer) {
    this.bidragGcpProxyConsumer = bidragGcpProxyConsumer;
  }


  public HttpResponse<List<Sjablontall>> hentSjablonSjablontall() {
    String sjablonSjablontallUrl = "/sjablon/sjablontall?all=true";
    return bidragGcpProxyConsumer.hentSjablonListe(sjablonSjablontallUrl, SJABLON_SJABLONTALL_LISTE);
  }

  public HttpResponse<List<Forbruksutgifter>> hentSjablonForbruksutgifter() {
    String sjablonSjablontallUrl = "/sjablon/forbruksutgifter?all=true";
    return bidragGcpProxyConsumer.hentSjablonListe(sjablonSjablontallUrl, SJABLON_FORBRUKSUTGIFTER_LISTE);
  }

  public HttpResponse<List<MaksTilsyn>> hentSjablonMaksTilsyn() {
    String sjablonSjablontallUrl = "/sjablon/makstilsyn?all=true";
    return bidragGcpProxyConsumer.hentSjablonListe(sjablonSjablontallUrl, SJABLON_MAKSTILSYN_LISTE);
  }

  public HttpResponse<List<MaksFradrag>> hentSjablonMaksFradrag() {
    String sjablonSjablontallUrl = "/sjablon/maksfradrag?all=true";
    return bidragGcpProxyConsumer.hentSjablonListe(sjablonSjablontallUrl, SJABLON_MAKSFRADRAG_LISTE);
  }

  public HttpResponse<List<Samvaersfradrag>> hentSjablonSamvaersfradrag() {
    String sjablonSjablontallUrl = "/sjablon/samvaersfradrag?all=true";
    return bidragGcpProxyConsumer.hentSjablonListe(sjablonSjablontallUrl, SJABLON_SAMVAERSFRADRAG_LISTE);
  }

  public HttpResponse<List<Bidragsevne>> hentSjablonBidragsevne() {
    String sjablonSjablontallUrl = "/sjablon/bidragsevner?all=true";
    return bidragGcpProxyConsumer.hentSjablonListe(sjablonSjablontallUrl, SJABLON_BIDRAGSEVNE_LISTE);
  }

  public HttpResponse<List<TrinnvisSkattesats>> hentSjablonTrinnvisSkattesats() {
    String sjablonSjablontallUrl = "/sjablon/trinnvisskattesats?all=true";
    return bidragGcpProxyConsumer.hentSjablonListe(sjablonSjablontallUrl, SJABLON_TRINNVIS_SKATTESATS_LISTE);
  }

  public HttpResponse<List<Barnetilsyn>> hentSjablonBarnetilsyn() {
    String sjablonSjablontallUrl = "/sjablon/barnetilsyn?all=true";
    return bidragGcpProxyConsumer.hentSjablonListe(sjablonSjablontallUrl, SJABLON_BARNETILSYN_LISTE);
  }
}
