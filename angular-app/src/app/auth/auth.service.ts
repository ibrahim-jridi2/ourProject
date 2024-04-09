import {Injectable} from "@angular/core";
import {KeycloakService} from "keycloak-angular";

@Injectable({
  providedIn: "root"
})
export class  AuthService {

  constructor(private keycloakService: KeycloakService) { }

  public getUsername(): string {
    console.log(this.keycloakService.getKeycloakInstance())
    return this.keycloakService.getUsername();
    
  }

  public logout(): void {
    this.keycloakService.logout().then(() => this.keycloakService.clearToken());
  }

}
