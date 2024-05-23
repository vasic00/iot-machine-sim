import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductService } from '../product.service';
import { WebSocketService } from '../web-socket.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent implements OnInit {

  formGroup1!: FormGroup;
  formGroup2!: FormGroup;

  selectedPlate: any = null;

  plates: any[] = [];
  platesOnOrder: any[] = [];

  constructor(private formBuilder: FormBuilder,
    private productService: ProductService,
    private webSocketService: WebSocketService) {
  }

  ngOnInit(): void {
    console.log("ProductsComponent init")
    this.formGroup1 = this.formBuilder.group({
      plateId: ['', Validators.required],
      price: ['', [Validators.required, Validators.min]],
      quantity: ['', [Validators.required, Validators.min]]
    });
    this.formGroup2 = this.formBuilder.group({
      buyer: ['', Validators.required],
      buydate: ['', Validators.required],
      paydate: ['', Validators.required],
      address: ['', Validators.required]
    });
    this.productService.getAllPlates().subscribe((data: any) => {
      this.plates = data;
    });
  }

  submitForm1() {
    if (this.formGroup1.valid) {
      let plate = { plateId: this.formGroup1.get("plateId")?.value, price: this.formGroup1.get("price")?.value, quantity: this.formGroup1.get("quantity")?.value };
      this.platesOnOrder.push(plate);
      this.formGroup1.reset();
    }
  }

  submitForm2() {
    if (this.formGroup2.valid && this.platesOnOrder.length > 0) {
      let order = {
        buyer: this.formGroup2.get("buyer")?.value, buydate: this.formGroup2.get("buydate")?.value, paydate: this.formGroup2.get("paydate")?.value,
        address: this.formGroup2.get("address")?.value, items: this.platesOnOrder
      };
      this.webSocketService.sendMessage(JSON.stringify(order));
      this.platesOnOrder = [];
      this.formGroup2.reset();
    }
  }

  updateSelectedPlate(event: Event) {
    let element = event.target as HTMLSelectElement;
    console.log(element.value);
    this.productService.getPlateById(parseInt(element.value.split(": ")[1])).subscribe((data: any) => {
      this.selectedPlate = data;
      console.log(this.selectedPlate);
    })
  }

}
