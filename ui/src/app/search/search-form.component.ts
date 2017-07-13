import { Component, Inject } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router"
import { SearchForm }    from '../_models/search-form';
import { User } from "../_models/user";
import { SearchService } from "./search.service";
import { MultimediaContent } from "../_models/multimediaContent";
import { MdDialog, MdDialogRef } from "@angular/material";
import { DialogDetailComponent } from "./dialog-detail/dialog-detail.component";

@Component({
  selector: 'app-search-form',
  providers: [SearchService],
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css']
})
export class SearchFormComponent {
    submitted = false;
    currentUser: User;
    types = ['Audio', 'Video', 'Text', 'Image'];
    searchForm: SearchForm;
    searchResult: MultimediaContent[];
    searchVideoResult: MultimediaContent[];
    searchImgResult: MultimediaContent[];
    searchAudioResult: MultimediaContent[];
    searchTextResult: MultimediaContent[];
   
   videofilter: Boolean = true;
   audiofilter: Boolean = true;
   textfilter: Boolean = true;
   imagefilter: Boolean = true;

   filterbar:boolean = true;
   showSidebar: boolean = true;

    constructor(private searchService: SearchService, private router: Router){
        this.currentUser = JSON.parse(localStorage.getItem("currentUser"))
        this.searchForm=new SearchForm('','','',new Date(),new Date(),'','')
    }

    onSubmit() {

        this.submitted = true;
        console.log('this.searchForm',this.searchForm);
        localStorage.setItem("searchForm", JSON.stringify(this.searchForm));
        this.search();
        
}


    search(){
        this.searchService.search(this.searchForm)
        .subscribe(
                  res => {
                      this.searchResult=res.json().multimediaContents;
                      console.log('this.searchResult: '+this.searchResult);
                      this.searchVideoResult= this.searchResult.filter(
                        mc => mc.type === 'video');
                        console.log('search video result size: '+this.searchVideoResult.length);
                      this.searchImgResult= this.searchResult.filter(
                        mc => mc.type === 'image');
                        console.log('search image result size: '+this.searchImgResult.length);
                      this.searchAudioResult= this.searchResult.filter(
                        mc => mc.type === 'audio');
                        console.log('search audio result size: '+this.searchAudioResult.length);
                      this.searchTextResult= this.searchResult.filter(
                        mc => mc.type === 'text');
                        console.log('search text result size: '+this.searchTextResult.length);
                      //console.log(this.searchResult);
                      this.submitted = false;
                  },
                  error => {
                      console.log('search - subscribe - error:',error);
                      this.submitted = false;
                  }
                )
    }

sidebar():number {
  if(this.showSidebar){
       return 0;
  }else{
      return -150;
  }
}

filter_button():number {
  if(this.showSidebar){
       return 0;
  }else{
      return 150;
  }
}

  getDate(date:string): string{
    return new Date(date).toString().slice(0,15);
  }

    // TODO: Remove this when we're done
    get diagnostic() { return JSON.stringify(this.searchResult); }

}

