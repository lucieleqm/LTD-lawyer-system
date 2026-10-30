import { api } from '@/lib/api/axios';
import {
  AtualizarDemandaRequest,
  CriarDemandaRequest,
  CriarTramitacaoRequest,
} from '../schema';
import {
  Demanda,
  DemandaListItem,
  Tramitacao,
  TramitacaoDisponivel,
} from '../types';
import { PageResponse } from '@/types/pageResponse';
import { EtapaDemanda } from '../constants';

interface ListarDemandasParams {
  pageIndex: number;
  pageSize: number;
  termo?: string;
  etapaAtual?: EtapaDemanda;
  tempestividade?: string;
  sort?: string[];
}

export const demandasService = {
  listar: async ({
    pageIndex,
    pageSize,
    termo,
    etapaAtual,
    tempestividade,
    sort,
  }: ListarDemandasParams): Promise<PageResponse<DemandaListItem>> => {
    const { data } = await api.get('/demandas', {
      params: {
        page: pageIndex,
        size: pageSize,
        termo: termo,
        etapaAtual: etapaAtual,
        tempestividade: tempestividade,
        sort: sort,
      },
    });
    return data;
  },

  buscarPorId: async (id: number): Promise<Demanda> => {
    const { data } = await api.get(`/demandas/${id}`);
    return data;
  },

  criar: async (dados: CriarDemandaRequest): Promise<Demanda> => {
    const { data } = await api.post('/demandas', dados);
    return data;
  },

  listarTramitacoes: async (
    demandaId: number,
  ): Promise<PageResponse<Tramitacao>> => {
    const { data } = await api.get(`/demandas/${demandaId}/tramitacoes`);
    return data;
  },

  listarAcoesDisponiveis: async (
    demandaId: number,
  ): Promise<TramitacaoDisponivel[]> => {
    const { data } = await api.get(
      `/demandas/${demandaId}/tramitacoes/disponiveis`,
    );
    return data;
  },

  tramitar: async (
    demandaId: number,
    dados: CriarTramitacaoRequest,
  ): Promise<void> => {
    await api.post(`/demandas/${demandaId}/tramitacoes`, dados);
  },

  atualizar: async (
    id: number,
    dados: AtualizarDemandaRequest,
  ): Promise<Demanda> => {
    const { data } = await api.put(`/demandas/${id}`, dados);
    return data;
  },
};
