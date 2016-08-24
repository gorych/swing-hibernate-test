package by.gstu.computerdetails.entity;

import by.gstu.computerdetails.annotation.TableColumn;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table
public class MatrixType extends BaseEntity {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 200, nullable = true)
    private String description;

    @Column(nullable = false)
    private double weight;

    public MatrixType() {
    }

    public MatrixType(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public MatrixType(String name, String description, double weight) {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    @TableColumn(name = "ID", index = 0, hidden = true)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @TableColumn(name = "Наименование", index = 1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @TableColumn(name = "Описание", index = 2)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @TableColumn(name = "Вес", index = 3)
    public double getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        if (weight <= 0 || weight > 10) {
            throw new IllegalArgumentException("Matrix weight less or eq then 0 or more then 1");
        }
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MatrixType that = (MatrixType) o;

        return new EqualsBuilder()
                .append(weight, that.weight)
                .append(name, that.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(weight)
                .toHashCode();
    }

    @Override
    public String toString() {
        return name.toUpperCase();
    }
}
