package br.com.alura.AluraFake.integrationtest.base;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public abstract class IntegrationTestBase {

    @Autowired
    protected EntityManager entityManager;

    protected void clearAndFlushEntityManager() {
        entityManager.flush();
        entityManager.clear();
    }
}
