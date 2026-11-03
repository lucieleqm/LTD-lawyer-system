package com.advocacia.estacio.modules.judicial.areas_direito;

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
public class AreaDireitoService {
    private final AreaDireitoRepository areaDireitoRepository;

    @Transactional
    public AreaDireitoDTO.Response cadastrar(AreaDireitoDTO.Request req) {
        String nome = req.nome().strip();
        if (areaDireitoRepository.existsByNomeIgnoreCase(nome)) {
            throw new ConflitoDeDadosException("Já existe uma área do direito com esse nome");
        }
        AreaDireito area = areaDireitoRepository.save(req.toEntity());
        return new AreaDireitoDTO.Response(area);
    }

    @Transactional(readOnly = true)
    public Page<AreaDireitoDTO.Response> listar(Situacao situacao, Pageable pageable) {
        return areaDireitoRepository.findAll(SituacaoSpec.de(situacao), pageable)
                .map(AreaDireitoDTO.Response::new);
    }

    @Transactional(readOnly = true)
    public SliceResponse<AreaDireitoDTO.Summary> listarOpcoes(String nome, int page) {
        Pageable pageable = Paginacao.opcoes(page, Sort.by("nome").ascending().and(Sort.by("id")));
        String filtro = (nome == null) ? "" : nome.trim();

        return SliceResponse.de(
                areaDireitoRepository.findByNomeContainingIgnoreCase(filtro, pageable),
                AreaDireitoDTO.Summary::new
        );
    }

    @Transactional(readOnly = true)
    public AreaDireitoDTO.Response buscarPorId(Long id) {
        return new AreaDireitoDTO.Response(buscarEntidade(id));
    }

    @Transactional
    public AreaDireitoDTO.Response atualizar(Long id, AreaDireitoDTO.Request req) {
        AreaDireito area = buscarEntidade(id);
        String nome = req.nome().strip();
        if (areaDireitoRepository.existsByNomeIgnoreCaseAndIdNot(nome, id)) {
            throw new ConflitoDeDadosException("Já existe uma área do direito com esse nome");
        }
        area.atualizar(nome);
        return new AreaDireitoDTO.Response(area);
    }

    @Transactional
    public AreaDireitoDTO.Response arquivar(Long id) {
        AreaDireito area = buscarEntidade(id);
        area.arquivar();
        return new AreaDireitoDTO.Response(area);
    }

    @Transactional
    public AreaDireitoDTO.Response reativar(Long id) {
        AreaDireito area = buscarEntidade(id);
        area.reativar();
        return new AreaDireitoDTO.Response(area);
    }

    @Transactional
    public void excluir(Long id) {
        AreaDireito area = buscarEntidade(id);

        try {
            areaDireitoRepository.delete(area);
            areaDireitoRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new RegraDeNegocioException(
                    "Esta área já foi utilizada e não pode ser excluída. Arquive-a em vez disso.");
        }
    }

    @Transactional(readOnly = true)
    public AreaDireito obterAtivaParaVinculo(Long id) {
        AreaDireito area = buscarEntidade(id);
        if (!area.isAtivo()) {
            throw new RegraDeNegocioException(
                    "Área do direito arquivada não pode ser usada em novos cadastros");
        }
        return area;
    }

    // --- MÉTODOS PRIVADOS (Auxiliares internos) ---

    /**
     * Centraliza a busca e a regra de "Não Encontrado",
     * evitando duplicação de código.
     */
    private AreaDireito buscarEntidade(Long id) {
        return areaDireitoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Área do direito não encontrada"));
    }
}
