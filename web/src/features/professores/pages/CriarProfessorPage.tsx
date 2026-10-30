import { useNavigate } from 'react-router-dom';

import { notifications } from '@mantine/notifications';
import { Container } from '@mantine/core';

import { PageHeader } from '@/components/ui/PageHeader';
import { paths } from '@/routes/paths';

import { ProfessorForm } from '../components/ProfessorForm';
import { professoresService } from '../services/professoresService';
import { CriarProfessorRequest } from '../schema';

export default function CriarProfessorPage() {
  const navigate = useNavigate();

  async function handleSalvar(dados: CriarProfessorRequest) {
    await professoresService.criar(dados);
    notifications.show({
      message: 'Professor cadastrado com sucesso',
      color: 'teal',
    });
    navigate(paths.professores.lista);
  }

  return (
    <Container>
      <PageHeader title="Cadastrar Professor" />
      <ProfessorForm modo="criar" onSubmit={handleSalvar} />
    </Container>
  );
}
