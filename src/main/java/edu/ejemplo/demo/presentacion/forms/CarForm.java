package edu.ejemplo.demo.presentacion.forms;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by admin on 7/7/2016.
 */
public class CarForm {

    private Long id;
    private String holderId;
    private String holderName;
    private String carPlate;
    private String carName;
    private Integer creditCardId;
    private String carPlate2;
    private MultipartFile carPict;
    private byte[] carPictBytes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotEmpty
    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public byte[] getCarPictBytes() {
        return carPictBytes;
    }

    public void setCarPictBytes(byte[] carPictBytes) {
        this.carPictBytes = carPictBytes;
    }

    public String getHolderId() {
        return holderId;
    }

    public void setHolderId(String holderId) {
        this.holderId = holderId;
    }

//    @NotEmpty
    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    @NotEmpty
    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    @NotEmpty
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
