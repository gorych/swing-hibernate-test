package by.gstu.computerdetails.entity;

import by.gstu.computerdetails.annotation.TableColumn;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    public ScreenResolution() {
    }

    public ScreenResolution(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @TableColumn(name = "Х", index = 0)
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @TableColumn(name = "У", index = 1)
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ScreenResolution that = (ScreenResolution) o;

        return new EqualsBuilder()
                .append(x, that.x)
                .append(y, that.y)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(x)
                .append(y)
                .toHashCode();
    }

    @Override
    public String toString() {
        return x + " x " + y;
    }
}
