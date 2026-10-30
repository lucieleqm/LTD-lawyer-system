import { Link } from 'react-router-dom';
import { Stack, Title, Text, Button, Container, Group } from '@mantine/core';
import { paths } from '@/routes/paths';
import { NotFoundImage } from '@/pages/NotFound/NotFoundImage';
import classes from './NotFound.module.css';

export default function NotFoundPage() {
  return (
    <Container className={classes.root}>
      <div className={classes.inner}>
        <NotFoundImage className={classes.image} />
        <div className={classes.content}>
          <Title className={classes.title}>Página não encontrada</Title>
          <Text
            c="dimmed"
            size="lg"
            ta="center"
            className={classes.description}
          >
            A página que você está tentando abrir não existe. Você pode ter
            digitado o endereço incorretamente ou a página foi movida para outra
            URL. Se você acha que isso é um erro, entre em contato com o
            suporte.
          </Text>
          <Group justify="center">
            <Button component={Link} to={paths.home}>
              Voltar ao início
            </Button>
          </Group>
        </div>
      </div>
    </Container>
    // <Stack align="center" justify="center" mih="100vh" gap="xs">
    //   <Title order={1}>404</Title>
    //   <Text c="dimmed">Página não encontrada.</Text>
    //   <Button component={Link} to={paths.home} mt="md">
    //     Voltar ao início
    //   </Button>
    // </Stack>
  );
}
