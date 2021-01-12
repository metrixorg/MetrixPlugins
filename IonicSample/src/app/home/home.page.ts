import { Component } from '@angular/core';
import { Metrix } from '@ionic-native/metrix';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {

  constructor() {

  }

  sendSimpleEvent() {
    Metrix.newEvent("bsfer")
  }

  sendEventWithAttributes() {
    var attributes = {};
    attributes['first_name'] = 'Ali';
    attributes['last_name'] = 'Bagheri';
    attributes['manufacturer'] = 'Nike';
    attributes['product_name'] = 'shirt';
    attributes['type'] = 'sport';
    attributes['size'] = 'large';
    Metrix.newEvent("tmgxb", attributes)
  }

  sendSimpleRevenue() {
    Metrix.newRevenue('wwjuz', 3200.55);
  }

  sendFullRevenue() {
    Metrix.newRevenue('qqaan', 35500.155, 0, '150');
  }
}
