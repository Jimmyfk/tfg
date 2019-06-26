import {Component, Injector, NgModuleFactory, OnInit, SystemJsNgModuleLoader, ViewContainerRef} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  title = 'front';

  constructor(private loader: SystemJsNgModuleLoader,
              private injector: Injector,
              private vcr: ViewContainerRef) {

  }

  ngOnInit(): void {
    this.loader.load('./src/app/common/header/header.module#HeaderModule')
      .then((moduleFactory: NgModuleFactory<any>) => {
        const moduleRef = moduleFactory.create(this.injector);
        const entryComponent = (moduleFactory.moduleType as any).entry;
        const compFactory = moduleRef.componentFactoryResolver.resolveComponentFactory(entryComponent);
        this.vcr.createComponent(compFactory);
      });
  }
}
