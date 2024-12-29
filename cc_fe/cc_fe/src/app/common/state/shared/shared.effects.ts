import {inject, Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {GenericSuccessModalComponent} from "../../../modals/generic-success-modal/generic-success-modal.component";
import {GenericFailedAction, GenericSuccessAction} from "./shared.actions";
import {map} from "rxjs";
import {BsModalService, ModalOptions} from "ngx-bootstrap/modal";
import {GenericFailedModalComponent} from "../../../modals/generic-failed-modal/generic-failed-modal.component";

@Injectable()
export class SharedEffects {
  private actions$ = inject(Actions);

  constructor(private modalService: BsModalService) {
  }

  genericSuccessEffect = createEffect(() =>
    this.actions$.pipe(
      ofType(GenericSuccessAction),
      map(actionProps => {
        const initialState = {
          message: actionProps.message // Pass this to the modal
        };
        const modalOptions: ModalOptions = {
          initialState: initialState,
          backdrop: true,  // Enables backdrop click to close the modal
          keyboard: true,  // Close the modal when pressing escape
        };

        this.modalService.show(GenericSuccessModalComponent, modalOptions)
      })
    ), {dispatch: false}
  )

  genericFailedEffect = createEffect(() =>
    this.actions$.pipe(
      ofType(GenericFailedAction),
      map(actionProps => {
        const initialState = {
          message: actionProps.message // Pass this to the modal
        };
        const modalOptions: ModalOptions = {
          initialState: initialState,
          backdrop: true,  // Enables backdrop click to close the modal
          keyboard: true,  // Close the modal when pressing escape
        };

        this.modalService.show(GenericFailedModalComponent, modalOptions)
      })
    ), {dispatch: false}
  )
}
