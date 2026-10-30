import { Center, Loader, Stack, Text } from '@mantine/core';

interface LoadingStateProps {
  message?: string;
  size?: 'sm' | 'md' | 'lg' | 'xl';
}

export function LoadingState({
  message = 'Carregando...',
  size = 'sm',
}: LoadingStateProps) {
  return (
    <Center py={64} w="100%" h="100%">
      <Stack align="center" gap="xs">
        <Loader color="institucional" size={size} />
        <Text c="dimmed" size={size}>
          {message}
        </Text>
      </Stack>
    </Center>
  );
}
