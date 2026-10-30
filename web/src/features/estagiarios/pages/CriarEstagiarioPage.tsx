import { useNavigate } from 'react-router-dom';

import { notifications } from '@mantine/notifications';
import { Container } from '@mantine/core';

import { paths } from '@/routes/paths';

import { EstagiarioForm } from '../components/EstagiarioForm';
import { estagiariosService } from '../services/estagiariosService';

import { CriarEstagiarioRequest } from '../schema';
import { PageHeader } from '@/components/ui/PageHeader';
import { periodoEstagioLabel } from '../constants';

export default function CriarEstagiarioPage() {
  const navigate = useNavigate();

  const periodosOptions = Object.entries(periodoEstagioLabel).map(
    ([value, label]) => ({ value, label }),
  );

  async function handleSalvar(dados: CriarEstagiarioRequest) {
    await estagiariosService.criar(dados);
    notifications.show({
      message: 'Estagiário cadastrado com sucesso',
      color: 'teal',
    });
    navigate(paths.estagiarios.lista);
  }

  return (
    <Container>
      <PageHeader title="Cadastrar Estagiário" />
      <EstagiarioForm
        modo="criar"
        onSubmit={handleSalvar}
        periodos={periodosOptions}
      />
    </Container>
  );
}
