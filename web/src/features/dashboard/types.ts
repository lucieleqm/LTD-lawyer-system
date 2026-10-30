interface DashboardCardConfig {
  title: string;
  description: string;
  icon: React.ReactNode;
  to: string;
}

export interface DashboardSection {
  title: string;
  color?: string;
  cards: DashboardCardConfig[];
}
