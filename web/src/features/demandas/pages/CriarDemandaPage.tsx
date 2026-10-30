import { useNavigate } from 'react-router-dom';

import { notifications } from '@mantine/notifications';
import { Container } from '@mantine/core';

import { paths } from '@/routes/paths';
import { PageHeader } from '@/components/ui/PageHeader';

import { DemandaForm } from '../components/DemandaForm';
import { demandasService } from '../services/demandasService';
import { CriarDemandaRequest } from '../schema';
import { etapaDemandaLabel } from '../constants';

export default function CriarDemandaPage() {
  const navigate = useNavigate();

  const opcoesEtapas = Object.entries(etapaDemandaLabel).map(
    ([value, label]) => ({ value, label }),
  );

  async function handleSalvar(dados: CriarDemandaRequest) {
    await demandasService.criar(dados);
    notifications.show({
      message: 'Demanda registrada com sucesso',
      color: 'teal',
    });
    navigate(paths.demandas.lista);
  }

  return (
    <Container pos="relative">
      <PageHeader title="Nova Demanda" />

      <DemandaForm
        modo="criar"
        onSubmit={handleSalvar}
        opcoesEtapas={opcoesEtapas}
      />
    </Container>
  );
}
