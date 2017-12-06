import { Filter } from '../_models/filter';
import { MultimediaContent } from '../_models/multimediaContent';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'filters',
    pure: false
})


export class FilterPipe implements PipeTransform {

    transform(items: MultimediaContent[], activeType: Filter[], activeRepositories: Filter[]): MultimediaContent[] {
        //console.log('activeType',activeType);
        //console.log('items',items);
        //console.log('activeRepositories',activeRepositories);
        return items.filter(item => activeType.findIndex(obj => obj.name === item.type && obj.enabled === true) !== -1)
                    .filter(item2 => activeRepositories.findIndex(obj => obj.name === item2.source.name && obj.enabled === true) !== -1);

    }
}
