import { useNavigate } from 'react-router-dom';

import { notifications } from '@mantine/notifications';
import { Container } from '@mantine/core';

import { PageHeader } from '@/components/ui/PageHeader';
import { paths } from '@/routes/paths';

import { TribunalForm } from '../components/TribunalForm';
import { tribunaisService } from '../services/tribunaisService';
import { CriarTribunalRequest } from '../schema';

export default function CriarTribunalPage() {
  const navigate = useNavigate();

  async function handleSalvar(dados: CriarTribunalRequest) {
    await tribunaisService.criar(dados);
    notifications.show({
      message: 'Tribunal cadastrado com sucesso',
      color: 'teal',
    });
    navigate(paths.tribunais.lista);
  }

  return (
    <Container>
      <PageHeader title="Cadastrar Tribunal" />
      <TribunalForm modo="criar" onSubmit={handleSalvar} />
    </Container>
  );
}
