package edu.ejemplo.demo.negocio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ejemplo.demo.model.Parking;
import edu.ejemplo.demo.negocio.ParkingsService;
import edu.ejemplo.demo.repositorios.ParkingRepository;
import edu.ejemplo.demo.repositorios.UserRepository;

@Service
public class ParkingsServiceImpl implements ParkingsService {

	@Autowired
	private ParkingRepository parkingsRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public Iterable<Parking> getParkings() {
		return parkingsRepository.findAll();
	}

}
