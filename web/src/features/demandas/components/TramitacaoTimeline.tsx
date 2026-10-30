import {
  Timeline,
  Text,
  Group,
  Badge,
  Paper,
  Anchor,
  Spoiler,
} from '@mantine/core';
import {
  IconExternalLink,
  IconMessage,
  IconPaperclip,
} from '@tabler/icons-react';
import { Tramitacao } from '../types';
import { EmptyState } from '@/components/ui/EmptyState';
import { usuarioRoleLabel } from '@/constants/roles';
import {
  tipoTramitacaoColor,
  tipoTramitacaoIcon,
  tipoTramitacaoLabel,
} from '@/constants/tipoTramitacao';
import { formatarData } from '@/utils/formatters';

interface TramitacaoTimelineProps {
  tramitacoes: Tramitacao[];
}

export function TramitacaoTimeline({ tramitacoes }: TramitacaoTimelineProps) {
  if (tramitacoes.length === 0) {
    return <EmptyState />;
  }

  const ordenadas = [...tramitacoes].sort(
    (a, b) => new Date(b.criadoEm).getTime() - new Date(a.criadoEm).getTime(),
  );

  return (
    <Timeline
      active={ordenadas.length}
      bulletSize={24}
      lineWidth={2}
      color="institucional"
    >
      {ordenadas.map((t) => (
        <Timeline.Item
          key={t.id}
          bullet={tipoTramitacaoIcon[t.tipoTramitacao]}
          title={
            <>
              <Group gap={6} wrap="nowrap">
                <Text fw={500} size="sm">
                  {usuarioRoleLabel[t.responsavelRole]}: {t.responsavelNome}
                </Text>
                <Badge
                  size="xs"
                  variant="light"
                  color={tipoTramitacaoColor[t.tipoTramitacao]}
                >
                  {tipoTramitacaoLabel[t.tipoTramitacao]}
                </Badge>
              </Group>
              <Text size="xs" c="dimmed" mt={4}>
                {formatarData(t.criadoEm, 'extenso')}
              </Text>
            </>
          }
        >
          {(t.observacoes || t.linkAnexo) && (
            <Paper withBorder p="xs" radius="sm" mt={4} bg="gray.0">
              {t.observacoes && (
                <Group gap={6} align="flex-start" wrap="nowrap" mb={'xs'}>
                  <IconMessage
                    size={14}
                    color="var(--mantine-color-gray-6)"
                    style={{ marginTop: 2, flexShrink: 0 }}
                  />
                  <Spoiler
                    maxHeight={52}
                    showLabel="Mostrar mais"
                    hideLabel="Ocultar"
                    style={{ flex: 1 }}
                  >
                    <Text size="sm" c={'gray.6'}>
                      {t.observacoes}
                    </Text>
                  </Spoiler>
                </Group>
              )}

              {t.linkAnexo && (
                <Group gap={6} align="flex-start" wrap="nowrap">
                  <IconPaperclip
                    size={14}
                    color="var(--mantine-color-gray-6)"
                    style={{ marginTop: 2 }}
                  />
                  <Anchor href={t.linkAnexo} size="sm">
                    <Group gap={4} wrap="nowrap">
                      <span>Visualizar anexo</span>
                      <IconExternalLink size={14} />
                    </Group>
                  </Anchor>
                </Group>
              )}
            </Paper>
          )}
        </Timeline.Item>
      ))}
    </Timeline>
  );
}
