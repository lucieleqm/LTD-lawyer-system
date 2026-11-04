export interface Option {
  value: string;
  label: string;
}

export interface SliceResponse<T> {
  content: T[];
  page: number;
  size: number;
  hasNext: boolean;
}

export type FetchOptions = (
  termo: string,
  page: number,
) => Promise<SliceResponse<Option>>;
