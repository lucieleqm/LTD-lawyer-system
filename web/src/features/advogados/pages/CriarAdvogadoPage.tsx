import { useNavigate } from 'react-router-dom';

import { notifications } from '@mantine/notifications';
import { Container } from '@mantine/core';

import { paths } from '@/routes/paths';

import { AdvogadoForm } from '../components/AdvogadoForm';
import { advogadosService } from '../services/advogadosService';
import { CriarAdvogadoRequest } from '../schema';
import { PageHeader } from '@/components/ui/PageHeader';

export default function CriarAdvogadoPage() {
  const navigate = useNavigate();

  async function handleSalvar(dados: CriarAdvogadoRequest) {
    await advogadosService.criar(dados);
    notifications.show({
      message: 'Advogado cadastrado com sucesso',
      color: 'teal',
    });
    navigate(paths.advogados.lista);
  }

  return (
    <Container>
      <PageHeader title="Cadastrar Advogado" />

      <AdvogadoForm modo="criar" onSubmit={handleSalvar} />
    </Container>
  );
}
