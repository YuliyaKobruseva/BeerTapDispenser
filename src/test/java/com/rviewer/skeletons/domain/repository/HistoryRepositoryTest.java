package com.rviewer.skeletons.domain.repository;

import com.rviewer.skeletons.domain.model.Dispenser;
import com.rviewer.skeletons.domain.model.History;
import com.rviewer.skeletons.utils.entities.TestDispenserEntityFactory;
import com.rviewer.skeletons.utils.entities.TestHistoryEntityFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class HistoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HistoryRepository historyRepository;
    private Dispenser savedDispenser;

    @BeforeEach
    void setUp() {
        Dispenser dispenser = TestDispenserEntityFactory.createDispenser();
        savedDispenser = entityManager.persistFlushFind(dispenser);
    }

    @Test
    void findByDispenserId_thenReturnHistory() {
        // Setup data
        History history = TestHistoryEntityFactory.createOpenDispenserRequest();
        history.setDispenser(savedDispenser);
        entityManager.persist(history);
        entityManager.flush();

        // Execute the query method
        Optional<History> foundHistory = historyRepository.findByDispenserId(savedDispenser.getId());

        // Validate the results
        assertThat(foundHistory).isPresent();
        assertThat(foundHistory.get().getDispenser().getId()).isEqualTo(savedDispenser.getId());
    }

    @Test
    void findAllByDispenserId_thenReturnHistories() {
        // Setup data
        History history1 = TestHistoryEntityFactory.createOpenDispenserRequest();
        History history2 = TestHistoryEntityFactory.createCloseDispenserRequest();
        history1.setDispenser(savedDispenser);
        history2.setDispenser(savedDispenser);
        entityManager.persist(history1);
        entityManager.persist(history2);
        entityManager.flush();

        // Execute the query method
        List<History> histories = historyRepository.findAllByDispenserId(savedDispenser.getId());

        // Validate the results
        assertThat(histories).hasSize(2);
        assertThat(histories.get(0).getDispenser().getId()).isEqualTo(savedDispenser.getId());
        assertThat(histories.get(1).getDispenser().getId()).isEqualTo(savedDispenser.getId());
    }
    @Test
    void whenFindByDispenserIdAndOpenedAtIsNotNullAndClosedAtIsNull_thenReturnHistory() {
        // Arrange
        History history = TestHistoryEntityFactory.createOpenDispenserRequest();
        history.setDispenser(savedDispenser);
        entityManager.persist(history);
        entityManager.flush();

        // Act
        Optional<History> foundHistory = historyRepository.
                findByDispenserIdAndOpenedAtIsNotNullAndClosedAtIsNull(savedDispenser.getId());

        // Assert
        assertThat(foundHistory).isPresent();
        assertThat(foundHistory.get().getDispenser().getId()).isEqualTo(savedDispenser.getId());
        assertThat(foundHistory.get().getClosedAt()).isNull();
        assertThat(foundHistory.get().getOpenedAt()).isNotNull();
    }

    @Test
    void whenSaveHistory_thenCorrectlyPersisted() {
        // Setup data
        History newHistory = TestHistoryEntityFactory.createOpenDispenserRequest();
        newHistory.setDispenser(savedDispenser);

        // Save the new history object using the repository
        History savedHistory = historyRepository.save(newHistory);

        // Validate the results
        History foundHistory = entityManager.find(History.class, savedHistory.getId());
        assertThat(foundHistory).isNotNull();
        assertThat(foundHistory.getDispenser().getId()).isEqualTo(savedDispenser.getId());
        assertThat(foundHistory.getClosedAt()).isNull();
        assertThat(foundHistory.getFlowVolume()).isEqualTo(0.064);
        assertThat(foundHistory.getTotalSpent()).isEqualTo(0.00);
    }

}