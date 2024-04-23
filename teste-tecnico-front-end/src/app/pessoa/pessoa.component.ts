import { Component } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { Pessoa } from './modelo/pessoa';
import { PessoaService } from './servico/pessoa.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-pessoa',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterOutlet, RouterLink],
  templateUrl: './pessoa.component.html',
  styleUrl: './pessoa.component.css'
})
export class PessoaComponent {

  constructor(private pessoaService: PessoaService, private router: Router){}

  public pessoas: Pessoa[] = [];
  public filtro: string = "";

  ngOnInit(): void {
    this.filtrar();
  }

  filtrar() {
    this.getPessoas(this.filtro);
  }

  getPessoas(filtro: string): void {
    this.pessoaService.getAllPessoas(filtro).subscribe(res => {
      this.pessoas = res.content;
    })
  }

  editarPessoa(pessoa: Pessoa) {
    this.router.navigate(['/pessoa-cadastro/', pessoa.id])
  }

  excluirPessoa(pessoa: Pessoa) {
    this.pessoaService.deletaPessoa(pessoa.id).subscribe(res => {
      this.filtrar();
      alert('Pessoa excluída com sucesso!')
    })
  }

  cadastroPessoa() {
    this.router.navigate(['/pessoa-cadastro'])
  }

}
