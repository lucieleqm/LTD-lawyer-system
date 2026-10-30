import { Menu, ActionIcon } from '@mantine/core';
import { IconDots } from '@tabler/icons-react';

export interface RowAction {
  label: string;
  icon: React.ReactNode;
  onClick: () => void;
  color?: string;
  hidden?: boolean;
}

interface RowActionsMenuProps {
  actions: RowAction[];
}

export function RowActionsMenu({ actions }: RowActionsMenuProps) {
  const visiveis = actions.filter((a) => !a.hidden);

  if (visiveis.length === 0) return null;

  return (
    <Menu shadow="md" width={200} position="bottom-end" withinPortal>
      <Menu.Target>
        <ActionIcon variant="subtle" color="gray">
          <IconDots size={16} />
        </ActionIcon>
      </Menu.Target>

      <Menu.Dropdown>
        {visiveis.map((action) => (
          <Menu.Item
            key={action.label}
            leftSection={action.icon}
            color={action.color}
            onClick={action.onClick}
          >
            {action.label}
          </Menu.Item>
        ))}
      </Menu.Dropdown>
    </Menu>
  );
}
