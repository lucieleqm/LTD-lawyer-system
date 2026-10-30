import { ROLE, Role } from '@/constants/roles';
import { adminDashboardConfig } from './configs/adminDashboardConfig';
import { coordenadorDashboardConfig } from './configs/coordenadorDashboard.config';
import { professorDashboardConfig } from './configs/professorDashboardConfig';
import { advogadoDashboardConfig } from './configs/advogadoDashBoardConfig';
import { secretarioDashboardConfig } from './configs/secretarioDashBoardConfig';
import { estagiarioDashboardConfig } from './configs/estagiarioDashboardConfig';
import { DashboardSection } from './types';

const dashboardSectionsByRole: Record<Role, DashboardSection[]> = {
  [ROLE.ADMIN]: adminDashboardConfig,
  [ROLE.COORDENADOR_DO_CURSO]: coordenadorDashboardConfig,
  [ROLE.PROFESSOR]: professorDashboardConfig,
  [ROLE.ADVOGADO]: advogadoDashboardConfig,
  [ROLE.SECRETARIO]: secretarioDashboardConfig,
  [ROLE.ESTAGIARIO]: estagiarioDashboardConfig,
};

// [ROLE.COORDENADOR_DO_CURSO]: [
//   {
//     title: 'Gestão de Usuários',
//     description: 'Gerenciar estagiários e professores',
//     icon: <IconUsers size={20} />,
//     to: paths.usuarios,
//   },
//   {
//     title: 'Processos',
//     description: 'Acompanhar processos do curso',
//     icon: <IconFileText size={20} />,
//     to: paths.processos.lista,
//   },
//   {
//     title: 'Demandas',
//     description: 'Distribuir demandas entre estagiários',
//     icon: <IconClipboardList size={20} />,
//     to: paths.demandas.lista,
//   },
// ],

// [ROLE.PROFESSOR]: [
//   {
//     title: 'Demandas',
//     description: 'Demandas dos seus orientandos',
//     icon: <IconClipboardList size={20} />,
//     to: paths.demandas.lista,
//   },
//   {
//     title: 'Processos',
//     description: 'Processos sob sua supervisão',
//     icon: <IconFileText size={20} />,
//     to: paths.processos.lista,
//   },
// ],

// [ROLE.ADVOGADO]: [
//   {
//     title: 'Processos',
//     description: 'Seus processos ativos',
//     icon: <IconGavel size={20} />,
//     to: paths.processos.lista,
//   },
// ],

// [ROLE.SECRETARIO]: [
//   {
//     title: 'Gestão de Usuários',
//     description: 'Cadastro de novos usuários',
//     icon: <IconUsers size={20} />,
//     to: paths.usuarios,
//   },
//   {
//     title: 'Processos',
//     description: 'Organização de processos',
//     icon: <IconFileText size={20} />,
//     to: paths.processos.lista,
//   },
// ],

// [ROLE.ESTAGIARIO]: [
//   {
//     title: 'Minhas Demandas',
//     description: 'Demandas atribuídas a você',
//     icon: <IconSchool size={20} />,
//     to: paths.demandas.lista,
//   },
//   {
//     title: 'Meus Processos',
//     description: 'Processos que você acompanha',
//     icon: <IconFileText size={20} />,
//     to: paths.processos.lista,
//   },
// ],

export function getDashboardSectionsForRole(role: Role): DashboardSection[] {
  return dashboardSectionsByRole[role] ?? [];
}
