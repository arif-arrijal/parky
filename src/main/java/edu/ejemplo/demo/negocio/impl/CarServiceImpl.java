package edu.ejemplo.demo.negocio.impl;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import edu.ejemplo.demo.converter.EditCarConverter;
import edu.ejemplo.demo.excepciones.BusinessLogicException;
import edu.ejemplo.demo.model.Coche;
import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.negocio.CarService;
import edu.ejemplo.demo.presentacion.forms.CarForm;
import edu.ejemplo.demo.repositorios.CarDataDao;
import edu.ejemplo.demo.repositorios.CarRepository;
import edu.ejemplo.demo.repositorios.UserRepository;
import edu.ejemplo.demo.validators.CarValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by admin on 7/8/2016.
 */

@Service("carService")
public class CarServiceImpl implements CarService {

    private Logger log = LoggerFactory.getLogger(CarServiceImpl.class);

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CarValidator carValidator;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private EditCarConverter editCarConverter;
    @Autowired
    private CarDataDao carDataDao;


    @Override
    public Coche saveOrUpdate(CarForm form, HttpServletRequest request){
        Coche coche = new Coche();

        try {
            //validate data
            carValidator.validateCar(form);
            extractUploadedFotoBytes(form);
            byte[] bytes = form.getCarPictBytes();
            String filename = form.getCarPlate() + "_" + form.getCarName();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            User user = userRepository.findOneByEmail(name);

                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + filename);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                log.info("Server File Location="
                        + serverFile.getAbsolutePath());

                log.info("You successfully uploaded file=" + filename);

            //save data
            if (form.getId() != null){
                coche = carRepository.findById(form.getId());
                coche.setMatricula(form.getCarPlate());
                coche.setActivo(false);
                coche.setMatriculaValidada(false);
                coche.setConductor(user);
                coche.setFotoPermiso(serverFile.getAbsolutePath());
                coche.setNombreCoche(form.getCarName());
                carRepository.save(coche);
            }else {
                coche.setMatricula(form.getCarPlate());
                coche.setActivo(false);
                coche.setMatriculaValidada(false);
                coche.setConductor(user);
                coche.setFotoPermiso(serverFile.getAbsolutePath());
                coche.setNombreCoche(form.getCarName());
                carRepository.save(coche);
            }

        }catch (IOException io){
            io.printStackTrace();
            String error = messageSource.getMessage("error.failed.upload", null, null);
            throw new BusinessLogicException(error);
        }catch (Exception ex){
            String exception = ex.getMessage();
            throw new BusinessLogicException(exception);
        }
        return coche;
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, timeout = 30)
    public DataSet<Coche> findWithDatatablesCriterias(DatatablesCriterias criterias) {
        List<Coche> carList = carDataDao.findWithDatatablesCriterias(criterias);
        Long count  = carDataDao.getTotalCount();
        Long countFiltered = carDataDao.getFilteredCount(criterias);
        return new DataSet<>(carList, count, countFiltered);
    }

    @Override
    public CarForm findCarById(Long id) {
        Coche coche = carRepository.findById(id);
        CarForm carForm = editCarConverter.convertToEditAndDetail(coche);
        return carForm;
    }

    private void extractUploadedFotoBytes(CarForm carForm) throws IOException {
        byte[] carPhotoBytes = StreamUtils.copyToByteArray(carForm.getCarPict().getInputStream());
        carForm.getCarPict().getInputStream().close();
        carForm.setCarPictBytes(carPhotoBytes);
    }

}
