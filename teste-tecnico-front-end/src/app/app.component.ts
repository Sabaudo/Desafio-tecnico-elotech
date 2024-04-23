import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink } from '@angular/router';
import { PessoaCadastroComponent } from './pessoa/pessoa-cadastro/pessoa-cadastro.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { PessoaComponent } from './pessoa/pessoa.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,
    RouterLink, 
    CommonModule, 
    PessoaCadastroComponent, 
    PessoaComponent, 
    MatToolbarModule
  ],
    
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'teste-elotech';
}
