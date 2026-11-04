import { RouteObject } from 'react-router-dom';
import { paths } from '@/routes/paths';
import { RoleGuard } from '@/routes/RoleGuard';
import CriarAreaDireitoPage from './pages/CriarAreaDireitoPage';
import EditarAreaDireitoPage from './pages/EditarAreaDireitoPage';
import ListaTribunaisPage from './pages/ListaAreaDireito';
import { DetalheAreaDireitoPage } from './pages/DetalheAreaDireitoPage';

export const areasDireitoRoutes: RouteObject[] = [
  {
    path: paths.areasDireito.lista,
    element: (
      <RoleGuard action="areasDireito:visualizar">
        <ListaTribunaisPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.areasDireito.detalhe(':id'),
    element: (
      <RoleGuard action="areasDireito:visualizar">
        <DetalheAreaDireitoPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.areasDireito.novo,
    element: (
      <RoleGuard action="areasDireito:criar">
        <CriarAreaDireitoPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.areasDireito.editar(':id'),
    element: (
      <RoleGuard action="areasDireito:editar">
        <EditarAreaDireitoPage />
      </RoleGuard>
    ),
  },
];
