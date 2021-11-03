package ca.sfu.fluorine.parentapp.model.children;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChildTest {
	private final String firstName = "John";
	private final String lastName = "Doe";

	@Test
	void testCreationWithNormalStrings() {
		Child child = new Child(firstName, lastName);
		assertEquals(child.getFirstName(), firstName);
		assertEquals(child.getLastName(), lastName);
	}

	@Test
	void testCreationWithEmptyStrings() {
		assertThrows(IllegalArgumentException.class, () -> {
			Child child = new Child(firstName, "");
		});

		assertThrows(IllegalArgumentException.class, () -> {
			Child child = new Child("", lastName);
		});

		assertThrows(IllegalArgumentException.class, () -> {
			Child child = new Child("", "");
		});
	}
}