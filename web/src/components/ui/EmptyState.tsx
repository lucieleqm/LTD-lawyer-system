import { Center, Stack, Text } from '@mantine/core';
import { IconDatabaseOff } from '@tabler/icons-react';
import React from 'react';

interface EmptyStateProps {
  icon?: React.ReactNode;
  message?: string;
  description?: string;
}

export function EmptyState({
  icon = <IconDatabaseOff size={32} color="var(--mantine-color-gray-4)" />,
  message = 'Nenhum registro encontrado.',
  description,
}: EmptyStateProps) {
  return (
    <Center py={64}>
      <Stack align="center" gap={4}>
        {icon}
        <Text c="dimmed" size="sm">
          {message}
        </Text>
        {description && <Text c="dimmed">{description}</Text>}
      </Stack>
    </Center>
  );
}
