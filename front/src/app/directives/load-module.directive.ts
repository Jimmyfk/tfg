import {
  Directive,
  Inject,
  Injector,
  Input,
  NgModuleFactory,
  NgModuleFactoryLoader,
  NgModuleRef,
  OnDestroy,
  OnInit,
  Type,
  ViewContainerRef
} from '@angular/core';
import {LAZY_MODULES, LAZY_MODULES_MAP} from '../common/inyector';

type ModuleWithRoot = Type<any> & { rootComponent: Type<any> };

@Directive({
  selector: '[appLoadModule]'
})
export class LoadModuleDirective implements OnInit, OnDestroy {

  @Input() moduleName: keyof LAZY_MODULES;
  private moduleRef: NgModuleRef<any>;

  constructor(private vcr: ViewContainerRef,
              private injector: Injector,
              private loader: NgModuleFactoryLoader,
              @Inject(LAZY_MODULES_MAP) private modulesMap) {
  }

  ngOnDestroy(): void {
    this.moduleRef.destroy();
  }

  ngOnInit(): void {
    this.loader
      .load(this.modulesMap[this.moduleName])
      .then((moduleFactory: NgModuleFactory<any>) => {
        this.moduleRef = moduleFactory.create(this.injector);
        const rootComponent = (moduleFactory.moduleType as ModuleWithRoot).rootComponent;
        const factory = this.moduleRef.componentFactoryResolver.resolveComponentFactory(rootComponent);
        this.vcr.createComponent(factory);
      });
  }

}
