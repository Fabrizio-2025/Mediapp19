package com.mitocode.controller;

import com.mitocode.dto.MedicoDTO;
import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Medico;
import com.mitocode.service.MedicoService;
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
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoService service;

    @Autowired
    private ModelMapper mapper;


    @GetMapping
    public ResponseEntity<List<MedicoDTO>> listar() throws Exception {
        List<MedicoDTO> lista = service.listar().stream().map(p->mapper.map(p, MedicoDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoDTO> listarPorId(@PathVariable("id") Integer id) throws Exception {
        MedicoDTO dtoResponse;
        Medico obj = service.listarPorId(id);
        if (obj==null) {
            throw new ModeloNotFoundException("Id No encontrado "+id);

        } else {
            dtoResponse = mapper.map(obj, MedicoDTO.class);
        }

        return new ResponseEntity<>(dtoResponse,HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<MedicoDTO> registrar(@Valid @RequestBody MedicoDTO dtoRequest) throws Exception{
//        Medico p = mapper.map(dtoRequest,Medico.class);
//        Medico obj = service.registrar(p);
//        MedicoDTO dtoRespons = mapper.map(obj,MedicoDTO.class);
//        return new ResponseEntity<>(dtoRespons,HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<Void> registrar(@Valid @RequestBody MedicoDTO dtoRequest) throws Exception{
        Medico m = mapper.map(dtoRequest,Medico.class);
        Medico obj = service.registrar(m);
        MedicoDTO dtoRespons = mapper.map(obj,MedicoDTO.class);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdMedico()).toUri();
        return  ResponseEntity.created(location).build();
    }



    @PutMapping("/{id}")
    public ResponseEntity<MedicoDTO> Modificar(@PathVariable("id") Integer id, @RequestBody MedicoDTO dtoRequest) throws Exception{
        Medico med = service.listarPorId(dtoRequest.getIdMedico());
        if(med != null){
            throw new ModeloNotFoundException("Id No encontrado "+dtoRequest.getIdMedico());
        }
        Medico m =mapper.map(dtoRequest,Medico.class);

        Medico obj = service.modificar(m);

        MedicoDTO dtoresponce = mapper.map(obj, MedicoDTO.class);

        return new ResponseEntity<>(dtoresponce,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id )throws Exception{
        Medico med = service.listarPorId(id);

        if(med != null){
            throw new ModeloNotFoundException("Id No encontrado "+id);
        }
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<MedicoDTO> listarHateoasPorId (@PathVariable("id") Integer id) throws Exception{

        Medico obj = service.listarPorId(id);
        if (obj==null) {
            throw new ModeloNotFoundException("Id No encontrado "+id);

        }
        MedicoDTO dto = mapper.map(obj,MedicoDTO.class);

        EntityModel<MedicoDTO> recurso = EntityModel.of(dto);

        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).listarPorId(id));
        recurso.add(link1.withRel("Medico-recurso1"));

        return recurso;
    }


}
