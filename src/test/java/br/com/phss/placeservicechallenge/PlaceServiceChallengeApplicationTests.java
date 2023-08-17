package br.com.phss.placeservicechallenge;

import br.com.phss.placeservicechallenge.api.PlaceRequest;
import br.com.phss.placeservicechallenge.domain.Place;
import br.com.phss.placeservicechallenge.domain.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
class PlaceServiceChallengeApplicationTests {
	public static final Place CENTRAL_PERK = new Place(
			1L, "Central Perk", "central-perk", "NY", "NY", null, null);
	@Autowired
	WebTestClient webTestClient;
	@Autowired
	PlaceRepository placeRepository;

	@Test
	public void testCreatePlaceSuccess() {
		var name = "Valid name";
		var city = "Valid city";
		var state = "Valid State";
		var slug = "valid-name";

		webTestClient
				.post()
				.uri("/places")
				.bodyValue(
						new PlaceRequest(name, city, state)
				)
				.exchange()
				.expectBody()
				.jsonPath("name").isEqualTo(name)
				.jsonPath("city").isEqualTo(city)
				.jsonPath("state").isEqualTo(state)
				.jsonPath("slug").isEqualTo(slug)
				.jsonPath("createdAt").isNotEmpty()
				.jsonPath("updatedAt").isNotEmpty();
	}

	@Test
	public void testCreatePlaceFailure() {
		var name = "";
		var city = "";
		var state = "";

		webTestClient
				.post()
				.uri("/places")
				.bodyValue(
						new PlaceRequest(name, state, city)
				)
				.exchange()
				.expectStatus().isBadRequest();
	}

	@Test
	public void testEditPlaceSuccess() {
		final String newName = "New Name";
		final String newCity = "New City";
		final String newState = "New State";
		final String newSlug = "new-name";

		//Update all fields
		webTestClient
				.patch()
				.uri("/places/1")
				.bodyValue(
						new PlaceRequest(
								newName,
								newCity,
								newState
						)
				)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("name").isEqualTo(newName)
				.jsonPath("city").isEqualTo(newCity)
				.jsonPath("state").isEqualTo(newState)
				.jsonPath("slug").isEqualTo(newSlug)
				.jsonPath("createdAt").isNotEmpty()
				.jsonPath("updatedAt").isNotEmpty();

		//Update only name
		webTestClient
				.patch()
				.uri("/places/1")
				.bodyValue(
						new PlaceRequest(
								CENTRAL_PERK.name(),
								null,
								null
						)
				)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("name").isEqualTo(CENTRAL_PERK.name())
				.jsonPath("city").isEqualTo(newCity)
				.jsonPath("state").isEqualTo(newState)
				.jsonPath("slug").isEqualTo(CENTRAL_PERK.slug())
				.jsonPath("createdAt").isNotEmpty()
				.jsonPath("updatedAt").isNotEmpty();

		//Update only city
		webTestClient
				.patch()
				.uri("/places/1")
				.bodyValue(
						new PlaceRequest(
								null,
								CENTRAL_PERK.city(),
								null
						)
				)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("name").isEqualTo(CENTRAL_PERK.name())
				.jsonPath("city").isEqualTo(CENTRAL_PERK.city())
				.jsonPath("state").isEqualTo(newState)
				.jsonPath("slug").isEqualTo(CENTRAL_PERK.slug())
				.jsonPath("createdAt").isNotEmpty()
				.jsonPath("updatedAt").isNotEmpty();

		//Update only state
		webTestClient
				.patch()
				.uri("/places/1")
				.bodyValue(
						new PlaceRequest(
								null,
								null,
								CENTRAL_PERK.state()
						)
				)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("name").isEqualTo(CENTRAL_PERK.name())
				.jsonPath("city").isEqualTo(CENTRAL_PERK.city())
				.jsonPath("state").isEqualTo(CENTRAL_PERK.state())
				.jsonPath("slug").isEqualTo(CENTRAL_PERK.slug())
				.jsonPath("createdAt").isNotEmpty()
				.jsonPath("updatedAt").isNotEmpty();
	}

	@Test
	public void testGetFailure() {
		webTestClient
				.get()
				.uri("/places/11")
				.exchange()
				.expectStatus().isNotFound();
	}

	@Test
	public void testListAllSuccess() {
		webTestClient
				.get()
				.uri("/places")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$[0].name").isEqualTo(CENTRAL_PERK.name())
				.jsonPath("$[0].slug").isEqualTo(CENTRAL_PERK.slug())
				.jsonPath("$[0].state").isEqualTo(CENTRAL_PERK.state())
				.jsonPath("$[0].createdAt").isNotEmpty()
				.jsonPath("$[0].updatedAt").isNotEmpty();
	}

	@Test
	public void testListByNameSuccess() {
		webTestClient
				.get()
				.uri("/places?name=%s".formatted(CENTRAL_PERK.name()))
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$[0].name").isEqualTo(CENTRAL_PERK.name())
				.jsonPath("$[0].slug").isEqualTo(CENTRAL_PERK.slug())
				.jsonPath("$[0].state").isEqualTo(CENTRAL_PERK.state())
				.jsonPath("$[0].createdAt").isNotEmpty()
				.jsonPath("$[0].updatedAt").isNotEmpty();
	}

	@Test
	public void testListByNameNotFound() {
		webTestClient
				.get()
				.uri("/places?name=name")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.length()").isEqualTo(0);
	}

}
