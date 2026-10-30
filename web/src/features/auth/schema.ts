import { z } from 'zod';

export const loginSchema = z.object({
  login: z.string().min(1, 'Login é obrigatório'),
  password: z.string().min(1, 'Senha é obrigatória'),
});

export const esqueciSenhaSchema = z.object({
  login: z.string().email('Informe um e-mail válido'),
});

export const redefinirSenhaSchema = z
  .object({
    novaSenha: z.string().min(8, 'A senha deve ter ao menos 8 caracteres'),
    confirmarSenha: z.string(),
  })
  .refine((dados) => dados.novaSenha === dados.confirmarSenha, {
    message: 'As senhas não coincidem',
    path: ['confirmarSenha'], // erro aparece embaixo do campo de confirmação
  });

export type LoginRequest = z.infer<typeof loginSchema>;
export type EsqueciSenhaRequest = z.infer<typeof esqueciSenhaSchema>;
export type RedefinirSenhaRequest = z.infer<typeof redefinirSenhaSchema>;
