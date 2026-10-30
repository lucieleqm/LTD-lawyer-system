import { Card, Text, ThemeIcon, Group } from '@mantine/core';
import { IconChevronRight } from '@tabler/icons-react';
import { useNavigate } from 'react-router-dom';
import classes from './DashboardCard.module.css';

interface DashboardCardProps {
  title: string;
  description: string;
  icon: React.ReactNode;
  to: string;
  color?: string;
}

export function DashboardCard({
  title,
  description,
  icon,
  to,
  color = 'institucional',
}: DashboardCardProps) {
  const navigate = useNavigate();

  return (
    <Card
      withBorder
      padding="lg"
      radius="md"
      onClick={() => navigate(to)}
      className={classes.card}
      style={
        {
          cursor: 'pointer',
          '--card-color': `var(--mantine-color-${color}-6)`,
        } as React.CSSProperties
      }
    >
      <Group justify="space-between" mb="sm">
        <ThemeIcon
          size="lg"
          radius="md"
          className={classes.icon}
          color={color}
          variant="light"
        >
          {icon}
        </ThemeIcon>
        <IconChevronRight size={16} color="var(--mantine-color-gray-4)" />
      </Group>

      <Text fw={600} mb={4} className={classes.title}>
        {title}
      </Text>
      <Text size="sm" c="dimmed">
        {description}
      </Text>
    </Card>
  );
}
