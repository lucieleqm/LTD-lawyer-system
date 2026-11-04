import { BaseResponse } from '@/types/baseResponse';
import { TribunalSummary } from '../tribunais/types';

export interface OrgaoJulgador extends BaseResponse {
  nome: string;
  tribunal: TribunalSummary;
  instancia: number;
}

export interface OrgaoJulgadorSummary {
  id: number;
  nome: string;
}
