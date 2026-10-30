package com.advocacia.estacio.modules.auth;

import com.advocacia.estacio.modules.parametros.ConfiguracaoSistema;
import com.advocacia.estacio.modules.usuarios.Usuario;
import com.advocacia.estacio.modules.usuarios.UsuarioRole;
import com.advocacia.estacio.modules.usuarios.UsuarioStatus;
import com.advocacia.estacio.infra.security.CustomUserDetails;
import com.advocacia.estacio.modules.parametros.ConfigSistemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.advocacia.estacio.modules.usuarios.UsuarioRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;
	private final ConfigSistemaRepository configSistemaRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByLogin(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

		ConfiguracaoSistema config = configSistemaRepository.findById(1L).orElse(null);

		boolean estagiarioDeFerias = false;

		if (usuario.getRole() == UsuarioRole.ESTAGIARIO && config != null) {
			LocalDate hoje = LocalDate.now();
			LocalDate inicio = config.getInicioFeriasColetivas();
			LocalDate fim = config.getFimFeriasColetivas();

			if (inicio != null && fim != null) {
				estagiarioDeFerias = !hoje.isBefore(inicio) && !hoje.isAfter(fim);
			}
		}

		boolean bloqueadoPeloAdmin = usuario.getStatus() == UsuarioStatus.BLOQUEADO;
		boolean isBloqueado = estagiarioDeFerias || bloqueadoPeloAdmin;
		boolean isAtivo = usuario.getStatus() != UsuarioStatus.INATIVO;
		Long pessoaId = (usuario.getPessoa() != null) ? usuario.getPessoa().getId() : null;

		return new CustomUserDetails(
				usuario.getId(),
				pessoaId,
				usuario.getLogin(),
				usuario.getPassword(),
				usuario.getRole(),
				isBloqueado,
				isAtivo);
	}
}
