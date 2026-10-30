import { Paper, Title, Grid, Divider } from '@mantine/core';

interface DetailSectionProps {
  title: string;
  children: React.ReactNode;
}

export function DetailSection({ title, children }: DetailSectionProps) {
  return (
    <Paper withBorder p="lg">
      <Divider
        mb="md"
        label={
          <Title order={6} c={'gray.7'}>
            {title}
          </Title>
        }
        labelPosition="left"
      />
      <Grid>{children}</Grid>
    </Paper>
  );
}
