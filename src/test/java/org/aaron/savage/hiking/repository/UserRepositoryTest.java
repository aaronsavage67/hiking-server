package org.aaron.savage.hiking.repository;

import org.aaron.savage.hiking.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void clearDB() {
        userRepository.findAll().forEach(entry -> testEntityManager.remove(entry));
    }

    private UserEntity createUser() {

        return new UserEntity()
                .setUsername("user67")
                .setPassword("securePassword")
                .setEmail("email@account.com")
                .setActivated("yes")
                .setCode("123456");
    }

    @Test
    public void testThatEntryCanBeRetrieved() {

        //arrange
        UserEntity expectedUser = createUser();
        testEntityManager.persist(expectedUser);

        //act
        List<UserEntity> actualUsers = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        //assert
        assertThat(actualUsers).containsOnly(expectedUser);
    }

    @Test
    public void testThatEntryCanBeRetrievedByUsername() {

        //arrange
        UserEntity expectedUser = createUser();
        testEntityManager.persist(expectedUser);

        //act
        UserEntity actualUser = userRepository.findByUsername("user67");

        //assert
        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    public void testThatEntryCanBeRetrievedByPassword() {

        //arrange
        UserEntity expectedUser = createUser();
        testEntityManager.persist(expectedUser);

        //act
        UserEntity actualUser = userRepository.findByPassword("securePassword");

        //assert
        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    public void testThatEntryDoesNotExistWhenRetrievedByUsername() {

        //arrange

        //act
        UserEntity actualUser = userRepository.findByUsername("user67");

        //assert
        assertThat(actualUser).isNull();
    }
}