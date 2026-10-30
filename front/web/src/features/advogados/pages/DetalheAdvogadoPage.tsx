import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import { LoadingState } from '@/components/ui/LoadingState';
import { EmptyState } from '@/components/ui/EmptyState';

import {
  DetailField,
  DetailHeader,
  DetailSection,
  DetailShell,
} from '@/components/ui/detail';

import { advogadosService } from '../services/advogadosService';
import { Advogado } from '../types';
import { formatarData } from '@/utils/formatters';
import { paths } from '@/routes/paths';
import { EditButton } from '@/components/ui/EditButton';
import { UsuarioStatusSelect } from '@/components/ui/UsuarioStatusSelect';
import { useAuth } from '@/features/auth/hooks/useAuth';
import { useChangeUsuarioStatus } from '@/hooks/useChangeUsuarioStatus';
import { usePermission } from '@/features/auth/hooks/usePermission';

export function DetalheAdvogadoPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const { user } = useAuth();
  const { alterar } = useChangeUsuarioStatus({ onSuccess: carregar });

  const podeEditar = usePermission('advogados:editar');
  const podeAlterarStatus = usePermission('usuarios:alterarStatus');

  const [isLoading, setIsLoading] = useState(true);
  const [advogado, setAdvogado] = useState<Advogado | null>(null);

  function carregar() {
    if (!id) return;
    setIsLoading(true);
    advogadosService
      .buscarPorId(Number(id))
      .then(setAdvogado)
      .finally(() => setIsLoading(false));
  }

  useEffect(carregar, [id]);

  const ehVoceMesmo = advogado?.usuario.id === user!.id;

  if (isLoading) return <LoadingState />;
  if (!advogado) return <EmptyState />;

  return (
    <DetailShell>
      <DetailHeader
        title={advogado.pessoa.nome}
        breadcrumbs={[
          { label: 'Início', link: paths.home },
          { label: 'Advogados', link: paths.advogados.lista },
          { label: advogado.pessoa.nome },
        ]}
        actions={[
          <>
            {podeEditar && (
              <EditButton
                onClick={() => navigate(paths.advogados.editar(advogado.id))}
              />
            )}
            {!ehVoceMesmo && (
              <UsuarioStatusSelect
                value={advogado.usuario.status}
                onChange={(novoStatus) =>
                  alterar(advogado.usuario.id, novoStatus)
                }
                disabled={!podeAlterarStatus || ehVoceMesmo}
              />
            )}
          </>,
        ]}
      />
      <DetailSection title="Informações Pessoais">
        <DetailField
          label="E-mail para Contato"
          value={advogado.pessoa.email}
        />
        <DetailField label="Telefone" value={advogado.pessoa.telefone} />
        <DetailField
          label="Data de Nascimento"
          value={formatarData(advogado.dataDeNascimento, 'curto')}
        />
      </DetailSection>

      {advogado.endereco && (
        <DetailSection title="Endereço">
          <DetailField
            label="Logradouro"
            value={advogado.endereco.logradouro}
          />
          <DetailField label="Número" value={advogado.endereco.numero} />
          <DetailField
            label="Complemento"
            value={advogado.endereco.complemento}
          />
          <DetailField label="Bairro" value={advogado.endereco.bairro} />
          <DetailField label="Cidade" value={advogado.endereco.cidade} />
          <DetailField label="CEP" value={advogado.endereco.cep} />
        </DetailSection>
      )}

      <DetailSection title="Acesso ao Sistema">
        <DetailField label="Login" value={advogado.usuario.login} />
        <DetailField
          label="Criado em"
          value={formatarData(advogado.usuario.criadoEm, 'curto')}
        />
        {advogado.usuario.desativadoEm && (
          <DetailField
            label="Desativado em"
            value={formatarData(advogado.usuario.desativadoEm, 'curto')}
          />
        )}
        {advogado.usuario.bloqueadoEm && (
          <DetailField
            label="Bloqueado em"
            value={formatarData(advogado.usuario.bloqueadoEm, 'curto')}
          />
        )}
      </DetailSection>
    </DetailShell>
  );
}
