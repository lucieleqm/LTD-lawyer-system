import { Button, ButtonProps } from '@mantine/core';
import { IconEdit } from '@tabler/icons-react';

interface EditButtonProps extends ButtonProps {
  type?: 'button' | 'submit' | 'reset';
  onClick?: () => void;
}

export function EditButton({ children, ...rest }: EditButtonProps) {
  return (
    <Button
      variant="light"
      color="institucional"
      leftSection={<IconEdit size={18} />}
      {...rest}
    >
      {children || 'Editar'}
    </Button>
  );
}
