package com.advocacia.estacio.modules.demandas.tramitacoes;

import com.advocacia.estacio.modules.demandas.EtapaDemanda;
import com.advocacia.estacio.modules.usuarios.UsuarioRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

// ação/evento histórico
@Getter
@RequiredArgsConstructor
public enum TipoTramitacao {

    ABERTURA(
            "Abertura da Demanda",
            null,
            Set.of(UsuarioRole.COORDENADOR_DO_CURSO, UsuarioRole.ADMIN),
            Set.of()
    ),

    // --- Ações do Aluno / Estagiário ---

    ENVIO_PARA_REVISAO(
        "Envio para Revisão do Professor",
        EtapaDemanda.REVISAO_PROFESSOR,
        Set.of(UsuarioRole.ESTAGIARIO, UsuarioRole.ADMIN),
        Set.of(EtapaDemanda.ELABORACAO)
    ),

    // --- Ações do Professor ---
    DEVOLUCAO_AO_ESTAGIARIO(
            "Devolução para Ajustes do Estagiário",
            EtapaDemanda.ELABORACAO,
            Set.of(UsuarioRole.PROFESSOR, UsuarioRole.ADMIN),
            Set.of(EtapaDemanda.REVISAO_PROFESSOR)
    ),
    APROVACAO_DO_PROFESSOR(
            "Aprovação e Envio ao Advogado",
            EtapaDemanda.VALIDACAO_ADVOGADO,
            Set.of(UsuarioRole.PROFESSOR, UsuarioRole.ADMIN),
            Set.of(EtapaDemanda.REVISAO_PROFESSOR)
    ),

    // --- Ações do Advogado ---
    DEVOLUCAO_AO_PROFESSOR(
            "Devolução para Revisão do Professor",
            EtapaDemanda.REVISAO_PROFESSOR,
            Set.of(UsuarioRole.ADVOGADO, UsuarioRole.ADMIN),
            Set.of(EtapaDemanda.VALIDACAO_ADVOGADO)
    ),
    PROTOCOLO_REALIZADO(
            "Petição Protocolada",
            EtapaDemanda.PROTOCOLADA,
            Set.of(UsuarioRole.ADVOGADO, UsuarioRole.ADMIN),
            Set.of(EtapaDemanda.VALIDACAO_ADVOGADO)
    ),
    ARQUIVAMENTO_DEMANDA(
            "Arquivamento da Demanda",
            EtapaDemanda.ARQUIVADA,
            Set.of(UsuarioRole.ADVOGADO, UsuarioRole.ADMIN),
            Set.of(EtapaDemanda.ELABORACAO, EtapaDemanda.REVISAO_PROFESSOR, EtapaDemanda.VALIDACAO_ADVOGADO, EtapaDemanda.PROTOCOLADA)
    ),

    // --- Ação Neutra (Não altera a etapa atual) ---
    DESPACHO_INTERNO(
            "Anotação / Juntada de Documentos",
            null,
            Set.of(UsuarioRole.ESTAGIARIO, UsuarioRole.PROFESSOR, UsuarioRole.ADVOGADO, UsuarioRole.ADMIN),
            Set.of(EtapaDemanda.ELABORACAO, EtapaDemanda.REVISAO_PROFESSOR, EtapaDemanda.VALIDACAO_ADVOGADO)
    );



    private final String descricao;
    private final EtapaDemanda etapaDestino;
    private final Set<UsuarioRole> papeisPermitidos;
    private final Set<EtapaDemanda> etapasOrigemPermitidas;

    public boolean podeSerExecutadaNa(EtapaDemanda etapaAtual) {
        if (this.etapasOrigemPermitidas.isEmpty()) return true;
        return this.etapasOrigemPermitidas.contains(etapaAtual);
    }
    public boolean podeSerExecutadaPor(UsuarioRole role) {
        return this.papeisPermitidos.contains(role);
    }
}
