package edu.ejemplo.demo.negocio.impl;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import edu.ejemplo.demo.converter.EditCardConverter;
import edu.ejemplo.demo.excepciones.BusinessLogicException;
import edu.ejemplo.demo.model.Coche;
import edu.ejemplo.demo.model.TarjetaCredito;
import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.negocio.CardService;
import edu.ejemplo.demo.presentacion.forms.CardForm;
import edu.ejemplo.demo.repositorios.CarRepository;
import edu.ejemplo.demo.repositorios.CardDataDao;
import edu.ejemplo.demo.repositorios.TarjetaRepository;
import edu.ejemplo.demo.repositorios.UserRepository;
import edu.ejemplo.demo.validators.CardValidator;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 7/11/2016.
 */
@Service("cardService")
public class CardServiceImpl implements CardService {

    private Logger log = LoggerFactory.getLogger(CardServiceImpl.class);

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TarjetaRepository cardRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private EditCardConverter editCardConverter;
    @Autowired
    private CardValidator cardValidator;
    @Autowired
    private CardDataDao cardDataDao;

    @Override
    public TarjetaCredito saveOrUpdate(CardForm form, HttpServletRequest request) throws IOException {

        TarjetaCredito card = new TarjetaCredito();

        try {
            //validate data
            cardValidator.validateCard(form);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            User user = userRepository.findOneByEmailAndActive(name, true);
            Date expirationDate = dateFormat.parse(form.getExpirationDate());
            List<Coche> carList = carRepository.findByConductor(user);

            //save data
            if (form.getId() != null){
                card = cardRepository.findById(form.getId());
                card.setNombreTitular(form.getNameHolder());
                card.setNumeroTarjeta(form.getCreditCardNumber());
                card.setFechaCaducidad(expirationDate);
                card.setUsuario(user);
                TarjetaCredito card2 = cardRepository.save(card);
                log.info("successfully edit credit card " + card2.getNumeroTarjeta());
            }else {
                card.setNombreTitular(form.getNameHolder());
                card.setNumeroTarjeta(form.getCreditCardNumber());
                card.setFechaCaducidad(expirationDate);
                card.setUsuario(user);
                TarjetaCredito card2 = cardRepository.save(card);
                log.info("successfully saved credit card " + card2.getNumeroTarjeta());

                //set credit card for every car without credit card
                for (Coche coche : carList){
                    if (coche.getTarjetaCredito() == null){
                        coche.setTarjetaCredito(card2);
                        carRepository.save(coche);
                        log.info("successfully saved credit card " + card2.getNumeroTarjeta() + " to car " + coche.getNombreCoche());
                    }
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
            String exception = ex.getMessage();
            throw new BusinessLogicException(exception);
        }
        return card;
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, timeout = 30)
    public DataSet<TarjetaCredito> findWithDatatablesCriterias(DatatablesCriterias criterias) {
        List<TarjetaCredito> cardList =  cardDataDao.findWithDatatablesCriterias(criterias);
        Long count  = cardDataDao.getTotalCount();
        Long countFiltered = cardDataDao.getFilteredCount(criterias);
        return new DataSet<>(cardList, count, countFiltered);
    }

    @Override
    public CardForm findCardById(Long id) {
        TarjetaCredito card = cardRepository.findById(id);
        return editCardConverter.convertToEditAndDetail(card);
    }

    @Override
    public String deleteCard(Long id) {
        TarjetaCredito card = cardRepository.findById(id);
        String cardNumber = card.getNumeroTarjeta();
        User user = card.getUsuario();
        List<Coche> listCardWithThisCreditCard = carRepository.findByConductor(user);

        //check if this car is not using by a car, if using, delete credit card value from that car
        if (listCardWithThisCreditCard != null){
            for (Coche car : listCardWithThisCreditCard){
                car.setTarjetaCredito(null);
                carRepository.save(car);
                log.info("successfully unlink credit card" + card.getNumeroTarjeta() + " from car " + car.getNombreCoche());
            }
        }
        cardRepository.delete(card);
        log.info("successfully delete data credit card " + cardNumber );
        return cardNumber;
    }

    @Override
    public List<CardForm> findAll() {
        List<TarjetaCredito> creditCardList = cardRepository.findAll();
        List<CardForm> creditCardFormList = new ArrayList<>();
        for (TarjetaCredito card : creditCardList){
            CardForm form = new CardForm();
            form.setId(card.getId());
            form.setCreditCardNumber(card.getNumeroTarjeta());
            creditCardFormList.add(form);
        }
        return creditCardFormList;
    }
}
