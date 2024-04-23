import { Routes } from '@angular/router';
import { PessoaCadastroComponent } from './pessoa/pessoa-cadastro/pessoa-cadastro.component';
import { PessoaComponent } from './pessoa/pessoa.component';

export const routes: Routes = [
    { path: '', redirectTo: 'pessoa', pathMatch: 'full' },
    {path: 'pessoa-cadastro', component:  PessoaCadastroComponent},
    {path: 'pessoa-cadastro/:id', component:  PessoaCadastroComponent},
    {path: 'pessoa', component: PessoaComponent}
];
