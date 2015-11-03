package br.com.yaw.angularjs.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.yaw.angularjs.model.Mercadoria;
import br.com.yaw.angularjs.persistence.MercadoriaRepository;

/**
 * Controller trata as operações REST sobre a entidade da <code>Mercadoria</code>.
 * 
 * @author <a href="mailto:eder@yaw.com.br">Eder Magalhães</a>
 */
@RestController
@RequestMapping(value="/api/mercadorias")
public class MercadoriaController {
    
    private MercadoriaRepository repository;
    private MongoOperations mongoOperations;
    
    @Autowired
    public MercadoriaController(MercadoriaRepository repository, 
            MongoOperations mongoOperations) {
        this.repository = repository;
        this.mongoOperations = mongoOperations;
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(value=HttpStatus.OK)
    public List<Mercadoria> list() {
        return repository.findAll();
    }
    
    @RequestMapping(value="/{id}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(value=HttpStatus.OK)
    public Mercadoria findById(@PathVariable String id) {
        return repository.findOne(id);
    }
    
    @RequestMapping(method = RequestMethod.POST,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(value=HttpStatus.CREATED)
    public Mercadoria create(@Valid @RequestBody Mercadoria mercadoria) {
        return repository.save(mercadoria);
    }
    
    @RequestMapping(value="/{id}", method = RequestMethod.PUT,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(value=HttpStatus.OK)
    public Mercadoria update(@Valid @PathVariable String id, 
            @RequestBody Mercadoria mercadoria) {
        return repository.save(mercadoria);
    }
    
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    public void remove(@PathVariable String id) {
        repository.delete(id);
    }
    
    @RequestMapping(value="/search", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(value=HttpStatus.OK)
    public List<Mercadoria> search(String nome, String descricao, 
            String categoria, Double precoDe, Double precoAte,
            String ordemPor) {
        return repository.findByCriteria(nome, descricao, categoria, 
                precoDe, precoAte, ordemPor, mongoOperations);
    }
    
}
