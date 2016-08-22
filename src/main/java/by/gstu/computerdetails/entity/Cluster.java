package by.gstu.computerdetails.entity;

import by.gstu.computerdetails.annotation.TableColumn;
import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.StringUtils;
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

    @NotNull
    @ManyToOne
    @JoinColumn(name = "monitor_id")
    private Monitor prototype;

    public Cluster() {
    }

    public Cluster(long id, String name, String description, Monitor prototype) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prototype = prototype;
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
        return prototype.getName();
    }
}
