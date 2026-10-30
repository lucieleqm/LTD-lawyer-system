import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { notifications } from '@mantine/notifications';
import { AssistidoForm } from '../components/AssistidoForm';
import { assistidosService } from '../services/assistidosService';
import { Assistido } from '../types';
import { paths } from '@/routes/paths';
import { AtualizarAssistidoRequest } from '../schema';
import { PageHeader } from '@/components/ui/PageHeader';
import { estadoCivilLabel } from '@/constants/estadoCivil';
import { EmptyState } from '@/components/ui/EmptyState';
import { LoadingState } from '@/components/ui/LoadingState';

function paraValoresDoForm(assistido: Assistido) {
  return {
    matricula: assistido.matricula,
    profissao: assistido.profissao,
    nacionalidade: assistido.nacionalidade,
    naturalidade: assistido.naturalidade,
    estadoCivil: assistido.estadoCivil,
    nome: assistido.pessoa.nome,
    telefone: assistido.pessoa.telefone,
    email: assistido.pessoa.email,
    endereco: assistido.endereco
      ? {
          logradouro: assistido.endereco.logradouro,
          numero: assistido.endereco.numero,
          complemento: assistido.endereco.complemento,
          bairro: assistido.endereco.bairro,
          cidade: assistido.endereco.cidade,
          cep: assistido.endereco.cep,
        }
      : undefined,
  };
}

export default function EditarAssistidoPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [assistido, setAssistido] = useState<Assistido | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  const estadosCivisOptions = Object.entries(estadoCivilLabel).map(
    ([value, label]) => ({ value, label }),
  );

  useEffect(() => {
    if (!id) return;

    assistidosService
      .buscarPorId(Number(id))
      .then(setAssistido)
      .finally(() => setIsLoading(false));
  }, [id]);

  async function handleSalvar(dados: AtualizarAssistidoRequest) {
    await assistidosService.atualizar(Number(id), dados);
    notifications.show({
      message: 'Assistido atualizado com sucesso',
      color: 'teal',
    });
    navigate(paths.assistidos.lista);
  }

  if (isLoading) {
    return <LoadingState />;
  }

  if (!assistido) {
    return <EmptyState message="Assistido não encontrado." />;
  }

  return (
    <div>
      <PageHeader title="Editar Assistido" />
      <AssistidoForm
        modo="editar"
        valoresIniciais={paraValoresDoForm(assistido)}
        onSubmit={handleSalvar}
        estadosCivis={estadosCivisOptions}
      />
    </div>
  );
}
