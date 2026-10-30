import { RouteObject } from 'react-router-dom';
import { paths } from '@/routes/paths';
import { RoleGuard } from '@/routes/RoleGuard';
import CriarProfessorPage from './pages/CriarProfessorPage';
import EditarProfessorPage from './pages/EditarProfessorPage';
import ListaProfessoresPage from './pages/ListaProfessor';
import { DetalheProfessorPage } from './pages/DetalheProfessorPage';

export const professoresRoutes: RouteObject[] = [
  {
    path: paths.professores.lista,
    element: (
      <RoleGuard action="professores:visualizar">
        <ListaProfessoresPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.professores.detalhe(':id'),
    element: (
      <RoleGuard action="professores:visualizar">
        <DetalheProfessorPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.professores.novo,
    element: (
      <RoleGuard action="professores:criar">
        <CriarProfessorPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.professores.editar(':id'),
    element: (
      <RoleGuard action="professores:editar">
        <EditarProfessorPage />
      </RoleGuard>
    ),
  },
];
