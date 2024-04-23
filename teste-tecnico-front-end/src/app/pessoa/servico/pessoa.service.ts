import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pessoa } from '../modelo/pessoa';

@Injectable({
  providedIn: 'root'
})
export class PessoaService {

  private url: string = 'http://localhost:8080';

    constructor(private http: HttpClient) {

    }

    criaPessoa(pessoa: Pessoa): Observable<Pessoa> {
        return this.http.post<Pessoa>(this.url + '/pessoa', pessoa);
    }

    getAllPessoas(filtro: string): Observable<any> {
        return this.http.get<any>(this.url + '/pessoa?nome=' + filtro);
    }

    getPessoa(id: number): Observable<Pessoa> {
        return this.http.get<Pessoa>(this.url + `/pessoa/${id}`)
    }

    atualizaPessoa(pessoa: Pessoa): Observable<Pessoa> {
        return this.http.put<Pessoa>(this.url + `/pessoa/${pessoa.id}`, pessoa);
    }

    deletaPessoa(id: number): Observable<void> {
        return this.http.delete<void>(this.url + `/pessoa/${id}`);
    }

}
