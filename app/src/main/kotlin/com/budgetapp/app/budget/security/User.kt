package com.budgetapp.app.budget.security

import lombok.Data


@Data
class User {
    var uid: String? = null
    var name: String? = null
    var email: String? = null
    var isEmailVerified = false
    var issuer: String? = null
    var picture: String? = null
}