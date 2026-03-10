package com.crud.demo.repository;

import com.crud.demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    UserRepository repository;

    @Test
    public void should_find_no_users_if_repository_is_empty() {
        Iterable users = repository.findAll();

        assertThat(users).isEmpty();
    }

    @Test
    public void should_save_an_user() {
        User user = repository.save(new User(null, "Tom", "Hanks", "tom@example.com", "password123", null));

        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull().isPositive();
        assertThat(user.getFirstName()).isEqualTo("Tom");
        assertThat(user.getLastName()).isEqualTo("Hanks");
        assertThat(user.getEmail()).isEqualTo("tom@example.com");
        assertThat(user.getPassword()).isEqualTo("password123");
    }

    @Test
    public void should_find_all_users() {
        User user1 = new User(null, "Tom", "Hanks", "tom@example.com", "password123", null);
        entityManager.persist(user1);

        User user2 = new User(null, "George", "Lucas", "george@example.com", "password123", null);
        entityManager.persist(user2);

        User user3 = new User(null, "Christian", "Bale", "christian@example.com", "password123", null);
        entityManager.persist(user3);

        Iterable users = repository.findAll();

        assertThat(users).hasSize(3).contains(user1, user2, user3);
    }

    @Test
    public void should_find_user_by_id() {
        User user1 = new User(null, "Tom", "Hanks", "tom@example.com", "password123", null);
        entityManager.persist(user1);

        User user2 = new User(null, "George", "Lucas", "george@example.com", "password123", null);
        entityManager.persist(user2);

        User foundUser = repository.findById(user2.getId()).get();

        assertThat(foundUser).isEqualTo(user2);
    }

    @Test
    public void should_update_tutorial_by_id() {
        User user1 = new User(null, "Tom", "Hanks", "tom@example.com", null, null);
        entityManager.persist(user1);

        User user2 = new User(null, "George", "Lucas", "george@example.com", null, null);
        entityManager.persist(user2);

        User updatedUser = new User(null, "Christian", "Bale", "christian@example.com", null, null);
        entityManager.persist(updatedUser);

        User user = repository.findById(user2.getId()).get();
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        repository.save(user);

        User checkUser = repository.findById(user2.getId()).get();

        assertThat(checkUser.getId()).isEqualTo(user2.getId());
        assertThat(checkUser.getFirstName()).isEqualTo(user2.getFirstName());
        assertThat(checkUser.getLastName()).isEqualTo(user2.getLastName());
        assertThat(checkUser.getEmail()).isEqualTo(user2.getEmail());
    }

    @Test
    public void should_delete_tutorial_by_id() {
        User user1 = new User(null, "Tom", "Hanks", "tom@example.com", null, null);
        entityManager.persist(user1);

        User user2 = new User(null, "George", "Lucas", "george@example.com", null, null);
        entityManager.persist(user2);

        User user3 = new User(null, "Christian", "Bale", "christian@example.com", null, null);
        entityManager.persist(user3);

        repository.deleteById(user2.getId());

        Iterable users = repository.findAll();

        assertThat(users).hasSize(2).contains(user1, user3);
    }
}
