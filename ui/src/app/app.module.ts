﻿import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { AppRoutingModule } from './app-routing.module';
import { SearchModule } from './search/search.module';
import { AppComponent } from './app.component';
import { AlertComponent } from './_directives/index';
import { AuthGuard } from './_guards/index';
import {
    AlertService,
    AuthenticationService,
    AuthService,
    UserService,
    RepositoryService,
    UserRepositoryService,
    BookmarkService,
    HistorysearchService,
    McssrService
    /*PagerService*/
} from './_services/index';
import { LoginComponent } from './login/index';
import { RegisterComponent } from './register/index';
import { NavbarComponent } from './navbar/index';
import { LoggedinComponent } from './loggedin/index';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import 'hammerjs';
import { FlexLayoutModule } from '@angular/flex-layout';
import { SettingComponent } from './setting/setting.component';
import { SearchHistoryComponent } from './search-history/search-history.component';
import { BreadcrumbComponent } from './navbar/breadcrumb/breadcrumb.component';
import {
    DialogRegistrationRepository,
    RegistrationRepositoryComponent
} from './setting/registration-repository/registration-repository.component';
import { ProfileComponent } from './profile/profile.component';
import {
    MatSlideToggleModule,
    MatProgressSpinnerModule,
    MatMenuModule,
    MatSelectModule,
    MatButtonModule,
    MatDatepickerModule,
    MatToolbarModule,
    MatCardModule,
    MatInputModule,
    MatIconModule,
    MatTooltipModule,
    MatListModule,
    MatDialogModule
} from '@angular/material';
import { ComponentsModule } from './shared/components.module';
// import { SemanticSearchComponent } from './semantic-search/semantic-search.component';
import { NgxPaginationModule } from 'ngx-pagination';


@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        SearchModule,
        AppRoutingModule,
        NoopAnimationsModule,
        FlexLayoutModule,
        MatSlideToggleModule,
        MatProgressSpinnerModule,
        MatMenuModule,
        MatSelectModule,
        MatButtonModule,
        MatDatepickerModule,
        MatToolbarModule,
        MatCardModule,
        MatInputModule,
        MatIconModule,
        MatTooltipModule,
        MatListModule,
        MatDialogModule,
        ComponentsModule,
        NgxPaginationModule
    ],
    declarations: [
        AppComponent,
        AlertComponent,
        LoginComponent,
        RegisterComponent,
        NavbarComponent,
        LoggedinComponent,
        SettingComponent,
        SearchHistoryComponent,
        BreadcrumbComponent,
        DialogRegistrationRepository,
        RegistrationRepositoryComponent,
        ProfileComponent
        // SemanticSearchComponent
    ],
    providers: [
        AuthGuard,
        AlertService,
        AuthenticationService,
        AuthService,
        UserService,
        RepositoryService,
        UserRepositoryService,
        BookmarkService,
        HistorysearchService,
        McssrService
    ],
    entryComponents: [RegistrationRepositoryComponent, DialogRegistrationRepository],
    bootstrap: [AppComponent]
})

export class AppModule { }
