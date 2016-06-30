package edu.ejemplo.demo.negocio.impl;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import edu.ejemplo.demo.model.Role;
import edu.ejemplo.demo.repositorios.ParkingDataDao;
import edu.ejemplo.demo.repositorios.RoleRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.ejemplo.demo.excepciones.YaExisteException;
import edu.ejemplo.demo.model.Parking;
import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.negocio.ParkingsService;
import edu.ejemplo.demo.repositorios.ParkingRepository;
import edu.ejemplo.demo.repositorios.UserRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "parkingService")
public class ParkingsServiceImpl implements ParkingsService {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ParkingsServiceImpl.class);

	@Autowired
	private ParkingRepository parkingsRepository;
	@Autowired
	private ParkingDataDao parkingDataDao;
	@Autowired
	private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String emailSender;


	@Override
	@Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, timeout = 30)
	public DataSet<Parking> findWithDatatablesCriterias(DatatablesCriterias criterias) {
		List<Parking> parkingList = parkingDataDao.findWithDatatablesCriterias(criterias);
		Long count  = parkingDataDao.getTotalCount();
		Long countFiltered = parkingDataDao.getFilteredCount(criterias);
		return new DataSet<>(parkingList, count, countFiltered);
	}

	@Override
	public Iterable<Parking> getParkings() {
		return parkingsRepository.findAll();
	}

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, timeout = 30)
    public Parking saveOrUpdate(Parking entity, HttpServletRequest request) {
        Parking parking;

        boolean sentEmail = false;
        if(entity.getId() != null){
            parking = parkingsRepository.findOne(entity.getId());
            BeanUtils.copyProperties(entity, parking);
        }else{
            Role roleParking = roleRepository.findOne(Role.ROLE_PARKING);
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(roleParking);
            parking = entity;
            parking.setRoles(roleSet);
            parking.setEmailCode(RandomStringUtils.randomAlphanumeric(6));
            sentEmail = true;
        }

        if(entity.getPassword() != null  && entity.getPassword() != ""){
            String hashPassword = passwordEncoder.encode(entity.getPassword());
            parking.setPassword(hashPassword);
        }

        parking = parkingsRepository.save(parking);

        if(sentEmail){
            // send email
            String baseUrl = ServletUriComponentsBuilder.fromContextPath(request).build().toString();
            Context context = new Context();
            context.setVariable("verifyCode", parking.getEmailCode());
            context.setVariable("verifyUrl", baseUrl + "/verify/" + parking.getEmail() + "/" + parking.getEmailCode());
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            String htmlContent = templateEngine.process("email/confirmacion", context);
            try {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setSubject("Email de confirmacion de tu cuenta en Parky.es.");
                message.setFrom(emailSender);
                message.setTo(parking.getEmail());
                message.setText(htmlContent, true);
                mailSender.send(mimeMessage);
            } catch (MessagingException e) {
                LOGGER.error(e.getMessage(), e.getCause());
            }
        }

        return parking;
    }


    public void registrar(User user, Parking parking) throws YaExisteException {
		
		if (userRepository.getUserByEmail(user.getEmail()) != null) {
			throw new YaExisteException();
		}
		
		userRepository.save(user);
//		parking.setUsuario(user);
		parkingsRepository.save(parking);		
	}

}
