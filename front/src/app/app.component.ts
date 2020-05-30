// Componente principal
import {AfterViewInit, Component, OnInit, ViewChild, ViewContainerRef} from '@angular/core';
import {LazyloaderService} from './services/lazy/lazyloader.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, AfterViewInit {

  @ViewChild('header', {read: ViewContainerRef, static: false})
  header: ViewContainerRef;

  @ViewChild('footer', {read: ViewContainerRef, static: false})
  footer: ViewContainerRef;

  title = 'front';

  constructor(private loader: LazyloaderService) {  }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.loader.load('header', this.header).then(() => this.loader.load('footer', this.footer));
  }


}
