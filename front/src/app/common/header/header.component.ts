import {AfterViewInit, Component, OnInit, ViewChild, ViewContainerRef} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {LazyloaderService} from '../../services/lazy/lazyloader.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: []
})
export class HeaderComponent implements OnInit, AfterViewInit {
  @ViewChild('auth', {read: ViewContainerRef, static: false})
  auth: ViewContainerRef;
  constructor(private authService: AuthService,
              private loader: LazyloaderService) {
  }

  ngOnInit() {
  }

  isAdmin() {
    return this.authService.isAdmin();
  }

  ngAfterViewInit(): void {
    this.loader.load('auth', this.auth).then();
  }

}
