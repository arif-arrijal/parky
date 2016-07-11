package edu.ejemplo.demo.presentacion.forms;

import java.io.Serializable;

/**
 * Created by admin on 7/11/2016.
 */
public class FormOptionAllStrings implements Serializable{
    private String id;
    private String value;

    public FormOptionAllStrings(){

    }

    public FormOptionAllStrings(String id, String value){
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
