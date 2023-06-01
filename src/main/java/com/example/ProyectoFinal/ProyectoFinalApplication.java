package com.example.ProyectoFinal;

import com.example.ProyectoFinal.models.*;
import com.example.ProyectoFinal.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ProyectoFinalApplication implements CommandLineRunner {
	@Autowired
	ConciertoRepository conciertoRepository;

	@Autowired
	ConferenciaRepository conferenciaRepository;

	@Autowired
	TallerRepository tallerRepository;

	@Autowired
	ParticipanteRepository participanteRepository;

	@Autowired
	CommentRepository commentRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProyectoFinalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {



		Participante participante1 = new Participante(null,null,null);
		participante1.setName("Pepe");
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


    }
}
