import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  private socket: WebSocket;

  constructor() {

    this.socket = new WebSocket('ws://localhost:8084');

    this.socket.onopen = () => {
      console.log('WebSocket connection opened');
    };

    this.socket.onerror = (error) => {
      console.error('WebSocket error:', error);
    };
  }

  sendMessage(message: string) {
    if (this.socket.readyState === WebSocket.OPEN) {
      this.socket.send(message);
    } else {
      console.error('WebSocket connection is not open');
    }
  }

}
