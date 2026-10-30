package com.advocacia.estacio.modules.usuarios;

import com.advocacia.estacio.infra.exceptions.RegraDeNegocioException;
import com.advocacia.estacio.infra.security.SecurityUtils;
import com.advocacia.estacio.infra.exceptions.ConflitoDeDadosException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	private final SecurityUtils securityUtils;

	public Usuario cadastrar(String login, String senha, UsuarioRole role) {
		if (this.usuarioRepository.findByLogin(login).isPresent()) {
			throw new ConflitoDeDadosException("Já existe um usuário cadastrado com este login.");
		}
		return new Usuario(login, passwordEncoder.encode(senha), role);
	}

	@Transactional(readOnly = true)
	public UsuarioDTO.Response buscarPorId(Long id) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
		return new UsuarioDTO.Response(usuario);
	}

	@Transactional
	public void alterarStatus(Long id, UsuarioStatus status) {
		Long idUsuarioLogado = securityUtils.getIdUsuarioLogado();
		if (id.equals(idUsuarioLogado)) {
			throw new RegraDeNegocioException("Você não pode alterar seu próprio status de acesso.");
		}

		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

		if (usuario.getStatus() == status) return;

		switch (status) {
			case ATIVO -> usuario.reativar();
			case INATIVO -> usuario.desativar();
			case BLOQUEADO -> usuario.bloquear();
			default -> throw new IllegalArgumentException("Status desconhecido");
		}
	}

	// TODO: redefinir senha

	// TODO: serviço auxiliar de criação da primeira senha
}