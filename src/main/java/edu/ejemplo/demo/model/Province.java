package edu.ejemplo.demo.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by admin on 7/11/2016.
 */
@Entity
@Table(name = "province")
public class Province implements Serializable {

    private static final long serialVersionUID = 4666318272270682897L;
    private Long id;
    private String name;
    private Integer value;
    private Integer version;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "province_seq_gen")
    @SequenceGenerator(name = "province_seq_gen", sequenceName = "province_seq")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "value", nullable = false)
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
