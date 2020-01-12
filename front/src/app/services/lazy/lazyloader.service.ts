import {Compiler, Inject, Injectable, Injector, NgModuleFactory, Type, ViewContainerRef} from '@angular/core';
import {LAZY_WIDGETS} from './lazy-tokens';


@Injectable()
export class LazyloaderService {

  constructor(private injector: Injector,
              private compiler: Compiler,
              @Inject(LAZY_WIDGETS) private lazyWidgets: { [key: string]: () =>
              Promise<NgModuleFactory<any> | Type<any>> }) { }

  async load(path: string, container: ViewContainerRef) {
    const ngModule = await this.lazyWidgets[path]();
    const moduleFactory = ngModule instanceof NgModuleFactory ? ngModule : await this.compiler.compileModuleAsync(ngModule);
    const entryComponent = (moduleFactory.moduleType as any).entry;
    const moduleRef = moduleFactory.create(this.injector);
    const compFactory = moduleRef.componentFactoryResolver.resolveComponentFactory(entryComponent);
    const comp = container.createComponent(compFactory);
  }
}



