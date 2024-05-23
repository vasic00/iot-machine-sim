import { Component, OnInit } from '@angular/core';
import { ControlService } from '../control.service';

@Component({
  selector: 'app-machines',
  templateUrl: './machines.component.html',
  styleUrl: './machines.component.css'
})
export class MachinesComponent implements OnInit {

  m1Color: string = "red";
  m2Color: string = "red";
  m3Color: string = "red";

  actionLogs: any[] = [];
  m1Logs: any[] = [];
  m2Logs: any[] = [];
  m3Logs: any[] = [];

  machineSelected: string = "m1";

  constructor(private controlService: ControlService) {
  }

  ngOnInit(): void {
    console.log("MachineComponent init")
    this.controlService.getAllStatus().subscribe((data: any) => {
      console.log(data);
      this.actionLogs = data;
      this.m1Logs = this.actionLogs.filter((action: any) => action.machineId === 1);
      let lastM1Action = this.m1Logs.reduce(function (a, b) { return new Date(a.datetime) > new Date(b.datetime) ? a : b });
      switch (lastM1Action.status) {
        case "shutdown":
          this.m1Color = "red";
          break;
        case "start":
          this.m1Color = "green";
          break;
        case "resume":
          this.m1Color = "green";
          break;
        case "pause":
          this.m1Color = "goldenrod"
          break;
        default:
          break;
      }
      this.m2Logs = this.actionLogs.filter((action: any) => action.machineId === 2);
      let lastM2Action = this.m2Logs.reduce(function (a, b) { return new Date(a.datetime) > new Date(b.datetime) ? a : b });
      switch (lastM2Action.status) {
        case "shutdown":
          this.m2Color = "red";
          break;
        case "start":
          this.m2Color = "green";
          break;
        case "resume":
          this.m2Color = "green";
          break;
        case "pause":
          this.m2Color = "goldenrod"
          break;
        default:
          break;
      }
      this.m3Logs = this.actionLogs.filter((action: any) => action.machineId === 3);
      let lastM3Action = this.m3Logs.reduce(function (a, b) { return new Date(a.datetime) > new Date(b.datetime) ? a : b });
      switch (lastM3Action.status) {
        case "shutdown":
          this.m3Color = "red";
          break;
        case "start":
          this.m3Color = "green";
          break;
        case "resume":
          this.m3Color = "green";
          break;
        case "pause":
          this.m3Color = "goldenrod"
          break;
        default:
          break;
      }
    })
  }

  doAction(action: string) {
    let machineToUpdate = this.machineSelected;
    let previousColor = "";
    if (machineToUpdate === "m1")
      previousColor = this.m1Color;
    else if (machineToUpdate === "m2")
      previousColor = this.m2Color;
    else previousColor = this.m3Color;
    this.controlService.changeStatus(parseInt(machineToUpdate.replace("m", "")), action).subscribe((data: any) => {
      if (previousColor !== "red") {
        let currentColor = "";
        if (action === "PAUSE")
          currentColor = "goldenrod";
        else if (action === "CONT")
          currentColor = "green";
        else currentColor = "red";
        if (machineToUpdate === "m1")
          this.m1Color = currentColor;
        else if (machineToUpdate === "m2")
          this.m2Color = currentColor;
        else this.m3Color = currentColor;
      }
    })
  }

  changeSelection(machine: string) {
    this.machineSelected = machine;
  }

  sortByDate() {
    this.actionLogs.sort((a, b) => {
      return new Date(b.datetime).getTime() - new Date(a.datetime).getTime();
    })
  }

  sortByMachineId() {
    this.actionLogs.sort((a, b) => {
      return a.machineId - b.machineId;
    })
  }

  refreshTable() {
    this.controlService.getAllStatus().subscribe((data: any) => {
      this.actionLogs = data;
    })
  }

}