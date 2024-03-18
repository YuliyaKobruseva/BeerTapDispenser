package com.rviewer.skeletons.domain.mapper.interfaces;

import com.rviewer.skeletons.domain.dto.requests.DispenserCreateRequest;
import com.rviewer.skeletons.domain.exceptions.InvalidRequestException;
import com.rviewer.skeletons.domain.model.Dispenser;
import com.rviewer.skeletons.utils.entities.TestRequestsEntityFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IDispenserMapperTest {
    private IDispenserMapper dispenserMapper;

    @BeforeEach
    void setUp() {
        dispenserMapper = new IDispenserMapper();
    }

    @Test
    void dispenserCreateRequestToDispenser_ValidRequest_SuccessfulMapping() {
        // Given
        DispenserCreateRequest request = TestRequestsEntityFactory.createNewDispenserRequest();

        // When
        Dispenser result = dispenserMapper.dispenserCreateRequestToDispenser(request);

        // Then
        assertNotNull(result);
        assertEquals(0.064, result.getFlowVolume());
    }

    @Test
    void dispenserCreateRequestToDispenser_NullRequest_ThrowsException() {
        // Given
        DispenserCreateRequest request = null;

        // When & Then
        assertThrows(InvalidRequestException.class, () -> dispenserMapper.dispenserCreateRequestToDispenser(request));
    }

}