import { RouteObject } from 'react-router-dom';
import { paths } from '@/routes/paths';

import { RoleGuard } from '@/routes/RoleGuard';

import AdministrativoCreatePage from './pages/CriarAdministrativoPage';
import ListaAdministrativoPage from './pages/ListaAdministrativoPage';
import { DetalheAdministrativoPage } from './pages/DetalheAdministrativoPage';
import CriarAdministrativoPage from './pages/CriarAdministrativoPage';

export const administrativoRoutes: RouteObject[] = [
  {
    path: paths.administrativo.lista,
    element: (
      <RoleGuard action="administrativo:visualizar">
        <ListaAdministrativoPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.administrativo.detalhe(':id'),
    element: (
      <RoleGuard action="administrativo:visualizar">
        <DetalheAdministrativoPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.administrativo.novo,
    element: (
      <RoleGuard action="administrativo:criar">
        <CriarAdministrativoPage />
      </RoleGuard>
    ),
  },
];
