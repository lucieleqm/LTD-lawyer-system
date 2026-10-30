import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { notifications } from '@mantine/notifications';
import { Container } from '@mantine/core';

import { paths } from '@/routes/paths';
import { PageHeader } from '@/components/ui/PageHeader';
import { Professor } from '../types';
import { professoresService } from '../services/professoresService';
import { AtualizarProfessorRequest } from '../schema';
import { ProfessorForm } from '../components/ProfessorForm';
import { EmptyState } from '@/components/ui/EmptyState';
import { LoadingState } from '@/components/ui/LoadingState';

function paraValoresDoForm(professor: Professor) {
  return {
    nome: professor.pessoa.nome,
    telefone: professor.pessoa.telefone,
  };
}

export default function EditarProfessorPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
 
  const [professor, setProfessor] = useState<Professor | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    if (!id) return;

    professoresService
      .buscarPorId(Number(id))
      .then(setProfessor)
      .finally(() => setIsLoading(false));
  }, [id]);

  async function handleSalvar(dados: AtualizarProfessorRequest) {
    await professoresService.atualizar(Number(id), dados);
    notifications.show({
      message: 'Professor atualizado com sucesso',
      color: 'teal',
    });
    navigate(paths.professores.lista);
  }

  if (isLoading) {
    return <LoadingState />;
  }

  if (!professor) {
    return <EmptyState message="Professor não encontrado." />;
  }

  return (
    <Container>
      <PageHeader title="Editar Professor" />
      <ProfessorForm
        modo="editar"
        valoresIniciais={paraValoresDoForm(professor)}
        onSubmit={handleSalvar}
      />
    </Container>
  );
}
