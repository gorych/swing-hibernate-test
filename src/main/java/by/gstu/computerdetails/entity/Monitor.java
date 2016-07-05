package by.gstu.computerdetails.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table
public class Monitor extends BaseEntity implements Serializable {

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private double diagonal;

    @Column(nullable = false, name = "guarantee_period")
    private int guaranteePeriod;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "screen_resolution_id")
    private ScreenResolution screenResolution;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("price", price)
                .append("diagonal", diagonal)
                .append("guaranteePeriod", guaranteePeriod)
                .append("screenResolution", screenResolution)
                .toString();
    }
}
