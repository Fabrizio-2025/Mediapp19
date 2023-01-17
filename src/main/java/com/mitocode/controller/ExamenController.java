package com.mitocode.controller;

import com.mitocode.dto.ExamenDTO;
import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Examen;
import com.mitocode.service.ExamenService;
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
@RequestMapping("/examenes")
public class ExamenController {

    @Autowired
    private ExamenService service;

    @Autowired
    private ModelMapper mapper;


    @GetMapping
    public ResponseEntity<List<ExamenDTO>> listar() throws Exception {
        List<ExamenDTO> lista = service.listar().stream().map(e->mapper.map(e, ExamenDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamenDTO> listarPorId(@PathVariable("id") Integer id) throws Exception {
        ExamenDTO dtoResponse;
        Examen obj = service.listarPorId(id);
        if (obj==null) {
            throw new ModeloNotFoundException("Id No encontrado "+id);

        } else {
            dtoResponse = mapper.map(obj, ExamenDTO.class);
        }

        return new ResponseEntity<>(dtoResponse,HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<ExamenDTO> registrar(@Valid @RequestBody ExamenDTO dtoRequest) throws Exception{
//        Examen p = mapper.map(dtoRequest,Examen.class);
//        Examen obj = service.registrar(p);
//        ExamenDTO dtoRespons = mapper.map(obj,ExamenDTO.class);
//        return new ResponseEntity<>(dtoRespons,HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<Void> registrar(@Valid @RequestBody ExamenDTO dtoRequest) throws Exception{
        Examen e = mapper.map(dtoRequest,Examen.class);
        Examen obj = service.registrar(e);
        ExamenDTO dtoRespons = mapper.map(obj,ExamenDTO.class);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdExamen()).toUri();
        return  ResponseEntity.created(location).build();
    }



    @PutMapping("/{id}")
    public ResponseEntity<ExamenDTO> Modificar(@PathVariable("id") Integer id, @RequestBody ExamenDTO dtoRequest) throws Exception{
        Examen exa = service.listarPorId(dtoRequest.getIdExamen());
        if(exa != null){
            throw new ModeloNotFoundException("Id No encontrado "+dtoRequest.getIdExamen());
        }
        Examen e =mapper.map(dtoRequest,Examen.class);

        Examen obj = service.modificar(e);

        ExamenDTO dtoresponce = mapper.map(obj, ExamenDTO.class);

        return new ResponseEntity<>(dtoresponce,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id )throws Exception{
        Examen exa = service.listarPorId(id);

        if(exa != null){
            throw new ModeloNotFoundException("Id No encontrado "+id);
        }
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<ExamenDTO> listarHateoasPorId (@PathVariable("id") Integer id) throws Exception{

        Examen obj = service.listarPorId(id);
        if (obj==null) {
            throw new ModeloNotFoundException("Id No encontrado "+ id);

        }
        ExamenDTO dto = mapper.map(obj,ExamenDTO.class);

        EntityModel<ExamenDTO> recurso = EntityModel.of(dto);

        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).listarPorId(id));
        recurso.add(link1.withRel("Examen-recurso1"));

        return recurso;
    }


}
