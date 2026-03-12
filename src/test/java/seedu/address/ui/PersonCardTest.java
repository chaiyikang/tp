package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonCardTest {

    @BeforeAll
    public static void setUp() throws Exception {
        CompletableFuture<Void> future = new CompletableFuture<>();
        try {
            Platform.startup(() -> future.complete(null));
        } catch (IllegalStateException e) {
            future.complete(null); // Toolkit already initialized
        }
        future.get();
    }

    private <T> T runOnFxThread(java.util.concurrent.Callable<T> callable) throws Exception {
        CompletableFuture<T> future = new CompletableFuture<>();
        Platform.runLater(() -> {
            try {
                future.complete(callable.call());
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        return future.get();
    }

    private String getLabelText(PersonCard personCard, String fieldName) throws Exception {
        Field field = PersonCard.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        Label label = (Label) field.get(personCard);
        return label.getText();
    }

    private int getTagCount(PersonCard personCard) throws Exception {
        Field field = PersonCard.class.getDeclaredField("tags");
        field.setAccessible(true);
        FlowPane tags = (FlowPane) field.get(personCard);
        return tags.getChildren().size();
    }

    @Test
    public void display() throws Exception {
        // no tags
        Person personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonCard personCard = runOnFxThread(() -> new PersonCard(personWithNoTags, 1));

        assertEquals("1. ", getLabelText(personCard, "id"));
        assertEquals(personWithNoTags.getName().fullName, getLabelText(personCard, "name"));
        assertEquals(personWithNoTags.getRole().roleName, getLabelText(personCard, "role"));
        assertEquals(personWithNoTags.getPhone().value, getLabelText(personCard, "phone"));
        assertEquals(personWithNoTags.getAddress().value, getLabelText(personCard, "address"));
        assertEquals(personWithNoTags.getEmail().value, getLabelText(personCard, "email"));
        assertEquals(0, getTagCount(personCard));

        // with tags
        Person personWithTags = new PersonBuilder().build();
        PersonCard personCardWithTags = runOnFxThread(() -> new PersonCard(personWithTags, 2));

        assertEquals("2. ", getLabelText(personCardWithTags, "id"));
        assertEquals(personWithTags.getName().fullName, getLabelText(personCardWithTags, "name"));
        assertEquals(personWithTags.getRole().roleName, getLabelText(personCardWithTags, "role"));
        assertEquals(personWithTags.getPhone().value, getLabelText(personCardWithTags, "phone"));
        assertEquals(personWithTags.getAddress().value, getLabelText(personCardWithTags, "address"));
        assertEquals(personWithTags.getEmail().value, getLabelText(personCardWithTags, "email"));
        assertEquals(personWithTags.getTags().size(), getTagCount(personCardWithTags));
    }

    @Test
    public void equals() throws Exception {
        Person person = new PersonBuilder().build();
        PersonCard personCard = runOnFxThread(() -> new PersonCard(person, 1));

        // same person, same index -> returns true
        PersonCard copy = runOnFxThread(() -> new PersonCard(person, 1));
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        PersonCard differentPersonCard = runOnFxThread(() -> new PersonCard(differentPerson, 1));
        assertFalse(personCard.equals(differentPersonCard));

        // same person, different index -> returns false
        PersonCard differentIndexCard = runOnFxThread(() -> new PersonCard(person, 2));
        assertFalse(personCard.equals(differentIndexCard));
    }
}
