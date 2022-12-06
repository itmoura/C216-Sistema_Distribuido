package br.inatel.labs.labrest.server.services;

import br.inatel.labs.labrest.server.controllers.exceptions.CursoAlreadyExistException;
import br.inatel.labs.labrest.server.entities.Curso;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CursoService {
    private List<Curso> listaDeCursos = new ArrayList<>();


    @PostConstruct
    private void setup() {
        var c1 = new Curso(1L, "REST com Spring Boot", 20);
        var c2 = new Curso(2L, "Programação Java 11", 80);
        var c3 = new Curso(3L, "Dominando a IDE Eclipse", 1200);
        var c4 = new Curso(4L, "Dominando a IDE Intellij", 20);
        listaDeCursos.add(c1);
        listaDeCursos.add(c2);
        listaDeCursos.add(c3);
        listaDeCursos.add(c4);
    }


    public List<Curso> buscarTodosOsCursos() {
        Collections.sort(listaDeCursos);
        return listaDeCursos;
    }

    public Curso buscarCursoPeloId(Long cursoId) {
        var opCurso = listaDeCursos.stream()
                .filter(c -> c.getId().equals(cursoId))
                .findFirst();

        if (opCurso.isEmpty()) {
            String msg = "Nenhum Curso encontrado com id " + cursoId;
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg);
        }

        return opCurso.get();
    }

    public Curso criarNovoCurso(Curso curso) {
        var id = this.nextIndex();
        curso.setId(id);
        curso.setDescricao(curso.getDescricao().trim());
        if (this.verifyIfCursoAlreadyExist(curso.getDescricao()))
            throw new CursoAlreadyExistException("Curso já existe na base!");

        listaDeCursos.add(curso);
        return curso;
    }

    public Curso atualizarCurso(Curso curso) {
        if (!listaDeCursos.remove(curso)) {
            var msg = "Nenhum Curso encontrado com id: " + curso.getId();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg);
        }

        listaDeCursos.add(curso);
        return curso;
    }

    public boolean deletaPorId(Long id) {
        var curso = listaDeCursos.stream().filter(c -> c.getId().equals(id)).findFirst();
        if (curso.isPresent())
            Curso.numeroDeCursosDeletados++;
        return curso.map(value -> listaDeCursos.remove(value)).orElse(false);
    }

    private Long nextIndex() {
        return (long) listaDeCursos.size() + 1L + Curso.numeroDeCursosDeletados;
    }

    private Boolean verifyIfCursoAlreadyExist(String desc) {
        var cursoOptional = listaDeCursos.stream().filter(
                c -> c.getDescricao().equals(desc)
        ).findFirst();

        return cursoOptional.isPresent();
    }
}
