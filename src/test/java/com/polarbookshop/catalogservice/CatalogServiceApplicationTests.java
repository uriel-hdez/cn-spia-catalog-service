package com.polarbookshop.catalogservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.polarbookshop.catalogservice.domain.Book;

@SpringBootTest(
		webEnvironment = WebEnvironment.RANDOM_PORT
)
class CatalogServiceApplicationTests {
	
	@Autowired
	private WebTestClient webTestClient;

	@Test
	void whenPostRequestThenBookCreated() {
		var expectedBook = new Book("1231231231", "Title", "Author", 9.90);
		webTestClient
			.post()
			.uri("/books")
			.bodyValue(expectedBook)
			.exchange()
			.expectStatus().isCreated()
			.expectBody(Book.class).value(createdBook -> {
				assertThat(createdBook).isNotNull();
				assertThat(createdBook.isbn())
				  .isEqualTo(expectedBook.isbn());
			});
	}

}
