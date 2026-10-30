import { createBrowserRouter } from 'react-router-dom';
import { PublicRoute } from '@/routes/PublicRoute';
import { PrivateRoute } from '@/routes/PrivateRoute';
import { paths } from '@/routes/paths';

import { MainLayout } from '@/components/layouts/MainLayout';
import { DashboardPage } from '@/features/dashboard/pages/DashboardPage';

import LoginPage from '@/features/auth/pages/LoginPage';
import EsqueciSenhaPage from '@/features/auth/pages/EsqueciSenhaPage';
import RedefinirSenhaPage from '@/features/auth/pages/RedefinirSenhaPage';
import AcessoNegadoPage from '@/features/auth/pages/AcessoNegadoPage';

import NotFoundPage from '@/pages/NotFound/NotFoundPage';

import { estagiariosRoutes } from '@/features/estagiarios/routes';
import { advogadosRoutes } from '@/features/advogados/routes';
import { assistidosRoutes } from '@/features/assistidos/routes';
import { administrativoRoutes } from '@/features/equipe-administrativa/routes';
import { demandasRoutes } from '@/features/demandas/routes';
import { processosRoutes } from '@/features/processos/routes';
import { professoresRoutes } from '@/features/professores/routes';

export const router = createBrowserRouter([
  {
    element: <PublicRoute />,
    children: [
      { path: paths.login, element: <LoginPage /> },
      { path: paths.esqueciSenha, element: <EsqueciSenhaPage /> },
      { path: paths.redefinirSenha, element: <RedefinirSenhaPage /> },
    ],
  },

  { path: paths.acessoNegado, element: <AcessoNegadoPage /> },

  {
    element: <PrivateRoute />,
    children: [
      {
        element: <MainLayout />,
        children: [
          { path: paths.home, element: <DashboardPage /> },

          ...estagiariosRoutes,
          ...professoresRoutes,
          ...advogadosRoutes,
          ...assistidosRoutes,
          ...administrativoRoutes,
          ...demandasRoutes,
          ...processosRoutes,
        ],
      },
    ],
  },

  { path: '*', element: <NotFoundPage /> },
]);
