package one.digitalinnovation.aluno.lab_padroes_projeto_spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import one.digitalinnovation.aluno.lab_padroes_projeto_spring.handler.BusinessException;
import one.digitalinnovation.aluno.lab_padroes_projeto_spring.model.Pessoa;
import one.digitalinnovation.aluno.lab_padroes_projeto_spring.service.PessoaService;

@RestController
@RequestMapping("pessoas")
public class PessoaRestController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<Iterable<Pessoa>> buscarTodos() {
        return ResponseEntity.ok(pessoaService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarId(@PathVariable Long id) {
        return ResponseEntity.ok(pessoaService.buscarId(id));
    }

    @PostMapping
    public ResponseEntity<Pessoa> inserir(@RequestBody Pessoa pessoa) {
        excessoesController(pessoa);
        pessoaService.inserir(pessoa);
        return ResponseEntity.ok(pessoa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        excessoesController(pessoa);
        pessoaService.atualizar(id, pessoa);

        return ResponseEntity.ok(pessoa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pessoa> deletar(@PathVariable Long id) {
        pessoaService.deletar(id);
        return ResponseEntity.ok().build();
    }

    private void excessoesController(Pessoa pessoa){
        if (pessoa.getNome() == null || pessoa.getNome().length() <= 0) {
            throw new BusinessException("O campo nome precisa ser preenchido");
        }
        if (pessoa.getEndereco().getCep() == null) {
            throw new BusinessException("O campo Cep nao pode ser nulo");
        }
        if (pessoa.getEndereco().getCep().length() != 8){
            throw new BusinessException("O Cep precisa ter 8 caracteres");
        }
    }

}
