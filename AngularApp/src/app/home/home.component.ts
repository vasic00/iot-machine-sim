import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})

export class HomeComponent implements OnInit {

  showMachines: boolean = true;

  constructor() {

  }

  ngOnInit(): void {

  }

  showComponent(flag: boolean, machinesButton: HTMLElement, productsButton: HTMLElement) {
    this.showMachines = flag;
    if (flag) {
      machinesButton.classList.remove("not-selected");
      machinesButton.classList.add("selected");
      productsButton.classList.remove("selected");
      productsButton.classList.add("not-selected");
    }
    else {
      machinesButton.classList.remove("selected");
      machinesButton.classList.add("not-selected");
      productsButton.classList.remove("not-selected");
      productsButton.classList.add("selected");
    }
  }

}
