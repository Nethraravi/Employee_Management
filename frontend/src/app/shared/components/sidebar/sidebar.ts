import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
//import {traceDynamicValue} from '@angular/compiler-cli/src/ngtsc/partial_evaluator';
@Component({
  standalone: true,
  selector: 'app-sidebar',
  imports: [RouterLink,MatListModule,MatIconModule],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class Sidebar {}
