import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Professor } from '../types';
import { professoresService } from '../services/professoresService';
import { LoadingState } from '@/components/ui/LoadingState';
import { EmptyState } from '@/components/ui/EmptyState';
import {
  DetailField,
  DetailHeader,
  DetailSection,
  DetailShell,
} from '@/components/ui/detail';
import { paths } from '@/routes/paths';
import { EditButton } from '@/components/ui/EditButton';
import { formatarData } from '@/utils/formatters';
import { useAuth } from '@/features/auth/hooks/useAuth';
import { useChangeUsuarioStatus } from '@/hooks/useChangeUsuarioStatus';
import { usePermission } from '@/features/auth/hooks/usePermission';
import { UsuarioStatusSelect } from '@/components/ui/UsuarioStatusSelect';

export function DetalheProfessorPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const { user } = useAuth();
  const { alterar } = useChangeUsuarioStatus({ onSuccess: carregar });

  const podeEditar = usePermission('professores:editar');
  const podeAlterarStatus = usePermission('usuarios:alterarStatus');

  const [isLoading, setIsLoading] = useState(true);
  const [professor, setProfessor] = useState<Professor | null>(null);

  function carregar() {
    if (!id) return;
    setIsLoading(true);
    professoresService
      .buscarPorId(Number(id))
      .then(setProfessor)
      .finally(() => setIsLoading(false));
  }

  useEffect(carregar, [id]);

  if (isLoading) return <LoadingState />;
  if (!professor) return <EmptyState />;

  const ehVoceMesmo = professor.usuario.id === user!.id;

  return (
    <DetailShell>
      <DetailHeader
        title={professor.pessoa.nome}
        breadcrumbs={[
          { label: 'Início', link: paths.home },
          { label: 'Professores', link: paths.professores.lista },
          { label: professor.pessoa.nome },
        ]}
        actions={[
          <>
            {podeEditar && (
              <EditButton
                onClick={() => navigate(paths.professores.editar(professor.id))}
              />
            )}
            {!ehVoceMesmo && (
              <UsuarioStatusSelect
                value={professor.usuario.status}
                onChange={(novoStatus) =>
                  alterar(professor.usuario.id, novoStatus)
                }
                disabled={!podeAlterarStatus || ehVoceMesmo}
              />
            )}
          </>,
        ]}
      />
      <DetailSection title="Informações de Contato">
        <DetailField
          label="E-mail para Contato"
          value={professor.pessoa.email}
        />
        <DetailField label="Telefone" value={professor.pessoa.telefone} />
      </DetailSection>

      <DetailSection title="Acesso ao Sistema">
        <DetailField label="Login" value={professor.usuario.login} />
        <DetailField
          label="Criado em"
          value={formatarData(professor.usuario.criadoEm, 'curto')}
        />
        {professor.usuario.desativadoEm && (
          <DetailField
            label="Desativado em"
            value={formatarData(professor.usuario.desativadoEm, 'curto')}
          />
        )}
        {professor.usuario.bloqueadoEm && (
          <DetailField
            label="Bloqueado em"
            value={formatarData(professor.usuario.bloqueadoEm, 'curto')}
          />
        )}
      </DetailSection>
    </DetailShell>
  );
}
