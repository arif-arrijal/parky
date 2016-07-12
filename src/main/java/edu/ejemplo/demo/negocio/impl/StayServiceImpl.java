package edu.ejemplo.demo.negocio.impl;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import edu.ejemplo.demo.converter.EditStayConverter;
import edu.ejemplo.demo.excepciones.BusinessLogicException;
import edu.ejemplo.demo.model.Stay;
import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.negocio.StayService;
import edu.ejemplo.demo.presentacion.forms.StayForm;
import edu.ejemplo.demo.repositorios.StayDataDao;
import edu.ejemplo.demo.repositorios.StayRepository;
import edu.ejemplo.demo.repositorios.UserRepository;
import edu.ejemplo.demo.validators.StayValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by admin on 7/11/2016.
 */
@Service("stayService")
public class StayServiceImpl implements StayService {

    private Logger log = LoggerFactory.getLogger(StayServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StayRepository stayRepository;
    @Autowired
    private EditStayConverter editStayConverter;
    @Autowired
    private StayValidator stayValidator;
    @Autowired
    private StayDataDao stayDataDao;

    @Override
    public Stay saveOrUpdate(StayForm form, HttpServletRequest request) throws IOException {

        Stay stay = new Stay();

        try {
            //validate data
            stayValidator.validateStay(form);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            User user = userRepository.findOneByEmail(name);

            //save data
            if (form.getId() != null){
                stay = stayRepository.findById(form.getId());
                stay.setName(form.getName());
                stay.setFeePerMinutes(form.getFeePerMinutes());
                stay.setUser(user);
                stayRepository.save(stay);
            }else {
                stay.setName(form.getName());
                stay.setFeePerMinutes(form.getFeePerMinutes());
                stay.setUser(user);
                stayRepository.save(stay);
            }

        }catch (Exception ex){
            ex.printStackTrace();
            String exception = ex.getMessage();
            throw new BusinessLogicException(exception);
        }
        return stay;
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, timeout = 30)
    public DataSet<Stay> findWithDatatablesCriterias(DatatablesCriterias criterias) {
        List<Stay> stayList =  stayDataDao.findWithDatatablesCriterias(criterias);
        Long count  = stayDataDao.getTotalCount();
        Long countFiltered = stayDataDao.getFilteredCount(criterias);
        return new DataSet<>(stayList, count, countFiltered);
    }

    @Override
    public StayForm findStayById(Long id) {
        Stay stay = stayRepository.findById(id);
        return editStayConverter.convertToEditAndDetail(stay);
    }

    @Override
    public String deleteStay(Long id) {
        Stay stay = stayRepository.findById(id);
        String name = stay.getName();
        stayRepository.delete(stay);
        log.info("delete data stay " + name + " done");
        return name;
    }
}
