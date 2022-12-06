package br.inatel.labs.labrest.client;

import br.inatel.labs.labrest.client.model.Curso;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class WebClientGetCurso {

    public static void main(String[] args) {
        var listaCurso= new ArrayList<Curso> ();
        var fluxCurso = WebClient.create("http://localhost:8080")
                .get()
                .uri("/curso")
                .retrieve()
                .bodyToFlux(Curso.class);

        fluxCurso.subscribe(listaCurso::add);
        fluxCurso.blockLast();

        System.out.println("Lista de cursos: ");
        System.out.println(listaCurso);
    }
}
