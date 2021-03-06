/*
 * Copyright � 2017. All information contained here included the intellectual and technical concepts are property of Null Point Software.
 */

package repositories;

import domain.Module;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by daviddelatorre on 15/5/17.
 */
public interface ModuleRepository extends JpaRepository<Module, Integer> {


}
