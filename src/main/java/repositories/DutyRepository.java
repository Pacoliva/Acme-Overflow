/*
 * Copyright � 2017. All information contained here included the intellectual and technical concepts are property of Null Point Software.
 */

package repositories;

import domain.Duty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by daviddelatorre on 28/3/17.
 */
@Repository
public interface DutyRepository extends JpaRepository<Duty, Integer> {
}
