// components/layout/AuthLayout.tsx
import { Text, Title } from '@mantine/core';
import { IconScale } from '@tabler/icons-react';
import classes from './AuthLayout.module.css';

interface AuthLayoutProps {
  children: React.ReactNode;
}

export function AuthLayout({ children }: AuthLayoutProps) {
  return (
    <div className={classes.wrapper}>
      <div className={classes.brandPanel}>
        <IconScale size={40} />
        <Title order={2} mt="md" mb="xs">
          Núcleo de Prática Jurídica
        </Title>
        <Text size="sm" opacity={0.85} maw={360}>
          Plataforma de gestão de estágios, processos e demandas do curso de
          Direito.
        </Text>
      </div>

      <div className={classes.formPanel}>
        <div className={classes.formContent}>{children}</div>
      </div>
    </div>
  );
}
{
  /* <Image
            src={balancaLogo}
            alt="Balança da Justiça"
            w={80}
            mx="auto"
            mb="md"
            radius="md"
          /> */
}
