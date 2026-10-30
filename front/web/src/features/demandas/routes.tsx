import { RouteObject } from 'react-router-dom';
import { paths } from '@/routes/paths';

import { RoleGuard } from '@/routes/RoleGuard';
import CriarDemandaPage from './pages/CriarDemandaPage';
import ListaDemandasPage from './pages/ListaDemandasPage';
import DetalheDeamandaPage from './pages/DetalheDemandaPage';
import EditarDemandaPage from './pages/EditarDemandaPage';

export const demandasRoutes: RouteObject[] = [
  {
    path: paths.demandas.lista,
    element: (
      <RoleGuard action="demandas:visualizar">
        <ListaDemandasPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.demandas.detalhe(':id'),
    element: (
      <RoleGuard action="demandas:visualizar">
        <DetalheDeamandaPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.demandas.novo,
    element: (
      <RoleGuard action="demandas:criar">
        <CriarDemandaPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.demandas.editar(':id'),
    element: (
      <RoleGuard action="demandas:editar">
        <EditarDemandaPage />
      </RoleGuard>
    ),
  },
];
