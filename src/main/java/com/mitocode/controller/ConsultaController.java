package com.mitocode.controller;

import com.mitocode.dto.ConsultaDTO;
import com.mitocode.dto.ConsultaListaExamenDTO;
import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Consulta;
import com.mitocode.model.Examen;
import com.mitocode.repo.ConsultaRepo;
import com.mitocode.service.ConsultaService;
import io.swagger.v3.core.util.ReferenceTypeUtils;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
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
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService service;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ConsultaRepo consultaRepo;


    @GetMapping
    public ResponseEntity<List<ConsultaDTO>> listar() throws Exception {
        List<ConsultaDTO> lista = service.listar().stream().map(c->mapper.map(c, ConsultaDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> listarPorId(@PathVariable("id") Integer id) throws Exception {
        ConsultaDTO dtoResponse;
        Consulta obj = service.listarPorId(id);
        if (obj==null) {
            throw new ModeloNotFoundException("Id No encontrado "+id);

        } else {
            dtoResponse = mapper.map(obj, ConsultaDTO.class);
        }

        return new ResponseEntity<>(dtoResponse,HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<ConsultaDTO> registrar(@Valid @RequestBody ConsultaDTO dtoRequest) throws Exception{
//        Consulta p = mapper.map(dtoRequest,Consulta.class);
//        Consulta obj = service.registrar(p);
//        ConsultaDTO dtoRespons = mapper.map(obj,ConsultaDTO.class);
//        return new ResponseEntity<>(dtoRespons,HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<Void> registrar(@Valid @RequestBody ConsultaListaExamenDTO dtoRequest) throws Exception{
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        Consulta c = mapper.map(dtoRequest,Consulta.class);
        List<Examen> examenes = mapper.map(dtoRequest.getLstExamen(),new TypeToken <List<Examen>>(){}.getType());

        Consulta obj = service.registrarTransaccional(c,examenes);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdConsulta()).toUri();
        //Consulta obj = service.registrar(c);
        //ConsultaDTO dtoRespons = mapper.map(obj,ConsultaDTO.class);
        //URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdConsulta()).toUri();
        //return  ResponseEntity.created(location).build();
        return ResponseEntity.created(location).build();
    }



    @PutMapping("/{id}")
    public ResponseEntity<ConsultaDTO> Modificar(@PathVariable("id") Integer id, @RequestBody ConsultaDTO dtoRequest) throws Exception{
        Consulta con = service.listarPorId(dtoRequest.getIdConsulta());
        if(con != null){
            throw new ModeloNotFoundException("Id No encontrado "+dtoRequest.getIdConsulta());
        }
        Consulta c =mapper.map(dtoRequest,Consulta.class);

        Consulta obj = service.modificar(c);

        ConsultaDTO dtoresponce = mapper.map(obj, ConsultaDTO.class);

        return new ResponseEntity<>(dtoresponce,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id )throws Exception{
        Consulta con = service.listarPorId(id);

        if(con != null){
            throw new ModeloNotFoundException("Id No encontrado "+id);
        }
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<ConsultaDTO> listarHateoasPorId (@PathVariable("id") Integer id) throws Exception{

        Consulta obj = service.listarPorId(id);
        if (obj==null) {
            throw new ModeloNotFoundException("Id No encontrado "+ id);

        }
        ConsultaDTO dto = mapper.map(obj,ConsultaDTO.class);

        EntityModel<ConsultaDTO> recurso = EntityModel.of(dto);

        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).listarPorId(id));
        recurso.add(link1.withRel("Consulta-recurso1"));

        return recurso;
    }


}
