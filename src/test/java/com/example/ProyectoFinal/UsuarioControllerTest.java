package com.example.ProyectoFinal;

import com.example.ProyectoFinal.dto.UsuarioDTO;
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
public class UsuarioControllerTest {


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
    private OrganizadorRepository organizadorRepository;

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

        Organizador organizador = new Organizador(new ArrayList<>());
        organizador.setName("Luis");
        organizador.setEmail("luis@gmail.com");
        organizador.setPassword("passwordseguro");
        organizadorRepository.save(organizador);


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
    public void shouldAddUsuario() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO("Pepito","pepito@mail.com","contraseña","participante");

        String body = objectMapper.writeValueAsString(usuarioDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/registrar-usuario")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(5, participanteRepository.findAll().size());
    }

    @Test
    public void shouldShowAllParticipants() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/participantes"))
                .andExpect(status().isOk()).andReturn();
        assertEquals(4, participanteRepository.findAll().size());
    }

    @Test
    public void shouldShowAllOrganizadores () throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/organizador"))
                .andExpect(status().isOk()).andReturn();
        assertEquals(1, organizadorRepository.findAll().size());
    }

    @Test
    public void shouldInscribirseAEventoPorId() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/inscribirse-evento/1/1"))
                .andExpect(status().isOk()).andReturn();

        assertEquals(1, conciertoRepository.findById(1l).get().getParticipantesAsistentes().size());
    }

    @Test
    public void shouldShowParticipantesPorId () throws Exception {

        Participante participante = participanteRepository.findParticipanteByName("Paco");

        Concierto concierto =  conciertoRepository.findConciertoByName("Concierto de U2");

        List<Participante> participantes = concierto.getParticipantesAsistentes();
        participantes.add(participante);
        conciertoRepository.save(concierto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/eventos-participantes/1"))
                .andExpect(status().isOk()).andReturn();

        assertEquals(1, concierto.getParticipantesAsistentes().size());
    }

    @Test
    public void shouldDeleteParticipantFromEventoPorId () throws Exception {
        Participante participante = participanteRepository.findParticipanteByName("Pepe");

        Concierto concierto =  conciertoRepository.findConciertoByName("Concierto de U2");

        List<Participante> participantes = concierto.getParticipantesAsistentes();
        participantes.add(participante);
        conciertoRepository.save(concierto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/eliminar-participante-evento/1/1"))
                .andExpect(status().isOk()).andReturn();

        // Recargamos el objeto concierto desde la base de datos.
        concierto =  conciertoRepository.findConciertoByName("Concierto de U2");

        assertEquals(0, concierto.getParticipantesAsistentes().size());

    }
}
