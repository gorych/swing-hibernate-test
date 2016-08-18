package bean;

import by.gstu.computerdetails.entity.Monitor;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class Cluster {

    private String description;
    private Monitor prototype;
    private List<Monitor> monitors;

    public Cluster() {
    }

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
        this.prototype = prototype;
    }

    public List<Monitor> getMonitors() {
        return monitors;
    }

    public void setMonitors(List<Monitor> monitors) {
        this.monitors = monitors;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("description", description)
                .append("prototype", prototype)
                .append("monitors", monitors)
                .toString();
    }
}