package by.gstu.computerdetails.entity;

import by.gstu.computerdetails.annotation.TableColumn;

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
    public String toString() {
        return x + " x " + y;
    }
}
