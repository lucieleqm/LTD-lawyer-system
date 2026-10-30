import { z } from 'zod';

export const enderecoSchema = z.object({
  logradouro: z.string().min(1, 'Logradouro é obrigatório'),
  numero: z.string().min(1, 'Número é obrigatório'),
  bairro: z.string().min(1, 'Bairro é obrigatório'),
  cidade: z.string().min(1, 'Cidade é obrigatória'),
  cep: z.string().regex(/^\d{5}-?\d{3}$/, 'CEP inválido'),
  complemento: z.string().optional(),
});

export type EnderecoInput = z.infer<typeof enderecoSchema>;
