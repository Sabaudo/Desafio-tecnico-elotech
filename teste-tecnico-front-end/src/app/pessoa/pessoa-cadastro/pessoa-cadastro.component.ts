import { Component } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Pessoa } from '.././modelo/pessoa';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { PessoaService } from '../servico/pessoa.service';

@Component({
  selector: 'app-pessoa-cadastro',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './pessoa-cadastro.component.html'
})
export class PessoaCadastroComponent {

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private pessoaService: PessoaService){}

  public titulo: string = "Cadastrar Pessoa";
  public pessoa: Pessoa = new Pessoa();

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(parametro => {
      if (parametro["id"] == undefined) {
        this.titulo = "Cadastrar Pessoa";
      } else if (parametro["id"] != undefined) {
        this.carregarPessoa(Number(parametro["id"]))
      } 
    });

  }

  redirectListagem() {
    this.router.navigate(['/pessoa']);
  }

  carregarPessoa(id: number) {
    this.titulo = "Alterar Pessoa";
    this.pessoaService.getPessoa(id).subscribe(res => {
      this.pessoa = res;
    });

  }

  submit() {
    if(this.pessoa.id == null) {
      this.pessoaService.criaPessoa(this.pessoa).subscribe(res => {
        alert('Pessoa criada com sucesso!')
        this.redirectListagem();
      });  
    } else {
      this.pessoaService.atualizaPessoa(this.pessoa).subscribe(res => {
        alert('Pessoa atualizada com sucesso!')
        this.redirectListagem();
      })
    }
  }

  adicionarContato(): void {
    this.pessoa.contatos.push({
      id: 0, nome: '', email: '', telefone: ''
    });
  }

  removerContato(index: number) {
    this.pessoa.contatos.splice(index, 1);
  }
}
