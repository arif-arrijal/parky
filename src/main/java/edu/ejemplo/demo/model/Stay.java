package edu.ejemplo.demo.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by admin on 7/11/2016.
 */
@Entity
@Table(name = "stay")
public class Stay implements Serializable {

    private static final long serialVersionUID = 4666318272270682897L;
    private Long id;
    private String name;
    private Integer feePerMinutes;
    private Integer version;
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "stay_seq_gen")
    @SequenceGenerator(name = "stay_seq_gen", sequenceName = "stay_seq")
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

    @Column(name = "fee", nullable = false)
    public Integer getFeePerMinutes() {
        return feePerMinutes;
    }

    public void setFeePerMinutes(Integer feePerMinutes) {
        this.feePerMinutes = feePerMinutes;
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
