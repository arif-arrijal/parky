package edu.ejemplo.demo.presentacion.value_object;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Created by admin on 7/8/2016.
 */
public class AddCarResult implements Serializable{

    private Integer carId;
    private Integer holderId;
    private String holderName;
    private String carPlate;
    private String carPlate2;
    private MultipartFile carPict;
    private Integer creditCardId;

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getHolderId() {
        return holderId;
    }

    public void setHolderId(Integer holderId) {
        this.holderId = holderId;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getCarPlate2() {
        return carPlate2;
    }

    public void setCarPlate2(String carPlate2) {
        this.carPlate2 = carPlate2;
    }

    public MultipartFile getCarPict() {
        return carPict;
    }

    public void setCarPict(MultipartFile carPict) {
        this.carPict = carPict;
    }

    public Integer getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Integer creditCardId) {
        this.creditCardId = creditCardId;
    }
}
