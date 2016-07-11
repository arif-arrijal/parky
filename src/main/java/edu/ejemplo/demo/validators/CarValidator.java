package edu.ejemplo.demo.validators;

import edu.ejemplo.demo.excepciones.BusinessLogicException;
import edu.ejemplo.demo.presentacion.forms.CarForm;
import edu.ejemplo.demo.repositorios.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;

/**
 * Created by admin on 7/9/2016.
 */
@Component
public class CarValidator {

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private CarRepository carRepository;

    public void validateCar(CarForm carForm) throws IOException{

        if (carForm.getId() == null){
            if (carRepository.countByMatricula(carForm.getCarPlate().trim())>0){
                Object[] errorArgs = {carForm.getCarPlate()};
                String error = messageSource.getMessage("error.carplate.already.registered", errorArgs, null);
                throw new BusinessLogicException(error);
            }
            if (carRepository.countByNombreCoche(carForm.getCarName().trim())>0){
                Object[] errorArgs = {carForm.getCarName()};
                String error = messageSource.getMessage("error.carname.already.registered", errorArgs, null);
                throw new BusinessLogicException(error);
            }
            extractUploadedCarBytes(carForm);
            byte[] fotoInDb = carForm.getCarPictBytes();
            if ((fotoInDb == null || fotoInDb.length == 0) && carForm.getCarPictBytes().length == 0) {
                String errorMessage = messageSource.getMessage("error.empty.photo",null,null);
                throw new BusinessLogicException(errorMessage);
            }
        }

        if (carForm.getCarPlate().equals(carForm.getCarPlate2())){
        }else{
            String error = messageSource.getMessage("error.carplate.notmach.with.confirm", null, null);
            throw new BusinessLogicException(error);
        }
    }

    private void extractUploadedCarBytes(CarForm carForm) throws IOException {
        byte[] carPhotoBytes = StreamUtils.copyToByteArray(carForm.getCarPict().getInputStream());
        carForm.getCarPict().getInputStream().close();
        carForm.setCarPictBytes(carPhotoBytes);
    }
}
