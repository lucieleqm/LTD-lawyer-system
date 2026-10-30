import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Administrativo } from '../types';
import { administrativoService } from '../services/administrativoService';
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

export function DetalheAdministrativoPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(true);
  const [administrativo, setAdministrativo] = useState<Administrativo | null>(
    null,
  );

  function carregar() {
    if (!id) return;
    setIsLoading(true);
    administrativoService
      .buscarPorId(Number(id))
      .then(setAdministrativo)
      .finally(() => setIsLoading(false));
  }

  useEffect(carregar, [id]);

  if (isLoading) return <LoadingState />;
  if (!administrativo) return <EmptyState />;

  return (
    <DetailShell>
      <DetailHeader
        title={administrativo.pessoa.nome}
        breadcrumbs={[
          { label: 'Início', link: paths.home },
          { label: 'Equipe Administrativa', link: paths.administrativo.lista },
          { label: administrativo.pessoa.nome },
        ]}
        actions={[
          <EditButton
            onClick={() =>
              navigate(paths.administrativo.editar(administrativo.pessoa.id))
            }
          />,
        ]}
      />
      <DetailSection title="Informações de Contato">
        <DetailField
          label="E-mail para Contato"
          value={administrativo.pessoa.email}
        />
        <DetailField label="Telefone" value={administrativo.pessoa.telefone} />
      </DetailSection>

      <DetailSection title="Acesso ao Sistema">
        <DetailField label="Login" value={administrativo.usuario.login} />
        <DetailField
          label="Criado em"
          value={formatarData(administrativo.usuario.criadoEm, 'curto')}
        />
        {administrativo.usuario.desativadoEm && (
          <DetailField
            label="Desativado em"
            value={formatarData(administrativo.usuario.desativadoEm, 'curto')}
          />
        )}
      </DetailSection>
    </DetailShell>
  );
}
