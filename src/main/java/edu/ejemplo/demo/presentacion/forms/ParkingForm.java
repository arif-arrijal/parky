package edu.ejemplo.demo.presentacion.forms;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by zulfy on 30/06/16.
 */
public class ParkingForm extends UserForm {

    private String direccion;
    private String ciudad;
    private String codigoPostal;
    private String provincia;
    private String cif;
    private String telefono;
    private Long tarifa;
    private String claveAPI;

    @NotEmpty
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @NotEmpty
    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @NotEmpty
    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    @NotEmpty
    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    @NotEmpty
    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    @NotEmpty
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @NotNull
    public Long getTarifa() {
        return tarifa;
    }

    public void setTarifa(Long tarifa) {
        this.tarifa = tarifa;
    }

    @NotEmpty
    public String getClaveAPI() {
        return claveAPI;
    }

    public void setClaveAPI(String claveAPI) {
        this.claveAPI = claveAPI;
    }
}