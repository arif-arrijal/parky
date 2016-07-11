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
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        String ROOT = "upload";

        Coche coche = new Coche();

        try {
            //validate data
            carValidator.validateCar(form);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            User user = userRepository.findOneByEmail(name);

            String filename = "";

            if (!form.getCarPict().isEmpty()){
                String extension = FilenameUtils.getExtension(form.getCarPict().getOriginalFilename());
                log.info("extension obj " + extension);
                filename = RandomStringUtils.randomAlphanumeric(10)+ "." + extension;

                // Creating the directory to store file
                File dir = new File(ROOT);
                if (!dir.exists())
                    dir.mkdirs();
                Files.copy(form.getCarPict().getInputStream(), Paths.get(ROOT, filename));
                log.info("successfully save file " + filename + " to directory " + ROOT);
            }


            //save data
            if (form.getId() != null){
                coche = carRepository.findById(form.getId());
                coche.setMatricula(form.getCarPlate());
                coche.setActivo(false);
                coche.setMatriculaValidada(false);
                coche.setConductor(user);
                coche.setNombreCoche(form.getCarName());
                if (!form.getCarPict().isEmpty()){
                    Files.deleteIfExists(Paths.get(ROOT, coche.getFotoPermiso()));
                    coche.setFotoPermiso(filename);
                }
                carRepository.save(coche);
            }else {
                coche.setMatricula(form.getCarPlate());
                coche.setActivo(false);
                coche.setMatriculaValidada(false);
                coche.setConductor(user);
                coche.setNombreCoche(form.getCarName());
                coche.setFotoPermiso(filename);
                carRepository.save(coche);
            }

        }catch (FileAlreadyExistsException io){
            io.printStackTrace();
            Object[] args = {form.getCarPict().getOriginalFilename()};
            String error = messageSource.getMessage("error.file.already.exist", args, null);
            throw new BusinessLogicException(error);
        }catch (Exception ex){
            ex.printStackTrace();
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
        return editCarConverter.convertToEditAndDetail(coche);
    }

    @Override
    public byte[] getCarPhoto(Long id) {
        Coche coche = carRepository.findById(id);
        byte[] file = new byte[0];
            try {
                Path path = Paths.get("upload", coche.getFotoPermiso());
                InputStream is = Files.newInputStream(path);
                file = IOUtils.toByteArray(is);
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        
        return file;
    }
}
