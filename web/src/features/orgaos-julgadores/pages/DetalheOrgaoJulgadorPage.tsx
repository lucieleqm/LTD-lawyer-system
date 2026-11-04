import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { OrgaoJulgador } from '../types';
import { orgaosJulgadoresService } from '../services/orgaosJulgadoresService';
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
import { usePermission } from '@/features/auth/hooks/usePermission';
import { formatarData } from '@/utils/formatters';
import { situacaoRegistro } from '@/constants/situacao';

export function DetalheOrgaoJulgadorPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const podeEditar = usePermission('orgaosJulgadores:editar');

  const [isLoading, setIsLoading] = useState(true);
  const [orgaoJulgador, setOrgaoJulgador] = useState<OrgaoJulgador | null>(
    null,
  );

  function carregar() {
    if (!id) return;
    setIsLoading(true);
    orgaosJulgadoresService
      .buscarPorId(Number(id))
      .then(setOrgaoJulgador)
      .finally(() => setIsLoading(false));
  }

  useEffect(carregar, [id]);

  if (isLoading) return <LoadingState />;
  if (!orgaoJulgador) return <EmptyState />;

  return (
    <DetailShell>
      <DetailHeader
        title={orgaoJulgador.nome}
        breadcrumbs={[
          { label: 'Início', link: paths.home },
          { label: 'Orgãos Julgadores', link: paths.orgaosJulgadores.lista },
          { label: orgaoJulgador.nome },
        ]}
        actions={[
          <>
            {podeEditar && (
              <EditButton
                onClick={() =>
                  navigate(paths.orgaosJulgadores.editar(orgaoJulgador.id))
                }
              />
            )}
          </>,
        ]}
      />
      <DetailSection title="Informações Gerais">
        <DetailField label="Instância" value={orgaoJulgador.instancia} />
        <DetailField label="Tribunal" value={orgaoJulgador.tribunal.nome} />
        <DetailField
          label="Situação"
          value={situacaoRegistro(orgaoJulgador.ativo).label}
        />
        <DetailField
          label="Criado em"
          value={formatarData(orgaoJulgador.criadoEm)}
        />
        <DetailField label="Criado por" value={orgaoJulgador.criadoPor} />
        <DetailField
          label="Última atualização"
          value={formatarData(orgaoJulgador.atualizadoEm)}
        />
        <DetailField
          label="Atualizado Por"
          value={orgaoJulgador.atualizadoPor}
        />
      </DetailSection>
    </DetailShell>
  );
}
