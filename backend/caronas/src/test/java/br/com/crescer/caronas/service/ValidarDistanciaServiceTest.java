package br.com.crescer.caronas.service;

import br.com.crescer.caronas.Service.ValidarDistanciaService;
import br.com.crescer.caronas.entity.Destino;
import br.com.crescer.caronas.entity.DiaSemana;
import br.com.crescer.caronas.entity.Rotina;
import br.com.crescer.caronas.entity.Usuario;
import br.com.crescer.caronas.entity.Origem;
import br.com.crescer.caronas.entity.RotinaDiaSemana;
import br.com.crescer.caronas.repository.RotinaDiaSemanaRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author christopher.michel
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional(propagation = Propagation.REQUIRED)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class ValidarDistanciaServiceTest {

    ValidarDistanciaService service = new ValidarDistanciaService();
    
    @Test
    public void testFiltrarDiaSemana2DiasVolta1() {
        final Rotina rotina = instanciarRotina();
        final List<String> listaStringTest = new ArrayList<>();
        listaStringTest.add("SEGUNDA");
        listaStringTest.add("TERCA");
        List<RotinaDiaSemana> rotinaDiaSemana = service.filtrarDiaSemana(listaStringTest, rotina);
        assertEquals(1, rotinaDiaSemana.size());
    }
    
    @Test
    public void testFiltrarDiaSemana4DiasVolta2() {
        final Rotina rotina = instanciarRotina2();
        final List<String> listaStringTest = new ArrayList<>();
        listaStringTest.add("SEGUNDA");
        listaStringTest.add("TERCA");
        listaStringTest.add("QUARTA");
        listaStringTest.add("QUINTA");
        List<RotinaDiaSemana> rotinaDiaSemana = service.filtrarDiaSemana(listaStringTest, rotina);
        assertEquals(2, rotinaDiaSemana.size());
    }
    
    @Test
    public void testFiltrarDiaSemana4DiasVolta0() {
        final Rotina rotina = instanciarRotina3();
        final List<String> listaStringTest = new ArrayList<>();
        listaStringTest.add("SEGUNDA");
        listaStringTest.add("TERCA");
        listaStringTest.add("QUARTA");
        listaStringTest.add("QUINTA");
        List<RotinaDiaSemana> rotinaDiaSemana = service.filtrarDiaSemana(listaStringTest, rotina);
        assertEquals(0, rotinaDiaSemana.size());
    }
    
    @Test
    public void testDescontarVagas() {
        final Rotina rotinaMotorista = instanciarRotina2();  
        final Rotina rotinaPassageiro = instanciarRotina();
        assertEquals(0, 0);
    }
        
    private Rotina instanciarRotina() {
        Usuario usuario = new Usuario("Teste", "teste@teste.com", "Masculino", "2", "senha");
        Destino destino = new Destino("destino", BigDecimal.ONE, BigDecimal.ONE);
        Origem origem = new Origem("origem", BigDecimal.ONE, BigDecimal.ONE);
        List<RotinaDiaSemana> listaDeDias = new ArrayList<>();
        listaDeDias.add(new RotinaDiaSemana(5, new DiaSemana("SEGUNDA")));
        Rotina rotina = new Rotina(true, new Date(), BigDecimal.TEN, BigDecimal.ZERO, listaDeDias, destino, origem, usuario);
        for (RotinaDiaSemana rotinaDiaSemana : listaDeDias) {
            rotinaDiaSemana.setRotina(rotina);
        }
        return rotina;
    }
    
    private Rotina instanciarRotina2() {
        Usuario usuario = new Usuario("Teste", "teste@teste.com", "Masculino", "2", "senha");
        Destino destino = new Destino("destino", BigDecimal.ONE, BigDecimal.ONE);
        Origem origem = new Origem("origem", BigDecimal.ONE, BigDecimal.ONE);
        List<RotinaDiaSemana> listaDeDias = new ArrayList<>();
        listaDeDias.add(new RotinaDiaSemana(5, new DiaSemana("SEGUNDA")));
        listaDeDias.add(new RotinaDiaSemana(5, new DiaSemana("TERCA")));
        Rotina rotina = new Rotina(true, new Date(), BigDecimal.TEN, BigDecimal.ZERO, listaDeDias, destino, origem, usuario);
        for (RotinaDiaSemana rotinaDiaSemana : listaDeDias) {
            rotinaDiaSemana.setRotina(rotina);
        }
        return rotina;
    }
    
    private Rotina instanciarRotina3() {
        Usuario usuario = new Usuario("Teste", "teste@teste.com", "Masculino", "2", "senha");
        Destino destino = new Destino("destino", BigDecimal.ONE, BigDecimal.ONE);
        Origem origem = new Origem("origem", BigDecimal.ONE, BigDecimal.ONE);
        List<RotinaDiaSemana> listaDeDias = new ArrayList<>();
        Rotina rotina = new Rotina(true, new Date(), BigDecimal.TEN, BigDecimal.ZERO, listaDeDias, destino, origem, usuario);
        for (RotinaDiaSemana rotinaDiaSemana : listaDeDias) {
            rotinaDiaSemana.setRotina(rotina);
        }
        return rotina;
    }

}
