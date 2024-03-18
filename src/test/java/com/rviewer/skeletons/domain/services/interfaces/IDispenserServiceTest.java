package com.rviewer.skeletons.domain.services.interfaces;

import com.rviewer.skeletons.domain.dto.requests.DispenserCreateRequest;
import com.rviewer.skeletons.domain.dto.requests.DispenserStatusRequest;
import com.rviewer.skeletons.domain.dto.responses.DispenserRevenueResponse;
import com.rviewer.skeletons.domain.dto.responses.UsageDto;
import com.rviewer.skeletons.domain.enums.DispenserStatusEnum;
import com.rviewer.skeletons.domain.mapper.DispenserMapper;
import com.rviewer.skeletons.domain.model.Dispenser;
import com.rviewer.skeletons.domain.model.History;
import com.rviewer.skeletons.domain.repository.DispenserRepository;
import com.rviewer.skeletons.domain.repository.HistoryRepository;
import com.rviewer.skeletons.utils.entities.TestDispenserEntityFactory;
import com.rviewer.skeletons.utils.entities.TestHistoryEntityFactory;
import com.rviewer.skeletons.utils.entities.TestRequestsEntityFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertNotNull;


@ExtendWith(MockitoExtension.class)
class IDispenserServiceTest {

    @Mock
    DispenserRepository dispenserRepository;
    @Mock
    HistoryRepository historyRepository;

    @Mock
    DispenserMapper dispenserMapper;

    @InjectMocks
    private IDispenserService dispenserService;

    @Test
    void createDispenser_ShouldReturnDispenser() {
        // Given
        DispenserCreateRequest createRequest = TestRequestsEntityFactory.createNewDispenserRequest();
        Dispenser mappedDispenser = TestDispenserEntityFactory.createDispenser();
        Dispenser savedDispenser = TestDispenserEntityFactory.createDispenser();

        when(dispenserMapper.dispenserCreateRequestToDispenser(createRequest)).thenReturn(mappedDispenser);
        when(dispenserRepository.save(mappedDispenser)).thenReturn(savedDispenser);

        // When
        Dispenser result = dispenserService.createDispenser(createRequest);

        // Then
        assertThat(result).isEqualTo(savedDispenser);
        verify(dispenserMapper).dispenserCreateRequestToDispenser(createRequest);
        verify(dispenserRepository).save(mappedDispenser);
    }

    @Test
    void changeStatus_shouldUpdateDispenserStatus() {
        // Given
        Long dispenserId = 1L;
        Dispenser existingDispenser = TestDispenserEntityFactory.createDispenser();
        existingDispenser.setId(dispenserId);

        DispenserStatusRequest statusRequest = TestRequestsEntityFactory.createDispenserStatusRequest();
        statusRequest.setDispenserId("1");

        when(dispenserRepository.save(any(Dispenser.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(dispenserRepository.findById(dispenserId)).thenReturn(Optional.of(existingDispenser));

        // When
        dispenserService.changeStatus(dispenserId, statusRequest);

        // Then
        verify(dispenserRepository).findById(dispenserId);
        verify(dispenserRepository).save(existingDispenser);
        assertEquals(DispenserStatusEnum.OPEN, existingDispenser.getStatus());
    }

    @Test
    void calculateRevenueReturnsCorrectResponse() {
        // Arrange
        Long dispenserId = 1L;
        List<History> mockHistories = TestHistoryEntityFactory.historyList();
        List<UsageDto> expectedUsages = TestHistoryEntityFactory.dispenserUsages();
        double expectedTotalRevenue = expectedUsages.stream()
                .mapToDouble(UsageDto::getTotalSpent)
                .sum();
        Dispenser mockDispenser = TestDispenserEntityFactory.createDispenser();
        mockDispenser.setId(dispenserId);

        // Set up mock behavior
        when(dispenserRepository.findById(dispenserId)).thenReturn(Optional.of(mockDispenser));
        when(historyRepository.findAllByDispenserId(dispenserId)).thenReturn(mockHistories);

        // Act
        DispenserRevenueResponse result = dispenserService.calculateRevenue(dispenserId);

        // Assert
        assertNotNull(result.toString(), "Result should not be null");
        assertEquals(expectedTotalRevenue, result.getAmount(), "Total revenue should be correctly calculated");
        assertEquals(expectedUsages.size(), result.getUsages().size(), "Usage list size should match");

        // Verify
        verify(historyRepository, times(1)).findAllByDispenserId(dispenserId);
    }

    @Test
    void calculateRevenueWithNoUsagesReturnsCorrectResponse() {
        // Arrange
        Long dispenserId = 1L;
        List<History> emptyHistoryList = new ArrayList<>();
        Dispenser mockDispenser = TestDispenserEntityFactory.createDispenser();
        mockDispenser.setId(dispenserId);

        when(dispenserRepository.findById(dispenserId)).thenReturn(Optional.of(mockDispenser));
        when(historyRepository.findAllByDispenserId(dispenserId)).thenReturn(emptyHistoryList);

        // Act
        DispenserRevenueResponse result = dispenserService.calculateRevenue(dispenserId);

        // Assert
        assertNotNull(result.toString(), "Result should not be null");
        assertEquals(0, result.getAmount(), 0.001, "Total revenue should be 0 for no usages");
        assertTrue(result.getUsages().isEmpty(), "Usages list should be empty for no usages");

        // Verify
        verify(dispenserRepository).findById(dispenserId);
        verify(historyRepository).findAllByDispenserId(dispenserId);
    }

    @Test
    void calculateRevenueWithInvalidIdThrowsException() {
        // Arrange
        Long invalidDispenserId = 999L; // Assuming 999L is an ID that does not exist

        when(dispenserRepository.findById(invalidDispenserId)).
                thenThrow(new EntityNotFoundException("Dispenser not found with id: " + invalidDispenserId));

        // Act & Assert
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            dispenserService.calculateRevenue(invalidDispenserId);
        }, "Expected calculateRevenue to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Dispenser not found with id: " +
                invalidDispenserId), "Expected exception message to contain the invalid ID");

        // Verify
        verify(dispenserRepository).findById(invalidDispenserId);
    }

}