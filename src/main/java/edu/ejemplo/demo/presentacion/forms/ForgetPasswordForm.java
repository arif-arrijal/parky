package edu.ejemplo.demo.presentacion.forms;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Created by admin on 7/12/2016.
 */
public class ForgetPasswordForm implements Serializable {

    private Long id;
    private String forgetPassword;
    private Integer checkForget;

    public Integer getCheckForget() {
        return checkForget;
    }

    public void setCheckForget(Integer checkForget) {
        this.checkForget = checkForget;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotEmpty
    public String getForgetPassword() {
        return forgetPassword;
    }

    public void setForgetPassword(String forgetPassword) {
        this.forgetPassword = forgetPassword;
    }
}
