import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { notifications } from '@mantine/notifications';
import { Container } from '@mantine/core';

import { paths } from '@/routes/paths';
import { PageHeader } from '@/components/ui/PageHeader';
import { AreaDireito } from '../types';
import { areasDireitoService } from '../services/areasDireitoService';
import { AreaDireitoRequest } from '../schema';

import { EmptyState } from '@/components/ui/EmptyState';
import { LoadingState } from '@/components/ui/LoadingState';
import { AreaDireitoForm } from '../components/AreaDireitoForm';

function paraValoresDoForm(areaDireito: AreaDireito) {
  return {
    nome: areaDireito.nome,
  };
}

export default function EditarAreaDireitoPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [areaDireito, setAreaDireito] = useState<AreaDireito | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    if (!id) return;

    areasDireitoService
      .buscarPorId(Number(id))
      .then(setAreaDireito)
      .finally(() => setIsLoading(false));
  }, [id]);

  async function handleSalvar(dados: AreaDireitoRequest) {
    await areasDireitoService.atualizar(Number(id), dados);
    notifications.show({
      message: 'Área do Direito atualizada com sucesso',
      color: 'teal',
    });
    navigate(paths.areasDireito.lista);
  }

  if (isLoading) {
    return <LoadingState />;
  }

  if (!areaDireito) {
    return <EmptyState message="Área do Direito não encontrada." />;
  }

  return (
    <Container>
      <PageHeader title="Editar Área do Direito" />
      <AreaDireitoForm
        modo="editar"
        valoresIniciais={paraValoresDoForm(areaDireito)}
        onSubmit={handleSalvar}
      />
    </Container>
  );
}
