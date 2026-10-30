import { useNavigate } from 'react-router-dom';

import { notifications } from '@mantine/notifications';
import { Container } from '@mantine/core';

import { paths } from '@/routes/paths';

import { AssistidoForm } from '../components/AssistidoForm';
import { assistidosService } from '../services/assistidosService';
import { CriarAssistidoRequest } from '../schema';
import { estadoCivilLabel } from '@/constants/estadoCivil';
import { PageHeader } from '@/components/ui/PageHeader';

export default function CriarAssistidoPage() {
  const navigate = useNavigate();

  const estadosCivisOptions = Object.entries(estadoCivilLabel).map(
    ([value, label]) => ({ value, label }),
  );

  async function handleSalvar(dados: CriarAssistidoRequest) {
    await assistidosService.criar(dados);
    notifications.show({
      message: 'Assistido cadastrado com sucesso',
      color: 'teal',
    });
    navigate(paths.assistidos.lista);
  }

  return (
    <Container>
      <PageHeader title="Cadastrar Assistido" />
      <AssistidoForm
        modo="criar"
        onSubmit={handleSalvar}
        estadosCivis={estadosCivisOptions}
      />
    </Container>
  );
}
