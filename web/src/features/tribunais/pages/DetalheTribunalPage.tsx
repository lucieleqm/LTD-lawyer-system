import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Tribunal } from '../types';
import { tribunaisService } from '../services/tribunaisService';
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
import { esferaJusticaLabel } from '../constants';

export function DetalheTribunalPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const podeEditar = usePermission('tribunais:editar');

  const [isLoading, setIsLoading] = useState(true);
  const [tribunal, setTribunal] = useState<Tribunal | null>(null);

  function carregar() {
    if (!id) return;
    setIsLoading(true);
    tribunaisService
      .buscarPorId(Number(id))
      .then(setTribunal)
      .finally(() => setIsLoading(false));
  }

  useEffect(carregar, [id]);

  if (isLoading) return <LoadingState />;
  if (!tribunal) return <EmptyState />;

  return (
    <DetailShell>
      <DetailHeader
        title={tribunal.nome}
        breadcrumbs={[
          { label: 'Início', link: paths.home },
          { label: 'Tribunais', link: paths.tribunais.lista },
          { label: tribunal.sigla },
        ]}
        actions={[
          <>
            {podeEditar && (
              <EditButton
                onClick={() => navigate(paths.tribunais.editar(tribunal.id))}
              />
            )}
          </>,
        ]}
      />
      <DetailSection title="Informações Gerais">
        <DetailField
          label="Situação"
          value={tribunal.ativo ? 'Ativo' : 'Inativo'}
        />
        <DetailField label="Esfera" value={esferaJusticaLabel[tribunal.esfera]} />
        <DetailField label="Sigla" value={tribunal.sigla} />
        <DetailField label="Criado em" value={formatarData(tribunal.criadoEm)} />
        <DetailField label="Criado por" value={tribunal.criadoPor} />
        <DetailField label="Última atualização" value={formatarData(tribunal.atualizadoEm)} />
        <DetailField label="Atualizado Por" value={tribunal.atualizadoPor} />
      </DetailSection>
    </DetailShell>
  );
}
