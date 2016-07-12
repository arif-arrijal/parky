package edu.ejemplo.demo.repositorios;

import edu.ejemplo.demo.model.Stay;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by admin on 7/11/2016.
 */
public interface StayRepository extends JpaRepository<Stay, Long> {
    Stay findById(Long id);
    Long countByName(String name);
    Long countByFeePerMinutes(Integer feePerMinutes);
}
