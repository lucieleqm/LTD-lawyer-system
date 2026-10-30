import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Demanda, Tramitacao } from '../types';
import { demandasService } from '../services/demandasService';
import { LoadingState } from '@/components/ui/LoadingState';
import { EmptyState } from '@/components/ui/EmptyState';
import { DetailShell } from '@/components/ui/detail/DetailShell';
import { DetailHeader } from '@/components/ui/detail/DetailHeader';
import {
  tempestividadeColor,
  tempestividadeLabel,
} from '@/constants/tempestividade';
import { Box, Button, Spoiler, Stack, Text } from '@mantine/core';
import { DetailSection } from '@/components/ui/detail/DetailSection';
import { DetailField } from '@/components/ui/detail/DetailField';
import { etapaDemandaColor, etapaDemandaLabel } from '../constants';
import { formatarData } from '@/utils/formatters';
import { TramitacaoTimeline } from '../components/TramitacaoTimeline';
import { useDisclosure } from '@mantine/hooks';
import { AdicionarTramitacaoModal } from '../components/AdicionarTramitacaoModal';
import { paths } from '@/routes/paths';
import { EditButton } from '@/components/ui/EditButton';
import { usePermission } from '@/features/auth/hooks/usePermission';

export default function DetalheDeamandaPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [demanda, setDemanda] = useState<Demanda | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [tramitacoes, setTramitacoes] = useState<Tramitacao[] | null>(null);

  const podeEditar = usePermission('demandas:editar');

  const [modalAberto, { open: abrirModal, close: fecharModal }] =
    useDisclosure(false);

  function carregar() {
    if (!id) return;
    setIsLoading(true);
    demandasService
      .buscarPorId(Number(id))
      .then(setDemanda)
      .finally(() => setIsLoading(false));
  }

  useEffect(carregar, [id]);

  useEffect(() => {
    if (tramitacoes === null && demanda) {
      demandasService
        .listarTramitacoes(demanda.id)
        .then((resposta) => setTramitacoes(resposta.content));
    }
  }, [tramitacoes, demanda]);

  if (isLoading) return <LoadingState />;
  if (!demanda) return <EmptyState />;

  return (
    <DetailShell>
      <DetailHeader
        title={`Demanda #${demanda.id}`}
        breadcrumbs={[
          { label: 'Início', link: paths.home },
          { label: 'Demandas', link: paths.demandas.lista },
          { label: `Demanda #${demanda.id}` },
        ]}
        actions={
          <>
            <Button onClick={abrirModal}>Nova Tramitação</Button>
            {podeEditar && (
              <EditButton
                onClick={() => navigate(paths.demandas.editar(demanda.id))}
              />
            )}
          </>
        }
        badges={[
          {
            label: tempestividadeLabel[demanda.tempestividade],
            color: tempestividadeColor[demanda.tempestividade],
            variant: 'light',
          },
          {
            label: etapaDemandaLabel[demanda.etapaAtual],
            color: etapaDemandaColor[demanda.etapaAtual],
            variant: 'outline',
          },
        ]}
      />

      <DetailSection title="Informações Gerais">
        <DetailField label="Estagiário" value={demanda.estagiarioNome} />
        <DetailField label="Advogado" value={demanda.advogadoNome} />
        <DetailField label="Professor" value={demanda.professorNome} />

        <DetailField
          label="Prazo Final"
          value={formatarData(demanda.prazoFinal)}
        />
        <DetailField
          label="Prazo da Documentação"
          value={formatarData(demanda.prazoDocumentos)}
        />

        <DetailField
          label="Data de Abertura"
          value={formatarData(demanda.dataAbertura)}
        />

        <Box>
          <Text size="sm" c="dimmed" mb={4}>
            Descrição
          </Text>
          <Spoiler maxHeight={42} hideLabel="Ocultar" showLabel="Mostrar mais">
            <Text size="sm">{demanda.descricao}</Text>
          </Spoiler>
        </Box>
      </DetailSection>

      <Stack title="Tramitações" mb="sm">
        <Text size="sm" c="dimmed">
          Aqui você pode ver todas as tramitações registradas para esta demanda.
        </Text>

        {tramitacoes === null ? (
          <LoadingState message="Carregando ..." />
        ) : tramitacoes.length === 0 ? (
          <EmptyState message="Nenhuma tramitação registada para esta demanda." />
        ) : (
          <TramitacaoTimeline tramitacoes={tramitacoes} />
        )}
      </Stack>

      <AdicionarTramitacaoModal
        opened={modalAberto}
        onClose={fecharModal}
        demandaId={demanda.id}
        onSuccess={() => {
          carregar();
          setTramitacoes(null);
        }}
      />
    </DetailShell>
  );
}
