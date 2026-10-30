import { Grid, Text } from '@mantine/core';

interface DetailFieldProps {
  label: string;
  value: React.ReactNode;
  span?: number;
}

export function DetailField({ label, value, span = 4 }: DetailFieldProps) {
  return (
    <Grid.Col span={span}>
      <Text size="sm" c="dimmed">
        {label}
      </Text>
      <Text size="sm">{value ?? '-'}</Text>
    </Grid.Col>
  );
}
