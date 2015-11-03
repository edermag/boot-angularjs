package br.com.yaw.angularjs;

import static org.mockito.Matchers.any;

import java.util.Arrays;
import java.util.List;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;

import br.com.yaw.angularjs.model.Mercadoria;
import br.com.yaw.angularjs.persistence.MercadoriaRepository;
import br.com.yaw.angularjs.web.MercadoriaController;

/**
 * Controller trata as operações REST sobre a entidade da <code>Mercadoria</code>.
 * 
 * @author <a href="mailto:eder@yaw.com.br">Eder Magalhães</a>
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
public class MercadoriaControllerTest {

    @InjectMocks
    private MercadoriaController controller;
    
    @Mock
    private MercadoriaRepository repository;
    
    private Mercadoria buildMercadoria(String id, String nome, String descricao, 
            String categoria, Double preco, Integer qtde) {
        Mercadoria m = new Mercadoria();
        m.setId(id);
        m.setNome(nome);
        m.setDescricao(descricao);
        m.setCategoria(categoria);
        m.setPreco(preco);
        m.setQuantidade(qtde);
        return m;
    }
    
    @Test
    public void testListAll() throws Exception {
        Mockito.when(repository.findAll())
            .then(a -> Arrays.asList(
                    buildMercadoria("1", "Mercadoria Teste 1", "teste", "Cat", 100.0, 14),
                    buildMercadoria("2", "Mercadoria Teste 2", "outro teste", "Cat", 230.0, 4)));
        List<Mercadoria> mercadorias = controller.list();
        Assert.assertFalse(mercadorias.isEmpty());
    }
    
    @Test
    public void testFindById() throws Exception {
        String mercadoriaId = "10";
        Mockito.when(repository.findAll())
            .then(a -> Arrays.asList(
                buildMercadoria(mercadoriaId, "Mercadoria Teste 10", "teste", "Cat", 180.0, 12)));
        List<Mercadoria> mercadorias = controller.list();
        Assert.assertFalse(mercadorias.isEmpty());
        Assert.assertEquals(mercadorias.stream().findFirst().get().getId(), mercadoriaId);
    }
    
    @Test
    public void testCreate() throws Exception {
        String novoId = "20";
        Mercadoria antes = buildMercadoria(null, "Nova", "Nova mercadoria", "Nova Categoria", 570.2, 22);
        Mockito.when(repository.save((Mercadoria)any()))
            .then(a -> buildMercadoria(novoId, "Nova", "Nova mercadoria", "Nova Categoria", 570.2, 22));
        
        Mercadoria depois = controller.create(antes);
        Assert.assertTrue(antes.getId() == null);
        Assert.assertTrue(depois.getId() != null);
    }
    
    @Test(expected=ValidationException.class)
    public void testCreateInvalid() throws Exception {
        Mercadoria m = buildMercadoria(null, "Invalida", null, null, null, null);
        Mockito.when(repository.save((Mercadoria)any())).thenThrow(new ValidationException());
        controller.create(m);
    }
    
    @Test
    public void testUpdate() throws Exception {
        final Mercadoria m = buildMercadoria("30", "Qualquer", "Outra mercadoria", "Nova Categoria", 570.2, 22);
        String nomeAnterior = m.getNome();
        m.setNome("Qualquer outra");
        Mockito.when(repository.save((Mercadoria)any())).then(a -> m);
        Mercadoria atualizada = controller.update(m.getId(), m);
        
        Assert.assertNotEquals(nomeAnterior, atualizada.getNome());
    }
    
    @Test(expected=ValidationException.class)
    public void testUpdateInvalid() throws Exception {
        Mercadoria m = buildMercadoria("30", "Qualquer", "Outra mercadoria", "Nova Categoria", 570.2, 22);
        m.setCategoria(null);
        m.setPreco(null);
        m.setQuantidade(null);
        Mockito.when(repository.save((Mercadoria)any())).thenThrow(new ValidationException());
        controller.create(m);
    }
    
    @Test
    public void testRemove() throws Exception {
        String toRemove = "2";
        controller.remove(toRemove);        
    }
    
    @Test
    public void testSearchByNome() throws Exception {
        List<Mercadoria> mercadorias = Arrays.asList(
                buildMercadoria("7", "Coca-cola", "Zero", "Bebidas", 3.5, 40),
                buildMercadoria("8", "Inspirion 15", "Dell Notebooks", "Eletronico", 2350.0, 2));
        
        String nome = "coca";
        
        Mockito.when(repository.findByCriteria(any(), any(), any(), any(), any(), any(), any()))
        .then(a -> Arrays.asList(mercadorias.get(0)));
        
        List<Mercadoria> resultado = controller.search(nome, null, null, null, null, null);
        Assert.assertTrue(resultado.size() == 1);
    }
    
    @Test
    public void testSearchByCategoria() throws Exception {
        List<Mercadoria> mercadorias = Arrays.asList(
                buildMercadoria("7", "Coca-cola", "Zero", "Bebidas", 3.5, 40),
                buildMercadoria("8", "Inspirion 15", "Dell Notebooks", "Eletronico", 2350.0, 2));
        
        String categoria = "note";
        
        Mockito.when(repository.findByCriteria(any(), any(), any(), any(), any(), any(), any()))
        .then(a -> Arrays.asList(mercadorias.get(1)));
        
        List<Mercadoria> resultado = controller.search(null, null, categoria, null, null, null);
        Assert.assertTrue(resultado.size() == 1);
    }
    
    @Test
    public void testSearchByPrecoDe() throws Exception {
        List<Mercadoria> mercadorias = Arrays.asList(
                buildMercadoria("7", "Coca-cola", "Zero", "Bebidas", 3.5, 40),
                buildMercadoria("8", "Inspirion 15", "Dell Notebooks", "Eletronico", 2350.0, 2));
        
        Double precoDe = 2000.0;
        
        Mockito.when(repository.findByCriteria(any(), any(), any(), any(), any(), any(), any()))
        .then(a -> Arrays.asList(mercadorias.get(1)));
        
        List<Mercadoria> resultado = controller.search(null, null, null, precoDe, null, null);
        Assert.assertTrue(resultado.size() == 1);
    }
    
}
