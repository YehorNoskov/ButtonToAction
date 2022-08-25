package com.innovecs.test.domain

enum class ButtonAction(val action: String) {
    ANIMATION("animation"),
    TOAST("toast"),
    CALL("call"),
    NOTIFICATION("notification"),
    ERROR("error")
}