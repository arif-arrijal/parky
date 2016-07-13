package edu.ejemplo.demo.negocio.impl;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import edu.ejemplo.demo.converter.EditProvinceConverter;
import edu.ejemplo.demo.excepciones.BusinessLogicException;
import edu.ejemplo.demo.model.Province;
import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.negocio.ProvinceService;
import edu.ejemplo.demo.presentacion.forms.ProvinceForm;
import edu.ejemplo.demo.repositorios.ProvinceDataDao;
import edu.ejemplo.demo.repositorios.ProvinceRepository;
import edu.ejemplo.demo.repositorios.UserRepository;
import edu.ejemplo.demo.validators.ProvinceValidator;
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
@Service("provinceService")
public class ProvinceServiceImpl implements ProvinceService {

    private Logger log = LoggerFactory.getLogger(ProvinceServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private EditProvinceConverter editProvinceConverter;
    @Autowired
    private ProvinceValidator provinceValidator;
    @Autowired
    private ProvinceDataDao provinceDataDao;

    @Override
    public Province saveOrUpdate(ProvinceForm form, HttpServletRequest request) throws IOException {

        Province province = new Province();

        try {
            //validate data
            provinceValidator.validateProvince(form);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            User user = userRepository.findOneByEmailAndActive(name, true);

            //save data
            if (form.getId() != null){
                province = provinceRepository.findById(form.getId());
                province.setName(form.getName());
                province.setValue(form.getValue());
                provinceRepository.save(province);
            }else {
                province.setName(form.getName());
                province.setValue(form.getValue());
                provinceRepository.save(province);
            }

        }catch (Exception ex){
            ex.printStackTrace();
            String exception = ex.getMessage();
            throw new BusinessLogicException(exception);
        }
        return province;
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, timeout = 30)
    public DataSet<Province> findWithDatatablesCriterias(DatatablesCriterias criterias) {
        List<Province> provinceList =  provinceDataDao.findWithDatatablesCriterias(criterias);
        Long count  = provinceDataDao.getTotalCount();
        Long countFiltered = provinceDataDao.getFilteredCount(criterias);
        return new DataSet<>(provinceList, count, countFiltered);
    }

    @Override
    public ProvinceForm findProvinceById(Long id) {
        Province province = provinceRepository.findById(id);
        return editProvinceConverter.convertToEditAndDetail(province);
    }

    @Override
    public String deleteProvince(Long id) {
        Province province = provinceRepository.findById(id);
        String name = province.getName();
        provinceRepository.delete(province);
        log.info("delete data province " + name + " done");
        return name;
    }
}
