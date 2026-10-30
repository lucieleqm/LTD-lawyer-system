import { ROLE, usuarioRoleLabel } from '@/constants/roles';

export const ROLES_ADMINISTRATIVAS = [
  ROLE.COORDENADOR_DO_CURSO,
  ROLE.SECRETARIO,
];

export const OPCOES_SELECT_ADMINISTRATIVO = ROLES_ADMINISTRATIVAS.map(
  (role) => ({
    value: role,
    label: usuarioRoleLabel[role],
  }),
);
