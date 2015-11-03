package br.com.yaw.angularjs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import br.com.yaw.angularjs.model.Mercadoria;
import br.com.yaw.angularjs.persistence.MercadoriaRepository;

import com.google.common.collect.Lists;
import com.owlike.genson.Genson;

/**
 * Entrypoint do Spring-boot.
 * 
 * @author <a href="mailto:eder@yaw.com.br">Eder Magalhães</a>
 */
@SpringBootApplication
@Configuration
@ComponentScan(basePackages="br.com.yaw.angularjs")
@EnableMongoRepositories(basePackages="br.com.yaw.angularjs.persistence")
@EnableAutoConfiguration
public class Application implements CommandLineRunner {
    
    @Autowired
    private MercadoriaRepository repository;
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Override
    public void run(String ... args) {
        repository.deleteAll();
        
        Genson genson = new Genson(); //load dos dados de teste
        try {
            String fileName = "data.js";
            ClassLoader classLoader = getClass().getClassLoader();
            Mercadoria[] mercadorias = 
                    genson.deserialize(classLoader.getResource(fileName).openStream(), 
                            Mercadoria[].class);
            
            repository.save(Lists.newArrayList(mercadorias));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
