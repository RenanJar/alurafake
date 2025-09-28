package br.com.alura.AluraFake.infra.repository;

import br.com.alura.AluraFake.domain.enumeration.Role;
import br.com.alura.AluraFake.domain.user.entity.User;
import br.com.alura.AluraFake.integrationtest.base.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryIntegrationTest extends IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;


    @Test
    void shouldReturnTrueWhenEmailExists() {
        boolean exists = userRepository.existsByEmail("caio@alura.com.br");
        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseWhenEmailDoesNotExist() {
        boolean exists = userRepository.existsByEmail("outro@example.com");
        assertFalse(exists);
    }

    @Test
    void shouldFindUserByEmail() {
        Optional<User> found = userRepository.findByEmail("caio@alura.com.br");
        assertTrue(found.isPresent());
        assertEquals("Caio", found.get().getName());
        assertEquals(Role.STUDENT, found.get().getRole());
    }

    @Test
    void shouldReturnEmptyWhenEmailNotFound() {
        Optional<User> found = userRepository.findByEmail("naoexiste@example.com");
        assertTrue(found.isEmpty());
    }
}