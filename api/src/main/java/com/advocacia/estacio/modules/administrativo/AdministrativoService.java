package com.advocacia.estacio.modules.administrativo;

import com.advocacia.estacio.infra.exceptions.RegraDeNegocioException;
import com.advocacia.estacio.modules.pessoas.Pessoa;
import com.advocacia.estacio.modules.pessoas.PessoaRepository;
import com.advocacia.estacio.modules.usuarios.Usuario;
import com.advocacia.estacio.modules.usuarios.UsuarioRepository;
import com.advocacia.estacio.modules.usuarios.UsuarioRole;
import com.advocacia.estacio.modules.usuarios.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Módulo responsável pela gestão de pessoal ADMINISTRATIVO do sistema
 * (Coordenador do Curso, Secretário).
 *
 * Esses papéis não possuem entidade própria (diferente de Estagiario, Advogado
 * e Professor), pois não carregam atributos além de Pessoa + Usuario + role.
 *
 * Professor NÃO está incluído aqui: apesar de também ser um funcionário da
 * instituição, ele participa ativamente do fluxo acadêmico/jurídico
 * (orientação de estagiários, avaliação de demandas), por isso possui entidade
 * própria com potencial para atributos específicos (departamento, disciplina, etc).
 */
@Service
@RequiredArgsConstructor
public class AdministrativoService {
    private final PessoaRepository pessoaRepository;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public AdministrativoDTO.Response cadastrar(AdministrativoDTO.CreateRequest req) {
        if (!req.role().isAdministrativo()) {
            throw new RegraDeNegocioException("Role inválida. Apenas Coordenadores e Secretários podem ser cadastrados por aqui.");
        }

        Usuario usuario = usuarioService.cadastrar(req.email(), req.senha(), req.role());
        Pessoa pessoa = req.toEntity(usuario);
        pessoa = pessoaRepository.save(pessoa);
        return new AdministrativoDTO.Response(pessoa);
    }

    @Transactional(readOnly = true)
    public Page<AdministrativoDTO.ListResponse> listar(Pageable pageable) {
        List<UsuarioRole> rolesAdministrativas = List.of(
                UsuarioRole.COORDENADOR_DO_CURSO,
                UsuarioRole.SECRETARIO
        );

        return usuarioRepository.buscarPorRoles(rolesAdministrativas, pageable).map(AdministrativoDTO.ListResponse::new);
    }

    @Transactional(readOnly = true)
    public AdministrativoDTO.Response buscarPorId(Long id) {
        var pessoa = pessoaRepository.buscarDetalhesPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Perfil não encontrado."));

        return new AdministrativoDTO.Response(pessoa);
    }
}
