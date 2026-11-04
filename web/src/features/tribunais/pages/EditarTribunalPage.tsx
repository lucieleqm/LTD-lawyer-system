import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { notifications } from '@mantine/notifications';
import { Container } from '@mantine/core';

import { paths } from '@/routes/paths';
import { PageHeader } from '@/components/ui/PageHeader';
import { Tribunal } from '../types';
import { tribunaisService } from '../services/tribunaisService';
import { AtualizarTribunalRequest } from '../schema';

import { EmptyState } from '@/components/ui/EmptyState';
import { LoadingState } from '@/components/ui/LoadingState';
import { TribunalForm } from '../components/TribunalForm';

function paraValoresDoForm(tribunal: Tribunal) {
  return {
    nome: tribunal.nome,
    sigla: tribunal.sigla,
    esfera: tribunal.esfera,
  };
}

export default function EditarTribunalPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [tribunal, setTribunal] = useState<Tribunal | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    if (!id) return;

    tribunaisService
      .buscarPorId(Number(id))
      .then(setTribunal)
      .finally(() => setIsLoading(false));
  }, [id]);

  async function handleSalvar(dados: AtualizarTribunalRequest) {
    await tribunaisService.atualizar(Number(id), dados);
    notifications.show({
      message: 'Tribunal atualizado com sucesso',
      color: 'teal',
    });
    navigate(paths.tribunais.lista);
  }

  if (isLoading) {
    return <LoadingState />;
  }

  if (!tribunal) {
    return <EmptyState message="Tribunal não encontrado." />;
  }

  return (
    <Container>
      <PageHeader title="Editar Tribunal" />
      <TribunalForm
        modo="editar"
        valoresIniciais={paraValoresDoForm(tribunal)}
        onSubmit={handleSalvar}
      />
    </Container>
  );
}
