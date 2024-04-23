import { Contato } from "./contato";

export class Pessoa {
  id: number;
  nome: string;
  cpf: string;
  dataNascimento: string;
  contatos: Contato[] = new Array();

  constructor() {
    
  }

}