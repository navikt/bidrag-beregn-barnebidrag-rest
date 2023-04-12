package no.nav.bidrag.beregn.barnebidrag.rest;

import no.nav.bidrag.beregn.barnebidrag.BarnebidragCore;
import no.nav.bidrag.beregn.barnebidrag.rest.consumer.SjablonConsumer;
import no.nav.bidrag.beregn.barnebidrag.rest.mapper.BPAndelUnderholdskostnadCoreMapper;
import no.nav.bidrag.beregn.barnebidrag.rest.mapper.BarnebidragCoreMapper;
import no.nav.bidrag.beregn.barnebidrag.rest.mapper.BidragsevneCoreMapper;
import no.nav.bidrag.beregn.barnebidrag.rest.mapper.ForholdsmessigFordelingCoreMapper;
import no.nav.bidrag.beregn.barnebidrag.rest.mapper.NettoBarnetilsynCoreMapper;
import no.nav.bidrag.beregn.barnebidrag.rest.mapper.SamvaersfradragCoreMapper;
import no.nav.bidrag.beregn.barnebidrag.rest.mapper.UnderholdskostnadCoreMapper;
import no.nav.bidrag.beregn.bidragsevne.BidragsevneCore;
import no.nav.bidrag.beregn.bpsandelunderholdskostnad.BPsAndelUnderholdskostnadCore;
import no.nav.bidrag.beregn.forholdsmessigfordeling.ForholdsmessigFordelingCore;
import no.nav.bidrag.beregn.nettobarnetilsyn.NettoBarnetilsynCore;
import no.nav.bidrag.beregn.samvaersfradrag.SamvaersfradragCore;
import no.nav.bidrag.beregn.underholdskostnad.UnderholdskostnadCore;
import no.nav.bidrag.commons.ExceptionLogger;
import no.nav.bidrag.commons.web.CorrelationIdFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeregnBarnebidragConfig {

  @Bean
  public BidragsevneCore bidragsevneCore() {
    return BidragsevneCore.getInstance();
  }

  @Bean
  public NettoBarnetilsynCore nettoBarnetilsynCore() {
    return NettoBarnetilsynCore.getInstance();
  }

  @Bean
  public UnderholdskostnadCore underholdskostnadCore() {
    return UnderholdskostnadCore.getInstance();
  }

  @Bean
  public BPsAndelUnderholdskostnadCore bpAndelUnderholdskostnadCore() {
    return BPsAndelUnderholdskostnadCore.getInstance();
  }

  @Bean
  public SamvaersfradragCore samvaersfradragCore() {
    return SamvaersfradragCore.getInstance();
  }

  @Bean
  public BarnebidragCore barnebidragCore() {
    return BarnebidragCore.getInstance();
  }

  @Bean
  public ForholdsmessigFordelingCore forholdsmessigFordelingCore() {
    return ForholdsmessigFordelingCore.getInstance();
  }

  @Bean
  public SjablonConsumer sjablonConsumer(@Value("${BIDRAGSJABLON_URL}") String sjablonBaseUrl, RestTemplate restTemplate) {
    return new SjablonConsumer(restTemplate, sjablonBaseUrl);
  }

  @Bean
  public BidragsevneCoreMapper bidragsevneCoreMapper() {
    return new BidragsevneCoreMapper();
  }

  @Bean
  public NettoBarnetilsynCoreMapper nettoBarnetilsynCoreMapper() {
    return new NettoBarnetilsynCoreMapper();
  }

  @Bean
  public UnderholdskostnadCoreMapper underholdskostnadCoreMapper() {
    return new UnderholdskostnadCoreMapper();
  }

  @Bean
  public BPAndelUnderholdskostnadCoreMapper bpAndelUnderholdskostnadCoreMapper() {
    return new BPAndelUnderholdskostnadCoreMapper();
  }

  @Bean
  public SamvaersfradragCoreMapper samvaersfradragCoreMapper() {
    return new SamvaersfradragCoreMapper();
  }

  @Bean
  public BarnebidragCoreMapper barnebidragCoreMapper() {
    return new BarnebidragCoreMapper();
  }

  @Bean
  public ForholdsmessigFordelingCoreMapper forholdsmessigFordelingCoreMapper() {
    return new ForholdsmessigFordelingCoreMapper();
  }

  @Bean
  public ExceptionLogger exceptionLogger() {
    return new ExceptionLogger(BidragBeregnBarnebidrag.class.getSimpleName());
  }

  @Bean
  public CorrelationIdFilter correlationIdFilter() {
    return new CorrelationIdFilter();
  }

}
