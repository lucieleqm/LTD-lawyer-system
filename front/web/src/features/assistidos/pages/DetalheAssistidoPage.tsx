import { useEffect, useState } from 'react';
import { Navigate, useNavigate, useParams } from 'react-router-dom';

import { LoadingState } from '@/components/ui/LoadingState';
import { EmptyState } from '@/components/ui/EmptyState';

import {
  DetailField,
  DetailHeader,
  DetailSection,
  DetailShell,
} from '@/components/ui/detail';

import { assistidosService } from '../services/assistidosService';
import { Assistido } from '../types';
import { paths } from '@/routes/paths';
import { estadoCivilLabel } from '@/constants/estadoCivil';
import { EditButton } from '@/components/ui/EditButton';
import { usePermission } from '@/features/auth/hooks/usePermission';

export default function DetalheAssistidoPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(true);
  const [assistido, setAssistido] = useState<Assistido | null>(null);

  const podeEditar = usePermission('assistidos:editar');

  function carregar() {
    if (!id) return;
    setIsLoading(true);
    assistidosService
      .buscarPorId(Number(id))
      .then(setAssistido)
      .finally(() => setIsLoading(false));
  }

  useEffect(carregar, [id]);

  if (isLoading) return <LoadingState />;
  if (!assistido) return <EmptyState />;

  return (
    <DetailShell>
      <DetailHeader
        title={assistido.pessoa.nome}
        breadcrumbs={[
          { label: 'Início', link: paths.home },
          { label: 'Assistidos', link: paths.assistidos.lista },
          { label: assistido.pessoa.nome },
        ]}
        actions={[
          <>
            {podeEditar && (
              <EditButton
                onClick={() => navigate(paths.assistidos.editar(assistido.id))}
              />
            )}
          </>,
        ]}
      />
      <DetailSection title="Informações Pessoais">
        <DetailField
          label="E-mail para Contato"
          value={assistido.pessoa.email}
        />
        <DetailField label="Telefone" value={assistido.pessoa.telefone} />
        <DetailField label="Matrícula" value={assistido.matricula} />
        <DetailField label="Profissão" value={assistido.profissao} />
        <DetailField label="Nacionalidade" value={assistido.nacionalidade} />
        <DetailField label="Naturalidade" value={assistido.naturalidade} />
        <DetailField
          label="Estado Civil"
          value={estadoCivilLabel[assistido.estadoCivil]}
        />
      </DetailSection>

      {assistido.endereco && (
        <DetailSection title="Endereço">
          <DetailField
            label="Logradouro"
            value={assistido.endereco.logradouro}
          />
          <DetailField label="Número" value={assistido.endereco.numero} />
          <DetailField
            label="Complemento"
            value={assistido.endereco.complemento}
          />
          <DetailField label="Bairro" value={assistido.endereco.bairro} />
          <DetailField label="Cidade" value={assistido.endereco.cidade} />
          <DetailField label="CEP" value={assistido.endereco.cep} />
        </DetailSection>
      )}
    </DetailShell>
  );
}
