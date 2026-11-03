package com.advocacia.estacio.modules.judicial.tribunais;

import com.advocacia.estacio.infra.exceptions.ConflitoDeDadosException;
import com.advocacia.estacio.infra.exceptions.RegraDeNegocioException;
import com.advocacia.estacio.shared.entity.Situacao;
import com.advocacia.estacio.shared.pagination.Paginacao;
import com.advocacia.estacio.shared.pagination.SliceResponse;
import com.advocacia.estacio.shared.specification.SituacaoSpec;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TribunalService {
    private final TribunalRepository tribunalRepository;

    @Transactional
    public TribunalDTO.Response cadastrar(TribunalDTO.Request req) {
        String nome = req.nome().strip();
        if (tribunalRepository.existsByNomeIgnoreCase(nome)) {
            throw new ConflitoDeDadosException("Já existe um tribunal com esse nome");
        }
        Tribunal tribunal = tribunalRepository.save(req.toEntity());
        return new TribunalDTO.Response(tribunal);
    }

    @Transactional(readOnly = true)
    public Page<TribunalDTO.Response> listar(Situacao situacao, Pageable pageable) {
        return tribunalRepository.findAll(SituacaoSpec.de(situacao), pageable)
                .map(TribunalDTO.Response::new);
    }

    @Transactional(readOnly = true)
    public SliceResponse<TribunalDTO.Summary> listarOpcoes(String nome, int page) {
        Pageable pageable = Paginacao.opcoes(page, Sort.by("nome").ascending().and(Sort.by("id")));
        String filtro = (nome == null) ? "" : nome.trim();

        return SliceResponse.de(
                tribunalRepository.findByNomeContainingIgnoreCase(filtro, pageable),
                TribunalDTO.Summary::new
        );
    }

    @Transactional(readOnly = true)
    public TribunalDTO.Response buscarPorId(Long id) {
        return new TribunalDTO.Response(buscarEntidade(id));
    }

    @Transactional
    public TribunalDTO.Response atualizar(Long id, TribunalDTO.Request req) {
        Tribunal tribunal = buscarEntidade(id);
        String nome = req.nome().strip();
        if (tribunalRepository.existsByNomeIgnoreCaseAndIdNot(nome, id)) {
            throw new ConflitoDeDadosException("Já existe um tribunal com esse nome");
        }
        tribunal.atualizar(nome, req.sigla(), req.esfera());
        return new TribunalDTO.Response(tribunal);
    }

    @Transactional
    public TribunalDTO.Response arquivar(Long id) {
        Tribunal tribunal = buscarEntidade(id);
        tribunal.arquivar();
        return new TribunalDTO.Response(tribunal);
    }

    @Transactional
    public TribunalDTO.Response reativar(Long id) {
        Tribunal tribunal = buscarEntidade(id);
        tribunal.reativar();
        return new TribunalDTO.Response(tribunal);
    }

    @Transactional
    public void excluir(Long id) {
        Tribunal tribunal = buscarEntidade(id);

        try {
            tribunalRepository.delete(tribunal);
            tribunalRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new RegraDeNegocioException(
                    "Este tribunal já foi utilizado e não pode ser excluído. Arquive-o em vez disso.");
        }
    }

    @Transactional(readOnly = true)
    public Tribunal obterAtivaParaVinculo(Long id) {
        Tribunal tribunal = buscarEntidade(id);
        if (!tribunal.isAtivo()) {
            throw new RegraDeNegocioException(
                    "Área do direito arquivado não pode ser usado em novos cadastros");
        }
        return tribunal;
    }

    // --- MÉTODOS PRIVADOS (Auxiliares internos) ---

    /**
     * Centraliza a busca e a regra de "Não Encontrado",
     * evitando duplicação de código.
     */
    private Tribunal buscarEntidade(Long id) {
        return tribunalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tribunal não encontrado"));
    }
}
