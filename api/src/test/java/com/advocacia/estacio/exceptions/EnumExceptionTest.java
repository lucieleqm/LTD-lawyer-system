package com.advocacia.estacio.exceptions;

import com.advocacia.estacio.modules.demandas.enums.EtapaDemanda;
import com.advocacia.estacio.modules.demandas.enums.RespondidoPor;
import com.advocacia.estacio.modules.demandas.services.DemandaService;
import com.advocacia.estacio.modules.estagiarios.PeriodoEstagio;
import com.advocacia.estacio.modules.pessoas.EstadoCivil;
import com.advocacia.estacio.modules.processos.AreaDireito;
import com.advocacia.estacio.modules.processos.ProcessoStatus;
import com.advocacia.estacio.modules.processos.Tribunal;
import com.advocacia.estacio.modules.usuarios.UsuarioRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class EnumExceptionTest {

	@Autowired
	DemandaService demandaService;

	// ÁREA DO DIREITO
	@Test
	@DisplayName("Deve lançar exceção EnumException para AreaDoDireito por código")
	void EnumException_AreaDireito_codigo() {
		assertThrows(EnumException.class, () -> AreaDireito.toEnum(6));
	}

	@Test
	@DisplayName("Deve lançar exceção EnumException para AreaDoDireito por descricao")
	void EnumException_AreaDireito_descricao() {
		assertThrows(EnumException.class, () -> AreaDireito.toEnum("error"));
	}

	// DEMANDA STATUS
	@Test
	@DisplayName("Deve lançar exceção EnumException para DemandaStatus por código")
	void EnumException_DemandaStatus_getDemandaStatus() {
		assertThrows(EnumException.class, () -> demandaService.getDemandaStatus(UsuarioRole.COORDENADOR_DO_CURSO));
	}

	@Test
	@DisplayName("Deve lançar exceção EnumException para DemandaStatus por código")
	void EnumException_DemandaStatus_codigo() {
		assertThrows(EnumException.class, () -> EtapaDemanda.toEnum(20));
	}

	@Test
	@DisplayName("Deve lançar exceção EnumException para DemandaStatus por status")
	void EnumException_DemandaStatus_status() {
		assertThrows(EnumException.class, () -> EtapaDemanda.toEnum("error"));
	}

	// ESTADO CIVIL
	@Test
	@DisplayName("Deve lançar exceção EnumException para EstadoCivil por código")
	void EnumException_EstadoCivil_codigo() {
		assertThrows(EnumException.class, () -> EstadoCivil.toEnum(6));
	}

	@Test
	@DisplayName("Deve lançar exceção EnumException para EstadoCivil por estado")
	void EnumException_EstadoCivil_estado() {
		assertThrows(EnumException.class, () -> EstadoCivil.toEnum("error"));
	}

	// PERIODO ESTADO
	@Test
	@DisplayName("Deve lançar exceção EnumException para PeriodoEstagio por codigo")
	void EnumException_PeriodoEstagio_codigo() {
		assertThrows(EnumException.class, () -> PeriodoEstagio.toEnum(6));
	}

	@Test
	@DisplayName("Deve lançar exceção EnumException para PeriodoEstagio por tipo")
	void EnumException_PeriodoEstagio_tipo() {
		assertThrows(EnumException.class, () -> PeriodoEstagio.toEnum("error"));
	}

	// RESPONDIDO POR
	@Test
	@DisplayName("Deve lançar exceção EnumException para RespondidoPor por codigo")
	void EnumException_RespondidoPor_codigo() {
		assertThrows(EnumException.class, () -> RespondidoPor.toEnum(6));
	}

	@Test
	@DisplayName("Deve lançar exceção EnumException para RespondidoPor por tipo")
	void EnumException_RespondidoPor_tipo()  {
		assertThrows(EnumException.class, () -> RespondidoPor.toEnum("error"));
	}

	// STATUS PROCESSO
	@Test
	@DisplayName("Deve lançar exceção EnumException para StatusProcesso por tipo")
	void EnumException_StatusProcesso_codigo() {
		assertThrows(EnumException.class, () -> ProcessoStatus.toEnum(6));
	}

	@Test
	@DisplayName("Deve lançar exceção EnumException para StatusProcesso por tipo")
	void EnumException_StatusProcesso_tipo() {
		assertThrows(EnumException.class, () -> ProcessoStatus.toEnum("error"));
	}

	// TIPO DO ATOR
	@Test
	@DisplayName("Deve lançar exceção EnumException para TipoDoAtor por codigo")
	void EnumException_TipoDoAtor_codigo() {
		assertThrows(EnumException.class, () -> TipoDoAtor.toEnum(6));
	}

	@Test
	@DisplayName("Deve lançar exceção EnumException para TipoDoAtor por tipo")
	void EnumException_TipoDoAtor_tipo() {
		assertThrows(EnumException.class, () -> TipoDoAtor.toEnum("error"));
	}

	// TRIBUNAL
	@Test
	@DisplayName("Deve lançar exceção EnumException para Tribunal por codigo")
	void EnumException_Tribunal_codigo() {
		assertThrows(EnumException.class, () -> Tribunal.toEnum(6));
	}

	@Test
	@DisplayName("Deve lançar exceção EnumException para Tribunal por tipo")
	void EnumException_Tribunal_tipo() {
		assertThrows(EnumException.class, () -> Tribunal.toEnum("error"));
	}
}
