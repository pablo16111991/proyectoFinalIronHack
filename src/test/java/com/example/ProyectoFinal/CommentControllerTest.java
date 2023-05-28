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
    public void addCommentTest() throws Exception {
        Concierto concierto1 = conciertoRepository.findConciertoByName("Concierto de Nirvana");

        Participante participante3 = new Participante();
       participante3.setName("Tomy");
       participante3.setEmail("tomy@gmail.com");
       participante3.setPassword("passworddelcarajo");
       participante3 = participanteRepository.save(participante3);

        CommentRequest request = new CommentRequest(1l,1l,"Ejemplo de la vida");

        String body = objectMapper.writeValueAsString(request);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/añadir-comment")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        assertEquals(4, commentRepository.findAll().size());

    }

   @Test
    public void shouldShowCommentByEventId() throws Exception {


       Comment comment1 = new Comment();
       comment1.setComment("Este es el primer comentario");
       comment1.setCommentDate(LocalDate.now());
       comment1.setEvent(conciertoRepository.findById(1l).get());
       comment1.setParticipante(participanteRepository.findById(1l).get());
       commentRepository.save(comment1);

       Comment comment2 = new Comment();
       comment2.setComment("Este es el segundo comentario");
       comment2.setCommentDate(LocalDate.now());
       comment2.setEvent(conciertoRepository.findById(1l).get());
       comment2.setParticipante(participanteRepository.findById(2l).get());
       commentRepository.save(comment2);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/comment-by-eventId/1"))
                .andExpect(status().isOk()).andReturn();
        assertEquals(2, conciertoRepository.findById(1l).get().getComments().size());
    }


    @Test
    public void shouldShowCommentByUserId() throws Exception {

        Conferencia conferencia1 = new Conferencia(List.of("Picasso", "Dalí"));
        conferencia1.setName("Conferencia de Artes");
        conferencia1.setEventDate(LocalDate.of(2023, 6, 18));  // Conferencia programada para el 18 de junio de 2023
        conferenciaRepository.save(conferencia1);

        Comment comment1 = new Comment();
        comment1.setComment("Este es el primer comentario");
        comment1.setCommentDate(LocalDate.now());
        comment1.setEvent(conferencia1);
        comment1.setParticipante(participanteRepository.findById(1l).get());
        commentRepository.save(comment1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/comment-by-userId/1"))
                .andExpect(status().isOk()).andReturn();
        assertEquals(1, participanteRepository.findById(1l).get().getComments().size());
    }

}
