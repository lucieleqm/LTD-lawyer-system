import z from 'zod';

export const areaDireitoSchema = z.object({
  nome: z.string().trim().min(3, 'O nome é obrigatório'),
});

export type AreaDireitoRequest = z.infer<typeof areaDireitoSchema>;
