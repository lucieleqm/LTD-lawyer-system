import { useNavigate, useParams } from 'react-router-dom';
import { Processo } from '../types';
import { useEffect, useState } from 'react';
import {
  areaDireitoLabel,
  papelNoProcessoLabel,
  tribunalLabel,
} from '../constants';
import { processosService } from '../services/processosService';
import { AtualizarProcessoRequest } from '../schema';
import { notifications } from '@mantine/notifications';
import { paths } from '@/routes/paths';
import { Center, Loader } from '@mantine/core';
import { EmptyState } from '@/components/ui/EmptyState';
import { PageHeader } from '@/components/ui/PageHeader';
import { ProcessoForm } from '../components/ProcessoForm';
import { LoadingState } from '@/components/ui/LoadingState';

function paraValoresDoForm(processo: Processo) {
  return {
    numeroProcessoPje: processo.numeroProcessoPje,
    areaDireito: processo.areaDireito,
    tribunal: processo.tribunal,
    assunto: processo.assunto,
    vara: processo.vara,
    status: processo.status,
    estagiarioId: processo.estagiarioId,
    estagiarioNome: processo.estagiarioNome,
    advogadoId: processo.advogadoId,
    advogadoNome: processo.advogadoNome,
    assistidoId: processo.assistidoId,
    assistidoNome: processo.assistidoNome,
    partesEnvolvidas: processo.partesEnvolvidas,
    prazoFinal: processo.prazoFinal,
  };
}

export default function EditarProcessoPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [processo, setProcesso] = useState<Processo | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  const opcoesTribunais = Object.entries(tribunalLabel).map(
    ([value, label]) => ({ value, label }),
  );

  const opcoesAreasDireito = Object.entries(areaDireitoLabel).map(
    ([value, label]) => ({ value, label }),
  );
  const opcoesPapeis = Object.entries(papelNoProcessoLabel).map(
    ([value, label]) => ({ value, label }),
  );

  useEffect(() => {
    if (!id) return;

    processosService
      .buscarPorId(Number(id))
      .then(setProcesso)
      .finally(() => setIsLoading(false));
  }, [id]);

  async function handleSalvar(dados: AtualizarProcessoRequest) {
    await processosService.atualizar(Number(id), dados);
    notifications.show({
      message: 'Processo atualizado com sucesso',
      color: 'teal',
    });
    navigate(paths.processos.lista);
  }

  if (isLoading) {
    return <LoadingState />;
  }

  if (!processo) {
    return <EmptyState message="Processo não encontrado." />;
  }

  return (
    <div>
      <PageHeader title="Editar Processo" />
      <ProcessoForm
        modo="editar"
        valoresIniciais={paraValoresDoForm(processo)}
        onSubmit={handleSalvar}
        opcoesAreasDireito={opcoesAreasDireito}
        opcoesPapeis={opcoesPapeis}
        opcoesTribunais={opcoesTribunais}
      />
    </div>
  );
}
