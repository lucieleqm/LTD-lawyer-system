import { RouteObject } from 'react-router-dom';
import { paths } from '@/routes/paths';
import { RoleGuard } from '@/routes/RoleGuard';
import CriarTribunalPage from './pages/CriarTribunalPage';
import EditarTribunalPage from './pages/EditarTribunalPage';
import ListaTribunaisPage from './pages/ListaTribunal';
import { DetalheTribunalPage } from './pages/DetalheTribunalPage';

export const tribunaisRoutes: RouteObject[] = [
  {
    path: paths.tribunais.lista,
    element: (
      <RoleGuard action="tribunais:visualizar">
        <ListaTribunaisPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.tribunais.detalhe(':id'),
    element: (
      <RoleGuard action="tribunais:visualizar">
        <DetalheTribunalPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.tribunais.novo,
    element: (
      <RoleGuard action="tribunais:criar">
        <CriarTribunalPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.tribunais.editar(':id'),
    element: (
      <RoleGuard action="tribunais:editar">
        <EditarTribunalPage />
      </RoleGuard>
    ),
  },
];
