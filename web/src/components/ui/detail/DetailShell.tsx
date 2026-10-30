import { Stack } from '@mantine/core';

interface DetailShellProps {
  children: React.ReactNode;
}

export function DetailShell({ children, ...rest }: DetailShellProps) {
  return (
    <Stack gap="lg" {...rest}>
      {children}
    </Stack>
  );
}
