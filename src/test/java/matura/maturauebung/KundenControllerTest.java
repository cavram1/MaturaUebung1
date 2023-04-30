package matura.maturauebung;

import matura.maturauebung.MaturaUebungApplication;
import matura.maturauebung.model.Kunde;
import matura.maturauebung.repository.KundenRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MaturaUebungApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KundenControllerTest {

    @Autowired
    private KundenRepository kundenRepository;

    Kunde k;

    @Test
    @Order(1)
    public void createKunde() {
        k = new Kunde();
        k.setName("MAx");
        k.setEmail("e@g.com");

        Assertions.assertDoesNotThrow(() ->
                kundenRepository.save(k)
        );
    }

    @Test
    @Order(2)
    public void selectKunde() {
        Assertions.assertDoesNotThrow(() -> {
                    Assertions.assertEquals("MAx", k.getName());
                }
        );
    }

    @Test
    @Order(2)
    public void deleteKunde() {
        Assertions.assertDoesNotThrow(() -> {
                    kundenRepository.delete(k);
                }
        );
    }


}