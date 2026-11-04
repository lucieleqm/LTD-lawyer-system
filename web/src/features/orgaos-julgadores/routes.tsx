import { RouteObject } from 'react-router-dom';
import { paths } from '@/routes/paths';
import { RoleGuard } from '@/routes/RoleGuard';
import { DetalheOrgaoJulgadorPage } from './pages/DetalheOrgaoJulgadorPage';
import ListaOrgaosJulgadoresPage from './pages/ListaOrgaoJulgador';
import CriarOrgaoJulgadorPage from './pages/CriarOrgaoJulgadorPage';
import EditarOrgaoJulgadorPage from './pages/EditarOrgaoJulgadorPage';

export const orgaosJulgadoresRoutes: RouteObject[] = [
  {
    path: paths.orgaosJulgadores.lista,
    element: (
      <RoleGuard action="orgaosJulgadores:visualizar">
        <ListaOrgaosJulgadoresPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.orgaosJulgadores.detalhe(':id'),
    element: (
      <RoleGuard action="orgaosJulgadores:visualizar">
        <DetalheOrgaoJulgadorPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.orgaosJulgadores.novo,
    element: (
      <RoleGuard action="orgaosJulgadores:criar">
        <CriarOrgaoJulgadorPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.orgaosJulgadores.editar(':id'),
    element: (
      <RoleGuard action="orgaosJulgadores:editar">
        <EditarOrgaoJulgadorPage />
      </RoleGuard>
    ),
  },
];
