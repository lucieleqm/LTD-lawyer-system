package com.advocacia.estacio.modules.judicial.orgaos_julgadores;

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
public class OrgaoJulgadorService {
    private final OrgaoJulgadorRepository orgaoJulgadorRepository;

    @Transactional
    public OrgaoJulgadorDTO.Response cadastrar(OrgaoJulgadorDTO.Request req) {
        String nome = req.nome().strip();
        if (orgaoJulgadorRepository.existsByNomeIgnoreCase(nome)) {
            throw new ConflitoDeDadosException("Já existe um órgão julgador com esse nome");
        }
        OrgaoJulgador orgao = orgaoJulgadorRepository.save(req.toEntity());
        return new OrgaoJulgadorDTO.Response(orgao);
    }

    @Transactional(readOnly = true)
    public Page<OrgaoJulgadorDTO.Response> listar(Situacao situacao, Pageable pageable) {
        return orgaoJulgadorRepository.findAll(SituacaoSpec.de(situacao), pageable)
                .map(OrgaoJulgadorDTO.Response::new);
    }

    @Transactional(readOnly = true)
    public SliceResponse<OrgaoJulgadorDTO.Summary> listarOpcoes(String nome, int page) {
        Pageable pageable = Paginacao.opcoes(page, Sort.by("nome").ascending().and(Sort.by("id")));
        String filtro = (nome == null) ? "" : nome.trim();

        return SliceResponse.de(
                orgaoJulgadorRepository.findByNomeContainingIgnoreCase(filtro, pageable),
                OrgaoJulgadorDTO.Summary::new
        );
    }

    @Transactional(readOnly = true)
    public OrgaoJulgadorDTO.Response buscarPorId(Long id) {
        return new OrgaoJulgadorDTO.Response(buscarEntidade(id));
    }

    @Transactional
    public OrgaoJulgadorDTO.Response atualizar(Long id, OrgaoJulgadorDTO.Request req) {
        OrgaoJulgador orgao = buscarEntidade(id);
        String nome = req.nome().strip();
        if (orgaoJulgadorRepository.existsByNomeIgnoreCaseAndIdNot(nome, id)) {
            throw new ConflitoDeDadosException("Já existe um órgão julgador com esse nome");
        }
        orgao.atualizar(nome, req.tribunal(), req.instancia());
        return new OrgaoJulgadorDTO.Response(orgao);
    }

    @Transactional
    public OrgaoJulgadorDTO.Response arquivar(Long id) {
        OrgaoJulgador orgao = buscarEntidade(id);
        orgao.arquivar();
        return new OrgaoJulgadorDTO.Response(orgao);
    }

    @Transactional
    public OrgaoJulgadorDTO.Response reativar(Long id) {
        OrgaoJulgador orgao = buscarEntidade(id);
        orgao.reativar();
        return new OrgaoJulgadorDTO.Response(orgao);
    }

    @Transactional
    public void excluir(Long id) {
        OrgaoJulgador orgao = buscarEntidade(id);

        try {
            orgaoJulgadorRepository.delete(orgao);
            orgaoJulgadorRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new RegraDeNegocioException(
                    "Este órgão julgador já foi utilizado e não pode ser excluído. Arquive-o em vez disso.");
        }
    }

    @Transactional(readOnly = true)
    public OrgaoJulgador obterAtivaParaVinculo(Long id) {
        OrgaoJulgador orgao = buscarEntidade(id);
        if (!orgao.isAtivo()) {
            throw new RegraDeNegocioException(
                    "Órgão julgador arquivado não pode ser usado em novos cadastros");
        }
        return orgao;
    }

    // --- MÉTODOS PRIVADOS (Auxiliares internos) ---

    /**
     * Centraliza a busca e a regra de "Não Encontrado",
     * evitando duplicação de código.
     */
    private OrgaoJulgador buscarEntidade(Long id) {
        return orgaoJulgadorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Órgão julgador não encontrado"));
    }
}
