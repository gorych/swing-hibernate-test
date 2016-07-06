package by.gstu.computerdetails.entity;

import by.gstu.computerdetails.annotation.TableColumn;
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

    @TableColumn(name = "Наименование")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @TableColumn(name = "Цена, руб.")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @TableColumn(name = "Диагональ, дюйм")
    public double getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(double diagonal) {
        this.diagonal = diagonal;
    }

    @TableColumn(name = "Гарантийный период, мес")
    public int getGuaranteePeriod() {
        return guaranteePeriod;
    }

    public void setGuaranteePeriod(int guaranteePeriod) {
        this.guaranteePeriod = guaranteePeriod;
    }

    @TableColumn(name = "Разрешение")
    public String getFormatScreenResolution() {
        return screenResolution.getX() + " x " + screenResolution.getY();
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
