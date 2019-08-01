import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'prettyprint'
})
export class PrettyPrintPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    return JSON.stringify(value, undefined, 4)
      .replace(/ /g, '&nbsp')
      .replace(/\n/g, '<br/>');
  }
}
