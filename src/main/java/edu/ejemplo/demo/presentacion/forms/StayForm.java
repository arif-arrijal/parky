package edu.ejemplo.demo.presentacion.forms;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by admin on 7/11/2016.
 */
public class StayForm {

    private Long id;
    private String name;
    private Integer feePerMinutes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotEmpty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @NotNull
    public Integer getFeePerMinutes() {
        return feePerMinutes;
    }

    public void setFeePerMinutes(Integer feePerMinutes) {
        this.feePerMinutes = feePerMinutes;
    }
}
