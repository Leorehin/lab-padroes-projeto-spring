package one.digitalinnovation.aluno.lab_padroes_projeto_spring.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import one.digitalinnovation.aluno.lab_padroes_projeto_spring.model.Endereco;
import one.digitalinnovation.aluno.lab_padroes_projeto_spring.model.EnderecoRepository;
import one.digitalinnovation.aluno.lab_padroes_projeto_spring.model.Pessoa;
import one.digitalinnovation.aluno.lab_padroes_projeto_spring.model.PessoaRepository;
import one.digitalinnovation.aluno.lab_padroes_projeto_spring.service.PessoaService;
import one.digitalinnovation.aluno.lab_padroes_projeto_spring.service.ViaCepService;

@Service
public class PessoaServiceImplement implements PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaService;

    @Override
    public Iterable<Pessoa> buscarTodos() {
        return pessoaRepository.findAll();
    }

    @Override
    public Pessoa buscarId(Long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        return pessoa.get();
    }

    @Override
    public void inserir(Pessoa pessoa) {
        System.out.println("Inserindo pessoa");

        salvarPessoaComCep(pessoa);

    }

    @Override
    public void atualizar(Long id, Pessoa pessoa) {

        Optional<Pessoa> pessoaBd = pessoaRepository.findById(id);
        // se a pessoa existe no banco de dados
        String cep = pessoa.getEndereco().getCep();

        Endereco endereco = verificaEndereco(cep);
        pessoaBd.get().setEndereco(endereco);
        pessoaBd.get().setNome(pessoa.getNome());
        pessoaRepository.save(pessoaBd.get());
    }

    @Override
    public void deletar(Long id) {
        pessoaRepository.deleteById(id);
    }

    private void salvarPessoaComCep(Pessoa pessoa) {
        //o cep retorna nulo, e da erro de nullpointer...
        String cep= pessoa.getEndereco().getCep();
        
        Endereco endereco = verificaEndereco(cep);
        pessoa.setEndereco(endereco);
        pessoaRepository.save(pessoa);
        System.out.println("Pessoa salva!");
    }

    private Endereco verificaEndereco(String cep) {
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        return endereco;
    }

    
}
