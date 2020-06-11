package no.nav.bidrag.beregn.bidrag.rest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BidragBeregnBidrag.class, webEnvironment = RANDOM_PORT)
class BidragBeregnBidragRestApplicationTests {

	@Test
	void contextLoads() {
	}
}
