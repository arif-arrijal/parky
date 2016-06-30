package edu.ejemplo.demo.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by zulfy on 30/06/16.
 */
@Entity
@Table(name = "role")
public class Role implements Serializable{

    private static final long serialVersionUID = 3812946732650242262L;

    public static final Long ROLE_ADMIN=1L;
    public static final Long ROLE_PARKING=2L;
    public static final Long ROLE_CONDUCTOR=3L;

    private Long id;
    private String code;
    private String name;
    private Integer version;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "code", nullable = false, unique = true)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "name", length = 45, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}