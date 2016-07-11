package edu.ejemplo.demo.repositorios;

import edu.ejemplo.demo.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by admin on 7/11/2016.
 */
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    Province findById(Long id);
    Long countByName(String name);
    Long countByValue(Integer value);
}
