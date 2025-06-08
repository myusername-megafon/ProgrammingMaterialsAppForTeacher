package com.example.programmingmaterials.model

abstract class LoginEvent {
    var isConsumed: Boolean = false

    class NavigateMain : LoginEvent()
}
