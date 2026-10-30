import { RouteObject } from 'react-router-dom';
import { paths } from '@/routes/paths';

import { RoleGuard } from '@/routes/RoleGuard';
import CriarAssistidoPage from './pages/CriarAssistidoPage';
import EditarAssistidoPage from './pages/EditarAssistidoPage';
import ListaAssistidosPage from './pages/ListaAssistidosPage';
import DetalheAssistidoPage from './pages/DetalheAssistidoPage';

export const assistidosRoutes: RouteObject[] = [
  {
    path: paths.assistidos.lista,
    element: (
      <RoleGuard action="assistidos:visualizar">
        <ListaAssistidosPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.assistidos.detalhe(':id'),
    element: (
      <RoleGuard action="assistidos:visualizar">
        <DetalheAssistidoPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.assistidos.novo,
    element: (
      <RoleGuard action="assistidos:criar">
        <CriarAssistidoPage />
      </RoleGuard>
    ),
  },
  {
    path: paths.assistidos.editar(':id'),
    element: (
      <RoleGuard action="assistidos:editar">
        <EditarAssistidoPage />
      </RoleGuard>
    ),
  },
];
