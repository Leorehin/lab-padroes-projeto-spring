package one.digitalinnovation.aluno.lab_padroes_projeto_spring.service;

import one.digitalinnovation.aluno.lab_padroes_projeto_spring.model.Pessoa;

public interface PessoaService {
    
    Iterable<Pessoa> buscarTodos();
    
    Pessoa buscarId(Long id);

    void inserir(Pessoa pessoa);

    void atualizar(Long id, Pessoa pessoa);

    void deletar(Long id);

}
