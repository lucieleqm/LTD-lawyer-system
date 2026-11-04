import { useNavigate } from 'react-router-dom';

import { notifications } from '@mantine/notifications';
import { Container } from '@mantine/core';

import { PageHeader } from '@/components/ui/PageHeader';
import { paths } from '@/routes/paths';

import { OrgaoJulgadorForm } from '../components/OrgaoJulgadorForm';
import { orgaosJulgadoresService } from '../services/orgaosJulgadoresService';
import { OrgaoJulgadorRequest } from '../schema';

export default function CriarOrgaoJulgadorPage() {
  const navigate = useNavigate();

  async function handleSalvar(dados: OrgaoJulgadorRequest) {
    await orgaosJulgadoresService.criar(dados);
    notifications.show({
      message: 'Órgão Julgador cadastrado com sucesso',
      color: 'teal',
    });
    navigate(paths.orgaosJulgadores.lista);
  }

  return (
    <Container>
      <PageHeader title="Formulário de Cadastro" />
      <OrgaoJulgadorForm modo="criar" onSubmit={handleSalvar} />
    </Container>
  );
}
