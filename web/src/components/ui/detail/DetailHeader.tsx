import { Group, Badge, Stack, BadgeProps} from '@mantine/core';
import { PageHeader, PageHeaderProps } from '../PageHeader';
interface DetailHeaderProps extends PageHeaderProps {
  badges?: (BadgeProps & { label: string })[];
  actions?: React.ReactNode;
}

export function DetailHeader({
  badges,
  actions,
  ...pageHeaderProps
}: DetailHeaderProps) {
  return (
    <Group justify="space-between" align="flex-start" mb="md">
      <Stack gap="xs">
        <PageHeader {...pageHeaderProps} mb={0} />
        {badges && badges.length > 0 && (
          <Group gap="xs">
            {badges.map((badge) => {
              const { label, ...props } = badge;

              return (
                <Badge key={label} {...props}>
                  {label}
                </Badge>
              );
            })}
          </Group>
        )}
      </Stack>
      <Group gap="md" align="flex-end">
        {actions}
      </Group>
    </Group>
  );
}
