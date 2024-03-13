package com.rviewer.skeletons.domain.model;

import com.rviewer.skeletons.domain.enums.DispenserStatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "HISTORY")
@Getter
@Setter
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dispenser_id", referencedColumnName = "id")
    private Dispenser dispenser;

    @Column(name = "opened_at")
    private LocalDateTime  openedAt;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @Column(name = "flow_volume", precision = 10, scale = 3)
    private double flowVolume;

    @Column(name = "total_spent", precision = 10, scale = 3, nullable = false)
    private double totalSpent = 0.00;
    public History() {
    }
     public History(Dispenser dispenser, LocalDateTime  openedAt, LocalDateTime  closedAt, double flowVolume, double totalSpent) {
        this.dispenser = dispenser;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.flowVolume = flowVolume;
        this.totalSpent = totalSpent;
    }

}
