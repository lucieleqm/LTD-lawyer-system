import {
  Anchor,
  Box,
  BoxProps,
  Breadcrumbs,
  Group,
  Text,
  Title,
} from '@mantine/core';
import { IconProps } from '@tabler/icons-react';
import { ReactElement } from 'react';
import { Link } from 'react-router-dom';

export interface BreadcrumbItem {
  label: string;
  link?: string;
}
export interface PageHeaderProps extends BoxProps {
  title?: string;
  icon?: ReactElement<IconProps>;
  description?: string;
  breadcrumbs?: BreadcrumbItem[];
}

export function PageHeader({
  icon,
  title,
  description,
  breadcrumbs,
  ...rest
}: PageHeaderProps) {
  return (
    <Box mb="xl" {...rest}>
      {breadcrumbs && breadcrumbs.length > 0 && (
        <Breadcrumbs mb="xs">
          {breadcrumbs.map((item, index) =>
            item.link ? (
              <Anchor component={Link} to={item.link} key={index} size="sm">
                {item.label}
              </Anchor>
            ) : (
              <Text key={index} size="sm" c="dimmed">
                {item.label}
              </Text>
            ),
          )}
        </Breadcrumbs>
      )}
      <Group gap="xs" align="flex-start">
        {icon}
        <Title order={3}>{title}</Title>
      </Group>

      {description && (
        <Text size="sm" c="dimmed">
          {description}
        </Text>
      )}
    </Box>
  );
}
