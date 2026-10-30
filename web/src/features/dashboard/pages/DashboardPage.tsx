import { useAuth } from '@/features/auth/hooks/useAuth';
import { SimpleGrid, Stack, Text, Title, Group, Divider } from '@mantine/core';
import { DashboardCard } from '../components/DashboardCard';
import { getDashboardSectionsForRole } from '../dashboardConfig';

export function DashboardPage() {
  const { user } = useAuth();
  const sections = getDashboardSectionsForRole(user!.role);

  return (
    <Stack gap="xl">
      <div>
        <Title order={2} c="institucional.8">
          Olá, {user?.login}
        </Title>
        <Text c="dimmed" size="sm">
          Aqui está um resumo das áreas disponíveis para você.
        </Text>
      </div>

      {sections.map((section) => (
        <div key={section.title}>
          <Group gap="xs" mb="md">
            <Text
              fw={700}
              size="sm"
              c={`${section.color}.9`}
              tt="uppercase"
              style={{ letterSpacing: 0.5 }}
            >
              {section.title}
            </Text>
            <Divider style={{ flex: 1 }} />
          </Group>

          <SimpleGrid cols={{ base: 1, sm: 2, lg: 4 }} spacing="md">
            {section.cards.map((card) => (
              <DashboardCard key={card.to} {...card} color={section.color} />
            ))}
          </SimpleGrid>
        </div>
      ))}
    </Stack>
  );
}
