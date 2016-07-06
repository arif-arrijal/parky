package edu.ejemplo.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable{

    private static final long serialVersionUID = 2042799731106514446L;
    public static final String SITE_KEY = "6LdaMSQTAAAAAIgXlcJkKfcMSkGIEPZK-NRHxTU8";
    public static final String SECRET_KEY = "6LdaMSQTAAAAADuYSPx8yLa8ydBRP9SbYmN3IpGC";

	private Long id;
    private String nombre;
	private String email;
	private String password;
	private Set<Role> roles;
	private String emailCode; //the code to be sent to the user to validate the address
	private Boolean emailVerificado; //will be false until email is verified from the activation code /link
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

    @Column(name = "nombre", nullable = false)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "email", unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_role",joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Column(name = "email_code", unique = true)
    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }

    @Column(name = "email_verificado")
    public Boolean getEmailVerificado() {
        return emailVerificado;
    }

    public void setEmailVerificado(Boolean emailVerificado) {
        this.emailVerificado = emailVerificado;
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}