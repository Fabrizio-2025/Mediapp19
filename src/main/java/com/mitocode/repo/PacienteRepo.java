package com.mitocode.repo;

import com.mitocode.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepo extends GenericRepo<Paciente,Integer> {

}
