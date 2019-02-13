Feature: Inicio de sesion
  Inicio de sesio de usuarios

  Scenario: Iniciar sesion
      Given Con usuario valido
      And contraseña valida
      When validar datos de inicio de sesion
      Then inicio de sesion exitoso

  Scenario: Iniciar sesion con datos incorrectos
      Given Con usuario valido
      And contraseña no valida
      When validar datos de inicio de sesion
      Then inicio de sesion no exitoso