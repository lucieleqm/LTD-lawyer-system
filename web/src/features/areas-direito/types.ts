import { BaseResponse } from '@/types/baseResponse';

export interface AreaDireito extends BaseResponse {
  nome: string;
}

export interface AreaDireitoSummary {
  id: number;
  nome: string;
}
