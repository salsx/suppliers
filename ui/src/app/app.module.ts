﻿import { NgModule } from "@angular/core"
import { BrowserModule } from "@angular/platform-browser"
import { FormsModule } from "@angular/forms"
import { HttpModule } from "@angular/http"
import { AppRoutingModule } from "./app-routing.module"
import { SearchModule } from "./search/search.module"

import { AppComponent } from "./app.component"
import { AppConfig } from "./app.config"

import { AlertComponent } from "./_directives/index"
import { AuthGuard } from "./_guards/index"
import { AlertService, AuthenticationService, UserService, /*PagerService*/ } from "./_services/index"
import { HomeComponent } from "./home/index"
import { LoginComponent } from "./login/index"
import { RegisterComponent } from "./register/index"
import { SidebarComponent } from "./sidebar/index"
import { NavbarComponent } from "./navbar/index"
import { LoggedinComponent } from "./loggedin/index"

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        SearchModule,
        AppRoutingModule
    ],
    declarations: [
        AppComponent,
        AlertComponent,
        HomeComponent,
        LoginComponent,
        RegisterComponent,
        SidebarComponent,
        NavbarComponent,
        LoggedinComponent
    ],
    providers: [
        AppConfig,
        AuthGuard,
        AlertService,
        AuthenticationService,
        UserService
        //PagerService
    ],
    bootstrap: [AppComponent]
})

export class AppModule {}
