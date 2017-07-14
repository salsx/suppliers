﻿import { Bookmark } from './../_models/bookmark';
import { Injectable } from "@angular/core"
import { Http, Headers, RequestOptions, Response } from "@angular/http"

import { AppConfig } from "../app.config"


@Injectable()
export class BookmarkService {
    constructor(private http: Http, private config: AppConfig) { }

    getAll() {
        return this.http.get(this.config.apiUrl + "/bookmark", this.jwt()).map((response: Response) => response.json())
    }

    get(id: string) {
        return this.http.get(this.config.apiUrl + "/bookmark/" + id, this.jwt()).map((response: Response) => response.json())
    }

    findByUser() {
        return this.http.get(this.config.apiUrl + "/bookmark/me/", this.jwt()).map((response: Response) => response.json())
    }

    create(bookmark: Bookmark) {
        return this.http.post(this.config.apiUrl + "/bookmark", bookmark, this.jwt())
    }

    delete(id: string) {
        return this.http.delete(this.config.apiUrl + "/bookmark/" + id, this.jwt())
    }

    // private helper methods

    private jwt() {
        // create authorization header with jwt token
        let currentUser = JSON.parse(localStorage.getItem("currentUser"))
        if (currentUser && currentUser.token) {
            let headers = new Headers({ "Authorization": "Bearer " + currentUser.token })
            return new RequestOptions({ headers: headers })
        }
        return null
    }
}