package by.gstu.computerdetails.entity;

import by.gstu.computerdetails.annotation.TableColumn;
import org.apache.commons.lang3.StringUtils;
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

    public Monitor() {
    }

    public Monitor(String name, BigDecimal price, double diagonal, int guaranteePeriod, ScreenResolution screenResolution) {
        setName(name);
        setPrice(price);
        setDiagonal(diagonal);
        setGuaranteePeriod(guaranteePeriod);
        this.screenResolution = screenResolution;
    }

    @TableColumn(name = "Наименование", index = 0)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Monitor name= " + name + " is incorrect.");
        }
        this.name = name;
    }

    @TableColumn(name = "Цена, руб", index = 1)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        long intPrice = price.longValue();
        if (intPrice < 0) {
            throw new IllegalArgumentException("Price less than zero.");
        }
        this.price = price;
    }

    @TableColumn(name = "Диагональ, дюйм", index = 2)
    public double getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(double diagonal) {
        if (diagonal < 0) {
            throw new IllegalArgumentException("Diagonal less than zero.");
        }
        this.diagonal = diagonal;
    }

    @TableColumn(name = "Гарант. период, мес", index = 3)
    public int getGuaranteePeriod() {
        return guaranteePeriod;
    }

    public void setGuaranteePeriod(int guaranteePeriod) {
        if (guaranteePeriod < 0) {
            throw new IllegalArgumentException("Guarantee period less than zero.");
        }
        this.guaranteePeriod = guaranteePeriod;
    }

    @TableColumn(name = "Разрешение", index = 4)
    public String getFormatScreenResolution() {
        return screenResolution.toString();
    }

    public ScreenResolution getScreenResolution() {
        return screenResolution;
    }

    public void setScreenResolution(ScreenResolution screenResolution) {
        this.screenResolution = screenResolution;
    }

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
