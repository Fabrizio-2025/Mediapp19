package com.mitocode.controller;

import com.mitocode.dto.EspecialidadDTO;
import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Especialidad;
import com.mitocode.service.EspecialidadService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadService service;

    @Autowired
    private ModelMapper mapper;


    @GetMapping
    public ResponseEntity<List<EspecialidadDTO>> listar() throws Exception {
        List<EspecialidadDTO> lista = service.listar().stream().map(e->mapper.map(e, EspecialidadDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadDTO> listarPorId(@PathVariable("id") Integer id) throws Exception {
        EspecialidadDTO dtoResponse;
        Especialidad obj = service.listarPorId(id);
        if (obj==null) {
            throw new ModeloNotFoundException("Id No encontrado "+id);

        } else {
            dtoResponse = mapper.map(obj, EspecialidadDTO.class);
        }

        return new ResponseEntity<>(dtoResponse,HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<EspecialidadDTO> registrar(@Valid @RequestBody EspecialidadDTO dtoRequest) throws Exception{
//        Especialidad p = mapper.map(dtoRequest,Especialidad.class);
//        Especialidad obj = service.registrar(p);
//        EspecialidadDTO dtoRespons = mapper.map(obj,EspecialidadDTO.class);
//        return new ResponseEntity<>(dtoRespons,HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<Void> registrar(@Valid @RequestBody EspecialidadDTO dtoRequest) throws Exception{
        Especialidad e = mapper.map(dtoRequest,Especialidad.class);
        Especialidad obj = service.registrar(e);
        EspecialidadDTO dtoRespons = mapper.map(obj,EspecialidadDTO.class);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdEspecialidad()).toUri();
        return  ResponseEntity.created(location).build();
    }



    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadDTO> Modificar(@PathVariable("id") Integer id, @RequestBody EspecialidadDTO dtoRequest) throws Exception{
        Especialidad exa = service.listarPorId(dtoRequest.getIdEspecialidad());
        if(exa != null){
            throw new ModeloNotFoundException("Id No encontrado "+dtoRequest.getIdEspecialidad());
        }
        Especialidad e =mapper.map(dtoRequest,Especialidad.class);

        Especialidad obj = service.modificar(e);

        EspecialidadDTO dtoresponce = mapper.map(obj, EspecialidadDTO.class);

        return new ResponseEntity<>(dtoresponce,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id )throws Exception{
        Especialidad esp = service.listarPorId(id);

        if(esp != null){
            throw new ModeloNotFoundException("Id No encontrado "+id);
        }
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<EspecialidadDTO> listarHateoasPorId (@PathVariable("id") Integer id) throws Exception{

        Especialidad obj = service.listarPorId(id);
        if (obj==null) {
            throw new ModeloNotFoundException("Id No encontrado "+ id);

        }
        EspecialidadDTO dto = mapper.map(obj,EspecialidadDTO.class);

        EntityModel<EspecialidadDTO> recurso = EntityModel.of(dto);

        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).listarPorId(id));
        recurso.add(link1.withRel("Especialidad-recurso1"));

        return recurso;
    }


}
