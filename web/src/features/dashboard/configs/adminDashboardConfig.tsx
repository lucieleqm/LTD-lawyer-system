import {
  IconUsers,
  IconFileText,
  IconClipboardList,
  IconSettings,
  IconGavel,
  IconSchool,
  IconBriefcase,
} from '@tabler/icons-react';
import { paths } from '@/routes/paths';
import { DashboardSection } from '../types';

export const adminDashboardConfig: DashboardSection[] = [
  {
    title: 'Gestão de Pessoas',
    color: 'blue',
    cards: [
      {
        title: 'Equipe Administrativa',
        description: 'Coordenador e secretários',
        icon: <IconBriefcase size={20} />,
        to: paths.administrativo.lista,
      },
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
      {
        title: 'Advogados',
        description: 'Advogados vinculados ao núcleo',
        icon: <IconGavel size={20} />,
        to: paths.advogados.lista,
      },
      {
        title: 'Assistidos',
        description: 'Pessoas atendidas pelo núcleo',
        icon: <IconUsers size={20} />,
        to: paths.assistidos.lista,
      },
    ],
  },
  {
    title: 'Trabalho Jurídico',
    color: 'blue',
    cards: [
      {
        title: 'Processos',
        description: 'Todos os processos do sistema',
        icon: <IconFileText size={20} />,
        to: paths.processos.lista,
      },
      {
        title: 'Demandas',
        description: 'Todas as demandas do curso',
        icon: <IconClipboardList size={20} />,
        to: paths.demandas.lista,
      },
    ],
  },
  {
    title: 'Sistema',
    color: 'blue',
    cards: [
      {
        title: 'Configurações',
        description: 'Parâmetros gerais do sistema',
        icon: <IconSettings size={20} />,
        to: paths.configuracoes,
      },
    ],
  },
];
