package com.mitocode.repo;

import com.mitocode.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepo extends GenericRepo<Medico,Integer> {

}
