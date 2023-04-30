package matura.maturauebung;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import matura.maturauebung.controller.KundenController;
import matura.maturauebung.model.Admin;
import matura.maturauebung.model.Kunde;
import matura.maturauebung.repository.AdminRepository;
import matura.maturauebung.repository.KundenRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = KundenController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KundenRestControllerTest {

    @MockBean
    private KundenRepository kundenRepository;

    @MockBean
    private AdminRepository adminRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    Kunde k;
    List<Kunde> list;

    @BeforeAll
    void setup() {

        Admin a = new Admin();
        a.setToken("khfdhfdfhkdhfdk");
        adminRepository.save(a);
        when(adminRepository.findById(1L)).thenReturn(Optional.of(a));

        Kunde kunde = new Kunde();
        kunde.setName("Max");
        kunde.setEmail("e@h.com");
        kundenRepository.save(k);

        list = new ArrayList<>(Arrays.asList(kunde));
        when(kundenRepository.findAll()).thenReturn(list);

        when(kundenRepository.findById(1L)).thenReturn(Optional.of(kunde));

    }

    @Test
    @Order(1)
    public void createUserTest() throws Exception {
        Optional<Admin> a = adminRepository.findById(1L);
        k = new Kunde(1L, "Max", "Mustermann");

        if (a.isPresent()) {
            Admin admin = a.get();

            mockMvc.perform(post("/kunde/create")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + admin.getToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(k)))
                    .andExpect(status().isCreated());
        }
    }

    @Test
    @Order(2)
    void shouldCreateKunde() throws Exception {
        Kunde tutorial = new Kunde(1L, "Spring Boot @WebMvcTest", "Description");
        Optional<Admin> a = adminRepository.findById(1L);


        if (a.isPresent()) {
            Admin admin = a.get();
            mockMvc.perform(post("/api/tutorials")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + admin.getToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(tutorial)))
                    .andExpect(status().isCreated())
                    .andDo(print());
        }
    }


    @Test
    @Order(3)
    void returnCustomer() throws Exception {
        Optional<Admin> a = adminRepository.findById(1L);

        if (a.isPresent()) {

            Admin admin = a.get();
            mockMvc.perform(get("/kunde/load/{id}", 1L)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + admin.getToken()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.name").value(k.getName()))
                    .andExpect(jsonPath("$.email").value(k.getEmail()))
                    .andDo(print());

            assertEquals(kundenRepository.findById(1L), k);
        }
    }

    @Test
    @Order(4)
    void returnCustomers() throws Exception {
        Optional<Admin> a = adminRepository.findById(1L);

        if (a.isPresent()) {

            Admin admin = a.get();
            MvcResult m = mockMvc.perform(get("/kunde/load")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + admin.getToken()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andReturn();

            objectMapper = new ObjectMapper();
            Kunde[] list = objectMapper.readValue(m.getResponse().getContentAsString(), Kunde[].class);
            assertEquals(kundenRepository.findAll().size(), list.length);
        }
    }

    @Test
    @Order(5)
    void shouldReturnTutorial() throws Exception {
        long id = 1L;
        Kunde tutorial = new Kunde(id, "Spring Boot @WebMvcTest", "Description");

        Optional<Admin> a = adminRepository.findById(1L);

        if (a.isPresent()) {

            Admin admin = a.get();
            when(kundenRepository.findById(id)).thenReturn(Optional.of(tutorial));
            mockMvc.perform(get("/kunde/load/{id}", id)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + admin.getToken()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(id))
                    .andExpect(jsonPath("$.email").value(tutorial.getEmail()))
                    .andExpect(jsonPath("$.name").value(tutorial.getName()))
                    .andDo(print());

        }
    }
}