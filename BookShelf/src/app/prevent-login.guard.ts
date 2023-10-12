import { CanActivateFn, Router } from '@angular/router';
import { DOCUMENT } from '@angular/common';
import {inject, Inject, Injectable} from "@angular/core";



export const preventLoginGuard: CanActivateFn = (route, state) => {
  // Verifica se il JSESSIONID è presente nei cookie

  /*var curCookie ="pippo=pippo"
    ", expires=324234"
    ", path=/"
    ", domain=localhost" ;
  document.cookie=curCookie;*/
  //const jSessionId = http.getCookie('JSESSIONID');

  const jSessionId = document.cookie.split(';').find(cookie => cookie.trim().startsWith('Authenticated='));

  if (jSessionId) {
    window.location.reload();
    return false; // L'utente è autenticato
  } else {
    // Reindirizza l'utente alla pagina di login se non è autenticato

    return true;
  }
};
