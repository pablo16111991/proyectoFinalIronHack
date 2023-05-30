package com.example.ProyectoFinal;

import com.example.ProyectoFinal.dto.ConciertoDTO;
import com.example.ProyectoFinal.dto.ConferenciaDTO;
import com.example.ProyectoFinal.dto.EventDateNNameDTO;
import com.example.ProyectoFinal.dto.TallerDTO;
import com.example.ProyectoFinal.models.*;
import com.example.ProyectoFinal.repositories.*;
import com.example.ProyectoFinal.servicies.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class EventoControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private ConciertoRepository conciertoRepository;
    @Autowired
    private TallerRepository tallerRepository;
    @Autowired
    private ConferenciaRepository conferenciaRepository;

    @Autowired
    private ObjectMapper objectMapper; // Esto usará el ObjectMapper preconfigurado de Spring Boot

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Participante participante1 = new Participante(null,null,null);
        participante1.setName("Paco");
        participante1.setEmail("paco@gmail.com");
        participante1.setPassword("contraseñainquebrantable");
        participanteRepository.save(participante1);

        Participante participante2 = new Participante(null,null,null);
        participante2.setName("Luis");
        participante2.setEmail("luis@gmail.com");
        participante2.setPassword("passwordseguro");
        participanteRepository.save(participante2);


        Concierto concierto1 = new Concierto("U2", "Pop-Rock");
        concierto1.setName("Concierto de Metallica");
        concierto1.setEventDate(LocalDate.of(2023, 9, 10));
        conciertoRepository.save(concierto1);


        Conferencia conferencia1 = new Conferencia(List.of("Picasso", "Dalí"));
        conferencia1.setName("Conferencia de Plastilina");
        conferencia1.setEventDate(LocalDate.of(2023, 6, 18));  // Conferencia programada para el 18 de junio de 2023
        conferenciaRepository.save(conferencia1);


        Taller taller1 = new Taller("Paco de lucía",List.of("Guitarra, Bajo, Cajón flamenco"));
        taller1.setName("Taller de NeoFlamenco Japonés");
        taller1.setEventDate(LocalDate.of(2023, 6, 17));  // Taller programado para el 18 de junio de 2023
        tallerRepository.save(taller1);


        Comment comment1 = new Comment();
        comment1.setComment("Este es el primer comentario");
        comment1.setCommentDate(LocalDate.now());
        comment1.setEvent(concierto1);
        comment1.setParticipante(participante1);
        commentRepository.save(comment1);

        Comment comment2 = new Comment();
        comment2.setComment("Este es el segundo comentario");
        comment2.setCommentDate(LocalDate.now());
        comment2.setEvent(taller1);
        comment2.setParticipante(participante2);
        commentRepository.save(comment2);

        Comment comment3 = new Comment();
        comment3.setComment("Este es el tercer comentario");
        comment3.setCommentDate(LocalDate.now());
        comment3.setEvent(concierto1);
        comment3.setParticipante(participante2);
        commentRepository.save(comment3);

    }

    @AfterEach
    void tearDown() {
        participanteRepository.deleteAll();
        conciertoRepository.deleteAll();
        tallerRepository.deleteAll();
        conferenciaRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @Test
    public void shouldShowAllTest  () throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/events"))
                .andExpect(status().isOk()).andReturn();

        Long totalEventos = conciertoRepository.count() + tallerRepository.count() + conferenciaRepository.count();

        assertEquals(6, totalEventos);
    }

    @Test
    void shouldShowAllConcertsWorkshopsConferencesTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/events-concierto"))
                .andExpect(status().isOk()).andReturn();

        assertEquals(2, conciertoRepository.count());

        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.get("/events-taller"))
                .andExpect(status().isOk()).andReturn();

        assertEquals(2, tallerRepository.count());

        MvcResult result3 = mockMvc.perform(MockMvcRequestBuilders.get("/events-conferencia"))
                .andExpect(status().isOk()).andReturn();

        assertEquals(2, conferenciaRepository.count());

    }

    @Test
    public void shouldFindEventByIdTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/encontrar event/1"))
                .andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Concierto de U2"));
    }

    @Test
    public void shouldDeleteEventByIdTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/borrar-evento/1"))
                .andExpect(status().isOk()).andReturn();
        assertEquals(false, conciertoRepository.existsById(1l));

    }

    @Test
    public void shouldUpdateEventTest() throws Exception {

        Concierto concierto = conciertoRepository.findConciertoByName("Concierto de U2");

        EventDateNNameDTO eventDateNNameDTO = new EventDateNNameDTO(concierto.getEventId(),"Concierto de Coldplay",concierto.getEventDate());

        String body = objectMapper.writeValueAsString(eventDateNNameDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/actualizar-evento")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        concierto = conciertoRepository.findConciertoByEventId(concierto.getEventId());

        assertEquals("Concierto de Coldplay", concierto.getName());

    }

    @Test
    public void shouldAddEventTest() throws Exception {
        ConciertoDTO conciertoDTO =  new ConciertoDTO("concierto añadido de prueba",LocalDate.now(),new ArrayList<>(),new ArrayList<>(),"el nuevo","neoRock");
        String body1 = objectMapper.writeValueAsString(conciertoDTO);
        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.post("/add-concierto")
                        .content(body1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        Concierto concierto = conciertoRepository.findConciertoByName("concierto añadido de prueba");

        assertEquals(true,conciertoRepository.existsById(concierto.getEventId()));


        TallerDTO tallerDTO = new TallerDTO("taller añadido de prueba",LocalDate.now(),new ArrayList<>(),new ArrayList<>(),"el nuevo",new ArrayList<>());
        String body2 = objectMapper.writeValueAsString(tallerDTO);
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.post("/add-taller")
                        .content(body2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        Taller taller = tallerRepository.findTallerByName("taller añadido de prueba");

        assertEquals(true,tallerRepository.existsById(taller.getEventId()));

        ConferenciaDTO conferenciaDTO = new ConferenciaDTO("conferencia añadida de prueba",LocalDate.now(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        String body3 = objectMapper.writeValueAsString(conferenciaDTO);
        MvcResult result3 = mockMvc.perform(MockMvcRequestBuilders.post("/add-conferencia")
                        .content(body3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        Conferencia conferencia = conferenciaRepository.findConferenciaByName("conferencia añadida de prueba");

        assertEquals(true, conferenciaRepository.existsById(conferencia.getEventId()));

        /*
            private String name;
    private LocalDate eventDate;
    private List<Participante> participantesAsistentes;
    private List<Participante> participantesPendientes;
    private List<String> ponentes;
         */

    }

}
