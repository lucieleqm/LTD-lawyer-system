import { api } from '@/lib/api/axios';
import { Professor, ProfessorListItem, ProfessorOption } from '../types';
import { PageResponse } from '@/types/pageResponse';
import { AtualizarProfessorRequest, CriarProfessorRequest } from '../schema';

interface ListarProfessoresParams {
  pageIndex: number;
  pageSize: number;
  termo?: string;
}

export const professoresService = {
  listar: async ({
    pageIndex,
    pageSize,
    termo,
  }: ListarProfessoresParams): Promise<PageResponse<ProfessorListItem>> => {
    const { data } = await api.get('/professores', {
      params: { page: pageIndex, size: pageSize, nome: termo || undefined },
    });
    return data;
  },

  listarOpcoes: async (
    termo?: string,
  ): Promise<{ value: string; label: string }[]> => {
    const { data } = await api.get('professores/opcoes', {
      params: { nome: termo || undefined },
    });

    return data.map((professor: ProfessorOption) => ({
      value: String(professor.id),
      label: professor.nome,
    }));
  },

  buscarPorId: async (id: number): Promise<Professor> => {
    const { data } = await api.get(`/professores/${id}`);
    return data;
  },

  criar: async (dados: CriarProfessorRequest): Promise<Professor> => {
    const { data } = await api.post('/professores', dados);
    return data;
  },

  atualizar: async (
    id: number,
    dados: AtualizarProfessorRequest,
  ): Promise<Professor> => {
    const { data } = await api.put(`/professores/${id}`, dados);
    return data;
  },
};
