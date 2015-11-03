package br.com.yaw.angularjs.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.google.common.base.Strings;

import br.com.yaw.angularjs.model.Mercadoria;

/**
 * Repositório com as operações de persistência da <code>Mercadoria</code>.
 * 
 * @author <a href="mailto:eder@yaw.com.br">Eder Magalhães</a>
 */
public interface MercadoriaRepository extends MongoRepository<Mercadoria, String> {

    default List<Mercadoria> findByCriteria(String nome, String descricao, String categoria,
            Double precoDe, Double precoAte, String ordemPor, MongoOperations mongoOperations) {
        List<Criteria> criterias = new ArrayList<>();
        if (!Strings.isNullOrEmpty(nome)) {
            criterias.add(Criteria.where("nome")
                    .regex(Pattern.compile(nome, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
        }
        
        if (!Strings.isNullOrEmpty(descricao)) {
            criterias.add(Criteria.where("descricao")
                    .regex(Pattern.compile(descricao, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
        }
        
        if (!Strings.isNullOrEmpty(categoria)) {
            criterias.add(Criteria.where("categoria")
                    .regex(Pattern.compile(categoria, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
        }
        
        if (precoDe != null && precoAte != null) {
            criterias.add(Criteria.where("preco").gte(precoDe).lte(precoAte));
        } else {
            if (precoDe != null) {
                criterias.add(Criteria.where("preco").gte(precoDe));
            }
            
            if (precoAte != null) {
                criterias.add(Criteria.where("preco").lte(precoAte));
            }
        }
        
        Query query = new Query();
        
        for (Criteria c: criterias) {
            query.addCriteria(c);
        }
        
        if (!Strings.isNullOrEmpty(ordemPor)) {
            query.with(new Sort(new Sort.Order(Sort.Direction.ASC, ordemPor)));
        }
        
        return mongoOperations.find(query, Mercadoria.class);
    }
    
}
