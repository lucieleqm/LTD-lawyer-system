import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { notifications } from '@mantine/notifications';
import { Container } from '@mantine/core';

import { paths } from '@/routes/paths';
import { PageHeader } from '@/components/ui/PageHeader';
import { OrgaoJulgador } from '../types';
import { orgaosJulgadoresService } from '../services/orgaosJulgadoresService';
import { OrgaoJulgadorRequest } from '../schema';

import { EmptyState } from '@/components/ui/EmptyState';
import { LoadingState } from '@/components/ui/LoadingState';
import { OrgaoJulgadorForm } from '../components/OrgaoJulgadorForm';

function paraValoresDoForm(orgaoJulgador: OrgaoJulgador) {
  return {
    nome: orgaoJulgador.nome,
    instancia: orgaoJulgador.instancia,
    tribunalId: orgaoJulgador.tribunal.id,
  };
}

export default function EditarOrgaoJulgadorPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [orgaoJulgador, setOrgaoJulgador] = useState<OrgaoJulgador | null>(
    null,
  );
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    if (!id) return;

    orgaosJulgadoresService
      .buscarPorId(Number(id))
      .then(setOrgaoJulgador)
      .finally(() => setIsLoading(false));
  }, [id]);

  async function handleSalvar(dados: OrgaoJulgadorRequest) {
    await orgaosJulgadoresService.atualizar(Number(id), dados);
    notifications.show({
      message: 'Área do Direito atualizada com sucesso',
      color: 'teal',
    });
    navigate(paths.orgaosJulgadores.lista);
  }

  if (isLoading) {
    return <LoadingState />;
  }

  if (!orgaoJulgador) {
    return <EmptyState message="Órgão Julgador não encontrada." />;
  }

  return (
    <Container>
      <PageHeader title="Editar Órgão Julgador" />
      <OrgaoJulgadorForm
        modo="editar"
        valoresIniciais={paraValoresDoForm(orgaoJulgador)}
        onSubmit={handleSalvar}
      />
    </Container>
  );
}
