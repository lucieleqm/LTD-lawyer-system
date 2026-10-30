import { useNavigate } from 'react-router-dom';

import { notifications } from '@mantine/notifications';
import { Container } from '@mantine/core';

import { paths } from '@/routes/paths';

import { CriarAdministrativoRequest } from '../schema';
import { PageHeader } from '@/components/ui/PageHeader';
import { administrativoService } from '../services/administrativoService';
import { AdministrativoForm } from '../components/AdministrativoForm';
import { Role, usuarioRoleLabel } from '@/constants/roles';

export default function CriarAdministrativoPage() {
  const navigate = useNavigate();

  async function handleSalvar(dados: CriarAdministrativoRequest) {
    await administrativoService.criar(dados);
    notifications.show({
      message: `${usuarioRoleLabel[dados.role as Role]} cadastrado com sucesso`,
      color: 'teal',
    });
    navigate(paths.administrativo.lista);
  }

  return (
    <Container>
      <PageHeader title="Cadastrar Coordenador ou Secretário" />
      <AdministrativoForm modo="criar" onSubmit={handleSalvar} />
    </Container>
  );
}
