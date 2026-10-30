import { IconClipboardList, IconFileText } from '@tabler/icons-react';
import { paths } from '@/routes/paths';
import { DashboardSection } from '../types';

export const advogadoDashboardConfig: DashboardSection[] = [
  {
    title: 'Meu Trabalho',
    cards: [
      {
        title: 'Minhas Demandas',
        description: 'Demandas atribuídas a você',
        icon: <IconClipboardList size={20} />,
        to: paths.demandas.lista,
      },
      {
        title: 'Meus Processos',
        description: 'Seus processos ativos',
        icon: <IconFileText size={20} />,
        to: paths.processos.lista,
      },
    ],
  },
];
