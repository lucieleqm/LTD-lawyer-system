package com.advocacia.estacio.modules.professores;

import com.advocacia.estacio.modules.usuarios.Usuario;
import com.advocacia.estacio.modules.usuarios.UsuarioService;
import com.advocacia.estacio.modules.usuarios.UsuarioRole;
import com.advocacia.estacio.modules.usuarios.UsuarioStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final UsuarioService usuarioService;

    @Transactional
    public ProfessorDTO.Response cadastrar(ProfessorDTO.CreateRequest req) {
        Usuario usuario = usuarioService.cadastrar(req.email(), req.senha(), UsuarioRole.PROFESSOR);
        Professor professor = req.toEntity(usuario);
        professor = professorRepository.save(professor);

        return new ProfessorDTO.Response(professor);
    }

    @Transactional(readOnly = true)
    public Page<ProfessorDTO.ListResponse> listar(ProfessorDTO.SearchFilter filtro, Pageable pageable) {
        return professorRepository.buscarComFiltros(filtro.nome(), filtro.usuarioStatus(), pageable)
                .map(ProfessorDTO.ListResponse::new);
    }

    @Transactional(readOnly = true)
    public List<ProfessorDTO.OptionResponse> listarOpcoes(String nome) {
        Pageable limite = PageRequest.of(0, 20);
        List<UsuarioStatus> statusPermitidos = List.of(UsuarioStatus.ATIVO, UsuarioStatus.BLOQUEADO);
        return professorRepository.buscarAtivosPorNome(nome, statusPermitidos, limite);
    }

    @Transactional(readOnly = true)
    public ProfessorDTO.Response buscarPorId(Long id) {
        Professor professor = professorRepository.buscarDetalhesPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado."));

        return new ProfessorDTO.Response(professor);
    }

    @Transactional
    public ProfessorDTO.Response atualizar(Long id, ProfessorDTO.UpdateRequest req) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado."));

        professor.atualizarDados(req.nome(), req.telefone());
        return new ProfessorDTO.Response(professor);
    }

    /**
     * Retorna apenas a referência (Proxy) da entidade Professor para uso em chaves estrangeiras.
     * NÃO executa um SELECT no banco de dados.
     */
    public Professor obterReferencia(Long id) {
        return professorRepository.getReferenceById(id);
    }
}
