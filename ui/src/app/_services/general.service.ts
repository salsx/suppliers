import { Injectable } from "@angular/core"
import { Http, Headers, RequestOptions, Response } from "@angular/http"
import { environment } from '../../environments/environment';
import { MultimediaContent } from './../_models/multimediaContent';
import { mediafile } from './../_models/mediafile';

@Injectable()
export class GeneralService {

    constructor(private http: Http) { }

    get(url: string) {
        return this.http.get(url)
            .map((response: Response) => response.json());

    }

}