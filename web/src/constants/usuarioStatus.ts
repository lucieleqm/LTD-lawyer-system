export const USUARIO_STATUS = {
  ATIVO: 'ATIVO',
  INATIVO: 'INATIVO',
  BLOQUEADO: 'BLOQUEADO',
} as const;

export type UsuarioStatus =
  (typeof USUARIO_STATUS)[keyof typeof USUARIO_STATUS];

export const usuarioStatusLabel: Record<UsuarioStatus, string> = {
  [USUARIO_STATUS.ATIVO]: 'Ativo',
  [USUARIO_STATUS.INATIVO]: 'Inativo',
  [USUARIO_STATUS.BLOQUEADO]: 'Bloqueado',
};

export const usuarioStatusColor: Record<UsuarioStatus, string> = {
  [USUARIO_STATUS.ATIVO]: 'teal',
  [USUARIO_STATUS.INATIVO]: 'gray',
  [USUARIO_STATUS.BLOQUEADO]: 'yellow',
};
