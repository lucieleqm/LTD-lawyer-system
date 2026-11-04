import { useNavigate } from 'react-router-dom';

import { notifications } from '@mantine/notifications';
import { Container } from '@mantine/core';

import { PageHeader } from '@/components/ui/PageHeader';
import { paths } from '@/routes/paths';

import { AreaDireitoForm } from '../components/AreaDireitoForm';
import { areasDireitoService } from '../services/areasDireitoService';
import { AreaDireitoRequest } from '../schema';

export default function CriarAreaDireitoPage() {
  const navigate = useNavigate();

  async function handleSalvar(dados: AreaDireitoRequest) {
    await areasDireitoService.criar(dados);
    notifications.show({
      message: 'Área do Direito cadastrada com sucesso',
      color: 'teal',
    });
    navigate(paths.areasDireito.lista);
  }

  return (
    <Container>
      <PageHeader title="Formulário de Cadastro" />
      <AreaDireitoForm modo="criar" onSubmit={handleSalvar} />
    </Container>
  );
}
