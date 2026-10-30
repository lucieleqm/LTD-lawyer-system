// contrato com o ProblemDetail da biblioteca 'import org.springframework.http.ProblemDetail'
// utilizada no back-end para respostas de erros.
export interface FieldError {
  field: string;
  message: string;
}

export interface ProblemDetail {
  type: string;
  title: string;
  status: number;
  detail: string;
  instance?: string;
  fields?: FieldError[];   // só existe em erro de validação
}