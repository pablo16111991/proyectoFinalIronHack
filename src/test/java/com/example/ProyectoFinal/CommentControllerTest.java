package com.example.ProyectoFinal;

import com.example.ProyectoFinal.dto.CommentRequest;
import com.example.ProyectoFinal.models.*;
import com.example.ProyectoFinal.repositories.*;
import com.example.ProyectoFinal.servicies.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CommentControllerTest {



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

    private final ObjectMapper objectMapper = new ObjectMapper();

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
        concierto1.setName("Concierto de U2");
        concierto1.setEventDate(LocalDate.of(2023, 9, 10));
        conciertoRepository.save(concierto1);


        Conferencia conferencia1 = new Conferencia(List.of("Picasso", "Dalí"));
        conferencia1.setName("Conferencia de Artes");
        conferencia1.setEventDate(LocalDate.of(2023, 6, 18));  // Conferencia programada para el 18 de junio de 2023
        conferenciaRepository.save(conferencia1);


        Taller taller1 = new Taller("Paco de lucía",List.of("Guitarra, Bajo, Cajón flamenco"));
        taller1.setName("Taller de Flamenco");
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
    public void addCommentTest() throws Exception {
        Concierto concierto1 = conciertoRepository.findConciertoByName("Concierto de Nirvana");
        Participante participante1 = participanteRepository.findParticipanteByEmail("paco@gmail.com");
        Comment comment = new Comment();
        comment.setComment("Comentario de prueba para el test de Springboot");
        comment.setCommentDate(LocalDate.now());
        comment.setEvent(concierto1);
        comment.setParticipante(participante1);



        String body = objectMapper.writeValueAsString(comment);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/añadir-comment")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        assertEquals(4, commentRepository.findAll().size());

    }




}
