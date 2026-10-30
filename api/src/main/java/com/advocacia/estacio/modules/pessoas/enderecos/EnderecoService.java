package com.advocacia.estacio.modules.pessoas.enderecos;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoService {

	private final EnderecoRepository enderecoRepository;

	public Endereco cadastrar(EnderecoDTO.Request req) {
		if (req == null) return null;
		// TODO: adicionar validação de Cep no futuro, talvez.
		Endereco endereco = req.toEntity();
		return enderecoRepository.save(endereco);
	}

	// TODO: relacionamento de Pessoa com Endereco deve ser revisto.
	private void atualizar(Endereco enderecoAtual, EnderecoDTO.Request req) {
		if (enderecoAtual == null) return;

		enderecoAtual.atualizarDados(
				req.cep(),
				req.logradouro(),
				req.numero(),
				req.complemento(),
				req.bairro(),
				req.cidade()
		);
	}

	public Endereco cadastrarOuAtualizar(Endereco enderecoAtual, EnderecoDTO.Request req) {
		// seta null no endereço da Pessoa caso não tiver dados na req (exclusão do endereço)
		if (req == null) return null;
		if (enderecoAtual != null) {
			this.atualizar(enderecoAtual, req);
			return enderecoAtual;
		}
		return this.cadastrar(req);
	}
}
