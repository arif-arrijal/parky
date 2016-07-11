package edu.ejemplo.demo.negocio.impl;

import edu.ejemplo.demo.negocio.ConductorService;
import org.springframework.stereotype.Service;

@Service
public class ConductorServiceImpl implements ConductorService {

//    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ConductorServiceImpl.class);
//
//	@Autowired
//	private UserRepository conductorRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//    @Autowired
//    private JavaMailSender mailSender;
//    @Value("${spring.mail.username}")
//    private String emailSender;
//	@Autowired
//	private SpringTemplateEngine templateEngine;
//
//	@Override
//	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class, timeout = 30)
//	public vozid registrar(User user, Conductor conductor, HttpServletRequest request) throws YaExisteException {
//
//		if (userRepository.getUserByEmail(user.getEmail()) != null) {
//			throw new YaExisteException();
//		}
//
//		userRepository.save(user);
//		conductor.setUsuario(user);
//		conductorRepository.save(conductor);
//
//		// send email
//		String baseUrl = ServletUriComponentsBuilder.fromContextPath(request).build().toString();
//		Context context = new Context();
//		context.setVariable("verifyUrl", baseUrl + "/verify/" + user.getEmail() + "/" + user.getEmailCode());
//        context.setVariable("verifyCode", user.getEmailCode());
//		MimeMessage mimeMessage = mailSender.createMimeMessage();
//		String htmlContent = templateEngine.process("email/confirmacion", context);
//		try {
//			MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//			message.setSubject("Email de confirmacion de tu cuenta en Parky.es.");
//			message.setFrom(emailSender);
//			message.setTo(user.getEmail());
//			message.setText(htmlContent, true);
//			mailSender.send(mimeMessage);
//		} catch (MessagingException e) {
//			LOGGER.error(e.getMessage(), e.getCause());
//		}
//
//	}
//
//    @Override
//    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class, timeout = 30)
//    public Boolean activate(String email, String verifyCode) {
//        User user = userRepository.findOneByEmailAndEmailCode(email, verifyCode);
//        if(user == null){
//            return false;
//        }else{
//            userRepository.activate(email);
//        }
//        return true;
//    }
}
