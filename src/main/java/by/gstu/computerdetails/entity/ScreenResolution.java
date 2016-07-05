package by.gstu.computerdetails.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table
public class ScreenResolution extends BaseEntity implements Serializable {

    @Column(nullable = false)
    private int x;

    @Column(nullable = false)
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ScreenResolution() {
    }

    public ScreenResolution(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("x", x)
                .append("y", y)
                .toString();
    }
}
