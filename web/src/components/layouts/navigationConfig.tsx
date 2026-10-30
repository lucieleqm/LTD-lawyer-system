import {
  IconLayoutDashboard,
  IconUsers,
  IconFileText,
  IconClipboardList,
  IconSettings,
  IconSchool,
  IconGavel,
  IconBriefcase,
} from '@tabler/icons-react';
import { Role } from '@/constants/roles';
import { paths } from '@/routes/paths';
import { can, PermissionAction } from '@/permissions/permissions';

interface NavItem {
  label: string;
  icon?: React.ReactNode;
  to: string;
  requiredAction?: PermissionAction; // se omitido, qualquer um pode acessar
}

interface NavGroup {
  label: string;
  icon: React.ReactNode;
  items: NavItem[];
}

const groupSize = 20;

// itens q ficam soltos, fora de um grupo
const topLevelItems: NavItem[] = [
  {
    label: 'Dashboard',
    icon: <IconLayoutDashboard size={groupSize} />,
    to: paths.home,
  },
];

const navGroups: NavGroup[] = [
  {
    label: 'Gestão de Pessoas',
    icon: <IconUsers size={groupSize} />,
    items: [
      {
        label: 'Equipe Administrativa',
        to: paths.administrativo.lista,
        requiredAction: 'administrativo:visualizar',
      },
      {
        label: 'Professores',
        to: paths.professores.lista,
        requiredAction: 'professores:visualizar',
      },
      {
        label: 'Estagiários',
        to: paths.estagiarios.lista,
        requiredAction: 'estagiarios:visualizar',
      },
      {
        label: 'Advogados',
        to: paths.advogados.lista,
        requiredAction: 'advogados:visualizar',
      },
      {
        label: 'Assistidos',
        to: paths.assistidos.lista,
        requiredAction: 'assistidos:visualizar',
      },
    ],
  },
  {
    label: 'Trabalho Jurídico',
    icon: <IconFileText size={groupSize} />,
    items: [
      {
        label: 'Processos',
        to: paths.processos.lista,
        requiredAction: 'processos:visualizar',
      },
      {
        label: 'Demandas',
        to: paths.demandas.lista,
        requiredAction: 'demandas:visualizar',
      },
    ],
  },
  {
    label: 'Sistema',
    icon: <IconSettings size={groupSize} />,
    items: [
      {
        label: 'Configurações',
        to: paths.configuracoes,
        requiredAction: 'configuracoes:visualizar',
      },
    ],
  },
];

function filtrarItens(items: NavItem[], role: Role): NavItem[] {
  return items.filter(
    (item) => !item.requiredAction || can(role, item.requiredAction),
  );
}

export function getNavigationForRole(role: Role) {
  const grupos = navGroups
    .map((grupo) => ({ ...grupo, items: filtrarItens(grupo.items, role) }))
    .filter((grupo) => grupo.items.length > 0); // esconde o grupo inteiro se não sobrar nenhum item

  return {
    topLevelItems: filtrarItens(topLevelItems, role),
    navGroups: grupos,
  };
}
