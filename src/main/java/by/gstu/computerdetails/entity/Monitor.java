package by.gstu.computerdetails.entity;

import by.gstu.computerdetails.algorithm.NormalizeObject;
import by.gstu.computerdetails.annotation.TableColumn;
import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Monitor extends BaseEntity implements NormalizeObject {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private double diagonal;

    @Column(nullable = false, name = "guarantee_period")
    private int guaranteePeriod;

    @Column(nullable = false, name = "is_proto")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isProto;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "screen_resolution_id")
    private ScreenResolution screenResolution;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "matrix_type_id")
    private MatrixType matrixType;

    @OneToMany(mappedBy = "prototype", cascade = CascadeType.ALL)
    private List<Cluster> clusters = new ArrayList<Cluster>();

    public Monitor() {
    }

    public Monitor(BigDecimal price, double diagonal) {
        this.price = price;
        this.diagonal = diagonal;
    }

    public Monitor(String name, BigDecimal price, double diagonal, int guaranteePeriod, ScreenResolution screenResolution, MatrixType matrixType) {
        setName(name);
        setPrice(price);
        setDiagonal(diagonal);
        setGuaranteePeriod(guaranteePeriod);
        setScreenResolution(screenResolution);
        setMatrixType(matrixType);
        setProto(false);
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

    @TableColumn(name = "Цена, руб", index = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        double intPrice = price.doubleValue();
        if (intPrice < 0) {
            throw new IllegalArgumentException("Price less than zero.");
        }
        this.price = price;
    }

    @TableColumn(name = "Тип матрицы", index = 3)
    public MatrixType getMatrixType() {
        return matrixType;
    }

    public void setMatrixType(MatrixType matrixType) {
        if (matrixType == null) {
            throw new IllegalArgumentException("Matrix type is null.");
        }
        this.matrixType = matrixType;
    }

    @TableColumn(name = "Диагональ, дюйм", index = 4)
    public double getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(double diagonal) {
        if (diagonal < 0) {
            throw new IllegalArgumentException("Diagonal less than zero.");
        }
        this.diagonal = diagonal;
    }

    @TableColumn(name = "Гарант. период, мес", index = 5)
    public int getGuaranteePeriod() {
        return guaranteePeriod;
    }

    public void setGuaranteePeriod(int guaranteePeriod) {
        if (guaranteePeriod < 0) {
            throw new IllegalArgumentException("Guarantee period less than zero.");
        }
        this.guaranteePeriod = guaranteePeriod;
    }

    @TableColumn(name = "Разрешение", index = 6)
    public String getFormatScreenResolution() {
        return screenResolution.toString();
    }

    public ScreenResolution getScreenResolution() {
        return screenResolution;
    }

    public void setScreenResolution(ScreenResolution screenResolution) {
        if (screenResolution == null) {
            throw new IllegalArgumentException("Screen resolution is null.");
        }
        this.screenResolution = screenResolution;
    }

    public boolean isProto() {
        return isProto;
    }

    public void setProto(boolean proto) {
        isProto = proto;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(List<Cluster> clusters) {
        this.clusters = clusters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Monitor monitor = (Monitor) o;

        return new EqualsBuilder()
                .append(diagonal, monitor.diagonal)
                .append(guaranteePeriod, monitor.guaranteePeriod)
                .append(name, monitor.name)
                .append(price, monitor.price)
                .append(screenResolution, monitor.screenResolution)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(price)
                .append(diagonal)
                .append(guaranteePeriod)
                .append(screenResolution)
                .toHashCode();
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


    public double[] getSignValues() {
        int resolutionSum = screenResolution.getX() + screenResolution.getY();
        return new double[]{
                price.doubleValue(), guaranteePeriod, diagonal, resolutionSum, matrixType.getWeight()
                // price.doubleValue(), diagonal
        };
    }

    public int signCount() {
        return 5;
        //return 2;
    }
}
