import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import { usePermission } from '@/features/auth/hooks/usePermission';
import { estagiariosService } from '../services/estagiariosService';
import { Estagiario } from '../types';

import { DetailShell } from '@/components/ui/detail/DetailShell';
import { DetailHeader } from '@/components/ui/detail/DetailHeader';
import { DetailSection } from '@/components/ui/detail/DetailSection';
import { DetailField } from '@/components/ui/detail/DetailField';
import { periodoEstagioLabel } from '../constants';
import { LoadingState } from '@/components/ui/LoadingState';
import { EmptyState } from '@/components/ui/EmptyState';
import { formatarData } from '@/utils/formatters';
import { useChangeUsuarioStatus } from '@/hooks/useChangeUsuarioStatus';
import { UsuarioStatusSelect } from '@/components/ui/UsuarioStatusSelect';
import { useAuth } from '@/features/auth/hooks/useAuth';

import { IconSchool } from '@tabler/icons-react';
import { EditButton } from '@/components/ui/EditButton';
import { paths } from '@/routes/paths';

export default function DetalheEstagiarioPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const { user } = useAuth();

  const [estagiario, setEstagiario] = useState<Estagiario | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  const podeEditar = usePermission('estagiarios:editar');
  const podeAlterarStatus = usePermission('usuarios:alterarStatus');
  const { alterar } = useChangeUsuarioStatus({ onSuccess: carregar });

  function carregar() {
    if (!id) return;
    setIsLoading(true);
    estagiariosService
      .buscarPorId(Number(id))
      .then(setEstagiario)
      .finally(() => setIsLoading(false));
  }

  useEffect(carregar, [id]);

  if (isLoading) return <LoadingState />;
  if (!estagiario) return <EmptyState />;

  const ehVoceMesmo = estagiario.usuario.id === user!.id;

  return (
    <DetailShell>
      <DetailHeader
        title={estagiario.pessoa.nome}
        breadcrumbs={[
          { label: 'Início', link: paths.home },
          { label: 'Estagiários', link: paths.estagiarios.lista },
          { label: `${estagiario.pessoa.nome}` },
        ]}
        actions={
          <>
            {podeEditar && (
              <EditButton
                onClick={() =>
                  navigate(paths.estagiarios.editar(estagiario.id))
                }
              />
            )}
            {!ehVoceMesmo && (
              <UsuarioStatusSelect
                value={estagiario.usuario.status}
                onChange={(novoStatus) =>
                  alterar(estagiario.usuario.id, novoStatus)
                }
                disabled={!podeAlterarStatus || ehVoceMesmo}
              />
            )}
          </>
        }
      />

      <DetailSection title="Dados Acadêmicos">
        <DetailField label="Matrícula" value={estagiario.matricula} />
        <DetailField
          label="Período"
          value={periodoEstagioLabel[estagiario.periodoEstagio]}
        />
      </DetailSection>

      <DetailSection title="Dados de Contato">
        <DetailField label="Telefone" value={estagiario.pessoa.telefone} />
        <DetailField
          label="Email para contato"
          value={estagiario.pessoa.email}
        />
      </DetailSection>

      <DetailSection title="Acesso ao Sistema">
        <DetailField label="Login" value={estagiario.usuario.login} />
        <DetailField
          label="Criado em"
          value={formatarData(estagiario.usuario.criadoEm, 'curto')}
        />
        {estagiario.usuario.desativadoEm && (
          <DetailField
            label="Desativado em"
            value={formatarData(estagiario.usuario.desativadoEm, 'curto')}
          />
        )}
        {estagiario.usuario.bloqueadoEm && (
          <DetailField
            label="Bloqueado em"
            value={formatarData(estagiario.usuario.bloqueadoEm, 'curto')}
          />
        )}
      </DetailSection>
    </DetailShell>
  );
}
