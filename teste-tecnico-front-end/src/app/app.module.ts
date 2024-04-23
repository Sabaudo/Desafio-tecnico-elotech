import { NgModule } from "@angular/core";
import {MatToolbarModule} from "@angular/material/toolbar"
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { AppComponent } from "./app.component";

import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { FormsModule } from '@angular/forms'; 
import { HttpClientModule } from "@angular/common/http";
import { CommonModule, DatePipe } from "@angular/common";
import { PessoaService } from "./pessoa/servico/pessoa.service";


@NgModule({
    declarations: [],
    imports: [
        AppComponent,
        BrowserModule,
        BrowserAnimationsModule,
        CommonModule,
        HttpClientModule,
        MatToolbarModule,
        MatSlideToggleModule
    ],
    providers: [DatePipe, PessoaService],
  bootstrap: []
})
export class AppModule {}