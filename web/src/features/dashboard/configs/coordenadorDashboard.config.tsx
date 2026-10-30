import {
  IconClipboardList,
  IconFileText,
  IconSchool,
  IconUsers,
} from '@tabler/icons-react';
import { paths } from '@/routes/paths';
import { DashboardSection } from '../types';

export const coordenadorDashboardConfig: DashboardSection[] = [
  {
    title: 'Gestão de Pessoas',
    cards: [
      {
        title: 'Professores',
        description: 'Corpo docente do curso',
        icon: <IconUsers size={20} />,
        to: paths.professores.lista,
      },
      {
        title: 'Estagiários',
        description: 'Cadastro e acompanhamento acadêmico',
        icon: <IconSchool size={20} />,
        to: paths.estagiarios.lista,
      },
    ],
  },
  {
    title: 'Trabalho Jurídico',
    cards: [
      {
        title: 'Demandas',
        description: 'Distribuir demandas entre estagiários',
        icon: <IconClipboardList size={20} />,
        to: paths.demandas.lista,
      },
      {
        title: 'Processos',
        description: 'Acompanhar processos do curso',
        icon: <IconFileText size={20} />,
        to: paths.processos.lista,
      },
    ],
  },
];
