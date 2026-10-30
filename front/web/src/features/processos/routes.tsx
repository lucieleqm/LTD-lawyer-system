import { RouteObject } from 'react-router-dom';
import { paths } from '@/routes/paths';

import { RoleGuard } from '@/routes/RoleGuard';
import CriarProcessoPage from './pages/CriarProcessoPage';
import ListaProcessosPage from './pages/ListaProcessosPage';
import DetalheProcessoPage from './pages/DetalheProcessoPage';
import EditarProcessoPage from './pages/EditarProcessoPage';

export const processosRoutes: RouteObject[] = [
  {
    path: paths.processos.lista,
    element: (
      <RoleGuard action="processos:visualizar">
        <ListaProcessosPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.processos.detalhe(':id'),
    element: (
      <RoleGuard action="processos:visualizar">
        <DetalheProcessoPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.processos.novo,
    element: (
      <RoleGuard action="processos:criar">
        <CriarProcessoPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.processos.editar(':id'),
    element: (
      <RoleGuard action="processos:editar">
        <EditarProcessoPage />
      </RoleGuard>
    ),
  },
];
