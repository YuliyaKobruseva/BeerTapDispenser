package com.rviewer.skeletons.infrastructure.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rviewer.skeletons.domain.dto.requests.DispenserCreateRequest;
import com.rviewer.skeletons.domain.dto.requests.DispenserStatusRequest;
import com.rviewer.skeletons.domain.dto.responses.DispenserRevenueResponse;
import com.rviewer.skeletons.domain.exceptions.StatusAlreadySetException;
import com.rviewer.skeletons.domain.model.Dispenser;
import com.rviewer.skeletons.domain.services.DispenserService;
import com.rviewer.skeletons.utils.entities.TestDispenserEntityFactory;
import com.rviewer.skeletons.utils.entities.TestRequestsEntityFactory;
import com.rviewer.skeletons.utils.entities.TestResponsesEntityFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = DispenserController.class)
class DispenserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DispenserService dispenserService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateDispenser() throws Exception {
        DispenserCreateRequest request = TestRequestsEntityFactory.createNewDispenserRequest();
        Dispenser expectedDispenser  = TestDispenserEntityFactory.createDispenser();
        expectedDispenser.setId(1L);

        given(dispenserService.createDispenser(any(DispenserCreateRequest.class))).willReturn(expectedDispenser);

        mockMvc.perform(MockMvcRequestBuilders.post("/dispenser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(expectedDispenser.getId()));
    }
    @Test
    void changeStatus_WithValidRequest_ShouldReturnAccepted() throws Exception {
        Long dispenserId = 1L;
        String requestJson = "{\"status\":\"OPENED\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/dispenser/" + dispenserId + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isAccepted());

        verify(dispenserService).changeStatus(eq(dispenserId), any(DispenserStatusRequest.class));
    }

    @Test
    void changeStatus_WithSameStatus_ShouldReturnConflict() throws Exception {
        Long dispenserId = 1L;
        String requestJson = "{\"status\":\"open\"}";

        doThrow(new StatusAlreadySetException("Status is already set to OPENED"))
                .when(dispenserService).changeStatus(eq(dispenserId), any(DispenserStatusRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/dispenser/" + dispenserId + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isConflict());

        verify(dispenserService).changeStatus(eq(dispenserId), any(DispenserStatusRequest.class));
    }

    @Test
    void getDispenserRevenue_WithValidId_ShouldReturnRevenueResponse() throws Exception {
        Long dispenserId = 1L;
        DispenserRevenueResponse expectedResponse = TestResponsesEntityFactory.createDispenserRevenueResponse();

        given(dispenserService.calculateRevenue(anyLong())).willReturn(expectedResponse);

        mockMvc.perform(get("/dispenser/{id}/spending", dispenserId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.amount").value(expectedResponse.getAmount()));
    }
    @Test
    void getDispenserRevenue_WithInvalidId_ShouldReturnNotFound() throws Exception {
        Long invalidDispenserId = 999L;

        given(dispenserService.calculateRevenue(anyLong())).
                willThrow(new EntityNotFoundException("Dispenser not found with id: " + invalidDispenserId));

        mockMvc.perform(get("/dispenser/{id}/spending", invalidDispenserId))
                .andExpect(status().isNotFound());
    }
}


