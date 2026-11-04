import { ROLE, Role } from '@/constants/roles';

const permissions = {
  // Tribunais
  'tribunais:criar': [ROLE.ADMIN],
  'tribunais:editar': [ROLE.ADMIN],
  'tribunais:visualizar': [ROLE.ADMIN],
  'tribunais:arquivar': [ROLE.ADMIN],
  'tribunais:ativar': [ROLE.ADMIN],
  'tribunais:excluir': [ROLE.ADMIN],

  // Órgãos Julgadores
  'orgaosJulgadores:criar': [ROLE.ADMIN],
  'orgaosJulgadores:editar': [ROLE.ADMIN],
  'orgaosJulgadores:visualizar': [ROLE.ADMIN],

  // Áreas de Direito
  'areasDireito:criar': [ROLE.ADMIN],
  'areasDireito:editar': [ROLE.ADMIN],
  'areasDireito:visualizar': [ROLE.ADMIN],

  // Processos
  'processos:criar': [ROLE.COORDENADOR_DO_CURSO, ROLE.ADMIN],
  'processos:editar': [ROLE.COORDENADOR_DO_CURSO, ROLE.ADMIN],
  'processos:visualizar': [
    ROLE.COORDENADOR_DO_CURSO,
    ROLE.ADMIN,
    ROLE.ESTAGIARIO,
    ROLE.ADVOGADO,
  ],

  // Demandas
  'demandas:criar': [ROLE.COORDENADOR_DO_CURSO, ROLE.PROFESSOR, ROLE.ADMIN],
  'demandas:editar': [ROLE.COORDENADOR_DO_CURSO, ROLE.ADMIN],
  // 'demandas:atribuir': [ROLE.COORDENADOR_DO_CURSO, ROLE.PROFESSOR, ROLE.ADMIN],
  'demandas:visualizar': [
    ROLE.COORDENADOR_DO_CURSO,
    ROLE.ADMIN,
    ROLE.ESTAGIARIO,
    ROLE.ADVOGADO,
    ROLE.PROFESSOR,
  ],

  // Usuários
  'usuarios:alterarStatus': [ROLE.ADMIN],

  // Administrativo (Coordenador e Secretário)
  'administrativo:criar': [ROLE.ADMIN],
  'administrativo:editar': [ROLE.ADMIN],
  'administrativo:visualizar': [
    ROLE.ADMIN,
    ROLE.COORDENADOR_DO_CURSO,
    ROLE.SECRETARIO,
  ],

  // Professores
  'professores:criar': [ROLE.ADMIN],
  'professores:editar': [ROLE.ADMIN],
  'professores:visualizar': [ROLE.ADMIN],

  // Assistidos
  'assistidos:criar': [ROLE.ADMIN],
  'assistidos:editar': [ROLE.ADMIN],
  'assistidos:visualizar': [ROLE.ADMIN],

  // Estagiarios
  'estagiarios:criar': [ROLE.ADMIN],
  'estagiarios:editar': [ROLE.ADMIN],
  'estagiarios:visualizar': [ROLE.ADMIN],

  // Advogados
  'advogados:criar': [ROLE.ADMIN],
  'advogados:editar': [ROLE.ADMIN],
  'advogados:visualizar': [ROLE.ADMIN],

  // Configurações
  'configuracoes:visualizar': [ROLE.ADMIN],
} as const satisfies Record<string, readonly Role[]>;

export type PermissionAction = keyof typeof permissions;

export function can(role: Role, action: PermissionAction): boolean {
  return (permissions[action] as readonly Role[]).includes(role);
}
