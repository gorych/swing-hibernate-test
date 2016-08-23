package by.gstu.computerdetails.entity;

import by.gstu.computerdetails.annotation.TableColumn;
import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table
public class Cluster extends BaseEntity {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 300, nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "monitor_id")
    private Monitor prototype;

    public Cluster() {
    }

    public Cluster(String name, String description) {
        setName(name);
        this.description = description;
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
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Monitor name= " + name + " is incorrect.");
        }
        this.name = name;
    }

    @TableColumn(name = "Описание", index = 2)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Monitor getPrototype() {
        return prototype;
    }

    public void setPrototype(Monitor prototype) {
        if (prototype == null) {
            throw new IllegalArgumentException("Prototype is null.");
        }
        this.prototype = prototype;
    }

    @TableColumn(name = "Прототип", index = 3)
    public String getStringPrototype() {
        return prototype.getDiagonal()+"";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Cluster cluster = (Cluster) o;

        return new EqualsBuilder()
                .append(id, cluster.id)
                .append(name, cluster.name)
                .append(prototype, cluster.prototype)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(prototype)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("description", description)
                .append("prototype", prototype)
                .toString();
    }
}
