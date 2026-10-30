import { RouteObject } from 'react-router-dom';
import { paths } from '@/routes/paths';

import CriarEstagiarioPage from './pages/CriarEstagiarioPage';
import EditarEstagiarioPage from './pages/EditarEstagiarioPage';
import ListaEstagiariosPage from './pages/ListaEstagiarioPage';
import { RoleGuard } from '@/routes/RoleGuard';
import DetalheEstagiarioPage from './pages/DetalheEstagiarioPage';

export const estagiariosRoutes: RouteObject[] = [
  {
    path: paths.estagiarios.lista,
    element: (
      <RoleGuard action="estagiarios:visualizar">
        <ListaEstagiariosPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.estagiarios.detalhe(':id'),
    element: (
      <RoleGuard action="estagiarios:visualizar">
        <DetalheEstagiarioPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.estagiarios.novo,
    element: (
      <RoleGuard action="estagiarios:criar">
        <CriarEstagiarioPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.estagiarios.editar(':id'),
    element: (
      <RoleGuard action="estagiarios:editar">
        <EditarEstagiarioPage />
      </RoleGuard>
    ),
  },
];
