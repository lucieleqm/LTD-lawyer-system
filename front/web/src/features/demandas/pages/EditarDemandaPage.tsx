import { useNavigate, useParams } from 'react-router-dom';
import { Demanda } from '../types';
import { useEffect, useState } from 'react';
import { demandasService } from '../services/demandasService';
import { AtualizarDemandaRequest } from '../schema';
import { notifications } from '@mantine/notifications';
import { paths } from '@/routes/paths';
import { LoadingState } from '@/components/ui/LoadingState';
import { EmptyState } from '@/components/ui/EmptyState';
import { PageHeader } from '@/components/ui/PageHeader';
import { DemandaForm } from '../components/DemandaForm';
import { etapaDemandaLabel } from '../constants';

function paraValoresDoForm(demanda: Demanda) {
  return {
    descricao: demanda.descricao,
    prazoFinal: demanda.prazoFinal,
    prazoDocumentos: demanda.prazoDocumentos,
    etapaAtual: demanda.etapaAtual,
    tempestividade: demanda.tempestividade,
    advogadoId: demanda.advogadoId,
    advogadoNome: demanda.advogadoNome,
    estagiarioId: demanda.estagiarioId,
    estagiarioNome: demanda.estagiarioNome,
    professorId: demanda.professorId,
    professorNome: demanda.professorNome,
  };
}

export default function EditarDemandaPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [demanda, setDemanda] = useState<Demanda | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  const opcoesEtapas = Object.entries(etapaDemandaLabel).map(
    ([value, label]) => ({ value, label }),
  );

  useEffect(() => {
    if (!id) return;

    demandasService
      .buscarPorId(Number(id))
      .then(setDemanda)
      .finally(() => setIsLoading(false));
  }, [id]);

  async function handleSalvar(dados: AtualizarDemandaRequest) {
    await demandasService.atualizar(Number(id), dados);
    notifications.show({
      message: 'Demanda atualizada com sucesso',
      color: 'teal',
    });
    navigate(paths.demandas.lista);
  }

  if (isLoading) {
    return <LoadingState />;
  }

  if (!demanda) {
    return <EmptyState message="Demanda não encontrada." />;
  }

  return (
    <div>
      <PageHeader title="Editar Demanda" />
      <DemandaForm
        modo="editar"
        valoresIniciais={paraValoresDoForm(demanda)}
        onSubmit={handleSalvar}
        opcoesEtapas={opcoesEtapas}
      />
    </div>
  );
}
