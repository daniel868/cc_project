import {Component, Input, OnInit} from '@angular/core';
import {BsModalRef} from "ngx-bootstrap/modal";

@Component({
  selector: 'app-generic-success-modal',
  templateUrl: './generic-success-modal.component.html',
  styleUrls: ['./generic-success-modal.component.scss']
})
export class GenericSuccessModalComponent implements OnInit {
  @Input()
  message: string;

  constructor(private modalRef: BsModalRef) {
  }

  ngOnInit(): void {
  }

  closeModal() {
    if (!!this.modalRef) {
      this.modalRef.hide()
    }
  }
}
