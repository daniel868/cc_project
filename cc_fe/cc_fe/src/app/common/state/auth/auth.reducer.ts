import {ROLE} from "../../../model/role";

export interface AuthState {
  loggedAsGuest: boolean,
  role: ROLE | null
}
