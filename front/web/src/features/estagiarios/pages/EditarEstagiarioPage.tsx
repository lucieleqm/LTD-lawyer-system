import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { notifications } from '@mantine/notifications';
import { Center, Loader } from '@mantine/core';
import { EstagiarioForm } from '../components/EstagiarioForm';
import { estagiariosService } from '../services/estagiariosService';
import { Estagiario } from '../types';
import { periodoEstagioLabel } from '../constants';
import { paths } from '@/routes/paths';
import { AtualizarEstagiarioRequest } from '../schema';
import { PageHeader } from '@/components/ui/PageHeader';
import { EmptyState } from '@/components/ui/EmptyState';
import { LoadingState } from '@/components/ui/LoadingState';

function paraValoresDoForm(estagiario: Estagiario) {
  return {
    nome: estagiario.pessoa.nome,
    telefone: estagiario.pessoa.telefone,
    email: estagiario.pessoa.email,
    matricula: estagiario.matricula,
    periodoEstagio: estagiario.periodoEstagio,
  };
}

export default function EditarEstagiarioPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [estagiario, setEstagiario] = useState<Estagiario | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  const periodosOptions = Object.entries(periodoEstagioLabel).map(
    ([value, label]) => ({ value, label }),
  );

  useEffect(() => {
    if (!id) return;

    estagiariosService
      .buscarPorId(Number(id))
      .then(setEstagiario)
      .finally(() => setIsLoading(false));
  }, [id]);

  async function handleSalvar(dados: AtualizarEstagiarioRequest) {
    await estagiariosService.atualizar(Number(id), dados);
    notifications.show({
      message: 'Estagiário atualizado com sucesso',
      color: 'teal',
    });
    navigate(paths.estagiarios.lista);
  }

  if (isLoading) {
    return <LoadingState />;
  }

  if (!estagiario) {
    return <EmptyState message="Estagiário não encontrado." />;
  }

  return (
    <div>
      <PageHeader title="Editar Estagiário" />
      <EstagiarioForm
        modo="editar"
        valoresIniciais={paraValoresDoForm(estagiario)}
        onSubmit={handleSalvar}
        periodos={periodosOptions}
      />
    </div>
  );
}
