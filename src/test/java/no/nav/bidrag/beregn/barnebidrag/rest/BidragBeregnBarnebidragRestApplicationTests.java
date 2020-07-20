package no.nav.bidrag.beregn.barnebidrag.rest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BidragBeregnBarnebidrag.class, webEnvironment = RANDOM_PORT)
class BidragBeregnBarnebidragRestApplicationTests {

	@Test
	void contextLoads() {
	}
}
