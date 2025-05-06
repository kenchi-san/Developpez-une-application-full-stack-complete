import {
  Component,
  HostListener,
  ViewChild,
  AfterViewInit
} from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { AuthService } from '../../service/AuthService';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements AfterViewInit {
  isDesktop: boolean = window.innerWidth >= 768;
  @ViewChild('sidenav') sidenav!: MatSidenav;

  constructor(private authService: AuthService) {}

  @HostListener('window:resize')
  onResize() {
    this.isDesktop = window.innerWidth >= 768;
  }

  ngAfterViewInit(): void {
    this.isDesktop = window.innerWidth >= 768;
  }

  logout(): void {
    this.authService.logout();
  }
}
