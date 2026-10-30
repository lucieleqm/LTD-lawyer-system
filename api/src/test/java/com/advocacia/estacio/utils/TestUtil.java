package com.advocacia.estacio.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.advocacia.estacio.modules.advogados.Advogado;
import com.advocacia.estacio.modules.advogados.AdvogadoRepository;
import com.advocacia.estacio.modules.assistidos.Assistido;
import com.advocacia.estacio.modules.assistidos.AssistidoRepository;
import com.advocacia.estacio.modules.demandas.repositories.DemandaRepository;
import com.advocacia.estacio.modules.estagiarios.Estagiario;
import com.advocacia.estacio.modules.estagiarios.EstagiarioRepository;
import com.advocacia.estacio.modules.estagiarios.PeriodoEstagio;
import com.advocacia.estacio.modules.pessoas.enderecos.EnderecoRepository;
import com.advocacia.estacio.modules.processos.Processo;
import com.advocacia.estacio.modules.processos.AreaDireito;
import com.advocacia.estacio.modules.processos.ProcessoStatus;
import com.advocacia.estacio.modules.processos.Tribunal;
import com.advocacia.estacio.modules.processos.ProcessoRepository;
import com.advocacia.estacio.modules.processos.movimentacoes.MovimentoRepository;
import com.advocacia.estacio.modules.usuarios.Usuario;
import com.advocacia.estacio.modules.usuarios.UsuarioRepository;
import com.advocacia.estacio.modules.usuarios.UsuarioRole;
import com.advocacia.estacio.modules.usuarios.UsuarioStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.advocacia.estacio.domain.records.AuthenticationDto;
import com.advocacia.estacio.domain.records.RegistroDto;
import com.advocacia.estacio.exceptions.NumeroDoProcessoExistenteException;
import com.advocacia.estacio.infra.security.TokenService;
import com.advocacia.estacio.modules.advogados.AdvogadoService;
import com.advocacia.estacio.modules.assistidos.AssistidoService;
import com.advocacia.estacio.modules.estagiarios.EstagiarioService;

@Component
public class TestUtil {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
    EstagiarioRepository estagiarioRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	ProcessoRepository processoRepository;
	
	@Autowired
	AssistidoRepository assistidoRepository;
	
	@Autowired
	AdvogadoRepository advogadoRepository;
	
	@Autowired
	MovimentoRepository movimentoRepository;
	
	@Autowired
	DemandaRepository demandaRepository;
	
	@Autowired
	AtorRepository atorRepository;
	
	@Autowired
	AdvogadoService advogadoService;
	
	@Autowired
	AssistidoService assistidoService;
	
	@Autowired
	EstagiarioService estagiarioService;
	
	@Autowired
	UsuarioService usuarioService;

	@Autowired
	DemandaRespondeRepository demandaRespondeRepository;

	@Autowired
	DesativarAtivarUsuarioPorDataRepository desativarAtivarUsuarioPorDataRepository;
	
	@Autowired
	TokenService tokenService;

    public void deleteAll() {
		demandaRespondeRepository.deleteAll();
		demandaRepository.deleteAll();
		movimentoRepository.deleteAll();
		processoRepository.deleteAll();
		estagiarioRepository.deleteAll();
		atorRepository.deleteAll();
		assistidoRepository.deleteAll();
		advogadoRepository.deleteAll();
		enderecoRepository.deleteAll();
		usuarioRepository.deleteAll();
		desativarAtivarUsuarioPorDataRepository.deleteAll();
	}
	
	public Processo getProcesso() {

        Assistido assistido = assistidoService.salvar(getAssistidoDto());
        Advogado advogado = advogadoService.salvar(getAdvogadoDto());
        Estagiario estagiario = estagiarioRepository.save(getEstagiario2());

		String numeroDoProcesso =  String.format("%d%s",LocalDate.now().getYear(), "1");

		return new Processo(null, assistido, numeroDoProcesso, "62354", "Seguro", "3210",
				LocalDate.now().plusDays(2L), advogado.getNome(), advogado, estagiario, 
				AreaDireito.CIVIL, Tribunal.ESTADUAL, ProcessoStatus.TRAMITANDO, "",
				null, LocalDateTime.now());
	}
	
	public Processo salvar(ProcessoRequestDto request) {
		Assistido assistido = assistidoService.buscarPorId(request.getAssistidoId());
		Advogado advogado = advogadoService.buscarPorId(request.getAdvogadoId());
		Estagiario estagiario = estagiarioService.buscarPorId(request.getEstagiarioId());
		Processo processo = new Processo(request);
		processo.setAssistido(assistido);
		processo.setAdvogado(advogado);
		processo.setNumeroDoProcesso(gerarNumeroProcesso());
		processo.tramitando();
		processo.setEstagiario(estagiario);
		processo.setUltimaAtualizacao(LocalDateTime.now());
		validarProcesso(processo);
		return processoRepository.save(processo);
	}
	
	private String gerarNumeroProcesso() {
		return String.format("%d%d", LocalDate.now().getYear(), processoRepository.count());
	}
	
	private void validarProcesso(Processo processo) {
		if(processoRepository.existsByNumeroDoProcesso(processo.getNumeroDoProcesso())) {
			throw new NumeroDoProcessoExistenteException();
		}
	}

	public AssistidoDto getAssistidoDto() {
		return new AssistidoDto(null, "Ana Carla", "20250815", "86766523354", 
				"ana@gmail.com","Cientista de Dados", "brasileiro", 
				"São Luís/MA", "Solteiro(a)", "São Luís", "Vila Palmeira", "rua dos nobres", 12, "43012-232");
	}

	public AdvogadoDto getAdvogadoDto() {
		return new AdvogadoDto(null, "Carlos Silva", "carlos@gmail.com",
				"88566519808", "25/09/1996", "São Luís", "Vila Lobão", 
				"rua do passeio", 11, "53022-112", "1234");
	}

	public AdvogadoDto getAdvogadoDto2() {
		return new AdvogadoDto(null, "Mauricio Silva", "mauricio@gmail.com",
				"88281546889", "25/09/1994", "São Luís", "Vila Lobão",
				"rua do passeio", 12, "53022-102", "1234");
	}
	
	public Estagiario getEstagiario() {
		return new Estagiario(
				"Pedro Lucas", "pedro@gmail.com", "92921421224", "20251208",
				PeriodoEstagio.ESTAGIO_I);
	}
	
	public Estagiario getEstagiario2() {
		return new Estagiario(
				"João Lucas", "lucas@gmail.com", "92921421224", "20251209",
				PeriodoEstagio.ESTAGIO_II);
	}

	public EstagiarioDto getEstagiarioDto() {
		return new EstagiarioDto(null,
				"Pedro Lucas", "pedro@gmail.com", "92921421224",
				"20251208", "Estágio I", "1234");
	}

	public EstagiarioDto getEstagiarioDto2() {
		return new EstagiarioDto(null,
				"João Miguel", "joao@gmail.com", "92921421224",
				"20251208", "Estágio II", "1234");
	}
	
	public Usuario getUsuarioAuth() {
		String senha = new BCryptPasswordEncoder().encode("1234");
		return new Usuario("professor@gmail.com", senha, UsuarioRole.ADMIN);
	}

	public DesativarAtivarUsuarioPorData getDataAtivacaoDto() {
		return new DesativarAtivarUsuarioPorData(UsuarioRole.ESTAGIARIO, null, UsuarioStatus.ATIVO);
	}

	public DesativarAtivarUsuarioPorData getDataDesativacaoDto() {
		return new DesativarAtivarUsuarioPorData(UsuarioRole.ESTAGIARIO, null, UsuarioStatus.INATIVO);
	}
	
	public AuthenticationDto getAuthenticationDto() {
		return new AuthenticationDto("professor@gmail.com", "1234");
	}
	
	public List<RegistroDto> getRegistroDtos() {
		return List.of(
				new RegistroDto("professor@gmail.com", "1234", UsuarioRole.ADMIN),
				new RegistroDto("professor2@gmail.com", "1234", UsuarioRole.ADMIN),
				new RegistroDto("professor3@gmail.com", "1234", UsuarioRole.ADMIN));

	}
	
	public List<AtorDto> getAtores() {
		return List.of(
				new AtorDto(null, "Roberto Carlos", "roberto@gmail.com", "Coordenador do curso", "1234"),
				new AtorDto(null, "José Augusto", "jose@gmail.com", "Secretário", "1234"),
				new AtorDto(null, "Fabio Junior", "fabio@gmail.com", "Professor", "1234")
				);
	}
	
	public String getToken() {
		usuarioService.salvar(getRegistroDtos().get(0));
		return usuarioService.login(getAuthenticationDto()).token();
	}
}
