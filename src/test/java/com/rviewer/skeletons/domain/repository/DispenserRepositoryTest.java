package com.rviewer.skeletons.domain.repository;

import com.rviewer.skeletons.domain.enums.DispenserStatusEnum;
import com.rviewer.skeletons.domain.model.Dispenser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DispenserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DispenserRepository dispenserRepository;

    @Test
    void whenSaveDispenser_thenCorrectlyPersisted() {
        // Create a new dispenser instance
        Dispenser newDispenser = new Dispenser();

        // Set the properties of newDispenser as needed
        newDispenser.setFlowVolume(0.05);

        // Save the new dispenser object using the repository
        Dispenser savedDispenser = dispenserRepository.save(newDispenser);

        // Retrieve the persisted dispenser using TestEntityManager for verification
        Dispenser foundDispenser = entityManager.find(Dispenser.class, savedDispenser.getId());

        // Validate the results
        assertThat(foundDispenser).isNotNull();
        assertThat(foundDispenser.getFlowVolume()).isEqualTo(0.05);
        assertThat(foundDispenser.getStatus()).isEqualTo(DispenserStatusEnum.CLOSE);
    }
}