import { BaseResponse } from '@/types/baseResponse';
import { EsferaJustica } from './constants';

export interface Tribunal extends BaseResponse {
  nome: string;
  sigla: string;
  esfera: EsferaJustica;
}

export interface TribunalSummary {
  id: number;
  nome: string;
}
