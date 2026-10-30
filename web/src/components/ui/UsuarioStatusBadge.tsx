import { Badge, BadgeProps } from '@mantine/core';
import {
  UsuarioStatus,
  usuarioStatusLabel,
  usuarioStatusColor,
} from '@/constants/usuarioStatus';

interface Props extends BadgeProps {
  status: UsuarioStatus;
}

export function UsuarioStatusBadge({ status, ...badgeProps }: Props) {
  return (
    <Badge color={usuarioStatusColor[status]} {...badgeProps}>
      {usuarioStatusLabel[status]}
    </Badge>
  );
}
