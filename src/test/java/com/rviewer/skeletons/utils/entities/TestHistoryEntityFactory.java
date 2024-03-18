package com.rviewer.skeletons.utils.entities;

import com.rviewer.skeletons.domain.dto.responses.UsageDto;
import com.rviewer.skeletons.domain.model.Dispenser;
import com.rviewer.skeletons.domain.model.History;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class TestHistoryEntityFactory {
    public static History createOpenDispenserRequest() {
        History newHistory = new History();
        newHistory.setDispenser(TestDispenserEntityFactory.createDispenser());
        newHistory.setOpenedAt(LocalDateTime.now());
        newHistory.setFlowVolume(0.064);
        newHistory.setTotalSpent(0.00);
        return newHistory;
    }

    public static History createCloseDispenserRequest() {
        History newHistory = new History();
        newHistory.setDispenser(TestDispenserEntityFactory.createDispenser());
        newHistory.setOpenedAt(LocalDateTime.now());
        newHistory.setClosedAt(LocalDateTime.now());
        newHistory.setFlowVolume(0.064);
        newHistory.setTotalSpent(0.01);
        return newHistory;
    }

    public static List<UsageDto> dispenserUsages() {
        List<UsageDto> usages = new ArrayList<>();

        UsageDto usage = new UsageDto();
        usage.setFlowVolume(0.064);
        usage.setTotalSpent(39.2);
        usage.setOpenedAt(convertDate("2024-01-01T02:00:00Z"));
        usage.setClosedAt(convertDate("2024-01-01T02:00:50Z"));
        usages.add(usage);

        UsageDto usage2 = new UsageDto();
        usage2.setFlowVolume(0.064);
        usage2.setTotalSpent(17.248);
        usage2.setOpenedAt(convertDate("2024-01-01T02:50:58Z"));
        usage2.setClosedAt(convertDate("2024-01-01T02:51:20Z"));
        usages.add(usage2);

        UsageDto usage3 = new UsageDto();
        usage3.setFlowVolume(0.064);
        usage3.setTotalSpent(1.23);
        usage3.setOpenedAt(LocalDateTime.now());
        usage3.setClosedAt(null);
        usages.add(usage3);

        return usages;
    }

    public static List<History> historyList() {
        List<History> historyList = new ArrayList<>();
        Dispenser dispenser = TestDispenserEntityFactory.createDispenser();
        dispenser.setId(1L);

        History history = new History();
        history.setDispenser(dispenser);
        history.setOpenedAt(convertDate("2024-01-01T02:00:00Z"));
        history.setClosedAt(convertDate("2024-01-01T02:00:50Z"));
        history.setFlowVolume(0.064);
        history.setTotalSpent(39.2);
        historyList.add(history);

        History history2 = new History();
        history2.setDispenser(dispenser);
        history2.setOpenedAt(convertDate("2024-01-01T02:50:58Z"));
        history2.setClosedAt(convertDate("2024-01-01T02:51:20Z"));
        history2.setFlowVolume(0.064);
        history2.setTotalSpent(17.248);
        historyList.add(history2);

        History history3 = new History();
        history3.setDispenser(dispenser);
        history3.setOpenedAt(LocalDateTime.now());
        history3.setClosedAt(null);
        history3.setFlowVolume(0.064);
        history3.setTotalSpent(1.23);
        historyList.add(history3);

        return historyList;
    }

    private static LocalDateTime convertDate(String date) {
        Instant instant = Instant.parse(date);
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }
}
