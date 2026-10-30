export const ROLE = {
  ADMIN: 'ADMIN',
  COORDENADOR_DO_CURSO: 'COORDENADOR_DO_CURSO',
  SECRETARIO: 'SECRETARIO',
  PROFESSOR: 'PROFESSOR',
  ADVOGADO: 'ADVOGADO',
  ESTAGIARIO: 'ESTAGIARIO',
} as const;

export type Role = (typeof ROLE)[keyof typeof ROLE];

export const usuarioRoleLabel: Record<Role, string> = {
  [ROLE.ADMIN]: 'Admin',
  [ROLE.COORDENADOR_DO_CURSO]: 'Coordenador(a) do Curso',
  [ROLE.SECRETARIO]: 'Secretário(a)',
  [ROLE.PROFESSOR]: 'Professor(a)',
  [ROLE.ADVOGADO]: 'Advogado(a)',
  [ROLE.ESTAGIARIO]: 'Estagiário(a)',
};
