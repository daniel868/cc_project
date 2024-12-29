import {Component, Input, OnInit} from '@angular/core';
import {BsModalRef} from "ngx-bootstrap/modal";

@Component({
  selector: 'app-generic-failed-modal',
  templateUrl: './generic-failed-modal.component.html',
  styleUrls: ['./generic-failed-modal.component.scss']
})
export class GenericFailedModalComponent implements OnInit {

  @Input()
  message: string;

  constructor(private modalRef: BsModalRef) {
  }

  ngOnInit(): void {
  }

  closeModal() {
    if (!!this.modalRef) {
      this.modalRef.hide();
    }
  }
}
