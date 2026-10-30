import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { notifications } from '@mantine/notifications';

import { paths } from '@/routes/paths';
import { Advogado } from '../types';
import { advogadosService } from '../services/advogadosService';
import { AtualizarAdvogadoRequest } from '../schema';
import { AdvogadoForm } from '../components/AdvogadoForm';
import { PageHeader } from '@/components/ui/PageHeader';
import { EmptyState } from '@/components/ui/EmptyState';
import { LoadingState } from '@/components/ui/LoadingState';

function paraValoresDoForm(advogado: Advogado) {
  return {
    nome: advogado.pessoa.nome,
    telefone: advogado.pessoa.telefone,
    email: advogado.pessoa.email,
    dataDeNascimento: advogado.dataDeNascimento,
    endereco: advogado.endereco
      ? {
          cep: advogado.endereco.cep,
          logradouro: advogado.endereco.logradouro,
          numero: advogado.endereco.numero,
          complemento: advogado.endereco.complemento || '',
          bairro: advogado.endereco.bairro,
          cidade: advogado.endereco.cidade,
        }
      : undefined,
  };
}

export default function EditarAdvogadoPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [advogado, setAdvogado] = useState<Advogado | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    if (!id) return;

    advogadosService
      .buscarPorId(Number(id))
      .then(setAdvogado)
      .finally(() => setIsLoading(false));
  }, [id]);

  async function handleSalvar(dados: AtualizarAdvogadoRequest) {
    await advogadosService.atualizar(Number(id), dados);
    notifications.show({
      message: 'Advogado atualizado com sucesso',
      color: 'teal',
    });
    navigate(paths.advogados.lista);
  }

  if (isLoading) {
    return <LoadingState />;
  }

  if (!advogado) {
    return <EmptyState message="Advogado não encontrado." />;
  }

  return (
    <div>
      <PageHeader title="Editar Advogado" />
      <AdvogadoForm
        modo="editar"
        valoresIniciais={paraValoresDoForm(advogado)}
        onSubmit={handleSalvar}
      />
    </div>
  );
}
