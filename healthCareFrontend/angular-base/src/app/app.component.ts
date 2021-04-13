import { ToastrService } from 'ngx-toastr';
import { Component } from '@angular/core';
import { MenuItem } from '@core/modelo/menu-item';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app-base';
  
  constructor(private toastr: ToastrService){

  }

  public companies: MenuItem[] = [
    { url: '/home', nombre: 'Home' },
    //{ url: '/producto', nombre: 'Gestión Reservas' },
    { url: '/reserva', nombre: "Reservas"}
    
  ];

  submit(){
    this.toastr.success('I am running with Toastr!', 'Success');
  }
  
  cancel(){
    this.toastr.error('How is bad with your request!', 'Error');
  }
}