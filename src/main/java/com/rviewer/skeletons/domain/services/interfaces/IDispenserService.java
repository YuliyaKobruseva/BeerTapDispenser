package com.rviewer.skeletons.domain.services.interfaces;

import com.rviewer.skeletons.domain.dto.requests.DispenserCreateRequest;
import com.rviewer.skeletons.domain.dto.requests.DispenserStatusRequest;
import com.rviewer.skeletons.domain.dto.responses.DispenserRevenueResponse;
import com.rviewer.skeletons.domain.enums.DispenserStatusEnum;
import com.rviewer.skeletons.domain.exceptions.InvalidStatusException;
import com.rviewer.skeletons.domain.exceptions.StatusAlreadySetException;
import com.rviewer.skeletons.domain.mapper.DispenserMapper;
import com.rviewer.skeletons.domain.model.Dispenser;
import com.rviewer.skeletons.domain.model.History;
import com.rviewer.skeletons.domain.model.PriceList;
import com.rviewer.skeletons.domain.repository.DispenserRepository;
import com.rviewer.skeletons.domain.repository.HistoryRepository;
import com.rviewer.skeletons.domain.services.DispenserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class IDispenserService implements DispenserService {

    private static final Logger logger = LoggerFactory.getLogger(IDispenserService.class);

    private final DispenserRepository dispenserRepository;
    private final HistoryRepository historyRepository;

    private final DispenserMapper dispenserMapper;

    public IDispenserService(DispenserRepository dispenserRepository, HistoryRepository historyRepository, DispenserMapper dispenserMapper) {
        this.dispenserRepository = dispenserRepository;
        this.historyRepository = historyRepository;
        this.dispenserMapper = dispenserMapper;
    }


    /**
     * @param dispenserCreateRequest
     * @return
     */
    @Override
    public Dispenser createDispenser(DispenserCreateRequest dispenserCreateRequest) {
        return dispenserRepository.save(dispenserMapper.dispenserCreateRequestToDispenser(dispenserCreateRequest));
    }

    /**
     * @param id
     * @param dispenserStatusRequest
     */
    @Override
    public void changeStatus(Long id, DispenserStatusRequest dispenserStatusRequest) {
        Dispenser dispenser = findDispenserById(id);

        DispenserStatusEnum newStatus = parseStatus(dispenserStatusRequest.getStatus());

        validateStatusChange(dispenser.getStatus(), newStatus);

        updateDispenserStatus(dispenser, newStatus);

        updateHistoryLog(dispenser, newStatus);
    }

    /**
     * @param id
     */
    @Override
    public DispenserRevenueResponse calculateRevenue(Long id) {
        findDispenserById(id);

        List<History> historyList = retrieveAllHistoryForDispenser(id);

        double totalRevenue = calculateTotalRevenue(historyList);

        DispenserRevenueResponse dispenserRevenueResponse = new DispenserRevenueResponse();
        dispenserRevenueResponse.setAmount(totalRevenue);
        dispenserRevenueResponse.setUsages(historyList);

        return dispenserRevenueResponse;
    }

    private Dispenser findDispenserById(Long id) {
        return dispenserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dispenser not found with id: " + id));
    }

    private DispenserStatusEnum parseStatus(String status) {
        try {
            return DispenserStatusEnum.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid status value: {}", status, e);
            throw new InvalidStatusException("Invalid status value: " + status);
        }
    }

    private void validateStatusChange(DispenserStatusEnum currentStatus, DispenserStatusEnum newStatus) {
        if (currentStatus == newStatus) {
            logger.debug("The status is already {}", newStatus);
            throw new StatusAlreadySetException("The status is already " + newStatus);
        }
    }

    private void updateDispenserStatus(Dispenser dispenser, DispenserStatusEnum newStatus) {
        dispenser.setStatus(newStatus);
        dispenserRepository.save(dispenser);
    }

    private void updateHistoryLog(Dispenser dispenser, DispenserStatusEnum newStatus) {
        switch (newStatus) {
            case OPENED:
                openTap(dispenser);
                break;
            case CLOSED:
                closeTap(dispenser);
                break;
            default:
                logger.error("Invalid status value: {}", newStatus);
                throw new IllegalArgumentException("Invalid status value: " + newStatus);
        }
    }
    private void openTap(Dispenser dispenser) {
        History history = new History();
        history.setDispenser(dispenser);
        history.setOpenedAt(LocalDateTime.now());
        historyRepository.save(history);
    }

    private void closeTap(Dispenser dispenser) {
        History history = findOpenHistoryForDispenser(dispenser.getId());
        history.setClosedAt(LocalDateTime.now());
        history.setTotalSpent(calculateUsageSpent(history));
        historyRepository.save(history);
    }

    private History findOpenHistoryForDispenser(Long dispenserId) {
        return historyRepository.findByDispenserId(dispenserId)
                .orElseThrow(() -> new EntityNotFoundException("History log not found for dispenser with id: " + dispenserId));
    }

    private double calculateUsageSpent(History history) {
        if (history.getClosedAt() == null) {
            return 0.00;
        } else {
            long durationInSeconds = Duration.between(history.getOpenedAt(), history.getClosedAt()).getSeconds();
            return durationInSeconds * history.getFlowVolume() * PriceList.PRICE_BEER_A;
        }
    }

    private List<History> retrieveAllHistoryForDispenser(Long dispenserId) {
        return historyRepository.findAllByDispenserId(dispenserId);
    }

    public double calculateTotalRevenue(List<History> historyList) {
        return historyList.stream()
                .mapToDouble(History::getTotalSpent)
                .sum();
    }
}
