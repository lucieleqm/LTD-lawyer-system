import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Processo } from '../types';
import { processosService } from '../services/processosService';
import { LoadingState } from '@/components/ui/LoadingState';
import { EmptyState } from '@/components/ui/EmptyState';
import {
  DetailField,
  DetailHeader,
  DetailSection,
  DetailShell,
} from '@/components/ui/detail';
import { paths } from '@/routes/paths';
import { Table, Text } from '@mantine/core';
import { formatarData } from '@/utils/formatters';
import {
  areaDireitoLabel,
  papelNoProcessoLabel,
  processoStatusLabel,
  tribunalLabel,
} from '../constants';
import { usePermission } from '@/features/auth/hooks/usePermission';
import { EditButton } from '@/components/ui/EditButton';

export default function DetalheProcessoPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [processo, setProcesso] = useState<Processo | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  const podeEditar = usePermission('processos:editar');

  function carregar() {
    if (!id) return;
    setIsLoading(true);
    processosService
      .buscarPorId(Number(id))
      .then(setProcesso)
      .finally(() => setIsLoading(false));
  }

  useEffect(carregar, [id]);

  if (isLoading) return <LoadingState />;
  if (!processo) return <EmptyState />;

  return (
    <DetailShell>
      <DetailHeader
        title={`Processo ${processo.numeroProcesso}`}
        breadcrumbs={[
          { label: 'Início', link: paths.home },
          { label: 'Processos', link: paths.processos.lista },
          { label: `Processo ${processo.numeroProcesso}` },
        ]}
        actions={
          podeEditar && (
            <EditButton
              onClick={() => navigate(paths.processos.editar(processo.id))}
            />
          )
        }
      />

      <DetailSection title="Informações Gerais">
        <DetailField
          label="Número do Processo"
          value={processo.numeroProcesso}
        />
        <DetailField
          label="Número Pje do Processo"
          value={processo.numeroProcessoPje}
        />

        <DetailField
          label="Prazo Final"
          value={formatarData(processo.prazoFinal)}
        />

        <DetailField label="Estagiário" value={processo.estagiarioNome} />
        <DetailField label="Advogado" value={processo.advogadoNome} />
        <DetailField label="Assistido" value={processo.assistidoNome} />

        <DetailField
          label="Área do Direito"
          value={areaDireitoLabel[processo.areaDireito]}
        />
        <DetailField
          label="Tribunal"
          value={tribunalLabel[processo.tribunal]}
        />
        <DetailField label="Vara" value={processo.vara} />
        <DetailField label="Assunto" value={processo.assunto} />

        <DetailField
          label="Status"
          value={processoStatusLabel[processo.status]}
        />

        <DetailField
          label="Criado em"
          value={formatarData(processo.criadoEm)}
        />

        <DetailField
          label="Última atualização"
          value={formatarData(processo.atualizadoEm)}
        />
      </DetailSection>

      <DetailSection title="Outras Partes Envolvidas">
        {processo.partesEnvolvidas && processo.partesEnvolvidas.length > 0 ? (
          <Table striped highlightOnHover withTableBorder>
            <Table.Thead>
              <Table.Tr>
                <Table.Th>Nome</Table.Th>
                <Table.Th>Papel no Processo</Table.Th>
                <Table.Th>Especificação</Table.Th>
              </Table.Tr>
            </Table.Thead>
            <Table.Tbody>
              {processo.partesEnvolvidas.map((parte) => (
                <Table.Tr key={parte.id}>
                  <Table.Td>
                    <Text fw={500} size="sm">
                      {parte.nome}
                    </Text>
                  </Table.Td>
                  <Table.Td>
                    <Text fw={500} size="sm">
                      {
                        papelNoProcessoLabel[
                          parte.papel as keyof typeof papelNoProcessoLabel
                        ]
                      }
                    </Text>
                  </Table.Td>
                  <Table.Td>
                    {parte.especificacaoPapel ? (
                      <Text size="sm" c="dimmed">
                        {parte.especificacaoPapel}
                      </Text>
                    ) : (
                      <Text size="sm" c="dimmed" fs="italic">
                        Não informada
                      </Text>
                    )}
                  </Table.Td>
                </Table.Tr>
              ))}
            </Table.Tbody>
          </Table>
        ) : (
          <Text c="dimmed" fs="italic" size="sm" py="sm">
            Nenhuma parte envolvida registrada neste processo.
          </Text>
        )}
      </DetailSection>
    </DetailShell>
  );
}
