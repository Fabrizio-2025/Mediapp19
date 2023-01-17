package com.mitocode.controller;

import com.mitocode.dto.PacienteDTO;
import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Paciente;
import com.mitocode.service.PacienteService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @Autowired
    private ModelMapper mapper;


    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listar() throws Exception {
        List<PacienteDTO> lista = service.listar().stream().map(p->mapper.map(p, PacienteDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> listarPorId(@PathVariable("id") Integer id) throws Exception {
        PacienteDTO dtoResponse;
        Paciente obj = service.listarPorId(id);
        if (obj==null) {
            throw new ModeloNotFoundException("Id No encontrado "+id);

        } else {
            dtoResponse = mapper.map(obj, PacienteDTO.class);
        }

        return new ResponseEntity<>(dtoResponse,HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<PacienteDTO> registrar(@Valid @RequestBody PacienteDTO dtoRequest) throws Exception{
//        Paciente p = mapper.map(dtoRequest,Paciente.class);
//        Paciente obj = service.registrar(p);
//        PacienteDTO dtoRespons = mapper.map(obj,PacienteDTO.class);
//        return new ResponseEntity<>(dtoRespons,HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<Void> registrar(@Valid @RequestBody PacienteDTO dtoRequest) throws Exception{
        Paciente p = mapper.map(dtoRequest,Paciente.class);
        Paciente obj = service.registrar(p);
        PacienteDTO dtoRespons = mapper.map(obj,PacienteDTO.class);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdPaciente()).toUri();
        return  ResponseEntity.created(location).build();
    }



    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> Modificar(@PathVariable("id") Integer id, @RequestBody PacienteDTO dtoRequest) throws Exception{
        Paciente pac = service.listarPorId(dtoRequest.getIdPaciente());
        if(pac != null){
            throw new ModeloNotFoundException("Id No encontrado "+dtoRequest.getIdPaciente());
        }
        Paciente p=mapper.map(dtoRequest,Paciente.class);

        Paciente obj = service.modificar(p);

        PacienteDTO dtoresponce = mapper.map(obj, PacienteDTO.class);

        return new ResponseEntity<>(dtoresponce,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id )throws Exception{
        Paciente pac = service.listarPorId(id);

        if(pac != null){
            throw new ModeloNotFoundException("Id No encontrado "+id);
        }
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<PacienteDTO> listarHateoasPorId (@PathVariable("id") Integer id) throws Exception{

        Paciente obj = service.listarPorId(id);
        if (obj==null) {
            throw new ModeloNotFoundException("Id No encontrado "+id);

        }
        PacienteDTO dto = mapper.map(obj,PacienteDTO.class);

        EntityModel<PacienteDTO> recurso = EntityModel.of(dto);

        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).listarPorId(id));
        recurso.add(link1.withRel("paciente-recurso1"));

        return recurso;
    }


}
