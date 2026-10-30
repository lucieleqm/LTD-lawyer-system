package com.advocacia.estacio.infra.seed;

import com.advocacia.estacio.modules.pessoas.Pessoa;
import com.advocacia.estacio.modules.pessoas.PessoaRepository;
import com.advocacia.estacio.modules.usuarios.Usuario;
import com.advocacia.estacio.modules.usuarios.UsuarioRepository;
import com.advocacia.estacio.modules.usuarios.UsuarioRole;
import com.advocacia.estacio.modules.usuarios.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PessoaRepository pessoaRepository;
    private final UsuarioService usuarioService;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password:#{null}}")
    private String adminPassword;

    @Override
    @Transactional
    public void run(String... args) {
        if (adminPassword == null) {
            throw new IllegalStateException("ADMIN_PASSWORD não configurada. Defina a variável de ambiente antes de iniciar em produção.");
        }

        if (usuarioRepository.findByLogin(adminEmail).isEmpty()) {
            Usuario usuarioAdmin = usuarioService.cadastrar(adminEmail, adminPassword, UsuarioRole.ADMIN);

            Pessoa pessoaAdmin = Pessoa.builder()
                    .nome("Administrador do Sistema")
                    .telefone("98999999999")
                    .email(adminEmail)
                    .build();

            pessoaAdmin.vincularUsuario(usuarioAdmin);

            pessoaRepository.save(pessoaAdmin);
            System.out.println("✅ Usuário ADMIN (com Pessoa) criado com sucesso! Login: " + adminEmail);
        }
    }
}
